package org.dromara.pdf.pdfbox.component.table;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;
import org.dromara.pdf.pdfbox.component.barcode.XEasyPdfBarCode;
import org.dromara.pdf.pdfbox.component.image.XEasyPdfImage;
import org.dromara.pdf.pdfbox.component.line.XEasyPdfLine;
import org.dromara.pdf.pdfbox.component.rect.XEasyPdfRect;
import org.dromara.pdf.pdfbox.component.text.XEasyPdfText;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDefaultFontStyle;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPositionStyle;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;

import java.awt.*;
import java.io.Serializable;
import java.util.Optional;

/**
 * pdf单元格组件
 *
 * @author xsx
 * @date 2020/6/6
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
public class XEasyPdfCell implements Serializable {

    private static final long serialVersionUID = -94458460877280249L;

    /**
     * pdf单元格参数
     */
    private final XEasyPdfCellParam param = new XEasyPdfCellParam();

    /**
     * 有参构造
     *
     * @param width 宽度
     */
    public XEasyPdfCell(float width) {
        this.param.setWidth(Math.abs(width));
    }

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     */
    public XEasyPdfCell(float width, float height) {
        this.param.setWidth(Math.abs(width)).setHeight(Math.abs(height));
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回单元格组件
     */
    public XEasyPdfCell setContentMode(XEasyPdfComponent.ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回单元格组件
     */
    public XEasyPdfCell setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回单元格组件
     */
    public XEasyPdfCell setHeight(float height) {
        this.param.setHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.param.setBackgroundColor(backgroundColor);
        }
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBorderWidth(float borderWidth) {
        this.param.setBorderWidth(Math.abs(borderWidth));
        return this;
    }

    /**
     * 设置边框点线长度
     *
     * @param borderLineLength 边框点线长度
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBorderLineLength(float borderLineLength) {
        this.param.setBorderLineLength(Math.abs(borderLineLength));
        return this;
    }

    /**
     * 设置边框点线间隔
     *
     * @param borderLineSpace 边框点线间隔
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBorderLineSpace(float borderLineSpace) {
        this.param.setBorderLineSpace(Math.abs(borderLineSpace));
        return this;
    }

    /**
     * 设置边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBorderColor(Color borderColor) {
        if (borderColor != null) {
            this.param.setBorderColor(borderColor);
        }
        return this;
    }

    /**
     * 设置左边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setLeftBorderColor(Color borderColor) {
        this.param.setLeftBorderColor(borderColor);
        return this;
    }

    /**
     * 设置右边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setRightBorderColor(Color borderColor) {
        this.param.setRightBorderColor(borderColor);
        return this;
    }

    /**
     * 设置上边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setTopBorderColor(Color borderColor) {
        this.param.setTopBorderColor(borderColor);
        return this;
    }

    /**
     * 设置下边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setBottomBorderColor(Color borderColor) {
        this.param.setBottomBorderColor(borderColor);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回单元格组件
     */
    public XEasyPdfCell setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回单元格组件
     */
    public XEasyPdfCell setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 开启边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableBorder() {
        this.param.setHasBorder(Boolean.FALSE)
                .setHasTopBorder(Boolean.FALSE)
                .setHasBottomBorder(Boolean.FALSE)
                .setHasLeftBorder(Boolean.FALSE)
                .setHasRightBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回单元格组件
     */
    public XEasyPdfCell setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回单元格组件
     */
    public XEasyPdfCell setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回单元格组件
     */
    public XEasyPdfCell setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回单元格组件
     */
    public XEasyPdfCell setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     *
     * @param style 样式
     * @return 返回单元格组件
     */
    public XEasyPdfCell setHorizontalStyle(XEasyPdfPositionStyle style) {
        // 如果样式不为空，则设置样式
        if (style != null) {
            // 检查水平样式
            XEasyPdfPositionStyle.checkHorizontalStyle(style);
            // 设置全局水平样式
            this.param.setHorizontalStyle(style);
        }
        return this;
    }

    /**
     * 设置垂直样式（居上、居中、居下）
     *
     * @param style 样式
     * @return 返回单元格组件
     */
    public XEasyPdfCell setVerticalStyle(XEasyPdfPositionStyle style) {
        // 如果样式不为空，则设置样式
        if (style != null) {
            // 检查水平样式
            XEasyPdfPositionStyle.checkVerticalStyle(style);
            // 设置全局水平样式
            this.param.setVerticalStyle(style);
        }
        return this;
    }

    /**
     * 开启上下左右居中
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 合并垂直单元格
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableVerticalMerge() {
        this.param.setIsVerticalMerge(Boolean.TRUE);
        return this;
    }

    /**
     * 开启组件自动换行
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableNewLine() {
        this.param.setIsNewLine(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭组件自动换行
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableNewLine() {
        this.param.setIsNewLine(Boolean.FALSE);
        return this;
    }

    /**
     * 开启组件自身样式
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableComponentSelfStyle() {
        this.param.setIsComponentSelfStyle(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭上边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableTopBorder() {
        this.param.setHasTopBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭下边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableBottomBorder() {
        this.param.setHasBottomBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭左边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableLeftBorder() {
        this.param.setHasLeftBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭右边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableRightBorder() {
        this.param.setHasRightBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭上下边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableTopBottomBorder() {
        this.param.setHasTopBorder(Boolean.FALSE).setHasBottomBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭左右边框
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell disableLeftRightBorder() {
        this.param.setHasLeftBorder(Boolean.FALSE).setHasRightBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自动缩放字体大小
     *
     * @return 返回单元格组件
     */
    public XEasyPdfCell enableAutoScaleFontSize() {
        this.param.setIsAutoScaleFontSize(Boolean.TRUE);
        return this;
    }

    /**
     * 添加内容
     *
     * @param component 组件
     * @return 返回单元格组件
     */
    public XEasyPdfCell addContent(XEasyPdfComponent component) {
        this.param.setComponent(component);
        return this;
    }

    /**
     * 获取pdf单元格参数
     *
     * @return 返回单元格参数
     */
    XEasyPdfCellParam getParam() {
        return param;
    }

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     */
    float init(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row) {
        // 初始化参数
        this.param.init(document, page, row);
        // 获取行高
        Float rowHeight = this.param.getHeight();
        // 获取组件列表
        XEasyPdfComponent component = this.param.getComponent();
        // 如果组件属于文本组件，则初始化文本
        if (component instanceof XEasyPdfText) {
            // 获取文本组件
            XEasyPdfText text = (XEasyPdfText) component;
            // 初始化文本组件
            this.initText(document, page, row, text);
            // 如果行高为空，则重置行高
            if (rowHeight == null) {
                // 重置行高
                rowHeight = text.getHeight(document, page);
            }
        }
        // 如果组件属于图片组件，则初始化图片
        else if (component instanceof XEasyPdfImage) {
            // 获取图片组件
            XEasyPdfImage image = (XEasyPdfImage) component;
            // 初始化图片组件
            this.initImage(document, page, row, image);
            // 如果行高为空，则重置行高
            if (rowHeight == null) {
                // 重置行高
                rowHeight = Float.valueOf(image.getHeight(document, page));
            }
        }
        return rowHeight == null ? 0 : rowHeight;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param table    pdf表格
     * @param row      pdf表格行
     */
    void doDraw(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table, XEasyPdfRow row) {
        // 如果列高未初始化，则进行初始化
        if (this.param.getHeight() == null) {
            // 初始化列高
            this.param.setHeight(row.getParam().getHeight());
        }
        // 填充背景色
        this.fillBackgroundColor(document, page, row);
        // 写入边框
        this.writeBorder(document, page, table, row);
        // 如果开启组件自动换行，则开启页面自动定位
        if (this.param.getIsNewLine()) {
            // 开启页面自动定位
            page.enablePosition();
        }
        // 获取当前页面Y轴起始坐标
        float pageY = page.getPageY();
        // 获取当前行X轴起始坐标
        float rowBeginX = row.getParam().getBeginX();
        // 获取组件
        XEasyPdfComponent component = this.param.getComponent();
        // 如果组件不为空，则绘制组件
        if (component != null) {
            // 如果重置上下文，则组件开启重置上下文
            if (this.param.getIsResetContext()) {
                // 组件开启重置上下文
                component.enableResetContext();
            }
            // 如果组件属于文本组件，则写入文本
            if (component instanceof XEasyPdfText) {
                // 写入文本
                this.writeText(document, page, row, (XEasyPdfText) component);
            }
            // 如果组件属于图片组件，则写入图片
            else if (component instanceof XEasyPdfImage) {
                // 写入图片
                this.writeImage(document, page, row, (XEasyPdfImage) component);
            }
            // 如果组件属于条形码组件，则写入条形码
            else if (component instanceof XEasyPdfBarCode) {
                // 写入条形码
                this.writeBarCode(document, page, row, (XEasyPdfBarCode) component);
            }
            // 如果组件属于线条组件，则写入线条
            else if (component instanceof XEasyPdfLine) {
                // 写入线条
                this.writeLine(document, page, row, (XEasyPdfLine) component);
            }
            // 其他组件，则直接绘制
            else {
                // 写入其他组件
                this.writeOtherComponent(document, page, row, component);
            }
        }
        // 重置为当前行X轴原始坐标
        row.getParam().setBeginX(rowBeginX);
        // 重置为当前页面Y轴原始坐标
        page.setPageY(pageY);
        // 关闭页面自动定位
        page.disablePosition();
    }

    /**
     * 开启左边框
     */
    void enableLeftBorder() {
        this.param.setHasLeftBorder(Boolean.TRUE).setHasBorder(Boolean.TRUE);
    }

    /**
     * 开启右边框
     */
    void enableRightBorder() {
        this.param.setHasRightBorder(Boolean.TRUE).setHasBorder(Boolean.TRUE);
    }

    /**
     * 开启上边框
     */
    void enableTopBorder() {
        this.param.setHasTopBorder(Boolean.TRUE).setHasBorder(Boolean.TRUE);
    }

    /**
     * 开启下边框
     */
    void enableBottomBorder() {
        this.param.setHasBottomBorder(Boolean.TRUE).setHasBorder(Boolean.TRUE);
    }

    /**
     * 填充背景色
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     */
    private void fillBackgroundColor(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row) {
        // 如果背景色不为白色，则填充背景色
        if (!Color.WHITE.equals(this.param.getBackgroundColor())) {
            // 绘制矩形填充背景色
            XEasyPdfRect rect = XEasyPdfHandler.Rect.build(
                    this.param.getWidth(),
                    this.param.getHeight(),
                    row.getParam().getBeginX(),
                    row.getParam().getBeginY() - this.param.getHeight() - this.param.getMarginTop()
            );
            // 如果重置上下文，则开启重置上下文
            if (this.param.getIsResetContext()) {
                // 开启重置上下文
                rect.enableResetContext();
            }
            // 设置其他参数
            rect.setContentMode(this.param.getContentMode())
                    .setBackgroundColor(this.param.getBackgroundColor())
                    .setBorderColor(this.param.getBackgroundColor())
                    .setNewLine(false)
                    .disableCheckPage()
                    .draw(document, page);
        }
    }

    /**
     * 写入边框
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     */
    @SneakyThrows
    private void writeBorder(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table, XEasyPdfRow row) {
        // 如果带有边框，则进行写入边框
        if (this.param.getHasBorder()) {
            // 构建单元格边框
            XEasyPdfCellBorder cellBorder = new XEasyPdfCellBorder()
                    .setDocument(document.getTarget())
                    .setPage(page.getLastPage())
                    .setContentMode(Optional.ofNullable(this.param.getContentMode()).orElse(XEasyPdfComponent.ContentMode.APPEND).getMode())
                    .setIsResetContext(this.param.getIsResetContext())
                    .setWidth(this.param.getWidth())
                    .setHeight(this.param.getHeight())
                    .setLeftBorderColor(this.param.getLeftBorderColor())
                    .setRightBorderColor(this.param.getRightBorderColor())
                    .setTopBorderColor(this.param.getTopBorderColor())
                    .setBottomBorderColor(this.param.getBottomBorderColor())
                    .setBorderWidth(this.param.getBorderWidth())
                    .setBorderLineLength(this.param.getBorderLineLength())
                    .setBorderLineSpace(this.param.getBorderLineSpace())
                    .setBeginX(row.getParam().getBeginX())
                    .setBeginY(row.getParam().getBeginY() - this.param.getMarginTop())
                    .setHasTopBorder(this.param.getHasTopBorder())
                    .setHasBottomBorder(this.param.getHasBottomBorder())
                    .setHasLeftBorder(this.param.getHasLeftBorder())
                    .setHasRightBorder(this.param.getHasRightBorder());
            // 获取表格参数
            XEasyPdfTableParam tableParam = table.getParam();
            // 如果单元格边框颜色与表格边框颜色相同，则直接绘制边框
            if (this.param.getBorderColor().equals(tableParam.getBorderColor())) {
                // 绘制边框
                cellBorder.drawBorder();
            }
            // 否则添加单元格边框列表
            else {
                // 添加单元格边框列表
                tableParam.getCellBorderList().add(cellBorder);
            }
        }
    }

    /**
     * 写入文本
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param text     pdf文本
     */
    private void writeText(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfText text) {
        // 如果开启自动缩放字体大小，则自动调整字体大小
        if (this.param.getIsAutoScaleFontSize() != null && this.param.getIsAutoScaleFontSize()) {
            // 获取文本高度
            float textHeight = text.getHeight(document, page);
            // 如果文本高度大于单元格高度且文本字体大小大于1，则重置文本字体大小
            while (textHeight > this.param.getHeight() && text.getFontSize() > 1) {
                // 重置文本高度（重置文本字体大小）
                textHeight = text.setFontSize(text.getFontSize() - 1).setMaxHeight(null).setSplitTextList(null).getHeight(document, page);
            }
        }
        // 设置定位及绘制
        text.setPosition(
                row.getParam().getBeginX(),
                this.initYForText(document, page, row, text) - this.param.getMarginTop()
        ).enableChildComponent().draw(document, page);
    }

    /**
     * 写入图片
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param image    pdf图片
     */
    private void writeImage(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfImage image) {
        // 设置定位及绘制
        image.setPosition(
                row.getParam().getBeginX() + this.param.getBorderWidth() / 2,
                this.initYForImage(document, page, row, image.getHeight(document, page)) - this.param.getMarginTop()
        ).draw(document, page);
    }

    /**
     * 写入条形码
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param barCode  pdf条形码
     */
    private void writeBarCode(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfBarCode barCode) {
        // 设置定位及绘制
        barCode.setPosition(
                row.getParam().getBeginX() + this.param.getBorderWidth(),
                this.initYForImage(document, page, row, barCode.getHeight(document, page)) - this.param.getMarginTop()
        ).draw(document, page);
    }

    /**
     * 写入线条
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param line     pdf线条
     */
    private void writeLine(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfLine line) {
        // 设置定位并绘制
        line.setContentMode(this.param.getContentMode())
                .setWidth(this.param.getWidth())
                .setPosition(
                        row.getParam().getBeginX(),
                        row.getParam().getBeginY() - this.param.getMarginTop()
                ).draw(document, page);
    }

    /**
     * 写入其他组件
     *
     * @param document  pdf文档
     * @param page      pdf页面
     * @param row       pdf表格行
     * @param component pdf组件
     */
    private void writeOtherComponent(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfComponent component) {
        // 设置定位并绘制
        component.setPosition(
                row.getParam().getBeginX(),
                page.getPageY() - this.param.getMarginTop()
        ).draw(document, page);
    }

    /**
     * 写入文本
     *
     * @param text pdf文本
     */
    private void initText(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfText text) {
        // 如果需要初始化，则进行初始化
        if (text.isNeedInitialize()) {
            // 设置文本为子组件并设置宽度
            text.enableChildComponent().setWidth(this.param.getWidth());
            // 如果开启组件样式，则使用文本自身样式
            if (this.param.getIsComponentSelfStyle()) {
                // 设置文本自身样式
                this.param.setContentMode(text.getContentMode())
                        .setFontPath(text.getFontPath())
                        .setFontSize(text.getFontSize())
                        .setFontColor(text.getFontColor())
                        .setVerticalStyle(text.getVerticalStyle())
                        .setHorizontalStyle(text.getHorizontalStyle());
            }
            // 设置文本为子组件并设置宽度
            text.setNeedInitialize(false)
                    .setContentMode(this.param.getContentMode())
                    .setFontPath(this.param.getFontPath())
                    .setFontSize(this.param.getFontSize())
                    .setFontColor(this.param.getFontColor())
                    .setHorizontalStyle(this.param.getHorizontalStyle())
                    .setVerticalStyle(this.param.getVerticalStyle());
        }
    }

    /**
     * 写入图片
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param image    pdf图片
     */
    private void initImage(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfImage image) {
        // 如果需要初始化，则进行初始化
        if (image.isNeedInitialize()) {
            // 如果开启组件样式，则使用图片自身样式
            if (this.param.getIsComponentSelfStyle()) {
                // 设置图片自身样式
                this.param.setContentMode(image.getContentMode())
                        .setVerticalStyle(image.getVerticalStyle())
                        .setHorizontalStyle(image.getHorizontalStyle());
            }
            // 获取单元格高度
            Float height = this.param.getHeight();
            // 如果高度为空，则重置为行高
            if (height == null) {
                // 重置为行高
                height = row.getParam().getHeight();
            }
            // 如果仍然为空，则重置为图片高度
            if (height == null) {
                // 重置为图片高度
                height = Float.valueOf(image.getHeight(document, page));
            }
            // 如果为自定义尺寸，则使用最小值重置图片宽高
            if (image.isCustomRectangle()) {
                // 重置图片宽度与高度
                image.setWidth(Math.min(image.getWidth(document, page), this.param.getWidth()) - this.param.getBorderWidth())
                        .setHeight(Math.min(image.getHeight(document, page), height) - this.param.getBorderWidth());
            }
            // 否则重置图片宽高为单元格宽高
            else {
                // 重置图片宽度与高度
                image.setWidth(this.param.getWidth() - this.param.getBorderWidth())
                        .setHeight(height - this.param.getBorderWidth());
            }
            // 设置图片参数
            image.setNeedInitialize(false)
                    .enableChildComponent()
                    .setMaxWidth(this.param.getWidth() - this.param.getBorderWidth())
                    .setMaxHeight(height - this.param.getBorderWidth())
                    .setContentMode(this.param.getContentMode())
                    .setHorizontalStyle(this.param.getHorizontalStyle())
                    .setVerticalStyle(this.param.getVerticalStyle());
        }
    }

    /**
     * 初始化文本Y轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param text     pdf文本
     * @return 返回Y轴起始坐标
     */
    private float initYForText(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, XEasyPdfText text) {
        // 获取文本高度
        float height = text.getHeight(document, page);
        // 定义Y轴起始坐标为页面Y轴起始坐标
        float y = page.getPageY();
        // 如果垂直样式为居上，则重置Y轴起始坐标为Y轴起始坐标-字体大小-边框宽度-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-边框宽度-行上边距
            y = y - text.getFontSize() - this.param.getBorderWidth() - row.getParam().getMarginTop();
            return y;
        }
        // 如果垂直样式为居中，则重置Y轴起始坐标为Y轴起始坐标-字体大小-(单元格高度-文本高度)/2-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-(单元格高度-文本高度)/2-行上边距
            y = y - text.getFontSize() - (this.param.getHeight() - height) / 2 - row.getParam().getMarginTop();
            return y;
        }
        // 如果垂直样式为居下，则重置Y轴起始坐标为Y轴起始坐标-字体大小-单元格高度+文本高度-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.BOTTOM) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-单元格高度+文本高度-行上边距
            y = y - text.getFontSize() - this.param.getHeight() + height - row.getParam().getMarginTop();
        }
        return y;
    }

    /**
     * 初始化图片Y轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     * @param height   高度
     * @return 返回Y轴起始坐标
     */
    private float initYForImage(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row, float height) {
        // 定义Y轴起始坐标为页面Y轴起始坐标
        float y = page.getPageY();
        // 如果图片高度等于单元格高度-边框宽度或垂直样式为居上，则重置Y轴起始坐标为Y轴起始坐标-图片高度-边框宽度-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为Y轴起始坐标-图片高度-边框宽度-行上边距
            y = y - height - this.param.getBorderWidth() - row.getParam().getMarginTop();
            return y;
        }
        // 如果垂直样式为居中，则重置Y轴起始坐标为Y轴起始坐标-图片高度-(单元格高度-图片高度)/2-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为Y轴起始坐标-图片高度-(单元格高度-图片高度)/2-行上边距
            y = y - height - (this.param.getHeight() - height) / 2 - row.getParam().getMarginTop();
            return y;
        }
        // 如果垂直样式为居下，则重置Y轴起始坐标为Y轴起始坐标-单元格高度+边框宽度-行上边距
        if (this.param.getVerticalStyle() == XEasyPdfPositionStyle.BOTTOM) {
            // 重置Y轴起始坐标为Y轴起始坐标-单元格高度+边框宽度/2-行上边距
            y = y - this.param.getHeight() + this.param.getBorderWidth() - row.getParam().getMarginTop();
        }
        return y;
    }
}
