package org.dromara.pdf.pdfbox.core.enums;

import lombok.Getter;

/**
 * 图像类型
 *
 * @author xsx
 * @date 2023/11/6
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
@Getter
public enum ImageType {
    /**
     * jpeg
     */
    JPEG("jpeg"),
    JPE("jpeg"),
    JPG("jpeg"),
    /**
     * png
     */
    PNG("png"),
    /**
     * tiff
     */
    TIFF("tiff"),
    /**
     * bmp
     */
    BMP("bmp"),
    /**
     * gif
     */
    GIF("gif"),
    /**
     * jpeg2000
     */
    JP2("JPEG2000"),
    JPC("JPEG2000"),
    JPF("JPEG2000"),
    JPX("JPEG2000"),
    J2C("JPEG2000"),
    J2K("JPEG2000");

    /**
     * 类型
     */
    private final String type;

    /**
     * 有参构造
     *
     * @param type 类型
     */
    ImageType(String type) {
        this.type = type;
    }

}
