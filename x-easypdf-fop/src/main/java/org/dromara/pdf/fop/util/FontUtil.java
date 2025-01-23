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
        InputStream inputStream = null;
        try {
            FontInfo fontInfo = FontInfoHelper.get();
            FontTriplet triplet = fontInfo.findAdjustWeight(name, style.getStyle(), style.getWeight());
            org.apache.fop.fonts.Font font = fontInfo.getFontInstance(triplet, size);
            if (font.getFontTriplet().equals(org.apache.fop.fonts.Font.DEFAULT_FONT)) {
                return new Font(null, style.getAwtStyle(), size);
            }
            URI uri = font.getFontMetrics().getFontURI();
            try {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri.getPath());
                if (Objects.isNull(inputStream)) {
                    inputStream = Files.newInputStream(Paths.get(uri));
                }
            } catch (Exception e) {
                log.warn("the font['" + name + "'] can not be found, use default font");
                return new Font(null, style.getAwtStyle(), size);
            }
            return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(style.getAwtStyle(), size);
        } finally {
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }
}
