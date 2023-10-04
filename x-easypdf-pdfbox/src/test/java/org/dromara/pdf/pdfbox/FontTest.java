package org.dromara.pdf.pdfbox;

import lombok.SneakyThrows;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.enums.FontType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.fonts.FontInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
public class FontTest {
    @Test
    public void testAddFont() {
        PdfHandler.getFontHandler().addFont(Paths.get("D:\\PDF\\text\\SourceHanSerifSC-Regular.otf").toFile());
        PdfHandler.getFontHandler().getFontNames().forEach(System.out::println);
    }

    @Test
    public void testGetTrueTypeFont() {
        try (InputStream inputStream = Files.newInputStream(Paths.get("D:\\PDF\\text\\SourceHanSerifSC-Regular.otf"))) {
            PdfHandler.getFontHandler().addFont(inputStream, "SourceHanSerifSC", FontType.OTF);
            TrueTypeFont trueTypeFont = PdfHandler.getFontHandler().getTrueTypeFont("SourceHanSerifSC-Regular");
            System.out.println("name = " + trueTypeFont.getName());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPDFont() {
        try (Document document = PdfHandler.getDocumentHandler().create()) {
            PDFont pdFont = PdfHandler.getFontHandler().getPDFont(document.getTarget(), "SimSun", true);
            System.out.println("name = " + pdFont.getName());
        }
    }

    @SneakyThrows
    @Test
    public void testFont() {
        List<? extends FontInfo> fontInfos = PdfHandler.getFontHandler().getFontInfos();
        for (FontInfo fontInfo : fontInfos) {
            FontBoxFont font = fontInfo.getFont();
            System.out.println("font.getName() = " + font.getName());
        }
    }
}
