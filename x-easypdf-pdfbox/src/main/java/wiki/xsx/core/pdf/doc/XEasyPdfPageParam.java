package wiki.xsx.core.pdf.doc;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf页面参数
 *
 * @author xsx
 * @date 2020/4/7
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
class XEasyPdfPageParam implements Serializable {

    private static final long serialVersionUID = 3226308238459966086L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
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
     * pdfBox页面尺寸（原有尺寸）
     */
    private XEasyPdfPageRectangle originalPageSize = XEasyPdfPageRectangle.A4;
    /**
     * pdfBox页面尺寸（当前尺寸）
     */
    private XEasyPdfPageRectangle currentPageSize = XEasyPdfPageRectangle.A4;
    /**
     * pdfBox页面尺寸（手动修改）
     */
    private transient PDRectangle modifyPageSize;
    /**
     * 旋转角度
     */
    private Integer rotation;
    /**
     * 包含的pdfBox页面列表
     */
    private List<PDPage> pageList = new ArrayList<>(64);
    /**
     * 新增的pdfBox页面列表
     */
    private List<PDPage> newPageList = new ArrayList<>(64);
    /**
     * 最新页面
     */
    private transient PDPage lastPage;
    /**
     * pdf组件列表
     */
    private transient List<XEasyPdfComponent> componentList = new ArrayList<>(64);
    /**
     * 左边距
     */
    private Float marginLeft;
    /**
     * 右边距
     */
    private Float marginRight;
    /**
     * 上边距
     */
    private Float marginTop;
    /**
     * 下边距
     */
    private Float marginBottom;
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
     * 页面背景图片
     */
    private XEasyPdfImage backgroundImage;
    /**
     * 页面背景色
     */
    private Color backgroundColor;
    /**
     * 是否允许添加水印
     */
    private Boolean allowWatermark = Boolean.TRUE;
    /**
     * 是否允许添加背景图片
     */
    private Boolean allowBackgroundImage = Boolean.TRUE;
    /**
     * 是否允许添加背景色
     */
    private Boolean allowBackgroundColor = Boolean.TRUE;
    /**
     * 是否允许添加页眉
     */
    private Boolean allowHeader = Boolean.TRUE;
    /**
     * 是否允许添加页脚
     */
    private Boolean allowFooter = Boolean.TRUE;
    /**
     * 是否允许重置定位
     */
    private Boolean allowResetPosition = Boolean.TRUE;
    /**
     * 是否允许旋转固有页面
     */
    private Boolean allowRotateInherentPage = Boolean.TRUE;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 获取文档参数
        XEasyPdfDocumentParam documentParam = document.getParam();
        // 如果内容模式未初始化，则初始化为文档内容模式
        if (this.contentMode == null) {
            // 初始化为文档内容模式
            this.contentMode = documentParam.getContentMode();
        }
        // 如果重置上下文未初始化，则初始化为文档重置上下文
        if (this.isResetContext == null) {
            // 初始化为文档重置上下文
            this.isResetContext = documentParam.getIsResetContext();
        }
        // 如果字体路径未初始化，则初始化为文档字体路径
        if (this.fontPath == null) {
            // 初始化为文档字体路径
            this.fontPath = documentParam.getFontPath();
        }
        // 如果左边距未初始化，则初始化为文档左边距
        if (this.marginLeft == null) {
            // 初始化为文档左边距
            this.marginLeft = documentParam.getGlobalMarginLeft();
        }
        // 如果右边距未初始化，则初始化为文档右边距
        if (this.marginRight == null) {
            // 初始化为文档右边距
            this.marginRight = documentParam.getGlobalMarginRight();
        }
        // 如果上边距未初始化，则初始化为文档上边距
        if (this.marginTop == null) {
            // 初始化为文档上边距
            this.marginTop = documentParam.getGlobalMarginTop();
        }
        // 如果下边距未初始化，则初始化为文档下边距
        if (this.marginBottom == null) {
            // 初始化为文档下边距
            this.marginBottom = documentParam.getGlobalMarginBottom();
        }
        // 如果页面背景色未初始化，则初始化为文档背景色
        if (this.backgroundColor == null) {
            // 初始化为文档背景色
            this.backgroundColor = documentParam.getGlobalBackgroundColor();
        }
        // 如果页面背景图片未初始化，则初始化为文档背景图片
        if (this.backgroundImage == null) {
            // 初始化为文档背景图片
            this.backgroundImage = documentParam.getGlobalBackgroundImage();
        }
        // 初始化字体
        this.font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath, true);
    }
}
