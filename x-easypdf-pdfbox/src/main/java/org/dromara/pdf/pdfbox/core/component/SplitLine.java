package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.LineCapStyle;

import java.awt.*;
import java.util.Objects;

/**
 * 分割线组件
 *
 * @author xsx
 * @date 2023/11/6
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
public class SplitLine extends AbstractComponent {

    /**
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
    /**
     * 颜色
     */
    protected Color lineColor;
    /**
     * 线帽样式
     */
    protected LineCapStyle lineCapStyle;
    /**
     * 线长
     */
    protected Float lineLength;
    /**
     * 线宽
     */
    protected Float lineWidth;
    /**
     * 点线长度
     */
    protected Float dottedLength;
    /**
     * 点线间隔
     */
    protected Float dottedSpacing;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public SplitLine(Page page) {
        super(page);
        this.fontConfiguration = new FontConfiguration(page.getFontConfiguration());
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        this.fontConfiguration.setFontName(fontName);
        this.getContext().addFontCache(fontName);
    }


    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.SPLIT_LINE;
    }

    /**
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        // 初始化
        this.init();
        // 重置光标
        this.getContext().getCursor().setX(this.getBeginX() + this.getLineLength() + this.getMarginRight());
        // 重置
        super.reset(this.getType());
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 新建内容流
        PDPageContentStream stream = this.initContentStream();
        // 定义X轴起始坐标
        float beginX = this.getBeginX() + this.getRelativeBeginX();
        // 定义Y轴起始坐标
        float beginY = this.getBeginY() - this.getRelativeBeginY();
        // 实线
        if (this.getDottedSpacing() == 0F) {
            // 渲染实线
            this.renderSolid(stream, beginX, beginY);
        } else {
            // 渲染虚线
            this.renderDotted(stream, beginX, beginY);
        }
        // 结束
        stream.stroke();
        // 关闭内容流
        stream.close();
        // 重置光标
        this.getContext().getCursor().setX(this.getBeginX() + this.getLineLength() + this.getMarginRight());
        // 重置
        super.reset(this.getType());
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化颜色
        if (Objects.isNull(this.getLineColor())) {
            this.lineColor = Color.BLACK;
        }
        // 初始化线帽样式
        if (Objects.isNull(this.getLineCapStyle())) {
            this.lineCapStyle = LineCapStyle.NORMAL;
        }
        // 初始化线长
        if (Objects.isNull(this.getLineLength())) {
            this.lineLength = this.getContext().getPage().getWithoutMarginWidth() - this.getMarginLeft() - this.getMarginRight();
        }
        // 初始化线宽
        if (Objects.isNull(this.getLineWidth())) {
            this.lineWidth = 1F;
        }
        // 初始化点线长度
        if (Objects.isNull(this.getDottedLength())) {
            this.dottedLength = 1F;
        }
        // 初始化点线间隔
        if (Objects.isNull(this.getDottedSpacing())) {
            this.dottedSpacing = 0F;
        }
        // 初始化容器换行或首行
        if (this.getContext().getIsFirstComponent()) {
            super.setBeginY(this.getBeginY() - this.getLineWidth());
        }
        // 初始化起始X轴坐标
        this.initBeginX(this.getLineLength());
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getLineLength();
    }

    /**
     * 初始化内容流
     *
     * @return 返回内容流
     */
    @SneakyThrows
    protected PDPageContentStream initContentStream() {
        // 创建内容流
        PDPageContentStream stream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getContext().getTargetPage(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 设置字体
        stream.setFont(this.getContext().getFont(this.getFontConfiguration().getFontName()), this.getFontConfiguration().getFontSize());
        // 设置线宽
        stream.setLineWidth(this.getLineWidth());
        // 设置颜色
        stream.setStrokingColor(this.getLineColor());
        // 设置线帽样式
        stream.setLineCapStyle(this.getLineCapStyle().getType());
        // 返回内容流
        return stream;
    }

    /**
     * 渲染实线
     *
     * @param stream 内容流
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    protected void renderSolid(PDPageContentStream stream, float beginX, float beginY) {
        // 移动
        stream.moveTo(beginX, beginY);
        // 连线
        stream.lineTo(beginX + this.getLineLength(), beginY);
    }

    /**
     * 渲染虚线
     *
     * @param stream 内容流
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     */
    @SneakyThrows
    protected void renderDotted(PDPageContentStream stream, float beginX, float beginY) {
        // 定义X轴结束坐标
        float endX = beginX + this.getLineLength();
        // 移动
        stream.moveTo(beginX, beginY);
        // 定义标记
        boolean flag = true;
        // 循环
        while (flag) {
            // 重置X轴起始坐标
            beginX = beginX + this.getDottedLength();
            // 连线
            stream.lineTo(beginX, beginY);
            // 重置X轴起始坐标
            beginX = beginX + getDottedSpacing();
            // 结束
            if (beginX >= endX) {
                // 重置标记
                flag = false;
            } else {
                // 移动
                stream.moveTo(beginX, beginY);
            }
        }
    }
}
