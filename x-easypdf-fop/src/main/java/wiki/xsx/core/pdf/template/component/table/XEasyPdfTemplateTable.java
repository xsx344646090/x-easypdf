package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.*;
import java.util.Optional;

/**
 * pdf模板-表格组件
 * <p>fo:table标签</p>
 *
 * @author xsx
 * @date 2022/8/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplateTable implements XEasyPdfTemplateComponent {

    /**
     * 表格参数
     */
    private final XEasyPdfTemplateTableParam param = new XEasyPdfTemplateTableParam();

    /**
     * 设置表头
     *
     * @param header 表头
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setHeader(XEasyPdfTemplateTableHeader header) {
        this.param.setHeader(header);
        return this;
    }

    /**
     * 设置表格体
     *
     * @param body 表格体
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBody(XEasyPdfTemplateTableBody body) {
        this.param.setBody(body);
        return this;
    }

    /**
     * 设置表尾
     *
     * @param footer 表尾
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFooter(XEasyPdfTemplateTableFooter footer) {
        this.param.setFooter(footer);
        return this;
    }

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorder(String border) {
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
    public XEasyPdfTemplateTable setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置边框折叠
     *
     * @param borderCollapse 边框折叠
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorderCollapse(String borderCollapse) {
        this.param.setBorderCollapse(borderCollapse);
        return this;
    }

    /**
     * 设置边框间距
     *
     * @param borderSpacing 边框间距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorderSpacing(String borderSpacing) {
        this.param.setBorderSpacing(borderSpacing);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回表格组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateTable setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setWordSpacing(String spacing) {
        this.param.setWordSpacing(spacing);
        return this;
    }

    /**
     * 设置单词换行
     * <p>normal：正常</p>
     * <p>break-all：字符换行</p>
     * <p>keep-all：整词换行</p>
     *
     * @param wordBreak 单词换行
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setWordBreak(String wordBreak) {
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
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFontFamily(String fontFamily) {
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
    public XEasyPdfTemplateTable setFontStyle(String fontStyle) {
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
    public XEasyPdfTemplateTable setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFontColor(Color fontColor) {
        this.param.setColor(fontColor);
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
    public XEasyPdfTemplateTable setHorizontalStyle(String style) {
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
    public XEasyPdfTemplateTable setVerticalStyle(String style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param minRowHeight 最小行高
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMinRowHeight(String minRowHeight) {
        this.param.setMinRowHeight(minRowHeight);
        return this;
    }

    /**
     * 设置最小列宽
     *
     * @param minColumnWidth 最小列宽
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setMinColumnWidth(String minColumnWidth) {
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
    public XEasyPdfTemplateTable setBreakBefore(String breakBefore) {
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
    public XEasyPdfTemplateTable setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启自动省略表头
     * <p>注：分页时，省略表头</p>
     *
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable enableAutoOmitHeader() {
        this.param.setIsAutoOmitHeader(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自动省略表尾
     * <p>注：分页时，省略表尾</p>
     *
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable enableAutoOmitFooter() {
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
        Element block = this.createBlockElement(document, this.param);
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
        Element table = document.createElement(XEasyPdfTemplateTags.TABLE);
        // 添加表头
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableHeader(document));
        // 添加表格体
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableBody(document));
        // 添加表尾
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableFooter(document));
        // 设置垂直样式
        Optional.ofNullable(this.param.getVerticalStyle()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.DISPLAY_ALIGN, v.intern().toLowerCase()));
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.WIDTH, v.intern().toLowerCase()));
        // 设置高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.HEIGHT, v.intern().toLowerCase()));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER, v.intern().toLowerCase()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER_STYLE, v.intern().toLowerCase()));
        // 设置边框折叠
        Optional.ofNullable(this.param.getBorderCollapse()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER_COLLAPSE, v.intern().toLowerCase()));
        // 设置边框间距
        Optional.ofNullable(this.param.getBorderSpacing()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER_SPACING, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern().toLowerCase()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(table, v));
        // 设置行间距
        Optional.ofNullable(param.getLeading()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(param.getLetterSpacing()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(param.getWordSpacing()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置单词换行
        Optional.ofNullable(param.getWordBreak()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.WORD_BREAK, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(param.getWhiteSpace()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置是否自动省略表头
        Optional.ofNullable(param.getIsAutoOmitHeader()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.TABLE_OMIT_HEADER_AT_BREAK, v.toString().intern().toLowerCase()));
        // 设置是否自动省略表尾
        Optional.ofNullable(param.getIsAutoOmitFooter()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.TABLE_OMIT_FOOTER_AT_BREAK, v.toString().intern().toLowerCase()));
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
        XEasyPdfTemplateTableHeader header = this.param.getHeader();
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
        XEasyPdfTemplateTableBody body = this.param.getBody();
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
        XEasyPdfTemplateTableFooter footer = this.param.getFooter();
        // 如果表尾为空则返回空，否则返回表尾元素
        return footer == null ? null : footer.setMinColumnWidth(this.param.getMinColumnWidth()).setMinRowHeight(this.param.getMinRowHeight()).createElement(document);
    }
}
