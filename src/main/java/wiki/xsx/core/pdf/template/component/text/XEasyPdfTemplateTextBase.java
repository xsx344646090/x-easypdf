package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTextPositionStyle;

/**
 * pdf模板-文本基础组件
 * <p>fo:block标签</p>
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
public class XEasyPdfTemplateTextBase {

    /**
     * 创建Block节点
     *
     * @param document fo文档
     * @param param    文本参数
     * @return 返回节点
     */
    Element createBlock(Document document, XEasyPdfTemplateTextBaseParam param) {
        // 创建block节点
        Element block = document.createElement(XEasyPdfTemplateConstants.TagName.BLOCK);
        // 如果开启边框，则添加边框
        if (param.getHasBlockBorder() != null) {
            // 添加边框
            block.setAttribute("border", "1px solid black");
        }
        // 如果高度不为空，则设置行内高度
        if (param.getHeight() != null) {
            // 设置行内高度
            block.setAttribute("line-height", param.getHeight());
        }
        // 如果水平样式不为空，则设置水平样式
        if (param.getHorizontalStyle() != null) {
            // 获取水平样式
            XEasyPdfTemplateTextPositionStyle style = param.getHorizontalStyle();
            // 如果为水平样式，则设置水平样式
            if (style.isHorizontalStyle()) {
                // 设置水平样式
                block.setAttribute(style.isCenter() ? "text-align" : style.getKey(), style.getValue());
            }
        }
        // 返回block节点
        return block;
    }
}
