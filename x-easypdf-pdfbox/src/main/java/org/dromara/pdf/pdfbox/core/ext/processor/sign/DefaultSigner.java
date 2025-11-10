package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.SneakyThrows;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.Attributes;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.*;

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
        ContentSigner signer = new JcaContentSignerBuilder(this.options.getAlgorithm().getName()).setProvider(Constants.SIGN_PROVIDER).build(this.options.getCertificate().getPrivateKey());
        // 定义摘要提供者
        DigestCalculatorProvider provider = new JcaDigestCalculatorProviderBuilder().setProvider(Constants.SIGN_PROVIDER).build();
        // 定义签名信息生成器
        SignerInfoGenerator infoGenerator = new JcaSignerInfoGeneratorBuilder(provider).build(signer, this.options.getCertificate().getCertificate());
        // 添加签名信息
        generator.addSignerInfoGenerator(infoGenerator);
        // 添加证书
        generator.addCertificates(new JcaCertStore(Arrays.asList(this.options.getCertificate().getChain())));
        // 生成签名数据
        CMSSignedData signedData = generator.generate(this.getCMSData(content), this.options.getIsEncapsulated());
        // 添加时间戳
        if (Objects.nonNull(this.options.getTsaClient())) {
            signedData = this.addTimeStamp(signedData);
        }
        // 返回签名字节数组
        return signedData.getEncoded();
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
        SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider(Constants.SIGN_PROVIDER).build(holder);
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
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(Constants.SIGN_PROVIDER).getCertificate(holder);
        // 检查证书的有效期
        cert.checkValidity();
        // 使用证书的公钥验证证书
        cert.verify(cert.getPublicKey(), Constants.SIGN_PROVIDER);
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

    /**
     * 添加时间戳
     *
     * @param signedData 签名数据
     * @return 返回签名数据
     */
    protected CMSSignedData addTimeStamp(CMSSignedData signedData) {
        // 检查TSA客户端是否已配置
        if (Objects.nonNull(this.options.getTsaClient())) {
            // 获取签名信息存储
            SignerInformationStore signerStore = signedData.getSignerInfos();
            // 创建新的签名信息列表
            List<SignerInformation> newSigners = new ArrayList<>();
            // 遍历原始签名信息中的每个签名者
            for (SignerInformation signer : signerStore.getSigners()) {
                // 对每个签名者添加时间戳，并将结果添加到新签名列表中
                newSigners.add(this.signTimeStamp(signer));
            }
            // 使用新的签名信息替换原始签名信息，并返回更新后的CMS签名数据
            return CMSSignedData.replaceSigners(signedData, new SignerInformationStore(newSigners));
        }
        // 如果没有配置TSA客户端，则直接返回原始签名数据
        return signedData;
    }

    /**
     * 添加时间戳
     *
     * @param signer 签名者信息
     * @return 返回签名者信息
     */
    @SneakyThrows
    protected SignerInformation signTimeStamp(SignerInformation signer) {
        // 获取签名者的未签名属性表
        AttributeTable unsignedAttributes = signer.getUnsignedAttributes();
        // 创建一个ASN1可编码向量，用于存储属性
        ASN1EncodableVector vector = new ASN1EncodableVector();
        // 如果未签名属性不为空，则将其转换为ASN1可编码向量
        if (unsignedAttributes != null) {
            vector = unsignedAttributes.toASN1EncodableVector();
        }
        // 使用TSA客户端获取时间戳令牌，传入签名数据的字节数组流
        TimeStampToken timeStampToken = this.options.getTsaClient().getTimeStampToken(new ByteArrayInputStream(signer.getSignature()));
        // 获取时间戳令牌的编码字节数组
        byte[] token = timeStampToken.getEncoded();
        // 定义签名时间戳的OID对象标识符
        ASN1ObjectIdentifier oid = PKCSObjectIdentifiers.id_aa_signatureTimeStampToken;
        // 创建签名时间戳属性，使用OID和DER编码的令牌集合
        ASN1Encodable signatureTimeStamp = new Attribute(oid, new DERSet(ASN1Primitive.fromByteArray(token)));
        // 将签名时间戳属性添加到向量中
        vector.add(signatureTimeStamp);
        // 使用向量创建新的属性集合
        Attributes signedAttributes = new Attributes(vector);
        // 替换签名者的未签名属性并返回新的签名者信息
        return SignerInformation.replaceUnsignedAttributes(signer, new AttributeTable(signedAttributes));
    }
}
