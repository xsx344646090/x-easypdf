package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.enums.RenderType;
import org.dromara.pdf.pdfbox.util.FileUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.dromara.pdf.pdfbox.util.RenderingHintUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.rendering.PDFRenderer;
import org.dromara.pdf.shade.org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.*;
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
 * 渲染处理器
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
@EqualsAndHashCode(callSuper = true)
public class RenderProcessor extends AbstractProcessor {

    /**
     * 图像DPI
     */
    protected Float dpi;
    /**
     * 渲染类型
     */
    protected RenderType renderType;
    /**
     * 渲染提示
     */
    protected RenderingHints renderingHints;
    /**
     * 是否内存优化（可能降低图像质量）
     */
    protected Boolean isMemoryOptimization;
    /**
     * 是否黑白
     */
    protected Boolean isBinary;
    /**
     * 是否灰度
     */
    protected Boolean isGray;
    /**
     * 是否透明
     */
    protected Boolean isAlpha;
    /**
     * 合并类型
     */
    protected MergeType mergeType;

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public RenderProcessor(Document document) {
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
     * 设置渲染提示
     *
     * @param renderingHints 渲染提示
     */
    public void setRenderingHints(RenderingHints renderingHints) {
        Objects.requireNonNull(renderingHints, "the render hints can not be null");
        this.renderingHints = renderingHints;
    }

    /**
     * 开启内存优化（可能降低图像质量）
     */
    public void enableOptimization() {
        this.isMemoryOptimization = Boolean.TRUE;
    }

    /**
     * 开启黑白
     */
    public void enableBinary() {
        this.isBinary = Boolean.TRUE;
        this.isGray = Boolean.FALSE;
        this.isAlpha = Boolean.FALSE;
    }

    /**
     * 开启灰度
     */
    public void enableGray() {
        this.isGray = Boolean.TRUE;
        this.isBinary = Boolean.FALSE;
        this.isAlpha = Boolean.FALSE;
    }

    /**
     * 开启透明
     */
    public void enableAlpha() {
        this.isAlpha = Boolean.TRUE;
        this.isBinary = Boolean.FALSE;
        this.isGray = Boolean.FALSE;
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
        this.image(outputPath, imageType, (String) null);
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
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 初始化
        this.init();
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (Objects.isNull(prefix)) {
            // 初始化文档名称前缀
            prefix = "x-easypdf";
        }
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.getDocument());
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
     * @param imageType 图像类型
     * @param pageIndex 页面索引
     */
    @SneakyThrows
    public BufferedImage image(ImageType imageType, int pageIndex) {
        // 检查参数
        Objects.requireNonNull(imageType, "the image type can not be null");
        // 初始化
        this.init();
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.getDocument());
        // 设置二次采样
        renderer.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染目的
        renderer.setDefaultDestination(this.renderType.getDestination());
        // 返回图片
        return renderer.renderImageWithDPI(
                Math.min(Math.max(pageIndex, 0), this.getDocument().getNumberOfPages() - 1),
                this.dpi,
                this.getColorType()
        );
    }

