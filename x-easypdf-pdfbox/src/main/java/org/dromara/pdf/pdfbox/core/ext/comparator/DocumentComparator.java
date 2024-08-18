package org.dromara.pdf.pdfbox.core.ext.comparator;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.ImageCompareInfo;
import org.dromara.pdf.pdfbox.core.info.TextCompareInfo;

import java.util.List;
import java.util.Map;

/**
 * 文档比较器
 *
 * @author xsx
 * @date 2024/8/6
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
public class DocumentComparator extends AbstractComparator {
    
    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentComparator(Document document) {
        super(document);
    }
    
    /**
     * 比较文本
     *
     * @param other 文档
     */
    public Map<Integer, List<TextCompareInfo>> compareToText(Document other, int... pageIndexes) {
        return new TextComparator(this.document).compareTo(other, pageIndexes);
    }
    
    /**
     * 比较图像
     *
     * @param other 文档
     */
    public Map<Integer, List<ImageCompareInfo>> compareToImage(Document other, int... pageIndexes) {
        return new ImageComparator(this.document).compareTo(other, pageIndexes);
    }
}
