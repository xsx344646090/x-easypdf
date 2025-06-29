package org.dromara.pdf.pdfbox.core.ext.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;
import org.dromara.pdf.pdfbox.core.ext.AbstractExpander;
import org.dromara.pdf.pdfbox.core.ext.handler.tokenizer.AbstractTokenizer;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.util.List;

/**
 * 抽象文本助手
 *
 * @author xsx
 * @date 2024/9/25
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
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTextHandler extends AbstractExpander {

    /**
     * 分词器
     */
    protected AbstractTokenizer tokenizer;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractTextHandler(Document document) {
        super(document);
    }

    /**
     * 写入文本
     *
     * @param fontConfiguration 字体配置
     * @param contentStream     内容流
     * @param text              文本
     */
    public abstract void writeText(FontConfiguration fontConfiguration, PDPageContentStream contentStream, TextLineInfo text);

    /**
     * 拆分文本（单行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    public TextLineInfo splitText(FontConfiguration fontConfiguration, String text, float lineWidth) {
        return this.tokenizer.splitText(fontConfiguration, text, lineWidth);
    }

    /**
     * 拆分文本段落（多行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本列表
     */
    public List<TextLineInfo> splitLines(FontConfiguration fontConfiguration, String text, float lineWidth) {
        return this.tokenizer.splitLines(fontConfiguration, text, lineWidth);
    }

    /**
     * 获取文本宽度
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @return 返回文本宽度
     */
    public float getTextWidth(FontConfiguration fontConfiguration, String text) {
        return TextUtil.getTextWidth(text, this.getContext(), fontConfiguration.getSpecialFontNames(), fontConfiguration.getFontName(), fontConfiguration.getFontSize(), fontConfiguration.getCharacterSpacing());
    }

    /**
     * 获取文本高度
     *
     * @param fontConfiguration 字体配置
     * @param rowCount          文本行数
     * @return 返回文本高度
     */
    public float getTextHeight(FontConfiguration fontConfiguration, int rowCount) {
        return TextUtil.getTextHeight(this.getContext(), fontConfiguration.getFontName(), fontConfiguration.getFontSize(), fontConfiguration.getLeading(), rowCount);
    }
}
