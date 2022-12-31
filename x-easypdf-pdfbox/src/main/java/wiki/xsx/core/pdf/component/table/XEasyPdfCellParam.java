package wiki.xsx.core.pdf.component.table;

import lombok.Data;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;

import java.awt.Color;
import java.io.Serializable;

/**
 * pdf单元格参数
 *
 * @author xsx
 * @date 2020/6/6
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
@Data
@Accessors(chain = true)
class XEasyPdfCellParam implements Serializable {

    private static final long serialVersionUID = 8411726264113185762L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否带有边框
     */
    private Boolean hasBorder;
    /**
     * 是否带有上边框
     */
    private Boolean hasTopBorder = Boolean.TRUE;
    /**
     * 是否带有下边框
     */
    private Boolean hasBottomBorder = Boolean.TRUE;
    /**
     * 是否带有左边框
     */
    private Boolean hasLeftBorder = Boolean.TRUE;
    /**
     * 是否带有右边框
     */
    private Boolean hasRightBorder = Boolean.TRUE;
    /**
     * 背景颜色
     */
    private Color backgroundColor;
    /**
     * 边框颜色
     */
    private Color borderColor;
    /**
     * 边框宽度
     */
    private Float borderWidth;
    /**
     * 边框点线长度
     */
    private Float borderLineLength;
    /**
     * 边框点线间隔
     */
    private Float borderLineSpace;
    /**
     * 左边框颜色
     */
    private Color leftBorderColor;
    /**
     * 右边框颜色
     */
    private Color rightBorderColor;
    /**
     * 上边框颜色
     */
    private Color topBorderColor;
    /**
     * 下边框颜色
     */
    private Color bottomBorderColor;
    /**
     * 列宽
     */
    private Float width;
    /**
     * 列高
     */
    private Float height;
    /**
     * pdf组件
     */
    private XEasyPdfComponent component;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体大小
     */
    private Float fontSize;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 表格样式（居左、居中、居右）
     * 默认居左
     */
    private XEasyPdfPositionStyle horizontalStyle;
    /**
     * 表格样式（居上、居中、居下）
     * 默认居上
     */
    private XEasyPdfPositionStyle verticalStyle;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 是否水平合并
     */
    private Boolean isHorizontalMerge = Boolean.FALSE;
    /**
     * 是否垂直合并
     */
    private Boolean isVerticalMerge = Boolean.FALSE;
    /**
     * 是否组件换行
     */
    private Boolean isNewLine = Boolean.TRUE;
    /**
     * 是否组件样式
     */
    private Boolean isComponentSelfStyle = Boolean.FALSE;
    /**
     * 是否自动缩放字体大小
     */
    private Boolean isAutoScaleFontSize;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param row      pdf表格行
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row) {
        // 获取pdf表格行参数
        XEasyPdfRowParam rowParam = row.getParam();
        // 如果边框标记为空，则初始化边框标记
        if (this.hasBorder == null) {
            // 初始化边框标记
            this.hasBorder = rowParam.getHasBorder();
        }
        // 如果开启边框，则初始化边框宽度
        if (this.hasBorder) {
            // 如果边框宽度未初始化，则进行初始化
            if (this.borderWidth == null) {
                // 初始化边框宽度
                this.borderWidth = rowParam.getBorderWidth();
            }
            // 如果边框颜色未初始化，则进行初始化
            if (this.borderColor == null) {
                // 初始化边框颜色
                this.borderColor = rowParam.getBorderColor();
            }
            // 如果边框点线长度未初始化，则进行初始化
            if (this.borderLineLength == null) {
                // 初始化边框点线长度
                this.borderLineLength = rowParam.getBorderLineLength();
            }
            // 如果边框点线间隔未初始化，则进行初始化
            if (this.borderLineSpace == null) {
                // 初始化边框点线间隔
                this.borderLineSpace = rowParam.getBorderLineSpace();
            }
            // 如果左边框颜色未初始化，则进行初始化
            if (this.leftBorderColor==null) {
                // 初始化左边框颜色
                this.leftBorderColor = this.borderColor;
            }
            // 如果右边框颜色未初始化，则进行初始化
            if (this.rightBorderColor==null) {
                // 初始化右边框颜色
                this.rightBorderColor = this.borderColor;
            }
            // 如果上边框颜色未初始化，则进行初始化
            if (this.topBorderColor==null) {
                // 初始化上边框颜色
                this.topBorderColor = this.borderColor;
            }
            // 如果下边框颜色未初始化，则进行初始化
            if (this.bottomBorderColor==null) {
                // 初始化下边框颜色
                this.bottomBorderColor = this.borderColor;
            }
        }
        // 否则初始化边框宽度为0
        else {
            // 初始化边框宽度
            this.borderWidth = 0F;
        }
        // 如果内容模式未初始化，则初始化为页面内容模式
        if (this.contentMode == null) {
            // 初始化为页面内容模式
            this.contentMode = rowParam.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化为页面是否重置上下文
        if (this.isResetContext == null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = rowParam.getIsResetContext();
        }
        // 如果字体路径未初始化，则初始化为默认字体路径
        if (this.fontPath == null) {
            // 初始化为默认字体路径
            this.fontPath = rowParam.getFontPath();
        }
        // 如果字体大小未初始化，则进行初始化
        if (this.fontSize == null) {
            // 初始化字体大小
            this.fontSize = rowParam.getFontSize();
        }
        // 如果字体颜色未初始化，则进行初始化
        if (this.fontColor == null) {
            // 初始化字体颜色
            this.fontColor = rowParam.getFontColor();
        }
        // 如果背景颜色未初始化，则进行初始化
        if (this.backgroundColor == null) {
            // 初始化背景颜色
            this.backgroundColor = rowParam.getBackgroundColor();
        }
        // 如果水平样式未初始化，则进行初始化
        if (this.horizontalStyle == null) {
            // 初始化水平样式
            this.horizontalStyle = rowParam.getHorizontalStyle();
        }
        // 如果垂直样式未初始化，则进行初始化
        if (this.verticalStyle == null) {
            // 初始化垂直样式
            this.verticalStyle = rowParam.getVerticalStyle();
        }
        // 如果是否自动缩放字体大小未初始化，则初始化为表格行是否自动缩放字体大小
        if (this.isAutoScaleFontSize == null) {
            // 初始化为表格行是否自动缩放字体大小
            this.isAutoScaleFontSize = rowParam.getIsAutoScaleFontSize();
        }
    }
}
