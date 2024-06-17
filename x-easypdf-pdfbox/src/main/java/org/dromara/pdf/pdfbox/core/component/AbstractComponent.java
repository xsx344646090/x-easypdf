package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.AbstractBase;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingCondition;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.enums.BorderStyle;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;

import java.awt.*;
import java.io.Closeable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    protected Set<PagingEvent> pagingEvents;
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
     * 是否自定义X轴坐标
     */
    protected Boolean isCustomX;
    /**
     * 是否自定义Y轴坐标
     */
    protected Boolean isCustomY;
    /**
     * 是否换行
     */
    private Boolean isWrap;
    /**
     * 是否分页
     */
    private Boolean isBreak;
    /**
     * 水平对齐方式
     */
    protected HorizontalAlignment horizontalAlignment;
    /**
     * 垂直对齐方式
     */
    protected VerticalAlignment verticalAlignment;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public AbstractComponent(Page page) {
        this(page, true);
    }

    /**
     * 无参构造
     */
    protected AbstractComponent() {
    }

    /**
     * 有参构造
     *
     * @param component 组件
     */
    protected AbstractComponent(AbstractComponent component) {
        super.init(component);
        this.marginConfiguration = new MarginConfiguration(component.getMarginConfiguration());
        this.borderConfiguration = new BorderConfiguration(component.getBorderConfiguration());
        this.pagingEvents = component.getPagingEvents();
    }

    /**
     * 有参构造
     *
     * @param page 页面
     */
    protected AbstractComponent(Page page, boolean isResetPage) {
        Page lastPage;
        if (isResetPage) {
            lastPage = page.getLastPage();
        } else {
            lastPage = page;
        }
        super.init(lastPage);
        this.context.reset(lastPage);
        this.marginConfiguration = new MarginConfiguration();
        this.borderConfiguration = new BorderConfiguration();
        this.pagingEvents = new HashSet<>();
    }

    /**
     * 检查换行
     */
    protected void checkWrap() {

    }

    /**
     * 设置自定义X轴坐标
     *
     * @param beginX X轴坐标
     */
    public void setBeginX(Float beginX) {
        this.beginX = beginX;
        this.isCustomX = Objects.nonNull(beginX);
    }

    /**
     * 设置自定义Y轴坐标
     *
     * @param beginY Y轴坐标
     */
    public void setBeginY(Float beginY) {
        this.beginY = beginY;
        this.isCustomY = Objects.nonNull(beginY);
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
    public void setBorderStyle(BorderStyle style) {
        this.borderConfiguration.setBorderStyle(style);
    }

    /**
     * 设置边框线宽
     *
     * @param width 线宽
     */
    public void setBorderWidth(float width) {
        this.borderConfiguration.setBorderWidth(width);
    }

    /**
     * 设置边框点线长度
     *
     * @param length 长度
     */
    public void setBorderLineLength(float length) {
        this.borderConfiguration.setBorderLineLength(length);
    }

    /**
     * 设置边框点线间隔
     *
     * @param spacing 间隔
     */
    public void setBorderLineSpacing(float spacing) {
        this.borderConfiguration.setBorderLineSpacing(spacing);
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
     * @param flag 是否边框
     */
    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
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
    public BorderStyle getBorderStyle() {
        return this.borderConfiguration.getBorderStyle();
    }

    /**
     * 获取边框线宽
     *
     * @return 返回边框线宽
     */
    public Float getBorderWidth() {
        return this.borderConfiguration.getBorderWidth();
    }

    /**
     * 获取边框点线长度
     *
     * @return 返回边框点线长度
     */
    public Float getBorderLineLength() {
        return this.borderConfiguration.getBorderLineLength();
    }

    /**
     * 获取边框点线间隔
     *
     * @return 返回边框点线间隔
     */
    public Float getBorderLineSpacing() {
        return this.borderConfiguration.getBorderLineSpacing();
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
                this.getPagingEvents().add(this.getContext().getPageHeader().getPagingEvent());
            }
        }
        // 初始化页脚分页事件
        if (Objects.nonNull(this.getContext().getPageFooter())) {
            if (this.getContext().getExecutingComponentType().isNotPageHeaderOrFooter()) {
                this.getPagingEvents().add(this.getContext().getPageFooter().getPagingEvent());
            }
        }
        // 初始化水平对齐方式
        if (Objects.isNull(this.getHorizontalAlignment())) {
            this.setHorizontalAlignment(this.getPage().getHorizontalAlignment());
        }
        // 初始化垂直对齐方式
        if (Objects.isNull(this.getVerticalAlignment())) {
            this.setVerticalAlignment(this.getPage().getVerticalAlignment());
        }
        // 初始化是否自定义X轴坐标
        if (Objects.isNull(this.getIsCustomX())) {
            this.setIsCustomX(Boolean.FALSE);
        }
        // 初始化是否自定义Y轴坐标
        if (Objects.isNull(this.getIsCustomY())) {
            this.setIsCustomY(Boolean.FALSE);
        }
        // 初始化是否分页
        if (Objects.isNull(this.getIsBreak())) {
            this.setIsBreak(Boolean.FALSE);
        }
        // 初始化是否换行
        if (Objects.isNull(this.getIsWrap())) {
            this.setIsWrap(Boolean.FALSE);
        }
        // 初始化分页
        if (this.getIsBreak()) {
            this.processBreak();
        }
        // 初始化相对起始X轴坐标
        if (Objects.isNull(this.getRelativeBeginX())) {
            this.setRelativeBeginX(0F);
        }
        // 初始化相对起始Y轴坐标
        if (Objects.isNull(this.getRelativeBeginY())) {
            this.setRelativeBeginY(0F);
        }
    }

    /**
     * 设置X轴坐标
     *
     * @param beginX    X轴坐标
     * @param isCustomX 是否自定义
     */
    protected void setBeginX(Float beginX, boolean isCustomX) {
        this.beginX = beginX;
        this.isCustomX = isCustomX;
    }

    /**
     * 设置Y轴坐标
     *
     * @param beginY    Y轴坐标
     * @param isCustomY 是否自定义
     */
    protected void setBeginY(Float beginY, boolean isCustomY) {
        this.beginY = beginY;
        this.isCustomY = isCustomY;
    }

    /**
     * 初始化起始X轴坐标
     *
     * @param width 宽度
     */
    protected void initBeginX(float width) {
        // 匹配水平对齐方式
        switch (this.getHorizontalAlignment()) {
            // 居中
            case CENTER: {
                // 获取偏移量
                float offset = (this.getContext().getWrapWidth() + this.getContext().getWrapBeginX() - this.getBeginX() - width) / 2;
                // 设置起始X轴坐标
                this.setBeginX(this.getBeginX() + offset);
                // 结束
                break;
            }
            // 居右
            case RIGHT: {
                // 获取偏移量
                float offset = this.getContext().getWrapWidth() - width - this.getMarginRight();
                // 设置起始X轴坐标
                this.setBeginX(offset);
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
     * 换行
     */
    protected void wrap() {
        // 设置X轴起始坐标
        this.setBeginX(null);
        // 设置Y轴起始坐标
        this.setBeginY(null);
        // 设置是否换行
        this.setIsWrap(Boolean.TRUE);
        // 检查换行
        this.checkWrap();
        // 设置是否换行
        this.setIsWrap(Boolean.FALSE);
    }

    /**
     * 是否换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isWrap() {
        // 是否手动设置
        if (this.getIsWrap()) {
            return Boolean.TRUE;
        }
        // 换行宽度或X轴起始坐标未初始化
        if (Objects.isNull(this.getContext().getWrapWidth()) || Objects.isNull(this.getBeginX())) {
            return Boolean.FALSE;
        }
        return this.isNeedWrap();
    }

    /**
     * 是否需要换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean isNeedWrap() {
        return this.getContext().getWrapWidth() + this.getContext().getWrapBeginX() - this.getBeginX() < 0;
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
        boolean flag = this.checkPaging(component, beginY);
        // 分页
        if (flag) {
            this.paging();
        }
        // 返回分页标识
        return flag;
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
     * 执行分页
     */
    protected void paging() {
        // 处理分页
        this.processBreak();
        // 换行
        this.wrap();
    }

    /**
     * 重置
     */
    protected void reset(ComponentType type) {
        // 重置X轴起始坐标
        this.beginX = null;
        // 重置Y轴起始坐标
        this.beginY = null;
        // 重置是否自定义X轴坐标
        this.isCustomX = null;
        // 重置是否自定义Y轴坐标
        this.isCustomY = null;
        // 重置换行起始坐标
        this.getContext().resetWrapBeginX(null);
        // 重置当前执行组件类型
        this.getContext().resetExecutingComponentType(type);
        // 重置是否第一个组件
        this.getContext().setIsFirstComponent(Boolean.FALSE);
    }

    /**
     * 检查分页
     *
     * @param beginY Y轴起始坐标
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean checkPaging(float beginY) {
        // Y轴坐标小于下边距+页面下边距
        if (beginY < this.getMarginBottom() + this.getBottom()) {
            // 返回true
            return true;
        }
        // 返回false
        return false;
    }

    /**
     * 处理分页
     */
    protected void processBreak() {
        Optional.ofNullable(this.pagingEvents).ifPresent(events -> events.forEach(event -> event.before(this)));
        this.executeBreak();
        Optional.ofNullable(this.pagingEvents).ifPresent(events -> events.forEach(event -> event.after(this)));
    }

    /**
     * 执行分页
     */
    protected Page executeBreak() {
        Page subPage = this.getContext().getPage().getSubPage();
        if (Objects.isNull(subPage)) {
            if (!this.getContext().getIsAlreadyPaging()) {
                subPage = this.getContext().getPage().createSubPage();
            }
        } else {
            this.getContext().setPage(subPage);
            this.getContext().resetCursor();
        }
        this.resetXY();
        super.init(this.getContext().getPage());
        return subPage;
    }

    /**
     * 重置起始XY轴坐标
     */
    protected void resetXY() {
        this.beginX = null;
        this.beginY = null;
    }
}
