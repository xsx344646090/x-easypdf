package org.dromara.pdf.fop;

import org.junit.Test;
import org.dromara.pdf.fop.doc.XEasyPdfTemplateDocument;
import org.dromara.pdf.fop.doc.component.block.XEasyPdfTemplateBlockContainer;
import org.dromara.pdf.fop.doc.component.image.XEasyPdfTemplateImage;
import org.dromara.pdf.fop.doc.component.text.XEasyPdfTemplateText;
import org.dromara.pdf.fop.doc.page.XEasyPdfTemplatePage;
import org.dromara.pdf.fop.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/8/6
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
public class XEasyPdfTemplateBlockContainerTest {
    @Test
    public void test() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build();
        // 创建页面
        XEasyPdfTemplatePage page = XEasyPdfTemplateHandler.Page.build().setFontFamily("微软雅黑");
        // 创建普通文本
        XEasyPdfTemplateText leftText = XEasyPdfTemplateHandler.Text.build().setText("左侧文本");
        // 创建普通文本
        XEasyPdfTemplateText rightText = XEasyPdfTemplateHandler.Text.build().setText("右侧文本");
        // 创建图像
        XEasyPdfTemplateImage image = XEasyPdfTemplateHandler.Image.build()
                // 设置图像路径（绝对路径）
                .setPath("/E:\\pdf\\test\\fo\\test.svg")
                // 设置图像宽度
                .setWidth("150px")
                // 设置图像高度
                .setHeight("150px");
        // 创建容器
        XEasyPdfTemplateBlockContainer container = XEasyPdfTemplateHandler.BlockContainer.build();
        // 添加容器组件
        container.addComponent(leftText, image, rightText);
        // 添加页面主体组件
        page.addBodyComponent(container);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }
}
