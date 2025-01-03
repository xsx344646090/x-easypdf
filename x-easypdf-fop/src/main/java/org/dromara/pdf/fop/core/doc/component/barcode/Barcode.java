package org.dromara.pdf.fop.core.doc.component.barcode;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-条形码组件
 * <p>fo:barcode</p>
 *
 * @author xsx
 * @date 2022/10/18
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Barcode implements Component {

    /**
     * 条形码参数
     */
    private final BarcodeParam param = new BarcodeParam();

    /**
     * 设置上下左右边距
     *
     * @param margin 边距
     * @return 返回条形码组件
     */
    public Barcode setMargin(String margin) {
        this.param.setMargin(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回条形码组件
     */
    public Barcode setMarginTop(String marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回条形码组件
     */
    public Barcode setMarginBottom(String marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回条形码组件
     */
    public Barcode setMarginLeft(String marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param paddingRight 右边距
     * @return 返回条形码组件
     */
    public Barcode setMarginRight(String paddingRight) {
        this.param.setMarginRight(paddingRight);
        return this;
    }

    /**
     * 设置上下左右填充
     *
     * @param padding 填充
     * @return 返回条形码组件
     */
    public Barcode setPadding(String padding) {
        this.param.setPadding(padding);
        return this;
    }

    /**
     * 设置上填充
     *
     * @param paddingTop 上填充
     * @return 返回条形码组件
     */
    public Barcode setPaddingTop(String paddingTop) {
        this.param.setPaddingTop(paddingTop);
        return this;
    }

    /**
     * 设置下填充
     *
     * @param paddingBottom 下填充
     * @return 返回条形码组件
     */
    public Barcode setPaddingBottom(String paddingBottom) {
        this.param.setPaddingBottom(paddingBottom);
        return this;
    }

    /**
     * 设置左填充
     *
     * @param paddingLeft 左填充
     * @return 返回条形码组件
     */
    public Barcode setPaddingLeft(String paddingLeft) {
        this.param.setPaddingLeft(paddingLeft);
        return this;
    }

    /**
     * 设置右填充
     *
     * @param paddingRight 右填充
     * @return 返回条形码组件
     */
    public Barcode setPaddingRight(String paddingRight) {
        this.param.setPaddingRight(paddingRight);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回条形码组件
     */
    public Barcode setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置条形码类型
     * <p>一维码：</p>
     * <p>codabar：库德巴码</p>
     * <p>code_39：标准39码</p>
     * <p>code_93：标准93码</p>
     * <p>code_128：标准128码</p>
     * <p>ean_8：缩短版国际商品条码</p>
     * <p>ean_13：标准版国际商品条码</p>
     * <p>itf：交叉码</p>
     * <p>upc_a：美国商品码a</p>
     * <p>upc_e：美国商品码e</p>
     * <p>upc_ean_extension：美国商品码扩展码</p>
     * <p>二维码：</p>
     * <p>qr_code：qr码</p>
     * <p>aztec：阿兹特克码</p>
     * <p>data_matrix：dm码</p>
     * <p>maxi_code：maxi码</p>
     * <p>pdf_417：pdf-417码</p>
     * <p>rss_14：rss-14码</p>
     * <p>rss_expanded：rss扩展码</p>
     *
     * @param type 类型
     * @return 返回条形码组件
     */
    public Barcode setType(String type) {
        this.param.setType(type);
        return this;
    }

    /**
     * 设置条形码缩放比例（实际比例）
     *
     * @param scaleRate 缩放比例
     * @return 返回条形码组件
     */
    public Barcode setScaleRate(String scaleRate) {
        this.param.setScaleRate(scaleRate);
        return this;
    }

    /**
     * 设置条形码旋转弧度
     *
     * @param radians 旋转弧度
     * @return 返回条形码组件
     */
    public Barcode setRadians(String radians) {
        this.param.setRadians(radians);
        return this;
    }

    /**
     * 设置条形码边距
     *
     * @param codeMargin 条形码边距
     * @return 返回条形码组件
     */
    public Barcode setCodeMargin(String codeMargin) {
        this.param.setCodeMargin(codeMargin);
        return this;
    }

    /**
     * 设置条形码纠错级别
     *
     * @param errorLevel 纠错级别
     * @return 返回条形码组件
     */
    public Barcode setErrorLevel(String errorLevel) {
        this.param.setErrorLevel(errorLevel);
        return this;
    }

    /**
     * 设置条形码图像宽度
     *
     * @param width 图像宽度
     * @return 返回条形码组件
     */
    public Barcode setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置条形码图像高度
     *
     * @param height 图像高度
     * @return 返回条形码组件
     */
    public Barcode setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置条形码内容
     *
     * @param content 内容
     * @return 返回条形码组件
     */
    public Barcode setContent(String content) {
        this.param.setContent(content);
        return this;
    }

    /**
     * 设置条形码前景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param onColor 前景颜色
     * @return 返回条形码组件
     */
    public Barcode setOnColor(String onColor) {
        this.param.setOnColor(onColor);
        return this;
    }

    /**
     * 设置条形码背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param offColor 背景颜色
     * @return 返回条形码组件
     */
    public Barcode setOffColor(String offColor) {
        this.param.setOffColor(offColor);
        return this;
    }

    /**
     * 设置条形码文字
     *
     * @param words 文字
     * @return 返回条形码组件
     */
    public Barcode setWords(String words) {
        this.param.setWords(words);
        return this;
    }

    /**
     * 设置条形码文字颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param wordsColor 文字颜色
     * @return 返回条形码组件
     */
    public Barcode setWordsColor(String wordsColor) {
        this.param.setWordsColor(wordsColor);
        return this;
    }

    /**
     * 设置条形码文字名称
     *
     * @param wordsFamily 文字名称
     * @return 返回条形码组件
     */
    public Barcode setWordsFamily(String wordsFamily) {
        this.param.setWordsFamily(wordsFamily);
        return this;
    }

    /**
     * 设置条形码文字样式
     * <p>normal：正常</p>
     * <p>bold：粗体</p>
     * <p>bold_italic：粗体斜体</p>
     * <p>italic：斜体</p>
     *
     * @param wordsStyle 文字样式
     * @return 返回条形码组件
     */
    public Barcode setWordsStyle(String wordsStyle) {
        this.param.setWordsStyle(wordsStyle);
        return this;
    }

    /**
     * 设置条形码文字大小
     *
     * @param wordsSize 文字大小
     * @return 返回条形码组件
     */
    public Barcode setWordsSize(String wordsSize) {
        this.param.setWordsSize(wordsSize);
        return this;
    }

    /**
     * 设置条形码文字偏移量-X轴
     *
     * @param wordsOffsetX 文字偏移量-X轴
     * @return 返回条形码组件
     */
    public Barcode setWordsOffsetX(String wordsOffsetX) {
        this.param.setWordsOffsetX(wordsOffsetX);
        return this;
    }

    /**
     * 设置条形码文字偏移量-Y轴
     *
     * @param wordsOffsetY 文字偏移量-Y轴
     * @return 返回条形码组件
     */
    public Barcode setWordsOffsetY(String wordsOffsetY) {
        this.param.setWordsOffsetY(wordsOffsetY);
        return this;
    }

    /**
     * 设置文本水平样式
     * <p>left：居左</p>
     * <p>center：居中</p>
     * <p>right：居右</p>
     *
     * @param style 水平样式
     * @return 返回条形码组件
     */
    public Barcode setHorizontalStyle(String style) {
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
     * @return 返回条形码组件
     */
    public Barcode setBreakBefore(String breakBefore) {
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
     * @return 返回条形码组件
     */
    public Barcode setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回条形码组件
     */
    public Barcode enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回条形码组件
     */
    public Barcode enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回条形码组件
     */
    public Barcode enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 开启边框（调试时使用）
     *
     * @return 返回条形码组件
     */
    public Barcode enableBorder() {
        this.param.setHasBorder(Boolean.TRUE);
        return this;
    }
    
    /**
     * 开启无白边
     *
     * @return 返回条形码组件
     */
    public Barcode enableNoWhiteBorder() {
        this.param.setIsNoWhiteBorder("true");
        return this;
    }
    
    /**
     * 开启缓存
     *
     * @return 返回条形码组件
     */
    public Barcode enableCache() {
        this.param.setIsCache("true");
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
        // 如果条形码类型为空，则返回空元素
        if (this.param.getType() == null) {
            // 返回空元素
            return null;
        }
        // 如果条形码内容为空，则返回空元素
        if (this.param.getContent() == null) {
            // 返回空元素
            return null;
        }
        // 创建block元素
        Element block = this.createBlockElement(document, this.param);
        // 添加foreignObject元素
        block.appendChild(this.createForeignObject(document));
        // 返回block元素
        return block;
    }

    /**
     * 创建foreignObject元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createForeignObject(Document document) {
        // 创建foreignObject元素
        Element foreignObject = document.createElement(TemplateTags.INSTREAM_FOREIGN_OBJECT);
        // 创建barcode元素
        Element barcode = document.createElement(TemplateTags.BARCODE);
        // 设置条形码类型
        Optional.ofNullable(this.param.getType()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.TYPE, v.intern().toLowerCase()));
        // 设置条形码缩放比例
        Optional.ofNullable(this.param.getScaleRate()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.SCALE_RATE, v.intern().toLowerCase()));
        // 设置条形码旋转弧度
        Optional.ofNullable(this.param.getRadians()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.RADIANS, v.intern().toLowerCase()));
        // 设置条形码边距
        Optional.ofNullable(this.param.getCodeMargin()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.CODE_MARGIN, v.intern().toLowerCase()));
        // 设置条形码纠错级别
        Optional.ofNullable(this.param.getErrorLevel()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.ERROR_LEVEL, v.intern().toLowerCase()));
        // 设置条形码图像宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WIDTH, v.intern().toLowerCase()));
        // 设置条形码图像高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.HEIGHT, v.intern().toLowerCase()));
        // 设置条形码内容
        Optional.ofNullable(this.param.getContent()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.CONTENT, v.intern()));
        // 设置条形码前景颜色
        Optional.ofNullable(this.param.getOnColor()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.ON_COLOR, v.intern().toLowerCase()));
        // 设置条形码背景颜色
        Optional.ofNullable(this.param.getOffColor()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.OFF_COLOR, v.intern().toLowerCase()));
        // 设置条形码文字
        Optional.ofNullable(this.param.getWords()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS, v.intern()));
        // 设置条形码文字颜色
        Optional.ofNullable(this.param.getWordsColor()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_COLOR, v.intern().toLowerCase()));
        // 设置条形码文字名称
        Optional.ofNullable(this.param.getWordsFamily()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_FAMILY, v.intern().toLowerCase()));
        // 设置条形码文字样式
        Optional.ofNullable(this.param.getWordsStyle()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_STYLE, v.intern().toLowerCase()));
        // 设置条形码文字大小
        Optional.ofNullable(this.param.getWordsSize()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_SIZE, v.intern().toLowerCase()));
        // 设置条形码文字偏移量-X轴
        Optional.ofNullable(this.param.getWordsOffsetX()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_OFFSET_X, v.intern().toLowerCase()));
        // 设置条形码文字偏移量-Y轴
        Optional.ofNullable(this.param.getWordsOffsetY()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.WORDS_OFFSET_Y, v.intern().toLowerCase()));
        // 设置条形码无白边
        Optional.ofNullable(this.param.getIsNoWhiteBorder()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.NO_WHITE_BORDER, v.intern().toLowerCase()));
        // 设置条形码缓存
        Optional.ofNullable(this.param.getIsCache()).ifPresent(v -> barcode.setAttribute(TemplateAttributes.CACHE, v.intern().toLowerCase()));
        // 添加barcode元素
        foreignObject.appendChild(barcode);
        // 返回foreignObject元素
        return foreignObject;
    }
}
