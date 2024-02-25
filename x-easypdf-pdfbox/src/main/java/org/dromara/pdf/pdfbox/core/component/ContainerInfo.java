package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.AbstractBaseBorder;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;

/**
 * 容器信息
 *
 * @author xsx
 * @date 2023/9/15
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
public class ContainerInfo extends AbstractBaseBorder {

    /**
     * 分页次数
     */
    private Integer pagingCount;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;
    /**
     * X轴起始坐标
     */
    private Float beginX;
    /**
     * Y轴起始坐标
     */
    private Float beginY;
    /**
     * 分页事件
     */
    private PagingEvent pagingEvent;
    /**
     * 是否分页边框
     */
    private Boolean isPagingBorder;
    /**
     * 是否第一个组件
     */
    private Boolean isFirstComponent;

    /**
     * 有参构造
     *
     * @param container 容器
     */
    public ContainerInfo(Container container) {
        // 初始化
        super.init(container, false);
        // 初始化分页次数
        this.pagingCount = 0;
        // 初始化宽度
        this.width = container.getWidth();
        // 初始化高度
        this.height = container.getHeight();
        // 初始化X轴坐标
        this.beginX = container.getBeginX();
        // 初始化Y轴坐标
        this.beginY = container.getBeginY();
        // 初始化分页事件
        this.pagingEvent = container.getPagingEvent();
        // 初始化是否分页边框
        this.isPagingBorder = container.getIsPagingBorder();
        // 初始化是否第一个组件
        this.isFirstComponent = Boolean.TRUE;
        // 初始化上下文
        this.setContext(container.getContext());
    }

    /**
     * 分页次数累计
     */
    public void pagingCount() {
        this.pagingCount++;
    }

    /**
     * 是否分页
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isPaging() {
        return this.pagingCount > 0;
    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {

    }
}
