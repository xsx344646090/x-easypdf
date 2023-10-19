package org.dromara.pdf.fop.doc.component.barcode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.pdf.fop.doc.component.ComponentParam;

/**
 * pdf模板-条形码参数
 *
 * @author xsx
 * @date 2022/10/18
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
@EqualsAndHashCode(callSuper = true)
class BarcodeParam extends ComponentParam {
    /**
     * 条形码类型
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
     */
    private String type;
    /**
     * 条形码缩放比例（实际比例）
     * <p>默认：2，即实际图像大小为显示图像的2倍</p>
     */
    private String scaleRate;
    /**
     * 条形码旋转弧度
     */
    private String radians;
    /**
     * 条形码边距
     */
    private String codeMargin;
    /**
     * 条形码纠错级别
     */
    private String errorLevel;
    /**
     * 条形码图像宽度
     */
    private String width;
    /**
     * 条形码图像高度
     */
    private String height;
    /**
     * 条形码内容
     */
    private String content;
    /**
     * 条形码前景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String onColor;
    /**
     * 条形码背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String offColor;
    /**
     * 条形码文字
     */
    private String words;
    /**
     * 条形码文字颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String wordsColor;
    /**
     * 条形码文字名称
     */
    private String wordsFamily;
    /**
     * 条形码文字样式
     * <p>normal：正常</p>
     * <p>bold：粗体</p>
     * <p>bold_italic：粗体斜体</p>
     * <p>italic：斜体</p>
     */
    private String wordsStyle;
    /**
     * 条形码文字大小
     */
    private String wordsSize;
    /**
     * 条形码文字偏移量-X轴
     */
    private String wordsOffsetX;
    /**
     * 条形码文字偏移量-Y轴
     */
    private String wordsOffsetY;
}
