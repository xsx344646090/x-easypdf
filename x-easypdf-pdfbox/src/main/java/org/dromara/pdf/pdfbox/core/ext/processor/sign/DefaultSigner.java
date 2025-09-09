package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.InputStream;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;

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
public class DefaultSigner implements Signer {

    static {
        // 设置提供者bc
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 提供者bc
     */
    public static final String PROVIDER = "BC";

    /**
     * 签名选项
     */
    private SignOptions options;

    /**
     * 无参构造
     */
    public DefaultSigner() {
    }

    /**
     * 有参构造
     *
     * @param options 签名选项
     */
    public DefaultSigner(SignOptions options) {
        this.options = options;
    }

    /**
     * 签名
     *
     * @param content 内容
     * @return 返回字节数组
     */
    @Override
    @SneakyThrows
    public byte[] sign(InputStream content) {
        // 定义cms签名器
        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        // 定义内容签名器
        ContentSigner signer = new JcaContentSignerBuilder(this.options.getAlgorithm()).setProvider(PROVIDER).build(this.options.getCertificate().getPrivateKey());
        // 定义摘要提供者
        DigestCalculatorProvider provider = new JcaDigestCalculatorProviderBuilder().setProvider(PROVIDER).build();
        // 定义签名信息生成器
        SignerInfoGenerator infoGenerator = new JcaSignerInfoGeneratorBuilder(provider).build(signer, (X509Certificate) this.options.getCertificate().getChain()[0]);
        // 添加签名信息
        generator.addSignerInfoGenerator(infoGenerator);
        // 添加证书
        generator.addCertificates(new JcaCertStore(Arrays.asList(this.options.getCertificate().getChain())));
        // 返回签名字节数组
        return generator.generate(this.getCMSData(content), this.options.getIsEncapsulated()).getEncoded();
    }

    /**
     * 验证签名
     * <p>注：当未封装签名数据时，无法验证签名</p>
     *
     * @param signature 签名
     */
    @SneakyThrows
    @Override
    public void verifySignature(PDSignature signature) {
        // 创建CMSSignedData对象
        CMSSignedData signedData = new CMSSignedData(signature.getContents());
        // 获取签名者信息
        SignerInformation signer = this.getSigner(signedData);
        // 获取证书持有者信息
        X509CertificateHolder holder = this.getCertificateHolder(signedData, signer);
        // 使用JCA构建签名信息验证器，并指定提供者
        SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider(PROVIDER).build(holder);
        // 验证签名
        if (!signer.verify(verifier)) {
            throw new SignatureException("The signature is invalid");
        }
    }

    /**
     * 验证证书
     *
     * @param signature 签名
     */
    @SneakyThrows
    @Override
    public void verifyCertificate(PDSignature signature) {
        // 创建CMSSignedData对象
        CMSSignedData signedData = new CMSSignedData(signature.getContents());
        // 获取签名者信息
        SignerInformation signer = this.getSigner(signedData);
        // 获取证书持有者信息
        X509CertificateHolder holder = this.getCertificateHolder(signedData, signer);
        // 获取证书
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(PROVIDER).getCertificate(holder);
        // 检查证书的有效期
        cert.checkValidity();
        // 使用证书的公钥验证证书
        cert.verify(cert.getPublicKey(), PROVIDER);
    }

    /**
     * 获取签名信息
     *
     * @param signedData 签名数据
     * @return 返回签名信息
     */
    @SneakyThrows
    protected SignerInformation getSigner(CMSSignedData signedData) {
        SignerInformationStore signers = signedData.getSignerInfos();
        return signers.getSigners().iterator().next();
    }

    /**
     * 获取证书持有者
     *
     * @param signedData 签名数据
     * @param signer     签名信息
     * @return 返回证书持有者
     */
    @SuppressWarnings("all")
    @SneakyThrows
    protected X509CertificateHolder getCertificateHolder(CMSSignedData signedData, SignerInformation signer) {
        Store<X509CertificateHolder> certStore = signedData.getCertificates();
        Collection<X509CertificateHolder> certs = certStore.getMatches(signer.getSID());
        return certs.iterator().next();
    }
}
