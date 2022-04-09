package wiki.xsx.core.pdf.component.table;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf表格组件参数
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
class XEasyPdfTableParam {
    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 单元格边框列表
     */
    private List<XEasyPdfCellBorder> cellBorderList = new ArrayList<>(256);
    /**
     * 是否自动表头
     */
    private Boolean isAutoTitle = false;
    /**
     * 表头行
     */
    private XEasyPdfRow titleRow;
    /**
     * 行列表
     */
    private List<XEasyPdfRow> rows = new ArrayList<>(256);
    /**
     * 是否带有边框
     */
    private Boolean hasBorder = true;
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
    private float borderWidth = 1F;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 5F;
    /**
     * 下边距
     */
    private Float marginBottom = 0F;
    /**
     * X轴起始坐标
     */
    private Float beginX;
    /**
     * Y轴起始坐标
     */
    private Float beginY;
    /**
     * 默认字体样式
     */
    private XEasyPdfDefaultFontStyle defaultFontStyle;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体
     */
    private PDFont font;
    /**
     * 字体大小
     */
    private float fontSize = 12F;
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
     * 是否完成绘制
     */
    private boolean isDraw = false;

    /**
     * 初始化
     * @param document pdf文档
     * @param page pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果内容模式未初始化，则初始化为页面内容模式
        if (this.contentMode==null) {
            // 初始化为页面内容模式
            this.contentMode = page.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化为页面是否重置上下文
        if (this.isResetContext==null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 如果默认字体未初始化，则初始化为页面默认字体
        if (this.defaultFontStyle==null) {
            // 初始化为页面默认字体
            this.defaultFontStyle = page.getDefaultFontStyle();
        }
        // 如果字体路径未初始化，则初始化为默认字体路径
        if (this.fontPath==null) {
            // 初始化为默认字体路径
            this.fontPath = this.defaultFontStyle.getPath();
        }
        // 初始化字体
        this.font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath, true);
        // 如果背景颜色未初始化，则进行初始化
        if (this.backgroundColor==null) {
            // 初始化背景颜色
            this.backgroundColor = page.getBackgroundColor();
        }
    }
}
