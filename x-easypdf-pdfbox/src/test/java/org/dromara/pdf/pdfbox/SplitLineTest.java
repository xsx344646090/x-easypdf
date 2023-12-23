package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.SplitLine;
import org.dromara.pdf.pdfbox.core.component.Textarea;
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
public class SplitLineTest {

    @Test
    public void solidTest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);

        Page page = document.createPage(PageSize.A4);

        SplitLine splitLine = new SplitLine(page);
        splitLine.setMarginTop(100F);
        splitLine.render();

        document.appendPage(page);
        document.save("E:\\PDF\\splitLine\\solidTest.pdf");
        document.close();
    }

    @Test
    public void dottedTest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);

        Page page = document.createPage(PageSize.A4);

        SplitLine splitLine = new SplitLine(page);
        splitLine.setMarginTop(50F);
        splitLine.setDottedLength(5F);
        splitLine.setDottedSpacing(5F);
        splitLine.setLineWidth(5F);
        splitLine.setLineLength(200F);
        splitLine.render();

        document.appendPage(page);
        document.save("E:\\PDF\\splitLine\\dottedTest.pdf");
        document.close();
    }

    @Test
    public void textTest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);

        Page page = document.createPage(PageSize.A4);

        Textarea textarea = new Textarea(page);
        textarea.setText("目录");
        textarea.setFontSize(20F);
        textarea.render();

        Textarea title = new Textarea(page);
        title.setText("第一章");
        title.setFontSize(12F);
        title.setIsWrap(true);
        title.setMarginTop(20F);
        title.render();

        SplitLine splitLine = new SplitLine(page);
        splitLine.setRelativeBeginY(-5F);
        splitLine.setDottedLength(2F);
        splitLine.setDottedSpacing(1F);
        splitLine.setMarginLeft(20F);
        splitLine.setMarginRight(20F);
        splitLine.setLineLength(400F);
        splitLine.render();

        Textarea pageNum = new Textarea(page);
        pageNum.setText("1");
        pageNum.setFontSize(12F);
        pageNum.render();

        document.appendPage(page);
        document.save("E:\\PDF\\splitLine\\textTest.pdf");
        document.close();
    }
}
