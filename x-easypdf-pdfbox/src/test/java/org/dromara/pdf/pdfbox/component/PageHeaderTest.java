package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageFooter;
import org.dromara.pdf.pdfbox.core.base.PageHeader;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.io.File;
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

            Page page = new Page(document);
            PageHeader pageHeader = new PageHeader(document.getCurrentPage());

            Textarea headerText = new Textarea(pageHeader.getPage());
            headerText.setText("页眉");

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

            Page page = new Page(document);
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

            Page page = new Page(document);
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
    
    /**
     * 测试页眉
     */
    @Test
    public void test() {
        PdfHandler.disableScanSystemFonts();
        PdfHandler.getFontHandler().addFont(new File("E:\\PDF\\pdfbox\\textarea\\NotoEmoji-VariableFont_wght.ttf"), "emoji");
        Document document = PdfHandler.getDocumentHandler().create();
        document.setFontStyle(FontStyle.NORMAL);
        document.setMarginTop(10F);
        document.setMarginBottom(10F);
        document.setMarginRight(20F);
        document.setMarginLeft(20F);
        document.setSpecialFontNames("emoji");
        
        Page page = new Page(document);
        
        // 页眉 -- start
        PageHeader pageHeader = new PageHeader(document.getCurrentPage());
        pageHeader.setHeight(16F);
        pageHeader.setIsBorderBottom(true);
        
        Textarea headerTextLeft = new Textarea(pageHeader.getPage());
        headerTextLeft.setText("测试小程序");
        headerTextLeft.setHorizontalAlignment(HorizontalAlignment.LEFT);
        
        Textarea headerTextRight = new Textarea(pageHeader.getPage());
        headerTextRight.setText("数据来源于微信小程序：测试小程序");
        headerTextRight.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        
        pageHeader.setComponents(headerTextLeft, headerTextRight);
        pageHeader.render();
        // 页眉 -- end
        
        // 页脚 -- start
        PageFooter pageFooter = new PageFooter(document.getCurrentPage());
        pageFooter.setHeight(12F);
        pageFooter.setIsBorder(false);
        
        Textarea footerText = new Textarea(pageFooter.getPage());
        footerText.setText("第" + footerText.getPlaceholder() + "页");
        footerText.setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        pageFooter.setComponents(footerText);
        pageFooter.render();
        // 页脚 -- end
        
        document.appendPage(page);
        document.saveAndClose(new File("E:\\PDF\\pdfbox\\document\\test.pdf"));
    }
}
