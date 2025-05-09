package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.support.linearization.Linearizer;

import java.io.OutputStream;

/**
 * 线性化处理器
 *
 * @author xsx
 * @date 2025/4/30
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
@EqualsAndHashCode(callSuper = true)
public class LinearizationProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public LinearizationProcessor(Document document) {
        super(document);
    }

    /**
     * 线性化
     * <p>注：仅适用加载的文档</p>
     *
     * @param outputStream 输出流
     */
    public void linearize(OutputStream outputStream) {
        Linearizer linearizer = new Linearizer(this.getDocument());
        linearizer.linearize().write(outputStream);
    }
}
