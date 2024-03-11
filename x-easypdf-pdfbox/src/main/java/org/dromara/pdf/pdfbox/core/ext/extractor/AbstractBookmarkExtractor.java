package org.dromara.pdf.pdfbox.core.ext.extractor;

import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.*;

/**
 * 抽象书签提取器
 *
 * @author xsx
 * @date 2023/10/25
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
public abstract class AbstractBookmarkExtractor extends AbstractExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractBookmarkExtractor(Document document) {
        super(document);
    }

    /**
     * 提取书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签字典 <p>key = 书签索引，value = 提取书签</p>
     */
    public abstract Map<Integer, PDOutlineItem> extract(int... bookmarkIndexes);

    /**
     * 提取书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签字典（key = 书签索引，value = 提取书签）
     */
    protected Map<Integer, PDOutlineItem> processBookmark(int... bookmarkIndexes) {
        // 定义书签字典
        Map<Integer, PDOutlineItem> data = new HashMap<>(32);
        // 获取pdfbox目录
        PDDocumentCatalog documentCatalog = this.getDocument().getDocumentCatalog();
        // 获取pdfbox文档概要
        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
        // 文档概要非空
        if (Objects.nonNull(documentOutline)) {
            // 定义索引
            int index = 0;
            // 获取书签列表
            Iterable<PDOutlineItem> items = documentOutline.children();
            // 书签索引非空
            if (Objects.nonNull(bookmarkIndexes) && bookmarkIndexes.length > 0) {
                // 获取书签列表
                PrimitiveIterator.OfInt iterator = Arrays.stream(bookmarkIndexes).sorted().iterator();
                // 定义书签索引
                int bookmarkIndex = 0;
                // 遍历获取书签列表
                for (PDOutlineItem outlineItem : items) {
                    // 跳过小于书签索引
                    if (index < bookmarkIndex) {
                        // 索引自增
                        index++;
                        // 跳过
                        continue;
                    }
                    // 没有书签
                    if (!iterator.hasNext()) {
                        // 结束
                        break;
                    }
                    // 重置书签索引
                    bookmarkIndex = iterator.next();
                    // 当前索引等于书签索引
                    if (index == bookmarkIndex) {
                        // 添加数据
                        data.put(index, outlineItem);
                    }
                    // 索引自增
                    index++;
                }
            } else {
                // 遍历获取书签列表
                for (PDOutlineItem outlineItem : items) {
                    // 添加数据
                    data.put(index, outlineItem);
                    // 索引自增
                    index++;
                }
            }
        }
        // 返回书签字典
        return data;
    }
}
