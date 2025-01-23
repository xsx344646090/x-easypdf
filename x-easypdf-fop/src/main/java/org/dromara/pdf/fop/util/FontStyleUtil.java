package org.dromara.pdf.fop.util;


import lombok.Getter;
import org.apache.fop.fonts.Font;

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
    public static FontStyle getStyle(String name) {
        return FontStyle.valueOf(name.toUpperCase());
    }

    /**
     * 文字样式
     */
    @Getter
    public enum FontStyle {
        /**
         * 正常
         */
        NORMAL(Font.STYLE_NORMAL, java.awt.Font.PLAIN),
        /**
         * 粗体
         */
        BOLD(Font.STYLE_NORMAL, java.awt.Font.BOLD),
        /**
         * 粗体斜体
         */
        BOLD_ITALIC(Font.STYLE_ITALIC, java.awt.Font.BOLD | java.awt.Font.ITALIC),
        /**
         * 斜体
         */
        ITALIC(Font.STYLE_ITALIC, java.awt.Font.ITALIC);
        /**
         * 样式
         */
        private final String style;
        /**
         * awt样式
         */
        private final int awtStyle;

        /**
         * 有参构造
         *
         * @param style    样式
         * @param awtStyle awt样式
         */
        FontStyle(String style, int awtStyle) {
            this.style = style;
            this.awtStyle = awtStyle;
        }

        public int getWeight() {
            return this.isBold() ? Font.WEIGHT_BOLD : Font.WEIGHT_NORMAL;
        }

        public boolean isBold() {
            return this == BOLD || this == BOLD_ITALIC;
        }

        public boolean isItalic() {
            return this == ITALIC || this == BOLD_ITALIC;
        }
    }
}
