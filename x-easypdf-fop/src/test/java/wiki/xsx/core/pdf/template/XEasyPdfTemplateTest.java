package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;
import wiki.xsx.core.pdf.template.template.XEasyPdfTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
public class XEasyPdfTemplateTest {

    @Test
    public void testFont() {
        // 定义字体路径
        String fontPath = "C:\\Windows\\Fonts\\msyh.ttf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\myFont.xml";
        // 转换字体
        XEasyPdfTemplateHandler.Font.build()
                .setFontPath(fontPath)
                .setOutputPath(outputPath)
                .transform();
    }

    @Test
    public void testThymeleaf() {
        for (int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            this.testThymeleaf2();
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - begin));
        }
    }

    @Test
    public void testThymeleaf2() {
        // 定义fop配置文件路径
        String configPath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\thymeleaf\\template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 设置值
        data.put("data", "hello world");
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath).setTemplateData(data))
                .transform(outputPath);
    }

    @Test
    public void testThymeleaf3() {
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\thymeleaf\\template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }

    @Test
    public void testXml() {
        for (int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            this.testXml2();
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - begin));
        }
    }

    @Test
    public void testXml2() {
        // 定义fop配置文件路径
        String configPath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\xml\\template.fo";
        // 定义xml数据路径
        String xmlPath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\xml\\data.xml";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\XML.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                // 设置配置文件
                .setConfigPath(configPath)
                // 设置XML数据源
                .setDataSource(XEasyPdfTemplateHandler.DataSource.XML.build().setTemplatePath(templatePath).setXmlPath(xmlPath))
                // 转换
                .transform(outputPath);
    }

    @Test
    public void testBarcode() {
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\x-easypdf\\gitee\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\barcode.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\barcode.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }

    @Test
    public void testBarcode2() {
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\x-easypdf\\gitee\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\barcode.fo";
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
    public void testBarcode3() {
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\x-easypdf\\gitee\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\barcode.fo";
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
}
