package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-文本扩展组件
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
public class XEasyPdfTemplateTextExtend extends XEasyPdfTemplateTextBase {

    /**
     * 文本扩展参数
     */
    private final XEasyPdfTemplateTextExtendParam param = new XEasyPdfTemplateTextExtendParam();

    /**
     * 设置初始化容量
     *
     * @param initialCapacity 设置初始化容量
     * @return 返回文本扩展组件
     */
    private XEasyPdfTemplateTextExtend setInitialCapacity(int initialCapacity) {
        this.param.setTextList(new ArrayList<>(initialCapacity));
        return this;
    }

    /**
     * 设置文本语言
     *
     * @param language 语言
     * @return 返回文本扩展组件
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    public XEasyPdfTemplateTextExtend setLanguage(String language) {
        this.param.setLanguage(language);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 设置水平样式
     * <p>LEFT：居左</p>
     * <p>CENTER：居中</p>
     * <p>RIGHT：居右</p>
     *
     * @param style 水平样式
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 添加文本扩展组件
     *
     * @param texts 文本组件列表
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend addTexts(XEasyPdfTemplateText... texts) {
        if (texts != null) {
            Collections.addAll(this.param.getTextList(), texts);
        }
        return this;
    }

    /**
     * 添加文本扩展组件
     *
     * @param textList 文本组件列表
     * @return 返回文本扩展组件
     */
    public XEasyPdfTemplateTextExtend addTexts(List<XEasyPdfTemplateText> textList) {
        if (textList != null) {
            this.param.getTextList().addAll(textList);
        }
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
        if (this.param.getTextList() == null) {
            // 返回空元素
            return null;
        }
        // 初始化block元素
        Element block = this.initBlock(document, this.param);
        // 添加遍历子元素
        this.param.getTextList().forEach(
                v -> Optional.ofNullable(v.init(this.param).createElement(document)).ifPresent(
                        // 添加元素
                        e -> block.appendChild(e.getFirstChild())
                )
        );
        // 返回block元素
        return block;
    }
}
