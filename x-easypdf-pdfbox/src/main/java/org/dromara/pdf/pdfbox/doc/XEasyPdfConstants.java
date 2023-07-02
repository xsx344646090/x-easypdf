package org.dromara.pdf.pdfbox.doc;

/**
 * pdf常量
 *
 * @author xsx
 * @date 2022/8/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public final class XEasyPdfConstants {

    /**
     * pdfbox生产者
     */
    public static final String PDFBOX_PRODUCER = "x-easypdf/pdfbox";
    /**
     * 每英寸像素点
     */
    public static final Integer POINTS_PER_INCH = 72;
    /**
     * 每毫米像素点
     */
    public static final Float POINTS_PER_MM = 1 / 25.4f * POINTS_PER_INCH;
    /**
     * 总页码占位符
     */
    public static final String TOTAL_PAGE_PLACEHOLDER = "${TPN}";
    /**
     * 当前页码占位符
     */
    public static final String CURRENT_PAGE_PLACEHOLDER = "${CPN}";
    /**
     * 字体映射策略键
     */
    public static final String FONT_MAPPING_POLICY_KEY = "x-easypdf.font.mapping";
}
