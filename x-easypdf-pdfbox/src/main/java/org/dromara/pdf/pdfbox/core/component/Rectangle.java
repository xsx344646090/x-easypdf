package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;

import java.awt.*;
import java.util.Objects;

/**
 * 矩形组件
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
public class Rectangle extends AbstractComponent {

    /**
     * 宽度
     */
    protected Float width;
    /**
     * 高度
     */
    protected Float height;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 边框颜色
     */
    protected Color borderColor;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Rectangle(Page page) {
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
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        // 初始化
        this.init();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
        }
        // 重置光标
        this.getContext().getCursor().reset(
                this.getBeginX() + this.getWidth() + this.getMarginRight(),
                this.getBeginY() - this.getHeight() - this.getMarginBottom()
        );
        // 重置
        super.reset(this.getType());
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 定义X轴偏移量
        float offsetX = 0.5F * this.getWidth();
        // 定义Y轴偏移量
        float offsetY = 0.5F * this.getHeight();
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 保存图形状态
        contentStream.saveGraphicsState();
        // 移动到中心点
        contentStream.transform(
                Matrix.getTranslateInstance(
                        this.getBeginX() + this.getRelativeBeginX() + offsetX,
                        this.getBeginY() - this.getRelativeBeginY() + offsetY
                )
        );
        // 旋转
        contentStream.transform(Matrix.getRotateInstance(Math.toRadians(this.getAngle()), 0, 0));
        // 移动到左下角
        contentStream.transform(Matrix.getTranslateInstance(-offsetX, -offsetY));
        // 添加矩形（边框矩形）
        contentStream.addRect(-this.getBorderConfiguration().getBorderWidth() / 2, this.getBorderConfiguration().getBorderWidth() / 2, this.getWidth(), this.getHeight());
        // 设置矩形颜色（边框颜色）
        contentStream.setNonStrokingColor(this.getBorderColor());
        // 填充矩形（边框矩形）
        contentStream.fill();
        // 添加背景
        if (Objects.nonNull(this.getBackgroundColor())) {
            // 添加矩形（背景矩形）
            contentStream.addRect(this.getBorderConfiguration().getBorderWidth() / 2, this.getBorderConfiguration().getBorderWidth() * 1.5F, this.getWidth() - this.getBorderConfiguration().getBorderWidth() * 2, this.getHeight() - this.getBorderConfiguration().getBorderWidth() * 2);
            // 设置矩形颜色（背景颜色）
            contentStream.setNonStrokingColor(this.getBackgroundColor());
            // 填充矩形（背景矩形）
            contentStream.fill();
        }
        // 恢复图形状态
        contentStream.restoreGraphicsState();
        // 关闭内容流
        contentStream.close();
        // 重置光标
        this.getContext().getCursor().reset(
                this.getBeginX() + this.getWidth() + this.getMarginRight(),
                this.getBeginY() + this.getHeight() - this.getMarginBottom()
        );
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化宽度与高度
        // this.initWidthAndHeight();
        // 初始化起始X轴坐标
        this.initBeginX(this.getWidth());
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化边框颜色
        if (Objects.isNull(this.borderColor)) {
            this.borderColor = Color.BLACK;
        }
        // 检查换行
        this.checkWrap();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            if (this.isPaging(this, this.getBeginY())) {
                this.setBeginY(this.getBeginY(), false);
            }
        }
    }

    /**
     * 检查换行
     */
    protected void checkWrap() {
        // 初始化X轴坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), Boolean.FALSE);
        }
        // 初始化换行
        if (this.isWrap()) {
            this.getContext().getCursor().reset(
                    this.getContext().getWrapBeginX(),
                    this.getContext().getCursor().getY() - this.getHeight()
            );
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft(), Boolean.FALSE);
        }
        // 初始化Y轴坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getContext().getCursor().getY() - this.getMarginTop(), Boolean.FALSE);
        }
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

    /**
     * 是否需要换行
     *
     * @return 返回布尔值，true为是，false为否
     */
    @Override
    protected boolean isNeedWrap() {
        return this.getContext().getWrapWidth() - this.getBeginX() < this.getWidth();
    }
}
