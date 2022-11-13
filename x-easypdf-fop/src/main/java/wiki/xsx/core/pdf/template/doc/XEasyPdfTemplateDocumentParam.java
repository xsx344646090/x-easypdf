package wiki.xsx.core.pdf.template.doc;

import lombok.Data;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.doc.bookmark.XEasyPdfTemplateBookmarkComponent;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePageComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-文档参数
 *
 * @author xsx
 * @date 2022/8/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
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
class XEasyPdfTemplateDocumentParam {
    /**
     * 配置文件路径（fop配置文件路径）
     */
    private String configPath;
    /**
     * pdf模板页面列表
     */
    private final List<XEasyPdfTemplatePageComponent> pageList = new ArrayList<>(10);
    /**
     * pdf模板书签列表
     */
    private List<XEasyPdfTemplateBookmarkComponent> bookmarkList = new ArrayList<>(10);
}
