package wiki.xsx.core.pdf.template.doc.component.text;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponentParam;

/**
 * pdf模板-文本基础参数
 *
 * @author xsx
 * @date 2022/8/8
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
class XEasyPdfTemplateTextBaseParam extends XEasyPdfTemplateComponentParam {

    /**
     * 内部地址
     * <p>注：标签id</p>
     */
    private String linkInternalDestination;
    /**
     * 外部地址
     * <p>注：url</p>
     */
    private String linkExternalDestination;
    /**
     * 删除线颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    protected String deleteLineColor;
    /**
     * 下划线宽度
     */
    protected String underLineWidth;
    /**
     * 下划线颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    protected String underLineColor;
    /**
     * 是否包含删除线
     */
    protected Boolean hasDeleteLine = Boolean.FALSE;
    /**
     * 是否包含下划线
     */
    protected Boolean hasUnderLine = Boolean.FALSE;
    /**
     * 是否包含超链接
     */
    protected Boolean hasLink = Boolean.FALSE;
}
