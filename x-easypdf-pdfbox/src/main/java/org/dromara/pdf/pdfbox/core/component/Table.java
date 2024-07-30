package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.DefaultContainerPagingEvent;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 表格组件
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
public class Table extends AbstractComponent {

    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 行列表
     */
    protected List<TableRow> rows;
    /**
     * 单元格宽度
     */
    protected List<Float> cellWidths;
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
    public Table(Page page) {
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
     * 设置列宽
     *
     * @param widths 列宽
     */
    public void setCellWidths(float... widths) {
        if (Objects.nonNull(widths)) {
            this.cellWidths = new ArrayList<>(widths.length);
            for (Float cellWidth : widths) {
                if (Objects.isNull(cellWidth)) {
                    throw new IllegalArgumentException("the cell width can not be null");
                }
                if (cellWidth <= 0F) {
                    throw new IllegalArgumentException("the cell width must be greater than zero");
                }
                this.cellWidths.add(cellWidth);
            }
        } else {
            this.cellWidths = null;
        }
    }

    /**
     * 设置列宽
     *
     * @param widths 列宽
     */
    public void setCellWidths(List<Float> widths) {
        if (Objects.nonNull(widths)) {
            this.setCellWidths(CommonUtil.toFloatArray(widths));
        } else {
            this.cellWidths = null;
        }
    }

    /**
     * 设置行
     *
     * @param rows 行
     */
    @SuppressWarnings("all")
    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }

    /**
     * 设置行
     *
     * @param rows 行
     */
    public void setRows(TableRow... rows) {
        if (Objects.nonNull(rows)) {
            this.rows = new ArrayList<>(rows.length);
            Collections.addAll(this.rows, rows);
        } else {
            this.rows = null;
        }
    }

    /**
     * 添加行
     *
     * @param rows 行
     */
    public void addRows(List<TableRow> rows) {
        if (Objects.nonNull(rows)) {
            if (Objects.isNull(this.rows)) {
                this.rows = new ArrayList<>(rows);
            } else {
                this.rows.addAll(rows);
            }
        }
    }

    /**
     * 添加行
     *
     * @param rows 行
     */
    public void addRows(TableRow... rows) {
        if (Objects.nonNull(rows)) {
            if (Objects.isNull(this.rows)) {
                this.rows = new ArrayList<>(rows.length);
            }
            Collections.addAll(this.rows, rows);
        }
    }

    /**
     * 获取宽度
     *
     * @return 返回宽度
     */
    public Float getWidth() {
        if (Objects.isNull(this.cellWidths)) {
            return 0F;
        }
        return (float) this.cellWidths.stream().mapToDouble(Float::doubleValue).sum();
    }

