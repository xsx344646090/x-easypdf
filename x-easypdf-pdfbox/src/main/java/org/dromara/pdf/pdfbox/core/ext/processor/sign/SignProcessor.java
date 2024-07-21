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
        // 获取文档的AcroForm，如果不存在则创建一个新的PDAcroForm对象
        PDAcroForm form = Optional.ofNullable(this.getDocument().getDocumentCatalog().getAcroForm(null)).orElse(new PDAcroForm(this.getDocument()));
        // 创建一个新的PDSignatureField对象
        PDSignatureField field = new PDSignatureField(form);
        // 设置签名值
        field.setValue(signature);
        // 获取AcroForm中的字段列表
        List<PDField> fields = form.getFields();
        // 将新的签名字段添加到字段列表中
        fields.add(field);
        // 将更新后的字段列表设置回AcroForm中
        form.setFields(fields);
        // 将更新后的AcroForm设置回文档的DocumentCatalog中
        this.getDocument().getDocumentCatalog().setAcroForm(form);
        // 返回签名字段
        return field;
    }
    
    /**
     * 移除签名字段
     *
     * @param index 字段索引
     */
    public void remove(int index) {
        // 获取文档的表单
        PDAcroForm form = Optional.ofNullable(this.getDocument().getDocumentCatalog().getAcroForm(null)).orElse(new PDAcroForm(this.getDocument()));
        // 获取表单中的字段
        List<PDField> fields = form.getFields();
        // 如果索引超出范围，则提示信息并返回
        if (fields.size() <= index) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
            return;
        }
        // 创建一个新的字段列表
        List<PDField> newFields = new ArrayList<>(fields.size());
        // 遍历字段列表
        int i = 0;
        for (PDField field : fields) {
            // 如果索引不等于当前字段索引，则将字段添加到新字段列表中
            if (index != i) {
                newFields.add(field);
            }
            i++;
        }
        // 设置新字段列表
        form.setFields(newFields);
        // 将新表单设置到文档目录中
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
        // 检查签名是否为空，如果为空则抛出异常
        Objects.requireNonNull(signature, "the signature can not be null");
        // 检查选项是否为空，如果为空则抛出异常
        Objects.requireNonNull(options, "the options can not be null");
        // 检查输出流是否为空，如果为空则抛出异常
        Objects.requireNonNull(outputStream, "the outputStream can not be null");
        // 如果签名者对象为空，则创建一个新的默认签名者
        if (Objects.isNull(this.signer)) {
            this.signer = new DefaultSigner(options);
        }
        // 初始化选项
        options.init();
        // 设置文档的权限
        this.setMdpPermission(this.getDocument(), signature, options.getPermission());
        // 锁定签名字段
        this.lockSignFields(this.getDocument(), signature);
        // 添加签名到文档
        this.getDocument().addSignature(signature, this.signer, options.createOptions(this.getDocument(), signature));
        // 保存文档
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
        // 获取文档目录中的权限字典
        COSBase base = document.getDocumentCatalog().getCOSObject().getDictionaryObject(COSName.PERMS);
        // 如果权限字典是一个字典对象
        if (base instanceof COSDictionary) {
            // 将权限字典转换为字典对象
            COSDictionary permsDict = (COSDictionary) base;
            // 获取权限字典中的文档权限字典
            base = permsDict.getDictionaryObject(COSName.DOCMDP);
            // 如果文档权限字典是一个字典对象
            if (base instanceof COSDictionary) {
                // 将文档权限字典转换为字典对象
                COSDictionary signatureDict = (COSDictionary) base;
                // 获取文档权限字典中的引用数组
                base = signatureDict.getDictionaryObject("Reference");
                // 如果引用数组是一个数组对象
                if (base instanceof COSArray) {
                    // 将引用数组转换为数组对象
                    COSArray refArray = (COSArray) base;
                    // 遍历引用数组
                    for (int i = 0; i < refArray.size(); ++i) {
                        // 获取引用数组中的对象
                        base = refArray.getObject(i);
                        // 如果对象是一个字典对象
                        if (base instanceof COSDictionary) {
                            // 将对象转换为字典对象
                            COSDictionary sigRefDict = (COSDictionary) base;
                            // 如果字典对象中的TransformMethod是DOCMDP
                            if (COSName.DOCMDP.equals(sigRefDict.getDictionaryObject("TransformMethod"))) {
                                // 获取字典对象中的TransformParams
                                base = sigRefDict.getDictionaryObject("TransformParams");
                                // 如果TransformParams是一个字典对象
                                if (base instanceof COSDictionary) {
                                    // 将TransformParams转换为字典对象
                                    COSDictionary transformDict = (COSDictionary) base;
                                    // 获取字典对象中的P值
                                    int accessPermissions = transformDict.getInt(COSName.P, 0);
                                    // 如果P值不在1到3之间，则将其设置为2
                                    if (accessPermissions < 1 || accessPermissions > 3) {
                                        accessPermissions = 2;
                                    }
                                    // 返回P值
                                    return accessPermissions;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 如果没有找到符合条件的权限，则返回0
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
        // 获取文档中的签名字典列表
        List<PDSignature> signatureList = document.getSignatureDictionaries();
        // 遍历签名字典列表
        for (PDSignature sig : signatureList) {
            // 如果签名类型为DOC_TIME_STAMP，则跳过
            if (COSName.DOC_TIME_STAMP.equals(sig.getCOSObject().getItem(COSName.TYPE))) {
                continue;
            }
            // 如果签名字典中包含CONTENTS项，则返回
            if (sig.getCOSObject().containsKey(COSName.CONTENTS)) {
                return;
            }
        }
        
        // 获取签名字典
        COSDictionary sigDict = signature.getCOSObject();
        
        // 创建转换参数字典
        COSDictionary transformParameters = new COSDictionary();
        // 设置转换参数类型为TransformParams
        transformParameters.setItem(COSName.TYPE, COSName.getPDFName("TransformParams"));
        // 设置转换参数P值为权限类型
        transformParameters.setInt(COSName.P, permission.getType());
        // 设置转换参数V值为1.2
        transformParameters.setName(COSName.V, "1.2");
        // 设置转换参数需要更新
        transformParameters.setNeedToBeUpdated(true);
        
        // 创建签名引用字典
        COSDictionary referenceDict = new COSDictionary();
        // 设置签名引用类型为SigRef
        referenceDict.setItem(COSName.TYPE, COSName.getPDFName("SigRef"));
        // 设置签名引用转换方法为DocMDP
        referenceDict.setItem("TransformMethod", COSName.DOCMDP);
        // 设置签名引用摘要方法为SHA1
        referenceDict.setItem("DigestMethod", COSName.getPDFName("SHA1"));
        // 设置签名引用转换参数为transformParameters
        referenceDict.setItem("TransformParams", transformParameters);
        // 设置签名引用需要更新
        referenceDict.setNeedToBeUpdated(true);
        
        // 创建签名引用数组
        COSArray referenceArray = new COSArray();
        // 将签名引用字典添加到签名引用数组中
        referenceArray.add(referenceDict);
        // 将签名引用数组添加到签名字典中
        sigDict.setItem("Reference", referenceArray);
        // 设置签名引用数组需要更新
        referenceArray.setNeedToBeUpdated(true);
        
        // 获取文档目录
        COSDictionary catalogDict = document.getDocumentCatalog().getCOSObject();
        // 创建权限字典
        COSDictionary permsDict = new COSDictionary();
        // 将权限字典添加到文档目录中
        catalogDict.setItem(COSName.PERMS, permsDict);
        // 将签名添加到权限字典中
        permsDict.setItem(COSName.DOCMDP, signature);
        // 设置文档目录需要更新
        catalogDict.setNeedToBeUpdated(true);
        // 设置权限字典需要更新
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
            // 定义一个final类型的Predicate对象，用于判断PDField是否应该被锁定
            final Predicate<PDField> shallBeLocked;
            // 获取需要锁定的字段名列表
            final List<String> fieldNames = this.getLockFieldNames(dictionary);
            // 获取字典中的Action字段
            final COSName action = dictionary.getCOSName(COSName.getPDFName("Action"));
            // 判断action是否为空
            if (Objects.nonNull(action)) {
                // 如果action为Include，则锁定fieldNames中包含的字段
                if (action.equals(COSName.getPDFName("Include"))) {
                    shallBeLocked = f -> fieldNames.contains(f.getFullyQualifiedName());
                    // 如果action为Exclude，则锁定fieldNames中不包含的字段
                } else if (action.equals(COSName.getPDFName("Exclude"))) {
                    shallBeLocked = f -> !fieldNames.contains(f.getFullyQualifiedName());
                    // 如果action为All，则锁定所有字段
                } else if (action.equals(COSName.getPDFName("All"))) {
                    shallBeLocked = f -> true;
                    // 否则不锁定任何字段
                } else {
                    shallBeLocked = f -> false;
                }
                // 锁定字段
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
        // 获取字典中的FIELDS数组
        final COSArray fields = dictionary.getCOSArray(COSName.FIELDS);
        // 如果FIELDS数组为空，则返回一个空的列表
        if (Objects.isNull(fields)) {
            return Collections.emptyList();
            // 否则，将FIELDS数组转换为列表，并过滤出COSString类型的元素，将其转换为字符串，并收集到一个新的列表中
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
        // 定义一个布尔变量，用于判断是否更新
        boolean isUpdated = false;
        // 如果fields不为空
        if (fields != null) {
            // 遍历fields中的每个字段
            for (PDField field : fields) {
                // 定义一个布尔变量，用于判断当前字段是否更新
                boolean isUpdatedField = false;
                // 如果当前字段需要被锁定
                if (shallBeLocked.test(field)) {
                    // 将当前字段的字段标志设置为锁定
                    field.setFieldFlags(field.getFieldFlags() | 1);
                    // 如果当前字段是PDTerminalField类型
                    if (field instanceof PDTerminalField) {
                        // 遍历当前字段的每个控件
                        for (PDAnnotationWidget widget : field.getWidgets()) {
                            // 将控件设置为锁定
                            widget.setLocked(true);
                        }
                    }
                    // 当前字段已更新
                    isUpdatedField = true;
                }
                // 如果当前字段是PDNonTerminalField类型
                if (field instanceof PDNonTerminalField) {
                    // 如果当前字段的子字段需要被锁定
                    if (this.lockSignFields(((PDNonTerminalField) field).getChildren(), shallBeLocked)) {
                        // 当前字段已更新
                        isUpdatedField = true;
                    }
                }
                // 如果当前字段已更新
                if (isUpdatedField) {
                    // 将当前字段的COSObject设置为需要更新
                    field.getCOSObject().setNeedToBeUpdated(true);
                    // 获取当前字段的第一个控件的Appearance
                    PDAppearanceDictionary appearance = field.getWidgets().get(0).getAppearance();
                    // 将Appearance的COSObject设置为需要更新
                    appearance.getCOSObject().setNeedToBeUpdated(true);
                    // 将Appearance的NormalAppearance的COSObject设置为需要更新
                    appearance.getNormalAppearance().getCOSObject().setNeedToBeUpdated(true);
                    // 标记为已更新
                    isUpdated = true;
                }
            }
        }
        // 返回是否更新
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
