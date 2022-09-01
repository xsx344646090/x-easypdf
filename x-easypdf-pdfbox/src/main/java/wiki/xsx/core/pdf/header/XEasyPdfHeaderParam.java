package wiki.xsx.core.pdf.header;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.component.line.XEasyPdfLine;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf页眉组件参数
 *
 * @author xsx
 * @date 2020/6/7
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
class XEasyPdfHeaderParam implements Serializable {

    private static final long serialVersionUID = -5055961872807917576L;

    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 文本
     */
    private XEasyPdfText text;
    /**
     * 图片
     */
    private XEasyPdfImage image;
    /**
     * 自定义组件
     */
    private XEasyPdfComponent component;
    /**
     * 分割线列表
     */
    private List<XEasyPdfLine> lineList = new ArrayList<>(6);
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 右边距
     */
    private Float marginRight = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 5F;
    /**
     * 页面X轴起始坐标（图片）
     */
    private Float imageBeginX;
    /**
     * 页面Y轴起始坐标（图片）
     */
    private Float imageBeginY;
    /**
     * 页面X轴起始坐标（文本）
     */
    private Float textBeginX;
    /**
     * 页面Y轴起始坐标（文本）
     */
    private Float textBeginY;
    /**
     * 高度
     */
    private Float height;

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果重置上下文未初始化，则初始化为文档重置上下文
        if (this.isResetContext == null) {
            // 初始化为文档重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 初始化高度
        this.initHeight(document, page);
        // 获取pdfBox最新页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 获取Y轴起始坐标
        float beginY = rectangle.getHeight() - this.marginTop;
        // 如果图片不为空，则初始化图片坐标
        if (this.image != null && this.imageBeginX == null && this.imageBeginY == null) {
            // 初始化图片X轴起始坐标
            this.imageBeginX = this.marginLeft;
            // 初始化图片Y轴起始坐标
            this.imageBeginY = beginY;
        }
        // 如果文本不为空，则初始化文本坐标
        if (this.text != null && this.textBeginX == null && this.textBeginY == null) {
            // 初始化文本X轴起始坐标
            this.textBeginX = this.marginLeft;
            // 初始化文本Y轴起始坐标
            this.textBeginY = beginY;
        }
    }

    /**
     * 没有组件
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasNotComponent() {
        return this.text==null&&this.image==null&&this.component==null&&this.lineList.isEmpty();
    }

    /**
     * 初始化高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    private void initHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果高度未初始化，则进行初始化
        if (this.height == null) {
            // 定义文本高度
            float textHeight = 0F;
            // 如果文本不为空，则获取文本高度
            if (this.text != null) {
                // 获取文本高度
                textHeight = this.text.getHeight(document, page);
            }
            // 定义图片高度
            float imageHeight = 0F;
            // 如果图片不为空，则获取图片高度
            if (this.image != null) {
                // 关闭图片自适应
                this.image.disableSelfAdaption();
                // 如果自定义图片宽度为空，则设置为页面宽度
                if (this.image.getWidth(document, page) == null) {
                    // 设置为页面宽度
                    this.image.setWidth(page.getLastPage().getMediaBox().getWidth());
                }
                // 获取图片高度
                imageHeight = this.image.getHeight(document, page);
            }
            // 初始化高度，文本高度与图片高度取最大值，加上边距
            this.height = Math.max(textHeight, imageHeight) + this.marginTop;
        }
    }
}
