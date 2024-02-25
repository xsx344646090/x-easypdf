package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.util.List;
import java.util.Objects;

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
public class TextareaWatermark extends AbstractBaseFont implements Watermark {

    /**
     * 自定义起始X轴坐标
     */
    private Float beginX;
    /**
     * 自定义起始Y轴坐标
     */
    private Float beginY;
    /**
     * 文本行数
     */
    private Integer lines;
    /**
     * 每行文本数
     */
    private Integer countOfLine;
    /**
     * 每行文本间距
     */
    private Float spacingOfLine;
    /**
     * 制表符大小
     */
    private Integer tabSize;
    /**
     * 文本列表
     */
    private List<String> textList;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextareaWatermark(Document document) {
        super.setContext(document.getContext());
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        super.setFontName(fontName);
        super.setFont(null);
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
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.WATERMARK;
    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {
        // 检查文本列表
        if (Objects.isNull(this.textList) || this.textList.isEmpty()) {
            throw new IllegalArgumentException("the text list can not be empty");
        }
        // 初始化当前执行组件类型
        if (Objects.isNull(this.getContext().getExecutingComponentType())) {
            this.getContext().setExecutingComponentType(this.getType());
        }
        // 初始化字体
        if (Objects.nonNull(this.getFontName())) {
            PDFont pdFont = PdfHandler.getFontHandler().getPDFont(this.getContext().getTargetDocument(), this.getFontName(), true);
            super.setFont(pdFont);
        }
        // 初始化行数
        if (Objects.isNull(this.lines)) {
            this.lines = 1;
        }
        // 初始化每行文本数
        if (Objects.isNull(this.countOfLine)) {
            this.countOfLine = 1;
        }
        // 初始化每行文本间距
        if (Objects.isNull(this.spacingOfLine)) {
            this.spacingOfLine = 50F;
        }
        // 初始化制表符大小
        if (Objects.isNull(this.tabSize)) {
            this.tabSize = 4;
        }
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
        // 初始化参数
        super.init(page, false);
        // 初始化基础
        this.initBase();
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
                    // 开始写入
                    stream.beginText();
                    // 初始化字体颜色
                    this.initFontColor(stream);
                    // 初始化位置
                    this.initPosition(stream, beginX, beginY);
                    // 获取写入文本
                    String writeText = TextUtil.replaceTab(text, this.getTabSize());
                    // 写入文本
                    stream.showText(writeText);
                    // 结束写入
                    stream.endText();
                    // 重置Y轴起始坐标
                    beginY = beginY - this.getFontSize();
                    // 重置最大宽度
                    if (initFlag) {
                        // 重置最大宽度
                        maxWidth = Math.max(maxWidth, TextUtil.getTextRealWidth(writeText, this.getFont(), this.getFontSize(), this.getCharacterSpacing()));
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
        // 返回内容流
        return contentStream;
    }

    /**
     * 初始化字体颜色
     *
     * @param stream pdfbox内容流
     */
    @SneakyThrows
    protected void initFontColor(PDPageContentStream stream) {
        // 填充
        if (this.getFontStyle().isFill()) {
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
        }
        // 空心
        if (this.getFontStyle().isStroke()) {
            // 设置字体颜色
            stream.setStrokingColor(this.getFontColor());
        }
        // 细体
        if (this.getFontStyle().isLight()) {
            // 设置背景颜色
            stream.setStrokingColor(this.getBackgroundColor());
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
        }
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
        // 斜体
        if (this.getFontStyle().isItalic()) {
            // 设置文本矩阵
            stream.setTextMatrix(new Matrix(1, 0, 0.3F, 1, beginX, beginY));
        } else {
            // 设置文本定位
            stream.newLineAtOffset(beginX, beginY);
        }
    }
}
