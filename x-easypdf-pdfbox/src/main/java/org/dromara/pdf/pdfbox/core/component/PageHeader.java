package org.dromara.pdf.pdfbox.core.component;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.ComponentType;
import org.dromara.pdf.pdfbox.core.Page;

import java.util.Objects;

/**
 * 页眉
 *
 * @author xsx
 * @date 2023/9/21
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf-new is licensed under Mulan PSL v2.
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
        page.getContext().setPageHeader(this);
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
     * 初始化
     */
    @Override
    public void init() {
        // 初始化基础
        this.initBase();
        // 初始化X轴起始坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getPage().getMarginLeft());
        }
        // 初始化Y轴起始坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getContext().getPage().getHeight() - this.getContext().getPage().getMarginTop());
        }
        // 重置游标
        this.getContext().getCursor().reset(this.getBeginX(), this.getBeginY() - this.getBeginYOffset());
    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {
        // 初始化参数
        super.init(this.getContext().getPage(), false);
    }
}
