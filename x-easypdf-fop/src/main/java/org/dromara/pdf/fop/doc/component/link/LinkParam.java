package org.dromara.pdf.fop.doc.component.link;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.pdf.fop.doc.component.Component;
import org.dromara.pdf.fop.doc.component.ComponentParam;

/**
 * pdf模板-超链接参数
 *
 * @author xsx
 * @date 2022/11/2
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
class LinkParam extends ComponentParam {

    /**
     * pdf模板组件
     */
    private Component component;

    /**
     * 内部地址
     * <p>注：标签id</p>
     */
    private String internalDestination;
    /**
     * 外部地址
     * <p>注：url</p>
     */
    private String externalDestination;
}
