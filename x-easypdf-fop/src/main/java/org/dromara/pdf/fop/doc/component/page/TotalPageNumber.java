package org.dromara.pdf.fop.doc.component.page;

import org.dromara.pdf.fop.TemplateAttributes;
import org.dromara.pdf.fop.TemplateTags;
import org.dromara.pdf.fop.doc.component.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-总页码组件
 *
 * @author xsx
 * @date 2022/11/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class TotalPageNumber implements Component {

    /**
     * 页面id
     */
    private String pageId;

    /**
     * 设置页面id
     *
     * @param pageId 页面id
     * @return 返回总页码组件
     */
    public TotalPageNumber setPageId(String pageId) {
        this.pageId = pageId;
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    @Override
    public Element createElement(Document document) {
        // 创建block元素
        Element block = this.createEmptyElement(document);
        // 创建totalPageNumber元素
        Element totalPageNumber = document.createElement(TemplateTags.PAGE_NUMBER_CITATION_LAST);
        // 设置引用id
        totalPageNumber.setAttribute(TemplateAttributes.REF_ID, Optional.ofNullable(this.pageId).orElseThrow(() -> new IllegalArgumentException("the page id can not bu null")));
        // 添加总页码
        block.appendChild(totalPageNumber);
        // 返回block元素
        return block;
    }
}
