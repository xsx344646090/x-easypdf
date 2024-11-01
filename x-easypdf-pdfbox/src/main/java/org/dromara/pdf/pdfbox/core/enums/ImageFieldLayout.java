package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;

/**
 * 图像字段布局
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
@Getter
public enum ImageFieldLayout {
    /**
     * 无图像
     */
    IMAGE_NONE(0),
    /**
     * 仅图像
     */
    IMAGE_ONLY(1),
    /**
     * 图像在标签上
     */
    IMAGE_UP(2),
    /**
     * 图像在标签下
     */
    IMAGE_DOWN(3),
    /**
     * 图像在标签左
     */
    IMAGE_LEFT(4),
    /**
     * 图像在标签右
     */
    IMAGE_RIGHT(5),
    /**
     * 图像在标签后
     */
    IMAGE_BEHIND(6);
    /**
     * 类型
     */
    private final int type;

    /**
     * 构造方法
     *
     * @param type 类型
     */
    ImageFieldLayout(int type) {
        this.type = type;
    }
}
