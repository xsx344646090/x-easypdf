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
     * 设置id
     *
     * @param id id
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setId(String id) {
        this.param.setId(id);
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
     * <p>left：居左</p>
     * <p>center：居中</p>
     * <p>right：居右</p>
     *
     * @param style 水平样式
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setHorizontalStyle(String style) {
        this.param.setHorizontalStyle(style);
        return this;
    }

    /**
     * 设置分页符-前
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之前强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之前强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakBefore 分页符
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setBreakBefore(String breakBefore) {
        this.param.setBreakBefore(breakBefore);
        return this;
    }

    /**
     * 设置分页符-后
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之后强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之后强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakAfter 分页符
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回图像组件
     */
    public XEasyPdfTemplateImage enableKeepWithNext() {
        this.param.setKeepWithNext("always");
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
        // 设置图像宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.CONTENT_WIDTH, v.intern().toLowerCase()));
        // 设置图像高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> externalGraphic.setAttribute(XEasyPdfTemplateAttributes.CONTENT_HEIGHT, v.intern().toLowerCase()));
        // 设置图像路径
        externalGraphic.setAttribute(XEasyPdfTemplateAttributes.SRC, this.param.getIsRemote() != null ? this.param.getPath().intern() : new File(this.param.getPath()).toURI().getPath().intern());
        // 返回externalGraphic元素
        return externalGraphic;
    }
}
