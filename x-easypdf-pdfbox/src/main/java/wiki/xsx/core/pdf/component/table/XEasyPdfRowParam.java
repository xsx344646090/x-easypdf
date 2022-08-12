package wiki.xsx.core.pdf.component.table;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.table.XEasyPdfCellBorder;
import wiki.xsx.core.pdf.component.table.XEasyPdfTableParam;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.util.XEasyPdfConvertUtil;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

/**
 * pdf表格行组件参数
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
class XEasyPdfRowParam implements Serializable {

    private static final long serialVersionUID = -8085463000293380631L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 单元格列表
     */
    private List<XEasyPdfCell> cells = new ArrayList<>(10);
    /**
     * 拆分行
     */
    private XEasyPdfRow splitRow;
    /**
     * 是否带有边框
     */
    private Boolean hasBorder;
    /**
     * 是否带有上边框
     */
    private XEasyPdfRow.BorderPolicy borderPolicy;
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
     * 左边距
     */
    private Float marginLeft;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 行高
     */
    private Float height;
    /**
     * 最小行高
     */
    private Float minHeight;
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
    private Float fontSize;
    /**
     * 字体颜色
     */
    private Color fontColor;
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
     * 是否分页
     */
    private Boolean isPaging;
    /**
     * 是否自动拆分行
     */
    private Boolean isAutoSplit;
    /**
     * 是否自动缩放字体大小
     */
    private Boolean isAutoScaleFontSize;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param table    pdf表格
     * @param row      pdf表格行
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table, XEasyPdfRow row) {
        // 如果单元格列表不为空，则初始化
        if (!this.cells.isEmpty()) {
            // 获取表格参数
            XEasyPdfTableParam tableParam = table.getParam();
            // 如果边框标记为空，则初始化边框标记
            if (this.hasBorder == null) {
                // 初始化边框标记
                this.hasBorder = tableParam.getHasBorder();
            }
            // 如果开启边框，则初始化边框宽度
            if (this.hasBorder) {
                // 初始化边框
                this.initBorder();
                // 如果边框宽度为空，则初始化边框宽度
                if (this.borderWidth == null) {
                    // 初始化边框宽度
                    this.borderWidth = tableParam.getBorderWidth();
                }
                // 如果边框颜色未初始化，则进行初始化
                if (this.borderColor == null) {
                    // 初始化边框颜色
                    this.borderColor = tableParam.getBorderColor();
                }
            }
            // 否则重置边框宽度为0
            else {
                // 重置边框宽度为0
                this.borderWidth = 0F;
            }
            // 如果内容模式未初始化，则初始化为页面内容模式
            if (this.contentMode == null) {
                // 初始化为页面内容模式
                this.contentMode = tableParam.getContentMode();
            }
            // 如果是否重置上下文未初始化，则初始化为表格是否重置上下文
            if (this.isResetContext == null) {
                // 初始化为表格是否重置上下文
                this.isResetContext = tableParam.getIsResetContext();
            }
            // 如果字体路径未初始化，则初始化为表格字体路径
            if (this.fontPath == null) {
                // 初始化为表格字体路径
                this.fontPath = tableParam.getFontPath();
            }
            // 如果字体大小未初始化，则进行初始化
            if (this.fontSize == null) {
                // 初始化字体大小
                this.fontSize = tableParam.getFontSize();
            }
            // 如果字体颜色未初始化，则进行初始化
            if (this.fontColor == null) {
                // 初始化字体颜色
                this.fontColor = tableParam.getFontColor();
            }
            // 如果背景颜色未初始化，则进行初始化
            if (this.backgroundColor == null) {
                // 初始化背景颜色
                this.backgroundColor = tableParam.getBackgroundColor();
            }
            // 如果水平样式未初始化，则进行初始化
            if (this.horizontalStyle == null) {
                // 初始化水平样式
                this.horizontalStyle = tableParam.getHorizontalStyle();
            }
            // 如果垂直样式未初始化，则进行初始化
            if (this.verticalStyle == null) {
                // 初始化垂直样式
                this.verticalStyle = tableParam.getVerticalStyle();
            }
            // 如果是否自动缩放字体大小未初始化，则初始化为表格是否自动缩放字体大小
            if (this.isAutoScaleFontSize == null) {
                // 初始化为表格是否自动缩放字体大小
                this.isAutoScaleFontSize = tableParam.getIsAutoScaleFontSize();
            }
            // 初始化自动拆分行
            this.isAutoSplit = tableParam.getIsAutoSplit();
            // 定义行高
            float rowHeight = 0F;
            // 遍历单元格列表
            for (XEasyPdfCell cell : this.cells) {
                // 如果单元格开启垂直合并，则跳过
                if (cell.getParam().getIsVerticalMerge()) {
                    // 跳过
                    continue;
                }
                // 初始化行高
                rowHeight = Math.max(rowHeight, cell.init(document, page, row));
            }
            // // 如果最小行高未初始化，则进行初始化
            if (this.minHeight == null) {
                // 初始化最小行高
                this.minHeight = tableParam.getMinRowHeight();
            }
            // 如果最小行高已初始化，则重置行高
            if (this.minHeight != null) {
                // 重置行高
                rowHeight = Math.max(rowHeight, this.minHeight);
            }
            // 如果行高未初始化，则进行初始化
            if (this.height == null) {
                // 初始化行高
                this.height = rowHeight;
            }
            // 初始化Y轴起始坐标 = 当前Y轴起始坐标
            this.beginY = this.checkPage(document, page, table, row);
            // 如果X轴起始坐标为初始化，则进行初始化
            if (this.beginX == null) {
                // 如果表格X轴起始坐标不为空，则重置为表格X轴起始坐标
                if (tableParam.getBeginX() != null) {
                    // 初始化X轴起始坐标 = 表格X轴起始坐标
                    this.beginX = tableParam.getBeginX();
                }
                // 否则重置为左边距
                else {
                    // 如果左边距不为空，则X轴起始坐标 = 左边距
                    if (this.marginLeft != null) {
                        // 初始化X轴起始坐标 = 左边距
                        this.beginX = this.marginLeft;
                    } else {
                        // 初始化X轴起始坐标 = 表格左边距
                        this.beginX = tableParam.getMarginLeft();
                    }
                }
            }
        }
    }

    /**
     * 初始化边框
     */
    private void initBorder() {
        // 如果边框策略不为空，则初始化边框
        if (this.borderPolicy != null) {
            // 判断边框策略
            switch (this.borderPolicy) {
                // 仅左右边框
                case ONLY_LEFT_RIGHT: {
                    // 初始化左右边框
                    this.initBorder(false, false, true, true);
                    // 结束
                    break;
                }
                // 仅上下边框
                case ONLY_TOP_BOTTOM: {
                    // 初始化上下边框
                    this.initBorder(true, true, false, false);
                    // 结束
                    break;
                }
                // 仅上左右边框
                case ONLY_TOP_LEFT_RIGHT: {
                    // 初始化上左右边框
                    this.initBorder(true, false, true, true);
                    // 结束
                    break;
                }
                // 仅下左右边框
                case ONLY_BOTTOM_LEFT_RIGHT: {
                    // 初始化下左右边框
                    this.initBorder(false, true, true, true);
                    // 结束
                    break;
                }
                // 仅上边框
                case ONLY_TOP: {
                    // 初始化上边框
                    this.initBorder(true, false, false, false);
                    // 结束
                    break;
                }
                // 仅下边框
                case ONLY_BOTTOM: {
                    // 初始化下边框
                    this.initBorder(false, true, false, false);
                    // 结束
                    break;
                }
                // 无边框
                default: this.initBorder(false, false, false, false);
            }
        }
    }

    /**
     * 初始化边框
     *
     * @param hasTop    是否包含上边框
     * @param hasBottom 是否包含下边框
     * @param hasLeft   是否包含左边框
     * @param hasRight  是否包含右边框
     */
    private void initBorder(boolean hasTop, boolean hasBottom, boolean hasLeft, boolean hasRight) {
        /**
         * 如果单元格列表不为空，则初始化边框
         */
        if (!this.cells.isEmpty()) {
            // 遍历单元格列表
            for (XEasyPdfCell cell : this.cells) {
                // 关闭边框
                cell.disableBorder();
                // 如果有上边框，则开启上边框
                if (hasTop) {
                    // 开启上边框
                    cell.enableTopBorder();
                }
                // 如果有下边框，则开启下边框
                if (hasBottom) {
                    // 开启下边框
                    cell.enableBottomBorder();
                }
            }
            // 如果有左边框，则开启第一列左边框
            if (hasLeft) {
                // 开启第一列左边框
                this.cells.get(0).enableLeftBorder();
            }
            // 如果有右边框，则开启最后一列右边框
            if (hasRight) {
                // 开启最后一列右边框
                this.cells.get(this.cells.size() - 1).enableRightBorder();
            }
        }
    }

    /**
     * 分页检查
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param table    pdf表格
     * @param row      pdf表格行
     * @return 返回Y轴起始坐标
     */
    private float checkPage(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table, XEasyPdfRow row) {
        // 定义页脚高度
        float footerHeight = 0F;
        // 如果允许添加页脚，且页脚不为空则初始化页脚高度
        if (page.isAllowFooter() && page.getFooter() != null) {
            // 初始化页脚高度
            footerHeight = page.getFooter().getHeight(document, page);
        }
        // 获取当前页面Y轴起始坐标
        Float pageY = page.getPageY();
        // 获取当前Y轴起始坐标 = 当前页面Y轴起始坐标 - 上边距
        float currentY = pageY - this.marginTop;
        // 如果分页标识未初始化，则进行初始化
        if (this.isPaging == null) {
            // 重置分页标识为当前Y轴起始坐标-页脚高度-行高小于表格下边距
            this.isPaging = currentY - footerHeight - this.height < table.getParam().getMarginBottom();
            // 如果分页标识为需要分页且自动拆分行，则拆分行
            if (this.isPaging) {
                // 如果开启自动拆分行，则拆分行
                if (this.isAutoSplit) {
                    // 计算最大高度
                    float maxHeight = currentY - footerHeight - table.getParam().getMarginBottom();
                    // 拆分行
                    this.split(document, page, table, row, maxHeight);
                }
                // 如果需要分页，则进行分页
                if (this.isPaging) {
                    // 分页
                    this.paging(document, page, table);
                    // 获取当前Y轴起始坐标 = 当前页面Y轴起始坐标 - 上边距
                    currentY = page.getPageY() - this.marginTop;
                }
            }
        }
        // 如果需要分页，则进行分页
        else if (this.isPaging) {
            // 分页
            this.paging(document, page, table);
            // 获取当前Y轴起始坐标 = 当前页面Y轴起始坐标 - 上边距
            currentY = page.getPageY() - this.marginTop;
            // 重置分页标识为当前Y轴起始坐标-页脚高度-行高小于表格下边距
            this.isPaging = currentY - footerHeight - this.height < table.getParam().getMarginBottom();
            // 如果仍然需要分页，且开启自动拆分行，则拆分行
            if (this.isPaging && this.isAutoSplit) {
                // 计算最大高度
                float maxHeight = currentY - footerHeight - table.getParam().getMarginBottom();
                // 拆分行
                this.split(document, page, table, row, maxHeight);
                // 如果需要分页，则进行分页
                if (this.isPaging) {
                    // 分页
                    this.paging(document, page, table);
                    // 获取当前Y轴起始坐标 = 当前页面Y轴起始坐标 - 上边距
                    currentY = page.getPageY() - this.marginTop;
                }
            }
        }
        return currentY;
    }

    /**
     * 分页
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param table    pdf表格
     */
    private void paging(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table) {
        // 获取页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 获取表格参数
        XEasyPdfTableParam tableParam = table.getParam();
        // 获取单元格边框列表
        List<XEasyPdfCellBorder> cellBorderList = tableParam.getCellBorderList();
        // 如果单元格边框列表不为空，则绘制单元格边框
        if (!cellBorderList.isEmpty()) {
            // 遍历单元格边框列表
            for (XEasyPdfCellBorder cellBorder : cellBorderList) {
                // 绘制单元格边框
                cellBorder.drawBorder();
            }
            // 重置单元格边框列表
            tableParam.setCellBorderList(new ArrayList<>(256));
        }
        // 开启页面自动定位
        page.enablePosition();
        // 添加新页面
        page.addNewPage(document, rectangle);
        // 关闭页面自动定位
        page.disablePosition();
        // 如果当前页面Y轴起始坐标未初始化，则进行初始化
        if (page.getPageY() == null) {
            // 重置当前页面Y轴起始坐标 = 页面高度 - 表格上边距
            page.setPageY(rectangle.getHeight() - tableParam.getMarginTop());
        }
        // 否则重置为当前页面Y轴起始坐标 = 当前页面Y轴起始坐标 - 表格上边距
        else {
            // 重置当前页面Y轴起始坐标 = 当前页面Y轴起始坐标 - 表格上边距
            page.setPageY(page.getPageY() - tableParam.getMarginTop());
        }
        // 绘制表头
        this.drawTitle(document, page, tableParam);
    }

    /**
     * 绘制表头
     *
     * @param document   pdf文档
     * @param page       pdf页面
     * @param tableParam pdf表格参数
     */
    private void drawTitle(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTableParam tableParam) {
        // 获取表头
        XEasyPdfTable title = tableParam.getTitle();
        // 如果表头不为空，则绘制表头
        if (title != null) {
            // 初始化
            title.init(title.getParam());
            // 绘制表头
            title.draw(document, page);
        }
    }

    /**
     * 拆分
     *
     * @param document  pdf文档
     * @param page      pdf页面
     * @param table     pdf表格
     * @param row       pdf表格行
     * @param maxHeight 最大高度
     */
    private void split(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfTable table, XEasyPdfRow row, float maxHeight) {
        // 定义拆分字典
        Map<Integer, List<String>> splitMap = new HashMap<>(this.cells.size());
        // 获取行高
        float originalHeight = this.height;
        // 拆分行
        boolean splitFlag = this.splitRow(document, page, splitMap, maxHeight);
        // 如果拆分行成功，则设置拆分行
        if (splitFlag) {
            // 重置分页标记（设置拆分行）
            this.isPaging = this.setSplitRow(document, page, row, splitMap, originalHeight, maxHeight);
        }
    }

    /**
     * 重置单元格高度
     */
    private void resetCellHeight() {
        // 遍历单元格
        for (XEasyPdfCell cell : this.cells) {
            // 重置单元格高度为空
            cell.getParam().setHeight(null);
        }
    }

    /**
     * 拆分行
     *
     * @param document  pdf文档
     * @param page      pdf页面
     * @param splitMap  拆分字典
     * @param maxHeight 最大高度
     * @return 返回布尔值，是为true，否为false
     */
    private boolean splitRow(
            XEasyPdfDocument document,
            XEasyPdfPage page,
            Map<Integer, List<String>> splitMap,
            float maxHeight
    ) {
        // 遍历单元格列表（检测是否为全文本）
        for (XEasyPdfCell cell : this.cells) {
            // 获取单元格组件
            XEasyPdfComponent component = cell.getParam().getComponent();
            // 如果不为文本，则直接返回
            if (!(component instanceof XEasyPdfText)) {
                // 返回拆分标记为否
                return false;
            }
        }
        // 遍历单元格列表
        for (int i = 0, count = this.cells.size(); i < count; i++) {
            // 获取单元格
            XEasyPdfCell cell = this.cells.get(i);
            // 获取单元格高度
            Float cellHeight = cell.getParam().getHeight();
            // 如果单元格高度为空，则重置为0
            if (cellHeight == null) {
                // 重置单元格高度为0
                cellHeight = 0F;
            }
            // 获取单元格组件
            XEasyPdfComponent component = cell.getParam().getComponent();
            // 如果单元格组件为文本组件，则拆分文本
            if (component instanceof XEasyPdfText) {
                // 转换为文本组件
                XEasyPdfText text = (XEasyPdfText) component;
                // 重置单元格高度
                cellHeight = Math.max(cellHeight, text.getHeight(document, page));
                // 如果单元格高度大于最大高度，则拆分文本
                if (cellHeight > maxHeight) {
                    // 获取待添加文本列表
                    List<String> splitTextList = text.getSplitTextList();
                    // 获取文本行数
                    int textListSize = Math.min(Math.round(((maxHeight - text.getMarginTop()) / (text.getFontSize() + text.getLeading()))), splitTextList.size());
                    // 如果文本行数大于0，则重置待添加文本列表
                    if (textListSize > 0) {
                        // 重置待添加文本列表
                        text.setSplitTextList(splitTextList.subList(0, textListSize));
                        // 重置文本高度
                        text.setMaxHeight(null);
                        // 如果文本高度大于最大高度，则重置剩余文本列表
                        if (text.getHeight(document, page) > maxHeight) {
                            // 如果文本行数等于1，则重置待添加文本列表
                            if (textListSize == 1) {
                                // 重置待添加文本列表
                                text.setSplitTextList(new ArrayList<>(0));
                                // 重置剩余文本列表
                                splitMap.put(i, splitTextList);
                            }
                            // 否则重置待添加文本列表
                            else {
                                // 重置待添加文本列表
                                text.setSplitTextList(splitTextList.subList(0, textListSize - 1));
                                // 添加剩余文本列表
                                splitMap.put(i, splitTextList.subList(textListSize - 1, splitTextList.size()));
                            }
                            // 重置文本高度
                            text.setMaxHeight(null);
                        }
                        // 否则添加剩余文本列表
                        else {
                            // 添加剩余文本列表
                            splitMap.put(i, splitTextList.subList(textListSize, splitTextList.size()));
                        }
                        // 重置单元格高度为空
                        cell.getParam().setHeight(null);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 设置拆分行
     *
     * @param row            pdf表格行
     * @param splitMap       拆分字典
     * @param originalHeight 原有行高
     * @param maxHeight      最大行高
     * @return 返回布尔值，是为true，否为false
     */
    private boolean setSplitRow(
            XEasyPdfDocument document,
            XEasyPdfPage page,
            XEasyPdfRow row,
            Map<Integer, List<String>> splitMap,
            float originalHeight,
            float maxHeight
    ) {
        // 如果拆分字典为空，则重置拆分行为空并返回
        if (splitMap.isEmpty()) {
            // 重置拆分行
            this.splitRow = null;
            // 返回需要分页
            return true;
        }
        // 重置行高
        this.height = maxHeight;
        // 计算拆分行高
        float rowHeight = originalHeight - maxHeight;
        // 获取拆分集合
        Set<Map.Entry<Integer, List<String>>> entrySet = splitMap.entrySet();
        // 创建拆分行
        XEasyPdfRow splitRow = XEasyPdfConvertUtil.toNewObject(row);
        // 获取拆分行参数
        XEasyPdfRowParam splitRowParam = splitRow.getParam();
        // 重置单元格高度
        splitRowParam.resetCellHeight();
        // 获取拆分行单元格列表
        List<XEasyPdfCell> cells = splitRowParam.getCells();
        // 遍历单元格列表
        for (int i = 0, count = this.cells.size(); i < count; i++) {
            // 获取单元格
            XEasyPdfCell cell = this.cells.get(i);
            // 获取单元格组件
            XEasyPdfComponent component = cell.getParam().getComponent();
            // 如果单元格组件为文本组件，则重置文本内容
            if (component instanceof XEasyPdfText) {
                // 转换为文本组件
                XEasyPdfText text = (XEasyPdfText) component;
                // 如果文本高度小于等于最大高度，则重置拆分行单元格内容
                if (text.getHeight(document, page) <= maxHeight) {
                    // 获取拆分行单元格
                    cell = cells.get(i);
                    // 获取拆分行单元格组件
                    component = cell.getParam().getComponent();
                    // 转换为文本组件
                    text = (XEasyPdfText) component;
                    // 重置文本内容
                    text.setSplitTextList(new ArrayList<>(0));
                    // 重置文本最大高度
                    text.setMaxHeight(null);
                }
            }
        }
        // 遍历拆分集合
        for (Map.Entry<Integer, List<String>> entry : entrySet) {
            // 获取对应单元格
            XEasyPdfCell cell = cells.get(entry.getKey());
            // 获取单元格组件
            XEasyPdfComponent component = cell.getParam().getComponent();
            // 转换为文本组件
            XEasyPdfText text = (XEasyPdfText) component;
            // 重置文本内容
            text.setSplitTextList(entry.getValue());
            // 重置文本最大高度
            text.setMaxHeight(null);
            // 获取文本高度
            float textHeight = text.getHeight(document, page);
            // 如果文本高度大于拆分行高，则重置拆分行高为文本高度
            if (textHeight > rowHeight) {
                // 重置拆分行高为文本高度
                rowHeight = textHeight;
            }
        }
        // 重置拆分行高
        splitRowParam.setHeight(rowHeight);
        // 初始化拆分行
        this.splitRow = splitRow;
        // 返回不需要分页
        return false;
    }
}
