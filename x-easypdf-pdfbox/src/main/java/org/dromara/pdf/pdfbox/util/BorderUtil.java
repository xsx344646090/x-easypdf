package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.BorderParam;
import org.dromara.pdf.pdfbox.enums.BorderStyle;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/6/26
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class BorderUtil {

    /**
     * 绘制常规边框
     *
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox尺寸
     * @param param     边框参数
     */
    @SneakyThrows
    public static void drawNormalBorder(PDPageContentStream stream, PDRectangle rectangle, BorderParam param) {
        // 设置线宽
        stream.setLineWidth(param.getWidth());
        // 设置线帽样式
        stream.setLineCapStyle(0);
        // 连线
        line(stream, rectangle, param);
    }

    /**
     * 连线
     *
     * @param stream pdfbox内容流
     */
    private static void line(PDPageContentStream stream, PDRectangle rectangle, BorderParam param) {
        // 实线
        if (param.getStyle() == BorderStyle.SOLID) {
            // 绘制实线
            drawSolidLine(stream, rectangle, param);
            return;
        }
        // 虚线
        if (param.getStyle() == BorderStyle.DOTTED) {
            // 绘制虚线
            drawDottedLine(stream, rectangle, param);
        }
    }

    /**
     * 绘制实线
     *
     * @param stream pdfbox内容流
     */
    @SneakyThrows
    private static void drawSolidLine(PDPageContentStream stream, PDRectangle rectangle, BorderParam param) {
        // 绘制上边框
        if (param.getIsTop()) {
            // 设置颜色
            stream.setStrokingColor(param.getTopColor());
            // 移动到x,y坐标点
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getUpperRightY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getUpperRightY());
            // 结束
            stream.stroke();
        }
        // 绘制下边框
        if (param.getIsBottom()) {
            // 设置颜色
            stream.setStrokingColor(param.getBottomColor());
            // 移动到x,y坐标点
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY());
            // 结束
            stream.stroke();
        }
        // 绘制左边框
        if (param.getIsLeft()) {
            // 设置颜色
            stream.setStrokingColor(param.getLeftColor());
            // 移动到x,y坐标点
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getLowerLeftX(), rectangle.getUpperRightY());
            // 结束
            stream.stroke();
        }
        // 绘制右边框
        if (param.getIsRight()) {
            // 设置颜色
            stream.setStrokingColor(param.getRightColor());
            // 移动到x,y坐标点
            stream.moveTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getUpperRightY());
            // 结束
            stream.stroke();
        }
    }

    /**
     * 绘制虚线
     *
     * @param contentStream pdfbox内容流
     */
    @SneakyThrows
    private static void drawDottedLine(PDPageContentStream contentStream, PDRectangle rectangle, BorderParam param) {
        // 绘制上边框
        if (param.getIsTop()) {
            // 绘制上边框
            drawDottedLine(
                    contentStream,
                    param.getTopColor(),
                    param.getLineLength(),
                    param.getLineSpace(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY()
            );
        }
        // 绘制下边框
        if (param.getIsBottom()) {
            // 绘制下边框
            drawDottedLine(
                    contentStream,
                    param.getBottomColor(),
                    param.getLineLength(),
                    param.getLineSpace(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制左边框
        if (param.getIsLeft()) {
            // 绘制左边框
            drawDottedLine(
                    contentStream,
                    param.getLeftColor(),
                    param.getLineLength(),
                    param.getLineSpace(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY()
            );
        }
        // 绘制右边框
        if (param.getIsRight()) {
            // 绘制右边框
            drawDottedLine(
                    contentStream,
                    param.getLeftColor(),
                    param.getLineLength(),
                    param.getLineSpace(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY()
            );
        }
    }

    /**
     * 绘制虚线
     *
     * @param contentStream pdfbox内容流
     * @param color         边框颜色
     * @param beginX        X轴起始坐标
     * @param beginY        Y轴起始坐标
     * @param endX          X轴结束坐标
     * @param endY          Y轴结束坐标
     */
    @SneakyThrows
    private static void drawDottedLine(
            PDPageContentStream contentStream,
            Color color,
            float lineLength,
            float lineSpace,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 设置颜色
        contentStream.setStrokingColor(color);
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
        float lineWidth = lineLength + lineSpace;
        // 计算线条数量（向下取整，至少为1）
        int count = Math.max((int) (totalLength / (lineWidth)), 1);
        // 计算偏移量（居中计算）
        float offset = Math.abs((totalLength - (count * lineWidth) + lineSpace) / 2);
        // 如果点线长度大于总长度，则重置点线长度 = 总长度
        if (lineLength > totalLength) {
            // 重置点线长度 = 总长度
            lineLength = totalLength;
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
                beginX = beginX + lineLength;
                // 连线
                contentStream.lineTo(beginX, beginY);
                // 重置X轴坐标 = X轴坐标 + 点线间隔
                beginX = beginX + lineSpace;
            }
            // 否则垂直连线
            else {
                // 重置Y轴坐标 = Y轴坐标 - 点线长度
                beginY = beginY - lineLength;
                // 连线
                contentStream.lineTo(beginX, beginY);
                // 重置Y轴坐标 = Y轴坐标 - 点线间隔
                beginY = beginY - lineSpace;
            }
        }
        // 结束
        contentStream.stroke();
    }
}
