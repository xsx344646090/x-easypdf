package org.dromara.pdf.pdfbox.handler;


import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.ext.extractor.DocumentExtractor;
import org.dromara.pdf.pdfbox.core.ext.processor.*;

/**
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
     * 获取合并处理器
     *
     * @param document 文档
     * @return 返回合并处理器
     */
    public static MergeProcessor getMergeProcessor(Document document) {
        return new MergeProcessor(document);
    }

    /**
     * 获取拆分处理器
     *
     * @param document 文档
     * @return 返回拆分处理器
     */
    public static SplitProcessor getSplitProcessor(Document document) {
        return new SplitProcessor(document);
    }

    /**
     * 获取图像处理器
     *
     * @param document 文档
     * @return 返回图像处理器
     */
    public static ImageProcessor getImageProcessor(Document document) {
        return new ImageProcessor(document);
    }

    /**
     * 获取替换处理器
     *
     * @param document 文档
     * @return 返回替换处理器
     */
    public static ReplaceProcessor getReplaceProcessor(Document document) {
        return new ReplaceProcessor(document);
    }

    /**
     * 获取打印处理器
     *
     * @param document 文档
     * @return 返回打印处理器
     */
    public static PrintProcessor getPrintProcessor(Document document) {
        return new PrintProcessor(document);
    }

    /**
     * 获取页面处理器
     *
     * @param document 文档
     * @return 返回页面处理器
     */
    public static PageProcessor getPageProcessor(Document document) {
        return new PageProcessor(document);
    }

    /**
     * 获取书签处理器
     *
     * @param document 文档
     * @return 返回书签处理器
     */
    public static BookmarkProcessor getBookmarkProcessor(Document document) {
        return new BookmarkProcessor(document);
    }

    /**
     * 获取表单处理器
     *
     * @param document 文档
     * @return 返回表单处理器
     */
    public static FormProcessor getFormProcessor(Document document) {
        return new FormProcessor(document);
    }

    /**
     * 获取图层处理器
     *
     * @param document 文档
     * @return 返回图层处理器
     */
    public static LayerProcessor getLayerProcessor(Document document) {
        return new LayerProcessor(document);
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
}
