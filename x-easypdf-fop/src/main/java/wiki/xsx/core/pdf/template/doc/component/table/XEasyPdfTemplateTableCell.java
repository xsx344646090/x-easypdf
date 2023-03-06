package wiki.xsx.core.pdf.template.doc.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-表格单元格组件
 * <p>fo:table-cell标签</p>
 *
 * @author xsx
 * @date 2022/8/23
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfTemplateTableCell {

    /**
     * 单元格参数
     */
    private final XEasyPdfTemplateTableCellParam param = new XEasyPdfTemplateTableCellParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回表格单元格组件
     */
    private XEasyPdfTemplateTableCell setInitialCapacity(int initialCapacity) {
        this.param.setComponents(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setWidth(String width) {
        if (this.param.getWidth() == null) {
            this.param.setWidth(width);
        }
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setHeight(String height) {
        if (this.param.getHeight() == null) {
            this.param.setHeight(height);
        }
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorder(String border) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderColor(String borderColor) {
        this.param.setBorderColor(borderColor);
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderWidth(String borderWidth) {
        this.param.setBorderWidth(borderWidth);
        return this;
    }

    /**
     * 设置上边框
     *
     * @param border 边框
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderTop(String border) {
        this.param.setBorderTop(border);
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderTopStyle(String borderStyle) {
        this.param.setBorderTopStyle(borderStyle);
        return this;
    }

    /**
     * 设置上边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderTopColor(String borderColor) {
        this.param.setBorderTopColor(borderColor);
        return this;
    }

    /**
     * 设置上边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderTopWidth(String borderWidth) {
        this.param.setBorderTopWidth(borderWidth);
        return this;
    }

    /**
     * 设置下边框
     *
     * @param border 边框
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderBottom(String border) {
        this.param.setBorderBottom(border);
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderBottomStyle(String borderStyle) {
        this.param.setBorderBottomStyle(borderStyle);
        return this;
    }

    /**
     * 设置下边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderBottomColor(String borderColor) {
        this.param.setBorderBottomColor(borderColor);
        return this;
    }

    /**
     * 设置下边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderBottomWidth(String borderWidth) {
        this.param.setBorderBottomWidth(borderWidth);
        return this;
    }

    /**
     * 设置左边框
     *
     * @param border 边框
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderLeft(String border) {
        this.param.setBorderLeft(border);
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderLeftStyle(String borderStyle) {
        this.param.setBorderLeftStyle(borderStyle);
        return this;
    }

    /**
     * 设置左边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderLeftColor(String borderColor) {
        this.param.setBorderLeftColor(borderColor);
        return this;
    }

    /**
     * 设置左边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderLeftWidth(String borderWidth) {
        this.param.setBorderLeftWidth(borderWidth);
        return this;
    }

    /**
     * 设置右边框
     *
     * @param border 边框
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderRight(String border) {
        this.param.setBorderRight(border);
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderRightStyle(String borderStyle) {
        this.param.setBorderRightStyle(borderStyle);
        return this;
    }

    /**
     * 设置右边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param borderColor 边框颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderRightColor(String borderColor) {
        this.param.setBorderRightColor(borderColor);
        return this;
    }

    /**
     * 设置右边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderRightWidth(String borderWidth) {
        this.param.setBorderRightWidth(borderWidth);
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回表格单元格组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateTableCell setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置单词间距
     *
     * @param spacing 单词间距
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setWordSpacing(String spacing) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setWhiteSpace(String whiteSpace) {
        this.param.setWhiteSpace(whiteSpace);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontFamily(String fontFamily) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontStyle(String fontStyle) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setFontColor(String color) {
        this.param.setColor(color);
        return this;
    }

    /**
     * 设置合并行数
     *
     * @param rows 行数
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setRowSpan(Integer rows) {
        this.param.setRowSpan(rows);
        return this;
    }

    /**
     * 设置合并列数
     *
     * @param columns 列数
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setColumnSpan(Integer columns) {
        this.param.setColumnSpan(columns);
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setHorizontalStyle(String style) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setVerticalStyle(String style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 设置背景
     *
     * @param background 背景
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackground(String background) {
        this.param.setBackground(background);
        return this;
    }

    /**
     * 设置背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundImage(String image) {
        this.param.setBackgroundImage(image);
        return this;
    }

    /**
     * 设置背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundAttachment(String attachment) {
        this.param.setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundColor(String color) {
        this.param.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundPosition(String position) {
        this.param.setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置背景图片水平定位
     *
     * @param position 定位
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundHorizontalPosition(String position) {
        this.param.setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置背景图片垂直定位
     *
     * @param position 定位
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundVerticalPosition(String position) {
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
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBackgroundRepeat(String repeat) {
        this.param.setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 模板组件
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell addComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getComponents(), v));
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 模板组件
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell addComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getComponents()::addAll);
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    public Element createElement(Document document) {
        // 创建tableCell元素
        Element tableCell = document.createElement(XEasyPdfTemplateTags.TABLE_CELL);
        // 重置属性
        this.resetAttribute(tableCell);
        // 添加id
        Optional.ofNullable(this.param.getId()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.ID, v.intern()));
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.WIDTH, v.intern().toLowerCase()));
        // 设置高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.HEIGHT, v.intern().toLowerCase()));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER, v.intern().toLowerCase()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_STYLE, v.intern().toLowerCase()));
        // 设置边框颜色
        Optional.ofNullable(this.param.getBorderColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_COLOR, v.intern().toLowerCase()));
        // 设置边框宽度
        Optional.ofNullable(this.param.getBorderWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_WIDTH, v.intern().toLowerCase()));
        // 设置上边框
        Optional.ofNullable(this.param.getBorderTop()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_TOP, v.intern().toLowerCase()));
        // 设置上边框样式
        Optional.ofNullable(this.param.getBorderTopStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_TOP_STYLE, v.intern().toLowerCase()));
        // 设置上边框颜色
        Optional.ofNullable(this.param.getBorderTopColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_TOP_COLOR, v.intern().toLowerCase()));
        // 设置上边框宽度
        Optional.ofNullable(this.param.getBorderTopWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_TOP_WIDTH, v.intern().toLowerCase()));
        // 设置下边框
        Optional.ofNullable(this.param.getBorderBottom()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_BOTTOM, v.intern().toLowerCase()));
        // 设置下边框样式
        Optional.ofNullable(this.param.getBorderBottomStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_BOTTOM_STYLE, v.intern().toLowerCase()));
        // 设置下边框颜色
        Optional.ofNullable(this.param.getBorderBottomColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_BOTTOM_COLOR, v.intern().toLowerCase()));
        // 设置下边框宽度
        Optional.ofNullable(this.param.getBorderBottomWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_BOTTOM_WIDTH, v.intern().toLowerCase()));
        // 设置左边框
        Optional.ofNullable(this.param.getBorderLeft()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_LEFT, v.intern().toLowerCase()));
        // 设置左边框样式
        Optional.ofNullable(this.param.getBorderLeftStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_LEFT_STYLE, v.intern().toLowerCase()));
        // 设置左边框颜色
        Optional.ofNullable(this.param.getBorderLeftColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_LEFT_COLOR, v.intern().toLowerCase()));
        // 设置左边框宽度
        Optional.ofNullable(this.param.getBorderLeftWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_LEFT_WIDTH, v.intern().toLowerCase()));
        // 设置右边框
        Optional.ofNullable(this.param.getBorderRight()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_RIGHT, v.intern().toLowerCase()));
        // 设置右边框样式
        Optional.ofNullable(this.param.getBorderRightStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_RIGHT_STYLE, v.intern().toLowerCase()));
        // 设置右边框颜色
        Optional.ofNullable(this.param.getBorderRightColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_RIGHT_COLOR, v.intern().toLowerCase()));
        // 设置右边框宽度
        Optional.ofNullable(this.param.getBorderRightWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_RIGHT_WIDTH, v.intern().toLowerCase()));
        // 设置文本语言
        Optional.ofNullable(this.param.getLanguage()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.LANGUAGE, v.intern().toLowerCase()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 设置行间距
        Optional.ofNullable(this.param.getLeading()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(this.param.getLetterSpacing()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(this.param.getWordSpacing()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(this.param.getWhiteSpace()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置合并行数
        Optional.ofNullable(this.param.getRowSpan()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.NUMBER_ROWS_SPANNED, v.toString().intern()));
        // 设置合并列数
        Optional.ofNullable(this.param.getColumnSpan()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.NUMBER_COLUMNS_SPANNED, v.toString().intern()));
        // 设置水平样式
        Optional.ofNullable(this.param.getHorizontalStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.TEXT_ALIGN, v.intern().toLowerCase()));
        // 设置垂直样式
        Optional.ofNullable(this.param.getVerticalStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.DISPLAY_ALIGN, v.intern().toLowerCase()));
        // 设置背景颜色
        Optional.ofNullable(this.param.getBackgroundColor()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_COLOR, v.intern().toLowerCase()));
        // 设置背景
        Optional.ofNullable(this.param.getBackground()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND, v.intern().toLowerCase()));
        // 设置背景图片
        Optional.ofNullable(this.param.getBackgroundImage()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_IMAGE, v.intern()));
        // 设置背景附件
        Optional.ofNullable(this.param.getBackgroundAttachment()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_ATTACHMENT, v.intern().toLowerCase()));
        // 设置背景图片定位
        Optional.ofNullable(this.param.getBackgroundPosition()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION, v.intern().toLowerCase()));
        // 设置背景图片水平定位
        Optional.ofNullable(this.param.getBackgroundPositionHorizontal()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION_HORIZONTAL, v.intern().toLowerCase()));
        // 设置背景图片垂直定位
        Optional.ofNullable(this.param.getBackgroundPositionVertical()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION_VERTICAL, v.intern().toLowerCase()));
        // 设置背景图片重复
        Optional.ofNullable(this.param.getBackgroundRepeat()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_REPEAT, v.intern().toLowerCase()));
        // 添加组件
        Optional.ofNullable(this.param.getComponents()).ifPresent(v -> v.forEach(e -> tableCell.appendChild(e.createElement(document))));
        // 返回tableCell元素
        return tableCell;
    }

    /**
     * 重置属性
     *
     * @param tableCell tableCell元素
     */
    private void resetAttribute(Element tableCell) {
        // 设置边距
        tableCell.setAttribute(XEasyPdfTemplateAttributes.MARGIN, "0");
        // 设置填充
        tableCell.setAttribute(XEasyPdfTemplateAttributes.PADDING, "0");
    }
}
