package wiki.xsx.core.pdf.component.image;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.util.XEasyPdfImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * pdf图片组件
 *
 * @author xsx
 * @date 2020/3/30
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
@EqualsAndHashCode
public class XEasyPdfImage implements XEasyPdfComponent {

    private static final long serialVersionUID = -5646605850827547633L;

    /**
     * pdf图片参数
     */
    private final XEasyPdfImageParam param = new XEasyPdfImageParam();

    /**
     * 无参构造
     */
    XEasyPdfImage(){}

    /**
     * 有参构造
     *
     * @param imageFile 待添加图片
     */
    @SneakyThrows
    public XEasyPdfImage(File imageFile) {
        this.param.setImageType(XEasyPdfImageUtil.parseType(imageFile)).setImage(XEasyPdfImageUtil.read(imageFile));
    }

    /**
     * 有参构造
     *
     * @param imageStream 待添加图片数据流
     * @param imageType   待添加图片类型（扩展名）
     */
    @SneakyThrows
    public XEasyPdfImage(InputStream imageStream, XEasyPdfImageType imageType) {
        this.param.setImageType(imageType.name().toLowerCase()).setImage(XEasyPdfImageUtil.read(imageStream));
    }

    /**
     * 有参构造
     *
     * @param image     待添加图片
     * @param imageType 待添加图片类型（扩展名）
     */
    @SneakyThrows
    public XEasyPdfImage(BufferedImage image, XEasyPdfImageType imageType) {
        this.param.setImageType(imageType.name().toLowerCase()).setImage(image);
    }

    /**
     * 有参构造
     *
     * @param imageFile 待添加图片
     * @param width     图片宽度
     * @param height    图片高度
     */
    @SneakyThrows
    public XEasyPdfImage(File imageFile, int width, int height) {
        this.param.setImageType(XEasyPdfImageUtil.parseType(imageFile))
                .setImage(XEasyPdfImageUtil.read(imageFile))
                .setWidth(Math.abs(width))
                .setHeight(Math.abs(height))
                .setIsCustomRectangle(Boolean.TRUE);
    }

    /**
     * 有参构造
     *
     * @param imageStream 待添加图片数据流
     * @param imageType   待添加图片类型（扩展名）
     * @param width       图片宽度
     * @param height      图片高度
     */
    @SneakyThrows
    public XEasyPdfImage(InputStream imageStream, XEasyPdfImageType imageType, int width, int height) {
        this.param.setImageType(imageType.name().toLowerCase())
                .setImage(XEasyPdfImageUtil.read(imageStream))
                .setWidth(Math.abs(width))
                .setHeight(Math.abs(height))
                .setIsCustomRectangle(Boolean.TRUE);
    }

    /**
     * 有参构造
     *
     * @param image     待添加图片
     * @param imageType 待添加图片类型（扩展名）
     * @param width     图片宽度
     * @param height    图片高度
     */
    @SneakyThrows
    public XEasyPdfImage(BufferedImage image, XEasyPdfImageType imageType, int width, int height) {
        this.param.setImageType(imageType.name().toLowerCase())
                .setImage(image)
                .setWidth(Math.abs(width))
                .setHeight(Math.abs(height))
                .setIsCustomRectangle(Boolean.TRUE);
    }

    /**
     * 设置图片
     *
     * @param imageFile 待添加图片
     * @return 返回图片组件
     */
    @SneakyThrows
    public XEasyPdfImage setImage(File imageFile) {
        this.param.setImageType(XEasyPdfImageUtil.parseType(imageFile)).setImage(XEasyPdfImageUtil.read(imageFile));
        this.param.setImageXObject(null);
        return this;
    }

    /**
     * 设置图片
     *
     * @param image     待添加图片
     * @param imageType 待添加图片类型
     * @return 返回图片组件
     */
    public XEasyPdfImage setImage(BufferedImage image, XEasyPdfImageType imageType) {
        this.param.setImageType(imageType.name().toLowerCase()).setImage(image);
        this.param.setImageXObject(null);
        return this;
    }

    /**
     * 设置图片
     *
     * @param imageStream 待添加图片数据流
     * @param imageType   待添加图片类型
     * @return 返回图片组件
     */
    public XEasyPdfImage setImage(InputStream imageStream, XEasyPdfImageType imageType) {
        this.param.setImageType(imageType.name().toLowerCase()).setImage(XEasyPdfImageUtil.read(imageStream));
        this.param.setImageXObject(null);
        return this;
    }

    /**
     * 关闭图片大小自适应
     *
     * @return 返回图片组件
     */
    public XEasyPdfImage disableSelfAdaption() {
        this.param.setEnableSelfAdaption(Boolean.FALSE);
        return this;
    }

    /**
     * 开启自身样式
     *
     * @return 返回图片组件
     */
    public XEasyPdfImage enableSelfStyle() {
        this.param.setUseSelfStyle(Boolean.TRUE);
        return this;
    }

    /**
     * 开启居中样式（水平居中，垂直居中）
     *
     * @return 返回图片组件
     */
    public XEasyPdfImage enableCenterStyle() {
        this.param.setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setVerticalStyle(XEasyPdfPositionStyle.CENTER);
        return this;
    }

