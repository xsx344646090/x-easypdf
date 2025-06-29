package org.dromara.pdf.pdfbox.core.ext.convertor.office.word;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.office.AbstractOfficeConvertor;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 抽象word转换器
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
public abstract class AbstractWordConvertor extends AbstractOfficeConvertor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractWordConvertor(Document document) {
        super(document);
    }

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源输入流
     * @return 返回文档
     */
    public abstract Document toPdf(WordType type, InputStream source);

    /**
     * 转word
     *
     * @param type   类型
     * @param output 输出流
     * @return 返回布尔值，true为成功，false为失败
     */
    public abstract boolean toWord(WordType type, OutputStream output);

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源路径
     * @return 返回文档
     */
    @SneakyThrows
    public Document toPdf(WordType type, String source) {
        Objects.requireNonNull(type, "the type can not be null");
        return super.toPdf(type.getType(), source);
    }

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源文件
     * @return 返回文档
     */
    @SneakyThrows
    public Document toPdf(WordType type, File source) {
        Objects.requireNonNull(source, "the source can not be null");
        try (InputStream inputStream = Files.newInputStream(source.toPath())) {
            return this.toPdf(type, inputStream);
        }
    }

    /**
     * 转word
     *
     * @param type   类型
     * @param output 输出路径
     * @return 返回布尔值，true为成功，false为失败
     */
    @SneakyThrows
    public boolean toWord(WordType type, String output) {
        Objects.requireNonNull(output, "the output can not be null");
        try (OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get(output)))) {
            return this.toWord(type, outputStream);
        }
    }

    /**
     * 转word
     *
     * @param type   类型
     * @param output 输出文件
     * @return 返回布尔值，true为成功，false为失败
     */
    @SneakyThrows
    public boolean toWord(WordType type, File output) {
        Objects.requireNonNull(output, "the output can not be null");
        try (OutputStream outputStream = Files.newOutputStream(output.toPath())) {
            return this.toWord(type, outputStream);
        }
    }
}
