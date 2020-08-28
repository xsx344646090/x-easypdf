package wiki.xsx.core.pdf.doc;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.component.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.component.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.page.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * pdf文档参数
 * @author xsx
 * @date 2020/4/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 * </p>
 */
@Data
@Accessors(chain = true)
class XEasyPdfDocumentParam {
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体
     */
    private PDFont font;
    /**
     * pdfBox文档
     */
    private PDDocument source;
    /**
     * pdfBox文档（目标文档）
     */
    private PDDocument target;
    /**
     * pdf页面列表
     */
    private List<XEasyPdfPage> pageList = new ArrayList<>(256);
    /**
     * pdf表单字典
     */
    private Map<String, String> formMap;
    /**
     * 全局页面水印
     */
    private XEasyPdfWatermark globalWatermark;
    /**
     * 全局页眉
     */
    private XEasyPdfHeader globalHeader;
    /**
     * 全局页脚
     */
    private XEasyPdfFooter globalFooter;
    /**
     * pdf文档权限
     */
    private XEasyPdfDocumentPermission permission;
    /**
     * pdf文档信息
     */
    private XEasyPdfDocumentInfo documentInfo;
    /**
     * 是否重置
     */
    private boolean isReset;
    /**
     * 文档背景色
     */
    private Color backgroundColor = Color.WHITE;

    /**
     * 初始化字体
     * @param document pdf文档
     */
    void initFont(XEasyPdfDocument document) {
        if (this.fontPath!=null&&this.font==null) {
            this.font = XEasyPdfFontUtil.loadFont(document, this.fontPath);
        }
    }

    /**
     * 初始化文档信息
     */
    void initInfo(XEasyPdfDocument document) {
        if (this.source!=null) {
            PDDocumentInformation documentInformation = this.source.getDocumentInformation();
            this.documentInfo = new XEasyPdfDocumentInfo(document)
                    .setTitle(documentInformation.getTitle())
                    .setAuthor(documentInformation.getAuthor())
                    .setSubject(documentInformation.getSubject())
                    .setKeywords(documentInformation.getKeywords())
                    .setCreator(documentInformation.getCreator())
                    .setCreateTime(documentInformation.getCreationDate())
                    .setUpdateTime(documentInformation.getModificationDate());
        }
    }
}
