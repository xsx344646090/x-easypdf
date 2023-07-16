package org.dromara.pdf.pdfbox.component;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.BorderParam;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageParam;
import org.dromara.pdf.pdfbox.enums.BorderStyle;
import org.dromara.pdf.pdfbox.enums.ContentMode;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.support.Position;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * 文本组件
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
public class Text implements Component {

    /**
     * 参数
     */
    private final TextParam param = new TextParam();

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Text(Page page) {
        this.param.setPageParam(page.getParam());
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回文本
     */
    public Text setMargin(float margin) {
        this.param.setMarginTop(margin).setMarginBottom(margin).setMarginLeft(margin).setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回文本
     */
    public Text setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回文本
     */
    public Text setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回文本
     */
    public Text setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回文本
     */
    public Text setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置制表符大小（空格数）
     *
     * @param size 大小
     * @return 返回文本
     */
    public Text setTabSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("the size can not be less than 0");
        }
        this.param.setTabSize(size);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回文本
     */
    public Text setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回文本
     */
    public Text setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     * @return 返回文本
     */
    public Text setFontName(String fontName) {
        this.param.getFontParam().setFontName(fontName);
        this.param.getFontParam().setFont(null);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本
     */
    public Text setFontSize(float fontSize) {
        if (fontSize < 1) {
            throw new IllegalArgumentException("the font size must be greater than 1");
        }
        this.param.getFontParam().setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param color 字体颜色
     * @return 返回文本
     */
    public Text setFontColor(Color color) {
        this.param.getFontParam().setFontColor(color);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param style 字体样式
     * @return 返回文本
     */
    public Text setFontStyle(FontStyle style) {
        this.param.getFontParam().setFontStyle(style);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回文本
     */
    public Text setCharacterSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the character spacing must be greater than 0");
        }
        this.param.getFontParam().setCharacterSpacing(spacing);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本
     */
    public Text setLeading(float leading) {
        if (leading < 0) {
            throw new IllegalArgumentException("the leading must be greater than 0");
        }
        this.param.getFontParam().setLeading(leading);
        return this;
    }

    /**
     * 设置高亮颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setHighlightColor(Color color) {
        if (color != null) {
            this.param.setHighlightColor(color);
        }
        return this;
    }

    /**
     * 设置高亮
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setHighlight(boolean flag) {
        this.param.setIsHighlight(flag);
        return this;
    }

    /**
     * 设置下划线宽度
     *
     * @param width 宽度
     * @return 返回文本
     */
    public Text setUnderlineWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the underline width can not be less than 1");
        }
        this.param.setUnderlineWidth(width);
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setUnderlineColor(Color color) {
        if (color != null) {
            this.param.setUnderlineColor(color);
        }
        return this;
    }

    /**
     * 设置下划线
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setUnderline(boolean flag) {
        this.param.setIsUnderline(flag);
        return this;
    }

    /**
     * 设置删除线宽度
     *
     * @param width 宽度
     * @return 返回文本
     */
    public Text setDeleteLineWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the delete line width can not be less than 1");
        }
        this.param.setDeleteLineWidth(width);
        return this;
    }

    /**
     * 设置删除线颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setDeleteLineColor(Color color) {
        if (color != null) {
            this.param.setDeleteLineColor(color);
        }
        return this;
    }

    /**
     * 设置删除线
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setDeleteLine(boolean flag) {
        this.param.setIsDeleteLine(flag);
        return this;
    }

    /**
     * 设置超链接地址
     *
     * @param url 地址
     * @return 返回文本
     */
    public Text setLinkUrl(String url) {
        if (url == null) {
            this.param.setIsLink(Boolean.FALSE);
        } else {
            this.param.setIsLink(Boolean.TRUE);
        }
        this.param.setLinkUrl(url);
        return this;
    }

    /**
     * 设置边框
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setBorder(boolean flag) {
        BorderParam borderParam = this.param.getBorderParam();
        borderParam.setIsTop(flag);
        borderParam.setIsBottom(flag);
        borderParam.setIsLeft(flag);
        borderParam.setIsRight(flag);
        return this;
    }

    /**
     * 设置边框颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setBorderColor(Color color) {
        BorderParam borderParam = this.param.getBorderParam();
        borderParam.setTopColor(color);
        borderParam.setBottomColor(color);
        borderParam.setLeftColor(color);
        borderParam.setRightColor(color);
        return this;
    }

    /**
     * 设置上边框
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setTopBorder(boolean flag) {
        this.param.getBorderParam().setIsTop(flag);
        return this;
    }

    /**
     * 设置下边框
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setBottomBorder(boolean flag) {
        this.param.getBorderParam().setIsBottom(flag);
        return this;
    }

    /**
     * 设置左边框
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setLeftBorder(boolean flag) {
        this.param.getBorderParam().setIsLeft(flag);
        return this;
    }

    /**
     * 设置右边框
     *
     * @param flag 标记
     * @return 返回文本
     */
    public Text setRightBorder(boolean flag) {
        this.param.getBorderParam().setIsRight(flag);
        return this;
    }

    /**
     * 设置上边框颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setTopBorderColor(Color color) {
        this.param.getBorderParam().setTopColor(color);
        return this;
    }

    /**
     * 设置下边框颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setBottomBorderColor(Color color) {
        this.param.getBorderParam().setBottomColor(color);
        return this;
    }

    /**
     * 设置左边框颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setLeftBorderColor(Color color) {
        this.param.getBorderParam().setLeftColor(color);
        return this;
    }

    /**
     * 设置右边框颜色
     *
     * @param color 颜色
     * @return 返回文本
     */
    public Text setRightBorderColor(Color color) {
        this.param.getBorderParam().setRightColor(color);
        return this;
    }

    /**
     * 设置边框样式
     *
     * @param style 样式
     * @return 返回文本
     */
    public Text setBorderStyle(BorderStyle style) {
        this.param.getBorderParam().setStyle(style);
        return this;
    }

    /**
     * 设置边框线宽
     *
     * @param width 线宽
     * @return 返回文本
     */
    public Text setBorderWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the border width can not be less than 1");
        }
        this.param.getBorderParam().setWidth(width);
        return this;
    }

    /**
     * 设置边框点线长度
     *
     * @param lineLength 点线长度
     * @return 返回文本
     */
    public Text setBorderLineLength(float lineLength) {
        if (lineLength < 1) {
            throw new IllegalArgumentException("the line length can not be less than 1");
        }
        this.param.getBorderParam().setLineLength(lineLength);
        return this;
    }

    /**
     * 设置边框点线间隔
     *
     * @param lineSpace 点线间隔
     * @return 返回文本
     */
    public Text setBorderLineSpace(float lineSpace) {
        if (lineSpace < 1) {
            throw new IllegalArgumentException("the line space can not be less than 1");
        }
        this.param.getBorderParam().setLineSpace(lineSpace);
        return this;
    }

    /**
     * 设置起始X轴坐标
     *
     * @param beginX X轴坐标
     * @return 返回文本
     */
    public Text setBeginX(float beginX) {
        this.param.setBeginX(beginX);
        return this;
    }

    /**
     * 设置起始Y轴坐标
     *
     * @param beginY Y轴坐标
     * @return 返回文本
     */
    public Text setBeginY(float beginY) {
        this.param.setBeginY(beginY);
        return this;
    }

    /**
     * 设置相对起始X轴坐标（不影响后续坐标）
     *
     * @param beginX X轴坐标
     * @return 返回文本
     */
    public Text setRelativeBeginX(float beginX) {
        this.param.setRelativeBeginX(beginX);
        return this;
    }

    /**
     * 设置相对起始Y轴坐标（不影响后续坐标）
     *
     * @param beginY Y轴坐标
     * @return 返回文本
     */
    public Text setRelativeBeginY(float beginY) {
        this.param.setRelativeBeginY(beginY);
        return this;
    }

    /**
     * 设置换行
     *
     * @param isWrap 是否换行
     * @return 返回文本
     */
    public Text setWrap(boolean isWrap) {
        this.param.setIsWrap(isWrap);
        return this;
    }

    /**
     * 设置分页
     *
     * @param isBreak 是否分页
     * @return 返回文本
     */
    public Text setBreak(boolean isBreak) {
        this.param.setIsBreak(isBreak);
        return this;
    }

    /**
     * 重置起始XY轴坐标
     *
     * @return 返回文本
     */
    public Text resetBeginXY() {
        this.param.setBeginX(null).setBeginY(null);
        return this;
    }

    /**
     * 重置相对起始XY轴坐标
     *
     * @return 返回文本
     */
    public Text resetRelativeBeginXY() {
        this.param.setRelativeBeginX(0F).setRelativeBeginY(0F);
        return this;
    }

    /**
     * 渲染
     *
     * @return 返回页面
     */
    @Override
    public Page render() {
        this.param.init();
        this.renderPage();
        return this.param.getPageParam().getDocumentParam().getLastPage();
    }

    /**
     * 渲染
     */
    @SneakyThrows
    private void renderPage() {
        // 检查分页
        this.checkPaging(this.param.getBeginY());
        // 获取页面参数
        PageParam pageParam = this.param.getPageParam();
        // 获取文本列表
        List<String> textList = this.param.getTextList();
        // 文本列表不为空
        if (!textList.isEmpty()) {
            // 初始化内容流
            PDPageContentStream contentStream = this.initContentStream();
            // 创建坐标
            Position position = Position.create(
                    this.param.getBeginX() + this.param.getRelativeBeginX(),
                    this.param.getBeginY() - this.param.getRelativeBeginY()
            );
            // 遍历文本列表
            for (String text : textList) {
                // 写入
                contentStream = this.write(text, contentStream, position);
                // 重置坐标
                position.reset(
                        pageParam.getMarginLeft(),
                        position.getY() - this.param.getFontParam().getFontSize() - this.param.getFontParam().getLeading() - this.param.getUnderlineWidth()
                );
            }
            // 重置Y轴坐标
            position.setY(position.getY() + this.param.getFontParam().getFontSize() + this.param.getFontParam().getLeading() + this.param.getUnderlineWidth());
            // 重置页面位置坐标
            this.resetPagePosition(pageParam, position.getY());
            // 关闭内容流
            contentStream.close();
        }
    }

    /**
     * 写入
     *
     * @param text          文本
     * @param contentStream 内容流
     * @param position      坐标
     */
    @SneakyThrows
    private PDPageContentStream write(String text, PDPageContentStream contentStream, Position position) {
        // 文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 检查分页
            if (this.checkPaging(position.getY())) {
                // 重置Y轴坐标
                position.setY(this.param.getBeginY());
                // 关闭内容流
                contentStream.close();
                // 重置内容流
                contentStream = this.initContentStream();
            }
            // 获取尺寸
            PDRectangle lineRectangle = this.getLineRectangle(text, position);
            // 添加高亮
            this.addHighlight(lineRectangle, contentStream);
            // 添加文本
            this.addText(text, lineRectangle, contentStream);
            // 添加删除线
            this.addDeleteLine(lineRectangle, contentStream);
            // 添加下划线
            this.addUnderline(lineRectangle, contentStream);
            // 添加超链接
            this.addLink(lineRectangle);
            // 添加边框
            this.addBorder(lineRectangle, contentStream);
        }
        return contentStream;
    }

    /**
     * 检查分页
     *
     * @param beginY 起始Y轴坐标
     * @return 返回布尔值，true为是，false为否
     */
    private boolean checkPaging(float beginY) {
        // 起始Y轴坐标小于页面下边距
        if (beginY < this.param.getPageParam().getMarginBottom()) {
            // 获取是否分页
            Boolean isBreak = this.param.getIsBreak();
            // 设置分页
            this.setBreak(true);
            // 重新初始化参数
            this.param.init();
            // 重置分页
            this.setBreak(isBreak);
            return true;
        }
        return false;
    }

    /**
     * 添加高亮（不支持旋转）
     *
     * @param rectangle     尺寸
     * @param contentStream 内容流
     */
    @SneakyThrows
    private void addHighlight(PDRectangle rectangle, PDPageContentStream contentStream) {
        // 开启高亮
        if (this.param.getIsHighlight()) {
            // 绘制矩形
            contentStream.addRect(rectangle.getLowerLeftX(), rectangle.getLowerLeftY() - this.param.getFontParam().getFontSize() / 10, rectangle.getWidth(), rectangle.getHeight());
            // 设置矩形颜色
            contentStream.setNonStrokingColor(this.param.getHighlightColor());
            // 填充矩形
            contentStream.fill();
            // 重置矩形颜色
            contentStream.setNonStrokingColor(this.param.getPageParam().getBackgroundColor());
        }
    }

    /**
     * 添加文本
     *
     * @param text          文本
     * @param rectangle     尺寸
     * @param contentStream 内容流
     */
    @SneakyThrows
    private void addText(String text, PDRectangle rectangle, PDPageContentStream contentStream) {
        // 开始写入
        contentStream.beginText();
        // 初始化字体颜色
        this.initFontColor(contentStream);
        // 初始化位置
        this.initPosition(contentStream, rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
        // 写入文本
        contentStream.showText(text);
        // 结束写入
        contentStream.endText();
    }

    /**
     * 添加删除线（不支持旋转）
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    @SneakyThrows
    private void addDeleteLine(PDRectangle rectangle, PDPageContentStream stream) {
        // 开启删除线
        if (this.param.getIsDeleteLine()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-删除线宽度/2+字体高度/2-字体高度/10
            float beginY = rectangle.getLowerLeftY() - this.param.getDeleteLineWidth() / 2 + this.param.getFontParam().getFontSize() / 2 - this.param.getFontParam().getFontSize() / 10;
            // 设置颜色
            stream.setStrokingColor(this.param.getDeleteLineColor());
            // 设置线宽
            stream.setLineWidth(this.param.getDeleteLineWidth());
            // 设置定位
            stream.moveTo(rectangle.getLowerLeftX(), beginY);
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), beginY);
            // 结束
            stream.stroke();
        }
    }

    /**
     * 添加下划线（不支持旋转）
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    @SneakyThrows
    private void addUnderline(PDRectangle rectangle, PDPageContentStream stream) {
        // 开启下划线
        if (this.param.getIsUnderline()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-下划线宽度/2
            // float beginY = rectangle.getLowerLeftY() - this.param.getUnderlineWidth() / 2;
            // 设置颜色
            stream.setStrokingColor(this.param.getUnderlineColor());
            // 设置线宽
            stream.setLineWidth(this.param.getUnderlineWidth());
            // 设置定位
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY());
            // 结束
            stream.stroke();
        }
    }

    /**
     * 添加超链接（不支持旋转）
     *
     * @param rectangle 尺寸
     */
    @SneakyThrows
    private void addLink(PDRectangle rectangle) {
        // 开启超链接
        if (this.param.getIsLink()) {
            // 创建动作
            PDActionURI action = new PDActionURI();
            // 设置url
            action.setURI(this.param.getLinkUrl());
            // 创建链接
            PDAnnotationLink link = new PDAnnotationLink();
            // 设置名称
            link.setAnnotationName((UUID.randomUUID().toString()));
            // 设置动作
            link.setAction(action);
            // 设置范围
            link.setRectangle(rectangle);
            // 添加链接
            this.param.getPageParam().getTarget().getAnnotations().add(link);
        }
    }

    /**
     * 添加边框
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    private void addBorder(PDRectangle rectangle, PDPageContentStream stream) {
        rectangle.setLowerLeftY(rectangle.getLowerLeftY() - this.param.getFontParam().getFontSize() / 6);
        BorderUtil.drawNormalBorder(stream, rectangle, this.param.getBorderParam());
    }

    /**
     * 重置颜色
     *
     * @param contentStream 内容流
     */
    @SneakyThrows
    private void resetColor(PDPageContentStream contentStream) {
        // 重置矩形颜色
        contentStream.setNonStrokingColor(this.param.getPageParam().getBackgroundColor());
    }

    /**
     * 初始化内容流
     *
     * @return 返回内容流
     */
    @SneakyThrows
    private PDPageContentStream initContentStream() {
        // 初始化内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.param.getPageParam().getDocumentParam().getTarget(),
                this.param.getPageParam().getTarget(),
                this.param.getContentMode().getMode(),
                true
        );
        // 初始化字体
        contentStream.setFont(this.param.getFontParam().getFont(), this.param.getFontParam().getFontSize());
        // 初始化渲染模式
        contentStream.setRenderingMode(this.param.getFontParam().getFontStyle().getMode());
        // 初始化行间距
        contentStream.setLeading(this.param.getFontParam().getLeading());
        // 初始化文本间隔
        contentStream.setCharacterSpacing(this.param.getFontParam().getCharacterSpacing());
        return contentStream;
    }

    /**
     * 初始化字体颜色
     *
     * @param stream pdfbox内容流
     */
    @SneakyThrows
    void initFontColor(PDPageContentStream stream) {
        // 填充
        if (this.param.getFontParam().getFontStyle().isFill()) {
            // 设置字体颜色
            stream.setNonStrokingColor(this.param.getFontParam().getFontColor());
        }
        // 空心
        if (this.param.getFontParam().getFontStyle().isStroke()) {
            // 设置字体颜色
            stream.setStrokingColor(this.param.getFontParam().getFontColor());
        }
        // 细体
        if (this.param.getFontParam().getFontStyle().isLight()) {
            // 设置背景颜色
            stream.setStrokingColor(this.param.getBackgroundColor());
            // 设置字体颜色
            stream.setNonStrokingColor(this.param.getFontParam().getFontColor());
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
    void initPosition(PDPageContentStream stream, float beginX, float beginY) {
        // 斜体
        if (this.param.getFontParam().getFontStyle().isItalic()) {
            // 设置文本矩阵
            stream.setTextMatrix(new Matrix(1, 0, 0.3F, 1, beginX, beginY));
        } else {
            // 设置文本定位
            stream.newLineAtOffset(beginX, beginY);
        }
    }

    /**
     * 获取行尺寸
     *
     * @param text     待写入文本
     * @param position 坐标
     * @return 返回尺寸
     */
    private PDRectangle getLineRectangle(String text, Position position) {
        // Y轴偏移量
        float offsetY = this.param.getFontParam().getFontSize() / 8;
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 设置起始X轴坐标
        rectangle.setLowerLeftX(position.getX());
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(position.getY());
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(position.getY() + this.param.getFontParam().getFontSize() - offsetY);
        // 设置结束X轴坐标为起始坐标+文本真实宽度
        rectangle.setUpperRightX(position.getX() + TextUtil.getTextRealWidth(text, this.param.getFontParam().getFont(), this.param.getFontParam().getFontSize(), this.param.getFontParam().getCharacterSpacing()));
        // 返回尺寸
        return rectangle;
    }

    /**
     * 重置页面位置坐标
     *
     * @param pageParam 页面参数
     * @param beginY    Y轴坐标
     */
    private void resetPagePosition(PageParam pageParam, float beginY) {
        // 获取文本列表
        List<String> textList = this.param.getTextList();
        // 文本列表不为空
        if (!textList.isEmpty()) {
            // 获取最后一行文本宽度
            float width = TextUtil.getTextRealWidth(
                    textList.get(textList.size() - 1),
                    this.param.getFontParam().getFont(),
                    this.param.getFontParam().getFontSize(),
                    this.param.getFontParam().getCharacterSpacing()
            );
            // 定义X轴坐标
            float beginX;
            // 文本列表为单行
            if (textList.size() == 1) {
                // 是否自定义X轴坐标
                if (this.param.getIsCustomX()) {
                    // 重置X轴坐标
                    beginX = this.param.getBeginX() + width;
                } else {
                    // 重置X轴坐标
                    beginX = pageParam.getBeginX() + this.param.getMarginLeft() - this.param.getMarginRight() + width;
                }
            } else {
                // 重置X轴坐标
                beginX = pageParam.getMarginLeft() + this.param.getMarginLeft() - this.param.getMarginRight() + width;
            }
            // 重置页面X轴起始坐标
            pageParam.setBeginX(beginX);
            // 重置页面Y轴起始坐标
            pageParam.setBeginY(beginY);
        }

        // 重置X轴起始坐标
        this.param.setBeginX(pageParam.getBeginX());
        // 重置Y轴起始坐标
        this.param.setBeginY(pageParam.getBeginY());
    }
}
