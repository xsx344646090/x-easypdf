package wiki.xsx.core.pdf.component.line;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;

/**
 * 实线分割线组件
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
public class XEasyPdfSolidSplitLine implements XEasyPdfLine {

    /**
     * 分割线参数
     */
    private final XEasyPdfLineParam param = new XEasyPdfLineParam();

    /**
     * 无参构造
     */
    public XEasyPdfSolidSplitLine() {}

    /**
     * 设置字体路径
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
     * @param style 默认字体样式
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        this.param.setDefaultFontStyle(style);
        return this;
    }

    /**
     * 设置边距（上下左右）
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
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
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     * @param margin 边距
     * @return 返回实线分割线组件
     */
    public XEasyPdfSolidSplitLine setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置分割线宽度
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
     * @param color 分割线颜色
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setColor(Color color) {
        if (color!=null) {
            this.param.setColor(color);
        }
        return this;
    }

    /**
     * 设置分割线线型
     * @param lineCapStyle 分割线线型
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setLineCapStyle(XEasyPdfLineCapStyle lineCapStyle) {
        if (lineCapStyle!=null) {
            this.param.setStyle(lineCapStyle);
        }
        return this;
    }

    /**
     * 设置坐标
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setPosition(float beginX, float beginY) {
        return this;
    }

    /**
     * 设置宽度
     * @param width 宽度
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setWidth(float width) {
        return this;
    }

    /**
     * 设置高度
     * @param height 高度
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setHeight(float height) {
        return this;
    }

    /**
     * 设置内容模式
     * @param mode 内容模式
     * @return 返回实线分割线组件
     */
    @Override
    public XEasyPdfSolidSplitLine setContentMode(ContentMode mode) {
        if (mode!=null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 获取线条宽度
     * @return 返回线条宽度
     */
    @Override
    public float getLineWidth() {
        return this.param.getLineWidth();
    }

    /**
     * 绘制
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
        if (page.getParam().isAllowResetPosition()) {
            // 设置pdf页面Y轴起始坐标，起始坐标 = 起始坐标 - 线宽 / 2
            page.getParam().setPageY(this.param.getBeginY() - this.param.getLineWidth() / 2);
        }
        // 完成标记
        this.param.setDraw(true);
        // 字体路径不为空，说明该组件设置字体，则直接进行字体关联
        if (this.param.getFontPath()!=null&&this.param.getFontPath().length()>0) {
            // 重置字体为null
            this.param.setFont(null);
        }
    }

    /**
     * 是否完成绘制
     * @return 返回布尔值，完成为true，未完成为false
     */
    @Override
    public boolean isDraw() {
        return this.param.isDraw();
    }

    /**
     * 初始化参数
     * @param document pdf文档
     * @param page pdf页面
     */
    private void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 分页检查
        this.param.checkPage(document, page);
        // 定义线宽
        float lineWidth = this.param.getLineWidth() / 2;
        // 获取pdfBox最新页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 设置X轴Y轴起始结束坐标
        this.param.setBeginX(
                // 左边距
                this.param.getMarginLeft()
        ).setBeginY(
                // 如果当前页面Y轴坐标为空，则起始坐标 = pdfBox最新页面高度 - 上边距 - 线宽，否则起始坐标 = 当前页面Y轴坐标 - 上边距 - 线宽
                page.getParam().getPageY() == null?
                // pdfBox最新页面高度 - 上边距 - 线宽
                rectangle.getHeight() - this.param.getMarginTop() - lineWidth:
                // 当前页面Y轴坐标 - 上边距 - 线宽
                page.getParam().getPageY() - this.param.getMarginTop() - lineWidth
        ).setEndX(
                // 页面宽度 - 右边距
                rectangle.getWidth() - this.param.getMarginRight()
        ).setEndY(
                // Y轴起始坐标
                this.param.getBeginY()
        );
        // 如果字体路径为空，且默认字体样式不为空，则进行初始化字体路径
        if (this.param.getFontPath()==null&&this.param.getDefaultFontStyle()!=null) {
            // 初始化字体路径
            this.param.setFontPath(this.param.getDefaultFontStyle().getPath());
        }
        // 初始化字体
        this.param.setFont(XEasyPdfFontUtil.loadFont(document, page, this.param.getFontPath(), true));
    }
}
