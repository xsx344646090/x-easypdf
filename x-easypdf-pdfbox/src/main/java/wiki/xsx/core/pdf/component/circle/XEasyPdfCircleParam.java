package wiki.xsx.core.pdf.component.circle;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * pdf圆形参数
 *
 * @author xsx
 * @date 2022/1/26
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
class XEasyPdfCircleParam implements Serializable {

    private static final long serialVersionUID = -3306952717958325982L;

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
     * 半径
     */
    private Float radius;
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
        // 如果半径未设置，则抛异常
        if (this.radius == null) {
            throw new RuntimeException("the radius can not be null");
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
            // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 圆形半径，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 圆形半径
            this.beginY = pageY == null ?
                    rectangle.getHeight() - this.marginTop - this.radius :
                    pageY - this.marginTop - this.radius;
        }
        // 如果检查页面为真，并且Y轴起始坐标-页脚高度小于下边距，则进行分页
        if (this.checkPage && this.beginY - footerHeight < this.marginBottom) {
            // 添加新页面
            page.addNewPage(document, rectangle);
            // 获取当前页面Y轴起始坐标
            Float pageY = page.getPageY();
            // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 圆形半径，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 圆形半径
            this.beginY = pageY == null ?
                    rectangle.getHeight() - this.marginTop - this.radius :
                    pageY - this.marginTop - this.radius;
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

    /**
     * 初始化数据坐标点
     *
     * @return 返回数据坐标点列表
     */
    List<XEasyPdfCircle.Point> initDataPoints() {
        // 定义4个圆形数据坐标点
        List<XEasyPdfCircle.Point> points = new ArrayList<>(4);
        // 添加数据上坐标点
        points.add(new XEasyPdfCircle.Point(this.beginX, this.beginY + this.radius));
        // 添加数据右坐标点
        points.add(new XEasyPdfCircle.Point(this.beginX + this.radius, this.beginY));
        // 添加数据下坐标点
        points.add(new XEasyPdfCircle.Point(this.beginX, this.beginY - this.radius));
        // 添加数据左坐标点
        points.add(new XEasyPdfCircle.Point(this.beginX - this.radius, this.beginY));
        return points;
    }

    /**
     * 初始化控制坐标点
     *
     * @param dataPoints 数据坐标点列表
     * @return 返回控制坐标点列表
     */
    List<XEasyPdfCircle.Point> initCtrlPoints(List<XEasyPdfCircle.Point> dataPoints) {
        // 计算补偿
        final float offset = this.radius * 0.551915024494F;
        // 定义8个圆形控制坐标点
        List<XEasyPdfCircle.Point> points = new ArrayList<>(8);
        // 获取数据上坐标点
        XEasyPdfCircle.Point top = dataPoints.get(0);
        // 添加上右控制坐标点
        points.add(new XEasyPdfCircle.Point(top.getX() + offset, top.getY()));
        // 获取数据右坐标点
        XEasyPdfCircle.Point right = dataPoints.get(1);
        // 添加右上控制坐标点
        points.add(new XEasyPdfCircle.Point(right.getX(), right.getY() + offset));
        // 添加右下控制坐标点
        points.add(new XEasyPdfCircle.Point(right.getX(), right.getY() - offset));
        // 获取数据下坐标点
        XEasyPdfCircle.Point bottom = dataPoints.get(2);
        // 添加下右控制坐标点
        points.add(new XEasyPdfCircle.Point(bottom.getX() + offset, bottom.getY()));
        // 添加下左控制坐标点
        points.add(new XEasyPdfCircle.Point(bottom.getX() - offset, bottom.getY()));
        // 获取数据左坐标点
        XEasyPdfCircle.Point left = dataPoints.get(3);
        // 添加左下控制坐标点
        points.add(new XEasyPdfCircle.Point(left.getX(), left.getY() - offset));
        // 添加左上控制坐标点
        points.add(new XEasyPdfCircle.Point(left.getX(), left.getY() + offset));
        // 添加上左控制坐标点
        points.add(new XEasyPdfCircle.Point(top.getX() - offset, top.getY()));
        return points;
    }
}
