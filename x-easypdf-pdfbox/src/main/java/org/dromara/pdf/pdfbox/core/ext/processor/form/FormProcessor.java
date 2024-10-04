package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.fixup.AcroFormDefaultFixup;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.AbstractProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import java.util.function.Function;

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
     * 表单
     */
    protected PDAcroForm form;
    /**
     * 字体
     */
    protected PDFont font;
    /**
     * 字体大小
     */
    protected Float fontSize;
    /**
     * 字体颜色
     */
    protected Color fontColor;

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
     * 设置字体
     *
     * @param fontName  字体名称
     * @param fontSize  字体大小
     * @param fontColor 字体颜色
     */
    public void setFont(String fontName, float fontSize, Color fontColor) {
        // 初始化字体
        this.font = PdfHandler.getFontHandler().getPDFont(this.getDocument(), fontName, true);
        // 初始化字体大小
        this.fontSize = fontSize;
        // 初始化字体颜色
        this.fontColor = fontColor;
        // 添加字体
        this.form.getDefaultResources().put(COSName.getPDFName(this.font.getName()), this.font);
    }

    /**
     * 添加字段
     *
     * @param function 表单助手
     */
    public void addField(Function<PDAcroForm, PDField> function) {
        this.getFields().add(function.apply(this.form));
        this.reset();
    }

    /**
     * 替换关键字
     *
     * @param keyMap 关键字字典
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
        this.reset();
    }

    /**
     * 移除字段
     *
     * @param keys 字段关键字
     */
    @SneakyThrows
    public void remove(String... keys) {
        // 非空
        if (Objects.nonNull(keys) && keys.length > 0) {
            // 获取字段列表
            List<PDField> fields = this.getFields();
            // 遍历字段key
            for (String key : keys) {
                // 获取表单字段
                PDField field = this.form.getField(key);
                // 存在字段
                if (Objects.nonNull(field)) {
                    // 移除表单字段
                    fields.remove(field);
                }
            }
            // 重置字段
            this.form.setFields(fields);
        } else {
            // 重置字段
            this.form.setFields(new ArrayList<>(0));
        }
        // 重置表单
        this.reset();
    }

    /**
     * 填写文本
     *
     * @param formMap 表单字典
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
                    // 添加外观
                    if (this.isAddAppearance()) {
                        // 设置默认外观
                        ((PDTextField) field).setDefaultAppearance(this.createDefaultAppearance());
                    }
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
        this.reset();
        // 添加外观
        if (this.isAddAppearance()) {
            // 嵌入字体
            this.font.subset();
        }
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
        this.reset();
    }

    /**
     * 扁平化表单
     *
     * @param refreshAppearances 是否刷新外观
     * @param keys               字段关键字
     */
    @SneakyThrows
    public void flatten(boolean refreshAppearances, String... keys) {
        // 获取表单字段
        List<PDField> fields = this.getFields();
        // 非空
        if (Objects.nonNull(keys) && keys.length > 0) {
            // 重置字段
            fields = new ArrayList<>(keys.length);
            // 遍历关键字
            for (String key : keys) {
                // 获取字段
                PDField field = this.form.getField(key);
                // 非空
                if (Objects.nonNull(field)) {
                    // 添加字段
                    fields.add(field);
                } else {
                    // 提示信息
                    log.warn("the field['" + key + "'] is not exist, will be ignored");
                }
            }
        }
        // 扁平化
        this.form.flatten(fields, refreshAppearances);
        // 重置表单
        this.reset();
    }

    /**
     * 只读
     *
     * @param keys 关键字
     */
    public void readOnly(String... keys) {
        // 非空
        if (Objects.nonNull(keys) && keys.length > 0) {
            // 遍历关键字
            for (String key : keys) {
                // 获取字段
                PDField field = this.form.getField(key);
                // 非空
                if (Objects.nonNull(field)) {
                    // 设置只读
                    field.setReadOnly(true);
                } else {
                    // 提示信息
                    log.warn("the field['" + key + "'] is not exist, will be ignored");
                }
            }
        } else {
            // 遍历字段
            this.getFields().forEach(field -> {
                // 设置只读
                field.setReadOnly(true);
            });
        }
        // 重置表单
        this.reset();
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

    /**
     * 是否添加外观
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isAddAppearance() {
        return Objects.nonNull(this.font);
    }

    /**
     * 创建默认样式字符串
     */
    protected String createDefaultAppearance() {
        Float fontSize = this.fontSize;
        Color fontColor = this.fontColor;
        if (Objects.isNull(fontSize)) {
            fontSize = 12F;
        }
        if (Objects.isNull(fontColor)) {
            fontColor = Color.BLACK;
        }
        // 返回外观
        return String.format("/%s %d Tf %s", font.getName(), fontSize.intValue(), ColorUtil.toPDColorString(fontColor));
    }

    /**
     * 重置表单
     */
    protected void reset() {
        this.getDocument().getDocumentCatalog().setAcroForm(this.form);
    }
}
