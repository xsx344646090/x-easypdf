package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/11/11
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfTemplateWatermarkTest {

    @Test
    public void test() {
        // 定义输出路径
        String pdfPath = "E:\\pdf\\test\\fo\\template-xsl-fo.pdf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-xsl-fo.fo";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().addPage(
                XEasyPdfTemplateHandler.Page.build().setStartWidth("21cm").setStartWatermark(
                        XEasyPdfTemplateHandler.Watermark.build().setId("test").setText("test").setWidth("100pt").setHeight("50pt")
                ).addBodyComponent(
                        XEasyPdfTemplateHandler.TextExtend.build().setFontFamily("微软雅黑").setFontSize("30pt").addText(
                                XEasyPdfTemplateHandler.Text.build().setText("hello"),
                                XEasyPdfTemplateHandler.Text.build().setText("上标").setFontSize("12pt").setVerticalStyle("top"),
                                XEasyPdfTemplateHandler.Text.build().setText(", world")
                        )
                )
        );
        XEasyPdfTemplateHandler.Template.build().setDataSource(
                XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(document)
        ).save(outputPath);
        document.transform(pdfPath);
    }
}
