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

    /**
     * 测试页面边距
     */
    @Test
    public void setMarginTest() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();

                    Page page = new Page(document);
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

    /**
     * 测试页面宽高
     */
    @Test
    public void getWidthAndHeightTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        Assert.assertEquals(PageSize.A4.getWidth(), page.getWidth());
                        Assert.assertEquals(PageSize.A4.getHeight(), page.getHeight());
                    }
                }
        );
    }

    /**
     * 测试排除页面边距后的宽高
     */
    @Test
    public void getWithoutMarginWidthAndHeightTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        page.setMargin(50F);
                        Assert.assertEquals((Object) (PageSize.A4.getWidth() - 100), page.getWithoutMarginWidth());
                        Assert.assertEquals((Object) (PageSize.A4.getHeight() - 100), page.getWithoutMarginHeight());
                    }
                }
        );
    }

    /**
     * 测试首个页面
     */
    @Test
    public void getFirstParentPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        Assert.assertNull(page.getParentPage());
                    }
                }
        );
    }

    /**
     * 测试最后一个页面
     */
    @Test
    public void getLastSubPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        Assert.assertNull(page.getLastSubPage());
                    }
                }
        );
    }

    /**
     * 测试最新页码
     */
    @Test
    public void getLastNoTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        Assert.assertEquals(1L, (long) page.getLastNo());
                    }
                }
        );
    }

    /**
     * 测试页面旋转
     */
    @Test
    public void rotationTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
                        page.rotation(RotationAngle.ROTATION_90);

                        document.appendPage(page);
                        document.save("E:\\PDF\\pdfbox\\page\\rotationTest.pdf");
                    }
                }
        );
    }

    /**
     * 测试页面缩放
     */
    @Test
    public void scaleTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\table\\tableTest.pdf").toFile())) {
                        for (Page page : document.getPages()) {
                            page.scale(PageSize.create(page.getWidth() * 0.5F, page.getHeight() * 0.5F));
                        }
                        
                        document.save("E:\\PDF\\pdfbox\\page\\scaleTest.pdf");
                    }
                }
        );
    }

    /**
     * 测试页面裁剪
     */
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

    /**
     * 测试页面重置尺寸
     */
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

    /**
     * 测试创建子页面
     */
    @Test
    public void createSubPageTest() {
        this.test(
                () -> {
                    try (Document document = PdfHandler.getDocumentHandler().create()) {
                        Page page = new Page(document);
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
