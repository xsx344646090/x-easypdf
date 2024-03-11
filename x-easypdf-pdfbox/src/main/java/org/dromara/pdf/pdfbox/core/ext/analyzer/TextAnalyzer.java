package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 文本分析器
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
public class TextAnalyzer extends AbstractTextAnalyzer {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理文本
     *
     * @param pageIndex 页面索引
     */
    @SneakyThrows
    @Override
    public void processText(int pageIndex) {
        // 创建文本剥离器
        DefaultTextStripper textStripper = new DefaultTextStripper(pageIndex);
        // 创建写入器
        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new ByteArrayOutputStream()))) {
            // 写入文本
            textStripper.writeText(this.getDocument(), writer);
        }
        // 重置文本信息列表
        this.infoSet.addAll(textStripper.getInfoSet());
    }
}
