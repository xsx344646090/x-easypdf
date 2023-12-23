package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
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
public class ImageTest extends BaseTest {

    @Test
    public void pngTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Image image = new Image(page);
            image.setImage(Paths.get("E:\\PDF\\image\\test.png").toFile());
            image.setWidth(100);
            image.setHeight(100);
            image.setAngle(45F);
            image.render();

            document.appendPage(page);

            document.save("E:\\PDF\\image\\pngTest.pdf");

            document.close();
        });
    }

    @Test
    public void jpegTest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageSize.A4);
        Image image = new Image(page);
        image.setImage(Paths.get("E:\\PDF\\image\\test.jpeg").toFile());
        image.render();
        document.save("E:\\PDF\\image\\jpegTest.pdf");
        document.close();
    }

    @Test
    public void svgTest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageSize.A4);
        Image image = new Image(page);
        image.setImage(new File("F:\\workspace\\my\\x-easypdf-3.0\\x-easypdf-fop\\src\\test\\resources\\org\\dromara\\pdf\\fop\\svg\\test.svg"));
        image.render();
        document.save("E:\\PDF\\image\\svgTest.pdf");
        document.close();
    }

    @Test
    public void testSize() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageSize.A4);
        Image image = new Image(page);
        image.setWidth(600);
        image.setHeight(600);
        image.setImage(Paths.get("E:\\PDF\\image\\test.jpeg").toFile());
        image.render();
        document.save("E:\\PDF\\image\\testSize.pdf");
        document.close();
    }

    @Test
    public void testMix() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageSize.A4);
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
        document.save("E:\\PDF\\image\\testMix.pdf");
        document.close();
    }
}
