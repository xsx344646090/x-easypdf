package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.support.Constants;

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
     * 半径
     */
    protected Float radius;
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
    public Circle(Page page) {
        super(page);
    }

    /**
     * 设置半径
     *
     * @param radius 半径
     */
    public void setRadius(float radius) {
        if (radius < 1) {
            throw new IllegalArgumentException("the radius can not be less than 1");
        }
        this.radius = radius;
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.CIRCLE;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 校验参数
        Objects.requireNonNull(this.radius, "the radius can not be null");
        // 初始化
        super.init();
        // 初始化边框颜色
        if (Objects.isNull(this.borderColor)) {
            this.borderColor = Color.BLACK;
        }
        // 初始化边框颜色
        if (Objects.isNull(this.backgroundColor)) {
            this.backgroundColor = this.getPage().getBackgroundColor();
        }
        // 获取半径
        float diameter = this.getMinWidth();
        // 初始化起始XY轴坐标
        this.initBeginXY(diameter, diameter);
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getRadius();
    }

    /**
     * 写入内容
     */
    @SneakyThrows
    @Override
    protected void writeContents() {
        if (!this.getContext().getIsVirtualRender()) {
            // 新建内容流
            PDPageContentStream contentStream = new PDPageContentStream(
                    this.getContext().getTargetDocument(),
                    this.getContext().getTargetPage(),
                    this.getContentMode().getMode(),
                    true,
                    this.getIsResetContentStream()
            );
            // 设置背景颜色
            contentStream.setNonStrokingColor(this.getBackgroundColor());
            // 设置边框颜色
            contentStream.setStrokingColor(this.getBorderColor());
            // 绘制边框圆形
            this.renderCircle(contentStream, this.getRadius());
            // 关闭内容流
            contentStream.close();
        }
    }

    /**
     * 绘制圆形
     *
     * @param contentStream 内容流
     * @param radius        半径
     */
    @SneakyThrows
    protected void renderCircle(PDPageContentStream contentStream, float radius) {
        // 计算补偿
        final float offset = radius * Constants.BEZIER_COEF;
        // 获取X轴坐标
        float beginX = this.getBeginX() + this.getRelativeBeginX();
        // 获取Y轴坐标
        float beginY = this.getBeginY() + this.getRelativeBeginX();
        // 移动坐标
        contentStream.moveTo(beginX, beginY + radius);
        // 绘制右上
        contentStream.curveTo(beginX + offset, beginY + radius, beginX + radius, beginY + offset, beginX + radius, beginY);
        // 绘制右下
        contentStream.curveTo(beginX + radius, beginY - offset, beginX + offset, beginY - radius, beginX, beginY - radius);
        // 绘制左下
        contentStream.curveTo(beginX - offset, beginY - radius, beginX - radius, beginY - offset, beginX - radius, beginY);
        // 绘制左上
        contentStream.curveTo(beginX - radius, beginY + offset, beginX - offset, beginY + radius, beginX, beginY + radius);
        // 填充并闭合
        contentStream.fillAndStroke();
    }

    /**
     * 重置
     */
    protected void reset() {
        // 获取X轴坐标
        float x = this.getBeginX() + this.getMinWidth() + this.getMarginRight();
        // 获取Y轴坐标
        float y = this.getBeginY();
        // 重置
        super.reset(this.getType(), x, y);
    }
}
