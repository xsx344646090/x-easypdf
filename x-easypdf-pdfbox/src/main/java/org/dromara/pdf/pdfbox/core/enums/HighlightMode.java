package org.dromara.pdf.pdfbox.core.enums;

import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;

/**
 * 高亮模式
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
public enum HighlightMode {
    /**
     * 无
     */
    NONE(PDAnnotationLink.HIGHLIGHT_MODE_NONE),
    /**
     * 反转
     */
    INVERT(PDAnnotationLink.HIGHLIGHT_MODE_INVERT),
    /**
     * 轮廓
     */
    OUTLINE(PDAnnotationLink.HIGHLIGHT_MODE_OUTLINE),
    /**
     * 推送
     */
    PUSH(PDAnnotationLink.HIGHLIGHT_MODE_PUSH);

    /**
     * 模式
     */
    private final String mode;

    /**
     * 有参构造
     *
     * @param mode 模式
     */
    HighlightMode(String mode) {
        this.mode = mode;
    }

    /**
     * 获取模式
     *
     * @return 返回模式
     */
    public String getMode() {
        return this.mode;
    }
}
