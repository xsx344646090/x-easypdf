package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Context;

import java.awt.*;

/**
 * 边框工具
 *
 * @author xsx
 * @date 2023/6/26
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
public class BorderUtil {
    
    /**
     * 绘制实线
     *
     * @param stream pdfbox内容流
     * @param color  颜色
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @param endX   X轴结束坐标
     * @param endY   Y轴结束坐标
     */
    @SneakyThrows
    public static void drawSolidLine(
            PDPageContentStream stream,
            Color color,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 设置颜色
        stream.setStrokingColor(color);
        // 移动到x,y坐标点
        stream.moveTo(beginX, beginY);
        // 连线
        stream.lineTo(endX, endY);
        // 结束
        stream.stroke();
    }
    
    /**
     * 绘制虚线
     *
     * @param stream pdfbox内容流
     * @param color         颜色
     * @param dottedLength  点线长度
     * @param dottedSpacing 点线间隔
     * @param beginX        X轴起始坐标
     * @param beginY        Y轴起始坐标
     * @param endX          X轴结束坐标
     * @param endY          Y轴结束坐标
     */
    @SneakyThrows
    public static void drawDottedLine(
            PDPageContentStream stream,
            Color color,
            float dottedLength,
            float dottedSpacing,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 设置颜色
        stream.setStrokingColor(color);
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
        float lineWidth = dottedLength + dottedSpacing;
        // 计算线条数量（向下取整，至少为1）
        int count = Math.max((int) (totalLength / (lineWidth)), 1);
        // 计算偏移量（居中计算）
        float offset = Math.abs((totalLength - (count * lineWidth) + dottedSpacing) / 2);
        // 如果点线长度大于总长度，则重置点线长度 = 总长度
        if (dottedLength > totalLength) {
            // 重置点线长度 = 总长度
            dottedLength = totalLength;
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
            stream.moveTo(beginX, beginY);
            // 如果为水平，则水平连线
            if (isHorizontal) {
                // 重置X轴坐标 = X轴坐标 + 点线长度
                beginX = beginX + dottedLength;
                // 连线
                stream.lineTo(beginX, beginY);
                // 重置X轴坐标 = X轴坐标 + 点线间隔
                beginX = beginX + dottedSpacing;
            }
            // 否则垂直连线
            else {
                // 重置Y轴坐标 = Y轴坐标 - 点线长度
                beginY = beginY - dottedLength;
                // 连线
                stream.lineTo(beginX, beginY);
                // 重置Y轴坐标 = Y轴坐标 - 点线长度 - 点线间隔
                beginY = beginY - dottedLength - dottedSpacing;
            }
        }
        // 结束
        stream.stroke();
    }
    
    /**
     * 绘制边框（根据边框数据）
     *
     * @param data      边框数据
     * @param rectangle 尺寸
     */
    @SneakyThrows
    public static void drawBorderWithData(BorderData data, PDRectangle rectangle) {
        // 获取上下文
        Context context = data.getContext();
        // 初始化内容流
        PDPageContentStream stream = new PDPageContentStream(
                context.getTargetDocument(),
                context.getTargetPage(),
                data.getContentMode().getMode(),
                true,
                data.getIsResetContentStream()
        );
        // 绘制常规边框
        BorderUtil.drawNormalBorder(stream, rectangle, data);
        // 关闭内容流
        stream.close();
    }
    
    /**
     * 绘制常规边框
     *
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox尺寸
     * @param data      边框数据
     */
    @SneakyThrows
    public static void drawNormalBorder(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        // 设置线宽
        stream.setLineWidth(data.getBorderLineWidth());
        // 设置线帽样式
        stream.setLineCapStyle(data.getBorderLineCapStyle().getType());
        // 连线
        line(stream, rectangle, data);
    }
    
    /**
     * 连线
     *
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox页面尺寸
     * @param data      边框数据
     */
    private static void line(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        switch (data.getBorderLineStyle()) {
            case SOLID: {
                // 绘制实线
                drawSolidLine(stream, rectangle, data);
                break;
            }
            case DOTTED: {
                // 绘制虚线
                drawDottedLine(stream, rectangle, data);
                break;
            }
        }
    }
    
    /**
     * 绘制实线
     *
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox页面尺寸
     * @param data      边框数据
     */
    @SneakyThrows
    private static void drawSolidLine(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        // 绘制上边框
        if (data.getIsBorderTop()) {
            drawSolidLine(
                    stream,
                    data.getBorderTopColor(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY()
            );
        }
        // 绘制下边框
        if (data.getIsBorderBottom()) {
            drawSolidLine(
                    stream,
                    data.getBorderBottomColor(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制左边框
        if (data.getIsBorderLeft()) {
            drawSolidLine(
                    stream,
                    data.getBorderLeftColor(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY()
            );
        }
        // 绘制右边框
        if (data.getIsBorderRight()) {
            drawSolidLine(
                    stream,
                    data.getBorderRightColor(),
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
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox页面尺寸
     * @param data      边框数据
     */
    @SneakyThrows
    private static void drawDottedLine(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        // 绘制上边框
        if (data.getIsBorderTop()) {
            drawDottedLine(
                    stream,
                    data.getBorderTopColor(),
                    data.getBorderLineWidth(),
                    data.getBorderDottedSpacing(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY()
            );
        }
        // 绘制下边框
        if (data.getIsBorderBottom()) {
            drawDottedLine(
                    stream,
                    data.getBorderBottomColor(),
                    data.getBorderLineWidth(),
                    data.getBorderDottedSpacing(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制左边框
        if (data.getIsBorderLeft()) {
            drawDottedLine(
                    stream,
                    data.getBorderLeftColor(),
                    data.getBorderLineWidth(),
                    data.getBorderDottedSpacing(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制右边框
        if (data.getIsBorderRight()) {
            drawDottedLine(
                    stream,
                    data.getBorderRightColor(),
                    data.getBorderLineWidth(),
                    data.getBorderDottedSpacing(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
    }
}
