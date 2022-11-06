package wiki.xsx.core.pdf.template.handler;

import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;

import java.awt.*;

/**
 * pdf模板元素助手
 *
 * @author xsx
 * @date 2022/8/23
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
public class XEasyPdfTemplateElementHandler {

    /**
     * 添加子元素
     *
     * @param parent 父元素
     * @param child  子元素
     */
    public static void appendChild(Element parent, Element child) {
        if (parent != null && child != null) {
            parent.appendChild(child);
        }
    }

    /**
     * 添加颜色
     *
     * @param element 元素
     * @param color   颜色
     */
    public static void appendColor(Element element, Color color) {
        appendColor(element, XEasyPdfTemplateAttributes.COLOR, color);
    }

    /**
     * 添加颜色
     *
     * @param element   元素
     * @param attribute 属性
     * @param color     颜色
     */
    public static void appendColor(Element element, String attribute, Color color) {
        if (element != null && color != null) {
            element.setAttribute(
                    attribute,
                    String.join(
                            "",
                            "rgb(",
                            String.valueOf(color.getRed()),
                            ",",
                            String.valueOf(color.getGreen()),
                            ",",
                            String.valueOf(color.getBlue()),
                            ")"
                    )
            );
        }
    }
}
