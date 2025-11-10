package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

import java.awt.*;
import java.util.Objects;

/**
 * 颜色工具
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
public class ColorUtil {

    /**
     * 转为PDColor对象
     *
     * @param color 颜色对象
     * @return PDColor对象
     */
    public static PDColor toPDColor(Color color) {
        // 检查参数
        Objects.requireNonNull(color, "the color can not be null");
        // 转为PDColor对象
        return new PDColor(
                new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f},
                PDDeviceRGB.INSTANCE
        );
    }
    
    /**
     * 转为PDColor对象
     *
     * @param color 颜色对象
     * @return PDColor对象
     */
    public static String toPDColorString(Color color) {
        float[] components = toPDColor(color).getComponents();
        return String.join(" ", Float.toString(components[0]), Float.toString(components[1]), Float.toString(components[2]), "rg");
    }

    /**
     * 转为Color对象
     *
     * @param color PDColor对象
     * @return 颜色对象
     */
    @SneakyThrows
    public static Color toColor(PDColor color) {
        // 检查参数
        Objects.requireNonNull(color, "the color can not be null");
        // 转为Color对象
        return new Color(color.toRGB());
    }

    /**
     * 转为Color对象
     *
     * @param hexString 十六进制字符串
     * @return 颜色对象
     */
    public static Color toColor(String hexString) {
        // 检查参数
        Objects.requireNonNull(hexString, "the hex string can not be null");
        // 十六进制字符串转换为Color对象
        return Color.decode(hexString);
    }

    /**
     * 转为十六进制字符串
     *
     * @param color 颜色对象
     * @return 返回一个以"#"开头的十六进制值
     */
    public static String toHexString(Color color) {
        // 检查参数
        Objects.requireNonNull(color, "the color can not be null");
        // RGB值转换为十六进制字符串
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
