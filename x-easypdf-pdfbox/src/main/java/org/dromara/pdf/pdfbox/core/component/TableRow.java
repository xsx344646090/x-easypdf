package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;

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
     * 是否分页边框
     */
    protected Boolean isPagingBorder;

    /**
     * 无参构造
     */
    public TableRow(float height) {
        this.setHeight(height);
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
    @SuppressWarnings("all")
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
     */
    public void virtualRender(Page page, Float beginX, Float beginY) {
        this.init(page, beginX, beginY);
        if (Objects.nonNull(this.getCells())) {

        }
    }

    /**
     * 渲染
     */
    public void render(Page page, Float beginX, Float beginY) {
        this.init(page, beginX, beginY);
        if (Objects.nonNull(this.getCells())) {
            List<Float> cellWidths = this.getTable().getCellWidths();
            int index = 0;
            for (TableCell tableCell : this.getCells()) {
                if (Objects.nonNull(tableCell)) {
                    tableCell.render(beginX, beginY);
                }
                beginX = beginX + cellWidths.get(index);
                index++;
            }
        }
    }

    /**
     * 初始化
     *
     * @param page
     * @param beginX
     * @param beginY
     */
    protected void init(Page page, Float beginX, Float beginY) {
        this.page = page;
        this.beginX = beginX;
        this.beginY = beginY;
        this.initBorder();
        this.initCells();
    }

    /**
     * 初始化边框
     */
    protected void initBorder() {
        super.init(this.page, this.table.getBorderConfiguration());
        // 初始化是否分页边框
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = this.table.getIsPagingBorder();
        }
    }

    /**
     * 初始化单元格
     */
    protected void initCells() {
        if (Objects.nonNull(this.cells)) {
            int index = 0;
            for (TableCell tableCell : this.cells) {
                if (Objects.nonNull(tableCell)) {
                    tableCell.setRow(this);
                    tableCell.setIndex(index);
                }
                index++;
            }
        }
    }
}
