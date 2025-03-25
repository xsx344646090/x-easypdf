package org.dromara.pdf.fop.core.doc.component.table;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.dromara.pdf.fop.handler.ElementHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-表格组件
 * <p>fo:table标签</p>
 *
 * @author xsx
 * @date 2022/8/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Table implements Component {

    /**
     * 表格参数
     */
    private final TableParam param = new TableParam();

    /**
     * 设置表头
     *
     * @param header 表头
     * @return 返回表格组件
     */
    public Table setHeader(TableHeader header) {
        this.param.setHeader(header);
        return this;
    }

    /**
     * 设置表格体
     *
     * @param body 表格体
     * @return 返回表格组件
     */
    public Table setBody(TableBody body) {
        this.param.setBody(body);
        return this;
    }

    /**
     * 设置表尾
     *
     * @param footer 表尾
     * @return 返回表格组件
     */
    public Table setFooter(TableFooter footer) {
        this.param.setFooter(footer);
        return this;
    }

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回表格组件
     */
    public Table setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回表格组件
     */
    public Table setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回表格组件
     */
    public Table setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回表格组件
     */
    public Table setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回表格组件
     */
    public Table setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回表格组件
     */
    public Table setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回表格组件
     */
    public Table setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回表格组件
     */
    public Table setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回表格组件
     */
    public Table setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回表格组件
     */
    public Table setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回表格组件
     */
    public Table setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回表格组件
     */
    public Table setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回表格组件
     */
    public Table setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格组件
     */
    public Table setBorder(String border) {
        this.param.setBorder(border);
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
     * @return 返回表格组件
     */
    public Table setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置边框折叠
     * <p>collapse：合并</p>
     * <p>separate：分开</p>
     *
     * @param borderCollapse 边框折叠
     * @return 返回表格组件
     */
    public Table setBorderCollapse(String borderCollapse) {
        this.param.setBorderCollapse(borderCollapse);
        return this;
    }

    /**
     * 设置边框间距
     *
     * @param borderSpacing 边框间距
     * @return 返回表格组件
     */
    public Table setBorderSpacing(String borderSpacing) {
        this.param.setBorderSpacing(borderSpacing);
        return this;
    }

    /**
     * 设置边框半径
     *
     * @param borderRadius 边框半径
     * @return 返回表格组件
     */
    public Table setBorderRadius(String borderRadius) {
        this.param.setBorderRadius(borderRadius);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回表格组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public Table setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回表格组件
     */
    public Table setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回表格组件
     */
    public Table setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回表格组件
     */
    public Table setWordSpacing(String spacing) {
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
     * @return 返回表格组件
     */
    public Table setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回表格组件
     */
    public Table setFontFamily(String fontFamily) {
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
     * @return 返回表格组件
     */
    public Table setFontStyle(String fontStyle) {
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
     * @return 返回表格组件
     */
    public Table setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格组件
     */
    public Table setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回表格组件
     */
    public Table setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回表格组件
     */
    public Table setFontColor(String color) {
        this.param.setColor(color);
        return this;
    }

    /**
     * 设置文本水平样式
     * <p>left：居左</p>
     * <p>center：居中</p>
     * <p>right：居右</p>
     * <p>justify：两端对齐</p>
     *
     * @param style 水平样式
     * @return 返回表格组件
     */
    public Table setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置文本垂直样式
     * <p>before：居上</p>
     * <p>center：居中</p>
     * <p>after：居下</p>
     *
     * @param style 垂直样式
     * @return 返回表格组件
     */
    public Table setVerticalStyle(String style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param minRowHeight 最小行高
     * @return 返回表格组件
     */
    public Table setMinRowHeight(String minRowHeight) {
        this.param.setMinRowHeight(minRowHeight);
        return this;
    }

    /**
     * 设置最小列宽
     *
     * @param minColumnWidth 最小列宽
     * @return 返回表格组件
     */
    public Table setMinColumnWidth(String minColumnWidth) {
        this.param.setMinColumnWidth(minColumnWidth);
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
     * @return 返回表格组件
     */
    public Table setBreakBefore(String breakBefore) {
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
     * @return 返回表格组件
     */
    public Table setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 设置背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回表格组件
     */
    public Table setBackgroundColor(String color) {
        this.param.setBackgroundColor(color);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回表格组件
     */
    public Table enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回表格组件
     */
    public Table enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回表格组件
     */
    public Table enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启自动省略表头
     * <p>注：分页时，省略表头</p>
     *
     * @return 返回表格组件
     */
    public Table enableAutoOmitHeader() {
        this.param.setIsAutoOmitHeader(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自动省略表尾
     * <p>注：分页时，省略表尾</p>
     *
     * @return 返回表格组件
     */
    public Table enableAutoOmitFooter() {
        this.param.setIsAutoOmitFooter(Boolean.TRUE);
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
        Element block = this.createBlockElement(document, this.param, false);
        // 添加table元素
        block.appendChild(this.createTable(document));
        // 返回block元素
        return block;
    }

    /**
     * 创建table元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTable(Document document) {
        // 创建table元素
        Element table = document.createElement(TemplateTags.TABLE);
        // 添加表头
        ElementHandler.appendChild(table, this.createTableHeader(document));
        // 添加表尾
        ElementHandler.appendChild(table, this.createTableFooter(document));
        // 添加表格体
        ElementHandler.appendChild(table, this.createTableBody(document));
        // 设置上下左右边距
        Optional.ofNullable(param.getMargin()).ifPresent(v -> table.setAttribute(TemplateAttributes.MARGIN, v.intern().toLowerCase()));
        // 设置上边距
        Optional.ofNullable(param.getMarginTop()).ifPresent(v -> table.setAttribute(TemplateAttributes.MARGIN_TOP, v.intern().toLowerCase()));
        // 设置下边距
        Optional.ofNullable(param.getMarginBottom()).ifPresent(v -> table.setAttribute(TemplateAttributes.MARGIN_BOTTOM, v.intern().toLowerCase()));
        // 设置左边距
        Optional.ofNullable(param.getMarginLeft()).ifPresent(v -> table.setAttribute(TemplateAttributes.MARGIN_LEFT, v.intern().toLowerCase()));
        // 设置右边距
        Optional.ofNullable(param.getMarginRight()).ifPresent(v -> table.setAttribute(TemplateAttributes.MARGIN_RIGHT, v.intern().toLowerCase()));
        // 设置垂直样式
        Optional.ofNullable(this.param.getVerticalStyle()).ifPresent(v -> table.setAttribute(TemplateAttributes.DISPLAY_ALIGN, v.intern().toLowerCase()));
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> table.setAttribute(TemplateAttributes.WIDTH, v.intern().toLowerCase()));
        // 设置高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> table.setAttribute(TemplateAttributes.HEIGHT, v.intern().toLowerCase()));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> table.setAttribute(TemplateAttributes.BORDER, v.intern().toLowerCase()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> table.setAttribute(TemplateAttributes.BORDER_STYLE, v.intern().toLowerCase()));
        // 设置边框折叠
        Optional.ofNullable(this.param.getBorderCollapse()).ifPresent(v -> table.setAttribute(TemplateAttributes.BORDER_COLLAPSE, v.intern().toLowerCase()));
        // 设置边框间距
        Optional.ofNullable(this.param.getBorderSpacing()).ifPresent(v -> table.setAttribute(TemplateAttributes.BORDER_SPACING, v.intern().toLowerCase()));
        // 设置边框半径
        Optional.ofNullable(this.param.getBorderRadius()).ifPresent(v -> table.setAttribute(TemplateAttributes.BORDER_RADIUS, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> table.setAttribute(TemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> table.setAttribute(TemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> table.setAttribute(TemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> table.setAttribute(TemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> table.setAttribute(TemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> table.setAttribute(TemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> table.setAttribute(TemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 设置行间距
        Optional.ofNullable(param.getLeading()).ifPresent(v -> table.setAttribute(TemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(param.getLetterSpacing()).ifPresent(v -> table.setAttribute(TemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(param.getWordSpacing()).ifPresent(v -> table.setAttribute(TemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(param.getWhiteSpace()).ifPresent(v -> table.setAttribute(TemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置是否自动省略表头
        Optional.ofNullable(param.getIsAutoOmitHeader()).ifPresent(v -> table.setAttribute(TemplateAttributes.TABLE_OMIT_HEADER_AT_BREAK, v.toString().intern().toLowerCase()));
        // 设置是否自动省略表尾
        Optional.ofNullable(param.getIsAutoOmitFooter()).ifPresent(v -> table.setAttribute(TemplateAttributes.TABLE_OMIT_FOOTER_AT_BREAK, v.toString().intern().toLowerCase()));
        // 设置背景颜色
        Optional.ofNullable(param.getBackgroundColor()).ifPresent(v -> table.setAttribute(TemplateAttributes.BACKGROUND_COLOR, v.intern().toLowerCase()));
        // 设置表格布局
        table.setAttribute(TemplateAttributes.TABLE_LAYOUT, "fixed");
        // 返回table元素
        return table;
    }

    /**
     * 创建tableHeader元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableHeader(Document document) {
        // 获取表头
        TableHeader header = this.param.getHeader();
        // 如果表头为空则返回空，否则返回表头元素
        return header == null ? null : header.setMinColumnWidth(this.param.getMinColumnWidth()).setMinRowHeight(this.param.getMinRowHeight()).createElement(document);
    }

    /**
     * 创建tableBody元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableBody(Document document) {
        // 获取表格体
        TableBody body = this.param.getBody();
        // 如果表格体为空，则提示错误信息
        if (body == null) {
            // 提示错误信息
            throw new IllegalArgumentException("the table body can not be null");
        }
        // 返回表格体元素
        return body.setMinColumnWidth(this.param.getMinColumnWidth()).setMinRowHeight(this.param.getMinRowHeight()).createElement(document);
    }

    /**
     * 创建tableFooter元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableFooter(Document document) {
        // 获取表尾
        TableFooter footer = this.param.getFooter();
        // 如果表尾为空则返回空，否则返回表尾元素
        return footer == null ? null : footer.setMinColumnWidth(this.param.getMinColumnWidth()).setMinRowHeight(this.param.getMinRowHeight()).createElement(document);
    }
}
