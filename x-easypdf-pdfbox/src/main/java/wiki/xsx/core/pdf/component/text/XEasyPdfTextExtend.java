package wiki.xsx.core.pdf.component.text;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.XEasyPdfPagingCondition;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * pdf文本扩展组件
 *
 * @author xsx
 * @date 2022/7/29
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
public class XEasyPdfTextExtend implements XEasyPdfComponent {

    private static final long serialVersionUID = -3542039487035427025L;

    /**
     * 文本参数
     */
    private final XEasyPdfTextExtendParam param = new XEasyPdfTextExtendParam();

    /**
     * 有参构造
     *
     * @param text 待写入文本
     */
    public XEasyPdfTextExtend(String text) {
        this.param.setText(text);
    }

    /**
     * 有参构造
     *
     * @param text       待写入文本
     * @param scriptText 待写入角标文本
     */
    public XEasyPdfTextExtend(String text, String scriptText) {
        this.param.setText(text);
        this.param.setScriptText(scriptText);
    }

    /**
     * 开启文本追加
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableTextAppend() {
        this.param.setIsTextAppend(Boolean.TRUE);
        return this;
    }

    /**
     * 开启居中样式（水平居中，垂直居中）
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 开启子组件
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableChildComponent() {
        this.param.setIsChildComponent(Boolean.TRUE);
        return this;
    }

    /**
     * 开启下划线
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableUnderline() {
        this.param.setIsUnderline(Boolean.TRUE);
        return this;
    }

    /**
     * 开启删除线
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableDeleteLine() {
        this.param.setIsDeleteLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启高亮
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableHighlight() {
        this.param.setIsHighlight(Boolean.TRUE);
        return this;
    }

    /**
     * 开启整行旋转
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableRotateLine() {
        this.param.setIsRotateLine(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回文本扩展组件
     */
    @Override
    public XEasyPdfTextExtend enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启自身样式
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend enableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭自身样式
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend disableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.FALSE);
        return this;
    }

    /**
     * 设置自动缩进
     *
     * @param indent 缩进值
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setAutoIndent(int indent) {
        this.param.setIndent(Math.abs(indent));
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setLeading(float leading) {
        this.param.setLeading(Math.abs(leading));
        return this;
    }

    /**
     * 设置文本间隔
     *
     * @param characterSpacing 文本间隔
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setCharacterSpacing(float characterSpacing) {
        this.param.setCharacterSpacing(Math.abs(characterSpacing));
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置高亮颜色
     *
     * @param highlightColor 高亮颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setHighlightColor(Color highlightColor) {
        if (highlightColor != null) {
            this.param.setHighlightColor(highlightColor);
        }
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @param underlineColor 下划线颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setUnderlineColor(Color underlineColor) {
        if (underlineColor != null) {
            this.param.setUnderlineColor(underlineColor);
        }
        return this;
    }

    /**
     * 设置下划线线宽
     *
     * @param underlineWidth 下划线线宽
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setUnderlineWidth(float underlineWidth) {
        this.param.setUnderlineWidth(Math.abs(underlineWidth));
        return this;
    }

    /**
     * 设置删除线颜色
     *
     * @param deleteLineColor 删除线颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setDeleteLineColor(Color deleteLineColor) {
        if (deleteLineColor != null) {
            this.param.setDeleteLineColor(deleteLineColor);
        }
        return this;
    }

    /**
     * 设置删除线线宽
     *
     * @param deleteLineWidth 删除线线宽
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setDeleteLineWidth(float deleteLineWidth) {
        this.param.setDeleteLineWidth(Math.abs(deleteLineWidth));
        return this;
    }

    /**
     * 设置超链接地址
     *
     * @param linkUrl 超链接地址
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setLink(String linkUrl) {
        this.param.setLinkUrl(linkUrl);
        return this;
    }

    /**
     * 设置评论
     *
     * @param comment 评论
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setComment(String comment) {
        this.param.setComment(comment);
        return this;
    }

    /**
     * 设置文本透明度
     *
     * @param alpha 文本透明度
     * @return 返回页面水印组件
     */
    public XEasyPdfTextExtend setAlpha(float alpha) {
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
    public XEasyPdfTextExtend setRadians(double radians) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setHorizontalStyle(XEasyPdfPositionStyle style) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setVerticalStyle(XEasyPdfPositionStyle style) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setSplitTextList(List<String> splitTextList) {
        if (splitTextList != null) {
            this.param.setSplitTextList(new ArrayList<>(splitTextList)).setSplitTemplateTextList(new ArrayList<>(splitTextList));
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param oldValue    待替换字符串
     * @param replacement 替换字符串
     * @return 返回返回文本扩展组件
     */
    public XEasyPdfTextExtend setReplaceCharacters(String oldValue, String replacement) {
        if (oldValue != null && replacement != null) {
            this.param.getReplaceCharacterMap().put(oldValue, replacement);
        }
        return this;
    }

    /**
     * 设置替换字符
     *
     * @param replaceMap 待替换字典
     * @return 返回返回文本扩展组件
     */
    public XEasyPdfTextExtend setReplaceCharacters(Map<String, String> replaceMap) {
        if (replaceMap != null && !replaceMap.isEmpty()) {
            this.param.getReplaceCharacterMap().putAll(replaceMap);
        }
        return this;
    }

    /**
     * 设置是否换行（影响下一个组件是否换行）
     *
     * @param isNewLine 是否换行
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setNewLine(boolean isNewLine) {
        this.param.setIsNewLine(isNewLine);
        return this;
    }

    /**
     * 设置是否分页检查
     *
     * @param isCheckPage 是否分页检查
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setCheckPage(boolean isCheckPage) {
        this.param.setCheckPage(isCheckPage);
        return this;
    }

    /**
     * 设置定位
     *
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回文本扩展组件
     */
    @Override
    public XEasyPdfTextExtend setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回文本扩展组件
     */
    @Override
    public XEasyPdfTextExtend setWidth(float width) {
        this.param.setMaxWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回文本扩展组件
     */
    @Override
    public XEasyPdfTextExtend setHeight(float height) {
        this.param.setMaxHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight 最大高度
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setMaxHeight(Float maxHeight) {
        this.param.setMaxHeight(maxHeight);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回文本扩展组件
     */
    @Override
    public XEasyPdfTextExtend setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置渲染模式
     *
     * @param renderingMode 渲染模式
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setRenderingMode(XEasypdfTextRenderingMode renderingMode) {
        if (renderingMode != null) {
            this.param.setRenderingMode(renderingMode);
        }
        return this;
    }

    /**
     * 设置分页条件
     *
     * @param pagingCondition 分页条件
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setPagingCondition(XEasyPdfPagingCondition pagingCondition) {
        this.param.setPagingCondition(pagingCondition);
        return this;
    }

    /**
     * 设置是否需要初始化
     *
     * @param needInitialize 是否需要初始化
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setNeedInitialize(boolean needInitialize) {
        this.param.setIsNeedInitialize(needInitialize);
        return this;
    }

    /**
     * 设置字体缩放比例
     *
     * @param fontScaleRatio 字体缩放比例（0.1~0.7之间）
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setFontScaleRatio(float fontScaleRatio) {
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
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setScriptType(XEasyPdfTextScriptType scriptType) {
        this.param.setScriptType(scriptType);
        return this;
    }

    /**
     * 设置角标文本
     *
     * @param scriptText 角标文本
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend setScriptType(String scriptText) {
        this.param.setScriptText(scriptText);
        return this;
    }

    /**
     * 替换占位符
     *
     * @param placeholder 占位符
     * @param value       新字符串
     * @return 返回文本扩展组件
     */
    public XEasyPdfTextExtend replaceAllPlaceholder(String placeholder, String value) {
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
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 绘制文本
        new XEasyPdfText(this.param).draw(document, page);
        // 如果角标文本不为空，则绘制角标文本
        if (this.param.getScriptText() != null) {
            // 重置文本参数
            this.param.setText(this.param.getScriptText()).setSplitTextList(null).setSplitTemplateTextList(null);
            // 绘制角标
            new XEasyPdfTextScript(this.param).draw(document, page);
        }
    }

    /**
     * 获取高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本高度
     */
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
    public float getWidth(XEasyPdfDocument document, XEasyPdfPage page) {
        return this.param.getWidth(document, page);
    }

    /**
     * 获取内容模式
     *
     * @return 返回内容模式
     */
    public ContentMode getContentMode() {
        return this.param.getContentMode();
    }

    /**
     * 获取Y轴起始坐标
     *
     * @return 返回Y轴起始坐标
     */
    float getBeginY() {
        return this.param.getBeginY();
    }

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    public float getMarginTop() {
        return this.param.getMarginTop();
    }

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    public float getMarginBottom() {
        return this.param.getMarginBottom();
    }

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    public float getMarginLeft() {
        return this.param.getMarginLeft();
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public float getMarginRight() {
        return this.param.getMarginRight();
    }

    /**
     * 获取待添加文本列表
     *
     * @return 返回待添加文本列表
     */
    public List<String> getSplitTextList() {
        return this.param.getSplitTextList();
    }

    /**
     * 获取字体大小
     *
     * @return 返回字体大小
     */
    public float getFontSize() {
        return this.param.getFontSize();
    }

    /**
     * 获取字体路径
     *
     * @return 返回字体路径
     */
    public String getFontPath() {
        return this.param.getFontPath();
    }

    /**
     * 获取行间距
     *
     * @return 返回行间距
     */
    public float getLeading() {
        return this.param.getLeading();
    }

    /**
     * 获取文本间隔
     *
     * @return 返回文本间隔
     */
    public float getCharacterSpacing() {
        return this.param.getCharacterSpacing();
    }

    /**
     * 获取透明度
     *
     * @return 返回透明度
     */
    public float getAlpha() {
        return this.param.getAlpha();
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
}
