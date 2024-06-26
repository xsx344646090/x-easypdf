package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.RotationAngle;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.support.Constants;

import java.awt.*;
import java.io.Closeable;
import java.util.List;
import java.util.*;

/**
 * 页面
 *
 * @author xsx
 * @date 2023/6/5
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
public class Page extends AbstractBase implements Closeable {

    /**
     * 边距配置
     */
    protected MarginConfiguration marginConfiguration;
    /**
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * id
     */
    protected String id;
    /**
     * 页面索引
     */
    protected Integer index;
    /**
     * 任务页面
     */
    protected PDPage target;
    /**
     * 页面尺寸
     */
    protected PageSize pageSize;
    /**
     * 父页面
     */
    protected Page parentPage;
    /**
     * 子页面
     */
    protected Page subPage;
    /**
     * 水平对齐方式
     */
    protected HorizontalAlignment horizontalAlignment;
    /**
     * 垂直对齐方式
     */
    protected VerticalAlignment verticalAlignment;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public Page(Document document) {
        this(document, new PDPage(PageSize.A4.getSize()));
    }

    /**
     * 有参构造
     *
     * @param document 文档
     * @param pageSize 页面尺寸
     */
    public Page(Document document, PageSize pageSize) {
        if (Objects.isNull(pageSize)) {
            pageSize = PageSize.A4;
        }
        this.init(new PDPage(pageSize.getSize()), document, document.getMarginConfiguration(), document.getFontConfiguration(), document.getBackgroundColor());
    }

    /**
     * 有参构造
     *
     * @param document 文档
     * @param target   任务页面
     */
    public Page(Document document, PDPage target) {
        this.init(target, document, document.getMarginConfiguration(), document.getFontConfiguration(), document.getBackgroundColor());
    }

    /**
     * 有参构造
     *
     * @param page 页面
     */
    protected Page(Page page) {
        this.init(new PDPage(page.getPageSize().getSize()), page, page.getMarginConfiguration(), page.getFontConfiguration(), page.getBackgroundColor());
        this.parentPage = page;
        page.setSubPage(this);
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.setMarginTop(margin);
        this.setMarginBottom(margin);
        this.setMarginLeft(margin);
        this.setMarginRight(margin);
        this.getContext().resetWrapWidth(null);
        this.getContext().resetHeight(null);
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     */
    public void setMarginTop(float margin) {
        // 获取页面高度
        Float height = this.getContext().getPage().getHeight();
        // 获取上边距
        Float marginTop = this.getMarginTop();
        // 设置上边距
        this.marginConfiguration.setMarginTop(margin);
        // 获取光标
        Cursor cursor = this.getContext().getCursor();
        // 重置光标Y轴坐标
        if (Objects.equals(cursor.getY(), height - marginTop)) {
            cursor.setY(height - margin);
        }
        this.getContext().resetHeight(null);
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     */
    public void setMarginBottom(float margin) {
        this.marginConfiguration.setMarginBottom(margin);
        this.getContext().resetHeight(null);
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    public void setMarginLeft(float margin) {
        // 获取左边距
        Float marginLeft = this.getMarginLeft();
        // 重置左边距
        this.marginConfiguration.setMarginLeft(margin);
        // 获取光标
        Cursor cursor = this.getContext().getCursor();
        // 重置光标X轴坐标
        if (Objects.equals(cursor.getX(), marginLeft)) {
            cursor.setX(margin);
        }
        this.getContext().setWrapBeginX(margin);
        this.getContext().resetWrapWidth(null);
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     */
    public void setMarginRight(float margin) {
        this.marginConfiguration.setMarginRight(margin);
        this.getContext().resetWrapWidth(null);
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     */
    public void setBackgroundColor(Color color) {
        if (!Objects.equals(this.backgroundColor, color)) {
            this.backgroundColor = color;
            this.initBackgroundColor();
        }
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
     * 设置特殊字体名称
     *
     * @param fontNames 字体名称
     */
    public void setSpecialFontNames(String... fontNames) {
        this.getContext().addFontCache(fontNames);
        Collections.addAll(this.fontConfiguration.getSpecialFontNames(), fontNames);
    }

    /**
     * 设置字体大小
     *
     * @param size 大小
     */
    public void setFontSize(float size) {
        this.fontConfiguration.setFontSize(size);
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色
     */
    public void setFontColor(Color color) {
        this.fontConfiguration.setFontColor(color);
    }

    /**
     * 设置字体透明度
     *
     * @param alpha 透明度
     */
    public void setFontAlpha(float alpha) {
        this.fontConfiguration.setFontAlpha(alpha);
    }

    /**
     * 设置字体样式
     *
     * @param style 样式
     */
    public void setFontStyle(FontStyle style) {
        this.fontConfiguration.setFontStyle(style);
    }

    /**
     * 设置字体斜率（斜体字）
     *
     * @param slope 斜率
     */
    public void setFontSlope(float slope) {
        this.fontConfiguration.setFontSlope(slope);
    }

    /**
     * 设置字符间距
     *
     * @param spacing 间距
     */
    public void setCharacterSpacing(float spacing) {
        this.fontConfiguration.setCharacterSpacing(spacing);
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     */
    public void setLeading(float leading) {
        this.fontConfiguration.setLeading(leading);
    }

    /**
     * 设置水平对齐方式
     *
     * @param horizontalAlignment 水平对齐方式
     */
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        Objects.requireNonNull(horizontalAlignment, "the horizontal alignment can not be null");
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * 设置垂直对齐方式
     *
     * @param verticalAlignment 垂直对齐方式
     */
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        Objects.requireNonNull(verticalAlignment, "the vertical alignment can not be null");
        this.verticalAlignment = verticalAlignment;
    }

    /**
     * 获取字体
     *
     * @return 返回字体
     */
    public PDFont getFont() {
        return this.getContext().getFont(this.fontConfiguration.getFontName());
    }

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    public Float getMarginTop() {
        return this.marginConfiguration.getMarginTop();
    }

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    public Float getMarginBottom() {
        return this.marginConfiguration.getMarginBottom();
    }

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    public Float getMarginLeft() {
        return this.marginConfiguration.getMarginLeft();
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public Float getMarginRight() {
        return this.marginConfiguration.getMarginRight();
    }

    public String getFontName() {
        return this.fontConfiguration.getFontName();
    }

    public List<String> getSpecialFontNames() {
        return this.fontConfiguration.getSpecialFontNames();
    }

    public Float getFontSize() {
        return this.fontConfiguration.getFontSize();
    }

    public Color getFontColor() {
        return this.fontConfiguration.getFontColor();
    }

    public Float getFontAlpha() {
        return this.fontConfiguration.getFontAlpha();
    }

    public FontStyle getFontStyle() {
        return this.fontConfiguration.getFontStyle();
    }

    public Float getFontSlope() {
        return this.fontConfiguration.getFontSlope();
    }

    public Float getCharacterSpacing() {
        return this.fontConfiguration.getCharacterSpacing();
    }

    public Float getLeading() {
        return this.fontConfiguration.getLeading();
    }

    /**
     * 获取页面宽度
     *
     * @return 返回页面宽度
     */
    public Float getWidth() {
        return this.pageSize.getWidth();
    }

    /**
     * 获取页面高度
     *
     * @return 返回页面高度
     */
    public Float getHeight() {
        return this.pageSize.getHeight();
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
        // 父页面不为空
        if (Objects.nonNull(parent)) {
            // 循环获取
            while (Objects.nonNull(parent.getParentPage())) {
                parent = parent.getParentPage();
            }
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
        // 子页面不为空
        if (Objects.nonNull(subPage)) {
            // 循环获取
            while (Objects.nonNull(subPage.getSubPage())) {
                // 重置子页面
                subPage = subPage.getSubPage();
            }
        }
        // 返回子页面
        return subPage;
    }

    /**
     * 获取最新页面
     *
     * @return 返回最新页面
     */
    public Page getLastPage() {
        return Optional.ofNullable(this.getLastSubPage()).orElse(this);
    }

    /**
     * 获取最新页码
     *
     * @return 返回页码
     */
    public Integer getLastNo() {
        // 定义索引
        int index = Optional.ofNullable(this.getParentPage()).map(p -> 0).orElse(1);
        // 获取子页面
        Page subPage = Optional.ofNullable(this.getFirstParentPage()).orElse(this.getSubPage());
        // 子页面不为空
        if (Objects.nonNull(subPage)) {
            // 索引自增
            index++;
            // 循环获取
            while (Objects.nonNull(subPage.getSubPage())) {
                // 索引自增
                index++;
                // 重置子页面
                subPage = subPage.getSubPage();
            }
        }
        // 返回索引
        return index;
    }

    /**
     * 获取页码占位符
     *
     * @return 返回页码占位符
     */
    public String getPlaceholder() {
        return Constants.CURRENT_PAGE_PLACEHOLDER;
    }

    /**
     * 旋转
     *
     * @param angle 角度
     */
    public void rotation(RotationAngle angle) {
        Objects.requireNonNull(angle, "the rotation angle can not be null");
        this.target.setRotation(angle.getAngle());
    }

    /**
     * 缩放
     *
     * @param rectangle 页面尺寸
     */
    public void scale(PageSize rectangle) {
        // 校验
        Objects.requireNonNull(rectangle, "the rectangle can not be null");
        // 设置原尺寸
        this.getTarget().setArtBox(this.getPageSize().getSize());
        // 重置尺寸
        this.getTarget().setMediaBox(rectangle.getSize());
        this.getTarget().setCropBox(null);
        this.setPageSize(rectangle);
    }

    /**
     * 裁剪
     *
     * @param rectangle 页面尺寸
     */
    public void crop(PageSize rectangle) {
        // 校验
        Objects.requireNonNull(rectangle, "the rectangle can not be null");
        // 设置原尺寸
        this.getTarget().setArtBox(this.getPageSize().getSize());
        // 重置尺寸
        this.getTarget().setCropBox(rectangle.getSize());
        this.setPageSize(rectangle);
    }

    /**
     * 重置尺寸
     */
    public void resetRectangle() {
        // 获取原始尺寸
        COSArray artBox = this.getTarget().getCOSObject().getCOSArray(COSName.ART_BOX);
        // 重置尺寸
        if (Objects.nonNull(artBox)) {
            PDRectangle rectangle = new PDRectangle(artBox);
            this.getTarget().setMediaBox(rectangle);
            this.setPageSize(new PageSize(rectangle));
        } else {
            this.setPageSize(new PageSize(this.getTarget().getMediaBox()));
        }
        // 重置尺寸
        this.getTarget().setCropBox(null);
        this.getTarget().setArtBox(null);
    }

    /**
     * 创建子页面
     */
    public Page createSubPage() {
        return new Page(this);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 重置上下文
        super.setContext(null);
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
     * @param target              任务页面
     * @param base                基础类
     * @param marginConfiguration 边距配置
     * @param fontConfiguration   字体配置
     * @param backgroundColor     背景颜色
     */
    protected void init(PDPage target, AbstractBase base, MarginConfiguration marginConfiguration, FontConfiguration fontConfiguration, Color backgroundColor) {
        // 初始化id
        this.id = UUID.randomUUID().toString();
        // 初始化任务页面
        this.target = target;
        // 初始化页面尺寸
        this.pageSize = new PageSize(target.getCropBox());
        // 初始化边距配置
        this.marginConfiguration = new MarginConfiguration(marginConfiguration);
        // 初始化字体配置
        this.fontConfiguration = new FontConfiguration(fontConfiguration);
        // 初始化背景颜色
        this.backgroundColor = backgroundColor;
        // 初始化水平对齐方式
        this.horizontalAlignment = HorizontalAlignment.LEFT;
        // 初始化垂直对齐方式
        this.verticalAlignment = VerticalAlignment.TOP;
        // 父类初始化
        super.init(base);
        // 获取上下文
        Context context = this.getContext();
        // 重置上下文
        context.reset(this);
        // 初始化页面索引
        this.index = this.getContext().getPageCount() - 1;
        // 初始化特殊字体
        if (Objects.nonNull(this.fontConfiguration.getSpecialFontNames())) {
            for (String specialFontName : this.fontConfiguration.getSpecialFontNames()) {
                context.addFontCache(specialFontName);
            }
        }
        // 初始化背景颜色
        if (Objects.equals(this.backgroundColor, Color.WHITE)) {
            this.initBackgroundColor();
        }
        // 初始化页眉
        if (context.hasPageHeader()) {
            context.getPageHeader().setIsAlreadyRendered(Boolean.FALSE);
        }
        // 初始化页脚
        if (context.hasPageFooter()) {
            context.getPageFooter().setIsAlreadyRendered(Boolean.FALSE);
        }
    }

    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    protected void initBackgroundColor() {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true,
                this.getIsResetContentStream()
        );
        // 绘制矩形（背景矩形）
        contentStream.addRect(0, 0, this.getWithoutMarginWidth(), this.getWithoutMarginHeight());
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
    }
}
