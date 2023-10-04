package org.dromara.pdf.pdfbox.enums;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 条形码纠错级别
 *
 * @author xsx
 * @date 2023/8/28
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf-box is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public enum BarcodeErrorLevel {
    /**
     * L 水平 7%的字码可被修正
     */
    L(ErrorCorrectionLevel.L),
    /**
     * M 水平 15%的字码可被修正
     */
    M(ErrorCorrectionLevel.M),
    /**
     * Q 水平 25%的字码可被修正
     */
    Q(ErrorCorrectionLevel.Q),
    /**
     * H 水平 30%的字码可被修正
     */
    H(ErrorCorrectionLevel.H);

    /**
     * 纠错级别
     */
    private final ErrorCorrectionLevel level;

    /**
     * 有参构造
     *
     * @param level 纠错级别
     */
    BarcodeErrorLevel(ErrorCorrectionLevel level) {
        this.level = level;
    }

    /**
     * 获取纠错级别
     *
     * @return 返回纠错级别
     */
    public ErrorCorrectionLevel getLevel() {
        return this.level;
    }
}
