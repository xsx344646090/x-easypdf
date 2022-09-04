package wiki.xsx.core.pdf.template.page;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplateRegionStyle;

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
     * @param pageWidth 页面宽度
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageWidth(String pageWidth) {
        Optional.ofNullable(pageWidth).ifPresent(this.param::setPageWidth);
        return this;
    }

    /**
     * 设置页面高度
     *
     * @param pageHeight 页面高度
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageHeight(String pageHeight) {
        Optional.ofNullable(pageHeight).ifPresent(this.param::setPageHeight);
        return this;
    }

    /**
     * 设置页面边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageMargin(String margin) {
        Optional.ofNullable(margin).ifPresent(v -> this.param.setMarginTop(v).setMarginBottom(v).setMarginLeft(v).setMarginRight(v));
        return this;
    }

    /**
     * 设置页面上边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageMarginTop(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param::setMarginTop);
        return this;
    }

    /**
     * 设置页面下边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageMarginBottom(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param::setMarginBottom);
        return this;
    }

    /**
     * 设置页面左边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageMarginLeft(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param::setMarginLeft);
        return this;
    }

    /**
     * 设置页面右边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setPageMarginRight(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param::setMarginRight);
        return this;
    }

    /**
     * 设置页面主体边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setBodyMargin(String margin) {
        Optional.ofNullable(margin).ifPresent(v -> this.param.getRegionBodyParam().setMarginTop(v).setMarginBottom(v).setMarginLeft(v).setMarginRight(v));
        return this;
    }

    /**
     * 设置页面主体上边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setBodyMarginTop(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param.getRegionBodyParam()::setMarginTop);
        return this;
    }

    /**
     * 设置页面主体下边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setBodyMarginBottom(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param.getRegionBodyParam()::setMarginBottom);
        return this;
    }

    /**
     * 设置页面主体左边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setBodyMarginLeft(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param.getRegionBodyParam()::setMarginLeft);
        return this;
    }

    /**
     * 设置页面主体右边距
     *
     * @param margin 边距
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setBodyMarginRight(String margin) {
        Optional.ofNullable(margin).ifPresent(this.param.getRegionBodyParam()::setMarginRight);
        return this;
    }

    /**
     * 设置页眉高度
     *
     * @param height 高度
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setHeaderHeight(String height) {
        Optional.ofNullable(height).ifPresent(this.param.getRegionBeforeParam()::setHeight);
        return this;
    }

    /**
     * 设置页脚高度
     *
     * @param height 高度
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage setFooterHeight(String height) {
        Optional.ofNullable(height).ifPresent(this.param.getRegionAfterParam()::setHeight);
        return this;
    }

    /**
     * 切换横向
     *
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage changeLandscape() {
        this.param.changeLandscape();
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addBodyComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBodyParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addBodyComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBodyParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBeforeParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBeforeParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addFooterComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionAfterParam().getComponentList(), v));
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回pdf模板-页面
     */
    public XEasyPdfTemplatePage addFooterComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionAfterParam().getComponentList()::addAll);
        return this;
    }

    /**
     * 转换
     *
     * @param index    当前索引
     * @param document fo文档
     * @return 返回节点
     */
    @Override
    public Node transform(int index, Document document) {
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
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_WIDTH, this.param.getPageWidth().intern());
        // 设置页面高度
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_HEIGHT, this.param.getPageHeight().intern());
        // 设置页面上边距
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, this.param.getMarginTop().intern());
        // 设置页面下边距
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, this.param.getMarginBottom().intern());
        // 设置页面左边距
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, this.param.getMarginLeft().intern());
        // 设置页面右边距
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, this.param.getMarginRight().intern());
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
        regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, this.param.getRegionBodyParam().getMarginTop().intern());
        // 设置页面主体区域下边距
        regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, this.param.getRegionBodyParam().getMarginBottom().intern());
        // 设置页面主体区域左边距
        regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, this.param.getRegionBodyParam().getMarginLeft().intern());
        // 设置页面主体区域右边距
        regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, this.param.getRegionBodyParam().getMarginRight().intern());
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
        regionBefore.setAttribute(XEasyPdfTemplateAttributes.EXTENT, this.param.getRegionBeforeParam().getHeight().intern());
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
        regionAfter.setAttribute(XEasyPdfTemplateAttributes.EXTENT, this.param.getRegionAfterParam().getHeight().intern());
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
        flow.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, XEasyPdfTemplateRegionStyle.BODY.getValue());
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
        staticContent.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, XEasyPdfTemplateRegionStyle.BEFORE.getValue());
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
        staticContent.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, XEasyPdfTemplateRegionStyle.AFTER.getValue());
        // 遍历页脚区域组件列表
        this.param.getRegionAfterParam().getComponentList().forEach(
                // 添加组件
                v -> staticContent.appendChild(v.transform(document))
        );
        // 返回静态内容
        return staticContent;
    }
}
