package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Circle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
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

                    // 创建圆形组件
                    Circle circle = new Circle(document.getCurrentPage());
// 设置边框颜色
                    circle.setBorderColor(new Color(0, 191, 255));
// 设置边框宽度
                    circle.setBorderLineWidth(1F);
// 设置背景颜色
                    circle.setBackgroundColor(Color.LIGHT_GRAY);
// 设置半径
                    circle.setRadius(50F);
// 设置水平居中
                    circle.setHorizontalAlignment(HorizontalAlignment.CENTER);
// 设置垂直居中
                    circle.setVerticalAlignment(VerticalAlignment.CENTER);
// 绘制
                    circle.render();

                    document.appendPage(page);
                    document.save("E:\\PDF\\pdfbox\\circle\\circleTest.pdf");
                    document.close();
                }
        );
    }
}
