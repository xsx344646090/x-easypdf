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
import org.dromara.pdf.pdfbox.core.component.Container;
import org.dromara.pdf.pdfbox.core.component.PageFooter;
import org.dromara.pdf.pdfbox.core.component.PageHeader;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
        document.saveAndClose("D:\\PDF\\test.pdf");

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

    @Test
    public void restructurePageTest() {
        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\document\\insertPage.pdf");
        document.restructurePage(1, 0, 1, 0, 1);
        document.saveAndClose("E:\\PDF\\document\\restructurePageTest.pdf");
    }

    @Test
    public void testTotalPageNumber() {
        Document document = this.create(null);
        int totalPageNumber = document.getTotalPageNumber();
        document.close();
        document = this.create(totalPageNumber);
        document.saveAndClose("E:\\PDF\\document\\testTotalPageNumber.pdf");
    }

    private Document create(Integer totalPage) {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setTotalPageNumber(totalPage);

        Page page = document.createPage(PageRectangle.A4);
        page.setBorderColor(Color.LIGHT_GRAY);

        PageHeader pageHeader = new PageHeader(document.getCurrentPage());
        Textarea headerText = new Textarea(pageHeader.getPage());
        headerText.setText("页眉");
        pageHeader.setWidth(490F);
        pageHeader.setHeight(100F);
        pageHeader.setComponents(Collections.singletonList(headerText));
        pageHeader.setIsBorder(true);
        pageHeader.render();

        PageFooter pageFooter = new PageFooter(document.getCurrentPage());
        Textarea footerText = new Textarea(pageHeader.getPage());
        footerText.setText("页脚");
        pageFooter.setWidth(490F);
        pageFooter.setHeight(350F);
        pageFooter.setComponents(Collections.singletonList(footerText));
        pageFooter.setIsBorder(true);
        pageFooter.render();

        Container container = new Container(document.getCurrentPage());
        container.setWidth(100F);
        container.setHeight(100F);
        container.setIsBorder(true);
        Textarea textarea1 = new Textarea(container.getPage());
        textarea1.setText("hello");
        Textarea textarea2 = new Textarea(container.getPage());
        textarea2.setText("world1world2");
        Textarea textarea3 = new Textarea(container.getPage());
        textarea3.setText("总页数" + Optional.ofNullable(totalPage).orElse(0));
        container.setComponents(Arrays.asList(textarea1, textarea2, textarea3));
        container.render();
        container.render();
        container.render();
        container.render();
        container.render();
        container.render();
        container.setIsWrap(true);
        container.render();
        document.appendPage(page);
        return document;
    }
}
