package org.dromara.pdf.pdfbox.handler;


import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.ext.extractor.DocumentExtractor;
import org.dromara.pdf.pdfbox.core.ext.processor.DocumentProcessor;

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
}