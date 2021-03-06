package wiki.xsx.core.pdf.header;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.component.line.XEasyPdfLine;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

import java.util.Arrays;
import java.util.List;

/**
 * pdf页眉组件
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
public class XEasyPdfDefaultHeader implements XEasyPdfHeader{

    /**
     * 页眉参数
     */
    private final XEasyPdfHeaderParam param = new XEasyPdfHeaderParam();

    /**
     * 有参构造
     * @param image pdf图片
     */
    public XEasyPdfDefaultHeader(XEasyPdfImage image) {
        this(image, null);
    }

    /**
     * 有参构造
     * @param text pdf文本
     */
    public XEasyPdfDefaultHeader(XEasyPdfText text) {
        this(null, text);
    }

    /**
     * 有参构造
     * @param image pdf图片
     * @param text pdf文本
     */
    public XEasyPdfDefaultHeader(XEasyPdfImage image, XEasyPdfText text) {
        this.param.setImage(image).setText(text);
    }

    /**
     * 添加分割线
     * @param splitLine pdf分割线
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader addSplitLine(XEasyPdfLine ...splitLine) {
        if (splitLine!=null) {
            this.param.getLineList().addAll(Arrays.asList(splitLine));
        }
        return this;
    }

    /**
     * 设置边距（上左右）
     * @param margin 边距
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin);
        return this;
    }

    /**
     * 设置左边距
     * @param margin 边距
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     * @param margin 边距
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     * @param margin 边距
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 获取页眉高度
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回页眉高度
     */
    @Override
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.param.getHeight()==null) {
            // 初始化参数
            this.param.init(document, page);
        }
        return this.param.getHeight();
    }

    @Override
    public boolean check(XEasyPdfComponent component) {
        if (component!=null) {
            if (component instanceof XEasyPdfImage) {
                return this.param.getImage()!=null&&this.param.getImage().equals(component);
            }
            if (component instanceof XEasyPdfText) {
                return this.param.getText()!=null&&this.param.getText().equals(component);
            }
        }
        return false;
    }

    /**
     * 绘制
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化参数
        this.param.init(document, page);
        // 如果文本不为空，则进行文本绘制
        if (this.param.getText()!=null) {
            // 获取文本
            XEasyPdfText text = this.param.getText();
            // 设置文本参数
            text.replaceAllPlaceholder(
                    XEasyPdfHandler.Page.getCurrentPagePlaceholder(), page.getCurrentIndex(document)+""
            ).setCheckPage(false);
            // 设置位置并绘制文本
            text.setPosition(
                    this.param.getTextBeginX(), this.initYForText(document, page, text)
            ).draw(document, page);
        }
        // 如果图片不为空，则进行图片绘制
        if (this.param.getImage()!=null) {
            // 绘制图片
            this.param.getImage()
                    .setContentMode(XEasyPdfComponent.ContentMode.PREPEND)
                    .setPosition(
                            this.param.getImageBeginX(),
                            this.param.getImageBeginY()-this.param.getImage().getHeight(document, page)
                    ).draw(document, page);
        }
        // 如果分割线列表不为空，则进行分割线绘制
        if (!this.param.getLineList().isEmpty()) {
            // 获取分割线列表
            List<XEasyPdfLine> lineList = this.param.getLineList();
            // 遍历分割线列表
            for (XEasyPdfLine xEasyPdfLine : lineList) {
                // 绘制分割线
                xEasyPdfLine.setMarginLeft(this.param.getMarginLeft()).setMarginRight(this.param.getMarginRight()).draw(document, page);
            }
        }
    }

    /**
     * 初始化文本Y轴起始坐标
     * @param document pdf文档
     * @param page pdf页面
     * @param text pdf文本
     * @return 返回Y轴起始坐标
     */
    private float initYForText(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfText text) {
        // 获取页眉高度
        float height = this.param.getHeight();
        // 获取文本高度
        float textHeight = text.getHeight(document, page);
        // 定义Y轴起始坐标为页面Y轴起始坐标+行间距/2
        float y = this.param.getTextBeginY() + text.getLeading()/2;
        // 如果垂直样式为居上，则重置Y轴起始坐标为Y轴起始坐标-字体大小
        if (text.getVerticalStyle()== XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小
            y = y - text.getFontSize();
            return y;
        }
        // 如果垂直样式为居中，则重置Y轴起始坐标为Y轴起始坐标-字体大小-(页眉高度-文本高度)/2
        if (text.getVerticalStyle()== XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-(页眉高度-文本高度)/2
            y = y - text.getFontSize() - (height - textHeight) / 2;
            return y;
        }
        // 如果垂直样式为居下，则重置Y轴起始坐标为Y轴起始坐标-字体大小-页眉高度+文本高度
        if (text.getVerticalStyle()==XEasyPdfPositionStyle.BOTTOM) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-单元格高度+文本高度
            y = y - text.getFontSize() - height + textHeight;
        }
        return y;
    }
}
