package org.dromara.pdf.fop.core.doc.component.text;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-文本基础组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/8/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
abstract class TextBase implements Component {

    /**
     * 初始化Block元素
     *
     * @param document fo文档
     * @param param    文本参数
     * @return 返回元素
     */
    Element initBlock(Document document, TextBaseParam param) {
        // 创建block元素
        Element block = this.createBlockElement(document, param);
        // 设置行间距
        Optional.ofNullable(param.getLeading()).ifPresent(v -> block.setAttribute(TemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(param.getLetterSpacing()).ifPresent(v -> block.setAttribute(TemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(param.getWordSpacing()).ifPresent(v -> block.setAttribute(TemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(param.getWhiteSpace()).ifPresent(v -> block.setAttribute(TemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置空白空间折叠
        Optional.ofNullable(param.getWhiteSpaceCollapse()).ifPresent(v -> block.setAttribute(TemplateAttributes.WHITE_SPACE_COLLAPSE, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(param.getTextIndent()).ifPresent(v -> block.setAttribute(TemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置端前缩进
        Optional.ofNullable(param.getStartIndent()).ifPresent(v -> block.setAttribute(TemplateAttributes.START_INDENT, v.intern().toLowerCase()));
        // 设置端后缩进
        Optional.ofNullable(param.getEndIndent()).ifPresent(v -> block.setAttribute(TemplateAttributes.END_INDENT, v.intern().toLowerCase()));
        // 如果包含删除线，则设置删除线
        if (param.getHasDeleteLine()) {
            // 设置文本装饰（删除线）
            block.setAttribute(TemplateAttributes.TEXT_DECORATION, "line-through");
            // 设置删除线颜色
            Optional.ofNullable(param.getDeleteLineColor()).ifPresent(v -> block.setAttribute(TemplateAttributes.COLOR, v.intern().toLowerCase()));
        }
        // 返回block元素
        return block;
    }
}
