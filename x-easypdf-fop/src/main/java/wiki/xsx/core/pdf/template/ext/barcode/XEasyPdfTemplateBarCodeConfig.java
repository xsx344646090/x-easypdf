package wiki.xsx.core.pdf.template.ext.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.fop.fo.expr.PropertyException;
import org.apache.fop.util.ColorUtil;
import org.apache.xmlgraphics.util.UnitConv;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 条形码配置
 *
 * @author xsx
 * @date 2022/10/12
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
     * 条形码文字样式
     * <p>正常：Font.PLAIN</p>
     * <p>粗体：Font.BOLD</p>
     * <p>斜体：Font.ITALIC</p>
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
        return this.radians != null && this.radians % 360 != 0;
    }

    /**
     * 获取旋转尺寸
     *
     * @return 返回旋转后的尺寸
     */
    Rectangle getRotateRectangle() {
        Rectangle src = new Rectangle(new Dimension(this.width, this.words == null ? this.height : this.height + wordsSize));
        final int angle = 90;
        final int num = 2;
        if (this.radians < 0) {
            this.radians += 360;
        }
        if (this.radians >= angle) {
            if (this.radians / angle % num == 1) {
                return new Rectangle((int) src.getHeight(), (int) src.getWidth());
            }
            this.radians = this.radians % angle;
        }
        double radius = Math.sqrt(src.height * src.height + src.width * src.width) / num;
        double len = num * Math.sin(Math.toRadians(this.radians) / num) * radius;
        double radiansAlpha = (Math.PI - Math.toRadians(this.radians)) / num;
        double radiansWidth = Math.atan(src.height / (double) src.width);
        double radiansHeight = Math.atan(src.width / (double) src.height);
        int lenWidth = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansWidth)));
        int lenHeight = Math.abs((int) (len * Math.cos(Math.PI - radiansAlpha - radiansHeight)));
        return new Rectangle((src.width + lenWidth * num), (src.height + lenHeight * num));
    }

    /**
     * 初始化参数
     *
     * @param attributes 节点属性
     */
    private void initParams(NamedNodeMap attributes) {
        // 初始化类型
        this.type = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.TYPE))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['type'] can not be null"))
                        .getNodeValue(),
                v -> BarcodeFormat.valueOf(v.toUpperCase()),
                () -> new IllegalArgumentException("the barcode attribute['type'] is unsupported")
        );
        // 初始化缩放比例
        this.scaleRate = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.SCALE_RATE), "2"),
                Double::parseDouble,
                () -> new IllegalArgumentException("the barcode attribute['scale-rate'] is error")
        );
        // 初始化图像宽度
        this.width = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.WIDTH))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['width'] can not be null"))
                        .getNodeValue(),
                v -> (int) (UnitConv.convert(v) / 1000 * this.scaleRate),
                () -> new IllegalArgumentException("the barcode attribute['width'] is error")
        );
        // 初始化图像高度
        this.height = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.HEIGHT))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['height'] can not be null"))
                        .getNodeValue(),
                v -> (int) (UnitConv.convert(v) / 1000 * this.scaleRate),
                () -> new IllegalArgumentException("the barcode attribute['height'] is error")
        );
        // 初始化内容
        this.content = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.CONTENT))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['content'] can not be null"))
                        .getNodeValue(),
                v -> v,
                null
        );
        // 初始化条形码边距
        this.codeMargin = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.CODE_MARGIN), "1"),
                Integer::parseInt,
                () -> new IllegalArgumentException("the barcode attribute['code-margin'] is error")
        );
        // 初始化纠错级别
        this.errorLevel = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.ERROR_LEVEL), "M"),
                v -> ErrorCorrectionLevel.valueOf(v.toUpperCase()),
                () -> new IllegalArgumentException("the barcode attribute['error-level'] is error")
        );
        // 初始化前景颜色
        this.onColor = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.ON_COLOR), "BLACK"),
                v -> {
                    try {
                        return ColorUtil.parseColorString(null, v);
                    } catch (PropertyException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> new IllegalArgumentException("the barcode attribute['on-color'] is error")
        );
        // 初始化背景颜色
        this.offColor = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.OFF_COLOR), "WHITE"),
                v -> {
                    try {
                        return ColorUtil.parseColorString(null, v);
                    } catch (PropertyException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> new IllegalArgumentException("the barcode attribute['off-color'] is error")
        );
        // 初始化文字
        this.words = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS), null),
                v -> v,
                null
        );
        // 初始化文字颜色
        this.wordsColor = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_COLOR), "BLACK"),
                v -> {
                    try {
                        return ColorUtil.parseColorString(null, v);
                    } catch (PropertyException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> new IllegalArgumentException("the barcode attribute['words-color'] is error")
        );
        // 初始化文字样式
        this.wordsStyle = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_STYLE), "NORMAL"),
                v -> WordsStyle.valueOf(v.toUpperCase()).style,
                () -> new IllegalArgumentException("the barcode attribute['words-style'] is error")
        );
        // 初始化文字大小
        this.wordsSize = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_SIZE), "12pt"),
                v -> (int) (UnitConv.convert(v) / 1000 * this.scaleRate),
                () -> new IllegalArgumentException("the barcode attribute['words-size'] is error")
        );
        // 初始化文字偏移量-X轴
        this.wordsOffsetX = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_OFFSET_X), "0"),
                Integer::parseInt,
                () -> new IllegalArgumentException("the barcode attribute['words-offset-x'] is error")
        );
        // 初始化文字偏移量-Y轴
        this.wordsOffsetY = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_OFFSET_Y), "4"),
                Integer::parseInt,
                () -> new IllegalArgumentException("the barcode attribute['words-offset-y'] is error")
        );
        // 初始化旋转弧度
        this.radians = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.RADIANS), "0"),
                Double::parseDouble,
                () -> new IllegalArgumentException("the barcode attribute['radians'] is error")
        );
    }

    /**
     * 仅初始化尺寸参数
     *
     * @param attributes 节点属性
     */
    private void onlyInitRectangleParams(NamedNodeMap attributes) {
        // 初始化图像宽度
        this.width = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.WIDTH))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['width'] can not be null"))
                        .getNodeValue(),
                v -> UnitConv.convert(v) / 1000,
                () -> new IllegalArgumentException("the barcode attribute['width'] is error")
        );
        // 初始化图像高度
        this.height = this.resolveValue(
                Optional.ofNullable(attributes.getNamedItem(XEasyPdfTemplateAttributes.HEIGHT))
                        .orElseThrow(() -> new IllegalArgumentException("the barcode attribute['height'] can not be null"))
                        .getNodeValue(),
                v -> UnitConv.convert(v) / 1000,
                () -> new IllegalArgumentException("the barcode attribute['height'] is error")
        );
        // 初始化旋转弧度
        this.radians = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.RADIANS), "0"),
                Double::parseDouble,
                () -> new IllegalArgumentException("the barcode attribute['radians'] is error")
        );
        // 初始化文字
        this.words = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS), null),
                v -> v,
                null
        );
        // 初始化文字大小
        this.wordsSize = this.resolveValue(
                this.getValue(attributes.getNamedItem(XEasyPdfTemplateAttributes.WORDS_SIZE), "12pt"),
                v -> UnitConv.convert(v) / 1000,
                () -> new IllegalArgumentException("the barcode attribute['words-size'] is error")
        );
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
     * 获取值
     *
     * @param node         节点
     * @param defaultValue 默认值
     * @return 返回值
     */
    private String getValue(Node node, String defaultValue) {
        return node == null ? defaultValue : node.getNodeValue();
    }

    /**
     * 解析值
     *
     * @param value     值
     * @param function  处理方法
     * @param exception 异常信息
     * @param <X>       异常类型
     * @param <R>       返回类型
     * @return 返回处理后的值
     */
    @SneakyThrows
    private <X extends Throwable, R> R resolveValue(String value, Function<String, R> function, Supplier<? extends X> exception) {
        try {
            return function.apply(value);
        } catch (Exception e) {
            throw exception.get();
        }
    }

    /**
     * 文字样式
     */
    private enum WordsStyle {
        /**
         * 正常
         */
        NORMAL(Font.PLAIN),
        /**
         * 粗体
         */
        BOLD(Font.BOLD),
        /**
         * 粗体斜体
         */
        BOLD_ITALIC(Font.BOLD|Font.ITALIC),
        /**
         * 斜体
         */
        ITALIC(Font.ITALIC);
        /**
         * 样式
         */
        final int style;

        /**
         * 有参构造
         *
         * @param style 样式
         */
        WordsStyle(int style) {
            this.style = style;
        }
    }
}
