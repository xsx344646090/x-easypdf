package wiki.xsx.core.pdf.template.ext.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.fop.util.ColorUtil;
import org.apache.xmlgraphics.util.UnitConv;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.util.XEasyPdfTemplateFontStyleUtil;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePageRectangle;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 条形码配置
 *
 * @author xsx
 * @date 2022/10/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
@Data
@Accessors(chain = true)
public class XEasyPdfTemplateBarCodeConfig {

    /**
     * 条形码类型
     */
    private BarcodeFormat type;
    /**
     * 缩放比例
     */
    private Double scaleRate;
    /**
     * 图像宽度
     */
    private Integer width;
    /**
     * 图像高度
     */
    private Integer height;
    /**
     * 条形码内容
     */
    private String content;
    /**
     * 条形码边距
     */
    private Integer codeMargin;
    /**
     * 条形码纠错级别
     */
    private ErrorCorrectionLevel errorLevel;
    /**
     * 条形码前景颜色
     */
    private Color onColor;
    /**
     * 条形码背景颜色
     */
    private Color offColor;
    /**
     * 条形码文字
     */
    private String words;
    /**
     * 条形码文字颜色
     */
    private Color wordsColor;
    /**
     * 条形码文字名称
     */
    private String wordsFamily;
    /**
     * 条形码文字样式
     * <p>正常：Font.PLAIN</p>
     * <p>粗体：Font.BOLD</p>
     * <p>斜体：Font.ITALIC</p>
     * <p>粗体斜体：Font.BOLD|Font.ITALIC</p>
     */
    private Integer wordsStyle;
    /**
     * 条形码文字大小
     */
    private Integer wordsSize;
    /**
     * 条形码文字偏移量-X轴
     */
    private Integer wordsOffsetX;
    /**
     * 条形码文字偏移量-Y轴
     */
    private Integer wordsOffsetY;
    /**
     * 旋转弧度
     */
    private Double radians;
    /**
     * 编码设置
     */
    private final Map<EncodeHintType, Object> encodeHints = new HashMap<>(8);

    /**
     * 初始化
     *
     * @param attributes 节点属性
     * @return 返回条形码配置
     */
    static XEasyPdfTemplateBarCodeConfig init(NamedNodeMap attributes) {
        // 创建配置
        XEasyPdfTemplateBarCodeConfig config = new XEasyPdfTemplateBarCodeConfig();
        // 初始化参数
        config.initParams(attributes);
        // 初始化编码设置
        config.initEncodeHints();
        // 返回配置
        return config;
    }

    /**
     * 仅初始化尺寸
     *
     * @param attributes 节点属性
     * @return 返回条形码配置
     */
    static XEasyPdfTemplateBarCodeConfig onlyInitRectangle(NamedNodeMap attributes) {
        // 创建配置
        XEasyPdfTemplateBarCodeConfig config = new XEasyPdfTemplateBarCodeConfig();
        // 初始化参数
        config.onlyInitRectangleParams(attributes);
        // 返回配置
        return config;
    }

    /**
     * 是否旋转
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean isRotate() {
        return this.radians != null;
    }

    /**
     * 获取旋转尺寸
     *
     * @return 返回旋转后的尺寸
     */
    Rectangle getRotateRectangle() {
        return XEasyPdfTemplatePageRectangle.getRotateRectangle(this.width, this.height, this.radians);
    }

