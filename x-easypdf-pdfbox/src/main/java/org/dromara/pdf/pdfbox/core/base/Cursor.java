package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;

/**
 * 光标
 *
 * @author xsx
 * @date 2023/9/7
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
@Data
public class Cursor {
    /**
     * 当前X轴坐标
     */
    private Float x;
    /**
     * 当前Y轴坐标
     */
    private Float y;

    /**
     * 重置
     *
     * @param x X轴坐标
     * @param y Y轴坐标
     */
    public void reset(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
