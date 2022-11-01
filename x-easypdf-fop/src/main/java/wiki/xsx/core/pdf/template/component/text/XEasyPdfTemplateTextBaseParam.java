package wiki.xsx.core.pdf.template.component.text;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponentParam;

import java.awt.Color;

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
@EqualsAndHashCode(callSuper = true)
class XEasyPdfTemplateTextBaseParam extends XEasyPdfTemplateComponentParam {
    /**
     * 语言
     *
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    protected String language;
    /**
     * 行间距
     */
    protected String leading;
    /**
     * 字符间距
     */
    protected String letterSpacing;
    /**
     * 字体名称
     */
    protected String fontFamily;
    /**
     * 字体样式
     */
    protected String fontStyle;
    /**
     * 字体大小
     */
    protected String fontSize;
    /**
     * 字体大小调整
     */
    protected String fontSizeAdjust;
    /**
     * 字体重量
     */
    protected String fontWeight;
    /**
     * 字体颜色
     */
    protected Color fontColor;
    /**
     * 删除线颜色
     */
    protected Color deleteLineColor;
    /**
     * 下划线宽度
     */
    protected String underLineWidth;
    /**
     * 下划线颜色
     */
    protected Color underLineColor;
    /**
     * 是否包含删除线
     */
    protected Boolean hasDeleteLine = Boolean.FALSE;
    /**
     * 是否包含下划线
     */
    protected Boolean hasUnderLine = Boolean.FALSE;
}
