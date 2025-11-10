package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.text.PDFTextStripper;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文本提取器
 *
 * @author xsx
 * @date 2023/10/20
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
@EqualsAndHashCode(callSuper = true)
public class TextExtractor extends AbstractTextExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextExtractor(Document document) {
        // 初始化
        super(document);
    }

    /**
     * 正则提取文本
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    @Override
    public Map<Integer, List<String>> extractByRegex(String regex, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, List<String>> data = new HashMap<>(32);
        // 创建文本剥离器
        PDFTextStripper stripper = new PDFTextStripper();
        // 根据页面索引提取文本
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 设置起始页面
                stripper.setStartPage(index + 1);
                // 设置结束页面
                stripper.setEndPage(index + 1);
                // 提取文本
                data.put(index, this.processTextByRegex(regex, stripper));
            }
        } else {
            // 循环提取页面文本
            for (int index = 0; index < this.getDocument().getNumberOfPages(); index++) {
                // 设置起始页面
                stripper.setStartPage(index + 1);
                // 设置结束页面
                stripper.setEndPage(index + 1);
                // 提取全部文本
                data.put(index, this.processTextByRegex(regex, stripper));
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param regionArea    区域
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    @Override
    public Map<Integer, Map<String, String>> extractByRegionArea(String wordSeparator, Map<String, Rectangle> regionArea, int... pageIndexes) {
        return this.extractText(this::processTextByRegionArea, wordSeparator, regionArea, pageIndexes);
    }

    /**
     * 表格提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param regionArea    区域
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    @Override
    public Map<Integer, Map<String, List<List<String>>>> extractByTable(String wordSeparator, Map<String, Rectangle> regionArea, int... pageIndexes) {
        return this.extractText(this::processTextByTable, wordSeparator, regionArea, pageIndexes);
    }
}
