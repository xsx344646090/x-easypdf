package org.dromara.pdf.fop.core.datasource;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 * pdf模板-thymeleaf数据源
 *
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
@EqualsAndHashCode(callSuper = true)
public class ThymeleafDataSource extends AbstractDataSource {

    /**
     * 文件路径模板引擎
     */
    private static final TemplateEngine FILEPATH_TEMPLATE_ENGINE = initTemplateEngine(new FileTemplateResolver());
    /**
     * 类路径模板引擎
     */
    private static final TemplateEngine CLASSPATH_TEMPLATE_ENGINE = initTemplateEngine(new ClassLoaderTemplateResolver());
    /**
     * 所在区域
     */
    private Locale locale = Locale.SIMPLIFIED_CHINESE;

    /**
     * 设置模板路径
     * <p>注：资源路径以“classpath:”作为前缀</p>
     *
     * @param templatePath 模板路径
     * @return 返回thymeleaf数据源
     */
    public ThymeleafDataSource setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    /**
     * 设置模板数据
     *
     * @param templateData 模板数据
     * @return 返回thymeleaf数据源
     */
    public ThymeleafDataSource setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
        return this;
    }

    /**
     * 设置所在区域
     *
     * @param locale 所在区域
     * @return 返回thymeleaf数据源
     */
    public ThymeleafDataSource setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    /**
     * 处理模板
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    protected InputStream processTemplate() {
        // 创建上下文
        Context context = new Context();
        // 设置本地化策略
        context.setLocale(this.locale);
        // 设置变量数据
        context.setVariables(this.templateData);
        try (
                // 创建输出流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);
                // 创建写入器
                Writer writer = new OutputStreamWriter(outputStream)
        ) {
            // 如果模板路径为非资源路径，则使用文件路径模板引擎
            if (Thread.currentThread().getContextClassLoader().getResource(this.templatePath) == null) {
                // 使用文件路径模板引擎处理
                FILEPATH_TEMPLATE_ENGINE.process(this.templatePath, context, writer);
            }
            // 否则使用类路径模板引擎
            else {
                // 使用类路径模板引擎处理
                CLASSPATH_TEMPLATE_ENGINE.process(this.templatePath, context, writer);
            }
            // 返回输入流
            return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        }
    }

    /**
     * 初始化模板引擎
     *
     * @param resolver 解析器
     * @return 返回模板引擎
     */
    private static TemplateEngine initTemplateEngine(AbstractConfigurableTemplateResolver resolver) {
        // 创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        // 设置字符编码
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 设置模板模式
        resolver.setTemplateMode(TemplateMode.XML);
        // 设置模板解析器
        templateEngine.setTemplateResolver(resolver);
        // 返回模板引擎
        return templateEngine;
    }
}
