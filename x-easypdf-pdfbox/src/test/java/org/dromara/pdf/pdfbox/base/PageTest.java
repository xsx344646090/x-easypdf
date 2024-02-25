package org.dromara.pdf.pdfbox.base;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.RotationAngle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.nio.file.Paths;

/**
 * @author xsx
 * @date 2023/7/17
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
public class PageTest extends BaseTest {

    @Test
    public void setMarginTest() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();

                    Page page = document.createPage(PageSize.A4);
                    page.setMargin(50F);
                    page.setBackgroundColor(Color.CYAN);
                    page.setFontName("仿宋");

                    Textarea textarea = new Textarea(page);
                    textarea.setText("测试测试测试测试测试测试");
                    textarea.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\pdfbox\\page\\setMarginTest.pdf");
                    document.close();
                }
        );
    }

    @Test
    public void getWidthAndHeightTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        Assert.assertEquals(PageSize.A4.getWidth(), page.getWidth());
                        Assert.assertEquals(PageSize.A4.getHeight(), page.getHeight());
                    }
                }
        );
    }

    @Test
    public void getWithoutMarginWidthAndHeightTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        page.setMargin(50F);
                        Assert.assertEquals((Object) (PageSize.A4.getWidth() - 100), page.getWithoutMarginWidth());
                        Assert.assertEquals((Object) (PageSize.A4.getHeight() - 100), page.getWithoutMarginHeight());
                    }
                }
        );
    }

    @Test
    public void getFirstParentPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        Assert.assertNull(page.getParentPage());
                    }
                }
        );
    }

    @Test
    public void getLastSubPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        Assert.assertNull(page.getLastSubPage());
                    }
                }
        );
    }

    @Test
    public void getLastIndexTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        Assert.assertEquals(1L, (long) page.getLastIndex());
                    }
                }
        );
    }

    @Test
    public void rotationTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage(PageSize.A4);
                        page.rotation(RotationAngle.ROTATION_90);

                        document.appendPage(page);
                        document.save("E:\\PDF\\pdfbox\\page\\rotationTest.pdf");
                    }
                }
        );
    }

    @Test
    public void scaleTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\page\\rotationTest.pdf").toFile())) {
                        Page page = document.getPage(0);
                        page.scale(PageSize.A5);

                        document.save("E:\\PDF\\pdfbox\\page\\scaleTest.pdf");
                    }
                }
        );
    }

    @Test
    public void cropTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\page\\scaleTest.pdf").toFile())) {
                        Page page = document.getPage(0);
                        page.crop(PageSize.create(10F, 200F, 10F, 200F));

                        document.save("E:\\PDF\\pdfbox\\page\\cropTest.pdf");
                    }
                }
        );
    }

    @Test
    public void resetRectangleTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\page\\cropTest.pdf").toFile())) {
                        Page page = document.getPage(0);
                        page.resetRectangle();

                        document.save("E:\\PDF\\pdfbox\\page\\resetRectangleTest.pdf");
                    }
                }
        );
    }

    @Test
    public void createSubPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = document.createPage();
                        page.setFontName("微软雅黑");
                        page.createSubPage();
                        Page subPage = page.getSubPage();
                        Assert.assertNotNull(subPage);
                        Assert.assertEquals(subPage.getFontName(), page.getFontName());
                    }
                }
        );
    }
}
