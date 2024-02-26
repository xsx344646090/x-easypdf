package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.printing.Orientation;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.PrintOrientation;
import org.dromara.pdf.pdfbox.core.enums.PrintScaling;
import org.dromara.pdf.pdfbox.util.RenderingHintUtil;
import sun.print.RasterPrinterJob;

import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import java.awt.*;
import java.awt.print.*;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 打印处理器
 *
 * @author xsx
 * @date 2023/11/15
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
public class PrintProcessor extends AbstractProcessor {

    /**
     * 打印起始页码
     */
    protected Integer beginPageNo;
    /**
     * 打印结束页码
     */
    protected Integer endPageNo;
    /**
     * 打印缩放
     */
    @Setter
    protected PrintScaling scaling;
    /**
     * 打印方向
     */
    @Setter
    protected PrintOrientation orientation;
    /**
     * 打印dpi
     */
    @Setter
    protected Float dpi;
    /**
     * 打印渲染提示
     */
    @Setter
    protected RenderingHints renderingHints;
    /**
     * 是否显示边框
     */
    @Setter
    protected Boolean isShowBorder;
    /**
     * 是否居中
     */
    @Setter
    protected Boolean isCenter;
    /**
     * 是否内存优化（可能降低图像质量）
     */
    @Setter
    protected Boolean isMemoryOptimization;
    /**
     * 打印数量
     */
    @Setter
    protected Integer count;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public PrintProcessor(Document document) {
        super(document);
    }

    /**
     * 设置起始页码
     * <p>注：从1开始</p>
     *
     * @param beginPageNo 起始页码
     */
    public void setBeginPageNo(Integer beginPageNo) {
        if (Objects.nonNull(beginPageNo) && beginPageNo < 1) {
            throw new IllegalArgumentException("the begin page no must be greater than 1");
        }
        this.beginPageNo = beginPageNo;
    }

    /**
     * 设置结束页码
     *
     * @param endPageNo 结束页码
     */
    public void setEndPageNo(Integer endPageNo) {
        int pages = this.getDocument().getNumberOfPages();
        if (Objects.nonNull(endPageNo) && endPageNo > pages) {
            throw new IllegalArgumentException("the end page no must not be greater than the page num of the document['" + pages + "']");
        }
        if (Objects.nonNull(endPageNo) && endPageNo < Optional.ofNullable(this.beginPageNo).orElse(1)) {
            throw new IllegalArgumentException("the end page no must not be less than begin page num or 1");
        }
        this.endPageNo = endPageNo;
    }

    /**
     * 打印
     */
    @SneakyThrows
    public void print() {
        // 初始化
        this.init();
        // 获取打印任务
        RasterPrinterJob job = (RasterPrinterJob) PrinterJob.getPrinterJob();
        // 设置打印服务（默认）
        job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
        // 设置页面选项
        job.setPageable(this.initPageable());
        // 设置打印数量
        job.setCopies(this.count);
        // 初始化打印属性
        this.initPrintRequestAttribute(job);
        // 执行打印
        job.print();
    }

    /**
     * 初始化
     */
    protected void init() {
        if (Objects.isNull(this.beginPageNo)) {
            this.beginPageNo = 1;
        }
        if (Objects.isNull(this.endPageNo)) {
            this.endPageNo = this.getDocument().getNumberOfPages();
        }
        if (Objects.isNull(this.scaling)) {
            this.scaling = PrintScaling.ACTUAL_SIZE;
        }
        if (Objects.isNull(this.orientation)) {
            this.orientation = PrintOrientation.PORTRAIT;
        }
        if (Objects.isNull(this.dpi)) {
            this.dpi = 0F;
        }
        if (Objects.isNull(this.renderingHints)) {
            this.renderingHints = RenderingHintUtil.createDefaultRenderingHints();
        }
        if (Objects.isNull(this.isShowBorder)) {
            this.isShowBorder = Boolean.FALSE;
        }
        if (Objects.isNull(this.isCenter)) {
            this.isCenter = Boolean.TRUE;
        }
        if (Objects.isNull(this.isMemoryOptimization)) {
            this.isMemoryOptimization = Boolean.FALSE;
        }
        if (Objects.isNull(this.count)) {
            this.count = 1;
        }
    }

    /**
     * 初始化打印选项
     *
     * @return 返回打印选项
     */
    protected PDFPrintable initPrintable() {
        // 创建打印选项
        PDFPrintable printable = new PDFPrintable(
                this.getDocument(),
                this.scaling.getScaling(),
                this.isShowBorder,
                this.dpi,
                this.isCenter,
                new PDFRenderer(this.getDocument())
        );
        // 设置二次采样
        printable.setSubsamplingAllowed(this.isMemoryOptimization);
        // 设置渲染提示
        printable.setRenderingHints(this.renderingHints);
        // 返回页面选项
        return printable;
    }

    /**
     * 初始化页面选项
     *
     * @return 返回页面选项
     */
    protected DefaultPageable initPageable() {
        // 返回页面选项
        return new DefaultPageable(
                this.getDocument(),
                this.orientation.getOrientation(),
                this.initPrintable()
        );
    }

