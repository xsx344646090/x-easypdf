package org.dromara.pdf.pdfbox.core.base;

import lombok.Getter;
import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * 尺寸
 *
 * @author xsx
 * @date 2023/12/28
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Getter
public class Size {
    /**
     * 宽度
     */
    private final Float width;
    /**
     * 高度
     */
    private final Float height;
    /**
     * 矩形
     */
    private final PDRectangle rectangle;

    /**
     * 有参构造
     *
     * @param leftX   X轴左坐标
     * @param rightX  X轴右坐标
     * @param bottomY Y轴下坐标
     * @param topY    Y轴上坐标
     */
    private Size(float leftX, float rightX, float bottomY, float topY) {
        this.width = Math.abs(rightX - leftX);
        this.height = Math.abs(topY - bottomY);
        this.rectangle = new PDRectangle(new BoundingBox(leftX, bottomY, rightX, topY));
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
    public static Size create(float leftX, float rightX, float bottomY, float topY) {
        return new Size(leftX, rightX, bottomY, topY);
    }

    /**
     * 获取X轴左坐标
     *
     * @return 返回X轴左坐标
     */
    public float getLeftX() {
        return this.rectangle.getLowerLeftX();
    }

    /**
     * 获取X轴右坐标
     *
     * @return 返回X轴右坐标
     */
    public float getRightX() {
        return this.rectangle.getUpperRightX();
    }

    /**
     * 获取Y轴下坐标
     *
     * @return 返回Y轴下坐标
     */
    public float getBottomY() {
        return this.rectangle.getLowerLeftY();
    }

    /**
     * 获取Y轴上坐标
     *
     * @return 返回Y轴上坐标
     */
    public float getTopY() {
        return this.rectangle.getUpperRightY();
    }
}
