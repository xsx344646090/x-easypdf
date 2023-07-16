package org.dromara.pdf.pdfbox;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.List;

/**
 * @author xsx
 * @date 2023/7/16
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
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
        Document document = PdfHandler.getDocumentHandler().build();
        document.setFontName("SimSun");
        PDDocument pdDocument = document.getPDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        pdDocument.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
        PDFont pdFont = document.getPDFont();
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
    public void fontTest() {
        List<String> fontNames = PdfHandler.getFontHandler().getFontNames();
        fontNames.forEach(System.out::println);
        System.out.println("--------------------fontNames = " + fontNames.size());
    }
}
