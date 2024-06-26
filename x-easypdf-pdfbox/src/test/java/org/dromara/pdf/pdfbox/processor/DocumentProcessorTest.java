package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.*;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.EncryptAlgorithm;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.KeyStoreType;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.SignOptions;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.SignProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class DocumentProcessorTest extends BaseTest {

    /**
     * 测试文档合并
     */
    @Test
    public void mergeTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf");
                    Document newDocument = PdfHandler.getDocumentHandler().create()
            ) {
                newDocument.appendPage(new Page(document));

                MergeProcessor processor = PdfHandler.getDocumentProcessor(newDocument).getMergeProcessor();
                processor.merge(document);

                newDocument.save("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf");
            }
        });
    }

    /**
     * 测试文档拆分
     */
    @Test
    public void splitTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                SplitProcessor processor = PdfHandler.getDocumentProcessor(document).getSplitProcessor();
                processor.split("E:\\PDF\\pdfbox\\processor\\splitTest.pdf", 1, 0);
            }
        });
    }

    /**
     * 测试文档转图片
     */
    @Test
    public void imageTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                RenderProcessor processor = PdfHandler.getDocumentProcessor(document).getRenderProcessor();
                processor.setDpi(300);
                processor.enableBinary();
                processor.enableVerticalMerge();
                processor.image("E:\\PDF\\pdfbox\\processor", ImageType.PNG);
            }
        });
    }

    /**
     * 测试文本替换
     */
    @Test
    public void replaceTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                ReplaceProcessor.ReplaceCharacterInfo replaceInfo1 = new ReplaceProcessor.ReplaceCharacterInfo('0', '1', Stream.of(3, 5).collect(Collectors.toSet()), PdfHandler.getFontHandler().getPDFont(document.getTarget(), "仿宋"));
                ReplaceProcessor.ReplaceCharacterInfo replaceInfo2 = new ReplaceProcessor.ReplaceCharacterInfo('3', '4', Stream.of(0, 1).collect(Collectors.toSet()), PdfHandler.getFontHandler().getPDFont(document.getTarget(), "仿宋"));
                processor.replaceText(Arrays.asList(replaceInfo1, replaceInfo2), 0);
                document.save("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
            }
        });
    }

    /**
     * 测试打印
     */
    @Test
    public void printTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                PrintProcessor processor = PdfHandler.getDocumentProcessor(document).getPrintProcessor();
                processor.setBeginPageNo(2);
                processor.setEndPageNo(2);
                processor.print();
            }
        });
    }

    /**
     * 测试重排序页面
     */
    @Test
    public void pageTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.resort(1, 0);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\pageTest.pdf");
            }
        });
    }

    /**
     * 测试添加书签
     */
    @Test
    public void bookmarkTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")) {
                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                PDOutlineItem outlineItem = new PDOutlineItem();
                outlineItem.setTitle("hello world");
                outlineItem.setDestination(document.getPage(1).getTarget());
                processor.append(outlineItem);
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\bookmarkTest.pdf");
            }
        });
    }

    /**
     * 测试添加评论
     */
    @Test
    public void commentTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\mergeTest.pdf")
            ) {
                // 创建处理器
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();

                // 创建评论
                PDAnnotationFreeText commentAnnotation = new PDAnnotationFreeText();
                // 设置名称
                commentAnnotation.setAnnotationName(UUID.randomUUID().toString());
                // 设置内容
                commentAnnotation.setContents("测试评论");
                // 设置背景颜色
                commentAnnotation.setColor(ColorUtil.toPDColor(Color.LIGHT_GRAY));
                // 设置作者
                commentAnnotation.setTitlePopup("xsx");
                // 设置主题
                commentAnnotation.setSubject("主题");
                // 设置日期
                commentAnnotation.setModifiedDate((new GregorianCalendar()));
                // 设置范围
                commentAnnotation.setRectangle(Size.create(200F, 300F, 700F, 720F).getRectangle());
                // 添加评论
                processor.add(0, "微软雅黑", 16F, Color.BLUE, commentAnnotation);

                // 保存
                document.save("E:\\PDF\\pdfbox\\processor\\commentTest.pdf");
            }
        });
    }

    /**
     * 测试表单填写
     */
    @Test
    public void formTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\hello-world.pdf")) {
                FormProcessor processor = PdfHandler.getDocumentProcessor(document).getFormProcessor();
                Map<String, String> data = new HashMap<>();
                data.put("test1", "hello world");
                data.put("test2", "你好，贵阳");
                processor.fillText(data);

                document.save("E:\\PDF\\pdfbox\\processor\\formTest.pdf");
            }
        });
    }

    /**
     * 测试添加图层
     */
    @Test
    public void layerTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().create()) {

                Page page = new Page(document);

                Textarea textarea = new Textarea(page);
                textarea.setText("Hello World!");
                textarea.setFontSize(13F);
                textarea.render();

                LayerProcessor processor = PdfHandler.getDocumentProcessor(document).getLayerProcessor();
                processor.append("layer1", ImageUtil.read(Paths.get("E:\\PDF\\pdfbox\\processor\\x-easypdf2.png").toFile()), page);

                document.appendPage(page);
                document.save("E:\\PDF\\pdfbox\\processor\\layerTest.pdf");
            }
        });
    }

    @Test
    public void metadataTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().create()) {

                MetadataProcessor processor = PdfHandler.getDocumentProcessor(document).getMetadataProcessor();
                processor.setTitle("你好，贵阳");
                processor.setAuthors("x-easypdf");
                processor.setDescription("测试metadata");
                processor.setKeywords("测试", "metadata", "贵阳");
                processor.flush();

                document.appendPage(new Page(document));
                document.save("E:\\PDF\\pdfbox\\processor\\metadataTest.pdf");
            }
        });
    }

    @Test
    public void signTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
                    OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\signTest.pdf"));
            ) {

                // 获取密码字符数组
                char[] passwordCharArray = "123456".toCharArray();
                // 获取密钥库
                KeyStore keyStore = KeyStore.getInstance(KeyStoreType.PKCS12.name());
                // 定义证书文件流
                try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\x-easypdf.p12"))) {
                    // 加载证书
                    keyStore.load(inputStream, passwordCharArray);
                }
                // 证书获取别名
                String alias = keyStore.aliases().nextElement();
                // 获取证书链
                Certificate[] certificateChain = keyStore.getCertificateChain(alias);
                SignOptions options = SignOptions.builder()
                        .key((PrivateKey) keyStore.getKey(alias, passwordCharArray))
                        .certificates(certificateChain)
                        .algorithm(EncryptAlgorithm.SHA256withRSA.name())
                        .pageIndex(1)
                        .preferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE*2)
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

    @Test
    public void multiSignTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
                    OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\signTest.pdf"));
            ) {

                // 获取密码字符数组
                char[] passwordCharArray = "123456".toCharArray();
                // 获取密钥库
                KeyStore keyStore = KeyStore.getInstance(KeyStoreType.PKCS12.name());
                // 定义证书文件流
                try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\processor\\x-easypdf.p12"))) {
                    // 加载证书
                    keyStore.load(inputStream, passwordCharArray);
                }
                // 证书获取别名
                String alias = keyStore.aliases().nextElement();
                // 获取证书链
                Certificate[] certificateChain = keyStore.getCertificateChain(alias);
                SignOptions options = SignOptions.builder()
                        .key((PrivateKey) keyStore.getKey(alias, passwordCharArray))
                        .certificates(certificateChain)
                        .algorithm(EncryptAlgorithm.SHA256withRSA.name())
                        .pageIndex(1)
                        .preferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE*2)
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
                options.setPreferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE*6);
                processor.sign(signature, options, outputStream);
            }
        });
    }
}
