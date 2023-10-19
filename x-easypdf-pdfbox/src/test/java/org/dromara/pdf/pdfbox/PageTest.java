package org.dromara.pdf.pdfbox;

import org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageRectangle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/7/17
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
public class PageTest {

    @Test
    public void testBackgroundColor() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setBackgroundColor(Color.CYAN);
        Page page = document.createPage(PageRectangle.A4);
        page.setBackgroundColor(Color.WHITE);
        document.saveAndClose("E:\\PDF\\page\\testPage.pdf");
    }

    @Test
    public void testResetRectangle() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\page\\testPage.pdf");
        Page page = document.getPage(0);
        PDPage target = page.getTarget();
        System.out.println("MediaBox = " + target.getMediaBox().getWidth() + ", " + target.getMediaBox().getHeight());
        System.out.println("CropBox = " + target.getCropBox().getWidth() + ", " + target.getCropBox().getHeight());
        System.out.println("ArtBox = " + target.getArtBox().getWidth() + ", " + target.getArtBox().getHeight());
        System.out.println("-------------------------------------------------");
        page.resetRectangle(PageRectangle.A2);
        System.out.println("MediaBox = " + target.getMediaBox().getWidth() + ", " + target.getMediaBox().getHeight());
        System.out.println("CropBox = " + target.getCropBox().getWidth() + ", " + target.getCropBox().getHeight());
        System.out.println("ArtBox = " + target.getArtBox().getWidth() + ", " + target.getArtBox().getHeight());
        document.saveAndClose("E:\\PDF\\page\\testResetRectangle.pdf");
    }
}
