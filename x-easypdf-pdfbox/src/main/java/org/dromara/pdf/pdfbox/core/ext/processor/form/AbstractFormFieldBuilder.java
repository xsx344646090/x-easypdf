package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.Objects;
import java.util.Optional;

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
    protected Page page;
    /**
     * 尺寸
     */
    protected Size size;
    /**
     * 水平对齐
     */
    @Setter
    protected HorizontalAlignment alignment;
    /**
     * 字段名称
     */
    @Setter
    protected String name;
    /**
     * 是否只读
     */
    @Setter
    protected Boolean isReadonly;
    /**
     * 是否必须
     */
    @Setter
    protected Boolean isRequired;
    /**
     * 是否不导出
     */
    @Setter
    protected Boolean isNoExport;

    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    public AbstractFormFieldBuilder(Document document, Page page, Size size) {
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
     * 初始化属性
     *
     * @param field 字段
     */
    protected void initProperties(PDField field) {
        Objects.requireNonNull(this.name, "the name can not be null");
        field.setPartialName(this.name);
        Optional.ofNullable(this.isReadonly).ifPresent(field::setReadOnly);
        Optional.ofNullable(this.isRequired).ifPresent(field::setRequired);
        Optional.ofNullable(this.isNoExport).ifPresent(field::setNoExport);
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
        widget.setPage(this.page.getTarget());
        widget.setPrinted(true);
        this.page.getTarget().getAnnotations().add(widget);
    }
}
