package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/8/6
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
public class XEasyPdfTemplateImageTest {

    @Test
    public void testImage() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-image.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().setConfigPath(configPath).addPage(
                XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                        XEasyPdfTemplateHandler.Image.build()
                                // 绝对路径
                                .setPath("/E:\\pdf\\test\\fo\\test.png")
                                // .setPath("http://tiebapic.baidu.com/forum/w%3D580/sign=70bb6037c8b1cb133e693c1bed5556da/4c2955fbb2fb4316293171a265a4462308f7d384.jpg")
                                .setWidth("100px")
                                .setHeight("100px")
                                .setHorizontalStyle("center")
                                .enableBorder()
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testSvg() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-svg.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().addPage(
                XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                        XEasyPdfTemplateHandler.Image.build()
                                // 相对路径
                                .setPath("src/test/resources/wiki/xsx/core/pdf/template/svg/test.svg")
                                .setWidth("100pt")
                                .setHeight("100pt")
                                .setHorizontalStyle("center")
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }
}
