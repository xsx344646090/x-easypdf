package org.dromara.pdf.pdfbox.core.ext.handler;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.component.TextLineInfo;
import org.dromara.pdf.pdfbox.core.ext.handler.tokenizer.StandardTokenizer;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.List;
import java.util.Objects;

/**
 * 文本助手
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
public class TextHandler extends AbstractTextHandler {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextHandler(Document document) {
        super(document);
        this.tokenizer = new StandardTokenizer();
        this.tokenizer.setContext(this.getContext());
    }

    /**
     * 写入文本
     *
     * @param fontConfiguration 字体配置
     * @param contentStream     内容流
     * @param text              文本
     */
    @SneakyThrows
    @Override
    public void writeText(FontConfiguration fontConfiguration, PDPageContentStream contentStream, TextLineInfo text) {
        // 获取特殊字体名称
        List<String> specialFontNames = fontConfiguration.getSpecialFontNames();
        // 获取字体
        PDFont font = this.getContext().getFont(fontConfiguration.getFontName());
        // 获取字体大小
        Float fontSize = fontConfiguration.getFontSize();
        // 获取文本内容
        String content = text.getText();
        // 遍历字符
        for (int i = 0; i < content.length(); i++) {
            // 获取字符
            char character = content.charAt(i);
            try {
                // 写入文本
                contentStream.showCharacter(character);
            } catch (Exception e) {
                // 处理单字符
                boolean flag = this.processSingle(contentStream, character, specialFontNames, font, fontSize);
                // 未解析成功
                if (flag) {
                    // 还有下一个字符
                    if (i + 1 < content.length()) {
                        // 获取下一个字符
                        char next = content.charAt(i + 1);
                        // 处理双字符
                        flag = this.processDouble(contentStream, character, next, specialFontNames, font, fontSize);
                        // 未解析成功
                        if (flag) {
                            // 重置字体
                            contentStream.setFont(this.getContext().getFont(Constants.DEFAULT_FONT_NAME), fontSize);
                            // 写入未知字符
                            contentStream.showCharacter(Constants.DEFAULT_UNKNOWN_CHARACTER);
                            // 重置字体
                            contentStream.setFont(font, fontSize);
                        } else {
                            // 跳过下一个
                            i++;
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理单字符
     *
     * @param contentStream    内容流
     * @param character        字符
     * @param specialFontNames 特殊字体名称
     * @param font             字体
     * @param fontSize         字体大小
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    protected boolean processSingle(
            PDPageContentStream contentStream,
            char character,
            List<String> specialFontNames,
            PDFont font,
            Float fontSize
    ) {
        // 定义标记
        boolean flag = true;
        // 特殊字体不为空
        if (Objects.nonNull(specialFontNames)) {
            // 遍历特殊字体
            for (String specialFontName : specialFontNames) {
                try {
                    // 设置字体
                    contentStream.setFont(this.getContext().getFont(specialFontName), fontSize);
                    // 写入文本
                    contentStream.showCharacter(character);
                    // 重置标记
                    flag = false;
                    // 跳出循环
                    break;
                } catch (Exception ignore) {
                    // ignore
                }
            }
            // 重置字体
            contentStream.setFont(font, fontSize);
        }
        // 返回标记
        return flag;
    }

    /**
     * 处理双字符
     *
     * @param contentStream    内容流
     * @param character        字符
     * @param next             下一个字符
     * @param specialFontNames 特殊字体名称
     * @param font             字体
     * @param fontSize         字体大小
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    protected boolean processDouble(
            PDPageContentStream contentStream,
            char character,
            char next,
            List<String> specialFontNames,
            PDFont font,
            Float fontSize
    ) {
        // 定义标记
        boolean flag = true;
        // 特殊字体不为空
        if (Objects.nonNull(specialFontNames)) {
            // 获取拼接文本
            String joinStr = String.valueOf(new char[]{character, next});
            // 遍历特殊字体
            for (String specialFontName : specialFontNames) {
                try {
                    // 设置字体
                    contentStream.setFont(this.getContext().getFont(specialFontName), fontSize);
                    // 写入文本
                    contentStream.showText(joinStr);
                    // 重置标记
                    flag = false;
                    // 跳出循环
                    break;
                } catch (Exception ignore) {
                    // ignore
                }
            }
            // 重置字体
            contentStream.setFont(font, fontSize);
        }
        // 返回标记
        return flag;
    }
}
