package org.dromara.pdf.pdfbox.component.image;

import lombok.SneakyThrows;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.dromara.pdf.pdfbox.util.XEasyPdfImageUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * pdf矢量图片组件
 *
 * @author xsx
 * @date 2022/7/20
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
public class XEasyPdfSVGImage extends XEasyPdfImage {

    private static final long serialVersionUID = 5166066820276019483L;

    /**
     * 设置图片类型
     *
     * @param imageType 图片类型（仅支持PNG/JPEG/TIFF）
     * @return 返回pdf svg图片组件
     */
    public XEasyPdfSVGImage setImageType(XEasyPdfImageType imageType) {
        if (imageType!=null) {
            this.getParam().setImageType(imageType.name().toLowerCase());
        }
        return this;
    }

    /**
     * 设置图片
     *
     * @param imageFile 待添加图片
     * @return 返回图片组件
     */
    @SneakyThrows
    @Override
    public XEasyPdfSVGImage setImage(File imageFile) {
        try (InputStream imageStream = new FileInputStream(imageFile)) {
            this.getParam().setImage(this.processSVG(imageStream));
            return this;
        }
    }

    /**
     * 设置图片
     *
     * @param imageStream 待添加图片数据流
     * @param imageType   待添加图片类型
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfSVGImage setImage(InputStream imageStream, XEasyPdfImageType imageType) {
        this.getParam().setImageType(imageType.name().toLowerCase()).setImage(this.processSVG(imageStream));
        return this;
    }

    /**
     * 处理svg
     *
     * @param inputStream 文件输入流
     * @return 返回图像对象
     */
    private BufferedImage processSVG(InputStream inputStream) {
        // 获取图片类型名称
        String imageTypeName = this.getParam().getImageType();
        // 如果图片类型名称为空，则提示错误信息
        if (imageTypeName==null) {
            // 提示错误信息
            throw new IllegalArgumentException("the image type can not be null");
        }
        // 定义图片类型
        XEasyPdfImageType imageType;
        try {
            // 获取图片类型
            imageType = XEasyPdfImageType.valueOf(imageTypeName.toUpperCase());
        } catch (Exception exception) {
            // 提示错误信息
            throw new IllegalArgumentException("the image type is unsupported: " + imageTypeName);
        }
        // 定义图像对象
        BufferedImage image;
        // 判断类型
        switch (imageType) {
            // PNG类型
            case PNG: {
                // 转为PNG图片
                image = this.toImage(inputStream, new PNGTranscoder());
                // 结束
                break;
            }
            // JPEG类型
            case JPEG: {
                // 转为JPEG图片
                image = this.toImage(inputStream, new JPEGTranscoder());
                // 结束
                break;
            }
            // TIFF类型
            default: {
                // 转为TIFF图片
                image = this.toImage(inputStream, new TIFFTranscoder());
            }
        }
        // 返回图像对象
        return image;
    }

    /**
     * 转为图片
     *
     * @param inputStream 文件输入流
     * @param transcoder  转码器
     * @return 返回图像对象
     */
    private BufferedImage toImage(InputStream inputStream, ImageTranscoder transcoder) {
        // 创建转码器输入
        TranscoderInput input = new TranscoderInput(inputStream);
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            // 转换
            transcoder.transcode(input, new TranscoderOutput(outputStream));
            // 返回图像对象
            return XEasyPdfImageUtil.read(outputStream.toByteArray());
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the file is unsupported");
        }
    }
}
