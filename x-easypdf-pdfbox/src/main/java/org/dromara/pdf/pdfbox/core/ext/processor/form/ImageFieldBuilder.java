package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.ImageFieldLayout;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSInteger;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSName;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSString;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;

import java.util.Objects;
import java.util.Optional;

/**
 * 图像字段构建器
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
public class ImageFieldBuilder extends AbstractFormFieldBuilder {
    
    /**
     * 布局选项
     */
    protected static final COSName LAYOUT_KEY = COSName.getPDFName("TP");
    /**
     * 标签
     */
    protected String label;
    /**
     * 布局
     */
    protected ImageFieldLayout layout;
    
    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    private ImageFieldBuilder(Document document, Page page, Size size) {
        super(document, page, size);
    }
    
    /**
     * 构建器
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     * @return 返回图像字段构建器
     */
    public static ImageFieldBuilder builder(Document document, Page page, Size size) {
        return new ImageFieldBuilder(document, page, size);
    }
    
    /**
     * 构建
     *
     * @param form 表单
     * @return 返回字段
     */
    @Override
    public PDField build(PDAcroForm form) {
        PDPushButton field = new PDPushButton(form);
        this.initProperties(field);
        this.initSize(field);
        this.initAppearance(field);
        return field;
    }
    
    /**
     * 初始化属性
     *
     * @param field 字段
     */
    protected void initProperties(PDField field) {
        super.initProperties(field);
        if (Objects.isNull(this.layout)) {
            this.layout = ImageFieldLayout.IMAGE_ONLY;
        }
    }
    
    /**
     * 初始化外观
     *
     * @param field 字段
     */
    protected void initAppearance(PDField field) {
        PDAppearanceCharacteristicsDictionary appearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        COSDictionary dictionary = appearance.getCOSObject();
        dictionary.setItem(LAYOUT_KEY, COSInteger.get(this.layout.getType()));
        dictionary.setItem(COSName.CA, new COSString(Optional.ofNullable(this.label).orElse("")));
        field.getWidgets().get(0).setAppearanceCharacteristics(appearance);
    }
}