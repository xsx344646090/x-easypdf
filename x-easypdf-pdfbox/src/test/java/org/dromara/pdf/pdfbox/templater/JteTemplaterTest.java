package org.dromara.pdf.pdfbox.templater;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.templater.JteTemplater;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsx
 * @date 2025/7/21
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
public class JteTemplaterTest extends BaseTest {

    /**
     * 获取html内容测试
     */
    @Test
    public void getHtmlContentTest() {
        this.test(() -> {
            Map<String, Object> data = new HashMap<>();
            data.put("title", "Hello World");
            JteTemplater templater = PdfHandler.getDocumentTemplater().getJteTemplater();
            templater.setTemplatePath("template/jte");
            templater.setTemplateName("template.html");
            templater.setTemplateData(data);
            String content = templater.getHtmlContent();
            System.out.println(content);
        });
    }

    /**
     * 转pdf测试
     */
    @Test
    public void transformTest() {
        this.test(() -> {
            Map<String, Object> data = new HashMap<>();
            data.put("title", "Hello World");
            JteTemplater templater = PdfHandler.getDocumentTemplater().getJteTemplater();
            templater.setTemplatePath("template/jte");
            templater.setTemplateName("template.html");
            templater.setTemplateData(data);
            templater.setMargin(10F);
            Document document = templater.transform();
            document.saveAndClose("E:\\PDF\\pdfbox\\template\\jte.pdf");
        });
    }
}
