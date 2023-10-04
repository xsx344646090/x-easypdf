package org.dromara.pdf.pdfbox.enums;

import java.awt.*;

/**
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
public enum BarcodeWordsStyle {

    /**
     * 正常
     */
    NORMAL(Font.PLAIN),
    /**
     * 粗体
     */
    BOLD(Font.BOLD),
    /**
     * 斜体
     */
    ITALIC(Font.ITALIC),
    /**
     * 粗体斜体
     */
    BOLD_ITALIC(Font.BOLD|Font.ITALIC);
    /**
     * 样式
     */
    private final int style;

    /**
     * 有参构造
     *
     * @param style 样式
     */
    BarcodeWordsStyle(int style) {
        this.style = style;
    }

    /**
     * 获取样式
     *
     * @return 返回样式
     */
    public int getStyle() {
        return this.style;
    }
}
