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
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.dromara.pdf.pdfbox.util.UnitUtil;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * playwright转换器
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
public class PlaywrightConvertor extends HtmlConvertor {

    static {
        // 初始化驱动
        initDriver();
    }

    /**
     * 本地线程
     */
    protected static final ThreadLocal<Page> THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 页面加载状态
     */
    protected PageLoadState pageState;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public PlaywrightConvertor(Document document) {
        super(document);
    }

    @Override
    protected void init() {
        super.init();
        // 初始化页面加载状态
        if (Objects.isNull(this.pageState)) {
            this.pageState = PageLoadState.DOMCONTENTLOADED;
        }
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回字节
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
                .setHeaderTemplate(this.header)
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
        // 初始化
        this.init();
        // 返回图像
        return ImageUtil.read(this.convertToImageBytes(url));
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回字节
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
     * 初始化驱动
     */
    protected static void initDriver() {
        try {
            // 创建日志
            Log log = LogFactory.getLog(PlaywrightConvertor.class);
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Initializing browser driver...");
            }
            // 初始化驱动
            Driver driver = Driver.ensureDriverInstalled(Collections.emptyMap(), false);
            // 检查远程地址
            if (Objects.isNull(System.getProperty(Constants.PLAYWRIGHT_URL))) {
                // 打印日志
                if (log.isInfoEnabled()) {
                    log.info("Checking and install chromium browser...");
                }
                // 创建命令构建器
                ProcessBuilder pb = driver.createProcessBuilder();
                pb.environment().put("PLAYWRIGHT_DOWNLOAD_HOST", "https://npmmirror.com/mirrors/playwright");
                // 添加命令
                pb.command().addAll(asList("install", "chromium", "--with-deps", "--no-shell"));
                // 获取版本
                String version = Playwright.class.getPackage().getImplementationVersion();
                // 设置版本
                if (version != null) {
                    pb.environment().put("PW_CLI_DISPLAY_VERSION", version);
                }
                // 设置继承IO
                pb.inheritIO();
                // 启动进程
                Process process = pb.start();
                // 等待进程结束
                process.waitFor();
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Skipped chromium browser check because remote playwright is enabled...");
                }
            }
            // 设置跳过浏览器下载
            System.setProperty("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "true");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
