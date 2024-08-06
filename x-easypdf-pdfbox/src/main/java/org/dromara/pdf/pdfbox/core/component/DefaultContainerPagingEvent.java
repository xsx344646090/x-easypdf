package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.PageHeader;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.util.Optional;

/**
 * 默认容器分页事件
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
public class DefaultContainerPagingEvent extends AbstractPagingEvent {
    
    /**
     * 分页之前
     *
     * @param component 当前组件
     */
    @Override
    public void before(Component component) {
        // 获取组件的上下文
        Context context = component.getContext();
        // 获取边框信息
        BorderInfo info = context.getBorderInfo();
        // 获取边框的起始Y坐标
        Float beginY = info.getBeginY();
        // 如果边框还没有被渲染
        if (!info.getIsAlreadyRendered()) {
            // 获取边框的高度
            Float height = info.getHeight();
            // 获取页脚的高度和页面的底部边距
            float bottom = context.getPageFooterHeight() + context.getPage().getMarginBottom();
            // 设置边框的高度
            info.setHeight(Math.abs(beginY - bottom));
            // 获取边框是否在顶部
            Boolean isBorderTop = info.getIsBorderTop();
            // 获取边框是否在底部
            Boolean isBorderBottom = info.getIsBorderBottom();
            // 如果边框是分页边框
            if (info.getIsPagingBorder()) {
                // 设置边框在顶部和底部
                info.setIsBorderTop(Boolean.TRUE);
                info.setIsBorderBottom(Boolean.TRUE);
            } else {
                // 如果边框是分页
                if (info.isPaging()) {
                    // 设置边框不在顶部
                    info.setIsBorderTop(Boolean.FALSE);
                }
                // 设置边框不在底部
                info.setIsBorderBottom(Boolean.FALSE);
            }
            // 创建一个矩形
            PDRectangle rectangle = new PDRectangle(
                    info.getBeginX(),
                    beginY - info.getHeight(),
                    info.getWidth(),
                    info.getHeight()
            );
            // 如果非虚拟渲染
            if (!context.getIsVirtualRender()) {
                // 根据组件的类型执行不同的操作
                switch (context.getExecutingComponentType()) {
                    case CONTAINER:
                    case TABLE: {
                        // 添加背景颜色
                        CommonUtil.addBackgroundColor(info.getContext(), info.getContentMode(), info.getIsResetContentStream(), rectangle, info.getBackgroundColor());
                        break;
                    }
                    default:
                        break;
                }
                // 绘制边框
                BorderUtil.drawBorderWithData(info, rectangle);
            }
            // 恢复边框的顶部和底部
            info.setIsBorderTop(isBorderTop);
            info.setIsBorderBottom(isBorderBottom);
            // 更新边框的高度
            info.setHeight(height - info.getHeight());
            // 更新分页计数
            info.pagingCount();
        }
        // 设置是否已经分页
        context.setIsAlreadyPaging(false);
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
        // 重置光标
        context.resetCursor();
        // 获取边框信息
        BorderInfo borderInfo = context.getBorderInfo();
        // 设置边框的起始Y坐标
        borderInfo.setBeginY(context.getCursor().getY() - Optional.ofNullable(context.getPageHeader()).map(PageHeader::getHeight).orElse(0F));
        // 设置边框是否已经渲染
        borderInfo.setIsAlreadyRendered(false);
        // 设置是否已经分页
        context.setIsAlreadyPaging(true);
    }
}
