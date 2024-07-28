package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;

import java.util.List;

/**
 * @author xsx
 * @date 2024/6/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Data
class TableFooter {

    /**
     * 表格
     */
    protected Table table;
    /**
     * 行列表
     */
    protected List<TableRow> tableRows;
    /**
     * 单元格宽度
     */
    protected List<Float> cellWidths;
    /**
     * 是否已经渲染
     */
    protected Boolean isAlreadyRendered;

}
