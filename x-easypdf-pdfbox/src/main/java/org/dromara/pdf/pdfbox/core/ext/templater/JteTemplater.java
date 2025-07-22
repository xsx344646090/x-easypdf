package org.dromara.pdf.pdfbox.core.ext.templater;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.WriterOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.Writer;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jte模板引擎
 *
 * @author xsx
 * @date 2025/7/21
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class JteTemplater extends AbstractHtmlTemplater {
    /**
     * 引擎缓存
     */
    protected static final Map<String, TemplateEngine> ENGINE_CACHE = new ConcurrentHashMap<>(16);


    /**
     * 有参构造
     *
     * @param document 文档
     */
    public JteTemplater(Document document) {
        super(document);
    }

    /**
     * 处理
     *
     * @param writer 写入器
     */
    @SneakyThrows
    @Override
    protected void process(Writer writer) {
        // 获取模板全路径
        String template = String.join("/", this.templatePath, this.templateName);
        // 创建输出
        WriterOutput output = new WriterOutput(writer);
        // 渲染模板
        TemplateEngine engine = this.getEngine(template);
        // 非资源路径
        if (Thread.currentThread().getContextClassLoader().getResource(template) == null) {
            // 渲染模板
            engine.render(this.templateName, this.templateData, output);
        } else {
            // 渲染模板
            engine.render(template, this.templateData, output);
        }
    }

    /**
     * 初始化模板引擎
     *
     * @param template 模板
     * @return 返回模板引擎
     */
    protected TemplateEngine getEngine(String template) {
        // 返回模板引擎
        return ENGINE_CACHE.computeIfAbsent(template, this::initEngine);
    }

    /**
     * 初始化模板引擎
     *
     * @param template 模板
     * @return 返回模板引擎
     */
    protected TemplateEngine initEngine(String template) {
        // 定义解析器
        CodeResolver resolver;
        // 非资源路径
        if (Thread.currentThread().getContextClassLoader().getResource(template) == null) {
            // 使用目录解析器
            resolver = new DirectoryCodeResolver(Paths.get(template).getParent());
        } else {
            // 使用资源解析器
            resolver = new ResourceCodeResolver(".", Thread.currentThread().getContextClassLoader());
        }
        // 返回模板引擎
        return TemplateEngine.create(resolver, ContentType.Html);
    }
}
