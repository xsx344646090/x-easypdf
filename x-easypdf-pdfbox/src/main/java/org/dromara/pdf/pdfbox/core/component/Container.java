package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.util.BorderUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 容器组件
 *
 * @author xsx
 * @date 2023/9/14
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
public class Container extends AbstractComponent {

    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float Height;
    /**
     * 组件列表
     */
    private List<Component> components;
    /**
     * 分页事件
     */
    private PagingEvent pagingEvent;
    /**
     * 是否分页边框
     */
    private Boolean isPagingBorder;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Container(Page page) {
        super(page);
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.CONTAINER;
    }

    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    public PagingEvent getPagingEvent() {
        return this.pagingEvent;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 校验宽度
        Objects.requireNonNull(this.width, "the width can not be null");
        // 校验高度
        Objects.requireNonNull(this.Height, "the height can not be null");
        // 初始化
        super.init();
        // 初始化其他
        this.initOthers();
        // 重置X轴起始坐标
        this.getContext().getCursor().setX(this.getBeginX());
    }

    /**
     * 虚拟渲染
     */
    @Override
    @SneakyThrows
    public void virtualRender() {
        // 初始化
        this.init();
        // 设置虚拟分页事件
        this.setPagingEvent(new DefaultContainerVirtualPagingEvent());
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            if (this.isPaging(this, this.getBeginY())) {
                // 重置容器信息Y轴起始坐标
                this.getContext().getContainerInfo().setBeginY(this.getContext().getCursor().getY());
            }
        }
        // 渲染之前X轴坐标
        Float beforeX = this.getBeginX();
        // 渲染之前Y轴坐标
        Float beforeY = this.getContext().getCursor().getY();
        // 渲染组件
        Optional.ofNullable(this.getComponents()).orElse(Collections.emptyList()).forEach(this::virtualRenderComponent);
        // 添加虚拟边框
        this.addVirtualBorder(beforeY, this.getContext().getContainerInfo());
        // 重置
        this.reset(beforeX);
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 设置分页事件
        this.setPagingEvent(new DefaultContainerPagingEvent());
        // // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            if (this.isPaging(this, this.getBeginY())) {
                // 重置容器信息Y轴起始坐标
                this.getContext().getContainerInfo().setBeginY(this.getContext().getCursor().getY());
            }
        }
        // 渲染之前X轴坐标
        Float beforeX = this.getBeginX();
        // 渲染之前Y轴坐标
        Float beforeY = this.getContext().getCursor().getY();
        // 渲染组件
        Optional.ofNullable(this.getComponents()).orElse(Collections.emptyList()).forEach(this::renderComponent);
        // 添加边框
        this.addBorder(beforeY, this.getContext().getContainerInfo());
        // 重置
        this.reset(beforeX);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 重置容器信息
        this.getContext().setContainerInfo(null);
        // 关闭
        super.close();
    }

    /**
     * 重置
     *
     * @param beforeX X轴起始坐标
     */
    protected void reset(Float beforeX) {
        // 重置X轴坐标
        this.getContext().getCursor().setX(beforeX + this.getWidth());
        // 重置换行宽度
        this.getContext().resetWrapWidth(null);
        // 重置换行高度
        this.getContext().resetWrapHeight(this.getContext().getContainerInfo().getHeight());
        // 重置
        super.reset(this.getType());
        // 重置换行高度
        this.getContext().setWrapHeight(this.getHeight());
    }

    /**
     * 初始化其他
     */
    protected void initOthers() {
        // 是否自定义X轴坐标
        if (!this.getIsCustomX()) {
            this.setBeginX(this.getContext().getPage().getMarginLeft() + this.getMarginLeft());
        }
        // 初始化是否分页边框
        if (Objects.isNull(this.getIsPagingBorder())) {
            this.setIsPagingBorder(Boolean.FALSE);
        }
        // 初始化容器信息
        this.getContext().setContainerInfo(new ContainerInfo(this));
        // 初始化换行宽度
        this.getContext().resetWrapWidth(this.getWidth());
    }

    /**
     * 虚拟渲染组件
     *
     * @param component 组件
     */
    protected void virtualRenderComponent(Component component) {
        // 初始化X轴换行起始坐标
        this.getContext().setWrapBeginX(this.getBeginX());
        // 渲染组件
        component.virtualRender();
        // 重置是否第一个组件
        this.getContext().getContainerInfo().setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 渲染组件
     *
     * @param component 组件
     */
    protected void renderComponent(Component component) {
        // 初始化X轴换行起始坐标
        this.getContext().setWrapBeginX(this.getBeginX());
        // 渲染组件
        component.render();
        // 重置是否第一个组件
        this.getContext().getContainerInfo().setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 添加虚拟边框
     *
     * @param beforeY 组件渲染前Y轴坐标
     * @param info    容器信息
     */
    @SneakyThrows
    protected void addVirtualBorder(Float beforeY, ContainerInfo info) {
        // 是否分页
        if (info.isPaging()) {
            // 非分页边框
            if (!this.getIsPagingBorder()) {
                // 重置是否上边框
                info.setIsBorderTop(Boolean.FALSE);
            }
            // 重置Y轴坐标
            beforeY = info.getBeginY();
        }
        // 需要分页
        if (this.checkPaging(beforeY - info.getHeight())) {
            // 分页
            this.paging();
            // 递归添加边框
            this.addVirtualBorder(info.getBeginY(), info);
        } else {
            // 重置光标
            this.getContext().getCursor().setY(beforeY - info.getHeight());
        }
    }

    /**
     * 添加边框
     *
     * @param beforeY 组件渲染前Y轴坐标
     * @param info    容器信息
     */
    @SneakyThrows
    protected void addBorder(Float beforeY, ContainerInfo info) {
        // 是否分页
        if (info.isPaging()) {
            // 非分页边框
            if (!this.getIsPagingBorder()) {
                // 重置是否上边框
                info.setIsBorderTop(Boolean.FALSE);
            }
            // 重置Y轴坐标
            beforeY = info.getBeginY();
        }
        // 需要分页
        if (this.checkPaging(beforeY - info.getHeight())) {
            // 分页
            this.paging();
            // 递归添加边框
            this.addBorder(info.getBeginY(), info);
        } else {
            // 绘制边框
            BorderUtil.drawBorderWithBase(
                    info,
                    new PDRectangle(
                            info.getBeginX(),
                            info.getBeginY() - info.getHeight(),
                            info.getWidth(),
                            info.getHeight()
                    )
            );
            // 重置光标
            this.getContext().getCursor().setY(beforeY - info.getHeight());
        }
    }

    /**
     * 默认虚拟容器分页事件
     */
    public static class DefaultContainerVirtualPagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {
            // 获取上下文
            Context context = component.getContext();
            // 获取容器信息
            ContainerInfo info = context.getContainerInfo();
            // 存在容器信息
            if (Objects.nonNull(info)) {
                // 重置高度
                info.setHeight(info.getHeight() - info.getBeginY() + component.getBottom());
                // 分页次数累计
                info.pagingCount();
            }
        }

        /**
         * 分页之后
         *
         * @param component 当前组件
         */
        @Override
        public void after(Component component) {
            // 获取上下文
            Context context = component.getContext();
            // 重置Y轴起始坐标
            Optional.ofNullable(context.getContainerInfo()).ifPresent(info -> info.setBeginY(context.getCursor().getY()));
        }
    }

    /**
     * 默认容器分页事件
     */
    public static class DefaultContainerPagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {
            // 获取上下文
            Context context = component.getContext();
            // 获取容器信息
            ContainerInfo info = context.getContainerInfo();
            // 存在容器信息
            if (Objects.nonNull(info)) {
                // 获取高度
                Float height = info.getHeight();
                // 重置高度
                info.setHeight(info.getBeginY() - component.getBottom());
                // 是否上边框
                Boolean isBorderTop = info.getIsBorderTop();
                // 是否下边框
                Boolean isBorderBottom = info.getIsBorderBottom();
                // 是否分页边框
                if (info.getIsPagingBorder()) {
                    // 重置是否上边框
                    info.setIsBorderTop(Boolean.TRUE);
                    // 重置是否下边框
                    info.setIsBorderBottom(Boolean.TRUE);
                } else {
                    // 是否分页
                    if (info.isPaging()) {
                        // 重置是否上边框
                        info.setIsBorderTop(Boolean.FALSE);
                    }
                    // 重置是否下边框
                    info.setIsBorderBottom(Boolean.FALSE);
                }
                // 绘制边框
                BorderUtil.drawBorderWithBase(
                        info,
                        new PDRectangle(
                                info.getBeginX(),
                                info.getBeginY() - info.getHeight(),
                                info.getWidth(),
                                info.getHeight()
                        )
                );
                // 重置是否上边框
                info.setIsBorderTop(isBorderTop);
                // 重置是否下边框
                info.setIsBorderBottom(isBorderBottom);
                // 重置高度
                info.setHeight(height - info.getHeight());
                // 分页次数累计
                info.pagingCount();
            }
        }

        /**
         * 分页之后
         *
         * @param component 当前组件
         */
        @Override
        public void after(Component component) {
            // 获取上下文
            Context context = component.getContext();
            // 重置Y轴起始坐标
            Optional.ofNullable(context.getContainerInfo()).ifPresent(info -> info.setBeginY(context.getCursor().getY()));
        }
    }
}
