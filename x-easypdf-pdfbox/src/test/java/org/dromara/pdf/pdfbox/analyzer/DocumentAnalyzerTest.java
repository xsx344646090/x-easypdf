package org.dromara.pdf.pdfbox.analyzer;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.info.*;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.Set;

/**
 * @author xsx
 * @date 2023/10/19
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
public class DocumentAnalyzerTest extends BaseTest {

    /**
     * 测试文本
     */
    @Test
    public void textTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
                    DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
            ) {
                Set<TextInfo> infoSet = analyzer.analyzeText();
                infoSet.forEach(System.out::println);
            }
        });
    }

    /**
     * 测试字符数
     */
    @Test
    public void characterTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
            ) {
                DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
                int characterCount = analyzer.getCharacterCount(0);
                log.info("Character count: " + characterCount);
            }
        });
    }

    /**
     * 测试评论
     */
    @Test
    public void commentTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
                    DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
            ) {
                Set<CommentInfo> infoSet = analyzer.analyzeComment();
                infoSet.forEach(System.out::println);
            }
        });
    }

    /**
     * 测试图片
     */
    @Test
    public void imageTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
                    DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
            ) {
                Set<ImageInfo> infoSet = analyzer.analyzeImage();
                infoSet.forEach(log::info);
            }
        });
    }

    /**
     * 测试书签
     */
    @Test
    public void bookmarkTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
                    DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
            ) {
                Set<BookmarkInfo> infoSet = analyzer.analyzeBookmark();
                infoSet.forEach(log::info);
            }
        });
    }

    /**
     * 测试表单
     */
    @Test
    public void formTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\analyzer\\hello-world.pdf");
                    DocumentAnalyzer analyzer = new DocumentAnalyzer(document);
            ) {
                Set<FormFieldInfo> infoSet = analyzer.analyzeForm();
                infoSet.forEach(System.out::println);
            }
        });
    }
}
