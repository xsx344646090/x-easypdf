package wiki.xsx.core.pdf.template.component.text;

import lombok.Data;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTextPositionStyle;

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
     * 行间距
     */
    private String leading;
    /**
     * 字符间距
     */
    private String letterSpacing;
    /**
     * 字体名称
     */
    private String fontFamily;
    /**
     * 字体样式
     */
    private String fontStyle;
    /**
     * 字体大小
     */
    private String fontSize;
    /**
     * 字体大小调整
     */
    private String fontSizeAdjust;
    /**
     * 字体重量
     */
    private String fontWeight;
    /**
     * 字体颜色
     */
    private Color fontColor;
    /**
     * 上填充
     */
    private String paddingTop;
    /**
     * 下填充
     */
    private String paddingBottom;
    /**
     * 左填充
     */
    private String paddingLeft;
    /**
     * 右填充
     */
    private String paddingRight;
    /**
     * 是否包含边框
     */
    private Boolean hasBorder;
    /**
     * 水平样式（居左、居中、居右）
     */
    private XEasyPdfTemplateTextPositionStyle horizontalStyle;
}
