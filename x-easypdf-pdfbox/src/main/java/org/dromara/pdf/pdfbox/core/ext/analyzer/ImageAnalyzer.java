package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.HashSet;
import java.util.Objects;

/**
 * 图像分析器
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
public class ImageAnalyzer extends AbstractImageAnalyzer {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public ImageAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理图像
     *
     * @param pageIndex 页面索引
     * @param page      pdfbox页面
     */
    @SneakyThrows
    public void processImage(int pageIndex, PDPage page) {
        // 初始化流引擎
        if (Objects.isNull(this.streamEngine)) {
            this.streamEngine = new DefaultStreamEngine(this.log);
        }
        // 重置页面索引
        this.streamEngine.setPageIndex(pageIndex);
        // 重置图像索引
        this.streamEngine.setImageIndex(0);
        // 重置图像信息列表
        this.streamEngine.setInfoSet(new HashSet<>(16));
        // 处理页面
        this.streamEngine.processPage(page);
        // 添加图像信息
        this.infoSet.addAll(this.streamEngine.getInfoSet());
    }
}
