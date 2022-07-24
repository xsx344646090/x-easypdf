package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfWatermark;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * pdf页面
 *
 * @author xsx
 * @date 2020/3/9
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
public class XEasyPdfPage implements Serializable {

    private static final long serialVersionUID = 2960592445501222343L;

    /**
     * pdf页面参数
     */
    private final XEasyPdfPageParam param = new XEasyPdfPageParam();

    /**
     * 有参构造
     *
     * @param page pdfBox页面
     */
    XEasyPdfPage(PDPage page) {
        // 如果pdfbox页面不为空，则添加页面
        if (page != null) {
            // 重置最新页面
            this.param.setLastPage(page);
            // 重置页面尺寸（原有尺寸）
            this.param.setOriginalPageSize(new XEasyPdfPageRectangle(page.getMediaBox()));
            // 重置页面尺寸（当前尺寸）
            this.param.setCurrentPageSize(new XEasyPdfPageRectangle(page.getTrimBox()));
            // 添加pdfBox页面
            this.param.getPageList().add(page);
        }
    }

    /**
     * 有参构造
     *
     * @param pageSize pdf页面尺寸
     */
    public XEasyPdfPage(XEasyPdfPageRectangle pageSize) {
        // 如果pdfBox页面尺寸不为空，则设置pdfBox页面尺寸
        if (pageSize != null) {
            // 重置最新页面
            this.param.setLastPage(new PDPage(pageSize.getSize()));
            // 重置页面尺寸（原有尺寸）
            this.param.setOriginalPageSize(pageSize);
            // 重置页面尺寸（当前尺寸）
            this.param.setCurrentPageSize(pageSize);
        }
        // 否则使用A4尺寸重置最新页面
        else {
            // 使用A4尺寸重置最新页面
            this.param.setLastPage(new PDPage(XEasyPdfPageRectangle.A4.getSize()));
        }
    }

