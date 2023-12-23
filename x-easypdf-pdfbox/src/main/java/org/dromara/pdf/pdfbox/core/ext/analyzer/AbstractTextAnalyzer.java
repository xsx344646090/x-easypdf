package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.TextInfo;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象文本分析器
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
public abstract class AbstractTextAnalyzer extends PDFTextStripper implements Closeable {

    /**
     * 文档
     */
    protected Document document;
    /**
     * 页面索引
     */
    protected Integer pageIndex;
    /**
     * 文本信息列表
     */
    @Getter
    protected final List<TextInfo> infoList = new ArrayList<>(64);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractTextAnalyzer(Document document) {
        this.document = document;
        this.setSortByPosition(true);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }
}