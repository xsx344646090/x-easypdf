package wiki.xsx.core.pdf;

import org.junit.Test;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfDocumentAnalyzer;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;

/**
 * @author xsx
 * @date 2022/6/26
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
public class HelloWorld {

    @Test
    public void test() {
        // 开启系统字体映射
        // XEasyPdfHandler.Font.enableSystemFontMapping();
        // // 构建pdf
        // XEasyPdfHandler.Document.build(XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("Hello World"))).save("E:\\pdf\\hello-world.pdf").close();

        XEasyPdfDocument document = XEasyPdfHandler.Document.load("E:\\pdf\\hello-world2.pdf");
        XEasyPdfDocumentAnalyzer analyzer = document.analyzer();
        XEasyPdfPage xEasyPdfPage = document.getPageList().get(0);
        XEasyPdfHeader header = XEasyPdfHandler.Header.build().addComponent(
                XEasyPdfHandler.Text.build("test")
        );
        xEasyPdfPage.setHeader(header);
        document.save("E:\\pdf\\hello-world3.pdf").close();
    }

    @Test
    public void test2() {
        XEasyPdfHandler.Document.build(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Table.build(
                                XEasyPdfHandler.Table.Row.build(
                                        XEasyPdfHandler.Table.Row.Cell.build(200F, 60F).addContent(
                                                XEasyPdfHandler.Table.build(
                                                        XEasyPdfHandler.Table.Row.build(
                                                                XEasyPdfHandler.Table.Row.Cell.build(200F).addContent(
                                                                        XEasyPdfHandler.Text.build("Hello World-row1Hello World-row1")
                                                                )
                                                        ),
                                                        XEasyPdfHandler.Table.Row.build(
                                                                XEasyPdfHandler.Table.Row.Cell.build(200F).addContent(
                                                                        XEasyPdfHandler.Text.build("Hello World-row2")
                                                                )
                                                        )
                                                ).disableBorder()
                                        )
                                )
                        )
                )
        ).save("E:\\pdf\\test.pdf").close();
    }
}
