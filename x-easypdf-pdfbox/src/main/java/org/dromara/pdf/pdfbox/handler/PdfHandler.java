package org.dromara.pdf.pdfbox.handler;


import org.apache.pdfbox.filter.Filter;
import org.dromara.pdf.pdfbox.core.base.Banner;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.ext.comparator.DocumentComparator;
import org.dromara.pdf.pdfbox.core.ext.convertor.DocumentConvertor;
import org.dromara.pdf.pdfbox.core.ext.extractor.DocumentExtractor;
import org.dromara.pdf.pdfbox.core.ext.parser.ai.DocumentAIParser;
import org.dromara.pdf.pdfbox.core.ext.processor.DocumentProcessor;
import org.dromara.pdf.pdfbox.core.ext.templater.DocumentTemplater;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.FileUtil;

/**
 * pdf助手
 *
 * @author xsx
 * @date 2023/6/1
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
public class PdfHandler {

    static {
        Banner.print();
    }

    /**
     * 获取字体助手
     *
     * @return 返回字体助手
     */
    public static FontHandler getFontHandler() {
        return FontHandler.getInstance();
    }

    /**
     * 获取文档助手
     *
     * @return 返回文档助手
     */
    public static DocumentHandler getDocumentHandler() {
        return DocumentHandler.getInstance();
    }

    /**
     * 获取文档处理器
     *
     * @param document 文档
     * @return 返回文档处理器
     */
    public static DocumentProcessor getDocumentProcessor(Document document) {
        return new DocumentProcessor(document);
    }

    /**
     * 获取文档分析器
     *
     * @param document 文档
     * @return 返回文档分析器
     */
    public static DocumentAnalyzer getDocumentAnalyzer(Document document) {
        return new DocumentAnalyzer(document);
    }

    /**
     * 获取文档提取器
     *
     * @param document 文档
     * @return 返回文档提取器
     */
    public static DocumentExtractor getDocumentExtractor(Document document) {
        return new DocumentExtractor(document);
    }

    /**
     * 获取文档比较器
     *
     * @param document 文档
     * @return 返回文档比较器
     */
    public static DocumentComparator getDocumentComparator(Document document) {
        return new DocumentComparator(document);
    }

    /**
     * 获取文档AI解析器
     *
     * @param document 文档
     * @return 返回文档AI解析器
     */
    public static DocumentAIParser getDocumentAIParser(Document document) {
        return new DocumentAIParser(document);
    }

    /**
     * 获取文档转换器
     *
     * @return 返回文档转换器
     */
    public static DocumentConvertor getDocumentConvertor() {
        return new DocumentConvertor(null);
    }

    /**
     * 获取文档转换器
     *
     * @param document 文档
     * @return 返回文档转换器
     */
    public static DocumentConvertor getDocumentConvertor(Document document) {
        return new DocumentConvertor(document);
    }

    /**
     * 获取文档模板引擎
     *
     * @return 返回文档模板引擎
     */
    public static DocumentTemplater getDocumentTemplater() {
        return new DocumentTemplater(null);
    }

    /**
     * 开启压缩
     * <p>注：等级越高，压缩率越高，速度越慢</p>
     *
     * @param level 压缩等级（0~9）
     */
    public static void enableCompression(int level) {
        if (level < 0 || level > 9) {
            throw new IllegalArgumentException("the level must be between 0 and 9");
        }
        System.setProperty(Filter.SYSPROP_DEFLATELEVEL, String.valueOf(level));
    }

    /**
     * 关闭系统字体扫描
     */
    public static void disableScanSystemFonts() {
        System.setProperty(Constants.FONT_SCAN_SWITCH, "false");
    }

    /**
     * 关闭标语
     */
    public static void disableBanner() {
        Banner.disable();
    }

    /**
     * 清理字体缓存
     *
     * @return 返回布尔值，true为是，false为否
     */
    public static boolean clearFontCache() {
        return FileUtil.getFontCacheFile().delete();
    }
}
