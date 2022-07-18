package wiki.xsx.core.pdf.footer;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

/**
 * pdf页脚组件
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
public class XEasyPdfDefaultFooter implements XEasyPdfFooter {

    private static final long serialVersionUID = 6124740112893964108L;

    /**
     * 页脚参数
     */
    private final XEasyPdfFooterParam param = new XEasyPdfFooterParam();

    /**
     * 有参构造
     *
     * @param component 自定义组件
     */
    public XEasyPdfDefaultFooter(XEasyPdfComponent component) {
        this.param.setComponent(component);
    }

    /**
     * 有参构造
     *
     * @param image pdf图片
     */
    public XEasyPdfDefaultFooter(XEasyPdfImage image) {
        this(image, null);
    }

    /**
     * 有参构造
     *
     * @param text pdf文本
     */
    public XEasyPdfDefaultFooter(XEasyPdfText text) {
        this(null, text);
    }

    /**
     * 有参构造
     *
     * @param image pdf图片
     * @param text  pdf文本
     */
    public XEasyPdfDefaultFooter(XEasyPdfImage image, XEasyPdfText text) {
        this.param.setImage(image).setText(text);
    }

    /**
     * 开启上下文重置
     *
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 设置边距（左右下）
     *
     * @param margin 边距
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 添加自定义组件
     *
     * @param component pdf组件
     * @return 返回页脚组件
     */
    @Override
    public XEasyPdfDefaultFooter addComponent(XEasyPdfComponent component) {
        this.param.setComponent(component);
        return this;
    }

    /**
     * 获取页脚高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回页脚高度
     */
    @Override
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果高度未初始化，则初始化参数
        if (this.param.getHeight() == null) {
            // 初始化参数
            this.param.init(document, page);
        }
        // 返回高度
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
        // 关闭页面自动重置定位
        page.disablePosition();
        // 如果图片不为空，则绘制图片
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
                    .setPosition(this.param.getBeginX(), this.param.getBeginY() + this.param.getMarginBottom())
                    .draw(document, page);
        }
        // 如果文本不为空，则写入文本
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
                    this.param.getBeginX(), this.initYForText(document, page, text)
            ).enableChildComponent().draw(document, page);
            // 关闭页面自动重置定位
            page.disablePosition();
        }
        // 如果自定义组件不为空，则进行自定义组件绘制
        if (this.param.getComponent() != null) {
            // 绘制自定义组件
            this.param.getComponent().draw(document, page);
        }
        // 开启页面自动重置定位
        page.enablePosition();
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
        // 获取页脚高度
        float height = this.param.getHeight();
        // 获取文本高度
        float textHeight = text.getHeight(document, page);
        // 定义Y轴起始坐标为页面Y轴起始坐标+页脚高度+行间距/2
        float y = this.param.getBeginY() + height + text.getLeading() / 2;
        // 如果垂直样式为居上，则重置Y轴起始坐标为Y轴起始坐标-字体大小
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小
            y = y - text.getFontSize();
            return y;
        }
        // 如果垂直样式为居中，则重置Y轴起始坐标为Y轴起始坐标-字体大小-(页脚高度-文本高度)/2
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-(页脚高度-文本高度)/2
            y = y - text.getFontSize() - (height - textHeight) / 2;
            return y;
        }
        // 如果垂直样式为居下，则重置Y轴起始坐标为Y轴起始坐标-字体大小-页脚高度+文本高度
        if (text.getVerticalStyle() == XEasyPdfPositionStyle.BOTTOM) {
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-单元格高度+文本高度
            y = y - text.getFontSize() - height + textHeight;
        }
        return y;
    }
}
