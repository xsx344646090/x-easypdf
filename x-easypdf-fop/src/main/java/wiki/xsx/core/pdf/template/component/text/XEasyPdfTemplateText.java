package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplatePositionStyle;

import java.awt.*;

/**
 * pdf模板-文本组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/8/5
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
public class XEasyPdfTemplateText extends XEasyPdfTemplateTextBase {

    /**
     * 文本参数
     */
    private final XEasyPdfTemplateTextParam param = new XEasyPdfTemplateTextParam();

    /**
     * 设置文本
     *
     * @param text 文本
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setText(String text) {
        this.param.setText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param fontStyle 字体样式
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 设置水平样式
     *
     * @param style 水平样式
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setHorizontalStyle(XEasyPdfTemplatePositionStyle style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
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
        // 如果文本为空，则返回空元素
        if (this.param.getText() == null) {
            // 返回空元素
            return null;
        }
        // 初始化block元素
        Element block = this.initBlock(document, this.param);
        // 创建inline元素
        Element inline = this.createInline(document);
        // 添加inline元素
        block.appendChild(inline);
        // 返回block元素
        return block;
    }

    /**
     * 初始化
     *
     * @param param 文本基础参数
     * @return 返回pdf模板-文本组件
     */
    XEasyPdfTemplateText init(XEasyPdfTemplateTextBaseParam param) {
        this.param.init(param);
        return this;
    }

    /**
     * 创建inline元素
     *
     * @param document fo文档
     */
    private Element createInline(Document document) {
        // 创建inline元素
        Element inline = document.createElement(XEasyPdfTemplateTags.IN_LINE);
        // 如果字体名称不为空，则设置字体名称
        if (this.param.getFontFamily() != null) {
            // 设置字体名称
            inline.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, this.param.getFontFamily());
        }
        // 如果字体样式不为空，则设置字体样式
        if (this.param.getFontStyle() != null) {
            // 设置字体样式
            inline.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, this.param.getFontStyle());
        }
        // 如果字体大小不为空，则设置字体大小
        if (this.param.getFontSize() != null) {
            // 设置字体大小
            inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, this.param.getFontSize());
        }
        // 如果字体大小调整不为空，则设置字体大小调整
        if (this.param.getFontSizeAdjust() != null) {
            // 设置字体大小调整
            inline.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, this.param.getFontSizeAdjust());
        }
        // 如果字体重量不为空，则设置字体重量
        if (this.param.getFontWeight() != null) {
            // 设置字体重量
            inline.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, this.param.getFontWeight());
        }
        // 如果字体颜色不为空，则设置字体颜色
        if (this.param.getFontColor() != null) {
            // 获取字体颜色
            Color fontColor = this.param.getFontColor();
            // 设置字体颜色
            inline.setAttribute(
                    XEasyPdfTemplateAttributes.COLOR,
                    String.join(
                            "",
                            "rgb(",
                            String.valueOf(fontColor.getRed()),
                            ",",
                            String.valueOf(fontColor.getGreen()),
                            ",",
                            String.valueOf(fontColor.getBlue()),
                            ")"
                    )
            );
        }
        // 设置文本
        inline.setTextContent(this.param.getText());
        // 返回inline元素
        return inline;
    }
}
