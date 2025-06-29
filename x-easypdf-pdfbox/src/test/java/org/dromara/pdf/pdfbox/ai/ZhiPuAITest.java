package org.dromara.pdf.pdfbox.ai;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.parser.ai.AIParseInfo;
import org.dromara.pdf.pdfbox.core.ext.parser.ai.ZhiPuAIParser;
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
public class ZhiPuAITest extends BaseTest {

    /**
     * 页面图像测试
     */
    @Test
    public void parseImageWithPageTest() {
        this.test(()->{
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            ZhiPuAIParser parser = PdfHandler.getDocumentAIParser(document).getZhiPuAI(sk, false);
            AIParseInfo info = parser.parseImageWithPage("根据“这是一张XXX地点XXX的图片”的格式描述图片展示的内容，说出具体的城市，使用中文回答", 0, 1);
            log.info("返回内容：\n" + info.getResult());
            document.close();
        });
    }

    /**
     * 页面图像测试
     */
    @Test
    public void parsePageWithImageTest() {
        this.test(()->{
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            ZhiPuAIParser parser = PdfHandler.getDocumentAIParser(document).getZhiPuAI(sk, true);
            AIParseInfo info = parser.parsePageWithImage("提取表格内容，以json格式返回", 1);
            log.info("返回内容：\n" + info.getResult());
            document.close();
        });
    }

    /**
     * 页面文本测试
     */
    @Test
    public void parseTextWithPageTest() {
        this.test(()->{
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            ZhiPuAIParser parser = PdfHandler.getDocumentAIParser(document).getZhiPuAI(sk, false);
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
            String sk = System.getenv("sk");
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            ZhiPuAIParser parser = PdfHandler.getDocumentAIParser(document).getZhiPuAI(sk, true);
            AIParseInfo info = parser.parseTextWithDocument("提取表格内容，以json格式返回");
            log.info("返回内容：\n" + info.getResult());
            document.close();
        });
    }
}
