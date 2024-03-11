package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.component.PageHeader;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author xsx
 * @date 2023/8/24
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
public class PageHeaderTest extends BaseTest {

    /**
     * 测试文本页眉
     */
    @Test
    public void textareaTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);
            PageHeader pageHeader = new PageHeader(document.getCurrentPage());

            Textarea headerText = new Textarea(pageHeader.getPage());
            headerText.setText("页眉");

            pageHeader.setWidth(490F);
            pageHeader.setHeight(100F);
            pageHeader.setComponents(Collections.singletonList(headerText));
            pageHeader.setIsBorder(true);
            pageHeader.render();

            Textarea textarea1 = new Textarea(document.getCurrentPage());
            textarea1.setText("hello");
            textarea1.render();

            Textarea textarea2 = new Textarea(document.getCurrentPage());
            textarea2.setText("world1world2");
            textarea2.render();

            Textarea textarea3 = new Textarea(document.getCurrentPage());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 2300; i++) {
                builder.append("testtesttest123中文").append(i);
            }
            textarea3.setText(builder.toString());
            textarea3.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\pageHeader\\textareaTest.pdf");
            document.close();
        });
    }

    /**
     * 测试图片页眉
     */
    @Test
    public void imageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);
            PageHeader pageHeader = new PageHeader(document.getCurrentPage());

            Image image1 = new Image(pageHeader.getPage());
            image1.setImage(Paths.get("E:\\PDF\\pdfbox\\pageHeader\\test.svg").toFile());
            image1.setWidth(50);
            image1.setHeight(50);
            image1.setIsBorder(true);

            Image image2 = new Image(pageHeader.getPage());
            image2.setImage(Paths.get("E:\\PDF\\pdfbox\\pageHeader\\test.jpg").toFile());
            image2.setWidth(50);
            image2.setHeight(50);
            image2.setIsBorder(true);

            Image image3 = new Image(pageHeader.getPage());
            image3.setImage(Paths.get("E:\\PDF\\pdfbox\\pageHeader\\test.png").toFile());
            image3.setWidth(50);
            image3.setHeight(50);
            image3.setIsBorder(true);

            pageHeader.setWidth(100F);
            pageHeader.setHeight(100F);
            pageHeader.setComponents(Arrays.asList(image1, image2, image3));
            pageHeader.setIsBorder(true);
            pageHeader.render();

            Textarea textarea1 = new Textarea(document.getCurrentPage());
            textarea1.setText("hello");
            textarea1.render();

            Textarea textarea2 = new Textarea(document.getCurrentPage());
            textarea2.setText("world1world2");
            textarea2.render();

            Textarea textarea3 = new Textarea(document.getCurrentPage());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 200; i++) {
                builder.append("testtesttest123中文").append(i);
            }
            textarea3.setText(builder.toString());
            textarea3.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\pageHeader\\imageTest.pdf");
            document.close();
        });
    }

    /**
     * 测试文本+图片页眉
     */
    @Test
    public void textareaAndImageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);
            PageHeader pageHeader = new PageHeader(document.getCurrentPage());

            Textarea headerText1 = new Textarea(pageHeader.getPage());
            headerText1.setText("页眉1");
            headerText1.setFontSize(30F);

            Image image = new Image(pageHeader.getPage());
            image.setImage(Paths.get("E:\\PDF\\pdfbox\\pageHeader\\test.svg").toFile());
            image.setWidth(50);
            image.setHeight(50);
            image.setRelativeBeginY(-30F);

            Textarea headerText2 = new Textarea(pageHeader.getPage());
            headerText2.setText("页眉2");
            headerText2.setFontSize(30F);

            pageHeader.setWidth(490F);
            pageHeader.setHeight(100F);
            pageHeader.setComponents(Arrays.asList(headerText1, image, headerText2));
            pageHeader.setIsBorder(true);
            pageHeader.render();

            Textarea textarea1 = new Textarea(document.getCurrentPage());
            textarea1.setText("hello");
            textarea1.render();

            Textarea textarea2 = new Textarea(document.getCurrentPage());
            textarea2.setText("world1world2");
            textarea2.render();

            Textarea textarea3 = new Textarea(document.getCurrentPage());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 200; i++) {
                builder.append("testtesttest123中文").append(i);
            }
            textarea3.setText(builder.toString());
            textarea3.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\pageHeader\\textareaAndImageTest.pdf");
            document.close();
        });
    }
}
