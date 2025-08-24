package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * 默认签名器
 *
 * @author xsx
 * @date 2024/3/13
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
public class DefaultSigner implements SignatureInterface {

    static {
        // 设置提供者bc
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 提供者bc
     */
    private static final String PROVIDER = "BC";
    /**
     * 私钥
     */
    private final PrivateKey privateKey;
    /**
     * 证书
     */
    private final Certificate[] certificates;
    /**
     * 算法
     */
    private final String algorithm;

    /**
     * 有参构造
     *
     * @param options 签名选项
     */
    public DefaultSigner(SignOptions options) {
        this.privateKey = options.getCertificate().getPrivateKey();
        this.certificates = options.getCertificate().getChain();
        this.algorithm = options.getAlgorithm();
    }

    /**
     * 签名
     *
     * @param content 内容
     * @return 返回字节数组
     */
    @SneakyThrows
    @Override
    public byte[] sign(InputStream content) {
        // 定义cms签名器
        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        // 定义内容签名器
        ContentSigner signer = new JcaContentSignerBuilder(this.algorithm).setProvider(PROVIDER).build(this.privateKey);
        // 定义摘要提供者
        DigestCalculatorProvider provider = new JcaDigestCalculatorProviderBuilder().setProvider(PROVIDER).build();
        // 定义签名信息生成器
        SignerInfoGenerator infoGenerator = new JcaSignerInfoGeneratorBuilder(provider).build(signer, (X509Certificate) this.certificates[0]);
        // 添加签名信息
        generator.addSignerInfoGenerator(infoGenerator);
        // 添加证书
        generator.addCertificates(new JcaCertStore(Arrays.asList(this.certificates)));
        // 返回签名字节数组
        return generator.generate(this.getCMSData(content), false).getEncoded();
    }

    /**
     * 获取CMS数据
     *
     * @param content 内容流
     * @return 返回CMS数据
     */
    protected CMSTypedData getCMSData(InputStream content) {
        return new DefaultCMSProcessor(content);
    }
}
