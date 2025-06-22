package org.dromara.pdf.pdfbox.core.ext.convertor.documents4j;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.documents4j.excel.ExcelConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.documents4j.ppt.PowerpointConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.documents4j.rtf.RichTextConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.documents4j.word.WordConvertor;

/**
 * documents4j转换器
 *
 * @author xsx
 * @date 2025/6/18
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
public class Document4jConvertor extends AbstractConvertor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public Document4jConvertor(Document document) {
        super(document);
    }

    /**
     * 获取word转换器
     *
     * @return 返回word转换器
     */
    public WordConvertor getWordConvertor() {
        return new WordConvertor(this.document);
    }

    /**
     * 获取excel转换器
     *
     * @return 返回excel转换器
     */
    public ExcelConvertor getExcelConvertor() {
        return new ExcelConvertor(this.document);
    }

    /**
     * 获取ppt转换器
     *
     * @return 返回ppt转换器
     */
    public PowerpointConvertor getPowerpointConvertor() {
        return new PowerpointConvertor(this.document);
    }

    /**
     * 获取rtf转换器
     *
     * @return 返回rtf转换器
     */
    public RichTextConvertor getRichTextConvertor() {
        return new RichTextConvertor(this.document);
    }
}
