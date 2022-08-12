package wiki.xsx.core.pdf.template.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplatePositionStyle;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTag;

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
        return document.createElement(XEasyPdfTemplateTag.BLOCK);
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
        // 如果开启边框，则添加边框
        if (param.getHasBorder() != null) {
            // 添加边框
            block.setAttribute("border", XEasyPdfTemplateConstants.DEFAULT_BORDER_VALUE);
        }
        // 如果水平样式不为空，则设置水平样式
        if (param.getHorizontalStyle() != null) {
            // 获取水平样式
            XEasyPdfTemplatePositionStyle style = param.getHorizontalStyle();
            // 如果为水平样式，则设置水平样式
            if (style.isHorizontalStyle()) {
                // 设置水平样式
                block.setAttribute(style.getKey(), style.getValue());
            }
        }
        // 如果上填充不为空，则设置上填充
        if (param.getPaddingTop() != null) {
            // 设置上填充
            block.setAttribute("padding-top", param.getPaddingTop());
        }
        // 如果下填充不为空，则设置下填充
        if (param.getPaddingBottom() != null) {
            // 设置下填充
            block.setAttribute("padding-bottom", param.getPaddingBottom());
        }
        // 如果左填充不为空，则设置左填充
        if (param.getPaddingLeft() != null) {
            // 设置左填充
            block.setAttribute("padding-left", param.getPaddingLeft());
        }
        // 如果右填充不为空，则设置右填充
        if (param.getPaddingRight() != null) {
            // 设置右填充
            block.setAttribute("padding-left", param.getPaddingRight());
        }
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
        Element element = this.createElement(document);
        return element != null ? element : this.createEmptyElement(document);
    }
}
