package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.util.List;

/**
 * 抽象分词器
 *
 * @author xsx
 * @date 2025/4/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
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
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    public abstract TextLineInfo splitText(FontConfiguration fontConfiguration, String text, float lineWidth);

    /**
     * 拆分文本段落（多行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本列表
     */
    public abstract List<TextLineInfo> splitLines(FontConfiguration fontConfiguration, String text, float lineWidth);

    /**
     * 获取文本宽度
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @return 返回文本宽度
     */
    @SneakyThrows
    public float getTextWidth(FontConfiguration fontConfiguration, String text) {
        // 返回真实文本宽度
        return TextUtil.getTextWidth(text, this.context, fontConfiguration.getSpecialFontNames(), fontConfiguration.getFontName(), fontConfiguration.getFontSize(), fontConfiguration.getCharacterSpacing());
    }
}