    /**
     * 设置旋转角度
     *
     * @param rotation 旋转角度
     * @return 返回pdf页面
     */
    public XEasyPdfPage setRotation(Rotation rotation) {
        this.param.setRotation(rotation.rotation);
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回pdf页面
     */
    public XEasyPdfPage setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 获取页面字体路径
     *
     * @return 返回页面字体路径
     */
    public String getFontPath() {
        return this.param.getFontPath();
    }

    /**
     * 获取页面字体
     *
     * @return 返回pdfBox字体
     */
    public PDFont getFont() {
        return this.param.getFont();
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回pdf页面
     */
    public XEasyPdfPage setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置页面X轴坐标
     *
     * @param pageX X轴坐标
     * @return 返回pdf页面
     */
    public XEasyPdfPage setPageX(Float pageX) {
        this.param.setPageX(pageX);
        return this;
    }

    /**
     * 获取页面X轴坐标
     *
     * @return 返回页面X轴坐标
     */
    public Float getPageX() {
        return this.param.getPageX();
    }

    /**
     * 设置页面X轴坐标
     *
     * @param pageY Y轴坐标
     * @return 返回pdf页面
     */
    public XEasyPdfPage setPageY(Float pageY) {
        this.param.setPageY(pageY);
        return this;
    }

    /**
     * 获取页面Y轴坐标
     *
     * @return 返回页面Y轴坐标
     */
    public Float getPageY() {
        return this.param.getPageY();
    }

    /**
     * 设置内容模式
     *
     * @param contentMode 内容模式
     * @return 返回返回pdf页面
     */
    public XEasyPdfPage setContentMode(XEasyPdfComponent.ContentMode contentMode) {
        if (contentMode != null) {
            this.param.setContentMode(contentMode);
        }
        return this;
    }

    /**
     * 获取内容模式
     *
     * @return 返回内容模式
     */
    public XEasyPdfComponent.ContentMode getContentMode() {
        return this.param.getContentMode();
    }

    /**
     * 设置页面背景图片
     *
     * @param backgroundImage 背景图片
     * @return 返回pdf页面
     */
    public XEasyPdfPage setBackgroundImage(XEasyPdfImage backgroundImage) {
        this.param.setBackgroundImage(backgroundImage);
        return this;
    }

    /**
     * 获取页面背景图片
     *
     * @return 返回pdf页面
     */
    public XEasyPdfImage getBackgroundImage() {
        return this.param.getBackgroundImage();
    }

    /**
     * 设置页面背景色
     *
     * @param backgroundColor 背景色
     * @return 返回pdf页面
     */
    public XEasyPdfPage setBackgroundColor(Color backgroundColor) {
        this.param.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 获取页面背景色
     *
     * @return 返回页面背景色
     */
    public Color getBackgroundColor() {
        return this.param.getBackgroundColor();
    }

    /**
     * 设置水印
     *
     * @param watermark pdf水印
     * @return 返回pdf页面
     */
    public XEasyPdfPage setWatermark(XEasyPdfWatermark watermark) {
        this.param.setWatermark(watermark);
        return this;
    }

    /**
     * 获取水印
     *
     * @return 返回pdf水印
     */
    public XEasyPdfWatermark getWatermark() {
        return this.param.getWatermark();
    }

    /**
     * 设置页眉
     *
     * @param header pdf页眉
     * @return 返回pdf页面
     */
    public XEasyPdfPage setHeader(XEasyPdfHeader header) {
        this.param.setHeader(header);
        return this;
    }

    /**
     * 获取页眉
     *
     * @return 返回pdf页眉
     */
    public XEasyPdfHeader getHeader() {
        return this.param.getHeader();
    }

    /**
     * 设置页脚
     *
     * @param footer pdf页脚
     * @return 返回pdf页面
     */
    public XEasyPdfPage setFooter(XEasyPdfFooter footer) {
        this.param.setFooter(footer);
        return this;
    }

    /**
     * 获取页脚
     *
     * @return 返回pdf页脚
     */
    public XEasyPdfFooter getFooter() {
        return this.param.getFooter();
    }

    /**
     * 获取每毫米像素点
     *
     * @return 返回每毫米像素点
     */
    public float getUnit() {
        return XEasyPdfPageRectangle.getUnit();
    }

    /**
     * 获取当前页面宽度
     *
     * @return 返回当前页面宽度
     */
    public float getWidth() {
        return this.param.getCurrentPageSize().getWidth();
    }

    /**
     * 获取当前页面高度
     *
     * @return 返回当前页面高度
     */
    public float getHeight() {
        return this.param.getCurrentPageSize().getHeight();
    }

    /**
     * 获取当前页面尺寸
     *
     * @return 返回当前页面尺寸
     */
    public XEasyPdfPageRectangle getRectangle() {
        return this.param.getCurrentPageSize();
    }

    /**
     * 获取当前页面尺寸X轴左坐标
     *
     * @return 返回当前页面尺寸X轴左坐标
     */
    public float getRectangleLeftX() {
        return this.param.getCurrentPageSize().getLeftX();
    }

    /**
     * 获取当前页面尺寸X轴右坐标
     *
     * @return 返回当前页面尺寸X轴右坐标
     */
    public float getRectangleRightX() {
        return this.param.getCurrentPageSize().getRightX();
    }

    /**
     * 获取当前页面尺寸Y轴下坐标
     *
     * @return 返回当前页面尺寸Y轴下坐标
     */
    public float getRectangleBottomY() {
        return this.param.getCurrentPageSize().getBottomY();
    }

    /**
     * 获取当前页面尺寸Y轴上坐标
     *
     * @return 返回当前页面尺寸Y轴上坐标
     */
    public float getRectangleTopY() {
        return this.param.getCurrentPageSize().getTopY();
    }

    /**
     * 获取原有页面宽度
     *
     * @return 返回原有页面宽度
     */
    public float getOriginalWidth() {
        return this.param.getOriginalPageSize().getWidth();
    }

    /**
     * 获取原有页面高度
     *
     * @return 返回原有页面高度
     */
    public float getOriginalHeight() {
        return this.param.getOriginalPageSize().getHeight();
    }

    /**
     * 获取原有页面尺寸
     *
     * @return 返回原有页面尺寸
     */
    public XEasyPdfPageRectangle getOriginalRectangle() {
        return this.param.getOriginalPageSize();
    }

    /**
     * 获取原有页面尺寸X轴左坐标
     *
     * @return 返回当前页面尺寸X轴左坐标
     */
    public float getOriginalRectangleLeftX() {
        return this.param.getOriginalPageSize().getLeftX();
    }

    /**
     * 获取原有页面尺寸X轴右坐标
     *
     * @return 返回当前页面尺寸X轴右坐标
     */
    public float getOriginalRectangleRightX() {
        return this.param.getOriginalPageSize().getRightX();
    }

    /**
     * 获取原有页面尺寸Y轴下坐标
     *
     * @return 返回当前页面尺寸Y轴下坐标
     */
    public float getOriginalRectangleBottomY() {
        return this.param.getOriginalPageSize().getBottomY();
    }

    /**
     * 获取原有页面尺寸Y轴上坐标
     *
     * @return 返回当前页面尺寸Y轴上坐标
     */
    public float getOriginalRectangleTopY() {
        return this.param.getOriginalPageSize().getTopY();
    }

    /**
     * 获取pdfBox最新页面
     *
     * @return 返回pdfBox最新页面
     */
    public PDPage getLastPage() {
        return this.param.getLastPage();
    }

    /**
     * 获取当前页面索引
     *
     * @return 返回当前页面索引
     */
    public int getCurrentIndex(XEasyPdfDocument document) {
        return document.getParam().getTotalPage();
    }

    /**
     * 获取包含的pdfBox页面列表
     *
     * @return 返回包含的pdfBox页面列表
     */
    public List<PDPage> getPageList() {
        return this.param.getPageList();
    }

    /**
     * 获取新增的pdfBox页面列表
     *
     * @return 返回新增的pdfBox页面列表
     */
    public List<PDPage> getNewPageList() {
        return this.param.getNewPageList();
    }

    /**
     * 关闭背景色
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableBackgroundColor() {
        this.param.setAllowBackgroundColor(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭背景图片
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableBackgroundImage() {
        this.param.setAllowBackgroundImage(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭水印
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableWatermark() {
        this.param.setAllowWatermark(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭页眉
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableHeader() {
        this.param.setAllowHeader(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭页脚
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableFooter() {
        this.param.setAllowFooter(Boolean.FALSE);
        return this;
    }

    /**
     * 关闭旋转固有页面
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disableRotateInherentPage() {
        this.param.setAllowRotateInherentPage(Boolean.FALSE);
        return this;
    }

    /**
     * 开启页面自动重置定位
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage enablePosition() {
        this.param.setAllowResetPosition(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭页面自动重置定位
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage disablePosition() {
        this.param.setAllowResetPosition(Boolean.FALSE);
        return this;
    }

    /**
     * 开启重置上下文
     *
     * @return 返回pdf页面
     */
    public XEasyPdfPage enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 是否允许重置定位
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowResetPosition() {
        return this.param.getAllowResetPosition();
    }

    /**
     * 是否允许添加页眉
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowHeader() {
        return this.param.getAllowHeader();
    }

    /**
     * 是否允许添加页脚
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowFooter() {
        return this.param.getAllowFooter();
    }

    /**
     * 是否允许添加水印
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowWatermark() {
        return this.param.getAllowWatermark();
    }

    /**
     * 是否允许添加背景图片
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowBackgroundImage() {
        return this.param.getAllowBackgroundImage();
    }

    /**
     * 是否允许添加背景色
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowBackgroundColor() {
        return this.param.getAllowBackgroundColor();
    }

    /**
     * 是否允许旋转固有页面
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isAllowRotateInherentPage() {
        return this.param.getAllowRotateInherentPage();
    }

    /**
     * 是否重置上下文
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean isResetContext() {
        return this.param.getIsResetContext();
    }

    /**
     * 添加新页面
     *
     * @param pageSize 页面尺寸
     * @param document pdf文档
     * @return 返回pdf页面
     */
    public XEasyPdfPage addNewPage(XEasyPdfDocument document, PDRectangle pageSize) {
        // 定义pdfBox页面
        PDPage page = pageSize == null ? new PDPage(this.param.getOriginalPageSize().getSize()) : new PDPage(pageSize);
        // 如果旋转角度不为空，则设置旋转角度
        if (this.param.getRotation() != null) {
            // 设置旋转角度
            page.setRotation(this.param.getRotation());
        }
        // 重置最新页面
        this.param.setLastPage(page);
        // 添加pdfBox页面，如果页面尺寸为空，则添加默认A4页面，否则添加所给尺寸页面
        this.param.getNewPageList().add(page);
        // 初始化总页数
        document.getParam().initTotalPage(1);
        // 重置页面X轴Y轴起始坐标
        this.param.setPageX(null).setPageY(null);
        // 绘制页眉与页脚
        this.drawHeaderAndFooter(document);
        // 绘制背景图片
        this.drawBackgroundImage(document);
        // 设置背景颜色
        this.setLastPageBackgroundColor(document);
        return this;
    }

    /**
     * 添加pdf组件
     *
     * @param components pdf组件
     * @return 返回pdf页面
     */
    public XEasyPdfPage addComponent(XEasyPdfComponent... components) {
        // 如果组件不为空，则添加组件
        if (components != null) {
            // 添加组件
            Collections.addAll(this.param.getComponentList(), components);
        }
        return this;
    }

    /**
     * 添加pdf组件
     *
     * @param components pdf组件列表
     * @return 返回pdf页面
     */
    public XEasyPdfPage addComponent(List<XEasyPdfComponent> components) {
        // 如果组件不为空，则添加组件
        if (components != null && !components.isEmpty()) {
            // 添加组件
            this.param.getComponentList().addAll(components);
        }
        return this;
    }

    /**
     * 修改页面尺寸
     *
     * @param pageSize pdf页面尺寸
     * @return 返回pdf页面
     */
    public XEasyPdfPage modifyPageSize(XEasyPdfPageRectangle pageSize) {
        // 如果页面尺寸不为空，则修改页面尺寸
        if (pageSize != null) {
            // 设置当前页面尺寸
            this.param.setCurrentPageSize(pageSize);
            // 设置原有页面尺寸
            this.param.setModifyPageSize(pageSize.getSize());
        }
        return this;
    }

    /**
     * 构建pdf页面
     *
     * @param document pdf文档
     */
    void build(XEasyPdfDocument document) {
        this.build(document, this.param.getOriginalPageSize());
    }

    /**
     * 构建pdf页面
     *
     * @param document pdf文档
     * @param pageSize 页面尺寸
     */
    void build(XEasyPdfDocument document, XEasyPdfPageRectangle pageSize) {
        // 初始化参数
        this.param.init(document, this);
        // 如果原有pdfbox页面列表为空，则添加新页面，否则设置页面尺寸
        if (this.param.getPageList().isEmpty()) {
            // 添加新页面
            this.addNewPage(document, pageSize.getSize());
            // 如果原有pdfbox页面列表不为空，则设置页面尺寸
        } else {
            // 获取页面尺寸
            PDRectangle modifyPageSize = this.param.getModifyPageSize();
            // 如果页面尺寸不为空，则进行设置
            if (modifyPageSize != null) {
                // 获取原有pdfbox页面列表
                List<PDPage> pageList = this.param.getPageList();
                // 遍历pdfbox页面列表
                for (PDPage page : pageList) {
                    // 修改页面尺寸
                    this.modifyPageSize(page, modifyPageSize);
                }
            }
        }
        // 获取pdf组件列表
        List<XEasyPdfComponent> componentList = this.param.getComponentList();
        // 如果组件列表数量大于0，则进行组件绘制
        if (!componentList.isEmpty()) {
            // 遍历组件列表
            for (XEasyPdfComponent component : componentList) {
                // 组件不为空，则进行绘制
                if (component != null) {
                    // 组件绘制
                    component.draw(document, this);
                }
            }
        }
        // 绘制水印
        this.drawWatermark(document);
    }

    /**
     * 修改页面尺寸
     *
     * @param page     pdfbox页面
     * @param pageSize pdfbox页面尺寸
     */
    private void modifyPageSize(PDPage page, PDRectangle pageSize) {
        // 设置页面尺寸
        page.setCropBox(pageSize);
        // 如果允许旋转固有页面且旋转角度不为空，则设置旋转角度
        if (this.param.getAllowRotateInherentPage() && this.param.getRotation() != null) {
            // 设置旋转角度
            page.setRotation(this.param.getRotation());
        }
    }

    /**
     * 设置最新页面背景颜色
     *
     * @param document pdf文档
     */
    @SneakyThrows
    private void setLastPageBackgroundColor(XEasyPdfDocument document) {
        // 如果当前pdf页面允许添加页面背景色，则进行页面背景色绘制
        if (this.param.getAllowBackgroundColor()) {
            // 如果背景颜色不为白色，则进行背景颜色设置
            if (!Color.WHITE.equals(this.param.getBackgroundColor())) {
                // 获取pdfBox最新页面
                PDPage lastPage = this.getLastPage();
                // 如果最新页面不为空，则进行背景颜色设置
                if (lastPage != null) {
                    // 获取页面尺寸
                    PDRectangle rectangle = lastPage.getMediaBox();
                    // 新建内容流
                    PDPageContentStream contentStream = new PDPageContentStream(
                            document.getTarget(),
                            lastPage,
                            PDPageContentStream.AppendMode.PREPEND,
                            true,
                            this.param.getIsResetContext()
                    );
                    // 绘制矩形（背景矩形）
                    contentStream.addRect(
                            0,
                            0,
                            rectangle.getWidth(),
                            rectangle.getHeight()
                    );
                    // 设置矩形颜色（背景颜色）
                    contentStream.setNonStrokingColor(this.param.getBackgroundColor());
                    // 填充矩形（背景矩形）
                    contentStream.fill();
                    // 内容流重置颜色为黑色
                    contentStream.setNonStrokingColor(Color.BLACK);
                    // 关闭内容流
                    contentStream.close();
                }
            }
        }
    }

    /**
     * 绘制背景图片
     *
     * @param document pdf文档
     */
    private void drawBackgroundImage(XEasyPdfDocument document) {
        // 如果当前pdf页面允许添加页面背景图片，则进行页面背景图片绘制
        if (this.param.getAllowBackgroundImage()) {
            // 如果页面背景图片不为空，则进行绘制
            if (this.param.getBackgroundImage() != null) {
                // 获取页面X轴坐标
                Float pageX = this.param.getPageX();
                // 获取页面Y轴坐标
                Float pageY = this.param.getPageY();
                // 设置页面X轴Y轴坐标为空
                this.param.setPageX(null).setPageY(null);
                // 关闭页面自动重置定位
                this.disablePosition();
                // 绘制页面背景图片
                this.param.getBackgroundImage().setContentMode(XEasyPdfComponent.ContentMode.PREPEND).draw(document, this);
                // 开启页面自动重置定位
                this.enablePosition();
                // 还原页面X轴Y轴坐标
                this.param.setPageX(pageX).setPageY(pageY);
            }
        }
    }

    /**
     * 绘制页眉与页脚
     *
     * @param document pdf文档
     */
    private void drawHeaderAndFooter(XEasyPdfDocument document) {
        // 绘制页眉
        this.drawHeader(document);
        // 绘制页脚
        this.drawFooter(document);
    }

    /**
     * 绘制页眉
     *
     * @param document pdf文档
     */
    private void drawHeader(XEasyPdfDocument document) {
        // 如果当前pdf页面允许添加页眉，则进行页眉绘制
        if (this.param.getAllowHeader()) {
            // 如果页眉未初始化，则设置全局页眉
            if (this.param.getHeader() == null) {
                // 设置全局页眉
                this.param.setHeader(document.getGlobalHeader());
            }
            // 如果页眉不为空，则进行绘制
            if (this.param.getHeader() != null) {
                // 绘制页眉
                this.param.getHeader().draw(document, this);
            }
        }
    }

    /**
     * 绘制页脚
     *
     * @param document pdf文档
     */
    private void drawFooter(XEasyPdfDocument document) {
        // 如果当前pdf页面允许添加页脚，则进行页脚绘制
        if (this.param.getAllowFooter()) {
            // 如果页脚未初始化，则设置全局页脚
            if (this.param.getFooter() == null) {
                // 设置全局页脚
                this.param.setFooter(document.getGlobalFooter());
            }
            // 如果页脚不为空，则进行绘制
            if (this.param.getFooter() != null) {
                // 绘制页脚
                this.param.getFooter().draw(document, this);
            }
        }
    }

    /**
     * 绘制水印
     *
     * @param document pdf文档
     */
    private void drawWatermark(XEasyPdfDocument document) {
        // 如果当前pdf页面允许添加页面水印，则进行页面水印绘制
        if (this.param.getAllowWatermark()) {
            // 获取页面水印
            XEasyPdfWatermark watermark = this.param.getWatermark();
            // 如果页面水印未初始化，则重置为全局页面水印
            if (watermark == null) {
                // 重置为全局页面水印
                watermark = document.getGlobalWatermark();
            }
            // 如果页面水印不为空，则进行绘制
            if (watermark != null) {
                // 绘制水印
                watermark.draw(document, this);
            }
        }
    }

    /**
     * 旋转角度
     */
    public enum Rotation {
        /**
         * 正向0度
         */
        FORWARD_0(0),
        /**
         * 正向90度
         */
        FORWARD_90(90),
        /**
         * 正向180度
         */
        FORWARD_180(180),
        /**
         * 正向270度
         */
        FORWARD_270(270);

        /**
         * 旋转角度
         */
        private final int rotation;

        /**
         * 有参构造
         *
         * @param rotation 旋转角度
         */
        Rotation(int rotation) {
            this.rotation = rotation;
        }
    }
}
