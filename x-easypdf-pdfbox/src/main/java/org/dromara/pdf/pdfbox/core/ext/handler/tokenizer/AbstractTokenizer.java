package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;
import org.dromara.pdf.pdfbox.util.TextUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.List;

/**
 * 抽象分词器
 *
 * @author xsx
 * @date 2025/4/19
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
@Setter
public abstract class AbstractTokenizer {

    /**
     * 上下文
     */
    protected Context context;

    /**
     * 拆分文本（单行）
     *
     * @param fontConfiguration 字体配置
     * @param font              字体
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    public abstract TextLineInfo splitText(FontConfiguration fontConfiguration, PDFont font, String text, float lineWidth);

    /**
     * 拆分文本段落（多行）
     *
     * @param fontConfiguration 字体配置
     * @param font              字体
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本列表
     */
    public abstract List<TextLineInfo> splitLines(FontConfiguration fontConfiguration, PDFont font, String text, float lineWidth);

    /**
     * 获取字符宽度
     *
     * @param fontConfiguration 字体配置
     * @param font              字体
     * @param character         字符
     * @return 返回字符宽度
     */
    @SneakyThrows
    public float getCharacterWidth(FontConfiguration fontConfiguration, PDFont font, char character) {
        // 返回真实文本宽度
        return TextUtil.getCharacterWidth(character, this.context, font, fontConfiguration.getSpecialFontNames(), fontConfiguration.getFontSize());
    }

    /**
     * 获取文本宽度
     *
     * @param fontConfiguration 字体配置
     * @param font              字体
     * @param text              文本
     * @return 返回文本宽度
     */
    @SneakyThrows
    public float getTextWidth(FontConfiguration fontConfiguration, PDFont font, String text) {
        // 返回真实文本宽度
        return TextUtil.getTextWidth(text, this.context, font, fontConfiguration.getSpecialFontNames(), fontConfiguration.getFontSize(), fontConfiguration.getCharacterSpacing());
    }

    /**
     * 获取空格宽度
     *
     * @param fontConfiguration 字体配置
     * @param font              字体
     * @return 返回空格宽度
     */
    public float getBlankWidth(FontConfiguration fontConfiguration, PDFont font) {
        return getCharacterWidth(fontConfiguration, font, ' ');
    }
}
