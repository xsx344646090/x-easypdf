package org.dromara.pdf.pdfbox;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.ImageProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author xsx
 * @date 2023/11/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class ImageProcessorTest {

    @Test
    public void testDocument() {
        try(
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\watermark\\textarea\\testDocument.pdf");
                ImageProcessor imager = new ImageProcessor(document);
        ) {
            imager.setDpi(300F);
            imager.image("E:\\PDF\\document\\imager", ImageType.PNG, "my");
        }
    }

    @SneakyThrows
    @Test
    public void testPage() {
        try(
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\watermark\\textarea\\testDocument.pdf");
                ImageProcessor imager = new ImageProcessor(document);
                OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\document\\imager\\testPage.png"));
        ) {
            imager.setDpi(300F);
            imager.image(outputStream, ImageType.PNG, 0);
        }
    }
}
