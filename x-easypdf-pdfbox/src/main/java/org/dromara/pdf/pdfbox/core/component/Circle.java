package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.ComponentType;
import org.dromara.pdf.pdfbox.core.base.Page;

import java.awt.*;
import java.util.Objects;

/**
 * 圆形组件
 *
 * @author xsx
 * @date 2023/11/24
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
@EqualsAndHashCode(callSuper = true)
public class Circle extends AbstractComponent {

    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;
    /**
     * 边框颜色
     */
    private Color borderColor;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Circle(Page page) {
        super(page);
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     */
    public void setWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the width can not be less than 1");
        }
        this.width = width;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     */
    public void setHeight(float height) {
        if (height < 1) {
            throw new IllegalArgumentException("the height can not be less than 1");
        }
        this.height = height;
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.RECTANGLE;
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
        // 初始化
        super.init();
        // 初始化宽度与高度
        this.initWidthAndHeight();
        // 初始化边框颜色
        if (Objects.isNull(this.borderColor)) {
            this.borderColor = Color.BLACK;
        }
        // 初始化首行
        if (!this.getIsCustomY() && this.getContext().isFirstLine()) {
            this.setBeginY(this.getBeginY() - this.getHeight());
        }
        // 检查换行
        if (this.isWrap()) {
            this.wrap();
        }
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
        }
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 添加矩形（边框矩形）
        contentStream.addRect(
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() + this.getRelativeBeginY(),
                this.getWidth(),
                this.getHeight()
        );
        // 设置矩形颜色（边框颜色）
        contentStream.setNonStrokingColor(this.getBorderColor());
        // 填充矩形（边框矩形）
        contentStream.fill();
        // 添加矩形（背景矩形）
        contentStream.addRect(
                this.getBeginX() + this.getRelativeBeginX() + this.getBorderWidth(),
                this.getBeginY() + this.getRelativeBeginY() + this.getBorderWidth(),
                this.getWidth() - this.getBorderWidth() * 2,
                this.getHeight() - this.getBorderWidth() * 2
        );
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
        // 重置光标
        this.getContext().getCursor().reset(
                this.getBeginX() + this.getWidth() + this.getMarginRight(),
                this.getBeginY() - this.getHeight() - this.getMarginBottom()
        );
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化宽度与高度
     */
    private void initWidthAndHeight() {
        // 获取最大宽度
        float maxWidth = this.getContext().getWrapWidth() + this.getPage().getMarginLeft() - this.getBeginX() - this.getMarginRight();
        // 获取最大高度
        float maxHeight = this.getBeginY() - this.getMarginBottom() - this.getContext().getPage().getMarginBottom();
        // 初始化宽度
        if (Objects.isNull(this.width)) {
            // 初始化宽度
            this.width = maxWidth;
        }
        // 图片宽度大于最大宽度，则重置
        if (this.width > maxWidth) {
            // 重置图片宽度
            this.width = maxWidth;
        }
        // 初始化高度
        if (Objects.isNull(this.height)) {
            // 初始化高度
            this.height = maxHeight;
        }
        // 图片宽度大于最大高度，则重置
        if (this.height > maxHeight) {
            // 重置图片高度
            this.height = maxHeight;
        }
    }
}
