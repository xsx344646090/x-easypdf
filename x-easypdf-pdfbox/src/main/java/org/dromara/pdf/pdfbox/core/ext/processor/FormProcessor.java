package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.fixup.AcroFormDefaultFixup;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * 表单处理器
 *
 * @author xsx
 * @date 2023/11/20
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
@Getter
public class FormProcessor extends AbstractProcessor {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(FormProcessor.class);
    /**
     * 字体替换正则（未包含字体大小）
     */
    private static final String NONE_FONT_SIZE_REGEX = "/\\S*";
    /**
     * 字体替换正则（包含字体大小）
     */
    private static final String FONT_SIZE_REGEX = "/.*Tf";
    /**
     * 表单
     */
    private PDAcroForm form;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public FormProcessor(Document document) {
        this(document, true, true);
    }

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public FormProcessor(Document document, boolean isFixForm, boolean isNeedAppearance) {
        super(document);
        this.form = this.initForm(document.getTarget(), isFixForm, isNeedAppearance);
    }

    /**
     * 获取字段
     *
     * @return 返回字段
     */
    public List<PDField> getFields() {
        return this.form.getFields();
    }

    /**
     * 替换key
     *
     * @param keyMap key字典
     */
    public void replaceKey(Map<String, String> keyMap) {
        // 遍历key字典
        keyMap.forEach((key, value) -> {
            // 获取表单字段
            PDField field = this.form.getField(key);
            // 存在字段
            if (Objects.nonNull(field)) {
                // 重置key
                field.setMappingName(value);
            }
        });
        // 重置表单
        this.document.getTarget().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 填写文本
     *
     * @param formMap 表单字典
     */
    public void fillText(Map<String, String> formMap) {
        this.fillText(formMap, null, null);
    }

    /**
     * 填写文本
     *
     * @param formMap  表单字典
     * @param font     字体
     * @param fontSize 字体大小
     */
    public void fillText(Map<String, String> formMap, PDFont font, Integer fontSize) {
        // 检查参数
        Objects.requireNonNull(formMap, "the form map can not be null");
        // 获取表单字典键值集合
        Set<Map.Entry<String, String>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, String> entry : entrySet) {
            // 获取表单字典中对应的pdfBox表单字段
            PDField field = this.form.getField(entry.getKey());
            // 如果pdfBox表单字段不为空，则填充值
            if (Objects.nonNull(field)) {
                // 重置新值
                this.resetText(font, fontSize, field, entry.getValue());
            }
        }
        // 重置表单
        this.document.getTarget().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 填写图像
     *
     * @param formMap 表单字典
     */
    @SneakyThrows
    public void fillImage(Map<String, BufferedImage> formMap) {
        // 检查参数
        Objects.requireNonNull(formMap, "the form map can not be null");
        // 获取表单字典键值集合
        Set<Map.Entry<String, BufferedImage>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, BufferedImage> entry : entrySet) {
            // 获取表单字典中对应的pdfBox表单字段
            PDField field = this.form.getField(entry.getKey());
            // 非空且为按钮类型
            if (Objects.nonNull(field) && field instanceof PDPushButton) {
                // 获取部件列表
                List<PDAnnotationWidget> widgets = field.getWidgets();
                // 非空
                if (!widgets.isEmpty()) {
                    // 获取外观
                    PDAppearanceCharacteristicsDictionary appearanceCharacteristics = field.getWidgets().get(0).getAppearanceCharacteristics();
                    // 非空
                    if (Objects.nonNull(appearanceCharacteristics)) {
                        // 获取图像
                        BufferedImage image = entry.getValue();
                        // 非空
                        if (Objects.nonNull(image)) {
                            // 设置图像
                            appearanceCharacteristics.getCOSObject().setItem(
                                    COSName.I,
                                    PDImageXObject.createFromByteArray(
                                            this.document.getTarget(),
                                            ImageUtil.resetBytes(ImageUtil.toBytes(image, "png")),
                                            "unknown"
                                    ).getCOSObject()
                            );
                        } else {
                            // 清空图像
                            appearanceCharacteristics.getCOSObject().setItem(COSName.I, null);
                        }
                    }
                }
            }
        }
        // 重置表单
        this.document.getTarget().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 清空
     */
    public void clear() {
        // 清空字段
        this.form.getFields().forEach(this::clearField);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        super.close();
        this.form = null;
    }

    /**
     * 初始化表单
     *
     * @param document         pdfbox文档
     * @param isFixForm        是否修复表单
     * @param isNeedAppearance 是否启用外观
     * @return 返回pdfbox表单
     */
    protected PDAcroForm initForm(PDDocument document, boolean isFixForm, boolean isNeedAppearance) {
        // 获取pdfbox文档大纲
        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
        // 定义pdfbox表单
        PDAcroForm acroForm;
        // 如果开启修复表单，则初始化修复后的表单
        if (isFixForm) {
            // 初始化修复后的表单
            acroForm = documentCatalog.getAcroForm(new AcroFormDefaultFixup(document));
        } else {
            // 初始化原始表单
            acroForm = documentCatalog.getAcroForm(null);
        }
        // 如果表单依然为空，则初始化空表单
        if (Objects.isNull(acroForm)) {
            // 初始化空表单
            acroForm = new PDAcroForm(document);
        }
        // 设置外观
        acroForm.setNeedAppearances(isNeedAppearance);
        // 返回表单
        return acroForm;
    }

    /**
     * 重置文本
     *
     * @param font     pdfbox字体
     * @param field    pdfbox表单字段
     * @param newValue 字段新值
     */
    @SneakyThrows
    protected void resetText(PDFont font, Integer fontSize, PDField field, String newValue) {
        // 如果字体不为空，则替换与嵌入字体
        if (Objects.nonNull(font)) {
            // 如果表单字段为文本字段，则重置默认外观
            if (field instanceof PDTextField) {
                // 转换为文本字体
                PDTextField textField = (PDTextField) field;
                // 获取默认外观
                String defaultAppearance = textField.getDefaultAppearance();
                // 使用原字体大小
                if (Objects.isNull(fontSize)) {
                    // 重置默认外观（使用原字体大小）
                    textField.setDefaultAppearance(
                            defaultAppearance.replaceFirst(
                                    NONE_FONT_SIZE_REGEX,
                                    String.join("", "/", font.getName())
                            )
                    );
                } else {
                    // 重置默认外观（使用自定义字体大小）
                    textField.setDefaultAppearance(
                            defaultAppearance.replaceFirst(
                                    FONT_SIZE_REGEX,
                                    String.join(" ", "/", font.getName(), String.valueOf(fontSize), "Tf")
                            )
                    );
                }
            }
        }
        // 设置新值
        field.setValue(newValue);
    }

    /**
     * 清空表单字段
     *
     * @param field 表单字段
     */
    protected void clearField(PDField field) {
        // 如果为终端字段，则删除页面注释
        if (field instanceof PDTerminalField) {
            // 删除页面注释
            field.getWidgets().forEach(v -> Optional.ofNullable(v.getPage()).ifPresent(this::clearAnnotations));
        } else if (field instanceof PDNonTerminalField) {
            // 递归清空
            ((PDNonTerminalField) field).getChildren().forEach(this::clearField);
        }
    }

    /**
     * 清空注解
     *
     * @param page 页面
     */
    protected void clearAnnotations(PDPage page) {
        try {
            page.getAnnotations().clear();
        } catch (IOException e) {
            log.warn("the page annotation can not be cleared, will be ignored");
        }
    }
}
