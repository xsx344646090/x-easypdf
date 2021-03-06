package wiki.xsx.core.pdf.mark;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;

/**
 * pdf页面水印参数
 * @author xsx
 * @date 2020/3/25
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
class XEasyPdfWatermarkParam {
    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode = XEasyPdfComponent.ContentMode.APPEND;
    /**
     * 默认字体样式
     */
    private XEasyPdfDefaultFontStyle defaultFontStyle = XEasyPdfDefaultFontStyle.NORMAL;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体
     */
    private PDFont font;
    /**
     * 字体大小
     */
    private Float fontSize = 50F;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 透明度（值越小越透明，0.0-1.0）
     */
    private Float alpha = 0.2F;
    /**
     * 文本弧度
     */
    private Double radians = 30D;
    /**
     * 水印文本
     */
    private String text;
    /**
     * 文本间隔
     */
    private Float wordSpace;
    /**
     * 文本行数
     */
    private Integer wordLine = 8;
    /**
     * 文本行间距
     */
    private Float leading;
    /**
     * 是否需要初始化
     */
    private boolean isNeedInit = true;

    /**
     * 初始化
     * @param document pdf文档
     * @param page pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果字体路径为空，则进行初始化字体路径
        if (this.fontPath==null) {
            // 初始化字体路径
            this.fontPath = this.defaultFontStyle.getPath();
        }
        // 初始化字体
        this.font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath, true);
        // 如果文本间隔未初始化，则进行初始化
        if (this.wordSpace==null) {
            // 初始化文本间隔为6倍字体大小
            this.wordSpace = this.fontSize * 6;
        }
        // 如果文本行间距未初始化，则进行初始化
        if (this.leading==null) {
            // 初始化文本行间距为2倍字体大小
            this.leading = this.fontSize * 2;
        }
        // 是否需要初始化为false
        this.isNeedInit = false;
    }
}
