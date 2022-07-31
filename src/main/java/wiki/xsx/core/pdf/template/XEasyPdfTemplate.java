package wiki.xsx.core.pdf.template;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.apache.xmlgraphics.util.MimeConstants;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

/**
 * pdf模板
 *
 * @author xsx
 * @date 2022/7/31
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
public class XEasyPdfTemplate {

    /**
     * 模板参数
     */
    private final XEasyPdfTemplateParam param = new XEasyPdfTemplateParam();

    /**
     * 设置配置路径
     *
     * @param configPath 配置路径
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setConfigPath(String configPath) {
        this.param.setConfigPath(configPath);
        return this;
    }

    /**
     * 设置模板路径
     *
     * @param templatePath 模板路径
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setTemplatePath(String templatePath) {
        this.param.setTemplatePath(templatePath);
        return this;
    }

    /**
     * 设置模板数据
     *
     * @param templateData 模板数据
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setTemplateData(Map<String, Object> templateData) {
        this.param.setTemplateData(templateData);
        return this;
    }

    /**
     * 创建pdf
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument create() {
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 构建pdf
            this.create(outputStream);
            // 创建输入流
            try (InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()))) {
                // 加载pdf并返回
                return XEasyPdfHandler.Document.load(inputStream);
            }
        }
    }

    /**
     * 创建pdf
     *
     * @param outputPath 输出路径
     */
    @SneakyThrows
    public void create(String outputPath) {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputPath))) {
            this.create(outputStream);
        }
    }

    /**
     * 创建pdf
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void create(OutputStream outputStream) {
        // 初始化参数
        this.param.init();
        InputStreamReader reader = null;
        InputStream inputStream = null;
        try {
            // 创建模板输入流读取器
            reader = new InputStreamReader(
                    new BufferedInputStream(new ByteArrayInputStream(this.getTemplate().getBytes(StandardCharsets.UTF_8))),
                    StandardCharsets.UTF_8
            );
            // 创建配置输入流
            inputStream = Files.newInputStream(Paths.get(this.param.getConfigPath()));
            // 创建fop工厂
            final FopFactory fopFactory = new FopFactoryBuilder(new File(this.param.getConfigPath()).getParentFile().toURI()).setConfiguration(
                    new DefaultConfigurationBuilder().build(inputStream)
            ).build();
            // 定义转换结构
//            SAXResult result = new SAXResult(fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(), outputStream).getDefaultHandler());
            // 设置系统id
//            result.setSystemId(UUID.randomUUID().toString());
            // 设置内容助手
//            result.setHandler(fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(), outputStream).getDefaultHandler());
            // 创建转换器
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(reader));
            // 设置参数版本
            transformer.setParameter("versionParam", "2.0");
            // 转换
            transformer.transform(new StreamSource(), new SAXResult(fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(), outputStream).getDefaultHandler()));
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 获取模板
     *
     * @return 返回模板内容
     */
    @SneakyThrows
    private String getTemplate() {
        // 定义模板
        String template;
        // 创建输入流（从资源路径读取）
        try (InputStream inputStream = this.getClass().getResourceAsStream(this.param.getTemplatePath())) {
            // 获取模板内容
            template = IOUtils.toString(inputStream, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            try {
                // 重新获取模板内容（从绝对路径获取）
                template = new String(Files.readAllBytes(Paths.get(this.param.getTemplatePath())), StandardCharsets.UTF_8);
            } catch (Exception ex) {
                // 提示错误信息
                throw new IllegalArgumentException("the template can not be loaded，the path['" + this.param.getTemplatePath() + "'] is error");
            }
        }
        // 如果模板数据不为空，则处理模板
        if (this.param.isNotEmptyTemplateData()) {
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
            context.setLocale(Locale.CHINA);
            // 设置变量数据
            context.setVariables(this.param.getTemplateData());
            // 处理模板并返回
            return templateEngine.process(template, context);
        }
        System.out.println("template = " + template);
        // 返回模板内容
        return template;
    }
}
