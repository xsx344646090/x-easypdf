package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTag;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

/**
 * pdf模板-表格组件
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
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    @Override
    public Element createElement(Document document) {
        // 创建block元素
        Element block = this.createBlockElement(document, this.param);
        // 创建table元素
        Element table = this.createTable(document);
        // 添加table元素
        block.appendChild(table);
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
        Element table = document.createElement(XEasyPdfTemplateTag.TABLE);
        table.appendChild(this.createTableHeader(document));
        table.appendChild(this.createTableBody(document));
        table.appendChild(this.createTableFooter(document));
        return table;
    }

    /**
     * 创建tableHeader元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableHeader(Document document) {
        Element tableHeader = document.createElement(XEasyPdfTemplateTag.TABLE_HEADER);
        return tableHeader;
    }

    /**
     * 创建tableBody元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableBody(Document document) {
        Element tableBody = document.createElement(XEasyPdfTemplateTag.TABLE_BODY);
        return tableBody;
    }

    /**
     * 创建tableFooter元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableFooter(Document document) {
        Element tableFooter = document.createElement(XEasyPdfTemplateTag.TABLE_FOOTER);
        return tableFooter;
    }
}
