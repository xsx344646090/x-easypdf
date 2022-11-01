package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.Color;
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
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回文本组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateText setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param fontStyle 字体样式
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体重量
     *
     * @param fontWeight 字体重量
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 设置水平样式
     * <p>LEFT：居左</p>
     * <p>CENTER：居中</p>
     * <p>RIGHT：居右</p>
     *
     * @param style 水平样式
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置垂直对齐
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     *
     * @param style 垂直样式
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setVerticalStyle(String style) {
        this.param.setVerticalAlign(style);
        return this;
    }

    /**
     * 设置删除线颜色
     *
     * @param color 颜色
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setDeleteLineColor(Color color) {
        this.param.setDeleteLineColor(color);
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param color 颜色
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setUnderLineColor(Color color) {
        this.param.setUnderLineColor(color);
        return this;
    }

    /**
     * 设置下划线宽度
     *
     * @param width 宽度
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText setUnderLineWidth(String width) {
        this.param.setUnderLineWidth(width);
        return this;
    }

    /**
     * 开启删除线
     *
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText enableDeleteLine() {
        this.param.setHasDeleteLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启下划线
     *
     * @return 返回文本组件
     */
    public XEasyPdfTemplateText enableUnderLine() {
        this.param.setHasUnderLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回文本组件
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
        // 如果包含删除线，则设置删除线
        if (this.param.getHasDeleteLine()) {
            // 设置文本装饰（删除线）
            block.setAttribute(XEasyPdfTemplateAttributes.TEXT_DECORATION, "line-through");
            // 设置删除线颜色
            Optional.ofNullable(this.param.getDeleteLineColor()).ifPresent(c -> XEasyPdfTemplateElementHandler.appendColor(block, c));
        }
        // 添加inline元素
        block.appendChild(this.createInline(document));
        // 返回block元素
        return block;
    }

    /**
     * 初始化
     *
     * @param param 文本基础参数
     * @return 返回文本组件
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
        // 设置上下左右填充
        Optional.ofNullable(this.param.getPadding()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.PADDING, v.intern()));
        // 设置上填充
        Optional.ofNullable(this.param.getPaddingTop()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.PADDING_TOP, v.intern()));
        // 设置下填充
        Optional.ofNullable(this.param.getPaddingBottom()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.PADDING_BOTTOM, v.intern()));
        // 设置左填充
        Optional.ofNullable(this.param.getPaddingLeft()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.PADDING_LEFT, v.intern()));
        // 设置右填充
        Optional.ofNullable(this.param.getPaddingRight()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.PADDING_RIGHT, v.intern()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.LANGUAGE, v.intern()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getFontColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(inline, v));
        // 设置垂直对齐
        Optional.ofNullable(this.param.getVerticalAlign()).ifPresent(v -> inline.setAttribute(XEasyPdfTemplateAttributes.VERTICAL_ALIGN, v.intern()));
        // 如果包含下划线，则设置下划线
        if (this.param.hasUnderLine) {
            // 获取下划线设置
            String borderBottom = Optional.ofNullable(this.param.getUnderLineWidth()).orElse("1pt")
                    + " solid " +
                    Optional.ofNullable(this.param.getUnderLineColor()).map(
                            v -> "rgb(" + v.getRed() + "," + v.getGreen() + "," + v.getBlue() + ")"
                    ).orElse("");
            // 设置下划线
            inline.setAttribute(XEasyPdfTemplateAttributes.BORDER_BOTTOM, borderBottom.intern());
        }
        // 设置文本
        inline.setTextContent(this.param.getText());
        // 返回inline元素
        return inline;
    }
}
