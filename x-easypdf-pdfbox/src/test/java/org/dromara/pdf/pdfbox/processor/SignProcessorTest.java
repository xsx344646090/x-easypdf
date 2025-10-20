package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.*;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.FileUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @author xsx
 * @date 2024/2/26
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class SignProcessorTest extends BaseTest {

    /**
     * 签名测试
     */
    @Test
    public void signTest() {
        PdfHandler.disableScanSystemFonts();
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
                    OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get("E:\\PDF\\pdfbox\\processor\\sign\\signTest.pdf")));
                    InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\x-easypdf.pfx"))
            ) {
                VisualOptions visualOptions = VisualOptions.builder()
                        .image(ImageUtil.read(Paths.get("E:\\PDF\\pdfbox\\processor\\sign\\数字签名.png").toFile()))
                        .build();
                SignOptions options = SignOptions.builder()
                        .certificate(new CertificateInfo(KeyStoreType.PKCS12, inputStream, "123456", null))
                        .algorithm(EncryptAlgorithm.SHA1withRSA)
                        .pageIndex(0)
                        .visualOptions(visualOptions)
                        .build();

                PDSignature signature = new PDSignature();
                signature.setName("x-easypdf");
                signature.setLocation("贵阳");
                signature.setReason("测试");
                signature.setContactInfo("xsx");
                signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
                signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
                signature.setSignDate(Calendar.getInstance());
                SignProcessor processor = PdfHandler.getDocumentProcessor(document).getSignProcessor();
                processor.sign(signature, options, outputStream);
            }
        });
    }

    /**
     * 验证签名测试
     */
    @Test
    public void verifySignatureTest() {
        PdfHandler.disableScanSystemFonts();
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\sign\\signTest.pdf");
            ) {
                SignProcessor processor = PdfHandler.getDocumentProcessor(document).getSignProcessor();
                processor.verifySignature(processor.getSignature("Signature1"));
            }
        });
    }

    /**
     * 多次签名测试
     */
    @Test
    public void multiSignTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
                    OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\sign\\signTest.pdf"));
                    InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\x-easypdf.pfx"))
            ) {

                SignOptions options = SignOptions.builder()
                        .certificate(new CertificateInfo(KeyStoreType.PKCS12, inputStream, "123456", null))
                        .algorithm(EncryptAlgorithm.SHA256withRSA)
                        .pageIndex(1)
                        .build();

                PDSignature signature = new PDSignature();
                signature.setName("x-easypdf");
                signature.setLocation("贵阳");
                signature.setReason("测试");
                signature.setContactInfo("xsx");
                signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
                signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
                signature.setSignDate(Calendar.getInstance());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
                SignProcessor processor = PdfHandler.getDocumentProcessor(document).getSignProcessor();
                processor.multiSign(signature, options, byteArrayOutputStream);
                byteArrayOutputStream.close();

                signature = new PDSignature();
                signature.setName("x-easypdf");
                signature.setLocation("贵阳");
                signature.setReason("测试2");
                signature.setContactInfo("xsx");
                signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
                signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
                signature.setSignDate(Calendar.getInstance());
                processor.sign(signature, options, outputStream);
            }
        });
    }
}
