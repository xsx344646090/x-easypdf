package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTag;

import java.util.Collections;
import java.util.List;

/**
 * pdf模板-表格行组件
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

    private final XEasyPdfTemplateTableRowParam param = new XEasyPdfTemplateTableRowParam();

    public XEasyPdfTemplateTableRow addCell(XEasyPdfTemplateTableCell ...cells) {
        if (cells!=null) {
            Collections.addAll(this.param.getCells(), cells);
        }
        return this;
    }

    public XEasyPdfTemplateTableRow addCell(List<XEasyPdfTemplateTableCell> cells) {
        if (cells!=null) {
            this.param.getCells().addAll(cells);
        }
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
        Element tableRow = document.createElement(XEasyPdfTemplateTag.TABLE_ROW);
        // 获取单元格列表
        List<XEasyPdfTemplateTableCell> cells = this.param.getCells();
        // 遍历单元格列表
        for (XEasyPdfTemplateTableCell cell : cells) {
            // 添加单元格
            tableRow.appendChild(cell.createElement(document));
        }
        // 返回tableCell元素
        return tableRow;
    }
}
