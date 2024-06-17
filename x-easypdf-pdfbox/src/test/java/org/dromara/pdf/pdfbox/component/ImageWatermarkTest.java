package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.ImageWatermark;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

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
public class ImageWatermarkTest extends BaseTest {

    /**
     * 测试文档水印
     */
    @Test
    public void documentTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.appendPage(new Page(document));
            document.appendPage(new Page(document));

            ImageWatermark watermark = new ImageWatermark(document);
            watermark.setImage(Paths.get("E:\\PDF\\pdfbox\\image\\test.jpg").toFile());
            watermark.setBeginX(30F);
            watermark.setBeginY(470F);
            watermark.setWidth(100);
            watermark.setHeight(100);
            watermark.setLines(2);
            watermark.setCountOfLine(4);
            watermark.setSpacingOfLine(50F);
            watermark.render(document);

            document.save("E:\\PDF\\pdfbox\\watermark\\image\\documentTest.pdf");
            document.close();
        });
    }

    /**
     * 测试页面水印
     */
    @Test
    public void pageTest() {
        this.test(()->{
            Document document = PdfHandler.getDocumentHandler().create();
            document.appendPage(new Page(document));
            document.appendPage(new Page(document));

            ImageWatermark watermark = new ImageWatermark(document);
            watermark.setImage(Paths.get("E:\\PDF\\pdfbox\\image\\test.png").toFile());
            watermark.setBeginX(30F);
            watermark.setBeginY(470F);
            watermark.setWidth(100);
            watermark.setHeight(100);
            watermark.setLines(2);
            watermark.setCountOfLine(4);
            watermark.setSpacingOfLine(50F);
            watermark.setAngle(45F);
            watermark.render(document.getCurrentPage());

            document.save("E:\\PDF\\pdfbox\\watermark\\image\\pageTest.pdf");
            document.close();
        });
    }
}
