package wiki.xsx.core.pdf.template.template.datasource;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

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
     * 模板引擎
     */
    private static final TemplateEngine TEMPLATE_ENGINE = initTemplateEngine();
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
        return new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8);
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
     * 初始化模板引擎
     *
     * @return 返回模板引擎
     */
    private static TemplateEngine initTemplateEngine() {
        // 创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        // 创建文件解析器
        FileTemplateResolver resolver = new FileTemplateResolver();
        // 设置字符编码
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 设置模板模式
        resolver.setTemplateMode(TemplateMode.XML);
        // 设置模板解析器
        templateEngine.setTemplateResolver(resolver);
        // 返回模板引擎
        return templateEngine;
    }

    /**
     * 获取输入流
     *
     * @return 返回输入流
     */
    @SneakyThrows
    private InputStream getInputStream() {
        // 如果模板数据不为空，则处理模板，否则直接加载模板
        return this.isNotEmptyTemplateData()?this.processTemplate():this.loadTemplateInputStream();
    }

    /**
     * 加载模板输入流
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    private InputStream loadTemplateInputStream() {
        try {
            // 从资源路径加载模板
            InputStream inputStream = this.getClass().getResourceAsStream(this.templatePath);
            // 如果不为空，则返回，否则从绝对路径加载模板
            return inputStream != null ? inputStream : Files.newInputStream(Paths.get(this.templatePath));
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
        }
    }

    /**
     * 处理模板
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    private InputStream processTemplate() {
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
            // 处理模板
            TEMPLATE_ENGINE.process(this.templatePath, context, writer);
            // 返回输入流
            return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
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
