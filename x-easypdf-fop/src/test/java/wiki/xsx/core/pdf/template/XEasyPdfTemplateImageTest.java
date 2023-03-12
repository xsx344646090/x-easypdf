package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.doc.component.image.XEasyPdfTemplateImage;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePage;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

import java.util.ArrayList;
import java.util.List;

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
public class XEasyPdfTemplateImageTest {

    @Test
    public void testImage() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build();
        // 创建页面
        XEasyPdfTemplatePage page = XEasyPdfTemplateHandler.Page.build();
        // 创建图像
        XEasyPdfTemplateImage image = XEasyPdfTemplateHandler.Image.build()
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

    @Test
    public void test100Image() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\t\\template-image-100";
        for (int k = 0; k < 100; k++) {
            long begin = System.currentTimeMillis();
            List<XEasyPdfTemplateComponent> imageList = new ArrayList<>(100);
            for (int i = 0; i < 20; i++) {
                imageList.add(
                        XEasyPdfTemplateHandler.Image.build()
                                // 绝对路径
                                .setPath("/E:\\pdf\\test\\fo\\test.jpg")
                                .setWidth("21cm")
                                .setHeight("29.7cm")
                );
            }
            // 转换pdf
            XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().addPage(
                    XEasyPdfTemplateHandler.Page.build().addBodyComponent(imageList)
            );
            // 转换pdf
            document.transform(outputPath+k+".pdf");
            long end = System.currentTimeMillis();
            System.out.printf("第%s次，耗时：%s\n", k, (end-begin));
        }
    }
}
