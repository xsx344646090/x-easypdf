package wiki.xsx.core.pdf.template.template.datasource;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

/**
 * pdf模板-thymeleaf数据源
 *
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
@Setter
@Accessors(chain = true)
public class XEasyPdfTemplateThymeleafDataSource implements XEasyPdfTemplateDataSource {
    /**
     * 模板路径（绝对路径）
     */
    private String templatePath;
    /**
     * 模板数据
     */
    private Map<String, Object> templateData;
    /**
     * 所在区域
     */
    private Locale locale = Locale.SIMPLIFIED_CHINESE;

    /**
     * 获取数据源读取器
     *
     * @return 返回数据源读取器
     */
    @SneakyThrows
    @Override
    public Reader getSourceReader() {
        // 获取输入流
        try (InputStream inputStream = this.getInputStream()) {
            // 创建数据源
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
    }

    /**
     * 转换
     *
     * @param fopFactory   fop工厂
     * @param foAgent      fo代理
     * @param outputStream 输出流
     */
    @SneakyThrows
    @Override
    public void transform(FopFactory fopFactory, FOUserAgent foAgent, OutputStream outputStream) {
        this.saxTransform(fopFactory, foAgent, outputStream);
    }

    /**
     * 获取输入流
     *
     * @return 返回输入流
     */
    private InputStream getInputStream() {
        // 如果模板数据不为空，则处理模板
        if (this.isNotEmptyTemplateData()) {
            // 定义模板
            String template;
            try {
                // 获取模板内容
                template = new String(Files.readAllBytes(Paths.get(this.templatePath)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                // 提示错误信息
                throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
            }
            // 创建模板引擎
            TemplateEngine templateEngine = new TemplateEngine();
            // 创建字符串解析器
            StringTemplateResolver resolver = new StringTemplateResolver();
            // 设置模板模式
            resolver.setTemplateMode(TemplateMode.XML);
            // 设置模板解析器
            templateEngine.setTemplateResolver(resolver);
            // 创建上下文
            Context context = new Context();
            // 设置本地化策略
            context.setLocale(this.locale);
            // 设置变量数据
            context.setVariables(this.templateData);
            // 处理模板并返回
            return new BufferedInputStream(new ByteArrayInputStream(templateEngine.process(template, context).getBytes(StandardCharsets.UTF_8)));
        } else {
            try {
                return Files.newInputStream(Paths.get(this.templatePath));
            } catch (Exception e) {
                // 提示错误信息
                throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
            }
        }
    }

    /**
     * 模板数据是否非空
     *
     * @return 返回布尔值，是为true，否为false
     */
    private boolean isNotEmptyTemplateData() {
        return this.templateData != null && !this.templateData.isEmpty();
    }
}
