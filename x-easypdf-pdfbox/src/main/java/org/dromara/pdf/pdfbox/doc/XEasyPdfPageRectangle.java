package org.dromara.pdf.pdfbox.doc;

import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.Serializable;

/**
 * pdf页面尺寸
 *
 * @author xsx
 * @date 2022/6/10
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
public class XEasyPdfPageRectangle implements Serializable {

    private static final long serialVersionUID = 3832021109368673135L;

    /**
     * 每英寸像素点
     */
    private static final Integer POINTS_PER_INCH = XEasyPdfConstants.POINTS_PER_INCH;
    /**
     * 每毫米像素点
     */
    private static final Float POINTS_PER_MM = XEasyPdfConstants.POINTS_PER_MM;
    /**
     * 宽度
     */
    private final Float width;
    /**
     * 高度
     */
    private final Float height;
    /**
     * pdfBox页面尺寸
     */
    private final PDRectangle size;

    /**
     * ************************************************** A类 ****************************************************
     * /**
     * A0
     * <p>841 * 1189，单位：mm</p>
     * <p>2383.937 * 3370.3938，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A0 = new XEasyPdfPageRectangle(841 * POINTS_PER_MM, 1189 * POINTS_PER_MM);
    /**
     * A1
     * <p>594 * 841，单位：mm</p>
     * <p>1683.7795 * 2383.937，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A1 = new XEasyPdfPageRectangle(594 * POINTS_PER_MM, 841 * POINTS_PER_MM);
    /**
     * A2
     * <p>420 * 594，单位：mm</p>
     * <p>1190.5513 * 1683.7795，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A2 = new XEasyPdfPageRectangle(420 * POINTS_PER_MM, 594 * POINTS_PER_MM);
    /**
     * A3
     * <p>297 * 420，单位：mm</p>
     * <p>841.8898 * 1190.5513，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A3 = new XEasyPdfPageRectangle(297 * POINTS_PER_MM, 420 * POINTS_PER_MM);
    /**
     * A4
     * <p>210 * 297，单位：mm</p>
     * <p>595.27563 * 841.8898，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A4 = new XEasyPdfPageRectangle(210 * POINTS_PER_MM, 297 * POINTS_PER_MM);
    /**
     * A5
     * <p>148 * 210，单位：mm</p>
     * <p>419.52756 * 595.27563，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A5 = new XEasyPdfPageRectangle(148 * POINTS_PER_MM, 210 * POINTS_PER_MM);
    /**
     * A6
     * <p>105 * 148，单位：mm</p>
     * <p>297.63782 * 419.52756，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A6 = new XEasyPdfPageRectangle(105 * POINTS_PER_MM, 148 * POINTS_PER_MM);
    /**
     * A7
     * <p>74 * 105，单位：mm</p>
     * <p>209.76378 * 297.63782，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A7 = new XEasyPdfPageRectangle(74 * POINTS_PER_MM, 105 * POINTS_PER_MM);
    /**
     * A8
     * <p>52 * 74，单位：mm</p>
     * <p>147.40158 * 209.76378，单位：px</p>
     */
    public static final XEasyPdfPageRectangle A8 = new XEasyPdfPageRectangle(52 * POINTS_PER_MM, 74 * POINTS_PER_MM);

    /**
     * ************************************************** B类 ****************************************************
     * /**
     * B0
     * <p>1030 * 1456，单位：mm</p>
     * <p>2919.685 * 4127.244，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B0 = new XEasyPdfPageRectangle(1030 * POINTS_PER_MM, 1456 * POINTS_PER_MM);
    /**
     * B1
     * <p>728 * 1030，单位：mm</p>
     * <p>2063.622 * 2919.685，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B1 = new XEasyPdfPageRectangle(728 * POINTS_PER_MM, 1030 * POINTS_PER_MM);
    /**
     * B2
     * <p>515 * 728，单位：mm</p>
     * <p>1459.8425 * 2063.622，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B2 = new XEasyPdfPageRectangle(515 * POINTS_PER_MM, 728 * POINTS_PER_MM);
    /**
     * B3
     * <p>364 * 515，单位：mm</p>
     * <p>1031.811 * 1459.8425，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B3 = new XEasyPdfPageRectangle(364 * POINTS_PER_MM, 515 * POINTS_PER_MM);
    /**
     * B4
     * <p>257 * 364，单位：mm</p>
     * <p>728.50397 * 1031.811，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B4 = new XEasyPdfPageRectangle(257 * POINTS_PER_MM, 364 * POINTS_PER_MM);
    /**
     * B5
     * <p>182 * 257，单位：mm</p>
     * <p>515.9055 * 728.50397，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B5 = new XEasyPdfPageRectangle(182 * POINTS_PER_MM, 257 * POINTS_PER_MM);
    /**
     * B6
     * <p>128 * 182，单位：mm</p>
     * <p>362.83466 * 515.9055，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B6 = new XEasyPdfPageRectangle(128 * POINTS_PER_MM, 182 * POINTS_PER_MM);
    /**
     * B7
     * <p>91 * 128，单位：mm</p>
     * <p>257.95276 * 362.83466，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B7 = new XEasyPdfPageRectangle(91 * POINTS_PER_MM, 128 * POINTS_PER_MM);
    /**
     * B8
     * <p>64 * 91，单位：mm</p>
     * <p>181.41733 * 257.95276，单位：px</p>
     */
    public static final XEasyPdfPageRectangle B8 = new XEasyPdfPageRectangle(64 * POINTS_PER_MM, 91 * POINTS_PER_MM);

    /**
     * 有参构造
     *
     * @param size pdfbox页面尺寸
     */
    XEasyPdfPageRectangle(PDRectangle size) {
        this.width = size.getWidth();
        this.height = size.getHeight();
        this.size = size;
    }

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     */
    private XEasyPdfPageRectangle(float width, float height) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
        this.size = new PDRectangle(this.width, this.height);
    }

    /**
     * 有参构造
     *
     * @param leftX   宽度
     * @param rightX  高度
     * @param bottomY 高度
     * @param topY    高度
     */
    private XEasyPdfPageRectangle(float leftX, float rightX, float bottomY, float topY) {
        this.width = Math.abs(rightX - leftX);
        this.height = Math.abs(topY - bottomY);
        this.size = new PDRectangle(new BoundingBox(leftX, bottomY, rightX, topY));
    }

    /**
     * 创建页面尺寸
     *
     * @param width  宽度
     * @param height 高度
     * @return 返回页面尺寸
     */
    public static XEasyPdfPageRectangle create(float width, float height) {
        return new XEasyPdfPageRectangle(width, height);
    }

    /**
     * 创建页面尺寸
     *
     * @param leftX   X轴左坐标
     * @param rightX  X轴右坐标
     * @param bottomY Y轴下坐标
     * @param topY    Y轴上坐标
     * @return 返回页面尺寸
     */
    public static XEasyPdfPageRectangle create(float leftX, float rightX, float bottomY, float topY) {
        return new XEasyPdfPageRectangle(leftX, rightX, bottomY, topY);
    }

    /**
     * 切换横向
     *
     * @return 返回页面尺寸
     */
    public XEasyPdfPageRectangle changeLandscape() {
        return new XEasyPdfPageRectangle(this.height, this.width);
    }

    /**
     * 获取每毫米像素点
     *
     * @return 返回每毫米像素点
     */
    public static float getUnit() {
        return POINTS_PER_MM;
    }

    /**
     * 获取宽度
     *
     * @return 返回宽度
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * 获取高度
     *
     * @return 返回高度
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * 获取X轴左坐标
     *
     * @return 返回X轴左坐标
     */
    public float getLeftX() {
        return this.size.getLowerLeftX();
    }

    /**
     * 获取X轴右坐标
     *
     * @return 返回X轴右坐标
     */
    public float getRightX() {
        return this.size.getUpperRightX();
    }

    /**
     * 获取Y轴下坐标
     *
     * @return 返回Y轴下坐标
     */
    public float getBottomY() {
        return this.size.getLowerLeftY();
    }

    /**
     * 获取Y轴上坐标
     *
     * @return 返回Y轴上坐标
     */
    public float getTopY() {
        return this.size.getUpperRightY();
    }

    /**
     * 获取pdfbox页面尺寸
     *
     * @return 返回pdfbox页面尺寸
     */
    public PDRectangle getSize() {
        return this.size;
    }
}
