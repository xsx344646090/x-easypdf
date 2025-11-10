package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Cursor;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.common.PDRectangle;

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
        // 获取组件的上下文
        Context context = component.getContext();
        // 获取边框信息
        BorderInfo info = context.getBorderInfo();
        // 如果边框还没有被渲染
        if (!info.getIsAlreadyRendered()) {
            // 获取边框的起始Y坐标
            Float beginY = info.getBeginY();
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
                // 添加背景颜色
                this.addBackgroundColor(context, info, rectangle);
                // 绘制边框
                BorderUtil.drawBorderWithData(info, rectangle, info.getBackgroundColor());
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
        // 获取边框信息
        BorderInfo borderInfo = context.getBorderInfo();
        // 设置Y轴起始坐标
        borderInfo.setBeginY(context.getCursor().getY() - this.processTableHeader(context, component));
        // 设置边框是否已经渲染
        borderInfo.setIsAlreadyRendered(false);
        // 设置是否已经分页
        context.setIsAlreadyPaging(true);
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
            // 获取光标
            Cursor cursor = context.getCursor();
            // 获取起始X轴坐标
            float beginX = cursor.getX() + table.getRelativeBeginX();
            // 获取起始Y轴坐标
            float beginY = cursor.getY();
            // 渲染
            header.render(context.getPage(), beginX, beginY);
            // 返回表头高度
            return header.getHeight();
        }
        // 返回页眉高度
        return context.getPageHeaderHeight();
    }

    /**
     * 添加背景颜色
     *
     * @param context   上下文
     * @param info      边框信息
     * @param rectangle 矩形
     */
    @SneakyThrows
    protected void addBackgroundColor(Context context, BorderInfo info, PDRectangle rectangle) {
        // 非圆角边框，非页面背景颜色
        if (info.getBorderLineCapStyle() != LineCapStyle.ROUND && !Objects.equals(info.getBackgroundColor(), context.getPage().getBackgroundColor())) {
            // 添加背景颜色
            CommonUtil.addBackgroundColor(context, info.getContentMode(), info.getIsResetContentStream(), rectangle, info.getBackgroundColor());
        }
    }
}
