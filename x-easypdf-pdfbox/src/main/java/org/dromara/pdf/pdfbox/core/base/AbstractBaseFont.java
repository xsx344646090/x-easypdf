package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;

import java.awt.*;
import java.util.Objects;

/**
 * 抽象基础字体类
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
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBaseFont extends AbstractBaseBorder {
    /**
     * 字体名称
     */
    private String fontName;
    /**
     * pdfbox字体
     */
    private PDFont font;
    /**
     * 字体大小
     */
    private Float fontSize;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 字体透明度
     */
    private Float fontAlpha;
    /**
     * 字体样式
     */
    private FontStyle fontStyle;
    /**
     * 字体斜率（斜体字）
     */
    private Float fontSlope;
    /**
     * 字符间距
     */
    private Float characterSpacing;
    /**
     * 行间距
     */
    private Float leading;

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     */
    public void setFontSize(float fontSize) {
        if (fontSize < 1) {
            throw new IllegalArgumentException("the font size must be greater than 1");
        }
        this.fontSize = fontSize;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     */
    public void setCharacterSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the character spacing must be greater than 0");
        }
        this.characterSpacing = spacing;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     */
    public void setLeading(float leading) {
        if (leading < 0) {
            throw new IllegalArgumentException("the leading must be greater than 0");
        }
        this.leading = leading;
    }

    /**
     * 初始化
     *
     * @param base                基础参数
     * @param isInitMarginByParam 是否使用参数初始化边距
     */
    public void init(AbstractBaseFont base, boolean isInitMarginByParam) {
        // 初始化
        super.init(base, isInitMarginByParam);
        // 初始化字体
        this.initFont(base);
    }

    /**
     * 初始化字体
     *
     * @param base 基础参数
     */
    protected void initFont(AbstractBaseFont base) {
        // 初始化pdfbox字体
        if (Objects.isNull(this.font)) {
            this.font = base.font;
        }
        // 初始化字体名称
        if (Objects.isNull(this.fontName)) {
            this.fontName = base.fontName;
        }
        // 初始化字体大小
        if (Objects.isNull(this.fontSize)) {
            this.fontSize = base.fontSize;
        }
        // 初始化字体颜色
        if (Objects.isNull(this.fontColor)) {
            this.fontColor = base.fontColor;
        }
        // 初始化字体透明度
        if (Objects.isNull(this.fontAlpha)) {
            this.fontAlpha = base.fontAlpha;
        }
        // 初始化字体样式
        if (Objects.isNull(this.fontStyle)) {
            this.fontStyle = base.fontStyle;
        }
        // 初始化字体斜率
        if (Objects.isNull(this.fontSlope)) {
            if (this.fontStyle.isItalic()) {
                this.fontSlope = 0.3F;
            } else {
                this.fontSlope = base.fontSlope;
            }
        }
        // 初始化字符间距
        if (Objects.isNull(this.characterSpacing)) {
            this.characterSpacing = base.characterSpacing;
        }
        // 初始化行间距
        if (Objects.isNull(this.leading)) {
            this.leading = base.leading;
        }
    }
}
