package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

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
public class XEasyPdfTemplateBarcodeTest {

    @Test
    public void testBarcodeForXML() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\barcode.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }

    @Test
    public void testBarcodeForXML2() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\";
        // 构建pdf模板
        XEasyPdfTemplate pdfTemplate = XEasyPdfTemplateHandler.Template.build().setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath));
        // 循环转换
        for (int i = 0; i < 20; i++) {
            // 转换pdf
            pdfTemplate.transform(outputPath + "barcode" + i + ".pdf");
        }
    }

    @Test
    public void testBarcodeForXML3() {
        // 定义xsl-fo模板路径
        String templatePath = "src/test/resources/wiki/xsx/core/pdf/template/barcode/template.fo";
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
                            () -> XEasyPdfTemplateHandler.Template.build()
                                    .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                                    .transform(outputPath + "barcode" + index + ".pdf")
                    )
            );
        }
        // 执行任务
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

    @Test
    public void testBarcodeForDocument() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-barcode.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Document.build()
                .setConfigPath(configPath)
                .addPage(
                        XEasyPdfTemplateHandler.Page.build().addBodyComponent(
                                XEasyPdfTemplateHandler.Barcode.build()
                                        .setType("CODABAR")
                                        .setWidth("150px")
                                        .setHeight("50px")
                                        .setContent("11223344"),
                                XEasyPdfTemplateHandler.Barcode.build()
                                        .setType("QR_CODE")
                                        .setWidth("100px")
                                        .setHeight("100px")
                                        .setContent("https://www.x-easypdf.cn")
                        )
                ).transform(outputPath);
    }
}
