package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.doc.bookmark.XEasyPdfTemplateBookmark;
import wiki.xsx.core.pdf.template.doc.component.text.XEasyPdfTemplateText;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePage;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/11/11
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
public class XEasyPdfTemplateWatermarkTest {

    @Test
    public void test() {
        // 定义输出路径
        String pdfPath = "E:\\pdf\\test\\fo\\template-xsl-fo.pdf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-xsl-fo.fo";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().addPage(
                XEasyPdfTemplateHandler.Page.build().setStartWidth("21cm").setStartWatermark(
                        XEasyPdfTemplateHandler.Watermark.build().setId("test").setText("test").setWidth("100pt").setHeight("50pt")
                ).addBodyComponent(
                        XEasyPdfTemplateHandler.TextExtend.build().setFontFamily("微软雅黑").setFontSize("30pt").addText(
                                XEasyPdfTemplateHandler.Text.build().setText("hello"),
                                XEasyPdfTemplateHandler.Text.build().setText("上标").setFontSize("12pt").setVerticalStyle("top"),
                                XEasyPdfTemplateHandler.Text.build().setText(", world")
                        )
                )
        );
        XEasyPdfTemplateHandler.Template.build().setDataSource(
                XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(document)
        ).transform(pdfPath);
    }

    @Test
    public void test2() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build();
        // 创建页面（空白页）
        XEasyPdfTemplatePage page = XEasyPdfTemplateHandler.Page.build();
        // 定义书签
        XEasyPdfTemplateBookmark bookmark = XEasyPdfTemplateHandler.Bookmark.build()
                // 设置标题
                .setTitle("目录")
                // 设置内部地址（对应组件id）
                .setInternalDestination("title");
        // 创建标题
        XEasyPdfTemplateText title = XEasyPdfTemplateHandler.Text.build()
                // 设置id
                .setId("title")
                // 设置文本
                .setText("贵阳市简介")
                // 设置字体
                .setFontFamily("微软雅黑")
                // 设置字体大小
                .setFontSize("20pt");
        // 创建子书签
        XEasyPdfTemplateBookmark childBookmark = XEasyPdfTemplateHandler.Bookmark.build()
                // 设置标题
                .setTitle("贵阳市行政区划")
                // 设置内部地址（对应组件id）
                .setInternalDestination("text");
        // 添加子书签
        bookmark.addChild(childBookmark);
        // 创建内容
        XEasyPdfTemplateText text = XEasyPdfTemplateHandler.Text.build()
                // 设置id
                .setId("text")
                // 设置文本
                .setText("贵阳市行政区划")
                // 设置字体
                .setFontFamily("微软雅黑")
                // 设置字体大小
                .setFontSize("16pt");
        // 添加文本
        page.addBodyComponent(title, text);
        // 添加页面
        document.addPage(page);
        // 添加书签
        document.addBookmark(bookmark);
        // 转换pdf
        document.transform(outputPath);
    }
}
