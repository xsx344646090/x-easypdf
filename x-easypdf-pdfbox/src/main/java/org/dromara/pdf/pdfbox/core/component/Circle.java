package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Position;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
        // 获取直径
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
        return this.getRadius() * 2;
    }

    /**
     * 初始化数据坐标点
     *
     * @param radius 半径
     * @return 返回数据坐标点列表
     */
    protected List<Position> initPosition(float radius) {
        // 获取X轴坐标（补偿半径）
        float x = this.getBeginX() + this.radius + this.getRelativeBeginX();
        // 获取Y轴坐标（补偿半径）
        float y = this.getBeginY() + this.radius - this.getRelativeBeginY();
        // 定义4个圆形数据坐标点
        List<Position> list = new ArrayList<>(4);
        // 添加数据上坐标点
        list.add(new Position(x, y + radius));
        // 添加数据右坐标点
        list.add(new Position(x + radius, y));
        // 添加数据下坐标点
        list.add(new Position(x, y - radius));
        // 添加数据左坐标点
        list.add(new Position(x - radius, y));
        // 返回数据坐标点列表
        return list;
    }

    /**
     * 初始化控制坐标点
     *
     * @return 返回控制坐标点列表
     */
    protected List<Position> initCtrlPosition(List<Position> positions) {
        // 计算补偿
        final float offset = this.getRadius() * 0.551915024494F;
        // 定义8个圆形控制坐标点
        List<Position> points = new ArrayList<>(8);
        // 获取数据上坐标点
        Position top = positions.get(0);
        // 添加上右控制坐标点
        points.add(new Position(top.getX() + offset, top.getY()));
        // 获取数据右坐标点
        Position right = positions.get(1);
        // 添加右上控制坐标点
        points.add(new Position(right.getX(), right.getY() + offset));
        // 添加右下控制坐标点
        points.add(new Position(right.getX(), right.getY() - offset));
        // 获取数据下坐标点
        Position bottom = positions.get(2);
        // 添加下右控制坐标点
        points.add(new Position(bottom.getX() + offset, bottom.getY()));
        // 添加下左控制坐标点
        points.add(new Position(bottom.getX() - offset, bottom.getY()));
        // 获取数据左坐标点
        Position left = positions.get(3);
        // 添加左下控制坐标点
        points.add(new Position(left.getX(), left.getY() - offset));
        // 添加左上控制坐标点
        points.add(new Position(left.getX(), left.getY() + offset));
        // 添加上左控制坐标点
        points.add(new Position(top.getX() - offset, top.getY()));
        // 返回控制坐标点列表
        return points;
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
            // 绘制边框圆形
            this.renderCircle(contentStream, this.getRadius(), this.getBorderColor());
            // 绘制背景圆形
            if (Objects.nonNull(this.getBackgroundColor())) {
                // 绘制背景圆形
                this.renderCircle(contentStream, this.getRadius() - this.getBorderConfiguration().getBorderLineWidth(), this.getBackgroundColor());
            }
            // 关闭内容流
            contentStream.close();
        }
    }

    /**
     * 绘制圆形
     *
     * @param contentStream 内容流
     * @param radius        半径
     * @param color         颜色
     */
    @SneakyThrows
    protected void renderCircle(PDPageContentStream contentStream, float radius, Color color) {
        // 获取数据坐标点列表
        List<Position> positions = this.initPosition(radius);
        // 获取控制坐标点列表
        List<Position> ctrlPositions = this.initCtrlPosition(positions);
        // 上数据坐标点
        Position dataTop = positions.get(0);
        // 上右控制坐标点
        Position ctrlTopRight = ctrlPositions.get(0);
        // 右上控制坐标点
        Position ctrlRightTop = ctrlPositions.get(1);
        // 右数据坐标点
        Position dataRight = positions.get(1);
        // 右下控制坐标点
        Position ctrlRightBottom = ctrlPositions.get(2);
        // 下右控制坐标点
        Position ctrlBottomRight = ctrlPositions.get(3);
        // 下数据坐标点
        Position dataBottom = positions.get(2);
        // 下左控制坐标点
        Position ctrlBottomLeft = ctrlPositions.get(4);
        // 左下控制坐标点
        Position ctrlLeftBottom = ctrlPositions.get(5);
        // 左数据坐标点
        Position dataLeft = positions.get(3);
        // 左上控制坐标点
        Position ctrlLeftTop = ctrlPositions.get(6);
        // 上左控制坐标点
        Position ctrlTopLeft = ctrlPositions.get(7);
        // 绘制圆形
        contentStream.moveTo(dataTop.getX(), dataTop.getY());
        // 连线
        contentStream.curveTo(
                ctrlTopRight.getX(), ctrlTopRight.getY(),
                ctrlRightTop.getX(), ctrlRightTop.getY(),
                dataRight.getX(), dataRight.getY()
        );
        // 连线
        contentStream.curveTo(
                ctrlRightBottom.getX(), ctrlRightBottom.getY(),
                ctrlBottomRight.getX(), ctrlBottomRight.getY(),
                dataBottom.getX(), dataBottom.getY()
        );
        // 连线
        contentStream.curveTo(
                ctrlBottomLeft.getX(), ctrlBottomLeft.getY(),
                ctrlLeftBottom.getX(), ctrlLeftBottom.getY(),
                dataLeft.getX(), dataLeft.getY()
        );
        // 连线
        contentStream.curveTo(
                ctrlLeftTop.getX(), ctrlLeftTop.getY(),
                ctrlTopLeft.getX(), ctrlTopLeft.getY(),
                dataTop.getX(), dataTop.getY()
        );
        // 设置圆形颜色
        contentStream.setNonStrokingColor(color);
        // 填充圆形
        contentStream.fill();
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
