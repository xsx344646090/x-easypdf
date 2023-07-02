package org.dromara.pdf.pdfbox.header;

import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;
import org.dromara.pdf.pdfbox.component.image.XEasyPdfImage;
import org.dromara.pdf.pdfbox.component.line.XEasyPdfLine;
import org.dromara.pdf.pdfbox.component.text.XEasyPdfText;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPositionStyle;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;

import java.util.Arrays;
import java.util.List;

/**
 * pdf页眉组件
 *
 * @author xsx
 * @date 2020/6/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfDefaultHeader implements XEasyPdfHeader {

    private static final long serialVersionUID = -7390695218795028439L;

    /**
     * 页眉参数
     */
    private final XEasyPdfHeaderParam param = new XEasyPdfHeaderParam();

    /**
     * 无参构造
     */
    public XEasyPdfDefaultHeader() {
    }

    /**
     * 有参构造
     *
     * @param component 自定义组件
     */
    public XEasyPdfDefaultHeader(XEasyPdfComponent component) {
        this.param.setComponent(component);
    }

    /**
     * 有参构造
     *
     * @param image pdf图片
     */
    public XEasyPdfDefaultHeader(XEasyPdfImage image) {
        this(image, null);
    }

    /**
     * 有参构造
     *
     * @param text pdf文本
     */
    public XEasyPdfDefaultHeader(XEasyPdfText text) {
        this(null, text);
    }

    /**
     * 有参构造
     *
     * @param image pdf图片
     * @param text  pdf文本
     */
    public XEasyPdfDefaultHeader(XEasyPdfImage image, XEasyPdfText text) {
        this.param.setImage(image).setText(text);
    }

    /**
     * 开启上下文重置
     *
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 添加分割线
     *
     * @param splitLine pdf分割线
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader addSplitLine(XEasyPdfLine... splitLine) {
        if (splitLine != null) {
            this.param.getLineList().addAll(Arrays.asList(splitLine));
        }
        return this;
    }

    /**
     * 添加自定义组件
     *
     * @param component pdf组件
     * @return 返回页眉组件
     */
    @Override
    public XEasyPdfDefaultHeader addComponent(XEasyPdfComponent component) {
        this.param.setComponent(component);
        return this;
    }

    /**
     * 设置边距（上左右）
     *
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
     *
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
     *
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
     *
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
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回页眉高度
     */
    @Override
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.param.getHeight() == null) {
            // 初始化参数
            this.param.init(document, page);
        }
        return this.param.getHeight();
    }

    /**
     * 获取总页码占位符
     *
     * @return 返回总页码占位符
     */
    @Override
    public String getTotalPagePlaceholder() {
        return XEasyPdfHandler.Page.getTotalPagePlaceholder();
    }

    /**
     * 获取当前页码占位符
     *
     * @return 返回当前页码占位符
     */
    @Override
    public String getCurrentPagePlaceholder() {
        return XEasyPdfHandler.Page.getCurrentPagePlaceholder();
    }

    /**
     * 获取文本字体路径
     *
     * @return 返回文本字体路径
     */
    @Override
    public String getTextFontPath() {
        if (this.param.getText() != null) {
            return this.param.getText().getFontPath();
        }
        return null;
    }

    /**
     * 检查组件
     *
     * @param component 组件
     * @return 返回布尔值，true为是，false为否
     */
    @Override
    public boolean check(XEasyPdfComponent component) {
        // 如果组件不为空，则判断对应组件
        if (component != null) {
            // 如果组件为图片，则判断图片是否相同
            if (component instanceof XEasyPdfImage) {
                // 判断是否相同
                return this.param.getImage() != null && this.param.getImage().equals(component);
            }
            // 如果组件为文本，则判断文本是否相同
            if (component instanceof XEasyPdfText) {
                // 判断是否相同
                return this.param.getText() != null && this.param.getText().equals(component);
            }
        }
        return false;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化参数
        this.param.init(document, page);
        // 如果文本不为空，则进行文本绘制
        if (this.param.getText() != null) {
            // 获取文本
            XEasyPdfText text = this.param.getText();
            // 如果开启重置上下文，则设置文本开启重置上下文
            if (this.param.getIsResetContext()) {
                // 文本开启重置上下文
                text.enableResetContext();
            }
            // 设置文本参数
            text.replaceAllPlaceholder(
                    XEasyPdfHandler.Page.getCurrentPagePlaceholder(), page.getCurrentIndex(document) + ""
            ).setCheckPage(false);
            // 设置位置并绘制文本
            text.setPosition(
                    this.param.getTextBeginX(), this.initYForText(document, page, text)
            ).enableChildComponent().draw(document, page);
        }
        // 如果图片不为空，则进行图片绘制
        if (this.param.getImage() != null) {
            // 获取图片
            XEasyPdfImage image = this.param.getImage();
            // 如果开启重置上下文，则设置图片开启重置上下文
            if (this.param.getIsResetContext()) {
                // 图片开启重置上下文
                image.enableResetContext();
            }
            // 绘制图片
            image.setContentMode(XEasyPdfComponent.ContentMode.PREPEND)
                    .setPosition(
                            this.param.getImageBeginX(),
                            this.param.getImageBeginY() - this.param.getImage().getHeight(document, page)
                    ).draw(document, page);
        }
        // 如果自定义组件不为空，则进行自定义组件绘制
        if (this.param.getComponent() != null) {
            // 绘制自定义组件
            this.param.getComponent().draw(document, page);
        }
        // 如果分割线列表不为空，则进行分割线绘制
        if (!this.param.getLineList().isEmpty()) {
            // 获取分割线列表
            List<XEasyPdfLine> lineList = this.param.getLineList();
            // 遍历分割线列表
            for (XEasyPdfLine xEasyPdfLine : lineList) {
                // 如果开启重置上下文，则设置分割线开启重置上下文
                if (this.param.getIsResetContext()) {
                    // 分割线开启重置上下文
                    xEasyPdfLine.enableResetContext();
                }
                // 绘制分割线
                xEasyPdfLine.setMarginLeft(this.param.getMarginLeft()).setMarginRight(this.param.getMarginRight()).draw(document, page);
            }
        }
        // 如果没有组件，则重置页面Y轴坐标
        if (this.param.hasNotComponent()) {
            // 重置页面Y轴坐标
            page.setPageY(page.getHeight() - this.param.getHeight());
        }
    }

    /**
     * 初始化文本Y轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param text     pdf文本
     * @return 返回Y轴起始坐标
     */
    private float initYForText(XEasyPdfDocument document, XEasyPdfPage page, XEasyPdfText text) {
        // 获取页眉高度
        float height = this.param.getHeight();
        // 获取文本高度
        float textHeight = text.getHeight(document, page);
        // 定义Y轴起始坐标为页面Y轴起始坐标+行间距/2
        float y = this.param.getTextBeginY() + text.getLeading() / 2;
        // 如果垂直样式为居上，则重置Y轴起始坐标为Y轴起始坐标-字体大小
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小
            y = y - text.getFontSize();
            return y;
        }
        // 如果垂直样式为居中，则重置Y轴起始坐标为Y轴起始坐标-字体大小-(页眉高度-文本高度)/2
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-(页眉高度-文本高度)/2
            y = y - text.getFontSize() - (height - textHeight) / 2;
            return y;
        }
        // 如果垂直样式为居下，则重置Y轴起始坐标为Y轴起始坐标-字体大小-页眉高度+文本高度
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.BOTTOM) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-单元格高度+文本高度
            y = y - text.getFontSize() - height + textHeight;
        }
        return y;
    }
}
