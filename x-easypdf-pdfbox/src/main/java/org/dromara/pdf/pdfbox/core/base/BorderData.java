package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.enums.BorderStyle;

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
     * @param base          基础类
     * @param borderConfiguration 配置
     */
    public BorderData(AbstractBase base, BorderConfiguration borderConfiguration) {
        this.init(base, borderConfiguration);
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderConfiguration.setBorderStyle(borderStyle);
    }

    public void setBorderWidth(float width) {
        this.borderConfiguration.setBorderWidth(width);
    }

    public void setBorderLineLength(float length) {
        this.borderConfiguration.setBorderLineLength(length);
    }

    public void setBorderLineSpacing(float spacing) {
        this.borderConfiguration.setBorderLineSpacing(spacing);
    }

    public void setBorderTopColor(Color color) {
        this.borderConfiguration.setBorderTopColor(color);
    }

    public void setBorderBottomColor(Color color) {
        this.borderConfiguration.setBorderBottomColor(color);
    }

    public void setBorderLeftColor(Color color) {
        this.borderConfiguration.setBorderLeftColor(color);
    }

    public void setBorderRightColor(Color color) {
        this.borderConfiguration.setBorderRightColor(color);
    }

    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
    }

    public void setIsBorderTop(boolean flag) {
        this.borderConfiguration.setIsBorderTop(flag);
    }

    public void setIsBorderBottom(boolean flag) {
        this.borderConfiguration.setIsBorderBottom(flag);
    }

    public void setIsBorderLeft(boolean flag) {
        this.borderConfiguration.setIsBorderLeft(flag);
    }

    public void setIsBorderRight(boolean flag) {
        this.borderConfiguration.setIsBorderRight(flag);
    }

    public BorderStyle getBorderStyle() {
        return this.borderConfiguration.getBorderStyle();
    }

    public Float getBorderWidth() {
        return this.borderConfiguration.getBorderWidth();
    }

    public Float getBorderLineLength() {
        return this.borderConfiguration.getBorderLineLength();
    }

    public Float getBorderLineSpacing() {
        return this.borderConfiguration.getBorderLineSpacing();
    }

    public Color getBorderTopColor() {
        return this.borderConfiguration.getBorderTopColor();
    }

    public Color getBorderBottomColor() {
        return this.borderConfiguration.getBorderBottomColor();
    }

    public Color getBorderLeftColor() {
        return this.borderConfiguration.getBorderLeftColor();
    }

    public Color getBorderRightColor() {
        return this.borderConfiguration.getBorderRightColor();
    }

    public Boolean getIsBorderTop() {
        return this.borderConfiguration.getIsBorderTop();
    }

    public Boolean getIsBorderBottom() {
        return this.borderConfiguration.getIsBorderBottom();
    }

    public Boolean getIsBorderLeft() {
        return this.borderConfiguration.getIsBorderLeft();
    }

    public Boolean getIsBorderRight() {
        return this.borderConfiguration.getIsBorderRight();
    }

    /**
     * 初始化
     *
     * @param base          基础类
     * @param borderConfiguration 配置
     */
    protected void init(AbstractBase base, BorderConfiguration borderConfiguration) {
        super.init(base);
        this.borderConfiguration = new BorderConfiguration(borderConfiguration);
    }
}
