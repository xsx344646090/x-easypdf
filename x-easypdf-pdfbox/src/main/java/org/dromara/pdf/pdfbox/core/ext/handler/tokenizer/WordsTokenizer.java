package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;

import java.util.*;

/**
 * 单词分词器
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
public class WordsTokenizer extends AbstractTokenizer {

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
        // 如果待输入文本为空，或文本长度为0
        if (Objects.isNull(text) || text.isEmpty()) {
            // 返回空字符串
            return null;
        }
        // 拆分文本
        String[] words = text.split(" ");
        // 字符宽度大于行宽
        if (this.getTextWidth(fontConfiguration, words[0]) > lineWidth) {
            // 返回空字符串
            return null;
        }
        // 定义临时文本
        String temp;
        // 定义宽度
        float width = 0F;
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义连接器
        StringJoiner joiner = new StringJoiner(" ");
        // 遍历词组
        for (String word : words) {
            // 添加单词
            joiner.add(word);
            // 获取当前文本
            temp = joiner.toString();
            // 计算当前文本真实宽度
            width = this.getTextWidth(fontConfiguration, temp);
            // 真实宽度大于行宽度
            if (width > lineWidth) {
                // 返回截取字符串
                return new TextLineInfo(temp.substring(0, temp.length() - word.length() - 1), lastWidth);
            } else {
                // 重置前一次宽度
                lastWidth = width;
            }
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
        // 如果待输入文本为空，或文本长度为0
        if (Objects.isNull(text) || text.isEmpty()) {
            // 返回空列表
            return new ArrayList<>();
        }
        // 拆分文本
        String[] words = text.trim().split(" ");
        // 字符宽度大于行宽
        if (this.getTextWidth(fontConfiguration, words[0]) > lineWidth) {
            // 返回空列表
            return new ArrayList<>();
        }
        // 定义文本列表
        List<TextLineInfo> lineList = new LinkedList<>();
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义宽度
        float width = 0F;
        // 定义起始索引
        int beginIndex = 0;
        // 定义连接器
        StringJoiner joiner = new StringJoiner(" ");
        // 遍历词组
        while (beginIndex < words.length) {
            // 添加单词
            joiner.add(words[beginIndex]);
            // 计算当前文本真实宽度
            width = this.getTextWidth(fontConfiguration, joiner.toString());
            // 如果真实宽度大于行宽度，则减少一个字符
            if (width > lineWidth) {
                // 获取当前文本
                String temp = joiner.toString();
                // 加入文本列表
                lineList.add(new TextLineInfo(temp.substring(0, temp.length() - words[beginIndex].length() - 1), lastWidth));
                // 重置开始索引
                beginIndex = beginIndex - 1;
                // 重置连接器
                joiner = new StringJoiner(" ");
            } else {
                lastWidth = width;
            }
            beginIndex++;
        }
        // 如果连接器文本长度大于1，则为最后一行，加入文本列表
        if (joiner.length() > 1) {
            // 加入文本列表
            lineList.add(new TextLineInfo(joiner.toString(), width));
        }
        // 返回文本列表
        return lineList;
    }
}
