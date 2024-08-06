package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;

import java.util.Optional;

/**
 * 默认页眉页脚分页事件
 *
 * @author xsx
 * @date 2024/7/16
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
public class DefaultPageHeaderFooterPagingEvent extends AbstractPagingEvent {
    /**
     * 分页之后
     *
     * @param component 当前组件
     */
    @Override
    public void after(Component component) {
        // 获取上下文
        Context context = component.getContext();
        // 获取换行起始坐标
        Float wrapBeginX = context.getWrapBeginX();
        // 获取换行宽度
        Float wrapWidth = context.getWrapWidth();
        // 获取执行组件类型
        ComponentType currentExecutingComponentType = context.getExecutingComponentType();
        // 渲染组件
        Optional.ofNullable(context.getPageHeader()).ifPresent(AbstractPageHeaderOrFooter::render);
        // 渲染组件
        Optional.ofNullable(context.getPageFooter()).ifPresent(AbstractPageHeaderOrFooter::render);
        // 重置执行组件类型
        context.setExecutingComponentType(currentExecutingComponentType);
        // 重置换行起始坐标
        context.resetWrapBeginX(wrapBeginX);
        // 重置换行宽度
        context.resetWrapWidth(wrapWidth);
    }
}
