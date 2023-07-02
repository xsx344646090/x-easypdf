package org.dromara.pdf.pdfbox.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * pdf文档签名器
 *
 * @author xsx
 * @date 2021/12/7
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
public class XEasyPdfDocumentSigner implements Serializable {

    private static final long serialVersionUID = -3449241065190072431L;

    /**
     * pdf文档签名器参数
     */
    private final XEasyPdfDocumentSignerParam param = new XEasyPdfDocumentSignerParam();

    /**
     * 有参构造
     *
     * @param pdfDocument pdf文档
     */
    XEasyPdfDocumentSigner(XEasyPdfDocument pdfDocument) {
        this.param.setPdfDocument(pdfDocument);
        this.param.setDocument(this.param.getPdfDocument().build(true));
    }

    /**
     * 有参构造
     *
     * @param pdfDocument pdf文档
     */
    XEasyPdfDocumentSigner(XEasyPdfDocument pdfDocument, PDDocument document) {
        this.param.setPdfDocument(pdfDocument);
        this.param.setDocument(document);
    }

    /**
     * 设置签名信息
     *
     * @param name        名称
     * @param location    位置
     * @param reason      原因
     * @param contactInfo 信息
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setSignerInfo(
            String name,
            String location,
            String reason,
            String contactInfo
    ) {
        this.param.getSignature().setName(name);
        this.param.getSignature().setLocation(location);
        this.param.getSignature().setReason(reason);
        this.param.getSignature().setContactInfo(contactInfo);
        return this;
    }

    /**
     * 设置签名过滤器
     *
     * @param filter    过滤器
     * @param subFilter 子过滤器
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setSignFilter(
            XEasyPdfDocumentSignFilter.Filter filter,
            XEasyPdfDocumentSignFilter.SubFilter subFilter
    ) {
        if (filter != null) {
            this.param.getSignature().setFilter(filter.getFilter());
        }
        if (subFilter != null) {
            this.param.getSignature().setSubFilter(subFilter.getFilter());
        }
        return this;
    }

    /**
     * 设置签名图片
     *
     * @param image        图片
     * @param marginLeft   图片左边距
     * @param marginTop    图片上边距
     * @param scalePercent 图片缩放比例
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setSignImage(
            BufferedImage image,
            float marginLeft,
            float marginTop,
            float scalePercent
    ) {
        this.param.setImage(image)
                .setImageMarginLeft(marginLeft)
                .setImageMarginTop(marginTop)
                .setImageScalePercent(scalePercent - 100);
        return this;
    }

    /**
     * 设置签名证书
     *
     * @param signAlgorithm       签名算法
     * @param keyStoreType        密钥库类型
     * @param certificate         证书文件
     * @param certificatePassword 证书密码
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setCertificate(
            XEasyPdfDocumentSignAlgorithm signAlgorithm,
            XEasyPdfDocumentSignKeyStoreType keyStoreType,
            File certificate,
            String certificatePassword
    ) {
        this.param.setSignAlgorithm(signAlgorithm)
                .setKeyStoreType(keyStoreType)
                .setCertificate(certificate)
                .setCertificatePassword(certificatePassword);
        return this;
    }

    /**
     * 设置签名内存大小（默认：250K）
     *
     * @param preferredSignatureSize 签名内存大小
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setPreferredSignatureSize(int preferredSignatureSize) {
        this.param.setPreferredSignatureSize(preferredSignatureSize);
        return this;
    }

    /**
     * 设置自定义签名接口
     *
     * @param customSignature 自定义pdfbox签名接口
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setCustomSignature(SignatureInterface customSignature) {
        this.param.setCustomSignature(customSignature);
        return this;
    }

    /**
     * 设置签名后pdf权限
     *
     * @param accessPermissions pdf权限
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner setAccessPermissions(int accessPermissions) {
        this.param.setAccessPermissions(accessPermissions);
        return this;
    }

    /**
     * 签名
     *
     * @param pageIndex    签名页面索引
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void sign(int pageIndex, OutputStream outputStream) {
        // 创建字节数组输出流
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192)) {
            // 初始化参数
            this.param.init(pageIndex);
            // 创建任务文档
            PDDocument target = this.param.getDocument();
            // 保存文档
            target.save(byteArrayOutputStream);
            // 添加签名
            this.addSignature(true, PDDocument.load(byteArrayOutputStream.toByteArray()), outputStream);
        }
        // 关闭文档
        this.param.getPdfDocument().close();
    }

    /**
     * 签名
     *
     * @param outputStream 输出流
     * @param pageIndexes  签名页面索引
     */
    @SneakyThrows
    public void sign(OutputStream outputStream, int... pageIndexes) {
        // 创建字节数组输出流
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192)) {
            // 获取任务文档
            PDDocument target = this.param.getDocument();
            // 保存文档
            target.save(byteArrayOutputStream);
            // 关闭文档
            target.close();
            // 重置任务文档
            this.param.setDocument(PDDocument.load(byteArrayOutputStream.toByteArray()));
        }
        // 定义当前索引
        int index = 0;
        // 获取签名内存大小（默认：250K）
        int signSize = this.param.getPreferredSignatureSize();
        // 获取pdf访问权限
        int accessPermission = this.param.getAccessPermissions();
        // 如果页面索引不为空，则根据页面索引添加签名
        if (pageIndexes != null && pageIndexes.length > 0) {
            // 获取最后索引
            int lastIndex = pageIndexes.length - 1;
            // 遍历页面索引
            for (int pageIndex : pageIndexes) {
                // 添加签名并获取索引
                index = this.addSignature(index, pageIndex, index == lastIndex, signSize, accessPermission, this.param.getDocument(), outputStream);
                // 重置签名内存大小（原大小3.5倍）
                signSize = (int) (signSize * 3.5);
            }
        }
        // 否则全文档页面签名
        else {
            // 获取页面总数
            int total = this.param.getDocument().getNumberOfPages();
            // 获取最后索引
            int lastIndex = total - 1;
            // 遍历页面
            for (int pageIndex = 0; pageIndex < total; pageIndex++) {
                // 添加签名并获取索引
                index = this.addSignature(index, pageIndex, index == lastIndex, signSize, accessPermission, this.param.getDocument(), outputStream);
                // 重置签名内存大小（原大小3.5倍）
                signSize = (int) (signSize * 3.5);
            }
        }
        // 资源释放
        this.release();
    }

    /**
     * 添加签名
     *
     * @param isAddInfo    是否添加信息
     * @param target       目标文档
     * @param outputStream 输出流
     */
    @SneakyThrows
    void addSignature(boolean isAddInfo, PDDocument target, OutputStream outputStream) {
        // 获取pdf文档
        XEasyPdfDocument pdfDocument = this.param.getPdfDocument();
        // 添加信息
        if (isAddInfo) {
            // 替换总页码占位符
            pdfDocument.replaceTotalPagePlaceholder(target, false);
            // 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
            pdfDocument.setBasicInfo(target);
        }
        // 设置mdp权限
        this.setMdpPermission(target, this.param.getSignature(), this.param.getAccessPermissions());
        // 重置签名表单
        this.resetSignForm(target);
        // 添加签名
        target.addSignature(this.param.getSignature(), this.getSignatureInterface(), this.param.getSignatureOptions());
        // 保存文档
        target.saveIncremental(outputStream);
        // 关闭文档
        target.close();
    }

    /**
     * 重置签名表单
     *
     * @param target 目标文档
     */
    void resetSignForm(PDDocument target) {
        // 获取pdfbox表单
        PDAcroForm acroForm = target.getDocumentCatalog().getAcroForm(null);
        // 如果表单不为空且首次使用，则清除首次使用项
        if (acroForm != null && acroForm.getNeedAppearances()) {
            // 如果表单字段为空，则清除首次使用项
            if (acroForm.getFields().isEmpty()) {
                // 清除首次使用项
                acroForm.getCOSObject().removeItem(COSName.NEED_APPEARANCES);
            }
        }
    }

    /**
     * 获取签名参数
     *
     * @return 返回签名参数
     */
    XEasyPdfDocumentSignerParam getParam() {
        return this.param;
    }

    /**
     * 添加签名
     *
     * @param index            当前索引
     * @param pageIndex        页面索引
     * @param isLast           是否最后一页
     * @param signSize         签名内存
     * @param accessPermission 权限
     * @param target           pdfbox文档
     * @param outputStream     输出流
     * @return 返回索引
     */
    @SneakyThrows
    private int addSignature(
            int index,
            int pageIndex,
            boolean isLast,
            int signSize,
            int accessPermission,
            PDDocument target,
            OutputStream outputStream
    ) {
        // 设置签名内存大小
        this.param.setPreferredSignatureSize(signSize);
        // 如果为最后签名，则保存为最后文档
        if (isLast) {
            // 设置访问权限
            this.param.setAccessPermissions(accessPermission);
            // 初始化参数
            this.param.init(pageIndex);
            // 添加签名
            this.addSignature(true, target, outputStream);
        }
        // 否则保存临时文档
        else {
            // 设置访问权限
            this.param.setAccessPermissions(accessPermission > 1 ? 2 : accessPermission);
            // 初始化参数
            this.param.init(pageIndex);
            // 获取临时目录
            String tempPath = this.param.getTempDir() + File.separatorChar + index;
            // 创建文件流
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempPath)) {
                // 添加签名
                this.addSignature(false, target, fileOutputStream);
                // 创建新输入流
                try (InputStream inputStream = Files.newInputStream(Paths.get(tempPath), StandardOpenOption.DELETE_ON_CLOSE)) {
                    // 设置pdfbox文档
                    this.param.setDocument(PDDocument.load(inputStream));
                }
            }
        }
        // 返回当前索引+1
        return index + 1;
    }

    /**
     * 获取签名接口
     *
     * @return 返回签名接口
     */
    @SneakyThrows
    private SignatureInterface getSignatureInterface() {
        return this.param.getCustomSignature() != null ? this.param.getCustomSignature() : new XEasyPdfDocumentSignDefaultProcessor(this);
    }

    /**
     * 获取mdp权限
     *
     * @param doc pdfbox文档
     * @return 返回mdp权限
     */
    private int getMdpPermission(PDDocument doc) {
        COSBase base = doc.getDocumentCatalog().getCOSObject().getDictionaryObject(COSName.PERMS);
        if (base instanceof COSDictionary) {
            COSDictionary permsDict = (COSDictionary) base;
            base = permsDict.getDictionaryObject(COSName.DOCMDP);
            if (base instanceof COSDictionary) {
                COSDictionary signatureDict = (COSDictionary) base;
                base = signatureDict.getDictionaryObject("Reference");
                if (base instanceof COSArray) {
                    COSArray refArray = (COSArray) base;
                    for (int i = 0; i < refArray.size(); ++i) {
                        base = refArray.getObject(i);
                        if (base instanceof COSDictionary) {
                            COSDictionary sigRefDict = (COSDictionary) base;
                            if (COSName.DOCMDP.equals(sigRefDict.getDictionaryObject("TransformMethod"))) {
                                base = sigRefDict.getDictionaryObject("TransformParams");
                                if (base instanceof COSDictionary) {
                                    COSDictionary transformDict = (COSDictionary) base;
                                    int accessPermissions = transformDict.getInt(COSName.P, 2);
                                    if (accessPermissions < 1 || accessPermissions > 3) {
                                        accessPermissions = 2;
                                    }
                                    return accessPermissions;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 设置mdp权限
     *
     * @param doc               pdfbox文档
     * @param signature         pdfbox签名
     * @param accessPermissions 签名后pdf权限
     */
    @SneakyThrows
    private void setMdpPermission(PDDocument doc, PDSignature signature, int accessPermissions) {
        List<PDSignature> signatureList = doc.getSignatureDictionaries();
        for (PDSignature sig : signatureList) {
            if (COSName.DOC_TIME_STAMP.equals(sig.getCOSObject().getItem(COSName.TYPE))) {
                continue;
            }
            if (sig.getCOSObject().containsKey(COSName.CONTENTS)) {
                return;
                // throw new IOException("DocMDP transform method not allowed if an approval signature exists");
            }
        }

        COSDictionary sigDict = signature.getCOSObject();

        // DocMDP specific stuff
        COSDictionary transformParameters = new COSDictionary();
        transformParameters.setItem(COSName.TYPE, COSName.getPDFName("TransformParams"));
        transformParameters.setInt(COSName.P, accessPermissions);
        transformParameters.setName(COSName.V, "1.2");
        transformParameters.setNeedToBeUpdated(true);

        COSDictionary referenceDict = new COSDictionary();
        referenceDict.setItem(COSName.TYPE, COSName.getPDFName("SigRef"));
        referenceDict.setItem("TransformMethod", COSName.DOCMDP);
        referenceDict.setItem("DigestMethod", COSName.getPDFName("SHA1"));
        referenceDict.setItem("TransformParams", transformParameters);
        referenceDict.setNeedToBeUpdated(true);

        COSArray referenceArray = new COSArray();
        referenceArray.add(referenceDict);
        sigDict.setItem("Reference", referenceArray);
        referenceArray.setNeedToBeUpdated(true);

        // Catalog
        COSDictionary catalogDict = doc.getDocumentCatalog().getCOSObject();
        COSDictionary permsDict = new COSDictionary();
        catalogDict.setItem(COSName.PERMS, permsDict);
        permsDict.setItem(COSName.DOCMDP, signature);
        catalogDict.setNeedToBeUpdated(true);
        permsDict.setNeedToBeUpdated(true);
    }

    /**
     * 资源释放
     */
    @SneakyThrows
    private void release() {
        // 关闭文档
        this.param.getDocument().close();
        // 关闭文档
        this.param.getPdfDocument().close();
    }
}
