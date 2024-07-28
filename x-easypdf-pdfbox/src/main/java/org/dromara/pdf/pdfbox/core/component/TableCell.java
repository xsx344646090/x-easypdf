package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 表格单元格组件
 *
 * @author xsx
 * @date 2024/4/30
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
public class TableCell extends BorderData {
    
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 所在行
     */
    protected TableRow row;
    /**
     * 索引
     */
    protected Integer index;
    /**
     * 组件列表
     */
    protected List<Component> components;
    /**
     * 宽度
     */
    protected Float width;
    /**
     * 跨行数
     */
    protected Integer rowspan;
    /**
     * 跨列数
     */
    protected Integer colspan;
    /**
     * 起始X轴坐标
     */
    protected Float beginX;
    /**
     * 起始Y轴坐标
     */
    protected Float beginY;
    /**
     * 是否分页边框
     */
    protected Boolean isPagingBorder;
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
     * @param row 行
     */
    public TableCell(TableRow row) {
        this.row = row;
        this.borderConfiguration = new BorderConfiguration(false);
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
     * 获取宽度
     *
     * @return 返回宽度
     */
    public Float getWidth() {// 返回当前宽度
        // 跨列数为0
        if (this.getColspan() == 0) {
            // 返回当前宽度
            return this.width;
        }
        // 定义最后索引
        int lastIndex = this.index + this.colspan;
        // 定义总宽度
        float total = this.width;
        // 获取单元格宽度
        List<Float> cellWidths = this.getRow().getTable().getCellWidths();
        // 遍历单元格宽度
        for (int i = this.index + 1; i <= lastIndex; i++) {
            // 计算总宽度
            total = total + cellWidths.get(i);
        }
        // 返回总宽度
        return total;
    }
    
    /**
     * 获取高度
     *
     * @return 返回高度
     */
    protected Float getHeight() {
        // 如果行跨度为0，则返回行的高度
        if (this.getRowspan() == 0) {
            return this.row.getHeight();
        }
        // 定义最后索引
        int lastIndex = this.row.getIndex() + this.rowspan;
        // 总高度
        float total = this.row.getHeight();
        // 获取下一行
        TableRow tableRow = Optional.ofNullable(this.row.getNext()).orElse(this.row);
        // 遍历从当前行到最后一行的所有行
        for (int i = this.row.getIndex() + 1; i <= lastIndex; i++) {
            // 累加每行的高度
            total = total + tableRow.getHeight();
            // 获取下一行
            tableRow = Optional.ofNullable(tableRow.getNext()).orElse(this.row);
        }
        // 返回总高度
        return total;
    }
    
    /**
     * 获取跨行数
     *
     * @return 返回跨行数
     */
    public Integer getRowspan() {
        return Optional.ofNullable(this.rowspan).orElse(0);
    }
    
    /**
     * 获取跨列数
     *
     * @return 返回跨列数
     */
    public Integer getColspan() {
        return Optional.ofNullable(this.colspan).orElse(0);
    }
    
    /**
     * 获取页面
     *
     * @return 返回页面
     */
    public Page getPage() {
        return this.row.getPage();
    }
    
    /**
     * 虚拟渲染
     */
    public void virtualRender(Float beginX, Float beginY) {
        this.processRender(beginX, beginY, false);
    }
    
    /**
     * 渲染
     */
    public void render(Float beginX, Float beginY) {
        this.processRender(beginX, beginY, true);
    }
    
    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    protected PagingEvent getPagingEvent() {
        return this.row.getPagingEvent();
    }
    
    /**
     * 初始化
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    protected void init(Float beginX, Float beginY) {
        try {
            this.width = this.row.getTable().getCellWidths().get(this.index);
        } catch (Exception e) {
            throw new IllegalArgumentException("the index['" + this.index + "'] of cell width is undefined");
        }
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = this.row.getBackgroundColor();
        }
        if (Objects.isNull(this.contentHorizontalAlignment)) {
            this.contentHorizontalAlignment = this.row.getContentHorizontalAlignment();
        }
        if (Objects.isNull(this.contentVerticalAlignment)) {
            this.contentVerticalAlignment = this.row.getContentVerticalAlignment();
        }
        this.beginX = beginX;
        this.beginY = beginY;
        this.initBorder();
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
     * 初始化边框
     */
    protected void initBorder() {
        super.init(this.row, this.row.getBorderConfiguration());
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = this.row.getIsPagingBorder();
        }
    }
    
    /**
     * 处理渲染
     *
     * @param beginX   X轴起始坐标
     * @param beginY   Y轴起始坐标
     * @param isRender 是否渲染
     */
    protected void processRender(Float beginX, Float beginY, boolean isRender) {
        // 初始化
        this.init(beginX, beginY);
        // 获取边框信息
        BorderInfo borderInfo = this.getContext().getBorderInfo();
        // 获取页面
        Page page = this.getRow().getPage();
        // 获取上下文
        Context context = page.getContext();
        // 设置页面
        context.setPage(page);
        // 重置光标位置
        context.getCursor().reset(beginX, beginY);
        // 添加边框
        float tempY = this.addBorder(beginX, beginY - borderInfo.getHeight(), borderInfo);
        // 如果有组件
        if (Objects.nonNull(this.getComponents())) {
            // 设置页面
            context.setPage(page);
            // 设置是否是第一个组件
            context.setIsFirstComponent(true);
            // 设置高度
            context.setHeight(this.getHeight());
            // 重置光标位置
            context.getCursor().reset(beginX, beginY);
            // 遍历组件
            for (Component component : this.getComponents()) {
                // 设置换行X轴起始坐标
                context.setWrapBeginX(beginX);
                // 设置换行宽度
                context.setWrapWidth(this.getWidth());
                // 设置水平对齐方式
                component.setHorizontalAlignment(this.getContentHorizontalAlignment());
                // 设置垂直对齐方式
                component.setVerticalAlignment(this.getContentVerticalAlignment());
                // 如果需要渲染
                if (isRender) {
                    // 渲染组件
                    component.render();
                } else {
                    // 虚拟渲染组件
                    component.virtualRender();
                }
            }
        }
        // 重置光标位置
        context.resetCursor(beginX + this.getWidth(), tempY);
    }
    
    /**
     * 添加边框
     *
     * @param beginX 组件渲染前X轴坐标
     * @param beginY 组件渲染前Y轴坐标
     * @param info   容器信息
     * @return 返回Y轴起始坐标
     */
    @SneakyThrows
    protected float addBorder(Float beginX, Float beginY, BorderInfo info) {
        // 获取表格
        Table table = this.getRow().getTable();
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
        if (table.isPaging(table, beginY)) {
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
            // 添加背景颜色
            CommonUtil.addBackgroundColor(info.getContext(), info.getContentMode(), info.getIsResetContentStream(), rectangle, info.getBackgroundColor());
            // 绘制边框
            BorderUtil.drawBorderWithData(info, rectangle);
            // 返回Y轴起始坐标
            return rectangle.getLowerLeftY();
        }
    }
}
