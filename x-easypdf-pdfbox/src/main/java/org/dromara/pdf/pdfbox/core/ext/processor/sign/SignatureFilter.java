package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSName;

/**
 * 签名过滤器
 *
 * @author xsx
 * @date 2025/9/28
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class SignatureFilter {

    /**
     * A signature filter value.
     */
    public static final COSName FILTER_ADOBE_PPKLITE = COSName.ADOBE_PPKLITE;

    /**
     * A signature filter value.
     */
    public static final COSName FILTER_ENTRUST_PPKEF = COSName.ENTRUST_PPKEF;

    /**
     * A signature filter value.
     */
    public static final COSName FILTER_CICI_SIGNIT = COSName.CICI_SIGNIT;

    /**
     * A signature filter value.
     */
    public static final COSName FILTER_VERISIGN_PPKVS = COSName.VERISIGN_PPKVS;

    /**
     * A signature filter value.
     */
    public static final COSName FILTER_GM = COSName.getPDFName("GM.PKILite");

    /**
     * A signature subfilter value.
     */
    public static final COSName SUBFILTER_ADBE_X509_RSA_SHA1 = COSName.ADBE_X509_RSA_SHA1;

    /**
     * A signature subfilter value.
     */
    public static final COSName SUBFILTER_ADBE_PKCS7_DETACHED = COSName.ADBE_PKCS7_DETACHED;

    /**
     * A signature subfilter value.
     */
    public static final COSName SUBFILTER_ETSI_CADES_DETACHED = COSName.getPDFName("ETSI.CAdES.detached");

    /**
     * A signature subfilter value.
     */
    public static final COSName SUBFILTER_ADBE_PKCS7_SHA1 = COSName.ADBE_PKCS7_SHA1;

    /**
     * A signature subfilter value.
     */
    public static final COSName SUBFILTER_GM_SM2 = COSName.getPDFName("GM.SM2");
}
