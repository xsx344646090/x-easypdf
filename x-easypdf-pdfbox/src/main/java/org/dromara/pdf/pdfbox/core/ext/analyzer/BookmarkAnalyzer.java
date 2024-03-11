package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.List;
import java.util.Objects;

/**
 * 书签分析器
 *
 * @author xsx
 * @date 2023/10/19
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
@Getter
public class BookmarkAnalyzer extends AbstractBookmarkAnalyzer {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public BookmarkAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理书签
     *
     * @param bookmarkIndex 书签索引
     */
    @SneakyThrows
    @Override
    public void processOutlineItem(int... bookmarkIndex) {
        // 获取pdfbox目录
        PDDocumentCatalog documentCatalog = this.getDocument().getDocumentCatalog();
        // 获取pdfbox文档概要
        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
        // 如果文档概要不为空， 则解析书签信息
        if (Objects.nonNull(documentOutline)) {
            // 如果书签索引不为空，则解析给定书签索引信息
            if (Objects.nonNull(bookmarkIndex) && bookmarkIndex.length > 0) {
                // 获取书签列表
                List<PDOutlineItem> items = this.toOutlineItemList(documentOutline.children());
                // 遍历书签索引
                for (int index : bookmarkIndex) {
                    // 如果书签索引大于0，则处理书签
                    if (index > 0) {
                        // 处理书签
                        this.processOutlineItem(this.infoSet, documentCatalog, items.get(index));
                    }
                }
            } else {
                // 遍历pdfbox书签列表
                documentOutline.children().forEach(outlineItem -> this.processOutlineItem(this.infoSet, documentCatalog, outlineItem));
            }
        }
    }
}