package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import org.dromara.pdf.pdfbox.core.enums.ContentMode;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;

import java.awt.*;
import java.util.Objects;

/**
 * 抽象基础类
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
public abstract class AbstractBase {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 内容模式
     */
    private ContentMode contentMode;
    /**
     * 是否重置内容流
     */
    private Boolean isResetContentStream;
    /**
     * 背景颜色
     */
    private Color backgroundColor;
    /**
     * 水平对齐方式
     */
    private HorizontalAlignment horizontalAlignment;
    /**
     * 垂直对齐方式
     */
    private VerticalAlignment verticalAlignment;
    /**
     * 是否换行
     */
    private Boolean isWrap;
    /**
     * 是否分页
     */
    private Boolean isBreak;

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 初始化基础
     */
    public abstract void initBase();

    /**
     * 初始化
     */
    public void init(AbstractBase param) {
        // 初始化内容模式
        if (Objects.isNull(this.contentMode)) {
            this.contentMode = param.contentMode;
        }
        // 初始化背景颜色
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = param.backgroundColor;
        }
        // 初始化水平对齐方式
        if (Objects.isNull(this.horizontalAlignment)) {
            this.horizontalAlignment = param.horizontalAlignment;
        }
        // 初始化垂直对齐方式
        if (Objects.isNull(this.verticalAlignment)) {
            this.verticalAlignment = param.verticalAlignment;
        }
        // 初始化是否重置内容流
        if (Objects.isNull(this.isResetContentStream)) {
            this.isResetContentStream = param.isResetContentStream;
        }
        // 初始化是否换行
        if (Objects.isNull(this.isWrap)) {
            this.isWrap = param.isWrap;
            if (Objects.isNull(this.isWrap)) {
                this.isWrap = Boolean.FALSE;
            }
        }
        // 初始化是否分页
        if (Objects.isNull(this.isBreak)) {
            this.isBreak = param.isBreak;
            if (Objects.isNull(this.isBreak)) {
                this.isBreak = Boolean.FALSE;
            }
        }
        // 初始化上下文
        if (Objects.nonNull(this.context)) {
            if (Objects.isNull(this.context.getWrapHeight())) {
                this.context.setWrapHeight(this.context.getPage().getFontSize());
            }
            if (Objects.isNull(this.context.getWrapBeginX()) && Objects.nonNull(this.context.getPage())) {
                this.context.setWrapBeginX(this.context.getPage().getMarginLeft());
            }
        }
    }
}
