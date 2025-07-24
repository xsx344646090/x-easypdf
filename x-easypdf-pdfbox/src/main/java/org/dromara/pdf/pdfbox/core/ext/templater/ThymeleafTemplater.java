package org.dromara.pdf.pdfbox.core.ext.templater;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * thymeleaf模板引擎
 *
 * @author xsx
 * @date 2025/7/21
 * @see <a href="https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html">官方文档</a>
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
public class ThymeleafTemplater extends AbstractHtmlTemplater {

    /**
     * 文件路径模板引擎
     */
    protected static final TemplateEngine FILEPATH_TEMPLATE_ENGINE = initTemplateEngine(new FileTemplateResolver());
    /**
     * 类路径模板引擎
     */
    protected static final TemplateEngine CLASSPATH_TEMPLATE_ENGINE = initTemplateEngine(new ClassLoaderTemplateResolver());
    /**
     * 所在区域
     */
    protected Locale locale = Locale.SIMPLIFIED_CHINESE;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public ThymeleafTemplater(Document document) {
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
        // 创建上下文
        Context context = new Context();
        // 设置本地化策略
        context.setLocale(this.locale);
        // 设置变量数据
        context.setVariables(this.templateData);
        // 获取模板全路径
        String template = String.join("/", this.templatePath, this.templateName);
        // 非资源路径
        if (Thread.currentThread().getContextClassLoader().getResource(template) == null) {
            // 使用文件路径模板引擎
            FILEPATH_TEMPLATE_ENGINE.process(template, context, writer);
        } else {
            // 使用类路径模板引擎
            CLASSPATH_TEMPLATE_ENGINE.process(template, context, writer);
        }
    }

    /**
     * 初始化模板引擎
     *
     * @param resolver 解析器
     * @return 返回模板引擎
     */
    protected static TemplateEngine initTemplateEngine(AbstractConfigurableTemplateResolver resolver) {
        // 创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        // 设置字符编码
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 设置模板模式
        resolver.setTemplateMode(TemplateMode.HTML);
        // 设置模板解析器
        templateEngine.setTemplateResolver(resolver);
        // 返回模板引擎
        return templateEngine;
    }
}
