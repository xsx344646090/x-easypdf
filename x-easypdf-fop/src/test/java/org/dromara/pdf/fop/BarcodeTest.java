package org.dromara.pdf.fop;

import org.dromara.pdf.fop.doc.Document;
import org.dromara.pdf.fop.doc.component.barcode.Barcode;
import org.dromara.pdf.fop.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author xsx
 * @date 2022/10/18
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
public class BarcodeTest {

    @Test
    public void testBarcodeForXML() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.html";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\barcode.pdf";
        // 转换pdf
        TemplateHandler.Template.build()
                .setDataSource(TemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }

    @Test
    public void testBarcodeForXML2() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.html";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\";
        // 构建pdf模板
        Template pdfTemplate = TemplateHandler.Template.build().setDataSource(TemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath));
        // 循环转换
        for (int i = 0; i < 20; i++) {
            // 转换pdf
            pdfTemplate.transform(outputPath + "barcode" + i + ".pdf");
        }
    }

    @Test
    public void testBarcodeForXML3() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.html";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\";
        // 定义任务列表
        List<CompletableFuture<Void>> tasks = new ArrayList<>(20);
        // 循环添加任务
        for (int i = 0; i < 50; i++) {
            // 定义索引
            int index = i;
            // 添加任务
            tasks.add(
                    CompletableFuture.runAsync(
                            // 构建pdf模板
                            () -> TemplateHandler.Template.build()
                                    .setDataSource(TemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                                    .transform(outputPath + "barcode" + index + ".pdf")
                    )
            );
        }
        // 执行任务
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

    @Test
    public void testBarcodeForDocument() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        Document document = TemplateHandler.Document.build();
        // 创建页面
        Page page = TemplateHandler.Page.build();
        // 创建条形码
        Barcode codabar = TemplateHandler.Barcode.build()
                // 设置类型
                .setType("codabar")
                // 设置宽度
                .setWidth("150px")
                // 设置高度
                .setHeight("50px")
                // 设置内容
                .setContent("11223344")
                // 设置水平居中
                .setHorizontalStyle("center");
        // 创建二维码
        Barcode qrCode = TemplateHandler.Barcode.build()
                // 设置类型
                .setType("qr_code")
                // 设置宽度
                .setWidth("150px")
                // 设置高度
                .setHeight("150px")
                // 设置内容
                .setContent("https://www.x-easypdf.cn")
                // 设置显示文本
                .setWords("https://www.x-easypdf.cn")
                // 设置水平居中
                .setHorizontalStyle("center");
        // 添加条码
        page.addBodyComponent(codabar, qrCode);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }
}
