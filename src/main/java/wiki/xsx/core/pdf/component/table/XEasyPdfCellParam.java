package wiki.xsx.core.pdf.component.table;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf单元格参数
 * @author xsx
 * @date 2020/6/6
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
class XEasyPdfCellParam {
    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否组件换行
     */
    private boolean isNewLine = true;
    /**
     * 是否带有边框
     */
    private boolean hasBorder = true;
    /**
     * 背景颜色
     */
    private Color backgroundColor = Color.WHITE;
    /**
     * 边框颜色
     */
    private Color borderColor = Color.BLACK;
    /**
     * 列宽
     */
    private Float width;
    /**
     * 列高
     */
    private Float height;
    /**
     * pdf组件列表
     */
    private List<XEasyPdfComponent> componentList = new ArrayList<>(10);
    /**
     * 默认字体样式
     */
    private XEasyPdfDefaultFontStyle defaultFontStyle;
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
    private Float fontSize;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 边框宽度
     */
    private Float borderWidth = 1F;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 表格样式（居左、居中、居右）
     * 默认居左
     */
    private XEasyPdfTableStyle horizontalStyle;
    /**
     * 表格样式（居上、居中、居下）
     * 默认居上
     */
    private XEasyPdfTableStyle verticalStyle;


    /**
     * 初始化
     * @param document pdf文档
     * @param page pdf页面
     * @param row pdf表格行
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfRow row) {
        // 获取pdf表格行参数
        XEasyPdfRowParam rowParam = row.getParam();
        // 如果内容模式未初始化，则进行初始化
        if (this.contentMode==null) {
            // 初始化内容模式
            this.contentMode = rowParam.getContentMode();
        }
        // 如果字体路径为空，且默认字体样式不为空，则进行初始化字体路径
        if (this.fontPath==null&&this.defaultFontStyle!=null) {
            // 初始化字体路径
            this.fontPath = this.defaultFontStyle.getPath();
        }
        // 初始化字体
        this.font = XEasyPdfFontUtil.getFont(document, this.fontPath, rowParam.getFont());
        // 如果字体大小未初始化，则进行初始化
        if (this.fontSize==null) {
            // 初始化字体大小
            this.fontSize = rowParam.getFontSize();
        }
        // 如果水平样式未初始化，则进行初始化
        if (this.horizontalStyle ==null) {
            // 初始化水平样式
            this.horizontalStyle = rowParam.getHorizontalStyle();
        }
        // 如果垂直样式未初始化，则进行初始化
        if (this.verticalStyle ==null) {
            // 初始化垂直样式
            this.verticalStyle = rowParam.getVerticalStyle();
        }
    }
}
