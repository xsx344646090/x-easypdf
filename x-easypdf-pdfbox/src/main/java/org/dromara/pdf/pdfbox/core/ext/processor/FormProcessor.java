package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.fixup.AcroFormDefaultFixup;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
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
@EqualsAndHashCode(callSuper = true)
public class FormProcessor extends AbstractProcessor {

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
    protected PDAcroForm form;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public FormProcessor(Document document) {
        this(document, false, true);
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
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 填写文本
     *
     * @param formMap  表单字典
     */
    @SneakyThrows
    public void fillText(Map<String, String> formMap) {
        // 检查参数
        Objects.requireNonNull(formMap, "the form map can not be null");
        // 获取表单字典键值集合
        Set<Map.Entry<String, String>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, String> entry : entrySet) {
            // 获取表单字典中对应的pdfBox表单字段
            PDField field = this.form.getField(entry.getKey());
            // 存在字段
            if (Objects.nonNull(field)) {
                // 文本字段
                if (field instanceof PDTextField) {
                    // 设置新值
                    field.setValue(entry.getValue());
                } else {
                    // 提示信息
                    log.warn("the field['" + entry.getKey() + "'] is not text field, will be ignored");
                }
            } else {
                // 提示信息
                log.warn("the field['" + entry.getKey() + "'] is not exist, will be ignored");
            }
        }
        // 重置表单
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
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
                                            this.getDocument(),
                                            ImageUtil.toBytes(image, ImageType.PNG.getType()),
                                            ImageType.PNG.getType()
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
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 移除字段
     *
     * @param keys 字段key
     */
    @SneakyThrows
    public void remove(String... keys) {
        // 非空
        if (Objects.nonNull(keys) && keys.length > 0) {
            // 定义待清空字段
            List<PDField> fields = new ArrayList<>(keys.length);
            // 遍历字段key
            for (String key : keys) {
                // 获取表单字段
                PDField field = this.form.getField(key);
                // 存在字段
                if (Objects.nonNull(field)) {
                    // 添加表单字段
                    fields.add(field);
                }
            }
            // 清空
            this.form.flatten(fields, false);
        } else {
            // 清空
            this.form.flatten();
        }
        // 重置表单
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
    }

    /**
     * 清空
     */
    @SneakyThrows
    public void clear() {
        // 清空字段
        this.form.flatten(this.getFields(), true);
        // 重置表单
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
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
        // 设置缓存
        acroForm.setCacheFields(true);
        // 返回表单
        return acroForm;
    }
}
