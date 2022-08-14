package wiki.xsx.core.pdf.component.line;

import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.*;

/**
 * 虚线分割线组件
 *
 * @author xsx
 * @date 2020/3/4
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
public class XEasyPdfDottedSplitLine implements XEasyPdfLine {

    private static final long serialVersionUID = -8849448173029414111L;

    /**
     * 分割线参数
     */
    private final XEasyPdfLineParam param = new XEasyPdfLineParam();
    /**
     * 点线长度
     */
    private Float lineLength = 10F;
    /**
     * 点线间隔
     */
    private Float lineSpace = 10F;

    /**
     * 无参构造
     */
    public XEasyPdfDottedSplitLine() {
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XEasyPdfDottedSplitLine setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XEasyPdfDottedSplitLine setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XEasyPdfDottedSplitLine setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置分割线宽度
     *
     * @param lineWidth 分割线宽度
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setLineWidth(float lineWidth) {
        this.param.setLineWidth(Math.abs(lineWidth));
        return this;
    }

    /**
     * 设置分割线颜色
     *
     * @param color 分割线颜色
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setColor(Color color) {
        if (color != null) {
            this.param.setColor(color);
        }
        return this;
    }

    /**
     * 设置分割线线型
     *
     * @param lineCapStyle 分割线线型
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setLineCapStyle(XEasyPdfLineCapStyle lineCapStyle) {
        if (lineCapStyle != null) {
            this.param.setStyle(lineCapStyle);
        }
        return this;
    }

    /**
     * 设置点线长度
     *
     * @param lineLength 点线长度
     * @return 返回虚线分割线组件
     */
    public XEasyPdfDottedSplitLine setLineLength(float lineLength) {
        this.lineLength = Math.abs(lineLength);
        return this;
    }

    /**
     * 设置点线间隔
     *
     * @param lineSpace 点线间隔
     * @return 返回虚线分割线组件
     */
    public XEasyPdfDottedSplitLine setLineSpace(float lineSpace) {
        this.lineSpace = Math.abs(lineSpace);
        return this;
    }

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度(线长)
     *
     * @param width 宽度(线长)
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setHeight(float height) {
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 获取线条宽度
     *
     * @return 返回线条宽度
     */
    @Override
    public float getLineWidth() {
        return this.param.getLineWidth();
    }

    /**
     * 开启上下文重置
     *
     * @return 返回虚线分割线组件
     */
    @Override
    public XEasyPdfDottedSplitLine enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化虚线分割线参数
        this.init(document, page);
        // 定义线条组件
        XEasyPdfBaseLine xpdfLine;
        // 初始化线条组件
        xpdfLine = new XEasyPdfBaseLine(this.param);
        // 执行画图
        xpdfLine.draw(document, page);
        // 定义点线数量
        int count;
        // 如果宽度未初始化，则计算点线数量为(pdfBox最新页面宽度 - 左边距 - 右边距) / (点线长度 + 点线间隔)
        if (this.param.getWidth() == null) {
            // 点线数量 = (pdfBox最新页面宽度-左边距-右边距)/(点线长度+点线间隔)
            count = (int) Math.floor(
                    (page.getLastPage().getMediaBox().getWidth() - this.param.getMarginLeft() - this.param.getMarginRight())
                            /
                            (this.lineLength + this.lineSpace)
            );
        }
        // 否则计算点线数量为(宽度-左边距-右边距)/(点线长度+点线间隔)
        else {
            // 点线数量 = (宽度-左边距-右边距)/(点线长度+点线间隔)
            count = (int) Math.floor((this.param.getWidth() - this.param.getMarginLeft() - this.param.getMarginRight()) / (this.lineLength + this.lineSpace));
        }
        // 循环点线数量进行画图
        for (int j = 1; j <= count; j++) {
            // 设置页面X轴起始坐标，起始坐标 = 结束坐标 + 点线间隔
            this.param.setBeginX(
                    this.param.getEndX() + this.lineSpace
                    // 设置页面X轴结束坐标，结束坐标 = 起始坐标 + 点线长度
            ).setEndX(
                    this.param.getBeginX() + this.lineLength
            );
            // 重新初始化线条组件
            xpdfLine = new XEasyPdfBaseLine(this.param);
            // 执行画图
            xpdfLine.draw(document, page);
        }
        // 如果允许重置定位，则重置页面坐标
        if (page.isAllowResetPosition()) {
            // 设置pdf页面Y轴起始坐标，起始坐标 = 起始坐标 - 线宽 / 2
            page.setPageY(this.param.getBeginY() - this.param.getLineWidth() / 2);
        }
        // 重置页面X轴与Y轴起始坐标
        this.param.setBeginX(null).setBeginY(null);
    }

    /**
     * 初始化参数
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    private void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 分页检查
        this.param.checkPage(document, page);
        // 初始化参数
        this.param.init(document, page);
        // 如果X轴起始坐标已初始化且Y轴起始坐标已初始化，则设置X轴结束坐标与Y轴结束坐标
        if (this.param.getBeginX() != null && this.param.getBeginY() != null) {
            // 设置X轴结束坐标与Y轴结束坐标
            this.param.setEndX(this.param.getBeginX() + this.lineLength).setEndY(this.param.getBeginY());
            // 返回
            return;
        }
        // 定义线宽
        float lineWidth = this.param.getLineWidth() / 2;
        // 设置X轴Y轴起始结束坐标
        this.param.setBeginX(
                // 左边距
                this.param.getMarginLeft()
        ).setBeginY(
                // 如果当前页面Y轴坐标为空，则起始坐标 = pdfBox最新页面高度 - 上边距 - 线宽，否则起始坐标 = 当前页面Y轴坐标 - 上边距 - 线宽
                page.getPageY() == null ?
                        // pdfBox最新页面高度 - 上边距 - 线宽
                        page.getLastPage().getMediaBox().getHeight() - this.param.getMarginTop() - lineWidth :
                        // 当前页面Y轴坐标 - 上边距 - 线宽
                        page.getPageY() - this.param.getMarginTop() - lineWidth
        ).setEndX(
                // X轴起始坐标 + 点线长度
                this.param.getBeginX() + this.lineLength
        ).setEndY(
                // Y轴起始坐标
                this.param.getBeginY()
        );
    }
}
