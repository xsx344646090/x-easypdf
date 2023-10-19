package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.ComponentType;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

/**
 * 图像组件
 *
 * @author xsx
 * @date 2023/6/30
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
public class Image extends AbstractComponent {

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
     * 是否svg格式
     */
    private Boolean isSvg;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Image(Page page) {
        super(page);
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
        return ComponentType.IMAGE;
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
        // 校验图片
        Objects.requireNonNull(this.getImage(), "the image can not be null");
        // 初始化
        super.init();
        // 初始化宽度与高度
        this.initWidthAndHeight();
        // 初始化首行
        if (!this.getIsCustomY() && this.getContext().isFirstLine()) {
            this.setBeginY(this.getBeginY() - this.getHeight());
        }
        // 检查换行
        if (this.isWrap()) {
            this.wrap();
        }
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
        }
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 添加图片
        contentStream.drawImage(
                this.getImage(),
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() + this.getRelativeBeginY(),
                this.getWidth(),
                this.getHeight()
        );
        // 添加边框
        BorderUtil.drawNormalBorder(contentStream, this.getRectangle(), this);
        // 关闭内容流
        contentStream.close();
        // 重置游标
        this.getContext().getCursor().reset(
                this.getBeginX() + this.getWidth() + this.getMarginRight(),
                this.getBeginY() - this.getHeight() - this.getMarginBottom()
        );
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化宽度与高度
     */
    private void initWidthAndHeight() {
        // 初始化宽度
        if (Objects.isNull(this.width)) {
            // 初始化宽度
            this.width = this.image.getWidth();
        }
        // 获取最大宽度
        int maxWidth = (int) (this.getContext().getWrapWidth() - this.getBeginX() - this.getMarginRight());
        // 图片宽度大于最大宽度，则重置
        if (this.width > maxWidth) {
            // 重置图片宽度
            this.width = maxWidth;
        }
        // 初始化高度
        if (Objects.isNull(this.height)) {
            // 初始化高度
            this.height = this.image.getHeight();
        }
        // 获取最大高度
        int maxHeight = (int) (this.getBeginY() - this.getMarginBottom() - this.getContext().getPage().getMarginBottom());
        // 图片宽度大于最大高度，则重置
        if (this.height > maxHeight) {
            // 重置图片高度
            this.height = maxHeight;
        }
    }

    /**
     * 获取行尺寸
     *
     * @return 返回尺寸
     */
    private PDRectangle getRectangle() {
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 设置起始X轴坐标
        rectangle.setLowerLeftX(this.getBeginX() + this.getRelativeBeginX());
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(this.getBeginY() + this.getRelativeBeginY());
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(rectangle.getLowerLeftY() + this.getHeight());
        // 设置结束X轴坐标
        rectangle.setUpperRightX(rectangle.getLowerLeftX() + this.getWidth());
        // 返回尺寸
        return rectangle;
    }
}
