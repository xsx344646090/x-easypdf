package wiki.xsx.core.pdf.template.template;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateDataSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
     * 日志
     */
    private static final Log log = LogFactory.getLog(XEasyPdfTemplate.class);

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
     * 设置数据源
     *
     * @param dataSource 数据源
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setDataSource(XEasyPdfTemplateDataSource dataSource) {
        this.param.setDataSource(dataSource);
        return this;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setAuthor(String author) {
        this.param.setAuthor(author);
        return this;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setCreator(String creator) {
        this.param.setCreator(creator);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setTitle(String title) {
        this.param.setTitle(title);
        return this;
    }

    /**
     * 设置主题
     *
     * @param subject 主题
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setSubject(String subject) {
        this.param.setSubject(subject);
        return this;
    }

    /**
     * 设置关键词
     *
     * @param keywords 关键词
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setKeywords(String keywords) {
        this.param.setKeywords(keywords);
        return this;
    }

    /**
     * 设置创建时间
     *
     * @param date 创建时间
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate setCreationDate(Date date) {
        this.param.setCreationDate(date);
        return this;
    }

    /**
     * 开启辅助功能（将耗费更多内存）
     * <p>辅助功能特性，请参考：<a href="https://xmlgraphics.apache.org/fop/2.7/accessibility.html">Apache™ FOP: Accessibility</a></p>
     *
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate enableAccessibility() {
        this.param.setIsAccessibility(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭空标签（将耗费更多内存）
     *
     * @return 返回pdf模板
     */
    public XEasyPdfTemplate disableKeepEmptyTags() {
        this.param.setIsKeepEmptyTags(Boolean.FALSE);
        return this.enableAccessibility();
    }

    /**
     * 转换pdf
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public wiki.xsx.core.pdf.doc.XEasyPdfDocument transform() {
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 转换pdf
            this.transform(outputStream);
            // 创建输入流
            try (InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()))) {
                // 加载pdf并返回
                return wiki.xsx.core.pdf.handler.XEasyPdfHandler.Document.load(inputStream);
            }
        }
    }

    /**
     * 转换pdf
     *
     * @param outputPath 输出路径
     */
    @SneakyThrows
    public void transform(String outputPath) {
        // 创建输出流
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputPath))) {
            // 转换pdf
            this.transform(outputStream);
        }
    }

    /**
     * 转换pdf
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void transform(OutputStream outputStream) {
        // 初始化参数
        this.param.init();
        // 定义fop工厂
        FopFactory factory;
        try (
                // 创建配置输入流（从资源路径读取）
                InputStream inputStream = this.getClass().getResourceAsStream(this.param.getConfigPath())
        ) {
            // 创建fop工厂
            factory = new FopFactoryBuilder(
                    new File(".").toURI()
            ).setConfiguration(
                    new DefaultConfigurationBuilder().build(inputStream)
            ).build();
        } catch (Exception e) {
            try (
                    // 创建配置输入流（从绝对路径读取）
                    InputStream inputStream = Files.newInputStream(Paths.get(this.param.getConfigPath()))
            ) {
                // 创建fop工厂
                factory = new FopFactoryBuilder(
                        new File(this.param.getConfigPath()).toURI()
                ).setConfiguration(
                        new DefaultConfigurationBuilder().build(inputStream)
                ).build();
            }
        }
        // 获取fo代理
        FOUserAgent agent = this.getUserAgent(factory);
        // 如果开启日志，则打印xsl-fo内容
        if (log.isDebugEnabled()) {
            // 打印xsl-fo内容
            log.debug("XSL-FO ==> \n" + this.param.getDataSource().getDocumentContent());
        }
        // 转换pdf
        this.param.getDataSource().transform(factory, agent, outputStream);
    }

    /**
     * 获取fo代理
     *
     * @param fopFactory fop工厂
     * @return 返回代理
     */
    private FOUserAgent getUserAgent(FopFactory fopFactory) {
        // 创建代理
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        // 设置生产者
        userAgent.setProducer(XEasyPdfTemplateConstants.FOP_PRODUCER);
        // 设置开启辅助功能
        userAgent.setAccessibility(this.param.getIsAccessibility());
        // 设置作者
        userAgent.setAuthor(this.param.getAuthor());
        // 设置创建者
        userAgent.setCreator(this.param.getCreator());
        // 设置标题
        userAgent.setTitle(this.param.getTitle());
        // 设置主题
        userAgent.setSubject(this.param.getSubject());
        // 设置关键词
        userAgent.setKeywords(this.param.getKeywords());
        // 设置创建时间
        userAgent.setCreationDate(this.param.getCreationDate());
        // 设置保留空标签
        userAgent.setKeepEmptyTags(this.param.getIsAccessibility());
        // 返回代理
        return userAgent;
    }
}
