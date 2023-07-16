package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import org.dromara.pdf.pdfbox.enums.BorderStyle;

import java.awt.*;

/**
 * 边框参数
 *
 * @author xsx
 * @date 2023/6/21
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class BorderParam {
    /**
     * 样式
     */
    private BorderStyle style;
    /**
     * 线宽
     */
    private Float width;
    /**
     * 点线长度
     */
    private Float lineLength;
    /**
     * 点线间隔
     */
    private Float lineSpace;
    /**
     * 上边框颜色
     */
    private Color topColor;
    /**
     * 下边框颜色
     */
    private Color bottomColor;
    /**
     * 左边框颜色
     */
    private Color leftColor;
    /**
     * 右边框颜色
     */
    private Color rightColor;
    /**
     * 是否上边框
     */
    private Boolean isTop;
    /**
     * 是否下边框
     */
    private Boolean isBottom;
    /**
     * 是否左边框
     */
    private Boolean isLeft;
    /**
     * 是否右边框
     */
    private Boolean isRight;
}
