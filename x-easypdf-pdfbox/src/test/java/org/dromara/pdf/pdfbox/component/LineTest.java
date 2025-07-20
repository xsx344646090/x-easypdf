package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Line;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2023/11/7
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
public class LineTest extends BaseTest {

    /**
     * 测试实线分割线
     */
    @Test
    public void solidTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();

            Page page = new Page(document);

            Line line = new Line(document.getCurrentPage());
            line.setLineStyle(LineStyle.SOLID);
            line.setMarginTop(100F);
            line.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\line\\solidTest.pdf");
            document.close();
        });
    }

    /**
     * 测试虚线分割线
     */
    @Test
    public void dottedTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();

            Page page = new Page(document);

            Line line = new Line(document.getCurrentPage());
            line.setLineStyle(LineStyle.DOTTED);
            line.setMarginTop(100F);
            line.setDottedSpacing(5F);
            line.setDottedLength(5F);
            line.setLineWidth(5F);
            line.setLineLength(200F);
            line.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\line\\dottedTest.pdf");
            document.close();
        });
    }
}
