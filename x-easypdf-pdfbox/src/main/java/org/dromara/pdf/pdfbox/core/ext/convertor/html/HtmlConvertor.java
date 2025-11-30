package org.dromara.pdf.pdfbox.core.ext.convertor.html;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.impl.driver.Driver;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.ScreenshotType;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.IdUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.dromara.pdf.pdfbox.util.UnitUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * html转换器
 * <p>使用playwright实现</p>
 *
 * @author xsx
 * @date 2025/6/18
 * @see <a href="https://playwright.dev/java/docs/intro">playwright官方文档</a>
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
public class HtmlConvertor extends AbstractConvertor {

    static {
        // 初始化驱动
        initDriver();
    }

    /**
     * 本地线程
     */
    protected static final ThreadLocal<Page> THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 线程池
     */
    protected static final ThreadPoolExecutor POOL = DefaultThreadPool.createPool();
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
     * 请求超时时间（ms）
     */
    protected Long requestTimeout;
    /**
     * 页面加载状态
     */
    protected PageLoadState pageState;
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
     * 是否横向
     */
    protected Boolean isLandscape;
    /**
     * 是否包含背景
     */
    protected Boolean isIncludeBackground;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public HtmlConvertor(Document document) {
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
     * @param htmlContent html内容
     * @return 返回文档
     */
    @SneakyThrows
    public Document toPdfWithContent(String htmlContent) {
        this.init();
        Path path = Paths.get(Constants.TEMP_FILE_PATH, String.join(".", IdUtil.get(), "html"));
        Files.write(path, htmlContent.getBytes());
        try {
            return this.convertToPdf(path.toAbsolutePath().toString());
        } finally {
            Files.deleteIfExists(path);
        }
    }

    /**
     * 转pdf字节数组
     *
     * @param file 文件
     * @return 返回pdf字节数组
     */
    public byte[] toPdfBytes(File file) {
        // 转换pdf
        return this.toPdfBytes(file.getAbsolutePath());
    }

    /**
     * 转pdf字节数组
     *
     * @param url 地址
     * @return 返回pdf字节数组
     */
    public byte[] toPdfBytes(String url) {
        // 初始化
        this.init();
        // 转换pdf
        return this.convertToPdfBytes(url);
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
        // 返回文档
        return this.imageToPdf(bufferedImage);
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
        // 初始化
        this.init();
        // 返回图像
        return this.convertToImage(url);
    }

    /**
     * 转图像
     *
     * @param file 文件
     * @return 返回图像
     */
    public byte[] toImageBytes(File file) {
        return this.toImageBytes(file.getAbsolutePath());
    }

    /**
     * 转图像
     *
     * @param url 地址
     * @return 返回图像
     */
    public byte[] toImageBytes(String url) {
        // 初始化
        this.init();
        // 返回图像
        return this.convertToImageBytes(url);
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
        // 初始化页面加载状态
        if (Objects.isNull(this.pageState)) {
            this.pageState = PageLoadState.DOMCONTENTLOADED;
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
        // 初始化是否横向
        if (Objects.isNull(this.isLandscape)) {
            this.isLandscape = Boolean.FALSE;
        }
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected Document convertToPdf(String url) {
        // 返回pdf文档
        return PdfHandler.getDocumentHandler().load(this.convertToPdfBytes(url));
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected byte[] convertToPdfBytes(String url) {
        // 定义页面选项
        Page.PdfOptions options = new Page.PdfOptions()
                .setWidth(UnitUtil.pt2px(this.dpi, this.pageSize.getWidth()) + UNIT)
                .setHeight(UnitUtil.pt2px(this.dpi, this.pageSize.getHeight()) + UNIT)
                .setPrintBackground(this.isIncludeBackground)
                .setOutline(true)
                .setScale(this.scale)
                .setLandscape(this.isLandscape)
                .setMargin(
                        new Margin().setLeft(this.marginLeft + UNIT)
                                .setRight(this.marginRight + UNIT)
                                .setTop(this.marginTop + UNIT)
                                .setBottom(this.marginBottom + UNIT)
                );
        // 转pdf
        return this.convert(url, page -> page.pdf(options));
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected BufferedImage convertToImage(String url) {
        // 返回图像
        return ImageUtil.read(this.convertToImageBytes(url));
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回文档
     */
    protected byte[] convertToImageBytes(String url) {
        // 转图像
        return this.convert(url, page -> page.screenshot(
                new Page.ScreenshotOptions()
                        .setType(ScreenshotType.PNG)
                        .setFullPage(true)
                        .setOmitBackground(false)
        ));
    }

    /**
     * 转换
     *
     * @param url      地址
     * @param function 功能
     * @return 返回字节数组
     */
    @SneakyThrows
    protected byte[] convert(String url, Function<Page, byte[]> function) {
        return POOL.submit(() -> {
            // 定义起始时间
            long begin = 0L;
            // 定义结束时间
            long end = 0L;
            // 获取页面
            Page page = this.getBrowserPage();
            // 获取导航地址
            String navigateUrl = this.getNavigateUrl(url);
            // 打印日志
            if (log.isInfoEnabled()) {
                begin = System.currentTimeMillis();
                log.info("Loading page: " + navigateUrl);
            }
            // 导航地址
            page.navigate(navigateUrl);
            // 等待加载
            page.waitForLoadState(this.pageState.getState(), new Page.WaitForLoadStateOptions().setTimeout(this.requestTimeout));
            // 执行脚本
            page.evaluate("document.fonts.ready.then(() => { window.isFontLoaded = true; });");
            // 等待执行
            page.waitForFunction("window.isFontLoaded === true");
            // 打印日志
            if (log.isInfoEnabled()) {
                end = System.currentTimeMillis();
                log.info("Loaded page: " + (end - begin) + " ms");
                begin = end;
                log.info("Converting page...");
            }
            // 执行操作
            byte[] bytes = function.apply(page);
            // 打印日志
            if (log.isInfoEnabled()) {
                end = System.currentTimeMillis();
                log.info("Converted page: " + (end - begin) + " ms");
            }
            // 返回字节数组
            return bytes;
        }).get(5, TimeUnit.MINUTES);
    }

    /**
     * 图像转pdf
     *
     * @param sourceImage 图像
     * @return 返回文档
     */
    protected Document imageToPdf(BufferedImage sourceImage) {
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
        float scale = page.getWithoutMarginWidth() / sourceImage.getWidth();
        // 获取拆分图像
        List<BufferedImage> images = ImageUtil.splitForVertical(sourceImage, (int) (page.getWithoutMarginHeight() / scale));
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
     * 获取浏览器页面
     *
     * @return 返回浏览器页面
     */
    protected Page getBrowserPage() {
        // 获取浏览器页面
        Page page = THREAD_LOCAL.get();
        // 存在浏览器直接返回浏览器页面
        if (Objects.nonNull(page)) {
            return page;
        }
        // 初始化浏览器页面
        page = initBrowserPage();
        // 返回页面
        return page;
    }

    /**
     * 初始化浏览器页面
     *
     * @return 返回页面
     */
    @SneakyThrows
    @SuppressWarnings("all")
    protected Page initBrowserPage() {
        // 定义起始时间
        long begin = 0L;
        // 定义结束时间
        long end = 0L;
        // 打印日志
        if (log.isInfoEnabled()) {
            begin = System.currentTimeMillis();
            log.info("Initializing browser...");
        }
        // 获取远程地址
        String remoteUrl = System.getProperty(Constants.PLAYWRIGHT_URL);
        // 创建playwright
        Playwright playwright = Playwright.create();
        // 初始化浏览器
        Browser browser;
        if (Objects.isNull(remoteUrl)) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chromium"));
        } else {
            browser = playwright.chromium().connect("ws://" + remoteUrl);
        }
        // 打印日志
        if (log.isInfoEnabled()) {
            end = System.currentTimeMillis();
            log.info("Initialized browser: " + (end - begin) + " ms");
            begin = end;
            log.info("Initializing page...");
        }
        // 创建新页面
        Page newPage = browser.newPage();
        // 设置页面
        THREAD_LOCAL.set(newPage);
        // 添加钩子
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    THREAD_LOCAL.remove();
                    playwright.close();
                    log.info("Close browser successfully");
                }, "HtmlConvertor-ShutdownHook")
        );
        // 打印日志
        if (log.isInfoEnabled()) {
            end = System.currentTimeMillis();
            log.info("Initialized page: " + (end - begin) + " ms");
        }
        // 返回页面
        return newPage;
    }

    /**
     * 获取导航地址
     *
     * @param url 地址
     * @return 返回导航地址
     */
    protected String getNavigateUrl(String url) {
        try {
            return new URL(url).toString();
        } catch (MalformedURLException e) {
            return Paths.get(url).toUri().toString();
        }
    }

    /**
     * 初始化驱动
     */
    protected static void initDriver() {
        try {
            Log log = LogFactory.getLog(HtmlConvertor.class);
            if (log.isInfoEnabled()) {
                log.info("Initializing browser driver...");
            }
            Driver driver = Driver.ensureDriverInstalled(Collections.emptyMap(), false);
            if (Objects.isNull(System.getProperty(Constants.PLAYWRIGHT_URL))) {
                if (log.isInfoEnabled()) {
                    log.info("Checking and install chromium browser...");
                }
                ProcessBuilder pb = driver.createProcessBuilder();
                pb.command().addAll(asList("install", "chromium", "--with-deps", "--no-shell"));
                String version = Playwright.class.getPackage().getImplementationVersion();
                if (version != null) {
                    pb.environment().put("PW_CLI_DISPLAY_VERSION", version);
                }
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Skipped chromium browser check because remote playwright is enabled...");
                }
            }
            System.setProperty("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "true");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 线程池
     */
    protected static class DefaultThreadPool {

        /**
         * IO密集型任务配置
         */
        protected static ThreadPoolExecutor createPool() {
            String coreSize = System.getProperty(Constants.THREAD_CORE_SIZE, "1");
            String maxSize = System.getProperty(Constants.THREAD_MAX_SIZE, "1");
            String keepAliveTime = System.getProperty(Constants.THREAD_KEEP_ALIVE_TIME, "60");
            String queueSize = System.getProperty(Constants.THREAD_QUEUE_SIZE, "2000");
            return new ThreadPoolExecutor(
                    Integer.parseInt(coreSize),
                    Integer.parseInt(maxSize),
                    Integer.parseInt(keepAliveTime),
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(Integer.parseInt(queueSize)),
                    new DefaultThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
        }
    }

    /**
     * 默认线程工厂
     */
    protected static class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程组
         */
        private final ThreadGroup group;
        /**
         * 线程名称前缀
         */
        private final String namePrefix;
        /**
         * 线程计数器
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        /**
         * 有参构造
         */
        DefaultThreadFactory() {
            this.group = Thread.currentThread().getThreadGroup();
            this.namePrefix = "playwrightPool-" + "thread-";
        }

        /**
         * 新建线程
         *
         * @param r 运行器
         * @return 返回线程
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new DefaultThread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 默认线程
     */
    protected static class DefaultThread extends Thread {

        /**
         * 有参构造
         */
        public DefaultThread(ThreadGroup group, Runnable target, String name, long stackSize) {
            super(group, target, name, stackSize);
        }
    }
}
