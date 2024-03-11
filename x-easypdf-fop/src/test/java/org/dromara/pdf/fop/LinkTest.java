package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/11/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class LinkTest extends BaseTest {

    @Test
    public void textLinkTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\link\\textLinkTest.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().addBodyComponent(
                            TemplateHandler.Link.build().setText(
                                    TemplateHandler.Text.build()
                                            .setText("https://x-easypdf.cn")
                                            .setUnderLineColor("blue")
                                            .enableUnderLine()
                                            .setHorizontalStyle("center")
                            ).setExternalDestination("https://x-easypdf.cn")
                    )
            );
            // 转换pdf
            document.transform(outputPath);
        });
    }

    @Test
    public void textExtendLinkTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\link\\textExtendLinkTest.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().setId("test").setFontFamily("微软雅黑").addBodyComponent(
                            TemplateHandler.BlockContainer.build().setHorizontalStyle("center").addComponent(
                                    TemplateHandler.Text.build().setText("当前页码："),
                                    TemplateHandler.CurrentPageNumber.build(),
                                    TemplateHandler.Text.build().setText("，总页码："),
                                    TemplateHandler.TotalPageNumber.build().setPageId("test"),
                                    TemplateHandler.Text.build().setText("，x-easypdf官网").setLinkExternalDestination("https://x-easypdf.cn").enableLink()
                            )
                    )
            );
            // 转换pdf
            document.transform(outputPath);
        });
    }
}
