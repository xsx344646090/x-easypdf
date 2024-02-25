package org.dromara.pdf.pdfbox.extractor;

import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.extractor.DocumentExtractor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xsx
 * @date 2023/10/20
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
public class DocumentExtractorTest extends BaseTest {

    @Test
    public void extractTextTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, List<String>> map = extractor.extractText();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractTextByRegexTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, List<String>> map = extractor.extractTextByRegex(null);
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractTextByRegionAreaTest1() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, Map<String, String>> map = extractor.extractTextByRegionArea(
                        Collections.singletonMap("test", document.getCurrentPage().getPageSize().toRectangle())
                );
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractTextByRegionAreaTest2() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, Map<String, String>> map = extractor.extractTextByRegionArea(
                        Collections.singletonMap("test", document.getCurrentPage().getPageSize().toRectangle()),
                        " "
                );
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractTextForTableTest1() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, Map<String, List<List<String>>>> map = extractor.extractTextForTable(
                        Collections.singletonMap("test", document.getCurrentPage().getPageSize().toRectangle())
                );
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractTextForTableTest2() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, Map<String, List<List<String>>>> map = extractor.extractTextForTable(
                        Collections.singletonMap("test", document.getCurrentPage().getPageSize().toRectangle()),
                        " "
                );
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractImageTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, List<BufferedImage>> map = extractor.extractImage();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractFormTextTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<String, String> map = extractor.extractFormText();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractFormImageTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<String, BufferedImage> map = extractor.extractFormImage();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractFormFieldTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<String, PDField> map = extractor.extractFormField();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractCommentTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, List<String>> map = extractor.extractComment();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractCommentByRegexTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, List<String>> map = extractor.extractCommentByRegex(null);
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }

    @Test
    public void extractBookmarkTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\extractor\\hello-world.pdf");
                    DocumentExtractor extractor = new DocumentExtractor(document);
            ) {
                Map<Integer, PDOutlineItem> map = extractor.extractBookmark();
                map.forEach((key, value) -> log.info("key: " + key + ", value: " + value));
            }
        });
    }
}
