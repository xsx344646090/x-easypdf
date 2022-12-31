package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.util.Optional;

/**
 * pdf模板-文本基础组件
 * <p>fo:block标签</p>
 *
 * @author xsx
 * @date 2022/8/5
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
abstract class XEasyPdfTemplateTextBase implements XEasyPdfTemplateComponent {

    /**
     * 初始化Block元素
     *
     * @param document fo文档
     * @param param    文本参数
     * @return 返回元素
     */
    Element initBlock(Document document, XEasyPdfTemplateTextBaseParam param) {
        // 创建block元素
        Element block = this.createBlockElement(document, param);
        // 设置行间距
        Optional.ofNullable(param.getLeading()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.LINE_HEIGHT, v.intern().toLowerCase()));
        // 设置字符间距
        Optional.ofNullable(param.getLetterSpacing()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.LETTER_SPACING, v.intern().toLowerCase()));
        // 设置单词间距
        Optional.ofNullable(param.getWordSpacing()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WORD_SPACING, v.intern().toLowerCase()));
        // 设置单词换行
        Optional.ofNullable(param.getWordBreak()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WORD_BREAK, v.intern().toLowerCase()));
        // 设置空白空间
        Optional.ofNullable(param.getWhiteSpace()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.WHITE_SPACE, v.intern().toLowerCase()));
        // 设置文本缩进
        Optional.ofNullable(param.getTextIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.TEXT_INDENT, v.intern().toLowerCase()));
        // 设置端前缩进
        Optional.ofNullable(param.getStartIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.START_INDENT, v.intern().toLowerCase()));
        // 设置端后缩进
        Optional.ofNullable(param.getEndIndent()).ifPresent(v -> block.setAttribute(XEasyPdfTemplateAttributes.END_INDENT, v.intern().toLowerCase()));
        // 如果包含删除线，则设置删除线
        if (param.getHasDeleteLine()) {
            // 设置文本装饰（删除线）
            block.setAttribute(XEasyPdfTemplateAttributes.TEXT_DECORATION, "line-through");
            // 设置删除线颜色
            Optional.ofNullable(param.getDeleteLineColor()).ifPresent(c -> XEasyPdfTemplateElementHandler.appendColor(block, c));
        }
        // 返回block元素
        return block;
    }
}
