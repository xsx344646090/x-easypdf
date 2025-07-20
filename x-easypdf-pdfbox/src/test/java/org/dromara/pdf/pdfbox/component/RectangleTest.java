package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.DiagonalLine;
import org.dromara.pdf.pdfbox.core.component.Rectangle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
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

    /**
     * 测试矩形
     */
    @Test
    public void rectangleTest() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();
                    document.setMargin(50F);

                    Page page = new Page(document);

                    Rectangle rectangle = new Rectangle(document.getCurrentPage());
                    rectangle.setBorderColor(new Color(0, 191, 255));
                    rectangle.setBorderLineWidth(1F);
                    rectangle.setBackgroundColor(Color.LIGHT_GRAY);
                    rectangle.setBorderLineCapStyle(LineCapStyle.ROUND);
                    rectangle.setBorderRadius(10F);
                    rectangle.setWidth(200F);
                    rectangle.setHeight(200F);
                    rectangle.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    rectangle.setVerticalAlignment(VerticalAlignment.CENTER);
                    rectangle.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\pdfbox\\rectangle\\rectangleTest.pdf");
                    document.close();
                }
        );
    }

    /**
     * 测试矩形
     */
    @Test
    public void rectangleTest2() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();
                    document.setMargin(50F);

                    Page page = new Page(document);

                    Rectangle rectangle = new Rectangle(document.getCurrentPage());
                    rectangle.setBeginX(100F);
                    rectangle.setBeginY(100F);
                    rectangle.setWidth(150F);
                    rectangle.setHeight(150F);
                    rectangle.setBorderColor(Color.BLACK);
                    rectangle.render();

                    DiagonalLine line = new DiagonalLine(page);
                    line.setBeginX(100F);
                    line.setBeginY(100F);
                    line.setEndX(100F+150F);
                    line.setEndY(100F+150F);
                    line.setAngle(90F);
                    line.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\pdfbox\\rectangle\\rectangleTest2.pdf");
                    document.close();
                }
        );
    }
}
