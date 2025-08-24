package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;

/**
 * 线端样式
 *
 * @author xsx
 * @date 2025/7/28
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
public enum LineEndingStyle {
    /**
     * 无
     */
    None("无"),
    /**
     * 正方形
     */
    Square("正方形"),
    /**
     * 圆形
     */
    Circle("圆形"),
    /**
     * 菱形
     */
    Diamond("菱形"),
    /**
     * 开箭头
     */
    OpenArrow("开箭头"),
    /**
     * 闭箭头
     */
    ClosedArrow("闭箭头"),
    /**
     * 短线
     */
    Butt("短线"),
    /**
     * 反向开箭头
     */
    ROpenArrow("反向开箭头"),
    /**
     * 反向闭箭头
     */
    RClosedArrow("反向闭箭头"),
    /**
     * 斜短线
     */
    Slash("斜短线");

    /**
     * 备注
     */
    @Getter
    private final String remark;

    /**
     * 有参构造
     * @param remark 备注
     */
    LineEndingStyle(String remark) {
        this.remark = remark;
    }
}
