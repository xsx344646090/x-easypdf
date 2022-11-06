package wiki.xsx.core.pdf.template.component.block;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-块组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/11/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplateBlock implements XEasyPdfTemplateComponent {

    /**
     * 块参数
     */
    private final XEasyPdfTemplateBlockParam param = new XEasyPdfTemplateBlockParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回文本扩展组件
     */
    private XEasyPdfTemplateBlock setInitialCapacity(int initialCapacity) {
        this.param.setComponents(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回块组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateBlock setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setLetterSpacing(String spacing) {
        this.param.setLetterSpacing(spacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setWordSpacing(String spacing) {
        this.param.setWordSpacing(spacing);
        return this;
    }

    /**
     * 设置单词间距
     * <p>normal：正常</p>
     * <p>break-all：字符换行</p>
     * <p>keep-all：整词换行</p>
     *
     * @param wordBreak 单词换行
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setWordBreak(String wordBreak) {
        this.param.setWordBreak(wordBreak);
        return this;
    }

    /**
     * 设置空白空间
     * <p>normal：正常</p>
     * <p>pre：保留空格</p>
     * <p>nowrap：合并空格</p>
     *
     * @param whiteSpace 空白空间
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置文本缩进
     *
     * @param indent 缩进值
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setTextIndent(String indent) {
        this.param.setTextIndent(indent);
        return this;
    }

    /**
     * 设置段前缩进
     *
     * @param indent 缩进值
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setStartIndent(String indent) {
        this.param.setStartIndent(indent);
        return this;
    }

    /**
     * 设置段后缩进
     *
     * @param indent 缩进值
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setEndIndent(String indent) {
        this.param.setEndIndent(indent);
        return this;
    }

    /**
     * 设置段前空白
     *
     * @param space 空白值
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setSpaceBefore(String space) {
        this.param.setSpaceBefore(space);
        return this;
    }

    /**
     * 设置段后空白
     *
     * @param space 空白值
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setSpaceAfter(String space) {
        this.param.setSpaceAfter(space);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontFamily(String fontFamily) {
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
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontStyle(String fontStyle) {
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
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setFontColor(Color fontColor) {
        this.param.setColor(fontColor);
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
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置垂直对齐（用于角标设置）
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     *
     * @param style 垂直样式
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setVerticalStyle(String style) {
        this.param.setVerticalAlign(style);
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
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setBreakBefore(String breakBefore) {
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
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 添加组件列表
     *
     * @param components 组件列表
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock addComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getComponents(), v));
        return this;
    }

    /**
     * 添加组件列表
     *
     * @param components 组件列表
     * @return 返回块组件
     */
    public XEasyPdfTemplateBlock addComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getComponents()::addAll);
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
        // 创建block元素
        Element block = this.createBlockElement(document, param);
        // 设置行间距
        Optional.ofNullable(param.getLeading()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(param.getLetterSpacing()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(param.getWordSpacing()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置单词换行
        Optional.ofNullable(param.getWordBreak()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WORD_BREAK, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(param.getTextIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置端前缩进
        Optional.ofNullable(param.getStartIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.START_INDENT, v.intern().toLowerCase()));
        // 设置端后缩进
        Optional.ofNullable(param.getEndIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.END_INDENT, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(param.getTextIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern().toLowerCase()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(block, v));
        // 设置垂直对齐
        Optional.ofNullable(this.param.getVerticalAlign()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.VERTICAL_ALIGN, v.intern().toLowerCase()));
        // 设置文本
        Optional.ofNullable(this.param.getText()).ifPresent(block::setTextContent);
        // 添加组件
        Optional.ofNullable(this.param.getComponents()).ifPresent(v -> v.forEach(e -> this.appendChild(block, e.createElement(document))));
        // 返回block元素
        return block;
    }
}
