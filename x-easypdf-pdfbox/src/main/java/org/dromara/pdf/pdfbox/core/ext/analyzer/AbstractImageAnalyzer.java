package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.state.*;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象图像分析器
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
public abstract class AbstractImageAnalyzer extends PDFStreamEngine implements Closeable {

    /**
     * 文档
     */
    protected Document document;
    /**
     * 页面索引
     */
    protected Integer pageIndex;
    /**
     * 图像索引
     */
    protected Integer imageIndex;
    /**
     * 图像信息列表
     */
    @Getter
    protected final List<ImageInfo> infoList = new ArrayList<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractImageAnalyzer(Document document) {
        this.document = document;
        this.addOperator(new Concatenate(this));
        this.addOperator(new DrawObject(this));
        this.addOperator(new SetGraphicsStateParameters(this));
        this.addOperator(new Save(this));
        this.addOperator(new Restore(this));
        this.addOperator(new SetMatrix(this));
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }
}