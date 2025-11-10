package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSName;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDResources;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 抽象表单提取器
 *
 * @author xsx
 * @date 2024/2/21
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
public abstract class AbstractFormExtractor extends AbstractExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractFormExtractor(Document document) {
        super(document);
    }

    /**
     * 提取字段
     *
     * @return 返回字段字典 <p>key = 字段名称，value = 提取字段</p>
     */
    public abstract Map<String, PDField> extractField();

    /**
     * 提取文本
     *
     * @return 返回文本字典 <p>key = 字段名称，value = 提取文本</p>
     */
    public abstract Map<String, String> extractText();

    /**
     * 提取图像
     *
     * @return 返回图像字典 <p>key = 字段名称，value = 提取图像</p>
     */
    public abstract Map<String, BufferedImage> extractImage();

    /**
     * 处理字段
     *
     * @return 返回字段字典 <p>key = 字段名称，value = 提取字段</p>
     */
    protected Map<String, PDField> processField() {
        // 定义字段字典
        Map<String, PDField> data = new HashMap<>(32);
        // 处理
        this.process(data, (container, field) -> container.put(field.getFullyQualifiedName(), field));
        // 返回字段字典
        return data;
    }

    /**
     * 处理文本
     *
     * @return 返回文本字典 <p>key = 字段名称，value = 提取文本</p>
     */
    protected Map<String, String> processText() {
        // 定义文本字典
        Map<String, String> data = new HashMap<>(32);
        // 处理
        this.process(data, (container, field) -> container.put(field.getFullyQualifiedName(), field.getValueAsString()));
        // 返回文本字典
        return data;
    }

    /**
     * 处理图像
     *
     * @return 返回图像字典 <p>key = 字段名称，value = 提取图像</p>
     */
    @SneakyThrows
    protected Map<String, BufferedImage> processImage() {
        // 定义图像字典
        Map<String, BufferedImage> data = new HashMap<>(32);
        // 处理
        this.process(data, (container, field) -> {
            // 获取部件列表
            List<PDAnnotationWidget> widgets = field.getWidgets();
            // 非空
            if (!widgets.isEmpty()) {
                // 获取外观
                PDAppearanceCharacteristicsDictionary appearanceCharacteristics = field.getWidgets().get(0).getAppearanceCharacteristics();
                // 非空
                if (Objects.nonNull(appearanceCharacteristics)) {
                    // 获取普通图标
                    PDFormXObject normalIcon = appearanceCharacteristics.getNormalIcon();
                    // 非空
                    if (Objects.nonNull(normalIcon)) {
                        // 获取资源
                        PDResources resources = normalIcon.getResources();
                        // 非空
                        if (Objects.nonNull(resources)) {
                            // 获取资源内容名称
                            Iterable<COSName> objectNames = resources.getXObjectNames();
                            // 遍历资源内容名称
                            for (COSName objectName : objectNames) {
                                try {
                                    // 获取资源内容
                                    PDXObject xObject = resources.getXObject(objectName);
                                    // 添加图像
                                    if (xObject instanceof PDImage) {
                                        // 添加图像
                                        container.put(field.getFullyQualifiedName(), ((PDImage) xObject).getImage());
                                        // 结束
                                        break;
                                    }
                                } catch (Exception e) {
                                    log.error("the object name['" + objectName.getName() + "'] is invalid, will be ignored");
                                }
                            }
                        }
                    }
                }
            }
        });
        // 返回图像字典
        return data;
    }

    /**
     * 处理
     *
     * @param container 容器
     * @param consumer  消费者
     * @param <T>       容器类型
     */
    protected <T> void process(T container, BiConsumer<T, PDField> consumer) {
        // 获取pdfBox表单
        PDAcroForm form = this.getDocument().getDocumentCatalog().getAcroForm();
        // 如果表单不为空，则提取表单内容
        if (Objects.nonNull(form)) {
            // 获取表单字段列表
            List<PDField> fields = form.getFields();
            // 遍历表单字段列表
            for (PDField field : fields) {
                // 提取表单内容
                consumer.accept(container, field);
            }
        }
    }
}
