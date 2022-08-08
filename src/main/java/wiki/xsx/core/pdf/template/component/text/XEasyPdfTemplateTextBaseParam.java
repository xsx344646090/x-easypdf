package wiki.xsx.core.pdf.template.component.text;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.*;

/**
 * pdf模板-文本基础参数
 *
 * @author xsx
 * @date 2022/8/8
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
class XEasyPdfTemplateTextBaseParam {
    /**
     * 高度
     */
    private String height;
    /**
     * 字体名称
     */
    private String fontFamily;
    /**
     * 字体大小
     */
    private String fontSize;
    /**
     * 字体大小调整
     */
    private String fontSizeAdjust;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 是否包含边框
     */
    private Boolean hasBorder;
}
