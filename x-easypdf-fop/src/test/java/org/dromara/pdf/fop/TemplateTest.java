package org.dromara.pdf.fop;

import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
public class TemplateTest extends BaseTest {
    
    @Test
    public void testFont() {
        this.test(() -> {
            // 定义字体路径
            // String fontPath = "C:\\Windows\\Fonts\\simsun.ttc";
            String fontPath = "C:\\Windows\\Fonts\\SIMLI.TTF";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\myFont.xml";
            // 转换字体
            TemplateHandler.Font.build()
                    .setFontPath(fontPath)
                    .setOutputPath(outputPath)
                    // .setTtcName("SimSun")
                    .transform();
        });
    }
    
    @Test
    public void testThymeleaf() {
        this.test(() -> {
            for (int i = 0; i < 10; i++) {
                this.testThymeleaf2();
            }
        });
    }
    
    @Test
    public void testThymeleaf2() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/thymeleaf/template.html";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Thymeleaf.pdf";
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            // 设置值
            data.put("data", "hello world");
            data.put("signatureList", Arrays.asList(Collections.singletonMap("nameUrl", "/E:\\PDF\\fop\\test.png")));
            // 转换pdf
            TemplateHandler.Template.build()
                    .setConfigPath(configPath)
                    .setDataSource(
                            TemplateHandler.DataSource.Thymeleaf.build()
                                    .setTemplatePath(templatePath)
                                    .setTemplateData(data)
                    )
                    .transform(outputPath);
        });
    }
    
    @Test
    public void testThymeleaf3() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "C:\\Users\\xsx\\Downloads\\fop.xconf";
            // 定义xsl-fo模板路径
            // String templatePath = "org/dromara/pdf/fop/thymeleaf/template2.html";
            String templatePath = "org/dromara/pdf/fop/freemarker/template3.ftl";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Thymeleaf.pdf";
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            // 设置值
            data.put("createTime", "创建时间：2022-10-27 15:35:00");
            data.put("printTime", "打印时间：2022-10-27 15:36:00");
            data.put("printUser", "打印人：x-easypdf");
            data.put("data", "hello world");
            // 转换pdf
            TemplateHandler.Template.build().setDataSource(
                    TemplateHandler.DataSource.Thymeleaf.build()
                            .setTemplatePath(templatePath)
                    // .setTemplateData(data)
            ).transform(outputPath);
        });
    }
    
    @Test
    public void testThymeleafTotalPage() {
        this.test(() -> {
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/thymeleaf/template2.html";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Thymeleaf.pdf";
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            // 设置值
            data.put("createTime", "创建时间：2022-10-27 15:35:00");
            data.put("printTime", "打印时间：2022-10-27 15:36:00");
            data.put("printUser", "打印人：x-easypdf");
            data.put("data", "hello world");
            // 获取总页数
            Integer totalPage = TemplateHandler.Template.build().setDataSource(
                    TemplateHandler.DataSource.Thymeleaf.build()
                            .setTemplatePath(templatePath)
                            .setTemplateData(data)
            ).getTotalPage();
            log.info("Total page: " + totalPage);
        });
    }
    
    @Test
    public void testXml() {
        this.test(() -> {
            for (int i = 0; i < 10; i++) {
                this.testXml2();
            }
        });
        
    }
    
    @Test
    public void testXml2() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/xml/template.fo";
            // 定义xml数据路径
            String xmlPath = "org/dromara/pdf/fop/xml/data.xml";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\XML.pdf";
            // 转换pdf
            TemplateHandler.Template.build()
                    // 设置配置文件
                    .setConfigPath(configPath)
                    // 设置XML数据源
                    .setDataSource(
                            TemplateHandler.DataSource.XML.build()
                                    .setTemplatePath(templatePath)
                                    .setXmlPath(xmlPath)
                    )
                    // 转换
                    .transform(outputPath);
        });
    }
    
    @Test
    public void testXmlTotalPage() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/xml/template.fo";
            // 定义xml数据路径
            String xmlPath = "org/dromara/pdf/fop/xml/data.xml";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\XML.pdf";
            // 获取总页数
            Integer totalPage = TemplateHandler.Template.build()
                                        // 设置配置文件
                                        .setConfigPath(configPath)
                                        // 设置XML数据源
                                        .setDataSource(
                                                TemplateHandler.DataSource.XML.build()
                                                        .setTemplatePath(templatePath)
                                                        .setXmlPath(xmlPath)
                                        )
                                        // 获取总页数
                                        .getTotalPage();
            log.info("Total page: " + totalPage);
        });
    }
    
    @Test
    public void testJte() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath1 = "org/dromara/pdf/fop/jte/template.jte";
            // 定义xsl-fo模板路径
            String templatePath2 = "org/dromara/pdf/fop/barcode/barcode.fo";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Jte.pdf";
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
                                () -> TemplateHandler.Template.build()
                                              .setConfigPath(configPath)
                                              .setDataSource(
                                                      TemplateHandler.DataSource.Jte.build().setTemplatePath(index % 2 == 0 ? templatePath1 : templatePath2).setTemplateData(data)
                                              ).transform(outputPath + index)
                        )
                );
            }
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        });
    }
    
    @Test
    public void testJte2() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "org/dromara/pdf/fop/fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/jte/template.jte";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Jte.pdf";
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            List<String> list = new ArrayList<>(2);
            list.add("hello");
            list.add("world");
            // 设置值
            data.put("list", list);
            data.put("str", "hello world");
            TemplateHandler.Template.build()
                    .setConfigPath(configPath)
                    .setDataSource(
                            TemplateHandler.DataSource.Jte.build()
                                    .setTemplatePath(templatePath)
                                    .setTemplateData(data)
                    ).transform(outputPath);
        });
    }
    
    @Test
    public void testJteTotalPage() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "org/dromara/pdf/fop/fop.xconf";
            // 定义xsl-fo模板路径
            String templatePath = "org/dromara/pdf/fop/jte/template.jte";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Jte.pdf";
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            List<String> list = new ArrayList<>(2);
            list.add("hello");
            list.add("world");
            // 设置值
            data.put("list", list);
            data.put("str", "hello world");
            Integer totalPage = TemplateHandler.Template.build()
                                        .setConfigPath(configPath)
                                        .setDataSource(
                                                TemplateHandler.DataSource.Jte.build()
                                                        .setTemplatePath(templatePath)
                                                        .setTemplateData(data)
                                        ).getTotalPage();
            log.info("Total page: " + totalPage);
        });
    }
    
    @Test
    public void testFreemarker() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "org/dromara/pdf/fop/fop.xconf";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Freemarker.pdf";
            // 设置远程模板路径
            // TemplateHandler.DataSource.Freemarker.setTemplatePath("http://localhost:22222/temp/");
            // 设置模板路径
            TemplateHandler.DataSource.Freemarker.setTemplatePath("org/dromara/pdf/fop/freemarker");
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            // 定义数据list
            List<String> list = new ArrayList<>(2);
            list.add("hello");
            list.add("world");
            // 设置值
            data.put("list", list);
            data.put("str", "测试");
            TemplateHandler.Template.build()
                    .setConfigPath(configPath)
                    .setDataSource(
                            TemplateHandler.DataSource.Freemarker.build()
                                    .setTemplateName("template.fo")
                                    .setTemplateData(data)
                    ).transform(outputPath);
        });
    }
    
    @Test
    public void testFreemarkerTotalPage() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "org/dromara/pdf/fop/fop.xconf";
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\Freemarker.pdf";
            // 设置远程模板路径
            // TemplateHandler.DataSource.Freemarker.setTemplatePath("http://localhost:22222/temp/");
            // 设置模板路径
            TemplateHandler.DataSource.Freemarker.setTemplatePath("org/dromara/pdf/fop/freemarker");
            // 定义数据map
            Map<String, Object> data = new HashMap<>();
            // 定义数据list
            List<String> list = new ArrayList<>(2);
            list.add("hello");
            list.add("world");
            // 设置值
            data.put("list", list);
            data.put("str", "hello world");
            Integer totalPage = TemplateHandler.Template.build()
                                        .setConfigPath(configPath)
                                        .setDataSource(
                                                TemplateHandler.DataSource.Freemarker.build()
                                                        .setTemplateName("template.fo")
                                                        .setTemplateData(data)
                                        ).getTotalPage();
            log.info("Total page: " + totalPage);
        });
    }
}
