package org.dromara.pdf.pdfbox.enums;

/**
 * @author xsx
 * @date 2023/6/2
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
public enum RotationAngle {
    /**
     * 90度
     */
    ROTATION_90(90),
    /**
     * 180度
     */
    ROTATION_180(180),
    /**
     * 270度
     */
    ROTATION_270(270);

    /**
     * 后缀
     */
    private final Integer angle;

    /**
     * 有参构造
     *
     * @param angle 角度
     */
    RotationAngle(Integer angle) {
        this.angle = angle;
    }

    /**
     * 获取角度
     *
     * @return 返回角度
     */
    public Integer getAngle() {
        return this.angle;
    }
}
