package org.dromara.pdf.pdfbox.support;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.handlers.PDFreeTextAppearanceHandler;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.util.Objects;

/**
 * 文本外观助手
 *
 * @author xsx
 * @date 2023/12/28
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
public class DefaultFreeTextAppearanceHandler extends PDFreeTextAppearanceHandler {

    /**
     * 文档
     */
    protected Document document;
    /**
     * 注释
     */
    protected PDAnnotation annotation;
    /**
     * 字体
     */
    protected PDFont font;

    /**
     * 有参构造
     *
     * @param document   文档
     * @param annotation 注释
     */
    public DefaultFreeTextAppearanceHandler(Document document, PDAnnotation annotation) {
        this(document, annotation, null);
    }

    /**
     * 有参构造
     *
     * @param document   文档
     * @param annotation 注释
     * @param font       字体
     */
    public DefaultFreeTextAppearanceHandler(Document document, PDAnnotation annotation, PDFont font) {
        super(annotation, document.getTarget());
        this.document = document;
        this.annotation = annotation;
        this.font = font;
    }

    /**
     * 获取默认字体
     *
     * @return 返回字体
     */
    @SneakyThrows
    protected PDFont getDefaultFont() {
        if (Objects.isNull(this.font)) {
            this.font = this.document.getFont();
        }
        return this.font;
    }

    /**
     * 生成正常外观
     */
    @SneakyThrows
    @Override
    public void generateNormalAppearance() {
        // 生成正常外观
        super.generateNormalAppearance();
        // 获取字体处理器并将当前字体添加到子集中
        PdfHandler.getFontHandler().addToSubset(this.document.getTarget(), this.font, this.annotation.getContents());
        // 释放当前对象
        this.release();

    }

    /**
     * 释放资源
     */
    protected void release() {
        this.document = null;
        this.annotation = null;
        this.font = null;
    }
}
