package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.ext.processor.FormProcessor;
import org.dromara.pdf.pdfbox.core.info.CommentInfo;
import org.dromara.pdf.pdfbox.core.info.TextInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xsx
 * @date 2023/11/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
public class FormProcessorTest {

    @Before
    public void setUp() {
        // 初始化日志实现
        System.setProperty("org.apache.commons.logging.log", "org.apache.commons.logging.impl.SimpleLog");
        // 初始化日志级别
        System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "debug");
    }

    @Test
    public void testText() {
        try (
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\watermark\\textarea\\testDocument.pdf");
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
        ) {
            List<TextInfo> infoList = analyzer.analyzeText();
            infoList.forEach(System.out::println);
        }
    }

    @Test
    public void testComment() {
        try (
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\test\\test.pdf");
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
        ) {
            List<CommentInfo> infoList = analyzer.analyzeComment();
            infoList.forEach(System.out::println);
        }
    }

    @Test
    public void testImage() {
        try (
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\fo\\test\\test1.pdf");
                FormProcessor processor = new FormProcessor(document);
        ) {
            Map<String, BufferedImage> map = new HashMap<>(1);
            map.put("image", null);
            processor.fillImage(map);
            document.save("E:\\PDF\\fo\\test\\test2.pdf");
        }
    }

}
