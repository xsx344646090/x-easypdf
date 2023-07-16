package org.dromara.pdf.pdfbox.handler;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageRectangle;

/**
 * 页面助手
 *
 * @author xsx
 * @date 2023/6/2
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
public class PageHandler {

    /**
     * 助手实例
     */
    private static final PageHandler INSTANCE = new PageHandler();

    /**
     * 无参构造
     */
    private PageHandler() {
    }

    /**
     * 获取实例
     *
     * @return 返回实例
     */
    public static PageHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 构建
     *
     * @param document 文档
     * @return 返回页面
     */
    public Page build(Document document) {
        return new Page(document);
    }

    /**
     * 构建
     *
     * @param document  文档
     * @param rectangle 尺寸
     * @return 返回页面
     */
    public Page build(Document document, PageRectangle rectangle) {
        return new Page(document, rectangle);
    }
}
