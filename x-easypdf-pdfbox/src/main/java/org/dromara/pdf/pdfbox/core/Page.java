package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.awt.*;
import java.io.Closeable;
import java.util.Objects;
import java.util.UUID;

/**
 * 页面
 *
 * @author xsx
 * @date 2023/6/5
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
public class Page extends BaseFont implements Closeable {

    /**
     * id
     */
    private String id;
    /**
     * 任务页面
     */
    private PDPage target;
    /**
     * 页面尺寸
     */
    private PageRectangle rectangle;
    /**
     * 页面索引（当前页码）
     */
    private Integer index;
    /**
     * 父页面
     */
    private Page parentPage;
    /**
     * 子页面
     */
    private Page subPage;

    /**
     * 有参构造
     *
     * @param context   上下文
     * @param rectangle 尺寸
     */
    Page(Context context, PageRectangle rectangle) {
        this(context, new PDPage(rectangle.getSize()));
    }

    /**
     * 有参构造
     *
     * @param context 上下文
     * @param page    pdfbox页面
     */
    Page(Context context, PDPage page) {
        this.init(context, page);
        if (this.getBackgroundColor() != Color.WHITE) {
            this.initBackgroundColor();
        }
        context.reset(this);
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
     * 设置上边距
     *
     * @param margin 边距
     */
    @Override
    public void setMarginTop(float margin) {
        // 获取页面高度
        Float height = this.getContext().getPage().getHeight();
        // 获取上边距
        Float marginTop = this.getMarginTop();
        // 设置上边距
        super.setMarginTop(margin);
        // 获取游标
        Cursor cursor = this.getContext().getCursor();
        // 重置游标Y轴坐标
        if (Objects.equals(cursor.getY(), height - marginTop)) {
            cursor.setY(height - margin);
        }
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    @Override
    public void setMarginLeft(float margin) {
        // 获取左边距
        Float marginLeft = this.getMarginLeft();
        // 重置左边距
        super.setMarginLeft(margin);
        // 获取游标
        Cursor cursor = this.getContext().getCursor();
        // 重置游标X轴坐标
        if (Objects.equals(cursor.getX(), marginLeft)) {
            cursor.setX(margin);
        }
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        super.setFontName(fontName);
        super.setFont(PdfHandler.getFontHandler().getPDFont(this.getContext().getTargetDocument(), fontName, true));
    }

    /**
     * 获取页面宽度
     *
     * @return 返回页面宽度
     */
    public Float getWidth() {
        return this.rectangle.getWidth();
    }

    /**
     * 获取页面高度
     *
     * @return 返回页面高度
     */
    public Float getHeight() {
        return this.rectangle.getHeight();
    }

    /**
     * 获取排除页面边距的页面宽度
     *
     * @return 返回页面宽度
     */
    public Float getWithoutMarginWidth() {
        return this.getWidth() - this.getMarginLeft() - this.getMarginRight();
    }

    /**
     * 获取排除页面边距的页面高度
     *
     * @return 返回页面高度
     */
    public Float getWithoutMarginHeight() {
        return this.getHeight() - this.getMarginTop() - this.getMarginBottom();
    }

    /**
     * 获取第一个父页面
     *
     * @return 返回父页面
     */
    public Page getFirstParentPage() {
        // 获取父页面
        Page parent = this.getParentPage();
        // 父页面为空
        if (Objects.isNull(parent)) {
            // 返回空
            return null;
        }
        // 循环获取
        while (!Objects.isNull(parent.getParentPage())) {
            parent = parent.getParentPage();
        }
        // 返回父页面
        return parent;
    }

    /**
     * 获取最后一个子页面
     *
     * @return 返回子页面
     */
    public Page getLastSubPage() {
        // 获取子页面
        Page subPage = this.getSubPage();
        // 子页面为空
        if (Objects.isNull(subPage)) {
            // 返回空
            return null;
        }
        // 循环获取
        while (!Objects.isNull(subPage.getSubPage())) {
            subPage = subPage.getSubPage();
        }
        // 返回子页面
        return subPage;
    }

    /**
     * 再创建
     */
    public void recreate() {
        // 获取子页面
        Page subPage = new Page(this.getContext(), this.getRectangle());
        // 初始化
        subPage.init(this, true);
        // 设置父页面
        subPage.setParentPage(this);
        // 设置子页面
        this.setSubPage(subPage);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 重置上下文
        this.setContext(null);
        // 重置任务页面
        this.setTarget(null);
        // 重置父页面
        this.setParentPage(null);
        // 重置子页面
        this.setSubPage(null);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param target  任务页面
     */
    private void init(Context context, PDPage target) {
        // 设置上下文
        this.setContext(context);
        // 初始化
        super.init(context.getDocument(), true);
        // 初始化id
        this.id = UUID.randomUUID().toString();
        // 初始化任务页面
        this.target = target;
        // 初始化页面尺寸
        this.rectangle = new PageRectangle(target.getCropBox());
    }

    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    private void initBackgroundColor() {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true,
                this.getIsResetContentStream()
        );
        // 绘制矩形（背景矩形）
        contentStream.addRect(
                0,
                0,
                this.getWidth(),
                this.getHeight()
        );
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
    }
}
