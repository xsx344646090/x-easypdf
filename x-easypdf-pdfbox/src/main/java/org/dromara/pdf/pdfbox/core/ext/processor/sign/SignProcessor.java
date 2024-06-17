package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.AbstractProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 签名处理器
 *
 * @author xsx
 * @date 2024/3/12
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
public class SignProcessor extends AbstractProcessor {

    /**
     * 签名器
     */
    protected SignatureInterface signer;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public SignProcessor(Document document) {
        super(document);
    }

    /**
     * 获取签名字段
     *
     * @return 返回签名字段
     */
    public List<PDSignatureField> getFields() {
        return this.getDocument().getSignatureFields();
    }

    /**
     * 追加签名字段
     *
     * @param signature 签名
     * @return 返回签名字段
     */
    @SneakyThrows
    public PDSignatureField append(PDSignature signature) {
        PDAcroForm form = Optional.ofNullable(this.getDocument().getDocumentCatalog().getAcroForm(null)).orElse(new PDAcroForm(this.getDocument()));
        PDSignatureField field = new PDSignatureField(form);
        field.setValue(signature);
        List<PDField> fields = form.getFields();
        fields.add(field);
        form.setFields(fields);
        this.getDocument().getDocumentCatalog().setAcroForm(form);
        return field;
    }

