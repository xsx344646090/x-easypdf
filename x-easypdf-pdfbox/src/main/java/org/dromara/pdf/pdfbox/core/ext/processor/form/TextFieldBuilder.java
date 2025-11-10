package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.awt.*;
import java.util.Optional;

/**
 * 文本字段构建器
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
@Setter
@EqualsAndHashCode(callSuper = true)
public class TextFieldBuilder extends AbstractFormFieldBuilder {
    
    /**
     * 默认外观格式化
     */
    protected final static String DEFAULT_APPEARANCE_FORMATTER = "/%s %d Tf %s";
    /**
     * 字体名称
     */
    protected String fontName;
    /**
     * 字体大小
     */
    protected Float fontSize;
    /**
     * 字体颜色
     */
    protected Color fontColor;
    /**
     * 是否多行
     */
    protected Boolean isMultiline;
    /**
     * 是否密码
     */
    protected Boolean isPassword;
    /**
     * 是否文件选择
     */
    protected Boolean isFileSelect;
    /**
     * 是否非拼写检查
     */
    protected Boolean isDoNotSpellCheck;
    /**
     * 是否非滚动
     */
    protected Boolean isDoNotScroll;
    /**
     * 是否组合
     */
    protected Boolean isCombination;
    /**
     * 是否富文本
     */
    protected Boolean isRichText;
    /**
     * 富文本值
     */
    protected String richTextValue;
    /**
     * 最大长度
     */
    protected Integer maxLength;
    /**
     * 默认值
     */
    protected String defaultValue;
    
    /**
     * 有参构造
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     */
    private TextFieldBuilder(Document document, Page page, Size size) {
        super(document, page, size);
    }
    
    /**
     * 构建器
     *
     * @param document 文档
     * @param page     页面
     * @param size     尺寸
     * @return 返回文本字段构建器
     */
    public static TextFieldBuilder builder(Document document, Page page, Size size) {
        return new TextFieldBuilder(document, page, size);
    }
    
    /**
     * 构建
     *
     * @param form 表单
     * @return 返回字段
     */
    @Override
    public PDField build(PDAcroForm form) {
        PDTextField field = new PDTextField(form);
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
    protected void initProperties(PDTextField field) {
        super.initProperties(field);
        Optional.ofNullable(this.alignment).map(HorizontalAlignment::getAlignment).ifPresent(field::setQ);
        Optional.ofNullable(this.isMultiline).ifPresent(field::setMultiline);
        Optional.ofNullable(this.isPassword).ifPresent(field::setPassword);
        Optional.ofNullable(this.isFileSelect).ifPresent(field::setFileSelect);
        Optional.ofNullable(this.isDoNotSpellCheck).ifPresent(field::setDoNotSpellCheck);
        Optional.ofNullable(this.isDoNotScroll).ifPresent(field::setDoNotScroll);
        Optional.ofNullable(this.isCombination).ifPresent(field::setComb);
        Optional.ofNullable(this.isRichText).ifPresent(field::setRichText);
        Optional.ofNullable(this.richTextValue).ifPresent(field::setRichTextValue);
        Optional.ofNullable(this.maxLength).ifPresent(field::setMaxLen);
        Optional.ofNullable(this.defaultValue).ifPresent(field::setDefaultValue);
    }
    
    /**
     * 初始化外观
     *
     * @param field 字段
     */
    protected void initAppearance(PDTextField field) {
        String fontName = Optional.ofNullable(this.fontName).map(this.document.getContext()::getFont).orElse(this.document.getFont()).getName();
        Float fontSize = Optional.ofNullable(this.fontSize).orElse(Constants.DEFAULT_FONT_SIZE);
        Color fontColor = Optional.ofNullable(this.fontColor).orElse(Constants.DEFAULT_FONT_COLOR);
        String appearance = String.format(DEFAULT_APPEARANCE_FORMATTER, fontName, fontSize.intValue(), ColorUtil.toPDColorString(fontColor));
        field.setDefaultAppearance(appearance);
    }
}
