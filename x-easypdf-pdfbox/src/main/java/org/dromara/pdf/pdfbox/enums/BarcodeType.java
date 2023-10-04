package org.dromara.pdf.pdfbox.enums;

import com.google.zxing.BarcodeFormat;

/**
 * 条形码类型
 *
 * @author xsx
 * @date 2023/8/28
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf-box is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public enum BarcodeType {

    // ************** 一维码 **************  //
    /**
     * 库德巴码
     */
    CODA_BAR(BarcodeFormat.CODABAR),
    /**
     * 标准39码
     */
    CODE_39(BarcodeFormat.CODE_39),
    /**
     * 标准93码
     */
    CODE_93(BarcodeFormat.CODE_93),
    /**
     * 标准128码
     */
    CODE_128(BarcodeFormat.CODE_128),
    /**
     * 缩短版国际商品条码
     */
    EAN_8(BarcodeFormat.EAN_8),
    /**
     * 标准版国际商品条码
     */
    EAN_13(BarcodeFormat.EAN_13),
    /**
     * 交叉码
     */
    ITF(BarcodeFormat.ITF),
    /**
     * 美国商品码A
     */
    UPC_A(BarcodeFormat.UPC_A),
    /**
     * 美国商品码E
     */
    UPC_E(BarcodeFormat.UPC_E),

    // ************** 二维码 **************  //
    /**
     * QR码
     */
    QR_CODE(BarcodeFormat.QR_CODE),
    /**
     * 阿兹特克码
     */
    AZTEC(BarcodeFormat.AZTEC),
    /**
     * DM码
     */
    DATA_MATRIX(BarcodeFormat.DATA_MATRIX),
    /**
     * MaxiCode
     */
    MAXI_CODE(BarcodeFormat.MAXICODE),
    /**
     * PDF-417码
     */
    PDF_417(BarcodeFormat.PDF_417);

    /**
     * 条形码格式化类型
     */
    private final BarcodeFormat codeFormat;

    /**
     * 有参构造
     *
     * @param codeFormat 格式化类型
     */
    BarcodeType(BarcodeFormat codeFormat) {
        this.codeFormat = codeFormat;
    }

    /**
     * 获取格式化类型
     *
     * @return 返回格式化类型
     */
    public BarcodeFormat getCodeFormat() {
        return this.codeFormat;
    }

    /**
     * 是否二维码类型
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isQrType() {
        switch (this) {
            case QR_CODE:
            case AZTEC:
            case DATA_MATRIX:
            case MAXI_CODE:
            case PDF_417:
                return true;
            default:
                return false;
        }
    }
}
