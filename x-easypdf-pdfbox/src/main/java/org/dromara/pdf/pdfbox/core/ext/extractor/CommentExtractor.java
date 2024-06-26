package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.List;
import java.util.Map;

/**
 * 评论提取器
 *
 * @author xsx
 * @date 2023/10/24
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
public class CommentExtractor extends AbstractCommentExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public CommentExtractor(Document document) {
        super(document);
    }

    /**
     * 提取评论
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    @Override
    public Map<Integer, List<String>> extract(String regex, int... pageIndexes) {
        return this.processComment(regex, pageIndexes);
    }
}
