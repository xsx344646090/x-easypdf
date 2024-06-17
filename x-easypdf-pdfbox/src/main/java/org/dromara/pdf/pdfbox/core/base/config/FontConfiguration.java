package org.dromara.pdf.pdfbox.core.base.config;

import lombok.Data;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.support.Constants;

import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * 字体配置
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
public class FontConfiguration {

    /**
     * 字体名称
     */
    private String fontName;
    /**
     * 特殊字体名称
     */
    private List<String> specialFontNames;
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
     * 有参构造
     */
    public FontConfiguration() {
        this.init();
    }

    /**
     * 有参构造
     *
     * @param configuration 配置
     */
    public FontConfiguration(FontConfiguration configuration) {
        this.init(configuration);
    }

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
     */
    public void init() {
        // 初始化字体名称
        if (Objects.isNull(this.fontName)) {
            this.fontName = Constants.DEFAULT_FONT_NAME;
        }
        // 初始化字体大小
        if (Objects.isNull(this.fontSize)) {
            this.fontSize = 12F;
        }
        // 初始化字体颜色
        if (Objects.isNull(this.fontColor)) {
            this.fontColor = Color.BLACK;
        }
        // 初始化字体透明度
        if (Objects.isNull(this.fontAlpha)) {
            this.fontAlpha = 1.0F;
        }
        // 初始化字体样式
        if (Objects.isNull(this.fontStyle)) {
            this.fontStyle = FontStyle.NORMAL;
        }
        // 初始化字体斜率
        if (Objects.isNull(this.fontSlope)) {
            if (this.fontStyle.isItalic()) {
                this.fontSlope = 0.3F;
            }else {
                this.fontSlope = 0F;
            }
        }
        // 初始化字符间距
        if (Objects.isNull(this.characterSpacing)) {
            this.characterSpacing = 0F;
        }
        // 初始化行间距
        if (Objects.isNull(this.leading)) {
            this.leading = 0F;
        }
    }

    /**
     * 初始化
     *
     * @param configuration 配置
     */
    public void init(FontConfiguration configuration) {
        // 初始化字体名称
        if (Objects.isNull(this.fontName)) {
            this.fontName = configuration.fontName;
        }
        // 初始化特殊字体名称
        if (Objects.isNull(this.specialFontNames)) {
            this.specialFontNames = configuration.specialFontNames;
        }
        // 初始化字体大小
        if (Objects.isNull(this.fontSize)) {
            this.fontSize = configuration.fontSize;
        }
        // 初始化字体颜色
        if (Objects.isNull(this.fontColor)) {
            this.fontColor = configuration.fontColor;
        }
        // 初始化字体透明度
        if (Objects.isNull(this.fontAlpha)) {
            this.fontAlpha = configuration.fontAlpha;
        }
        // 初始化字体样式
        if (Objects.isNull(this.fontStyle)) {
            this.fontStyle = configuration.fontStyle;
        }
        // 初始化字体斜率
        if (Objects.isNull(this.fontSlope)) {
            this.fontSlope = configuration.fontSlope;
        }
        // 初始化字符间距
        if (Objects.isNull(this.characterSpacing)) {
            this.characterSpacing = configuration.characterSpacing;
        }
        // 初始化行间距
        if (Objects.isNull(this.leading)) {
            this.leading = configuration.leading;
        }
    }
}
