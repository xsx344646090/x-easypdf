package wiki.xsx.core.pdf.component;

import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

/**
 * pdf分页条件
 *
 * @author xsx
 * @date 2022/7/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface XEasyPdfPagingCondition {

    /**
     * 是否分页
     *
     * @param document   pdf文档
     * @param page       pdf页面
     * @param componentY 当前组件Y轴坐标
     * @return 返回布尔值，是为true，否为false
     */
    boolean isPaging(XEasyPdfDocument document, XEasyPdfPage page, Float componentY);
}
