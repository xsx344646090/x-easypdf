package org.dromara.pdf.pdfbox.core.ext;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.info.BookmarkInfo;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;
import org.dromara.pdf.pdfbox.core.info.TextInfo;

import java.io.Closeable;
import java.util.List;
import java.util.Objects;

/**
 * 文档分析器
 *
 * @author xsx
 * @date 2023/6/1
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class DocumentAnalyzer implements Closeable {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(DocumentAnalyzer.class);

    /**
     * pdfbox文档
     */
    private PDDocument document;
    /**
     * 文本信息列表
     */
    @Getter
    private List<TextInfo> textInfoList;
    /**
     * 图像信息列表
     */
    @Getter
    private List<ImageInfo> imageInfoList;
    /**
     * 图像信息列表
     */
    @Getter
    private List<BookmarkInfo> bookmarkInfoList;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    @SneakyThrows
    public DocumentAnalyzer(Document document) {
        this.document = document.getTarget();
    }

    /**
     * 分析文本
     *
     * @param pageIndex 页面索引
     * @return 返回文档分析器
     */
    @SneakyThrows
    public DocumentAnalyzer analyzeText(int... pageIndex) {
        // 创建文本分析器
        try (TextAnalyzer analyzer = new TextAnalyzer(this.document)) {
            // 如果给定页面索引为空，则处理文档所有页面文本
            if (Objects.isNull(pageIndex) || pageIndex.length == 0) {
                // 遍历文档页面索引
                for (int i = 0, count = this.document.getNumberOfPages(); i < count; i++) {
                    // 处理文本
                    analyzer.processText(i);
                }
            }
            // 否则处理给定页面索引文本
            else {
                // 遍历页面索引
                for (int index : pageIndex) {
                    // 如果页面索引大于等于0，则处理文本
                    if (index >= 0) {
                        // 处理文本
                        analyzer.processText(index);
                    }
                }
            }
            // 重置文本信息列表
            this.textInfoList = analyzer.getTextInfoList();
        }
        // 返回文档分析器
        return this;
    }

    /**
     * 分析图像
     *
     * @param pageIndex 页面索引
     * @return 返回文档分析器
     */
    public DocumentAnalyzer analyzeImage(int... pageIndex) {
        // 创建图像分析器
        ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
        // 获取页面树
        PDPageTree pages = this.document.getPages();
        // 如果给定页面索引为空，则处理文档所有页面图像
        if (Objects.isNull(pageIndex) || pageIndex.length == 0) {
            // 遍历文档页面索引
            for (int i = 0, count = pages.getCount(); i < count; i++) {
                // 设置页面索引并处理页面图像
                imageAnalyzer.processImage(i, pages.get(i));
            }
        }
        // 否则处理给定页面索引图像
        else {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 如果页面索引大于等于0，则处理图像
                if (index >= 0) {
                    // 设置页面索引并处理页面图像
                    imageAnalyzer.processImage(index, pages.get(index));
                }
            }
        }
        // 重置图像信息列表
        this.imageInfoList = imageAnalyzer.getImageInfoList();
        // 返回文档分析器
        return this;
    }

    /**
     * 分析书签
     *
     * @param bookmarkIndex 书签索引
     * @return 返回文档分析器
     */
    public DocumentAnalyzer analyzeBookmark(int... bookmarkIndex) {
        // 创建书签分析器
        try (BookmarkAnalyzer bookmarkAnalyzer = new BookmarkAnalyzer(this.document)) {
            // 重置书签信息列表
            this.bookmarkInfoList = bookmarkAnalyzer.processOutlineItem(bookmarkIndex);
        }
        // 返回文档分析器
        return this;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }
}
