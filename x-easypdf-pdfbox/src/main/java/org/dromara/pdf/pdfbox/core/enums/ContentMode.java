package org.dromara.pdf.pdfbox.core.enums;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * 内容模式
 *
 * @author xsx
 * @date 2023/6/12
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
public enum ContentMode {

    /**
     * 覆盖
     */
    OVERWRITE(PDPageContentStream.AppendMode.OVERWRITE),
    /**
     * 追加
     */
    APPEND(PDPageContentStream.AppendMode.APPEND),
    /**
     * 前置
     */
    PREPEND(PDPageContentStream.AppendMode.PREPEND);

    /**
     * pdfbox内容模式
     */
    private final PDPageContentStream.AppendMode appendMode;

    /**
     * 构造方法
     *
     * @param appendMode pdfbox内容模式
     */
    ContentMode(PDPageContentStream.AppendMode appendMode) {
        this.appendMode = appendMode;
    }

    /**
     * 获取内容模式
     *
     * @return 返回pdfbox内容模式
     */
    public PDPageContentStream.AppendMode getMode() {
        return this.appendMode;
    }
}
