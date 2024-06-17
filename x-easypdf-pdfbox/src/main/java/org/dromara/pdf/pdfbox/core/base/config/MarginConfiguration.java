package org.dromara.pdf.pdfbox.core.base.config;

import lombok.Data;

import java.util.Objects;

/**
 * 边距配置
 *
 * @author xsx
 * @date 2023/6/2
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
public class MarginConfiguration {
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
     * 无参构造
     */
    public MarginConfiguration() {
        this.init();
    }

    /**
     * 有参构造
     * @param configuration 配置
     */
    public MarginConfiguration(MarginConfiguration configuration) {
        this.init(configuration);
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.marginTop = margin;
        this.marginBottom = margin;
        this.marginLeft = margin;
        this.marginRight = margin;
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
     */
    public void init() {
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
     * 初始化
     *
     * @param configuration 配置
     */
    public void init(MarginConfiguration configuration) {
        // 初始化上边距
        if (Objects.isNull(this.marginTop)) {
            this.marginTop = configuration.marginTop;
        }
        // 初始化下边距
        if (Objects.isNull(this.marginBottom)) {
            this.marginBottom = configuration.marginBottom;
        }
        // 初始化左边距
        if (Objects.isNull(this.marginLeft)) {
            this.marginLeft = configuration.marginLeft;
        }
        // 初始化右边距
        if (Objects.isNull(this.marginRight)) {
            this.marginRight = configuration.marginRight;
        }
    }
}
