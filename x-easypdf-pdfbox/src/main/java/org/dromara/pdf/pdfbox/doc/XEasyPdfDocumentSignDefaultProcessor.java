package org.dromara.pdf.pdfbox.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * pdf文档签名默认处理器
 *
 * @author xsx
 * @date 2022/8/10
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfDocumentSignDefaultProcessor implements SignatureInterface {

    /**
     * pdf签名器
     */
    private final XEasyPdfDocumentSigner signer;

    /**
     * 提供者bc
     */
    private static final String PROVIDER = "BC";

    /**
     * 有参构造
     *
     * @getParam() signer pdf签名器
     */
    XEasyPdfDocumentSignDefaultProcessor(XEasyPdfDocumentSigner signer) {
        this.signer = signer;
    }

    /**
     * 签名
     *
     * @return 返回字节数组
     * @getParam() content 内容
     */
    @SneakyThrows
    @Override
    public byte[] sign(InputStream content) {
        // 设置提供者bc
        Security.addProvider(new BouncyCastleProvider());
        // 获取密码字符数组
        char[] passwordCharArray = this.signer.getParam().getCertificatePassword().toCharArray();
        // 获取密钥库
        KeyStore keyStore = KeyStore.getInstance(this.signer.getParam().getKeyStoreType().name());
        // 定义证书文件流
        try (FileInputStream inputStream = new FileInputStream(this.signer.getParam().getCertificate())) {
            // 加载证书
            keyStore.load(inputStream, passwordCharArray);
        }
        // 证书获取别名
        String alias = keyStore.aliases().nextElement();
        // 获取证书链
        Certificate[] certificateChain = keyStore.getCertificateChain(alias);
        // 定义cms签名器
        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        // 定义内容签名器
        ContentSigner sha1Signer = new JcaContentSignerBuilder(
                this.signer.getParam().getSignAlgorithm().name()
        ).setProvider(PROVIDER).build((PrivateKey) keyStore.getKey(alias, passwordCharArray));
        // 添加签名信息
        generator.addSignerInfoGenerator(
                new JcaSignerInfoGeneratorBuilder(
                        new JcaDigestCalculatorProviderBuilder().setProvider(PROVIDER).build()
                ).build(sha1Signer, (X509Certificate) certificateChain[0])
        );
        // 添加证书
        generator.addCertificates(new JcaCertStore(Arrays.asList(certificateChain)));
        // 返回签名字节数组
        return generator.generate(new CmsProcessableInputStream(content), true).getEncoded();
    }

    /**
     * cms数据
     */
    public static class CmsProcessableInputStream implements CMSTypedData {
        /**
         * 输入流
         */
        private final InputStream in;
        /**
         * 内容类型
         */
        private final ASN1ObjectIdentifier contentType;

        /**
         * 有参构造
         *
         * @getParam() is 输入流
         */
        CmsProcessableInputStream(InputStream is) {
            this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), is);
        }

        /**
         * 有参构造
         *
         * @getParam() type 内容类型
         * @getParam() is   输入流
         */
        CmsProcessableInputStream(ASN1ObjectIdentifier type, InputStream is) {
            this.contentType = type;
            this.in = is;
        }

        /**
         * 获取内容
         *
         * @return 返回输入流
         */
        @Override
        public Object getContent() {
            return this.in;
        }

        /**
         * 写出
         *
         * @throws IOException IO异常
         * @getParam() out 输出流
         */
        @Override
        public void write(OutputStream out) throws IOException {
            IOUtils.copy(this.in, out);
            this.in.close();
        }

        /**
         * 获取内容类型
         *
         * @return 返回内容类型
         */
        @Override
        public ASN1ObjectIdentifier getContentType() {
            return this.contentType;
        }
    }
}