    /**
     * 移除签名字段
     *
     * @param index 字段索引
     */
    public void remove(int index) {
        PDAcroForm form = Optional.ofNullable(this.getDocument().getDocumentCatalog().getAcroForm(null)).orElse(new PDAcroForm(this.getDocument()));
        List<PDField> fields = form.getFields();
        if (fields.size() <= index) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
            return;
        }
        List<PDField> newFields = new ArrayList<>(fields.size());
        int i = 0;
        for (PDField field : fields) {
            if (index != i) {
                newFields.add(field);
            }
            i++;
        }
        form.setFields(newFields);
        this.getDocument().getDocumentCatalog().setAcroForm(form);
    }

    /**
     * 签名
     *
     * @param signature    签名字段
     * @param options      签名选项
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void sign(PDSignature signature, SignOptions options, OutputStream outputStream) {
        Objects.requireNonNull(signature, "the signature can not be null");
        Objects.requireNonNull(options, "the options can not be null");
        Objects.requireNonNull(outputStream, "the outputStream can not be null");
        if (Objects.isNull(this.signer)) {
            this.signer = new DefaultSigner(options);
        }
        options.init();
        this.setMdpPermission(this.getDocument(), signature, options.getPermission());
        this.lockSignFields(this.getDocument(), signature);
        this.getDocument().addSignature(signature, this.signer, options.createOptions(this.getDocument(), signature));
        this.getDocument().saveIncremental(outputStream);
    }

    /**
     * 多重签名
     * <p>注：多次签名时，使用此方法</p>
     *
     * @param signature    签名字段
     * @param options      签名选项
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void multiSign(PDSignature signature, SignOptions options, ByteArrayOutputStream outputStream) {
        this.sign(signature, options, outputStream);
        this.release();
        this.document = PdfHandler.getDocumentHandler().load(outputStream.toByteArray());
    }

    /**
     * 释放
     */
    public void release() {
        this.document.close();
    }

    /**
     * 获取mdp权限
     *
     * @param document pdfbox文档
     * @return 返回mdp权限
     */
    protected int getMdpPermission(PDDocument document) {
        COSBase base = document.getDocumentCatalog().getCOSObject().getDictionaryObject(COSName.PERMS);
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
                                    int accessPermissions = transformDict.getInt(COSName.P, 0);
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
     * @param document   pdfbox文档
     * @param signature  pdfbox签名
     * @param permission 签名权限
     */
    @SneakyThrows
    protected void setMdpPermission(PDDocument document, PDSignature signature, SignPermission permission) {
        List<PDSignature> signatureList = document.getSignatureDictionaries();
        for (PDSignature sig : signatureList) {
            if (COSName.DOC_TIME_STAMP.equals(sig.getCOSObject().getItem(COSName.TYPE))) {
                continue;
            }
            if (sig.getCOSObject().containsKey(COSName.CONTENTS)) {
                return;
            }
        }

        COSDictionary sigDict = signature.getCOSObject();

        // DocMDP specific stuff
        COSDictionary transformParameters = new COSDictionary();
        transformParameters.setItem(COSName.TYPE, COSName.getPDFName("TransformParams"));
        transformParameters.setInt(COSName.P, permission.getType());
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
        COSDictionary catalogDict = document.getDocumentCatalog().getCOSObject();
        COSDictionary permsDict = new COSDictionary();
        catalogDict.setItem(COSName.PERMS, permsDict);
        permsDict.setItem(COSName.DOCMDP, signature);
        catalogDict.setNeedToBeUpdated(true);
        permsDict.setNeedToBeUpdated(true);
    }

    /**
     * 锁定签名字段
     *
     * @param document  文档
     * @param signature 签名
     */
    protected void lockSignFields(PDDocument document, PDSignature signature) {
        // 获取签名字典
        COSDictionary dictionary = signature.getCOSObject();
        // 获取pdfbox表单
        PDAcroForm form = document.getDocumentCatalog().getAcroForm(null);
        // 锁定字段
        if (Objects.nonNull(form)) {
            final Predicate<PDField> shallBeLocked;
            final List<String> fieldNames = this.getLockFieldNames(dictionary);
            final COSName action = dictionary.getCOSName(COSName.getPDFName("Action"));
            if (Objects.nonNull(action)) {
                if (action.equals(COSName.getPDFName("Include"))) {
                    shallBeLocked = f -> fieldNames.contains(f.getFullyQualifiedName());
                } else if (action.equals(COSName.getPDFName("Exclude"))) {
                    shallBeLocked = f -> !fieldNames.contains(f.getFullyQualifiedName());
                } else if (action.equals(COSName.getPDFName("All"))) {
                    shallBeLocked = f -> true;
                } else {
                    shallBeLocked = f -> false;
                }
                this.lockSignFields(form.getFields(), shallBeLocked);
            }
        }
    }

    /**
     * 获取锁定签名字段名称
     *
     * @param dictionary 签名字典
     * @return 返回锁定签名字段名称
     */
    protected List<String> getLockFieldNames(COSDictionary dictionary) {
        final COSArray fields = dictionary.getCOSArray(COSName.FIELDS);
        if (Objects.isNull(fields)) {
            return Collections.emptyList();
        } else {
            return fields.toList()
                    .stream()
                    .filter(c -> (c instanceof COSString))
                    .map(s -> ((COSString) s).getString())
                    .collect(Collectors.toList());
        }
    }

    /**
     * 锁定签名字段
     *
     * @param fields        表单字段
     * @param shallBeLocked 锁定
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean lockSignFields(List<PDField> fields, Predicate<PDField> shallBeLocked) {
        boolean isUpdated = false;
        if (fields != null) {
            for (PDField field : fields) {
                boolean isUpdatedField = false;
                if (shallBeLocked.test(field)) {
                    field.setFieldFlags(field.getFieldFlags() | 1);
                    if (field instanceof PDTerminalField) {
                        for (PDAnnotationWidget widget : field.getWidgets()) {
                            widget.setLocked(true);
                        }
                    }
                    isUpdatedField = true;
                }
                if (field instanceof PDNonTerminalField) {
                    if (this.lockSignFields(((PDNonTerminalField) field).getChildren(), shallBeLocked)) {
                        isUpdatedField = true;
                    }
                }
                if (isUpdatedField) {
                    field.getCOSObject().setNeedToBeUpdated(true);
                    PDAppearanceDictionary appearance = field.getWidgets().get(0).getAppearance();
                    appearance.getCOSObject().setNeedToBeUpdated(true);
                    appearance.getNormalAppearance().getCOSObject().setNeedToBeUpdated(true);
                    isUpdated = true;
                }
            }
        }
        return isUpdated;
    }

    /**
     * 重置签名表单
     *
     * @param target 目标文档
     */
    protected void resetSignForm(PDDocument target) {
        // 获取pdfbox表单
        PDAcroForm form = target.getDocumentCatalog().getAcroForm(null);
        // 如果表单不为空且首次使用，则清除首次使用项
        if (Objects.nonNull(form) && form.getNeedAppearances()) {
            // 如果表单字段为空，则清除首次使用项
            if (form.getFields().isEmpty()) {
                // 清除首次使用项
                form.getCOSObject().removeItem(COSName.NEED_APPEARANCES);
            }
        }
    }
}
