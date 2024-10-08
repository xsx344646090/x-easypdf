package org.dromara.pdf.pdfbox.core.ext.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.ext.AbstractExpander;
import org.dromara.pdf.pdfbox.support.CharacterList;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 抽象文本助手
 *
 * @author xsx
 * @date 2024/9/25
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
    public abstract void writeText(FontConfiguration fontConfiguration, PDPageContentStream contentStream, String text);

    /**
     * 拆分文本（单行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    public String splitText(FontConfiguration fontConfiguration, String text, float lineWidth) {
        // 如果待输入文本为空，或文本长度为0，或行宽减字体大小小于0，则直接返回空字符串
        if (TextUtil.isBlank(text) || lineWidth - fontConfiguration.getFontSize() < 0) {
            // 返回空字符串
            return null;
        }
        // 定义临时文本
        String tempText;
        // 定义当前行真实宽度
        float lineRealWidth;
        // 每行字数（估计）
        int fontCount = Math.max(1, (int) (lineWidth / (fontConfiguration.getFontSize() + fontConfiguration.getCharacterSpacing())));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            lineRealWidth = this.getTextWidth(fontConfiguration, tempText);
            // 如果真实宽度大于行宽度，则减少一个字符
            if (lineRealWidth > lineWidth) {
                // 返回截取字符串
                return text.substring(beginIndex, i - 1);
            }
        }
        // 返回文本
        return text;
    }

    /**
     * 拆分文本段落（换行）
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @param lineWidth         行宽
     * @return 返回文本
     */
    public List<String> splitLines(FontConfiguration fontConfiguration, String text, float lineWidth) {
        // 如果待输入文本为空，或文本长度为0，或行宽减字体大小小于0，则直接返回空列表
        if (TextUtil.isBlank(text) || lineWidth - fontConfiguration.getFontSize() < 0) {
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
        int fontCount = Math.max(1, (int) (lineWidth / (fontConfiguration.getFontSize() + fontConfiguration.getCharacterSpacing())));
        // 定义开始索引
        int beginIndex = 0;
        // 遍历文本
        for (int i = fontCount, len = text.length(); i <= len; i++) {
            // 截取临时文本
            tempText = text.substring(beginIndex, i);
            // 计算当前文本真实宽度
            lineRealWidth = this.getTextWidth(fontConfiguration, tempText);
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
        // 返回文本列表
        return lineList;
    }

    /**
     * 获取文本宽度
     *
     * @param fontConfiguration 字体配置
     * @param text              文本
     * @return 返回文本宽度
     */
    @SneakyThrows
    public float getTextWidth(FontConfiguration fontConfiguration, String text) {
        // 返回空宽度
        if (Objects.isNull(text)) {
            return 0F;
        }
        // 获取特殊字体名称
        List<String> specialFontNames = fontConfiguration.getSpecialFontNames();
        // 获取字体
        PDFont font = this.getContext().getFont(fontConfiguration.getFontName());
        // 获取字体大小
        Float fontSize = fontConfiguration.getFontSize();
        // 获取字符间距
        Float characterSpacing = fontConfiguration.getCharacterSpacing();
        // 定义文本宽度
        float width = 0F;
        // 定义临时字符串
        String str;
        // 获取字符链表
        CharacterList list = CharacterList.create(text);
        // 遍历文本
        while (list.hasNext()) {
            // 获取下一个字符链表
            list = list.getNext();
            // 获取字符
            Character character = list.getData();
            try {
                // 计算文本宽度
                width = width + font.getCharacterWidth(character);
            } catch (Exception e) {
                // 定义异常标识
                boolean flag = true;
                // 特殊字体名称不为空
                if (Objects.nonNull(specialFontNames)) {
                    // 遍历特殊字体
                    for (String specialFontName : specialFontNames) {
                        try {
                            // 再次计算文本宽度
                            width = width + this.getContext().getFont(specialFontName).getCharacterWidth(character);
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
                        if (list.hasNext()) {
                            // 获取下一个字符
                            Character next = list.getNext().getData();
                            // 重置字符串
                            str = String.valueOf(new char[]{character, next});
                            // 遍历特殊字体
                            for (String specialFontName : specialFontNames) {
                                try {
                                    // 再次计算文本宽度
                                    width = width + this.getContext().getFont(specialFontName).getStringWidth(str);
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
                                list.skipNext();
                            }
                        }
                    }
                }
                // 未解析成功
                if (flag) {
                    // 使用未知字符代替
                    width = width + this.getContext().getFont(Constants.DEFAULT_FONT_NAME).getCharacterWidth(Constants.DEFAULT_UNKNOWN_CHARACTER);
                }
            }
        }
        // 返回真实文本宽度
        return width == 0F ? 0F : fontSize * width / 1000 + (text.length() - 1) * characterSpacing;
    }

    /**
     * 获取文本高度
     *
     * @param fontConfiguration 字体配置
     * @param rowCount          行数
     * @return 返回文本真实高度
     */
    public float getTextHeight(FontConfiguration fontConfiguration, int rowCount) {
        if (rowCount == 0) {
            return 0F;
        }
        if (rowCount == 1) {
            return fontConfiguration.getFontSize();
        }
        int leadingCount = rowCount - 1;
        return (rowCount * fontConfiguration.getFontSize()) + (leadingCount * fontConfiguration.getLeading());
    }

    /**
     * 获取上下文
     *
     * @return 返回上下文
     */
    protected Context getContext() {
        return this.document.getContext();
    }
}
