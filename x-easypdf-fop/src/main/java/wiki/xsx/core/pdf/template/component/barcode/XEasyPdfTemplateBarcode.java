package wiki.xsx.core.pdf.template.component.barcode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.awt.*;
import java.util.Optional;

/**
 * pdf模板-条形码组件
 * <p>fo:barcode</p>
 *
 * @author xsx
 * @date 2022/10/18
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfTemplateBarcode implements XEasyPdfTemplateComponent {

    /**
     * 条形码参数
     */
    private final XEasyPdfTemplateBarcodeParam param = new XEasyPdfTemplateBarcodeParam();

    /**
     * 设置条形码类型
     * <p>一维码：</p>
     * <p>CODABAR：库德巴码</p>
     * <p>CODE_39：标准39码</p>
     * <p>CODE_93：标准93码</p>
     * <p>CODE_128：标准128码</p>
     * <p>EAN_8：缩短版国际商品条码</p>
     * <p>EAN_13：标准版国际商品条码</p>
     * <p>ITF：交叉码</p>
     * <p>UPC_A：美国商品码A</p>
     * <p>UPC_E：美国商品码E</p>
     * <p>UPC_EAN_EXTENSION：美国商品码扩展码</p>
     * <p>二维码：</p>
     * <p>QR_CODE：QR码</p>
     * <p>AZTEC：阿兹特克码</p>
     * <p>DATA_MATRIX：DM码</p>
     * <p>MAXI_CODE：Maxi码</p>
     * <p>PDF_417：PDF-417码</p>
     * <p>RSS_14：RSS-14码</p>
     * <p>RSS_EXPANDED：RSS扩展码</p>
     *
     * @param type 类型
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setType(String type) {
        this.param.setType(type);
        return this;
    }

    /**
     * 设置条形码缩放比例（实际比例）
     *
     * @param scaleRate 缩放比例
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setScaleRate(String scaleRate) {
        this.param.setScaleRate(scaleRate);
        return this;
    }

    /**
     * 设置条形码旋转弧度
     *
     * @param radians 旋转弧度
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setRadians(String radians) {
        this.param.setRadians(radians);
        return this;
    }

    /**
     * 设置条形码边距
     *
     * @param codeMargin 条形码边距
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setCodeMargin(String codeMargin) {
        this.param.setCodeMargin(codeMargin);
        return this;
    }

    /**
     * 设置条形码纠错级别
     *
     * @param errorLevel 纠错级别
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setErrorLevel(String errorLevel) {
        this.param.setErrorLevel(errorLevel);
        return this;
    }

    /**
     * 设置条形码图像宽度
     *
     * @param width 图像宽度
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置条形码图像高度
     *
     * @param height 图像高度
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置条形码内容
     *
     * @param content 内容
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setContent(String content) {
        this.param.setContent(content);
        return this;
    }

    /**
     * 设置条形码前景颜色
     *
     * @param onColor 前景颜色
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setOnColor(Color onColor) {
        this.param.setOnColor(onColor);
        return this;
    }

    /**
     * 设置条形码背景颜色
     *
     * @param offColor 背景颜色
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setOffColor(Color offColor) {
        this.param.setOffColor(offColor);
        return this;
    }

    /**
     * 设置条形码文字
     *
     * @param words 文字
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWords(String words) {
        this.param.setWords(words);
        return this;
    }

    /**
     * 设置条形码文字颜色
     *
     * @param wordsColor 文字颜色
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWordsColor(Color wordsColor) {
        this.param.setWordsColor(wordsColor);
        return this;
    }

    /**
     * 设置条形码文字样式
     * <p>NORMAL：正常</p>
     * <p>BOLD：粗体</p>
     * <p>BOLD_ITALIC：粗体斜体</p>
     * <p>ITALIC：斜体</p>
     *
     * @param wordsStyle 文字样式
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWordsStyle(String wordsStyle) {
        this.param.setWordsStyle(wordsStyle);
        return this;
    }

    /**
     * 设置条形码文字大小
     *
     * @param wordsSize 文字大小
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWordsSize(String wordsSize) {
        this.param.setWordsSize(wordsSize);
        return this;
    }

    /**
     * 设置条形码文字偏移量-X轴
     *
     * @param wordsOffsetX 文字偏移量-X轴
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWordsOffsetX(String wordsOffsetX) {
        this.param.setWordsOffsetX(wordsOffsetX);
        return this;
    }

    /**
     * 设置条形码文字偏移量-Y轴
     *
     * @param wordsOffsetY 文字偏移量-Y轴
     * @return 返回条形码组件
     */
    public XEasyPdfTemplateBarcode setWordsOffsetY(String wordsOffsetY) {
        this.param.setWordsOffsetY(wordsOffsetY);
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
        // 创建barcode元素
        Element foreignObject = this.createForeignObject(document);
        // 添加foreignObject元素
        block.appendChild(foreignObject);
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
        Element foreignObject = document.createElement(XEasyPdfTemplateTags.INSTREAM_FOREIGN_OBJECT);
        // 创建barcode元素
        Element barcode = document.createElement(XEasyPdfTemplateTags.BARCODE);
        // 设置元素命名空间
        barcode.setAttribute(XEasyPdfTemplateAttributes.NAMESPACE, XEasyPdfTemplateConstants.NAMESPACE);
        // 设置条形码类型
        Optional.ofNullable(this.param.getType()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.TYPE, v));
        // 设置条形码缩放比例
        Optional.ofNullable(this.param.getScaleRate()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.SCALE_RATE, v));
        // 设置条形码旋转弧度
        Optional.ofNullable(this.param.getRadians()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.RADIANS, v));
        // 设置条形码边距
        Optional.ofNullable(this.param.getCodeMargin()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.CODE_MARGIN, v));
        // 设置条形码纠错级别
        Optional.ofNullable(this.param.getErrorLevel()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.ERROR_LEVEL, v));
        // 设置条形码图像宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WIDTH, v));
        // 设置条形码图像高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.HEIGHT, v));
        // 设置条形码内容
        Optional.ofNullable(this.param.getContent()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.CONTENT, v));
        // 设置条形码前景颜色
        Optional.ofNullable(this.param.getOnColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(barcode, XEasyPdfTemplateAttributes.ON_COLOR, v));
        // 设置条形码背景颜色
        Optional.ofNullable(this.param.getOffColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(barcode, XEasyPdfTemplateAttributes.OFF_COLOR, v));
        // 设置条形码文字
        Optional.ofNullable(this.param.getWords()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WORDS, v));
        // 设置条形码文字颜色
        Optional.ofNullable(this.param.getWordsColor()).ifPresent(v -> XEasyPdfTemplateElementHandler.appendColor(barcode, XEasyPdfTemplateAttributes.WORDS_COLOR, v));
        // 设置条形码文字样式
        Optional.ofNullable(this.param.getWordsStyle()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WORDS_STYLE, v));
        // 设置条形码文字大小
        Optional.ofNullable(this.param.getWordsSize()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WORDS_SIZE, v));
        // 设置条形码文字偏移量-X轴
        Optional.ofNullable(this.param.getWordsOffsetX()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WORDS_OFFSET_X, v));
        // 设置条形码文字偏移量-Y轴
        Optional.ofNullable(this.param.getWordsOffsetY()).ifPresent(v -> barcode.setAttribute(XEasyPdfTemplateAttributes.WORDS_OFFSET_Y, v));
        // 添加barcode元素
        foreignObject.appendChild(barcode);
        // 返回foreignObject元素
        return foreignObject;
    }
}
