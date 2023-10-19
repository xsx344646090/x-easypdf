package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.*;
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
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
        return new ContainerPagingEvent();
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
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
     * 渲染
     */
    @Override
    public void render() {
        // 初始化
        this.init();
        // // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
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
    private void reset(Float beforeX) {
        // 重置X轴坐标
        this.getContext().getCursor().setX(beforeX + this.getWidth());
        // 重置换行宽度
        this.getContext().resetWrapWidth(null);
        // 重置换行高度
        this.getContext().resetWrapHeight(this.getContext().getContainerInfo().getHeight());
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化其他
     */
    private void initOthers() {
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
     * 渲染组件
     *
     * @param component 组件
     */
    private void renderComponent(Component component) {
        // 初始化X轴换行起始坐标
        this.getContext().setWrapBeginX(this.getBeginX());
        // 渲染组件
        component.render();
        // 重置是否第一个组件
        this.getContext().getContainerInfo().setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 添加边框
     *
     * @param beforeY 组件渲染前Y轴坐标
     * @param info    容器信息
     */
    @SneakyThrows
    private void addBorder(Float beforeY, ContainerInfo info) {
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
            // 获取高度
            float height = beforeY - this.getMarginBottom() - this.getContext().getPage().getMarginBottom();
            // 获取是否下边框
            Boolean isBorderBottom = info.getIsBorderBottom();
            // 非分页边框
            if (!this.getIsPagingBorder()) {
                // 重置是否下边框
                info.setIsBorderBottom(Boolean.FALSE);
            }
            // 绘制边框
            BorderUtil.drawBorderWithBase(
                    info,
                    new PDRectangle(
                            info.getBeginX(),
                            info.getBeginY() - height,
                            info.getWidth(),
                            height
                    )
            );
            // 重置是否下边框
            info.setIsBorderBottom(isBorderBottom);
            // 重置高度
            info.setHeight(info.getHeight() - height);
            // 分页次数累计
            info.pagingCount();
            // 分页
            this.paging();
            // 重置Y轴起始坐标
            info.setBeginY(this.getContext().getCursor().getY());
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
            // 重置游标
            this.getContext().getCursor().setY(beforeY - info.getHeight());
        }
    }

    /**
     * 容器分页事件
     */
    public static class ContainerPagingEvent extends AbstractPagingEvent {

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
                // 获取游标
                Cursor cursor = context.getCursor();
                // 获取高度
                Float height = info.getHeight();
                // 重置高度
                info.setHeight(info.getBeginY() - cursor.getY());
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
