package org.dromara.pdf.pdfbox.doc;

/**
 * pdf位置样式
 *
 * @author xsx
 * @date 2022/1/30
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public enum XEasyPdfPositionStyle {
    /**
     * 居上
     */
    TOP,
    /**
     * 居中
     */
    CENTER,
    /**
     * 居左
     */
    LEFT,
    /**
     * 居右
     */
    RIGHT,
    /**
     * 居下
     */
    BOTTOM;

    /**
     * 检查水平样式
     *
     * @param style 水平样式
     */
    public static void checkHorizontalStyle(XEasyPdfPositionStyle style) {
        // 如果给定样式为居上或居下，则提示错误
        if (style == XEasyPdfPositionStyle.TOP || style == XEasyPdfPositionStyle.BOTTOM) {
            // 提示错误
            throw new IllegalArgumentException("only set LEFT, CENTER or RIGHT style");
        }
    }

    /**
     * 检查垂直样式
     *
     * @param style 垂直样式
     */
    public static void checkVerticalStyle(XEasyPdfPositionStyle style) {
        // 如果给定样式为居左或居右，则提示错误
        if (style == XEasyPdfPositionStyle.LEFT || style == XEasyPdfPositionStyle.RIGHT) {
            // 提示错误
            throw new IllegalArgumentException("only set TOP, CENTER or BOTTOM style");
        }
    }
}
