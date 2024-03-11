package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;
import org.apache.pdfbox.rendering.RenderDestination;

/**
 * 渲染类型
 *
 * @author xsx
 * @date 2023/10/19
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
public enum RenderType {
    /**
     * 导出
     */
    EXPORT(RenderDestination.EXPORT),
    /**
     * 显示
     */
    VIEW(RenderDestination.VIEW),
    /**
     * 打印
     */
    PRINT(RenderDestination.PRINT);

    /**
     * 目的
     */
    @Getter
    private final RenderDestination destination;

    /**
     * 有参构造
     *
     * @param destination 目的
     */
    RenderType(RenderDestination destination) {
        this.destination = destination;
    }
}
