package wiki.xsx.core.pdf.component.circle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.Color;
import java.util.List;

/**
 * pdf圆形组件
 *
 * @author xsx
 * @date 2022/1/26
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfCircle implements XEasyPdfComponent {

    private static final long serialVersionUID = -4357177673484194277L;

    /**
     * pdf圆形参数
     */
    private final XEasyPdfCircleParam param = new XEasyPdfCircleParam();

    /**
     * 有参构造
     *
     * @param radius 半径
     */
    public XEasyPdfCircle(float radius) {
        this(radius, null, null);
    }

    /**
     * 有参构造
     *
     * @param radius 半径
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    public XEasyPdfCircle(float radius, Float beginX, Float beginY) {
        this.param.setRadius(Math.abs(radius)).setBeginX(beginX).setBeginY(beginY);
    }

    /**
     * 开启检查页面（自动分页）
     *
     * @return 返回圆形组件
     */
    public XEasyPdfCircle enableCheckPage() {
        this.param.setCheckPage(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭检查页面（自动分页）
     *
     * @return 返回圆形组件
     */
    public XEasyPdfCircle disableCheckPage() {
        this.param.setCheckPage(Boolean.FALSE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回圆形组件
     */
    @Override
    public XEasyPdfCircle enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置半径
     *
     * @param radius 半径
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setRadius(float radius) {
        this.param.setRadius(Math.abs(radius));
        return this;
    }

    /**
     * 设置是否有边框
     *
     * @param hasBorder 是否有边框
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setHasBorder(boolean hasBorder) {
        this.param.setHasBorder(hasBorder);
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setBorderWidth(float borderWidth) {
        this.param.setBorderWidth(Math.abs(borderWidth));
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.param.setBackgroundColor(backgroundColor);
        }
        return this;
    }

    /**
     * 设置边框颜色（设置是否有边框为true时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回圆形组件
     */
    public XEasyPdfCircle setBorderColor(Color borderColor) {
        if (borderColor != null) {
            this.param.setBorderColor(borderColor);
        }
        return this;
    }

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfCircle setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度（半径）
     *
     * @param width 宽度（半径）
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfCircle setWidth(float width) {
        this.param.setRadius(Math.abs(width));
        return this;
    }

    /**
     * 设置高度（半径）
     *
     * @param height 高度（半径）
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfCircle setHeight(float height) {
        this.param.setRadius(Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfCircle setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @SneakyThrows
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 参数初始化
        this.param.init(document, page);
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                document.getTarget(),
                page.getLastPage(),
                this.param.getContentMode().getMode(),
                true,
                this.param.getIsResetContext()
        );

        // 如果带有边框，则进行边框绘制
        if (this.param.getHasBorder()) {
            // 绘制边框圆形
            this.drawCircle(contentStream, this.param.getBorderColor());
            // 重置半径
            this.param.setRadius(this.param.getRadius() - this.param.getBorderWidth());
            // 绘制背景圆形
            this.drawCircle(contentStream, this.param.getBackgroundColor());
        }
        // 否则只绘制背景圆形
        else {
            // 绘制背景圆形
            this.drawCircle(contentStream, this.param.getBackgroundColor());
        }
        // 内容流重置颜色为黑色
        contentStream.setNonStrokingColor(Color.BLACK);
        // 关闭内容流
        contentStream.close();
        // 如果允许页面重置定位，则进行重置
        if (page.isAllowResetPosition()) {
            // 如果允许自动换行，则重置页面Y轴起始坐标
            if (this.param.getIsNewLine()) {
                // 重置页面X轴起始坐标
                page.setPageX(null);
                // 重置页面Y轴起始坐标
                page.setPageY(this.param.getBeginY());
            } else {
                // 重置页面X轴起始坐标
                page.setPageX(this.param.getBeginX() + this.param.getRadius());
            }
        }
    }

    /**
     * 绘制圆形
     *
     * @param contentStream 内容流
     * @param color         颜色
     */
    @SneakyThrows
    private void drawCircle(PDPageContentStream contentStream, Color color) {
        // 获取数据坐标点列表
        List<Point> dataPoints = this.param.initDataPoints();
        // 获取控制坐标点列表
        List<Point> ctrlPoints = this.param.initCtrlPoints(dataPoints);
        // 上数据坐标点
        Point dataTop = dataPoints.get(0);
        // 上右控制坐标点
        Point ctrlTopRight = ctrlPoints.get(0);
        // 右上控制坐标点
        Point ctrlRightTop = ctrlPoints.get(1);
        // 右数据坐标点
        Point dataRight = dataPoints.get(1);
        // 右下控制坐标点
        Point ctrlRightBottom = ctrlPoints.get(2);
        // 下右控制坐标点
        Point ctrlBottomRight = ctrlPoints.get(3);
        // 下数据坐标点
        Point dataBottom = dataPoints.get(2);
        // 下左控制坐标点
        Point ctrlBottomLeft = ctrlPoints.get(4);
        // 左下控制坐标点
        Point ctrlLeftBottom = ctrlPoints.get(5);
        // 左数据坐标点
        Point dataLeft = dataPoints.get(3);
        // 左上控制坐标点
        Point ctrlLeftTop = ctrlPoints.get(6);
        // 上左控制坐标点
        Point ctrlTopLeft = ctrlPoints.get(7);
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
     * 坐标点
     */
    @Data
    @AllArgsConstructor
    static class Point {
        /**
         * x轴坐标
         */
        private Float x;
        /**
         * y轴坐标
         */
        private Float y;
    }
}
