package org.dromara.pdf.pdfbox.core.base;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;

import java.util.Objects;

/**
 * 页眉
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
@EqualsAndHashCode(callSuper = true)
public class PageHeader extends AbstractPageHeaderOrFooter {

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public PageHeader(Page page) {
        super(page);
        this.getContext().setPageHeader(this);
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.PAGE_HEADER;
    }

    /**
     * 获取分页事件
     *
     * @return 返回分页事件
     */
    @Override
    public PagingEvent getPagingEvent() {
        return this.pagingEvent;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 检查高度
        Objects.requireNonNull(this.getHeight(), "the height can not be null");
        // 初始化X轴起始坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getPage().getMarginLeft() + this.getMarginLeft());
        }
        // 初始化Y轴起始坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getContext().getPage().getHeight() - this.getContext().getPage().getMarginTop());
        }
        // 初始化宽度
        this.initWidth();
        // 初始化背景颜色
        this.initBackgroundColor(this.getBeginX(), this.getBeginY());
        // 重置光标
        this.getContext().getCursor().reset(this.getBeginX(), this.getBeginY());
    }
}
