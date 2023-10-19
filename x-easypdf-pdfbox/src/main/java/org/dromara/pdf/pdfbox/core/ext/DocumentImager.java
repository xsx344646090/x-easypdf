package org.dromara.pdf.pdfbox.core.ext;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.enums.RenderType;
import org.dromara.pdf.pdfbox.util.FileUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档图像器
 *
 * @author xsx
 * @date 2023/10/20
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
public class DocumentImager implements Closeable {

    /**
     * pdf文档
     */
    private PDDocument document;
    /**
     * 图像DPI
     */
    private Float dpi = 72F;
    /**
     * 渲染类型
     */
    private RenderType renderType = RenderType.EXPORT;
    /**
     * 是否内存优化（可能降低图像质量）
     */
    private Boolean isMemoryOptimization = Boolean.FALSE;
    /**
     * 是否灰度
     */
    private Boolean isGray = Boolean.FALSE;
    /**
     * 是否透明
     */
    private Boolean isAlpha = Boolean.FALSE;
    /**
     * 是否合并
     */
    private Boolean isMerge = Boolean.FALSE;
    /**
     * 是否水平合并
     */
    private Boolean isHorizontalMerge = Boolean.FALSE;

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public DocumentImager(Document document) {
        this.document = document.getTarget();
    }

    /**
     * 设置图像DPI
     *
     * @param dpi 图像DPI
     * @return 返回文档图像器
     */
    public DocumentImager setDpi(float dpi) {
        this.dpi = Math.abs(dpi);
        return this;
    }

    /**
     * 设置渲染类型
     *
     * @param renderType 渲染类型
     * @return 返回文档图像器
     */
    public DocumentImager setRenderType(RenderType renderType) {
        this.renderType = renderType;
        return this;
    }

    /**
     * 开启内存优化（可能降低图像质量）
     *
     * @return 返回文档图像器
     */
    public DocumentImager enableOptimization() {
        this.isMemoryOptimization = Boolean.TRUE;
        return this;
    }

    /**
     * 开启灰度
     *
     * @return 返回文档图像器
     */
    public DocumentImager enableGray() {
        this.isGray = Boolean.TRUE;
        return this;
    }

    /**
     * 开启透明
     *
     * @return 返回文档图像器
     */
    public DocumentImager enableAlpha() {
        this.isAlpha = Boolean.TRUE;
        return this;
    }

    /**
     * 开启水平合并
     *
     * @return 返回文档图像器
     */
    public DocumentImager enableHorizontalMerge() {
        this.isHorizontalMerge = Boolean.TRUE;
        this.isMerge = Boolean.TRUE;
        return this;
    }

    /**
     * 开启垂直合并
     *
     * @return 返回文档图像器
     */
    public DocumentImager enableVerticalMerge() {
        this.isHorizontalMerge = Boolean.FALSE;
        this.isMerge = Boolean.TRUE;
        return this;
    }

    /**
     * 转为图片（整个文档）
     *
     * @param outputPath 输出路径（目录）
     * @param imageType  图片类型
     * @return 返回文档图像器
     */

    @SneakyThrows
    public DocumentImager image(String outputPath, ImageType imageType) {
        return this.image(outputPath, imageType, null);
    }

    /**
     * 转为图片（整个文档）
     *
     * @param outputPath 输出路径（目录）
     * @param imageType  图片类型
     * @param prefix     图片名称前缀
     * @return 返回文档图像器
     */
    @SneakyThrows
    public DocumentImager image(String outputPath, ImageType imageType, String prefix) {
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (prefix == null) {
            // 初始化文档名称前缀
            prefix = "x-easypdf";
        }
        // 图片格式名称
        String imageTypeName = imageType.name().toLowerCase();
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.document);
        // 设置二次采样
        renderer.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染目的
        renderer.setDefaultDestination(this.renderType.getDestination());
        // 任务文档页面总数
        int pageCount = this.document.getNumberOfPages();
        // 如果合并图片，则合并后保存图片
        if (this.isMerge) {
            // 定义图片列表
            List<BufferedImage> imageList = new ArrayList<>(pageCount);
            // 遍历页面索引
            for (int i = 0; i < pageCount; i++) {
                // 添加图片列表
                imageList.add(renderer.renderImageWithDPI(i, this.dpi, this.getColorType()));
            }
            // 拼接图片
            BufferedImage bufferedImage = ImageUtil.join(imageList, this.isHorizontalMerge);
            // 获取输出流
            try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath + File.separator + prefix + '.' + imageTypeName))))) {
                // 写出图片
                ImageIO.write(bufferedImage, imageTypeName, outputStream);
            }
        }
        // 否则单页保存图片
        else {
            // 文件名称构造器
            StringBuilder fileNameBuilder;
            // 遍历页面索引
            for (int i = 0; i < pageCount; i++) {
                // 新建文件名称构造器
                fileNameBuilder = new StringBuilder();
                // 构建文件名称
                fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(i + 1).append('.').append(imageTypeName);
                // 获取输出流
                try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(fileNameBuilder.toString()))))) {
                    // 渲染图片
                    BufferedImage bufferedImage = renderer.renderImageWithDPI(i, this.dpi, this.getColorType());
                    // 写出图片
                    ImageIO.write(bufferedImage, imageTypeName, outputStream);
                }
            }
        }
        return this;
    }

    /**
     * 转为图片（根据页面索引）
     *
     * @param outputStream 输出流
     * @param imageType    图片类型
     * @param pageIndex    页面索引
     * @return 返回文档图像器
     */
    @SneakyThrows
    public DocumentImager image(OutputStream outputStream, ImageType imageType, int... pageIndex) {
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.document);
        // 设置二次采样
        renderer.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染目的
        renderer.setDefaultDestination(this.renderType.getDestination());
        // 如果合并图片，则合并后保存图片
        if (this.isMerge) {
            // 定义图片列表
            List<BufferedImage> imageList = new ArrayList<>(pageIndex.length);
            // 遍历页面索引
            for (int index : pageIndex) {
                // 添加图片列表
                imageList.add(
                        renderer.renderImageWithDPI(
                                Math.min(Math.max(index, 0), this.document.getNumberOfPages() - 1),
                                this.dpi,
                                this.getColorType()
                        )
                );
            }
            // 拼接图片
            BufferedImage bufferedImage = ImageUtil.join(imageList, this.isHorizontalMerge);
            // 写出图片
            ImageIO.write(bufferedImage, imageType.name().toLowerCase(), outputStream);
        }
        // 否则单页保存图片
        else {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 渲染图片
                BufferedImage bufferedImage = renderer.renderImageWithDPI(
                        Math.min(Math.max(index, 0), this.document.getNumberOfPages() - 1),
                        this.dpi,
                        this.getColorType()
                );
                // 写出图片
                ImageIO.write(bufferedImage, imageType.name().toLowerCase(), outputStream);
            }
        }
        return this;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }

    /**
     * 获取颜色类型
     *
     * @return 返回颜色类型
     */
    private ImageType getColorType() {
        // 如果透明，则返回ARGB类型
        if (this.isAlpha) {
            // 返回ARGB类型
            return ImageType.ARGB;
        }
        // 如果灰度，则返回GRAY类型
        if (this.isGray) {
            // 返回GRAY类型
            return ImageType.GRAY;
        }
        // 返回RGB类型
        return ImageType.RGB;
    }
}
