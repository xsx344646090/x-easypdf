package wiki.xsx.core.pdf.template.page;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-页面
 *
 * @author xsx
 * @date 2022/8/5
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
public class XEasyPdfTemplatePage implements XEasyPdfTemplatePageComponent {

    /**
     * pdf模板-页面参数
     */
    private final XEasyPdfTemplatePageParam param = new XEasyPdfTemplatePageParam();

    /**
     * 设置页面宽度
     *
     * @param width 页面宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setWidth(String width) {
        Optional.ofNullable(width).ifPresent(this.param::setWidth);
        return this;
    }

    /**
     * 设置页面高度
     *
     * @param height 页面高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeight(String height) {
        Optional.ofNullable(height).ifPresent(this.param::setHeight);
        return this;
    }

    /**
     * 设置页面边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMargin(String margin) {
        this.param.setMarginTop(margin).setMarginBottom(margin).setMarginLeft(margin).setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面上边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginTop(String margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置页面下边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginBottom(String margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置页面左边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginLeft(String margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置页面右边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginRight(String margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面主体边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMargin(String margin) {
        this.param.getRegionBodyParam().setMarginTop(margin).setMarginBottom(margin).setMarginLeft(margin).setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面主体上边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginTop(String margin) {
        this.param.getRegionBodyParam().setMarginTop(margin);
        return this;
    }

    /**
     * 设置页面主体下边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginBottom(String margin) {
        this.param.getRegionBodyParam().setMarginBottom(margin);
        return this;
    }

    /**
     * 设置页面主体左边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginLeft(String margin) {
        this.param.getRegionBodyParam().setMarginLeft(margin);
        return this;
    }

    /**
     * 设置页面主体右边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginRight(String margin) {
        this.param.getRegionBodyParam().setMarginRight(margin);
        return this;
    }

    /**
     * 设置页眉高度
     *
     * @param height 高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderHeight(String height) {
        this.param.getRegionBeforeParam().setHeight(height);
        return this;
    }

    /**
     * 设置页脚高度
     *
     * @param height 高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterHeight(String height) {
        this.param.getRegionAfterParam().setHeight(height);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param fontStyle 字体样式
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体重量
     *
     * @param fontWeight 字体重量
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 切换横向
     *
     * @return 返回页面
     */
    public XEasyPdfTemplatePage changeLandscape() {
        this.param.changeLandscape();
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addBodyComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBodyParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addBodyComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBodyParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBeforeParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBeforeParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addFooterComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionAfterParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addFooterComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionAfterParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 创建元素
     *
     * @param index    当前索引
     * @param document fo文档
     * @return 返回节点
     */
    @Override
    public Element createElement(int index, Document document) {
        // 获取根元素
        Element root = document.getDocumentElement();
        // 添加页面模板并获取页面模板名称
        String masterName = this.addLayoutMasterSet(index, document, root);
        // 添加页面序列
        return this.addPageSequence(document, masterName);
    }

    /**
     * 添加页面模板
     *
     * @param index    当前页面索引
     * @param document fo文档
     * @param root     根元素
     * @return 返回页面模板名称
     */
    private String addLayoutMasterSet(int index, Document document, Element root) {
        // 构建页面模板名称
        String masterName = "page" + index;
        // 创建页面模板
        Node layoutMasterSet = root.getElementsByTagName(XEasyPdfTemplateTags.LAYOUT_MASTER_SET).item(0);
        // 添加单页面模板
        layoutMasterSet.appendChild(this.createSimplePageMaster(document, masterName));
        // 返回页面名称
        return masterName;
    }

    /**
     * 创建单页面模板
     *
     * @param document   fo文档
     * @param masterName 页面模板名称
     * @return 返回单页面模板元素
     */
    private Element createSimplePageMaster(Document document, String masterName) {
        // 创建单页面模板
        Element simplePageMaster = document.createElement(XEasyPdfTemplateTags.SIMPLE_PAGE_MASTER);
        // 设置页面名称
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MASTER_NAME, masterName);
        // 设置页面宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_WIDTH, v.intern()));
        // 设置页面高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_HEIGHT, v.intern()));
        // 设置页面上边距
        Optional.ofNullable(this.param.getMarginTop()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern()));
        // 设置页面下边距
        Optional.ofNullable(this.param.getMarginBottom()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern()));
        // 设置页面左边距
        Optional.ofNullable(this.param.getMarginLeft()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern()));
        // 设置页面右边距
        Optional.ofNullable(this.param.getMarginRight()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern()));
        // 添加页面主体区域
        simplePageMaster.appendChild(this.createRegionBody(document));
        // 添加页眉区域
        simplePageMaster.appendChild(this.createRegionBefore(document));
        // 添加页脚区域
        simplePageMaster.appendChild(this.createRegionAfter(document));
        // 返回单页面模板
        return simplePageMaster;
    }

    /**
     * 创建页面主体区域
     *
     * @param document fo文档
     * @return 返回页面主体区域元素
     */
    private Element createRegionBody(Document document) {
        // 创建页面主体区域
        Element regionBody = document.createElement(XEasyPdfTemplateTags.REGION_BODY);
        // 设置页面主体区域上边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginTop()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern()));
        // 设置页面主体区域下边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginBottom()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern()));
        // 设置页面主体区域左边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginLeft()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern()));
        // 设置页面主体区域右边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginRight()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern()));
        // 返回页面主体区域
        return regionBody;
    }

    /**
     * 创建页眉区域
     *
     * @param document fo文档
     * @return 返回页眉区域元素
     */
    private Element createRegionBefore(Document document) {
        // 创建页眉区域
        Element regionBefore = document.createElement(XEasyPdfTemplateTags.REGION_BEFORE);
        // 设置页眉区域范围
        Optional.ofNullable(this.param.getRegionBeforeParam().getHeight()).ifPresent(v -> regionBefore.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern()));
        // 返回页眉区域
        return regionBefore;
    }

    /**
     * 创建页脚区域
     *
     * @param document fo文档
     * @return 返回页脚区域元素
     */
    private Element createRegionAfter(Document document) {
        // 创建页脚区域
        Element regionAfter = document.createElement(XEasyPdfTemplateTags.REGION_AFTER);
        // 设置页脚区域范围
        Optional.ofNullable(this.param.getRegionAfterParam().getHeight()).ifPresent(v -> regionAfter.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern()));
        // 返回页脚区域
        return regionAfter;
    }

    /**
     * 添加页面序列
     *
     * @param document   fo文档
     * @param masterName 页面模板名称
     * @return 返回页面序列元素
     */
    private Element addPageSequence(Document document, String masterName) {
        // 创建页面序列
        Element pageSequence = document.createElement(XEasyPdfTemplateTags.PAGE_SEQUENCE);
        // 设置页面模板名称
        pageSequence.setAttribute(XEasyPdfTemplateAttributes.MASTER_REFERENCE, masterName);
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getFontColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(pageSequence, v));
        // 添加页面主体区域
        pageSequence.appendChild(this.addRegionBody(document));
        // 如果包含页眉，则添加页眉
        if (this.param.hasHeader()) {
            // 添加页眉
            pageSequence.appendChild(this.addRegionBefore(document));
        }
        // 如果包含页脚，则添加页脚
        if (this.param.hasFooter()) {
            // 添加页脚
            pageSequence.appendChild(this.addRegionAfter(document));
        }
        // 返回页面序列
        return pageSequence;
    }

    /**
     * 添加页面主体区域
     *
     * @param document fo文档
     * @return 返回页面主体区域元素
     */
    private Element addRegionBody(Document document) {
        // 创建页面流
        Element flow = document.createElement(XEasyPdfTemplateTags.FLOW);
        // 设置页面流向（页面主体区域）
        flow.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, "xsl-region-body");
        // 如果包含页面主体，则添加组件
        if (this.param.hasBody()) {
            // 遍历页面主体区域组件列表
            this.param.getRegionBodyParam().getComponentList().forEach(
                    // 添加组件
                    v -> flow.appendChild(v.transform(document))
            );
        }
        // 否则添加空元素
        else {
            // 添加空元素
            flow.appendChild(document.createElement(XEasyPdfTemplateTags.BLOCK));
        }
        // 返回页面流
        return flow;
    }

    /**
     * 添加页眉
     *
     * @param document fo文档
     * @return 返回页眉元素
     */
    private Element addRegionBefore(Document document) {
        // 创建静态内容
        Element staticContent = document.createElement(XEasyPdfTemplateTags.STATIC_CONTENT);
        // 设置页面流向（页眉区域）
        staticContent.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, "xsl-region-before");
        // 遍历页眉区域组件列表
        this.param.getRegionBeforeParam().getComponentList().forEach(
                // 添加组件
                v -> staticContent.appendChild(v.transform(document))
        );
        // 返回静态内容
        return staticContent;
    }

    /**
     * 添加页脚
     *
     * @param document fo文档
     * @return 返回页脚元素
     */
    private Element addRegionAfter(Document document) {
        // 创建静态内容
        Element staticContent = document.createElement(XEasyPdfTemplateTags.STATIC_CONTENT);
        // 设置页面流向（页脚区域）
        staticContent.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, "xsl-region-after");
        // 遍历页脚区域组件列表
        this.param.getRegionAfterParam().getComponentList().forEach(
                // 添加组件
                v -> staticContent.appendChild(v.transform(document))
        );
        // 返回静态内容
        return staticContent;
    }
}
