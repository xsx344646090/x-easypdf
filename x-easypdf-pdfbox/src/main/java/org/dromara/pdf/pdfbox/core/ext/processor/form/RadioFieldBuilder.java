package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.FormFieldStateStyle;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSArray;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSName;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSString;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 单选字段构建器
 *
 * @author xsx
 * @date 2024/10/18
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
@Setter
@EqualsAndHashCode(callSuper = true)
public class RadioFieldBuilder extends AbstractFormFieldBuilder {
    
    /**
     * 字段列表
     */
    protected List<RadioField> fields;
    
    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    private RadioFieldBuilder(Document document, Page page, Size size) {
        super(document, page, size);
    }
    
    /**
     * 构建器
     *
     * @param document 文档
     * @param page     页面
     * @return 返回单选字段构建器
     */
    public static RadioFieldBuilder builder(Document document, Page page) {
        return new RadioFieldBuilder(document, page, Size.create(0F, 0F, 0F, 0F));
    }
    
    /**
     * 添加字段
     *
     * @param fields 字段
     */
    public void addFields(RadioField... fields) {
        Objects.requireNonNull(fields, "the fields can not be null");
        if (Objects.isNull(this.fields)) {
            this.fields = new ArrayList<>(10);
        }
        Collections.addAll(this.fields, fields);
    }
    
    /**
     * 构建
     *
     * @param form 表单
     * @return 返回字段
     */
    @Override
    public PDField build(PDAcroForm form) {
        PDRadioButton field = new PDRadioButton(form);
        this.initProperties(field);
        this.initWidget(field);
        return field;
    }
    
    /**
     * 初始化部件
     *
     * @param field 字段
     */
    protected void initWidget(PDRadioButton field) {
        Objects.requireNonNull(this.fields, "the fields can not be null");
        COSArray options = new COSArray();
        List<PDAnnotationWidget> widgets = new ArrayList<>(this.fields.size());
        int index = 0;
        Integer selectIndex = null;
        for (RadioField radioField : this.fields) {
            PDAnnotationWidget widget = new PDAnnotationWidget(new COSDictionary());
            widget.setAppearanceState(COSName.Off.getName());
            widgets.add(widget);
            options.add(new COSString(radioField.getValue()));
            
            this.initAppearance(widget, radioField.getStyle());
            this.initSize(widget, radioField.getSize());
            this.initBorder(widget);
            if (radioField.getIsSelected()) {
                selectIndex = index;
            }
            index++;
        }
        if (Objects.nonNull(selectIndex)) {
            widgets.get(selectIndex).setAppearanceState(String.valueOf(selectIndex));
        }
        field.setWidgets(widgets);
        field.getCOSObject().setItem(COSName.OPT, options);
    }
    
    /**
     * 初始化外观
     *
     * @param widget 字段
     * @param style  状态样式
     */
    protected void initAppearance(PDAnnotationWidget widget, FormFieldStateStyle style) {
        PDAppearanceCharacteristicsDictionary appearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        COSDictionary dictionary = appearance.getCOSObject();
        dictionary.setItem(COSName.CA, style.getType());
        widget.setAppearanceCharacteristics(appearance);
    }
    
    /**
     * 初始化尺寸
     *
     * @param widget 部件
     * @param size   尺寸
     */
    @SneakyThrows
    protected void initSize(PDAnnotationWidget widget, Size size) {
        widget.setRectangle(size.getRectangle());
        widget.setPage(this.page.getTarget());
        widget.setPrinted(true);
        this.page.getTarget().getAnnotations().add(widget);
    }
    
    /**
     * 初始化边框
     *
     * @param widget 部件
     */
    protected void initBorder(PDAnnotationWidget widget) {
        PDBorderStyleDictionary borderStyle = new PDBorderStyleDictionary(new COSDictionary());
        borderStyle.setWidth(1F);
        borderStyle.setStyle(PDBorderStyleDictionary.STYLE_INSET);
        widget.setBorderStyle(borderStyle);
    }
}