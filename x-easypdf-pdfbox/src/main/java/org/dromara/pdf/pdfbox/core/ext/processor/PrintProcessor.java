package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.PrintOrientation;
import org.dromara.pdf.pdfbox.core.enums.PrintScaling;

import javax.print.PrintServiceLookup;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.util.Objects;

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
     * 有参构造
     *
     * @param document 文档
     */
    public PrintProcessor(Document document) {
        super(document);
    }

    /**
     * 打印
     *
     * @param count 数量
     */
    public void print(int count) {
        this.print(PrintScaling.ACTUAL_SIZE, PrintOrientation.PORTRAIT, count);
    }

    /**
     * 打印
     *
     * @param scaling 缩放
     * @param count   数量
     */
    public void print(PrintScaling scaling, int count) {
        this.print(scaling, PrintOrientation.PORTRAIT, count);
    }

    /**
     * 打印
     *
     * @param orientation 方向
     * @param count       数量
     */
    public void print(PrintOrientation orientation, int count) {
        this.print(PrintScaling.ACTUAL_SIZE, orientation, count);
    }

    /**
     * 打印
     *
     * @param scaling     缩放
     * @param orientation 方向
     * @param count       数量
     */
    @SneakyThrows
    public void print(PrintScaling scaling, PrintOrientation orientation, int count) {
        // 参数校验
        Objects.requireNonNull(scaling, "the scaling can not be null");
        // 参数校验
        Objects.requireNonNull(orientation, "the scaling can not be null");
        // 创建打印选项
        PDFPrintable printable = new PDFPrintable(this.document.getTarget(), scaling.getScaling());
        // 创建打印任务
        PDFPageable target = new PDFPageable(this.document.getTarget());
        // 创建页面格式对象
        PageFormat pageFormat = new PageFormat();
        // 设置打印方向
        pageFormat.setOrientation(orientation.getOrientation());
        // 添加打印页面
        target.append(printable, pageFormat);
        // 获取打印任务
        PrinterJob job = PrinterJob.getPrinterJob();
        // 设置打印服务（默认）
        job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
        // 设置打印任务
        job.setPageable(target);
        // 设置打印数量
        job.setCopies(count);
        // 执行打印
        job.print();
    }
}
