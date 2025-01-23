package org.dromara.pdf.pdfbox.other;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xsx
 * @date 2025/1/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
@Controller
public class HttpTest extends BaseTest {

    @SneakyThrows
    @GetMapping(value = "/test")
    public void test(HttpServletResponse response) {
        // 设置请求头
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=test.pdf");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        try (Document document = PdfHandler.getDocumentHandler().create()) {
            Page page = new Page(document);
            Textarea textarea = new Textarea(page);
            textarea.setText("测试中文");
            textarea.render();
            document.appendPage(page);
            document.save(response.getOutputStream());
        }
    }
}
