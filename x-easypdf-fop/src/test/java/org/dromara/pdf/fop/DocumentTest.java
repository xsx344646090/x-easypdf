package org.dromara.pdf.fop;

import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class DocumentTest {

    @Test
    public void helloWorld() {
        TemplateHandler.Document.build().addPage(TemplateHandler.Page.build().addBodyComponent(TemplateHandler.Text.build().setText("hello world").setHorizontalStyle("center"))).transform("D:\\PDF\\fo\\document.pdf");
    }

    @Test
    public void testXmlContent() {
        System.out.println(TemplateHandler.Document.build().addPage(TemplateHandler.Page.build()).getContent());
    }


    @Test
    public void testBlankPage() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-document.pdf";
        // 转换pdf
        TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build()
                        .setBodyBackgroundImage("url('/E:\\pdf\\test\\fo\\a-test.png')")
                        .setBodyBackgroundImageWidth("100pt")
                        .setBodyBackgroundImageHeight("100pt")
                        .setFooterHeight("20pt")
                        .setFooterBackgroundImage("url('/E:\\pdf\\test\\fo\\test.png')")
                        .addFooterComponent(
                                TemplateHandler.Text.build().setText("111")
                        ),
                TemplateHandler.Page.build()
        ).transform(outputPath);
    }

    @Test
    public void testChinese() {
        TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Text.build().setText("你好贵阳")
                                .setHorizontalStyle("center")
                                .setFontFamily("微软雅黑")
                )
        ).transform("E:\\pdf\\test\\fo\\document.pdf");
    }

    @Test
    public void testTemp() {
        TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Table.build().setFontFamily("微软雅黑").setBody(
                                TemplateHandler.Table.Body.build()
                                        .addRow(
                                                TemplateHandler.Table.Row.build().addCell(
                                                        TemplateHandler.Table.Cell.build().addComponent(
                                                                TemplateHandler.Text.build().setText("姓名：张三")
                                                                // XEasyPdfTemplateHandler.Image.build()
                                                                //         .setPath("/".concat("")).setWidth("100px").setHeight("100px")
                                                        ).setBorder("1").setBorderStyle("solid").setRowSpan(2),
                                                        TemplateHandler.Table.Cell.build().addComponent(
                                                                TemplateHandler.Text.build().setText("姓名：张三").setHorizontalStyle("left")
                                                        ).setBorder("1 solid")
                                                )
                                        )
                                        .addRow(
                                                TemplateHandler.Table.Row.build().addCell(
                                                        TemplateHandler.Table.Cell.build().addComponent(
                                                                TemplateHandler.Text.build().setText("电话：15123456789").setHorizontalStyle("left")
                                                        ).setBorder("1 solid")
                                                )
                                        )
                        )
                )
        ).transform("E:\\pdf\\test\\fo\\document.pdf");
    }
}
