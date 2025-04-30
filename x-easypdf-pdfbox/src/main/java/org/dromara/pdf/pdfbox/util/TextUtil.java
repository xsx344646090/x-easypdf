package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.support.Constants;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
     * 获取文本宽度
     *
     * @param text             文本
     * @param context          上下文
     * @param specialFontNames 获取特殊字体名称
     * @param fontName         字体名称
     * @param fontSize         字体大小
     * @param characterSpacing 字符间距
     * @return 返回文本宽度
     */
    @SneakyThrows
    public static float getTextWidth(String text, Context context, List<String> specialFontNames, String fontName, float fontSize, float characterSpacing) {
        // 返回空宽度
        if (Objects.isNull(text)) {
            return 0F;
        }
        // 参数检查
        Objects.requireNonNull(context, "the context can not be null");
        Objects.requireNonNull(fontName, "the font name can not be null");
        // 获取字体
        PDFont font = context.getFont(fontName);
        // 定义文本宽度
        float width = 0F;
        // 定义临时字符串
        String str;
        // 获取文本长度
        int length = text.length();
        // 遍历文本
        for (int i = 0; i < length; i++) {
            // 获取字符
            char character = text.charAt(i);
            // 重置字符串
            str = String.valueOf(character);
            try {
                // 计算文本宽度
                width = width + font.getCharacterWidth(str);
            } catch (Exception e) {
                // 定义异常标识
                boolean flag = true;
                // 特殊字体名称不为空
                if (Objects.nonNull(specialFontNames)) {
                    // 遍历特殊字体
                    for (String specialFontName : specialFontNames) {
                        try {
                            // 再次计算文本宽度
                            width = width + context.getFont(specialFontName).getCharacterWidth(str);
                            // 重置异常标识
                            flag = false;
                            // 结束
                            break;
                        } catch (Exception ignore) {
                            // ignore
                        }
                    }
                    // 未解析成功
                    if (flag) {
                        // 有下一个字符
                        if (i + 1 < length) {
                            // 获取下一个字符
                            char next = text.charAt(i + 1);
                            // 重置字符串
                            str = String.valueOf(new char[]{character, next});
                            // 遍历特殊字体
                            for (String specialFontName : specialFontNames) {
                                try {
                                    // 再次计算文本宽度
                                    width = width + context.getFont(specialFontName).getStringWidth(str);
                                    // 重置异常标识
                                    flag = false;
                                    // 结束
                                    break;
                                } catch (Exception ignore) {
                                    // ignore
                                }
                            }
                            // 解析成功
                            if (!flag) {
                                // 跳过下一个
                                i++;
                            }
                        }
                    }
                }
                // 未解析成功
                if (flag) {
                    // 使用未知字符代替
                    width = width + context.getFont(Constants.DEFAULT_FONT_NAME).getCharacterWidth(Constants.DEFAULT_UNKNOWN_CHARACTER);
                }
            }
        }

        // 返回真实文本宽度
        return width == 0F ? 0F : fontSize * width / 1000 + (text.length() - 1) * characterSpacing;
    }

    /**
     * 获取文本高度
     *
     * @param fontSize 字体大小
     * @param leading  行间距
     * @param rowCount 行数
     * @return 返回文本真实高度
     */
    public static float getTextHeight(float fontSize, float leading, int rowCount) {
        if (rowCount == 0) {
            return 0F;
        }
        if (rowCount == 1) {
            return fontSize;
        }
        int leadingCount = rowCount - 1;
        return (rowCount * fontSize) + (leadingCount * leading);
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
     * 是否英文字符
     *
     * @param character 字符
     * @return 返回布尔值，是为true，否为false
     */
    public static boolean isEnglishCharacter(Character character) {
        // 检查字符是否在大写字母范围内
        if (character >= 'A' && character <= 'Z') {
            return true;
        }
        // 检查字符是否在小写字母范围内
        if (character >= 'a' && character <= 'z') {
            return true;
        }
        return character == ',' || character == '.' || character == '?' || character == '!' || character == '-' || character == '+' || character == '*' || character == '/';
    }

    /**
     * 空格
     *
     * @param size 数量
     * @return 返回字符串
     */
    public static String spacing(int size) {
        final char space = ' ';
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(space);
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
            // 替换制表符
            if (c == tab) {
                // 添加空格
                temp.append(spacing(size));
            } else {
                // 添加字符
                temp.append(c);
            }
        }
        // 返回文本
        return builder.append(temp).toString();
    }
}
