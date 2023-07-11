package org.dromara.pdf.pdfbox.handler;

import lombok.SneakyThrows;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.font.*;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDefaultFontStyle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pdf字体映射助手
 *
 * @author xsx
 * @date 2022/6/20
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
    public void addFont(String fontPath, TrueTypeFont font) {
        FONT_NAME_MAPPING.putIfAbsent(font.getName(), font);
        Optional.ofNullable(this.getFontAliases(font.getName())).ifPresent(name -> FONT_NAME_MAPPING.putIfAbsent(name, font));
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
    @SuppressWarnings("all")
    private void init(XEasyPdfDefaultFontStyle... styles) {
        // 遍历字体样式
        for (XEasyPdfDefaultFontStyle style : styles) {
            // 初始化输入流（从资源路径读取）
            try (InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(style.getPath())))) {
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
        info = this.getFont(postScriptName + "-Regular");
        // 如果字体不为空，则返回字体
        if (info != null) {
            // 返回字体
            return info;
        }
        // 替换字体名称，去除空格
        String finalName = postScriptName.replaceAll(" ", "");
        // 重新获取字体（替换字体名称，去除空格）
        info = this.getFont(finalName);
        // 如果字体不为空，则返回字体
        if (info != null) {
            // 返回字体
            return info;
        }
        // 获取标准字体名称
        String mappedName = Standard14Fonts.getMappedName(finalName);
        // 如果名称不为空，则返回字体
        if (mappedName != null) {
            // 返回字体
            return FONT_NAME_MAPPING.get(mappedName);
        }
        return null;
    }

    /**
     * 查找字体名称
     *
     * @param postScriptName 字体名称
     * @return 返回字体名称
     */
    private String getFontAliases(String postScriptName) {
        // 如果字体名称为空，则返回空
        if (postScriptName == null) {
            // 返回空
            return null;
        }
        // 返回字体名称
        return Standard14Fonts.getMappedName(postScriptName.replaceAll(" ", ""));
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

    /**
     * 14种标准字体
     */
    private static class Standard14Fonts {

        /**
         * 别名
         */
        private static final Map<String, String> ALIASES = new HashMap<>(100);

        static {
            mapName("Courier-Bold");
            mapName("Courier-BoldOblique");
            mapName("Courier");
            mapName("Courier-Oblique");
            mapName("Helvetica");
            mapName("Helvetica-Bold");
            mapName("Helvetica-BoldOblique");
            mapName("Helvetica-Oblique");
            mapName("Symbol");
            mapName("Times-Bold");
            mapName("Times-BoldItalic");
            mapName("Times-Italic");
            mapName("Times-Roman");
            mapName("ZapfDingbats");

            mapName("CourierCourierNew", "Courier");
            mapName("CourierNew", "Courier");
            mapName("CourierNewPSMT", "Courier");
            mapName("LiberationMono", "Courier");
            mapName("NimbusMonL-Regu", "Courier");

            mapName("CourierNew,Italic", "Courier-Oblique");
            mapName("CourierNewPS-ItalicMT", "Courier-Oblique");
            mapName("CourierNew-Italic", "Courier-Oblique");
            mapName("LiberationMono-Italic", "Courier-Oblique");
            mapName("NimbusMonL-ReguObli", "Courier-Oblique");

            mapName("CourierNew,Bold", "Courier-Bold");
            mapName("CourierNewPS-BoldMT", "Courier-Bold");
            mapName("CourierNew-Bold", "Courier-Bold");
            mapName("LiberationMono-Bold", "Courier-Bold");
            mapName("NimbusMonL-Bold", "Courier-Bold");

            mapName("CourierNew,BoldItalic", "Courier-BoldOblique");
            mapName("CourierNewPS-BoldItalicMT", "Courier-BoldOblique");
            mapName("LiberationMono-BoldItalic", "Courier-BoldOblique");
            mapName("NimbusMonL-BoldObli", "Courier-BoldOblique");

            mapName("Arial", "Helvetica");
            mapName("ArialMT", "Helvetica");
            mapName("LiberationSans", "Helvetica");
            mapName("NimbusSanL-Regu", "Helvetica");

            mapName("Arial,Italic", "Helvetica-Oblique");
            mapName("Arial-ItalicMT", "Helvetica-Oblique");
            mapName("Arial-Italic", "Helvetica-Oblique");
            mapName("Helvetica-Italic", "Helvetica-Oblique");
            mapName("LiberationSans-Italic", "Helvetica-Oblique");
            mapName("NimbusSanL-ReguItal", "Helvetica-Oblique");

            mapName("Arial,Bold", "Helvetica-Bold");
            mapName("Arial-BoldMT", "Helvetica-Bold");
            mapName("Arial-Bold", "Helvetica-Bold");
            mapName("LiberationSans-Bold", "Helvetica-Bold");
            mapName("NimbusSanL-Bold", "Helvetica-Bold");

            mapName("Arial,BoldItalic", "Helvetica-BoldOblique");
            mapName("Arial-BoldItalicMT", "Helvetica-BoldOblique");
            mapName("Helvetica-BoldItalic", "Helvetica-BoldOblique");
            mapName("LiberationSans-BoldItalic", "Helvetica-BoldOblique");
            mapName("NimbusSanL-BoldItal", "Helvetica-BoldOblique");

            mapName("TimesNewRoman", "Times-Roman");
            mapName("TimesNewRomanPSMT", "Times-Roman");
            mapName("TimesNewRoman", "Times-Roman");
            mapName("TimesNewRomanPS", "Times-Roman");
            mapName("LiberationSerif", "Times-Roman");
            mapName("NimbusRomNo9L-Regu", "Times-Roman");

            mapName("TimesNewRoman,Italic", "Times-Italic");
            mapName("TimesNewRomanPS-ItalicMT", "Times-Italic");
            mapName("TimesNewRomanPS-Italic", "Times-Italic");
            mapName("TimesNewRoman-Italic", "Times-Italic");
            mapName("LiberationSerif-Italic", "Times-Italic");
            mapName("NimbusRomNo9L-ReguItal", "Times-Italic");

            mapName("TimesNewRoman,Bold", "Times-Bold");
            mapName("TimesNewRomanPS-BoldMT", "Times-Bold");
            mapName("TimesNewRomanPS-Bold", "Times-Bold");
            mapName("TimesNewRoman-Bold", "Times-Bold");
            mapName("LiberationSerif-Bold", "Times-Bold");
            mapName("NimbusRomNo9L-Medi", "Times-Bold");

            mapName("TimesNewRoman,BoldItalic", "Times-BoldItalic");
            mapName("TimesNewRomanPS-BoldItalicMT", "Times-BoldItalic");
            mapName("TimesNewRomanPS-BoldItalic", "Times-BoldItalic");
            mapName("TimesNewRoman-BoldItalic", "Times-BoldItalic");
            mapName("LiberationSerif-BoldItalic", "Times-BoldItalic");
            mapName("NimbusRomNo9L-MediItal", "Times-BoldItalic");

            mapName("Symbol,Italic", "Symbol");
            mapName("Symbol,Bold", "Symbol");
            mapName("Symbol,BoldItalic", "Symbol");
            mapName("Symbol", "Symbol");
            mapName("SymbolMT", "Symbol");
            mapName("StandardSymL", "Symbol");

            mapName("ZapfDingbatsITCbyBT-Regular", "ZapfDingbats");
            mapName("ZapfDingbatsITC", "ZapfDingbats");
            mapName("Dingbats", "ZapfDingbats");
            mapName("MS-Gothic", "ZapfDingbats");

            mapName("Times", "Times-Roman");
            mapName("Times,Italic", "Times-Italic");
            mapName("Times,Bold", "Times-Bold");
            mapName("Times,BoldItalic", "Times-BoldItalic");

            mapName("ArialMT", "Helvetica");
            mapName("Arial-ItalicMT", "Helvetica-Oblique");
            mapName("Arial-BoldMT", "Helvetica-Bold");
            mapName("Arial-BoldItalicMT", "Helvetica-BoldOblique");
        }

        /**
         * 映射名称
         *
         * @param baseName 基础名称
         */
        private static void mapName(String baseName) {
            ALIASES.put(baseName, baseName);
        }

        /**
         * 映射名称
         *
         * @param alias    别名
         * @param baseName 基础名称
         */
        private static void mapName(String alias, String baseName) {
            ALIASES.put(alias, baseName);
        }

        /**
         * 获取映射名称
         *
         * @param fontName 字体名称
         * @return 返回映射名称
         */
        public static String getMappedName(String fontName) {
            return ALIASES.get(fontName);
        }
    }
}
