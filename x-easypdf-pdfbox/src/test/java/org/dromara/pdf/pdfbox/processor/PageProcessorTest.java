package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.enums.PageJoinType;
import org.dromara.pdf.pdfbox.core.enums.RotationAngle;
import org.dromara.pdf.pdfbox.core.ext.processor.PageProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.List;

/**
 * @author xsx
 * @date 2024/2/26
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
public class PageProcessorTest extends BaseTest {

    /**
     * 测试获取页面
     */
    @Test
    public void getPagesTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                List<Page> pages = processor.getPages();
                pages.forEach(System.out::println);
            }
        });
    }

    /**
     * 测试插入页面
     */
    @Test
    public void insertTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                Page page = new Page(document);
                processor.insert(0, page);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\page\\insertTest.pdf");
            }
        });
    }

    /**
     * 测试追加页面
     */
    @Test
    public void appendTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                Page page = new Page(document);
                processor.append(page);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\page\\appendTest.pdf");
            }
        });
    }

    /**
     * 测试替换页面
     */
    @Test
    public void setTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                Page page = new Page(document);
                processor.set(0, page);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\page\\setTest.pdf");
            }
        });
    }

    /**
     * 测试移除页面
     */
    @Test
    public void removeTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.remove(0);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\page\\removeTest.pdf");
            }
        });
    }

    /**
     * 测试重排序页面
     */
    @Test
    public void resortTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.resort(1, 0);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\resortTest.pdf");
            }
        });
    }

    /**
     * 测试重组页面
     */
    @Test
    public void restructureTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.restructure(1, 0, 1);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\restructureTest.pdf");
            }
        });
    }

    /**
     * 测试缩放页面
     */
    @Test
    public void scaleTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.scale(PageSize.A5, 0);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\scaleTest.pdf");
            }
        });
    }

    /**
     * 测试拼接页面
     */
    @Test
    public void pageJoinTest1() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                List<Page> pages = processor.getPages();
                processor.join(PageJoinType.VERTICAL, new Page(document, PageSize.create(PageSize.A4.getWidth(), PageSize.A4.getHeight()*2)), pages.get(0), pages.get(1));
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\pageJoinTest.pdf");
            }
        });
    }

    /**
     * 测试拼接页面
     */
    @Test
    public void pageJoinTest2() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().create();
                    Document document1 = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf");
                    Document document2 = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf");
            ) {
                Page page = new Page(document, PageSize.A4);
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                List<Page> pages1 = document1.getPages();
                List<Page> pages2 = document2.getPages();
                float x = 0F;
                float y = page.getHeight();
                for (int i = 0; i < 1; i++) {
                    Page page1 = pages1.get(i);
                    page1.scale(0.5F);

                    Page page2 = pages2.get(i);
                    page2.scale(0.5F);

                    y = y - Math.max(page1.getHeight(), page2.getHeight());
                    processor.join(PageJoinType.HORIZONTAL, x, y, page, page1, page2);
                }

                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\pageJoinTest.pdf");
            }
        });
    }

    /**
     * 测试旋转页面
     */
    @Test
    public void rotationTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.rotation(RotationAngle.ROTATION_90, 0);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\rotationTest.pdf");
            }
        });
    }

    /**
     * 测试裁剪页面
     */
    @Test
    public void cropTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.crop(PageSize.create(100F, 500F, 100F, 800F), 0);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\cropTest.pdf");
            }
        });
    }

    /**
     * 测试重置尺寸
     */
    @Test
    public void resetSizeTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\page\\cropTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.resetSize();
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\page\\resetSizeTest.pdf");
            }
        });
    }
}
