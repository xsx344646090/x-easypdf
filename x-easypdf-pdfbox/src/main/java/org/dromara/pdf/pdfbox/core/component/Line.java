package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.Objects;

/**
 * 线条组件
 *
 * @author xsx
 * @date 2023/11/6
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
public class Line extends AbstractComponent {
    
    /**
     * 样式
     */
    protected LineStyle lineStyle;
    /**
     * 线帽样式
     */
    protected LineCapStyle lineCapStyle;
    /**
     * 线长
     */
    protected Float lineLength;
    /**
     * 线宽
     */
    protected Float lineWidth;
    /**
     * 线条颜色
     */
    protected Color lineColor;
    /**
     * 点线长度
     */
    protected Float dottedLength;
    /**
     * 点线间隔
     */
    protected Float dottedSpacing;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 透明度
     */
    protected Float alpha;
    
    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Line(Page page) {
        super(page);
    }
    
    
    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.LINE;
    }
    
    /**
     * 初始化
     */
    @SuppressWarnings("all")
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化样式
        if (Objects.isNull(this.lineStyle)) {
            this.lineStyle = LineStyle.SOLID;
        }
        // 初始化样式
        if (Objects.isNull(this.lineCapStyle)) {
            this.lineCapStyle = LineCapStyle.NORMAL;
        }
        // 初始化线长
        if (Objects.isNull(this.lineLength)) {
            this.lineLength = this.getContext().getWrapWidth() - this.getMarginLeft() - this.getMarginRight();
        }
        // 初始化线宽
        if (Objects.isNull(this.lineWidth)) {
            this.lineWidth = 1F;
        }
        // 初始化线条颜色
        if (Objects.isNull(this.lineColor)) {
            this.lineColor = Color.BLACK;
        }
        // 初始化点线长度
        if (Objects.isNull(this.dottedLength)) {
            this.dottedLength = 1F;
        }
        // 初始化点线间隔
        if (Objects.isNull(this.dottedSpacing)) {
            this.dottedSpacing = 1F;
        }
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化透明度
        if (Objects.isNull(this.alpha)) {
            this.alpha = 1.0F;
        }
        // 初始化起始XY轴坐标
        this.initBeginXY(this.getLineLength(), this.getLineWidth());
    }
    
    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getLineLength();
    }
    
    /**
     * 写入内容
     */
    @SneakyThrows
    @Override
    protected void writeContents() {
        // 新建内容流
        PDPageContentStream stream = this.initContentStream();
        // 初始化矩阵
        CommonUtil.initMatrix(stream, this.getBeginX(), this.getBeginY(), this.getRelativeBeginX(), this.getRelativeBeginY(), this.getLineLength(), this.getLineWidth(), this.getAngle(), this.getAlpha());
        // 定义X轴起始坐标
        float beginX = 0F;
        // 定义Y轴起始坐标
        float beginY = 0F;
        // 定义X轴结束坐标
        float endX = this.getLineLength();
        // 定义Y轴结束坐标
        float endY = 0F;
        // 判断样式
        switch (this.getLineStyle()) {
            // 实线
            case SOLID: {
                BorderUtil.drawSolidLine(stream, this.getLineColor(), beginX, beginY, endX, endY);
                break;
            }
            // 虚线
            case DOTTED: {
                BorderUtil.drawDottedLine(stream, this.getLineColor(), this.getDottedLength(), this.getDottedSpacing(), beginX, beginY, endX, endY);
                break;
            }
        }
        // 关闭内容流
        stream.close();
    }
    
    /**
     * 重置
     */
    @Override
    protected void reset() {
        // 获取X轴坐标
        float x = this.getBeginX() + this.getLineLength() + this.getMarginRight();
        // 获取Y轴坐标
        float y = this.getBeginY();
        // 重置
        super.reset(this.getType(), x, y);
    }
    
    /**
     * 初始化内容流
     *
     * @return 返回内容流
     */
    @SneakyThrows
    protected PDPageContentStream initContentStream() {
        // 创建内容流
        PDPageContentStream stream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 设置线宽
        stream.setLineWidth(this.getLineWidth());
        // 设置颜色
        stream.setStrokingColor(this.getLineColor());
        // 设置线帽样式
        stream.setLineCapStyle(this.getLineCapStyle().getType());
        // 返回内容流
        return stream;
    }
}
