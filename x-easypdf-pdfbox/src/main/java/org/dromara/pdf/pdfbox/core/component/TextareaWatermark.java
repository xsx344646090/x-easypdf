package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.AbstractBase;
import org.dromara.pdf.pdfbox.core.base.Context;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.ext.handler.AbstractTextHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文本域水印
 *
 * @author xsx
 * @date 2023/10/13
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
public class TextareaWatermark extends AbstractBase implements Watermark {

    /**
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
    /**
     * 文本助手
     */
    protected AbstractTextHandler textHandler;
    /**
     * 自定义起始X轴坐标
     */
    protected Float beginX;
    /**
     * 自定义起始Y轴坐标
     */
    protected Float beginY;
    /**
     * 文本行数
     */
    protected Integer lines;
    /**
     * 每行文本数
     */
    protected Integer countOfLine;
    /**
     * 每行文本间距
     */
    protected Float spacingOfLine;
    /**
     * 制表符大小
     */
    protected Integer tabSize;
    /**
     * 文本列表
     */
    protected List<String> textList;
    /**
     * 旋转角度
     */
    protected Float angle;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextareaWatermark(Document document) {
        super.setContext(document.getContext());
        this.fontConfiguration = new FontConfiguration();
        this.setLeading(100F);
        this.setFontSize(20F);
        this.setFontAlpha(0.5F);
        this.setFontColor(Color.GRAY);
    }

    /**
     * 设置文本助手
     *
     * @param handler 助手
     */
    public void setTextHandler(AbstractTextHandler handler) {
        Objects.requireNonNull(handler, "the handler can not be null");
        this.textHandler = handler;
    }

