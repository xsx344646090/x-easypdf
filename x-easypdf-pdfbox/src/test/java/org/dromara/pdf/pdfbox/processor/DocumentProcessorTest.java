package org.dromara.pdf.pdfbox.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.component.Rectangle;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.enums.PageJoinType;
import org.dromara.pdf.pdfbox.core.ext.processor.*;
import org.dromara.pdf.pdfbox.core.ext.processor.form.FormProcessor;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.EncryptAlgorithm;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.KeyStoreType;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.SignOptions;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.SignProcessor;
import org.dromara.pdf.pdfbox.core.info.ReplaceInfo;
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
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    @SneakyThrows
    @Test
    public void mergeTest() {
        this.test(() -> {
            int count = 3;
            List<CompletableFuture<?>> tasks = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                int finalI = i;
                tasks.add(
                        CompletableFuture.runAsync(() -> {
                            this.test(() -> {
                                try (
                                        Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\2.pdf", MemoryPolicy.setupTempFileOnly());
                                        Document newDocument = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\3.pdf", MemoryPolicy.setupTempFileOnly())
                                ) {

                                    MergeProcessor processor = PdfHandler.getDocumentProcessor(newDocument).getMergeProcessor();
                                    processor.merge(document);

                                    newDocument.save("E:\\PDF\\pdfbox\\processor\\mergeTest" + finalI + ".pdf");
                                }
                            });
                        })
                );
            }
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        });
    }

    /**
     * 测试文档拆分
     */
    @Test
    public void splitTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\Pdf1.7.pdf")) {
                SplitProcessor processor = PdfHandler.getDocumentProcessor(document).getSplitProcessor();
                int[] indexes = new int[703 - 682];
                int index = 0;
                for (int i = 682; i <= 702; i++) {
                    indexes[index] = i;
                    index++;
                }
                processor.split("E:\\PDF\\pdfbox\\processor\\splitTest.pdf", indexes);
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
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                PDFont font = document.getContext().getFont("微软雅黑");
                // 替换指定第一个与第二个
                ReplaceInfo replaceInfo1 = new ReplaceInfo('贵', '遵', Stream.of(0, 1).collect(Collectors.toSet()), font);
                // 替换全部
                ReplaceInfo replaceInfo2 = ReplaceInfo.builder().original('阳').value('义').font(font).build();
                // 替换为空（移除）
                ReplaceInfo replaceInfo3 = new ReplaceInfo('百', '\u0000', Stream.of(1).collect(Collectors.toSet()), font);
                ReplaceInfo replaceInfo4 = new ReplaceInfo('科', '\u0000', Stream.of(1).collect(Collectors.toSet()), font);
                processor.replaceText(Arrays.asList(replaceInfo1, replaceInfo2, replaceInfo3, replaceInfo4), 0);
                document.save("E:\\PDF\\pdfbox\\processor\\replaceTest.pdf");
            }
        });
    }

    /**
     * 测试文本替换
     */
    @Test
    public void replaceTest2() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("D:\\Edge浏览器下载\\文字文稿2宋体.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                PDFont font = document.getContext().getFont("仿宋");
                Map<String, String> map = new HashMap<>();
                map.put("${xword}", "测试文本");
                processor.replaceText(font, map, 0);
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
     * 测试拼接页面
     */
    @Test
    public void pageJoinTest1() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\table\\tableTest.pdf")) {
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                processor.join(PageJoinType.VERTICAL, document.getPage(0), document.getPage(1));
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\pageJoinTest.pdf");
            }
        });
    }

    /**
     * 测试拼接页面
     */
    @Test
    public void pageJoinTest2() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().create();
                    Document document1 = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
                    Document document2 = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf");
            ) {
                Page page = new Page(document, PageSize.A4);
                PageProcessor processor = PdfHandler.getDocumentProcessor(document).getPageProcessor();
                List<Page> pages1 = document1.getPages();
                List<Page> pages2 = document2.getPages();
                float x = 0F;
                float y = page.getHeight();
                for (int i = 0; i < 2; i++) {
                    Page page1 = pages1.get(i);
                    Rectangle rectangle1 = new Rectangle(page1);
                    rectangle1.setWidth(page1.getWidth());
                    rectangle1.setHeight(page1.getHeight());
                    rectangle1.setBorderColor(Color.BLACK);
                    rectangle1.render();
                    page1.scale(0.5F);

                    Page page2 = pages2.get(i);
                    Rectangle rectangle2 = new Rectangle(page2);
                    rectangle2.setWidth(page2.getWidth());
                    rectangle2.setHeight(page2.getHeight());
                    rectangle2.setBorderColor(Color.BLACK);
                    rectangle2.render();
                    page2.scale(0.5F);

                    y = y - Math.max(page1.getHeight(), page2.getHeight());
                    processor.join(PageJoinType.HORIZONTAL, x, y, page, page1, page2);
                }

                processor.flush();

                document.save("E:\\PDF\\pdfbox\\processor\\pageJoinTest.pdf");
            }
        });
    }

    /**
     * 测试添加书签
     */
    @Test
    public void bookmarkTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {

                BookmarkProcessor processor = PdfHandler.getDocumentProcessor(document).getBookmarkProcessor();
                PDOutlineItem outlineItem = new PDOutlineItem();
                outlineItem.setTitle("hello world");
                outlineItem.setDestination(document.getPage(0).getTarget());
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
                                              .preferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE * 2)
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
                                              .preferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE * 2)
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
                options.setPreferredSignatureSize(SignatureOptions.DEFAULT_SIGNATURE_SIZE * 6);
                processor.sign(signature, options, outputStream);
            }
        });
    }
}
