package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.dromara.pdf.fop.core.doc.component.image.Image;
import org.dromara.pdf.fop.core.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
public class ImageTest extends BaseTest {

    @Test
    public void pngTest() {
        this.test(() -> {
            for (int i = 0; i < 10; i++) {
                // 定义输出路径
                String outputPath = "E:\\PDF\\fop\\image\\pngTest"+i+".pdf";
                // 创建文档
                Document document = TemplateHandler.Document.build();
                // 创建页面
                Page page = TemplateHandler.Page.build();
                // 创建图像
                Image image = TemplateHandler.Image.build()
                        // 设置图像路径（绝对路径）
                        // .setPath("/E:\\PDF\\fop\\test.png")
                        .setPath(" https://down-cdn.dingtalk.com/ddmedia/iAELAqNwbmcDBgTNA3QFzQKIBtoAI4QBpCErfQcCqgylyrA7dF4N8tMDzwAAAY70KUwNBM4CmXdbBwAIAA1.png")
                        // 设置图像宽度
                        .setWidth("150px")
                        // 设置图像高度
                        .setHeight("150px")
                        // 设置缩放类型
                        .setScaling("non-uniform")
                        // 设置水平居中
                        .setHorizontalStyle("center");
                // 添加图像
                page.addBodyComponent(image);
                // 添加页面
                document.addPage(page);
                // 转换pdf
                document.transform(outputPath);
            }
        });
    }

    @Test
    public void svgTest() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\image\\svgTest.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().setBodyBackgroundImage(
                            "org/dromara/pdf/fop/svg/test.svg"
                    )
            );
            // 转换pdf
            document.transform(outputPath);
        });
    }

    @Test
    public void test100Image() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\image\\template-image-";
            for (int k = 0; k < 10; k++) {
                String finalK = String.valueOf(k);
                this.test(() -> {
                    List<Component> imageList = new ArrayList<>(20);
                    for (int i = 0; i < 20; i++) {
                        imageList.add(
                                TemplateHandler.Image.build()
                                        // 绝对路径
                                        .setPath("/E:\\PDF\\fop\\test.jpg")
                                        .setWidth("21cm")
                                        .setHeight("29.7cm")
                        );
                    }
                    // 转换pdf
                    Document document = TemplateHandler.Document.build().addPage(
                            TemplateHandler.Page.build().addBodyComponent(imageList)
                    );
                    // 转换pdf
                    document.transform(String.join("", outputPath, finalK, ".pdf"));
                }, String.format("第%s次", finalK));
            }
        });
    }
}
