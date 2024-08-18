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
    protected String fontName;
    /**
     * 特殊字体名称
     */
    protected List<String> specialFontNames;
    /**
     * 字体大小
     */
    protected Float fontSize;
    /**
     * 字体颜色
     */
    protected Color fontColor;
    /**
     * 字体描边颜色
     */
    protected Color strokColor;
    /**
     * 字体透明度
     */
    protected Float fontAlpha;
    /**
     * 字体样式
     */
    protected FontStyle fontStyle;
    /**
     * 字体斜率（斜体字）
     */
    protected Float fontSlope;
    /**
     * 字符间距
     */
    protected Float characterSpacing;
    /**
     * 行间距
     */
    protected Float leading;
    
    /**
     * 无参构造
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
        this.fontName = Constants.DEFAULT_FONT_NAME;
        // 初始化字体大小
        this.fontSize = Constants.DEFAULT_FONT_SIZE;
        // 初始化字体颜色
        this.fontColor = Constants.DEFAULT_FONT_COLOR;
        // 初始化字体描边颜色
        this.strokColor = Constants.DEFAULT_FONT_COLOR;
        // 初始化字体透明度
        this.fontAlpha = Constants.DEFAULT_FONT_ALPHA;
        // 初始化字体样式
        this.fontStyle = Constants.DEFAULT_FONT_STYLE;
        // 初始化字体斜率
        this.fontSlope = Constants.DEFAULT_FONT_SLOPE;
        // 初始化字符间距
        this.characterSpacing = Constants.DEFAULT_CHARACTER_SPACING;
        // 初始化行间距
        this.leading = Constants.DEFAULT_LEADING;
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
        // 初始化字体描边颜色
        if (Objects.isNull(this.strokColor)) {
            this.strokColor = configuration.strokColor;
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
