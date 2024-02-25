package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Barcode;
import org.dromara.pdf.pdfbox.core.enums.BarcodeType;
import org.dromara.pdf.pdfbox.core.enums.BorderStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2023/8/24
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
public class BarcodeTest extends BaseTest {

    @Test
    public void qrcodeTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Barcode barcode = new Barcode(document.getCurrentPage());
            barcode.setCodeType(BarcodeType.QR_CODE);
            barcode.setWidth(150);
            barcode.setHeight(150);
            barcode.setImageWidth(450);
            barcode.setImageHeight(450);
            // barcode.setMarginTop(100F);
            barcode.setContent("https://x-easypdf.cn");
            // barcode.setWords("文字");
            barcode.setWordsFontName("微软雅黑");
            barcode.setWordsSize(36);
            barcode.setIsBorder(true);
            barcode.setBorderStyle(BorderStyle.SOLID);
            barcode.setIsShowWords(true);
            barcode.setIsCache(Boolean.FALSE);
            barcode.setAngle(30F);
            barcode.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\barcode\\qrcodeTest.pdf");
            document.close();
        });
    }

    @Test
    public void barcodeTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Barcode barcode = new Barcode(document.getCurrentPage());
            barcode.setCodeType(BarcodeType.CODE_128);
            barcode.setWidth(250);
            barcode.setHeight(50);
            barcode.setImageWidth(500);
            barcode.setImageHeight(100);
            barcode.setContent("123456789");
            barcode.setWords("文字");
            barcode.setWordsSize(36);
            barcode.setWordsOffsetY(30);
            barcode.setIsBorder(true);
            barcode.setBorderStyle(BorderStyle.DOTTED);
            barcode.setIsShowWords(true);
            barcode.setIsCache(Boolean.FALSE);
            barcode.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\barcode\\barcodeTest.pdf");
            document.close();
        });
    }
}
