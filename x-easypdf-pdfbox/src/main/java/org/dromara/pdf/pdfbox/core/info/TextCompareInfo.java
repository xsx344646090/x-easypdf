package org.dromara.pdf.pdfbox.core.info;

import lombok.Builder;
import lombok.Data;

/**
 * 文本比较信息
 *
 * @author xsx
 * @date 2024/8/5
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
@Builder
public class TextCompareInfo {
    /**
     * 页面索引
     */
    private Integer pageIndex;
    /**
     * 行数
     */
    private Integer lineNumber;
    /**
     * 原内容
     */
    private String content;
    /**
     * 对比内容
     */
    private String compareContent;
}
