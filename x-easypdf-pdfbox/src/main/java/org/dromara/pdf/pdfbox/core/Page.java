package org.dromara.pdf.pdfbox.core;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.enums.ContentMode;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.awt.*;

/**
 * 页面
 *
 * @author xsx
 * @date 2023/6/5
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
public class Page {

    /**
     * 参数
     */
    private final PageParam param = new PageParam();

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public Page(Document document) {
        this(document, PageRectangle.A4);
    }

    /**
     * 有参构造
     *
     * @param document  文档
     * @param rectangle 尺寸
     */
    public Page(Document document, PageRectangle rectangle) {
        this(document.getParam(), rectangle);
    }

    /**
     * 有参构造
     *
     * @param documentParam 文档参数
     * @param rectangle     尺寸
     */
    Page(DocumentParam documentParam, PageRectangle rectangle) {
        this(documentParam.getPages().size() + 1, new PDPage(rectangle.getSize()), documentParam);
    }

    /**
     * 有参构造
     *
     * @param index         页面索引
     * @param page          尺寸
     * @param documentParam 文档参数
     */
    Page(int index, PDPage page, DocumentParam documentParam) {
        this.param.init(index, page, documentParam);
        documentParam.getPages().add(this);
        if (this.param.getBackgroundColor() != Color.WHITE) {
            this.initBackgroundColor();
        }
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回页面
     */
    public Page setMargin(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.initMargin(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public Page setMarginLeft(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.initMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public Page setMarginRight(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public Page setMarginTop(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.initMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public Page setMarginBottom(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回页面
     */
    public Page setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     * @return 返回页面
     */
    public Page setBackgroundColor(Color color) {
        if (color != null) {
            this.param.setBackgroundColor(color);
            this.initBackgroundColor();
        }
        return this;
    }

    /**
     * 设置水平对齐方式
     *
     * @param alignment 对齐方式
     * @return 返回页面
     */
    public Page setHorizontalAlignment(HorizontalAlignment alignment) {
        if (alignment != null) {
            this.param.setHorizontalAlignment(alignment);
        }
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     * @return 返回页面
     */
    public Page setFontName(String fontName) {
        this.param.getFontParam().setFontName(fontName);
        this.param.getFontParam().setFont(PdfHandler.getFontHandler().getPDFont(this.param.getDocumentParam().getTarget(), fontName, true));
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回页面
     */
    public Page setFontSize(float fontSize) {
        if (fontSize < 1) {
            throw new IllegalArgumentException("the font size must be greater than 1");
        }
        this.param.getFontParam().setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param color 字体颜色
     * @return 返回页面
     */
    public Page setFontColor(Color color) {
        this.param.getFontParam().setFontColor(color);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param style 字体样式
     * @return 返回页面
     */
    public Page setFontStyle(FontStyle style) {
        this.param.getFontParam().setFontStyle(style);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回页面
     */
    public Page setCharacterSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the character spacing must be greater than 0");
        }
        this.param.getFontParam().setCharacterSpacing(spacing);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回页面
     */
    public Page setLeading(float leading) {
        if (leading < 0) {
            throw new IllegalArgumentException("the leading must be greater than 0");
        }
        this.param.getFontParam().setLeading(leading);
        return this;
    }

    /**
     * 获取页面索引（当前页码）
     *
     * @return 返回页面索引
     */
    public Integer getIndex() {
        return this.param.getIndex();
    }

    /**
     * 获取页面参数
     *
     * @return 返回页面参数
     */
    public PageParam getParam() {
        return this.param;
    }

    /**
     * 释放资源
     */
    void release() {
        this.param.release();
    }

    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    private void initBackgroundColor() {
        // 获取页面尺寸
        PageRectangle rectangle = this.param.getRectangle();
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.param.getDocumentParam().getTarget(),
                this.param.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true
        );
        // 绘制矩形（背景矩形）
        contentStream.addRect(
                0,
                0,
                rectangle.getWidth(),
                rectangle.getHeight()
        );
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.param.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
    }
}
