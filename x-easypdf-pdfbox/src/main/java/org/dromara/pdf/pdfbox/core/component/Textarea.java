package org.dromara.pdf.pdfbox.core.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.HighlightMode;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.BorderUtil;
import org.dromara.pdf.pdfbox.util.CommonUtil;
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
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
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
        this.fontConfiguration = new FontConfiguration(page.getFontConfiguration());
    }
    
    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        this.getContext().addFontCache(fontName);
        this.fontConfiguration.setFontName(fontName);
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
        if (style.isItalic() && this.getFontSlope() == 0F) {
            this.setFontSlope(Constants.DEFAULT_FONT_ITALIC_SLOPE);
        }
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
     * 获取特殊字体名称
     *
     * @return 返回特殊字体名称
     */
    public List<String> getSpecialFontNames() {
        return this.fontConfiguration.getSpecialFontNames();
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
     * 获取字体颜色
     *
     * @return 返回字体颜色
     */
    public Color getFontColor() {
        return this.fontConfiguration.getFontColor();
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
     * 获取字体样式
     *
     * @return 返回字体样式
     */
    public FontStyle getFontStyle() {
        return this.fontConfiguration.getFontStyle();
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
     * 获取字符间距
     *
     * @return 返回字符间距
     */
    public Float getCharacterSpacing() {
        return this.fontConfiguration.getCharacterSpacing();
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
     * 初始化
     */
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化制表符大小
        if (Objects.isNull(this.tabSize)) {
            this.tabSize = 2;
        }
        // 初始化高亮颜色
        if (Objects.isNull(this.highlightColor)) {
            this.highlightColor = this.getContext().getPage().getBackgroundColor();
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
        // 检查换行
        this.checkWrap(this.getFontSize());
        // 检查分页
        if (this.getContext().isEqualsComponent(this.getType()) && this.checkPaging()) {
            this.setIsWrap(true);
            this.wrap(this.getFontSize());
        }
        // 初始化文本
        this.initText();
        // 初始化起始Y轴坐标
        this.initBeginY(TextUtil.getTextRealHeight(this.getTextList().size(), this.getFontSize(), this.getLeading()));
    }
    
    /**
     * 写入内容
     */
    @Override
    protected void writeContents() {
        if (this.getContext().getIsVirtualRender()) {
            this.virtualWrite();
        } else {
            this.write();
        }
    }
    
    /**
     * 重置
     */
    @Override
    protected void reset() {
    
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
        temp = temp.replace(Constants.CURRENT_PAGE_PLACEHOLDER, this.getPage().getLastNo().toString());
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
        // 重置文本列表
        this.textList = new LinkedList<>();
        // 临时文本列表无内容
        if (tempTextList.isEmpty()) {
            // 返回
            return;
        }
        // 获取上下文
        Context context = this.getContext();
        // 获取首行宽度
        float firstWidth = context.getWrapWidth() + context.getWrapBeginX() - this.getBeginX();
        // 获取新行宽度
        float newWidth = context.getWrapWidth();
        // 获取首行文本
        String text = tempTextList.get(0);
        // 首行文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 获取首行内容
            String firstContent = TextUtil.splitText(
                    context,
                    text,
                    firstWidth,
                    this.getFont(),
                    this.getFontSize(),
                    this.getCharacterSpacing(),
                    this.getSpecialFontNames()
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
                                this.getContext(),
                                text,
                                newWidth,
                                this.getFont(),
                                this.getFontSize(),
                                this.getCharacterSpacing(),
                                this.getSpecialFontNames()
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
                                    this.getContext(),
                                    text.substring(firstContent.length()),
                                    newWidth,
                                    this.getFont(),
                                    this.getFontSize(),
                                    this.getCharacterSpacing(),
                                    this.getSpecialFontNames()
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
                                this.getContext(),
                                tempTextList.get(i),
                                newWidth,
                                this.getFont(),
                                this.getFontSize(),
                                this.getCharacterSpacing(),
                                this.getSpecialFontNames()
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
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getFontSize();
    }
    
    /**
     * 虚拟渲染
     */
    @SneakyThrows
    protected void virtualWrite() {
        // 创建坐标
        Position position = new Position(
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() - this.getRelativeBeginY()
        );
        // 文本列表不为空
        if (!this.getTextList().isEmpty()) {
            // 定义文本
            String text = null;
            // 获取文本迭代器
            Iterator<String> iterator = this.getTextList().iterator();
            // 遍历文本
            while (iterator.hasNext()) {
                // 获取文本
                text = iterator.next();
                // 虚拟写入
                this.virtualWrite(text, position);
                // 判断是否还有下一个文本
                if (iterator.hasNext()) {
                    // 重置坐标
                    position.reset(
                            this.getContext().getWrapBeginX(),
                            position.getY() - this.getFontSize() - this.getLeading()
                    );
                }
            }
            // 文本宽度
            float textWidth = TextUtil.getTextRealWidth(this.getContext(), text, this.getFont(), this.getFontSize(), this.getCharacterSpacing(), this.getSpecialFontNames());
            // 重置页面位置坐标
            this.resetPagePosition(position, textWidth);
        }
        // 重置
        super.reset(this.getType(), position.getX(), position.getY());
    }
    
    /**
     * 渲染
     */
    @SneakyThrows
    protected void write() {
        // 创建坐标
        Position position = new Position(
                this.getBeginX() + this.getRelativeBeginX(),
                this.getBeginY() - this.getRelativeBeginY()
        );
        // 初始化目录
        this.initCatalog(position);
        // 文本列表不为空
        if (!this.getTextList().isEmpty()) {
            // 定义文本
            String text;
            // 文本宽度
            float textWidth = 0F;
            // 初始化内容流
            PDPageContentStream contentStream = this.initContentStream();
            // 获取文本迭代器
            Iterator<String> iterator = this.getTextList().iterator();
            // 遍历文本
            while (iterator.hasNext()) {
                // 获取文本
                text = iterator.next();
                // 重置文本宽度
                textWidth = TextUtil.getTextRealWidth(this.getContext(), text, this.getFont(), this.getFontSize(), this.getCharacterSpacing(), this.getSpecialFontNames());
                // 写入
                contentStream = this.write(text, textWidth, contentStream, position);
                // 判断是否还有下一个文本
                if (iterator.hasNext()) {
                    // 重置坐标
                    position.reset(
                            this.getContext().getWrapBeginX(),
                            position.getY() - this.getFontSize() - this.getLeading()
                    );
                }
            }
            // 关闭内容流
            contentStream.close();
            // 重置页面位置坐标
            this.resetPagePosition(position, textWidth);
        }
        // 重置
        super.reset(this.getType(), position.getX(), position.getY());
    }
    
    /**
     * 虚拟写入
     *
     * @param text     文本
     * @param position 坐标
     */
    protected void virtualWrite(String text, Position position) {
        // 文本不为空
        if (TextUtil.isNotBlank(text)) {
            this.virtualCheckPaging(position);
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
    protected PDPageContentStream write(String text, float textWidth, PDPageContentStream contentStream, Position position) {
        // 文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 检查分页
            contentStream = this.checkPaging(contentStream, position);
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
     * 检查分页
     *
     * @param contentStream 内容流
     * @param position      坐标
     * @return 返回内容流
     */
    @SneakyThrows
    protected PDPageContentStream checkPaging(PDPageContentStream contentStream, Position position) {
        // 检查分页
        if (this.checkPaging(this, position.getY())) {
            // 重置是否已经分页
            if (this.getContext().isEqualsComponent(this.getType())) {
                this.getContext().setIsAlreadyPaging(false);
            }
            // 分页
            this.processBreak();
            // 换行
            this.wrap(this.getFontSize());
            // 重置Y轴起始坐标
            position.setY(this.getBeginY() - this.getContext().getOffsetY());
            // 结束分页
            if (position.getY() > 0) {
                this.initAfterPagingPosition(position);
                // 关闭内容流
                contentStream.close();
                // 重置内容流
                return this.initContentStream();
            } else {
                // 继续分页
                return this.checkPaging(contentStream, position);
            }
        }
        // 返回内容流
        return contentStream;
    }
    
    /**
     * 检查分页
     *
     * @param position 坐标
     */
    @SneakyThrows
    protected void virtualCheckPaging(Position position) {
        // 检查分页
        if (this.checkPaging(this, position.getY())) {
            // 重置是否已经分页
            if (this.getContext().isEqualsComponent(this.getType())) {
                this.getContext().setIsAlreadyPaging(false);
            }
            // 分页
            this.processBreak();
            // 换行
            this.wrap(this.getFontSize());
            // 重置Y轴起始坐标
            position.setY(this.getBeginY() - this.getContext().getOffsetY());
            // 结束分页
            if (position.getY() > 0) {
                this.initAfterPagingPosition(position);
            } else {
                // 继续检查分页
                this.virtualCheckPaging(position);
            }
        }
    }
    
    /**
     * 初始化分页后坐标
     *
     * @param position 坐标
     */
    protected void initAfterPagingPosition(Position position) {
        if (this.getVerticalAlignment() == VerticalAlignment.BOTTOM) {
            position.setY(position.getY() + this.getFontSize());
        } else {
            position.setY(Math.min(position.getY(), this.getContext().getMaxBeginY() - this.getFontSize()));
        }
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
        CommonUtil.initFontColorAndAlpha(contentStream, this.getPage().getBackgroundColor(), this.getFontStyle(), this.getFontColor(), this.getFontAlpha());
        // 初始化位置
        this.initMatrix(contentStream, rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
        // 写入文本
        TextUtil.writeText(this.getContext(), contentStream, text, this.getSpecialFontNames(), this.getFont(), this.getFontSize());
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
        BorderUtil.drawNormalBorder(stream, rectangle, new BorderData(this, this.getBorderConfiguration()));
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
     * 初始化矩阵
     *
     * @param stream pdfbox内容流
     * @param beginX 起始X轴坐标
     * @param beginY 起始Y轴坐标
     */
    @SneakyThrows
    protected void initMatrix(PDPageContentStream stream, float beginX, float beginY) {
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
                float offset = (this.getContext().getWrapWidth() - textWidth) / 2;
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
                float offset = this.getContext().getWrapWidth() - textWidth - this.getMarginRight();
                // 设置起始X轴坐标
                rectangle.setLowerLeftX(position.getX() + offset);
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
        rectangle.setLowerLeftY(position.getY() + offsetY);
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(position.getY() + this.getFontSize());
        // 返回尺寸
        return rectangle;
    }
    
    /**
     * 重置位置坐标
     *
     * @param position  坐标
     * @param textWidth 文本宽度
     */
    protected void resetPagePosition(Position position, float textWidth) {
        // 重置X轴坐标
        position.setX(position.getX() + textWidth + this.getMarginRight());
        // 设置结束坐标
        if (Objects.nonNull(this.getCatalog())) {
            // 设置X轴坐标
            this.getCatalog().setEndX(position.getX());
            // 设置Y轴坐标
            this.getCatalog().setEndY(position.getY());
        }
    }
}
