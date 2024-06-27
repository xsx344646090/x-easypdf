package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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
     * 有参构造
     *
     * @param component 组件
     */
    protected Image(AbstractComponent component) {
        super(component);
    }

    /**
     * 有参构造
     *
     * @param page 页面
     */
    protected Image(Page page, boolean isResetPage) {
        super(page, isResetPage);
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
                ImageUtil.resetBytes(bytes),
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
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        // 初始化
        this.init();
        // 重置
        this.reset();
    }

    /**
     * 渲染
     */
    @Override
    public void render() {
        // 初始化
        this.init();
        // 写入图像
        this.writeImage();
        // 重置
        this.reset();
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
        // 检查换行
        this.checkWrap(this.getHeight());
        // 检查分页
        if (this.checkPaging()) {
            this.setIsWrap(true);
            this.wrap(this.getHeight());
            this.initBeginYForPaging(this.height);
        } else {
            this.initBeginY(height);
        }
        // 初始化起始X轴坐标
        this.initBeginX(this.width);
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
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
     * 写入图像
     */
    @SneakyThrows
    protected void writeImage() {
        // 定义X轴偏移量
        float offsetX = 0.5F * this.getWidth();
        // 定义Y轴偏移量
        float offsetY = 0.5F * this.getHeight();
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 保存图形状态
        contentStream.saveGraphicsState();
        // 移动到中心点
        contentStream.transform(
                Matrix.getTranslateInstance(
                        this.getBeginX() + this.getRelativeBeginX() + offsetX,
                        this.getBeginY() - this.getRelativeBeginY() + offsetY
                )
        );
        // 旋转
        contentStream.transform(Matrix.getRotateInstance(Math.toRadians(this.getAngle()), 0, 0));
        // 移动到左下角
        contentStream.transform(Matrix.getTranslateInstance(-offsetX, -offsetY));
        // 添加图片
        contentStream.drawImage(this.getImage(), 0, 0, this.getWidth(), this.getHeight());
        // 添加边框
        BorderUtil.drawNormalBorder(contentStream, this.getRectangle(), this.getBorderData());
        // 关闭内容流
        contentStream.close();
    }

    /**
     * 获取行尺寸
     *
     * @return 返回尺寸
     */
    protected PDRectangle getRectangle() {
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 设置起始X轴坐标
        rectangle.setLowerLeftX(0F);
        // 设置结束X轴坐标
        rectangle.setUpperRightX(this.getWidth());
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(0F);
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(this.getHeight());
        // 返回尺寸
        return rectangle;
    }

    /**
     * 获取边框数据
     *
     * @return 返回边框数据
     */
    protected BorderData getBorderData() {
        return new BorderData(this, this.getBorderConfiguration());
    }

    /**
     * 重置
     */
    protected void reset() {
        // 重置光标X轴
        this.getContext().getCursor().setX(this.getBeginX() + this.getWidth() + this.getMarginRight());
        // 重置
        super.reset(this.getType());
    }
}