    /**
     * 获取高度
     *
     * @return 返回高度
     */
    public Float getHeight() {
        if (Objects.isNull(this.rows)) {
            return 0F;
        }
        return (float) this.rows.stream().mapToDouble(TableRow::getHeight).sum();
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.TABLE;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 检查参数
        Objects.requireNonNull(this.cellWidths, "the cell widths can not be null");
        // 初始化
        super.init();
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
        // 重置Y轴相对坐标
        if (!this.isCustomPosition && this.relativeBeginY == 0F) {
            this.relativeBeginY = this.getFirstRowHeight();
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
        // 初始化内容水平对齐方式
        if (Objects.isNull(this.contentHorizontalAlignment)) {
            this.contentHorizontalAlignment = HorizontalAlignment.LEFT;
        }
        // 初始化内容垂直对齐方式
        if (Objects.isNull(this.contentVerticalAlignment)) {
            this.contentVerticalAlignment = VerticalAlignment.TOP;
        }
        // 初始化表格行
        this.initRows();
        // 检查换行
        this.checkWrap(this.getFirstRowHeight());
        // 检查分页
        if (this.checkPaging()) {
            this.setIsWrap(true);
            this.wrap(this.getFirstRowHeight());
        }
        // 初始化起始X轴坐标
        this.initBeginX(this.getWidth());
        // 初始化起始Y轴坐标
        this.initBeginY(this.getHeight());
    }

    /**
     * 初始化行
     */
    protected void initRows() {
        // 判断行是否为空
        if (Objects.nonNull(this.rows)) {
            // 获取行的最后一个元素的索引
            int last = this.rows.size() - 1;
            // 遍历行
            for (int i = 0; i < this.rows.size(); i++) {
                // 获取当前元素
                TableRow tableRow = this.rows.get(i);
                // 设置当前元素的索引
                tableRow.setIndex(i);
                // 如果当前元素不是第一个元素，则设置当前元素的前一个元素
                if (i > 0) {
                    tableRow.setPrevious(this.rows.get(i - 1));
                }
                // 如果当前元素不是最后一个元素，则设置当前元素的下一个元素
                if (i < last) {
                    tableRow.setNext(this.rows.get(i + 1));
                }
            }
        }
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
     * 写入内容
     */
    @Override
    protected void writeContents() {
        // 获取起始X坐标
        float beginX = this.getBeginX() + this.getRelativeBeginX();
        // 获取起始Y坐标
        float beginY = this.getBeginY() + this.getRelativeBeginY();
        // 判断是否有行
        if (Objects.nonNull(this.getRows())) {
            // 获取页
            Page page = this.getPage();
            // 获取页顶部坐标
            float top = this.getContext().getMaxBeginY();
            // 获取底部坐标
            float bottom = this.getBottom() - this.getMarginBottom();
            // 遍历行
            for (TableRow tableRow : this.getRows()) {
                // 判断是否为虚拟渲染
                if (this.getContext().getIsVirtualRender()) {
                    // 虚拟渲染
                    tableRow.virtualRender(page, beginX, beginY);
                } else {
                    // 渲染
                    tableRow.render(page, beginX, beginY);
                }
                // 获取行高度
                float height = tableRow.getHeight();
                // 获取最后一行高度
                float lastHeight = this.getLastHeight(beginY, height, top, bottom);
                // 判断高度是否分页
                if (tableRow.getIsBreak() || !Objects.equals(height, lastHeight) || beginY == 0F) {
                    // 获取分页数量
                    Integer pagingCount = this.getContext().getBorderInfo().getPagingCount();
                    // 判断分页数量是否为1或是否分页
                    if (pagingCount == 1 || tableRow.getIsBreak()) {
                        // 获取子页
                        page = page.getSubPage();
                    } else {
                        // 获取最后子页
                        page = page.getLastSubPage();
                    }
                    // 获取Y坐标
                    beginY = this.getContext().getCursor().getY();
                } else {
                    // 获取Y坐标
                    beginY = beginY - tableRow.getHeight();
                }
            }
        }
        // 重置
        this.reset(beginX, beginY);
    }

    /**
     * 执行分页
     */
    @Override
    protected Page executeBreak() {
        // 获取开始X坐标
        Float beginX = this.getBeginX();
        // 获取边框信息中的分页事件
        PagingEvent borderPagingEvent = Optional.ofNullable(this.getContext().getBorderInfo()).map(BorderInfo::getPagingEvent).orElse(null);
        // 自动分页
        if (!this.getContext().getIsManualBreak()) {
            // 如果分页事件不为空，则调用before方法
            Optional.ofNullable(borderPagingEvent).ifPresent(event -> event.before(this));
        }
        // 执行分页操作
        Page page = super.executeBreak();
        // 自动分页
        if (!this.getContext().getIsManualBreak()) {
            // 如果分页事件不为空，则调用after方法
            Optional.ofNullable(borderPagingEvent).ifPresent(event -> event.after(this));
        }
        // 设置开始X坐标
        this.setBeginX(beginX);
        // 返回分页结果
        return page;
    }

    /**
     * 获取第一行行高
     *
     * @return 返回高度
     */
    protected float getFirstRowHeight() {
        return Optional.ofNullable(this.rows).map(rows -> rows.get(0).getHeight()).orElse(0F);
    }

    /**
     * 获取最终高度
     *
     * @param beginY Y轴起始坐标
     * @param height 高度
     * @param top    顶点高度
     * @param bottom 底部高度
     * @return 返回最终高度
     */
    protected float getLastHeight(float beginY, float height, float top, float bottom) {
        // 如果Y轴起始坐标减去高度大于等于底部高度，则返回高度
        if (beginY - height >= bottom) {
            // 返回高度
            return height;
        }
        // 重置高度
        height = height - (beginY - bottom);
        // 重置Y轴起始坐标
        beginY = top;
        // 递归
        return this.getLastHeight(beginY, height, top, bottom);
    }

    /**
     * 重置
     */
    @Override
    protected void reset() {
        this.getContext().resetWrapWidth(null);
        this.getContext().resetHeight(null);
    }

    /**
     * 重置
     *
     * @param x X轴坐标
     * @param y Y轴坐标
     */
    protected void reset(float x, float y) {
        super.reset(this.getType(), x, y);
    }
}
