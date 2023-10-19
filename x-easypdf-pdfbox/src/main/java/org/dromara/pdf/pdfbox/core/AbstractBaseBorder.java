package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.enums.BorderStyle;

import java.awt.*;
import java.util.Objects;

/**
 * 抽象基础边框类
 *
 * @author xsx
 * @date 2023/9/22
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
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBaseBorder extends AbstractBaseMargin {
    /**
     * 边框样式
     */
    private BorderStyle borderStyle;
    /**
     * 边框线宽
     */
    private Float borderWidth;
    /**
     * 边框点线长度
     */
    private Float borderLineLength;
    /**
     * 边框点线间隔
     */
    private Float borderLineSpacing;
    /**
     * 上边框颜色
     */
    private Color borderTopColor;
    /**
     * 下边框颜色
     */
    private Color borderBottomColor;
    /**
     * 左边框颜色
     */
    private Color borderLeftColor;
    /**
     * 右边框颜色
     */
    private Color borderRightColor;
    /**
     * 是否上边框
     */
    private Boolean isBorderTop;
    /**
     * 是否下边框
     */
    private Boolean isBorderBottom;
    /**
     * 是否左边框
     */
    private Boolean isBorderLeft;
    /**
     * 是否右边框
     */
    private Boolean isBorderRight;

    /**
     * 设置边框
     *
     * @param flag 标记
     */
    public void setIsBorder(boolean flag) {
        this.isBorderTop = flag;
        this.isBorderBottom = flag;
        this.isBorderLeft = flag;
        this.isBorderRight = flag;
    }

    /**
     * 设置边框颜色
     *
     * @param color 颜色
     */
    public void setBorderColor(Color color) {
        this.borderTopColor = color;
        this.borderBottomColor = color;
        this.borderLeftColor = color;
        this.borderRightColor = color;
    }

    /**
     * 设置边框线宽
     *
     * @param width 线宽
     */
    public void setBorderWidth(float width) {
        if (width <= 0) {
            throw new IllegalArgumentException("the border width must be greater than 0");
        }
        this.borderWidth = width;
    }

    /**
     * 设置边框点线长度
     *
     * @param length 点线长度
     */
    public void setBorderLineLength(float length) {
        if (length <= 0) {
            throw new IllegalArgumentException("the border line length must be greater than 0");
        }
        this.borderLineLength = length;
    }

    /**
     * 设置边框点线间隔
     *
     * @param spacing 点线间隔
     */
    public void setBorderLineSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the border line spacing must be greater than 0");
        }
        this.borderLineSpacing = spacing;
    }

    /**
     * 初始化
     *
     * @param base                基础参数
     * @param isInitMarginByParam 是否使用参数初始化边距
     */
    public void init(AbstractBaseBorder base, boolean isInitMarginByParam) {
        // 初始化
        super.init(base, isInitMarginByParam);
        // 初始化边框
        this.initBorder(base);
    }

    /**
     * 初始化边框
     *
     * @param base 基础参数
     */
    private void initBorder(AbstractBaseBorder base) {
        // 初始化边框样式
        if (Objects.isNull(this.borderStyle)) {
            this.borderStyle = base.borderStyle;
        }
        // 初始化边框线宽
        if (Objects.isNull(this.borderWidth)) {
            this.borderWidth = base.borderWidth;
        }
        // 初始化边框点线长度
        if (Objects.isNull(this.borderLineLength)) {
            this.borderLineLength = base.borderLineLength;
        }
        // 初始化边框点线间隔
        if (Objects.isNull(this.borderLineSpacing)) {
            this.borderLineSpacing = base.borderLineSpacing;
        }
        // 初始化上边框颜色
        if (Objects.isNull(this.borderTopColor)) {
            this.borderTopColor = base.borderTopColor;
        }
        // 初始化下边框颜色
        if (Objects.isNull(this.borderBottomColor)) {
            this.borderBottomColor = base.borderBottomColor;
        }
        // 初始化左边框颜色
        if (Objects.isNull(this.borderLeftColor)) {
            this.borderLeftColor = base.borderLeftColor;
        }
        // 初始化右边框颜色
        if (Objects.isNull(this.borderRightColor)) {
            this.borderRightColor = base.borderRightColor;
        }
        // 初始化是否上边框
        if (Objects.isNull(this.isBorderTop)) {
            this.isBorderTop = base.isBorderTop;
        }
        // 初始化是否下边框
        if (Objects.isNull(this.isBorderBottom)) {
            this.isBorderBottom = base.isBorderBottom;
        }
        // 初始化是否左边框
        if (Objects.isNull(this.isBorderLeft)) {
            this.isBorderLeft = base.isBorderLeft;
        }
        // 初始化是否右边框
        if (Objects.isNull(this.isBorderRight)) {
            this.isBorderRight = base.isBorderRight;
        }
    }
}
