package wiki.xsx.core.pdf.template.doc.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-表尾组件
 * <p>fo:table-footer标签</p>
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
public class XEasyPdfTemplateTableFooter {

    /**
     * 表格行列表
     */
    private List<XEasyPdfTemplateTableRow> rows = new ArrayList<>(10);

    /**
     * 最小列宽
     */
    private String minColumnWidth;

    /**
     * 最小行高
     */
    private String minRowHeight;

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回表尾组件
     */
    private XEasyPdfTemplateTableFooter setInitialCapacity(int initialCapacity) {
        this.rows = new ArrayList<>(initialCapacity);
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param minRowHeight 最小行高
     * @return 返回表尾组件
     */
    public XEasyPdfTemplateTableFooter setMinRowHeight(String minRowHeight) {
        if (this.minRowHeight == null) {
            this.minRowHeight = minRowHeight;
        }
        return this;
    }

    /**
     * 设置最小列宽
     *
     * @param minColumnWidth 最小列宽
     * @return 返回表尾组件
     */
    public XEasyPdfTemplateTableFooter setMinColumnWidth(String minColumnWidth) {
        if (this.minColumnWidth == null) {
            this.minColumnWidth = minColumnWidth;
        }
        return this;
    }

    /**
     * 添加行
     *
     * @param rows 表格行列表
     * @return 返回表尾组件
     */
    public XEasyPdfTemplateTableFooter addRow(XEasyPdfTemplateTableRow... rows) {
        Optional.ofNullable(rows).ifPresent(v -> Collections.addAll(this.rows, v));
        return this;
    }

    /**
     * 添加行
     *
     * @param rows 表格行列表
     * @return 返回表尾组件
     */
    public XEasyPdfTemplateTableFooter addRow(List<XEasyPdfTemplateTableRow> rows) {
        Optional.ofNullable(rows).ifPresent(this.rows::addAll);
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
        // 创建tableFooter元素
        Element tableFooter = document.createElement(XEasyPdfTemplateTags.TABLE_FOOTER);
        // 遍历表格行列表
        for (XEasyPdfTemplateTableRow row : this.rows) {
            // 添加表格行
            tableFooter.appendChild(row.setMinColumnWidth(this.minColumnWidth).setMinRowHeight(this.minRowHeight).createElement(document));
        }
        // 返回tableFooter元素
        return tableFooter;
    }
}
