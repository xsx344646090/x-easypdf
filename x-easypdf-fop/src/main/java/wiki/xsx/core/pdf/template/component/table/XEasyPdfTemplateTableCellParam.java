package wiki.xsx.core.pdf.template.component.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponentParam;

/**
 * pdf模板-表格单元格参数
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
class XEasyPdfTemplateTableCellParam extends XEasyPdfTemplateComponentParam {

    /**
     * pdf模板组件
     */
    private XEasyPdfTemplateComponent component;
    /**
     * 宽度
     */
    private String width;
    /**
     * 高度
     */
    private String height;
    /**
     * 边框样式
     */
    private String borderStyle;
    /**
     * 边框折叠
     */
    protected String borderCollapse;
    /**
     * 边框间距
     */
    protected String borderSpacing;
    /**
     * 边框
     */
    protected String border;
}
