package wiki.xsx.core.pdf.component.table;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.table.XEasyPdfCell;
import wiki.xsx.core.pdf.component.table.XEasyPdfRowParam;
import wiki.xsx.core.pdf.component.table.XEasyPdfTable;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * pdf表格行组件
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
public class XEasyPdfRow implements Serializable {

    private static final long serialVersionUID = 2954489044413380387L;

    /**
     * pdf表格行参数
     */
    private final XEasyPdfRowParam param = new XEasyPdfRowParam();

    /**
     * 有参构造
     *
     * @param cells pdf单元格
     */
    public XEasyPdfRow(XEasyPdfCell... cells) {
        if (cells != null) {
            Collections.addAll(this.param.getCells(), cells);
        }
    }

    /**
     * 有参构造
     *
     * @param cellList pdf单元格列表
     */
    public XEasyPdfRow(List<XEasyPdfCell> cellList) {
        if (cellList != null) {
            this.param.getCells().addAll(cellList);
        }
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回表格行组件
     */
    public XEasyPdfRow setContentMode(XEasyPdfComponent.ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回表格行组件
     */
    public XEasyPdfRow setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回表格行组件
     */
    public XEasyPdfRow setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回表格行组件
     */
    public XEasyPdfRow setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回表格行组件
     */
    public XEasyPdfRow setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     * @return 返回表格行组件
     */
    public XEasyPdfRow setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.param.setBackgroundColor(backgroundColor);
        }
        return this;
    }

    /**
     * 边框宽度
     *
     * @param lineWidth 宽度
     * @return 返回表格行组件
     */
    public XEasyPdfRow setBorderWidth(float lineWidth) {
        this.param.setBorderWidth(Math.abs(lineWidth));
        return this;
    }

    /**
     * 设置边框颜色（开启边框时生效）
     *
     * @param borderColor 边框颜色
     * @return 返回表格行组件
     */
    public XEasyPdfRow setBorderColor(Color borderColor) {
        if (borderColor != null) {
            this.param.setBorderColor(borderColor);
        }
        return this;
    }

    /**
     * 设置行高
     *
     * @param height 行高
     * @return 返回表格行组件
     */
    public XEasyPdfRow setHeight(float height) {
        this.param.setHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置最小行高
     *
     * @param minHeight 行高
     * @return 返回表格行组件
     */
    public XEasyPdfRow setMinHeight(float minHeight) {
        this.param.setMinHeight(Math.abs(minHeight));
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回表格行组件
     */
    public XEasyPdfRow setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回表格行组件
     */
    public XEasyPdfRow setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     *
     * @param style 样式
     * @return 返回表格行组件
     */
    public XEasyPdfRow setHorizontalStyle(XEasyPdfPositionStyle style) {
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
     * @return 返回表格行组件
     */
    public XEasyPdfRow setVerticalStyle(XEasyPdfPositionStyle style) {
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
     * 设置边框策略（仅上下、仅左右、仅上左右、仅下左右）
     *
     * @param policy 边框策略
     * @return 返回表格行组件
     */
    public XEasyPdfRow setBorderPolicy(BorderPolicy policy) {
        this.param.setBorderPolicy(policy);
        return this;
    }

    /**
     * 开启上下左右居中
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 开启边框
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭边框
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow disableBorder() {
        this.param.setHasBorder(Boolean.FALSE);
        return this;
    }

    /**
     * 开启分页
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow enablePaging() {
        this.param.setIsPaging(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自动缩放字体大小
     *
     * @return 返回表格行组件
     */
    public XEasyPdfRow enableAutoScaleFontSize() {
        this.param.setIsAutoScaleFontSize(Boolean.TRUE);
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cells pdf单元格
     * @return 返回表格行组件
     */
    public XEasyPdfRow addCell(XEasyPdfCell... cells) {
        if (cells != null) {
            Collections.addAll(this.param.getCells(), cells);
        }
        return this;
    }

    /**
     * 添加单元格
     *
     * @param cellList pdf单元格列表
     * @return 返回表格行组件
     */
    public XEasyPdfRow addCell(List<XEasyPdfCell> cellList) {
        if (cellList != null) {
            this.param.getCells().addAll(cellList);
        }
        return this;
    }

    /**
     * 获取pdf表格行参数
     *
     * @return 返回表格行组件
     */
    XEasyPdfRowParam getParam() {
        return this.param;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param table    pdf表格
     */
    void doDraw(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table) {
        // 初始化参数
        this.param.init(document, page, table, this);
        // 获取单元格列表
        List<XEasyPdfCell> cells = this.param.getCells();
        // 遍历单元格列表
        for (XEasyPdfCell cell : cells) {
            // 如果单元格不为空，则进行绘制
            if (cell != null) {
                // 如果单元格为垂直合并，则跳过绘制
                if (cell.getParam().getIsVerticalMerge()) {
                    // 重置X轴起始坐标
                    this.param.setBeginX(this.param.getBeginX() + cell.getParam().getWidth() + cell.getParam().getMarginLeft());
                    // 跳过
                    continue;
                }
                // 设置X轴起始坐标
                this.param.setBeginX(this.param.getBeginX() + cell.getParam().getMarginLeft());
                // 绘制单元格
                cell.doDraw(document, page, table, this);
                // 重置X轴起始坐标
                this.param.setBeginX(this.param.getBeginX() + cell.getParam().getWidth());
            }
        }
        // 重置X轴起始坐标为空
        this.param.setBeginX(null);
        // 重置页面Y轴起始坐标
        page.setPageY(this.param.getBeginY() - this.param.getHeight());
        // 获取拆分行
        XEasyPdfRow splitRow = this.param.getSplitRow();
        // 如果拆分行不为空，则绘制拆分行
        if (splitRow != null) {
            // 绘制拆分行
            splitRow.doDraw(document, page, table);
        }
    }

    /**
     * 表格行边框策略
     */
    public enum BorderPolicy {
        /**
         * 仅左右边框
         */
        ONLY_LEFT_RIGHT,
        /**
         * 仅上下边框
         */
        ONLY_TOP_BOTTOM,
        /**
         * 仅上左右边框
         */
        ONLY_TOP_LEFT_RIGHT,
        /**
         * 仅下左右边框
         */
        ONLY_BOTTOM_LEFT_RIGHT,
        /**
         * 仅上边框
         */
        ONLY_TOP,
        /**
         * 仅下边框
         */
        ONLY_BOTTOM,
        /**
         * 无边框
         */
        NONE;

    }
}
