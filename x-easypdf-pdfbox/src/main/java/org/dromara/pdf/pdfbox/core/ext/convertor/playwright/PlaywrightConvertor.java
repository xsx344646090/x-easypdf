package org.dromara.pdf.pdfbox.core.ext.convertor.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.ScreenshotType;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.dromara.pdf.pdfbox.util.UnitUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * playwright转换器
 *
 * @author xsx
 * @date 2025/6/18
 * @see <a href="https://playwright.dev/java/docs/intro">官方文档</a>
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlaywrightConvertor extends AbstractConvertor {

    /**
     * 单位
     */
    protected static final String UNIT = "px";

    /**
     * dpi
     */
    protected Integer dpi;
    /**
     * 页面尺寸
     */
    protected PageSize pageSize;
    /**
     * 是否包含背景
     */
    protected Boolean isIncludeBackground;
    /**
     * 请求超时时间（ms）
     */
    protected Long requestTimeout;
    /**
     * 上边距（单位：px）
     */
    protected Float marginTop;
    /**
     * 下边距（单位：px）
     */
    protected Float marginBottom;
    /**
     * 左边距（单位：px）
     */
    protected Float marginLeft;
    /**
     * 右边距（单位：px）
     */
    protected Float marginRight;
    /**
     * 缩放比例（0.1-2.0）
     */
    protected Float scale;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public PlaywrightConvertor(Document document) {
        super(document);
    }

    /**
     * 边距（上下左右，单位：px）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.marginTop = margin;
        this.marginBottom = margin;
        this.marginLeft = margin;
        this.marginRight = margin;
    }

    /**
     * 转pdf
     *
     * @param file 文件
     * @return 返回文档
     */
    public Document toPdf(File file) {
        return this.toPdf(file.getAbsolutePath());
    }

    /**
     * 转pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    public Document toPdf(String url) {
        this.init();
        return this.convertToPdf(url);
    }

    /**
     * 转pdf
     *
     * @param file 文件
     * @return 返回文档
     */
    public Document toPdfWithImage(File file) {
        return this.toPdfWithImage(file.getAbsolutePath());
    }

    /**
     * 转pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    public Document toPdfWithImage(String url) {
        // 初始化
        this.init();
        // 获取图像
        BufferedImage bufferedImage = this.convertToImage(url);
        // 定义图像放大倍数
        final int multiple = 2;
        // 创建文档
        Document document = PdfHandler.getDocumentHandler().create();
        // 设置上边距
        document.setMarginTop(this.marginTop);
        // 设置下边距
        document.setMarginBottom(this.marginBottom);
        // 设置左边距
        document.setMarginLeft(this.marginLeft);
        // 设置右边距
        document.setMarginRight(this.marginRight);
        // 创建页面
        org.dromara.pdf.pdfbox.core.base.Page page = new org.dromara.pdf.pdfbox.core.base.Page(document);
        // 获取图像缩放比例
        float scale = page.getWithoutMarginWidth() / bufferedImage.getWidth();
        // 获取拆分图像
        List<BufferedImage> images = ImageUtil.splitForVertical(bufferedImage, (int) (page.getWithoutMarginHeight() / scale));
        // 遍历图像
        for (BufferedImage image : images) {
            // 创建图像
            Image component = new Image(document.getCurrentPage());
            // 设置图像
            component.setImage(ImageUtil.scale(image, image.getWidth() * multiple, image.getHeight() * multiple, java.awt.Image.SCALE_DEFAULT));
            // 设置缩放比例
            component.setScale(scale * this.scale / multiple);
            // 设置水平居中
            component.setHorizontalAlignment(HorizontalAlignment.CENTER);
            // 设置垂直居中
            component.setVerticalAlignment(VerticalAlignment.CENTER);
            // 绘制
            component.render();
        }
        // 添加页面
        document.appendPage(page);
        // 返回文档
        return document;
    }

    /**
     * 转图像
     *
     * @param file 文件
     * @return 返回图像
     */
    public BufferedImage toImage(File file) {
        return this.toImage(file.getAbsolutePath());
    }

    /**
     * 转图像
     *
     * @param url 地址
     * @return 返回图像
     */
    public BufferedImage toImage(String url) {
        this.init();
        return this.convertToImage(url);
    }

    /**
     * 初始化
     */
    protected void init() {
        // 初始化dpi
        if (Objects.isNull(this.dpi)) {
            this.dpi = 96;
        }
        // 初始化页面尺寸
        if (Objects.isNull(this.pageSize)) {
            this.pageSize = PageSize.A4;
        }
        // 初始化是否包含背景
        if (Objects.isNull(this.isIncludeBackground)) {
            this.isIncludeBackground = true;
        }
        // 初始化请求超时时间
        if (Objects.isNull(this.requestTimeout)) {
            this.requestTimeout = 60000L;
        }
        // 初始化上边距
        if (Objects.isNull(this.marginTop)) {
            this.marginTop = 0F;
        }
        // 初始化下边距
        if (Objects.isNull(this.marginBottom)) {
            this.marginBottom = 0F;
        }
        // 初始化左边距
        if (Objects.isNull(this.marginLeft)) {
            this.marginLeft = 0F;
        }
        // 初始化右边距
        if (Objects.isNull(this.marginRight)) {
            this.marginRight = 0F;
        }
        // 初始化缩放比例
        if (Objects.isNull(this.scale)) {
            this.scale = 1.0F;
        }
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected Document convertToPdf(String url) {
        // 定义页面选项
        Page.PdfOptions options = new Page.PdfOptions()
                .setWidth(UnitUtil.pt2px(this.dpi, this.pageSize.getWidth()) + UNIT)
                .setHeight(UnitUtil.pt2px(this.dpi, this.pageSize.getHeight()) + UNIT)
                .setPrintBackground(this.isIncludeBackground)
                .setOutline(true)
                .setScale(this.scale)
                .setMargin(
                        new Margin().setLeft(this.marginLeft + UNIT)
                                .setRight(this.marginRight + UNIT)
                                .setTop(this.marginTop + UNIT)
                                .setBottom(this.marginBottom + UNIT)
                );
        // 转pdf
        byte[] bytes = this.convert(url, page -> page.pdf(options));
        // 返回pdf文档
        return PdfHandler.getDocumentHandler().load(bytes);
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected BufferedImage convertToImage(String url) {
        // 转图像
        byte[] bytes = this.convert(url, page -> page.screenshot(
                new Page.ScreenshotOptions()
                        .setType(ScreenshotType.PNG)
                        .setFullPage(true)
                        .setOmitBackground(true)
        ));
        // 获取图像
        return ImageUtil.read(bytes);
    }

    /**
     * 转换
     *
     * @param url      地址
     * @param function 功能
     * @return 返回字节数组
     */
    protected byte[] convert(String url, Function<Page, byte[]> function) {
        // 定义起始时间
        long begin = 0L;
        // 打印日志
        if (log.isInfoEnabled()) {
            begin = System.currentTimeMillis();
            log.info("Initializing browser");
        }
        try (
                Playwright playwright = Playwright.create();
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true))
        ) {
            // 创建页面
            Page page = browser.newPage();
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Loading page: " + url);
            }
            // 导航地址
            page.navigate(url);
            // 等待
            page.waitForLoadState(LoadState.DOMCONTENTLOADED, new Page.WaitForLoadStateOptions().setTimeout(this.requestTimeout));
            // 执行脚本
            page.evaluate("document.fonts.ready.then(() => { window.isFontLoaded = true; });");
            // 等待执行
            page.waitForFunction("window.isFontLoaded === true");
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Converting page: " + url);
            }
            // 执行操作
            byte[] bytes = function.apply(page);
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Finished convert: " + (System.currentTimeMillis() - begin) + "ms");
            }
            // 返回字节数组
            return bytes;
        }
    }
}
