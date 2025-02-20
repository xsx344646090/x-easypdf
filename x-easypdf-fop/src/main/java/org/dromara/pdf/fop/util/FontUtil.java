package org.dromara.pdf.fop.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.fop.fonts.FontInfo;
import org.apache.fop.fonts.FontTriplet;
import org.dromara.pdf.fop.support.font.FontInfoHelper;

import java.awt.*;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 字体工具
 *
 * @author xsx
 * @date 2025/1/23
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Slf4j
public class FontUtil {

    /**
     * 创建awt字体
     *
     * @param name  字体名称
     * @param style 字体样式
     * @param size  字体大小
     * @return 返回awt字体
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static Font createAWTFont(String name, FontStyleUtil.FontStyle style, int size) {
        // 初始化输入流为null
        InputStream inputStream = null;
        try {
            // 获取字体信息
            FontInfo fontInfo = FontInfoHelper.get();
            // 返回一个默认的AWT字体
            if(Objects.isNull(fontInfo)) {
                return new Font(name, style.getAwtStyle(), size);
            }
            // 根据字体名称和样式查找合适的字体三元组
            FontTriplet triplet = fontInfo.findAdjustWeight(name, style.getStyle(), style.getWeight());
            // 获取字体实例
            org.apache.fop.fonts.Font font = fontInfo.getFontInstance(triplet, size);
            // 如果获取的字体是默认字体，则返回一个默认的AWT字体
            if (font.getFontTriplet().equals(org.apache.fop.fonts.Font.DEFAULT_FONT)) {
                return new Font(null, style.getAwtStyle(), size);
            }
            try {
                // 获取字体的URI
                URI uri = font.getFontMetrics().getFontURI();
                // 尝试从当前线程的上下文类加载器中获取字体文件输入流
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri.getPath());
                // 如果输入流为null，则尝试从文件系统中获取字体文件输入流
                if (Objects.isNull(inputStream)) {
                    inputStream = Files.newInputStream(Paths.get(uri));
                }
            } catch (Exception e) {
                // 如果获取字体文件失败，则记录警告日志并返回默认字体
                log.warn("the font['" + name + "'] can not be found, use default font");
                return new Font(null, style.getAwtStyle(), size);
            }
            // 创建并返回一个新的AWT字体实例
            return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(style.getAwtStyle(), size);
        } finally {
            // 确保在finally块中关闭输入流，避免资源泄漏
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }
}
