package org.dromara.pdf.pdfbox.core.ext.templater;

import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.ext.convertor.html.HtmlConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.IdUtil;

import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 抽象html模板引擎
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
@Setter
public abstract class AbstractHtmlTemplater extends AbstractTemplater {

    /**
     * 转换器
     */
    protected final HtmlConvertor convertor = PdfHandler.getDocumentConvertor().getHtmlConvertor();
    /**
     * 模板路径
     */
    protected String templatePath;
    /**
     * 模板名称
     */
    protected String templateName;
    /**
     * 模板数据
     */
    protected Map<String, Object> templateData;
    /**
     * dpi
     */
    protected Integer dpi;
    /**
     * 页面尺寸
     */
    protected PageSize pageSize;
    /**
     * 上边距（单位：px）
     */
    protected Float marginTop;
    /**
     * 下边距（单位：px）
     */
    protected Float marginBottom;
    /**
     * 左边距（单位：px）
     */
    protected Float marginLeft;
    /**
     * 右边距（单位：px）
     */
    protected Float marginRight;
    /**
     * 缩放比例（0.1-2.0）
     */
    protected Float scale;
    /**
     * 是否横向
     */
    protected Boolean isLandscape;
    /**
     * 是否包含背景
     */
    protected Boolean isIncludeBackground;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractHtmlTemplater(Document document) {
        super(document);
    }

    /**
     * 处理模板
     *
     * @param writer 写入器
     */
    protected abstract void process(Writer writer);

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.marginTop = margin;
        this.marginBottom = margin;
        this.marginLeft = margin;
        this.marginRight = margin;
    }

    /**
     * 获取html内容
     *
     * @return 返回html内容
     */
    @SneakyThrows
    public String getHtmlContent() {
        Path path = this.processTemplate(this::process);
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } finally {
            Files.deleteIfExists(path);
        }
    }

    /**
     * 获取html文件
     *
     * @return 返回html文件
     */
    @SneakyThrows
    public File getHtmlFile() {
        return this.processTemplate(this::process).toFile();
    }

    /**
     * 转换
     *
     * @return 返回文档
     */
    @SneakyThrows
    public Document transform() {
        this.initConvertor();
        Path path = this.processTemplate(this::process);
        try {
            return this.convertor.toPdf(path.toAbsolutePath().toString());
        } finally {
            Files.deleteIfExists(path);
        }
    }

    /**
     * 处理模板
     *
     * @return 返回路径
     */
    @SneakyThrows
    protected Path processTemplate(Consumer<Writer> consumer) {
        Objects.requireNonNull(this.templatePath, "the template path can not be null");
        Objects.requireNonNull(this.templateName, "the template name can not be null");
        Path path = new File(Constants.TEMP_FILE_PATH, IdUtil.get() + ".html").toPath();
        try (Writer writer = Files.newBufferedWriter(path)) {
            consumer.accept(writer);
            return path;
        }
    }

    /**
     * 初始化转换器
     */
    protected void initConvertor() {
        this.convertor.setDpi(this.dpi);
        this.convertor.setPageSize(this.pageSize);
        this.convertor.setMarginTop(this.marginTop);
        this.convertor.setMarginBottom(this.marginBottom);
        this.convertor.setMarginLeft(this.marginLeft);
        this.convertor.setMarginRight(this.marginRight);
        this.convertor.setScale(this.scale);
        this.convertor.setIsLandscape(this.isLandscape);
        this.convertor.setIsIncludeBackground(this.isIncludeBackground);
    }
}
