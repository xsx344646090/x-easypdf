package org.dromara.pdf.pdfbox.core.ext.convertor.html;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.InputStream;
import java.util.Objects;

/**
 * html转换器
 *
 * @author xsx
 * @date 2025/1/8
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
public class HtmlConvertor extends AbstractHtmlConvertor {
    
    /**
     * 有参构造
     *
     * @param document 文档
     */
    public HtmlConvertor(Document document) {
        super(document);
    }
    
    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源输入流
     * @return 返回文档
     */
    @SneakyThrows
    @Override
    public Document toPdf(HtmlType type, InputStream source) {
        Objects.requireNonNull(type, "the type can not be null");
        return super.toPdf(type.getType(), source);
    }
}
