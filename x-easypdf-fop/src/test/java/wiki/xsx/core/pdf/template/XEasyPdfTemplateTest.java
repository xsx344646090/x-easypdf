package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

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
        String templatePath = "classpath:wiki/xsx/core/pdf/template/thymeleaf/template2.html";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 设置值
        data.put("data", "hello world");
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                // .setConfigPath(configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath).setTemplateData(data))
                .transform(outputPath);
    }

    @Test
    public void testThymeleaf3() {
        // 定义xsl-fo模板路径
        String templatePath = "E:\\pdf\\test\\fo\\space-after-1\\space-after-1.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 设置值
        data.put("createTime", "创建时间：2022-10-27 15:35:00");
        data.put("printTime", "打印时间：2022-10-27 15:36:00");
        data.put("printUser", "打印人：x-easypdf");
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
        String templatePath = "H:\\java_workspace\\my\\mutil\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\xml\\template.html";
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
    public void testJte() {
        // 定义fop配置文件路径
        String configPath = "E:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath1 = "E:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\jte\\template.html";
        // 定义xsl-fo模板路径
        String templatePath2 = "E:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\barcode\\barcode.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Jte.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 定义数据list
        List<String> list = new ArrayList<>(2);
        list.add("hello");
        list.add("world");
        // 设置值
        data.put("list", list);
        data.put("str", "hello world");
        List<CompletableFuture<?>> tasks = new ArrayList<>(20);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 添加任务
            tasks.add(
                    CompletableFuture.runAsync(
                            // 转换pdf
                            () -> XEasyPdfTemplateHandler.Template.build()
                                    .setConfigPath(configPath)
                                    .setDataSource(
                                            XEasyPdfTemplateHandler.DataSource.Jte.build().setTemplatePath(index%2==0?templatePath1:templatePath2).setTemplateData(data)
                                    ).transform(outputPath + index)
                    )
            );
        }
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

    @Test
    public void testJte2() {
        // 定义fop配置文件路径
        String configPath = "/wiki/xsx/core/pdf/template/fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "wiki/xsx/core/pdf/template/jte/template.jte";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Jte.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        List<String> list = new ArrayList<>(2);
        list.add("hello");
        list.add("world");
        // 设置值
        data.put("list", list);
        data.put("str", "hello world");
        XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(configPath)
                .setDataSource(
                        XEasyPdfTemplateHandler.DataSource.Jte.build().setTemplatePath(templatePath).setTemplateData(data)
                ).transform(outputPath);
    }

    @Test
    public void testFreemarker() {
        // 定义fop配置文件路径
        String configPath = "E:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\fop.xconf";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Freemarker.pdf";
        // 设置模板路径
        XEasyPdfTemplateHandler.DataSource.Freemarker.setTemplatePath("E:\\workspace\\my\\x-easypdf\\x-easypdf-fop\\src\\test\\resources\\wiki\\xsx\\core\\pdf\\template\\freemarker");
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 定义数据list
        List<String> list = new ArrayList<>(2);
        list.add("hello");
        list.add("world");
        // 设置值
        data.put("list", list);
        data.put("str", "hello world");
        List<CompletableFuture<?>> tasks = new ArrayList<>(20);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 添加任务
            tasks.add(
                    CompletableFuture.runAsync(
                            // 转换pdf
                            () -> XEasyPdfTemplateHandler.Template.build()
                                    .setConfigPath(configPath)
                                    .setDataSource(
                                            XEasyPdfTemplateHandler.DataSource.Freemarker.build().setTemplateName("template.html").setTemplateData(data)
                                    ).transform(outputPath + index)
                    )
            );
        }
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }
}
