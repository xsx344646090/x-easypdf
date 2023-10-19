package org.dromara.pdf.fop;

import org.dromara.pdf.fop.doc.Document;
import org.dromara.pdf.fop.doc.bookmark.Bookmark;
import org.dromara.pdf.fop.doc.component.table.TableRow;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2022/11/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class LinkTest {

    @Test
    public void testTextLink() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-text-link.pdf";
        // 转换pdf
        Document document = TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Link.build().setText(
                                TemplateHandler.Text.build()
                                        .setText("https://www.x-easypdf.cn")
                                        .setUnderLineColor("blue")
                                        .enableUnderLine()
                                        .setHorizontalStyle("center")
                        ).setExternalDestination("https://www.x-easypdf.cn")
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testTextExtendLink() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-text-extend-link.pdf";
        // 转换pdf
        Document document = TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().setId("test").setFontFamily("微软雅黑").addBodyComponent(
                        TemplateHandler.BlockContainer.build().setHorizontalStyle("center").addComponent(
                                TemplateHandler.Text.build().setText("当前页码："),
                                TemplateHandler.CurrentPageNumber.build(),
                                TemplateHandler.Text.build().setText("，总页码："),
                                TemplateHandler.TotalPageNumber.build().setPageId("test")
                        )
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void test() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-demo.pdf";
        List<TableRow> rowList = new ArrayList<>(50);
        Bookmark bookmark = TemplateHandler.Bookmark.build().setTitle("TITLE").setInternalDestination("TEST");
        for (int i = 0; i < 20; i++) {
            String id = "bm" + i;
            rowList.add(
                    TemplateHandler.Table.Row.build().addCell(
                            TemplateHandler.Table.Cell.build()
                                    .setBorderStyle("solid")
                                    .setWidth("100pt")
                                    .addComponent(
                                            TemplateHandler.Text.build()
                                                    .setText("项目名称").setId(id)
                                    ),
                            TemplateHandler.Table.Cell.build()
                                    .setBorderStyle("solid")
                                    .addComponent(
                                            TemplateHandler.Text.build()
                                                    .setText(id)
                                    )
                    )
            );
            rowList.add(
                    TemplateHandler.Table.Row.build().addCell(
                            TemplateHandler.Table.Cell.build()
                                    .setBorderStyle("solid")
                                    .setWidth("100pt")
                                    .addComponent(
                                            TemplateHandler.Text.build()
                                                    .setText("时间")
                                    ),
                            TemplateHandler.Table.Cell.build()
                                    .setBorderStyle("solid")
                                    .addComponent(
                                            TemplateHandler.Text.build()
                                                    .setText("XXX")
                                    )
                    )
            );
            bookmark.addChild(
                    TemplateHandler.Bookmark.build().setTitle(id).setInternalDestination(id)
            );
        }
        rowList.add(
                TemplateHandler.Table.Row.build().addCell(
                        TemplateHandler.Table.Cell.build()
                                .setBorderStyle("solid")
                                .setWidth("100pt")
                                // .setRowSpan(10)
                                .addComponent(
                                        TemplateHandler.Text.build()
                                                .setText("项目名称")
                                ),
                        TemplateHandler.Table.Cell.build()
                                .setBorderStyle("solid")
                                .addComponent(
                                        TemplateHandler.Text.build()
                                                .setText("合并行 合并行").setWordSpacing("300pt").setLanguage("zh")
                                )
                )
        );
        // for (int i = 0; i < 9; i++) {
        //     rowList.add(
        //             XEasyPdfTemplateHandler.Table.Row.build().addCell(
        //                     XEasyPdfTemplateHandler.Table.Cell.build()
        //                             .setBorderStyle("solid")
        //                             .addComponent(
        //                                     XEasyPdfTemplateHandler.Text.build()
        //                                             .setText("合并行")
        //                             )
        //             )
        //     );
        // }
        bookmark.addChild(
                TemplateHandler.Bookmark.build().setTitle("ddd").setInternalDestination("ddd")
        );
        // 转换pdf
        Document document = TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().changeLandscape()
                        .setFontFamily("微软雅黑")
                        .setMargin("20pt")
                        .addBodyComponent(
                                TemplateHandler.Text.build()
                                        .setFontSize("30pt")
                                        .setId("TEST")
                                        .setText("报总经办")
                                        .setHorizontalStyle("center"),
                                TemplateHandler.TextExtend.build().setMarginTop("20pt").addText(
                                        TemplateHandler.Text.build()
                                                .setText("OA系统                               "),
                                        TemplateHandler.Text.build()
                                                .setText("创建时间：2022-11-03 17:15:00")
                                ),
                                TemplateHandler.Barcode.build()
                                        .setType("QR_CODE")
                                        .setContent("https://www.x-easypdf.cn")
                                        .setWidth("60pt")
                                        .setHeight("60pt")
                                        .setHorizontalStyle("right")
                                        .setMarginTop("-70pt"),
                                TemplateHandler.Text.build()
                                        .setText("微信扫一扫")
                                        .setHorizontalStyle("right")
                        ),
                TemplateHandler.Page.build().changeLandscape()
                        .setFontFamily("微软雅黑")
                        .setMargin("20pt")
                        .addBodyComponent(
                                TemplateHandler.Table.build().setId("ddd")
                                        .setMinRowHeight("30pt")
                                        .setVerticalStyle("center")
                                        .setBody(
                                                TemplateHandler.Table.Body.build().addRow(rowList)
                                        )
                        )
        ).addBookmark(bookmark);
        // 转换pdf
        document.transform(outputPath);
    }
}
