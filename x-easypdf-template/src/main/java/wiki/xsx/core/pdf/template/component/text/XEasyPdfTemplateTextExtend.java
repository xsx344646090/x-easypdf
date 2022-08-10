package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTextPositionStyle;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

import java.awt.*;
import java.util.Collections;
import java.util.List;

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
public class XEasyPdfTemplateTextExtend extends XEasyPdfTemplateTextBase implements XEasyPdfTemplateComponent {

    /**
     * 文本扩展参数
     */
    private final XEasyPdfTemplateTextExtendParam param = new XEasyPdfTemplateTextExtendParam();

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLeading(String leading) {
        this.param.setLeading(leading);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param letterSpacing 字符间距
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setLetterSpacing(String letterSpacing) {
        this.param.setLetterSpacing(letterSpacing);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 设置水平样式
     *
     * @param style 水平样式
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend setHorizontalStyle(XEasyPdfTemplateTextPositionStyle style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 添加文本扩展组件
     *
     * @param texts 文本组件列表
     * @return 返回pdf模板-文本扩展组件
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
     * @return 返回pdf模板-文本扩展组件
     */
    public XEasyPdfTemplateTextExtend addTexts(List<XEasyPdfTemplateText> textList) {
        if (textList != null) {
            this.param.getTextList().addAll(textList);
        }
        return this;
    }

    /**
     * 创建节点
     *
     * @param document fo文档
     * @return 返回节点
     */
    @Override
    public Node createNode(Document document) {
        // 如果文本为空，则返回空节点
        if (this.param.getTextList() == null) {
            // 返回空节点
            return null;
        }
        // 创建block节点
        Element block = this.createBlock(document, this.param);
        // 遍历文本扩展组件
        for (XEasyPdfTemplateText text : this.param.getTextList()) {
            // 初始化并创建节点
            Node node = text.init(this.param).createNode(document);
            // 如果节点不为空，则添加节点
            if (node!=null) {
                // 添加节点
                block.appendChild(node.getFirstChild());
            }
        }
        // 返回block节点
        return block;
    }
}
