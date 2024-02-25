package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/11/11
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
public class WatermarkTest extends BaseTest {

    @Test
    public void watermarkTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\watermark\\watermarkTest.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().setStartWidth("21cm").setStartWatermark(
                            TemplateHandler.Watermark.build().setId("test").setText("test").setWidth("100pt").setHeight("50pt")
                    ).addBodyComponent(
                            TemplateHandler.TextExtend.build().setFontFamily("微软雅黑").setFontSize("30pt").addText(
                                    TemplateHandler.Text.build().setText("hello"),
                                    TemplateHandler.Text.build().setText("上标").setFontSize("12pt").setVerticalStyle("top"),
                                    TemplateHandler.Text.build().setText(", world")
                            )
                    )
            );
            TemplateHandler.Template.build().setDataSource(
                    TemplateHandler.DataSource.Document.build().setDocument(document)
            ).transform(outputPath);
        });
    }
}
