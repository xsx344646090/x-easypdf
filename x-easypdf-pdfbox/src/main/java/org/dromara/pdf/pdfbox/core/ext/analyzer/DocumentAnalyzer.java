package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.BookmarkInfo;
import org.dromara.pdf.pdfbox.core.info.CommentInfo;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;
import org.dromara.pdf.pdfbox.core.info.TextInfo;

import java.util.List;
import java.util.Objects;

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
public class DocumentAnalyzer extends AbstractAnalyzer {

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
    public List<TextInfo> analyzeText(int... pageIndexes) {
        // 创建文本分析器
        try (TextAnalyzer analyzer = new TextAnalyzer(this.document)) {
            // 如果给定页面索引为空，则处理文档所有页面文本
            if (Objects.isNull(pageIndexes) || pageIndexes.length == 0) {
                // 遍历文档页面索引
                for (int i = 0, count = this.document.getTarget().getNumberOfPages(); i < count; i++) {
                    // 处理文本
                    analyzer.processText(i);
                }
            } else {
                // 遍历页面索引
                for (int index : pageIndexes) {
                    // 如果页面索引大于等于0，则处理文本
                    if (index >= 0) {
                        // 处理文本
                        analyzer.processText(index);
                    }
                }
            }
            // 返回文本信息列表
            return analyzer.getInfoList();
        }
    }

    /**
     * 分析评论
     *
     * @param pageIndexes 页面索引
     * @return 返回评论信息列表
     */
    public List<CommentInfo> analyzeComment(int... pageIndexes) {
        // 创建文本分析器
        try (CommentAnalyzer analyzer = new CommentAnalyzer(this.document)) {
            // 如果给定页面索引为空，则处理文档所有页面评论
            if (Objects.isNull(pageIndexes) || pageIndexes.length == 0) {
                // 处理评论
                analyzer.processComment();
            } else {
                PDPageTree pageTree = this.document.getTarget().getPages();
                // 遍历页面索引
                for (int index : pageIndexes) {
                    // 如果页面索引大于等于0，则处理评论
                    if (index >= 0) {
                        // 处理评论
                        analyzer.processComment(index, pageTree.get(index));
                    }
                }
            }
            // 返回评论信息列表
            return analyzer.getInfoList();
        }
    }

    /**
     * 分析图像
     *
     * @param pageIndexes 页面索引
     * @return 返回图像信息列表
     */
    public List<ImageInfo> analyzeImage(int... pageIndexes) {
        // 创建图像分析器
        try (ImageAnalyzer imageAnalyzer = new ImageAnalyzer(this.document)) {
            // 获取页面树
            PDPageTree pages = this.document.getTarget().getPages();
            // 如果给定页面索引为空，则处理文档所有页面图像
            if (Objects.isNull(pageIndexes) || pageIndexes.length == 0) {
                // 遍历文档页面索引
                for (int i = 0, count = pages.getCount(); i < count; i++) {
                    // 设置页面索引并处理页面图像
                    imageAnalyzer.processImage(i, pages.get(i));
                }
            } else {
                // 遍历页面索引
                for (int index : pageIndexes) {
                    // 如果页面索引大于等于0，则处理图像
                    if (index >= 0) {
                        // 设置页面索引并处理页面图像
                        imageAnalyzer.processImage(index, pages.get(index));
                    }
                }
            }
            // 返回图像信息列表
            return imageAnalyzer.getInfoList();
        }
    }

    /**
     * 分析书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签信息列表
     */
    public List<BookmarkInfo> analyzeBookmark(int... bookmarkIndexes) {
        // 创建书签分析器
        try (BookmarkAnalyzer bookmarkAnalyzer = new BookmarkAnalyzer(this.document)) {
            // 处理书签
            bookmarkAnalyzer.processOutlineItem(bookmarkIndexes);
            // 返回书签信息列表
            return bookmarkAnalyzer.getBookmarkInfoList();
        }
    }
}
