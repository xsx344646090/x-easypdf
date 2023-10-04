package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * 基础边距类
 *
 * @author xsx
 * @date 2023/6/2
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
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseMargin extends Base {
    /**
     * 上边距
     */
    private Float marginTop;
    /**
     * 下边距
     */
    private Float marginBottom;
    /**
     * 左边距
     */
    private Float marginLeft;
    /**
     * 右边距
     */
    private Float marginRight;

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.initMargin(margin);
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     */
    public void setMarginTop(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.marginTop = margin;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     */
    public void setMarginBottom(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.marginBottom = margin;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    public void setMarginLeft(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.marginLeft = margin;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     */
    public void setMarginRight(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.marginRight = margin;
    }

    /**
     * 初始化
     *
     * @param base                基础参数
     * @param isInitMarginByParam 是否使用参数初始化边距
     */
    public void init(BaseMargin base, boolean isInitMarginByParam) {
        // 是否使用参数初始化边距
        if (isInitMarginByParam) {
            // 使用参数初始化边距
            this.initMarginWithParam(base);
        } else {
            // 初始化边距
            this.initMargin();
        }
        // 初始化
        super.init(base);
    }

    /**
     * 初始化边距
     */
    protected void initMargin() {
        // 初始化上边距
        if (Objects.isNull(this.marginTop)) {
            this.marginTop = 0F;
        }
        // 初始化下边距
        if (Objects.isNull(this.marginBottom)) {
            this.marginBottom = 0F;
        }
        // 初始化左边距
        if (Objects.isNull(this.marginLeft)) {
            this.marginLeft = 0F;
        }
        // 初始化右边距
        if (Objects.isNull(this.marginRight)) {
            this.marginRight = 0F;
        }
    }

    /**
     * 初始化边距
     */
    protected void initMargin(float margin) {
        // 初始化上边距
        this.setMarginTop(margin);
        // 初始化下边距
        this.setMarginBottom(margin);
        // 初始化左边距
        this.setMarginLeft(margin);
        // 初始化右边距
        this.setMarginRight(margin);
    }

    /**
     * 初始化边距
     *
     * @param base 基础参数
     */
    private void initMarginWithParam(BaseMargin base) {
        // 初始化上边距
        if (Objects.isNull(this.marginTop)) {
            this.marginTop = base.marginTop;
        }
        // 初始化下边距
        if (Objects.isNull(this.marginBottom)) {
            this.marginBottom = base.marginBottom;
        }
        // 初始化左边距
        if (Objects.isNull(this.marginLeft)) {
            this.marginLeft = base.marginLeft;
        }
        // 初始化右边距
        if (Objects.isNull(this.marginRight)) {
            this.marginRight = base.marginRight;
        }
    }
}
