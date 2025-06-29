package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.enums.*;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.IdUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.OutputStream;
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
     * 背景图片
     */
    protected BufferedImage backgroundImage;
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
     * 是否开启内容边框
     */
    protected Boolean isContentBorder;

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
        this.init(new PDPage(pageSize.getSize()), document, document.getMarginConfiguration(), document.getFontConfiguration(), document.getBackgroundColor(), document.getBackgroundImage());
    }

    /**
     * 有参构造
     *
     * @param document 文档
     * @param target   任务页面
     */
    public Page(Document document, PDPage target) {
        this.init(target, document, document.getMarginConfiguration(), document.getFontConfiguration(), document.getBackgroundColor(), document.getBackgroundImage());
    }

    /**
     * 有参构造
     *
     * @param page 页面
     */
    protected Page(Page page) {
        this.init(new PDPage(page.getPageSize().getSize()), page, page.getMarginConfiguration(), page.getFontConfiguration(), page.getBackgroundColor(), page.getBackgroundImage());
        this.setIsContentBorder(page.getIsContentBorder());
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
     * 设置背景图片
     *
     * @param image 图片
     */
    public void setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        if (Objects.nonNull(image)) {
            this.initBackgroundImage();
        }
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
     * 设置是否开启内容边框
     *
     * @param isContentBorder 是否开启内容边框
     */
    public void setIsContentBorder(boolean isContentBorder) {
        this.isContentBorder = isContentBorder;
        if (this.isContentBorder) {
            this.initContentBorder();
        }
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
     * 获取下边距
     *
     * @return 返回下边距
     */
    public Float getMarginBottom() {
        return this.marginConfiguration.getMarginBottom();
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
     * 获取左边距
     *
     * @return 返回左边距
     */
    public Float getMarginLeft() {
        return this.marginConfiguration.getMarginLeft();
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
        // 重置换行起始坐标
        this.getContext().setWrapBeginX(margin);
        // 重置换行宽度
        this.getContext().resetWrapWidth(null);
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public Float getMarginRight() {
        return this.marginConfiguration.getMarginRight();
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
     * 获取字体名称
     *
     * @return 返回字体名称
     */
    public String getFontName() {
        return this.fontConfiguration.getFontName();
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
     * 获取特殊字体名称
     *
     * @return 返回特殊字体名称
     */
    public List<String> getSpecialFontNames() {
        return this.fontConfiguration.getSpecialFontNames();
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
     * 获取字体大小
     *
     * @return 返回字体大小
     */
    public Float getFontSize() {
        return this.fontConfiguration.getFontSize();
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
     * 获取字体颜色
     *
     * @return 返回字体颜色
     */
    public Color getFontColor() {
        return this.fontConfiguration.getFontColor();
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
     * 获取字体透明度
     *
     * @return 返回字体透明度
     */
    public Float getFontAlpha() {
        return this.fontConfiguration.getFontAlpha();
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
     * 获取字体样式
     *
     * @return 返回字体样式
     */
    public FontStyle getFontStyle() {
        return this.fontConfiguration.getFontStyle();
    }

    /**
     * 设置字体样式
     *
     * @param style 样式
     */
    public void setFontStyle(FontStyle style) {
        this.fontConfiguration.setFontStyle(style);
        if (style.isItalic() && this.getFontSlope() == 0F) {
            this.setFontSlope(Constants.DEFAULT_FONT_ITALIC_SLOPE);
        }
    }

    /**
     * 获取字体斜率（斜体字）
     *
     * @return 返回字体斜率（斜体字）
     */
    public Float getFontSlope() {
        return this.fontConfiguration.getFontSlope();
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
     * 获取字符间距
     *
     * @return 返回字符间距
     */
    public Float getCharacterSpacing() {
        return this.fontConfiguration.getCharacterSpacing();
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
     * 获取行间距
     *
     * @return 返回行间距
     */
    public Float getLeading() {
        return this.fontConfiguration.getLeading();
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
        // 获取宽度比例
        float widthScale = rectangle.getWidth() / this.getPageSize().getWidth();
        // 获取高度比例
        float heightScale = rectangle.getHeight() / this.getPageSize().getHeight();
        // 设置原尺寸
        this.getTarget().setArtBox(this.getPageSize().getSize());
        // 重置尺寸
        this.getTarget().setMediaBox(rectangle.getSize());
        this.getTarget().setCropBox(null);
        this.setPageSize(rectangle);
        // 缩放内容
        this.scaleContents(widthScale, heightScale);
    }

    /**
     * 缩放
     *
     * @param scale 比例
     */
    public void scale(float scale) {
        this.scale(this.getPageSize().scale(scale));
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
     * @param backgroundImage     背景图片
     */
    protected void init(PDPage target, AbstractBase base, MarginConfiguration marginConfiguration, FontConfiguration fontConfiguration, Color backgroundColor, BufferedImage backgroundImage) {
        // 初始化id
        this.id = IdUtil.get();
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
        // 初始化背景图片
        this.backgroundImage = backgroundImage;
        // 初始化水平对齐方式
        this.horizontalAlignment = HorizontalAlignment.LEFT;
        // 初始化垂直对齐方式
        this.verticalAlignment = VerticalAlignment.TOP;
        // 初始化是否开启内容边框
        this.isContentBorder = Boolean.FALSE;
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
        if (!Objects.equals(this.backgroundColor, Color.WHITE)) {
            this.initBackgroundColor();
        }
        // 初始化背景图片
        if (Objects.nonNull(this.backgroundImage)) {
            this.initBackgroundImage();
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
        contentStream.addRect(0, 0, this.getWidth(), this.getHeight());
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
    }

    /**
     * 初始化背景图片
     */
    @SneakyThrows
    protected void initBackgroundImage() {
        // 初始化图像
        PDImageXObject image = PDImageXObject.createFromByteArray(
                this.getContext().getTargetDocument(),
                ImageUtil.resetBytes(ImageUtil.toBytes(this.getBackgroundImage(), ImageType.PNG.getType())),
                "unknown"
        );
        // 获取宽度
        float width = Math.min(image.getWidth(), this.getWithoutMarginWidth());
        // 获取高度
        float height = Math.min(image.getHeight(), this.getWithoutMarginHeight());
        // X轴坐标
        float x = Math.max(this.getMarginLeft(), (this.getWithoutMarginWidth() - width) / 2);
        // Y轴坐标
        float y = Math.max(this.getMarginBottom(), (this.getWithoutMarginHeight() - height) / 2);
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true,
                this.getIsResetContentStream()
        );
        // 添加图像
        contentStream.drawImage(image, x, y, width, height);
        // 关闭内容流
        contentStream.close();
    }

    /**
     * 初始化内容边框
     */
    @SneakyThrows
    protected void initContentBorder() {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true,
                false
        );
        // 绘制矩形（背景矩形）
        contentStream.addRect(this.getMarginLeft(), this.getMarginBottom(), this.getWithoutMarginWidth(), this.getWithoutMarginHeight());
        // 设置矩形颜色（背景颜色）
        contentStream.setStrokingColor(Color.BLACK);
        // 填充矩形（背景矩形）
        contentStream.stroke();
        // 关闭内容流
        contentStream.close();
    }

    /**
     * 缩放内容
     *
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     */
    @SneakyThrows
    protected void scaleContents(float widthScale, float heightScale) {
        // 获取目标页面
        PDPage page = this.getTarget();
        // 创建流解析器
        PDFStreamParser parser = new PDFStreamParser(page);
        // 获取标记
        List<Object> tokens = parser.parse();
        // 添加宽度比例
        tokens.add(0, new COSFloat(widthScale));
        tokens.add(1, COSInteger.ZERO);
        tokens.add(2, COSInteger.ZERO);
        // 添加高度比例
        tokens.add(3, new COSFloat(heightScale));
        tokens.add(4, COSInteger.ZERO);
        tokens.add(5, COSInteger.ZERO);
        // 添加连接标记
        tokens.add(6, Operator.getOperator(OperatorName.CONCAT));
        // 创建流
        PDStream contents = new PDStream(this.getContext().getTargetDocument());
        // 创建输出流
        try (OutputStream outputStream = contents.createOutputStream()) {
            // 创建内容流写入器
            ContentStreamWriter writer = new ContentStreamWriter(outputStream);
            // 写入标记
            writer.writeTokens(tokens);
            // 设置内容
            page.setContents(contents);
        }
    }
}
