package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.AbstractPagingEvent;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.util.BorderUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    }

    /**
     * 虚拟渲染
     */
    @Override
    @SneakyThrows
    public void virtualRender() {
        this.processRender(new DefaultContainerVirtualPagingEvent(), this::virtualRenderComponent, this::addVirtualBorder);
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        this.processRender(new DefaultContainerPagingEvent(), this::renderComponent, this::addBorder);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 重置容器信息
        this.getContext().setBorderInfo(null);
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
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化其他
     */
    protected void initOthers() {
        // 检查换行
        this.checkWrap(this.getHeight());
        // 初始化是否分页边框
        if (Objects.isNull(this.getIsPagingBorder())) {
            this.setIsPagingBorder(Boolean.FALSE);
        }
        // 初始化起始X轴坐标
        this.initBeginX(this.getWidth());
        // 初始化容器信息
        this.getContext().setBorderInfo(new BorderInfo(this, this.getBorderConfiguration(), this.getWidth(), this.getHeight(), this.getBeginX(), this.getBeginY(), this.getPagingEvent(), this.getIsPagingBorder()));
        // 初始化换行宽度
        this.getContext().resetWrapWidth(this.getWidth());
        // 重置X轴起始坐标
        this.getContext().getCursor().setX(this.getBeginX());
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getWidth();
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
        this.getContext().getBorderInfo().setIsFirstComponent(Boolean.FALSE);
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
        this.getContext().getBorderInfo().setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 添加虚拟边框
     *
     * @param beforeY 组件渲染前Y轴坐标
     * @param info    容器信息
     */
    @SneakyThrows
    protected void addVirtualBorder(Float beforeY, BorderInfo info) {
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
        if (this.isPaging(this, beforeY - info.getHeight())) {
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
    protected void addBorder(Float beforeY, BorderInfo info) {
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
        if (this.isPaging(this, beforeY - info.getHeight())) {
            // 递归添加边框
            this.addBorder(info.getBeginY(), info);
        } else {
            // 创建尺寸
            PDRectangle rectangle = new PDRectangle(
                    info.getBeginX(),
                    info.getBeginY() - info.getHeight(),
                    info.getWidth(),
                    info.getHeight()
            );
            // 绘制边框
            BorderUtil.drawBorderWithData(info, rectangle);
            // 重置光标
            this.getContext().getCursor().setY(beforeY - info.getHeight());
        }
    }

    /**
     * 处理渲染
     *
     * @param pagingEvent    分页事件
     * @param consumer       消费者
     * @param borderConsumer 边框消费者
     */
    protected void processRender(PagingEvent pagingEvent, Consumer<Component> consumer, BiConsumer<Float, BorderInfo> borderConsumer) {
        // 初始化
        this.init();
        // 重置分页事件
        if (Objects.isNull(this.getPagingEvent())) {
            this.setPagingEvent(pagingEvent);
        }
        // 初始化其他
        this.initOthers();
        // 渲染之前X轴坐标
        Float beforeX = this.getBeginX();
        // 渲染之前Y轴坐标
        Float beforeY = this.getContext().getCursor().getY();
        // 渲染组件
        Optional.ofNullable(this.getComponents()).orElse(Collections.emptyList()).forEach(consumer);
        // 添加边框
        // borderConsumer.accept(beforeY, this.getContext().getBorderInfo());
        // 重置
        this.reset(beforeX);
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
            Context context = component.getContext();
            BorderInfo info = context.getBorderInfo();
            // 存在容器信息
            if (Objects.nonNull(info)) {
                Float height = info.getHeight();
                Float beginY = info.getBeginY();
                if (beginY < 0) {
                    beginY = height + beginY;
                }
                if (beginY - component.getBottom() < 0) {
                    beginY = component.getBottom() - beginY;
                    info.setHeight(beginY);
                } else {
                    info.setHeight(beginY - component.getBottom());
                }
                info.setHeight(height - info.getHeight());
                info.pagingCount();
            }
        }
    }

    /**
     * 默认容器分页事件
     */
    public static class DefaultContainerPagingEvent extends AbstractPagingEvent {

    }
}
