package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.AbstractBase;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.PagingEvent;
import org.dromara.pdf.pdfbox.core.base.config.BorderConfiguration;

/**
 * 边框信息
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
public class BorderInfo extends BorderData {

    /**
     * 分页次数
     */
    protected Integer pagingCount;
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
     * 分页事件
     */
    protected PagingEvent pagingEvent;
    /**
     * 是否分页边框
     */
    protected Boolean isPagingBorder;
    /**
     * 是否第一个组件
     */
    protected Boolean isFirstComponent;
    /**
     * 是否已经绘制
     */
    protected Boolean isAlreadyRendered;

    /**
     * 有参构造
     *
     * @param base           基础类
     * @param configuration  边框配置
     * @param width          宽度
     * @param height         高度
     * @param beginX         X轴起始坐标
     * @param beginY         Y轴起始坐标
     * @param pagingEvent    分页事件
     * @param isPagingBorder 是否分页边框
     */
    public BorderInfo(
            AbstractBase base,
            BorderConfiguration configuration,
            float width,
            float height,
            float beginX,
            float beginY,
            PagingEvent pagingEvent,
            Boolean isPagingBorder
    ) {
        this.init(base, configuration, width, height, beginX, beginY, pagingEvent, isPagingBorder);
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
     * 初始化
     *
     * @param base           基础类
     * @param configuration  边框配置
     * @param width          宽度
     * @param height         高度
     * @param beginX         X轴起始坐标
     * @param beginY         Y轴起始坐标
     * @param pagingEvent    分页事件
     * @param isPagingBorder 是否分页边框
     */
    protected void init(
            AbstractBase base,
            BorderConfiguration configuration,
            float width,
            float height,
            float beginX,
            float beginY,
            PagingEvent pagingEvent,
            Boolean isPagingBorder
    ) {
        // 初始化
        super.init(base, configuration);
        // 初始化分页次数
        this.pagingCount = 0;
        // 初始化宽度
        this.width = width;
        // 初始化高度
        this.height = height;
        // 初始化X轴坐标
        this.beginX = beginX;
        // 初始化Y轴坐标
        this.beginY = beginY;
        // 初始化分页事件
        this.pagingEvent = pagingEvent;
        // 初始化是否分页边框
        this.isPagingBorder = isPagingBorder;
        // 初始化是否第一个组件
        this.isFirstComponent = Boolean.TRUE;
        // 初始化是否已经绘制
        this.isAlreadyRendered = Boolean.FALSE;
    }
}
