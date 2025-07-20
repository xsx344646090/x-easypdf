package org.dromara.pdf.pdfbox.core.ext.convertor.office;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.conversion.IExternalConverter;
import com.documents4j.job.LocalConverter;
import lombok.SneakyThrows;
import org.apache.commons.logging.LogFactory;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 抽象office转换器
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
public abstract class AbstractOfficeConvertor extends AbstractConvertor {

    /**
     * 转换器
     */
    protected static final IConverter CONVERTER = init();

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractOfficeConvertor(Document document) {
        super(document);
    }

    /**
     * 初始化
     *
     * @return 返回转换器
     */
    @SuppressWarnings("all")
    protected static IConverter init() {
        LocalConverter.Builder builder = LocalConverter.builder().baseFolder(new File(Constants.TEMP_FILE_PATH)).processTimeout(600, TimeUnit.SECONDS);
        try {
            builder.enable((Class<? extends IExternalConverter>) Class.forName("com.documents4j.conversion.msoffice.MicrosoftPowerpointBridge"));
        } catch (ClassNotFoundException e) {
            LogFactory.getLog(AbstractOfficeConvertor.class).warn("The MicrosoftPowerpointBridge class not found, will skip the ppt initialization");
        }
        return builder.build();
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
        Objects.requireNonNull(type, "the type can not be null");
        Objects.requireNonNull(source, "the source can not be null");
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            boolean executed = CONVERTER.convert(source)
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
        Objects.requireNonNull(type, "the type can not be null");
        Objects.requireNonNull(output, "the output can not be null");
        File tempFile = this.document.getTempFile();
        boolean executed = CONVERTER.convert(tempFile)
                .as(DocumentType.PDF)
                .to(output)
                .as(type)
                .execute();
        if (Objects.nonNull(tempFile)) {
            if (!tempFile.delete()) {
                this.log.warn("Delete temp file['" + tempFile.getName() + "'] fail: " + tempFile.getAbsolutePath());
            }
        }
        return executed;
    }
}
