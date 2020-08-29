package wiki.xsx.core.pdf.page;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.component.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.component.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf页面参数
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
public class XEasyPdfPageParam {
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体
     */
    private PDFont font;
    /**
     * pdfBox最新页面当前X轴坐标
     */
    private Float pageX;
    /**
     * pdfBox最新页面当前Y轴坐标
     */
    private Float pageY;
    /**
     * pdfBox页面尺寸
     */
    private PDRectangle pageSize = PDRectangle.A4;
    /**
     * 包含的pdfBox页面列表
     */
    private List<PDPage> pageList = new ArrayList<>(256);
    /**
     * 新增的pdfBox页面列表
     */
    private List<PDPage> newPageList = new ArrayList<>(256);
    /**
     * pdf组件列表
     */
    private List<XEasyPdfComponent> componentList = new ArrayList<>(128);
    /**
     * 页面水印
     */
    private XEasyPdfWatermark watermark;
    /**
     * 页眉
     */
    private XEasyPdfHeader header;
    /**
     * 页脚
     */
    private XEasyPdfFooter footer;
    /**
     * 是否允许添加水印
     */
    private boolean allowWatermark = true;
    /**
     * 是否允许添加页眉
     */
    private boolean allowHeader = true;
    /**
     * 是否允许添加页脚
     */
    private boolean allowFooter = true;
    /**
     * 是否允许重置定位
     */
    private boolean allowResetPosition = true;
    /**
     * 页面背景色
     */
    private Color backgroundColor;

    /**
     * 初始化
     * @param document pdf文档
     * @param page pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.font==null) {
            this.font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath);
        }
        if (this.backgroundColor==null) {
            this.backgroundColor = document.getBackgroundColor();
        }
    }
}