    /**
     * 开启子组件
     *
     * @return 返回图片组件
     */
    public XEasyPdfImage enableChildComponent() {
        this.param.setIsChildComponent(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfImage enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 设置是否需要初始化
     *
     * @param needInitialize 是否需要初始化
     * @return 返回图片组件
     */
    public XEasyPdfImage setNeedInitialize(boolean needInitialize) {
        this.param.setIsNeedInitialize(needInitialize);
        return this;
    }

    /**
     * 设置旋转弧度
     *
     * @param radians 图片弧度
     * @return 返回图片组件
     */
    public XEasyPdfImage setRadians(double radians) {
        radians = radians % 360;
        if (radians != 0) {
            if (radians < 0) {
                radians += 360;
            }
            this.param.setRadians(radians);
        }
        return this;
    }

    /**
     * 设置最大宽度
     *
     * @param maxWidth 最大宽度
     * @return 返回图片组件
     */
    public XEasyPdfImage setMaxWidth(float maxWidth) {
        this.param.setMaxWidth(Math.abs(maxWidth));
        return this;
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight 最大高度
     * @return 返回图片组件
     */
    public XEasyPdfImage setMaxHeight(float maxHeight) {
        this.param.setMaxHeight(Math.abs(maxHeight));
        return this;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回图片组件
     */
    public XEasyPdfImage setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回图片组件
     */
    public XEasyPdfImage setMarginLeft(float margin) {
        this.param.setMarginLeft(margin).setHorizontalStyle(XEasyPdfPositionStyle.LEFT);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回图片组件
     */
    public XEasyPdfImage setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回图片组件
     */
    public XEasyPdfImage setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回图片组件
     */
    public XEasyPdfImage setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置水平样式（居左、居中、居右）
     *
     * @param style 样式
     * @return 返回图片组件
     */
    public XEasyPdfImage setHorizontalStyle(XEasyPdfPositionStyle style) {
        if (style != null) {
            if (style == XEasyPdfPositionStyle.LEFT || style == XEasyPdfPositionStyle.CENTER || style == XEasyPdfPositionStyle.RIGHT) {
                this.param.setHorizontalStyle(style);
            } else {
                throw new IllegalArgumentException("only set LEFT, CENTER or RIGHT style");
            }
        }
        return this;
    }

    /**
     * 设置垂直样式（居上、居中、居下）
     *
     * @param style 样式
     * @return 返回图片组件
     */
    public XEasyPdfImage setVerticalStyle(XEasyPdfPositionStyle style) {
        if (style != null) {
            if (style == XEasyPdfPositionStyle.TOP || style == XEasyPdfPositionStyle.CENTER || style == XEasyPdfPositionStyle.BOTTOM) {
                this.param.setVerticalStyle(style);
            } else {
                throw new IllegalArgumentException("only set TOP, CENTER or BOTTOM style");
            }
        }
        return this;
    }

    /**
     * 设置定位
     *
     * @param beginX 当前页面X轴坐标
     * @param beginY 当前页面Y轴坐标
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfImage setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfImage setWidth(float width) {
        this.param.setWidth((int) Math.abs(width)).setIsCustomRectangle(Boolean.TRUE);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfImage setHeight(float height) {
        this.param.setHeight((int) Math.abs(height)).setIsCustomRectangle(Boolean.TRUE);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回图片组件
     */
    @Override
    public XEasyPdfImage setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @SneakyThrows
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化pdfBox图片
        PDImageXObject pdImage = this.param.init(document, page, this);
        // 初始化位置
        this.param.initPosition(document, page);
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                document.getTarget(),
                page.getLastPage(),
                this.param.getContentMode().getMode(),
                true,
                this.param.getIsResetContext()
        );
        // 添加图片
        contentStream.drawImage(pdImage, this.param.getBeginX(), this.param.getBeginY(), this.param.getWidth(), this.param.getHeight());
        // 关闭内容流
        contentStream.close();
        // 如果允许页面重置定位，则进行重置
        if (page.isAllowResetPosition()) {
            // 设置文档页面X轴坐标Y轴坐标
            page.setPageX(this.param.getBeginX()).setPageY(this.param.getBeginY());
        }
        // 设置X轴Y轴坐标为初始值
        this.param.setBeginX(0F).setBeginY(null);
        // 如果待添加图片不为空，则释放图片资源
        if (this.param.getImage() != null) {
            // 设置待添加图片为空
            this.param.setImage(null);
        }
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
     * 获取图片宽度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回图片宽度
     */
    public Integer getWidth(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.param.getWidth() != null) {
            return this.param.getWidth();
        }
        this.param.init(document, page, this);
        return this.param.getWidth();
    }

    /**
     * 获取图片高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回图片高度
     */
    public Integer getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.param.getHeight() != null) {
            return this.param.getHeight();
        }
        this.param.init(document, page, this);
        return this.param.getHeight();
    }

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    public float getMarginTop() {
        return this.param.getMarginTop();
    }

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    public float getMarginBottom() {
        return this.param.getMarginBottom();
    }

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    public float getMarginLeft() {
        return this.param.getMarginLeft();
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public float getMarginRight() {
        return this.param.getMarginRight();
    }

    /**
     * 获取水平样式
     *
     * @return 返回图片样式
     */
    public XEasyPdfPositionStyle getHorizontalStyle() {
        return this.param.getHorizontalStyle();
    }

    /**
     * 获取垂直样式
     *
     * @return 返回图片样式
     */
    public XEasyPdfPositionStyle getVerticalStyle() {
        return this.param.getVerticalStyle();
    }

    /**
     * 是否使用自身样式
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isUseSelfStyle() {
        return this.param.getUseSelfStyle();
    }

    /**
     * 是否自定义尺寸
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isCustomRectangle() {
        return this.param.getIsCustomRectangle();
    }

    /**
     * 是否需要初始化
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isNeedInitialize() {
        return this.param.getIsNeedInitialize();
    }

    /**
     * 获取参数
     * @return 返回pdf图片参数
     */
    XEasyPdfImageParam getParam() {
        return this.param;
    }
}