package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
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
public class XEasyPdfTemplateTextExtend implements XEasyPdfTemplateComponent {

    /**
     * 文本扩展参数
     */
    private final XEasyPdfTemplateTextExtendParam param = new XEasyPdfTemplateTextExtendParam();

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend setFontColor(Color fontColor) {
        this.param.setFontColor(fontColor);
        return this;
    }

    /**
     * 添加文本组件
     *
     * @param texts 文本组件列表
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend addTexts(XEasyPdfTemplateText... texts) {
        if (texts != null) {
            Collections.addAll(this.param.getTextList(), texts);
        }
        return this;
    }

    /**
     * 添加文本组件
     *
     * @param textList 文本组件列表
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend addTexts(List<XEasyPdfTemplateText> textList) {
        if (textList != null) {
            this.param.getTextList().addAll(textList);
        }
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateTextExtend enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
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
        Element block = document.createElement(XEasyPdfTemplateConstants.TagName.BLOCK);
        // 如果高度不为空，则设置行内高度
        if (this.param.getHeight() != null) {
            // 设置行内高度
            block.setAttribute("line-height", this.param.getHeight());
        }
        // 遍历文本组件
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
