package org.dromara.pdf.fop.doc.component.block;

import org.dromara.pdf.fop.TemplateAttributes;
import org.dromara.pdf.fop.TemplateTags;
import org.dromara.pdf.fop.doc.component.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-块容器组件
 * <p>fo:block-container标签</p>
 *
 * @author xsx
 * @date 2022/11/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class BlockContainer implements Component {

    /**
     * 块参数
     */
    private final BlockContainerParam param = new BlockContainerParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回文本扩展组件
     */
    private BlockContainer setInitialCapacity(int initialCapacity) {
        this.param.setComponents(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回块容器组件
     */
    public BlockContainer setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回块容器组件
     */
    public BlockContainer setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回块容器组件
     */
    public BlockContainer setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回块容器组件
     */
    public BlockContainer setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回块容器组件
     */
    public BlockContainer setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回块容器组件
     */
    public BlockContainer setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回块容器组件
     */
    public BlockContainer setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回块容器组件
     */
    public BlockContainer setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回块容器组件
     */
    public BlockContainer setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回块容器组件
     */
    public BlockContainer setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回块容器组件
     */
    public BlockContainer setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回块容器组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public BlockContainer setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回块容器组件
     */
    public BlockContainer setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回块容器组件
     */
    public BlockContainer setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回块容器组件
     */
    public BlockContainer setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回块容器组件
     */
    public BlockContainer setLetterSpacing(String spacing) {
        this.param.setLetterSpacing(spacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回块容器组件
     */
    public BlockContainer setWordSpacing(String spacing) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置空白空间折叠
     * <p>true：是</p>
     * <p>false：否</p>
     *
     * @param whiteSpaceCollapse 折叠
     * @return 返回块容器组件
     */
    public BlockContainer setWhiteSpaceCollapse(String whiteSpaceCollapse) {
        this.param.setWhiteSpaceCollapse(whiteSpaceCollapse);
        return this;
    }

    /**
     * 设置文本缩进
     *
     * @param indent 缩进值
     * @return 返回块容器组件
     */
    public BlockContainer setTextIndent(String indent) {
        this.param.setTextIndent(indent);
        return this;
    }

    /**
     * 设置段前缩进
     *
     * @param indent 缩进值
     * @return 返回块容器组件
     */
    public BlockContainer setStartIndent(String indent) {
        this.param.setStartIndent(indent);
        return this;
    }

    /**
     * 设置段后缩进
     *
     * @param indent 缩进值
     * @return 返回块容器组件
     */
    public BlockContainer setEndIndent(String indent) {
        this.param.setEndIndent(indent);
        return this;
    }

    /**
     * 设置段前空白
     *
     * @param space 空白值
     * @return 返回块容器组件
     */
    public BlockContainer setSpaceBefore(String space) {
        this.param.setSpaceBefore(space);
        return this;
    }

    /**
     * 设置段后空白
     *
     * @param space 空白值
     * @return 返回块容器组件
     */
    public BlockContainer setSpaceAfter(String space) {
        this.param.setSpaceAfter(space);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回块容器组件
     */
    public BlockContainer setFontFamily(String fontFamily) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setFontStyle(String fontStyle) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回块容器组件
     */
    public BlockContainer setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回块容器组件
     */
    public BlockContainer setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回块容器组件
     */
    public BlockContainer setFontColor(String color) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置垂直对齐（用于角标设置）
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     *
     * @param style 垂直样式
     * @return 返回块容器组件
     */
    public BlockContainer setVerticalStyle(String style) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setBreakBefore(String breakBefore) {
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
     * @return 返回块容器组件
     */
    public BlockContainer setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 设置背景
     *
     * @param background 背景
     * @return 返回块容器组件
     */
    public BlockContainer setBackground(String background) {
        this.param.setBackground(background);
        return this;
    }

    /**
     * 设置背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundImage(String image) {
        this.param.setBackgroundImage(image);
        return this;
    }

    /**
     * 设置背景图片宽度
     *
     * @param width 图片宽度
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundImageWidth(String width) {
        this.param.setBackgroundImageWidth(width);
        return this;
    }

    /**
     * 设置背景图片高度
     *
     * @param height 图片高度
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundImageHeight(String height) {
        this.param.setBackgroundImageHeight(height);
        return this;
    }

    /**
     * 设置背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundAttachment(String attachment) {
        this.param.setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundColor(String color) {
        this.param.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundPosition(String position) {
        this.param.setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置背景图片水平定位
     *
     * @param position 定位
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundHorizontalPosition(String position) {
        this.param.setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置背景图片垂直定位
     *
     * @param position 定位
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundVerticalPosition(String position) {
        this.param.setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回块容器组件
     */
    public BlockContainer setBackgroundRepeat(String repeat) {
        this.param.setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回块容器组件
     */
    public BlockContainer setBorder(String border) {
        this.param.setBorder(border);
        return this;
    }

    /**
     * 设置边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBorderColor(String borderColor) {
        this.param.setBorderColor(borderColor);
        return this;
    }

    /**
     * 设置边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回块容器组件
     */
    public BlockContainer setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置边框折叠
     * <p>collapse：合并</p>
     * <p>separate：分开</p>
     *
     * @param borderCollapse 边框折叠
     * @return 返回块容器组件
     */
    public BlockContainer setBorderCollapse(String borderCollapse) {
        this.param.setBorderCollapse(borderCollapse);
        return this;
    }

    /**
     * 设置上边框
     *
     * @param border 边框
     * @return 返回块容器组件
     */
    public BlockContainer setBorderTop(String border) {
        this.param.setBorderTop(border);
        return this;
    }

    /**
     * 设置上边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBorderTopColor(String borderColor) {
        this.param.setBorderTopColor(borderColor);
        return this;
    }

    /**
     * 设置上边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回块容器组件
     */
    public BlockContainer setBorderTopStyle(String borderStyle) {
        this.param.setBorderTopStyle(borderStyle);
        return this;
    }

    /**
     * 设置下边框
     *
     * @param border 边框
     * @return 返回块容器组件
     */
    public BlockContainer setBorderBottom(String border) {
        this.param.setBorderBottom(border);
        return this;
    }

    /**
     * 设置下边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBorderBottomColor(String borderColor) {
        this.param.setBorderBottomColor(borderColor);
        return this;
    }

    /**
     * 设置下边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回块容器组件
     */
    public BlockContainer setBorderBottomStyle(String borderStyle) {
        this.param.setBorderBottomStyle(borderStyle);
        return this;
    }

    /**
     * 设置左边框
     *
     * @param border 边框
     * @return 返回块容器组件
     */
    public BlockContainer setBorderLeft(String border) {
        this.param.setBorderLeft(border);
        return this;
    }

    /**
     * 设置左边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBorderLeftColor(String borderColor) {
        this.param.setBorderLeftColor(borderColor);
        return this;
    }

    /**
     * 设置左边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回块容器组件
     */
    public BlockContainer setBorderLeftStyle(String borderStyle) {
        this.param.setBorderLeftStyle(borderStyle);
        return this;
    }

    /**
     * 设置右边框
     *
     * @param border 边框
     * @return 返回块容器组件
     */
    public BlockContainer setBorderRight(String border) {
        this.param.setBorderRight(border);
        return this;
    }

    /**
     * 设置右边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回块容器组件
     */
    public BlockContainer setBorderRightColor(String borderColor) {
        this.param.setBorderRightColor(borderColor);
        return this;
    }

    /**
     * 设置右边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回块容器组件
     */
    public BlockContainer setBorderRightStyle(String borderStyle) {
        this.param.setBorderRightStyle(borderStyle);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回块容器组件
     */
    public BlockContainer enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回块容器组件
     */
    public BlockContainer enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回块容器组件
     */
    public BlockContainer enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回块容器组件
     */
    public BlockContainer enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 添加组件列表
     *
     * @param components 组件列表
     * @return 返回块容器组件
     */
    public BlockContainer addComponent(Component... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getComponents(), v));
        return this;
    }

    /**
     * 添加组件列表
     *
     * @param components 组件列表
     * @return 返回块容器组件
     */
    public BlockContainer addComponent(List<Component> components) {
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
        // 创建blockContainer元素
        Element blockContainer = document.createElement(TemplateTags.BLOCK_CONTAINER);
        // 创建block元素
        Element block = this.createBlockElement(document, this.param);
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.WIDTH, v.intern().toLowerCase()));
        // 设置高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.HEIGHT, v.intern().toLowerCase()));
        // 设置行间距
        Optional.ofNullable(this.param.getLeading()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(this.param.getLetterSpacing()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(this.param.getWordSpacing()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(this.param.getTextIndent()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置端前缩进
        Optional.ofNullable(this.param.getStartIndent()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.START_INDENT, v.intern().toLowerCase()));
        // 设置端后缩进
        Optional.ofNullable(this.param.getEndIndent()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.END_INDENT, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(this.param.getTextIndent()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 设置垂直对齐
        Optional.ofNullable(this.param.getVerticalAlign()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.VERTICAL_ALIGN, v.intern().toLowerCase()));
        // 设置背景颜色
        Optional.ofNullable(this.param.getBackgroundColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_COLOR, v.intern().toLowerCase()));
        // 设置背景
        Optional.ofNullable(this.param.getBackground()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND, v.intern().toLowerCase()));
        // 设置背景图片
        Optional.ofNullable(this.param.getBackgroundImage()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_IMAGE, v.intern()));
        // 设置背景图片宽度
        Optional.ofNullable(this.param.getBackgroundImageWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_IMAGE_WIDTH, v.intern().toLowerCase()));
        // 设置背景图片高度
        Optional.ofNullable(this.param.getBackgroundImageHeight()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_IMAGE_HEIGHT, v.intern().toLowerCase()));
        // 设置背景附件
        Optional.ofNullable(this.param.getBackgroundAttachment()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_ATTACHMENT, v.intern().toLowerCase()));
        // 设置背景图片定位
        Optional.ofNullable(this.param.getBackgroundPosition()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_POSITION, v.intern().toLowerCase()));
        // 设置背景图片水平定位
        Optional.ofNullable(this.param.getBackgroundPositionHorizontal()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_POSITION_HORIZONTAL, v.intern().toLowerCase()));
        // 设置背景图片垂直定位
        Optional.ofNullable(this.param.getBackgroundPositionVertical()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_POSITION_VERTICAL, v.intern().toLowerCase()));
        // 设置背景图片重复
        Optional.ofNullable(this.param.getBackgroundRepeat()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BACKGROUND_REPEAT, v.intern().toLowerCase()));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER, v.intern().toLowerCase()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_STYLE, v.intern().toLowerCase()));
        // 设置边框颜色
        Optional.ofNullable(this.param.getBorderColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_COLOR, v.intern().toLowerCase()));
        // 设置边框宽度
        Optional.ofNullable(this.param.getBorderWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_WIDTH, v.intern().toLowerCase()));
        // 设置边框折叠
        Optional.ofNullable(this.param.getBorderCollapse()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_COLLAPSE, v.intern().toLowerCase()));
        // 设置上边框
        Optional.ofNullable(this.param.getBorderTop()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_TOP, v.intern().toLowerCase()));
        // 设置上边框样式
        Optional.ofNullable(this.param.getBorderTopStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_TOP_STYLE, v.intern().toLowerCase()));
        // 设置上边框颜色
        Optional.ofNullable(this.param.getBorderTopColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_TOP_COLOR, v.intern().toLowerCase()));
        // 设置上边框宽度
        Optional.ofNullable(this.param.getBorderTopWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_TOP_WIDTH, v.intern().toLowerCase()));
        // 设置下边框
        Optional.ofNullable(this.param.getBorderBottom()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_BOTTOM, v.intern().toLowerCase()));
        // 设置下边框样式
        Optional.ofNullable(this.param.getBorderBottomStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_BOTTOM_STYLE, v.intern().toLowerCase()));
        // 设置下边框颜色
        Optional.ofNullable(this.param.getBorderBottomColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_BOTTOM_COLOR, v.intern().toLowerCase()));
        // 设置下边框宽度
        Optional.ofNullable(this.param.getBorderBottomWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_BOTTOM_WIDTH, v.intern().toLowerCase()));
        // 设置左边框
        Optional.ofNullable(this.param.getBorderLeft()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_LEFT, v.intern().toLowerCase()));
        // 设置左边框样式
        Optional.ofNullable(this.param.getBorderLeftStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_LEFT_STYLE, v.intern().toLowerCase()));
        // 设置左边框颜色
        Optional.ofNullable(this.param.getBorderLeftColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_LEFT_COLOR, v.intern().toLowerCase()));
        // 设置左边框宽度
        Optional.ofNullable(this.param.getBorderLeftWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_LEFT_WIDTH, v.intern().toLowerCase()));
        // 设置右边框
        Optional.ofNullable(this.param.getBorderRight()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_RIGHT, v.intern().toLowerCase()));
        // 设置右边框样式
        Optional.ofNullable(this.param.getBorderRightStyle()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_RIGHT_STYLE, v.intern().toLowerCase()));
        // 设置右边框颜色
        Optional.ofNullable(this.param.getBorderRightColor()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_RIGHT_COLOR, v.intern().toLowerCase()));
        // 设置右边框宽度
        Optional.ofNullable(this.param.getBorderRightWidth()).ifPresent(v -> blockContainer.setAttribute(TemplateAttributes.BORDER_RIGHT_WIDTH, v.intern().toLowerCase()));
        // 添加组件
        this.param.getComponents().forEach(v -> this.appendChild(block, v.createElement(document)));
        // 添加block元素
        blockContainer.appendChild(block);
        // 返回blockContainer元素
        return blockContainer;
    }
}
