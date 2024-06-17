package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.pdfbox.core.enums.HighlightMode;

/**
 * 外部目标
 *
 * @author xsx
 * @date 2023/9/6
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
@Data
@Accessors(chain = true)
public class OuterDest {

    /**
     * 目标名称
     */
    private String name;
    /**
     * 目标地址
     */
    private String url;
    /**
     * 高亮模式
     */
    private HighlightMode highlightMode;

    /**
     * 创建
     *
     * @return 返回目标
     */
    public static OuterDest create() {
        return new OuterDest();
    }
}
