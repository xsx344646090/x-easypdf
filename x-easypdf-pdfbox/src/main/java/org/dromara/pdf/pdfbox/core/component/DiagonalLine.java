package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.Objects;

/**
 * 对角线组件
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
public class DiagonalLine extends AbstractComponent {
    
    /**
     * 线宽
     */
    protected Float lineWidth;
    /**
     * 线条颜色
     */
    protected Color lineColor;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 透明度
     */
    protected Float alpha;
    /**
     * X轴结束坐标
     */
    protected Float endX;
    /**
     * Y轴结束坐标
     */
    protected Float endY;
    
    /**
     * 有参构造
     *
     * @param page 页面
     */
    public DiagonalLine(Page page) {
        super(page);
    }
    
    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.DIAGONAL_LINE;
    }
    
    /**
     * 初始化
     */
    @SuppressWarnings("all")
    @Override
    protected void init() {
        // 校验参数
        Objects.requireNonNull(this.beginX, "the begin x can not be null");
        Objects.requireNonNull(this.beginY, "the begin y can not be null");
        Objects.requireNonNull(this.endX, "the end x can not be null");
        Objects.requireNonNull(this.endY, "the end y can not be null");
        // 初始化
        super.init();
        // 初始化线宽
        if (Objects.isNull(this.lineWidth)) {
            this.lineWidth = 1F;
        }
        // 初始化线条颜色
        if (Objects.isNull(this.lineColor)) {
            this.lineColor = Color.BLACK;
        }
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化透明度
        if (Objects.isNull(this.alpha)) {
            this.alpha = 1.0F;
        }
    }
    
    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return 0F;
    }
    
    /**
     * 写入内容
     */
    @SneakyThrows
    @Override
    protected void writeContents() {
        if (this.getAngle() % 180 == 0) {
            processNotRotation();
        } else {
            processRotation();
        }
    }
    
    /**
     * 处理旋转
     */
    @SneakyThrows
    protected void processRotation() {
        // 获取X轴坐标
        float beginX = 0F;
        // 获取Y轴坐标
        float beginY = 0F;
        // 获取长度
        float width = this.getEndX() - this.getBeginX();
        // 获取宽度
        float height = Math.abs(this.getBeginY() - this.getEndY());
        // 新建内容流
        PDPageContentStream stream = this.initContentStream();
        // 初始化矩阵
        CommonUtil.initMatrix(stream, this.getBeginX(), this.getBeginY(), this.getRelativeBeginX(), this.getRelativeBeginY(), width, height, this.getAngle(), this.getAlpha());
        // 移动到x,y坐标点
        stream.moveTo(beginX, beginY);
        // 连线
        stream.lineTo(width, height);
        // 结束
        stream.stroke();
        // 关闭内容流
        stream.close();
    }
    
    /**
     * 处理非旋转
     */
    @SneakyThrows
    protected void processNotRotation() {
        // 新建内容流
        PDPageContentStream stream = this.initContentStream();
        // 移动到x,y坐标点
        stream.moveTo(this.getBeginX() + this.getRelativeBeginX(), this.getBeginY() + this.getRelativeBeginY());
        // 连线
        stream.lineTo(this.getEndX(), this.getEndY());
        // 结束
        stream.stroke();
        // 关闭内容流
        stream.close();
    }
    
    /**
     * 重置
     */
    @Override
    protected void reset() {
        // 重置
        super.reset(this.getType(), this.getEndX(), this.getEndY());
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
        stream.setLineCapStyle(LineCapStyle.NORMAL.getType());
        // 返回内容流
        return stream;
    }
}
