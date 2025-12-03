package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.enums.*;

import java.awt.*;
import java.io.Closeable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;

/**
 * 抽象组件
 *
 * @author xsx
 * @date 2023/9/4
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
public abstract class AbstractComponent extends AbstractBase implements Component, Closeable {

    /**
     * 边距配置
     */
    protected MarginConfiguration marginConfiguration;
    /**
     * 边框配置
     */
    protected BorderConfiguration borderConfiguration;
    /**
     * 分页事件列表
     */
    protected LinkedHashSet<PagingEvent> pagingEvents;
    /**
     * 自定义分页条件
     */
    protected PagingCondition pagingCondition;
    /**
     * 自定义起始X轴坐标
     */
    protected Float beginX;
    /**
     * 自定义起始Y轴坐标
     */
    protected Float beginY;
    /**
     * X轴相对坐标（不影响后续坐标）
     */
    protected Float relativeBeginX;
    /**
     * Y轴相对坐标（不影响后续坐标）
     */
    protected Float relativeBeginY;
    /**
     * 是否换行
     */
    protected Boolean isWrap;
    /**
     * 是否分页
     */
    protected Boolean isBreak;
    /**
     * 是否自定义X坐标
     */
    protected Boolean isCustomX;
    /**
     * 是否自定义Y坐标
     */
    protected Boolean isCustomY;
    /**
     * 水平对齐方式
     */
    protected HorizontalAlignment horizontalAlignment;
    /**
     * 垂直对齐方式
     */
    protected VerticalAlignment verticalAlignment;

    /**
     * 无参构造
     */
    protected AbstractComponent() {

    }

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public AbstractComponent(Page page) {
        Page lastPage = page.getLastPage();
        super.init(lastPage);
        this.context.reset(lastPage);
        this.marginConfiguration = new MarginConfiguration();
        this.borderConfiguration = new BorderConfiguration();
        this.pagingEvents = new LinkedHashSet<>();
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    protected abstract float getMinWidth();

    /**
     * 写入内容
     */
    protected abstract void writeContents();

    /**
     * 重置
     */
    protected abstract void reset();

    /**
     * 设置自定义起始X轴坐标
     *
     * @param x 起始X轴坐标
     */
    public void setBeginX(Float x) {
        this.setBeginX(x, true);
    }

    /**
     * 设置自定义起始Y轴坐标
     *
     * @param y 起始X轴坐标
     */
    public void setBeginY(Float y) {
        this.setBeginY(y, true);
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.setMarginTop(margin);
        this.setMarginBottom(margin);
        this.setMarginLeft(margin);
        this.setMarginRight(margin);
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
     * 设置是否边框（上下左右）
     *
     * @param flag 是否边框
     */
    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
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
     * 设置上边距
     *
     * @param margin 边距
     */
    public void setMarginTop(float margin) {
        // 设置上边距
        this.marginConfiguration.setMarginTop(margin);
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
     * 设置下边距
     *
     * @param margin 边距
     */
    public void setMarginBottom(float margin) {
        this.marginConfiguration.setMarginBottom(margin);
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
     * 设置左边距
     *
     * @param margin 边距
     */
    public void setMarginLeft(float margin) {
        this.marginConfiguration.setMarginLeft(margin);
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
     * 设置右边距
     *
     * @param margin 边距
     */
    public void setMarginRight(float margin) {
        this.marginConfiguration.setMarginRight(margin);
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
     * 设置边框样式
     *
     * @param style 样式
     */
    public void setBorderLineStyle(LineStyle style) {
        this.borderConfiguration.setBorderLineStyle(style);
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
     * 设置线帽样式
     *
     * @param style 样式
     */
    public void setBorderLineCapStyle(LineCapStyle style) {
        this.borderConfiguration.setBorderLineCapStyle(style);
    }

    /**
     * 设置圆角半径
     *
     * @param radius 半径
     */
    public void setBorderRadius(float radius) {
        this.borderConfiguration.setBorderRadius(radius);
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
     * 设置边框线长
     *
     * @param length 线长
     */
    public void setBorderLineLength(float length) {
        this.borderConfiguration.setBorderLineLength(length);
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
     * 设置边框线宽
     *
     * @param width 线宽
     */
    public void setBorderLineWidth(float width) {
        this.borderConfiguration.setBorderLineWidth(width);
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
     * 设置边框点线间隔
     *
     * @param spacing 间隔
     */
    public void setBorderDottedSpacing(float spacing) {
        this.borderConfiguration.setBorderDottedSpacing(spacing);
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
     * 设置上边框颜色
     *
     * @param color 颜色
     */
    public void setBorderTopColor(Color color) {
        this.borderConfiguration.setBorderTopColor(color);
    }

    /**
     * 获取上边框宽度
     *
     * @return 返回上边框宽度
     */
    public Float getBorderTopLineWidth() {
        return this.borderConfiguration.getBorderTopLineWidth();
    }

    /**
     * 设置上边框宽度
     *
     * @param width 宽度
     */
    public void setBorderTopLineWidth(float width) {
        this.borderConfiguration.setBorderTopLineWidth(width);
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
     * 设置下边框颜色
     *
     * @param color 颜色
     */
    public void setBorderBottomColor(Color color) {
        this.borderConfiguration.setBorderBottomColor(color);
    }

    /**
     * 获取下边框宽度
     *
     * @return 返回下边框宽度
     */
    public Float getBorderBottomLineWidth() {
        return this.borderConfiguration.getBorderBottomLineWidth();
    }

    /**
     * 设置下边框宽度
     *
     * @param width 宽度
     */
    public void setBorderBottomLineWidth(float width) {
        this.borderConfiguration.setBorderBottomLineWidth(width);
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
     * 设置左边框颜色
     *
     * @param color 颜色
     */
    public void setBorderLeftColor(Color color) {
        this.borderConfiguration.setBorderLeftColor(color);
    }

    /**
     * 获取左边框宽度
     *
     * @return 返回左边框宽度
     */
    public Float getBorderLeftLineWidth() {
        return this.borderConfiguration.getBorderLeftLineWidth();
    }

    /**
     * 设置左边框宽度
     *
     * @param width 宽度
     */
    public void setBorderLeftLineWidth(float width) {
        this.borderConfiguration.setBorderLeftLineWidth(width);
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
     * 设置右边框颜色
     *
     * @param color 颜色
     */
    public void setBorderRightColor(Color color) {
        this.borderConfiguration.setBorderRightColor(color);
    }

    /**
     * 获取右边框宽度
     *
     * @return 返回右边框宽度
     */
    public Float getBorderRightLineWidth() {
        return this.borderConfiguration.getBorderRightLineWidth();
    }

    /**
     * 设置右边框宽度
     *
     * @param width 宽度
     */
    public void setBorderRightLineWidth(float width) {
        this.borderConfiguration.setBorderRightLineWidth(width);
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
     * 设置是否上边框
     *
     * @param flag 是否上边框
     */
    public void setIsBorderTop(boolean flag) {
        this.borderConfiguration.setIsBorderTop(flag);
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
     * 设置是否下边框
     *
     * @param flag 是否下边框
     */
    public void setIsBorderBottom(boolean flag) {
        this.borderConfiguration.setIsBorderBottom(flag);
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
     * 设置是否左边框
     *
     * @param flag 是否左边框
     */
    public void setIsBorderLeft(boolean flag) {
        this.borderConfiguration.setIsBorderLeft(flag);
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
     * 设置是否右边框
     *
     * @param flag 是否右边框
     */
    public void setIsBorderRight(boolean flag) {
        this.borderConfiguration.setIsBorderRight(flag);
    }

    /**
     * 获取基类
     *
     * @return 返回当前对象
     */
    @Override
    public AbstractBase getBase() {
        return this;
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
     * 获取下边距
     *
     * @return 返回下边距
     */
    public float getBottom() {
        return this.getMarginBottom() + this.getContext().getPageFooterHeight() + this.getPage().getMarginBottom();
    }

    /**
     * 添加分页事件
     *
     * @param event 事件
     */
    public void addPagingEvent(PagingEvent event) {
        this.pagingEvents.add(event);
    }

    /**
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        // 初始化
        this.init();
        // 重置是否虚拟渲染
        this.getContext().resetIsVirtualRender(true);
        // 写入内容
        this.writeContents();
        // 重置
        this.reset();
    }

    /**
     * 渲染
     */
    @Override
    public void render() {
        // 初始化
        this.init();
        // 重置是否虚拟渲染
        this.getContext().resetIsVirtualRender(false);
        // 写入内容
        this.writeContents();
        // 重置
        this.reset();
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        super.setContext(null);
    }

    /**
     * 初始化
     */
    protected void init() {
        // 初始化当前执行组件类型
        if (Objects.isNull(this.getContext().getExecutingComponentType())) {
            this.getContext().setExecutingComponentType(this.getType());
        }
        // 初始化页眉分页事件
        if (Objects.nonNull(this.getContext().getPageHeader())) {
            if (this.getContext().getExecutingComponentType().isNotPageHeaderOrFooter()) {
                LinkedHashSet<PagingEvent> events = new LinkedHashSet<>();
                events.add(this.getContext().getPageHeader().getPagingEvent());
                events.addAll(this.pagingEvents);
                this.pagingEvents = events;
            }
        }
        // 初始化页脚分页事件
        if (Objects.nonNull(this.getContext().getPageFooter())) {
            if (this.getContext().getExecutingComponentType().isNotPageHeaderOrFooter()) {
                this.pagingEvents.add(this.getContext().getPageFooter().getPagingEvent());
            }
        }
        // 初始化水平对齐方式
        if (Objects.isNull(this.horizontalAlignment)) {
            this.horizontalAlignment = this.getPage().getHorizontalAlignment();
        }
        // 初始化垂直对齐方式
        if (Objects.isNull(this.verticalAlignment)) {
            this.verticalAlignment = this.getPage().getVerticalAlignment();
        }
        // 初始化是否换行
        if (Objects.isNull(this.isWrap)) {
            this.isWrap = Boolean.FALSE;
        }
        // 初始化是否分页
        if (Objects.isNull(this.isBreak)) {
            this.isBreak = Boolean.FALSE;
        }
        // 初始化是否自定义X坐标
        if (Objects.isNull(this.isCustomX)) {
            this.isCustomX = Boolean.FALSE;
        }
        // 初始化是否自定义Y坐标
        if (Objects.isNull(this.isCustomY)) {
            this.isCustomY = Boolean.FALSE;
        }
        // 初始化相对起始X轴坐标
        if (Objects.isNull(this.relativeBeginX)) {
            this.relativeBeginX = 0F;
        }
        // 初始化相对起始Y轴坐标
        if (Objects.isNull(this.relativeBeginY)) {
            this.relativeBeginY = 0F;
        }
    }

    /**
     * 初始化起始X轴坐标
     *
     * @param width 宽度
     */
    protected void initBeginX(float width) {
        // 跳过
        if (width == 0) {
            return;
        }
        // 匹配水平对齐方式
        switch (this.getHorizontalAlignment()) {
            // 居中
            case CENTER: {
                // 获取偏移量
                float offset = (this.getContext().getWrapWidth() - width) / 2;
                // 设置起始X轴坐标
                this.setBeginX(this.getBeginX() + offset, this.getIsCustomX());
                // 结束
                break;
            }
            // 居右
            case RIGHT: {
                // 获取偏移量
                float offset = this.getContext().getWrapWidth() - width - this.getMarginRight();
                // 设置起始X轴坐标
                this.setBeginX(Math.min(this.getBeginX() + offset, this.getContext().getMaxBeginX() - width), this.getIsCustomX());
                // 结束
                break;
            }
            // 居左
            default: {
                // nothing to do
            }
        }
    }

    /**
     * 初始化起始Y轴坐标
     *
     * @param height 高度
     */
    protected void initBeginY(float height) {
        // 跳过
        if (height == 0) {
            return;
        }
        // 定义偏移量
        float offset = 0F;
        // 匹配垂直对齐方式
        switch (this.getVerticalAlignment()) {
            // 居中
            case CENTER: {
                // 获取偏移量
                offset = (this.getContext().getHeight() - height) / 2;
                // 结束
                break;
            }
            // 居下
            case BOTTOM: {
                // 获取偏移量
                offset = this.getContext().getHeight() - height - this.getMarginBottom();
                // 结束
                break;
            }
            // 居上
            default: {
                // nothing to do
            }
        }
        // 设置起始Y轴坐标
        this.setBeginY(this.getBeginY(this.getPage(), this.getBeginY() - offset), this.getIsCustomY());
    }

    /**
     * 获取起始Y轴坐标
     *
     * @param page   页面
     * @param beginY 起始Y轴坐标
     * @return 返回起始Y轴坐标
     */
    protected float getBeginY(Page page, float beginY) {
        return getBeginY(page, beginY, 0);
    }

    /**
     * 获取起始Y轴坐标
     *
     * @param page   页面
     * @param beginY 起始Y轴坐标
     * @param depth  递归深度
     * @return 返回起始Y轴坐标
     */
    protected float getBeginY(Page page, float beginY, int depth) {
        // 检查深度
        if (this.checkPageDepth(depth)) {
            // 检查起始Y轴坐标是否小于0
            if (beginY < 0) {
                // 获取子页面
                Page subPage = page.getSubPage();
                // 重置起始Y轴坐标
                beginY = beginY + subPage.getBodyHeight();
                // 检查起始Y轴坐标是否大于等于0
                if (beginY >= 0) {
                    // 设置当前页面为子页面并
                    this.getContext().setPage(subPage);
                    // 返回起始Y轴坐标
                    return beginY;
                }
                // 递归调用深度加1
                return getBeginY(subPage, beginY, depth + 1);
            } else {
                // 返回起始Y轴坐标
                return beginY;
            }
        }
        // 如果深度超过20000，直接返回beginY
        return beginY;
    }

    /**
     * 检查页面深度
     *
     * @param depth 页面深度
     * @return 返回是否分页
     */
    protected boolean checkPageDepth(int depth) {
        return depth < 1000;
    }

    /**
     * 初始化起始Y轴坐标
     *
     * @param height 高度
     */
    protected void initBeginYForPaging(float height) {
        this.initBeginY(height);
        float offsetY = this.getBeginY() + (height - this.getContext().getOffsetY());
        float maxBeginY = this.getContext().getMaxBeginY() - height;
        this.setBeginY(Math.min(maxBeginY, offsetY), this.getIsCustomY());
    }

    /**
     * 初始化起始XY轴坐标
     *
     * @param width  宽度
     * @param height 高度
     */
    protected void initBeginXY(float width, float height) {
        // 检查换行
        this.checkWrap(height);
        // 检查分页
        if (this.checkPaging()) {
            this.setIsWrap(true);
            this.wrap(height);
            this.initBeginYForPaging(height);
        } else {
            this.initBeginY(height);
        }
        // 初始化起始X轴坐标
        this.initBeginX(width);
    }

    /**
     * 检查换行
     */
    protected void checkWrap(float wrapHeight) {
        if (Objects.isNull(this.getIsCustomX())) {
            this.setIsCustomX(Boolean.FALSE);
        }
        if (Objects.isNull(this.getIsCustomY())) {
            this.setIsCustomY(Boolean.FALSE);
        }
        if (!this.getIsCustomY()) {
            this.wrap(wrapHeight);
        } else {
            if (Objects.isNull(this.getBeginX())) {
                this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), this.getIsCustomX());
            }
            if (Objects.isNull(this.getBeginY())) {
                this.setBeginY(this.getContext().getCursor().getY() - this.getMarginTop(), this.getIsCustomY());
            }
        }
    }

    /**
     * 换行
     *
     * @param wrapHeight 换行高度
     */
    protected void wrap(float wrapHeight) {
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), this.getIsCustomX());
        }
        if (this.isWrap()) {
            this.setIsWrap(false);
            this.getContext().resetCursor(
                    Optional.ofNullable(this.getContext().getWrapBeginX()).orElse(this.getPage().getMarginLeft()) + this.getMarginLeft(),
                    this.getContext().getCursor().getY() - wrapHeight - this.getMarginTop()
            );
            if (!this.getIsCustomX()) {
                this.setBeginX(this.getContext().getCursor().getX(), false);
            }
            this.setBeginY(this.getContext().getCursor().getY(), this.getIsCustomY());
        } else {
            this.setBeginY(this.getContext().getCursor().getY() - this.getMarginTop(), this.getIsCustomY());
        }
    }

    /**
     * 是否换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isWrap() {
        return this.getIsWrap() || this.isNeedWrap();
    }

    /**
     * 是否需要换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isNeedWrap() {
        boolean flag = this.getContext().getWrapWidth() - (this.getBeginX() - this.getContext().getWrapBeginX()) < this.getMinWidth();
        return this.getContext().getIsFirstComponent() || flag;
    }

    /**
     * 是否分页
     *
     * @param component 当前组件
     * @param beginY    Y轴起始坐标
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isPaging(Component component, float beginY) {
        // 获取分页标识
        boolean flag = this.getIsBreak() || this.checkPaging(component, beginY);
        // 分页
        if (flag) {
            this.processBreak();
        }
        // 返回分页标识
        return flag;
    }

    /**
     * 检查分页
     */
    protected boolean checkPaging() {
        // 非自定义坐标
        if (!(this.getIsCustomX() || this.getIsCustomY())) {
            // 检查分页
            return this.isPaging(this, this.getBeginY());
        }
        return false;
    }

    /**
     * 检查分页
     *
     * @param component 组件
     * @param beginY    Y轴起始坐标
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean checkPaging(Component component, float beginY) {
        return (
                this.checkPaging(beginY) ||
                        Optional.ofNullable(this.pagingCondition)
                                .map(condition -> condition.isPaging(component, beginY))
                                .orElse(false)
        ) && this.isPagingComponent();
    }

    /**
     * 检查分页
     *
     * @param beginY Y轴起始坐标
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean checkPaging(float beginY) {
        if (beginY < this.getBottom()) {
            this.getContext().resetOffsetY(this.getBottom() - beginY);
            return true;
        }
        return false;
    }

    /**
     * 是否分页组件
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isPagingComponent() {
        return Optional.ofNullable(this.getContext().getExecutingComponentType())
                .map(ComponentType::isNotPageHeaderOrFooter)
                .orElse(Boolean.FALSE);
    }

    /**
     * 处理分页
     */
    protected void processBreak() {
        Optional.ofNullable(this.pagingEvents).ifPresent(events -> events.forEach(event -> Optional.ofNullable(event).ifPresent(v -> v.before(this))));
        this.executeBreak();
        Optional.ofNullable(this.pagingEvents).ifPresent(events -> events.forEach(event -> Optional.ofNullable(event).ifPresent(v -> v.after(this))));
    }

    /**
     * 执行分页
     */
    protected Page executeBreak() {
        Page subPage = this.getPage().getSubPage();
        if (Objects.isNull(subPage)) {
            if (!this.getContext().getIsAlreadyPaging()) {
                subPage = this.getPage().createSubPage();
            }
        } else {
            this.getContext().setPage(subPage);
            this.getContext().resetCursor();
        }
        this.setIsBreak(false);
        this.resetXY();
        return subPage;
    }

    /**
     * 重置
     *
     * @param type 类型
     * @param x    x轴坐标
     * @param y    y轴坐标
     */
    protected void reset(ComponentType type, float x, float y) {
        // 重置起始XY轴坐标
        this.resetXY();
        // 获取上下文
        Context context = this.getContext();
        // 重置换行起始坐标
        context.resetWrapBeginX(null);
        // 重置光标
        context.resetCursor(x, y);
        // 重置当前执行组件类型
        context.resetExecutingComponentType(type);
        // 重置是否第一个组件
        context.setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 重置起始XY轴坐标
     */
    protected void resetXY() {
        this.beginX = null;
        this.beginY = null;
        this.isCustomX = null;
        this.isCustomY = null;
        ComponentType type = this.getContext().getExecutingComponentType();
        if (Objects.nonNull(type) && type.isNotPageHeaderOrFooter()) {
            this.relativeBeginX = 0F;
            this.relativeBeginY = 0F;
        }
    }

    /**
     * 设置自定义起始X轴坐标
     *
     * @param x                起始X轴坐标
     * @param isCustomPosition 是否自定义坐标
     */
    protected void setBeginX(Float x, Boolean isCustomPosition) {
        this.beginX = x;
        this.isCustomX = Optional.ofNullable(isCustomPosition).orElse(Boolean.FALSE);
    }

    /**
     * 设置自定义起始Y轴坐标
     *
     * @param y                起始X轴坐标
     * @param isCustomPosition 是否自定义坐标
     */
    protected void setBeginY(Float y, Boolean isCustomPosition) {
        this.beginY = y;
        this.isCustomY = Optional.ofNullable(isCustomPosition).orElse(Boolean.FALSE);
    }
}
