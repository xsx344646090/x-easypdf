package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.ext.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.info.BookmarkInfo;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;
import org.dromara.pdf.pdfbox.core.info.TextInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.List;

/**
 * @author xsx
 * @date 2023/10/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class DocumentAnalyzerTest {

    @Test
    public void testText() {
        try(
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\watermark\\textarea\\testDocument.pdf");
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
        ) {
            List<TextInfo> infoList = analyzer.analyzeText().getTextInfoList();
            infoList.forEach(System.out::println);
        }
    }

    @Test
    public void testImage() {
        try(
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\watermark\\image\\testDocument.pdf");
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
        ) {
            List<ImageInfo> infoList = analyzer.analyzeImage().getImageInfoList();
            infoList.forEach(System.out::println);
        }
    }

    @Test
    public void testBookmark() {
        try(
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\bookmark\\testDocument.pdf");
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
        ) {
            List<BookmarkInfo> infoList = analyzer.analyzeBookmark().getBookmarkInfoList();
            infoList.forEach(System.out::println);
        }
    }
}
