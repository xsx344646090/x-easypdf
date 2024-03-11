package org.dromara.pdf.pdfbox.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * @author xsx
 * @date 2023/10/10
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
@Data
@AllArgsConstructor
public class COSBaseInfo {
    /**
     * cosBase
     */
    private COSBase cosBase;
    /**
     * 字体索引
     */
    private Integer fontIndex;
    /**
     * 字体名称
     */
    private COSName fontName;
    /**
     * 字体
     */
    private PDFont font;
}
