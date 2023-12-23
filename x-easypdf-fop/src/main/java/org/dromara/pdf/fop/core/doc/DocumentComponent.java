package org.dromara.pdf.fop.core.doc;

import lombok.SneakyThrows;
import org.w3c.dom.Document;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * pdf模板-文档组件
 *
 * @author xsx
 * @date 2022/11/11
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface DocumentComponent {

    /**
     * 转换
     *
     * @param outputPath 输出路径
     */
    @SneakyThrows
    default void transform(String outputPath) {
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(outputPath))) {
            this.transform(outputStream);
        }
    }

    /**
     * 转换
     *
     * @param outputStream 输出流
     */
    void transform(OutputStream outputStream);

    /**
     * 获取xsl-fo文档
     *
     * @return 返回xsl-fo文档
     */
    Document getDocument();
}
