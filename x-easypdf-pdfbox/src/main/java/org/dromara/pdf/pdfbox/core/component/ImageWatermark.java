package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.dromara.pdf.pdfbox.core.base.AbstractBase;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 图像水印
 *
 * @author xsx
 * @date 2023/10/14
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
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageWatermark extends AbstractBase implements Watermark {

    /**
     * 自定义起始X轴坐标
     */
    protected Float beginX;
    /**
     * 自定义起始Y轴坐标
     */
    protected Float beginY;
    /**
     * pdfbox图像对象
     */
    protected PDImageXObject image;
    /**
     * 宽度（显示）
     */
    protected Integer width;
    /**
     * 高度（显示）
     */
    protected Integer height;
    /**
     * 图像行数
     */
    protected Integer lines;
    /**
     * 每行图像数
     */
    protected Integer countOfLine;
    /**
     * 行间距
     */
    protected Float leading;
    /**
     * 每行图像间距
     */
    protected Float spacingOfLine;
    /**
     * 透明度
     */
    protected Float alpha;

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
        this.setImage(IOUtils.toByteArray(inputStream));
        IOUtils.closeQuietly(inputStream);
    }

    /**
     * 设置图片
     *
     * @param image 图片
     */
    @SneakyThrows
    public void setImage(BufferedImage image) {
        Objects.requireNonNull(image, "the image can not be null");
        this.setImage(ImageUtil.toBytes(image, ImageType.JPEG.getType()));
    }

    /**
     * 设置图片
     *
     * @param bytes 字节数组
     */
    @SneakyThrows
    public void setImage(byte[] bytes) {
        Objects.requireNonNull(bytes, "the image bytes can not be null");
        this.image = this.getContext().getImageCache().computeIfAbsent(CommonUtil.getImageDigest(bytes), key -> CommonUtil.createImage(this.getContext().getTargetDocument(), bytes));
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
     * 初始化
     *
     * @param page 页面
     */
    @SneakyThrows
    protected void init(Page page) {
        // 检查图像
        Objects.requireNonNull(this.image, "the image can not be null");
        // 初始化参数
        super.init(page);
        // 初始化当前执行组件类型
        if (Objects.isNull(this.getContext().getExecutingComponentType())) {
            this.getContext().setExecutingComponentType(this.getType());
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
        // 初始化透明度
        if (Objects.isNull(this.alpha)) {
            this.alpha = 1.0F;
        }
        // 初始化自定义起始X轴坐标
        if (Objects.isNull(this.beginX)) {
            this.beginX = -this.width / 2F;
        }
        // 初始化自定义起始Y轴坐标
        if (Objects.isNull(this.beginY)) {
            this.beginY = page.getHeight() - this.height / 2;
        }

    }

    /**
     * 渲染图像
     *
     * @param page 页面
     */
    @SneakyThrows
    protected void renderImage(Page page) {
        // 获取X轴起始坐标
        float beginX = this.getBeginX();
        // 获取Y轴起始坐标
        float beginY = this.getBeginY();
        // 循环写入图像
        for (int i = 0; i < this.getLines(); i++) {
            // 循环写入图像
            for (int j = 0; j < this.getCountOfLine(); j++) {
                // 写入图像
                this.writeImage(page, beginX, beginY);
                // 重置X轴起始坐标
                beginX = beginX + this.getWidth() + this.getSpacingOfLine();
            }
            // 重置X轴起始坐标
            beginX = this.getBeginX();
            // 重置Y轴起始坐标
            beginY = beginY - this.getHeight() - this.getLeading();
        }
    }

    /**
     * 写入图像
     *
     * @param page   页面
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    protected void writeImage(Page page, float beginX, float beginY) {
        // 初始化内容流
        PDPageContentStream stream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                page.getTarget(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 创建扩展图形状态
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置透明度
        state.setNonStrokingAlphaConstant(this.getAlpha());
        // 设置图形状态参数
        stream.setGraphicsStateParameters(state);
        // 添加图片
        stream.drawImage(this.getImage(), beginX, beginY, this.getWidth(), this.getHeight());
        // 关闭流
        stream.close();
    }
}
