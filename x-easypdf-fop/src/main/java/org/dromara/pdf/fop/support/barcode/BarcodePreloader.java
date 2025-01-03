package org.dromara.pdf.fop.support.barcode;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import org.apache.xmlgraphics.image.loader.ImageContext;
import org.apache.xmlgraphics.image.loader.ImageInfo;
import org.apache.xmlgraphics.image.loader.impl.AbstractImagePreloader;
import org.dromara.pdf.fop.util.ImageUtil;
import org.w3c.dom.Node;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条形码预加载器
 *
 * @author xsx
 * @date 2022/10/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class BarcodePreloader extends AbstractImagePreloader {

    /**
     * 图像缓存
     */
    private static final Map<String, BufferedImage> CACHE = new HashMap<>(10);
    /**
     * 缓存锁
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * 预加载图像
     *
     * @param originalURI 原始地址
     * @param src         图像节点
     * @param context     上下文
     * @return 返回图像信息
     */
    @SuppressWarnings("all")
    @SneakyThrows
    @Override
    public ImageInfo preloadImage(String originalURI, Source src, ImageContext context) {
        // 如果图像节点为dom节点，则创建条形码图像
        if (src instanceof DOMSource) {
            // 转换为dom节点
            DOMSource domSource = (DOMSource) src;
            // 获取条形码节点
            Node barcode = domSource.getNode().getFirstChild();
            // 如果节点不为空且为条形码节点，则创建条形码图像
            if (barcode != null && BarcodeImageHandler.IMAGE_TYPE.equals(barcode.getLocalName())) {
                // 初始化条形码配置
                BarCodeConfig config = BarCodeConfig.init(barcode.getAttributes());
                // 生成条形码uri
                String uri = new String(MessageDigest.getInstance("MD5").digest(config.toString().getBytes())).intern();
                // 创建图像信息
                ImageInfo imageInfo = new ImageInfo(uri, BarcodeImageHandler.MIME_TYPE);
                // 添加条形码图像
                imageInfo.getCustomObjects().put(BarcodeImageHandler.IMAGE_TYPE, this.getImage(uri, config));
                // 返回图像信息
                return imageInfo;
            }
        }
        // 返回空
        return null;
    }

    /**
     * 获取图像
     *
     * @param uri    图像路径
     * @param config 条形码配置
     * @return 返回图像
     */
    private BufferedImage getImage(String uri, BarCodeConfig config) {
        // 是否缓存
        if (config.getIsCache()) {
            // 获取图像
            BufferedImage barcodeImage = CACHE.get(uri);
            // 如果图像为空，则创建图像
            if (barcodeImage == null) {
                try {
                    // 加锁
                    LOCK.lock();
                    // 再次获取图像
                    barcodeImage = CACHE.get(uri);
                    // 如果图像为空，则创建图像
                    if (barcodeImage == null) {
                        // 创建图像
                        barcodeImage = this.createBarCodeImage(config);
                        // 添加缓存
                        CACHE.put(uri, barcodeImage);
                    }
                } finally {
                    // 解锁
                    LOCK.unlock();
                }
            }
            // 返回图像
            return barcodeImage;
        }
        // 返回图像
        return this.createBarCodeImage(config);
    }

    /**
     * 创建条形码图像
     *
     * @param config 条形码配置
     * @return 返回条形码图像
     */
    @SneakyThrows
    private BufferedImage createBarCodeImage(BarCodeConfig config) {
        // 获取条形码图像
        BufferedImage barCodeImage = this.getBarCodeImage(
                new MultiFormatWriter().encode(
                        config.getContent(),
                        config.getType(),
                        config.getWidth(),
                        config.getHeight(),
                        config.getEncodeHints()
                ),
                config
        );
        // 如果文字不为空，则添加图像文字
        if (config.getWords() != null) {
            // 添加图像文字
            barCodeImage = this.addImageWords(barCodeImage, config);
        }
        // 如果需要旋转，则重置图像为旋转后的图像
        if (config.isRotate()) {
            // 重置图像为旋转后的图像
            barCodeImage = ImageUtil.rotate(barCodeImage, config.getRotateRectangle(), config.getRadians());
        }
        // 返回图像
        return barCodeImage;
    }

    /**
     * 获取条形码图像
     *
     * @param matrix 条形码矩阵
     * @param config 条形码配置
     * @return 返回条形码图像
     */
    private BufferedImage getBarCodeImage(BitMatrix matrix, BarCodeConfig config) {
        // 移除白边
        if (config.getIsNoWhiteBorder()) {
            matrix = this.removeWhiteBorder(matrix);
        }
        // 获取前景色
        final int onColor = config.getOnColor().getRGB();
        // 获取背景色
        final int offColor = config.getOffColor().getRGB();
        // 获取宽度
        int width = matrix.getWidth();
        // 获取高度
        int height = matrix.getHeight();
        // 定义图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 定义行像素
        int[] rowPixels = new int[width];
        // 定义位数组
        BitArray row = new BitArray(width);
        // 循环高度
        for (int y = 0; y < height; y++) {
            // 获取位数组
            row = matrix.getRow(y, row);
            // 循环宽度
            for (int x = 0; x < width; x++) {
                // 初始化行像素
                rowPixels[x] = row.get(x) ? onColor : offColor;
            }
            // 设置RGB
            image.setRGB(0, y, width, 1, rowPixels, 0, width);
        }
        // 返回图片
        return image;
    }
    
    /**
     * 移除白边
     *
     * @param matrix 矩阵
     * @return 返回矩阵
     */
    private BitMatrix removeWhiteBorder(BitMatrix matrix) {
        // 获取矩阵的外接矩形
        int[] rectangle = matrix.getEnclosingRectangle();
        // 获取矩形的宽度
        int width = rectangle[2] + 1;
        // 获取矩形的高度
        int height = rectangle[3] + 1;
        // 创建一个BitMatrix对象，宽度为width，高度为height
        BitMatrix bitMatrix = new BitMatrix(width, height);
        // 清空BitMatrix对象
        bitMatrix.clear();
        // 遍历矩阵中的每个元素
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 如果矩阵中的元素为true，则在BitMatrix对象中设置对应的元素为true
                if (matrix.get(i + rectangle[0], j + rectangle[1])) {
                    bitMatrix.set(i, j);
                }
            }
        }
        // 返回BitMatrix对象
        return bitMatrix;
    }

    /**
     * 添加图像文字
     *
     * @param image 图像
     * @return 返回添加文字后的图像
     */
    @SuppressWarnings("all")
    private BufferedImage addImageWords(BufferedImage image, BarCodeConfig config) {
        // 获取图像宽度
        int width = image.getWidth();
        // 获取图像高度
        int height = image.getHeight();
        // 定义转换图像
        BufferedImage out = new BufferedImage(width, height + config.getWordsSize(), BufferedImage.TYPE_INT_RGB);
        // 创建图像图形
        Graphics2D graphics = out.createGraphics();
        // 设置插值
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 设置图像抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置文本抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 设置笔划规范化控制参数
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        // 设置笔划
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        // 设置条形码背景色
        graphics.setColor(config.getOffColor());
        // 填充矩形
        graphics.fillRect(0, 0, width, height + config.getWordsSize());
        // 设置文字颜色
        graphics.setColor(config.getWordsColor());
        // 设置图像
        graphics.drawImage(image, 0, 0, width, height, null);
        // 设置字体
        graphics.setFont(new Font(config.getWordsFamily(), config.getWordsStyle(), config.getWordsSize()));
        // 文字长度
        int strWidth = graphics.getFontMetrics().stringWidth(config.getWords());
        // 定义X轴开始坐标（居中显示）
        int beginX = (width - strWidth) / 2 - config.getWordsOffsetX();
        // 定义Y轴开始坐标
        int beginY = height + config.getWordsSize() - config.getWordsOffsetY();
        // 设置文字
        graphics.drawString(config.getWords(), beginX, beginY);
        // 资源释放
        graphics.dispose();
        // 刷新图像
        image.flush();
        // 返回转换图像
        return out;
    }
}
