package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageHeader;
import org.dromara.pdf.pdfbox.core.component.Component;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.component.*;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2023/7/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class TableTest extends BaseTest {

    public static void main(String[] args) {
        // 创建文档
        Document document = PdfHandler.getDocumentHandler().create();
        // 创建页面
        Page page = new Page(document);
        // 定义每列宽度
        float width = 120F;
        // 定义行高
        float height = 70F;

        // 创建表格
        Table table = new Table(document.getCurrentPage());
        // 设置列宽（4列）
        table.setCellWidths(width, width, width, width);
        // 设置显示边框
        table.setIsBorder(true);
        table.setMargin(20);
        // 添加行
        for (int i = 0; i < 3; i++) {
            // 创建行
            TableRow tableRow = new TableRow(table);
            // 设置行高
            tableRow.setHeight(height);

            // 添加列
            for (int j = 0; j < 4; j++) {

                // 创建文本域
                Textarea textarea = new Textarea(table.getPage());
                // 设置文本
                textarea.setText( i + "行"  + j + "列");

                Textarea textarea2 = new Textarea(table.getPage());
                textarea2.setText(i + "-"  + j +"红色");
                textarea2.setFontColor(Color.RED);
                // 创建单元格
                TableCell cell = new TableCell(tableRow);
                // 添加组件
                cell.addComponents(textarea2, textarea);
                // 添加单元格
                tableRow.addCells(cell);
            }
            // 添加行
            table.addRows(tableRow);
        }
        // 绘制
        table.render();
        // 添加页面
        document.appendPage(page);
        document.saveAndClose("E:\\PDF\\pdfbox\\table\\test.pdf");
    }

    /**
     * 表格测试
     */
    @Test
    public void tableTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();

            // 创建页面
            Page page = new Page(document);

            // 定义每列宽度
            float width = 100F;
            // 定义行高
            float height = 70F;

            // 创建表格
            Table table = new Table(document.getCurrentPage());
            // 设置列宽（4列）
            table.setCellWidths(width, width, width, width);
            // 设置显示边框
            table.setIsBorder(true);

            // 添加行
            for (int i = 0; i < 10; i++) {
                // 创建行
                TableRow tableRow = new TableRow(table);
                // 设置行高
                tableRow.setHeight(height);
                // 第3行合并列
                if (i == 2) {
                    // 创建文本域
                    Textarea textarea = new Textarea(table.getPage());
                    // 设置文本
                    textarea.setText("第" + i + "行" + "合并列");
                    // 创建单元格
                    TableCell cell = new TableCell(tableRow);
                    // 设置合并列数
                    cell.setColspan(3);
                    // 添加组件
                    cell.addComponents(textarea);
                    // 添加单元格
                    tableRow.addCells(cell);
                } else {
                    // 添加列
                    for (int j = 0; j < 4; j++) {
                        // 被合并列
                        if (i > 3 && i < 6 && j == 0) {
                            // 添加占位
                            tableRow.addCells((TableCell) null);
                        } else {
                            // 创建文本域
                            Textarea textarea = new Textarea(table.getPage());
                            // 设置文本
                            textarea.setText("第" + i + "行" + "第" + j + "列");
                            // 创建单元格
                            TableCell cell = new TableCell(tableRow);
                            // 添加组件
                            cell.addComponents(textarea);
                            // 第4行第1列合并2行
                            if (i == 3 && j == 0) {
                                // 设置合并行数
                                cell.setRowspan(2);
                            }
                            // 添加单元格
                            tableRow.addCells(cell);
                        }
                    }
                }
                // 添加行
                table.addRows(tableRow);
            }


            // 绘制
            table.render();

            // 添加页面
            document.appendPage(page);
            // 保存文档
            document.save("E:\\PDF\\pdfbox\\table\\test.pdf");
            // 关闭文档
            document.close();

        });
    }

    /**
     * 表格测试
     */
    @Test
    public void simpleTableTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(70F);
            document.setFontName("微软雅黑");
            document.setFontSize(12F);

            Page page = new Page(document);
            page.setMarginTop(20F);
            page.setMarginBottom(20F);
            page.setFontName("仿宋");
            page.setFontSize(15F);
            page.setFontStyle(FontStyle.NORMAL);

            Textarea textarea = new Textarea(document.getCurrentPage());
            textarea.setText("物料标签");
            textarea.setFontSize(21F);
            textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            textarea.render();

            float width = page.getWithoutMarginWidth() / 6;
            float height = 70F;

            Table table = new Table(document.getCurrentPage());
            table.setCellWidths(width, width * 2, width, width * 2);
            table.setBeginX(70F);
            table.setIsBorder(true);
            table.setIsTogether(true);
            table.setBorderColor(Color.GRAY);

            TableRow tableRow1 = new TableRow(table);
            tableRow1.setHeight(height);
            Textarea row1cell1Textarea = new Textarea(table.getPage());
            row1cell1Textarea.setText("名称");
            row1cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row1cell2Textarea = new Textarea(table.getPage());
            row1cell2Textarea.setText("materialsName");
            row1cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row1TableCell1 = new TableCell(tableRow1);
            row1TableCell1.setComponents(row1cell1Textarea);
            TableCell row1TableCell2 = new TableCell(tableRow1);
            row1TableCell2.setComponents(row1cell2Textarea);
            row1TableCell2.setColspan(2);
            tableRow1.setCells(row1TableCell1, row1TableCell2);

            TableRow tableRow2 = new TableRow(table);
            tableRow2.setHeight(height);
            Textarea row2cell1Textarea = new Textarea(table.getPage());
            row2cell1Textarea.setText("数量");
            row2cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row2cell2Textarea = new Textarea(table.getPage());
            row2cell2Textarea.setText("materialsNum");
            row2cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row2TableCell1 = new TableCell(tableRow2);
            row2TableCell1.setComponents(row2cell1Textarea);
            TableCell row2TableCell2 = new TableCell(tableRow2);
            row2TableCell2.setComponents(row2cell2Textarea);
            Textarea row2cell3Textarea = new Textarea(table.getPage());
            row2cell3Textarea.setText("日期");
            row2cell3Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row2cell4Textarea = new Textarea(table.getPage());
            row2cell4Textarea.setText("materialsDate");
            row2cell4Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row2TableCell3 = new TableCell(tableRow2);
            row2TableCell3.setComponents(row2cell3Textarea);
            TableCell row2TableCell4 = new TableCell(tableRow2);
            row2TableCell4.setComponents(row2cell4Textarea);
            tableRow2.setCells(row2TableCell1, row2TableCell2, row2TableCell3, row2TableCell4);

            TableRow tableRow3 = new TableRow(table);
            tableRow3.setHeight(height);
            Textarea row3cell1Textarea = new Textarea(table.getPage());
            row3cell1Textarea.setText("料号");
            row3cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row3cell2Textarea = new Textarea(table.getPage());
            row3cell2Textarea.setText("materialsCode");
            row3cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row3cell3Textarea = new Textarea(table.getPage());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append("te st").append(i);
                builder.append("中文 测试").append(i);
            }
            row3cell3Textarea.setText(builder.toString());
            row3cell3Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row3TableCell1 = new TableCell(tableRow3);
            row3TableCell1.setComponents(row3cell1Textarea);
            TableCell row3TableCell2 = new TableCell(tableRow3);
            row3TableCell2.setColspan(1);
            row3TableCell2.setComponents(row3cell2Textarea);
            TableCell row3TableCell3 = new TableCell(tableRow3);
            row3TableCell3.setRowspan(2);
            row3TableCell3.setComponents(row3cell3Textarea);
            tableRow3.setCells(row3TableCell1, row3TableCell2, null, row3TableCell3);

            TableRow tableRow4 = new TableRow(table);
            tableRow4.setHeight(height);
            Textarea row4cell1Textarea = new Textarea(table.getPage());
            row4cell1Textarea.setText("标签号");
            row4cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row4cell2Textarea = new Textarea(table.getPage());
            row4cell2Textarea.setText("materialsLabel");
            row4cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row4TableCell1 = new TableCell(tableRow4);
            row4TableCell1.setComponents(row4cell1Textarea);
            TableCell row4TableCell2 = new TableCell(tableRow4);
            row4TableCell2.setColspan(1);
            row4TableCell2.setComponents(row4cell2Textarea);
            tableRow4.setCells(row4TableCell1, row4TableCell2, null, null);

            TableRow tableRow5 = new TableRow(table);
            tableRow5.setHeight(height);
            Textarea row5cell1Textarea = new Textarea(table.getPage());
            row5cell1Textarea.setText("SAP批次");
            row5cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row5cell2Textarea = new Textarea(table.getPage());
            row5cell2Textarea.setText("materialsSapBatch");
            row5cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row5TableCell1 = new TableCell(tableRow5);
            row5TableCell1.setComponents(row5cell1Textarea);
            TableCell row5TableCell2 = new TableCell(tableRow5);
            row5TableCell2.setColspan(1);
            row5TableCell2.setComponents(row5cell2Textarea);
            tableRow5.setCells(row5TableCell1, row5TableCell2, null, null);

            TableRow tableRow6 = new TableRow(table);
            tableRow6.setHeight(height);
            Textarea row6cell1Textarea = new Textarea(table.getPage());
            row6cell1Textarea.setText("品保判定");
            row6cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row6cell2Textarea = new Textarea(table.getPage());
            row6cell2Textarea.setText("qualityAssurance");
            row6cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row6TableCell1 = new TableCell(tableRow6);
            row6TableCell1.setComponents(row6cell1Textarea);
            TableCell row6TableCell2 = new TableCell(tableRow6);
            row6TableCell2.setComponents(row6cell2Textarea);
            Textarea row6cell3Textarea = new Textarea(table.getPage());
            row6cell3Textarea.setText("校验");
            row6cell3Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row6cell4Textarea = new Textarea(table.getPage());
            row6cell4Textarea.setText("check");
            row6cell4Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row6TableCell3 = new TableCell(tableRow6);
            row6TableCell3.setComponents(row6cell3Textarea);
            TableCell row6TableCell4 = new TableCell(tableRow6);
            row6TableCell4.setComponents(row6cell4Textarea);
            tableRow6.setCells(row6TableCell1, row6TableCell2, row6TableCell3, row6TableCell4);

            TableRow tableRow7 = new TableRow(table);
            tableRow7.setHeight(height);
            Textarea row7cell1Textarea = new Textarea(table.getPage());
            row7cell1Textarea.setText("备注");
            row7cell1Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Textarea row7cell2Textarea = new Textarea(table.getPage());
            row7cell2Textarea.setText("remark");
            row7cell2Textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            TableCell row7TableCell1 = new TableCell(tableRow7);
            row7TableCell1.setComponents(row7cell1Textarea);
            TableCell row7TableCell2 = new TableCell(tableRow7);
            row7TableCell2.setComponents(row7cell2Textarea);
            row7TableCell2.setColspan(2);
            tableRow7.setCells(row7TableCell1, row7TableCell2);

            List<TableRow> rowList = new ArrayList<>(10);
            for (int i = 1; i <= 15; i++) {
                TableRow tableRow = new TableRow(table);
                tableRow.setHeight(height);
                Textarea textarea1 = new Textarea(table.getPage());
                textarea1.setText("第" + i + "行");
                textarea1.setHorizontalAlignment(HorizontalAlignment.CENTER);
                Textarea textarea2 = new Textarea(table.getPage());
                textarea2.setText("内容" + i);
                textarea2.setHorizontalAlignment(HorizontalAlignment.CENTER);
                TableCell cell1 = new TableCell(tableRow);
                cell1.setComponents(textarea1);
                TableCell cell2 = new TableCell(tableRow);
                cell2.setComponents(textarea2);
                cell2.setColspan(2);
                tableRow.setCells(cell1, cell2);
                rowList.add(tableRow);
            }

            int rowCount = 13;
            TableRow tableRow = new TableRow(table);
            tableRow.setHeight(height);
            Textarea textarea1 = new Textarea(table.getPage());
            textarea1.setText("第16行");
            textarea1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            textarea1.setVerticalAlignment(VerticalAlignment.BOTTOM);
            Textarea textarea2 = new Textarea(table.getPage());
            textarea2.setText("合并内容1");
            textarea2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            textarea2.setVerticalAlignment(VerticalAlignment.BOTTOM);
            TableCell cell1 = new TableCell(tableRow);
            cell1.setComponents(textarea1);
            cell1.setRowspan(rowCount);
            TableCell cell2 = new TableCell(tableRow);
            cell2.setComponents(textarea2);
            cell2.setColspan(2);
            tableRow.setCells(cell1, cell2);
            rowList.add(tableRow);

            for (int i = 1; i <= rowCount; i++) {
                tableRow = new TableRow(table);
                tableRow.setHeight(height);
                textarea = new Textarea(table.getPage());
                textarea.setText("合并内容" + (i + 1));
                textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
                textarea.setVerticalAlignment(VerticalAlignment.BOTTOM);
                TableCell cell = new TableCell(tableRow);
                cell.setColspan(2);
                cell.setComponents(textarea);
                tableRow.setCells(null, cell);
                rowList.add(tableRow);
            }

            for (int i = 17; i <= 40; i++) {
                tableRow = new TableRow(table);
                tableRow.setHeight(height);
                textarea1 = new Textarea(table.getPage());
                textarea1.setText("第" + i + "行");
                textarea1.setHorizontalAlignment(HorizontalAlignment.CENTER);
                textarea2 = new Textarea(table.getPage());
                textarea2.setText("内容" + i);
                textarea2.setHorizontalAlignment(HorizontalAlignment.CENTER);
                cell1 = new TableCell(tableRow);
                cell1.setComponents(textarea1);
                cell2 = new TableCell(tableRow);
                cell2.setComponents(textarea2);
                cell2.setColspan(2);
                tableRow.setCells(cell1, cell2);
                rowList.add(tableRow);
            }

            table.setRows(tableRow1, tableRow2, tableRow3, tableRow4, tableRow5, tableRow6, tableRow7);
            table.addRows(rowList);
            table.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\table\\simpleTableTest.pdf");
            document.close();
        });
    }

    /**
     * 表格测试
     */
    @Test
    public void simpleTableTest2() {
        // 关闭字体扫描
        PdfHandler.disableScanSystemFonts();
        PdfHandler.getFontHandler().addFont(new File("C:\\Windows\\Fonts\\msyh.ttc"));
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(10F);
            document.setFontName("微软雅黑");
            document.setFontSize(12F);

            Page page = new Page(document);
            PageHeader header = new PageHeader(document.getCurrentPage());
            List<Component> components = new ArrayList<>(10);
            Textarea headerTextarea = new Textarea(header.getPage());
            headerTextarea.setText("页眉");
            headerTextarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
            components.add(headerTextarea);
            header.setComponents(components);
            header.setHeight(12F);
            header.setIsBorder(false);
            header.render();

            float width = page.getWithoutMarginWidth() / 6;
            float height = 200F;
            Table table = new Table(document.getCurrentPage());
            table.setCellWidths(width, width * 2, width, width * 2);
            table.setIsBorder(true);
            table.setIsPagingBorder(false);
            table.setBorderColor(Color.GRAY);

            List<TableRow> rowList = new ArrayList<>(16);

            int rowCount = 13;
            TableRow tableRow = new TableRow(table);
            tableRow.setHeight(height);
            Textarea textarea1 = new Textarea(table.getPage());
            textarea1.setText("合并行");
            textarea1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            textarea1.setVerticalAlignment(VerticalAlignment.BOTTOM);
            Textarea textarea2 = new Textarea(table.getPage());
            textarea2.setText("合并内容1");
            textarea2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            textarea2.setVerticalAlignment(VerticalAlignment.BOTTOM);
            TableCell cell1 = new TableCell(tableRow);
            cell1.setComponents(textarea1);
            cell1.setRowspan(rowCount);
            TableCell cell2 = new TableCell(tableRow);
            cell2.setComponents(textarea2);
            cell2.setColspan(2);
            tableRow.setCells(cell1, cell2);
            rowList.add(tableRow);

            for (int i = 1; i <= rowCount; i++) {
                tableRow = new TableRow(table);
                tableRow.setHeight(height);
                Textarea textarea = new Textarea(table.getPage());
                textarea.setText("合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容合并内容" + (i + 1));
                textarea.setMarginLeft(50F);
                TableCell cell = new TableCell(tableRow);
                cell.setColspan(2);
                cell.setComponents(textarea);
                tableRow.setCells(null, cell);
                rowList.add(tableRow);
            }
            table.setRows(rowList);
            table.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\table\\simpleTableTest.pdf");
            document.close();
        });
    }

    /**
     * 表格测试
     */
    @Test
    public void simpleTableTest3() {
        // 关闭字体扫描
        PdfHandler.disableScanSystemFonts();
        PdfHandler.getFontHandler().addFont(new File("C:\\Windows\\Fonts\\msyh.ttc"));
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");
            document.setFontSize(12F);

            Page page = new Page(document);

            float width = page.getWithoutMarginWidth() / 3;
            float height = 250;
            Table table = new Table(document.getCurrentPage());
            table.setCellWidths(width, width, width);
            table.setIsBorder(true);
            table.setIsPagingBorder(true);
            table.setIsTogether(true);
            table.setBorderColor(Color.GRAY);

            for (int i = 0; i < 5; i++) {
                TableRow row = new TableRow(table);
                row.setHeight(height);
                row.setBackgroundColor(ColorUtil.toColor("#33ccff"));
                // row.setIsTogether(true);
                // if (i == 3) {
                //     row.setIsBreak(true);
                // }
                for (int j = 0; j < 3; j++) {
                    TableCell cell = new TableCell(row);
                    if (j == 0) {
                        cell.setIsEnableUpLine(Boolean.TRUE);
                        cell.setIsEnableDownLine(Boolean.TRUE);
                    } else {
                        Textarea textarea = new Textarea(table.getPage());
                        textarea.setText("贵阳");
                        cell.setComponents(textarea);
                        cell.setContentHorizontalAlignment(HorizontalAlignment.CENTER);
                        cell.setContentVerticalAlignment(VerticalAlignment.CENTER);
                    }
                    row.addCells(cell);
                }
                table.addRows(row);
            }
            table.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\table\\simpleTableTest.pdf");
            document.close();
        });
    }

    /**
     * 表格测试
     */
    @Test
    public void simpleTableTest4() {
        // 关闭字体扫描
        PdfHandler.disableScanSystemFonts();
        PdfHandler.getFontHandler().addFont(new File("C:\\Windows\\Fonts\\msyh.ttc"));
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");
            document.setFontSize(12F);

            Page page = new Page(document);
            page.setMargin(20);

            float width = page.getWithoutMarginWidth() / 3;
            float height = 150F;

            Table table = new Table(document.getCurrentPage());
            table.setCellWidths(width, width, width);
            table.setIsBorder(true);
            table.setIsPagingBorder(false);

            for (int i = 0; i < 1; i++) {
                TableRow row = new TableRow(table);
                row.setHeight(20F);
                for (int j = 1; j < 4; j++) {
                    Textarea textarea = new Textarea(table.getPage());
                    textarea.setText("标题" + i);
                    textarea.setVerticalAlignment(VerticalAlignment.CENTER);
                    TableCell cell = new TableCell(row);
                    cell.setComponents(textarea);
                    row.addCells(cell);
                }
                table.addRows(row);
            }
            for (int i = 1; i < 4; i++) {
                TableRow row = new TableRow(table);
                row.setHeight(height);
                for (int j = 1; j < 4; j++) {
                    Textarea textarea = new Textarea(table.getPage());
                    textarea.setText("内容内容" + i);
                    if (i == 1) {
                        textarea.setHorizontalAlignment(HorizontalAlignment.LEFT);
                        textarea.setVerticalAlignment(VerticalAlignment.TOP);
                    } else if (i == 2) {
                        textarea.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        textarea.setVerticalAlignment(VerticalAlignment.CENTER);
                    } else {
                        textarea.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                        textarea.setVerticalAlignment(VerticalAlignment.BOTTOM);
                    }
                    TableCell cell = new TableCell(row);
                    cell.setComponents(textarea);
                    row.addCells(cell);
                }
                table.addRows(row);
            }
            for (int i = 1; i < 4; i++) {
                TableRow row = new TableRow(table);
                row.setHeight(height);
                row.setIsTogether(true);
                for (int j = 1; j < 4; j++) {
                    TableCell cell = new TableCell(row);
                    Image image = new Image(table.getPage());
                    image.setWidth(100);
                    image.setHeight(100);
                    image.setIsBorder(true);
                    if (i == 1) {
                        cell.setContentHorizontalAlignment(HorizontalAlignment.LEFT);
                        image.setImage(new File("E:\\PDF\\pdfbox\\image\\test.svg"));
                    } else if (i == 2) {
                        cell.setContentHorizontalAlignment(HorizontalAlignment.CENTER);
                        image.setImage(Paths.get("E:\\PDF\\pdfbox\\image\\test.jpg").toFile());
                    } else {
                        cell.setContentHorizontalAlignment(HorizontalAlignment.RIGHT);
                        image.setImage(Paths.get("E:\\PDF\\pdfbox\\image\\test.png").toFile());
                    }
                    if (j == 1) {
                        cell.setContentVerticalAlignment(VerticalAlignment.TOP);
                    } else if (j == 2) {
                        cell.setContentVerticalAlignment(VerticalAlignment.CENTER);
                    } else {
                        cell.setContentVerticalAlignment(VerticalAlignment.BOTTOM);
                    }
                    cell.setComponents(image);
                    row.addCells(cell);
                }
                table.addRows(row);
            }
            table.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\table\\simpleTableTest.pdf");
            document.close();
        });
    }

    /**
     * 表格测试
     */
    @Test
    public void tableHeaderTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");
            document.setFontSize(12F);
            document.setMargin(50F);

            Page page = new Page(document);

            float width = page.getWithoutMarginWidth() / 3;
            float height = 100;
            Table table = new Table(document.getCurrentPage());
            table.setCellWidths(width, width, width);
            table.setIsBorder(true);
            table.setIsTogether(true);
            table.setBorderColor(Color.BLACK);

            TableHeader header = new TableHeader(table);

            TableRow headerRow = new TableRow(table);
            headerRow.setBackgroundColor(Color.LIGHT_GRAY);
            headerRow.setHeight(20F);
            for (int j = 1; j < 4; j++) {
                Textarea textarea = new Textarea(table.getPage());
                textarea.setText("表头" + j);
                TableCell cell = new TableCell(headerRow);
                cell.setComponents(textarea);
                cell.setContentHorizontalAlignment(HorizontalAlignment.CENTER);
                cell.setContentVerticalAlignment(VerticalAlignment.CENTER);
                headerRow.addCells(cell);
            }
            header.addRows(headerRow);
            table.setHeader(header);

            for (int i = 0; i < 10; i++) {
                TableRow row = new TableRow(table);
                row.setHeight(height);
                row.setBackgroundColor(ColorUtil.toColor("#33ccff"));
                for (int j = 0; j < 3; j++) {
                    Textarea textarea = new Textarea(table.getPage());
                    textarea.setText("贵阳" + i);
                    TableCell cell = new TableCell(row);
                    cell.setComponents(textarea);
                    cell.setContentHorizontalAlignment(HorizontalAlignment.CENTER);
                    cell.setContentVerticalAlignment(VerticalAlignment.CENTER);
                    row.addCells(cell);
                }
                table.addRows(row);
            }
            table.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\table\\tableHeaderTest.pdf");
            document.close();
        });
    }
}
