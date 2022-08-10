package wiki.xsx.core.pdf.component.image;

import java.awt.*;

/**
 * 图片压缩模式枚举
 *
 * @author xsx
 * @date 2021/10/5
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
public enum XEasyPdfImageScaleMode {
    /**
     * 质量
     */
    QUALITY(Image.SCALE_SMOOTH),
    /**
     * 速度
     */
    SPEED(Image.SCALE_FAST),
    /**
     * 平衡
     */
    BALANCE(Image.SCALE_DEFAULT);

    /**
     * 编码
     */
    int code;

    /**
     * 有参构造
     *
     * @param code 编码
     */
    XEasyPdfImageScaleMode(int code) {
        this.code = code;
    }
}
