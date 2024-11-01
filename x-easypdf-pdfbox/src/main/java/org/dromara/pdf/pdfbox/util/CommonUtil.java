package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.enums.ContentMode;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 公共工具
 *
 * @author xsx
 * @date 2024/6/20
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
public class CommonUtil {
    
    /**
     * 初始化字体颜色及透明度
     *
     * @param stream    内容流
     * @param fontStyle 字体样式
     * @param fontColor 字体颜色
     * @param fontColor 字体描边颜色
     * @param fontAlpha 字体透明度
     */
    @SneakyThrows
    public static void initFontColorAndAlpha(
            PDPageContentStream stream,
            Color backgroundColor,
            FontStyle fontStyle,
            Color fontColor,
            Color strokColor,
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
            stream.setStrokingColor(strokColor);
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
     * 初始化矩阵
     *
     * @param stream         内容流
     * @param beginX         X轴起始坐标
     * @param beginY         Y轴起始坐标
     * @param relativeBeginX X轴相对起始坐标
     * @param relativeBeginY Y轴相对起始坐标
     * @param width          宽度
     * @param height         高度
     * @param angle          旋转角度
     * @param alpha          透明度
     */
    @SneakyThrows
    public static void initMatrix(
            PDPageContentStream stream,
            float beginX,
            float beginY,
            float relativeBeginX,
            float relativeBeginY,
            float width,
            float height,
            float angle,
            float alpha
    ) {
        // 定义X轴偏移量
        float offsetX = 0.5F * width;
        // 定义Y轴偏移量
        float offsetY = 0.5F * height;
        // 保存图形状态
        stream.saveGraphicsState();
        // 创建扩展图形状态
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置透明度
        state.setNonStrokingAlphaConstant(alpha);
        // 设置图形状态参数
        stream.setGraphicsStateParameters(state);
        // 移动到中心点
        stream.transform(
                Matrix.getTranslateInstance(
                        beginX + relativeBeginX + offsetX,
                        beginY - relativeBeginY + offsetY
                )
        );
        // 旋转
        stream.transform(Matrix.getRotateInstance(Math.toRadians(angle), 0, 0));
        // 移动到左下角
        stream.transform(Matrix.getTranslateInstance(-offsetX, -offsetY));
    }
    
    /**
     * 初始化线宽
     *
     * @param style 字体样式
     * @return 返回线宽
     */
    public static float initLineWidth(FontStyle style) {
        // 定义线宽
        float lineWidth = 1F;
        // 重置线宽
        if (style.isBold()) {
            lineWidth = 0.31415926F;
        } else if (style.isLight()) {
            lineWidth = 0F;
        } else if (style.isStroke()) {
            lineWidth = 0.5F;
        }
        // 返回线宽
        return lineWidth;
    }
    
    /**
     * 提取图像
     *
     * @param page 页面
     * @return 返回图像列表
     */
    @SneakyThrows
    public static List<BufferedImage> extractImage(PDPage page) {
        List<BufferedImage> data = new ArrayList<>(16);
        CommonUtil.extractImage(data, page.getResources());
        return data;
    }
    
    /**
     * 提取图像
     *
     * @param list      待接收图片列表
     * @param resources 页面资源
     */
    @SneakyThrows
    public static void extractImage(List<BufferedImage> list, PDResources resources) {
        // 获取页面资源内容名称
        Iterable<COSName> objectNames = resources.getXObjectNames();
        // 遍历资源内容名称
        for (COSName objectName : objectNames) {
            // 获取资源内容
            PDXObject xObject = resources.getXObject(objectName);
            // 如果资源内容为图片，则添加到待接收图片列表
            if (xObject instanceof PDImage) {
                // 添加到待接收图片列表
                list.add(((PDImage) xObject).getImage());
            } else if (xObject instanceof PDFormXObject) {
                // 提取图像
                extractImage(list, ((PDFormXObject) xObject).getResources());
            }
        }
    }
    
    /**
     * 添加背景颜色
     *
     * @param context              上下文
     * @param mode                 内容模式
     * @param isResetContentStream 是否重置内容流
     * @param rectangle            矩形
     * @param backgroundColor      背景颜色
     */
    @SneakyThrows
    public static void addBackgroundColor(Context context, ContentMode mode, boolean isResetContentStream, PDRectangle rectangle, Color backgroundColor) {
        // 添加背景颜色
        if (Objects.nonNull(backgroundColor)) {
            // 初始化内容流
            PDPageContentStream stream = new PDPageContentStream(
                    context.getTargetDocument(),
                    context.getTargetPage(),
                    mode.getMode(),
                    true,
                    isResetContentStream
            );
            // 添加矩形
            stream.addRect(rectangle);
            // 设置矩形颜色
            stream.setNonStrokingColor(backgroundColor);
            // 填充矩形
            stream.fill();
            // 关闭内容流
            stream.close();
        }
    }
    
    /**
     * 获取行尺寸
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    public static PDRectangle getRectangle(float beginX, float beginY, float width, float height) {
        // 返回尺寸
        return new PDRectangle(beginX, beginY, width, height);
    }
    
    /**
     * 获取行尺寸
     *
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    public static PDRectangle getRectangle(float width, float height) {
        // 返回尺寸
        return new PDRectangle(width, height);
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
    
    /**
     * 转字符型列表
     *
     * @param array 数组
     * @return 返回列表
     */
    public static List<Character> toCharacterList(char[] array) {
        List<Character> list = new ArrayList<>(array.length);
        for (char c : array) {
            list.add(c);
        }
        return list;
    }
    
    /**
     * 创建基本整型数组
     *
     * @param size 大小
     * @return 返回数组
     */
    public static int[] createIntArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
}
