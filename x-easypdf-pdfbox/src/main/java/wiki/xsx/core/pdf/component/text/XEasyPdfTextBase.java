package wiki.xsx.core.pdf.component.text;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.XEasyPdfComponentEvent;
import wiki.xsx.core.pdf.component.XEasyPdfPagingCondition;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.awt.Color;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * pdf文本基础组件
 *
 * @author xsx
 * @date 2022/7/26
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@EqualsAndHashCode
abstract class XEasyPdfTextBase implements XEasyPdfComponent {

    private static final long serialVersionUID = 3126411220662964797L;

    /**
     * 绘制之前事件
     */
    private XEasyPdfComponentEvent beforeEvent;
    /**
     * 绘制之后事件
     */
    private XEasyPdfComponentEvent afterEvent;

    /**
     * 设置定位
     *
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回文本组件
     */
    @Override
    public abstract XEasyPdfTextBase setPosition(float beginX, float beginY);

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回文本组件
     */
    @Override
    public abstract XEasyPdfTextBase setWidth(float width);

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回文本组件
     */
    @Override
    public abstract XEasyPdfTextBase setHeight(float height);

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回文本组件
     */
    @Override
    public abstract XEasyPdfTextBase setContentMode(ContentMode mode);

    /**
     * 开启上下文重置
     *
     * @return 返回pdf组件
     */
    @Override
    public abstract XEasyPdfTextBase enableResetContext();

    /**
     * 设置X轴起始坐标
     *
     * @param beginX X轴起始坐标
     */
    abstract void setBeginX(Float beginX);

    /**
     * 设置Y轴起始坐标
     *
     * @param beginY Y轴起始坐标
     */
    abstract void setBeginY(Float beginY);

    /**
     * 设置分页条件
     *
     * @param pagingCondition 分页条件
     * @return 返回文本组件
     */
    abstract XEasyPdfTextBase setPagingCondition(XEasyPdfPagingCondition pagingCondition);

    /**
     * 设置是否需要初始化
     *
     * @param needInitialize 是否需要初始化
     * @return 返回文本组件
     */
    abstract XEasyPdfTextBase setNeedInitialize(boolean needInitialize);

    /**
     * 获取内容模式
     *
     * @return 返回内容模式
     */
    abstract ContentMode getContentMode();

    /**
     * 获取高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本高度
     */
    abstract float getHeight(XEasyPdfDocument document, XEasyPdfPage page);

    /**
     * 获取宽度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本宽度
     */
    abstract float getWidth(XEasyPdfDocument document, XEasyPdfPage page);

    /**
     * 获取Y轴起始坐标
     *
     * @return 返回Y轴起始坐标
     */
    abstract float getBeginY();

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    abstract float getMarginTop();

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    abstract float getMarginBottom();

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    abstract float getMarginLeft();

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    abstract float getMarginRight();

    /**
     * 获取待添加文本列表
     *
     * @return 返回待添加文本列表
     */
    abstract List<String> getSplitTextList();

    /**
     * 获取字体大小
     *
     * @return 返回字体大小
     */
    abstract float getFontSize();

    /**
     * 获取字体路径
     *
     * @return 返回字体路径
     */
    abstract String getFontPath();

    /**
     * 获取行间距
     *
     * @return 返回行间距
     */
    abstract float getLeading();

    /**
     * 获取文本间隔
     *
     * @return 返回文本间隔
     */
    abstract float getCharacterSpacing();

    /**
     * 获取透明度
     *
     * @return 返回透明度
     */
    abstract float getAlpha();

    /**
     * 获取文本缩进值
     *
     * @return 返回文本缩进值
     */
    abstract Integer getIndent();

    /**
     * 获取超链接地址
     *
     * @return 返回超链接地址
     */
    abstract String getLinkUrl();

    /**
     * 获取评论
     *
     * @return 返回评论
     */
    abstract String getComment();

    /**
     * 获取字体颜色
     *
     * @return 返回字体颜色
     */
    abstract Color getFontColor();

    /**
     * 获取高亮颜色
     *
     * @return 返回高亮颜色
     */
    abstract Color getHighlightColor();

