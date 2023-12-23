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
public class ImageTest {

    @Test
    public void testImage() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        Document document = TemplateHandler.Document.build();
        // 创建页面
        Page page = TemplateHandler.Page.build();
        // 创建图像
        Image image = TemplateHandler.Image.build()
                // 设置图像路径（绝对路径）
                .setPath("/E:\\pdf\\test\\fo\\test.svg")
                // 设置图像宽度
                .setWidth("150px")
                // 设置图像高度
                .setHeight("150px")
                // 设置水平居中
                .setHorizontalStyle("center");
        // 添加图像
        page.addBodyComponent(image);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testSvg() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-svg.pdf";
        // 转换pdf
        Document document = TemplateHandler.Document.build().addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.Image.build()
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

    @Test
    public void test100Image() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\t\\template-image-100";
        for (int k = 0; k < 100; k++) {
            long begin = System.currentTimeMillis();
            List<Component> imageList = new ArrayList<>(100);
            for (int i = 0; i < 20; i++) {
                imageList.add(
                        TemplateHandler.Image.build()
                                // 绝对路径
                                .setPath("/E:\\pdf\\test\\fo\\test.jpg")
                                .setWidth("21cm")
                                .setHeight("29.7cm")
                );
            }
            // 转换pdf
            Document document = TemplateHandler.Document.build().addPage(
                    TemplateHandler.Page.build().addBodyComponent(imageList)
            );
            // 转换pdf
            document.transform(outputPath+k+".pdf");
            long end = System.currentTimeMillis();
            System.out.printf("第%s次，耗时：%s\n", k, (end-begin));
        }
    }
}
