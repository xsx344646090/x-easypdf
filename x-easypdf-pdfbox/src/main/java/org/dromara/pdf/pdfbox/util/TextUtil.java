package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文本工具
 *
 * @author xsx
 * @date 2020/3/24
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class TextUtil {

    /**
     * 拆分文本（单行）
     *
     * @param text             待输入文本
     * @param lineWidth        行宽度
     * @param font             字体
     * @param fontSize         字体大小
     * @param characterSpacing 文本间隔
     * @return 返回文本列表
     */
    @SneakyThrows
    public static String splitText(String text, float lineWidth, PDFont font, float fontSize, float characterSpacing) {
        // 如果待输入文本为空，或文本长度为0，或行宽减字体大小小于0，则直接返回空字符串
        if (isBlank(text) || lineWidth - fontSize < 0) {
            // 返回空字符串
            return null;
        }
        // 定义临时文本
        String tempText;
        // 定义当前行真实宽度
        float lineRealWidth;
        // 每行字数（估计）
        int fontCount = Math.max(1, (int) (lineWidth / (fontSize + characterSpacing)));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            lineRealWidth = getTextRealWidth(tempText, font, fontSize, characterSpacing);
            // 如果真实宽度大于行宽度，则减少一个字符
            if (lineRealWidth > lineWidth) {
                // 返回截取字符串
                return text.substring(beginIndex, i - 1);
            }
        }
        return text;
    }

    /**
     * 拆分文本段落（换行）
     *
     * @param text             待输入文本
     * @param lineWidth        行宽度
     * @param font             字体
     * @param fontSize         字体大小
     * @param characterSpacing 文本间隔
     * @return 返回文本列表
     */
    @SneakyThrows
    public static List<String> splitLines(String text, float lineWidth, PDFont font, float fontSize, float characterSpacing) {
        // 如果待输入文本为空，或文本长度为0，或行宽减字体大小小于0，则直接返回空列表
        if (isBlank(text) || lineWidth - fontSize < 0) {
            // 返回空列表
            return new ArrayList<>(0);
        }
        // 定义文本列表
        List<String> lineList = new ArrayList<>(200);
        // 定义临时文本
        String tempText;
        // 定义当前行真实宽度
        float lineRealWidth;
        // 每行字数（估计）
        int fontCount = Math.max(1, (int) (lineWidth / (fontSize + characterSpacing)));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            lineRealWidth = getTextRealWidth(tempText, font, fontSize, characterSpacing);
            // 如果真实宽度大于行宽度，则减少一个字符
            if (lineRealWidth > lineWidth) {
                // 加入文本列表
                lineList.add(text.substring(beginIndex, i - 1));
                // 重置开始索引
                beginIndex = i - 1;
                // 重置文本索引
                i = i + fontCount - 1;
                // 如果文本索引大于或等于文本长度，则为最后一行，加入文本列表
                if (i >= len) {
                    // 加入文本列表
                    lineList.add(text.substring(beginIndex));
                }
            }
        }
        // 如果开始索引加每行字数小于文本长度，则为最后一行，加入文本列表
        if (beginIndex + fontCount < text.length() || lineList.isEmpty()) {
            // 加入文本列表
            lineList.add(text.substring(beginIndex));
        }
        return lineList;
    }

    /**
     * 获取文本真实宽度
     *
     * @param text             文本
     * @param font             pdfbox字体
     * @param fontSize         字体大小
     * @param characterSpacing 字符间隔
     * @return 返回文本真实宽度
     */
    @SneakyThrows
    public static float getTextRealWidth(String text, PDFont font, float fontSize, float characterSpacing) {
        return fontSize * font.getStringWidth(text) / 1000 + (text.length() - 1) * characterSpacing;
    }

    /**
     * 转义正则字符
     *
     * @param text 待转义文本
     * @return 返回转义文本
     */
    public static String escapeForRegex(String text) {
        // 如果待转义文本为空，则返回空字符串
        if (isBlank(text)) {
            // 返回空字符串
            return "";
        }
        // 定义待转义字符数组
        final char[] escapeChars = {'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'};
        // 定义字符串构建器
        StringBuilder builder = new StringBuilder();
        // 获取转义文本字符数组
        char[] charArray = text.toCharArray();
        // 遍历转义文本字符数组
        for (char c : charArray) {
            // 遍历待转义字符数组
            for (char escapeChar : escapeChars) {
                // 如果字符为待转义字符，则添加转义字符
                if (escapeChar == c) {
                    // 添加转义字符
                    builder.append('\\');
                    // 结束遍历
                    break;
                }
            }
            // 添加字符
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 过滤特殊字符
     *
     * @param text 待过滤文本
     * @return 返回过滤后文本
     */
    public static String filterAll(String text) {
        // 替换特殊字符为空串
        return text.replaceAll("[\r\b\f]", "");
    }

    /**
     * 替换全部
     *
     * @param text       待替换文本
     * @param replaceMap 待替换字典
     * @return 返回替换后文本
     */
    public static String replaceAll(String text, Map<String, String> replaceMap) {
        // 如果文本为空，则返回空串
        if (isBlank(text)) {
            // 返回空串
            return "";
        }
        // 如果待替换字典为空，则返回待替换文本
        if (replaceMap == null || replaceMap.isEmpty()) {
            // 返回待替换文本
            return text;
        }
        // 定义临时文本
        String temp = text;
        // 获取待替换字典集合
        Set<Map.Entry<String, String>> entrySet = replaceMap.entrySet();
        // 遍历待替换字典集合
        for (Map.Entry<String, String> entry : entrySet) {
            // 替换文本
            temp = temp.replaceAll(entry.getKey(), entry.getValue());
        }
        // 返回替换后文本
        return temp;
    }

    /**
     * 空白
     *
     * @param text 文本
     * @return 返回布尔值，是为true，否为false
     */
    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    /**
     * 非空白
     *
     * @param text 文本
     * @return 返回布尔值，是为true，否为false
     */
    public static boolean isNotBlank(String text) {
        return text != null && text.trim().length() > 0;
    }

    /**
     * 拼接字符串
     *
     * @param delimiter 分隔符
     * @param texts     字符串列表
     * @return 返回拼接后的字符串
     */
    public static String join(CharSequence delimiter, String... texts) {
        // 如果字符串列表为空，则返回空串
        if (texts == null || texts.length == 0) {
            // 返回空串
            return "";
        }
        // 定义字符串构建器
        StringBuilder builder = new StringBuilder();
        // 遍历字符串列表
        for (String text : texts) {
            // 添加字符串
            builder.append(text);
            // 添加分隔符
            builder.append(delimiter);
        }
        // 如果分隔符不为空，则返回少一位
        if (delimiter != null && delimiter.length() > 0) {
            // 返回拼接后的字符串
            return builder.substring(0, builder.length() - 1);
        }
        // 返回拼接后的完整字符串
        return builder.toString();
    }

    /**
     * 空格
     *
     * @param size 数量
     * @return 返回字符串
     */
    public static String space(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
