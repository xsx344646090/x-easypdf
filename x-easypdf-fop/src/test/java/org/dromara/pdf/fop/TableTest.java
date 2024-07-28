package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.core.doc.component.table.*;
import org.dromara.pdf.fop.core.doc.component.text.Text;
import org.dromara.pdf.fop.core.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2022/8/23
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
 * jdbc:mysql://10.88.99.52:3306/test_middlestage_order_center?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&connectTimeout=5000&socketTimeout=5000&autoReconnect=true&rewriteBatchedStatements=true
 */
public class TableTest extends BaseTest {

    @Test
    public void tableTest1() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\table\\tableTest1.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build();
            Page page = TemplateHandler.Page.build();
            Table table = TemplateHandler.Table.build();
            table.setFontFamily("微软雅黑");
            TableHeader tableHeader = TemplateHandler.Table.Header.build();
            TableRow tableHeaderRow = TemplateHandler.Table.Row.build();
            tableHeaderRow.setFontSize("20pt");
            TableCell nameCell = TemplateHandler.Table.Cell.build();
            nameCell.addComponent(TemplateHandler.Text.build().setText("姓名"));
            TableCell dutiesCell = TemplateHandler.Table.Cell.build();
            dutiesCell.addComponent(TemplateHandler.Text.build().setText("职务"));
            TableCell phoneCell = TemplateHandler.Table.Cell.build();
            phoneCell.addComponent(TemplateHandler.Text.build().setText("电话"));
            tableHeaderRow.addCell(nameCell, dutiesCell, phoneCell);
            tableHeader.addRow(tableHeaderRow);
            table.setHeader(tableHeader);

            List<TableRow> rows = new ArrayList<>(30);
            for (int i = 0; i < 100; i++) {
                TableRow row = TemplateHandler.Table.Row.build();
                TableCell cell1 = TemplateHandler.Table.Cell.build();
                cell1.addComponent(TemplateHandler.Text.build().setText("姓名" + i));
                TableCell cell2 = TemplateHandler.Table.Cell.build();
                cell2.addComponent(TemplateHandler.Text.build().setText("职务" + i));
                TableCell cell3 = TemplateHandler.Table.Cell.build();
                cell3.addComponent(TemplateHandler.Text.build().setText("电话" + i));
                row.addCell(cell1, cell2, cell3);
                rows.add(row);
            }
            TableBody tableBody = TemplateHandler.Table.Body.build();
            tableBody.addRow(rows);
            table.setBody(tableBody);
            page.addBodyComponent(table);
            document.addPage(page);
            document.transform(outputPath);
        });
    }

    @Test
    public void tableTest2() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\table\\tableTest2.pdf";
            // 创建文档
            Document document = TemplateHandler.Document.build();
            // 创建页面（空白页）
            Page page = TemplateHandler.Page.build();
            // 创建表格
            Table table = TemplateHandler.Table.build();
            // 创建表格体
            TableBody tableBody = TemplateHandler.Table.Body.build();
            for (int i = 1; i <= 300; i++) {
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
        });
    }

    @Test
    public void tableSpanTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\table\\tableSpanTest.pdf";
            // 转换pdf
            TemplateHandler.Document.build().addPage(
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
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-4")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-5")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-6")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-7")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-8")
                                                    ).setBorder("1 solid black")
                                            ),
                                            TemplateHandler.Table.Row.build().addCell(
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                                    TemplateHandler.Text.build().setText("2-hello-world-1")
                                                            ).setBorder("1 solid black")
                                                            // 合并两行
                                                            .setRowSpan(4),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                                    TemplateHandler.Text.build().setText("2-hello-world-2")
                                                            ).setBorder("1 solid black")
                                                            // 合并两列
                                                            .setColumnSpan(4),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-6")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-7")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-8")
                                                    ).setBorder("1 solid black")
                                            ),
                                            TemplateHandler.Table.Row.build().addCell(
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-2")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-3")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-4")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-5")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-6")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-7")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-8")
                                                    ).setBorder("1 solid black")
                                            ),
                                            TemplateHandler.Table.Row.build().addCell(
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-2")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-3")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-4")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-5")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-6")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-7")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-8")
                                                    ).setBorder("1 solid black")
                                            ),
                                            TemplateHandler.Table.Row.build().addCell(
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-2")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("3-hello-world-3")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-4")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-5")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-6")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-7")
                                                    ).setBorder("1 solid black"),
                                                    TemplateHandler.Table.Cell.build().addComponent(
                                                            TemplateHandler.Text.build().setText("1-hello-world-8")
                                                    ).setBorder("1 solid black")
                                            )
                                    )
                            )
                    )
            ).transform(outputPath);
        });
    }

    @Test
    public void tableBorderStyleTest() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\table\\tableBorderStyleTest.pdf";
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            ),
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
                            )
                    )
            );
            // 转换pdf
            document.transform(outputPath);
        });
    }

    @Test
    public void tableBorderStyle3DTest() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\table\\tableBorderStyle3DTest.pdf";
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
        });
    }
}
