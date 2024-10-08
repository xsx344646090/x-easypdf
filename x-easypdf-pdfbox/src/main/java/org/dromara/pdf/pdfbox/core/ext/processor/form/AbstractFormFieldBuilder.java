package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;

import java.util.Objects;

/**
 * 抽象表单字段构建器
 *
 * @author xsx
 * @date 2024/9/25
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public abstract class AbstractFormFieldBuilder {

    /**
     * 文档
     */
    protected Document document;
    /**
     * 页面
     */
    protected PDPage page;
    /**
     * 尺寸
     */
    protected Size size;
    /**
     * 水平对齐
     */
    protected HorizontalAlignment alignment;
    /**
     * 字段名称
     */
    protected String name;
    /**
     * 是否只读
     */
    protected Boolean isReadonly;
    /**
     * 是否必须
     */
    protected Boolean isRequired;
    /**
     * 是否不导出
     */
    protected Boolean isNoExport;

    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    public AbstractFormFieldBuilder(Document document, PDPage page, Size size) {
        Objects.requireNonNull(document, "the document can not be null");
        Objects.requireNonNull(page, "the page can not be null");
        Objects.requireNonNull(size, "the size can not be null");
        this.document = document;
        this.page = page;
        this.size = size;
    }

    /**
     * 构建
     *
     * @param form 表单
     * @return 返回字段
     */
    public abstract PDField build(PDAcroForm form);

    /**
     * 设置字段名称
     *
     * @param name 字段名称
     * @return 返回字段构建器
     */
    public AbstractFormFieldBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 设置对齐方式
     *
     * @param alignment 对齐方式
     * @return 返回字段构建器
     */
    public AbstractFormFieldBuilder setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * 设置是否只读
     *
     * @param isReadonly 是否只读
     * @return 返回字段构建器
     */
    public AbstractFormFieldBuilder setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
        return this;
    }

    /**
     * 设置是否必须
     *
     * @param isRequired 是否必须
     * @return 返回字段构建器
     */
    public AbstractFormFieldBuilder setRequired(boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    /**
     * 设置是否不导出
     *
     * @param isNoExport 是否不导出
     * @return 返回字段构建器
     */
    public AbstractFormFieldBuilder setNoExport(boolean isNoExport) {
        this.isNoExport = isNoExport;
        return this;
    }

    /**
     * 初始化尺寸
     *
     * @param field 字段
     */
    @SneakyThrows
    protected void initSize(PDField field) {
        PDAnnotationWidget widget = field.getWidgets().get(0);
        widget.setRectangle(this.size.getRectangle());
        widget.setPage(this.page);
        widget.setPrinted(true);
        this.page.getAnnotations().add(widget);
    }
}
