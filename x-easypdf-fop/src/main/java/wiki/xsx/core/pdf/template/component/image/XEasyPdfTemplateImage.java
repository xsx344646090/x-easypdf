package wiki.xsx.core.pdf.template.component.image;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

import java.io.File;
import java.util.Optional;

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
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回图像组件
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
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setPath(String path) {
        this.param.setPath(path);
        return this;
    }

    /**
     * 设置水平样式
     * <p>LEFT：居左</p>
     * <p>CENTER：居中</p>
     * <p>RIGHT：居右</p>
     *
     * @param style 水平样式
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 开启远程
     *
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage enableRemote() {
        this.param.setIsRemote(Boolean.TRUE);
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage enableBorder() {
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
        // 如果图像路径为空，则返回空元素
        if (this.param.getPath() == null) {
            // 返回空元素
            return null;
        }
        // 创建block元素
        Element block = this.createBlockElement(document, this.param);
        // 添加externalGraphic元素
        block.appendChild(this.createExternalGraphic(document));
        // 返回block元素
        return block;
    }

    /**
     * 创建externalGraphic元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createExternalGraphic(Document document) {
        // 创建externalGraphic元素
        Element externalGraphic = document.createElement(XEasyPdfTemplateTags.EXTERNAL_GRAPHIC);
        // 设置上下左右填充
        Optional.ofNullable(param.getPadding()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.PADDING, v.intern()));
        // 设置上填充
        Optional.ofNullable(param.getPaddingTop()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.PADDING_TOP, v.intern()));
        // 设置下填充
        Optional.ofNullable(param.getPaddingBottom()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.PADDING_BOTTOM, v.intern()));
        // 设置左填充
        Optional.ofNullable(param.getPaddingLeft()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.PADDING_LEFT, v.intern()));
        // 设置右填充
        Optional.ofNullable(param.getPaddingRight()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.PADDING_RIGHT, v.intern()));
        // 设置图像宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.CONTENT_WIDTH, v.intern()));
        // 设置图像高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.CONTENT_HEIGHT, v.intern()));
        // 设置图像路径
        externalGraphic.setAttribute(XEasyPdfTemplateAttributes.SRC, this.param.getIsRemote() != null ? this.param.getPath().intern() : new File(this.param.getPath()).toURI().getPath().intern());
        // 返回externalGraphic元素
        return externalGraphic;
    }
}
