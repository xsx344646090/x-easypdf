package org.dromara.pdf.fop.core.doc.component.table;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-表格行组件
 * <p>fo:table-row标签</p>
 *
 * @author xsx
 * @date 2022/8/23
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
public class TableRow {

    /**
     * 表格行参数
     */
    private final TableRowParam param = new TableRowParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回表格行组件
     */
    private TableRow setInitialCapacity(int initialCapacity) {
        this.param.setCells(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格行组件
     */
    public TableRow setBorder(String border) {
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
     * @return 返回表格行组件
     */
    public TableRow setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回表格行组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public TableRow setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回表格行组件
     */
    public TableRow setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回表格行组件
     */
    public TableRow setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回表格行组件
     */
    public TableRow setWordSpacing(String spacing) {
        this.param.setWordSpacing(spacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回表格行组件
     */
    public TableRow setFontFamily(String fontFamily) {
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
     * @return 返回表格行组件
     */
    public TableRow setFontStyle(String fontStyle) {
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
     * @return 返回表格行组件
     */
    public TableRow setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格行组件
     */
    public TableRow setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回表格行组件
     */
    public TableRow setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回表格行组件
     */
    public TableRow setFontColor(String color) {
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
     * @return 返回表格行组件
     */
    public TableRow setHorizontalStyle(String style) {
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
     * @return 返回表格行组件
     */
    public TableRow setVerticalStyle(String style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 设置最小列宽
     *
     * @param minColumnWidth 最小列宽
     * @return 返回表格行组件
     */
    public TableRow setMinColumnWidth(String minColumnWidth) {
        if (this.param.getMinColumnWidth() == null) {
            this.param.setMinColumnWidth(minColumnWidth);
        }
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param minRowHeight 最小行高
     * @return 返回表格行组件
     */
    public TableRow setMinRowHeight(String minRowHeight) {
        if (this.param.getMinRowHeight() == null) {
            this.param.setMinRowHeight(minRowHeight);
        }
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
     * @return 返回表格行组件
     */
    public TableRow setBreakBefore(String breakBefore) {
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
     * @return 返回表格行组件
     */
    public TableRow setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回表格行组件
     */
    public TableRow enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回表格行组件
     */
    public TableRow enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回表格行组件
     */
    public TableRow enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格列表
     * @return 返回表格行组件
     */
    public TableRow addCell(TableCell... cells) {
        Optional.ofNullable(cells).ifPresent(v -> Collections.addAll(this.param.getCells(), v));
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格列表
     * @return 返回表格行组件
     */
    public TableRow addCell(List<TableCell> cells) {
        Optional.ofNullable(cells).ifPresent(this.param.getCells()::addAll);
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    public Element createElement(Document document) {
        // 创建tableRow元素
        Element tableRow = document.createElement(TemplateTags.TABLE_ROW);
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.BORDER, v.intern().toLowerCase()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.BORDER_STYLE, v.intern().toLowerCase()));
        // 设置水平样式
        Optional.ofNullable(this.param.getHorizontalStyle()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.TEXT_ALIGN, v.intern().toLowerCase()));
        // 设置垂直样式
        Optional.ofNullable(this.param.getVerticalStyle()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.DISPLAY_ALIGN, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 设置行间距
        Optional.ofNullable(this.param.getLeading()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(this.param.getLetterSpacing()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(this.param.getWordSpacing()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置分页符-前
        Optional.ofNullable(this.param.getBreakBefore()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.BREAK_BEFORE, v.intern().toLowerCase()));
        // 设置分页符-后
        Optional.ofNullable(this.param.getBreakAfter()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.BREAK_AFTER, v.intern().toLowerCase()));
        // 设置分页时保持一起
        Optional.ofNullable(this.param.getKeepTogether()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.KEEP_TOGETHER, v.intern().toLowerCase()));
        // 设置分页时与上一个保持一起
        Optional.ofNullable(this.param.getKeepWithPrevious()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.KEEP_WITH_PREVIOUS, v.intern().toLowerCase()));
        // 设置分页时与下一个保持一起
        Optional.ofNullable(this.param.getKeepWithNext()).ifPresent(v -> tableRow.setAttribute(TemplateAttributes.KEEP_WITH_NEXT, v.intern().toLowerCase()));
        // 遍历并添加单元格
        this.param.getCells().forEach(v -> tableRow.appendChild(v.setWidth(this.param.getMinColumnWidth()).setHeight(this.param.getMinRowHeight()).createElement(document)));
        // 返回tableRow元素
        return tableRow;
    }
}
