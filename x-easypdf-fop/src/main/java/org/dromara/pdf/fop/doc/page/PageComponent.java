package org.dromara.pdf.fop.doc.page;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * pdf模板-页面组件
 *
 * @author xsx
 * @date 2022/8/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface PageComponent {

    /**
     * 创建元素
     *
     * @param index    当前索引
     * @param document fo文档
     * @param bookmark 书签元素
     * @return 返回节点
     */
    Element createElement(int index, Document document, Element bookmark);
}
