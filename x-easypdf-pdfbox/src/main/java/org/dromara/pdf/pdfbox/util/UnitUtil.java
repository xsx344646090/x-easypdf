package org.dromara.pdf.pdfbox.util;

import org.dromara.pdf.pdfbox.support.Constants;

/**
 * 单位工具
 *
 * @author xsx
 * @date 2024/11/22
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public class UnitUtil {
    
    /**
     * px转pt
     *
     * @param dpi   dpi
     * @param value 值
     */
    public static float px2pt(int dpi, float value) {
        return value * Constants.POINTS_PER_INCH / dpi;
    }
    
    /**
     * mm转pt
     *
     * @param value 值
     */
    public static float mm2pt(float value) {
        return value * Constants.POINTS_PER_MM;
    }
    
    /**
     * em转pt
     *
     * @param value 值
     */
    public static float em2pt(float value) {
        return value * 12;
    }
}
