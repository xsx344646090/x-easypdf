package org.dromara.pdf.pdfbox.enums;

import lombok.Getter;
import org.apache.pdfbox.printing.Scaling;

/**
 * 打印缩放
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
public enum PrintScaling {
    /**
     * 实际尺寸
     */
    ACTUAL_SIZE(Scaling.ACTUAL_SIZE),
    /**
     * 缩小填充
     */
    SHRINK_TO_FIT(Scaling.SHRINK_TO_FIT),
    /**
     * 拉伸填充
     */
    STRETCH_TO_FIT(Scaling.STRETCH_TO_FIT),
    /**
     * 缩放至合适
     */
    SCALE_TO_FIT(Scaling.SCALE_TO_FIT);
    /**
     * 缩放
     */
    private final Scaling scaling;

    /**
     * 有参构造
     *
     * @param scaling 缩放
     */
    PrintScaling(Scaling scaling) {
        this.scaling = scaling;
    }
}
