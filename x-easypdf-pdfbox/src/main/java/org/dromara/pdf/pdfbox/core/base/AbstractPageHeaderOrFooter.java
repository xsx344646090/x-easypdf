package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.component.Component;
import org.dromara.pdf.pdfbox.core.enums.*;
import org.dromara.pdf.pdfbox.util.BorderUtil;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
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
    public AbstractPageHeaderOrFooter(Page page) {
        super.init(page);
        this.marginConfiguration = new MarginConfiguration();
        this.borderConfiguration = new BorderConfiguration();
        this.fontConfiguration = new FontConfiguration(page.getFontConfiguration());
        this.backgroundColor = page.getBackgroundColor();
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
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        this.fontConfiguration.setFontName(fontName);
        this.getContext().addFontCache(fontName);
    }

    /**
     * 设置特殊字体名称
     *
     * @param fontNames 字体名称
     */
    public void setSpecialFontNames(String... fontNames) {
        this.getContext().addFontCache(fontNames);
        Collections.addAll(this.fontConfiguration.getSpecialFontNames(), fontNames);
    }

    /**
     * 设置字体大小
     *
     * @param size 大小
     */
    public void setFontSize(float size) {
        this.fontConfiguration.setFontSize(size);
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色
     */
    public void setFontColor(Color color) {
        this.fontConfiguration.setFontColor(color);
    }

    /**
     * 设置字体透明度
     *
     * @param alpha 透明度
     */
    public void setFontAlpha(float alpha) {
        this.fontConfiguration.setFontAlpha(alpha);
    }

    /**
     * 设置字体样式
     *
     * @param style 样式
     */
    public void setFontStyle(FontStyle style) {
        this.fontConfiguration.setFontStyle(style);
    }

    /**
     * 设置字体斜率（斜体字）
     *
     * @param slope 斜率
     */
    public void setFontSlope(float slope) {
        this.fontConfiguration.setFontSlope(slope);
    }

    /**
     * 设置字符间距
     *
     * @param spacing 间距
     */
    public void setCharacterSpacing(float spacing) {
        this.fontConfiguration.setCharacterSpacing(spacing);
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     */
    public void setLeading(float leading) {
        this.fontConfiguration.setLeading(leading);
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
     * 设置水平对齐方式
     *
     * @param horizontalAlignment 水平对齐方式
     */
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        Objects.requireNonNull(horizontalAlignment, "the horizontal alignment can not be null");
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * 设置垂直对齐方式
     *
     * @param verticalAlignment 垂直对齐方式
     */
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        Objects.requireNonNull(verticalAlignment, "the vertical alignment can not be null");
        this.verticalAlignment = verticalAlignment;
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
     * 渲染前
     */
    public void renderBefore() {
        this.getContext().setIsFirstComponent(true);
    }

    /**
     * 虚拟渲染
     */
    @SneakyThrows
    public void virtualRender() {
        // 校验宽度
        Objects.requireNonNull(this.getWidth(), "the width can not be null");
        // 校验高度
        Objects.requireNonNull(this.getHeight(), "the height can not be null");
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
        // 校验高度
        Objects.requireNonNull(this.getHeight(), "the height can not be null");
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
        this.getContext().setIsFirstComponent(true);
        // 重置当前执行组件类型
        this.getContext().resetExecutingComponentType(this.getType());
        // 重置换行起始坐标
        this.getContext().resetWrapBeginX(null);
        // 重置换行宽度
        this.getContext().resetWrapWidth(null);
    }

    /**
     * 获取Y轴起始坐标偏移量
     *
     * @return 返回便宜量
     */
    public float getBeginYOffset() {
        // 定义偏移量
        float offset = 0F;
        // 组件存在
        if (!this.getComponents().isEmpty()) {
            // 获取第一个组件
            Component component = this.getComponents().get(0);
            // 匹配类型
            switch (component.getType()) {
                // 文本域
                case TEXTAREA: {
                    // offset = Optional.ofNullable(((Textarea) component).getFontSize()).orElse(this.getContext().getPage().getFontSize());
                    break;
                }
                // 自定义
                case CUSTOM: {
                    // TODO 待添加自定义类型逻辑
                }
            }
        }
        // 返回偏移量
        return offset;
    }

    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    protected void initBackgroundColor(float beginX, float beginY) {
        // 初始化背景颜色
        if (Objects.equals(this.backgroundColor, Color.WHITE)) {
            // 新建内容流
            PDPageContentStream contentStream = new PDPageContentStream(
                    this.getContext().getTargetDocument(),
                    this.getContext().getTargetPage(),
                    PDPageContentStream.AppendMode.APPEND,
                    true,
                    this.getIsResetContentStream()
            );
            // 绘制矩形（背景矩形）
            contentStream.addRect(beginX, beginY - this.getHeight(), this.getWidth() - this.getMarginLeft() - this.getMarginRight(), this.getHeight());
            // 设置矩形颜色（背景颜色）
            contentStream.setNonStrokingColor(this.getBackgroundColor());
            // 填充矩形（背景矩形）
            contentStream.fill();
            // 关闭内容流
            contentStream.close();
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
