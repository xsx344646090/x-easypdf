package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;

import java.awt.*;

/**
 * 边框数据
 *
 * @author xsx
 * @date 2024/5/13
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
@EqualsAndHashCode(callSuper = true)
public class BorderData extends AbstractBase {
    /**
     * 配置
     */
    protected BorderConfiguration borderConfiguration;

    /**
     * 无参构造
     */
    protected BorderData() {

    }

    /**
     * 有参构造
     *
     * @param base                基础类
     * @param borderConfiguration 配置
     */
    public BorderData(AbstractBase base, BorderConfiguration borderConfiguration) {
        this.init(base, borderConfiguration);
    }

    /**
     * 创建
     *
     * @param base                基础类
     * @param borderConfiguration 配置
     * @return 返回边框数据
     */
    public static BorderData create(AbstractBase base, BorderConfiguration borderConfiguration) {
        return new BorderData(base, borderConfiguration);
    }

    /**
     * 设置边框样式
     *
     * @param style 样式
     */
    public void setBorderLineStyle(LineStyle style) {
        this.borderConfiguration.setBorderLineStyle(style);
    }

    /**
     * 设置线帽样式
     *
     * @param style 样式
     */
    public void setBorderLineCapStyle(LineCapStyle style) {
        this.borderConfiguration.setBorderLineCapStyle(style);
    }

    /**
     * 设置边框线长
     *
     * @param length 线长
     */
    public void setBorderLineLength(float length) {
        this.borderConfiguration.setBorderLineLength(length);
    }

    /**
     * 设置边框线宽
     *
     * @param width 线宽
     */
    public void setBorderLineWidth(float width) {
        this.borderConfiguration.setBorderLineWidth(width);
    }

    /**
     * 设置边框点线间隔
     *
     * @param spacing 间隔
     */
    public void setBorderDottedSpacing(float spacing) {
        this.borderConfiguration.setBorderDottedSpacing(spacing);
    }

    /**
     * 设置上边框颜色
     *
     * @param color 颜色
     */
    public void setBorderTopColor(Color color) {
        this.borderConfiguration.setBorderTopColor(color);
    }

    /**
     * 设置下边框颜色
     *
     * @param color 颜色
     */
    public void setBorderBottomColor(Color color) {
        this.borderConfiguration.setBorderBottomColor(color);
    }

    /**
     * 设置左边框颜色
     *
     * @param color 颜色
     */
    public void setBorderLeftColor(Color color) {
        this.borderConfiguration.setBorderLeftColor(color);
    }

    /**
     * 设置右边框颜色
     *
     * @param color 颜色
     */
    public void setBorderRightColor(Color color) {
        this.borderConfiguration.setBorderRightColor(color);
    }

    /**
     * 设置是否边框（上下左右）
     *
     * @param flag 是否边框
     */
    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
    }

    /**
     * 设置是否上边框
     *
     * @param flag 是否边框
     */
    public void setIsBorderTop(boolean flag) {
        this.borderConfiguration.setIsBorderTop(flag);
    }

    /**
     * 设置是否下边框
     *
     * @param flag 是否边框
     */
    public void setIsBorderBottom(boolean flag) {
        this.borderConfiguration.setIsBorderBottom(flag);
    }

    /**
     * 设置是否左边框
     *
     * @param flag 是否边框
     */
    public void setIsBorderLeft(boolean flag) {
        this.borderConfiguration.setIsBorderLeft(flag);
    }

    /**
     * 设置是否右边框
     *
     * @param flag 是否边框
     */
    public void setIsBorderRight(boolean flag) {
        this.borderConfiguration.setIsBorderRight(flag);
    }

    /**
     * 获取边框样式
     *
     * @return 返回边框样式
     */
    public LineStyle getBorderLineStyle() {
        return this.borderConfiguration.getBorderLineStyle();
    }

    /**
     * 获取线帽样式
     *
     * @return 返回线帽样式
     */
    public LineCapStyle getBorderLineCapStyle() {
        return this.borderConfiguration.getBorderLineCapStyle();
    }

    /**
     * 获取边框线长
     *
     * @return 返回边框线长
     */
    public Float getBorderLineLength() {
        return this.borderConfiguration.getBorderLineLength();
    }

    /**
     * 获取边框线宽
     *
     * @return 返回边框线宽
     */
    public Float getBorderLineWidth() {
        return this.borderConfiguration.getBorderLineWidth();
    }

    /**
     * 获取边框点线间隔
     *
     * @return 返回边框点线间隔
     */
    public Float getBorderDottedSpacing() {
        return this.borderConfiguration.getBorderDottedSpacing();
    }

    /**
     * 获取上边框颜色
     *
     * @return 返回上边框颜色
     */
    public Color getBorderTopColor() {
        return this.borderConfiguration.getBorderTopColor();
    }

    /**
     * 获取下边框颜色
     *
     * @return 返回下边框颜色
     */
    public Color getBorderBottomColor() {
        return this.borderConfiguration.getBorderBottomColor();
    }

    /**
     * 获取左边框颜色
     *
     * @return 返回左边框颜色
     */
    public Color getBorderLeftColor() {
        return this.borderConfiguration.getBorderLeftColor();
    }

    /**
     * 获取右边框颜色
     *
     * @return 返回右边框颜色
     */
    public Color getBorderRightColor() {
        return this.borderConfiguration.getBorderRightColor();
    }

    /**
     * 获取是否上边框
     *
     * @return 返回是否上边框
     */
    public Boolean getIsBorderTop() {
        return this.borderConfiguration.getIsBorderTop();
    }

    /**
     * 获取是否下边框
     *
     * @return 返回是否下边框
     */
    public Boolean getIsBorderBottom() {
        return this.borderConfiguration.getIsBorderBottom();
    }

    /**
     * 获取是否左边框
     *
     * @return 返回是否左边框
     */
    public Boolean getIsBorderLeft() {
        return this.borderConfiguration.getIsBorderLeft();
    }

    /**
     * 获取是否右边框
     *
     * @return 返回是否右边框
     */
    public Boolean getIsBorderRight() {
        return this.borderConfiguration.getIsBorderRight();
    }

    /**
     * 初始化
     *
     * @param base                基础类
     * @param borderConfiguration 配置
     */
    protected void init(AbstractBase base, BorderConfiguration borderConfiguration) {
        super.init(base);
        this.borderConfiguration = new BorderConfiguration(borderConfiguration);
    }
}
