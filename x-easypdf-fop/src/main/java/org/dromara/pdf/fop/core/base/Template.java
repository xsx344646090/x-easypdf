package org.dromara.pdf.fop.core.base;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.pdf.fop.core.datasource.DataSource;
import org.dromara.pdf.fop.support.layout.ExpandLayoutManagerMaker;
import org.dromara.pdf.fop.util.FileUtil;
import org.dromara.pdf.pdfbox.core.base.Document;

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
public class Template {

    static {
        Banner.print();
    }

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(Template.class);

    /**
     * 模板参数
     */
    private final TemplateParam param = new TemplateParam();

    /**
     * 设置配置路径
     *
     * @param configPath 配置路径
     * @return 返回pdf模板
     */
    public Template setConfigPath(String configPath) {
        this.param.setConfigPath(configPath);
        return this;
    }

    /**
     * 设置数据源
     *
     * @param dataSource 数据源
     * @return 返回pdf模板
     */
    public Template setDataSource(DataSource dataSource) {
        this.param.setDataSource(dataSource);
        return this;
    }

    /**
     * 设置布局管理器
     *
     * @param maker 布局管理器
     * @return 返回pdf模板
     */
    public Template setLayoutManagerMaker(ExpandLayoutManagerMaker maker) {
        this.param.setLayoutManagerMaker(maker);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return 返回pdf模板
     */
    public Template setTitle(String title) {
        this.param.setTitle(title);
        return this;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     * @return 返回pdf模板
     */
    public Template setAuthor(String author) {
        this.param.setAuthor(author);
        return this;
    }

    /**
     * 设置主题
     *
     * @param subject 主题
     * @return 返回pdf模板
     */
    public Template setSubject(String subject) {
        this.param.setSubject(subject);
        return this;
    }

    /**
     * 设置关键词
     *
     * @param keywords 关键词
     * @return 返回pdf模板
     */
    public Template setKeywords(String keywords) {
        this.param.setKeywords(keywords);
        return this;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     * @return 返回pdf模板
     */
    public Template setCreator(String creator) {
        this.param.setCreator(creator);
        return this;
    }

    /**
     * 设置创建时间
     *
     * @param date 创建时间
     * @return 返回pdf模板
     */
    public Template setCreationDate(Date date) {
        this.param.setCreationDate(date);
        return this;
    }

    /**
     * 开启辅助功能（将耗费更多内存）
     *
     * @return 返回pdf模板
     * @see <a href="https://xmlgraphics.apache.org/fop/2.7/accessibility.html">Apache™ FOP: Accessibility</a>
     */
    public Template enableAccessibility() {
        this.param.setIsAccessibility(Boolean.TRUE);
        return this;
    }

    /**
     * 开启保留内存
     *
     * @return 返回pdf模板
     */
    public Template enableConserveMemory() {
        this.param.setIsConserveMemory(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭空标签（将耗费更多内存）
     *
     * @return 返回pdf模板
     */
    public Template disableKeepEmptyTags() {
        this.param.setIsKeepEmptyTags(Boolean.FALSE);
        return this.enableAccessibility();
    }

    /**
     * 关闭错误定位信息
     *
     * @return 返回pdf模板
     */
    public Template disableErrorInfo() {
        this.param.setIsErrorInfo(Boolean.FALSE);
        return this;
    }

    /**
     * 获取总页数
     *
     * @return 返回总页数
     */
    public Integer getTotalPage() {
        // 初始化参数
        this.param.initParams();
        // 转换pdf
        return this.param.getDataSource().getTotalPage(this.param.getFopFactory(), this.param.getUserAgent());
    }

    /**
     * 转换pdf
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public Document transform() {
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 转换pdf
            this.transform(outputStream);
            // 创建输入流
            try (InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()))) {
                // 加载pdf并返回
                return org.dromara.pdf.pdfbox.handler.PdfHandler.getDocumentHandler().load(inputStream);
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
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath))))) {
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
        // 如果开启日志，则打印xsl-fo内容
        if (log.isInfoEnabled()) {
            // 打印xsl-fo内容
            log.info("XSL-FO ==> \n" + this.param.getDataSource().getDocumentContent());
        }
        // 初始化参数
        this.param.initParams();
        // 转换pdf
        this.param.getDataSource().transform(this.param.getFopFactory(), this.param.getUserAgent(), outputStream);
    }
}
