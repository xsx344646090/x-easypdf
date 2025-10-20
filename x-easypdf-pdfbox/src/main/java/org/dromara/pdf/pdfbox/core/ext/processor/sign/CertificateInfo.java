package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.Data;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.support.Constants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * 证书信息
 *
 * @author xsx
 * @date 2025/8/13
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
public class CertificateInfo {
    /**
     * 类型
     */
    private KeyStoreType type;
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 密码
     */
    private String password;
    /**
     * 别名
     */
    private String alias;
    /**
     * 证书
     */
    private X509Certificate certificate;
    /**
     * 证书链
     */
    private Certificate[] chain;
    /**
     * 私钥
     */
    private PrivateKey privateKey;

    /**
     * 有参构造
     *
     * @param type        类型
     * @param inputStream 输入流
     * @param password    密码
     * @param alias       别名
     *
     */
    public CertificateInfo(KeyStoreType type, InputStream inputStream, String password, String alias) {
        this.type = type;
        this.inputStream = inputStream;
        this.password = password;
        this.alias = alias;
    }

    /**
     * 初始化
     */
    @SneakyThrows
    protected void init() {
        // 检查参数
        Objects.requireNonNull(this.inputStream, "the input stream can not be null");
        Objects.requireNonNull(this.password, "the password can not be null");
        Objects.requireNonNull(this.type, "the type can not be null");
        // 密码转字符数组
        char[] passwordCharArray = this.password.toCharArray();
        // 获取密钥库
        KeyStore keyStore = KeyStore.getInstance(this.type.name(), Constants.SIGN_PROVIDER);
        // 加载证书
        keyStore.load(this.inputStream, passwordCharArray);
        // 初始化别名
        if (Objects.isNull(this.alias)) {
            this.alias = keyStore.aliases().nextElement();
        }
        // 获取证书工厂
        CertificateFactory cf = CertificateFactory.getInstance(Constants.CERT_TYPE, Constants.SIGN_PROVIDER);
        // 获取证书
        Certificate cert = keyStore.getCertificate(this.alias);
        // 初始化证书
        try (ByteArrayInputStream stream = new ByteArrayInputStream(cert.getEncoded())) {
            this.certificate = ((X509Certificate) cf.generateCertificate(stream));
        }
        // 检查证书是否过期
        this.certificate.checkValidity();
        // 初始化证书链
        this.chain = keyStore.getCertificateChain(this.alias);
        // 初始化私钥
        this.privateKey = (PrivateKey) keyStore.getKey(this.alias, passwordCharArray);
    }
}
