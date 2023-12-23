package org.dromara.pdf.pdfbox.core.ext.extractor;

import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文档提取器
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
public class DocumentExtractor implements Closeable {

    /**
     * pdfbox文档
     */
    private Document document;

    /**
     * 构造方法
     *
     * @param document 文档
     */
    public DocumentExtractor(Document document) {
        this.document = document;
    }

    /**
     * 提取文本
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key=页面索引，value=提取文本）
     */
    public Map<Integer, List<String>> extractText(int... pageIndexes) {
        return this.extractTextForRegex(null, pageIndexes);
    }

    /**
     * 提取文本（正则）
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key=页面索引，value=提取文本）
     */
    public Map<Integer, List<String>> extractTextForRegex(String regex, int... pageIndexes) {
        try (SimpleTextExtractor extractor = new SimpleTextExtractor(this.document)) {
            return extractor.extract(regex, pageIndexes);
        }
    }

    /**
     * 提取文本（区域）
     *
     * @param regionArea  区域
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取文本字典（ key = 区域名称，value = 提取文本））
     */
    public Map<Integer, Map<String, String>> extractTextForRegionArea(
            Map<String, Rectangle> regionArea,
            int... pageIndexes
    ) {
        return this.extractTextForRegionArea(regionArea, " ", pageIndexes);
    }

    /**
     * 提取文本（区域）
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取文本字典（ key = 区域名称，value = 提取文本））
     */
    public Map<Integer, Map<String, String>> extractTextForRegionArea(
            Map<String, Rectangle> regionArea,
            String wordSeparator,
            int... pageIndexes
    ) {
        // 检查参数
        Objects.requireNonNull(regionArea, "the region area can not be null");
        // 检查参数
        Objects.requireNonNull(wordSeparator, "the word separator can not be null");
        // 创建提取器
        try (RegionTextExtractor extractor = new RegionTextExtractor(this.document, regionArea)) {
            // 提取文本
            return extractor.extract(wordSeparator, pageIndexes);
        }
    }

    /**
     * 提取文本（表格，单行单列）
     *
     * @param regionArea  区域
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取文本字典（ key = 区域名称，value = 提取文本））
     */
    public Map<Integer, Map<String, List<List<String>>>> extractTextForTable(
            Map<String, Rectangle> regionArea,
            int... pageIndexes
    ) {
        return this.extractTextForTable(regionArea, " ", pageIndexes);
    }

    /**
     * 提取文本（表格，单行单列）
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取文本字典（ key = 区域名称，value = 提取文本））
     */
    public Map<Integer, Map<String, List<List<String>>>> extractTextForTable(
            Map<String, Rectangle> regionArea,
            String wordSeparator,
            int... pageIndexes
    ) {
        // 检查参数
        Objects.requireNonNull(regionArea, "the region area can not be null");
        // 检查参数
        Objects.requireNonNull(wordSeparator, "the word separator can not be null");
        // 创建提取器
        try (SimpleTableTextExtractor extractor = new SimpleTableTextExtractor(this.document, regionArea)) {
            // 提取文本
            return extractor.extract(wordSeparator, pageIndexes);
        }
    }

    /**
     * 提取文本（表单）
     *
     * @return 返回文本字典（key = 字段名称，value = 提取文本）
     */
    public Map<String, String> extractTextForForm() {
        try (SimpleTextExtractor extractor = new SimpleTextExtractor(this.document)) {
            return extractor.extractForForm();
        }
    }

    /**
     * 提取图像
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取图像）
     */
    public Map<Integer, List<BufferedImage>> extractImage(int... pageIndexes) {
        // 创建提取器
        try (ImageExtractor extractor = new ImageExtractor(this.document)) {
            // 提取图像
            return extractor.extract(pageIndexes);
        }
    }

    /**
     * 提取图像（表单）
     *
     * @return 返回文本字典（key = 字段名称，value = 提取图像）
     */
    public Map<String, BufferedImage> extractImageForForm() {
        // 创建提取器
        try (ImageExtractor extractor = new ImageExtractor(this.document)) {
            // 提取图像
            return extractor.extractForForm();
        }
    }

    /**
     * 提取评论
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key=页面索引，value=提取评论）
     */
    public Map<Integer, List<String>> extractComment(int... pageIndexes) {
        return this.extractCommentForRegex(null, pageIndexes);
    }

    /**
     * 提取评论（正则）
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key=页面索引，value=提取评论）
     */
    public Map<Integer, List<String>> extractCommentForRegex(String regex, int... pageIndexes) {
        // 创建提取器
        try (CommentExtractor extractor = new CommentExtractor(this.document)) {
            // 提取评论
            return extractor.extract(regex, pageIndexes);
        }
    }

    /**
     * 提取书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签字典（key=书签索引，value=提取书签）
     */
    public Map<Integer, PDOutlineItem> extractBookmark(int... bookmarkIndexes) {
        // 创建提取器
        try (BookmarkExtractor extractor = new BookmarkExtractor(this.document)) {
            // 提取书签
            return extractor.extract(bookmarkIndexes);
        }
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }
}
