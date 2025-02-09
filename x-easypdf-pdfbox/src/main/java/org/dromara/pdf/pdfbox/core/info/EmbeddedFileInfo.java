package org.dromara.pdf.pdfbox.core.info;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

import java.util.Calendar;
import java.util.Objects;

/**
 * 嵌入文件信息
 *
 * @author xsx
 * @date 2025/1/20
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
@Data
public class EmbeddedFileInfo {
    /**
     * 文件名称
     */
    protected String name;
    /**
     * 文件描述
     */
    protected String description;
    /**
     * 内嵌文件
     */
    protected PDEmbeddedFile embeddedFile;

    /**
     * 有参构造
     *
     * @param name         文件名称
     * @param description  文件描述
     * @param embeddedFile 内嵌文件
     */
    public EmbeddedFileInfo(String name, String description, PDEmbeddedFile embeddedFile) {
        Objects.requireNonNull(name, "the name can not be null");
        Objects.requireNonNull(embeddedFile, "the embedded file can not be null");
        this.name = name;
        this.description = description;
        this.embeddedFile = embeddedFile;
    }

    /**
     * 获取创建日期
     *
     * @return 返回创建日期
     */
    @SneakyThrows
    public Calendar getCreateDate() {
        return this.embeddedFile.getCreationDate();
    }

    /**
     * 获取修改日期
     *
     * @return 返回修改日期
     */
    @SneakyThrows
    public Calendar getModifyDate() {
        return this.embeddedFile.getModDate();
    }

    /**
     * 获取文件大小
     *
     * @return 返回文件大小
     */
    @SneakyThrows
    public int getSize() {
        return this.embeddedFile.getSize();
    }

    /**
     * 获取文件类型
     *
     * @return 返回文件类型
     */
    @SneakyThrows
    public String getSubtype() {
        return this.embeddedFile.getSubtype();
    }

    /**
     * 获取文件字节数组
     *
     * @return 返回文件字节数组
     */
    @SneakyThrows
    public byte[] getFileBytes() {
        return this.embeddedFile.toByteArray();
    }
}
