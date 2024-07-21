package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.BorderData;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.*;
import java.util.Objects;

/**
 * 矩形组件
 *
 * @author xsx
 * @date 2023/11/24
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
public class Rectangle extends AbstractComponent {

    /**
     * 宽度
     */
    protected Float width;
    /**
     * 高度
     */
    protected Float height;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Rectangle(Page page) {
        super(page);
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     */
    public void setWidth(float width) {
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
    public void setHeight(float height) {
        if (height < 1) {
            throw new IllegalArgumentException("the height can not be less than 1");
        }
        this.height = height;
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.RECTANGLE;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 校验参数
        Objects.requireNonNull(this.width, "the width can not be null");
        Objects.requireNonNull(this.height, "the height can not be null");
        // 初始化
        super.init();
        // 设置边框
        super.setIsBorder(true);
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化起始XY轴坐标
        this.initBeginXY(this.width, this.height);
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
            CommonUtil.initMatrix(contentStream, this.getBeginX(), this.getBeginY(), this.getRelativeBeginX(), this.getRelativeBeginY(), this.getWidth(), this.getHeight(), this.getAngle());
            // 添加边框
            BorderUtil.drawNormalBorder(contentStream, CommonUtil.getRectangle(this.getWidth(), this.getHeight()), BorderData.create(this, this.getBorderConfiguration()));
            // 添加背景
            if (Objects.nonNull(this.getBackgroundColor())) {
                // 添加矩形（背景矩形）
                contentStream.addRect(this.getBorderConfiguration().getBorderLineWidth() / 2, this.getBorderConfiguration().getBorderLineWidth() / 2, this.getWidth() - this.getBorderConfiguration().getBorderLineWidth(), this.getHeight() - this.getBorderConfiguration().getBorderLineWidth());
                // 设置矩形颜色（背景颜色）
                contentStream.setNonStrokingColor(this.getBackgroundColor());
                // 填充矩形（背景矩形）
                contentStream.fill();
            }
            // 恢复图形状态
            contentStream.restoreGraphicsState();
            // 关闭内容流
            contentStream.close();
        }
    }

    /**
     * 重置
     */
    protected void reset() {
        // 获取X轴坐标
        float x = this.getBeginX() + this.getWidth() + this.getMarginRight();
        // 获取Y轴坐标
        float y = this.getBeginY();
        // 重置
        super.reset(this.getType(), x, y);
    }
}
