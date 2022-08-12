package wiki.xsx.core.pdf.template;

/**
 * pdf模板位置样式
 *
 * @author xsx
 * @date 2022/8/9
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public enum XEasyPdfTemplatePositionStyle {
    /**
     * 居上
     */
    TOP("display-align", "before"),
    /**
     * 居下
     */
    BOTTOM("display-align", "after"),
    /**
     * 水平居中
     */
    HORIZONTAL_CENTER("text-align", "center"),
    /**
     * 垂直居中
     */
    VERTICAL_CENTER("display-align", "center"),
    /**
     * 居左
     */
    LEFT("text-align", "start"),
    /**
     * 居右
     */
    RIGHT("text-align", "end");

    /**
     * 键
     */
    private final String key;
    /**
     * 值
     */
    private final String value;

    /**
     * 有参构造
     *
     * @param key   键
     * @param value 值
     */
    XEasyPdfTemplatePositionStyle(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取键
     *
     * @return 返回键
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 获取值
     *
     * @return 返回值
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 是否水平样式
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isHorizontalStyle() {
        return this == LEFT || this == HORIZONTAL_CENTER || this == RIGHT;
    }

    /**
     * 是否垂直样式
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isVerticalStyle() {
        return this == TOP || this == VERTICAL_CENTER || this == BOTTOM;
    }
}
