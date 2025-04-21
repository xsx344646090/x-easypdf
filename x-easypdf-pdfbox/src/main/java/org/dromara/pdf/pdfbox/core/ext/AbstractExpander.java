package org.dromara.pdf.pdfbox.core.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Document;

/**
 * 抽象扩展器
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
public abstract class AbstractExpander {

    /**
     * 日志
     */
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * 文档
     */
    protected Document document;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractExpander(Document document) {
        this.document = document;
    }

    /**
     * 无参构造
     */
    protected AbstractExpander() {

    }

    /**
     * 获取pdfbox文档
     *
     * @return 返回pdfbox文档
     */
    protected PDDocument getDocument() {
        return this.document.getTarget();
    }

    /**
     * 获取上下文
     *
     * @return 返回上下文
     */
    protected Context getContext() {
        return this.document.getContext();
    }
}
