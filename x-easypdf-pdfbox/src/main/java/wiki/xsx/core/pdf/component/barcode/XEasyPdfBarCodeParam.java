package wiki.xsx.core.pdf.component.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.awt.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * pdf条形码参数
 *
 * @author xsx
 * @date 2021/12/20
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
class XEasyPdfBarCodeParam implements Serializable {

    private static final long serialVersionUID = 4749447908058798998L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 条形码类型
     */
    private XEasyPdfBarCode.CodeType codeType;
    /**
     * 条形码边距
     */
    private Integer codeMargin;
    /**
     * 条形码前景颜色
     */
    private Color onColor = Color.BLACK;
    /**
     * 条形码背景颜色
     */
    private Color offColor = Color.WHITE;
    /**
     * 条形码内容
     */
    private String content;
    /**
     * 条形码文字
     */
    private String words;
    /**
     * 条形码文字颜色
     */
    private Color wordsColor = Color.BLACK;
    /**
     * 条形码文字样式
     */
    private XEasyPdfBarCode.WordsStyle wordsStyle = XEasyPdfBarCode.WordsStyle.NORMAL;
    /**
     * 条形码文字大小
     */
    private Integer wordsSize = 20;
    /**
     * 最大宽度
     */
    private Float maxWidth;
    /**
     * 图像宽度（显示宽度）
     */
    private Integer imageWidth;
    /**
     * 图像高度（显示高度）
     */
    private Integer imageHeight;
    /**
     * 图像最大宽度（真实宽度）
     */
    private Integer imageMaxWidth;
    /**
     * 图像最大高度（真实高度）
     */
    private Integer imageMaxHeight;
    /**
     * 旋转弧度
     */
    private Double radians;
    /**
     * 页面X轴起始坐标
     */
    private Float beginX = 0F;
    /**
     * 页面Y轴起始坐标
     */
    private Float beginY;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 右边距
     */
    private Float marginRight;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 下边距
     */
    private Float marginBottom = 0F;
    /**
     * 是否显示文字
     */
    private Boolean isShowWords = Boolean.FALSE;
    /**
     * 编码设置
     */
    private final Map<EncodeHintType, Object> encodeHints = new HashMap<>(8);

    /**
     * 设置编码设置
     *
     * @param type  编码设置类型
     * @param value 编码设置内容
     */
    void setEncodeHints(EncodeHintType type, Object value) {
        this.encodeHints.put(type, value);
    }

    /**
     * 初始化
     */
    void init() {
        // 初始化编码设置
        this.initEncodeHints();
        // 初始化宽度与高度
        this.initWidthAndHeight();
        // 如果显示文字，则重置高度
        if (this.isShowWords) {
            // 如果文字为空，则重置为条形码内容
            if (this.words == null || this.words.trim().length() == 0) {
                // 重置为条形码内容
                this.words = this.content;
            }
        }
    }

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化
        this.init();
        // 获取页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 初始化Y轴坐标
        this.initBeginY(document, page, rectangle);
        // 如果内容模式未初始化，则初始化为页面内容模式
        if (this.contentMode == null) {
            // 初始化为页面内容模式
            this.contentMode = page.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化为页面是否重置上下文
        if (this.isResetContext == null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 如果最大宽度未初始化，则初始化为页面宽度
        if (this.maxWidth == null) {
            // 初始化最大宽度 = 页面宽度
            this.maxWidth = rectangle.getWidth();
        }
        // 如果右边距不为空，则初始化页面X轴起始坐标 += 最大宽度 - 自定义宽度 - 右边距
        if (this.marginRight != null) {
            // 页面X轴起始坐标 += 最大宽度 - 自定义宽度 - 右边距
            this.beginX += this.maxWidth - this.imageWidth - this.marginRight;
        }
        // 否则初始化X轴起始坐标为页面X轴起始坐标 += 左边距
        else {
            // 页面X轴起始坐标 += 左边距
            this.beginX += this.marginLeft;
        }
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
     * 初始化编码设置
     */
    private void initEncodeHints() {
        // 初始化纠错级别
        this.initErrorLevel();
        // 设置编码为utf-8
        this.encodeHints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        // 设置边距
        this.encodeHints.put(EncodeHintType.MARGIN, this.codeMargin==null?this.codeType.margin:this.codeMargin);
    }

    /**
     * 初始化纠错级别
     */
    private void initErrorLevel() {
        // 获取纠错级别
        Object errorLevel = this.encodeHints.get(EncodeHintType.ERROR_CORRECTION);
        // 如果纠错级别不为空，则检查条形码格式化类型并重置
        if (errorLevel != null) {
            // 类型转换
            ErrorCorrectionLevel level = (ErrorCorrectionLevel) errorLevel;
            // 如果条形码格式化类型为阿兹特克码或PDF-417码，则重置纠错级别
            if (BarcodeFormat.AZTEC == this.codeType.codeFormat || BarcodeFormat.PDF_417 == this.codeType.codeFormat) {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, level.getBits());
            }
        }
        // 否则重置纠错级别
        else {
            // 如果条形码格式化类型为阿兹特克码或PDF-417码，则重置纠错级别
            if (BarcodeFormat.AZTEC == this.codeType.codeFormat || BarcodeFormat.PDF_417 == this.codeType.codeFormat) {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M.getBits());
            } else {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            }
        }
    }

