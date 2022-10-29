package wiki.xsx.core.pdf.component.table;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * pdf表格组件
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
public class XEasyPdfTable implements XEasyPdfComponent {

    private static final long serialVersionUID = 4155761069922557517L;

    /**
     * pdf表格参数
     */
    private final XEasyPdfTableParam param = new XEasyPdfTableParam();

    /**
     * 有参构造
     *
     * @param rows pdf表格行
     */
    public XEasyPdfTable(XEasyPdfRow... rows) {
        this.addRow(rows);
    }

    /**
     * 有参构造
     *
     * @param rowList pdf表格行列表
     */
    public XEasyPdfTable(List<XEasyPdfRow> rowList) {
        this.addRow(rowList);
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回表格组件
     */
    public XEasyPdfTable setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回表格组件
     */
    public XEasyPdfTable setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格组件
     */
    public XEasyPdfTable setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回表格组件
     */
    public XEasyPdfTable setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth 边框宽度
     * @return 返回表格组件
     */
    public XEasyPdfTable setBorderWidth(float borderWidth) {
        this.param.setBorderWidth(Math.abs(borderWidth));
        return this;
    }

    /**
     * 设置边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回表格组件
     */
    public XEasyPdfTable setBorderColor(Color borderColor) {
        if (borderColor != null) {
            this.param.setBorderColor(borderColor);
        }
        return this;
    }

    /**
     * 设置边框点线长度
     *
     * @param borderLineLength 边框点线长度
     * @return 返回表格组件
     */
    public XEasyPdfTable setBorderLineLength(float borderLineLength) {
        this.param.setBorderLineLength(Math.abs(borderLineLength));
        return this;
    }

    /**
     * 设置边框点线间隔
     *
     * @param borderLineSpace 边框点线间隔
     * @return 返回表格组件
     */
    public XEasyPdfTable setBorderLineSpace(float borderLineSpace) {
        this.param.setBorderLineSpace(Math.abs(borderLineSpace));
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     * @return 返回表格组件
     */
    public XEasyPdfTable setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.param.setBackgroundColor(backgroundColor);
        }
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回表格组件
     */
    public XEasyPdfTable setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回表格组件
     */
    public XEasyPdfTable setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回表格组件
     */
    public XEasyPdfTable setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     *
     * @param style 样式
     * @return 返回表格组件
     */
    public XEasyPdfTable setHorizontalStyle(XEasyPdfPositionStyle style) {
        if (style != null) {
            if (style == XEasyPdfPositionStyle.LEFT || style == XEasyPdfPositionStyle.CENTER || style == XEasyPdfPositionStyle.RIGHT) {
                this.param.setHorizontalStyle(style);
            } else {
                throw new IllegalArgumentException("only set LEFT, CENTER or RIGHT style");
            }
        }
        return this;
    }

    /**
     * 设置垂直样式（居上、居中、居下）
     *
     * @param style 样式
     * @return 返回表格组件
     */
    public XEasyPdfTable setVerticalStyle(XEasyPdfPositionStyle style) {
        if (style != null) {
            if (style == XEasyPdfPositionStyle.TOP || style == XEasyPdfPositionStyle.CENTER || style == XEasyPdfPositionStyle.BOTTOM) {
                this.param.setVerticalStyle(style);
            } else {
                throw new IllegalArgumentException("only set TOP, CENTER or BOTTOM style");
            }
        }
        return this;
    }

    /**
     * 开启上下左右居中
     *
     * @return 返回表格组件
     */
    public XEasyPdfTable enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 关闭自动拆分行（分页时，自动拆分行数据）
     *
     * @return 返回表格组件
     */
    public XEasyPdfTable disableAutoSplitRow() {
        this.param.setIsAutoSplit(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭边框
     *
     * @return 返回表格组件
     */
    public XEasyPdfTable disableBorder() {
        this.param.setHasBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回表格组件
     */
    @Override
    public XEasyPdfTable enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自动缩放字体大小
     *
     * @return 返回表格组件
     */
    public XEasyPdfTable enableAutoScaleFontSize() {
        this.param.setIsAutoScaleFontSize(Boolean.TRUE);
        return this;
    }

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回表格组件
     */
    @Override
    public XEasyPdfTable setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度（无效）
     *
     * @param width 宽度
     * @return 返回表格组件
     */
    @Deprecated
    @Override
    public XEasyPdfTable setWidth(float width) {
        return this;
    }

    /**
     * 设置高度（无效）
     *
     * @param height 高度
     * @return 返回表格组件
     */
    @Deprecated
    @Override
    public XEasyPdfTable setHeight(float height) {
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回表格组件
     */
    @Override
    public XEasyPdfTable setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param height 高度
     * @return 返回表格组件
     */
    public XEasyPdfTable setMinRowHeight(float height) {
        this.param.setMinRowHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置表头
     *
     * @param title pdf表格
     * @return 返回表格组件
     */
    public XEasyPdfTable setTitle(XEasyPdfTable title) {
        this.param.setTitle(title);
        return this;
    }

    /**
     * 添加表格行
     *
     * @param rows pdf表格行
     * @return 返回表格组件
     */
    public final XEasyPdfTable addRow(XEasyPdfRow... rows) {
        // 如果待添加表格行不为空，则添加
        if (rows != null) {
            // 添加表格行
            this.addRow(Arrays.asList(rows));
        }
        return this;
    }

    /**
     * 添加表格行
     *
     * @param rowList pdf表格行列表
     * @return 返回表格组件
     */
    public final XEasyPdfTable addRow(List<XEasyPdfRow> rowList) {
        // 如果待添加表格行不为空，则添加
        if (rowList != null) {
            // 添加表格行
            this.param.getRows().addAll(rowList);
        }
        return this;
    }

    /**
     * 插入表格行
     *
     * @param row pdf表格行
     * @return 返回表格组件
     */
    public XEasyPdfTable insertRow(int rowIndex, XEasyPdfRow row) {
        if (row != null) {
            // 添加表格行
            this.param.getRows().add(rowIndex, row);
        }
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
        // 关闭页面自动重置定位
        page.disablePosition();
        // 初始化参数
        this.param.init(document, page);
        // 如果X轴起始坐标不为空，则设置页面X轴起始坐标
        if (this.param.getBeginX() != null) {
            // 设置页面X轴起始坐标 = 表格X轴起始坐标
            page.setPageX(this.param.getBeginX());
        }
        // 如果Y轴起始坐标不为空，则设置页面Y轴起始坐标
        if (this.param.getBeginY() != null) {
            // 设置页面Y轴起始坐标 = 表格Y轴起始坐标 - 上边距
            page.setPageY(this.param.getBeginY() - this.param.getMarginTop());
        } else {
            // 设置页面Y轴起始坐标 = 页面Y轴起始坐标 - 上边距
            page.setPageY(page.getPageY() == null ? page.getLastPage().getMediaBox().getHeight() - this.param.getMarginTop() : page.getPageY() - this.param.getMarginTop());
        }
        // 获取表头
        XEasyPdfTable title = this.param.getTitle();
        // 如果表头不为空，则绘制表头
        if (title != null) {
            // 初始化
            title.init(this.param);
            // 绘制表头
            title.draw(document, page);
        }
        // 获取表格行列表
        List<XEasyPdfRow> rows = this.param.getRows();
        // 遍历表格行列表
        for (XEasyPdfRow row : rows) {
            // 绘制表格行
            row.doDraw(document, page, this);
        }
        // 获取单元格边框列表
        List<XEasyPdfCellBorder> cellBorderList = this.param.getCellBorderList();
        // 如果单元格边框列表不为空，则绘制单元格边框
        if (!cellBorderList.isEmpty()) {
            // 遍历单元格边框列表
            for (XEasyPdfCellBorder cellBorder : cellBorderList) {
                // 绘制单元格边框
                cellBorder.drawBorder();
            }
            // 重置单元格边框列表
            cellBorderList.clear();
        }
        // 开启页面自动重置定位
        page.enablePosition();
    }

    /**
     * 获取pdf表格参数
     *
     * @return 返回表格参数
     */
    XEasyPdfTableParam getParam() {
        return this.param;
    }

    /**
     * 初始化
     *
     * @param param 表格参数
     */
    void init(XEasyPdfTableParam param) {
        this.param.init(param);
    }
}
