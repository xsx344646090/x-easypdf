package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
     * 组件列表
     */
    protected List<Component> components;
    /**
     * 分页事件
     */
    protected PagingEvent pagingEvent;
    /**
     * 是否整体换行
     */
    protected Boolean isTogether;
    /**
     * 是否分页边框
     */
    protected Boolean isPagingBorder;
    /**
     * 内容上边距
     */
    protected Float contentMarginTop;
    /**
     * 内容下边距
     */
    protected Float contentMarginBottom;
    /**
     * 内容左边距
     */
    protected Float contentMarginLeft;
    /**
     * 内容右边距
     */
    protected Float contentMarginRight;
    /**
     * 内容水平对齐方式
     */
    protected HorizontalAlignment contentHorizontalAlignment;
    /**
     * 内容垂直对齐方式
     */
    protected VerticalAlignment contentVerticalAlignment;
    
    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Container(Page page) {
        super(page);
    }
    
    /**
     * 设置内容边距（上下左右）
     *
     * @param margin 边距
     */
    public void setContentMargin(float margin) {
        this.contentMarginTop = margin;
        this.contentMarginBottom = margin;
        this.contentMarginLeft = margin;
        this.contentMarginRight = margin;
    }
    
    /**
     * 设置宽度
     *
     * @param width 宽度
     */
    public void setWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the width can not be less than 1");
        }
        this.width = width;
    }
    
    /**
     * 设置高度
     *
     * @param height 高度
     */
    public void setHeight(float height) {
        if (height < 1) {
            throw new IllegalArgumentException("the height can not be less than 1");
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
     * 设置
     *
     * @param components 组件
     */
    public void setComponents(Component... components) {
        if (Objects.nonNull(components)) {
            this.components = new ArrayList<>(Arrays.asList(components));
        } else {
            this.components = null;
        }
    }
    
    /**
     * 添加组件
     *
     * @param components 组件
     */
    public void addComponents(List<Component> components) {
        if (Objects.nonNull(components)) {
            if (Objects.isNull(this.components)) {
                this.components = new ArrayList<>(components);
            } else {
                this.components.addAll(components);
            }
        }
    }
    
    /**
     * 添加组件
     *
     * @param components 组件
     */
    public void addComponents(Component... components) {
        if (Objects.nonNull(components)) {
            if (Objects.isNull(this.components)) {
                this.components = new ArrayList<>(components.length);
            }
            Collections.addAll(this.components, components);
        }
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
        Objects.requireNonNull(this.height, "the height can not be null");
        // 初始化
        super.init();
        // 初始化背景颜色
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = this.getPage().getBackgroundColor();
        }
        // 初始化内容上边距
        if (Objects.isNull(this.contentMarginTop)) {
            this.contentMarginTop = 0F;
        }
        // 初始化内容下边距
        if (Objects.isNull(this.contentMarginBottom)) {
            this.contentMarginBottom = 0F;
        }
        // 初始化内容左边距
        if (Objects.isNull(this.contentMarginLeft)) {
            this.contentMarginLeft = 0F;
        }
        // 初始化内容右边距
        if (Objects.isNull(this.contentMarginRight)) {
            this.contentMarginRight = 0F;
        }
        // 初始化分页事件
        this.pagingEvent = new DefaultContainerPagingEvent();
        // 初始化是否整体换行
        if (Objects.isNull(this.isTogether)) {
            this.isTogether = Boolean.FALSE;
        }
        // 初始化是否分页边框
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = Boolean.FALSE;
        }
        // 初始化内容水平对齐方式
        if (Objects.isNull(this.contentHorizontalAlignment)) {
            this.contentHorizontalAlignment = HorizontalAlignment.LEFT;
        }
        // 初始化内容垂直对齐方式
        if (Objects.isNull(this.contentVerticalAlignment)) {
            this.contentVerticalAlignment = VerticalAlignment.TOP;
        }
        // 检查换行
        this.checkWrap(this.height);
        // 检查分页
        if (this.checkPaging()) {
            this.setIsWrap(true);
            this.wrap(this.height);
        }
        // 初始化起始X轴坐标
        this.initBeginX(this.width);
        // 初始化起始Y轴坐标
        this.initBeginY(this.height);
        // 初始化边框信息
        this.getContext().setBorderInfo(
                new BorderInfo(
                        this,
                        this.getBorderConfiguration(),
                        this.getBackgroundColor(),
                        this.getWidth(),
                        this.getHeight(),
                        this.getBeginX(),
                        this.getBeginY(),
                        this.getPagingEvent(),
                        this.getIsPagingBorder()
                )
        );
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
     * 是否需要换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    @Override
    protected boolean isNeedWrap() {
        return this.getContext().getWrapWidth() - (this.getBeginX() - this.getContext().getWrapBeginX()) < this.getMinWidth();
    }
    
    /**
     * 写入内容
     */
    @Override
    protected void writeContents() {
        // 获取起始X坐标
        float beginX = this.getBeginX() + this.getRelativeBeginX();
        // 获取起始Y坐标
        float beginY = this.getBeginY() + this.getRelativeBeginY();
        // 获取换行宽度
        float wrapWidth = this.getWidth() - this.getContentMarginLeft() - this.getContentMarginRight();
        // 获取边框信息
        BorderInfo borderInfo = this.getContext().getBorderInfo();
        // 获取页面
        Page page = this.getPage();
        // 获取上下文
        Context context = page.getContext();
        // 设置页面
        context.setPage(page);
        // 重置光标位置
        context.getCursor().reset(beginX, beginY);
        // 添加边框
        float tempY = this.addBorder(beginX, beginY - borderInfo.getHeight(), borderInfo);
        // 获取页面
        Page tempPage = context.getPage();
        // 如果非虚拟渲染，且有组件
        if (!context.getIsVirtualRender() && Objects.nonNull(this.getComponents())) {
            // 设置页面
            context.setPage(page);
            // 设置是否是第一个组件
            context.setIsFirstComponent(true);
            // 设置高度
            context.setHeight(this.getHeight());
            // 重置光标位置
            context.getCursor().reset(beginX + this.getContentMarginLeft(), beginY - this.getContentMarginTop());
            // 遍历组件
            for (Component component : this.getComponents()) {
                // 设置换行X轴起始坐标
                context.setWrapBeginX(beginX + this.getContentMarginLeft());
                // 设置换行宽度
                context.setWrapWidth(wrapWidth);
                // 设置水平对齐方式
                component.setHorizontalAlignment(this.getContentHorizontalAlignment());
                // 设置垂直对齐方式
                component.setVerticalAlignment(this.getContentVerticalAlignment());
                // 渲染组件
                component.render();
            }
        }
        // 设置页面
        context.setPage(tempPage);
        // 重置光标
        context.resetCursorY(tempY);
    }
    
    /**
     * 重置
     */
    @Override
    protected void reset() {
        float x = this.getContext().getPage().getMarginLeft();
        float y = this.getContext().getCursor().getY();
        super.reset(this.getType(), x, y);
        // 重置换行起始坐标
        this.getContext().resetWrapWidth(null);
        this.getContext().resetHeight(null);
    }
    
    /**
     * 执行分页
     */
    @Override
    protected Page executeBreak() {
        // 定义页面
        AtomicReference<Page> page = new AtomicReference<>();
        // 获取开始X坐标
        Float beginX = this.getBeginX();
        // 获取开始Y坐标
        Float beginY = this.getBeginY();
        // 获取上下文
        Context context = this.getContext();
        // 非手动分页
        if (!context.getIsManualBreak()) {
            Optional.ofNullable(context.getBorderInfo()).map(BorderInfo::getPagingEvent).ifPresent(event -> {
                event.before(this);
                page.set(super.executeBreak());
                event.after(this);
            });
        } else {
            page.set(super.executeBreak());
        }
        // 设置开始X轴坐标
        this.setBeginX(beginX);
        // 设置开始Y轴坐标
        this.setBeginY(beginY);
        // 返回分页结果
        return page.get();
    }
    
    /**
     * 添加边框
     *
     * @param beginX 组件渲染前X轴坐标
     * @param beginY 组件渲染前Y轴坐标
     * @param info   容器信息
     */
    @SneakyThrows
    protected float addBorder(Float beginX, Float beginY, BorderInfo info) {
        // 重置X轴坐标
        info.setBeginX(beginX);
        // 是否分页
        if (info.isPaging()) {
            // 非分页边框
            if (!this.getIsPagingBorder()) {
                // 重置是否上边框
                info.setIsBorderTop(Boolean.FALSE);
            }
        }
        // 需要分页
        if (this.isPaging(this, beginY)) {
            // 递归添加边框
            return this.addBorder(beginX, info.getBeginY() - info.getHeight(), info);
        } else {
            // 创建尺寸
            PDRectangle rectangle = new PDRectangle(
                    info.getBeginX(),
                    info.getBeginY() - info.getHeight(),
                    info.getWidth(),
                    info.getHeight()
            );
            // 非虚拟渲染
            if (!this.getContext().getIsVirtualRender()) {
                // 添加背景颜色
                CommonUtil.addBackgroundColor(info.getContext(), info.getContentMode(), info.getIsResetContentStream(), rectangle, info.getBackgroundColor());
                // 绘制边框
                BorderUtil.drawBorderWithData(info, rectangle);
            }
            // 返回Y轴起始坐标
            return rectangle.getLowerLeftY();
        }
    }
}
