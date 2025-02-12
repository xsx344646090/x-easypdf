package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;

/**
 * 文本行信息
 *
 * @author xsx
 * @date 2024/11/5
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
public class TextLineInfo {
    
    /**
     * 文本
     */
    protected String text;
    /**
     * 宽度
     */
    protected Float width;
    
    /**
     * 有参构造
     *
     * @param text  文本
     * @param width 宽度
     */
    public TextLineInfo(String text, Float width) {
        this.text = text;
        this.width = width;
    }
}
