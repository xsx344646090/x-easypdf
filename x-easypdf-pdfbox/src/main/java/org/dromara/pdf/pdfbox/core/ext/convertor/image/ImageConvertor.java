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
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
        List<BufferedImage> list = new ArrayList<>(files.length);
        for (File file : files) {
            try (
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)
            ) {
                IOUtils.copy(bufferedInputStream, outputStream);
                list.add(ImageUtil.read(outputStream.toByteArray()));
            }
        }
        this.toPdf(pageSize, list);
    }

    /**
     * 转为pdf
     *
     * @param images 图像
     */
    @SneakyThrows
    public void toPdf(List<BufferedImage> images) {
        this.toPdf(PageSize.A4, images);
    }

    /**
     * 转为pdf
     *
     * @param pageSize 页面大小
     * @param images   图像
     */
    @SneakyThrows
    public void toPdf(PageSize pageSize, List<BufferedImage> images) {
        this.toPdf(pageSize, images.toArray(new BufferedImage[0]));
    }

    /**
     * 转为pdf
     *
     * @param images 图像
     */
    @SneakyThrows
    public void toPdf(BufferedImage... images) {
        this.toPdf(PageSize.A4, images);
    }

    /**
     * 转为pdf
     *
     * @param pageSize 页面大小
     * @param images   图像
     */
    @SneakyThrows
    public void toPdf(PageSize pageSize, BufferedImage... images) {
        // 参数检查
        Objects.requireNonNull(pageSize, "the page size can not be null");
        Objects.requireNonNull(images, "the image can not be null");
        if (images.length == 0) {
            throw new IllegalArgumentException("the image can not be empty");
        }
        // 定义页面
        List<Page> pages = new ArrayList<>(images.length);
        // 遍历图像
        for (BufferedImage image : images) {
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
