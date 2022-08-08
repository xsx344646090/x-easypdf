package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

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
public class XEasyPdfTemplateText implements XEasyPdfTemplateComponent {

    /**
     * 文本参数
     */
    private final XEasyPdfTemplateTextParam param = new XEasyPdfTemplateTextParam();

    /**
     * 无参构造
     */
    public XEasyPdfTemplateText() {
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

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
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-文本组件
     */
    public XEasyPdfTemplateText enableBorder() {
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
        if (this.param.getText() == null) {
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
        // 创建inline节点
        Element inline = document.createElement(XEasyPdfTemplateConstants.TagName.IN_LINE);
        // 如果开启边框，则添加边框
        if (this.param.getHasBorder() != null) {
            // 添加边框
            inline.setAttribute("border", "1px solid black");
        }
        // 如果字体名称不为空，则设置字体名称
        if (this.param.getFontFamily() != null) {
            // 设置字体名称
            inline.setAttribute("font-family", this.param.getFontFamily());
        }
        // 如果字体大小不为空，则设置字体大小
        if (this.param.getFontSize() != null) {
            // 设置字体大小
            inline.setAttribute("font-size", this.param.getFontSize());
        }
        // 如果字体大小调整不为空，则设置字体大小调整
        if (this.param.getFontSizeAdjust() != null) {
            // 设置字体大小调整
            inline.setAttribute("font-size-adjust", this.param.getFontSizeAdjust());
        }
        // 如果字体颜色不为空，则设置字体颜色
        if (this.param.getFontColor() != null) {
            // 获取字体颜色
            Color fontColor = this.param.getFontColor();
            // 设置字体颜色
            inline.setAttribute(
                    "color",
                    XEasyPdfTextUtil.join(
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
        // 添加inline节点
        block.appendChild(inline);
        // 返回block节点
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
     * 获取文本参数
     *
     * @return 返回文本参数
     */
    XEasyPdfTemplateTextParam getParam() {
        return this.param;
    }
}