    /**
     * 设置制表符大小（空格数）
     *
     * @param size 大小
     */
    public void setTabSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("the size can not be less than 0");
        }
        this.tabSize = size;
    }

    /**
     * 设置文本
     *
     * @param texts 文本
     */
    public void setTexts(String... texts) {
        if (Objects.nonNull(texts)) {
            this.textList = new ArrayList<>(texts.length);
            Collections.addAll(this.textList, texts);
        }
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
     * 获取页面
     *
     * @return 返回页面
     */
    public Page getPage() {
        return this.getContext().getPage();
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
     * 获取字体描边颜色
     *
     * @return 返回字体描边颜色
     */
    public Color getStrokColor() {
        return this.fontConfiguration.getStrokColor();
    }

    /**
     * 设置字体描边颜色
     *
     * @param color 颜色
     */
    public void setStrokColor(Color color) {
        this.fontConfiguration.setStrokColor(color);
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
     * @return 返回字体斜率
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
        // 渲染文本
        this.renderText(page);
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
        // 渲染文本
        document.getPages().forEach(this::render);
    }

    /**
     * 初始化
     *
     * @param page 页面
     */
    protected void init(Page page) {
        // 检查文本列表
        if (Objects.isNull(this.textList) || this.textList.isEmpty()) {
            throw new IllegalArgumentException("the text list can not be empty");
        }
        // 初始化
        super.init(page);
        // 初始化特殊字体
        if (Objects.nonNull(this.fontConfiguration.getSpecialFontNames())) {
            for (String specialFontName : this.fontConfiguration.getSpecialFontNames()) {
                this.getContext().addFontCache(specialFontName);
            }
        }
        // 初始化文本助手
        if (Objects.isNull(this.textHandler)) {
            this.textHandler = this.getContext().getTextHandler();
        }
        // 初始化当前执行组件类型
        if (Objects.isNull(this.getContext().getExecutingComponentType())) {
            this.getContext().setExecutingComponentType(this.getType());
        }
        // 初始化每行文本间距
        if (Objects.isNull(this.spacingOfLine)) {
            this.spacingOfLine = 100F;
        }
        // 初始化制表符大小
        if (Objects.isNull(this.tabSize)) {
            this.tabSize = 2;
        }
        // 初始化文本列表
        this.textList = this.textList.stream().map(t -> TextUtil.replaceTab(t, this.tabSize)).collect(Collectors.toList());
        // 初始化每行文本数
        if (Objects.isNull(this.countOfLine)) {
            int length = this.textList.get(0).length();
            int characterSpacingLength = length - 1;
            float textWidth = length * this.getFontSize() + characterSpacingLength * this.getCharacterSpacing() + this.spacingOfLine;
            this.countOfLine = (int) Math.ceil(this.getPage().getWidth() / textWidth);
        }
        // 初始化行数
        if (Objects.isNull(this.lines)) {
            int count = this.textList.size();
            int leadingCount = count - 1;
            float textHeight = count * this.getFontSize() + leadingCount * this.getLeading();
            this.lines = (int) Math.ceil(this.getPage().getHeight() / textHeight);
        }
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 45F;
        }
        // 初始化自定义起始X轴坐标
        if (Objects.isNull(this.beginX)) {
            this.beginX = 0F;
        }
        // 初始化自定义起始Y轴坐标
        if (Objects.isNull(this.beginY)) {
            this.beginY = page.getHeight() - this.getFontSize();
        }
    }

    /**
     * 渲染文本
     *
     * @param page 页面
     */
    @SneakyThrows
    protected void renderText(Page page) {
        // 获取X轴起始坐标
        float beginX = this.getBeginX();
        // 获取Y轴起始坐标
        float beginY = this.getBeginY();
        // 定义文本最大宽度
        float maxWidth = 0;
        // 定义文本最大宽度初始化标记
        boolean initFlag = true;
        // 初始化内容流
        PDPageContentStream stream = this.initContentStream(page);
        // 循环写入文本
        for (int i = 0; i < this.getLines(); i++) {
            // 定义最近Y轴起始坐标
            float lastBeginY = beginY;
            // 循环写入文本
            for (int j = 0; j < this.getCountOfLine(); j++) {
                // 遍历文本
                for (String text : this.getTextList()) {
                    // 获取写入文本
                    String writeText = TextUtil.replaceTab(text, this.getTabSize());
                    // 开始写入
                    stream.beginText();
                    // 初始化字体颜色及透明度
                    CommonUtil.initFontColorAndAlpha(stream, this.getPage().getBackgroundColor(), this.getFontStyle(), this.getFontColor(), this.getStrokColor(), this.getFontAlpha());
                    // 初始化位置
                    this.initPosition(stream, beginX, beginY);
                    // 写入文本
                    this.getTextHandler().writeText(this.getFontConfiguration(), stream, new TextLineInfo(text, 0F));
                    // 结束写入
                    stream.endText();
                    // 重置Y轴起始坐标
                    beginY = beginY - this.getFontSize();
                    // 重置最大宽度
                    if (initFlag) {
                        // 重置最大宽度
                        maxWidth = Math.max(maxWidth, this.getTextHandler().getTextWidth(this.getFontConfiguration(), writeText));
                    }
                }
                // 重置X轴起始坐标
                beginX = beginX + maxWidth + this.getSpacingOfLine();
                // 非最后一个
                if (j < this.getCountOfLine() - 1) {
                    // 重置Y轴起始坐标
                    beginY = lastBeginY;
                }
                // 重置文本最大宽度初始化标记
                initFlag = false;
            }
            // 重置X轴起始坐标
            beginX = this.getBeginX();
            // 重置Y轴起始坐标
            beginY = beginY - this.getLeading();
        }
        // 关闭流
        stream.close();
    }

    /**
     * 初始化内容流
     *
     * @param page 页面
     * @return 返回内容流
     */
    @SneakyThrows
    protected PDPageContentStream initContentStream(Page page) {
        // 获取上下文
        Context context = this.getContext();
        // 初始化内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                context.getTargetDocument(),
                page.getTarget(),
                this.getContentMode().getMode(),
                true,
                this.getIsResetContentStream()
        );
        // 初始化字体
        contentStream.setFont(this.getFont(), this.getFontSize());
        // 初始化渲染模式
        contentStream.setRenderingMode(this.getFontStyle().getMode());
        // 初始化字符间隔
        contentStream.setCharacterSpacing(this.getCharacterSpacing());
        // 初始化线宽
        contentStream.setLineWidth(CommonUtil.initLineWidth(this.getFontStyle()));
        // 返回内容流
        return contentStream;
    }

    /**
     * 初始化位置
     *
     * @param stream pdfbox内容流
     * @param beginX 起始X轴坐标
     * @param beginY 起始Y轴坐标
     */
    @SneakyThrows
    protected void initPosition(PDPageContentStream stream, float beginX, float beginY) {
        // 创建矩阵
        Matrix matrix = new Matrix(1, 0, this.getFontSlope(), 1, beginX, beginY);
        // 设置旋转
        matrix.rotate(Math.toRadians(this.getAngle()));
        // 设置文本矩阵
        stream.setTextMatrix(matrix);
    }
}
