package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.util.BorderUtil;

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
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
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
     * 是否分页边框
     */
    protected Boolean isPagingBorder;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Table(Page page) {
        super(page);
    }

    /**
     * 设置边框
     *
     * @param flag 标记
     */
    public void setIsBorder(boolean flag) {
        this.borderConfiguration.setIsBorder(flag);
    }

    @SuppressWarnings("all")
    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }

    public void setRows(TableRow... rows) {
        if (Objects.nonNull(rows)) {
            this.rows = new ArrayList<>(rows.length);
            Collections.addAll(this.rows, rows);
        } else {
            this.rows = null;
        }
    }

    public void addRows(List<TableRow> rows) {
        if (Objects.nonNull(rows)) {
            if (Objects.isNull(this.rows)) {
                this.rows = new ArrayList<>(rows);
            } else {
                this.rows.addAll(rows);
            }
        }
    }

    public void addRows(TableRow... rows) {
        if (Objects.nonNull(rows)) {
            if (Objects.isNull(this.rows)) {
                this.rows = new ArrayList<>(rows.length);
            }
            Collections.addAll(this.rows, rows);
        }
    }

    public void setCellWidths(Float... cellWidths) {
        if (Objects.nonNull(cellWidths)) {
            this.cellWidths = new ArrayList<>(cellWidths.length);
            for (Float cellWidth : cellWidths) {
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

    public void setCellWidths(List<Float> cellWidths) {
        if (Objects.nonNull(cellWidths)) {
            this.setCellWidths(cellWidths.toArray(new Float[0]));
            this.cellWidths = new ArrayList<>(cellWidths.size());
            for (float cellWidth : cellWidths) {
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
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        this.init();
        float beginX = this.getBeginX();
        float beginY = this.getBeginY();
        float pageHeaderHeight = Optional.ofNullable(this.getContext().getPageHeader()).map(PageHeader::getHeight).orElse(0F);
        float pageFooterHeight = Optional.ofNullable(this.getContext().getPageFooter()).map(PageFooter::getHeight).orElse(0F);
        if (Objects.nonNull(this.getRows())) {
            float diff = 0F;
            Page page = this.getPage();
            Page temp;
            boolean isPaging = false;
            for (TableRow tableRow : this.getRows()) {
                if (isPaging) {
                    temp = page.getSubPage();
                    if (Objects.isNull(temp)) {
                        temp = page.createSubPage();
                    }
                    page = temp;
                    this.getContext().resetCursor();
                    beginY = this.getContext().getCursor().getY() - pageHeaderHeight - diff + page.getMarginBottom();
                } else {
                    beginY = beginY - tableRow.getHeight();
                }
                diff = beginY - tableRow.getHeight() - this.getBottom() - pageFooterHeight - page.getMarginBottom();
                if (diff >= 0) {
                    isPaging = false;
                    diff = 0F;
                } else {
                    isPaging = true;
                    diff = Math.abs(diff);
                }
            }
        }
        // 重置
        this.reset(beginX, beginY);
    }

    /**
     * 渲染
     */
    @Override
    public void render() {
        this.init();
        this.setPagingEvent(new DefaultTablePagingEvent());
        float beginX = this.getBeginX();
        float beginY = this.getBeginY();
        float pageHeaderHeight = Optional.ofNullable(this.getContext().getPageHeader()).map(PageHeader::getHeight).orElse(0F);
        if (Objects.nonNull(this.getRows())) {
            Page page = this.getPage();
            float top = this.getPage().getHeight() - this.getPage().getMarginTop() - pageHeaderHeight;
            float bottom = this.getBottom() - this.getMarginBottom();
            for (TableRow tableRow : this.getRows()) {
                tableRow.render(page, beginX, beginY);
                float height = tableRow.getHeight();
                float lastHeight = this.getLastHeight(beginY, height, top, bottom);
                if (!Objects.equals(height, lastHeight)) {
                    Integer pagingCount = this.getContext().getBorderInfo().getPagingCount();
                    if (pagingCount == 1) {
                        page = page.getSubPage();
                    } else {
                        page = page.getLastSubPage();
                    }
                    beginY = this.getContext().getCursor().getY();
                } else {
                    beginY = beginY - tableRow.getHeight();
                }
            }
        }
        this.reset(beginX, beginY);
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
        if (beginY - height >= bottom) {
            return height;
        }
        height = height - (beginY - bottom);
        beginY = top;
        return this.getLastHeight(beginY, height, top, bottom);
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
        // 初始化是否分页边框
        if (Objects.isNull(this.isPagingBorder)) {
            this.isPagingBorder = Boolean.FALSE;
        }
        // 检查换行
        this.checkWrap();
        // 初始化表格行
        this.initRows();
    }

    /**
     * 初始化行
     */
    protected void initRows() {
        if (Objects.nonNull(this.rows)) {
            int last = this.rows.size() - 1;
            for (int i = 0; i < this.rows.size(); i++) {
                TableRow tableRow = this.rows.get(i);
                tableRow.setIndex(i);
                tableRow.setTable(this);
                if (i > 0) {
                    tableRow.setPrevious(this.rows.get(i - 1));
                }
                if (i < last) {
                    tableRow.setNext(this.rows.get(i + 1));
                }
            }
        }
    }

    /**
     * 检查换行
     */
    protected void checkWrap() {
        // 初始化X轴坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), Boolean.FALSE);
        }
        // 初始化换行
        if (this.isWrap()) {
            this.getContext().getCursor().reset(
                    Optional.ofNullable(this.getContext().getWrapBeginX()).orElse(this.getContext().getPage().getMarginLeft()),
                    this.getContext().getCursor().getY()
            );
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), Boolean.FALSE);
        }
        // 初始化Y轴坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getContext().getCursor().getY() - this.getMarginTop(), Boolean.FALSE);
        }
    }

    /**
     * 执行分页
     */
    @SuppressWarnings("all")
    @Override
    protected Page executeBreak() {
        Float beginX = this.getBeginX();
        PagingEvent borderPagingEvent = Optional.ofNullable(this.getContext().getBorderInfo()).map(BorderInfo::getPagingEvent).orElse(null);
        Optional.ofNullable(borderPagingEvent).ifPresent(event -> event.before(this));
        Page page = super.executeBreak();
        Optional.ofNullable(borderPagingEvent).ifPresent(event -> event.after(this));
        this.setBeginX(beginX);
        return page;
    }

    /**
     * 重置
     */
    protected void reset(float beginX, float beginY) {
        // 重置光标
        this.getContext().getCursor().reset(
                beginX + this.getWidth() + this.getMarginRight(),
                beginY
        );
        // 重置
        super.reset(this.getType());
    }

    /**
     * 默认虚拟表格分页事件
     */
    public static class DefaultTableVirtualPagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {
            Context context = component.getContext();
            BorderInfo info = context.getBorderInfo();
            // 存在容器信息
            if (Objects.nonNull(info)) {
                Float height = info.getHeight();
                Float beginY = info.getBeginY();
                if (beginY < 0) {
                    beginY = height + beginY;
                }
                if (beginY - component.getBottom() < 0) {
                    beginY = component.getBottom() - beginY;
                    info.setHeight(beginY);
                } else {
                    info.setHeight(beginY - component.getBottom());
                }
                info.setHeight(height - info.getHeight());
                info.pagingCount();
            }
        }
    }

    /**
     * 默认容器分页事件
     */
    public static class DefaultTablePagingEvent extends AbstractPagingEvent {

        /**
         * 分页之前
         *
         * @param component 当前组件
         */
        @Override
        public void before(Component component) {
            Context context = component.getContext();
            BorderInfo info = context.getBorderInfo();
            Float beginY = info.getBeginY();
            if (!info.getIsAlreadyRendered()) {
                Float height = info.getHeight();
                float bottom = context.getPageFooterHeight() + context.getPage().getMarginBottom();
                info.setHeight(Math.abs(beginY - bottom));
                Boolean isBorderTop = info.getIsBorderTop();
                Boolean isBorderBottom = info.getIsBorderBottom();
                if (info.getIsPagingBorder()) {
                    info.setIsBorderTop(Boolean.TRUE);
                    info.setIsBorderBottom(Boolean.TRUE);
                } else {
                    if (info.isPaging()) {
                        info.setIsBorderTop(Boolean.FALSE);
                    }
                    info.setIsBorderBottom(Boolean.FALSE);
                }
                PDRectangle rectangle = new PDRectangle(
                        info.getBeginX(),
                        beginY - info.getHeight(),
                        info.getWidth(),
                        info.getHeight()
                );
                BorderUtil.drawBorderWithData(info, rectangle);
                info.setIsBorderTop(isBorderTop);
                info.setIsBorderBottom(isBorderBottom);
                info.setHeight(height - info.getHeight());
                info.pagingCount();
            }
            context.setIsAlreadyPaging(false);
        }

        /**
         * 分页之后
         *
         * @param component 当前组件
         */
        @Override
        public void after(Component component) {
            Context context = component.getContext();
            context.resetCursor();
            BorderInfo borderInfo = context.getBorderInfo();
            borderInfo.setBeginY(context.getCursor().getY() - Optional.ofNullable(context.getPageHeader()).map(PageHeader::getHeight).orElse(0F));
            borderInfo.setIsAlreadyRendered(false);
            context.setIsAlreadyPaging(true);
        }
    }
}
