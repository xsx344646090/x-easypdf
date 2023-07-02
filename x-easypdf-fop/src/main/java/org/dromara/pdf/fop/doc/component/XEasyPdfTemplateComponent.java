package org.dromara.pdf.fop.doc.component;

import org.dromara.pdf.fop.XEasyPdfTemplateAttributes;
import org.dromara.pdf.fop.XEasyPdfTemplateConstants;
import org.dromara.pdf.fop.XEasyPdfTemplateTags;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Optional;

/**
 * pdf模板组件
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
        return this.createBlockElement(document, param, true);
    }

    /**
     * 创建block元素
     *
     * @param document              fo文档
     * @param param                 模板参数
     * @param isAddMarginAndPadding 是否添加边距与填充
     * @return 返回block元素
     */
    default Element createBlockElement(Document document, XEasyPdfTemplateComponentParam param, boolean isAddMarginAndPadding) {
        // 创建block元素
        Element block = this.createEmptyElement(document);
        // 如果需要添加边距与填充，则添加
        if (isAddMarginAndPadding) {
            // 设置上下左右边距
            Optional.ofNullable(param.getMargin()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN, v.intern().toLowerCase()));
            // 设置上边距
            Optional.ofNullable(param.getMarginTop()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern().toLowerCase()));
            // 设置下边距
            Optional.ofNullable(param.getMarginBottom()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern().toLowerCase()));
            // 设置左边距
            Optional.ofNullable(param.getMarginLeft()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern().toLowerCase()));
            // 设置右边距
            Optional.ofNullable(param.getMarginRight()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern().toLowerCase()));
            // 设置上下左右填充
            Optional.ofNullable(param.getPadding()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.PADDING, v.intern().toLowerCase()));
            // 设置上填充
            Optional.ofNullable(param.getPaddingTop()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.PADDING_TOP, v.intern().toLowerCase()));
            // 设置下填充
            Optional.ofNullable(param.getPaddingBottom()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.PADDING_BOTTOM, v.intern().toLowerCase()));
            // 设置左填充
            Optional.ofNullable(param.getPaddingLeft()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.PADDING_LEFT, v.intern().toLowerCase()));
            // 设置右填充
            Optional.ofNullable(param.getPaddingRight()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.PADDING_RIGHT, v.intern().toLowerCase()));
        }
        // 添加id
        Optional.ofNullable(param.getId()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.ID, v.intern()));
        // 添加边框
        Optional.ofNullable(param.getHasBorder()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.BORDER, XEasyPdfTemplateConstants.DEFAULT_BORDER_VALUE));
        // 设置水平样式
        Optional.ofNullable(param.getHorizontalStyle()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.TEXT_ALIGN, v.intern().toLowerCase()));
        // 设置段前空白
        Optional.ofNullable(param.getSpaceBefore()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.SPACE_BEFORE, v.intern().toLowerCase()));
        // 设置段后空白
        Optional.ofNullable(param.getSpaceAfter()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.SPACE_AFTER, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(param.getWhiteSpace()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置空白空间折叠
        Optional.ofNullable(param.getWhiteSpaceCollapse()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WHITE_SPACE_COLLAPSE, v.intern().toLowerCase()));
        // 设置分页符-前
        Optional.ofNullable(param.getBreakBefore()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.BREAK_BEFORE, v.intern().toLowerCase()));
        // 设置分页符-后
        Optional.ofNullable(param.getBreakAfter()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.BREAK_AFTER, v.intern().toLowerCase()));
        // 设置分页时保持一起
        Optional.ofNullable(param.getKeepTogether()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.KEEP_TOGETHER, v.intern().toLowerCase()));
        // 设置分页时与上一个保持一起
        Optional.ofNullable(param.getKeepWithPrevious()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.KEEP_WITH_PREVIOUS, v.intern().toLowerCase()));
        // 设置分页时与下一个保持一起
        Optional.ofNullable(param.getKeepWithNext()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.KEEP_WITH_NEXT, v.intern().toLowerCase()));
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
        // 如果元素不为空，则返回元素，否则返回空元素
        return Optional.ofNullable(this.createElement(document)).orElse(this.createEmptyElement(document));
    }

    /**
     * 添加子节点
     *
     * @param parent 父元素
     * @param child  子节点
     */
    default void appendChild(Element parent, Node child) {
        // 如果子节点为空，则直接返回
        if (child == null) {
            // 直接返回
            return;
        }
        // 添加子节点
        Optional.ofNullable(child.getFirstChild()).ifPresent(
                v -> {
                    // 添加子节点
                    parent.appendChild(v);
                    // 递归添加
                    this.appendChild(parent, child);
                }
        );
    }
}
