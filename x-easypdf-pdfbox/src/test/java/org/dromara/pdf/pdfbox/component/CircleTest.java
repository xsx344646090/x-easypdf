package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Circle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
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
public class CircleTest extends BaseTest {

    /**
     * 测试圆形
     */
    @Test
    public void circleTest() {
        this.test(
                () -> {
                    Document document = PdfHandler.getDocumentHandler().create();
                    document.setMargin(50F);

                    Page page = new Page(document);

                    Circle circle = new Circle(page);
                    circle.setBorderColor(new Color(0, 191, 255));
                    circle.setBorderWidth(5F);
                    circle.setBackgroundColor(Color.LIGHT_GRAY);
                    circle.setRadius(100F);
                    circle.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    circle.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\pdfbox\\circle\\circleTest.pdf");
                    document.close();
                }
        );
    }
}
