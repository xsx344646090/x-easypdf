package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/7/17
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
public class PageTest {

    @Test
    public void testBackgroundColor() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setBackgroundColor(Color.CYAN);
        Page page = PdfHandler.getPageHandler().build(document);
        page = PdfHandler.getPageHandler().build(document);
        page.setBackgroundColor(Color.WHITE);
        document.saveAndClose("D:\\PDF\\page\\testPage.pdf");
    }

}
