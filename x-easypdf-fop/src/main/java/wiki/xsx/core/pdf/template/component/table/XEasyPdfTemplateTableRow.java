package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;

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
public class XEasyPdfTemplateTableRow {

    /**
     * 表格行参数
     */
    private final XEasyPdfTemplateTableRowParam param = new XEasyPdfTemplateTableRowParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 设置初始化容量
     * @return 返回表格行组件
     */
    private XEasyPdfTemplateTableRow setInitialCapacity(int initialCapacity) {
        this.param.setCells(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow setBorder(String border) {
        this.param.setBorder(border);
        return this;
    }

    /**
     * 设置边框样式
     * <p>NONE：无</p>
     * <p>HIDDEN：隐藏</p>
     * <p>DOTTED：点虚线</p>
     * <p>DASHED：短虚线</p>
     * <p>SOLID：实线</p>
     * <p>DOUBLE：双实线</p>
     * <p>GROOVE：凹线（槽）</p>
     * <p>RIDGE：凸线（脊）</p>
     * <p>INSET：嵌入</p>
     * <p>OUTSET：凸出</p>
     *
     * @param borderStyle 边框样式
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
        return this;
    }

    /**
     * 设置文本水平样式
     * <p>LEFT：居左</p>
     * <p>CENTER：居中</p>
     * <p>RIGHT：居右</p>
     *
     * @param style 水平样式
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置文本垂直样式
     * <p>BEFORE：居上</p>
     * <p>CENTER：居中</p>
     * <p>AFTER：居下</p>
     *
     * @param style 垂直样式
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow setVerticalStyle(String style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格列表
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow addCell(XEasyPdfTemplateTableCell... cells) {
        Optional.ofNullable(cells).ifPresent(v -> Collections.addAll(this.param.getCells(), v));
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格列表
     * @return 返回表格行组件
     */
    public XEasyPdfTemplateTableRow addCell(List<XEasyPdfTemplateTableCell> cells) {
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
        Element tableRow = document.createElement(XEasyPdfTemplateTags.TABLE_ROW);
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> tableRow.setAttribute(XEasyPdfTemplateAttributes.BORDER, v.intern()));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> tableRow.setAttribute(XEasyPdfTemplateAttributes.BORDER_STYLE, v.intern()));
        // 设置水平样式
        Optional.ofNullable(this.param.getHorizontalStyle()).ifPresent(v -> tableRow.setAttribute(XEasyPdfTemplateAttributes.TEXT_ALIGN, v.intern()));
        // 设置垂直样式
        Optional.ofNullable(this.param.getVerticalStyle()).ifPresent(v -> tableRow.setAttribute(XEasyPdfTemplateAttributes.DISPLAY_ALIGN, v.intern()));
        // 获取单元格列表
        List<XEasyPdfTemplateTableCell> cells = this.param.getCells();
        // 遍历单元格列表
        for (XEasyPdfTemplateTableCell cell : cells) {
            // 添加单元格
            tableRow.appendChild(cell.createElement(document));
        }
        // 返回tableRow元素
        return tableRow;
    }
}
