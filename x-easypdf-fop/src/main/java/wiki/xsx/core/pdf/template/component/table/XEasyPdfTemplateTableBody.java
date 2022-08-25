package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pdf模板-表格体组件
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
public class XEasyPdfTemplateTableBody {

    /**
     * 表格行列表
     */
    private List<XEasyPdfTemplateTableRow> rows = new ArrayList<>(10);

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 设置初始化容量
     * @return 返回表格体组件
     */
    private XEasyPdfTemplateTableBody setInitialCapacity(int initialCapacity) {
        this.rows = new ArrayList<>(initialCapacity);
        return this;
    }

    /**
     * 添加行
     *
     * @param rows 表格行列表
     * @return 返回表格体组件
     */
    public XEasyPdfTemplateTableBody addRow(XEasyPdfTemplateTableRow... rows) {
        if (rows != null) {
            Collections.addAll(this.rows, rows);
        }
        return this;
    }

    /**
     * 添加行
     *
     * @param rows 表格行列表
     * @return 返回表格体组件
     */
    public XEasyPdfTemplateTableBody addRow(List<XEasyPdfTemplateTableRow> rows) {
        if (rows != null) {
            this.rows.addAll(rows);
        }
        return this;
    }

    /**
     * 获取表格行列表
     *
     * @return 返回表格行列表
     */
    public List<XEasyPdfTemplateTableRow> getRows() {
        return this.rows;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    public Element createElement(Document document) {
        // 创建tableBody元素
        Element tableBody = document.createElement(XEasyPdfTemplateTag.TABLE_BODY);
        // 遍历表格行列表
        for (XEasyPdfTemplateTableRow row : this.rows) {
            // 添加表格行
            tableBody.appendChild(row.createElement(document));
        }
        // 返回tableBody元素
        return tableBody;
    }
}