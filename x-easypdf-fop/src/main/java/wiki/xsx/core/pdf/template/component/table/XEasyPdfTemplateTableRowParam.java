package wiki.xsx.core.pdf.template.component.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponentParam;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-表格行参数
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
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
class XEasyPdfTemplateTableRowParam extends XEasyPdfTemplateComponentParam {

    /**
     * 单元格列表
     */
    private List<XEasyPdfTemplateTableCell> cells = new ArrayList<>(10);

    /**
     * 边框
     */
    protected String border;

    /**
     * 边框样式
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
     */
    private String borderStyle;
}
