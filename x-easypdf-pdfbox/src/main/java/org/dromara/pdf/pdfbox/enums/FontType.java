package org.dromara.pdf.pdfbox.enums;

/**
 * @author xsx
 * @date 2023/6/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public enum FontType {
    /**
     * ttf
     */
    TTF(".ttf"),
    /**
     * otf
     */
    OTF(".otf"),
    /**
     * ttc
     */
    TTC(".ttc"),
    /**
     * otc
     */
    OTC(".otc"),
    /**
     * pfb
     */
    PFB(".pfb");

    /**
     * 后缀
     */
    private final String suffix;

    /**
     * 有参构造
     *
     * @param suffix 后缀
     */
    FontType(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 获取后缀
     *
     * @return 返回后缀
     */
    public String getSuffix() {
        return suffix;
    }
}
