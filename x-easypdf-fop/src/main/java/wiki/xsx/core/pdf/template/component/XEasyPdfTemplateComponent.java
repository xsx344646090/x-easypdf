package wiki.xsx.core.pdf.template.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;

import java.util.Optional;

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
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    Element createElement(Document document);

    /**
     * 创建空元素
     *
     * @param document fo文档
     * @return 返回空元素
     */
    default Element createEmptyElement(Document document) {
        return document.createElement(XEasyPdfTemplateTags.BLOCK);
    }

    /**
     * 创建block元素
     *
     * @param document fo文档
     * @param param    模板参数
     * @return 返回block元素
     */
    default Element createBlockElement(Document document, XEasyPdfTemplateComponentParam param) {
        // 创建block元素
        Element block = this.createEmptyElement(document);
        // 添加边框
        Optional.ofNullable(param.getHasBorder()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.BORDER, XEasyPdfTemplateConstants.DEFAULT_BORDER_VALUE));
        // 设置水平样式
        Optional.ofNullable(param.getHorizontalStyle()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.TEXT_ALIGN, v.intern()));
        // 设置上下左右边距
        Optional.ofNullable(param.getMargin()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN, v.intern()));
        // 设置上边距
        Optional.ofNullable(param.getMarginTop()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern()));
        // 设置下边距
        Optional.ofNullable(param.getMarginBottom()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern()));
        // 设置左边距
        Optional.ofNullable(param.getMarginLeft()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern()));
        // 设置右边距
        Optional.ofNullable(param.getMarginRight()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern()));
        // 返回block元素
        return block;
    }

    /**
     * 转换
     *
     * @param document fo文档
     * @return 返回元素
     */
    default Element transform(Document document) {
        // 创建元素
        Element element = this.createElement(document);
        // 如果元素不为空，则返回元素，否则返回空元素
        return element != null ? element : this.createEmptyElement(document);
    }
}
