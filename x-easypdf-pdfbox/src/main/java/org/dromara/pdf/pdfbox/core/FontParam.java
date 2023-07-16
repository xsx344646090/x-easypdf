package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.enums.FontStyle;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/6/21
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
@Data
public class FontParam {
    /**
     * 当前使用字体名称
     */
    private String fontName;
    /**
     * 当前使用字体
     */
    private PDFont font;
    /**
     * 字体大小
     */
    private Float fontSize;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 字体样式
     */
    private FontStyle fontStyle;
    /**
     * 字符间距
     */
    private Float characterSpacing;
    /**
     * 行间距
     */
    private Float leading;
}
