package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.dromara.pdf.pdfbox.core.enums.ImageType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
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
     * SVG字节码
     */
    protected static final byte[] SVG_BYTES1 = new byte[]{0x3c, 0x3f, 0x78, 0x6d, 0x6c, 0x20};
    protected static final byte[] SVG_BYTES2 = new byte[]{0x3c, 0x73, 0x76, 0x67};
    /**
     * jpeg2000字节码
     */
    protected static final byte[] J2K_BYTES2 = new byte[]{0x00, 0x00, 0x00, 0x0c, 0x6a, 0x50, 0x20, 0x20};

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
     * 写入文件
     *
     * @param image 图片对象
     * @param file  文件
     */
    @SneakyThrows
    public static void write(BufferedImage image, File file) {
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
            write(image, outputStream);
        }
    }

    /**
     * 写入文件
     *
     * @param image        图片对象
     * @param outputStream 输出流
     */
    @SneakyThrows
    public static void write(BufferedImage image, OutputStream outputStream) {
        ImageIOUtil.writeImage(image, ImageType.PNG.name(), outputStream);
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
        // svg格式
        if (isSvgImage(byteArray)) {
            try (
                    // 定义输入流
                    InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                    // 定义输出流
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
            ) {
                // 转码svg
                new org.apache.batik.transcoder.image.PNGTranscoder().transcode(new org.apache.batik.transcoder.TranscoderInput(inputStream), new org.apache.batik.transcoder.TranscoderOutput(outputStream));
                // 重置字节数组
                byteArray = outputStream.toByteArray();
            }
            // jpeg2000格式
        } else if (isJ2kImage(byteArray)) {
            try (
                    // 定义输入流
                    InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                    // 定义输出流
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
            ) {
                // 转码jpeg2000
                org.dromara.pdf.pdfbox.support.image.J2kImageHandler.transcode(inputStream, outputStream, ImageType.PNG);
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
     * 转为base64
     *
     * @param sourceImage 源图片
     * @param imageType   图片类型
     * @return 返回base64
     */
    public static String toBase64(BufferedImage sourceImage, String imageType) {
        // 如果源图片为空，则提示错误信息
        Objects.requireNonNull(sourceImage, "the source image can not be null");
        // 如果图片类型为空，则提示错误信息
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 返回base64
        return Base64.getEncoder().encodeToString(toBytes(sourceImage, imageType));
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
        BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_INT_ARGB);
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
     * 拼接图片（水平）
     *
     * @param sourceImageList 图片列表
     * @return 返回拼接后的图片对象
     */
    public static BufferedImage joinForHorizontal(List<BufferedImage> sourceImageList) {
        // 如果图片列表为空，则提示错误信息
        Objects.requireNonNull(sourceImageList, "the source image list can not be null");
        // 获取首张源图片
        BufferedImage firstImage = sourceImageList.get(0);
        // 定义图片宽度
        int imageWidth = 0;
        // 定义图片高度
        int imageHeight = firstImage.getHeight();
        // 遍历源图片列表
        for (BufferedImage sourceImage : sourceImageList) {
            // 累计宽度
            imageWidth = imageWidth + sourceImage.getWidth();
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
            // 累计x轴坐标
            x = x + sourceImage.getWidth();
            // 释放源图片
            sourceImage.flush();
        }
        // 关闭资源
        graphics.dispose();
        // 返回图片
        return image;
    }

    /**
     * 拼接图片（垂直）
     *
     * @param sourceImageList 图片列表
     * @return 返回拼接后的图片对象
     */
    public static BufferedImage joinForVertical(List<BufferedImage> sourceImageList) {
        // 如果图片列表为空，则提示错误信息
        Objects.requireNonNull(sourceImageList, "the source image list can not be null");
        // 获取首张源图片
        BufferedImage firstImage = sourceImageList.get(0);
        // 定义图片宽度
        int imageWidth = firstImage.getWidth();
        // 定义图片高度
        int imageHeight = 0;
        // 遍历源图片列表
        for (BufferedImage sourceImage : sourceImageList) {
            // 累计高度
            imageHeight = imageHeight + sourceImage.getHeight();
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
            // 累计y轴坐标
            y = y + sourceImage.getHeight();
            // 释放源图片
            sourceImage.flush();
        }
        // 关闭资源
        graphics.dispose();
        // 返回图片
        return image;
    }

    /**
     * 拆分图片（水平）
     *
     * @param image 图片
     * @param width 宽度
     * @return 返回图片列表
     */
    public static List<BufferedImage> splitForHorizontal(BufferedImage image, int width) {
        Objects.requireNonNull(image, "the image can not be null");
        List<BufferedImage> imageList = new ArrayList<>(16);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int x = 0;
        int w = Math.min(width, imageWidth);
        while (x <= imageWidth && w > 0) {
            imageList.add(image.getSubimage(x, 0, w, imageHeight));
            x = x + w;
            w = Math.min(width, Math.abs(imageWidth - x));
        }
        return imageList;
    }

    /**
     * 拆分图片（垂直）
     *
     * @param image  图片
     * @param height 高度
     * @return 返回图片列表
     */
    public static List<BufferedImage> splitForVertical(BufferedImage image, int height) {
        Objects.requireNonNull(image, "the image can not be null");
        List<BufferedImage> imageList = new ArrayList<>(16);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int y = 0;
        int h = Math.min(height, imageHeight);
        while (y + h < imageHeight) {
            imageList.add(image.getSubimage(0, y, imageWidth, h));
            y = y + h;
            if (y + h > imageHeight) {
                h = Math.abs(imageHeight - y);
                y = imageHeight - h;
                imageList.add(image.getSubimage(0, y, imageWidth, h));
            }
        }
        return imageList;
    }

    /**
     * 是否svg图像（判断是否为xml）
     *
     * @param bytes 字节数组
     * @return 返回布尔值，true为是，false为否
     */
    public static boolean isSvgImage(byte[] bytes) {
        return isSvgImage1(bytes) || isSvgImage2(bytes);
    }

    /**
     * 是否jpeg2000图像
     *
     * @param bytes 字节数组
     * @return 返回布尔值，true为是，false为否
     */
    public static boolean isJ2kImage(byte[] bytes) {
        // 定义结果
        boolean result = true;
        // 遍历字节数组
        for (int i = 0; i < J2K_BYTES2.length; i++) {
            // 字节码不一致
            if (J2K_BYTES2[i] != bytes[i]) {
                // 重置结果
                result = false;
                // 结束循环
                break;
            }
        }
        // 返回结果
        return result;
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
        return new Rectangle((int) (src.getWidth() + offsetWidth * num), (int) (src.getHeight() + offsetHeight * num));
    }

    /**
     * 是否svg图像（判断是否为xml）
     *
     * @param bytes 字节数组
     * @return 返回布尔值，true为是，false为否
     */
    private static boolean isSvgImage1(byte[] bytes) {
        // 定义长度
        int length = 5;
        // 定义最大长度
        int maxLength = 24;
        // 定义索引
        int index = 0;
        // 定义标记字节
        byte flag = SVG_BYTES1[index];
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
                flag = SVG_BYTES1[++index];
            }
        }
        // 返回是否svg图像
        return index == length;
    }

    /**
     * 是否svg图像（判断是否为xml）
     *
     * @param bytes 字节数组
     * @return 返回布尔值，true为是，false为否
     */
    private static boolean isSvgImage2(byte[] bytes) {
        // 定义标记字节
        boolean flag = true;
        // 遍历字节数组
        for (int idx = 0; idx < SVG_BYTES2.length; idx++) {
            // 当前字节与标记字节相等
            if (bytes[idx] != SVG_BYTES2[idx]) {
                // 重置标记
                flag = false;
                // 结束
                break;
            }
        }
        // 返回是否svg图像
        return flag;
    }
}
