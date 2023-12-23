package org.dromara.pdf.pdfbox.core.info;

import lombok.Builder;
import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * 图像信息
 *
 * @author xsx
 * @date 2023/10/19
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
@Data
@Builder
public class ImageInfo {
    /**
     * 页面索引
     */
    private Integer pageIndex;
    /**
     * 页面宽度
     */
    private Float pageWidth;
    /**
     * 页面高度
     */
    private Float pageHeight;
    /**
     * 图像索引
     */
    private Integer imageIndex;
    /**
     * 图像类型
     */
    private String imageType;
    /**
     * 图像真实宽度
     */
    private Integer imageRealWidth;
    /**
     * 图像真实高度
     */
    private Integer imageRealHeight;
    /**
     * 图像显示宽度
     */
    private Integer imageDisplayWidth;
    /**
     * 图像显示高度
     */
    private Integer imageDisplayHeight;
    /**
     * 图像位置坐标
     */
    private String imagePosition;
    /**
     * 图像
     */
    private BufferedImage image;
}
