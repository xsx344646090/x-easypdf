package wiki.xsx.core.pdf.handler;

import lombok.SneakyThrows;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.font.*;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pdf字体映射助手
 *
 * @author xsx
 * @date 2022/6/20
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 * </p>
 */
public class XEasyPdfFontMapperHandler implements FontMapper {

    /**
     * 字体名称映射字典
     */
    private static final ConcurrentHashMap<String, FontBoxFont> FONT_NAME_MAPPING = new ConcurrentHashMap<>(16);
    /**
     * 字体路径映射字典
     */
    private static final ConcurrentHashMap<String, FontBoxFont> FONT_PATH_MAPPING = new ConcurrentHashMap<>(16);
    /**
     * 默认字体名称
     */
    private static final String DEFAULT_FONT_NAME = XEasyPdfDefaultFontStyle.NORMAL.getName();
    /**
     * 字体映射助手实例
     */
    private static final XEasyPdfFontMapperHandler INSTANCE = new XEasyPdfFontMapperHandler();

    /**
     * 有参构造
     */
    private XEasyPdfFontMapperHandler() {
        // 初始化
        this.init(XEasyPdfDefaultFontStyle.LIGHT, XEasyPdfDefaultFontStyle.NORMAL, XEasyPdfDefaultFontStyle.BOLD);
    }

    /**
     * 获取字体映射实例
     *
     * @return 返回字体映射实例
     */
    public static XEasyPdfFontMapperHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 添加字体
     *
     * @param fontPath 字体路径
     * @param font     字体
     */
    @SneakyThrows
    public void addFont(String fontPath, FontBoxFont font) {
        FONT_NAME_MAPPING.putIfAbsent(font.getName(), font);
        FONT_PATH_MAPPING.putIfAbsent(fontPath, font);
    }

    /**
     * 根据字体路径获取字体
     *
     * @param fontPath 字体路径
     * @return 返回字体
     */
    @SneakyThrows
    public FontBoxFont getFontByPath(String fontPath) {
        return FONT_PATH_MAPPING.get(fontPath);
    }

    /**
     * 获取ttf字体
     *
     * @param baseFont       字体名称
     * @param fontDescriptor 字体描述
     * @return 返回ttf字体
     */
    @Override
    public FontMapping<TrueTypeFont> getTrueTypeFont(String baseFont, PDFontDescriptor fontDescriptor) {
        // 查找字体
        TrueTypeFont ttf = (TrueTypeFont) this.findFont(baseFont);
        // 如果字体不为空，则返回字体
        if (ttf != null) {
            // 返回字体
            return new FontMapping<>(ttf, false);
        }
        // 返回默认字体
        return new FontMapping<>((TrueTypeFont) FONT_NAME_MAPPING.get(DEFAULT_FONT_NAME), true);
    }

    /**
     * 获取字体
     *
     * @param baseFont       字体名称
     * @param fontDescriptor 字体描述
     * @return 返回字体
     */
    @Override
    public FontMapping<FontBoxFont> getFontBoxFont(String baseFont, PDFontDescriptor fontDescriptor) {
        // 查找字体
        FontBoxFont font = this.findFont(baseFont);
        // 如果字体不为空，则返回字体
        if (font != null) {
            // 返回字体
            return new FontMapping<>(font, false);
        }
        // 返回默认字体
        return new FontMapping<>(FONT_NAME_MAPPING.get(DEFAULT_FONT_NAME), true);
    }

    /**
     * 获取字体CID信息
     *
     * @param baseFont       字体名称
     * @param fontDescriptor 字体描述
     * @param cidSystemInfo  CID系统信息
     * @return 返回字体CID信息
     */
    @Override
    public CIDFontMapping getCIDFont(String baseFont, PDFontDescriptor fontDescriptor, PDCIDSystemInfo cidSystemInfo) {
        // 查找字体
        FontBoxFont font = this.findFont(baseFont);
        // 如果字体为otf类型，则返回otf类型信息
        if (font instanceof OpenTypeFont) {
            // 返回otf类型信息
            return new CIDFontMapping((OpenTypeFont) font, null, false);
        }
        // 如果字体为ttf类型，则返回ttf类型信息
        if (font instanceof TrueTypeFont) {
            // 返回ttf类型信息
            return new CIDFontMapping(null, font, false);
        }
        // 返回默认字体类型信息
        return new CIDFontMapping(null, FONT_NAME_MAPPING.get(DEFAULT_FONT_NAME), true);
    }

    /**
     * 初始化
     *
     * @param styles 字体样式
     */
    private void init(XEasyPdfDefaultFontStyle... styles) {
        // 遍历字体样式
        for (XEasyPdfDefaultFontStyle style : styles) {
            // 初始化输入流（从资源路径读取）
            try (InputStream inputStream = new BufferedInputStream(XEasyPdfFontMapperHandler.class.getResourceAsStream(style.getPath()))) {
                // 解析字体
                TrueTypeFont font = new TTFParser(true, true).parse(inputStream);
                // 添加字体
                this.addFont(style.getPath(), font);
            } catch (IOException e) {
                // 提示异常信息
                throw new RuntimeException(e);
            }
        }
        // 初始化字体映射
        this.initFontMapper();
    }

    /**
     * 初始化字体映射
     */
    private void initFontMapper() {
        // 获取字体映射策略
        String fontMappingPolicy = System.getProperty(
                XEasyPdfHandler.FontMappingPolicy.key(),
                XEasyPdfHandler.FontMappingPolicy.DEFAULT.name()
        );
        // 如果字体映射策略为默认，则设置字体映射
        if (fontMappingPolicy.equals(XEasyPdfHandler.FontMappingPolicy.DEFAULT.name())) {
            // 设置字体映射
            FontMappers.set(this);
        }
    }

    /**
     * 查找字体
     *
     * @param postScriptName 字体名称
     * @return 返回字体
     */
    private FontBoxFont findFont(String postScriptName) {
        // 如果字体名称为空，则返回空
        if (postScriptName == null) {
            // 返回空
            return null;
        }
        // 获取字体
        FontBoxFont info = this.getFont(postScriptName);
        // 如果字体不为空，则返回字体
        if (info != null) {
            // 返回字体
            return info;
        }
        // 重新获取字体（替换字体名称，移除“-”）
        info = this.getFont(postScriptName.replace("-", ""));
        // 如果字体不为空，则返回字体
        if (info != null) {
            // 返回字体
            return info;
        }
        // 重新获取字体（替换字体名称，替换“,”为“-”）
        info = this.getFont(postScriptName.replace(',', '-'));
        // 如果字体不为空，则返回字体
        if (info != null) {
            // 返回字体
            return info;
        }
        // 重新获取字体（替换字体名称，添加“-Regular”）
        return this.getFont(postScriptName + "-Regular");
    }

    /**
     * 获取字体
     *
     * @param postScriptName 字体名称
     * @return 返回字体
     */
    private FontBoxFont getFont(String postScriptName) {
        // 如果字体名称包含“+”，则截取子集名称
        if (postScriptName.contains("+")) {
            // 重置字体名称（截取子集名称）
            postScriptName = postScriptName.substring(postScriptName.indexOf('+') + 1);
        }
        // 获取字体
        return FONT_NAME_MAPPING.get(postScriptName);
    }
}
