package org.dromara.pdf.pdfbox.enums;

import lombok.Getter;

/**
 * 打印方向
 *
 * @author xsx
 * @date 2023/10/18
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public enum PrintOrientation {
    /**
     * 横向
     */
    LANDSCAPE(0),
    /**
     * 纵向
     */
    PORTRAIT(1),
    /**
     * 反向横向
     */
    REVERSE_LANDSCAPE(2);
    /**
     * 方向
     */
    private final int orientation;

    /**
     * 有参构造
     *
     * @param orientation 方向
     */
    PrintOrientation(int orientation) {
        this.orientation = orientation;
    }
}
