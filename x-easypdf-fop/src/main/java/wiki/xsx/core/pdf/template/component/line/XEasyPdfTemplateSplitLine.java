package wiki.xsx.core.pdf.template.component.line;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.Color;
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
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置长度
     *
     * @param length 长度
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setLength(String length) {
        this.param.setLength(length);
        return this;
    }

    /**
     * 设置样式
     *
     * @param style 样式
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setStyle(String style) {
        this.param.setStyle(style);
        return this;
    }

    /**
     * 设置颜色
     *
     * @param color 颜色
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setColor(Color color) {
        this.param.setColor(color);
        return this;
    }

    /**
     * 设置文本水平样式
     * <p>LEFT：居左</p>
     * <p>CENTER：居中</p>
     * <p>RIGHT：居右</p>
     *
     * @param style 水平样式
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回分割线组件
     */
    public XEasyPdfTemplateSplitLine enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
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
        // 添加leader元素
        block.appendChild(this.createLeader(document));
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
        // 设置上下左右填充
        Optional.ofNullable(param.getPadding()).ifPresent(v -> leader.setAttribute(XEasyPdfTemplateAttributes.PADDING, v.intern()));
        // 设置上填充
        Optional.ofNullable(param.getPaddingTop()).ifPresent(v -> leader.setAttribute(XEasyPdfTemplateAttributes.PADDING_TOP, v.intern()));
        // 设置下填充
        Optional.ofNullable(param.getPaddingBottom()).ifPresent(v -> leader.setAttribute(XEasyPdfTemplateAttributes.PADDING_BOTTOM, v.intern()));
        // 设置左填充
        Optional.ofNullable(param.getPaddingLeft()).ifPresent(v -> leader.setAttribute(XEasyPdfTemplateAttributes.PADDING_LEFT, v.intern()));
        // 设置右填充
        Optional.ofNullable(param.getPaddingRight()).ifPresent(v -> leader.setAttribute(XEasyPdfTemplateAttributes.PADDING_RIGHT, v.intern()));
        // 设置分割线长度
        Optional.ofNullable(this.param.getLength()).ifPresent(
                v -> leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_LENGTH, v.intern())
        );
        // 设置分割线样式（默认：solid）
        Optional.ofNullable(this.param.getStyle()).ifPresent(
                v -> {
                    // 如果分割线样式为虚线，则设置为虚线样式
                    if ("dotted".equalsIgnoreCase(v)) {
                        // 设置虚线样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_PATTERN, XEasyPdfTemplateConstants.DEFAULT_DOTTED_SPLIT_LINE_STYLE_VALUE);
                    }
                    // 否则设置为规则样式
                    else {
                        // 设置分割线样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.LEADER_PATTERN, XEasyPdfTemplateConstants.DEFAULT_SPLIT_LINE_STYLE_VALUE);
                        // 设置规则样式
                        leader.setAttribute(XEasyPdfTemplateAttributes.RULE_STYLE, v.intern());
                    }
                }
        );
        // 设置分割线颜色
        Optional.ofNullable(this.param.getColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(leader, v));
        // 返回leader元素
        return leader;
    }
}
