package org.dromara.pdf.pdfbox.other;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Line;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;

/**
 * @author xsx
 * @date 2024/7/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class CatalogTest extends BaseTest {

    /**
     * 测试目录页
     */
    @Test
    public void catalogTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = new Page(document);

            Textarea textarea = new Textarea(page);
            textarea.setText("目录");
            textarea.setFontSize(20F);
            textarea.render();

            for (int i = 1; i <= 15; i++) {
                Textarea title = new Textarea(page);
                title.setText(String.format("第 %s 章", i));
                title.setFontSize(12F);
                title.setIsWrap(true);
                title.setMarginTop(20F);
                title.render();

                Line line = new Line(page);
                line.setLineStyle(LineStyle.DOTTED);
                line.setLineWidth(1F);
                line.setDottedSpacing(3F);
                line.setMarginLeft(20F);
                line.setMarginRight(20F);
                line.setRelativeBeginY(-4F);
                if (i > 9) {
                    line.setLineLength(343F);
                } else {
                    line.setLineLength(350F);
                }
                line.render();

                Textarea pageNum = new Textarea(page);
                pageNum.setText(String.format("%d", i * 2));
                pageNum.setFontSize(12F);
                pageNum.render();
            }

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\splitLine\\textTest.pdf");
            document.close();
        });
    }
}
