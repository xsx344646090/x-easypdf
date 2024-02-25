package org.dromara.pdf.fop;

import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class DocumentTest extends BaseTest {

    @Test
    public void helloWorld() {
        this.test(() -> {
            TemplateHandler.Document
                    .build()
                    .addPage(
                            TemplateHandler.Page
                                    .build()
                                    .addBodyComponent(
                                            TemplateHandler.Text
                                                    .build()
                                                    .setText("hello world")
                                                    .setHorizontalStyle("center")
                                    )
                    ).transform("E:\\PDF\\fop\\document.pdf");
        });
    }

    @Test
    public void xmlContentTest() {
        this.test(() -> {
            System.out.println(
                    TemplateHandler.Document
                            .build()
                            .addPage(TemplateHandler.Page.build())
                            .getContent()
            );
        });
    }

    @Test
    public void blankPageTest() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\template-document.pdf";
            // 转换pdf
            TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                    TemplateHandler.Page.build()
                            .setBodyBackgroundImage("url('/E:\\PDF\\fop\\watermark.png')")
                            .setBodyBackgroundImageWidth("100pt")
                            .setBodyBackgroundImageHeight("100pt")
                            .setFooterHeight("20pt")
                            .setFooterBackgroundImage("url('/E:\\PDF\\fop\\watermark.png')")
                            .addFooterComponent(
                                    TemplateHandler.Text.build().setText("111")
                            ),
                    TemplateHandler.Page.build()
            ).transform(outputPath);
        });
    }

    @Test
    public void chineseTest() {
        this.test(() -> {
            TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().addBodyComponent(
                            TemplateHandler.Text.build().setText("你好贵阳")
                                    .setHorizontalStyle("center")
                                    .setFontFamily("微软雅黑")
                    )
            ).transform("E:\\PDF\\fop\\chineseTest.pdf");
        });
    }
}
