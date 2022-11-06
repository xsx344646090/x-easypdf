package wiki.xsx.core.pdf.template.bookmark;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-书签组件
 *
 * @author xsx
 * @date 2022/11/2
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
public class XEasyPdfTemplateBookmark implements XEasyPdfTemplateBookmarkComponent {

    /**
     * 书签参数
     */
    private final XEasyPdfTemplateBookmarkParam param = new XEasyPdfTemplateBookmarkParam();

    /**
     * 设置子书签初始化容量
     *
     * @param initialCapacity 初始化容量
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setInitialCapacity(int initialCapacity) {
        this.param.setChildren(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置内部地址
     *
     * @param destination 地址
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setInternalDestination(String destination) {
        this.param.setInternalDestination(destination);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setTitle(String title) {
        this.param.setTitle(title);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     * <p>normal：正常</p>
     * <p>oblique：斜体</p>
     * <p>italic：斜体</p>
     * <p>backslant：斜体</p>
     *
     * @param fontStyle 字体样式
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体重量
     * <p>normal：正常（400）</p>
     * <p>bold：粗体（700）</p>
     * <p>bolder：加粗（900）</p>
     * <p>lighter：细体（100）</p>
     *
     * @param fontWeight 字体重量
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 添加子书签
     *
     * @param bookmarks 书签
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark addChild(XEasyPdfTemplateBookmark... bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(v -> Collections.addAll(this.param.getChildren(), v));
        return this;
    }

    /**
     * 添加子书签
     *
     * @param bookmarks 书签
     * @return 返回书签组件
     */
    public XEasyPdfTemplateBookmark addChild(List<XEasyPdfTemplateBookmark> bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(this.param.getChildren()::addAll);
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    @Override
    public Element createElement(Document document) {
        // 创建bookmark元素
        Element bookmark = document.createElement(XEasyPdfTemplateTags.BOOKMARK);
        // 设置内部地址
        Optional.ofNullable(this.param.getInternalDestination()).ifPresent(v -> bookmark.setAttribute(XEasyPdfTemplateAttributes.INTERNAL_DESTINATION, v.intern()));
        // 添加标题
        Optional.ofNullable(this.param.getTitle()).ifPresent(v -> bookmark.appendChild(this.createTitle(document)));
        // 添加组件
        Optional.ofNullable(this.param.getChildren()).ifPresent(v -> v.forEach(e -> bookmark.appendChild(e.createElement(document))));
        // 返回bookmark元素
        return bookmark;
    }

    /**
     * 创建title元素
     *
     * @param document fo文档
     * @return 返回title元素
     */
    private Element createTitle(Document document) {
        // 创建title元素
        Element title = document.createElement(XEasyPdfTemplateTags.BOOKMARK_TITLE);
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> title.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern().toLowerCase()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> title.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> title.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> title.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> title.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getFontColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(title, v));
        // 设置标题
        title.setTextContent(this.param.getTitle());
        // 返回title元素
        return title;
    }
}
