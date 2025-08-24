package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Position;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;
import org.dromara.pdf.pdfbox.support.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    /**
     * 绘制虚线
     *
     * @param stream        pdfbox内容流
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
        float offset = Math.abs((totalLength - (count * lineWidth) + dottedSpacing ) / 2);
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
                // 重置Y轴坐标 = Y轴坐标 - 点线间隔
                beginY = beginY - dottedSpacing;
            }
            // 关闭和描边
            stream.closeAndStroke();
        }
    }

    /**
     * 绘制边框（根据边框数据）
     *
     * @param data            边框数据
     * @param rectangle       尺寸
     * @param backgroundColor 背景颜色
     */
    @SneakyThrows
    public static void drawBorderWithData(
            BorderData data,
            PDRectangle rectangle,
            Color backgroundColor
    ) {
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
        drawNormalBorder(stream, rectangle, data, backgroundColor);
        // 关闭内容流
        stream.close();
        // 绘制加粗边框
        drawBolderBorder(rectangle, data);
    }

    /**
     * 绘制常规边框
     *
     * @param stream          pdfbox内容流
     * @param rectangle       pdfbox尺寸
     * @param data            边框数据
     * @param backgroundColor 背景颜色
     */
    @SneakyThrows
    public static void drawNormalBorder(
            PDPageContentStream stream,
            PDRectangle rectangle,
            BorderData data,
            Color backgroundColor
    ) {
        // 设置线宽
        stream.setLineWidth(data.getBorderLineWidth());
        // 设置线帽样式
        stream.setLineCapStyle(data.getBorderLineCapStyle().getType());
        // 设置背景颜色
        stream.setNonStrokingColor(backgroundColor);
        // 连线
        line(stream, rectangle, data);
    }

    /**
     * 绘制加粗边框
     *
     * @param rectangle pdfbox尺寸
     * @param data      边框数据
     */
    @SneakyThrows
    public static void drawBolderBorder(PDRectangle rectangle, BorderData data) {
        Context context = data.getContext();
        switch (data.getBorderLineStyle()) {
            // 实线
            case SOLID: {
                if (data.getIsBorderTop() && data.getBorderTopLineWidth() > data.getBorderLineWidth()) {
                    drawBolderSolidLine(context, data, data.getBorderTopColor(), data.getBorderTopLineWidth(), rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getUpperRightX(), rectangle.getUpperRightY());
                }
                if (data.getIsBorderBottom() && data.getBorderBottomLineWidth() > data.getBorderLineWidth()) {
                    drawBolderSolidLine(context, data, data.getBorderBottomColor(), data.getBorderBottomLineWidth(), rectangle.getLowerLeftX(), rectangle.getLowerLeftY(), rectangle.getUpperRightX(), rectangle.getLowerLeftY());
                }
                if (data.getIsBorderLeft() && data.getBorderLeftLineWidth() > data.getBorderLineWidth()) {
                    drawBolderSolidLine(context, data, data.getBorderLeftColor(), data.getBorderLeftLineWidth(), rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
                }
                if (data.getIsBorderRight() && data.getBorderRightLineWidth() > data.getBorderLineWidth()) {
                    drawBolderSolidLine(context, data, data.getBorderRightColor(), data.getBorderRightLineWidth(), rectangle.getUpperRightX(), rectangle.getUpperRightY(), rectangle.getUpperRightX(), rectangle.getLowerLeftY());
                }
                break;
            }
            // 虚线
            case DOTTED: {
                if (data.getIsBorderTop() && data.getBorderTopLineWidth() > data.getBorderLineWidth()) {
                    drawBolderDottedLine(context, data, data.getBorderTopColor(), data.getBorderTopLineWidth(), data.getBorderLineLength(), data.getBorderDottedSpacing(), rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getUpperRightX(), rectangle.getUpperRightY());
                }
                if (data.getIsBorderBottom() && data.getBorderBottomLineWidth() > data.getBorderLineWidth()) {
                    drawBolderDottedLine(context, data, data.getBorderBottomColor(), data.getBorderBottomLineWidth(), data.getBorderLineLength(), data.getBorderDottedSpacing(), rectangle.getLowerLeftX(), rectangle.getLowerLeftY(), rectangle.getUpperRightX(), rectangle.getLowerLeftY());
                }
                if (data.getIsBorderLeft() && data.getBorderLeftLineWidth() > data.getBorderLineWidth()) {
                    drawBolderDottedLine(context, data, data.getBorderLeftColor(), data.getBorderLeftLineWidth(), data.getBorderLineLength(), data.getBorderDottedSpacing(), rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
                }
                if (data.getIsBorderRight() && data.getBorderRightLineWidth() > data.getBorderLineWidth()) {
                    drawBolderDottedLine(context, data, data.getBorderRightColor(), data.getBorderRightLineWidth(), data.getBorderLineLength(), data.getBorderDottedSpacing(), rectangle.getUpperRightX(), rectangle.getUpperRightY(), rectangle.getUpperRightX(), rectangle.getLowerLeftY());
                }
                break;
            }
        }
    }

    /**
     * 绘制加粗实线边框
     *
     * @param context 上下文
     * @param data    边框数据
     * @param color   颜色
     * @param width   宽度
     * @param beginX  起始X轴坐标
     * @param beginY  起始Y轴坐标
     * @param endX    结束X轴坐标
     * @param endY    结束Y轴坐标
     */
    @SneakyThrows
    public static void drawBolderSolidLine(
            Context context,
            BorderData data,
            Color color,
            float width,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 初始化内容流
        PDPageContentStream stream = new PDPageContentStream(
                context.getTargetDocument(),
                context.getTargetPage(),
                data.getContentMode().getMode(),
                true,
                data.getIsResetContentStream()
        );
        // 设置线宽
        stream.setLineWidth(width);
        // 设置线帽样式
        stream.setLineCapStyle(data.getBorderLineCapStyle().getType());
        // 连线
        drawSolidLine(stream, color, beginX, beginY, endX, endY);
        // 描边
        stream.stroke();
        // 关闭
        stream.close();
    }

    /**
     * 绘制加粗实线边框
     *
     * @param context       上下文
     * @param data          边框数据
     * @param color         颜色
     * @param width         宽度
     * @param dottedLength  点线长度
     * @param dottedSpacing 点线间隔
     * @param beginX        X轴起始坐标
     * @param beginY        Y轴起始坐标
     * @param endX          X轴结束坐标
     * @param endY          Y轴结束坐标
     */
    @SneakyThrows
    public static void drawBolderDottedLine(
            Context context,
            BorderData data,
            Color color,
            float width,
            float dottedLength,
            float dottedSpacing,
            float beginX,
            float beginY,
            float endX,
            float endY
    ) {
        // 初始化内容流
        PDPageContentStream stream = new PDPageContentStream(
                context.getTargetDocument(),
                context.getTargetPage(),
                data.getContentMode().getMode(),
                true,
                data.getIsResetContentStream()
        );
        // 设置线宽
        stream.setLineWidth(width);
        // 设置线帽样式
        stream.setLineCapStyle(data.getBorderLineCapStyle().getType());
        // 连线
        drawDottedLine(stream, color, dottedLength, dottedSpacing, beginX, beginY, endX, endY);
        // 描边
        stream.stroke();
        // 关闭
        stream.close();
    }

    /**
     * 获取圆形数据坐标点
     *
     * @param radius 半径
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回数据坐标点列表
     */
    public static List<Position> getCircleDataPositions(float radius, float beginX, float beginY) {
        // 获取X轴坐标（补偿半径）
        float x = beginX + radius;
        // 获取Y轴坐标（补偿半径）
        float y = beginY + radius;
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
     * 获取圆形控制坐标点
     *
     * @param radius        半径
     * @param dataPositions 数据坐标点
     * @return 返回控制坐标点列表
     */
    public static List<Position> getCircleCtrlPositions(float radius, List<Position> dataPositions) {
        // 计算补偿
        final float offset = radius * 0.551915024494F;
        // 定义8个圆形控制坐标点
        List<Position> points = new ArrayList<>(8);
        // 获取数据上坐标点
        Position top = dataPositions.get(0);
        // 添加上右控制坐标点
        points.add(new Position(top.getX() + offset, top.getY()));
        // 获取数据右坐标点
        Position right = dataPositions.get(1);
        // 添加右上控制坐标点
        points.add(new Position(right.getX(), right.getY() + offset));
        // 添加右下控制坐标点
        points.add(new Position(right.getX(), right.getY() - offset));
        // 获取数据下坐标点
        Position bottom = dataPositions.get(2);
        // 添加下右控制坐标点
        points.add(new Position(bottom.getX() + offset, bottom.getY()));
        // 添加下左控制坐标点
        points.add(new Position(bottom.getX() - offset, bottom.getY()));
        // 获取数据左坐标点
        Position left = dataPositions.get(3);
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
     * 连线
     *
     * @param stream    pdfbox内容流
     * @param rectangle pdfbox页面尺寸
     * @param data      边框数据
     */
    private static void line(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        switch (data.getBorderLineStyle()) {
            // 实线
            case SOLID: {
                if (data.getBorderLineCapStyle() == LineCapStyle.ROUND && data.getBorderRadius() > 0) {
                    // 绘制圆角线
                    drawRoundLine(stream, rectangle, data);
                }else {
                    // 绘制实线
                    drawSolidLine(stream, rectangle, data);
                }
                break;
            }
            // 虚线
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
        // 绘制右边框
        if (data.getIsBorderRight()) {
            drawSolidLine(
                    stream,
                    data.getBorderRightColor(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制下边框
        if (data.getIsBorderBottom()) {
            drawSolidLine(
                    stream,
                    data.getBorderRightColor(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY()
            );
        }
        // 绘制左边框
        if (data.getIsBorderLeft()) {
            drawSolidLine(
                    stream,
                    data.getBorderRightColor(),
                    rectangle.getLowerLeftX(),
                    rectangle.getLowerLeftY(),
                    rectangle.getLowerLeftX(),
                    rectangle.getUpperRightY()
            );
        }
        // 描边
        stream.stroke();
    }

    /**
     * 绘制圆角线
     * @param stream
     * @param rectangle
     * @param data
     */
    @SneakyThrows
    private static void drawRoundLine(PDPageContentStream stream, PDRectangle rectangle, BorderData data) {
        // 定义补偿
        float offset = data.getBorderRadius() * Constants.BEZIER_COEF;
        // 上
        stream.moveTo(rectangle.getLowerLeftX() + data.getBorderRadius(), rectangle.getUpperRightY());
        stream.setStrokingColor(data.getBorderTopColor());
        stream.lineTo(rectangle.getUpperRightX() - data.getBorderRadius(), rectangle.getUpperRightY());
        // 右
        stream.setStrokingColor(data.getBorderRightColor());
        drawArcForRightTop(stream, data.getBorderRadius(), offset, rectangle.getUpperRightX() - data.getBorderRadius(), rectangle.getUpperRightY());
        stream.lineTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY() + data.getBorderRadius());
        drawArcForRightBottom(stream, data.getBorderRadius(), offset, rectangle.getUpperRightX(), rectangle.getLowerLeftY() + data.getBorderRadius());
        // 下
        stream.setStrokingColor(data.getBorderBottomColor());
        stream.lineTo(rectangle.getLowerLeftX() + data.getBorderRadius(), rectangle.getLowerLeftY());
        // 左
        stream.setStrokingColor(data.getBorderLeftColor());
        drawArcForLeftBottom(stream, data.getBorderRadius(), offset, rectangle.getLowerLeftX() + data.getBorderRadius(), rectangle.getLowerLeftY());
        stream.lineTo(rectangle.getLowerLeftX(), rectangle.getUpperRightY() - data.getBorderRadius());
        drawArcForLeftTop(stream, data.getBorderRadius(), offset, rectangle.getLowerLeftX(), rectangle.getUpperRightY() - data.getBorderRadius());
        stream.closePath();
        stream.fillAndStroke();
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
                    data.getBorderLineLength(),
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
                    data.getBorderLineLength(),
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
                    data.getBorderLineLength(),
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
                    data.getBorderLineLength(),
                    data.getBorderDottedSpacing(),
                    rectangle.getUpperRightX(),
                    rectangle.getUpperRightY(),
                    rectangle.getUpperRightX(),
                    rectangle.getLowerLeftY()
            );
        }
        stream.stroke();
    }

    /**
     * 绘制右上圆弧
     *
     * @param stream pdfbox内容流
     * @param radius 半径
     * @param offset 偏移量
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    private static void drawArcForRightTop(
            PDPageContentStream stream,
            float radius,
            float offset,
            float beginX,
            float beginY
    ) {
        // 绘制右上
        stream.curveTo(beginX + offset, beginY, beginX + radius, beginY - radius + offset, beginX + radius, beginY - radius);
    }

    /**
     * 绘制右下圆弧
     *
     * @param stream pdfbox内容流
     * @param radius 半径
     * @param offset 偏移量
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    private static void drawArcForRightBottom(
            PDPageContentStream stream,
            float radius,
            float offset,
            float beginX,
            float beginY
    ) {
        // 绘制右下
        stream.curveTo(beginX, beginY - offset, beginX - radius + offset, beginY - radius, beginX - radius, beginY - radius);
    }

    /**
     * 绘制左上圆弧
     *
     * @param stream pdfbox内容流
     * @param radius 半径
     * @param offset 偏移量
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    private static void drawArcForLeftTop(
            PDPageContentStream stream,
            float radius,
            float offset,
            float beginX,
            float beginY
    ) {
        // 绘制左上
        stream.curveTo(beginX, beginY + offset, beginX + radius - offset, beginY + radius, beginX + radius, beginY + radius);
    }

    /**
     * 绘制左下圆弧
     *
     * @param stream pdfbox内容流
     * @param radius 半径
     * @param offset 偏移量
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    private static void drawArcForLeftBottom(
            PDPageContentStream stream,
            float radius,
            float offset,
            float beginX,
            float beginY
    ) {
        // 绘制左下
        stream.curveTo(beginX - offset, beginY, beginX - radius, beginY + radius - offset, beginX - radius, beginY + radius);
    }
}
