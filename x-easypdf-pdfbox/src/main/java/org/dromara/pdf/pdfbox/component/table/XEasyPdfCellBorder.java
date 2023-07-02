package org.dromara.pdf.pdfbox.component.table;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.*;

/**
 * pdf单元格边框
 *
 * @author xsx
 * @date 2022/2/1
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * this.beginX-easypdf is licensed under the Mulan PSL v2.
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
@Accessors(chain = true)
final class XEasyPdfCellBorder {

    /**
     * pdfbox文档
     */
    private PDDocument document;
    /**
     * pdfbox页面
     */
    private PDPage page;
    /**
     * 内容模式
     */
    private PDPageContentStream.AppendMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;
    /**
     * X轴起始坐标
     */
    private Float beginX;
    /**
     * Y轴起始坐标
     */
    private Float beginY;
    /**
     * 左边框颜色
     */
    private Color leftBorderColor;
    /**
     * 右边框颜色
     */
    private Color rightBorderColor;
    /**
     * 上边框颜色
     */
    private Color topBorderColor;
    /**
     * 下边框颜色
     */
    private Color bottomBorderColor;
    /**
     * 边框宽度
     */
    private Float borderWidth;
    /**
     * 边框点线长度
     */
    private Float borderLineLength;
    /**
     * 边框点线间隔
     */
    private Float borderLineSpace;
    /**
     * 是否带有上边框
     */
    private Boolean hasTopBorder = Boolean.TRUE;
    /**
     * 是否带有下边框
     */
    private Boolean hasBottomBorder = Boolean.TRUE;
    /**
     * 是否带有左边框
     */
    private Boolean hasLeftBorder = Boolean.TRUE;
    /**
     * 是否带有右边框
     */
    private Boolean hasRightBorder = Boolean.TRUE;

    /**
     * 绘制边框
     */
    @SneakyThrows
    void drawBorder() {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(this.document, this.page, this.contentMode, true, this.isResetContext);
        // 设置线宽
        contentStream.setLineWidth(this.borderWidth);
        // 设置线帽样式
        contentStream.setLineCapStyle(0);
        // 连线
        this.line(contentStream);
        // 设置颜色
        contentStream.setStrokingColor(Color.BLACK);
        // 关闭内容流
        contentStream.close();
        // 重置pdfbox文档为空
        this.document = null;
        // 重置pdfbox页面为空
        this.page = null;
    }

    /**
     * 连线
     *
     * @param contentStream pdfbox内容流
     */
    private void line(PDPageContentStream contentStream) {
        // 定义最小值
        float min = 1F;
        // 如果点线长度不为空且大于最小值且点线间隔不为空且大于最小值，则绘制虚线
        if (this.borderLineLength != null && this.borderLineLength >= min && this.borderLineSpace != null && this.borderLineSpace >= min) {
            // 绘制虚线
            this.drawDottedLine(contentStream);
        }
        // 否则绘制实线
        else {
            // 绘制实线
            this.drawSolidLine(contentStream);
        }
    }

    /**
     * 绘制实线
     *
     * @param contentStream pdfbox内容流
     */
    @SneakyThrows
    private void drawSolidLine(PDPageContentStream contentStream) {
        // 定义X轴坐标
        float beginX = this.beginX;
        // 定义Y轴坐标
        float beginY = this.beginY;
        // 如果包含上边框，则绘制上边框
        if (this.hasTopBorder) {
            // 设置颜色
            contentStream.setStrokingColor(this.topBorderColor);
            // 移动到x,y坐标点
            contentStream.moveTo(beginX, beginY);
            // 重置X轴坐标为X轴坐标+宽度
            beginX = this.beginX + this.width;
            // 连线
            contentStream.lineTo(beginX, beginY);
            // 结束
            contentStream.stroke();
        }
        // 如果包含下边框，则绘制下边框
        if (this.hasBottomBorder) {
            // 设置颜色
            contentStream.setStrokingColor(this.bottomBorderColor);
            // 重置X轴坐标为起始坐标
            beginX = this.beginX;
            // 重置Y轴坐标为Y轴坐标-高度
            beginY = this.beginY - this.height;
            // 移动到x,y坐标点
            contentStream.moveTo(beginX, beginY);
            // 重置X轴坐标为X轴坐标+宽度
            beginX = this.beginX + this.width;
            // 连线
            contentStream.lineTo(beginX, beginY);
            // 结束
            contentStream.stroke();
        }
        // 如果包含左边框，则绘制左边框
        if (this.hasLeftBorder) {
            // 设置颜色
            contentStream.setStrokingColor(this.leftBorderColor);
            // 重置X轴坐标为起始坐标
            beginX = this.beginX;
            // 重置Y轴坐标为起始坐标
            beginY = this.beginY;
            // 移动到x,y坐标点
            contentStream.moveTo(beginX, beginY);
            // 重置Y轴坐标为Y轴坐标-高度
            beginY = this.beginY - this.height;
            // 连线
            contentStream.lineTo(beginX, beginY);
            // 结束
            contentStream.stroke();
        }
        // 如果包含右边框，则绘制右边框
        if (this.hasRightBorder) {
            // 设置颜色
            contentStream.setStrokingColor(this.rightBorderColor);
            // 重置X轴坐标为X轴坐标+宽度
            beginX = this.beginX + this.width;
            // 重置Y轴坐标为起始坐标
            beginY = this.beginY;
            // 移动到x,y坐标点
            contentStream.moveTo(beginX, beginY);
            // 重置Y轴坐标为Y轴坐标-高度
            beginY = this.beginY - this.height;
            // 连线
            contentStream.lineTo(beginX, beginY);
            // 结束
            contentStream.stroke();
        }
    }

    /**
     * 绘制虚线
     *
     * @param contentStream pdfbox内容流
     */
    @SneakyThrows
    private void drawDottedLine(PDPageContentStream contentStream) {
        // 定义X轴坐标
        float endX = this.beginX + this.width;
        // 定义Y轴坐标
        float endY = this.beginY - this.height;
        // 如果包含上边框，则绘制上边框
        if (this.hasTopBorder) {
            // 绘制上边框
            this.drawDottedLine(contentStream, this.topBorderColor, this.beginX, this.beginY, endX, this.beginY);
        }
        // 如果包含下边框，则绘制下边框
        if (this.hasBottomBorder) {
            // 绘制下边框
            this.drawDottedLine(contentStream, this.bottomBorderColor, this.beginX, endY, endX, endY);
        }
        // 如果包含左边框，则绘制左边框
        if (this.hasLeftBorder) {
            // 绘制左边框
            this.drawDottedLine(contentStream, this.leftBorderColor, this.beginX, this.beginY, this.beginX, endY);
        }
        // 如果包含右边框，则绘制右边框
        if (this.hasRightBorder) {
            // 绘制右边框
            this.drawDottedLine(contentStream, this.rightBorderColor, endX, this.beginY, endX, endY);
        }
    }

    /**
     * 绘制虚线
     *
     * @param contentStream pdfbox内容流
     * @param borderColor   边框颜色
     * @param beginX        X轴起始坐标
     * @param beginY        Y轴起始坐标
     * @param endX          X轴结束坐标
     * @param endY          Y轴结束坐标
     */
    @SneakyThrows
    private void drawDottedLine(
            PDPageContentStream contentStream,
            Color borderColor,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 设置颜色
        contentStream.setStrokingColor(borderColor);
        // 计算总长度
        float totalLength = endX - beginX;
        // 判断是否水平
        boolean isHorizontal = totalLength > 0;
        // 如果非水平，则重置总长度
        if (!isHorizontal) {
            // 重置总长度
            totalLength = beginY - endY;
        }
        // 计算线长
        float lineWidth = this.borderLineLength + this.borderLineSpace;
        // 计算线条数量（向下取整，至少为1）
        int count = Math.max((int) (totalLength / (lineWidth)), 1);
        // 计算偏移量（居中计算）
        float offset = Math.abs((totalLength - (count * lineWidth) + this.borderLineSpace) / 2);
        // 如果点线长度大于总长度，则重置点线长度 = 总长度
        if (this.borderLineLength > totalLength) {
            // 重置点线长度 = 总长度
            this.borderLineLength = totalLength;
            // 重置偏移量为0
            offset = 0F;
        }
        // 如果为水平，则重置X轴起始坐标
        if (isHorizontal) {
            // 重置X轴起始坐标
            beginX = beginX + offset;
        }
        // 否则重置Y轴起始坐标
        else {
            // 重置Y轴起始坐标
            beginY = beginY - offset;
        }
        // 循环连线
        for (int i = 0; i < count; i++) {
            // 移动到X,Y坐标点
            contentStream.moveTo(beginX, beginY);
            // 如果为水平，则水平连线
            if (isHorizontal) {
                // 重置X轴坐标 = X轴坐标 + 点线长度
                beginX = beginX + this.borderLineLength;
                // 连线
                contentStream.lineTo(beginX, beginY);
                // 重置X轴坐标 = X轴坐标 + 点线间隔
                beginX = beginX + this.borderLineSpace;
            }
            // 否则垂直连线
            else {
                // 重置Y轴坐标 = Y轴坐标 - 点线长度
                beginY = beginY - this.borderLineLength;
                // 连线
                contentStream.lineTo(beginX, beginY);
                // 重置Y轴坐标 = Y轴坐标 - 点线间隔
                beginY = beginY - this.borderLineSpace;
            }
        }
        // 结束
        contentStream.stroke();
    }
}
