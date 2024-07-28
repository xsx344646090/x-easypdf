package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.Map;

/**
 * 书签提取器
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
@EqualsAndHashCode(callSuper = true)
public class BookmarkExtractor extends AbstractBookmarkExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public BookmarkExtractor(Document document) {
        super(document);
    }

    /**
     * 提取书签
     *
     * @param bookmarkIndexes 书签索引
     * @return 返回书签字典 <p>key = 书签索引，value = 提取书签</p>
     */
    @Override
    public Map<Integer, PDOutlineItem> extract(int... bookmarkIndexes) {
        return this.processBookmark(bookmarkIndexes);
    }
}
