package org.dromara.pdf.pdfbox.core.ext.convertor.image;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.ext.convertor.AbstractConvertor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 图像转换器
 *
 * @author xsx
 * @date 2025/6/18
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
@EqualsAndHashCode(callSuper = true)
public class ImageConvertor extends AbstractConvertor {

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public ImageConvertor(Document document) {
        super(Optional.ofNullable(document).orElse(PdfHandler.getDocumentHandler().create()));
    }

    /**
     * 转为pdf
     *
     * @param files 图像文件
     */
    @SneakyThrows
    public void toPdf(List<File> files) {
        Objects.requireNonNull(files, "the files can not be null");
        this.toPdf(PageSize.A4, files.toArray(new File[0]));
    }

    /**
     * 转为pdf
     *
     * @param files 图像文件
     */
    @SneakyThrows
    public void toPdf(File... files) {
        this.toPdf(PageSize.A4, files);
    }

    /**
     * 转为pdf
     *
     * @param pageSize 页面大小
     * @param files    图像文件
     */
    @SneakyThrows
    public void toPdf(PageSize pageSize, File... files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
            this.toPdf(pageSize, Files.newInputStream(file.toPath()));
        }
    }

    /**
     * 转为pdf
     *
     * @param pageSize    页面大小
     * @param inputStream 图像流
     */
    @SneakyThrows
    public void toPdf(PageSize pageSize, InputStream inputStream) {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        this.toPdf(pageSize, bytes);
        IOUtils.closeQuietly(inputStream);
    }

    /**
     * 转为pdf
     *
     * @param images 图像
     */
    @SneakyThrows
    public void toPdf(byte[]... images) {
        this.toPdf(PageSize.A4, images);
    }

    /**
     * 转为pdf
     *
     * @param pageSize 页面大小
     * @param images   图像
     */
    @SneakyThrows
    public void toPdf(PageSize pageSize, byte[]... images) {
        // 参数检查
        Objects.requireNonNull(pageSize, "the page size can not be null");
        Objects.requireNonNull(images, "the image can not be null");
        if (images.length == 0) {
            throw new IllegalArgumentException("the image can not be empty");
        }
        // 定义页面
        List<Page> pages = new ArrayList<>(images.length);
        // 遍历图像
        for (byte[] image : images) {
            // 创建页面
            Page page = new Page(this.document, pageSize);
            // 创建图像组件
            Image component = new Image(page);
            // 设置图片
            component.setImage(image);
            // 设置宽度
            component.setWidth(page.getWidth().intValue());
            // 设置高度
            component.setHeight(page.getHeight().intValue());
            // 渲染
            component.render();
            // 添加页面
            pages.add(page);
        }
        // 添加页面
        this.document.appendPage(pages);
    }

    /**
     * 刷新
     *
     * @return 返回文档
     */
    public Document flush() {
        return this.document;
    }
}
