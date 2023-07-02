package org.dromara.pdf.fop.doc.component.text;

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
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
        // 如果语言未初始化，则初始化
        if (this.getLanguage() == null) {
            // 初始化语言
            this.setLanguage(param.getLanguage());
        }
        // 如果行间距未初始化，则初始化
        if (this.getLeading() == null) {
            // 初始化行间距
            this.setLeading(param.getLeading());
        }
        // 如果字符间距未初始化，则初始化
        if (this.getLetterSpacing() == null) {
            // 初始化字符间距
            this.setLetterSpacing(param.getLetterSpacing());
        }
        // 如果字体名称未初始化，则初始化
        if (this.getFontFamily() == null) {
            // 初始化字体名称
            this.setFontFamily(param.getFontFamily());
        }
        // 如果字体样式未初始化，则初始化
        if (this.getFontStyle() == null) {
            // 初始化字体样式
            this.setFontStyle(param.getFontStyle());
        }
        // 如果字体大小未初始化，则初始化
        if (this.getFontSize() == null) {
            // 初始化字体大小
            this.setFontSize(param.getFontSize());
        }
        // 如果字体大小调整未初始化，则初始化
        if (this.getFontSizeAdjust() == null) {
            // 初始化字体大小调整
            this.setFontSizeAdjust(param.getFontSizeAdjust());
        }
        // 如果字体重量未初始化，则初始化
        if (this.getFontWeight() == null) {
            // 初始化字体重量
            this.setFontWeight(param.getFontWeight());
        }
        // 如果字体颜色未初始化，则初始化
        if (this.getColor() == null) {
            // 初始化字体颜色
            this.setColor(param.getColor());
        }
        // 如果是否包含边框未初始化，则初始化
        if (this.getHasBorder() == null) {
            // 初始化是否包含边框
            this.setHasBorder(param.getHasBorder());
        }
    }
}
