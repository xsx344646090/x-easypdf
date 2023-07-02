package org.dromara.pdf.pdfbox.component.layout;

import lombok.Data;
import org.dromara.pdf.pdfbox.component.table.XEasyPdfTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2022/3/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
class XEasyPdfLayoutParam implements Serializable {

    private static final long serialVersionUID = 6269937080892192541L;

    /**
     * 是否包含表格边框
     */
    private Boolean hasTableBorder = Boolean.FALSE;
    /**
     * pdf表格
     */
    private XEasyPdfTable table = new XEasyPdfTable();
    /**
     * 组件列表
     */
    private transient List<XEasyPdfLayoutComponent> components = new ArrayList<>(10);
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;
}
