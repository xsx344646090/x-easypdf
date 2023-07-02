package org.dromara.pdf.pdfbox.mark;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

/**
 * pdf页面水印参数
 *
 * @author xsx
 * @date 2020/3/25
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
class XEasyPdfWatermarkParam implements Serializable {

    private static final long serialVersionUID = -3423024754445075443L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体大小
     */
    private Float fontSize = 50F;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 透明度（值越小越透明，0.0-1.0）
     */
    private Float alpha = 0.2F;
    /**
     * 文本弧度
     */
    private Double radians = 30D;
    /**
     * 水印文本
     */
    private List<String> texts;
    /**
     * 文本间隔
     */
    private Float wordSpace;
    /**
     * 文本单行数
     */
    private Integer wordCount;
    /**
     * 文本行数
     */
    private Integer wordLine = 8;
    /**
     * 字符间隔
     */
    private Float characterSpacing = 0F;
    /**
     * 文本行间距
     */
    private Float leading;
    /**
     * X轴起始坐标
     */
    private Float beginX;
    /**
     * Y轴起始坐标
     */
    private Float beginY;
    /**
     * 是否需要初始化
     */
    private Boolean isNeedInit = Boolean.TRUE;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
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
        // 如果字体路径未初始化，则初始化为页面字体路径
        if (this.fontPath == null) {
            // 初始化为页面字体路径
            this.fontPath = page.getFontPath();
        }
        // 如果文本间隔未初始化，则进行初始化
        if (this.wordSpace == null) {
            // 初始化文本间隔为6倍字体大小
            this.wordSpace = this.fontSize * 6;
        }
        // 如果文本行间距未初始化，则进行初始化
        if (this.leading == null) {
            // 初始化文本行间距为2倍字体大小
            this.leading = this.fontSize * 2;
        }
        // 是否需要初始化为false
        this.isNeedInit = Boolean.FALSE;
    }
}
