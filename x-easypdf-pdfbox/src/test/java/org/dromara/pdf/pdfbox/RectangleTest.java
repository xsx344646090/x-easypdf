package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Rectangle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/11/24
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
public class RectangleTest extends BaseTest {

    @Test
    public void rectangleTest() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();
                    document.setMargin(50F);

                    Page page = document.createPage(PageSize.A4);

                    Rectangle rectangle = new Rectangle(page);
                    rectangle.setBorderColor(new Color(0, 191, 255));
                    rectangle.setBorderWidth(5F);
                    rectangle.setBackgroundColor(Color.LIGHT_GRAY);
                    rectangle.setWidth(200F);
                    rectangle.setHeight(200F);
                    rectangle.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\rectangle\\rectangleTest.pdf");
                    document.close();
                }
        );
    }

    @Test
    public void rectangleTest2() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();
                    document.setMargin(50F);

                    Page page = document.createPage(PageSize.A4);

                    Rectangle rectangle = new Rectangle(page);
                    rectangle.setBeginX(100F);
                    rectangle.setBeginY(100F);
                    rectangle.setWidth(150F);
                    rectangle.setHeight(50F);
                    rectangle.setAngle(45F);
                    rectangle.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\rectangle\\rectangleTest2.pdf");
                    document.close();
                }
        );
    }
}
