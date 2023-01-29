package wiki.xsx.core.pdf.component.text;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;
import wiki.xsx.core.pdf.component.XEasyPdfPagingCondition;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 * pdf文本角标组件
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
@EqualsAndHashCode(callSuper = true)
public class XEasyPdfTextScript extends XEasyPdfTextBase {

    private static final long serialVersionUID = 8367990811132640481L;

    /**
     * 文本参数
     */
    private XEasyPdfTextScriptParam param = new XEasyPdfTextScriptParam();

    /**
     * 有参构造
     *
     * @param param 文本参数
     */
    XEasyPdfTextScript(XEasyPdfTextScriptParam param) {
        this.param = param;
    }

    /**
     * 有参构造
     *
     * @param text 待写入文本
     */
    public XEasyPdfTextScript(String text) {
        this.param.setText(text);
    }

    /**
     * 开启子组件
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableChildComponent() {
        this.param.setIsChildComponent(Boolean.TRUE);
        return this;
    }

    /**
     * 开启下划线
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableUnderline() {
        this.param.setIsUnderline(Boolean.TRUE);
        return this;
    }

    /**
     * 开启删除线
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableDeleteLine() {
        this.param.setIsDeleteLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启高亮
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableHighlight() {
        this.param.setIsHighlight(Boolean.TRUE);
        return this;
    }

    /**
     * 开启整行旋转
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableRotateLine() {
        this.param.setIsRotateLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自身样式
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript enableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭自身样式
     *
     * @return 返回角标组件
     */
    public XEasyPdfTextScript disableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.FALSE);
        return this;
    }

    /**
     * 设置自动缩进
     *
     * @param indent 缩进值
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setAutoIndent(int indent) {
        this.param.setIndent(Math.abs(indent));
        return this;
    }

    /**
     * 设置X轴起始坐标
     *
     * @param beginX X轴起始坐标
     */
    @Override
    void setBeginX(Float beginX) {
        this.param.setBeginX(beginX);
    }

    /**
     * 设置Y轴起始坐标
     *
     * @param beginY Y轴起始坐标
     */
    @Override
    void setBeginY(Float beginY) {
        this.param.setBeginY(beginY);
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setLeading(float leading) {
        this.param.setLeading(Math.abs(leading));
        return this;
    }

    /**
     * 设置文本间隔
     *
     * @param characterSpacing 文本间隔
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setCharacterSpacing(float characterSpacing) {
        this.param.setCharacterSpacing(Math.abs(characterSpacing));
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置高亮颜色
     *
     * @param highlightColor 高亮颜色
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setHighlightColor(Color highlightColor) {
        if (highlightColor != null) {
            this.param.setHighlightColor(highlightColor);
        }
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param underlineColor 下划线颜色
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setUnderlineColor(Color underlineColor) {
        if (underlineColor != null) {
            this.param.setUnderlineColor(underlineColor);
        }
        return this;
    }

    /**
     * 设置下划线线宽
     *
     * @param underlineWidth 下划线线宽
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setUnderlineWidth(float underlineWidth) {
        this.param.setUnderlineWidth(Math.abs(underlineWidth));
        return this;
    }

    /**
     * 设置删除线颜色
     *
     * @param deleteLineColor 删除线颜色
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setDeleteLineColor(Color deleteLineColor) {
        if (deleteLineColor != null) {
            this.param.setDeleteLineColor(deleteLineColor);
        }
        return this;
    }

    /**
     * 设置删除线线宽
     *
     * @param deleteLineWidth 删除线线宽
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setDeleteLineWidth(float deleteLineWidth) {
        this.param.setDeleteLineWidth(Math.abs(deleteLineWidth));
        return this;
    }

    /**
     * 设置超链接地址
     *
     * @param linkUrl 超链接地址
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setLink(String linkUrl) {
        this.param.setLinkUrl(linkUrl);
        return this;
    }

    /**
     * 设置评论
     *
     * @param comment 评论
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setComment(String comment) {
        this.param.setComment(comment);
        return this;
    }

    /**
     * 设置文本透明度
     *
     * @param alpha 文本透明度
     * @return 返回页面水印组件
     */
    public XEasyPdfTextScript setAlpha(float alpha) {
        if (alpha >= 0 && alpha <= 1) {
            this.param.setAlpha(alpha);
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param oldValue    待替换字符串
     * @param replacement 替换字符串
     * @return 返回返回角标组件
     */
    public XEasyPdfTextScript setReplaceCharacters(String oldValue, String replacement) {
        if (oldValue != null && replacement != null) {
            this.param.getReplaceCharacterMap().put(oldValue, replacement);
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param replaceMap 待替换字典
     * @return 返回返回角标组件
     */
    public XEasyPdfTextScript setReplaceCharacters(Map<String, String> replaceMap) {
        if (replaceMap != null && !replaceMap.isEmpty()) {
            this.param.getReplaceCharacterMap().putAll(replaceMap);
        }
        return this;
    }

    /**
     * 设置是否换行（影响下一个组件是否换行）
     *
     * @param isNewLine 是否换行
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setNewLine(boolean isNewLine) {
        this.param.setIsNewLine(isNewLine);
        return this;
    }

    /**
     * 设置是否分页检查
     *
     * @param isCheckPage 是否分页检查
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setCheckPage(boolean isCheckPage) {
        this.param.setCheckPage(isCheckPage);
        return this;
    }

    /**
     * 设置定位
     *
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setWidth(float width) {
        this.param.setMaxWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setHeight(float height) {
        this.param.setMaxHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight 最大高度
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setMaxHeight(Float maxHeight) {
        this.param.setMaxHeight(maxHeight);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置渲染模式
     *
     * @param renderingMode 渲染模式
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setRenderingMode(XEasypdfTextRenderingMode renderingMode) {
        if (renderingMode != null) {
            this.param.setRenderingMode(renderingMode);
        }
        return this;
    }

    /**
     * 设置分页条件
     *
     * @param pagingCondition 分页条件
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setPagingCondition(XEasyPdfPagingCondition pagingCondition) {
        this.param.setPagingCondition(pagingCondition);
        return this;
    }

    /**
     * 设置是否需要初始化
     *
     * @param needInitialize 是否需要初始化
     * @return 返回角标组件
     */
    @Override
    public XEasyPdfTextScript setNeedInitialize(boolean needInitialize) {
        this.param.setIsNeedInitialize(needInitialize);
        return this;
    }

    /**
     * 设置字体缩放比例
     *
     * @param fontScaleRatio 字体缩放比例（0.1~0.7之间）
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setFontScaleRatio(float fontScaleRatio) {
        float ratio = Math.abs(fontScaleRatio);
        float max = 0.7F;
        float min = 0.1F;
        this.param.setFontScaleRatio(Math.min(max, Math.max(ratio, min)));
        return this;
    }

    /**
     * 设置角标类型
     *
     * @param scriptType 角标类型
     * @return 返回角标组件
     */
    public XEasyPdfTextScript setScriptType(XEasyPdfTextScriptType scriptType) {
        this.param.setScriptType(scriptType);
        return this;
    }

    /**
     * 获取高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本高度
     */
    @Override
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        return this.param.getHeight(document, page);
    }

    /**
     * 获取宽度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本宽度
     */
    @Override
    public float getWidth(XEasyPdfDocument document, XEasyPdfPage page) {
        return this.param.getWidth(document, page);
    }

    /**
     * 获取内容模式
     *
     * @return 返回内容模式
     */
    @Override
    public ContentMode getContentMode() {
        return this.param.getContentMode();
    }

    /**
     * 获取Y轴起始坐标
     *
     * @return 返回Y轴起始坐标
     */
    @Override
    float getBeginY() {
        return this.param.getBeginY();
    }

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    @Override
    public float getMarginTop() {
        return this.param.getMarginTop();
    }

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    @Override
    public float getMarginBottom() {
        return this.param.getMarginBottom();
    }

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    @Override
    public float getMarginLeft() {
        return this.param.getMarginLeft();
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    @Override
    public float getMarginRight() {
        return this.param.getMarginRight();
    }

    /**
     * 获取待添加文本列表
     *
     * @return 返回待添加文本列表
     */
    @Override
    public List<String> getSplitTextList() {
        return this.param.getSplitTextList();
    }

    /**
     * 获取字体大小
     *
     * @return 返回字体大小
     */
    @Override
    public float getFontSize() {
        return this.param.getFontSize();
    }

    /**
     * 获取字体路径
     *
     * @return 返回字体路径
     */
    @Override
    public String getFontPath() {
        return this.param.getFontPath();
    }

    /**
     * 获取行间距
     *
     * @return 返回行间距
     */
    @Override
    public float getLeading() {
        return this.param.getLeading();
    }

    /**
     * 获取文本间隔
     *
     * @return 返回文本间隔
     */
    @Override
    public float getCharacterSpacing() {
        return this.param.getCharacterSpacing();
    }

    /**
     * 获取透明度
     *
     * @return 返回透明度
     */
    @Override
    float getAlpha() {
        return this.param.getAlpha();
    }

    /**
     * 获取文本缩进值
     *
     * @return 返回文本缩进值
     */
    @Override
    Integer getIndent() {
        return this.param.getIndent();
    }

    /**
     * 获取超链接地址
     *
     * @return 返回超链接地址
     */
    @Override
    String getLinkUrl() {
        return this.param.getLinkUrl();
    }

    /**
     * 获取评论
     *
     * @return 返回评论
     */
    @Override
    String getComment() {
        return this.param.getComment();
    }

    /**
     * 获取字体颜色
     *
     * @return 返回字体颜色
     */
    @Override
    Color getFontColor() {
        return this.param.getFontColor();
    }

    /**
     * 获取高亮颜色
     *
     * @return 返回高亮颜色
     */
    @Override
    Color getHighlightColor() {
        return this.param.getHighlightColor();
    }

    /**
     * 获取下划线颜色
     *
     * @return 返回下划线颜色
     */
    @Override
    Color getUnderlineColor() {
        return this.param.getUnderlineColor();
    }

    /**
     * 获取删除线颜色
     *
     * @return 返回删除线颜色
     */
    @Override
    Color getDeleteLineColor() {
        return this.param.getDeleteLineColor();
    }

    /**
     * 获取下划线宽度
     *
     * @return 返回下划线宽度
     */
    @Override
    float getUnderlineWidth() {
        return this.param.getUnderlineWidth();
    }

    /**
     * 获取删除线宽度
     *
     * @return 返回删除线宽度
     */
    @Override
    float getDeleteLineWidth() {
        return this.param.getDeleteLineWidth();
    }

    /**
     * 获取水平样式
     *
     * @return 返回水平样式
     */
    public XEasyPdfPositionStyle getHorizontalStyle() {
        return this.param.getHorizontalStyle();
    }

    /**
     * 获取垂直样式
     *
     * @return 返回垂直样式
     */
    public XEasyPdfPositionStyle getVerticalStyle() {
        return this.param.getVerticalStyle();
    }

    /**
     * 获取渲染模式
     *
     * @return 返回渲染模式
     */
    @Override
    XEasypdfTextRenderingMode getRenderingMode() {
        return this.param.getRenderingMode();
    }

    /**
     * 获取分页条件
     *
     * @return 返回分页条件
     */
    @Override
    XEasyPdfPagingCondition getPagingCondition() {
        return this.param.getPagingCondition();
    }

    /**
     * 是否使用自身样式
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isUseSelfStyle() {
        return this.param.getUseSelfStyle();
    }

    /**
     * 是否需要初始化
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    public boolean isNeedInitialize() {
        return this.param.getIsNeedInitialize();
    }

    /**
     * 是否重置上下文
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isResetContext() {
        return this.param.getIsResetContext();
    }

    /**
     * 是否换行
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isNewLine() {
        return this.param.getIsNewLine();
    }

    /**
     * 是否文本追加
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isTextAppend() {
        return this.param.getIsTextAppend();
    }

    /**
     * 是否高亮显示
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isHighlight() {
        return this.param.getIsHighlight();
    }

    /**
     * 是否添加下划线
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isUnderline() {
        return this.param.getIsUnderline();
    }

    /**
     * 是否添加删除线
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isDeleteLine() {
        return this.param.getIsDeleteLine();
    }

    /**
     * 是否分页检查
     *
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    boolean isCheckPage() {
        return this.param.getCheckPage();
    }

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果字体缩放比例未初始化，则初始化为0.5F
        if (this.param.getFontScaleRatio() == null) {
            // 初始化为0.5F
            this.param.setFontScaleRatio(0.5F);
        }
        // 如果角标类型未初始化，则初始化为上标类型
        if (this.param.getScriptType() == null) {
            // 初始化为上标类型
            this.param.setScriptType(XEasyPdfTextScriptType.SUPERSCRIPT);
        }
        // 如果渲染模式未初始化，则初始化为正常
        if (this.param.getRenderingMode() == null) {
            // 初始化为正常
            this.param.setRenderingMode(XEasypdfTextRenderingMode.NORMAL);
        }
        // 初始化文本追加
        this.param.setIsTextAppend(Boolean.TRUE);
        // 其他参数初始化
        this.param.init(document, page);
    }

    /**
     * 初始化X轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param text     待写入文本
     * @return 返回X轴起始坐标
     */
    @Override
    float initBeginX(XEasyPdfDocument document, XEasyPdfPage page, String text) {
        return this.param.initBeginX(document, page, text);
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化
        this.init(document, page);
        // 绘制
        this.doDraw(document, page);
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
    @SneakyThrows
    @Override
    void addText(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 如果角标类型为上标，则添加文本上标
        if (this.param.getScriptType() == XEasyPdfTextScriptType.SUPERSCRIPT) {
            // 添加文本上标
            this.addTextSuperscript(font, page, stream, text, beginX, beginY);
        }
        // 如果角标类型为下标，则添加文本下标
        if (this.param.getScriptType() == XEasyPdfTextScriptType.SUBSCRIPT) {
            // 添加文本下标
            this.addTextSubscript(font, page, stream, text, beginX, beginY);
        }
    }

    /**
     * 添加文本上标
     *
     * @param font   pdfbox字体
     * @param page   pdf页面
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addTextSuperscript(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 定义文本移动基线
        float textRise = XEasyPdfFontUtil.getFontHeight(font, this.param.getFontSize()) * (1 - this.param.getFontScaleRatio());
        // 开启文本输入
        stream.beginText();
        // 如果渲染模式为斜体，则设置字体为斜体
        if (this.param.getRenderingMode().isItalic()) {
            // 设置斜体
            stream.setTextMatrix(new Matrix(this.param.getFontScaleRatio(), 0F, 0.2F, this.param.getFontScaleRatio(), beginX, beginY + textRise));
        }
        // 否则设置文本定位
        else {
            // 设置文本定位
            stream.setTextMatrix(new Matrix(this.param.getFontScaleRatio(), 0F, 0F, this.param.getFontScaleRatio(), beginX, beginY + textRise));
        }
        // 文本输入
        stream.showText(text);
        // 结束文本写入
        stream.endText();
    }

    /**
     * 添加文本下标
     *
     * @param font   pdfbox字体
     * @param page   pdf页面
     * @param stream 内容流
     * @param text   待写入文本
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void addTextSubscript(
            PDFont font,
            XEasyPdfPage page,
            PDPageContentStream stream,
            String text,
            float beginX,
            float beginY
    ) {
        // 开启文本输入
        stream.beginText();
        // 如果渲染模式为斜体，则设置字体为斜体
        if (this.param.getRenderingMode().isItalic()) {
            // 设置斜体
            stream.setTextMatrix(new Matrix(this.param.getFontScaleRatio(), 0F, 0.2F, this.param.getFontScaleRatio(), beginX, beginY));
        }
        // 否则设置文本定位
        else {
            // 设置文本定位
            stream.setTextMatrix(new Matrix(this.param.getFontScaleRatio(), 0F, 0F, this.param.getFontScaleRatio(), beginX, beginY));
        }
        // 文本输入
        stream.showText(text);
        // 结束文本写入
        stream.endText();
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
    @Override
    PDRectangle getRectangleForWrite(PDFont font, String text, float beginX, float beginY) {
        // 创建尺寸
        PDRectangle rectangle = new PDRectangle();
        // 设置起始X轴坐标
        rectangle.setLowerLeftX(beginX);
        // 设置起始Y轴坐标
        rectangle.setLowerLeftY(beginY);
        // 设置结束Y轴坐标
        rectangle.setUpperRightY(beginY + this.param.getFontHeight());
        // 设置结束X轴坐标为起始坐标+文本真实宽度
        rectangle.setUpperRightX(beginX + XEasyPdfTextUtil.getTextRealWidth(text, font, this.param.getFontSize() * this.param.getFontScaleRatio(), this.getCharacterSpacing()));
        // 返回尺寸
        return rectangle;
    }
}
