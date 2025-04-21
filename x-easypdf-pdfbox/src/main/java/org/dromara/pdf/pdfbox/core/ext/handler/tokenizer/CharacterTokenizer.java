package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 字符分词器
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
public class CharacterTokenizer extends AbstractTokenizer {

    /**
     * 拆分文本（单行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    @Override
    public TextLineInfo splitText(FontConfiguration fontConfiguration, String text, float lineWidth) {
        // 如果待输入文本为空，或文本长度为0，或字符宽度大于行宽，则直接返回空
        if (Objects.isNull(text) || text.isEmpty() || this.getTextWidth(fontConfiguration, String.valueOf(text.charAt(0))) > lineWidth) {
            // 返回空字符串
            return null;
        }
        // 定义临时文本
        String tempText;
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义宽度
        float width = 0F;
        // 每行字数（估计）
        int fontCount = Math.max(1, (int) (lineWidth / (fontConfiguration.getFontSize() + fontConfiguration.getCharacterSpacing())));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            width = this.getTextWidth(fontConfiguration, tempText);
            // 如果真实宽度大于行宽度，则减少一个字符
            if (width > lineWidth) {
                // 返回截取字符串
                return new TextLineInfo(text.substring(beginIndex, i - 1), lastWidth);
            } else {
                lastWidth = width;
            }
        }
        // 计算当前文本真实宽度
        if (width == 0F) {
            width = this.getTextWidth(fontConfiguration, text);
        }
        // 返回文本
        return new TextLineInfo(text, width);
    }

    /**
     * 拆分文本段落（多行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本列表
     */
    @Override
    public List<TextLineInfo> splitLines(FontConfiguration fontConfiguration, String text, float lineWidth) {
        // 如果待输入文本为空，或文本长度为0，或字符宽度大于行宽，则直接返回空列表
        if (Objects.isNull(text) || text.isEmpty() || this.getTextWidth(fontConfiguration, String.valueOf(text.charAt(0))) > lineWidth) {
            // 返回空列表
            return new ArrayList<>(0);
        }
        // 定义文本列表
        List<TextLineInfo> lineList = new LinkedList<>();
        // 定义临时文本
        String tempText;
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义宽度
        float width = 0F;
        // 每行字数（估计）
        int fontCount = Math.max(1, (int) (lineWidth / (fontConfiguration.getFontSize() + fontConfiguration.getCharacterSpacing())));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            width = this.getTextWidth(fontConfiguration, tempText);
            // 如果真实宽度大于行宽度，则减少一个字符
            if (width > lineWidth) {
                // 加入文本列表
                lineList.add(new TextLineInfo(text.substring(beginIndex, i - 1), lastWidth));
                // 重置开始索引
                beginIndex = i - 1;
                // 重置文本索引
                i = i + fontCount - 1;
                // 如果文本索引大于或等于文本长度，则为最后一行，加入文本列表
                if (i >= len) {
                    // 加入文本列表
                    lineList.add(new TextLineInfo(text.substring(beginIndex), width));
                }
            } else {
                lastWidth = width;
            }
        }
        // 如果开始索引加每行字数小于文本长度，则为最后一行，加入文本列表
        if (beginIndex + fontCount < text.length() || lineList.isEmpty()) {
            // 截取临时文本
            tempText = text.substring(beginIndex);
            // 计算当前文本真实宽度
            if (width == 0F) {
                width = this.getTextWidth(fontConfiguration, tempText);
            }
            // 加入文本列表
            lineList.add(new TextLineInfo(tempText, width));
        }
        // 返回文本列表
        return lineList;
    }
}
