package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 抽象表头或表尾
 *
 * @author xsx
 * @date 2024/6/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
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
public abstract class AbstractTableHeaderOrFooter extends BorderData {
    
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 表格
     */
    protected Table table;
    /**
     * 行列表
     */
    protected List<TableRow> rows;
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
    public AbstractTableHeaderOrFooter(Table table) {
        this.table = table;
        this.borderConfiguration = new BorderConfiguration(false);
    }
    
    /**
     * 无参构造
     */
    protected AbstractTableHeaderOrFooter() {
    
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
     * 渲染
     *
     * @param page   页面
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     * @return 返回Y轴坐标
     */
    protected float render(Page page, float beginX, float beginY) {
        // 初始化
        this.init();
        // 判断是否有行
        if (Objects.nonNull(this.getRows())) {
            // 遍历行
            for (TableRow row : this.getRows()) {
                // 判断是否为虚拟渲染
                if (this.getContext().getIsVirtualRender()) {
                    // 虚拟渲染
                    row.virtualRender(page, beginX, beginY);
                } else {
                    // 渲染
                    row.render(page, beginX, beginY);
                }
                // 重置Y轴坐标
                beginY = beginY - row.getHeight();
            }
        }
        // 返回Y轴坐标
        return beginY;
    }
    
    /**
     * 初始化
     */
    protected void init() {
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
        // 初始化行
        this.initRows();
        
    }
    
    /**
     * 初始化边框
     */
    protected void initBorder() {
        super.init(this.table, this.table.getBorderConfiguration());
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
}
