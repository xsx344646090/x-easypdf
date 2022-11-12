package wiki.xsx.core.pdf.template.doc.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-文本扩展组件
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
public class XEasyPdfTemplateTextExtend extends XEasyPdfTemplateTextBase {

    /**
     * 文本扩展参数
     */
    private final XEasyPdfTemplateTextExtendParam param = new XEasyPdfTemplateTextExtendParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回文本扩展组件
     */
    private XEasyPdfTemplateTextExtend setInitialCapacity(int initialCapacity) {
        this.param.setTextList(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回文本扩展组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateTextExtend setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setWordSpacing(String spacing) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setWordBreak(String wordBreak) {
        this.param.setWordBreak(wordBreak);
        return this;
    }

    /**
     * 设置文本缩进
     *
     * @param indent 缩进值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setTextIndent(String indent) {
        this.param.setTextIndent(indent);
        return this;
    }

    /**
     * 设置段前缩进
     *
     * @param indent 缩进值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setStartIndent(String indent) {
        this.param.setStartIndent(indent);
        return this;
    }

    /**
     * 设置段后缩进
     *
     * @param indent 缩进值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setEndIndent(String indent) {
        this.param.setEndIndent(indent);
        return this;
    }

    /**
     * 设置段前空白
     *
     * @param space 空白值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setSpaceBefore(String space) {
        this.param.setSpaceBefore(space);
        return this;
    }

    /**
     * 设置段后空白
     *
     * @param space 空白值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setSpaceAfter(String space) {
        this.param.setSpaceAfter(space);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontFamily(String fontFamily) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontStyle(String fontStyle) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontColor(String color) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setBreakBefore(String breakBefore) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 设置背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setBackgroundColor(String color) {
        this.param.setBackgroundColor(color);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 添加文本组件
     *
     * @param texts 文本组件列表
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend addText(XEasyPdfTemplateText... texts) {
        Optional.ofNullable(texts).ifPresent(v -> Collections.addAll(this.param.getTextList(), v));
        return this;
    }

    /**
     * 添加文本组件
     *
     * @param texts 文本组件列表
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend addText(List<XEasyPdfTemplateText> texts) {
        Optional.ofNullable(texts).ifPresent(this.param.getTextList()::addAll);
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
        if (this.param.getTextList() == null) {
            // 返回空元素
            return null;
        }
        // 初始化block元素
        Element block = this.initBlock(document, this.param);
        // 添加子元素
        this.param.getTextList().forEach(
                // 遍历元素
                v -> Optional.ofNullable(v.init(this.param).createElement(document)).ifPresent(
                        // 添加元素
                        e -> block.appendChild(e.getFirstChild())
                )
        );
        // 返回block元素
        return block;
    }
}
