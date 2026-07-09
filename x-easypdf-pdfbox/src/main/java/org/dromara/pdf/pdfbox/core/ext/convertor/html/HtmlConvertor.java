package org.dromara.pdf.pdfbox.core.ext.convertor.html;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.SneakyThrows;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
public abstract class HtmlConvertor extends AbstractConvertor {

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
    protected String header;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public HtmlConvertor(Document document) {
        super(document);
    }

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回字节
     */
    protected abstract byte[] convertToPdfBytes(String url);

    /**
     * 转换pdf
     *
     * @param url 地址
     * @return 返回字节
     */
    protected abstract BufferedImage convertToImage(String url);

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
    @SuppressWarnings("all")
    @SneakyThrows
    public Document toPdfWithContent(String htmlContent) {
        this.init();
        Path path = Paths.get(Constants.TEMP_FILE_PATH, String.join(".", IdUtil.get(), "html"));
        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            channel.write(StandardCharsets.UTF_8.encode(htmlContent));
            channel.force(true);
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
     * 初始化
     */
    protected void init() {
        // 初始化dpi
        if (Objects.isNull(this.dpi)) {
            this.dpi = Constants.POINTS_PER_INCH;
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
     * 线程池
     */
    protected static class DefaultThreadPool {

        /**
         * IO密集型任务配置
         */
        protected static ThreadPoolExecutor createPool() {
            String coreSize = System.getProperty(Constants.THREAD_CORE_SIZE, "4");
            String maxSize = System.getProperty(Constants.THREAD_MAX_SIZE, "4");
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
            this.namePrefix = "htmlConvertorPool-" + "thread-";
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
