package org.dromara.pdf.pdfbox.core.component;

import org.dromara.pdf.pdfbox.core.ComponentType;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;

/**
 * 抽象水印
 *
 * @author xsx
 * @date 2023/10/13
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public interface Watermark {

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    ComponentType getType();

    /**
     * 渲染（单页面）
     *
     * @param page 页面
     */
    void render(Page page);

    /**
     * 渲染（文档）
     *
     * @param document 文档
     */
    void render(Document document);
}
