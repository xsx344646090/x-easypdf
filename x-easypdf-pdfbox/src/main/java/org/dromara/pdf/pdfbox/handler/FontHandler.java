package org.dromara.pdf.pdfbox.handler;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.FontFormat;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.FontType;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.support.fonts.FontInfo;
import org.dromara.pdf.pdfbox.support.fonts.FontMapperImpl;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * 字体助手
 *
 * @author xsx
 * @date 2023/6/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class FontHandler {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(FontHandler.class);
    /**
     * 助手实例
     */
    private static final FontHandler INSTANCE = new FontHandler();

    /**
     * 无参构造
     */
    private FontHandler() {
        this.addFont(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.DEFAULT_FONT_RESOURCE_PATH),
                Constants.DEFAULT_FONT_NAME,
                FontType.TTF
        );
    }

    /**
     * 获取实例
     *
     * @return 返回实例
     */
    public static FontHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 获取加载的字体名称
     *
     * @return 返回字体名称列表
     */
    public List<String> getFontNames() {
        return new ArrayList<>(FontMapperImpl.getInstance().getFontInfoByName().keySet());
    }

    /**
     * 获取所有字体
     *
     * @return 返回字体信息
     */
    public List<? extends FontInfo> getFontInfos() {
        return FontMapperImpl.getInstance().getProvider().getFontInfo();
    }

    /**
     * 获取pdfbox字体
     *
     * @param document pdf文档
     * @param fontName 字体名称
     * @return 返回pdfBox字体
     */
    public PDFont getPDFont(Document document, String fontName) {
        return this.getPDFont(document.getTarget(), fontName);
    }

    /**
     * 获取pdfbox字体
     *
     * @param document pdfbox文档
     * @param fontName 字体名称
     * @return 返回pdfBox字体
     */
    public PDFont getPDFont(PDDocument document, String fontName) {
        return this.getPDFont(document, fontName, true);
    }

    /**
     * 获取pdfbox字体
     *
     * @param document    文档
     * @param fontName    字体名称
     * @param embedSubset 是否嵌入子集
     * @return 返回pdfBox字体
     */
    @SneakyThrows
    public PDFont getPDFont(Document document, String fontName, boolean embedSubset) {
        return this.getPDFont(document.getTarget(), fontName, embedSubset);
    }

    /**
     * 获取pdfbox字体
     *
     * @param document    pdfbox文档
     * @param fontName    字体名称
     * @param embedSubset 是否嵌入子集
     * @return 返回pdfBox字体
     */
    @SneakyThrows
    public PDFont getPDFont(PDDocument document, String fontName, boolean embedSubset) {
        FontInfo fontInfo = FontMapperImpl.getInstance().getFontInfoByName().get(fontName);
        if (Objects.nonNull(fontInfo) && fontInfo.getFormat() == FontFormat.OTF) {
            embedSubset = false;
        }
        return PDType0Font.load(document, this.getTrueTypeFont(fontName), embedSubset);
    }

    /**
     * 获取字体
     *
     * @param fontName 字体名称
     * @return 返回字体
     */
    public TrueTypeFont getTrueTypeFont(String fontName) {
        FontInfo fontInfo = FontMapperImpl.getInstance().getFontInfoByName().get(fontName);
        return FontMapperImpl.getInstance().getTrueTypeFont(Optional.ofNullable(fontInfo).map(FontInfo::getPostScriptName).orElse(fontName), null).getFont();
    }

    /**
     * 添加自定义字体
     * <p>注：添加一次即可</p>
     *
     * @param file  字体文件
     * @param alias 别名
     */
    public void addFont(File file, String alias) {
        if (Objects.nonNull(file)) {
            FontMapperImpl.getInstance().getProvider().addFont(file, alias);
            if (log.isDebugEnabled()) {
                log.debug("Added font ['" + alias + "']");
            }
        }
    }

    /**
     * 添加自定义字体
     * <p>注：添加一次即可</p>
     *
     * @param files 字体文件
     */
    public void addFont(File... files) {
        if (Objects.nonNull(files)) {
            Arrays.stream(files).forEach(file -> FontMapperImpl.getInstance().getProvider().addFont(file));
        }
    }

    /**
     * 添加自定义字体
     * <p>注：添加一次即可</p>
     *
     * @param files 字体文件
     */
    public void addFont(Collection<File> files) {
        if (Objects.nonNull(files)) {
            files.forEach(file -> FontMapperImpl.getInstance().getProvider().addFont(file));
        }
    }

    /**
     * 添加自定义字体
     * <p>注：添加一次即可</p>
     *
     * @param inputStream 字体文件输入流
     * @param alias       别名
     * @param type        字体类型
     */
    public void addFont(InputStream inputStream, String alias, FontType type) {
        FontMapperImpl.getInstance().getProvider().addFont(inputStream, alias, type);
    }

    /**
     * 添加文本关联
     *
     * @param font pdfbox字体
     * @param text 文本
     */
    @SneakyThrows
    public void addToSubset(PDDocument document, PDFont font, String text) {
        // 如果字体不为空且字体为子集，则添加文本到子集
        if (Objects.nonNull(font) && font.willBeSubset()) {
            // 定义偏移量
            int offset = 0;
            // 获取文本长度
            int length = text.length();
            // 如果偏移量小于文本长度，则添加子集
            while (offset < length) {
                // 获取文本坐标
                int codePoint = text.codePointAt(offset);
                // 添加子集
                font.addToSubset(codePoint);
                // 重置偏移量
                offset += Character.charCount(codePoint);
            }
            // 添加字体到子集
            document.addFontToSubset(font);
        }
    }
}
