package org.dromara.pdf.pdfbox.core.ext.processor.form;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.fixup.AcroFormDefaultFixup;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.AbstractProcessor;
import org.dromara.pdf.pdfbox.handler.FontHandler;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

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
     * 字体名称
     */
    protected String fontName;
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
        this(document, false, false);
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
        // 初始化字体名称
        this.fontName = fontName;
        // 初始化字体大小
        this.fontSize = fontSize;
        // 初始化字体颜色
        this.fontColor = fontColor;
    }

    /**
     * 添加字段
     *
     * @param builders 构建器
     */
    public void addField(AbstractFormFieldBuilder... builders) {
        List<PDField> fields = this.getFields();
        for (AbstractFormFieldBuilder builder : builders) {
            fields.add(builder.build(this.form));
        }
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
        // 定义字体
        PDFont font = null;
        // 获取表单字典键值集合
        Set<Map.Entry<String, String>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, String> entry : entrySet) {
            // 获取字段
            PDField field = this.form.getField(entry.getKey());
            // 非空
            if (Objects.nonNull(field)) {
                // 文本类型
                if (field instanceof PDTextField) {
                    // 转文本字段
                    PDTextField textField = (PDTextField) field;
                    // 添加外观
                    if (this.isAddAppearance()) {
                        // 初始化字体
                        if (Objects.isNull(font)) {
                            // 初始化字体
                            font = PdfHandler.getFontHandler().getPDFont(this.getDocument(), this.fontName, true);
                            // 添加字体
                            this.form.getDefaultResources().put(COSName.getPDFName(font.getName()), font);
                        }
                        // 重置外观
                        textField.setDefaultAppearance(this.createDefaultAppearance(font));
                    }
                    // 嵌入文本
                    FontHandler.getInstance().addToSubset(this.getDocument(), font, entry.getValue());
                    // 设置新值
                    textField.setValue(entry.getValue());
                } else {
                    // 提示信息
                    log.warn("the field['" + entry.getKey() + "'] is not text field, will be ignored");
                }
            } else {
                // 提示信息
                log.warn("the field['" + entry.getKey() + "'] is not exist, will be ignored");
            }
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
            // 非空
            if (Objects.nonNull(field)) {
                // 按钮类型
                if (field instanceof PDPushButton) {
                    // 获取部件列表
                    List<PDAnnotationWidget> widgets = field.getWidgets();
                    // 非空
                    if (!widgets.isEmpty()) {
                        // 获取小部件
                        PDAnnotationWidget widget = field.getWidgets().get(0);
                        // 获取外观
                        PDAppearanceCharacteristicsDictionary appearanceCharacteristics = widget.getAppearanceCharacteristics();
                        // 初始化外观
                        if (Objects.isNull(appearanceCharacteristics)) {
                            appearanceCharacteristics = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
                        }
                        // 获取图像
                        BufferedImage image = entry.getValue();
                        // 非空
                        if (Objects.nonNull(image)) {
                            // 获取字典
                            COSDictionary dictionary = appearanceCharacteristics.getCOSObject();
                            // 设置图像
                            dictionary.setItem(
                                    COSName.I,
                                    CommonUtil.createImage(this.getContext(), ImageUtil.toBytes(image, ImageType.PNG.getType())).getCOSObject().getCOSObject()
                            );
                            // 图标位置
                            COSName tp = COSName.getPDFName("TP");
                            // 不包含
                            if (!dictionary.containsKey(tp)) {
                                // 设置仅图标
                                dictionary.setItem(tp, COSInteger.ONE);
                            }
                        } else {
                            // 清空图像
                            appearanceCharacteristics.getCOSObject().setItem(COSName.I, null);
                        }
                        // 设置外观
                        widget.setAppearanceCharacteristics(appearanceCharacteristics);
                    }
                } else {
                    // 提示信息
                    log.warn("the field['" + entry.getKey() + "'] is not image field, will be ignored");
                }
            } else {
                // 提示信息
                log.warn("the field['" + entry.getKey() + "'] is not exist, will be ignored");
            }
        }
    }

    /**
     * 填写单选
     *
     * @param key   字段键
     * @param index 选项索引
     */
    public void fillRadio(String key, int index) {
        // 检查参数
        Objects.requireNonNull(key, "the key can not be null");
        // 获取字段
        PDField field = this.form.getField(key);
        // 非空
        if (Objects.nonNull(field)) {
            // 单选类型
            if (field instanceof PDRadioButton) {
                try {
                    // 设置状态
                    field.getWidgets().get(index).setAppearanceState(String.valueOf(index));
                } catch (Exception e) {
                    throw new IllegalArgumentException("the field['" + key + "'] radio index is error");
                }
            } else {
                // 提示信息
                log.warn("the field['" + key + "'] is not radio field, will be ignored");
            }
        } else {
            // 提示信息
            log.warn("the field['" + key + "'] is not exist, will be ignored");
        }
    }

    /**
     * 填写多选
     *
     * @param formMap 表单字典
     */
    public void fillCheckBox(Map<String, Boolean> formMap) {
        // 检查参数
        Objects.requireNonNull(formMap, "the form map can not be null");
        // 获取表单字典键值集合
        Set<Map.Entry<String, Boolean>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, Boolean> entry : entrySet) {
            // 获取key
            String key = entry.getKey();
            // 获取字段
            PDField field = this.form.getField(entry.getKey());
            // 非空
            if (Objects.nonNull(field)) {
                // 多选类型
                if (field instanceof PDCheckBox) {
                    // 设置状态
                    field.getWidgets().get(0).setAppearanceState(entry.getValue() ? "0" : "off");
                } else {
                    // 提示信息
                    log.warn("the field['" + key + "'] is not checkbox field, will be ignored");
                }
            } else {
                // 提示信息
                log.warn("the field['" + key + "'] is not exist, will be ignored");
            }
        }
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
            this.getFields().forEach(field -> field.setReadOnly(true));
        }
    }

    /**
     * 刷新
     */
    @SneakyThrows
    public void flush() {
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
        acroForm.setCacheFields(false);
        // 返回表单
        return acroForm;
    }

    /**
     * 是否添加外观
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isAddAppearance() {
        return Objects.nonNull(this.fontName);
    }

    /**
     * 创建默认样式字符串
     *
     * @param font 字体
     */
    protected String createDefaultAppearance(PDFont font) {
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
}
