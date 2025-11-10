package org.dromara.pdf.pdfbox.base;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.FontType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.shade.org.apache.fontbox.ttf.TrueTypeFont;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocument;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.FontInfo;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

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

    /**
     * 测试获取字体名称
     */
    @Test
    public void getFontNamesTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().getFontNames().forEach(log::info);
        });
    }

    /**
     * 测试获取字体信息
     */
    @Test
    public void getFontInfosTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().getFontInfos().stream().map(FontInfo::getFontName).forEach(log::info);
        });
    }

    /**
     * 测试获取pdfbox字体
     */
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

    /**
     * 测试获取trueType字体
     */
    @Test
    public void getTrueTypeFontTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\font\\SourceHanSerifSC-Regular.otf"))) {
                PdfHandler.getFontHandler().addFont(inputStream, "SourceHanSerifSC", FontType.OTF);
                String fontName = "SourceHanSerifSC-Regular";
                TrueTypeFont trueTypeFont = PdfHandler.getFontHandler().getTrueTypeFont(fontName);
                Assert.assertEquals(fontName, trueTypeFont.getName());
            }
        });
    }

    /**
     * 测试添加字体
     */
    @Test
    public void addFontTest1() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Paths.get("E:\\PDF\\pdfbox\\font\\SourceHanSerifSC-Regular.otf").toFile());
            PdfHandler.getFontHandler().getFontNames().forEach(log::info);
        });
    }

    /**
     * 测试添加字体
     */
    @Test
    public void addFontTest2() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Collections.singletonList(Paths.get("E:\\PDF\\pdfbox\\font\\SourceHanSerifSC-Regular.otf").toFile()));
            PdfHandler.getFontHandler().getFontNames().forEach(log::info);
        });
    }

    /**
     * 测试添加字体
     */
    @Test
    public void addFontTest3() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\font\\SourceHanSerifSC-Regular.otf"))) {
                PdfHandler.getFontHandler().addFont(inputStream, "testFont", FontType.OTF);
                PdfHandler.getFontHandler().getFontNames().forEach(log::info);
            }
        });
    }

    /**
     * 测试嵌入字体
     */
    @Test
    public void addToSubsetTest() {
        this.test(() -> {
            try (PDDocument doc = new PDDocument()) {
                PDFont font = PdfHandler.getFontHandler().getPDFont(doc, "微软雅黑", true);
                PdfHandler.getFontHandler().addToSubset(doc, font, "测试");
            }
        });
    }
}
