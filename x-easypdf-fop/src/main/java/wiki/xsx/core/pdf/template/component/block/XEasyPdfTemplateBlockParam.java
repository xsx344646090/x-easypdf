package wiki.xsx.core.pdf.template.component.block;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponentParam;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-文本基础参数
 *
 * @author xsx
 * @date 2022/11/6
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
class XEasyPdfTemplateBlockParam extends XEasyPdfTemplateComponentParam {

    /**
     * 组件
     */
    private List<XEasyPdfTemplateComponent> components = new ArrayList<>(10);
    /**
     * 文本
     */
    private String text;
    /**
     * 垂直对齐
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     */
    private String verticalAlign;
}
