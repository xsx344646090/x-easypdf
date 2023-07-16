package org.dromara.pdf.pdfbox.component;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.Page;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 图像组件
 *
 * @author xsx
 * @date 2023/6/30
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class Image implements Component {

    /**
     * 参数
     */
    private final ImageParam param = new ImageParam();

    @SneakyThrows
    public Image setImage(File file) {
        if (file == null) {
            throw new IllegalArgumentException("the image file can not be null");
        }
        return this.setImage(Files.newInputStream(file.toPath()));
    }

    @SneakyThrows
    public Image setImage(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("the image input stream can not be null");
        }
        try (
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
        ) {
            IOUtils.copy(bufferedInputStream, outputStream);
            return this.setImage(outputStream.toByteArray());
        }
    }

    @SneakyThrows
    public Image setImage(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("the image bytes can not be null");
        }
        this.param.setImage(
                PDImageXObject.createFromByteArray(
                        this.param.getPageParam().getDocumentParam().getTarget(),
                        bytes,
                        "unknown"
                )
        );
        return this;
    }

    /**
     * 渲染
     *
     * @return 返回页面
     */
    @Override
    public Page render() {
        return null;
    }
}
