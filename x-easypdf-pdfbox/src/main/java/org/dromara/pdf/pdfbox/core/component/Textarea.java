package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 文本域组件
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
public class Textarea extends AbstractComponent {

    /**
     * 目录
     */
    protected CatalogInfo catalog;
    /**
     * 制表符大小
     */
    protected Integer tabSize;
    /**
     * 文本内容
     */
    protected String text;
    /**
     * 文本列表
     */
    protected List<String> textList;
    /**
     * 高亮颜色
     */
    protected Color highlightColor;
    /**
     * 是否高亮
     */
    protected Boolean isHighlight;
    /**
     * 下划线宽度
     */
    protected Float underlineWidth;
    /**
     * 下划线颜色
     */
    protected Color underlineColor;
    /**
     * 是否下划线
     */
    protected Boolean isUnderline;
    /**
     * 删除线宽度
     */
    protected Float deleteLineWidth;
    /**
     * 删除线颜色
     */
    protected Color deleteLineColor;
    /**
     * 是否删除线
     */
    protected Boolean isDeleteLine;
    /**
     * 内部目标
     */
    protected InnerDest innerDest;
    /**
     * 外部目标
     */
    protected OuterDest outerDest;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Textarea(Page page) {
        super(page);
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
     * 设置下划线宽度
     *
     * @param width 宽度
     */
    public void setUnderlineWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the underline width can not be less than 1");
        }
        this.underlineWidth = width;
    }

    /**
     * 设置删除线宽度
     *
     * @param width 宽度
     */
    public void setDeleteLineWidth(float width) {
        if (width < 1) {
            throw new IllegalArgumentException("the delete line width can not be less than 1");
        }
        this.deleteLineWidth = width;
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.TEXTAREA;
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
     * 虚拟渲染
     */
    @SneakyThrows
    public void virtualRender() {
        // 初始化
        this.init();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
        }
        // 创建坐标
        Position position = new Position(
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() - this.getRelativeBeginY()
        );
        // 文本列表不为空
        if (!this.getTextList().isEmpty()) {
            // 文本宽度
            float textWidth = 0F;
            // 初始化内容流
            PDPageContentStream contentStream = this.initContentStream();
            // 获取文本迭代器
            Iterator<String> iterator = this.getTextList().iterator();
            // 遍历文本
            while (iterator.hasNext()) {
                // 获取文本
                String text = iterator.next();
                // 重置文本宽度
                textWidth = TextUtil.getTextRealWidth(text, this.getFont(), this.getFontSize(), this.getCharacterSpacing());
                // 虚拟写入
                this.virtualWrite(text, position);
                // 判断是否还有下一个文本
                if (iterator.hasNext()) {
                    // 重置坐标
                    position.reset(
                            this.getContext().getWrapBeginX(),
                            position.getY() - this.getFontSize() - this.getLeading() - this.getUnderlineWidth()
                    );
                }
            }
            // 关闭内容流
            contentStream.close();
            // 重置页面位置坐标
            this.resetPagePosition(position, textWidth);
        }
        // 重置
        super.reset(this.getType());
        // 重置换行高度
        this.getContext().setWrapHeight(this.getFontSize());
    }

    /**
     * 渲染
     */
    @SneakyThrows
    @Override
    public void render() {
        // 初始化
        this.init();
        // 非自定义Y轴
        if (!this.getIsCustomY()) {
            // 检查分页
            this.isPaging(this, this.getBeginY());
        }
        // 创建坐标
        Position position = new Position(
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() - this.getRelativeBeginY()
        );
        // 初始化目录
        this.initCatalog(position);
        // 文本列表不为空
        if (!this.getTextList().isEmpty()) {
            // 文本宽度
            float textWidth = 0F;
            // 初始化内容流
            PDPageContentStream contentStream = this.initContentStream();
            // 获取文本迭代器
            Iterator<String> iterator = this.getTextList().iterator();
            // 遍历文本
            while (iterator.hasNext()) {
                // 获取文本
                String text = iterator.next();
                // 重置文本宽度
                textWidth = TextUtil.getTextRealWidth(text, this.getFont(), this.getFontSize(), this.getCharacterSpacing());
                // 写入
                contentStream = this.write(text, textWidth, contentStream, position);
                // 判断是否还有下一个文本
                if (iterator.hasNext()) {
                    // 重置坐标
                    position.reset(
                            this.getContext().getWrapBeginX(),
                            position.getY() - this.getFontSize() - this.getLeading() - this.getUnderlineWidth()
                    );
                }
            }
            // 关闭内容流
            contentStream.close();
            // 重置页面位置坐标
            this.resetPagePosition(position, textWidth);
        }
        // 重置
        super.reset(this.getType());
        // 重置换行高度
        this.getContext().setWrapHeight(this.getFontSize());
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 获取当前页面
        Page page = this.getContext().getPage();
        // 初始化容器换行或首行
        if (this.getContext().isContainerWrap() || (!this.getIsCustomY() && this.getContext().isFirstLine())) {
            super.setBeginY(this.getBeginY() - this.getFontSize());
        }
        // 初始化字体
        if (Objects.nonNull(this.getFontName())) {
            PDFont pdFont = PdfHandler.getFontHandler().getPDFont(this.getContext().getTargetDocument(), this.getFontName(), true);
            super.setFont(pdFont);
        }
        // 初始化制表符大小
        if (Objects.isNull(this.tabSize)) {
            this.tabSize = 4;
        }
        // 初始化高亮颜色
        if (Objects.isNull(this.highlightColor)) {
            this.highlightColor = page.getBackgroundColor();
        }
        // 初始化是否高亮
        if (Objects.isNull(this.isHighlight)) {
            this.isHighlight = Boolean.FALSE;
        }
        // 初始化下划线宽度
        if (Objects.isNull(this.underlineWidth)) {
            this.underlineWidth = 1F;
        }
        // 初始化下划线颜色
        if (Objects.isNull(this.underlineColor)) {
            this.underlineColor = this.getFontColor();
        }
        // 初始化是否下划线
        if (Objects.isNull(this.isUnderline)) {
            this.isUnderline = Boolean.FALSE;
        }
        // 初始化删除线宽度
        if (Objects.isNull(this.deleteLineWidth)) {
            this.deleteLineWidth = 1F;
        }
        // 初始化删除线颜色
        if (Objects.isNull(this.deleteLineColor)) {
            this.deleteLineColor = this.getFontColor();
        }
        // 初始化是否删除线
        if (Objects.isNull(this.isDeleteLine)) {
            this.isDeleteLine = Boolean.FALSE;
        }
        // 初始化文本
        this.initText();
    }

    /**
     * 初始化文本
     */
    protected void initText() {
        // 创建临时文本列表
        List<String> tempTextList = new LinkedList<>();
        // 添加文本
        if (Objects.nonNull(this.text)) {
            tempTextList.addAll(this.processText(this.text));
        } else if (Objects.nonNull(this.textList)) {
            this.textList.forEach(text -> tempTextList.addAll(this.processText(text)));
        } else {
            this.textList = Collections.emptyList();
            return;
        }
        // 初始化文本列表
        this.initTextList(tempTextList);
    }

    /**
     * 处理文本
     *
     * @param text 文本
     * @return 返回文本列表
     */
    protected List<String> processText(String text) {
        // 过滤特殊字符
        String temp = TextUtil.filterAll(text);
        // 替换当前页码
        temp = temp.replace(Constants.CURRENT_PAGE_PLACEHOLDER, this.getContext().getPage().getIndex().toString());
        // 替换制表符
        temp = TextUtil.replaceTab(temp, this.getTabSize());
        // 根据换行符拆分
        return Arrays.asList(temp.split("\n"));
    }

    /**
     * 初始化文本列表
     *
     * @param tempTextList 临时文本列表
     */
    protected void initTextList(List<String> tempTextList) {
        // 获取换行宽度
        float wrapWidth = this.getContext().getWrapWidth();
        // 换行小于字体大小
        if (wrapWidth < this.getFontSize()) {
            throw new IllegalArgumentException("the font size is larger, must set less font size");
        }
        // 获取首行宽度
        float firstLineWidth = wrapWidth - (this.getBeginX() - Optional.ofNullable(this.getContext().getWrapBeginX()).orElse(this.getContext().getPage().getMarginLeft()));
        // 初始化文本列表
        this.initTextList(tempTextList, firstLineWidth, wrapWidth);
    }

    /**
     * 初始化文本列表
     *
     * @param tempTextList   临时文本列表
     * @param firstLineWidth 首行宽度
     * @param newLineWidth   新行宽度
     */
    protected void initTextList(List<String> tempTextList, float firstLineWidth, float newLineWidth) {
        // 重置文本列表
        this.textList = new LinkedList<>();
        // 临时文本列表无内容
        if (tempTextList.isEmpty()) {
            // 返回
            return;
        }
        // 获取首行文本
        String text = tempTextList.get(0);
        // 首行文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 获取首行内容
            String firstContent = TextUtil.splitText(
                    text,
                    firstLineWidth,
                    this.getFont(),
                    this.getFontSize(),
                    this.getCharacterSpacing()
            );
            // 首行内容为空
            if (firstContent == null) {
                // 重置起始X轴坐标
                this.setBeginX(this.getContext().getWrapBeginX());
                // 重置起始Y轴坐标
                this.setBeginY(this.getContext().getCursor().getY() - this.getFontSize());
                // 添加文本
                this.textList.addAll(
                        TextUtil.splitLines(
                                text,
                                newLineWidth,
                                this.getFont(),
                                this.getFontSize(),
                                this.getCharacterSpacing()
                        )
                );
            } else {
                // 添加首行文本
                this.textList.add(firstContent);
                // 首行内容长度小于首行文本长度
                if (firstContent.length() < text.length()) {
                    // 添加剩余文本
                    this.textList.addAll(
                            TextUtil.splitLines(
                                    text.substring(firstContent.length()),
                                    newLineWidth,
                                    this.getFont(),
                                    this.getFontSize(),
                                    this.getCharacterSpacing()
                            )
                    );
                }
            }
        }
        // 遍历添加剩余文本
        for (int i = 1, count = tempTextList.size(); i < count; i++) {
            if (TextUtil.isNotBlank(tempTextList.get(i))) {
                this.textList.addAll(
                        TextUtil.splitLines(
                                tempTextList.get(i),
                                newLineWidth,
                                this.getFont(),
                                this.getFontSize(),
                                this.getCharacterSpacing()
                        )
                );
            }
        }
    }

    /**
     * 初始化目录
     *
     * @param position 坐标
     */
    protected void initCatalog(Position position) {
        // 初始化目录
        if (Objects.nonNull(this.catalog)) {
            // 获取上下文
            Context context = this.getContext();
            // 设置页面
            this.catalog.setPage(context.getPage());
            // 设置X轴起始坐标
            this.catalog.setBeginX(position.getX());
            // 设置Y轴起始坐标
            this.catalog.setBeginY(position.getY());
            // 添加目录
            context.getCatalogs().add(this.catalog);
        }
    }

    /**
     * 虚拟写入
     *
     * @param text     文本
     * @param position 坐标
     */
    @SneakyThrows
    protected void virtualWrite(String text, Position position) {
        // 文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 检查分页
            if (this.isPaging(this, position.getY())) {
                // 重置Y轴坐标
                position.setY(this.getBeginY());
            }
        }
    }

    /**
     * 写入
     *
     * @param text          文本
     * @param textWidth     文本宽度
     * @param contentStream 内容流
     * @param position      坐标
     */
    @SneakyThrows
    protected PDPageContentStream write(String text, float textWidth, PDPageContentStream contentStream, Position position) {
        // 文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 检查分页
            if (this.isPaging(this, position.getY())) {
                // 重置Y轴坐标
                position.setY(this.getBeginY() - this.getFontSize());
                // 关闭内容流
                contentStream.close();
                // 重置内容流
                contentStream = this.initContentStream();
            }
            // 获取尺寸
            PDRectangle lineRectangle = this.getLineRectangle(textWidth, position);
            // 添加高亮
            this.addHighlight(lineRectangle, contentStream);
            // 添加文本
            this.addText(text, lineRectangle, contentStream);
            // 添加删除线
            this.addDeleteLine(lineRectangle, contentStream);
            // 添加下划线
            this.addUnderline(lineRectangle, contentStream);
            // 添加内部目标
            this.addInnerDest(lineRectangle);
            // 添加外部目标
            this.addOuterDest(lineRectangle);
            // 添加边框
            this.addBorder(lineRectangle, contentStream);
            // 重置X轴坐标
            position.setX(lineRectangle.getLowerLeftX());
        }
        return contentStream;
    }

    /**
     * 添加高亮（不支持旋转）
     *
     * @param rectangle     尺寸
     * @param contentStream 内容流
     */
    @SneakyThrows
    protected void addHighlight(PDRectangle rectangle, PDPageContentStream contentStream) {
        // 开启高亮
        if (this.getIsHighlight()) {
            // 绘制矩形
            contentStream.addRect(rectangle.getLowerLeftX(), rectangle.getLowerLeftY() - this.getFontSize() / 10, rectangle.getWidth(), rectangle.getHeight());
            // 设置矩形颜色
            contentStream.setNonStrokingColor(this.getHighlightColor());
            // 填充矩形
            contentStream.fill();
            // 重置矩形颜色
            contentStream.setNonStrokingColor(this.getContext().getPage().getBackgroundColor());
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
    protected void addText(String text, PDRectangle rectangle, PDPageContentStream contentStream) {
        // 开始写入
        contentStream.beginText();
        // 初始化字体颜色及透明度
        this.initFontColorAndAlpha(contentStream);
        // 初始化位置
        this.initPosition(contentStream, rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
        // 写入文本
        contentStream.showText(text);
        // 结束写入
        contentStream.endText();
    }

    /**
     * 添加删除线
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    @SneakyThrows
    protected void addDeleteLine(PDRectangle rectangle, PDPageContentStream stream) {
        // 开启删除线
        if (this.getIsDeleteLine()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-删除线宽度/2+字体高度/2-字体高度/10
            float beginY = rectangle.getLowerLeftY() - this.getDeleteLineWidth() / 2 + this.getFontSize() / 2 - this.getFontSize() / 10;
            // 设置颜色
            stream.setStrokingColor(this.getDeleteLineColor());
            // 设置线宽
            stream.setLineWidth(this.getDeleteLineWidth());
            // 设置定位
            stream.moveTo(rectangle.getLowerLeftX(), beginY);
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), beginY);
            // 结束
            stream.stroke();
        }
    }

    /**
     * 添加下划线
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    @SneakyThrows
    protected void addUnderline(PDRectangle rectangle, PDPageContentStream stream) {
        // 开启下划线
        if (this.getIsUnderline()) {
            // 设置颜色
            stream.setStrokingColor(this.getUnderlineColor());
            // 设置线宽
            stream.setLineWidth(this.getUnderlineWidth());
            // 设置定位
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY());
            // 结束
            stream.stroke();
        }
    }

    /**
     * 添加内部目标
     *
     * @param rectangle 尺寸
     */
    @SneakyThrows
    protected void addInnerDest(PDRectangle rectangle) {
        // 设置内部目标
        if (Objects.nonNull(this.getInnerDest())) {
            // 判断非空
            Objects.requireNonNull(this.getInnerDest().getPage(), "the page of inner destination can not be null");
            // 创建目标
            PDPageXYZDestination destination = new PDPageXYZDestination();
            // 设置页面
            destination.setPage(this.getInnerDest().getPage().getTarget());
            // 设置内部目标Y轴坐标
            Optional.ofNullable(this.getInnerDest().getTopY()).ifPresent(destination::setTop);
            // 创建动作
            PDActionGoTo action = new PDActionGoTo();
            // 设置目标
            action.setDestination(destination);
            // 添加链接
            this.addLink(action, this.getInnerDest().getName(), rectangle, this.getInnerDest().getHighlightMode());
        }
    }

    /**
     * 添加外部目标
     *
     * @param rectangle 尺寸
     */
    @SneakyThrows
    protected void addOuterDest(PDRectangle rectangle) {
        // 设置外部目标
        if (Objects.nonNull(this.getOuterDest())) {
            // 判断非空
            Objects.requireNonNull(this.getOuterDest().getUrl(), "the url of outer destination can not be null");
            // 创建动作
            PDActionURI action = new PDActionURI();
            // 设置url
            action.setURI(this.getOuterDest().getUrl());
            // 添加链接
            this.addLink(action, this.getOuterDest().getName(), rectangle, this.getOuterDest().getHighlightMode());
        }
    }

    /**
     * 添加链接
     *
     * @param action    动作
     * @param name      名称
     * @param rectangle 尺寸
     */
    @SneakyThrows
    protected void addLink(PDAction action, String name, PDRectangle rectangle, HighlightMode mode) {
        // 获取任务页面
        PDPage target = this.getContext().getPage().getTarget();
        // 创建链接
        PDAnnotationLink link = new PDAnnotationLink();
        // 设置名称
        link.setAnnotationName(Optional.ofNullable(name).orElse(UUID.randomUUID().toString()));
        // 设置高亮模式
        Optional.ofNullable(mode).map(HighlightMode::getMode).ifPresent(link::setHighlightMode);
        // 设置动作
        link.setAction(action);
        // 设置范围
        link.setRectangle(rectangle);
        // 添加链接
        target.getAnnotations().add(link);
    }

    /**
     * 添加边框
     *
     * @param rectangle 尺寸
     * @param stream    内容流
     */
    protected void addBorder(PDRectangle rectangle, PDPageContentStream stream) {
        // 重置Y轴起始坐标
        rectangle.setLowerLeftY(rectangle.getLowerLeftY() - this.getFontSize() / 6);
        // 绘制边框
        BorderUtil.drawNormalBorder(stream, rectangle, this);
    }

    /**
     * 初始化内容流
     *
     * @return 返回内容流
     */
    @SneakyThrows
    protected PDPageContentStream initContentStream() {
        // 获取上下文
        Context context = this.getContext();
        // 初始化内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                context.getTargetDocument(),
                context.getTargetPage(),
                this.getContentMode().getMode(),
                true,
                true
        );
        // 初始化字体
        contentStream.setFont(this.getFont(), this.getFontSize());
        // 初始化渲染模式
        contentStream.setRenderingMode(this.getFontStyle().getMode());
        // 初始化行间距
        contentStream.setLeading(this.getLeading());
        // 初始化文本间隔
        contentStream.setCharacterSpacing(this.getCharacterSpacing());
        // 返回内容流
        return contentStream;
    }

    /**
     * 初始化字体颜色及透明度
     *
     * @param stream pdfbox内容流
     */
    @SneakyThrows
    protected void initFontColorAndAlpha(PDPageContentStream stream) {
        // 创建扩展图形状态
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置图形状态参数
        stream.setGraphicsStateParameters(state);
        // 填充
        if (this.getFontStyle().isFill()) {
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
            // 设置透明度
            state.setNonStrokingAlphaConstant(this.getFontAlpha());
        }
        // 空心
        if (this.getFontStyle().isStroke()) {
            // 设置字体颜色
            stream.setStrokingColor(this.getFontColor());
            // 设置透明度
            state.setStrokingAlphaConstant(this.getFontAlpha());
        }
        // 细体
        if (this.getFontStyle().isLight()) {
            // 设置背景颜色
            stream.setStrokingColor(this.getBackgroundColor());
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
            // 设置透明度
            state.setNonStrokingAlphaConstant(this.getFontAlpha());
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
        // 创建矩阵
        Matrix matrix = new Matrix(1, 0, this.getFontSlope(), 1, beginX, beginY);
        // 设置文本矩阵
        stream.setTextMatrix(matrix);
    }

    /**
     * 获取行尺寸
     *
     * @param textWidth 文本宽度
     * @param position  坐标
     * @return 返回尺寸
     */
    protected PDRectangle getLineRectangle(float textWidth, Position position) {
        // Y轴偏移量
        float offsetY = this.getFontSize() / 8;
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 匹配水平对齐方式
        switch (this.getHorizontalAlignment()) {
            // 居中
            case CENTER: {
                // 获取偏移量
                float offset = (this.getContext().getWrapWidth() + this.getContext().getPage().getMarginLeft() - this.getBeginX() - textWidth) / 2;
                // 设置起始X轴坐标
                rectangle.setLowerLeftX(position.getX() + offset);
                // 设置结束X轴坐标为起始坐标+文本真实宽度
                rectangle.setUpperRightX(rectangle.getLowerLeftX() + textWidth);
                // 结束
                break;
            }
            // 居右
            case RIGHT: {
                // 获取偏移量
                float offset = this.getContext().getWrapWidth() + this.getPage().getMarginRight() - textWidth;
                // 设置起始X轴坐标
                rectangle.setLowerLeftX(offset);
                // 设置结束X轴坐标为起始坐标+文本真实宽度
                rectangle.setUpperRightX(rectangle.getLowerLeftX() + textWidth);
                // 结束
                break;
            }
            // 居左
            default: {
                // 设置起始X轴坐标
                rectangle.setLowerLeftX(position.getX());
                // 设置结束X轴坐标为起始坐标+文本真实宽度
                rectangle.setUpperRightX(position.getX() + textWidth);
            }
        }
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(position.getY());
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(position.getY() + this.getFontSize() - offsetY);
        // 返回尺寸
        return rectangle;
    }

    /**
     * 重置页面位置坐标
     *
     * @param position  坐标
     * @param textWidth 文本宽度
     */
    protected void resetPagePosition(Position position, float textWidth) {
        // 获取上下文
        Context context = this.getContext();
        // 获取X轴坐标
        float beginX = position.getX() + textWidth + this.getMarginRight();
        // 重置光标
        context.getCursor().reset(beginX, position.getY());
        // 设置结束坐标
        if (Objects.nonNull(this.getCatalog())) {
            // 设置X轴坐标
            this.getCatalog().setEndX(beginX);
            // 设置Y轴坐标
            this.getCatalog().setEndY(position.getY());
        }
    }
}