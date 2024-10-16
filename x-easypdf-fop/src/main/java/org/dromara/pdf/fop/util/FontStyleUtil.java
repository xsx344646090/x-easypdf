package org.dromara.pdf.fop.util;

import java.awt.*;

/**
 * 文字样式工具
 *
 * @author xsx
 * @date 2022/11/10
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class FontStyleUtil {

    /**
     * 获取样式
     *
     * @param name 名称
     * @return 返回样式
     */
    public static int getStyle(String name) {
        return FontStyle.valueOf(name.toUpperCase()).style;
    }

    /**
     * 文字样式
     */
    private enum FontStyle {
        /**
         * 正常
         */
        NORMAL(Font.PLAIN),
        /**
         * 粗体
         */
        BOLD(Font.BOLD),
        /**
         * 粗体斜体
         */
        BOLD_ITALIC(Font.BOLD | Font.ITALIC),
        /**
         * 斜体
         */
        ITALIC(Font.ITALIC);
        /**
         * 样式
         */
        private final int style;

        /**
         * 有参构造
         *
         * @param style 样式
         */
        FontStyle(int style) {
            this.style = style;
        }
    }
}
