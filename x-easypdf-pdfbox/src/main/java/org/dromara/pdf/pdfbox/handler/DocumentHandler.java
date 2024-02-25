package org.dromara.pdf.pdfbox.handler;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.MemoryPolicy;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

/**
 * 文档助手
 *
 * @author xsx
 * @date 2023/6/2
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
     * 创建
     *
     * @return 返回文档
     */
    public Document create() {
        return new Document();
    }

    /**
     * 创建
     *
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document create(MemoryPolicy policy) {
        return new Document(policy);
    }

    /**
     * 读取
     *
     * @param path 路径
     * @return 返回文档
     */
    public Document load(String path) {
        return this.load(path, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param path   路径
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document load(String path, MemoryPolicy policy) {
        return this.load(path, null, null, null, policy);
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
        return this.load(Paths.get(path).toFile(), password, keyStore, alias, policy);
    }

    /**
     * 读取
     *
     * @param file 文件
     * @return 返回文档
     */
    public Document load(File file) {
        return this.load(file, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param file   文件
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document load(File file, MemoryPolicy policy) {
        return this.load(file, null, null, null, policy);
    }

    /**
     * 读取
     *
     * @param file     文件
     * @param password 密码
     * @return 返回文档
     */
    public Document load(File file, String password) {
        return this.load(file, password, null, null);
    }

    /**
     * 读取
     *
     * @param file     文件
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @return 返回文档
     */
    @SneakyThrows
    public Document load(File file, String password, InputStream keyStore, String alias) {
        return this.load(file, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param file     文件
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     * @return 返回文档
     */
    public Document load(File file, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        return new Document(file, password, keyStore, alias, policy);
    }

    /**
     * 读取
     *
     * @param bytes 字节数组
     * @return 返回文档
     */
    public Document load(byte[] bytes) {
        return this.load(bytes, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param bytes  字节数组
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document load(byte[] bytes, MemoryPolicy policy) {
        return this.load(bytes, null, null, null, policy);
    }

    /**
     * 读取
     *
     * @param bytes    字节数组
     * @param password 密码
     * @return 返回文档
     */
    public Document load(byte[] bytes, String password) {
        return this.load(bytes, password, null, null);
    }

    /**
     * 读取
     *
     * @param bytes    字节数组
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @return 返回文档
     */
    @SneakyThrows
    public Document load(byte[] bytes, String password, InputStream keyStore, String alias) {
        return this.load(bytes, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param bytes    字节数组
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     * @return 返回文档
     */
    public Document load(byte[] bytes, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        return new Document(bytes, password, keyStore, alias, policy);
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @return 返回文档
     */
    public Document load(InputStream inputStream) {
        return this.load(inputStream, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 读取
     *
     * @param inputStream 输入流
     * @param policy      内存策略
     * @return 返回文档
     */
    public Document load(InputStream inputStream, MemoryPolicy policy) {
        return this.load(inputStream, null, null, null, policy);
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

    /**
     * 资源读取
     *
     * @param path 路径
     * @return 返回文档
     */
    public Document loadByResource(String path) {
        return this.loadByResource(path, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 资源读取
     *
     * @param path   路径
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document loadByResource(String path, MemoryPolicy policy) {
        return this.loadByResource(path, null, null, null, policy);
    }

    /**
     * 资源读取
     *
     * @param path     路径
     * @param password 密码
     * @return 返回文档
     */
    public Document loadByResource(String path, String password) {
        return this.loadByResource(path, password, null, null);
    }

    /**
     * 资源读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @return 返回文档
     */
    @SneakyThrows
    public Document loadByResource(String path, String password, InputStream keyStore, String alias) {
        return this.loadByResource(path, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 资源读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     * @return 返回文档
     */
    @SneakyThrows
    public Document loadByResource(String path, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            return this.load(inputStream, password, keyStore, alias, policy);
        }
    }

    /**
     * 远程读取
     *
     * @param path 路径
     * @return 返回文档
     */
    public Document loadByRemote(String path) {
        return this.loadByRemote(path, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 远程读取
     *
     * @param path   路径
     * @param policy 内存策略
     * @return 返回文档
     */
    public Document loadByRemote(String path, MemoryPolicy policy) {
        return this.loadByRemote(path, null, null, null, policy);
    }

    /**
     * 远程读取
     *
     * @param path     路径
     * @param password 密码
     * @return 返回文档
     */
    public Document loadByRemote(String path, String password) {
        return this.loadByRemote(path, password, null, null);
    }

    /**
     * 远程读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @return 返回文档
     */
    @SneakyThrows
    public Document loadByRemote(String path, String password, InputStream keyStore, String alias) {
        return this.loadByRemote(path, password, keyStore, alias, MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 远程读取
     *
     * @param path     路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     * @return 返回文档
     */
    @SneakyThrows
    public Document loadByRemote(String path, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        URLConnection connection = new URL(path).openConnection();
        connection.setConnectTimeout(300000);
        connection.setReadTimeout(300000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        try (InputStream inputStream = connection.getInputStream()) {
            return this.load(inputStream, password, keyStore, alias, policy);
        }
    }
}
