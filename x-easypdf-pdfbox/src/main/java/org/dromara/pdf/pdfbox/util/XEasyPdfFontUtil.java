package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;
import org.dromara.pdf.pdfbox.handler.XEasyPdfFontMapperHandler;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

/**
 * 字体工具
 *
 * @author xsx
 * @date 2020/4/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfFontUtil {

    /**
     * ttf字体
     */
    private static final String TTF = ".ttf";
    /**
     * ttc字体集合
     */
    private static final String TTC = ".ttc";
    /**
     * otf字体
     */
    private static final String OTF = ".otf";
    /**
     * 字体集合分隔符
     */
    private static final String COLLECTION_FONT_SEPARATOR = ",";
    /**
     * ttf字体锁
     */
    private static final Object TTF_LOCK = new Object();
    /**
     * ttc字体集合锁
     */
    private static final Object TTC_LOCK = new Object();
    /**
     * otf字体锁
     */
    private static final Object OTF_LOCK = new Object();

    /**
     * 获取字体高度
     *
     * @param font     pdfbox字体
     * @param fontSize 字体大小
     * @return 返回字体高度
     */
    public static float getFontHeight(PDFont font, float fontSize) {
        return font.getFontDescriptor().getCapHeight() / 1000F * fontSize;
    }

    /**
     * 添加文本关联
     *
     * @param font pdfbox字体
     * @param text 文本
     */
    public static void addToSubset(PDFont font, String text) {
        // 如果字体不为空且字体为子集，则添加文本到子集
        if (font != null && font.willBeSubset()) {
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
        }
    }

    /**
     * 添加字体
     * @param fontPath 字体路径
     */
    public static void addFont(String fontPath) {
        try {
            // 获取字体路径
            String lowerPath = fontPath.toLowerCase(Locale.ROOT);
            // 如果字体为ttf字体，则添加ttf字体
            if (lowerPath.endsWith(TTF)) {
                // 添加ttf字体
                addAndGetTTFFont(fontPath);
            }
            // 如果字体为ttc字体集合，则添加ttc字体集合
            if (lowerPath.contains(TTC)) {
                // 添加ttc字体集合
                addAndGetTTCFont(fontPath);
            }
            // 如果字体为otf字体，则添加otf字体
            if (lowerPath.endsWith(OTF)) {
                // 添加otf字体
                addAndGetOTFFont(fontPath);
            }
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the font can not be loaded，the path['" + fontPath + "'] is error");
        }
    }

    /**
     * 获取字体
     *
     * @param document    pdf文档
     * @param fontPath    字体路径
     * @param defaultFont 默认字体
     * @return 返回pdfBox字体
     */
    public static PDFont getFont(XEasyPdfDocument document, String fontPath, PDFont defaultFont) {
        // 如果字体路径不为空，则加载字体
        if (fontPath != null) {
            // 加载字体
            return loadFont(document, fontPath, true);
        }
        // 返回默认字体
        return defaultFont;
    }

    /**
     * 加载字体
     *
     * @param document   pdf文档
     * @param page       pdf页面
     * @param fontPath   字体路径
     * @param isEmbedded 是否嵌入
     * @return 返回pdfBox字体
     */
    public static PDFont loadFont(XEasyPdfDocument document, XEasyPdfPage page, String fontPath, boolean isEmbedded) {
        // 如果字体路径不为空，则加载字体
        if (fontPath != null) {
            // 加载字体
            return loadFont(document, fontPath, isEmbedded);
        }
        // 否则加载文档字体
        else {
            // 加载文档字体
            return loadFont(document, page);
        }
    }

    /**
     * 加载字体
     *
     * @param document   pdf文档
     * @param fontPath   字体路径
     * @param isEmbedded 是否嵌入
     * @return 返回pdfBox字体
     */
    public static PDFont loadFont(XEasyPdfDocument document, String fontPath, boolean isEmbedded) {
        // 如果字体路径不为空，则加载字体
        if (fontPath != null) {
            // 定义pdfbox字体
            PDFont font;
            // 如果需要嵌入，则从缓存获取
            if (isEmbedded) {
                // 获取字体
                font = document.getFont(fontPath);
                // 如果字体不为空，则返回字体
                if (font != null) {
                    // 返回字体
                    return font;
                }
            }
            // 获取字体路径
            String lowerPath = fontPath.toLowerCase(Locale.ROOT);
            // 如果字体为ttf字体，则加载ttf字体
            if (lowerPath.endsWith(TTF)) {
                // 加载ttf字体
                return loadTTF(document, fontPath, isEmbedded);
            }
            // 如果字体为ttc字体集合，则加载ttc字体集合
            if (lowerPath.contains(TTC)) {
                // 加载ttc字体集合
                return loadTTC(document, fontPath, isEmbedded);
            }
            // 如果字体为otf字体，则加载otf字体
            if (lowerPath.endsWith(OTF)) {
                // 加载otf字体
                return loadOTF(document, fontPath);
            }
        }
        // 提示错误信息
        throw new IllegalArgumentException("the font can not be loaded，the path['" + fontPath + "'] is error");
    }

    /**
     * 加载字体
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回pdfBox字体
     */
    private static PDFont loadFont(XEasyPdfDocument document, XEasyPdfPage page) {
        // 定义pdfbox字体
        PDFont font = null;
        // 如果页面不为空，则重置为页面字体
        if (page != null) {
            // 重置为页面字体
            font = page.getFont();
        }
        // 如果文档不为空，则重置为文档字体
        else if (document != null) {
            // 重置为文档字体
            font = document.getFont();
        }
        // 如果字体为空，则提示错误信息
        if (font == null) {
            // 提示错误信息
            throw new IllegalArgumentException("the font can not be found");
        }
        // 返回pdfbox字体
        return font;
    }

    /**
     * 加载ttf字体
     *
     * @param document   pdf文档
     * @param fontPath   字体路径
     * @param isEmbedded 是否嵌入
     * @return 返回pdfBox字体
     */
    private static PDFont loadTTF(XEasyPdfDocument document, String fontPath, boolean isEmbedded) {
        try {
            // 加载字体
            PDFont font = PDType0Font.load(document.getTarget(), addAndGetTTFFont(fontPath), isEmbedded);
            // 如果需要嵌入，则添加字体缓存
            if (isEmbedded) {
                // 添加字体缓存
                document.addFont(fontPath, font);
            }
            // 返回pdfbox字体
            return font;
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the font can not be loaded，the path['" + fontPath + "'] is error");
        }
    }

    /**
     * 添加并获取ttf字体
     *
     * @param fontPath 字体路径
     * @return 返回ttf字体
     */
    @SneakyThrows
    private static TrueTypeFont addAndGetTTFFont(String fontPath) {
        // 从缓存中获取字体
        TrueTypeFont trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
        // 如果字体为空，则读取字体
        if (trueTypeFont == null) {
            // 加锁
            synchronized (TTF_LOCK) {
                // 再次从缓存中获取字体
                trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
                // 如果仍然为空，则读取字体文件
                if (trueTypeFont == null) {
                    // 初始化输入流（从资源路径读取）
                    try (InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fontPath)))) {
                        // 解析ttf字体
                        trueTypeFont = new TTFParser(true, true).parse(inputStream);
                    } catch (Exception e) {
                        // 重置输入流（从绝对路径读取）
                        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fontPath)))) {
                            // 重置ttf字体
                            trueTypeFont = new TTFParser(true, true).parse(inputStream);
                        }
                    }
                    // 添加字体缓存
                    XEasyPdfFontMapperHandler.getInstance().addFont(fontPath, trueTypeFont);
                }
            }
        }
        // 返回字体
        return trueTypeFont;
    }

    /**
     * 加载ttc字体
     *
     * @param document   pdf文档
     * @param fontPath   字体路径
     * @param isEmbedded 是否嵌入
     * @return 返回pdfBox字体
     */
    private static PDFont loadTTC(XEasyPdfDocument document, String fontPath, boolean isEmbedded) {
        try {
            // 加载字体
            PDFont font = PDType0Font.load(document.getTarget(), addAndGetTTCFont(fontPath), isEmbedded);
            // 如果需要嵌入，则添加字体缓存
            if (isEmbedded) {
                // 添加字体缓存
                document.addFont(fontPath, font);
            }
            // 返回pdfbox字体
            return font;
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the font can not be loaded，the path['" + fontPath + "'] is error");
        }
    }

    /**
     * 添加并获取ttc字体
     *
     * @param fontPath 字体路径
     * @return 返回ttc字体
     */
    @SneakyThrows
    private static TrueTypeFont addAndGetTTCFont(String fontPath) {
        // 定义字体路径拆分长度
        final int length = 2;
        // 拆分字体路径
        String[] fontPathSplit = fontPath.split(COLLECTION_FONT_SEPARATOR);
        // 如果拆分字体路径长度不等于定义的长度，则提示错误信息
        if (fontPathSplit.length != length) {
            // 提示错误信息
            throw new IllegalArgumentException();
        }
        // 从缓存中获取字体
        TrueTypeFont trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
        // 如果字体为空，则读取字体
        if (trueTypeFont == null) {
            // 加锁
            synchronized (TTC_LOCK) {
                // 再次从缓存中获取字体
                trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
                // 如果仍然为空，则读取字体文件
                if (trueTypeFont == null) {
                    // 初始化输入流（从资源路径读取）
                    try (InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fontPathSplit[0])))) {
                        // 创建ttc字体集合
                        TrueTypeCollection trueTypeCollection = new TrueTypeCollection(inputStream);
                        // 反射获取调用方法
                        Method method = trueTypeCollection.getClass().getDeclaredMethod("getFontAtIndex", int.class);
                        // 设置访问权限
                        method.setAccessible(true);
                        // 解析ttf字体
                        trueTypeFont = (TrueTypeFont) method.invoke(trueTypeCollection, Integer.parseInt(fontPathSplit[1]));
                    } catch (Exception e) {
                        // 重置输入流（从绝对路径读取）
                        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fontPathSplit[0])))) {
                            // 创建ttc字体集合
                            TrueTypeCollection trueTypeCollection = new TrueTypeCollection(inputStream);
                            // 反射获取调用方法
                            Method method = trueTypeCollection.getClass().getDeclaredMethod("getFontAtIndex", int.class);
                            // 设置访问权限
                            method.setAccessible(true);
                            // 解析ttf字体
                            trueTypeFont = (TrueTypeFont) method.invoke(trueTypeCollection, Integer.parseInt(fontPathSplit[1]));
                        }
                    }
                    // 添加字体缓存
                    XEasyPdfFontMapperHandler.getInstance().addFont(fontPath, trueTypeFont);
                }
            }
        }
        // 返回字体
        return trueTypeFont;
    }

    /**
     * 加载otf字体
     *
     * @param document pdf文档
     * @param fontPath 字体路径
     * @return 返回pdfBox字体
     */
    private static PDFont loadOTF(XEasyPdfDocument document, String fontPath) {
        try {
            // 从缓存获取pdfbox字体
            PDFont font = document.getOtfFont(fontPath);
            // 如果字体不为空，则返回字体
            if (font != null) {
                // 返回字体
                return font;
            }
            // 加载字体
            font = PDType0Font.load(document.getTarget(), addAndGetOTFFont(fontPath), false);
            // 添加字体缓存
            document.addOtfFont(fontPath, font);
            // 返回pdfbox字体
            return font;
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the font can not be loaded，the path['" + fontPath + "'] is error");
        }
    }

    /**
     * 添加并获取otf字体
     *
     * @param fontPath 字体路径
     * @return 返回otf字体
     */
    @SneakyThrows
    private static TrueTypeFont addAndGetOTFFont(String fontPath) {
        // 从缓存中获取字体
        TrueTypeFont trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
        // 如果字体为空，则读取字体
        if (trueTypeFont == null) {
            // 加锁
            synchronized (OTF_LOCK) {
                // 再次从缓存中获取字体
                trueTypeFont = (TrueTypeFont) XEasyPdfFontMapperHandler.getInstance().getFontByPath(fontPath);
                // 如果仍然为空，则读取字体文件
                if (trueTypeFont == null) {
                    // 初始化输入流（从资源路径读取）
                    try (InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fontPath)))) {
                        // 解析otf字体
                        trueTypeFont = new OTFParser(false, true).parse(inputStream);
                    } catch (Exception e) {
                        // 重置输入流（从绝对路径读取）
                        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fontPath)))) {
                            // 重置otf字体
                            trueTypeFont = new OTFParser(false, true).parse(inputStream);
                        }
                    }
                    // 添加字体缓存
                    XEasyPdfFontMapperHandler.getInstance().addFont(fontPath, trueTypeFont);
                }
            }
        }
        // 返回字体
        return trueTypeFont;
    }
}
