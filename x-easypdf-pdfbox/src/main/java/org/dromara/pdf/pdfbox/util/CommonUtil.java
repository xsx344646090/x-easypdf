package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;

import java.awt.*;
import java.util.List;


/**
 * 公共工具
 *
 * @author xsx
 * @date 2024/6/20
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public class CommonUtil {

    /**
     * 初始化字体颜色及透明度
     *
     * @param stream          内容流
     * @param backgroundColor 背景色
     * @param fontStyle       字体样式
     * @param fontColor       字体颜色
     * @param fontAlpha       字体透明度
     */
    @SneakyThrows
    public static void initFontColorAndAlpha(
            PDPageContentStream stream,
            Color backgroundColor,
            FontStyle fontStyle,
            Color fontColor,
            float fontAlpha
    ) {
        // 创建扩展图形状态
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置图形状态参数
        stream.setGraphicsStateParameters(state);
        // 填充
        if (fontStyle.isFill()) {
            // 设置字体颜色
            stream.setNonStrokingColor(fontColor);
            // 设置透明度
            state.setNonStrokingAlphaConstant(fontAlpha);
        }
        // 空心
        if (fontStyle.isStroke()) {
            // 设置字体颜色
            stream.setStrokingColor(fontColor);
            // 设置透明度
            state.setStrokingAlphaConstant(fontAlpha);
        }
        // 细体
        if (fontStyle.isLight()) {
            // 设置背景颜色
            stream.setStrokingColor(backgroundColor);
            // 设置字体颜色
            stream.setNonStrokingColor(fontColor);
            // 设置透明度
            state.setNonStrokingAlphaConstant(fontAlpha);
        }
    }

    /**
     * 转基本整型数组
     *
     * @param list 列表
     * @return 返回数组
     */
    public static int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 转基本浮点型数组
     *
     * @param list 列表
     * @return 返回数组
     */
    public static float[] toFloatArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * 转基本双精度浮点型数组
     *
     * @param list 列表
     * @return 返回数组
     */
    public static double[] toDoubleArray(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
