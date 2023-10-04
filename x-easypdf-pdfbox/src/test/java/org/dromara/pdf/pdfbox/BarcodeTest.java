package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageRectangle;
import org.dromara.pdf.pdfbox.core.component.Barcode;
import org.dromara.pdf.pdfbox.enums.BarcodeType;
import org.dromara.pdf.pdfbox.enums.BorderStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2023/8/24
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
public class BarcodeTest {

    @Test
    public void testQrcode() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageRectangle.A4);
        Barcode barcode = new Barcode(page);
        barcode.setCodeType(BarcodeType.QR_CODE);
        barcode.setWidth(150);
        barcode.setHeight(150);
        barcode.setContent("https://x-easypdf.cn");
        // barcode.setWords("文字");
        barcode.setWordsFontName("微软雅黑");
        barcode.setIsBorder(true);
        barcode.setBorderStyle(BorderStyle.DOTTED);
        barcode.setIsShowWords(true);
        barcode.setIsCache(Boolean.TRUE);
        barcode.render();
        document.appendPage(page);
        document.saveAndClose("E:\\PDF\\barcode\\testQrcode.pdf");
    }
}
