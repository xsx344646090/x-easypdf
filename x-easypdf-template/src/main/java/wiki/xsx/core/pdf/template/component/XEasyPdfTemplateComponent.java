package wiki.xsx.core.pdf.template.component;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;

/**
 * pdf模板组件
 *
 * @author xsx
 * @date 2022/8/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public interface XEasyPdfTemplateComponent {

    /**
     * 创建节点
     *
     * @param document fo文档
     * @return 返回节点
     */
    Node createNode(Document document);

    /**
     * 创建空节点
     *
     * @param document fo文档
     * @return 返回空节点
     */
    default Node createEmptyNode(Document document) {
        return document.createElement(XEasyPdfTemplateConstants.TagName.BLOCK);
    }

    /**
     * 转换
     *
     * @param document fo文档
     * @return 返回节点
     */
    default Node transform(Document document) {
        Node node = this.createNode(document);
        return node != null ? node : this.createEmptyNode(document);
    }
}
