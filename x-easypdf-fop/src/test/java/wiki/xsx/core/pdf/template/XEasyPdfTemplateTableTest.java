package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
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
                                                        XEasyPdfTemplateHandler.Text.build().setText("HELLO").setHorizontalStyle(XEasyPdfTemplatePositionStyle.HORIZONTAL_CENTER)
                                                ),
                                                XEasyPdfTemplateHandler.Table.Cell.build().setComponent(
                                                        XEasyPdfTemplateHandler.Text.build().setText("WORLD").setHorizontalStyle(XEasyPdfTemplatePositionStyle.HORIZONTAL_CENTER)
                                                )
                                        )
                                )
                        )
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }
}