    /**
     * 获取下划线颜色
     *
     * @return 返回下划线颜色
     */
    abstract Color getUnderlineColor();

    /**
     * 获取删除线颜色
     *
     * @return 返回删除线颜色
     */
    abstract Color getDeleteLineColor();

    /**
     * 获取下划线宽度
     *
     * @return 返回下划线宽度
     */
    abstract float getUnderlineWidth();

    /**
     * 获取删除线宽度
     *
     * @return 返回删除线宽度
     */
    abstract float getDeleteLineWidth();

    /**
     * 获取渲染模式
     *
     * @return 返回渲染模式
     */
    abstract XEasypdfTextRenderingMode getRenderingMode();

    /**
     * 获取分页条件
     *
     * @return 返回分页条件
     */
    abstract XEasyPdfPagingCondition getPagingCondition();

    /**
     * 是否使用自身样式
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isUseSelfStyle();

    /**
     * 是否需要初始化
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isNeedInitialize();

    /**
     * 是否重置上下文
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isResetContext();

    /**
     * 是否换行
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isNewLine();

    /**
     * 是否文本追加
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isTextAppend();

    /**
     * 是否高亮显示
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isHighlight();

    /**
     * 是否添加下划线
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isUnderline();

    /**
     * 是否添加删除线
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isDeleteLine();

    /**
     * 是否分页检查
     *
     * @return 返回布尔值，是为true，否为false
     */
    abstract boolean isCheckPage();

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    abstract void init(XEasyPdfDocument document, XEasyPdfPage page);

    /**
     * 初始化X轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param text     待写入文本
     * @return 返回X轴起始坐标
     */
    abstract float initBeginX(XEasyPdfDocument document, XEasyPdfPage page, String text);

    /**
     * 绘制之前
     *
     * @param event 组件事件
     */
    public void onBeforeDraw(XEasyPdfComponentEvent event) {
        this.beforeEvent = event;
    }

    /**
     * 绘制之后
     *
     * @param event 组件事件
     */
    public void onAfterDraw(XEasyPdfComponentEvent event) {
        this.afterEvent = event;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果绘制之前事件不为空，则执行
        if (this.beforeEvent != null) {
            // 执行绘制之前事件
            this.beforeEvent.execute(document, page, this);
        }
        // 绘制
        this.doDraw(document, page);
        // 如果绘制之后事件不为空，则执行
        if (this.afterEvent != null) {
            // 执行绘制之后事件
            this.afterEvent.execute(document, page, this);
        }
    }

    /**
     * 添加文本
     *
     * @param font   pdfbox字体
     * @param page   pdf页面
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    abstract void addText(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    );

    /**
     * 初始化内容流
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param font     pdfbox字体
     * @return 返回内容流
     */
    @SneakyThrows
    PDPageContentStream initPageContentStream(XEasyPdfDocument document, XEasyPdfPage page, PDFont font) {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                document.getTarget(),
                page.getLastPage(),
                this.getContentMode().getMode(),
                true,
                this.isResetContext()
        );

