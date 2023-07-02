package org.dromara.pdf.pdfbox.mark;


import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;

import java.io.Serializable;

/**
 * pdf水印组件接口
 *
 * @author xsx
 * @date 2020/6/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface XEasyPdfWatermark extends Serializable {

    /**
     * 开启上下文重置
     *
     * @return 返回水印组件
     */
    XEasyPdfWatermark enableResetContext();

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void draw(XEasyPdfDocument document, XEasyPdfPage page);
}
