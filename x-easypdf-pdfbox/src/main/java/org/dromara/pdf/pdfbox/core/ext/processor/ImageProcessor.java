package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.enums.RenderType;
import org.dromara.pdf.pdfbox.util.FileUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 图像处理器
 *
 * @author xsx
 * @date 2023/10/20
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
public class ImageProcessor extends AbstractProcessor {

    /**
     * 图像DPI
     */
    private Float dpi;
    /**
     * 渲染类型
     */
    private RenderType renderType;
    /**
     * 是否内存优化（可能降低图像质量）
     */
    private Boolean isMemoryOptimization;
    /**
     * 是否灰度
     */
    private Boolean isGray;
    /**
     * 是否透明
     */
    private Boolean isAlpha;
    /**
     * 合并类型
     */
    private MergeType mergeType;

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public ImageProcessor(Document document) {
        super(document);
    }

    /**
     * 设置图像DPI
     *
     * @param dpi 图像DPI
     */
    public void setDpi(float dpi) {
        this.dpi = Math.abs(dpi);
    }

    /**
     * 设置渲染类型
     *
     * @param renderType 渲染类型
     */
    public void setRenderType(RenderType renderType) {
        Objects.requireNonNull(renderType, "the render type can not be null");
        this.renderType = renderType;
    }

    /**
     * 开启内存优化（可能降低图像质量）
     */
    public void enableOptimization() {
        this.isMemoryOptimization = Boolean.TRUE;
    }

    /**
     * 开启灰度
     */
    public void enableGray() {
        this.isGray = Boolean.TRUE;
    }

    /**
     * 开启透明
     */
    public void enableAlpha() {
        this.isAlpha = Boolean.TRUE;
    }

    /**
     * 开启水平合并
     */
    public void enableHorizontalMerge() {
        this.mergeType = MergeType.HORIZONTAL;
    }

    /**
     * 开启垂直合并
     */
    public void enableVerticalMerge() {
        this.mergeType = MergeType.VERTICAL;
    }

    /**
     * 转为图片（整个文档）
     *
     * @param outputPath 输出路径（目录）
     * @param imageType  图像类型
     */

    @SneakyThrows
    public void image(String outputPath, ImageType imageType) {
        this.image(outputPath, imageType, null);
    }

