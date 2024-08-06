package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Context;

import java.util.Objects;

/**
 * 默认表格分页事件
 *
 * @author xsx
 * @date 2024/7/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public class DefaultTablePagingEvent extends AbstractPagingEvent {
    
    /**
     * 分页之前
     *
     * @param component 当前组件
     */
    @Override
    public void before(Component component) {
    
    }
    
    /**
     * 分页之后
     *
     * @param component 当前组件
     */
    @Override
    public void after(Component component) {
        // 获取组件的上下文
        Context context = component.getContext();
        // 获取边框信息
        BorderInfo borderInfo = context.getBorderInfo();
        // 设置边框的起始Y坐标
        borderInfo.setBeginY(borderInfo.getBeginY() - this.processTableHeader(context, component));
    }
    
    /**
     * 处理表头
     *
     * @param component 组件
     * @return 返回表头高度
     */
    protected float processTableHeader(Context context, Component component) {
        // 转为表格
        Table table = (Table) component;
        // 获取表头
        TableHeader header = table.getHeader();
        // 表头非空
        if (Objects.nonNull(header)) {
            // 获取起始X轴坐标
            float beginX = table.getBeginX() + table.getRelativeBeginX();
            // 获取起始Y轴坐标
            float beginY = context.getCursor().getY();
            // 渲染
            header.render(context.getPage(), beginX, beginY);
            // 返回表头高度
            return header.getHeight();
        }
        // 返回0
        return 0F;
    }
}
