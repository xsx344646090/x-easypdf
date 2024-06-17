package org.dromara.pdf.pdfbox.core.base;

import lombok.EqualsAndHashCode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.component.BorderInfo;
import org.dromara.pdf.pdfbox.core.component.Component;
import org.dromara.pdf.pdfbox.util.BorderUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 抽象分页事件
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
@EqualsAndHashCode
public abstract class AbstractPagingEvent implements PagingEvent {

    /**
     * 唯一标识
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * 分页之前
     *
     * @param component 当前组件
     */
    @Override
    public void before(Component component) {
        Context context = component.getContext();
        BorderInfo info = context.getBorderInfo();
        if (Objects.nonNull(info) && !info.getIsAlreadyRendered()) {
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
        Optional.ofNullable(context.getBorderInfo()).ifPresent(info -> {
            info.setBeginY(context.getCursor().getY());
            info.setIsAlreadyRendered(true);
            context.setIsAlreadyPaging(!(info.getBeginY() - info.getHeight() - component.getBottom() < 0));
        });
    }
}