    /**
     * 转为图片（整个文档）
     *
     * @param outputPath 输出路径（目录）
     * @param imageType  图像类型
     * @param prefix     图片名称前缀
     */
    @SneakyThrows
    public void image(String outputPath, ImageType imageType, String prefix) {
        // 检查参数
        Objects.requireNonNull(outputPath, "the output path can not be null");
        // 检查参数
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 初始化
        this.init();
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (Objects.isNull(prefix)) {
            // 初始化文档名称前缀
            prefix = "x-easypdf";
        }
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.document.getTarget());
        // 设置二次采样
        renderer.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染目的
        renderer.setDefaultDestination(this.renderType.getDestination());
        // 合并图片
        if (Objects.nonNull(this.mergeType)) {
            // 合并保存图片
            this.writeImageByMerge(renderer, imageType, outputPath, prefix);
        } else {
            // 单页保存图片
            this.writeImageBySingle(renderer, imageType, outputPath, prefix);
        }
    }

    /**
     * 转为图片（根据页面索引）
     *
     * @param outputStream 输出流
     * @param imageType    图像类型
     * @param pageIndexes  页面索引
     */
    @SneakyThrows
    public void image(OutputStream outputStream, ImageType imageType, int... pageIndexes) {
        // 检查参数
        Objects.requireNonNull(outputStream, "the output stream can not be null");
        // 检查参数
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 检查参数
        Objects.requireNonNull(pageIndexes, "the page indexes can not be null");
        // 初始化
        this.init();
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.document.getTarget());
        // 设置二次采样
        renderer.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染目的
        renderer.setDefaultDestination(this.renderType.getDestination());
        // 合并图片
        if (Objects.nonNull(this.mergeType)) {
            // 定义图片列表
            List<BufferedImage> imageList = new ArrayList<>(pageIndexes.length);
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 添加图片列表
                imageList.add(
                        renderer.renderImageWithDPI(
                                Math.min(Math.max(index, 0), this.document.getTarget().getNumberOfPages() - 1),
                                this.dpi,
                                this.getColorType()
                        )
                );
            }
            // 拼接图片
            BufferedImage bufferedImage = ImageUtil.join(imageList, this.mergeType == MergeType.HORIZONTAL);
            // 写出图片
            ImageIO.write(bufferedImage, imageType.name().toLowerCase(), outputStream);
        }
        // 否则单页保存图片
        else {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 渲染图片
                BufferedImage bufferedImage = renderer.renderImageWithDPI(
                        Math.min(Math.max(index, 0), this.document.getTarget().getNumberOfPages() - 1),
                        this.dpi,
                        this.getColorType()
                );
                // 写出图片
                ImageIO.write(bufferedImage, imageType.name().toLowerCase(), outputStream);
            }
        }
    }

    /**
     * 初始化
     */
    protected void init() {
        // 初始化dpi
        if (Objects.isNull(this.dpi)) {
            this.dpi = 72F;
        }
        // 初始化渲染类型
        if (Objects.isNull(this.renderType)) {
            this.renderType = RenderType.EXPORT;
        }
        // 初始化是否内存优化（可能降低图像质量）
        if (Objects.isNull(this.isMemoryOptimization)) {
            this.isMemoryOptimization = Boolean.FALSE;
        }
        // 初始化是否灰度
        if (Objects.isNull(this.isGray)) {
            this.isGray = Boolean.FALSE;
        }
        // 初始化是否透明
        if (Objects.isNull(this.isAlpha)) {
            this.isAlpha = Boolean.FALSE;
        }
    }

    /**
     * 合并写入
     *
     * @param renderer   pdf渲染器
     * @param imageType  图像类型
     * @param outputPath 输出路径
     * @param prefix     前缀
     */
    @SneakyThrows
    protected void writeImageByMerge(PDFRenderer renderer, ImageType imageType, String outputPath, String prefix) {
        // 图片格式名称
        String imageTypeName = imageType.getType();
        // 任务文档页面总数
        int pageCount = this.document.getTarget().getNumberOfPages();
        // 定义图片列表
        List<BufferedImage> imageList = new ArrayList<>(pageCount);
        // 遍历页面索引
        for (int i = 0; i < pageCount; i++) {
            // 添加图片列表
            imageList.add(renderer.renderImageWithDPI(i, this.dpi, this.getColorType()));
        }
        // 拼接图片
        BufferedImage bufferedImage = ImageUtil.join(imageList, this.mergeType == MergeType.HORIZONTAL);
        // 获取输出流
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath + File.separator + prefix + '.' + imageTypeName))))) {
            // 写出图片
            ImageIO.write(bufferedImage, imageTypeName, outputStream);
        }
    }

    /**
     * 单个写入
     *
     * @param renderer   pdf渲染器
     * @param imageType  图像类型
     * @param outputPath 输出路径
     * @param prefix     前缀
     */
    @SneakyThrows
    protected void writeImageBySingle(PDFRenderer renderer, ImageType imageType, String outputPath, String prefix) {
        // 图片格式名称
        String imageTypeName = imageType.getType();
        // 任务文档页面总数
        int pageCount = this.document.getTarget().getNumberOfPages();
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

    /**
     * 获取颜色类型
     *
     * @return 返回颜色类型
     */
    protected org.apache.pdfbox.rendering.ImageType getColorType() {
        // 如果透明，则返回ARGB类型
        if (this.isAlpha) {
            // 返回ARGB类型
            return org.apache.pdfbox.rendering.ImageType.ARGB;
        }
        // 如果灰度，则返回GRAY类型
        if (this.isGray) {
            // 返回GRAY类型
            return org.apache.pdfbox.rendering.ImageType.GRAY;
        }
        // 返回RGB类型
        return org.apache.pdfbox.rendering.ImageType.RGB;
    }

    /**
     * 合并类型
     */
    private enum MergeType {
        /**
         * 水平
         */
        HORIZONTAL,
        /**
         * 垂直
         */
        VERTICAL;
    }
}
