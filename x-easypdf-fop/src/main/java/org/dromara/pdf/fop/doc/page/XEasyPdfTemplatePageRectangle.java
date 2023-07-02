package org.dromara.pdf.fop.doc.page;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * pdf模板-页面尺寸
 *
 * @author xsx
 * @date 2022/11/10
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
public class XEasyPdfTemplatePageRectangle implements Serializable {

    private static final long serialVersionUID = -8290831144835083352L;

    /**
     * 默认每英寸像素点
     */
    private static final Integer DEFAULT_DPI = 72;
    /**
     * 每毫米像素点
     */
    public static final Float POINTS_PER_MM = 1 / 25.4f * DEFAULT_DPI;
    /**
     * 每英寸像素点
     */
    private Integer dpi = DEFAULT_DPI;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;

    /**
     * ************************************************** A类 ****************************************************
     * /**
     * A0
     * <p>841 * 1189，单位：mm</p>
     * <p>2383.937007 * 3370.393700，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A0 = new XEasyPdfTemplatePageRectangle(841 * POINTS_PER_MM, 1189 * POINTS_PER_MM);
    /**
     * A1
     * <p>594 * 841，单位：mm</p>
     * <p>1683.779527 * 2383.937007，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A1 = new XEasyPdfTemplatePageRectangle(594 * POINTS_PER_MM, 841 * POINTS_PER_MM);
    /**
     * A2
     * <p>420 * 594，单位：mm</p>
     * <p>1190.551181 * 1683.779527，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A2 = new XEasyPdfTemplatePageRectangle(420 * POINTS_PER_MM, 594 * POINTS_PER_MM);
    /**
     * A3
     * <p>297 * 420，单位：mm</p>
     * <p>841.889763 * 1190.551181，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A3 = new XEasyPdfTemplatePageRectangle(297 * POINTS_PER_MM, 420 * POINTS_PER_MM);
    /**
     * A4
     * <p>210 * 297，单位：mm</p>
     * <p>595.275590 * 841.889763，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A4 = new XEasyPdfTemplatePageRectangle(210 * POINTS_PER_MM, 297 * POINTS_PER_MM);
    /**
     * A5
     * <p>148 * 210，单位：mm</p>
     * <p>419.527559 * 595.275590，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A5 = new XEasyPdfTemplatePageRectangle(148 * POINTS_PER_MM, 210 * POINTS_PER_MM);
    /**
     * A6
     * <p>105 * 148，单位：mm</p>
     * <p>297.637795 * 419.527559，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A6 = new XEasyPdfTemplatePageRectangle(105 * POINTS_PER_MM, 148 * POINTS_PER_MM);
    /**
     * A7
     * <p>74 * 105，单位：mm</p>
     * <p>209.763779 * 297.637795，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A7 = new XEasyPdfTemplatePageRectangle(74 * POINTS_PER_MM, 105 * POINTS_PER_MM);
    /**
     * A8
     * <p>52 * 74，单位：mm</p>
     * <p>147.401574 * 209.763779，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle A8 = new XEasyPdfTemplatePageRectangle(52 * POINTS_PER_MM, 74 * POINTS_PER_MM);

    /**
     * ************************************************** B类 ****************************************************
     * /**
     * B0
     * <p>1030 * 1456，单位：mm</p>
     * <p>2919.685039 * 4127.244094，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B0 = new XEasyPdfTemplatePageRectangle(1030 * POINTS_PER_MM, 1456 * POINTS_PER_MM);
    /**
     * B1
     * <p>728 * 1030，单位：mm</p>
     * <p>2063.622047 * 2919.685039，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B1 = new XEasyPdfTemplatePageRectangle(728 * POINTS_PER_MM, 1030 * POINTS_PER_MM);
    /**
     * B2
     * <p>515 * 728，单位：mm</p>
     * <p>1459.842519 * 2063.622047，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B2 = new XEasyPdfTemplatePageRectangle(515 * POINTS_PER_MM, 728 * POINTS_PER_MM);
    /**
     * B3
     * <p>364 * 515，单位：mm</p>
     * <p>1031.811023 * 1459.842519，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B3 = new XEasyPdfTemplatePageRectangle(364 * POINTS_PER_MM, 515 * POINTS_PER_MM);
    /**
     * B4
     * <p>257 * 364，单位：mm</p>
     * <p>728.503937 * 1031.811023，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B4 = new XEasyPdfTemplatePageRectangle(257 * POINTS_PER_MM, 364 * POINTS_PER_MM);
    /**
     * B5
     * <p>182 * 257，单位：mm</p>
     * <p>515.905511 * 728.503937，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B5 = new XEasyPdfTemplatePageRectangle(182 * POINTS_PER_MM, 257 * POINTS_PER_MM);
    /**
     * B6
     * <p>128 * 182，单位：mm</p>
     * <p>362.834645 * 515.905511，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B6 = new XEasyPdfTemplatePageRectangle(128 * POINTS_PER_MM, 182 * POINTS_PER_MM);
    /**
     * B7
     * <p>91 * 128，单位：mm</p>
     * <p>257.952755 * 362.834645，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B7 = new XEasyPdfTemplatePageRectangle(91 * POINTS_PER_MM, 128 * POINTS_PER_MM);
    /**
     * B8
     * <p>64 * 91，单位：mm</p>
     * <p>181.417322 * 257.952755，单位：pt</p>
     */
    public static final XEasyPdfTemplatePageRectangle B8 = new XEasyPdfTemplatePageRectangle(64 * POINTS_PER_MM, 91 * POINTS_PER_MM);

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     */
    private XEasyPdfTemplatePageRectangle(float width, float height) {
        this.width = Math.abs(width);
        this.height = Math.abs(height);
    }

