package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.ColorUtil;

import java.awt.*;
import java.util.Objects;
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
     * 默认外观
     */
    protected String defaultAppearance;


    /**
     * 有参构造
     *
     * @param document
     * @param page
     * @param size
     */
    public TextFieldBuilder(Document document, PDPage page, Size size) {
        super(document, page, size);
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     * @return 返回字段构建器
     */
    public TextFieldBuilder setFontName(String fontName) {
        this.fontName = fontName;
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回字段构建器
     */
    public TextFieldBuilder setFontSize(Float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回字段构建器
     */
    public TextFieldBuilder setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param isMultiline 字体颜色
     * @return 返回字段构建器
     */
    public TextFieldBuilder setMultiline(boolean isMultiline) {
        this.isMultiline = isMultiline;
        return this;
    }

    /**
     * 设置是否密码
     *
     * @param isPassword 是否密码
     * @return 返回字段构建器
     */
    public TextFieldBuilder setPassword(boolean isPassword) {
        this.isPassword = isPassword;
        return this;
    }

    /**
     * 设置是否文件选择
     *
     * @param isFileSelect 是否文件选择
     * @return 返回字段构建器
     */
    public TextFieldBuilder setFileSelect(boolean isFileSelect) {
        this.isFileSelect = isFileSelect;
        return this;
    }

    /**
     * 设置是否非拼写检查
     *
     * @param isDoNotSpellCheck 是否非拼写检查
     * @return 返回字段构建器
     */
    public TextFieldBuilder setDoNotSpellCheck(boolean isDoNotSpellCheck) {
        this.isDoNotSpellCheck = isDoNotSpellCheck;
        return this;
    }

    /**
     * 设置是否非滚动
     *
     * @param isDoNotScroll 是否非滚动
     * @return 返回字段构建器
     */
    public TextFieldBuilder setDoNotScroll(boolean isDoNotScroll) {
        this.isDoNotScroll = isDoNotScroll;
        return this;
    }

    /**
     * 设置是否组合
     *
     * @param isCombination 是否组合
     * @return 返回字段构建器
     */
    public TextFieldBuilder setCombination(boolean isCombination) {
        this.isCombination = isCombination;
        return this;
    }

    /**
     * 设置是否富文本
     *
     * @param isRichText 是否富文本
     * @return 返回字段构建器
     */
    public TextFieldBuilder setRichText(boolean isRichText) {
        this.isRichText = isRichText;
        return this;
    }

    /**
     * 设置富文本值
     *
     * @param richTextValue 富文本值
     * @return 返回字段构建器
     */
    public TextFieldBuilder setRichTextValue(String richTextValue) {
        this.richTextValue = richTextValue;
        return this;
    }

    /**
     * 设置最大长度
     *
     * @param maxLength 最大长度
     * @return 返回字段构建器
     */
    public TextFieldBuilder setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     * @return 返回字段构建器
     */
    public TextFieldBuilder setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * 设置默认外观
     *
     * @param defaultAppearance 默认外观
     * @return 返回字段构建器
     */
    public TextFieldBuilder setDefaultAppearance(String defaultAppearance) {
        this.defaultAppearance = defaultAppearance;
        return this;
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
        this.initDefaultAppearance(field);
        return field;
    }

    /**
     * 初始化属性
     *
     * @param field 字段
     */
    protected void initProperties(PDTextField field) {
        Objects.requireNonNull(this.name, "the name can not be null");
        field.setPartialName(this.name);
        Optional.ofNullable(this.isReadonly).ifPresent(field::setReadOnly);
        Optional.ofNullable(this.isRequired).ifPresent(field::setRequired);
        Optional.ofNullable(this.isNoExport).ifPresent(field::setNoExport);
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
     * 初始化默认外观
     *
     * @param field 字段
     */
    protected void initDefaultAppearance(PDTextField field) {
        String fontName = Optional.ofNullable(this.fontName).map(this.document.getContext()::getFont).orElse(this.document.getFont()).getName();
        Float fontSize = Optional.ofNullable(this.fontSize).orElse(Constants.DEFAULT_FONT_SIZE);
        Color fontColor = Optional.ofNullable(this.fontColor).orElse(Constants.DEFAULT_FONT_COLOR);
        String appearance = String.format(DEFAULT_APPEARANCE_FORMATTER, fontName, fontSize.intValue(), ColorUtil.toPDColorString(fontColor));
        field.setDefaultAppearance(appearance);
    }
}
