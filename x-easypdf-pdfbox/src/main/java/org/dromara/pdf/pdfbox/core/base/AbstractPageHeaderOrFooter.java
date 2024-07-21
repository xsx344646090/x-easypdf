package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.component.Component;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 抽象页眉页脚
 *
 * @author xsx
 * @date 2023/9/21
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractPageHeaderOrFooter extends AbstractBase {
    /**
     * 边距配置
     */
    protected MarginConfiguration marginConfiguration;
    /**
     * 边框配置
     */
    protected BorderConfiguration borderConfiguration;
    /**
     * 分页事件
     */
    protected PagingEvent pagingEvent;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 宽度
     */
    protected Float width;
    /**
     * 高度
     */
    protected Float height;
    /**
     * X轴起始坐标
     */
    protected Float beginX;
    /**
     * Y轴起始坐标
     */
    protected Float beginY;
    /**
     * 组件列表
     */
    protected List<Component> components;
    /**
     * 是否已经绘制
     */
    protected Boolean isAlreadyRendered;
    
    
    /**
     * 有参构造
     *
     * @param page 页面
     */
    public AbstractPageHeaderOrFooter(Page page) {
        super.init(page);
        this.marginConfiguration = new MarginConfiguration();
        this.borderConfiguration = new BorderConfiguration();
        this.pagingEvent = new DefaultPageHeaderFooterPagingEvent();
        this.backgroundColor = page.getBackgroundColor();
        this.isAlreadyRendered = Boolean.FALSE;
    }
    
    /**
     * 初始化
     */
    protected abstract void init();
    
    /**
     * 获取类型
     *
     * @return 返回类型
     */
    public abstract ComponentType getType();
    
    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    public abstract PagingEvent getPagingEvent();
    
    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.marginConfiguration.setMargin(margin);
    }
    
    /**
     * 设置上边距
     *
     * @param margin 边距
     */
    public void setMarginTop(float margin) {
        // 设置上边距
        this.marginConfiguration.setMarginTop(margin);
    }
    
    /**
     * 设置下边距
     *
     * @param margin 边距
     */
    public void setMarginBottom(float margin) {
        this.marginConfiguration.setMarginBottom(margin);
    }
    
    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    public void setMarginLeft(float margin) {
        // 重置左边距
        this.marginConfiguration.setMarginLeft(margin);
    }
    
    /**
     * 设置右边距
     *
     * @param margin 边距
     */
    public void setMarginRight(float margin) {
        this.marginConfiguration.setMarginRight(margin);
    }
    
    /**
     * 设置边框样式
     *
     * @param style 样式
     */
    public void setBorderLineStyle(LineStyle style) {
        this.borderConfiguration.setBorderLineStyle(style);
    }
    
    /**
     * 设置线帽样式
     *
     * @param style 样式
     */
    public void setBorderLineCapStyle(LineCapStyle style) {
        this.borderConfiguration.setBorderLineCapStyle(style);
    }
    
    /**
     * 设置边框线长
     *
     * @param length 线长
     */
    public void setBorderLineLength(float length) {
        this.borderConfiguration.setBorderLineLength(length);
    }
    
    /**
     * 设置边框线宽
     *
     * @param width 线宽
     */
    public void setBorderLineWidth(float width) {
        this.borderConfiguration.setBorderLineWidth(width);
    }
    
    /**
     * 设置边框点线间隔
     *
     * @param spacing 间隔
     */
    public void setBorderDottedSpacing(float spacing) {
        this.borderConfiguration.setBorderDottedSpacing(spacing);
    }
    
    /**
     * 设置边框颜色（上下左右）
     *
     * @param color 颜色
     */
    public void setBorderColor(Color color) {
        this.borderConfiguration.setBorderColor(color);
    }
    
    /**
     * 设置上边框颜色
     *
     * @param color 颜色
     */
    public void setBorderTopColor(Color color) {
        this.borderConfiguration.setBorderTopColor(color);
    }
    
    /**
     * 设置下边框颜色
     *
     * @param color 颜色
     */
    public void setBorderBottomColor(Color color) {
        this.borderConfiguration.setBorderBottomColor(color);
    }
    
    /**
     * 设置左边框颜色
     *
     * @param color 颜色
     */
    public void setBorderLeftColor(Color color) {
        this.borderConfiguration.setBorderLeftColor(color);
    }
    
    /**
     * 设置右边框颜色
     *
     * @param color 颜色
     */
    public void setBorderRightColor(Color color) {
        this.borderConfiguration.setBorderRightColor(color);
    }
    
    /**
     * 设置是否上边框
     *
     * @param flag 是否上边框
     */
    public void setBorderRightColor(boolean flag) {
        this.borderConfiguration.setIsBorderTop(flag);
    }
    
    /**
     * 设置是否边框（上下左右）
     *
     * @param flag 是否下边框
     */
    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
    }
    
    /**
     * 设置是否下边框
     *
     * @param flag 是否下边框
     */
    public void setIsBorderBottom(boolean flag) {
        this.borderConfiguration.setIsBorderBottom(flag);
    }
    
    /**
     * 设置是否左边框
     *
     * @param flag 是否左边框
     */
    public void setIsBorderLeft(boolean flag) {
        this.borderConfiguration.setIsBorderLeft(flag);
    }
    
    /**
     * 设置是否右边框
     *
     * @param flag 是否右边框
     */
    public void setIsBorderRight(boolean flag) {
        this.borderConfiguration.setIsBorderRight(flag);
    }
    
    /**
     * 设置高度
     *
     * @param height 高度
     */
    public void setHeight(float height) {
        // 校验高度
        Objects.requireNonNull(height, "the height can not be null");
        if (height > this.getContext().getPage().getWithoutMarginHeight()) {
            throw new IllegalArgumentException("the height can not be greater than the page height");
        }
        this.height = height;
    }
    
    /**
     * 设置组件
     *
     * @param components 组件
     */
    @SuppressWarnings("all")
    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    /**
     * 设置组件
     *
     * @param components 组件
     */
    public void setComponents(Component... components) {
        if (Objects.nonNull(components)) {
            this.components = new ArrayList<>(components.length);
            Collections.addAll(this.components, components);
        } else {
            this.components = null;
        }
    }
    
    /**
     * 获取页面
     *
     * @return 返回页面
     */
    public Page getPage() {
        return this.getContext().getPage();
    }
    
    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    public Float getMarginTop() {
        return this.marginConfiguration.getMarginTop();
    }
    
    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    public Float getMarginBottom() {
        return this.marginConfiguration.getMarginBottom();
    }
    
    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    public Float getMarginLeft() {
        return this.marginConfiguration.getMarginLeft();
    }
    
    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public Float getMarginRight() {
        return this.marginConfiguration.getMarginRight();
    }
    
    /**
     * 获取边框样式
     *
     * @return 返回边框样式
     */
    public LineStyle getBorderLineStyle() {
        return this.borderConfiguration.getBorderLineStyle();
    }
    
    /**
     * 获取线帽样式
     *
     * @return 返回线帽样式
     */
    public LineCapStyle getBorderLineCapStyle() {
        return this.borderConfiguration.getBorderLineCapStyle();
    }
    
    /**
     * 获取边框线长
     *
     * @return 返回边框线长
     */
    public Float getBorderLineLength() {
        return this.borderConfiguration.getBorderLineLength();
    }
    
    /**
     * 获取边框线宽
     *
     * @return 返回边框线宽
     */
    public Float getBorderLineWidth() {
        return this.borderConfiguration.getBorderLineWidth();
    }
    
    /**
     * 获取边框点线间隔
     *
     * @return 返回边框点线间隔
     */
    public Float getBorderDottedSpacing() {
        return this.borderConfiguration.getBorderDottedSpacing();
    }
    
    /**
     * 获取上边框颜色
     *
     * @return 返回上边框颜色
     */
    public Color getBorderTopColor() {
        return this.borderConfiguration.getBorderTopColor();
    }
    
    /**
     * 获取下边框颜色
     *
     * @return 返回下边框颜色
     */
    public Color getBorderBottomColor() {
        return this.borderConfiguration.getBorderBottomColor();
    }
    
    /**
     * 获取左边框颜色
     *
     * @return 返回左边框颜色
     */
    public Color getBorderLeftColor() {
        return this.borderConfiguration.getBorderLeftColor();
    }
    
    /**
     * 获取右边框颜色
     *
     * @return 返回右边框颜色
     */
    public Color getBorderRightColor() {
        return this.borderConfiguration.getBorderRightColor();
    }
    
    /**
     * 获取是否上边框
     *
     * @return 返回是否上边框
     */
    public Boolean getIsBorderTop() {
        return this.borderConfiguration.getIsBorderTop();
    }
    
    /**
     * 获取是否下边框
     *
     * @return 返回是否下边框
     */
    public Boolean getIsBorderBottom() {
        return this.borderConfiguration.getIsBorderBottom();
    }
    
    /**
     * 获取是否左边框
     *
     * @return 返回是否左边框
     */
    public Boolean getIsBorderLeft() {
        return this.borderConfiguration.getIsBorderLeft();
    }
    
    /**
     * 获取是否右边框
     *
     * @return 返回是否右边框
     */
    public Boolean getIsBorderRight() {
        return this.borderConfiguration.getIsBorderRight();
    }
    
    /**
     * 渲染前
     */
    public void renderBefore() {
        // 设置当前组件是否为第一个组件
        this.getContext().setIsFirstComponent(true);
        // 如果当前组件有背景色
        if (Objects.nonNull(this.getBackgroundColor())) {
            // 创建一个矩形，用于设置背景色
            PDRectangle rectangle = new PDRectangle(
                    this.getBeginX(),
                    this.getBeginY() - this.getHeight(),
                    this.getWidth(),
                    this.getHeight()
            );
            // 添加背景色
            CommonUtil.addBackgroundColor(this.getContext(), this.getContentMode(), this.getIsResetContentStream(), rectangle, this.getBackgroundColor());
        }
    }
    
    /**
     * 虚拟渲染
     */
    @SneakyThrows
    public void virtualRender() {
        // 初始化
        this.init();
        // 渲染组件
        Optional.ofNullable(this.getComponents())
                .orElse(Collections.emptyList())
                .forEach(this::virtualRenderComponent);
        // 重置
        this.reset();
    }
    
    /**
     * 渲染
     */
    public void render() {
        // 未渲染
        if (!this.getIsAlreadyRendered()) {
            // 初始化
            this.init();
            // 渲染前
            this.renderBefore();
            // 渲染组件
            Optional.ofNullable(this.getComponents())
                    .orElse(Collections.emptyList())
                    .forEach(this::renderComponent);
            // 渲染后
            this.renderAfter();
            // 重置
            this.reset();
        }
    }
    
    /**
     * 渲染后
     */
    public void renderAfter() {
        if (this.getBorderConfiguration().hasBorder()) {
            // 创建边框数据
            BorderData borderData = new BorderData(this, this.getBorderConfiguration());
            // 创建尺寸
            PDRectangle rectangle = new PDRectangle(
                    this.getBeginX(),
                    this.getBeginY() - this.getHeight(),
                    this.getWidth(),
                    this.getHeight()
            );
            // 绘制边框
            BorderUtil.drawBorderWithData(borderData, rectangle);
        }
        this.setIsAlreadyRendered(true);
    }
    
    /**
     * 虚拟渲染组件
     *
     * @param component 组件
     */
    public void virtualRenderComponent(org.dromara.pdf.pdfbox.core.component.Component component) {
        // 初始化类型
        component.getContext().setExecutingComponentType(this.getType());
        // 初始化X轴换行起始坐标
        this.getContext().setWrapBeginX(this.getBeginX());
        // 初始化换行宽度
        this.getContext().resetWrapWidth(this.getWidth() - this.getMarginLeft() - this.getMarginRight());
        // 虚拟渲染组件
        component.virtualRender();
    }
    
    /**
     * 渲染组件
     *
     * @param component 组件
     */
    public void renderComponent(org.dromara.pdf.pdfbox.core.component.Component component) {
        // 初始化类型
        component.getContext().setExecutingComponentType(this.getType());
        // 初始化X轴换行起始坐标
        this.getContext().resetWrapBeginX(this.getBeginX());
        // 初始化换行宽度
        this.getContext().resetWrapWidth(this.getWidth() - this.getMarginLeft() - this.getMarginRight());
        // 初始化高度
        this.getContext().resetHeight(this.getHeight());
        // 渲染组件
        component.render();
    }
    
    /**
     * 重置
     */
    public void reset() {
        // 重置光标
        this.getContext().getCursor().reset(this.getBeginX(), this.getBeginY() - this.getHeight());
        // 重置是否第一个组件
        this.getContext().setIsFirstComponent(!this.getContext().hasPageHeader());
        // 重置当前执行组件类型
        this.getContext().resetExecutingComponentType(this.getType());
        // 重置换行起始坐标
        this.getContext().resetWrapBeginX(null);
        // 重置换行宽度
        this.getContext().resetWrapWidth(null);
    }
    
    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    protected void initBackgroundColor(float beginX, float beginY) {
        // 初始化背景颜色
        if (Objects.equals(this.backgroundColor, Color.WHITE)) {
            PDRectangle rectangle = new PDRectangle(beginX, beginY - this.getHeight(), this.getWidth() - this.getMarginLeft() - this.getMarginRight(), this.getHeight());
            CommonUtil.addBackgroundColor(this.getContext(), this.getContentMode(), this.getIsResetContentStream(), rectangle, this.getBackgroundColor());
        }
    }
    
    /**
     * 初始化宽度
     */
    protected void initWidth() {
        this.width = this.getContext().getPage().getWithoutMarginWidth();
    }
    
    /**
     * 隐藏宽度设置
     */
    protected void setWidth(float width) {
    
    }
}
