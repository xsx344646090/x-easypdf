package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.BorderData;
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
     * 设置线条颜色
     *
     * @param color 颜色
     */
    public void setLineColor(Color color) {
        if (Objects.nonNull(color)) {
            this.borderConfiguration.setBorderColor(color);
        }
    }
    
    /**
     * 设置线条样式
     *
     * @param style 样式
     */
    public void setLineStyle(LineStyle style) {
        if (Objects.nonNull(style)) {
            this.borderConfiguration.setBorderLineStyle(style);
        }
    }
    
    /**
     * 设置线帽样式
     *
     * @param style 样式
     */
    public void setLineCapStyle(LineCapStyle style) {
        if (Objects.nonNull(style)) {
            this.borderConfiguration.setBorderLineCapStyle(style);
        }
    }
    
    /**
     * 设置线条长度
     *
     * @param length 长度
     */
    public void setLineLength(Float length) {
        if (Objects.nonNull(length)) {
            this.borderConfiguration.setBorderLineLength(length);
        }
    }
    
    /**
     * 设置线条宽度
     *
     * @param width 宽度
     */
    public void setLineWidth(Float width) {
        if (Objects.nonNull(width)) {
            this.borderConfiguration.setBorderLineWidth(width);
        }
    }
    
    /**
     * 设置点线间距
     *
     * @param spacing 间距
     */
    public void setDottedSpacing(Float spacing) {
        if (Objects.nonNull(spacing)) {
            this.borderConfiguration.setBorderDottedSpacing(spacing);
        }
    }
    
    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.SPLIT_LINE;
    }
    
    /**
     * 获取线条颜色
     *
     * @return 返回颜色
     */
    public Color getLineColor() {
        return this.borderConfiguration.getBorderTopColor();
    }
    
    /**
     * 获取线条样式
     *
     * @return 返回样式
     */
    public LineStyle getLineStyle() {
        return this.borderConfiguration.getBorderLineStyle();
    }
    
    /**
     * 获取线帽样式
     *
     * @return 返回样式
     */
    public LineCapStyle getLineCapStyle() {
        return this.borderConfiguration.getBorderLineCapStyle();
    }
    
    /**
     * 获取线条长度
     *
     * @return 返回长度
     */
    public Float getLineLength() {
        return this.borderConfiguration.getBorderLineLength();
    }
    
    /**
     * 获取线条宽度
     *
     * @return 返回宽度
     */
    public Float getLineWidth() {
        return this.borderConfiguration.getBorderLineWidth();
    }
    
    /**
     * 获取点线间距
     *
     * @return 返回间距
     */
    public Float getDottedSpacing() {
        return this.borderConfiguration.getBorderDottedSpacing();
    }
    
    /**
     * 初始化
     */
    @SuppressWarnings("all")
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化线长
        if (Objects.isNull(this.getLineLength())) {
            this.setLineLength(this.getContext().getWrapWidth() - this.getMarginLeft() - this.getMarginRight());
        }
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化透明度
        if (Objects.isNull(this.alpha)) {
            this.alpha = 1.0F;
        }
        // 开启上边框
        this.borderConfiguration.setIsBorder(false);
        this.borderConfiguration.setIsBorderTop(true);
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
        // 添加边框
        BorderUtil.drawNormalBorder(stream, CommonUtil.getRectangle(this.getLineLength(), this.getLineWidth()), BorderData.create(this, this.getBorderConfiguration()));
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
