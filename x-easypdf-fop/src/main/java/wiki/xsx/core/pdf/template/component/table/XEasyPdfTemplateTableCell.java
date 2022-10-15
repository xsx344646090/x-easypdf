package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

import java.util.Optional;

/**
 * pdf模板-表格单元格组件
 * <p>fo:table-cell标签</p>
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
public class XEasyPdfTemplateTableCell {

    private final XEasyPdfTemplateTableCellParam param = new XEasyPdfTemplateTableCellParam();

    /**
     * 设置组件
     *
     * @param component 模板组件
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setComponent(XEasyPdfTemplateComponent component) {
        this.param.setComponent(component);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setWidth(String width) {
        this.param.setWidth(width);
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
     *
     * @param borderStyle 边框样式
     * @return 返回表格单元格组件
     */
    public XEasyPdfTemplateTableCell setBorderStyle(String borderStyle) {
        this.param.setBorderStyle(borderStyle);
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
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.WIDTH, v));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER, v));
        // 设置边框样式
        Optional.ofNullable(this.param.getBorderStyle()).ifPresent(v -> tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_STYLE, v));
        // 添加组件
        Optional.ofNullable(this.param.getComponent()).ifPresent(v -> tableCell.appendChild(v.createElement(document)));
        // 返回tableCell元素
        return tableCell;
    }

    /**
     * 初始化
     *
     * @param row 表格行
     * @return 返回表格单元格组件
     */
    XEasyPdfTemplateTableCell init(XEasyPdfTemplateTableRow row) {
        // 获取表格行参数
        XEasyPdfTemplateTableRowParam rowParam = row.getParam();
        // 如果边框样式为空，则初始化边框样式
        if (this.param.getBorderStyle() == null) {
            // 初始化边框样式为表格行边框样式
            this.param.setBorderStyle(rowParam.getBorderStyle());
        }
        // 返回表格单元格组件
        return this;
    }
}
