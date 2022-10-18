package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplateBorderStyle;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/8/23
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfTemplateTableTest {

    @Test
    public void testTable() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().setConfigPath(configPath).addPage(
                XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("1").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("2").setHorizontalStyle("center")
                                                )
                                        )
                                )
                        )
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testTableBorderStyle() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table-border-style.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().setConfigPath(configPath).addPage(
                XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("1").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("2").setHorizontalStyle("center")
                                                )
                                        )
                                )
                        ),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("NONE").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("NONE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.NONE.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("HIDDEN").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("HIDDEN").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.HIDDEN.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DOTTED").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DOTTED").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.DOTTED.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DASHED").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DASHED").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.DASHED.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("SOLID").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("SOLID").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.SOLID.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DOUBLE").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("DOUBLE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.DOUBLE.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("GROOVE").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("GROOVE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.GROOVE.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("RIDGE").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("RIDGE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.RIDGE.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("INSET").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("INSET").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.INSET.getValue())
                                )
                        ).setBorderCollapse("separate"),
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("OUTSET").setHorizontalStyle("center")
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("OUTSET").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle(XEasyPdfTemplateBorderStyle.OUTSET.getValue())
                                )
                        ).setBorderCollapse("separate")
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testTableBorderStyle3D() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table-border-style3D.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().setConfigPath(configPath).addPage(
                XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                        XEasyPdfTemplateHandler.Table.build().setBody(
                                XEasyPdfTemplateHandler.Table.Body.build().addRow(
                                        XEasyPdfTemplateHandler.Table.Row.build().addCell(
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("1").setHorizontalStyle("center")
                                                ).setBorder("inset 5pt").setWidth("70pt"),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("2").setHorizontalStyle("center")
                                                ).setBorder("inset 5pt").setWidth("70pt")
                                        )
                                )
                        ).setWidth("200pt").setHeight("100pt").setBorder("outset 10pt").setBorderCollapse("separate").setBorderSpacing("15pt").setHorizontalStyle("center")
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }
}
