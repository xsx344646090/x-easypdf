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
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * pdf文本组件
 *
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
@EqualsAndHashCode(callSuper = true)
public class XEasyPdfText extends XEasyPdfTextBase {

    private static final long serialVersionUID = -4199419015054023227L;

    /**
     * 文本参数
     */
    private XEasyPdfTextParam param = new XEasyPdfTextParam();

    /**
     * 有参构造
     *
     * @param param 文本参数
     */
    XEasyPdfText(XEasyPdfTextParam param) {
        this.param = param;
    }

    /**
     * 有参构造
     *
     * @param text 待输入文本
     */
    public XEasyPdfText(String text) {
        this.param.setText(text);
    }

    /**
     * 有参构造
     *
     * @param textList 待输入文本列表
     */
    public XEasyPdfText(List<String> textList) {
        if (textList != null) {
            this.param.setSplitTextList(new ArrayList<>(textList)).setSplitTemplateTextList(new ArrayList<>(textList));
        }
    }

    /**
     * 有参构造
     *
     * @param fontSize 字体大小
     * @param text     待输入文本
     */
    public XEasyPdfText(float fontSize, String text) {
        this.param.setFontSize(Math.abs(fontSize)).setText(text);
    }

    /**
     * 有参构造
     *
     * @param fontSize 字体大小
     * @param textList 待输入文本列表
     */
    public XEasyPdfText(float fontSize, List<String> textList) {
        this.param.setFontSize(Math.abs(fontSize));
        if (textList != null) {
            this.param.setSplitTextList(new ArrayList<>(textList)).setSplitTemplateTextList(new ArrayList<>(textList));
        }
    }

