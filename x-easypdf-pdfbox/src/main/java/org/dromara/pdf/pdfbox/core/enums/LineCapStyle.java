package org.dromara.pdf.pdfbox.core.enums;

/**
 * 线帽样式
 *
 * @author xsx
 * @date 2023/11/6
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
public enum LineCapStyle {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 圆角
     */
    ROUND(1),
    /**
     * 方角
     */
    SQUARE(2);
    /**
     * 类型
     */
    private final int type;

    /**
     * 有参构造
     *
     * @param type 类型
     */
    LineCapStyle(int type) {
        this.type = type;
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    public int getType() {
        return this.type;
    }
}