    /**
     * 初始化Y轴坐标
     *
     * @param document  pdf文档
     * @param page      pdf页面
     * @param rectangle 页面尺寸
     */
    private void initBeginY(XEasyPdfDocument document, XEasyPdfPage page, PDRectangle rectangle) {
        // 如果页面Y轴起始坐标为空，则初始化
        if (this.beginY == null) {
            // 定义页脚高度
            float footerHeight = 0F;
            // 如果允许添加页脚，且页脚不为空则初始化页脚高度
            if (page.isAllowFooter() && page.getFooter() != null) {
                // 初始化页脚高度
                footerHeight = page.getFooter().getHeight(document, page);
            }
            // 如果pdfBox最新页面当前Y轴坐标不为空，则不为新页面
            if (page.getPageY() != null) {
                // 定义Y轴坐标
                float initY = page.getPageY() != null ? page.getPageY() : rectangle.getHeight();
                // 页面Y轴起始坐标 = pdfBox最新页面当前Y轴坐标 - 上边距 - 自定义高度
                this.beginY = initY - this.marginTop - this.imageHeight;
                // 如果页面Y轴起始坐标-页脚高度小于等于下边距，则分页
                if (this.beginY - footerHeight <= this.marginBottom) {
                    // 添加新页面
                    page.addNewPage(document, rectangle);
                    // 页面Y轴起始坐标 = 页面高度 - 上边距 - 自定义高度
                    this.beginY = rectangle.getHeight() - this.marginTop - this.imageHeight;
                }
            }
            // 如果pdfBox最新页面当前Y轴坐标为空，则为新页面
            else {
                // 页面Y轴起始坐标 = 页面高度 - 上边距 - 自定义高度
                this.beginY = rectangle.getHeight() - this.marginTop - this.imageHeight;
            }
        }
    }

    /**
     * 初始化宽度与高度
     */
    private void initWidthAndHeight() {
        // 如果宽度未初始化，则初始化宽度
        if (this.imageWidth == null) {
            // 初始化宽度
            this.imageWidth = this.codeType.isQrType() ? 100 : 180;
        }
        // 如果高度未初始化，则初始化高度
        if (this.imageHeight == null) {
            // 初始化高度
            this.imageHeight = this.codeType.isQrType() ? this.imageWidth : 60;
        }
        // 如果最大宽度未初始化或小于宽度，则初始化最大宽度
        if (this.imageMaxWidth == null || this.imageMaxWidth < this.imageWidth) {
            // 初始化最大宽度为图像宽度
            this.imageMaxWidth = this.imageWidth;
        }
        // 如果最大高度未初始化或小于高度，则初始化最大高度
        if (this.imageMaxHeight == null || this.imageMaxHeight < this.imageHeight) {
            // 初始化最大高度为图像高度
            this.imageMaxHeight = this.imageHeight;
        }
    }

    /**
     * 重置页面Y轴起始坐标
     *
     * @param height 给定高度
     */
    void resetBeginY(int height) {
        // 重置页面Y轴起始坐标 = Y轴起始坐标 + 原有高度 - 给定高度
        this.beginY = this.beginY + this.imageHeight - height;
    }
}
