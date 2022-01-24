package wiki.xsx.core.pdf.component.text;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf文本组件
 * @author xsx
 * @date 2020/3/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfText implements XEasyPdfComponent {

    /**
     * 文本参数
     */
    private final XEasyPdfTextParam param = new XEasyPdfTextParam();

    /**
     * 有参构造
     * @param text 待输入文本
     */
    public XEasyPdfText(String text) {
        this.param.setText(text);
    }

    /**
     * 有参构造
     * @param textList 待输入文本列表
     */
    public XEasyPdfText(List<String> textList) {
        if (textList!=null) {
            this.param.setSplitTextList(new ArrayList<>(textList)).setSplitTemplateTextList(new ArrayList<>(textList));
        }
    }

    /**
     * 有参构造
     * @param fontSize 字体大小
     * @param text 待输入文本
     */
    public XEasyPdfText(float fontSize, String text) {
        this.param.setFontSize(Math.abs(fontSize)).setText(text);
    }

    /**
     * 有参构造
     * @param fontSize 字体大小
     * @param textList 待输入文本列表
     */
    public XEasyPdfText(float fontSize, List<String> textList) {
        this.param.setFontSize(Math.abs(fontSize));
        if (textList!=null) {
            this.param.setSplitTextList(new ArrayList<>(textList)).setSplitTemplateTextList(new ArrayList<>(textList));
        }
    }

    /**
     * 开启文本追加
     * @return 返回文本组件
     */
    public XEasyPdfText enableTextAppend() {
        this.param.setTextAppend(true);
        return this;
    }

    /**
     * 开启居中样式（水平居中，垂直居中）
     * @return 返回文本组件
     */
    public XEasyPdfText enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfTextStyle.CENTER).setVerticalStyle(XEasyPdfTextStyle.CENTER);
        return this;
    }

    /**
     * 开启子组件
     * @return 返回文本组件
     */
    public XEasyPdfText enableChildComponent() {
        this.param.setChildComponent(true);
        return this;
    }

    /**
     * 开启下划线
     * @return 返回文本组件
     */
    public XEasyPdfText enableUnderline() {
        this.param.setUnderline(true);
        return this;
    }

    /**
     * 开启删除线
     * @return 返回文本组件
     */
    public XEasyPdfText enableDeleteLine() {
        this.param.setDeleteLine(true);
        return this;
    }

    /**
     * 开启高亮
     * @return 返回文本组件
     */
    public XEasyPdfText enableHighlight() {
        this.param.setHighlight(true);
        return this;
    }

    /**
     * 开启自身样式
     * @return 返回文本组件
     */
    public XEasyPdfText enableSelfStyle() {
        this.param.setUseSelfStyle(true);
        return this;
    }

    /**
     * 关闭自身样式
     * @return 返回文本组件
     */
    public XEasyPdfText disableSelfStyle() {
        this.param.setUseSelfStyle(false);
        return this;
    }

    /**
     * 设置自动缩进
     * @param indent 缩进值
     * @return 返回文本组件
     */
    public XEasyPdfText setAutoIndent(int indent) {
        this.param.setIndent(Math.abs(indent));
        return this;
    }

    /**
     * 设置边距（上下左右）
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置行间距
     * @param leading 行间距
     * @return 返回文本组件
     */
    public XEasyPdfText setLeading(float leading) {
        this.param.setLeading(Math.abs(leading));
        return this;
    }

    /**
     * 设置字体路径
     * @param fontPath 字体路径
     * @return 返回文本组件
     */
    public XEasyPdfText setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     * @param style 默认字体样式
     * @return 返回文本组件
     */
    public XEasyPdfText setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        this.param.setDefaultFontStyle(style);
        return this;
    }

    /**
     * 设置字体大小
     * @param fontSize 字体大小
     * @return 返回文本组件
     */
    public XEasyPdfText setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     * @param fontColor 字体颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setFontColor(Color fontColor) {
        if (fontColor!=null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置高亮颜色
     * @param highlightColor 高亮颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setHighlightColor(Color highlightColor) {
        if (highlightColor!=null) {
            this.param.setHighlightColor(highlightColor);
        }
        return this;
    }

    /**
     * 设置下划线颜色
     * @param underlineColor 下划线颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setUnderlineColor(Color underlineColor) {
        if (underlineColor!=null) {
            this.param.setUnderlineColor(underlineColor);
        }
        return this;
    }

    /**
     * 设置下划线线宽
     * @param underlineWidth 下划线线宽
     * @return 返回文本组件
     */
    public XEasyPdfText setUnderlineWidth(float underlineWidth) {
        this.param.setUnderlineWidth(Math.abs(underlineWidth));
        return this;
    }

    /**
     * 设置删除线颜色
     * @param deleteLineColor 删除线颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setDeleteLineColor(Color deleteLineColor) {
        if (deleteLineColor!=null) {
            this.param.setDeleteLineColor(deleteLineColor);
        }
        return this;
    }

    /**
     * 设置删除线线宽
     * @param deleteLineWidth 删除线线宽
     * @return 返回文本组件
     */
    public XEasyPdfText setDeleteLineWidth(float deleteLineWidth) {
        this.param.setDeleteLineWidth(Math.abs(deleteLineWidth));
        return this;
    }

    /**
     * 设置文本透明度
     * @param alpha 文本透明度
     * @return 返回页面水印组件
     */
    public XEasyPdfText setAlpha(float alpha) {
        if (alpha>=0&&alpha<=1) {
            this.param.setAlpha(alpha);
        }
        return this;
    }

    /**
     * 设置文本弧度(-90至+90)
     * @param radians 文本弧度
     * @return 返回页面水印组件
     */
    public XEasyPdfText setRadians(double radians) {
        if (radians%360!=0) {
            double radiansTemp = radians%90;
            if (radiansTemp==0) {
                if (radians>0) {
                    radiansTemp = 90;
                }
                else {
                    radiansTemp = 270;
                }
            }
            else if (radiansTemp<0) {
                radiansTemp += 360;
            }
            this.param.setRadians(radiansTemp);
        }
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     * @param style 样式
     * @return 返回文本组件
     */
    public XEasyPdfText setHorizontalStyle(XEasyPdfTextStyle style) {
        if (style!=null) {
            if (style==XEasyPdfTextStyle.LEFT||style==XEasyPdfTextStyle.CENTER||style==XEasyPdfTextStyle.RIGHT) {
                this.param.setHorizontalStyle(style);
            }else {
                throw new IllegalArgumentException("only set LEFT, CENTER or RIGHT style");
            }
        }
        return this;
    }

    /**
     * 设置垂直样式（居上、居中、居下）
     * @param style 样式
     * @return 返回文本组件
     */
    public XEasyPdfText setVerticalStyle(XEasyPdfTextStyle style) {
        if (style!=null) {
            if (style==XEasyPdfTextStyle.TOP||style==XEasyPdfTextStyle.CENTER||style==XEasyPdfTextStyle.BOTTOM) {
                this.param.setVerticalStyle(style);
            }else {
                throw new IllegalArgumentException("only set TOP, CENTER or BOTTOM style");
            }
        }
        return this;
    }

    /**
     * 设置拆分后的待添加文本列表
     * @param splitTextList 拆分后的待添加文本列表
     * @return 返回文本组件
     */
    public XEasyPdfText setSplitTextList(List<String> splitTextList) {
        if (splitTextList!=null) {
            this.param.setSplitTextList(new ArrayList<>(splitTextList)).setSplitTemplateTextList(new ArrayList<>(splitTextList));
        }
        return this;
    }

    /**
     * 设置是否换行（影响下一组件是否换行）
     * @param isNewLine 是否换行
     * @return 返回文本组件
     */
    public XEasyPdfText setNewLine(boolean isNewLine) {
        this.param.setNewLine(isNewLine);
        return this;
    }

    /**
     * 设置是否分页检查
     * @param isCheckPage 是否分页检查
     * @return 返回文本组件
     */
    public XEasyPdfText setCheckPage(boolean isCheckPage) {
        this.param.setCheckPage(isCheckPage);
        return this;
    }

    /**
     * 设置定位
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY).setChildComponent(true);
        return this;
    }

    /**
     * 设置宽度
     * @param width 宽度
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setWidth(float width) {
        this.param.setMaxWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     * @param height 高度
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setHeight(float height) {
        this.param.setMaxHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     * @param mode 内容模式
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setContentMode(ContentMode mode) {
        if (mode!=null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 替换占位符
     * @param placeholder 占位符
     * @param value 新字符串
     * @return 返回文本组件
     */
    public XEasyPdfText replaceAllPlaceholder(String placeholder, String value) {
        // 获取待添加文本列表
        List<String> textList = this.param.getSplitTextList();
        // 获取待添加文本列表(模板)
        List<String> templateTextList = this.param.getSplitTemplateTextList();
        // 遍历待添加文本列表
        for (int i = 0, count = textList.size(); i < count; i++) {
            // 替换占位符
            textList.set(i, templateTextList.get(i).replace(placeholder, value));
        }
        return this;
    }

    /**
     * 绘制
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        this.doDraw(document, page);
        // 重置字体为null
        this.param.setFont(null);
    }

    /**
     * 是否已经绘制
     * @return 返回布尔值，完成为true，未完成为false
     */
    @Override
    public boolean isDraw() {
        return this.param.isDraw();
    }

    /**
     * 获取高度
     * @param document pdf文档
     * @param page pdf页面
     * @param marginLeft 左边距
     * @param marginRight 右边距
     * @return 返回文本高度
     */
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page, float marginLeft, float marginRight) {
        return this.param.getHeight(document, page, marginLeft, marginRight);
    }

    /**
     * 获取宽度
     * @param document pdf文档
     * @param page pdf页面
     * @param marginLeft 左边距
     * @param marginRight 右边距
     * @return 返回文本宽度
     */
    public float getWidth(XEasyPdfDocument document, XEasyPdfPage page, float marginLeft, float marginRight) {
        return this.param.getWidth(document, page, marginLeft, marginRight);
    }

    /**
     * 获取上边距
     * @return 返回上边距
     */
    public float getMarginTop() {
        return this.param.getMarginTop();
    }

    /**
     * 获取下边距
     * @return 返回下边距
     */
    public float getMarginBottom() {
        return this.param.getMarginBottom();
    }

    /**
     * 获取左边距
     * @return 返回左边距
     */
    public float getMarginLeft() {
        return this.param.getMarginLeft();
    }

    /**
     * 获取右边距
     * @return 返回右边距
     */
    public float getMarginRight() {
        return this.param.getMarginRight();
    }

    /**
     * 获取水平样式
     * @return 返回文本样式
     */
    public XEasyPdfTextStyle getHorizontalStyle() {
        return this.param.getHorizontalStyle();
    }

    /**
     * 获取垂直样式
     * @return 返回文本样式
     */
    public XEasyPdfTextStyle getVerticalStyle() {
        return this.param.getVerticalStyle();
    }

    /**
     * 获取待添加文本列表
     * @return 返回待添加文本列表
     */
    public List<String> getSplitTextList() {
        return this.param.getSplitTextList();
    }

    /**
     * 获取字体大小
     * @return 返回字体大小
     */
    public float getFontSize() {
        return this.param.getFontSize();
    }

    /**
     * 是否使用自身样式
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isUseSelfStyle() {
        return this.param.isUseSelfStyle();
    }

    /**
     * 初始化内容流
     * @param document pdf文档
     * @param page pdf页面
     * @return 返回内容流
     */
    @SneakyThrows
    private PDPageContentStream initPageContentStream(XEasyPdfDocument document, XEasyPdfPage page) {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                document.getTarget(),
                page.getLastPage(),
                this.param.getContentMode().getMode(),
                true,
                false
        );
        // 初始化pdfBox扩展图形对象
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置文本透明度
        state.setNonStrokingAlphaConstant(this.param.getAlpha());
        // 设置透明度标记
        state.setAlphaSourceFlag(true);
        // 设置图形参数
        contentStream.setGraphicsStateParameters(state);
        // 设置字体
        contentStream.setFont(this.param.getFont(), this.param.getFontSize());
        // 设置字体颜色
        contentStream.setNonStrokingColor(this.param.getFontColor());
        // 设置行间距
        contentStream.setLeading(this.param.getLeading() + this.param.getFontHeight());
        return contentStream;
    }

    /**
     * 执行画页面
     * @param document pdf文档
     * @param page pdf页面
     */
    @SneakyThrows
    private void doDraw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果设置不自动换行，则关闭页面自动重置定位
        if (!this.param.isNewLine()) {
            // 关闭页面自动重置定位
            page.disablePosition();
        }
        // 参数初始化
        this.param.init(document, page);
        // 拆分后的待添加文本列表
        List<String> splitTextList = this.param.getSplitTextList();
        // 文本总行数索引
        int totalLineIndex = splitTextList.size() - 1;
        // 定义内容流
        PDPageContentStream stream = null;
        // 定义X轴坐标
        float beginX = 0F;
        // 遍历文本输入
        for (String text:splitTextList) {
            // 初始化X轴坐标
            beginX = this.param.initBeginX(page.getParam().getPageX(), text);
            // 分页检查，并居左写入文本
            stream = this.writeText(
                    document,
                    page,
                    this.checkPage(document, page, stream),
                    text,
                    beginX,
                    this.param.getBeginY()
            );
            // 重置X轴起始坐标为左边距
            this.param.setBeginX(this.getMarginLeft());
        }
        //如果内容流不为空，则结束文本写入，并重置Y轴起始坐标
        if (stream!=null) {
            // 如果文本总行数索引大于-1，则重置Y轴起始坐标
            if (totalLineIndex>-1) {
                // 重置Y轴起始坐标，Y轴起始坐标 = Y轴起始坐标 + 字体高度 + 行间距，由于之前多减一行，所以现在加回来
                this.param.setBeginY(this.param.getBeginY() + this.param.getFontHeight() + this.param.getLeading() * 2);
                // 获取文本宽度
                float textWidth = this.param.getFontSize() * this.param.getFont().getStringWidth(splitTextList.get(totalLineIndex)) / 1000;
                // 设置页面X轴坐标
                page.getParam().setPageX(beginX+textWidth);
            }
            // 内容流重置颜色为黑色
            stream.setNonStrokingColor(Color.BLACK);
            // 关闭内容流
            stream.close();
            // 如果允许页面重置定位，则进行重置
            if (page.getParam().isAllowResetPosition()) {
                // 设置文档页面Y轴坐标
                page.getParam().setPageY(this.param.getBeginY());
            }
        }
        // 如果不允许页面重置定位，则进行重置
        if (!page.getParam().isAllowResetPosition()) {
            // 开启页面自动重置定位
            page.enablePosition();
        }
        // 完成标记
        this.param.setDraw(true);
    }

    /**
     * 分页检查
     * @param page pdf页面
     * @param stream 内容流
     * @return 返回内容流
     */
    @SneakyThrows
    private PDPageContentStream checkPage(XEasyPdfDocument document, XEasyPdfPage page, PDPageContentStream stream) {
        if (this.param.isCheckPage()) {
            // 定义页脚高度
            float footerHeight = 0F;
            // 如果允许添加页脚，且页脚不为空则初始化页脚高度
            if (page.isAllowFooter()&&page.getFooter()!=null) {
                // 初始化页脚高度
                footerHeight = page.getFooter().getHeight(document, page);
            }
            // 分页检查
            if (this.param.getBeginY() - (this.param.getFontHeight() + footerHeight) <= this.param.getMarginBottom()) {
                // 如果内容流不为空，则关闭并设置为空
                if (stream!=null) {
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
                this.param.setBeginY(
                        // 初始化页面Y轴起始坐标，如果当前页面Y轴坐标为空，则起始坐标 = 最大高度 - 上边距 - 字体高度 - 行间距，否则起始坐标 = 当前页面Y轴起始坐标 - 上边距 - 字体高度 - 行间距
                        page.getParam().getPageY()==null?
                                rectangle.getHeight() - this.param.getMarginTop() - this.param.getFontHeight() - this.param.getLeading():
                                page.getParam().getPageY() - this.param.getMarginTop() - this.param.getFontHeight() - this.param.getLeading()
                );
            }
        }
        return stream;
    }

    /**
     * 写入文本
     * @param document pdf文档
     * @param page pdf页面
     * @param stream 内容流
     * @param text 待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     * @return 返回内容流
     */
    @SneakyThrows
    private PDPageContentStream writeText(
            XEasyPdfDocument document,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果内容流为空，则初始化内容流
        if (stream==null) {
            // 初始化内容流
            stream = this.initPageContentStream(document, page);
        }
        // 添加高亮
        this.addHighlight(stream, text, beginX, beginY);
        // 添加文本
        this.addText(stream, text, beginX, beginY);
        // 添加下划线
        this.addUnderline(stream, text, beginX, beginY);
        // 添加删除线
        this.addDeleteLine(stream, text, beginX, beginY);
        // 重置Y轴起始坐标，Y轴起始坐标 = Y轴起始坐标 - 字体高度 - 行间距
        this.param.setBeginY(this.param.getBeginY() - (this.param.getFontHeight() + this.param.getLeading()));
        return stream;
    }

    /**
     * 添加高亮
     * @param stream 内容流
     * @param text 待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addHighlight(
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启高亮显示，则设置高亮
        if (this.param.isHighlight()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-字体高度/10
            beginY = beginY - this.param.getFontHeight()/10;
            // 如果文本弧度大于0，则宽度为字体大小*字符数
            if (this.param.getRadians() > 0) {
                // 绘制矩形
                stream.addRect(beginX, beginY, this.param.getFontSize()*text.length(), this.param.getFontHeight());
            }
            // 文本弧度为0，则宽度为文本真实宽度
            else {
                // 绘制矩形
                stream.addRect(beginX, beginY, XEasyPdfTextUtil.getTextRealWidth(text, this.param.getFont(),  this.param.getFontSize()), this.param.getFontHeight());
            }
            // 设置矩形颜色
            stream.setNonStrokingColor(this.param.getHighlightColor());
            // 填充矩形
            stream.fill();
            // 重置为黑色
            stream.setNonStrokingColor(Color.BLACK);
        }
    }

    /**
     * 添加文本
     * @param stream 内容流
     * @param text 待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addText(
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果文本弧度大于0，则进行文本旋转
        if (this.param.getRadians()>0) {
            // 当前行x轴坐标
            float x = this.initRadiansX(beginX);
            // 当前行y轴坐标
            float y = this.initRadiansY(beginY);
            // 获取当前行字符数组
            char[] charArray = text.toCharArray();
            // 定义临时字符串
            String textTemp;
            // 遍历前行字符数组
            for (char c : charArray) {
                // 获取待写入文本
                textTemp = String.valueOf(c);
                // 开启文本输入
                stream.beginText();
                // 设置文本弧度
                stream.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(this.param.getRadians()), x, y));
                // 文本输入
                stream.showText(textTemp);
                // 结束文本写入
                stream.endText();
                // 重置当前行x轴坐标， x轴坐标 = x轴坐标 + 字体大小
                x += this.getFontSize();
            }
        }
        // 否则正常文本输入
        else {
            // 开启文本输入
            stream.beginText();
            // 设置文本定位
            stream.newLineAtOffset(beginX, beginY);
            // 文本输入
            stream.showText(text);
            // 结束文本写入
            stream.endText();
        }
    }

    /**
     * 添加下划线
     * @param stream 内容流
     * @param text 待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addUnderline(
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启下划线，则设置下划线
        if (this.param.isUnderline()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-下划线宽度/2-字体高度/10
            beginY = beginY - this.param.getUnderlineWidth()/2 - this.param.getFontHeight()/10;
            // 设置定位
            stream.moveTo(beginX, beginY);
            // 设置颜色
            stream.setStrokingColor(this.param.getUnderlineColor());
            // 设置线宽
            stream.setLineWidth(this.param.getUnderlineWidth());
            // 如果文本弧度大于0，则X轴起始坐标为X轴起始坐标+字体大小*字符数
            if (this.param.getRadians() > 0) {
                // 连线
                stream.lineTo(beginX+this.param.getFontSize()*text.length(), beginY);
            }else {
                // 连线
                stream.lineTo(beginX+XEasyPdfTextUtil.getTextRealWidth(text, this.param.getFont(),  this.param.getFontSize()), beginY);
            }
            // 结束
            stream.stroke();
            // 重置为黑色
            stream.setStrokingColor(Color.BLACK);
        }
    }

    /**
     * 添加删除线
     * @param stream 内容流
     * @param text 待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addDeleteLine(
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果开启删除线，则设置删除线
        if (this.param.isDeleteLine()) {
            // 初始化Y轴起始坐标为Y轴起始坐标-删除线宽度/2+字体高度/2
            beginY = beginY - this.param.getDeleteLineWidth()/2 + this.param.getFontHeight()/2;
            // 设置定位
            stream.moveTo(beginX, beginY);
            // 设置颜色
            stream.setStrokingColor(this.param.getDeleteLineColor());
            // 设置线宽
            stream.setLineWidth(this.param.getDeleteLineWidth());
            // 如果文本弧度大于0，则X轴起始坐标为X轴起始坐标+字体大小*字符数
            if (this.param.getRadians() > 0) {
                // 连线
                stream.lineTo(beginX+this.param.getFontSize()*text.length(), beginY);
            }else {
                // 连线
                stream.lineTo(beginX+XEasyPdfTextUtil.getTextRealWidth(text, this.param.getFont(),  this.param.getFontSize()), beginY);
            }
            // 结束
            stream.stroke();
            // 重置为黑色
            stream.setStrokingColor(Color.BLACK);
        }
    }

    /**
     * 初始化文本弧度x轴坐标
     * @param beginX x轴起始坐标
     * @return 返回文本弧度x轴坐标
     */
    private float initRadiansX(float beginX) {
        if (this.param.getRadians()<=90) {
            beginX += this.getFontSize()/90*this.param.getRadians();
        }
        return beginX;
    }

    /**
     * 初始化文本弧度y轴坐标
     * @param beginY y轴起始坐标
     * @return 返回文本弧度y轴坐标
     */
    private float initRadiansY(float beginY) {
        if (this.param.getRadians()>=270) {
            beginY += this.getFontSize()/90*(90-this.param.getRadians()%90);
        }
        return beginY;
    }
}
