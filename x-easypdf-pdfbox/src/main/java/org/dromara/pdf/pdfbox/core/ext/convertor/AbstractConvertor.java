package org.dromara.pdf.pdfbox.core.ext.convertor;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.AbstractExpander;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 抽象转换器
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
public abstract class AbstractConvertor extends AbstractExpander {

    /**
     * 转换器
     */
    protected IConverter converter;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractConvertor(Document document) {
        super(document);
    }

    /**
     * 设置内置转换器
     *
     * @param converter 转换器
     */
    public void setInlineConverter(IConverter converter) {
        Objects.requireNonNull(converter, "the converter can not be null");
        this.converter = converter;
    }

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源路径
     * @return 返回文档
     */
    @SneakyThrows
    public Document toPdf(DocumentType type, String source) {
        Objects.requireNonNull(source, "the source can not be null");
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(source);
            if (Objects.isNull(inputStream)) {
                inputStream = Files.newInputStream(Paths.get(source));
            }
            return this.toPdf(type, inputStream);
        } finally {
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源输入流
     * @return 返回文档
     */
    @SneakyThrows
    protected Document toPdf(DocumentType type, InputStream source) {
        Objects.requireNonNull(this.converter, "the inline converter can not be null");
        Objects.requireNonNull(type, "the type can not be null");
        Objects.requireNonNull(source, "the source can not be null");
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            boolean executed = this.converter.convert(source)
                    .as(type)
                    .to(outputStream)
                    .as(DocumentType.PDF)
                    .execute();
            if (executed) {
                return PdfHandler.getDocumentHandler().load(outputStream.toByteArray());
            }
            throw new RuntimeException("convert fail");
        }
    }

    /**
     * 转file
     *
     * @param type   类型
     * @param output 输出流
     * @return 返回布尔值，true为成功，false为失败
     */
    @SneakyThrows
    protected boolean toFile(DocumentType type, OutputStream output) {
        Objects.requireNonNull(this.converter, "the inline converter can not be null");
        Objects.requireNonNull(type, "the type can not be null");
        Objects.requireNonNull(output, "the output can not be null");
        File tempFile = this.document.getTempFile();
        boolean executed = this.converter.convert(tempFile)
                .as(DocumentType.PDF)
                .to(output)
                .as(type)
                .execute();
        if (Objects.nonNull(tempFile)) {
            tempFile.delete();
        }
        return executed;
    }
}
