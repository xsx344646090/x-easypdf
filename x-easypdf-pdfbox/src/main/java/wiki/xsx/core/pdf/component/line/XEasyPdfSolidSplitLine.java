package wiki.xsx.core.pdf.component.line;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.Color;

/**
 * 实线分割线组件
 *
 * @author xsx
 * @date 2020/3/4
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfSolidSplitLine implements XEasyPdfLine {

    private static final long serialVersionUID = 923483874195529438L;

    /**
     * 分割线参数
     */
    private final XEasyPdfLineParam param = new XEasyPdfLineParam();

    /**
     * 无参构造
     */
    public XEasyPdfSolidSplitLine() {
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置分割线宽度
     *
     * @param lineWidth 分割线宽度
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setLineWidth(float lineWidth) {
        this.param.setLineWidth(Math.abs(lineWidth));
        return this;
    }

    /**
     * 设置分割线颜色
     *
     * @param color 分割线颜色
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setColor(Color color) {
        if (color != null) {
            this.param.setColor(color);
        }
        return this;
    }

    /**
     * 设置分割线线型
     *
     * @param lineCapStyle 分割线线型
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setLineCapStyle(XEasyPdfLineCapStyle lineCapStyle) {
        if (lineCapStyle != null) {
            this.param.setStyle(lineCapStyle);
        }
        return this;
    }

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度(线长)
     *
     * @param width 宽度(线长)
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setHeight(float height) {
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setContentMode(ContentMode mode) {
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
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine enableResetContext() {
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
        // 初始化分割线参数
        this.init(document, page);
        // 执行画图
        new XEasyPdfBaseLine(this.param).draw(document, page);
        // 如果允许重置定位，则重置pdf页面Y轴坐标
        if (page.isAllowResetPosition()) {
            // 设置pdf页面Y轴起始坐标，起始坐标 = 起始坐标 - 线宽 / 2
            page.setPageY(this.param.getBeginY() - this.param.getLineWidth() / 2);
        }
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
        // 获取pdfBox最新页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 如果X轴起始坐标已初始化且Y轴起始坐标已初始化，则直接返回
        if (this.param.getBeginX() != null && this.param.getBeginY() != null) {
            // 如果宽度已初始化，则设置X轴结束坐标为X轴起始坐标+宽度
            if (this.param.getWidth() != null) {
                // 设置X轴结束坐标 = X轴起始坐标+宽度
                this.param.setEndX(this.param.getBeginX() + this.param.getWidth());
            }
            // 否则设置X轴结束坐标为页面宽度-右边距
            else {
                // 设置X轴结束坐标 = 页面宽度-右边距
                this.param.setEndX(rectangle.getWidth() - this.param.getMarginRight());
            }
            // 设置Y轴结束坐标为Y轴起始坐标
            this.param.setEndY(this.param.getBeginY());
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
                        rectangle.getHeight() - this.param.getMarginTop() - lineWidth :
                        // 当前页面Y轴坐标 - 上边距 - 线宽
                        page.getPageY() - this.param.getMarginTop() - lineWidth
        ).setEndX(
                // 页面宽度 - 右边距
                rectangle.getWidth() - this.param.getMarginRight()
        ).setEndY(
                // Y轴起始坐标
                this.param.getBeginY()
        );
    }
}
