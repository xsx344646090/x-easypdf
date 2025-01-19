package org.dromara.pdf.pdfbox.ai;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.parser.ai.AIParseInfo;
import org.dromara.pdf.pdfbox.core.ext.parser.ai.OSChinaAIParser;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2024/12/18
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
public class OSChinaAITest extends BaseTest {
    
    /**
     * 页面文本测试
     */
    @Test
    public void parseTextWithPageTest() {
        this.test(()->{
            String ak = System.getenv("ak");
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            OSChinaAIParser parser = PdfHandler.getDocumentAIParser(document).getOSChinaAIParser(ak, sk, false);
            AIParseInfo info = parser.parseTextWithPage("一句话总结文本内容", 0, 0);
            log.info("返回内容：\n" + info.getResult());
            document.close();
        });
    }
    
    /**
     * 文档文本测试
     */
    @Test
    public void parseTextWithDocumentTest() {
        this.test(()->{
            String ak = System.getenv("ak");
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            OSChinaAIParser parser = PdfHandler.getDocumentAIParser(document).getOSChinaAIParser(ak, sk, true);
            AIParseInfo info = parser.parseTextWithDocument("提取表格内容，以json格式返回");
            log.info("返回内容：\n" + info.getResult());
            document.close();
        });
    }
}
