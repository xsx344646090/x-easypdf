package wiki.xsx.core.pdf.util;

import lombok.SneakyThrows;
import wiki.xsx.core.pdf.component.image.XEasyPdfImageType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * 图片工具
 *
 * @author xsx
 * @date 2020/3/30
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
public class XEasyPdfImageUtil {

    /**
     * 读取文件
     *
     * @param imageFile 图片文件
     * @return 返回图片对象
     */
    @SneakyThrows
    public static BufferedImage read(File imageFile) {
        // 如果图片文件为空，则提示错误信息
        if (imageFile == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
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
        if (imageStream == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
        // 读取图片
        return ImageIO.read(imageStream);
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
        if (bytes == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
        // 创建输入流
        try (InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
            // 读取图片
            return ImageIO.read(inputStream);
        }
    }

    /**
     * 写入文件
     *
     * @param image        图片对象
     * @param imageType    图片类型
     * @param outputStream 输出流
     */
    @SneakyThrows
    public static void write(BufferedImage image, XEasyPdfImageType imageType, OutputStream outputStream) {
        // 写入图片
        ImageIO.write(image, imageType.name(), outputStream);
    }

    /**
     * 解析图片类型
     *
     * @param imageFile 图片文件
     * @return 返回图片类型
     */
    public static String parseType(File imageFile) {
        // 如果图片文件为空，则提示错误信息
        if (imageFile == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
        // 获取文件名称
        String name = imageFile.getName();
        // 获取最后一个点号位置
        int dot = name.lastIndexOf('.');
        // 如果位置未获取到，则提示错误信息
        if (dot == -1) {
            // 提示错误信息
            throw new IllegalArgumentException("Image type not supported: " + name);
        }
        // 返回图片后缀名
        return name.substring(dot + 1).toLowerCase();
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
        if (sourceImage == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
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
     * 转为字节数组
     *
     * @param sourceImage 源图片
     * @param imageType   图片类型
     * @return 返回字节数组
     */
    @SneakyThrows
    public static byte[] toBytes(BufferedImage sourceImage, String imageType) {
        // 如果源图片为空，则提示错误信息
        if (sourceImage == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
        // 创建字节数组输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 写入图片
            ImageIO.write(sourceImage, imageType, outputStream);
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
        if (sourceImage == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
        // 创建字节数组输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 写入图片
            ImageIO.write(sourceImage, imageType, outputStream);
            // 返回字节数组输入流
            return new ByteArrayInputStream(outputStream.toByteArray());
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
        if (sourceImage == null) {
            // 提示错误信息
            throw new IllegalArgumentException("Image can not be null");
        }
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
     * @param sourceImageList 图片列表
     * @param isHorizontal 是否水平拼接
     * @return 返回拼接后的图片对象
     */
    public static BufferedImage join(List<BufferedImage> sourceImageList, boolean isHorizontal) {
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
        }
        // 否则累计高度
        else {
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
            }
            // 否则累计y轴坐标
            else {
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
     * 创建图形
     *
     * @param image 图片
     * @return 返回图形
     */
    private static Graphics2D createGraphics(BufferedImage image) {
        // 创建2d图像
        Graphics2D graphics = image.createGraphics();
        // 设置插值
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 设置抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置文本抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
        Rectangle src = new Rectangle(new Dimension(width, height));
        final int angle = 90;
        final int num = 2;
        if (radians >= angle) {
            if (radians / angle % num == 1) {
                return new Rectangle((int) src.getHeight(), (int) src.getWidth());
            }
            radians = radians % angle;
        }
        double radius = Math.sqrt(src.getHeight() * src.getHeight() + src.getWidth() * src.getWidth()) / num;
        double len = num * Math.sin(Math.toRadians(radians) / num) * radius;
        double radiansAlpha = (Math.PI - Math.toRadians(radians)) / num;
        double radiansWidth = Math.atan(src.getHeight() / src.getWidth());
        double radiansHeight = Math.atan(src.getWidth() / src.getHeight());
        int lenWidth = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansWidth)));
        int lenHeight = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansHeight)));
        return new Rectangle((src.width + lenWidth * num), (src.height + lenHeight * num));
    }
}
