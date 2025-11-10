package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.BookmarkProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.List;

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
public class BookmarkProcessorTest extends BaseTest {

    /**
     * 测试获取书签
     */
    @Test
    public void getItemsTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                List<PDOutlineItem> items = processor.getItems();
                items.forEach(System.out::println);
            }
        });
    }

    /**
     * 测试插入书签
     */
    @Test
    public void insertTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                PDOutlineItem outlineItem = new PDOutlineItem();
                outlineItem.setTitle("hello world");
                outlineItem.setDestination(document.getPage(0).getTarget());
                processor.insert(0, outlineItem);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\bookmark\\insertTest.pdf");
            }
        });
    }

    /**
     * 测试追加书签
     */
    @Test
    public void appendTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                PDOutlineItem outlineItem = new PDOutlineItem();
                outlineItem.setTitle("hello world");
                outlineItem.setDestination(document.getPage(0).getTarget());
                processor.append(outlineItem);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\bookmark\\appendTest.pdf");
            }
        });
    }

    /**
     * 测试替换书签
     */
    @Test
    public void setTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                PDOutlineItem outlineItem = new PDOutlineItem();
                outlineItem.setTitle("hello world");
                outlineItem.setDestination(document.getPage(0).getTarget());
                processor.set(0, outlineItem);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\bookmark\\setTest.pdf");
            }
        });
    }

    /**
     * 测试移除书签
     */
    @Test
    public void removeTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                processor.remove(0);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\bookmark\\removeTest.pdf");
            }
        });
    }
}
