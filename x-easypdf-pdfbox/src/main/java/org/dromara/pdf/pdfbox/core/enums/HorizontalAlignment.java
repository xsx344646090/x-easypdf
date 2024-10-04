package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;

/**
 * 水平对齐
 *
 * @author xsx
 * @date 2023/6/16
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
@Getter
public enum HorizontalAlignment {
    /**
     * 居左
     */
    LEFT(PDVariableText.QUADDING_LEFT),
    /**
     * 居中
     */
    CENTER(PDVariableText.QUADDING_CENTERED),
    /**
     * 居右
     */
    RIGHT(PDVariableText.QUADDING_RIGHT);

    /**
     * 有参构造
     *
     * @param alignment 对齐方式
     */
    HorizontalAlignment(int alignment) {
        this.alignment = alignment;
    }

    /**
     * 对齐方式
     */
    private final int alignment;
}
