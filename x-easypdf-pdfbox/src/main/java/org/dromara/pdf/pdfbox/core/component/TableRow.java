package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 表格行组件
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
public class TableRow extends BorderData {

    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 表格
     */
    protected Table table;
    /**
     * 所在页面
     */
    protected Page page;
    /**
     * 索引
     */
    protected Integer index;
    /**
     * 上一行
     */
    protected TableRow previous;
    /**
     * 下一行
     */
    protected TableRow next;
    /**
     * 单元格列表
     */
    protected List<TableCell> cells;
    /**
     * 高度
     */
    protected Float height;
    /**
     * 起始X轴坐标
     */
    protected Float beginX;
    /**
     * 起始Y轴坐标
     */
    protected Float beginY;
    /**
     * 是否分页
     */
    protected Boolean isBreak;
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
     * @param table 表格
     */
    public TableRow(Table table) {
        this.table = table;
        this.borderConfiguration = new BorderConfiguration(false);
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
     * 设置高度
     *
     * @param height 高度
     */
    public void setHeight(float height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height must be positive");
        }
        this.height = height;
    }

    /**
     * 设置单元格
     *
     * @param cells 单元格
     */
    public void setCells(List<TableCell> cells) {
        this.cells = cells;
    }

    /**
     * 设置单元格
     *
     * @param cells 单元格
     */
    public void setCells(TableCell... cells) {
        if (Objects.nonNull(cells)) {
            this.cells = new ArrayList<>(cells.length);
            Collections.addAll(this.cells, cells);
        } else {
            this.cells = null;
        }
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格
     */
    public void addCells(List<TableCell> cells) {
        if (Objects.nonNull(cells)) {
            if (Objects.isNull(this.cells)) {
                this.cells = new ArrayList<>(cells);
            } else {
                this.cells.addAll(cells);
            }
        }
    }

    /**
     * 添加单元格
     *
     * @param cells 单元格
     */
    public void addCells(TableCell... cells) {
        if (Objects.nonNull(cells)) {
            if (Objects.isNull(this.cells)) {
                this.cells = new ArrayList<>(Math.max(cells.length, 8));
            }
            Collections.addAll(this.cells, cells);
        }
    }

    /**
     * 获取宽度
     *
     * @return 返回宽度
     */
    public Float getWidth() {
        if (Objects.isNull(this.cells)) {
            return 0F;
        }
        return (float) this.cells.stream().mapToDouble(TableCell::getWidth).sum();
    }

    /**
     * 虚拟渲染
     *
     * @param page   页面
     * @param beginX 起始X轴坐标
     * @param beginY 起始Y轴坐标
     */
    protected void virtualRender(Page page, Float beginX, Float beginY) {
        // 初始化页面
        this.init(page, beginX, beginY);
        // 如果表格不为空
        if (Objects.nonNull(this.getCells())) {
            // 获取表格的列宽
            List<Float> cellWidths = this.getTable().getCellWidths();
            // 初始化索引
            int index = 0;
            // 遍历表格的每个单元格
            for (TableCell tableCell : this.getCells()) {
                // 如果单元格不为空
                if (Objects.nonNull(tableCell)) {
                    // 渲染单元格
                    tableCell.virtualRender(beginX, beginY);
                }
                // 更新下一个单元格的起始X坐标
                beginX = beginX + cellWidths.get(index);
                // 索引自增
                index++;
            }
        }
    }

    /**
     * 渲染
     *
     * @param page   页面
     * @param beginX 起始X轴坐标
     * @param beginY 起始Y轴坐标
     */
    protected void render(Page page, Float beginX, Float beginY) {
        // 初始化页面
        this.init(page, beginX, beginY);
        // 如果表格不为空
        if (Objects.nonNull(this.getCells())) {
            // 获取表格的列宽
            List<Float> cellWidths = this.getTable().getCellWidths();
            // 初始化索引
            int index = 0;
            // 遍历表格的每个单元格
            for (TableCell tableCell : this.getCells()) {
                // 如果单元格不为空
                if (Objects.nonNull(tableCell)) {
                    // 渲染单元格
                    tableCell.render(beginX, this.getBeginY());
                }
                // 更新下一个单元格的起始X坐标
                beginX = beginX + cellWidths.get(index);
                // 索引自增
                index++;
            }
        }
    }

    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    protected PagingEvent getPagingEvent() {
        return this.table.getPagingEvent();
    }

    /**
     * 初始化
     *
     * @param page   页面
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    protected void init(Page page, Float beginX, Float beginY) {
        // 检查参数
        Objects.requireNonNull(this.height, "the row height can not be null");
        // 设置页码
        this.page = page;
        // 设置起始X坐标
        this.beginX = beginX;
        // 设置起始Y坐标
        this.beginY = beginY;
        // 如果背景颜色为空，则设置为表格的背景颜色
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = this.table.getBackgroundColor();
        }
        // 如果是否换页为空，则设置为false
        if (Objects.isNull(this.isBreak)) {
            this.isBreak = Boolean.FALSE;
        }
        // 如果是否同行为空，则设置为表格的同行属性
        if (Objects.isNull(this.isTogether)) {
            this.isTogether = this.table.getIsTogether();
        }
        // 如果是否分页边框为空，则设置为表格的是否分页边框属性
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = this.table.getIsPagingBorder();
        }
        // 初始化内容上边距
        if (Objects.isNull(this.contentMarginTop)) {
            this.contentMarginTop = this.table.getContentMarginTop();
        }
        // 初始化内容下边距
        if (Objects.isNull(this.contentMarginBottom)) {
            this.contentMarginBottom = this.table.getContentMarginBottom();
        }
        // 初始化内容左边距
        if (Objects.isNull(this.contentMarginLeft)) {
            this.contentMarginLeft = this.table.getContentMarginLeft();
        }
        // 初始化内容右边距
        if (Objects.isNull(this.contentMarginRight)) {
            this.contentMarginRight = this.table.getContentMarginRight();
        }
        // 如果内容水平对齐方式为空，则设置为表格的内容水平对齐方式
        if (Objects.isNull(this.contentHorizontalAlignment)) {
            this.contentHorizontalAlignment = this.table.getContentHorizontalAlignment();
        }
        // 如果内容垂直对齐方式为空，则设置为表格的内容垂直对齐方式
        if (Objects.isNull(this.contentVerticalAlignment)) {
            this.contentVerticalAlignment = this.table.getContentVerticalAlignment();
        }
        // 初始化边框
        this.initBorder();
        // 初始化单元格
        this.initCells();
        // 检查是否同行
        this.checkTogether();
        // 检查是否换页
        this.checkBreak();
    }

    /**
     * 初始化（表头或表尾）
     *
     * @param headerOrFooter 表头或表尾
     */
    protected void initForHeaderOrFooter(AbstractTableHeaderOrFooter headerOrFooter) {
        // 如果背景颜色为空，则设置为表格的背景颜色
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = headerOrFooter.getBackgroundColor();
        }
        // 如果是否换页为空，则设置为false
        if (Objects.isNull(this.isBreak)) {
            this.isBreak = headerOrFooter.getIsBreak();
        }
        // 如果是否同行为空，则设置为表格的同行属性
        if (Objects.isNull(this.isTogether)) {
            this.isTogether = headerOrFooter.getIsTogether();
        }
        // 如果是否分页边框为空，则设置为表格的是否分页边框属性
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = headerOrFooter.getIsPagingBorder();
        }
        // 初始化内容上边距
        if (Objects.isNull(this.contentMarginTop)) {
            this.contentMarginTop = headerOrFooter.getContentMarginTop();
        }
        // 初始化内容下边距
        if (Objects.isNull(this.contentMarginBottom)) {
            this.contentMarginBottom = headerOrFooter.getContentMarginBottom();
        }
        // 初始化内容左边距
        if (Objects.isNull(this.contentMarginLeft)) {
            this.contentMarginLeft = headerOrFooter.getContentMarginLeft();
        }
        // 初始化内容右边距
        if (Objects.isNull(this.contentMarginRight)) {
            this.contentMarginRight = headerOrFooter.getContentMarginRight();
        }
        // 如果内容水平对齐方式为空，则设置为表格的内容水平对齐方式
        if (Objects.isNull(this.contentHorizontalAlignment)) {
            this.contentHorizontalAlignment = headerOrFooter.getContentHorizontalAlignment();
        }
        // 如果内容垂直对齐方式为空，则设置为表格的内容垂直对齐方式
        if (Objects.isNull(this.contentVerticalAlignment)) {
            this.contentVerticalAlignment = headerOrFooter.getContentVerticalAlignment();
        }
        // 初始化边框
        super.init(this.table, headerOrFooter.getBorderConfiguration());
    }

    /**
     * 初始化边框
     */
    protected void initBorder() {
        super.init(this.table, this.table.getBorderConfiguration());
    }

    /**
     * 初始化单元格
     */
    protected void initCells() {
        if (Objects.nonNull(this.cells)) {
            int index = 0;
            for (TableCell tableCell : this.cells) {
                if (Objects.nonNull(tableCell)) {
                    tableCell.setIndex(index);
                } else {
                    this.isTogether = false;
                }
                index++;
            }
        }
    }

    /**
     * 检查同行
     */
    protected void checkTogether() {
        if (this.isTogether && !this.isBreak) {
            this.isBreak = this.beginY - this.height < this.table.getBottom();
        }
    }

    /**
     * 检查分页
     */
    protected void checkBreak() {
        // 如果分页
        if (this.isBreak) {
            // 设置上下文是否已经分页
            this.getContext().setIsAlreadyPaging(false);
            // 设置上下文为手动分页
            this.getContext().setIsManualBreak(true);
            // 处理分页
            this.table.executeBreak();
            // 设置上下文为非手动分页
            this.getContext().setIsManualBreak(false);
            // 获取当前页面
            this.page = this.getContext().getPage();
            // 获取当前光标Y坐标
            this.beginY = this.getContext().getCursor().getY();
        }
    }

    public boolean hasNoMergeCell() {
        return true;
    }
}
