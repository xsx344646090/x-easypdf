package org.dromara.pdf.pdfbox.core.ext.convertor.ppt;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 抽象ppt转换器
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
public abstract class AbstractPowerpointConvertor extends AbstractConvertor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractPowerpointConvertor(Document document) {
        super(document);
    }

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源输入流
     * @return 返回文档
     */
    public abstract Document toPdf(PowerpointType type, InputStream source);

    /**
     * 转pdf
     *
     * @param type   类型
     * @param source 源路径
     * @return 返回文档
     */
    @SneakyThrows
    public Document toPdf(PowerpointType type, String source) {
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
    public Document toPdf(PowerpointType type, File source) {
        Objects.requireNonNull(source, "the source can not be null");
        try (InputStream inputStream = Files.newInputStream(source.toPath())) {
            return this.toPdf(type, inputStream);
        }
    }
}
