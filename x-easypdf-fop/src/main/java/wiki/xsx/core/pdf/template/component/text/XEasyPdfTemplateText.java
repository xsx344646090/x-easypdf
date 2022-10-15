package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplatePositionStyle;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.*;
import java.util.Optional;

/**
 * pdf模板-文本组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/8/5
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
public class XEasyPdfTemplateText extends XEasyPdfTemplateTextBase {

    /**
     * 文本参数
     */
    private final XEasyPdfTemplateTextParam param = new XEasyPdfTemplateTextParam();

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param fontStyle 字体样式
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体重量
     *
     * @param fontWeight 字体重量
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 设置水平样式
     *
     * @param style 水平样式
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setHorizontalStyle(XEasyPdfTemplatePositionStyle style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    @Override
    public Element createElement(Document document) {
        // 如果文本为空，则返回空元素
        if (this.param.getText() == null) {
            // 返回空元素
            return null;
        }
        // 初始化block元素
        Element block = this.initBlock(document, this.param);
        // 创建inline元素
        Element inline = this.createInline(document);
        // 添加inline元素
        block.appendChild(inline);
        // 返回block元素
        return block;
    }

    /**
     * 初始化
     *
     * @param param 文本基础参数
     * @return 返回pdf模板-文本组件
     */
    XEasyPdfTemplateText init(XEasyPdfTemplateTextBaseParam param) {
        this.param.init(param);
        return this;
    }

    /**
     * 创建inline元素
     *
     * @param document fo文档
     */
    private Element createInline(Document document) {
        // 创建inline元素
        Element inline = document.createElement(XEasyPdfTemplateTags.IN_LINE);
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v));
        // 设置字体颜色
        Optional.ofNullable(this.param.getFontColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(inline, v));
        // 设置文本
        inline.setTextContent(this.param.getText());
        // 返回inline元素
        return inline;
    }
}
