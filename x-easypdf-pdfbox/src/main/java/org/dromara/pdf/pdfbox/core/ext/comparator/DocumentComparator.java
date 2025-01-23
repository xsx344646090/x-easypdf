package org.dromara.pdf.pdfbox.core.ext.comparator;

import org.dromara.pdf.pdfbox.core.base.Document;

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
     * 获取文本比较器
     *
     * @return 返回文本比较器
     */
    public TextComparator getTextComparator() {
        return new TextComparator(this.document);
    }

    /**
     * 获取图像比较器
     *
     * @return 返回图像比较器
     */
    public ImageComparator getImageComparator() {
        return new ImageComparator(this.document);
    }
}
