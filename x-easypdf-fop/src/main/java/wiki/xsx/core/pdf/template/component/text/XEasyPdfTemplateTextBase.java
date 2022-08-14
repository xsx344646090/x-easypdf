package wiki.xsx.core.pdf.template.component.text;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;

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
        // 如果行间距不为空，则设置行间距
        if (param.getLeading() != null) {
            // 设置行间距
            block.setAttribute("line-height", param.getLeading());
        }
        // 如果字符间距不为空，则设置字符间距
        if (param.getLetterSpacing() != null) {
            // 设置字符间距
            block.setAttribute("letter-spacing", param.getLetterSpacing());
        }
        // 返回block元素
        return block;
    }
}
