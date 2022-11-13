package wiki.xsx.core.pdf.template.doc.component.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponentParam;

/**
 * pdf模板-表格参数
 *
 * @author xsx
 * @date 2022/8/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
class XEasyPdfTemplateTableParam extends XEasyPdfTemplateComponentParam {

    /**
     * 宽度
     */
    protected String width;
    /**
     * 高度
     */
    protected String height;
    /**
     * 边框
     */
    protected String border;
    /**
     * 边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    protected String borderStyle;
    /**
     * 边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    protected String borderColor;
    /**
     * 边框宽度
     */
    protected String borderWidth;
    /**
     * 边框折叠
     * <p>collapse：合并</p>
     * <p>separate：分开</p>
     */
    protected String borderCollapse;
    /**
     * 边框间距
     */
    protected String borderSpacing;
    /**
     * 边框圆角
     */
    protected String borderRadius;
    /**
     * 最小列宽
     */
    protected String minColumnWidth;
    /**
     * 最小行高
     */
    protected String minRowHeight;
    /**
     * 表头
     */
    private XEasyPdfTemplateTableHeader header;
    /**
     * 表格体
     */
    private XEasyPdfTemplateTableBody body;
    /**
     * 表尾
     */
    private XEasyPdfTemplateTableFooter footer;
    /**
     * 是否自动省略表头
     * <p>默认：false</p>
     */
    private Boolean isAutoOmitHeader;
    /**
     * 是否自动省略表尾
     * <p>默认：false</p>
     */
    private Boolean isAutoOmitFooter;
}
