package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.util.BorderUtil;

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
    protected Integer rowSpan;
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
     * 获取宽度
     *
     * @return 返回宽度
     */
    public Float getWidth() {// 返回当前宽度
        // 跨列数为0
        if (this.getColumnSpan() == 0) {
            // 返回当前宽度
            return this.width;
        }
        // 定义最后索引
        int lastIndex = this.index + this.colspan;
        // 总宽度
        float total = this.width;
        List<Float> cellWidths = this.getRow().getTable().getCellWidths();
        for (int i = this.index + 1; i <= lastIndex; i++) {
            total = total + cellWidths.get(i);
        }
        // 返回总宽度
        return total;
    }

    protected Float getHeight() {
        if (this.getRowSpan() == 0) {
            return this.row.getHeight();
        }
        // 定义最后索引
        int lastIndex = this.row.getIndex() + this.rowSpan;
        // 总高度
        float total = this.row.getHeight();
        TableRow tableRow = Optional.ofNullable(this.row.getNext()).orElse(this.row);
        for (int i = this.row.getIndex() + 1; i <= lastIndex; i++) {
            total = total + tableRow.getHeight();
            tableRow = Optional.ofNullable(tableRow.getNext()).orElse(this.row);
        }
        // 返回总高度
        return total;
    }

    public Integer getRowSpan() {
        return Optional.ofNullable(this.rowSpan).orElse(0);
    }

    public Integer getColumnSpan() {
        return Optional.ofNullable(this.colspan).orElse(0);
    }

    protected PagingEvent getPagingEvent() {
        return this.row.getTable().getPagingEvent();
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
            throw new IllegalArgumentException("unknown cell width: " + this.index);
        }
        this.beginX = beginX;
        this.beginY = beginY;
        // 初始化边框
        this.initBorder();
        // 初始化容器信息
        this.getContext().setBorderInfo(
                new BorderInfo(
                        this,
                        this.getBorderConfiguration(),
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
        // 初始化是否分页边框
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
        this.init(beginX, beginY);
        BorderInfo borderInfo = this.getContext().getBorderInfo();
        Page page = this.getRow().getPage();
        Context context = page.getContext();
        context.setPage(page);
        context.getCursor().reset(beginX, beginY);
        float tempY = this.addBorder(beginX, beginY - borderInfo.getHeight(), borderInfo);
        if (Objects.nonNull(this.getComponents())) {
            context.setPage(page);
            context.setIsFirstComponent(true);
            context.setHeight(this.getHeight());
            context.getCursor().reset(beginX, beginY);
            for (Component component : this.getComponents()) {
                context.setWrapBeginX(beginX);
                context.setWrapWidth(this.getWidth());
                if (isRender) {
                    component.render();
                } else {
                    component.virtualRender();
                }
            }
        }
        context.resetCursor(beginX + this.getWidth(), tempY);
    }

    /**
     * 添加边框
     *
     * @param beginY 组件渲染前Y轴坐标
     * @param info   容器信息
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
            // 绘制边框
            BorderUtil.drawBorderWithData(info, rectangle);
            // 返回Y轴起始坐标
            return rectangle.getLowerLeftY();
        }
    }
}
