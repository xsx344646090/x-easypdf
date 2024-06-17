package org.dromara.pdf.pdfbox.core.base;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.component.Component;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;

import java.util.Objects;
import java.util.Optional;

/**
 * 页脚
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
@EqualsAndHashCode(callSuper = true)
public class PageFooter extends AbstractPageHeaderOrFooter {

    /**
     * 分页事件
     */
    private PagingEvent pagingEvent;

    /**
     * 原始Y轴坐标
     */
    private Float originalY;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public PageFooter(Page page) {
        super(page);
        page.getContext().setPageFooter(this);
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.PAGE_FOOTER;
    }

    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    @Override
    public PagingEvent getPagingEvent() {
        return this.pagingEvent;
    }

    /**
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        this.pagingEvent = new DefaultPageFooterVirtualPagingEvent();
        super.virtualRender();
    }

    /**
     * 渲染
     */
    @Override
    public void render() {
        this.pagingEvent = new DefaultPageFooterPagingEvent();
        super.render();
    }

    /**
     * 重置
     */
    @Override
    public void reset() {
        super.reset();
        // 重置光标
        this.getContext().getCursor().reset(this.getBeginX(), this.originalY);
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 检查高度
        Objects.requireNonNull(this.getHeight(), "the height can not be null");
        // 初始化X轴起始坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getPage().getMarginLeft() + this.getMarginLeft());
        }
        // 初始化Y轴起始坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getHeight() + this.getContext().getPage().getMarginBottom());
        }
        // 初始化宽度
        this.initWidth();
        // 初始化背景颜色
        this.initBackgroundColor(this.getBeginX(), this.getBeginY());
        // 初始化原始Y轴坐标
        this.originalY = this.getContext().getCursor().getY();
        // 重置光标
        this.getContext().getCursor().reset(this.getBeginX(), this.getBeginY() - this.getBeginYOffset());
    }

    /**
     * 默认虚拟分页事件
     */
    public static class DefaultPageFooterVirtualPagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {

        }

        /**
         * 分页之后
         *
         * @param component 当前组件
         */
        @Override
        public void after(Component component) {
            // 获取换行起始坐标
            Float wrapBeginX = component.getContext().getWrapBeginX();
            // 获取执行组件类型
            ComponentType currentExecutingComponentType = component.getContext().getExecutingComponentType();
            // 渲染组件
            Optional.ofNullable(component.getContext().getPageFooter()).ifPresent(AbstractPageHeaderOrFooter::virtualRender);
            // 重置执行组件类型
            component.getContext().setExecutingComponentType(currentExecutingComponentType);
            // 重置换行起始坐标
            component.getContext().setWrapBeginX(wrapBeginX);
        }
    }

    /**
     * 默认分页事件
     */
    public static class DefaultPageFooterPagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {

        }

        /**
         * 分页之后
         *
         * @param component 当前组件
         */
        @Override
        public void after(Component component) {
            // 获取换行起始坐标
            Float wrapBeginX = component.getContext().getWrapBeginX();
            // 获取执行组件类型
            ComponentType currentExecutingComponentType = component.getContext().getExecutingComponentType();
            // 渲染组件
            Optional.ofNullable(component.getContext().getPageFooter()).ifPresent(AbstractPageHeaderOrFooter::render);
            // 重置执行组件类型
            component.getContext().setExecutingComponentType(currentExecutingComponentType);
            // 重置换行起始坐标
            component.getContext().setWrapBeginX(wrapBeginX);
        }
    }
}


