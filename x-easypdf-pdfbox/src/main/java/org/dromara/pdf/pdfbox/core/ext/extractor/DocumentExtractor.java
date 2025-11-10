package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

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
@Setter
@EqualsAndHashCode(callSuper = true)
public class DocumentExtractor extends AbstractExtractor implements Closeable {

    /**
     * 文本提取器
     */
    protected AbstractTextExtractor textExtractor;
    /**
     * 图像提取器
     */
    protected AbstractImageExtractor imageExtractor;
    /**
     * 表单提取器
     */
    protected AbstractFormExtractor formExtractor;
    /**
     * 评论提取器
     */
    protected AbstractCommentExtractor commentExtractor;
    /**
     * 书签提取器
     */
    protected AbstractBookmarkExtractor bookmarkExtractor;

    /**
     * 构造方法
     *
     * @param document 文档
     */
    public DocumentExtractor(Document document) {
        super(document);
    }

    /**
     * 提取文本
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key=页面索引，value=提取文本</p>
     */
    public Map<Integer, List<String>> extractText(int... pageIndexes) {
        return this.extractTextByRegex(null, pageIndexes);
    }

    /**
     * 正则提取文本
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key=页面索引，value=提取文本</p>
     */
    public Map<Integer, List<String>> extractTextByRegex(String regex, int... pageIndexes) {
        // 初始化提取器
        if (Objects.isNull(this.textExtractor)) {
            this.textExtractor = new TextExtractor(this.document);
        }
        // 提取文本
        return this.textExtractor.extractByRegex(regex, pageIndexes);
    }

    /**
     * 区域提取文本
     *
     * @param regionArea  区域
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>一级，key = 页面索引，value = 提取文本字典</p<p>二级，key = 区域名称，value = 提取文本</p>
     */
    public Map<Integer, Map<String, String>> extractTextByRegionArea(
            Map<String, Rectangle> regionArea,
            int... pageIndexes
    ) {
        return this.extractTextByRegionArea(regionArea, " ", pageIndexes);
    }

    /**
     * 区域提取文本
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>一级，key = 页面索引，value = 提取文本字典</p<p>二级，key = 区域名称，value = 提取文本</p>
     */
    public Map<Integer, Map<String, String>> extractTextByRegionArea(
            Map<String, Rectangle> regionArea,
            String wordSeparator,
            int... pageIndexes
    ) {
        // 检查参数
        Objects.requireNonNull(regionArea, "the region area can not be null");
        // 检查参数
        Objects.requireNonNull(wordSeparator, "the word separator can not be null");
        // 初始化提取器
        if (Objects.isNull(this.textExtractor)) {
            this.textExtractor = new TextExtractor(this.document);
        }
        // 提取文本
        return this.textExtractor.extractByRegionArea(wordSeparator, regionArea, pageIndexes);
    }

    /**
     * 表格提取文本
     * <p>注：单行单列</p>
     *
     * @param regionArea  区域
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>一级，key = 页面索引，value = 提取文本字典</p><p>二级，key = 区域名称，value = 提取文本</p>
     */
    public Map<Integer, Map<String, List<List<String>>>> extractTextForTable(
            Map<String, Rectangle> regionArea,
            int... pageIndexes
    ) {
        return this.extractTextForTable(regionArea, " ", pageIndexes);
    }

    /**
     * 表格提取文本
     * <p>注：单行单列</p>
     *
     * @param regionArea    区域
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典 <p>一级，key = 页面索引，value = 提取文本字典</p><p>二级，key = 区域名称，value = 提取文本</p>
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
        // 初始化提取器
        if (Objects.isNull(this.textExtractor)) {
            this.textExtractor = new TextExtractor(this.document);
        }
        // 提取文本
        return this.textExtractor.extractByTable(wordSeparator, regionArea, pageIndexes);
    }

    /**
     * 提取图像
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取图像</p>
     */
    public Map<Integer, List<BufferedImage>> extractImage(int... pageIndexes) {
        // 初始化提取器
        if (Objects.isNull(this.imageExtractor)) {
            this.imageExtractor = new ImageExtractor(this.document);
        }
        // 提取图像
        return this.imageExtractor.extract(pageIndexes);
    }

    /**
     * 表单提取文本
     *
     * @return 返回文本字典 <p>key = 字段名称，value = 提取文本</p>
     */
    public Map<String, String> extractFormText() {
        // 初始化提取器
        if (Objects.isNull(this.formExtractor)) {
            this.formExtractor = new FormExtractor(this.document);
        }
        // 提取文本
        return this.formExtractor.extractText();
    }

    /**
     * 表单提取图像
     *
     * @return 返回图像字典 <p>key = 字段名称，value = 提取图像</p>
     */
    public Map<String, BufferedImage> extractFormImage() {
        // 初始化提取器
        if (Objects.isNull(this.formExtractor)) {
            this.formExtractor = new FormExtractor(this.document);
        }
        // 提取图像
        return this.formExtractor.extractImage();
    }

    /**
     * 表单提取字段
     *
     * @return 返回字段字典 <p>key = 字段名称，value = 提取字段</p>
     */
    public Map<String, PDField> extractFormField() {
        // 初始化提取器
        if (Objects.isNull(this.formExtractor)) {
            this.formExtractor = new FormExtractor(this.document);
        }
        // 提取字段
        return this.formExtractor.extractField();
    }

    /**
     * 提取评论
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key=页面索引，value=提取评论</p>
     */
    public Map<Integer, List<String>> extractComment(int... pageIndexes) {
        return this.extractCommentByRegex(null, pageIndexes);
    }

    /**
     * 正则提取评论
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key=页面索引，value=提取评论</p>
     */
    public Map<Integer, List<String>> extractCommentByRegex(String regex, int... pageIndexes) {
        // 初始化提取器
        if (Objects.isNull(this.commentExtractor)) {
            this.commentExtractor = new CommentExtractor(document);
        }
        // 提取评论
        return this.commentExtractor.extract(regex, pageIndexes);
    }

    /**
     * 提取书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签字典 <p>key=书签索引，value=提取书签</p>
     */
    public Map<Integer, PDOutlineItem> extractBookmark(int... bookmarkIndexes) {
        // 初始化提取器
        if (Objects.isNull(this.bookmarkExtractor)) {
            this.bookmarkExtractor = new BookmarkExtractor(this.document);
        }
        // 提取书签
        return this.bookmarkExtractor.extract(bookmarkIndexes);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.textExtractor = null;
        this.imageExtractor = null;
        this.formExtractor = null;
        this.commentExtractor = null;
        this.bookmarkExtractor = null;
    }
}
