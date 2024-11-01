package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;

/**
 * 密钥长度
 *
 * @author xsx
 * @date 2023/10/18
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
public enum PWLength {
    /**
     * 长度40
     */
    LENGTH_40(40),
    /**
     * 长度128
     */
    LENGTH_128(128),
    /**
     * 长度256
     */
    LENGTH_256(256);
    /**
     * 长度
     */
    private final int length;

    /**
     * 构造方法
     *
     * @param length 长度
     */
    PWLength(int length) {
        this.length = length;
    }
}