    /**
     * 创建页面尺寸
     *
     * @param width  宽度
     * @param height 高度
     * @return 返回页面尺寸
     */
    public static XEasyPdfTemplatePageRectangle create(float width, float height) {
        return new XEasyPdfTemplatePageRectangle(width, height);
    }

    /**
     * 获取旋转尺寸
     *
     * @param width   宽度
     * @param height  高度
     * @param radians 旋转弧度
     * @return 返回旋转后的尺寸
     */
    public static Rectangle getRotateRectangle(int width, int height, double radians) {
        Rectangle src = new Rectangle(new Dimension(width, height));
        final int angle = 90;
        final int num = 2;
        if (radians < 0) {
            radians += 360;
        }
        if (radians % 360 == 0) {
            return src;
        }
        if (radians >= angle) {
            if (radians / angle % num == 1) {
                return new Rectangle((int) src.getHeight(), (int) src.getWidth());
            }
            radians = radians % angle;
        }
        double radius = Math.sqrt(src.height * src.height + src.width * src.width) / num;
        double len = num * Math.sin(Math.toRadians(radians) / num) * radius;
        double radiansAlpha = (Math.PI - Math.toRadians(radians)) / num;
        double radiansWidth = Math.atan(src.height / (double) src.width);
        double radiansHeight = Math.atan(src.width / (double) src.height);
        int lenWidth = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansWidth)));
        int lenHeight = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansHeight)));
        return new Rectangle((src.width + lenWidth * num), (src.height + lenHeight * num));
    }

    /**
     * 设置dpi
     *
     * @param dpi 每英寸像素点
     * @return 返回页面尺寸
     */
    public XEasyPdfTemplatePageRectangle setDpi(int dpi) {
        this.dpi = Math.abs(dpi);
        this.width = this.width * this.dpi / DEFAULT_DPI;
        this.height = this.height * this.dpi / DEFAULT_DPI;
        return this;
    }

    /**
     * 切换横向
     *
     * @return 返回页面尺寸
     */
    @SuppressWarnings("all")
    public XEasyPdfTemplatePageRectangle changeLandscape() {
        return new XEasyPdfTemplatePageRectangle(this.height, this.width);
    }

    /**
     * 获取每毫米像素点
     *
     * @return 返回每毫米像素点
     */
    public float getUnit() {
        if (Objects.equals(this.dpi, DEFAULT_DPI)) {
            return POINTS_PER_MM;
        }
        return POINTS_PER_MM * this.dpi / DEFAULT_DPI;
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
}