    /**
     * 初始化参数
     *
     * @param attributes 节点属性
     */
    private void initParams(NamedNodeMap attributes) {
        // 初始化类型
        this.type = this.resolveValue(attributes, XEasyPdfTemplateAttributes.TYPE, null, v -> BarcodeFormat.valueOf(v.toUpperCase()));
        // 初始化缩放比例
        this.scaleRate = this.resolveValue(attributes, XEasyPdfTemplateAttributes.SCALE_RATE, "2", Double::parseDouble);
        // 初始化图像宽度
        this.width = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WIDTH, null, this::parseUnit);
        // 初始化图像高度
        this.height = this.resolveValue(attributes, XEasyPdfTemplateAttributes.HEIGHT, null, this::parseUnit);
        // 初始化内容
        this.content = this.resolveValue(attributes, XEasyPdfTemplateAttributes.CONTENT, null, v -> v);
        // 初始化条形码边距
        this.codeMargin = this.resolveValue(attributes, XEasyPdfTemplateAttributes.CODE_MARGIN, "1", Integer::parseInt);
        // 初始化纠错级别
        this.errorLevel = this.resolveValue(attributes, XEasyPdfTemplateAttributes.ERROR_LEVEL, "M", v -> ErrorCorrectionLevel.valueOf(v.toUpperCase()));
        // 初始化前景颜色
        this.onColor = this.resolveValue(attributes, XEasyPdfTemplateAttributes.ON_COLOR, "BLACK", this::parseColor);
        // 初始化背景颜色
        this.offColor = this.resolveValue(attributes, XEasyPdfTemplateAttributes.OFF_COLOR, "WHITE", this::parseColor);
        // 初始化文字
        this.words = Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS)).map(Node::getNodeValue).orElse(null);
        // 初始化文字颜色
        this.wordsColor = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_COLOR, "BLACK", this::parseColor);
        // 初始化文字名称
        this.wordsFamily = Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_FAMILY)).map(Node::getNodeValue).orElse(null);
        // 初始化文字样式
        this.wordsStyle = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_STYLE, "NORMAL", XEasyPdfTemplateFontStyleUtil::getStyle);
        // 初始化文字大小
        this.wordsSize = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_SIZE, "12pt", this::parseUnit);
        // 初始化文字偏移量-X轴
        this.wordsOffsetX = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_OFFSET_X, "0pt", this::parseUnit);
        // 初始化文字偏移量-Y轴
        this.wordsOffsetY = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_OFFSET_Y, "2pt", this::parseUnit);
        // 初始化旋转弧度
        this.radians = this.resolveValue(attributes, XEasyPdfTemplateAttributes.RADIANS, "0", Double::parseDouble);
    }

    /**
     * 仅初始化尺寸参数
     *
     * @param attributes 节点属性
     */
    private void onlyInitRectangleParams(NamedNodeMap attributes) {
        // 初始化图像宽度
        this.width = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WIDTH, null, v -> UnitConv.convert(v) / 1000);
        // 初始化图像高度
        this.height = this.resolveValue(attributes, XEasyPdfTemplateAttributes.HEIGHT, null, v -> UnitConv.convert(v) / 1000);
        // 初始化旋转弧度
        this.radians = this.resolveValue(attributes, XEasyPdfTemplateAttributes.RADIANS, "0", Double::parseDouble);
        // 初始化文字
        this.words = Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS)).map(Node::getNodeValue).orElse(null);
        // 初始化文字大小
        this.wordsSize = this.resolveValue(attributes, XEasyPdfTemplateAttributes.WORDS_SIZE, "12pt", v -> UnitConv.convert(v) / 1000);
        // 如果旋转，则重置图像宽度与高度
        if (this.isRotate()) {
            // 获取旋转尺寸
            Rectangle rectangle = this.getRotateRectangle();
            // 重置图像宽度
            this.width = rectangle.width;
            // 重置图像高度
            this.height = rectangle.height;
        }
    }

    /**
     * 初始化编码设置
     */
    private void initEncodeHints() {
        // 初始化纠错级别
        this.initErrorLevel();
        // 设置编码为utf-8
        this.encodeHints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        // 设置边距
        this.encodeHints.put(EncodeHintType.MARGIN, this.codeMargin);
    }

    /**
     * 初始化纠错级别
     */
    private void initErrorLevel() {
        // 如果条形码格式化类型为阿兹特克码或PDF-417码，则重置纠错级别
        if (BarcodeFormat.AZTEC == this.type || BarcodeFormat.PDF_417 == this.type) {
            // 重置纠错级别
            this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, this.errorLevel.getBits());
        }
        // 否则添加纠错级别
        else {
            // 添加纠错级别
            this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, this.errorLevel);
        }
    }

    /**
     * 解析值
     *
     * @param attributes    节点属性
     * @param attributeName 属性名
     * @param defaultValue  默认值
     * @param function      处理方法
     * @param <R>           返回值类型
     * @return 返回属性值
     */
    private <R> R resolveValue(NamedNodeMap attributes, String attributeName, String defaultValue, Function<String, R> function) {
        return Optional.ofNullable(
                Optional.ofNullable(attributes.getNamedItem(attributeName)).map(Node::getNodeValue).orElse(defaultValue)
        ).map(v -> {
            try {
                return function.apply(v);
            } catch (Exception e) {
                throw new IllegalArgumentException("the barcode attribute['" + attributeName + "'] is error");
            }
        }).orElseThrow(() -> new IllegalArgumentException("the barcode attribute['" + attributeName + "'] can not be null"));
    }

    /**
     * 解析颜色
     *
     * @param color 颜色
     * @return 返回颜色
     */
    @SneakyThrows
    private Color parseColor(String color) {
        return ColorUtil.parseColorString(null, color);
    }

    /**
     * 解析单位
     *
     * @param unit 单位
     * @return 返回单位
     */
    @SneakyThrows
    private int parseUnit(String unit) {
        return (int) (UnitConv.convert(unit) / 1000 * this.scaleRate);
    }
}
