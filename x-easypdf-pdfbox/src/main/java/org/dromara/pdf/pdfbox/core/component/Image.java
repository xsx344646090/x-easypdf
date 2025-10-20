package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 图像组件
 *
 * @author xsx
 * @date 2023/6/30
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
public class Image extends AbstractComponent {

    /**
     * pdfbox图像对象
     */
    protected PDImageXObject image;
    /**
     * 宽度
     */
    protected Integer width;
    /**
     * 高度
     */
    protected Integer height;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 透明度
     */
    protected Float alpha;
    /**
     * 缩放比例
     */
    protected Float scale;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Image(Page page) {
        super(page);
    }

    /**
     * 设置宽度
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
     * 设置高度
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
        this.setImage(Files.readAllBytes(file.toPath()));
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
    }

    /**
     * 设置图片
     *
     * @param image 图片
     */
    @SneakyThrows
    public void setImage(BufferedImage image) {
        Objects.requireNonNull(image, "the image can not be null");
        this.setImage(ImageUtil.toBytes(image, ImageType.PNG.getType()));
    }

    /**
     * 设置图片
     *
     * @param bytes 字节数组
     */
    @SneakyThrows
    public void setImage(byte[] bytes) {
        Objects.requireNonNull(bytes, "the image bytes can not be null");
        this.image = CommonUtil.createImage(this.getContext(), bytes);
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
    protected void init() {
        // 校验图片
        Objects.requireNonNull(this.getImage(), "the image can not be null");
        // 初始化
        super.init();
        // 初始化宽度与高度
        this.initWidthAndHeight();
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化透明度
        if (Objects.isNull(this.alpha)) {
            this.alpha = 1.0F;
        }
        // 初始化起始XY轴坐标
        this.initBeginXY(this.width, this.height);
    }

    /**
     * 初始化宽度与高度
     */
    protected void initWidthAndHeight() {
        // 初始化缩放比例
        if (Objects.isNull(this.scale)) {
            // 初始化缩放比例
            this.scale = 1F;
        }
        // 初始化宽度
        if (Objects.isNull(this.width)) {
            // 初始化宽度
            this.width = (int) (this.image.getWidth() * this.scale);
        }
        // 初始化高度
        if (Objects.isNull(this.height)) {
            // 初始化高度
            this.height = (int) (this.image.getHeight() * this.scale);
        }
        // 重置宽度
        this.width = (int) Math.min(this.width, this.getPage().getWithoutMarginWidth());
    }


    /**
     * 初始化起始Y轴坐标
     *
     * @param height 高度
     */
    @Override
    protected void initBeginY(float height) {
        // 跳过
        if (height >= this.getPage().getWithoutMarginHeight()) {
            // 设置起始Y轴坐标
            this.setBeginY(this.getBottom() + this.getContext().getMinBeginY(), this.getIsCustomY());
            return;
        }
        super.initBeginY(height);
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getWidth();
    }

    /**
     * 写入内容
     */
    @SneakyThrows
    @Override
    protected void writeContents() {
        if (!this.getContext().getIsVirtualRender()) {
            // 新建内容流
            PDPageContentStream contentStream = new PDPageContentStream(
                    this.getContext().getTargetDocument(),
                    this.getContext().getTargetPage(),
                    this.getContentMode().getMode(),
                    true,
                    this.getIsResetContentStream()
            );
            // 初始化矩阵
            CommonUtil.initMatrix(contentStream, this.getBeginX(), this.getBeginY(), this.getRelativeBeginX(), this.getRelativeBeginY(), this.getWidth(), this.getHeight(), this.getAngle(), this.getAlpha());
            // 添加图像
            contentStream.drawImage(this.getImage(), 0, 0, this.getWidth(), this.getHeight());
            // 添加边框
            BorderUtil.drawNormalBorder(contentStream, CommonUtil.getRectangle(this.getWidth(), this.getHeight()), BorderData.create(this, this.getBorderConfiguration()), this.getPage().getBackgroundColor());
            // 关闭内容流
            contentStream.close();
        }
    }

    /**
     * 重置
     */
    @Override
    protected void reset() {
        // 获取X轴坐标
        float x = this.getBeginX() + this.getWidth() + this.getMarginRight();
        // 获取Y轴坐标
        float y = this.getBeginY();
        // 重置
        super.reset(this.getType(), x, y);
    }
}
