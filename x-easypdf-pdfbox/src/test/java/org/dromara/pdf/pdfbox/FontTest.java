package org.dromara.pdf.pdfbox;

import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.FontType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.fonts.FontInfo;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author xsx
 * @date 2023/7/17
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
public class FontTest extends BaseTest {

    @Test
    public void getFontNamesTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().getFontNames().forEach(System.out::println);
        });
    }

    @Test
    public void getFontInfosTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().getFontInfos().stream().map(FontInfo::getFontName).forEach(System.out::println);
        });
    }

    @Test
    public void getPDFontTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().create()) {
                String fontName = "SimSun";
                PDFont pdFont = PdfHandler.getFontHandler().getPDFont(document.getTarget(), fontName, true);
                Assert.assertEquals(fontName, pdFont.getName());
            }
        });
    }

    @Test
    public void getTrueTypeFontTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("F:\\pdf\\OTF\\SimplifiedChinese\\SourceHanSerifSC-Regular.otf"))) {
                PdfHandler.getFontHandler().addFont(inputStream, "SourceHanSerifSC", FontType.OTF);
                String fontName = "SourceHanSerifSC-Regular";
                TrueTypeFont trueTypeFont = PdfHandler.getFontHandler().getTrueTypeFont(fontName);
                Assert.assertEquals(fontName, trueTypeFont.getName());
            }
        });
    }

    @Test
    public void addFontTest1() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Paths.get("F:\\pdf\\OTF\\SimplifiedChinese\\SourceHanSerifSC-Regular.otf").toFile());
            PdfHandler.getFontHandler().getFontNames().forEach(System.out::println);
        });
    }

    @Test
    public void addFontTest2() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Arrays.asList(Paths.get("F:\\pdf\\OTF\\SimplifiedChinese\\SourceHanSerifSC-Regular.otf").toFile()));
            PdfHandler.getFontHandler().getFontNames().forEach(System.out::println);
        });
    }

    @Test
    public void addFontTest3() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("F:\\pdf\\OTF\\SimplifiedChinese\\SourceHanSerifSC-Regular.otf"))) {
                PdfHandler.getFontHandler().addFont(inputStream, "testFont", FontType.OTF);
                PdfHandler.getFontHandler().getFontNames().forEach(System.out::println);
            }
        });
    }

    @Test
    public void addToSubsetTest() {
        this.test(() -> {
            try (PDDocument doc = new PDDocument()) {
                PDFont font = PdfHandler.getFontHandler().getPDFont(doc, "微软雅黑", true);
                PdfHandler.getFontHandler().addToSubset(font, "测试");
            }
        });
    }
}
