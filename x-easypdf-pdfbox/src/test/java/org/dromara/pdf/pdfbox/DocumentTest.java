package org.dromara.pdf.pdfbox;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageRectangle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2023/7/16
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
public class DocumentTest {


    @SneakyThrows
    @Test
    public void test() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setFontName("SimSun");
        PDDocument pdDocument = document.getTarget();
        PDPage page = new PDPage(PDRectangle.A4);
        pdDocument.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
        PDFont pdFont = document.getFont();
        contentStream.setFont(pdFont, 12f);
        contentStream.beginText();
        contentStream.newLineAtOffset(0, page.getMediaBox().getHeight() - 12);
        contentStream.showText("你好，世界！hello world");
        contentStream.endText();
        contentStream.close();
        // differentSettings(pdDocument, page, document.getFont());
        document.save("D:\\PDF\\test.pdf").close();

        // List<String> fontNames = PdfHandler.getFontHandler().getFontNames();
        // fontNames.forEach(System.out::println);
        // System.out.println("--------------------fontNames = " + fontNames.size());

        // FontMapperImpl.getInstance().getProvider();
        // FontMapperImpl instance = FontMapperImpl.getInstance();
        // Map<String, FontInfo> fontInfoByName = instance.getFontInfoByName();
        // fontInfoByName.keySet().forEach(System.out::println);
    }

    @Test
    public void insertPageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\test.pdf");
        Page page = document.createPage(PageRectangle.A4);
        document.insertPage(2, page);
        document.saveAndClose("E:\\PDF\\document\\insertPageTest.pdf");
    }

    @Test
    public void appendPageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\test.pdf");
        Page page = document.createPage(PageRectangle.A4);
        document.appendPage(page);
        document.saveAndClose("E:\\PDF\\document\\appendPageTest.pdf");
    }

    @Test
    public void setPageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\insertPageTest.pdf");
        Page page = document.createPage(PageRectangle.A4);
        document.setPage(1, page);
        document.saveAndClose("E:\\PDF\\document\\setPageTest.pdf");
    }

    @Test
    public void removePageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\insertPageTest.pdf");
        document.removePage(0, 2, 5);
        document.saveAndClose("E:\\PDF\\document\\removePageTest.pdf");
    }

    @Test
    public void resortPageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\insertPage.pdf");
        document.reorderPage(1, 0);
        document.saveAndClose("E:\\PDF\\document\\resortPageTest.pdf");
    }
}
