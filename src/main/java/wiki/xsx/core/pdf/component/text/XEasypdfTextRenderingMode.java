package wiki.xsx.core.pdf.component.text;

import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

/**
 * pdf文本渲染模式
 *
 * @author xsx
 * @date 2022/7/18
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public enum XEasypdfTextRenderingMode {

    /**
     * 正常
     */
    NORMAL(RenderingMode.FILL),
    /**
     * 空心
     */
    STROKE(RenderingMode.STROKE),
    /**
     * 加粗
     */
    BOLD(RenderingMode.FILL_STROKE),
    /**
     * 细体
     */
    LIGHT(RenderingMode.FILL_STROKE),
    /**
     * 隐藏（不可见）
     */
    HIDDEN(RenderingMode.NEITHER),
    /**
     * 斜体
     */
    ITALIC(RenderingMode.FILL),
    /**
     * 斜体空心
     */
    ITALIC_STROKE(RenderingMode.STROKE),
    /**
     * 斜体加粗
     */
    ITALIC_BOLD(RenderingMode.FILL_STROKE),
    /**
     * 斜体细体
     */
    ITALIC_LIGHT(RenderingMode.FILL_STROKE);

    /**
     * pdfbox文本渲染模式
     */
    private final RenderingMode mode;

    /**
     * 有参构造
     *
     * @param mode pdfbox文本渲染模式
     */
    XEasypdfTextRenderingMode(RenderingMode mode) {
        this.mode = mode;
    }

    /**
     * 获取渲染模式
     *
     * @return 返回pdfbox文本渲染模式
     */
    RenderingMode getMode() {
        return mode;
    }

    /**
     * 是否空心
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean isStroke() {
        return this == STROKE || this == BOLD || this == ITALIC_STROKE || this == ITALIC_BOLD;
    }

    /**
     * 是否填充
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean isFill() {
        return this == NORMAL || this == BOLD || this == ITALIC || this == ITALIC_BOLD;
    }

    /**
     * 是否细体
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean isLight() {
        return this == LIGHT || this == ITALIC_LIGHT;
    }

    /**
     * 是否斜体
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean isItalic() {
        return this == ITALIC || this == ITALIC_STROKE || this == ITALIC_BOLD || this == ITALIC_LIGHT;
    }
}
