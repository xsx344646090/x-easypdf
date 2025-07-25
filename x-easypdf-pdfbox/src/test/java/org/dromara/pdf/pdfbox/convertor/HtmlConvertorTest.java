package org.dromara.pdf.pdfbox.convertor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.html.HtmlConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author xsx
 * @date 2025/6/18
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
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
    public void toPdfWithLocalTest() {
        HtmlConvertor convertor = PdfHandler.getDocumentConvertor().getHtmlConvertor();
        convertor.setMargin(10F);
        List<CompletableFuture<?>> list = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            list.add(CompletableFuture.runAsync(() -> {
                this.test(() -> {
                    Document document = convertor.toPdf("E:\\PDF\\pdfbox\\convertor\\html\\test1.html");
                    document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\test" + finalI + ".pdf");
                });
            }));
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
    }

    /**
     * html转pdf测试
     */
    @Test
    public void toPdfWithImageTest() {
        this.test(() -> {
            HtmlConvertor convertor = PdfHandler.getDocumentConvertor().getHtmlConvertor();
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
            HtmlConvertor convertor = PdfHandler.getDocumentConvertor().getHtmlConvertor();
            convertor.setMargin(10F);
            BufferedImage image = convertor.toImage("E:\\PDF\\pdfbox\\convertor\\html\\test1.html");
            ImageUtil.write(image, new File("E:\\PDF\\pdfbox\\convertor\\html\\test.png"));
        });
    }
}
