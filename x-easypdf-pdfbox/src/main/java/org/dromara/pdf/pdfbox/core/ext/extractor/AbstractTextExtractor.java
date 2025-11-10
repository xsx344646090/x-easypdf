package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.shade.org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.shade.org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 抽象文本提取器
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
public abstract class AbstractTextExtractor extends AbstractExtractor {

    /**
     * 表格正则（单行单列）
     */
    protected static final Pattern TABLE_PATTERN = Pattern.compile("(\\S[^\\n\\r]+)", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractTextExtractor(Document document) {
        super(document);
    }

    /**
     * 正则提取文本
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    public abstract Map<Integer, List<String>> extractByRegex(String regex, int... pageIndexes);

    /**
     * 区域提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param regionArea    区域
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    public abstract Map<Integer, Map<String, String>> extractByRegionArea(String wordSeparator, Map<String, Rectangle> regionArea, int... pageIndexes);

    /**
     * 表格提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param regionArea    区域
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    public abstract Map<Integer, Map<String, List<List<String>>>> extractByTable(String wordSeparator, Map<String, Rectangle> regionArea, int... pageIndexes);

    /**
     * 正则处理文本
     *
     * @param regex    正则表达式
     * @param stripper 文本剥离器
     * @return 返回文本列表
     */
    @SneakyThrows
    protected List<String> processTextByRegex(String regex, PDFTextStripper stripper) {
        // 定义文本列表
        List<String> list = new ArrayList<>(32);
        // 获取文本
        String text = stripper.getText(this.getDocument());
        // 如果正则表达式有内容，则进行匹配
        if (Objects.nonNull(regex) && !regex.trim().isEmpty()) {
            // 获取正则匹配器
            Matcher matcher = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE).matcher(text);
            // 循环匹配
            while (matcher.find()) {
                // 添加文本
                list.add(matcher.group());
            }
        } else {
            // 添加文本
            list.add(text);
        }
        // 返回文本列表
        return list;
    }

    /**
     * 区域处理文本
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param page          页面
     * @return 返回文本字典 <p>key = 区域名称，value = 提取文本</p>
     */
    @SneakyThrows
    protected Map<String, String> processTextByRegionArea(Map<String, Rectangle> regionArea, String wordSeparator, PDPage page) {
        // 创建文本剥离器
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        // 设置单词分隔符
        stripper.setWordSeparator(wordSeparator);
        // 设置区域
        regionArea.forEach(stripper::addRegion);
        // 定义文本字典
        Map<String, String> data;
        // 如果区域为空，则初始化文本字典为空字典
        if (regionArea.isEmpty()) {
            // 初始化文本字典为空字典
            data = new HashMap<>(0);
            // 如果区域不为空，则初始化文本字典并填充
        } else {
            // 获取区域名称列表
            Set<String> keySet = regionArea.keySet();
            // 初始化文本字典
            data = new HashMap<>(keySet.size());
            // 处理区域
            stripper.extractRegions(page);
            // 遍历区域名称列表
            for (String region : keySet) {
                // 设置文本字典
                data.put(region, stripper.getTextForRegion(region));
            }
        }
        return data;
    }

    /**
     * 表格处理文本
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param page          页面
     * @return 返回文本字典 <p>key = 区域名称，value = 提取文本</p>
     */
    protected Map<String, List<List<String>>> processTextByTable(Map<String, Rectangle> regionArea, String wordSeparator, PDPage page) {
        // 获取文本字典
        Map<String, String> sourceMap = this.processTextByRegionArea(regionArea, wordSeparator, page);
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
                rows.add(Arrays.stream(rowText.split(wordSeparator)).collect(Collectors.toList()));
            }
            // 添加文本
            dataMap.put(key, rows);
        });
        // 返回文本字典
        return dataMap;
    }

    /**
     * 提取文本
     *
     * @param function      功能函数
     * @param wordSeparator 单词分隔符
     * @param regionArea    区域
     * @param pageIndexes   页面索引
     * @param <R>           返回类型
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    protected <R> Map<Integer, R> extractText(Function<R> function, String wordSeparator, Map<String, Rectangle> regionArea, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, R> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                try {
                    // 添加数据
                    data.put(index, function.apply(regionArea, wordSeparator, pageTree.get(index)));
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        } else {
            // 定义索引
            int index = 0;
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加数据
                data.put(index, function.apply(regionArea, wordSeparator, page));
                // 索引自增
                index++;
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 功能接口
     */
    @FunctionalInterface
    protected interface Function<R> {
        /**
         * 应用
         *
         * @param regionArea    区域
         * @param wordSeparator 单词分隔符
         * @param page          页面
         * @return 返回文本字典 <p>key = 区域关键字，value = 提取文本</p>
         */
        R apply(Map<String, Rectangle> regionArea, String wordSeparator, PDPage page);
    }
}
