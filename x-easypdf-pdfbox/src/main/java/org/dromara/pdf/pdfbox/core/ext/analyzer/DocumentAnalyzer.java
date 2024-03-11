package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.*;

import java.io.Closeable;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 文档分析器
 *
 * @author xsx
 * @date 2023/6/1
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
@Getter
public class DocumentAnalyzer extends AbstractAnalyzer implements Closeable {

    /**
     * 文本分析器
     */
    protected AbstractTextAnalyzer textAnalyzer;
    /**
     * 评论分析器
     */
    protected AbstractCommentAnalyzer commentAnalyzer;
    /**
     * 图像分析器
     */
    protected AbstractImageAnalyzer imageAnalyzer;
    /**
     * 表单分析器
     */
    protected AbstractFormAnalyzer formAnalyzer;
    /**
     * 书签分析器
     */
    protected AbstractBookmarkAnalyzer bookmarkAnalyzer;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentAnalyzer(Document document) {
        super(document);
    }

    /**
     * 分析文本
     *
     * @param pageIndexes 页面索引
     * @return 返回文本信息列表
     */
    @SneakyThrows
    public Set<TextInfo> analyzeText(int... pageIndexes) {
        // 初始化分析器
        if (Objects.isNull(this.textAnalyzer)) {
            this.textAnalyzer = new TextAnalyzer(this.document);
        }
        // 处理文本
        this.process((index, page) -> this.textAnalyzer.processText(index), pageIndexes);
        // 返回文本信息列表
        return textAnalyzer.getInfoSet();
    }

    /**
     * 分析评论
     *
     * @param pageIndexes 页面索引
     * @return 返回评论信息列表
     */
    public Set<CommentInfo> analyzeComment(int... pageIndexes) {
        // 初始化分析器
        if (Objects.isNull(this.commentAnalyzer)) {
            this.commentAnalyzer = new CommentAnalyzer(this.document);
        }
        // 处理评论
        this.process(this.commentAnalyzer::processComment, pageIndexes);
        // 返回评论信息列表
        return this.commentAnalyzer.getInfoSet();
    }

    /**
     * 分析图像
     *
     * @param pageIndexes 页面索引
     * @return 返回图像信息列表
     */
    public Set<ImageInfo> analyzeImage(int... pageIndexes) {
        // 初始化分析器
        if (Objects.isNull(this.imageAnalyzer)) {
            this.imageAnalyzer = new ImageAnalyzer(this.document);
        }
        // 处理图像
        this.process(this.imageAnalyzer::processImage, pageIndexes);
        // 返回图像信息列表
        return this.imageAnalyzer.getInfoSet();
    }

    /**
     * 分析表单
     *
     * @return 返回表单信息列表
     */
    public Set<FormFieldInfo> analyzeForm(int... pageIndexes) {
        // 初始化分析器
        if (Objects.isNull(this.formAnalyzer)) {
            this.formAnalyzer = new FormAnalyzer(this.document);
        }
        // 处理表单
        this.process(this.formAnalyzer::processForm, pageIndexes);
        // 处理无页面字段
        if (Objects.isNull(pageIndexes) || pageIndexes.length == 0) {
            this.formAnalyzer.processForm(-1, null);
        }
        this.process(this.formAnalyzer::processForm, pageIndexes);
        // 返回表单信息列表
        return this.formAnalyzer.getInfoSet();
    }

    /**
     * 分析书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签信息列表
     */
    public Set<BookmarkInfo> analyzeBookmark(int... bookmarkIndexes) {
        // 初始化分析器
        if (Objects.isNull(this.bookmarkAnalyzer)) {
            this.bookmarkAnalyzer = new BookmarkAnalyzer(this.document);
        }
        // 处理书签
        this.bookmarkAnalyzer.processOutlineItem(bookmarkIndexes);
        // 返回书签信息列表
        return this.bookmarkAnalyzer.getInfoSet();
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.textAnalyzer = null;
        this.commentAnalyzer = null;
        this.imageAnalyzer = null;
        this.formAnalyzer = null;
        this.bookmarkAnalyzer = null;
    }

    /**
     * 处理
     *
     * @param consumer    消费者
     * @param pageIndexes 页面索引
     */
    protected void process(BiConsumer<Integer, PDPage> consumer, int[] pageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 如果给定页面索引为空，则处理文档所有页面表单
        if (Objects.isNull(pageIndexes) || pageIndexes.length == 0) {
            // 遍历文档页面索引
            for (int i = 0, count = pageTree.getCount(); i < count; i++) {
                // 处理
                consumer.accept(i, pageTree.get(i));
            }
        } else {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 如果页面索引大于等于0，则处理表单
                if (index >= 0) {
                    // 处理
                    consumer.accept(index, pageTree.get(index));
                }
            }
        }
    }
}
