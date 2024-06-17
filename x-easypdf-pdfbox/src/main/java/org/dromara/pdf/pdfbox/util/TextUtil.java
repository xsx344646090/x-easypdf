package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.Context;

import java.util.*;

/**
 * 文本工具
 *
 * @author xsx
 * @date 2023/3/24
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
public class TextUtil {

    /**
     * 拆分文本（单行）
     *
     * @param context          上下文
     * @param text             待输入文本
     * @param lineWidth        行宽度
     * @param font             字体
     * @param fontSize         字体大小
     * @param characterSpacing 文本间隔
     * @param specialFontNames 特殊字体名称
     * @return 返回文本列表
     */
    @SneakyThrows
    public static String splitText(
            Context context,
            String text,
            float lineWidth,
            PDFont font,
            float fontSize,
            float characterSpacing,
            List<String> specialFontNames
    ) {
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
            lineRealWidth = getTextRealWidth(context, tempText, font, fontSize, characterSpacing, specialFontNames);
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
     * @param context          上下文
     * @param text             待输入文本
     * @param lineWidth        行宽度
     * @param font             字体
     * @param fontSize         字体大小
     * @param characterSpacing 文本间隔
     * @param specialFontNames 特殊字体名称
     * @return 返回文本列表
     */
    @SneakyThrows
    public static List<String> splitLines(
            Context context,
            String text,
            float lineWidth,
            PDFont font,
            float fontSize,
            float characterSpacing,
            List<String> specialFontNames
    ) {
        // 如果待输入文本为空，或文本长度为0，或行宽减字体大小小于0，则直接返回空列表
        if (isBlank(text) || lineWidth - fontSize < 0) {
            // 返回空列表
            return new ArrayList<>(0);
        }
        // 定义文本列表
        List<String> lineList = new ArrayList<>(1024);
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
            lineRealWidth = getTextRealWidth(context, tempText, font, fontSize, characterSpacing, specialFontNames);
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
     * @param specialFontNames 特殊字体名称
     * @return 返回文本真实宽度
     */
    public static float getTextRealWidth(
            Context context,
            String text,
            PDFont font,
            float fontSize,
            float characterSpacing,
            List<String> specialFontNames
    ) {
        if (Objects.isNull(text)) {
            return 0F;
        }
        float width = 0F;
        char[] charArray = text.toCharArray();
        for (char c : charArray) {
            try {
                width = width + font.getStringWidth(String.valueOf(c));
            } catch (Exception e) {
                boolean flag = false;
                if (Objects.nonNull(specialFontNames)) {
                    for (String specialFontName : specialFontNames) {
                        try {
                            width = width + context.getFont(specialFontName).getStringWidth(String.valueOf(c));
                            flag = false;
                            break;
                        } catch (Exception ignore) {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return fontSize * width / 1000 + (text.length() - 1) * characterSpacing;
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
        if (Objects.isNull(replaceMap) || replaceMap.isEmpty()) {
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
        return Objects.nonNull(text) && !text.trim().isEmpty();
    }

    /**
     * 空格
     *
     * @param size 数量
     * @return 返回字符串
     */
    public static String spacing(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * 替换制表符
     *
     * @param text 待替换文本
     * @param size 空格数量
     * @return 返回字符串
     */
    public static String replaceTab(String text, int size) {
        // 定义制表符
        final char tab = '\t';
        // 获取文本字符数组
        char[] charArray = filterAll(text).toCharArray();
        // 定义文本构建器
        StringBuilder builder = new StringBuilder();
        // 定义临时文本构建器
        StringBuilder temp = new StringBuilder();
        // 遍历文本字符
        for (char c : charArray) {
            // 到达指定长度
            if (temp.length() == size) {
                // 添加文本
                builder.append(temp);
                // 重置临时文本构建器
                temp = new StringBuilder();
            }
            // 替换制表符
            if (c == tab) {
                // 添加空格
                temp.append(spacing(size - temp.length()));
            } else {
                // 添加字符
                temp.append(c);
            }
        }
        // 返回文本
        return builder.append(temp).toString();
    }

    /**
     * 写入文本
     *
     * @param context          上下文
     * @param contentStream    内容流
     * @param text             特殊字符
     * @param specialFontNames 特殊字体名称
     * @param font             字体
     * @param fontSize         字体大小
     * @throws Exception 异常
     */
    public static void writeText(
            Context context,
            PDPageContentStream contentStream,
            String text,
            List<String> specialFontNames,
            PDFont font,
            Float fontSize
    ) throws Exception {
        // 定义标记
        boolean flag = false;
        // 定义异常
        Exception exception = null;
        // 获取字符数组
        char[] charArray = text.toCharArray();
        // 遍历文本
        for (char character : charArray) {
            try {
                // 写入文本
                contentStream.showCharacter(character);
            } catch (Exception e) {
                // 重置标记
                flag = true;
                // 重置异常
                exception = e;
                // 特殊字体不为空
                if (Objects.nonNull(specialFontNames)) {
                    // 遍历特殊字体
                    for (String specialFontName : specialFontNames) {
                        try {
                            // 设置字体
                            contentStream.setFont(context.getFont(specialFontName), fontSize);
                            // 写入文本
                            contentStream.showCharacter(character);
                            // 重置字体
                            contentStream.setFont(font, fontSize);
                            // 重置标记
                            flag = false;
                            // 跳出循环
                            break;
                        } catch (Exception ignore) {
                        }
                    }
                }
            }
        }
        // 抛出异常
        if (flag) {
            throw exception;
        }
    }
}
