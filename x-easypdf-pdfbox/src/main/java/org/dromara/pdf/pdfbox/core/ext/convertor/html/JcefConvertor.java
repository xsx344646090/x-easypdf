package org.dromara.pdf.pdfbox.core.ext.convertor.html;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.SneakyThrows;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.EnumProgress;
import me.friwi.jcefmaven.IProgressHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.misc.CefPdfPrintSettings;
import org.cef.network.CefRequest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.IdUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * jcef转换器
 * <p>使用jcef实现</p>
 *
 * @author xsx
 * @date 2025/6/18
 * @see <a href="https://github.com/jcefmaven/jcefmaven">jcef官方文档</a>
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
public class JcefConvertor extends HtmlConvertor {

    /**
     * jcef app
     */
    protected static final CefApp APP = initApp();

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public JcefConvertor(Document document) {
        super(document);
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回字节
     */

    @SneakyThrows
    protected byte[] convertToPdfBytes(String url) {
        // 转pdf
        return POOL.submit(() -> {
            // 定义起始时间
            long begin = System.currentTimeMillis();
            // 创建任务
            CompletableFuture<Path> task = new CompletableFuture<>();
            // 创建客户端
            CefClient client = this.createClient(task);
            // 创建浏览器
            CefBrowser browser = this.createBrowser(client, url);
            // 执行操作
            Path path = task.get(this.requestTimeout, TimeUnit.MILLISECONDS);
            // 执行操作
            byte[] bytes = Files.readAllBytes(path);
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Converted page: " + (System.currentTimeMillis() - begin) + " ms");
            }
            // 删除文件
            Files.delete(path);
            // 打印日志
            if (log.isDebugEnabled()) {
                log.debug("Cleared cache file: " + path);
            }
            // 关闭浏览器
            browser.close(true);
            // 销毁客户端
            client.dispose();
            // 返回字节数组
            return bytes;
        }).get(this.requestTimeout + 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 转换图像
     *
     * @param url 地址
     * @return 返回图像
     */
    @SneakyThrows
    protected BufferedImage convertToImage(String url) {
        // 转图像
        return POOL.submit(() -> {
            // 定义起始时间
            long begin = System.currentTimeMillis();
            // 创建客户端
            CefClient client = this.createClient(new CompletableFuture<>());
            // 创建浏览器
            CefBrowser browser = this.createBrowser(client, url);
            // 创建图像
            BufferedImage image = browser.createScreenshot(true).get(this.requestTimeout, TimeUnit.MILLISECONDS);
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Converted page: " + (System.currentTimeMillis() - begin) + " ms");
            }
            // 关闭浏览器
            browser.close(true);
            // 销毁客户端
            client.dispose();
            // 返回图像
            return image;
        }).get(this.requestTimeout + 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建客户端
     *
     * @return 返回客户端
     */
    @SneakyThrows
    @SuppressWarnings("all")
    protected CefClient createClient(CompletableFuture<Path> future) {
        // 定义起始时间
        long begin = 0L;
        // 打印日志
        if (log.isInfoEnabled()) {
            begin = System.currentTimeMillis();
            log.info("Initializing client...");
        }
        // 创建客户端
        CefClient client = APP.createClient();
        // 添加加载处理器
        client.addLoadHandler(new DefaultLoadHandlerAdapter(this.log, future, this.initSettings()));
        // 打印日志
        if (log.isInfoEnabled()) {
            log.info("Initialized client: " + (System.currentTimeMillis() - begin) + " ms");
        }
        // 返回客户端
        return client;
    }

    /**
     * 创建浏览器
     *
     * @param client 客户端
     * @param url    请求地址
     * @return 返回浏览器
     */
    protected CefBrowser createBrowser(CefClient client, String url) {
        // 创建浏览器
        CefBrowser browser = client.createBrowser(this.getNavigateUrl(url), true, true);
        // 立即创建
        browser.createImmediately();
        // 创建框架
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(browser.getUIComponent(), BorderLayout.CENTER);
            frame.setSize(1920, 1080);
            frame.setLocation(0, 0);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(frame);
        });
        // 返回浏览器
        return browser;
    }

    /**
     * 初始化打印设置
     *
     * @return 返回打印设置
     */
    private CefPdfPrintSettings initSettings() {
        CefPdfPrintSettings settings = new CefPdfPrintSettings();
        settings.print_background = this.isIncludeBackground;
        settings.paper_width = this.pageSize.getWidth() / this.dpi;
        settings.paper_height = this.pageSize.getHeight() / this.dpi;
        settings.scale = this.scale;
        settings.landscape = this.isLandscape;
        settings.generate_document_outline = true;
        settings.margin_type = CefPdfPrintSettings.MarginType.CUSTOM;
        settings.margin_left = this.marginLeft / this.dpi;
        settings.margin_right = this.marginRight / this.dpi;
        settings.margin_top = this.marginTop / this.dpi;
        settings.margin_bottom = this.marginBottom / this.dpi;
        return settings;
    }

    /**
     * 初始化app
     */
    @SneakyThrows
    protected static CefApp initApp() {
        // 创建日志
        Log log = LogFactory.getLog(JcefConvertor.class);
        // 定义起始时间
        long beginTime = System.currentTimeMillis();
        // 打印日志
        if (log.isInfoEnabled()) {
            log.info("Initializing browser driver...");
        }
        // 创建构建器
        CefAppBuilder builder = new CefAppBuilder();
        // 设置安装目录
        builder.setInstallDir(new File(Constants.TEMP_FILE_PATH, "jcef-bundle"));
        // 设置过程处理器
        builder.setProgressHandler(new DefaultConsoleProgressHandler(log));
        // 设置参数
        builder.addJcefArgs(
                "--disable-component-update",
                "--disable-dev-shm-usage",
                "--disable-extensions",
                "--disable-features=BlinkGenPropertyTrees,NativeNotificationsTranslateUI,Vulkan",
                "--disable-gpu",
                "--disable-notifications",
                "--disable-software-rasterizer",
                "--disable-web-security",
                "--no-sandbox"
        );
        // 设置镜像
        builder.setMirrors(Arrays.asList(
                "https://mirrors.huaweicloud.com/repository/maven/me/friwi/jcef-natives-{platform}/{tag}/jcef-natives-{platform}-{tag}.jar",
                "https://repo.maven.apache.org/maven2/me/friwi/jcef-natives-{platform}/{tag}/jcef-natives-{platform}-{tag}.jar"
        ));
        // 设置
        builder.getCefSettings().windowless_rendering_enabled = true;
        builder.getCefSettings().javascript_flags = "--max_old_space_size=1024";
        builder.getCefSettings().cache_path = Constants.TEMP_FILE_PATH;
        builder.getCefSettings().root_cache_path = Constants.TEMP_FILE_PATH;
        builder.getCefSettings().persist_session_cookies = false;
        // 创建app
        CefApp app = builder.build();
        // 打印日志
        if (log.isInfoEnabled()) {
            log.info("Initialized browser driver: " + (System.currentTimeMillis() - beginTime) + " ms");
        }
        // 添加钩子
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    app.dispose();
                    log.info("Close browser successfully");
                }, "HtmlConvertor-ShutdownHook")
        );
        // 返回app
        return app;
    }

    /**
     * 默认控制台进度处理器
     */
    @Data
    public static class DefaultConsoleProgressHandler implements IProgressHandler {
        /**
         * 日志
         */
        private final Log log;

        /**
         * 有参构造
         *
         * @param log 日志
         */
        public DefaultConsoleProgressHandler(Log log) {
            this.log = log;
        }

        @Override
        public void handleProgress(EnumProgress state, float percent) {
            if (log.isInfoEnabled()) {
                if (!(percent < 0.0F) && !(percent > 100.0F)) {
                    log.info(state + " " + percent + "%");
                }
            }
        }
    }

    /**
     * 默认加载处理器
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DefaultLoadHandlerAdapter extends CefLoadHandlerAdapter {
        /**
         * 日志
         */
        private final Log log;
        /**
         * 任务
         */
        private final CompletableFuture<Path> task;
        /**
         * 设置
         */
        private final CefPdfPrintSettings settings;
        /**
         * 开始时间
         */
        private long beginTime;

        /**
         * 有参构造
         *
         * @param log      日志
         * @param task     任务
         * @param settings 设置
         */
        public DefaultLoadHandlerAdapter(Log log, CompletableFuture<Path> task, CefPdfPrintSettings settings) {
            this.task = task;
            this.log = log;
            this.settings = settings;

        }

        @Override
        public void onLoadStart(CefBrowser browser, CefFrame frame, CefRequest.TransitionType transitionType) {
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Loading page: " + frame.getURL());
            }
            // 设置开始时间
            this.beginTime = System.currentTimeMillis();
        }

        @Override
        public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
            // 打印日志
            if (log.isInfoEnabled()) {
                log.info("Loaded page: " + (System.currentTimeMillis() - this.beginTime) + " ms");
            }
            // 创建临时文件
            Path path = Paths.get(Constants.TEMP_FILE_PATH, IdUtil.get());
            // 打印pdf
            browser.printToPDF(path.toString(), this.settings, (url, ok) -> this.task.complete(path));
            // 打印日志
            if (log.isDebugEnabled()) {
                log.debug("Created cache file: " + path);
            }
        }

        @Override
        public void onLoadError(CefBrowser browser, CefFrame frame, ErrorCode errorCode, String errorText, String failedUrl) {
            log.error("Failed to load page: " + failedUrl);
        }
    }
}
