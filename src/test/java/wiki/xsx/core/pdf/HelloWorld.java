package wiki.xsx.core.pdf;

import org.junit.Test;
import wiki.xsx.core.pdf.component.text.XEasypdfTextRenderingMode;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

/**
 * @author xsx
 * @date 2022/6/26
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
public class HelloWorld {

    @Test
    public void test() {
        System.setProperty(XEasyPdfHandler.FontMappingPolicy.key(), XEasyPdfHandler.FontMappingPolicy.ALL.name());
        XEasyPdfHandler.Document.build(XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("Hello World"))).save("E:\\pdf\\hello-world.pdf").close();
    }

    @Test
    public void test2() {
        XEasyPdfHandler.Document.build(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("FILL").setFontSize(20F).setRadians(-10D),
                        XEasyPdfHandler.Text.build("FILL").setFontSize(20F).setRenderingMode(XEasypdfTextRenderingMode.FILL),
                        XEasyPdfHandler.Text.build("STROKE").setFontSize(20F).setRenderingMode(XEasypdfTextRenderingMode.STROKE),
                        XEasyPdfHandler.Text.build("FILL_STROKE").setFontSize(20F).setRenderingMode(XEasypdfTextRenderingMode.FILL_STROKE),
                        XEasyPdfHandler.Text.build("NEITHER").setFontSize(20F).setRenderingMode(XEasypdfTextRenderingMode.NEITHER)
                )
        ).save("E:\\pdf\\hello-world.pdf").close();
    }

    @Test
    public void test3() {
        XEasyPdfHandler.Document.load("E:\\pdf\\test.pdf").analyzer().finish();
    }
}
