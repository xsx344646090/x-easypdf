package org.dromara.pdf.pdfbox.convertor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.playwright.PlaywrightConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author xsx
 * @date 2025/6/18
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
public class HtmlConvertorTest extends BaseTest {

    /**
     * html转pdf测试
     */
    @Test
    public void toPdfForRemoteTest() {
        this.test(() -> {
            PlaywrightConvertor convertor = PdfHandler.getDocumentConvertor().getPlaywrightConvertor();
            convertor.setMargin(10F);
            convertor.setScale(0.85F);
            Document document = convertor.toPdf("https://www.oschina.net/");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\oschina.pdf");
        });
    }

    /**
     * html转pdf测试
     */
    @Test
    public void toPdfWithLocalTest() {
        this.test(() -> {
            PlaywrightConvertor convertor = PdfHandler.getDocumentConvertor().getPlaywrightConvertor();
            convertor.setMargin(10F);
            convertor.setScale(0.85F);
            Document document = convertor.toPdf("E:\\PDF\\pdfbox\\convertor\\html\\test1.html");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\test.pdf");
        });
    }

    /**
     * html转pdf测试
     */
    @Test
    public void toPdfWithImageTest() {
        this.test(() -> {
            PlaywrightConvertor convertor = PdfHandler.getDocumentConvertor().getPlaywrightConvertor();
            convertor.setMargin(10F);
            Document document = convertor.toPdfWithImage("https://www.oschina.net/");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\oschina-image.pdf");
        });
    }

    /**
     * html转image测试
     */
    @Test
    public void toImageTest() {
        this.test(() -> {
            PlaywrightConvertor convertor = PdfHandler.getDocumentConvertor().getPlaywrightConvertor();
            convertor.setMargin(10F);
            BufferedImage image = convertor.toImage("E:\\PDF\\pdfbox\\convertor\\html\\test1.html");
            ImageUtil.write(image, new File("E:\\PDF\\pdfbox\\convertor\\html\\test.png"));
        });
    }
}