        // 初始化pdfBox扩展图形对象
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置文本透明度
        state.setNonStrokingAlphaConstant(this.getAlpha());
        // 设置透明度标记
        state.setAlphaSourceFlag(true);
        // 设置图形参数
        contentStream.setGraphicsStateParameters(state);
        // 设置字体
        contentStream.setFont(font, this.getFontSize());
        // 设置渲染模式
        contentStream.setRenderingMode(this.getRenderingMode().getMode());
        // 设置行间距
        contentStream.setLeading(this.getLeading());
        // 设置文本间隔
        contentStream.setCharacterSpacing(this.getCharacterSpacing());
        // 返回内容流
        return contentStream;
    }

    /**
     * 执行画页面
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @SneakyThrows
    void doDraw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果设置不自动换行，则关闭页面自动重置定位
        if (!this.isNewLine()) {
            // 关闭页面自动重置定位
            page.disablePosition();
        }
        // 参数初始化
        this.init(document, page);
        // 拆分后的待添加文本列表
        List<String> splitTextList = this.getSplitTextList();
        // 文本总行数索引
        int totalLineIndex = splitTextList.size() - 1;
        // 获取字体
        PDFont font = XEasyPdfFontUtil.loadFont(document, page, this.getFontPath(), true);
        // 定义内容流
        PDPageContentStream stream = null;
        // 定义X轴坐标
        float beginX = 0F;
        // 定义遍历索引
        int index = 0;
        // 遍历文本输入
        for (String text : splitTextList) {
            // 初始化X轴坐标
            beginX = this.initBeginX(document, page, text);
            // 如果为第一行，且缩进值不为空，则重置X轴坐标
            if (index == 0 && this.getIndent() != null) {
                // 重置X轴坐标 = X轴坐标 + 缩进值 * (字体大小 + 文本间隔)
                beginX = beginX + this.getIndent() * (this.getFontSize() + this.getCharacterSpacing());
            }
            // 分页检查，并居左写入文本
            stream = this.writeText(
                    document,
                    page,
                    this.checkPage(document, page, stream),
                    text,
                    beginX,
                    this.getBeginY(),
                    index == totalLineIndex
            );
            // 如果为文本追加，则重置X轴起始坐标为0
            if (this.isTextAppend()) {
                // 重置X轴起始坐标为0
                this.setBeginX(0F);
            }
            // 遍历索引自增
            index++;
        }
        //如果内容流不为空，则结束文本写入，并重置Y轴起始坐标
        if (stream != null) {
            // 如果文本总行数索引大于-1，则重置Y轴起始坐标
            if (totalLineIndex > -1) {
                // 重置Y轴起始坐标，Y轴起始坐标 = Y轴起始坐标 + 字体高度 + 行间距，由于之前多减一行，所以现在加回来
                this.setBeginY(this.getBeginY() + this.getFontSize() + this.getLeading());
                // 获取文本宽度
                float textWidth = this.getFontSize() * font.getStringWidth(splitTextList.get(totalLineIndex)) / 1000;
                // 设置页面X轴坐标
                page.setPageX(beginX + textWidth);
            }
            // 关闭内容流
            stream.close();
            // 如果允许页面重置定位，则进行重置
            if (page.isAllowResetPosition()) {
                // 设置文档页面Y轴坐标
                page.setPageY(this.getBeginY());
            }
        }
        // 如果不允许页面重置定位，则进行重置
        if (!page.isAllowResetPosition()) {
            // 开启页面自动重置定位
            page.enablePosition();
        }
    }

    /**
     * 分页检查
     *
     * @param page   pdf页面
     * @param stream 内容流
     * @return 返回内容流
     */
    @SneakyThrows
    PDPageContentStream checkPage(XEasyPdfDocument document, XEasyPdfPage page, PDPageContentStream stream) {
        // 如果需要分页检查，则进行分页检查
        if (this.isCheckPage()) {
            // 定义页脚高度
            float footerHeight = 0F;
            // 如果允许添加页脚，且页脚不为空则初始化页脚高度
            if (page.isAllowFooter() && page.getFooter() != null) {
                // 初始化页脚高度
                footerHeight = page.getFooter().getHeight(document, page);
            }
            // 定义分页标记
            boolean pagingFlag = this.getBeginY() - footerHeight < this.getMarginBottom();
            // 如果分页标记为否，且分页条件不为空，则重置分页标记
            if (!pagingFlag && this.getPagingCondition() != null) {
                // 重置分页标记
                pagingFlag = this.getPagingCondition().isPaging(document, page, this.getBeginY());
            }
            // 分页检查
            if (pagingFlag) {
                // 如果内容流不为空，则关闭并设置为空
                if (stream != null) {
                    // 关闭内容流
                    stream.close();
                    // 设置内容流为空
                    stream = null;
                }
                // 获取页面尺寸
                PDRectangle rectangle = page.getLastPage().getMediaBox();
                // 添加新页面
                page.addNewPage(document, rectangle);
                // 重置页面Y轴起始坐标
                this.setBeginY(
                        // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 字体高度 - 行间距，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 字体高度 - 行间距
                        page.getPageY() == null ?
                                rectangle.getHeight() - this.getMarginTop() - this.getFontSize() - this.getLeading() :
                                page.getPageY() - this.getMarginTop() - this.getFontSize() - this.getLeading()
                );
            }
        }
        // 返回内容流
        return stream;
    }

    /**
     * 写入文本
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param stream   内容流
     * @param text     待写入文本
     * @param beginX   X轴坐标
     * @param beginY   Y轴坐标
     * @param isLast   是否完结
     * @return 返回内容流
     */
    @SneakyThrows
    PDPageContentStream writeText(
            XEasyPdfDocument document,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY,
            boolean isLast
    ) {
        // 获取字体
        PDFont font = XEasyPdfFontUtil.loadFont(document, page, this.getFontPath(), true);
        // 如果内容流为空，则初始化内容流
        if (stream == null) {
            // 初始化内容流
            stream = this.initPageContentStream(document, page, font);
        }
        // 添加评论
        this.addComment(font, page, text, beginX, beginY, isLast);
        // 添加超链接
        this.addLink(font, page, text, beginX, beginY);
        // 添加高亮
        this.addHighlight(font, page, stream, text, beginX, beginY);
        // 初始化字体颜色
        this.initFontColor(page, stream);
        // 添加文本
        this.addText(font, page, stream, text, beginX, beginY);
        // 添加下划线
        this.addUnderline(font, page, stream, text, beginX, beginY);
        // 添加删除线
        this.addDeleteLine(font, page, stream, text, beginX, beginY);
        // 重置Y轴起始坐标，Y轴起始坐标 = Y轴起始坐标 - 字体高度 - 行间距
        this.setBeginY(this.getBeginY() - this.getFontSize() - this.getLeading());
        // 返回内容流
        return stream;
    }

    /**
     * 添加超链接（不支持旋转）
     *
     * @param font   pdfbox字体
     * @param page   pdf页面
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    void addLink(PDFont font, XEasyPdfPage page, String text, float beginX, float beginY) {
        // 获取超链接地址
        String linkUrl = this.getLinkUrl();
        // 如果超链接地址不为空，则添加超链接
        if (linkUrl != null && linkUrl.trim().length() > 0) {
            // 创建动作
            PDActionURI action = new PDActionURI();
            // 设置url
            action.setURI(this.getLinkUrl());
            // 创建链接
            PDAnnotationLink link = new PDAnnotationLink();
            // 设置名称
            link.setAnnotationName((UUID.randomUUID().toString()));
            // 设置动作
            link.setAction(action);
            // 设置范围
            link.setRectangle(this.getRectangleForWrite(font, text, beginX, beginY));
            // 添加链接
            page.getLastPage().getAnnotations().add(link);
        }
    }

    /**
     * 添加评论
     *
     * @param font   pdfbox字体
     * @param page   pdf页面
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    void addComment(PDFont font, XEasyPdfPage page, String text, float beginX, float beginY, boolean isLast) {
        // 获取评论
        String content = this.getComment();
        // 如果评论不为空，则添加评论
        if (content != null && content.trim().length() > 0 && isLast) {
            // 创建评论
            PDAnnotationText comment = new PDAnnotationText();
            // 设置名称
            comment.setAnnotationName(UUID.randomUUID().toString());
            // 设置内容
            comment.setContents(content);
            // 设置类型
            comment.setName(PDAnnotationText.NAME_COMMENT);
            // 设置日期
            comment.setCreationDate((new GregorianCalendar()));
            // 设置范围
            comment.setRectangle(this.getRectangleForWrite(font, text, beginX, beginY));
            // 添加评论
            page.getLastPage().getAnnotations().add(comment);
        }
    }

    /**
     * 添加高亮（不支持旋转）
     *
     * @param font   pdfbox字体
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    void addHighlight(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启高亮显示，则设置高亮
        if (this.isHighlight()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-字体高度/10
            beginY = beginY - this.getFontSize() / 10;
            // 获取写入尺寸
            PDRectangle rectangle = this.getRectangleForWrite(font, text, beginX, beginY);
            // 绘制矩形
            stream.addRect(rectangle.getLowerLeftX(), rectangle.getLowerLeftY(), rectangle.getWidth(), rectangle.getHeight());
            // 设置矩形颜色
            stream.setNonStrokingColor(this.getHighlightColor());
            // 填充矩形
            stream.fill();
            // 重置颜色
            stream.setNonStrokingColor(page.getBackgroundColor());
        }
    }

    /**
     * 添加下划线（不支持旋转）
     *
     * @param font   pdfbox字体
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    void addUnderline(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启下划线，则设置下划线
        if (this.isUnderline()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-下划线宽度/2-字体高度/10
            beginY = beginY - this.getUnderlineWidth() / 2 - this.getFontSize() / 10;
            // 获取写入尺寸
            PDRectangle rectangle = this.getRectangleForWrite(font, text, beginX, beginY);
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
            // 重置颜色
            stream.setStrokingColor(page.getBackgroundColor());
        }
    }

    /**
     * 添加删除线（不支持旋转）
     *
     * @param font   pdfbox字体
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    void addDeleteLine(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启删除线，则设置删除线
        if (this.isDeleteLine()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-删除线宽度/2+字体高度/2
            beginY = beginY - this.getDeleteLineWidth() / 2 + this.getFontSize() / 2;
            // 获取写入尺寸
            PDRectangle rectangle = this.getRectangleForWrite(font, text, beginX, beginY);
            // 设置颜色
            stream.setStrokingColor(this.getDeleteLineColor());
            // 设置线宽
            stream.setLineWidth(this.getDeleteLineWidth());
            // 设置定位
            stream.moveTo(rectangle.getLowerLeftX(), rectangle.getLowerLeftY());
            // 连线
            stream.lineTo(rectangle.getUpperRightX(), rectangle.getLowerLeftY());
            // 结束
            stream.stroke();
            // 重置颜色
            stream.setStrokingColor(page.getBackgroundColor());
        }
    }

    /**
     * 初始化字体颜色
     *
     * @param page   pdf页面
     * @param stream pdfbox内容流
     */
    @SneakyThrows
    void initFontColor(XEasyPdfPage page, PDPageContentStream stream) {
        // 如果渲染模式为填充，则设置字体颜色
        if (this.getRenderingMode().isFill()) {
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
        }
        // 如果渲染模式为空心，则设置字体颜色
        if (this.getRenderingMode().isStroke()) {
            // 设置字体颜色
            stream.setStrokingColor(this.getFontColor());
        }
        // 如果渲染模式为细体，则设置字体颜色
        if (this.getRenderingMode().isLight()) {
            // 设置字体颜色
            stream.setStrokingColor(page.getBackgroundColor());
            // 设置字体颜色
            stream.setNonStrokingColor(this.getFontColor());
        }
    }

    /**
     * 获取写入尺寸
     *
     * @param font   pdfbox字体
     * @param text   待写入文本
     * @param beginX x轴起始坐标
     * @param beginY y轴起始坐标
     * @return 返回写入尺寸
     */
    PDRectangle getRectangleForWrite(PDFont font, String text, float beginX, float beginY) {
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 设置起始X轴坐标
        rectangle.setLowerLeftX(beginX);
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(beginY);
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(beginY + this.getFontSize());
        // 如果文本弧度大于0，则结束X轴坐标为起始坐标+字体大小*字符数
        if (this.getRadians() > 0) {
            // 设置结束X轴坐标为起始坐标+字体大小*字符数
            rectangle.setUpperRightX(beginX + this.getFontSize() * text.length());
        }
        // 文本弧度为0，则结束X轴坐标为起始坐标+文本真实宽度
        else {
            // 设置结束X轴坐标为起始坐标+文本真实宽度
            rectangle.setUpperRightX(beginX + XEasyPdfTextUtil.getTextRealWidth(text, font, this.getFontSize(), this.getCharacterSpacing()));
        }
        // 返回尺寸
        return rectangle;
    }

    /**
     * 获取旋转弧度
     *
     * @return 返回旋转弧度
     */
    float getRadians() {
        // 默认返回0
        return 0F;
    }
}
