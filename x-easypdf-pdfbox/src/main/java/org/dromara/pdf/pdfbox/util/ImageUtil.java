package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Objects;


/**
 * 图像工具
 *
 * @author xsx
 * @date 2023/3/30
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
public class ImageUtil {

    /**
     * 读取文件
     *
     * @param imageFile 图片文件
     * @return 返回图片对象
     */
    @SneakyThrows
    public static BufferedImage read(File imageFile) {
        // 如果图片文件为空，则提示错误信息
        Objects.requireNonNull(imageFile, "the image file can not be null");
        // 读取图片
        return ImageIO.read(imageFile);
    }

    /**
     * 读取文件
     *
     * @param imageStream 图片数据流
     * @return 返回图片对象
     */
    @SneakyThrows
    public static BufferedImage read(InputStream imageStream) {
        // 如果图片数据流为空，则提示错误信息
        Objects.requireNonNull(imageStream, "the image stream can not be null");
        // 读取图片
        return ImageIO.read(new BufferedInputStream(imageStream));
    }

    /**
     * 读取文件
     *
     * @param bytes 图片数据流
     * @return 返回图片对象
     */
    @SneakyThrows
    public static BufferedImage read(byte[] bytes) {
        // 如果图片数据流为空，则提示错误信息
        Objects.requireNonNull(bytes, "the image bytes can not be null");
        // 创建输入流
        try (InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
            // 读取图片
            return ImageIO.read(inputStream);
        }
    }

    /**
     * 缩放图片
     *
     * @param sourceImage 源图片
     * @param width       缩放宽度
     * @param height      缩放高度
     * @param scaleMode   缩放模式
     * @return 返回缩放后的图片对象
     */
    @SneakyThrows
    public static BufferedImage scale(BufferedImage sourceImage, int width, int height, int scaleMode) {
        // 如果源图片为空，则提示错误信息
        Objects.requireNonNull(sourceImage, "the source image can not be null");
        // 获取缩放后的图片
        Image temp = sourceImage.getScaledInstance(width, height, scaleMode);
        // 创建图片
        BufferedImage image = new BufferedImage(width, height, sourceImage.getType());
        // 创建2d图像
        Graphics2D graphics = createGraphics(image);
        // 绘制图像
        graphics.drawImage(temp, 0, 0, null);
        // 关闭资源
        graphics.dispose();
        // 返回图片
        return image;
    }

    /**
     * 重置图像字节数组
     *
     * @param bytes 字节数组
     * @return 返回字节数组
     */
    @SneakyThrows
    public static byte[] resetBytes(byte[] bytes) {
        // 字节数组校验
        Objects.requireNonNull(bytes, "the bytes can not be null");
        // 定义字节数组
        byte[] byteArray = bytes;
        // 如果svg格式
        if (isSvgImage(byteArray)) {
            try (
                    // 定义输入流
                    InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                    // 定义输出流
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
            ) {
                // 解析svg
                new PNGTranscoder().transcode(new TranscoderInput(inputStream), new TranscoderOutput(outputStream));
                // 重置字节数组
                byteArray = outputStream.toByteArray();
            }
        }
        // 返回字节数组
        return byteArray;
    }

    /**
     * 转为字节数组
     *
     * @param sourceImage 源图片
     * @param imageType   图片类型
     * @return 返回字节数组
     */
    @SneakyThrows
    public static byte[] toBytes(BufferedImage sourceImage, String imageType) {
        // 如果源图片为空，则提示错误信息
        Objects.requireNonNull(sourceImage, "the source image can not be null");
        // 如果图片类型为空，则提示错误信息
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 创建字节数组输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            // 写入图片
            ImageIOUtil.writeImage(sourceImage, imageType, outputStream);
            // 返回字节数组
            return outputStream.toByteArray();
        }
    }

    /**
     * 转为字节数组
     *
     * @param sourceImage 源图片
     * @param imageType   图片类型
     * @return 返回字节数组
     */
    @SneakyThrows
    public static InputStream toInputStream(BufferedImage sourceImage, String imageType) {
        // 如果源图片为空，则提示错误信息
        Objects.requireNonNull(sourceImage, "the source image can not be null");
        // 如果图片类型为空，则提示错误信息
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 创建字节数组输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 写入图片
            ImageIOUtil.writeImage(sourceImage, imageType, outputStream);
            // 返回输入流
            return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        }
    }

    /**
     * 旋转图片
     *
     * @param sourceImage 源图片
     * @param radians     旋转弧度
     * @return 返回旋转后的图片对象
     */
    public static BufferedImage rotate(BufferedImage sourceImage, double radians) {
        // 如果源图片为空，则提示错误信息
        Objects.requireNonNull(sourceImage, "the source image can not be null");
        // 获取图片宽度
        int imageWidth = sourceImage.getWidth();
        // 获取图片高度
        int imageHeight = sourceImage.getHeight();
        // 获取旋转尺寸
        Rectangle rectangle = getRotateRectangle(imageWidth, imageHeight, radians);
        // 创建图片
        BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, sourceImage.getType());
        // 创建2d图像
        Graphics2D graphics = createGraphics(image);
        // 转换
        graphics.translate((rectangle.width - imageWidth) / 2D, (rectangle.height - imageHeight) / 2D);
        // 旋转
        graphics.rotate(Math.toRadians(radians), imageWidth / 2D, imageHeight / 2D);
        // 绘制图像
        graphics.drawImage(sourceImage, 0, 0, null);
        // 关闭资源
        graphics.dispose();
        // 刷新图片
        sourceImage.flush();
        // 返回图片
        return image;
    }

    /**
     * 拼接图片
     *
     * @param sourceImageList 图片列表
     * @param isHorizontal    是否水平拼接
     * @return 返回拼接后的图片对象
     */
    public static BufferedImage join(List<BufferedImage> sourceImageList, boolean isHorizontal) {
        // 如果图片列表为空，则提示错误信息
        Objects.requireNonNull(sourceImageList, "the source image list can not be null");
        // 获取首张源图片
        BufferedImage firstImage = sourceImageList.get(0);
        // 定义图片宽度
        int imageWidth = 0;
        // 定义图片高度
        int imageHeight = 0;
        // 如果为水平拼接，则累计宽度
        if (isHorizontal) {
            // 重置图片高度
            imageHeight = firstImage.getHeight();
            // 遍历源图片列表
            for (BufferedImage sourceImage : sourceImageList) {
                // 累计宽度
                imageWidth = imageWidth + sourceImage.getWidth();
            }
        } else {
            // 重置图片宽度
            imageWidth = firstImage.getWidth();
            // 遍历源图片列表
            for (BufferedImage sourceImage : sourceImageList) {
                // 累计高度
                imageHeight = imageHeight + sourceImage.getHeight();
            }
        }
        // 创建图片
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, firstImage.getType());
        // 创建2d图像
        Graphics2D graphics = createGraphics(image);
        // 定义x轴坐标
        int x = 0;
        // 定义y轴坐标
        int y = 0;
        // 遍历源图片列表
        for (BufferedImage sourceImage : sourceImageList) {
            // 绘制图片
            graphics.drawImage(sourceImage, x, y, null);
            // 如果为水平拼接，则累计x轴坐标
            if (isHorizontal) {
                // 累计x轴坐标
                x = x + sourceImage.getWidth();
            } else {
                // 累计y轴坐标
                y = y + sourceImage.getHeight();
            }
            // 释放源图片
            sourceImage.flush();
        }
        // 关闭资源
        graphics.dispose();
        // 返回图片
        return image;
    }

    /**
     * 提取图像
     *
     * @param imageList 待接收图像列表
     * @param resources 页面资源
     */
    @SneakyThrows
    public static void extract(List<BufferedImage> imageList, PDResources resources) {
        // 获取页面资源内容名称
        Iterable<COSName> objectNames = resources.getXObjectNames();
        // 遍历资源内容名称
        for (COSName objectName : objectNames) {
            // 获取资源内容
            PDXObject xObject = resources.getXObject(objectName);
            // 如果资源内容为图片，则添加到待接收图片列表
            if (xObject instanceof PDImage) {
                // 添加到待接收图片列表
                imageList.add(((PDImage) xObject).getImage());
            } else if (xObject instanceof PDFormXObject) {
                // 提取图像
                extract(imageList, ((PDFormXObject) xObject).getResources());
            }
        }
    }

    /**
     * 是否svg图像
     * <p>注：判断是否为xml</p>
     *
     * @param bytes 字节数组
     * @return 返回布尔值，是为true，否为false
     */
    public static boolean isSvgImage(byte[] bytes) {
        // 定义svg字节码
        final byte[] svgBytes = new byte[]{0x3c, 0x3f, 0x78, 0x6d, 0x6c, 0x20};
        // 定义长度
        int length = 5;
        // 定义最大长度
        int maxLength = 24;
        // 定义索引
        int index = 0;
        // 定义标记字节
        byte flag = svgBytes[index];
        // 遍历字节数组
        for (int idx = 0; idx < bytes.length; idx++) {
            // 到达指定长度
            if (index == length || idx == maxLength) {
                // 结束
                break;
            }
            // 当前字节与标记字节相等
            if (bytes[idx] == flag) {
                // 重置标记
                flag = svgBytes[++index];
            }
        }
        // 返回是否svg图像
        return index == length;
    }

    /**
     * 创建图形
     *
     * @param image 图片
     * @return 返回图形
     */
    public static Graphics2D createGraphics(BufferedImage image) {
        // 如果图片为空，则提示错误信息
        Objects.requireNonNull(image, "the image can not be null");
        // 创建2d图像
        Graphics2D graphics = image.createGraphics();
        // 设置渲染提示
        graphics.setRenderingHints(RenderingHintUtil.createDefaultRenderingHints());
        // 设置笔划
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        // 返回图像
        return graphics;
    }

    /**
     * 获取旋转尺寸
     *
     * @param width   宽度
     * @param height  高度
     * @param radians 旋转弧度
     * @return 返回旋转后的尺寸
     */
    private static Rectangle getRotateRectangle(int width, int height, double radians) {
        // 定义矩形
        Rectangle src = new Rectangle(new Dimension(width, height));
        // 定义角度
        final int angle = 90;
        // 定义旋转次数
        final int num = 2;
        // 旋转角度大于等于90度
        if (radians >= angle) {
            // 旋转角度可以被90整除
            if (radians / angle % num == 1) {
                // 返回旋转后的矩形（宽高翻转）
                return new Rectangle((int) src.getHeight(), (int) src.getWidth());
            }
            // 旋转角度对90取余
            radians = radians % angle;
        }
        // 计算半径
        double radius = Math.sqrt(src.getHeight() * src.getHeight() + src.getWidth() * src.getWidth()) / num;
        // 计算旋转后的矩形长度
        double len = num * Math.sin(Math.toRadians(radians) / num) * radius;
        // 计算旋转角度
        double radiansAlpha = (Math.PI - Math.toRadians(radians)) / num;
        // 计算旋转后的矩形宽度
        double radiansWidth = Math.atan(src.getHeight() / src.getWidth());
        // 计算旋转后的矩形高度
        double radiansHeight = Math.atan(src.getWidth() / src.getHeight());
        // 计算旋转后的矩形宽度增加量
        int offsetWidth = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansWidth)));
        // 计算旋转后的矩形高度增加量
        int offsetHeight = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansHeight)));
        // 返回旋转后的矩形
        return new Rectangle((src.width + offsetWidth * num), (src.height + offsetHeight * num));
    }
}
