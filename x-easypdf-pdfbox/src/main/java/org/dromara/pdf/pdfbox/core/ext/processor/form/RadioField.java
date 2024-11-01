package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.Data;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.FormFieldStateStyle;

import java.util.Objects;
import java.util.Optional;

/**
 * 单选字段
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
@Data
public class RadioField {
    
    /**
     * 状态样式
     */
    protected FormFieldStateStyle style;
    /**
     * 尺寸
     */
    protected Size size;
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
     * @param size  尺寸
     * @param value 选项值
     */
    public RadioField(Size size, String value) {
        this(null, size, value, false);
    }
    
    /**
     * 有参构造
     *
     * @param size       尺寸
     * @param value      选项值
     * @param isSelected 是否选中
     */
    public RadioField(Size size, String value, boolean isSelected) {
        this(null, size, value, isSelected);
    }
    
    /**
     * 有参构造
     *
     * @param style 状态样式
     * @param size  尺寸
     * @param value 选项值
     */
    public RadioField(FormFieldStateStyle style, Size size, String value) {
        this(style, size, value, false);
    }
    
    /**
     * 有参构造
     *
     * @param style      状态样式
     * @param size       尺寸
     * @param value      选项值
     * @param isSelected 是否选中
     */
    public RadioField(FormFieldStateStyle style, Size size, String value, boolean isSelected) {
        Objects.requireNonNull(size, "the size can not be null");
        this.style = Optional.ofNullable(style).orElse(FormFieldStateStyle.HOOK);
        this.size = size;
        this.value = value;
        this.isSelected = isSelected;
    }
}