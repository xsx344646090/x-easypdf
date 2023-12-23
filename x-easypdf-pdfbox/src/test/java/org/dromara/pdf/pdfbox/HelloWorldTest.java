package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2023/12/23
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
public class HelloWorldTest extends BaseTest {

    @Test
    public void test() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");

            Page page = document.createPage();

            Textarea textarea = new Textarea(page);
            textarea.setText("Hello World!");
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\document\\hello-world.pdf");
            document.close();
        });
    }
}
