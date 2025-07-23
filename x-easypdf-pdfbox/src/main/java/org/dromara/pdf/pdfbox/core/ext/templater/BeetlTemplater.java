package org.dromara.pdf.pdfbox.core.ext.templater;

import lombok.SneakyThrows;
import org.apache.pdfbox.io.IOUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * beetl模板引擎
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
public class BeetlTemplater extends AbstractHtmlTemplater {

    /**
     * 模板引擎
     */
    protected static final GroupTemplate ENGINE = initEngine(new StringTemplateResourceLoader());

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public BeetlTemplater(Document document) {
        super(document);
    }

    /**
     * 初始化模板引擎
     *
     * @return 返回模板引擎
     */
    @SneakyThrows
    protected static GroupTemplate initEngine(ResourceLoader<?> loader) {
        return new GroupTemplate(loader, Configuration.defaultConfiguration());
    }

    /**
     * 处理
     *
     * @param writer 写入器
     */
    @SneakyThrows
    @Override
    protected void process(Writer writer) {
        // 定义字节数
        byte[] bytes;
        // 获取模板全路径
        String templatePath = String.join("/", this.templatePath, this.templateName);
        // 获取模板url
        URL url = Thread.currentThread().getContextClassLoader().getResource(templatePath);
        // 非资源路径
        if (Objects.isNull(url)) {
            bytes = Files.readAllBytes(Paths.get(templatePath));
        } else {
            // 读取模板
            try (InputStream inputStream = url.openStream()) {
                bytes = IOUtils.toByteArray(inputStream);
            }
        }
        // 获取模板
        Template template = ENGINE.getTemplate(new String(bytes, StandardCharsets.UTF_8));
        // 绑定数据
        template.binding(this.templateData);
        // 渲染
        template.renderTo(writer);
    }
}
