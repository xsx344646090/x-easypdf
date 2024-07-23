package org.dromara.pdf.pdfbox.core.base.config;

import lombok.Data;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;

import java.awt.*;
import java.util.Objects;

/**
 * 边框配置
 *
 * @author xsx
 * @date 2023/9/22
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
@Data
public class BorderConfiguration {
    /**
     * 边框样式
     */
    protected LineStyle borderLineStyle;
    /**
     * 线帽样式
     */
    protected LineCapStyle borderLineCapStyle;
    /**
     * 边框线长
     */
    protected Float borderLineLength;
    /**
     * 边框线宽
     */
    protected Float borderLineWidth;
    /**
     * 边框点线间隔
     */
    protected Float borderDottedSpacing;
    /**
     * 上边框颜色
     */
    protected Color borderTopColor;
    /**
     * 下边框颜色
     */
    protected Color borderBottomColor;
    /**
     * 左边框颜色
     */
    protected Color borderLeftColor;
    /**
     * 右边框颜色
     */
    protected Color borderRightColor;
    /**
     * 是否上边框
     */
    protected Boolean isBorderTop;
    /**
     * 是否下边框
     */
    protected Boolean isBorderBottom;
    /**
     * 是否左边框
     */
    protected Boolean isBorderLeft;
    /**
     * 是否右边框
     */
    protected Boolean isBorderRight;

    /**
     * 无参构造
     */
    public BorderConfiguration() {
        this(true);
    }

    /**
     * 有参构造
     *
     * @param isInit 是否初始化
     */
    public BorderConfiguration(boolean isInit) {
        if (isInit) {
            this.init();
        }
    }

    /**
     * 有参构造
     *
     * @param configuration 配置
     */
    public BorderConfiguration(BorderConfiguration configuration) {
        this.init(configuration);
    }

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
    public void setBorderLineWidth(float width) {
        if (width <= 0) {
            throw new IllegalArgumentException("the border line width must be greater than 0");
        }
        this.borderLineWidth = width;
    }

    /**
     * 设置边框线长
     *
     * @param length 线长
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
    public void setBorderDottedSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the border dotted spacing must be greater than 0");
        }
        this.borderDottedSpacing = spacing;
    }

    /**
     * 是否有边框
     *
     * @return 返回是否有边框
     */
    public boolean hasBorder() {
        return this.isBorderTop || this.isBorderBottom || this.isBorderLeft || this.isBorderRight;
    }

    /**
     * 初始化
     */
    public void init() {
        // 初始化边框样式
        if (Objects.isNull(this.borderLineStyle)) {
            this.borderLineStyle = LineStyle.SOLID;
        }
        // 初始化边框线帽样式
        if (Objects.isNull(this.borderLineCapStyle)) {
            this.borderLineCapStyle = LineCapStyle.SQUARE;
        }
        // 初始化边框线宽
        if (Objects.isNull(this.borderLineWidth)) {
            this.borderLineWidth = 1F;
        }
        // 初始化边框点线间隔
        if (Objects.isNull(this.borderDottedSpacing)) {
            this.borderDottedSpacing = 1F;
        }
        // 初始化上边框颜色
        if (Objects.isNull(this.borderTopColor)) {
            this.borderTopColor = Color.GRAY;
        }
        // 初始化下边框颜色
        if (Objects.isNull(this.borderBottomColor)) {
            this.borderBottomColor = Color.GRAY;
        }
        // 初始化左边框颜色
        if (Objects.isNull(this.borderLeftColor)) {
            this.borderLeftColor = Color.GRAY;
        }
        // 初始化右边框颜色
        if (Objects.isNull(this.borderRightColor)) {
            this.borderRightColor = Color.GRAY;
        }
        // 初始化是否上边框
        if (Objects.isNull(this.isBorderTop)) {
            this.isBorderTop = Boolean.FALSE;
        }
        // 初始化是否下边框
        if (Objects.isNull(this.isBorderBottom)) {
            this.isBorderBottom = Boolean.FALSE;
        }
        // 初始化是否左边框
        if (Objects.isNull(this.isBorderLeft)) {
            this.isBorderLeft = Boolean.FALSE;
        }
        // 初始化是否右边框
        if (Objects.isNull(this.isBorderRight)) {
            this.isBorderRight = Boolean.FALSE;
        }
    }

    /**
     * 初始化边框
     *
     * @param base 基础参数
     */
    public void init(BorderConfiguration base) {
        // 初始化边框样式
        if (Objects.isNull(this.borderLineStyle)) {
            this.borderLineStyle = base.borderLineStyle;
        }
        // 初始化边框线帽样式
        if (Objects.isNull(this.borderLineCapStyle)) {
            this.borderLineCapStyle = base.borderLineCapStyle;
        }
        // 初始化边框线长
        if (Objects.isNull(this.borderLineLength)) {
            this.borderLineLength = base.borderLineLength;
        }
        // 初始化边框线宽
        if (Objects.isNull(this.borderLineWidth)) {
            this.borderLineWidth = base.borderLineWidth;
        }
        // 初始化边框点线间隔
        if (Objects.isNull(this.borderDottedSpacing)) {
            this.borderDottedSpacing = base.borderDottedSpacing;
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
