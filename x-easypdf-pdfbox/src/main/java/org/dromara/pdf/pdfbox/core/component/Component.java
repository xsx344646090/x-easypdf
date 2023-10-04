package org.dromara.pdf.pdfbox.core.component;

import org.dromara.pdf.pdfbox.core.Base;
import org.dromara.pdf.pdfbox.core.ComponentType;
import org.dromara.pdf.pdfbox.core.Context;
import org.dromara.pdf.pdfbox.core.PagingEvent;

import java.util.Set;

/**
 * 组件
 *
 * @author xsx
 * @date 2023/6/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public interface Component {

    /**
     * 渲染
     */
    void render();

    /**
     * 设置X轴起始坐标
     *
     * @param beginX X轴起始坐标
     */
    void setBeginX(Float beginX);

    /**
     * 设置Y轴起始坐标
     *
     * @param beginY Y轴起始坐标
     */
    void setBeginY(Float beginY);

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    ComponentType getType();

    /**
     * 获取上下文
     *
     * @return 返回上下文
     */
    Context getContext();

    /**
     * 获取基类
     *
     * @return 返回当前对象
     */
    Base getBase();

    /**
     * 获取分页事件
     *
     * @return 分页事件
     */
    Set<PagingEvent> getPagingEvents();

    /**
     * 获取自定义分页条件
     *
     * @return 返回自定义分页条件
     */
    PagingCondition getPagingCondition();
}
