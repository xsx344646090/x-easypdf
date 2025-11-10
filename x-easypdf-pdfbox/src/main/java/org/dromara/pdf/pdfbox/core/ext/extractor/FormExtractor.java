package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 表单提取器
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
@EqualsAndHashCode(callSuper = true)
public class FormExtractor extends AbstractFormExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public FormExtractor(Document document) {
        super(document);
    }

    /**
     * 提取字段
     *
     * @return 返回字段字典 <p>key = 字段名称，value = 提取字段</p>
     */
    @Override
    public Map<String, PDField> extractField() {
        return this.processField();
    }

    /**
     * 提取文本
     *
     * @return 返回文本字典 <p>key = 字段名称，value = 提取文本</p>
     */
    @Override
    public Map<String, String> extractText() {
        return this.processText();
    }

    /**
     * 提取图像
     *
     * @return 返回图像字典 <p>key = 字段名称，value = 提取图像</p>
     */
    @Override
    public Map<String, BufferedImage> extractImage() {
        return this.processImage();
    }
}
