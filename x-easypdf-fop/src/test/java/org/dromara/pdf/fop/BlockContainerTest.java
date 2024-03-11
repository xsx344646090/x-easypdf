package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.core.doc.component.block.BlockContainer;
import org.dromara.pdf.fop.core.doc.component.image.Image;
import org.dromara.pdf.fop.core.doc.component.text.Text;
import org.dromara.pdf.fop.core.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/8/6
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
 */
public class BlockContainerTest extends BaseTest {

    @Test
    public void blockContainerTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\blockContainer\\blockContainerTest.pdf";
            // 创建文档
            Document document = TemplateHandler.Document.build();
            // 创建页面
            Page page = TemplateHandler.Page.build().setFontFamily("微软雅黑");
            // 创建普通文本
            Text leftText = TemplateHandler.Text.build().setText("左侧文本");
            // 创建普通文本
            Text rightText = TemplateHandler.Text.build().setText("右侧文本");
            // 创建图像
            Image image = TemplateHandler.Image.build()
                    // 设置图像路径（绝对路径）
                    .setPath("/E:\\PDF\\fop\\watermark.png")
                    // 设置图像宽度
                    .setWidth("150px")
                    // 设置图像高度
                    .setHeight("150px");
            // 创建容器
            BlockContainer container = TemplateHandler.BlockContainer.build();
            // 添加容器组件
            container.addComponent(leftText, image, rightText);
            // 添加页面主体组件
            page.addBodyComponent(container);
            // 添加页面
            document.addPage(page);
            // 转换pdf
            document.transform(outputPath);
        });
    }
}
