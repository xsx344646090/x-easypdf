package wiki.xsx.core.pdf.template.component.line;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplateBorderStyle;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.*;
import java.util.Optional;

/**
 * pdf模板-分割线组件
 * <p>fo:leader</p>
 *
 * @author xsx
 * @date 2022/9/3
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
public class XEasyPdfTemplateSplitLine implements XEasyPdfTemplateComponent {

    /**
     * 分割线参数
     */
    private final XEasyPdfTemplateSplitLineParam param = new XEasyPdfTemplateSplitLineParam();

    /**
     * 设置长度
     *
     * @param length 长度
     * @return 返回pdf模板-分割线组件
     */
    public XEasyPdfTemplateSplitLine setLength(String length) {
        Optional.ofNullable(length).ifPresent(this.param::setLength);
        return this;
    }

    /**
     * 设置样式
     *
     * @param style 样式
     * @return 返回pdf模板-分割线组件
     */
    public XEasyPdfTemplateSplitLine setStyle(String style) {
        Optional.ofNullable(style).ifPresent(this.param::setStyle);
        return this;
    }

    /**
     * 设置颜色
     *
     * @param color 颜色
     * @return 返回pdf模板-分割线组件
     */
    public XEasyPdfTemplateSplitLine setColor(Color color) {
        this.param.setColor(color);
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
        Element block = this.createBlockElement(document, this.param);
        // 创建leader元素
        Element leader = this.createLeader(document);
        // 添加leader元素
        block.appendChild(leader);
        // 返回block元素
        return block;
    }

    /**
     * 创建leader元素
     *
     * @param document fo文档
     * @return 返回leader元素
     */
    public Element createLeader(Document document) {
        // 创建leader元素
        Element leader = document.createElement(XEasyPdfTemplateTags.LEADER);
        // 设置分割线长度（默认：100%）
        Optional.ofNullable(this.param.getLength()).ifPresent(
                v -> leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_LENGTH, v)
        );
        // 设置分割线样式（默认：solid）
        Optional.ofNullable(this.param.getStyle()).ifPresent(
                v -> {
                    // 如果分割线样式为虚线，则设置为虚线样式
                    if (XEasyPdfTemplateBorderStyle.DOTTED.getValue().equalsIgnoreCase(v)) {
                        // 设置虚线样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_PATTERN, XEasyPdfTemplateConstants.DEFAULT_DOTTED_SPLIT_LINE_STYLE_VALUE);
                    }
                    // 否则设置为规则样式
                    else {
                        // 设置分割线样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_PATTERN, XEasyPdfTemplateConstants.DEFAULT_SPLIT_LINE_STYLE_VALUE);
                        // 设置规则样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.RULE_STYLE, v);
                    }
                }
        );
        // 设置分割线颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(leader, v));
        // 返回leader元素
        return leader;
    }
}
