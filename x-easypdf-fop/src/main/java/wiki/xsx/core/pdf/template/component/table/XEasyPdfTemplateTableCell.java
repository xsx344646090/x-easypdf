package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

/**
 * pdf模板-表格单元格组件
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
        // 如果宽度不为空，则设置宽度
        if (this.param.getWidth() != null) {
            // 设置宽度
            tableCell.setAttribute(XEasyPdfTemplateAttributes.WIDTH, this.param.getWidth());
        }
        // 如果边框不为空，则设置边框
        if (this.param.getBorder() != null) {
            // 设置边框
            tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER, this.param.getBorder());
        }
        // 如果边框样式不为空，则设置边框样式
        if (this.param.getBorderStyle() != null) {
            // 设置边框样式
            tableCell.setAttribute(XEasyPdfTemplateAttributes.BORDER_STYLE, this.param.getBorderStyle());
        }
        // 如果模板组件不为空，则添加组件
        if (this.param.getComponent() != null) {
            // 添加组件
            tableCell.appendChild(this.param.getComponent().createElement(document));
        }
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
