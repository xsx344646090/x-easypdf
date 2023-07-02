package org.dromara.pdf.pdfbox.component.rect;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;

import java.awt.*;
import java.io.Serializable;

/**
 * pdf矩形参数
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
@Data
@Accessors(chain = true)
class XEasyPdfRectParam implements Serializable {

    private static final long serialVersionUID = 1621265990621255413L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 右边距
     */
    private Float marginRight = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 下边距
     */
    private Float marginBottom = 0F;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;
    /**
     * 页面X轴起始坐标
     */
    private Float beginX;
    /**
     * 页面Y轴起始坐标
     */
    private Float beginY;
    /**
     * 是否有边框
     */
    private Boolean hasBorder = Boolean.FALSE;
    /**
     * 边框宽度
     */
    private Float borderWidth = 1F;
    /**
     * 背景颜色
     */
    private Color backgroundColor = Color.WHITE;
    /**
     * 边框颜色
     */
    private Color borderColor = Color.BLACK;
    /**
     * 页面检查
     */
    private Boolean checkPage = Boolean.TRUE;
    /**
     * 是否自动换行
     */
    private Boolean isNewLine = Boolean.TRUE;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果宽度未设置，则抛异常
        if (this.width == null) {
            throw new RuntimeException("the width can not be null");
        }
        // 如果高度未设置，则抛异常
        if (this.height == null) {
            throw new RuntimeException("the height can not be null");
        }
        // 如果内容模式未初始化，则初始化为页面内容模式
        if (this.contentMode == null) {
            // 初始化为页面内容模式
            this.contentMode = page.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化为页面是否重置上下文
        if (this.isResetContext == null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 获取页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 定义页脚高度
        float footerHeight = 0F;
        // 如果页面Y轴起始坐标未初始化，则进行初始化
        if (this.beginY == null) {
            // 如果允许添加页脚，且页脚不为空则初始化页脚高度
            if (page.isAllowFooter() && page.getFooter() != null) {
                // 初始化页脚高度
                footerHeight = page.getFooter().getHeight(document, page);
            }
            // 获取当前页面Y轴起始坐标
            Float pageY = page.getPageY();
            // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 矩形高度，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 矩形高度
            this.beginY = pageY == null ?
                    rectangle.getHeight() - this.marginTop - this.height :
                    pageY - this.marginTop - this.height;
        }
        // 如果检查页面为真，并且Y轴起始坐标-页脚高度小于等于下边距，则进行分页
        if (this.checkPage && this.beginY - footerHeight <= this.marginBottom) {
            // 添加新页面
            page.addNewPage(document, rectangle);
            // 获取当前页面Y轴起始坐标
            Float pageY = page.getPageY();
            // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 矩形高度，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 矩形高度
            this.beginY = pageY == null ?
                    rectangle.getHeight() - this.marginTop - this.height :
                    pageY - this.marginTop - this.height;
        }
        // 如果页面Y轴起始坐标未初始化，则进行初始化
        if (this.beginX == null) {
            // 获取当前页面X轴起始坐标
            Float pageX = page.getPageX();
            // 初始化页面X轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 左边距，否则起始坐标 = 当前页面X轴起始坐标 + 左边距
            this.beginX = pageX == null ?
                    this.marginLeft :
                    pageX + this.marginLeft;
        }
    }
}