    /**
     * 初始化打印属性
     */
    @SneakyThrows
    protected void initPrintRequestAttribute(RasterPrinterJob job) {
        final String methodName = "setAttributes";
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet(new PageRanges(this.beginPageNo, this.endPageNo));
        Method method = RasterPrinterJob.class.getDeclaredMethod(methodName, PrintRequestAttributeSet.class);
        method.setAccessible(true);
        method.invoke(job, attributes);
    }

    /**
     * 默认打印选项
     * <p>重写{@link org.apache.pdfbox.printing.PDFPageable}</p>
     */
    @Setter
    public static class DefaultPageable extends Book {
        private final PDDocument document;
        private final int numberOfPages;
        private final Orientation orientation;
        private final Printable printable;

        /**
         * Creates a new PDFPageable with the given page orientation and with optional page borders
         * shown. The image will be rasterized at the given DPI before being sent to the printer.
         *
         * @param document    the document to print
         * @param orientation page orientation policy
         * @param printable   true if page borders are to be printed
         */
        public DefaultPageable(PDDocument document, Orientation orientation, Printable printable) {
            this.document = document;
            this.orientation = orientation;
            this.printable = printable;
            this.numberOfPages = document.getNumberOfPages();
        }

        @Override
        public int getNumberOfPages() {
            return this.numberOfPages;
        }

        /**
         * {@inheritDoc}
         * <p>
         * Returns the actual physical size of the pages in the PDF file. May not fit the local printer.
         */
        @Override
        public PageFormat getPageFormat(int pageIndex) {
            PDPage page = this.document.getPage(pageIndex);
            PDRectangle mediaBox = this.getRotatedMediaBox(page);
            PDRectangle cropBox = this.getRotatedCropBox(page);

            // Java does not seem to understand landscape paper sizes, i.e. where width > height, it
            // always crops the imageable area as if the page were in portrait. I suspect that this is
            // a JDK bug but it might be by design, see PDFBOX-2922.
            //
            // As a workaround, we normalise all Page(s) to be portrait, then flag them as landscape in
            // the PageFormat.
            Paper paper;
            boolean isLandscape;
            if (mediaBox.getWidth() > mediaBox.getHeight()) {
                // rotate
                paper = new Paper();
                paper.setSize(mediaBox.getHeight(), mediaBox.getWidth());
                paper.setImageableArea(cropBox.getLowerLeftY(), cropBox.getLowerLeftX(), cropBox.getHeight(), cropBox.getWidth());
                isLandscape = true;
            } else {
                paper = new Paper();
                paper.setSize(mediaBox.getWidth(), mediaBox.getHeight());
                paper.setImageableArea(cropBox.getLowerLeftX(), cropBox.getLowerLeftY(), cropBox.getWidth(), cropBox.getHeight());
                isLandscape = false;
            }

            PageFormat format = new PageFormat();
            format.setPaper(paper);

            // auto portrait/landscape
            switch (this.orientation) {
                case AUTO:
                    format.setOrientation(isLandscape ? PageFormat.LANDSCAPE : PageFormat.PORTRAIT);
                    break;
                case LANDSCAPE:
                    format.setOrientation(PageFormat.LANDSCAPE);
                    break;
                case PORTRAIT:
                    format.setOrientation(PageFormat.PORTRAIT);
                    break;
                default:
                    break;
            }

            return format;
        }

        /**
         * Returns the <code>Printable</code> instance responsible for
         * rendering the page specified by <code>pageIndex</code>.
         *
         * @param i the zero based index of the page whose
         *          <code>Printable</code> is being requested
         * @return the <code>Printable</code> that renders the page.
         */
        @Override
        public Printable getPrintable(int i) {
            if (i >= this.numberOfPages) {
                throw new IndexOutOfBoundsException(i + " >= " + this.numberOfPages);
            }
            return this.printable;
        }

        /**
         * This will find the CropBox with rotation applied, for this page by looking up the hierarchy
         * until it finds them.
         *
         * @return The CropBox at this level in the hierarchy.
         */
        protected PDRectangle getRotatedCropBox(PDPage page) {
            PDRectangle cropBox = page.getCropBox();
            int rotationAngle = page.getRotation();
            if (rotationAngle == 90 || rotationAngle == 270) {
                return new PDRectangle(cropBox.getLowerLeftY(), cropBox.getLowerLeftX(), cropBox.getHeight(), cropBox.getWidth());
            } else {
                return cropBox;
            }
        }

        /**
         * This will find the MediaBox with rotation applied, for this page by looking up the hierarchy
         * until it finds them.
         *
         * @return The MediaBox at this level in the hierarchy.
         */
        protected PDRectangle getRotatedMediaBox(PDPage page) {
            PDRectangle mediaBox = page.getMediaBox();
            int rotationAngle = page.getRotation();
            if (rotationAngle == 90 || rotationAngle == 270) {
                return new PDRectangle(mediaBox.getLowerLeftY(), mediaBox.getLowerLeftX(), mediaBox.getHeight(), mediaBox.getWidth());
            } else {
                return mediaBox;
            }
        }
    }
}
