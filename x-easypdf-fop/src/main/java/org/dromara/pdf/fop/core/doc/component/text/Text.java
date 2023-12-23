package org.dromara.pdf.fop.core.doc.component.text;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-文本组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/8/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Text extends TextBase {

    /**
     * 文本参数
     */
    private final TextParam param = new TextParam();

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public Text setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回文本组件
     */
    public Text setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回文本组件
     */
    public Text setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回文本组件
     */
    public Text setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回文本组件
     */
    public Text setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回文本组件
     */
    public Text setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回文本组件
     */
    public Text setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回文本组件
     */
    public Text setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回文本组件
     */
    public Text setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回文本组件
     */
    public Text setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回文本组件
     */
    public Text setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回文本组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public Text setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回文本组件
     */
    public Text setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本组件
     */
    public Text setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回文本组件
     */
    public Text setLetterSpacing(String spacing) {
        this.param.setLetterSpacing(spacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回文本组件
     */
    public Text setWordSpacing(String spacing) {
        this.param.setWordSpacing(spacing);
        return this;
    }

    /**
     * 设置空白空间
     * <p>normal：正常</p>
     * <p>pre：保留空格</p>
     * <p>nowrap：合并空格</p>
     *
     * @param whiteSpace 空白空间
     * @return 返回文本组件
     */
    public Text setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置空白空间折叠
     * <p>true：是</p>
     * <p>false：否</p>
     *
     * @param whiteSpaceCollapse 折叠
     * @return 返回文本组件
     */
    public Text setWhiteSpaceCollapse(String whiteSpaceCollapse) {
        this.param.setWhiteSpaceCollapse(whiteSpaceCollapse);
        return this;
    }

    /**
     * 设置文本缩进
     *
     * @param indent 缩进值
     * @return 返回文本组件
     */
    public Text setTextIndent(String indent) {
        this.param.setTextIndent(indent);
        return this;
    }

    /**
     * 设置段前缩进
     *
     * @param indent 缩进值
     * @return 返回文本组件
     */
    public Text setStartIndent(String indent) {
        this.param.setStartIndent(indent);
        return this;
    }

    /**
     * 设置段后缩进
     *
     * @param indent 缩进值
     * @return 返回文本组件
     */
    public Text setEndIndent(String indent) {
        this.param.setEndIndent(indent);
        return this;
    }

    /**
     * 设置段前空白
     *
     * @param space 空白值
     * @return 返回文本组件
     */
    public Text setSpaceBefore(String space) {
        this.param.setSpaceBefore(space);
        return this;
    }

    /**
     * 设置段后空白
     *
     * @param space 空白值
     * @return 返回文本组件
     */
    public Text setSpaceAfter(String space) {
        this.param.setSpaceAfter(space);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回文本组件
     */
    public Text setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     * <p>normal：正常</p>
     * <p>oblique：斜体</p>
     * <p>italic：斜体</p>
     * <p>backslant：斜体</p>
     *
     * @param fontStyle 字体样式
     * @return 返回文本组件
     */
    public Text setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体重量
     * <p>normal：正常（400）</p>
     * <p>bold：粗体（700）</p>
     * <p>bolder：加粗（900）</p>
     * <p>lighter：细体（100）</p>
     *
     * @param fontWeight 字体重量
     * @return 返回文本组件
     */
    public Text setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本组件
     */
    public Text setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回文本组件
     */
    public Text setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回文本组件
     */
    public Text setFontColor(String color) {
        this.param.setColor(color);
        return this;
    }

    /**
     * 设置水平样式
     * <p>left：居左</p>
     * <p>center：居中</p>
     * <p>right：居右</p>
     * <p>justify：两端对齐</p>
     *
     * @param style 水平样式
     * @return 返回文本组件
     */
    public Text setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置垂直对齐（用于角标设置）
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     *
     * @param style 垂直样式
     * @return 返回文本组件
     */
    public Text setVerticalStyle(String style) {
        this.param.setVerticalAlign(style);
        return this;
    }

    /**
     * 设置内部地址
     * <p>注：标签id</p>
     *
     * @param destination 地址
     * @return 返回文本组件
     */
    public Text setLinkInternalDestination(String destination) {
        this.param.setLinkInternalDestination(destination);
        return this;
    }

    /**
     * 设置外部地址
     * <p>注：url</p>
     *
     * @param destination 地址
     * @return 返回文本组件
     */
    public Text setLinkExternalDestination(String destination) {
        this.param.setLinkExternalDestination(destination);
        return this;
    }

    /**
     * 设置删除线颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回文本组件
     */
    public Text setDeleteLineColor(String color) {
        this.param.setDeleteLineColor(color);
        return this;
    }

    /**
     * 设置下划线颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回文本组件
     */
    public Text setUnderLineColor(String color) {
        this.param.setUnderLineColor(color);
        return this;
    }

    /**
     * 设置下划线宽度
     *
     * @param width 宽度
     * @return 返回文本组件
     */
    public Text setUnderLineWidth(String width) {
        this.param.setUnderLineWidth(width);
        return this;
    }

    /**
     * 设置分页符-前
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之前强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之前强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakBefore 分页符
     * @return 返回文本组件
     */
    public Text setBreakBefore(String breakBefore) {
        this.param.setBreakBefore(breakBefore);
        return this;
    }

    /**
     * 设置分页符-后
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之后强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之后强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakAfter 分页符
     * @return 返回文本组件
     */
    public Text setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 设置背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回文本组件
     */
    public Text setBackgroundColor(String color) {
        this.param.setBackgroundColor(color);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回文本组件
     */
    public Text enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回文本组件
     */
    public Text enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回文本组件
     */
    public Text enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启超链接
     *
     * @return 返回文本组件
     */
    public Text enableLink() {
        this.param.setHasLink(Boolean.TRUE);
        return this;
    }

    /**
     * 开启删除线
     *
     * @return 返回文本组件
     */
    public Text enableDeleteLine() {
        this.param.setHasDeleteLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启下划线
     *
     * @return 返回文本组件
     */
    public Text enableUnderLine() {
        this.param.setHasUnderLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回文本组件
     */
    public Text enableBorder() {
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
        // 如果包含超链接
        if (this.param.getHasLink()) {
            // 创建link元素
            Element link = this.createLink(document);
            // 添加inline元素
            link.appendChild(this.createInline(document));
            // 添加link元素
            block.appendChild(link);
        }
        // 否则添加inline元素
        else {
            // 添加inline元素
            block.appendChild(this.createInline(document));
        }
        // 返回block元素
        return block;
    }

    /**
     * 初始化
     *
     * @param param 文本基础参数
     * @return 返回文本组件
     */
    Text init(TextBaseParam param) {
        this.param.init(param);
        return this;
    }

    /**
     * 创建link元素
     *
     * @param document fo文档
     * @return 返回link元素
     */
    private Element createLink(Document document) {
        // 创建link元素
        Element link = document.createElement(TemplateTags.BASIC_LINK);
        // 设置内部地址
        Optional.ofNullable(this.param.getLinkInternalDestination()).ifPresent(v -> link.setAttribute(TemplateAttributes.INTERNAL_DESTINATION, v.intern()));
        // 设置外部地址
        Optional.ofNullable(this.param.getLinkExternalDestination()).ifPresent(v -> link.setAttribute(TemplateAttributes.EXTERNAL_DESTINATION, v.intern()));
        // 返回link元素
        return link;
    }

    /**
     * 创建inline元素
     *
     * @param document fo文档
     */
    private Element createInline(Document document) {
        // 创建inline元素
        Element inline = document.createElement(TemplateTags.IN_LINE);
        // 设置上下左右填充
        Optional.ofNullable(this.param.getPadding()).ifPresent(v -> inline.setAttribute(TemplateAttributes.PADDING, v.intern().toLowerCase()));
        // 设置上填充
        Optional.ofNullable(this.param.getPaddingTop()).ifPresent(v -> inline.setAttribute(TemplateAttributes.PADDING_TOP, v.intern().toLowerCase()));
        // 设置下填充
        Optional.ofNullable(this.param.getPaddingBottom()).ifPresent(v -> inline.setAttribute(TemplateAttributes.PADDING_BOTTOM, v.intern().toLowerCase()));
        // 设置左填充
        Optional.ofNullable(this.param.getPaddingLeft()).ifPresent(v -> inline.setAttribute(TemplateAttributes.PADDING_LEFT, v.intern().toLowerCase()));
        // 设置右填充
        Optional.ofNullable(this.param.getPaddingRight()).ifPresent(v -> inline.setAttribute(TemplateAttributes.PADDING_RIGHT, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(this.param.getTextIndent()).ifPresent(v -> inline.setAttribute(TemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> inline.setAttribute(TemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> inline.setAttribute(TemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> inline.setAttribute(TemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> inline.setAttribute(TemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> inline.setAttribute(TemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> inline.setAttribute(TemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> inline.setAttribute(TemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 设置垂直对齐
        Optional.ofNullable(this.param.getVerticalAlign()).ifPresent(v -> inline.setAttribute(TemplateAttributes.VERTICAL_ALIGN, v.intern().toLowerCase()));
        // 如果包含下划线，则设置下划线
        if (this.param.hasUnderLine) {
            // 获取下划线设置
            String borderBottom = Optional.ofNullable(this.param.getUnderLineWidth()).orElse("1pt")
                    + " solid " +
                    Optional.ofNullable(this.param.getUnderLineColor()).orElse("");
            // 设置下划线
            inline.setAttribute(TemplateAttributes.BORDER_BOTTOM, borderBottom.intern());
        }
        // 设置背景颜色
        Optional.ofNullable(this.param.getBackgroundColor()).ifPresent(v -> inline.setAttribute(TemplateAttributes.BACKGROUND_COLOR, v.intern().toLowerCase()));
        // 设置文本
        inline.setTextContent(this.param.getText());
        // 返回inline元素
        return inline;
    }
}