    /**
     * 转为图片（根据页面索引）
     *
     * @param outputPath  输出路径
     * @param imageType   图像类型
     * @param pageIndexes 页面索引
     */
    @SneakyThrows
    public void image(String outputPath, ImageType imageType, int... pageIndexes) {
        try (OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath)))){
            this.image(outputStream, imageType, pageIndexes);
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
        Objects.requireNonNull(imageType, "the image type can not be null");
        Objects.requireNonNull(pageIndexes, "the page indexes can not be null");
        // 初始化
        this.init();
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(this.getDocument());
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
                                Math.min(Math.max(index, 0), this.getDocument().getNumberOfPages() - 1),
                                this.dpi,
                                this.getColorType()
                        )
                );
            }
            // 拼接图片
            BufferedImage bufferedImage = this.mergeType == MergeType.HORIZONTAL ? ImageUtil.joinForHorizontal(imageList) : ImageUtil.joinForVertical(imageList);
            // 写出图片
            ImageIOUtil.writeImage(bufferedImage, imageType.name().toLowerCase(), outputStream, this.dpi.intValue());
        } else {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 渲染图片
                BufferedImage bufferedImage = renderer.renderImageWithDPI(
                        Math.min(Math.max(index, 0), this.getDocument().getNumberOfPages() - 1),
                        this.dpi,
                        this.getColorType()
                );
                // 写出图片
                ImageIOUtil.writeImage(bufferedImage, imageType.name(), outputStream, this.dpi.intValue());
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
        // 初始化渲染提示
        if (Objects.isNull(this.renderingHints)) {
            this.renderingHints = RenderingHintUtil.createDefaultRenderingHints();
        }
        // 初始化是否内存优化（可能降低图像质量）
        if (Objects.isNull(this.isMemoryOptimization)) {
            this.isMemoryOptimization = Boolean.FALSE;
        }
        // 初始化是否黑白
        if (Objects.isNull(this.isBinary)) {
            this.isBinary = Boolean.FALSE;
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
        int pageCount = this.getDocument().getNumberOfPages();
        // 定义图片列表
        List<BufferedImage> imageList = new ArrayList<>(pageCount);
        // 遍历页面索引
        for (int i = 0; i < pageCount; i++) {
            // 添加图片列表
            imageList.add(renderer.renderImageWithDPI(i, this.dpi, this.getColorType()));
        }
        // 拼接图片
        BufferedImage bufferedImage = this.mergeType == MergeType.HORIZONTAL ? ImageUtil.joinForHorizontal(imageList) : ImageUtil.joinForVertical(imageList);
        // 获取输出流
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath + File.separator + prefix + '.' + imageTypeName))))) {
            // 写出图片
            ImageIOUtil.writeImage(bufferedImage, imageTypeName, outputStream, this.dpi.intValue());
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
        // 定义图片格式名称
        String imageTypeName = imageType.getType();
        // 定义文件名称构造器
        StringBuilder fileNameBuilder;
        // 任务文档页面总数
        int pageCount = this.getDocument().getNumberOfPages();
        // 遍历页面索引
        for (int index = 0; index < pageCount; index++) {
            // 重置名称构造器
            fileNameBuilder = new StringBuilder();
            // 构建文件名称
            fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(index + 1).append('.').append(imageTypeName);
            // 获取输出流
            try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(fileNameBuilder.toString()))))) {
                // 渲染图片
                BufferedImage bufferedImage = renderer.renderImageWithDPI(index, this.dpi, this.getColorType());
                // 写出图片
                ImageIOUtil.writeImage(bufferedImage, imageTypeName, outputStream, this.dpi.intValue());
            }
        }
    }

    /**
     * 获取颜色类型
     *
     * @return 返回颜色类型
     */
    protected org.dromara.pdf.shade.org.apache.pdfbox.rendering.ImageType getColorType() {
        // 如果黑白，则返回BINARY类型
        if (this.isBinary) {
            // 返回BINARY类型
            return org.dromara.pdf.shade.org.apache.pdfbox.rendering.ImageType.BINARY;
        }
        // 如果灰度，则返回GRAY类型
        if (this.isGray) {
            // 返回GRAY类型
            return org.dromara.pdf.shade.org.apache.pdfbox.rendering.ImageType.GRAY;
        }
        // 如果透明，则返回ARGB类型
        if (this.isAlpha) {
            // 返回ARGB类型
            return org.dromara.pdf.shade.org.apache.pdfbox.rendering.ImageType.ARGB;
        }
        // 返回RGB类型
        return org.dromara.pdf.shade.org.apache.pdfbox.rendering.ImageType.RGB;
    }

    /**
     * 合并类型
     */
    protected enum MergeType {
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
