package org.dromara.pdf.pdfbox.handler;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.MemoryPolicy;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文档助手
 *
 * @author xsx
 * @date 2023/6/2
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
public class DocumentHandler {

    /**
     * 助手实例
     */
    private static final DocumentHandler INSTANCE = new DocumentHandler();

    /**
     * 无参构造
     */
    private DocumentHandler() {
    }

    /**
     * 获取实例
     *
     * @return 返回实例
     */
    public static DocumentHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 构建
     *
     * @return 返回文档
     */
    public Document build() {
        return new Document();
    }

    /**
     * 读取
     *
     * @param path 路径
     * @return 返回文档
     */
    public Document load(String path) {
        return this.load(path, null);
    }

    /**
     * 读取
     *
     * @param path     路径
     * @param password 密码
     * @return 返回文档
     */
    public Document load(String path, String password) {
        return this.load(path, password, null, null);
    }

    /**
     * 读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @return 返回文档
     */
    @SneakyThrows
    public Document load(String path, String password, InputStream keyStore, String alias) {
        return this.load(path, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     * @return 返回文档
     */
    @SneakyThrows
    public Document load(String path, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
            return this.load(inputStream, password, keyStore, alias, policy);
        }
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @return 返回文档
     */
    public Document load(InputStream inputStream) {
        return this.load(inputStream, null);
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @param password    密码
     * @return 返回文档
     */
    public Document load(InputStream inputStream, String password) {
        return this.load(inputStream, password, null, null);
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     * @return 返回文档
     */
    public Document load(InputStream inputStream, String password, InputStream keyStore, String alias) {
        return this.load(inputStream, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     * @param policy      内存策略
     * @return 返回文档
     */
    public Document load(InputStream inputStream, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        return new Document(inputStream, password, keyStore, alias, policy);
    }
}
