package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.awt.*;
import java.io.Closeable;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单表格文本提取器
 *
 * @author xsx
 * @date 2023/10/23
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
public class SimpleTableTextExtractor implements Closeable {

    /**
     * 表格正则（单行单列）
     */
    private static final Pattern TABLE_PATTERN = Pattern.compile("(\\S[^\\n\\r]+)");
    /**
     * pdfbox文档
     */
    private PDDocument document;
    /**
     * 区域文本提取器
     */
    private final RegionTextExtractor extractor;

    /**
     * 有参构造
     *
     * @param document   文档
     * @param regionArea 区域
     */
    public SimpleTableTextExtractor(Document document, Map<String, Rectangle> regionArea) {
        this.document = document.getTarget();
        this.extractor = new RegionTextExtractor(document, regionArea);
    }

    /**
     * 提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典(key = 页面索引 ， value = 提取文本)
     */
    public Map<Integer, Map<String, List<List<String>>>> extract(String wordSeparator, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, Map<String, List<List<String>>>> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.document.getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            Arrays.stream(pageIndexes).sorted().forEach(
                    // 添加数据
                    index -> data.put(index, this.extract(wordSeparator, pageTree.get(index)))
            );
        } else {
            // 定义索引
            int index = 0;
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加数据
                data.put(index, this.extract(wordSeparator, page));
                // 索引自增
                index++;
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
        this.extractor.close();
    }

    /**
     * 提取文本
     *
     * @param page pdfbox页面
     * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
     */
    protected Map<String, List<List<String>>> extract(PDPage page) {
        return this.extract(" ", page);
    }

    /**
     * 提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param page          pdfbox页面
     * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
     */
    @SneakyThrows
    protected Map<String, List<List<String>>> extract(String wordSeparator, PDPage page) {
        // 获取文本字典
        Map<String, String> sourceMap = this.extractor.extract(wordSeparator, page);
        // 没有内容
        if (sourceMap.isEmpty()) {
            return new HashMap<>(0);
        }
        // 定义文本字典
        Map<String, List<List<String>>> dataMap = new HashMap<>(sourceMap.size());
        // 遍历文本
        sourceMap.forEach((key, value) -> {
            // 定义文本列表（行）
            List<List<String>> rows = new ArrayList<>(16);
            // 定义文本列表（列）
            List<String> columns = new ArrayList<>(16);
            // 获取正则匹配器
            Matcher matcher = TABLE_PATTERN.matcher(value);
            // 循环匹配
            while (matcher.find()) {
                // 添加文本列表
                columns.add(matcher.group());
            }
            // 遍历源文本列表
            for (String rowText : columns) {
                // 添加到待接收文本列表
                rows.add(Arrays.asList(rowText.split(wordSeparator)));
            }
            // 添加文本
            dataMap.put(key, rows);
        });
        // 返回文本字典
        return dataMap;
    }
}
