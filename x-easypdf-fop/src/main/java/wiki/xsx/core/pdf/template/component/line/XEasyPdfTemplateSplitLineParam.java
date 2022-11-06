package wiki.xsx.core.pdf.template.component.line;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponentParam;

/**
 * pdf模板-分割线参数
 *
 * @author xsx
 * @date 2022/9/3
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
class XEasyPdfTemplateSplitLineParam extends XEasyPdfTemplateComponentParam {

    /**
     * 长度
     */
    private String length;
    /**
     * 样式
     */
    private String style;
}
