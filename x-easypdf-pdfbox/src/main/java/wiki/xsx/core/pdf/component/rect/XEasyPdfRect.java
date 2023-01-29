package wiki.xsx.core.pdf.component.rect;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.Color;

/**
 * pdf矩形组件
 *
 * @author xsx
 * @date 2020/5/23
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
public class XEasyPdfRect implements XEasyPdfComponent {

    private static final long serialVersionUID = 7685200001639953693L;

    /**
     * pdf矩形参数
     */
    private final XEasyPdfRectParam param = new XEasyPdfRectParam();

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     */
    public XEasyPdfRect(float width, float height) {
        this(width, height, null, null);
    }

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    public XEasyPdfRect(float width, float height, Float beginX, Float beginY) {
        this.param.setWidth(Math.abs(width)).setHeight(Math.abs(height)).setBeginX(beginX).setBeginY(beginY);
    }

    /**
     * 开启上下文重置
     *
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfRect enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启检查页面（自动分页）
     *
     * @return 返回矩形组件
     */
    public XEasyPdfRect enableCheckPage() {
        this.param.setCheckPage(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭检查页面（自动分页）
     *
     * @return 返回矩形组件
     */
    public XEasyPdfRect disableCheckPage() {
        this.param.setCheckPage(Boolean.FALSE);
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回矩形组件
     */
    public XEasyPdfRect setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回矩形组件
     */
    public XEasyPdfRect setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回矩形组件
     */
    public XEasyPdfRect setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回矩形组件
     */
    public XEasyPdfRect setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回矩形组件
     */
    public XEasyPdfRect setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置是否有边框
     *
     * @param hasBorder 是否有边框
     * @return 返回矩形组件
     */
    public XEasyPdfRect setHasBorder(boolean hasBorder) {
        this.param.setHasBorder(hasBorder);
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回矩形组件
     */
    public XEasyPdfRect setBorderWidth(float borderWidth) {
        this.param.setBorderWidth(Math.abs(borderWidth));
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     * @return 返回矩形组件
     */
    public XEasyPdfRect setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.param.setBackgroundColor(backgroundColor);
        }
        return this;
    }

    /**
     * 设置边框颜色（设置是否有边框为true时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回矩形组件
     */
    public XEasyPdfRect setBorderColor(Color borderColor) {
        if (borderColor != null) {
            this.param.setBorderColor(borderColor);
        }
        return this;
    }

    /**
     * 设置是否换行
     *
     * @param newLine 是否换行
     * @return 返回矩形组件
     */
    public XEasyPdfRect setNewLine(boolean newLine) {
        this.param.setIsNewLine(newLine);
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
    public XEasyPdfRect setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfRect setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfRect setHeight(float height) {
        this.param.setHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回矩形组件
     */
    @Override
    public XEasyPdfRect setContentMode(ContentMode mode) {
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
            // 绘制矩形（边框矩形）
            contentStream.addRect(
                    this.param.getBeginX(),
                    this.param.getBeginY(),
                    this.param.getWidth(),
                    this.param.getHeight()
            );
            // 设置矩形颜色（边框颜色）
            contentStream.setNonStrokingColor(this.param.getBorderColor());
            // 填充矩形（边框矩形）
            contentStream.fill();
            // 绘制矩形（背景矩形）
            contentStream.addRect(
                    this.param.getBeginX() + this.param.getBorderWidth(),
                    this.param.getBeginY() + this.param.getBorderWidth(),
                    this.param.getWidth() - this.param.getBorderWidth() * 2,
                    this.param.getHeight() - this.param.getBorderWidth() * 2
            );
            // 设置矩形颜色（背景颜色）
            contentStream.setNonStrokingColor(this.param.getBackgroundColor());
            // 填充矩形（背景矩形）
            contentStream.fill();
        } else {
            // 绘制矩形（背景矩形）
            contentStream.addRect(
                    this.param.getBeginX(),
                    this.param.getBeginY(),
                    this.param.getWidth(),
                    this.param.getHeight()
            );
            // 设置矩形颜色（背景颜色）
            contentStream.setNonStrokingColor(this.param.getBackgroundColor());
            // 填充矩形（背景矩形）
            contentStream.fill();
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
                page.setPageX(this.param.getBeginX() + this.param.getWidth());
            }
        }
    }
}
