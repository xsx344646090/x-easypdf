package wiki.xsx.core.pdf.template.component.image;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTextPositionStyle;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

import java.io.File;

/**
 * pdf模板-图像组件
 * <p>fo:external-graphic</p>
 *
 * @author xsx
 * @date 2022/8/9
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplateImage implements XEasyPdfTemplateComponent {

    /**
     * 图像参数
     */
    private final XEasyPdfTemplateImageParam param = new XEasyPdfTemplateImageParam();

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置图像路径
     * <p>注：当开启远程时，此路径为网络地址</p>
     *
     * @param path 图像路径
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage setPath(String path) {
        this.param.setPath(path);
        return this;
    }

    /**
     * 设置水平样式
     *
     * @param style 水平样式
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage setHorizontalStyle(XEasyPdfTemplateTextPositionStyle style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置垂直样式
     *
     * @param style 垂直样式
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage setVerticalStyle(XEasyPdfTemplateTextPositionStyle style) {
        this.param.setVerticalStyle(style);
        return this;
    }

    /**
     * 开启远程
     *
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage enableRemote() {
        this.param.setIsRemote(Boolean.TRUE);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回pdf模板-图像组件
     */
    public XEasyPdfTemplateImage enableBorder() {
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
        if (this.param.getPath() == null) {
            return null;
        }
        // 创建block节点
        Element block = this.createBlock(document);
        Element externalGraphic = this.createExternalGraphic(document);
        block.appendChild(externalGraphic);
        // 返回block节点
        return block;
    }

    /**
     * 创建Block节点
     *
     * @param document fo文档
     * @return 返回节点
     */
    private Element createBlock(Document document) {
        // 创建block节点
        Element block = document.createElement(XEasyPdfTemplateConstants.TagName.BLOCK);
//        // 如果高度不为空，则设置行内高度
//        if (this.param.getHeight() != null) {
//            // 设置行内高度
//            block.setAttribute("line-height", (this.param.getHeight()));
//        }
        // 如果开启边框，则添加边框
        if (this.param.getHasBorder() != null) {
            // 添加边框
            block.setAttribute("border", "1px solid black");
        }
        // 如果水平样式不为空，则设置水平样式
        if (this.param.getHorizontalStyle() != null) {
            // 获取水平样式
            XEasyPdfTemplateTextPositionStyle style = this.param.getHorizontalStyle();
            // 如果为水平样式，则设置水平样式
            if (style.isHorizontalStyle()) {
                // 设置水平样式
                block.setAttribute(style.isCenter() ? "text-align" : style.getKey(), style.getValue());
            }
        }
        // 返回block节点
        return block;
    }

    private Element createExternalGraphic(Document document) {
        // 创建externalGraphic节点
        Element externalGraphic = document.createElement(XEasyPdfTemplateConstants.TagName.EXTERNAL_GRAPHIC);
        if (this.param.getWidth() != null) {
            // 设置垂直样式
            externalGraphic.setAttribute("content-width", this.param.getWidth());
        }
        if (this.param.getHeight() != null) {
            // 设置垂直样式
            externalGraphic.setAttribute("content-height", this.param.getHeight());
        }
        externalGraphic.setAttribute("src", this.param.getIsRemote() != null ? this.param.getPath() : new File(this.param.getPath()).toURI().getPath());
        return externalGraphic;
    }
}
