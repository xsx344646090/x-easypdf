package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 标准分词器
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
public class StandardTokenizer extends AbstractTokenizer {

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
        StringBuilder tempText = new StringBuilder();
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义宽度
        float width = 0F;
        // 获取字符数组
        char[] chars = text.toCharArray();
        // 定义词组
        List<Character> words = new ArrayList<>();
        // 遍历文本
        for (char c : chars) {
            // 英文字符
            if (TextUtil.isEnglishCharacter(c)) {
                // 添加字符
                words.add(c);
                continue;
            } else {
                // 词组非空
                if (!words.isEmpty()) {
                    // 添加字符
                    words.forEach(tempText::append);
                    // 计算当前文本真实宽度
                    width = this.getTextWidth(fontConfiguration, tempText.toString());
                    // 如果真实宽度大于行宽度，则减少一个字符
                    if (width > lineWidth) {
                        // 获取字符串
                        String temp = tempText.substring(0, tempText.length() - words.size());
                        // 空字符串
                        if (temp.isEmpty()) {
                            return null;
                        }
                        // 返回截取字符串
                        return new TextLineInfo(temp, lastWidth);
                    } else {
                        // 重置前一次宽度
                        lastWidth = width;
                        // 重置词组
                        words = new ArrayList<>();
                    }
                }
                // 添加字符
                tempText.append(c);
            }
            // 计算当前文本真实宽度
            width = this.getTextWidth(fontConfiguration, tempText.toString());
            // 如果真实宽度大于行宽度，则减少一个字符
            if (width > lineWidth) {
                // 返回截取字符串
                return new TextLineInfo(tempText.substring(0, tempText.length() - 1), lastWidth);
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
        // 如果待输入文本为空，或文本长度为0，或字符宽度大于行宽，则直接返回空列表
        if (Objects.isNull(text) || text.isEmpty() || this.getTextWidth(fontConfiguration, String.valueOf(text.charAt(0))) > lineWidth) {
            // 返回空列表
            return new ArrayList<>(0);
        }
        // 定义文本列表
        List<TextLineInfo> lineList = new LinkedList<>();
        // 定义临时文本
        StringBuilder tempText = new StringBuilder();
        // 定义前一次宽度
        float lastWidth = 0F;
        // 定义宽度
        float width = 0F;
        // 获取字符数组
        char[] chars = text.toCharArray();
        // 定义词组
        List<Character> words = new ArrayList<>();
        // 遍历文本
        for (char c : chars) {
            // 英文字符
            if (TextUtil.isEnglishCharacter(c)) {
                // 添加字符
                words.add(c);
            } else {
                // 词组非空
                if (!words.isEmpty()) {
                    // 添加字符
                    words.forEach(tempText::append);
                    // 计算当前文本真实宽度
                    width = this.getTextWidth(fontConfiguration, tempText.toString());
                    // 如果真实宽度大于行宽度，则减少一个字符
                    if (width > lineWidth) {
                        // 添加字符串
                        lineList.add(new TextLineInfo(tempText.substring(0, tempText.length() - words.size()), lastWidth));
                        // 重置临时文本
                        tempText = new StringBuilder();
                        // 添加字符
                        words.forEach(tempText::append);
                    } else {
                        // 重置前一次宽度
                        lastWidth = width;
                    }
                    // 重置词组
                    words = new ArrayList<>();
                }
                // 添加字符
                tempText.append(c);
                // 计算当前文本真实宽度
                width = this.getTextWidth(fontConfiguration, tempText.toString());
                // 如果真实宽度大于行宽度，则减少一个字符
                if (width > lineWidth) {
                    // 添加字符串
                    lineList.add(new TextLineInfo(tempText.substring(0, tempText.length() - 1), lastWidth));
                    // 重置临时文本
                    tempText = new StringBuilder();
                    // 添加字符
                    tempText.append(c);
                } else {
                    // 重置前一次宽度
                    lastWidth = width;
                }
            }
        }
        // 词组非空
        if (!words.isEmpty()) {
            // 添加字符
            words.forEach(tempText::append);
        }
        // 最后一行，加入文本列表
        if (tempText.length() > 0) {
            // 获取临时文本
            String temp = tempText.toString();
            // 加入文本列表
            lineList.add(new TextLineInfo(temp, this.getTextWidth(fontConfiguration, temp)));
        }
        // 返回文本列表
        return lineList;
    }
}
