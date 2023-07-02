package org.dromara.pdf.pdfbox.component.table;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPositionStyle;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf表格组件参数
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
@Data
@Accessors(chain = true)
class XEasyPdfTableParam implements Serializable {

    private static final long serialVersionUID = 8648004404874837308L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 表头
     */
    private XEasyPdfTable title;
    /**
     * 单元格边框列表
     */
    private transient List<XEasyPdfCellBorder> cellBorderList = new ArrayList<>(64);
    /**
     * 行列表
     */
    private transient List<XEasyPdfRow> rows = new ArrayList<>(64);
    /**
     * 是否带有边框
     */
    private Boolean hasBorder = Boolean.TRUE;
    /**
     * 最小行高
     */
    private Float minRowHeight;
    /**
     * 背景颜色
     */
    private Color backgroundColor;
    /**
     * 边框颜色
     */
    private Color borderColor = Color.BLACK;
    /**
     * 边框宽度
     */
    private Float borderWidth = 1F;
    /**
     * 边框点线长度
     */
    private Float borderLineLength;
    /**
     * 边框点线间隔
     */
    private Float borderLineSpace;
    /**
     * 左边距
     */
    private Float marginLeft;
    /**
     * 上边距
     */
    private Float marginTop;
    /**
     * 下边距
     */
    private Float marginBottom;
    /**
     * X轴起始坐标
     */
    private Float beginX;
    /**
     * Y轴起始坐标
     */
    private Float beginY;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体大小
     */
    private Float fontSize = 12F;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 水平样式（居左、居中、居右）
     * 默认居左
     */
    private XEasyPdfPositionStyle horizontalStyle = XEasyPdfPositionStyle.LEFT;
    /**
     * 垂直样式（居上、居中、居下）
     * 默认居上
     */
    private XEasyPdfPositionStyle verticalStyle = XEasyPdfPositionStyle.TOP;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 是否自动拆分行
     */
    private Boolean isAutoSplit = Boolean.TRUE;
    /**
     * 是否自动缩放字体大小
     */
    private Boolean isAutoScaleFontSize = Boolean.FALSE;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果内容模式未初始化，则初始化
        if (this.contentMode == null) {
            // 初始化为页面内容模式
            this.contentMode = page.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化
        if (this.isResetContext == null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 如果字体路径未初始化，则初始化
        if (this.fontPath == null) {
            // 初始化为页面字体路径
            this.fontPath = page.getFontPath();
        }
        // 如果背景颜色未初始化，则初始化
        if (this.backgroundColor == null) {
            // 初始化背景颜色
            this.backgroundColor = page.getBackgroundColor();
        }
        // 如果边框颜色未初始化，则初始化
        if (this.borderColor == null) {
            // 初始化边框颜色
            this.borderColor = Color.BLACK;
        }
        // 如果左边距未初始化，则初始化
        if (this.marginLeft == null) {
            // 初始化左边距
            this.marginLeft = 0F;
        }
        // 如果上边距未初始化，则初始化
        if (this.marginTop == null) {
            // 初始化上边距
            this.marginTop = 5F;
        }
        // 如果下边距未初始化，则初始化
        if (this.marginBottom == null) {
            // 初始化下边距
            this.marginBottom = 0F;
        }
    }

    /**
     * 初始化
     *
     * @param param pdf表格参数
     */
    void init(XEasyPdfTableParam param) {
        // 初始化上边距
        this.marginTop = 0F;
        // 初始化左边距
        this.marginLeft = param.getMarginLeft();
        // 初始化最小行高
        this.minRowHeight = param.getMinRowHeight();
        // 初始化为页面字体路径
        this.fontPath = param.getFontPath();
        // 初始化字体大小
        this.fontSize = param.getFontSize();
        // 初始化字体颜色
        this.fontColor = param.getFontColor();
        // 初始化背景颜色
        this.backgroundColor = param.getBackgroundColor();
        // 初始化边框颜色
        this.borderColor = param.getBorderColor();
        // 初始化边框宽度
        this.borderWidth = param.getBorderWidth();
        // 初始化内容模式
        this.contentMode = param.getContentMode();
        // 初始化是否重置上下文
        this.isResetContext = param.getIsResetContext();
        // 初始化是否自动拆分行
        this.isAutoSplit = param.getIsAutoSplit();
        // 初始化是否带有边框
        this.hasBorder = param.getHasBorder();
        // 初始化水平样式
        this.horizontalStyle = param.getHorizontalStyle();
        // 初始化垂直样式
        this.verticalStyle = param.getVerticalStyle();
    }
}