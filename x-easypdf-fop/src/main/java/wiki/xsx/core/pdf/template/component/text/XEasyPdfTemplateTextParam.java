package wiki.xsx.core.pdf.template.component.text;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * pdf模板-文本参数
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
class XEasyPdfTemplateTextParam extends XEasyPdfTemplateTextBaseParam {
    /**
     * 文本
     */
    private String text;
    /**
     * 垂直对齐
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     */
    private String verticalAlign;

    /**
     * 初始化
     *
     * @param param 文本基础参数
     */
    void init(XEasyPdfTemplateTextBaseParam param) {
        if (this.getLanguage() == null) {
            this.setLanguage(param.getLanguage());
        }
        if (this.getLeading() == null) {
            this.setLeading(param.getLeading());
        }
        if (this.getLetterSpacing() == null) {
            this.setLetterSpacing(param.getLetterSpacing());
        }
        if (this.getFontFamily() == null) {
            this.setFontFamily(param.getFontFamily());
        }
        if (this.getFontStyle() == null) {
            this.setFontStyle(param.getFontStyle());
        }
        if (this.getFontSize() == null) {
            this.setFontSize(param.getFontSize());
        }
        if (this.getFontSizeAdjust() == null) {
            this.setFontSizeAdjust(param.getFontSizeAdjust());
        }
        if (this.getFontWeight() == null) {
            this.setFontWeight(param.getFontWeight());
        }
        if (this.getFontColor() == null) {
            this.setFontColor(param.getFontColor());
        }
        if (this.getHasBorder() == null) {
            this.setHasBorder(param.getHasBorder());
        }
    }
}
