package org.dromara.pdf.pdfbox.core.ext.convertor.office.rtf;

import com.documents4j.api.DocumentType;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * rtf转换器
 *
 * @author xsx
 * @date 2025/1/14
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
public class RichTextConvertor extends AbstractRichTextConvertor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public RichTextConvertor(Document document) {
        super(document);
    }

    /**
     * 转pdf
     *
     * @param source 源输入流
     * @return 返回文档
     */
    @SneakyThrows
    @Override
    public Document toPdf(InputStream source) {
        return super.toPdf(DocumentType.RTF, source);
    }

    /**
     * 转rtf
     *
     * @param output 输出流
     * @return 返回布尔值，true为成功，false为失败
     */
    @Override
    public boolean toRtf(OutputStream output) {
        return super.toFile(DocumentType.RTF, output);
    }
}
