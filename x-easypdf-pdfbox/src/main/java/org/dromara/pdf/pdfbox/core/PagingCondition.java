package org.dromara.pdf.pdfbox.core;

import org.dromara.pdf.pdfbox.core.component.Component;

/**
 * 分页条件
 *
 * @author xsx
 * @date 2023/9/15
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
@FunctionalInterface
public interface PagingCondition {

    /**
     * 是否分页
     *
     * @param component 当前组件
     * @param beginY    Y轴起始坐标
     * @return 返回布尔值，true为是，false为否
     */
    boolean isPaging(Component component, float beginY);
}
