package org.dromara.pdf.pdfbox.core.enums;

import org.apache.pdfbox.cos.COSString;

/**
 * 单选字段样式
 *
 * @author xsx
 * @date 2024/10/18
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
public enum FormFieldStateStyle {
    /**
     * 勾形
     */
    HOOK("4"),
    /**
     * 圆形
     */
    CIRCLE("l"),
    /**
     * 叉形
     */
    FORK("8"),
    /**
     * 菱形
     */
    RHOMBUS("u"),
    /**
     * 正方形
     */
    SQUARE("n"),
    /**
     * 星形
     */
    STAR("H");
    /**
     * 类型
     */
    private final String type;
    
    /**
     * 构造方法
     *
     * @param type 类型
     */
    FormFieldStateStyle(String type) {
        this.type = type;
    }
    
    /**
     * 获取类型
     *
     * @return 返回类型
     */
    public COSString getType() {
        return new COSString(this.type);
    }
}