    /**
     * 开启文本追加
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableTextAppend() {
        this.param.setIsTextAppend(Boolean.TRUE);
        return this;
    }

    /**
     * 开启居中样式（水平居中，垂直居中）
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 开启子组件
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableChildComponent() {
        this.param.setIsChildComponent(Boolean.TRUE);
        return this;
    }

    /**
     * 开启下划线
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableUnderline() {
        this.param.setIsUnderline(Boolean.TRUE);
        return this;
    }

    /**
     * 开启删除线
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableDeleteLine() {
        this.param.setIsDeleteLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启高亮
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableHighlight() {
        this.param.setIsHighlight(Boolean.TRUE);
        return this;
    }

    /**
     * 开启整行旋转
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableRotateLine() {
        this.param.setIsRotateLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自身样式
     *
     * @return 返回文本组件
     */
    public XEasyPdfText enableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭自身样式
     *
     * @return 返回文本组件
     */
    public XEasyPdfText disableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.FALSE);
        return this;
    }

    /**
     * 设置自动缩进
     *
     * @param indent 缩进值
     * @return 返回文本组件
     */
    public XEasyPdfText setAutoIndent(int indent) {
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
     * @return 返回文本组件
     */
    public XEasyPdfText setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回文本组件
     */
    public XEasyPdfText setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本组件
     */
    public XEasyPdfText setLeading(float leading) {
        this.param.setLeading(Math.abs(leading));
        return this;
    }

    /**
     * 设置文本间隔
     *
     * @param characterSpacing 文本间隔
     * @return 返回文本组件
     */
    public XEasyPdfText setCharacterSpacing(float characterSpacing) {
        this.param.setCharacterSpacing(Math.abs(characterSpacing));
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回文本组件
     */
    public XEasyPdfText setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回文本组件
     */
    public XEasyPdfText setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本组件
     */
    public XEasyPdfText setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置高亮颜色
     *
     * @param highlightColor 高亮颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setHighlightColor(Color highlightColor) {
        if (highlightColor != null) {
            this.param.setHighlightColor(highlightColor);
        }
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param underlineColor 下划线颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setUnderlineColor(Color underlineColor) {
        if (underlineColor != null) {
            this.param.setUnderlineColor(underlineColor);
        }
        return this;
    }

    /**
     * 设置下划线线宽
     *
     * @param underlineWidth 下划线线宽
     * @return 返回文本组件
     */
    public XEasyPdfText setUnderlineWidth(float underlineWidth) {
        this.param.setUnderlineWidth(Math.abs(underlineWidth));
        return this;
    }

    /**
     * 设置删除线颜色
     *
     * @param deleteLineColor 删除线颜色
     * @return 返回文本组件
     */
    public XEasyPdfText setDeleteLineColor(Color deleteLineColor) {
        if (deleteLineColor != null) {
            this.param.setDeleteLineColor(deleteLineColor);
        }
        return this;
    }

    /**
     * 设置删除线线宽
     *
     * @param deleteLineWidth 删除线线宽
     * @return 返回文本组件
     */
    public XEasyPdfText setDeleteLineWidth(float deleteLineWidth) {
        this.param.setDeleteLineWidth(Math.abs(deleteLineWidth));
        return this;
    }

    /**
     * 设置超链接地址
     *
     * @param linkUrl 超链接地址
     * @return 返回文本组件
     */
    public XEasyPdfText setLink(String linkUrl) {
        this.param.setLinkUrl(linkUrl);
        return this;
    }

    /**
     * 设置评论
     *
     * @param comment 评论
     * @return 返回文本组件
     */
    public XEasyPdfText setComment(String comment) {
        this.param.setComment(comment);
        return this;
    }

    /**
     * 设置文本透明度
     *
     * @param alpha 文本透明度
     * @return 返回页面水印组件
     */
    public XEasyPdfText setAlpha(float alpha) {
        if (alpha >= 0 && alpha <= 1) {
            this.param.setAlpha(alpha);
        }
        return this;
    }

    /**
     * 设置文本弧度(逆时针旋转)
     *
     * @param radians 文本弧度
     * @return 返回文本扩展组件
     */
    public XEasyPdfText setRadians(double radians) {
        final int min = 0;
        final int max = 360;
        if (radians % max != min) {
            radians = radians % max;
            if (radians < min) {
                radians += max;
            }
            this.param.setRadians(radians);
        }
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     *
     * @param style 样式
     * @return 返回文本组件
     */
    public XEasyPdfText setHorizontalStyle(XEasyPdfPositionStyle style) {
        // 如果样式不为空，则设置样式
        if (style != null) {
            // 检查水平样式
            XEasyPdfPositionStyle.checkHorizontalStyle(style);
            // 设置全局水平样式
            this.param.setHorizontalStyle(style);
        }
        return this;
    }

    /**
     * 设置垂直样式（居上、居中、居下）
     *
     * @param style 样式
     * @return 返回文本组件
     */
    public XEasyPdfText setVerticalStyle(XEasyPdfPositionStyle style) {
        // 如果样式不为空，则设置样式
        if (style != null) {
            // 检查水平样式
            XEasyPdfPositionStyle.checkVerticalStyle(style);
            // 设置全局水平样式
            this.param.setVerticalStyle(style);
        }
        return this;
    }

    /**
     * 设置拆分后的待添加文本列表
     *
     * @param splitTextList 拆分后的待添加文本列表
     * @return 返回文本组件
     */
    public XEasyPdfText setSplitTextList(List<String> splitTextList) {
        if (splitTextList == null) {
            this.param.setSplitTextList(null).setSplitTemplateTextList(null);
        } else {
            this.param.setSplitTextList(new ArrayList<>(splitTextList)).setSplitTemplateTextList(new ArrayList<>(splitTextList));
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param oldValue    待替换字符串
     * @param replacement 替换字符串
     * @return 返回返回文本组件
     */
    public XEasyPdfText setReplaceCharacters(String oldValue, String replacement) {
        if (oldValue != null && replacement != null) {
            this.param.getReplaceCharacterMap().put(oldValue, replacement);
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param replaceMap 待替换字典
     * @return 返回返回文本组件
     */
    public XEasyPdfText setReplaceCharacters(Map<String, String> replaceMap) {
        if (replaceMap != null && !replaceMap.isEmpty()) {
            this.param.getReplaceCharacterMap().putAll(replaceMap);
        }
        return this;
    }

    /**
     * 设置是否换行（影响下一个组件是否换行）
     *
     * @param isNewLine 是否换行
     * @return 返回文本组件
     */
    public XEasyPdfText setNewLine(boolean isNewLine) {
        this.param.setIsNewLine(isNewLine);
        return this;
    }

    /**
     * 设置是否分页检查
     *
     * @param isCheckPage 是否分页检查
     * @return 返回文本组件
     */
    public XEasyPdfText setCheckPage(boolean isCheckPage) {
        this.param.setCheckPage(isCheckPage);
        return this;
    }

    /**
     * 设置定位
     *
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度
     *
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
     *
     * @param height 高度
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setHeight(float height) {
        this.param.setMaxHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight 最大高度
     * @return 返回文本组件
     */
    public XEasyPdfText setMaxHeight(Float maxHeight) {
        this.param.setMaxHeight(maxHeight);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置渲染模式
     *
     * @param renderingMode 渲染模式
     * @return 返回文本组件
     */
    public XEasyPdfText setRenderingMode(XEasypdfTextRenderingMode renderingMode) {
        if (renderingMode != null) {
            this.param.setRenderingMode(renderingMode);
        }
        return this;
    }

    /**
     * 设置分页条件
     *
     * @param pagingCondition 分页条件
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setPagingCondition(XEasyPdfPagingCondition pagingCondition) {
        this.param.setPagingCondition(pagingCondition);
        return this;
    }

    /**
     * 设置是否需要初始化
     *
     * @param needInitialize 是否需要初始化
     * @return 返回文本组件
     */
    @Override
    public XEasyPdfText setNeedInitialize(boolean needInitialize) {
        this.param.setIsNeedInitialize(needInitialize);
        return this;
    }

    /**
     * 替换占位符
     *
     * @param placeholder 占位符
     * @param value       新字符串
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
    public float getAlpha() {
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
        // 如果文本弧度大于0，则进行文本旋转
        if (this.param.getRadians() > 0) {
            // 如果开启整行旋转，则整行旋转
            if (this.param.getIsRotateLine()) {
                // 开启文本输入
                stream.beginText();
                // 设置文本弧度
                stream.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(this.param.getRadians()), beginX, beginY));
                // 文本输入
                stream.showText(text);
                // 结束文本写入
                stream.endText();
            }
            // 否则单字符旋转
            else {
                // 当前行x轴坐标
                float x = beginX;
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
                    stream.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(this.param.getRadians()), x, beginY));
                    // 文本输入
                    stream.showText(textTemp);
                    // 结束文本写入
                    stream.endText();
                    // 重置当前行x轴坐标， x轴坐标 = x轴坐标 + 文本宽度
                    x = x + XEasyPdfTextUtil.getTextRealWidth(textTemp, font, this.param.getFontSize(), this.param.getCharacterSpacing());
                }
            }
        }
        // 否则正常文本输入
        else {
            // 开启文本输入
            stream.beginText();
            // 设置斜体
            this.setItalic(stream, beginX, beginY);
            // 文本输入
            stream.showText(text);
            // 结束文本写入
            stream.endText();
        }
        // 重置颜色为页面背景色
        stream.setStrokingColor(page.getBackgroundColor());
        // 重置颜色为页面背景色
        stream.setNonStrokingColor(page.getBackgroundColor());
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
        // 如果文本弧度大于0，则结束X轴坐标为起始坐标+字体大小*字符数
        if (this.param.getRadians() > 0) {
            // 设置结束X轴坐标为起始坐标+字体大小*字符数
            rectangle.setUpperRightX(beginX + this.param.getFontSize() * text.length());
        }
        // 文本弧度为0，则结束X轴坐标为起始坐标+文本真实宽度
        else {
            // 设置结束X轴坐标为起始坐标+文本真实宽度
            rectangle.setUpperRightX(beginX + XEasyPdfTextUtil.getTextRealWidth(text, font, this.param.getFontSize(), this.getCharacterSpacing()));
        }
        return rectangle;
    }

    /**
     * 设置斜体
     *
     * @param stream 内容流
     * @param beginX X轴坐标
     * @param beginY Y轴坐标
     */
    @SneakyThrows
    private void setItalic(PDPageContentStream stream, float beginX, float beginY) {
        // 如果渲染模式为斜体，则设置字体为斜体
        if (this.param.getRenderingMode().isItalic()) {
            // 设置斜体
            stream.setTextMatrix(new Matrix(1, 0, 0.2F, 1, beginX, beginY));
        }
        // 否则设置文本定位
        else {
            // 设置文本定位
            stream.newLineAtOffset(beginX, beginY);
        }
    }
}
