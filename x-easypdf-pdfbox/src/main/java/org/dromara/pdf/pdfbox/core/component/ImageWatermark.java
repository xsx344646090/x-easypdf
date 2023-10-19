package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.AbstractBaseBorder;
import org.dromara.pdf.pdfbox.core.ComponentType;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

/**
 * 图像水印
 *
 * @author xsx
 * @date 2023/10/14
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
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageWatermark extends AbstractBaseBorder implements Watermark {

    /**
     * 自定义起始X轴坐标
     */
    private Float beginX;
    /**
     * 自定义起始Y轴坐标
     */
    private Float beginY;
    /**
     * pdfbox图像对象
     */
    private PDImageXObject image;
    /**
     * 宽度（显示）
     */
    private Integer width;
    /**
     * 高度（显示）
     */
    private Integer height;
    /**
     * 图像行数
     */
    private Integer lines;
    /**
     * 每行图像数
     */
    private Integer countOfLine;
    /**
     * 行间距
     */
    private Float leading;
    /**
     * 每行图像间距
     */
    private Float spacingOfLine;
    /**
     * 是否svg格式
     */
    private Boolean isSvg;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public ImageWatermark(Document document) {
        super.setContext(document.getContext());
    }

    /**
     * 设置宽度（显示）
     *
     * @param width 宽度
     */
    public void setWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("the width can not be less than 1");
        }
        this.width = width;
    }

    /**
     * 设置高度（显示）
     *
     * @param height 高度
     */
    public void setHeight(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("the height can not be less than 1");
        }
        this.height = height;
    }

    /**
     * 设置图片
     *
     * @param file 文件
     */
    @SneakyThrows
    public void setImage(File file) {
        Objects.requireNonNull(file, "the image file can not be null");
        this.setImage(Files.newInputStream(file.toPath()));
    }

    /**
     * 设置图片
     *
     * @param inputStream 输入流
     */
    @SneakyThrows
    public void setImage(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "the image input stream can not be null");
        try (
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
        ) {
            IOUtils.copy(bufferedInputStream, outputStream);
            this.setImage(outputStream.toByteArray());
        }
    }

    /**
     * 设置图片
     *
     * @param bytes 字节数组
     */
    @SneakyThrows
    public void setImage(byte[] bytes) {
        Objects.requireNonNull(bytes, "the image bytes can not be null");
        this.image = PDImageXObject.createFromByteArray(
                this.getContext().getTargetDocument(),
                ImageUtil.resetBytes(Optional.ofNullable(this.getIsSvg()).orElse(Boolean.FALSE), bytes),
                "unknown"
        );
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.WATERMARK;
    }

    /**
     * 初始化
     */
    @Override
    public void init() {

    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {

    }

    /**
     * 初始化
     *
     * @param page 页面
     */
    public void init(Page page) {
        // 检查图像
        Objects.requireNonNull(this.image, "the image can not be null");
        // 初始化参数
        super.init(page, false);
        // 初始化当前执行组件类型
        if (Objects.isNull(this.getContext().getExecutingComponentType())) {
            this.getContext().setExecutingComponentType(this.getType());
        }
        // 初始化自定义起始X轴坐标
        if (Objects.isNull(this.beginX)) {
            this.beginX = 0F;
        }
        // 初始化自定义起始Y轴坐标
        if (Objects.isNull(this.beginY)) {
            this.beginY = page.getHeight() - this.height;
        }
        // 初始化宽度
        if (Objects.isNull(this.width)) {
            this.width = this.image.getWidth();
        }
        // 初始化高度
        if (Objects.isNull(this.height)) {
            this.height = this.image.getHeight();
        }
        // 初始化行数
        if (Objects.isNull(this.lines)) {
            this.lines = 1;
        }
        // 初始化每行图像数
        if (Objects.isNull(this.countOfLine)) {
            this.countOfLine = 1;
        }
        // 初始化行间距
        if (Objects.isNull(this.leading)) {
            this.leading = Float.valueOf(this.height);
        }
        // 初始化每行图像间距
        if (Objects.isNull(this.spacingOfLine)) {
            this.spacingOfLine = Float.valueOf(this.width);
        }
        // 初始化是否svg格式
        if (Objects.isNull(this.isSvg)) {
            this.isSvg = Boolean.FALSE;
        }
    }

    /**
     * 渲染
     *
     * @param page 页面
     */
    @Override
    public void render(Page page) {
        // 校验参数
        Objects.requireNonNull(page, "the page can not be null");
        // 初始化
        this.init(page);
        // 渲染图像
        this.renderImage(page);
    }

    /**
     * 渲染
     *
     * @param document 文档
     */
    @Override
    public void render(Document document) {
        // 校验参数
        Objects.requireNonNull(document, "the document can not be null");
        // 渲染图像
        document.getPages().forEach(this::render);
    }

    /**
     * 写入文本
     *
     * @param page 页面
     */
    @SneakyThrows
    private void renderImage(Page page) {
        // 获取X轴起始坐标
        float beginX = this.getBeginX();
        // 获取Y轴起始坐标
        float beginY = this.getBeginY();
        // 初始化内容流
        PDPageContentStream stream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                page.getTarget(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 循环写入图像
        for (int i = 0; i < this.getLines(); i++) {
            // 循环写入图像
            for (int j = 0; j < this.getCountOfLine(); j++) {
                // 添加图片
                stream.drawImage(this.getImage(), beginX, beginY, this.getWidth(), this.getHeight());
                // 重置X轴起始坐标
                beginX = beginX + this.getWidth() + this.getSpacingOfLine();
            }
            // 重置X轴起始坐标
            beginX = this.getBeginX();
            // 重置Y轴起始坐标
            beginY = beginY - this.getHeight() - this.getLeading();
        }
        // 关闭流
        stream.close();
    }
}
