package org.dromara.pdf.fop;

import org.dromara.pdf.fop.doc.Document;
import org.dromara.pdf.fop.doc.component.table.Table;
import org.dromara.pdf.fop.doc.component.table.TableBody;
import org.dromara.pdf.fop.doc.component.table.TableCell;
import org.dromara.pdf.fop.doc.component.table.TableRow;
import org.dromara.pdf.fop.doc.component.text.Text;
import org.dromara.pdf.fop.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/8/23
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
public class TableTest {

    @Test
    public void testTable() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table.pdf";
        // 转换pdf
        TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1-hello-world-1")
                                                ).setBorder("1 solid black"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1-hello-world-2")
                                                ).setBorder("1 solid black")
                                        ),
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("2-hello-world-1")
                                                ).setBorder("1 solid black"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("2-hello-world-2")
                                                ).setBorder("1 solid black")
                                        )
                                )
                        ).setWidth("200pt").setMargin("10pt")
                )
        ).transform(outputPath);
    }

    @Test
    public void testTable2() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        Document document = TemplateHandler.Document.build();
        // 创建页面（空白页）
        Page page = TemplateHandler.Page.build();
        // 创建表格
        Table table = TemplateHandler.Table.build();
        // 创建表格体
        TableBody tableBody = TemplateHandler.Table.Body.build();
        for (int i = 1; i <= 3; i++) {
            // 创建表格行
            TableRow row = TemplateHandler.Table.Row.build();
            for (int j = 1; j <= 3; j++) {
                if (i == 3 && j == 1 || i == 2 && j == 3) {
                    continue;
                }
                // 创建表格单元格
                TableCell cell = TemplateHandler.Table.Cell.build();
                // 设置单元格边框
                cell.setBorder("1 solid black");
                // 创建文本
                Text text = TemplateHandler.Text.build().setText(String.format("%s-hello-world-%s", i, j));
                // 添加文本
                cell.addComponent(text);
                if (i == 2) {
                    if (j == 1) {
                        // 合并两行
                        cell.setRowSpan(2);
                    } else {
                        // 合并两列
                        cell.setColumnSpan(2);
                    }
                }
                // 添加单元格
                row.addCell(cell);
            }
            // 添加行
            tableBody.addRow(row);
        }
        // 设置表格体
        table.setBody(tableBody);
        // 添加表格
        page.addBodyComponent(table);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testTableSpan() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table.pdf";
        // 转换pdf
        TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1-hello-world-1")
                                                ).setBorder("1 solid black"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1-hello-world-2")
                                                ).setBorder("1 solid black"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1-hello-world-3")
                                                ).setBorder("1 solid black")
                                        ),
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                                TemplateHandler.Text.build().setText("2-hello-world-1")
                                                        ).setBorder("1 solid black")
                                                        // 合并两行
                                                        .setRowSpan(2),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                                TemplateHandler.Text.build().setText("2-hello-world-2")
                                                        ).setBorder("1 solid black")
                                                        // 合并两列
                                                        .setColumnSpan(2)
                                        ),
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("3-hello-world-2")
                                                ).setBorder("1 solid black"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("3-hello-world-3")
                                                ).setBorder("1 solid black")
                                        )
                                )
                        )
                )
        ).transform(outputPath);
    }

    @Test
    public void testTableBorderStyle() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-table-border-style.pdf";
        // 转换pdf
        Document document = TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("2").setHorizontalStyle("center")
                                                )
                                        )
                                )
                        ),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("NONE").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("NONE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("NONE")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("HIDDEN").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("HIDDEN").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("HIDDEN")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DOTTED").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DOTTED").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("DOTTED")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DASHED").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DASHED").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("DASHED")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("SOLID").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("SOLID").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("SOLID")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DOUBLE").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("DOUBLE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("DOUBLE")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("GROOVE").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("GROOVE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("GROOVE")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("RIDGE").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("RIDGE").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("RIDGE")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("INSET").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("INSET").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("INSET")
                                )
                        ).setBorderCollapse("separate"),
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("OUTSET").setHorizontalStyle("center")
                                                ),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("OUTSET").setHorizontalStyle("center")
                                                )
                                        ).setBorderStyle("OUTSET")
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
        Document document = TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Table.build().setBody(
                                TemplateHandler.Table.Body.build().addRow(
                                        TemplateHandler.Table.Row.build().addCell(
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("1").setHorizontalStyle("center")
                                                ).setBorder("inset 5pt").setWidth("70pt"),
                                                TemplateHandler.Table.Cell.build().addComponent(
                                                        TemplateHandler.Text.build().setText("2").setHorizontalStyle("center")
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
