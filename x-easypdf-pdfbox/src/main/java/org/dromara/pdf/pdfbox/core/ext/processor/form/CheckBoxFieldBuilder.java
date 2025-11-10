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
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

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
public class CheckBoxFieldBuilder extends AbstractFormFieldBuilder {
    
    /**
     * 状态样式
     */
    protected FormFieldStateStyle style;
    /**
     * 选项值
     */
    protected String value;
    /**
     * 是否选中
     */
    protected Boolean isSelected;
    
    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    private CheckBoxFieldBuilder(Document document, Page page, Size size) {
        super(document, page, size);
    }
    
    /**
     * 构建器
     *
     * @param document 文档
     * @param page     页面
     * @return 返回单选字段构建器
     */
    public static CheckBoxFieldBuilder builder(Document document, Page page, Size size) {
        return new CheckBoxFieldBuilder(document, page, size);
    }
    
    /**
     * 构建
     *
     * @param form 表单
     * @return 返回字段
     */
    @Override
    public PDField build(PDAcroForm form) {
        PDCheckBox field = new PDCheckBox(form);
        this.initProperties(field);
        this.initSize(field);
        this.initWidget(field);
        return field;
    }
    
    /**
     * 初始化属性
     *
     * @param field 字段
     */
    @SneakyThrows
    protected void initProperties(PDCheckBox field) {
        Objects.requireNonNull(this.name, "the value can not be null");
        super.initProperties(field);
        field.getCOSObject().setItem(COSName.OPT, new COSArray(Collections.singletonList(new COSString(this.value))));
    }
    
    /**
     * 初始化部件
     *
     * @param field 字段
     */
    protected void initWidget(PDCheckBox field) {
        PDAnnotationWidget widget = field.getWidgets().get(0);
        this.initAppearance(widget);
        this.initBorder(widget);
    }
    
    /**
     * 初始化外观
     *
     * @param widget 字段
     */
    protected void initAppearance(PDAnnotationWidget widget) {
        PDAppearanceCharacteristicsDictionary appearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        COSDictionary dictionary = appearance.getCOSObject();
        dictionary.setItem(COSName.CA, this.style.getType());
        widget.setAppearanceCharacteristics(appearance);
        widget.setAppearanceState(Optional.ofNullable(this.isSelected).filter(v -> v).map(v -> "0").orElse(COSName.Off.getName()));
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