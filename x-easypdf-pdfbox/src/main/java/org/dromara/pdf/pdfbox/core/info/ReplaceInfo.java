package org.dromara.pdf.pdfbox.core.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.Set;

/**
 * 替换字符信息
 *
 * @author xsx
 * @date 2024/7/12
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
@AllArgsConstructor
public class ReplaceInfo {
    /**
     * 原有值
     */
    private Character original;
    /**
     * 新值
     */
    private Character value;
    /**
     * 索引
     */
    private Set<Integer> indexes;
    /**
     * 替换字体
     */
    private PDFont font;
}
