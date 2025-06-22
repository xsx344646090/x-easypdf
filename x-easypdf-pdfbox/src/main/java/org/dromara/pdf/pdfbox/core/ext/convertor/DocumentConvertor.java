package org.dromara.pdf.pdfbox.core.ext.convertor;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.documents4j.Document4jConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.playwright.PlaywrightConvertor;

/**
 * 文档转换器
 *
 * @author xsx
 * @date 2025/1/8
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
public class DocumentConvertor extends AbstractConvertor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentConvertor(Document document) {
        super(document);
    }

    /**
     * 获取document4j转换器（office）
     *
     * @return 返回document4j转换器
     */
    public Document4jConvertor getDocument4jConvertor() {
        return new Document4jConvertor(this.document);
    }

    /**
     * 获取playwright转换器（html）
     *
     * @return 返回playwright转换器
     */
    public PlaywrightConvertor getPlaywrightConvertor() {
        return new PlaywrightConvertor(this.document);
    }
}
