package org.dromara.pdf.pdfbox.core.base;

import org.dromara.pdf.pdfbox.core.component.Component;

/**
 * 分页事件
 *
 * @author xsx
 * @date 2023/9/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface PagingEvent {

    /**
     * 分页之前
     *
     * @param component 当前组件
     */
    void before(Component component);

    /**
     * 分页之后
     *
     * @param component 当前组件
     */
    void after(Component component);
}
