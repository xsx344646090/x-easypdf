package org.dromara.pdf.pdfbox;

import org.junit.Test;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;

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
        XEasyPdfHandler.Font.enableSystemFontMapping();
        // 构建pdf
        XEasyPdfHandler.Document.build(XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("Hello World"))).save("E:\\pdf\\hello-world.pdf").close();
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
