package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

/**
 * @author xsx
 * @date 2023/8/24
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
public class ImageTest {

    @Test
    public void testPng() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.getCurrentPage();
        Image image = new Image(page);
        image.setImage(Paths.get("E:\\PDF\\image\\test.png").toFile());
        image.render();
        document.saveAndClose("E:\\PDF\\image\\testPng.pdf");
    }

    @Test
    public void testJpeg() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.getCurrentPage();
        Image image = new Image(page);
        image.setImage(Paths.get("E:\\PDF\\image\\test.jpeg").toFile());
        image.render();
        document.saveAndClose("E:\\PDF\\image\\testJpeg.pdf");
    }

    @Test
    public void testSvg() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.getCurrentPage();
        Image image = new Image(page);
        image.setIsSvg(true);
        image.setImage(new File("F:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\svg\\test.svg"));
        image.render();
        document.saveAndClose("E:\\PDF\\image\\testSvg.pdf");
    }

    @Test
    public void testSize() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.getCurrentPage();
        Image image = new Image(page);
        image.setWidth(600);
        image.setHeight(600);
        image.setImage(Paths.get("E:\\PDF\\image\\test.jpeg").toFile());
        image.render();
        document.saveAndClose("E:\\PDF\\image\\testSize.pdf");
    }

    @Test
    public void testMix() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.getCurrentPage();
        Textarea leftTextarea = new Textarea(page);
        leftTextarea.setText("左侧文本");
        leftTextarea.setFontColor(Color.BLUE);
        leftTextarea.render();
        Image image = new Image(page);
        image.setWidth(300);
        image.setHeight(300);
        image.setRelativeBeginY(12F);
        image.setImage(Paths.get("E:\\PDF\\image\\test.jpeg").toFile());
        image.render();
        Textarea rightTextarea = new Textarea(page);
        rightTextarea.setText("右侧文本");
        rightTextarea.setFontColor(Color.GREEN);
        rightTextarea.render();
        document.saveAndClose("E:\\PDF\\image\\testMix.pdf");
    }
}
