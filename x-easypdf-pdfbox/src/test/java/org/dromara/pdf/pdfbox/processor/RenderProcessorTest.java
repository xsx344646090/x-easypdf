package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.MemoryPolicy;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.RenderProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.FileUtil;
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
public class RenderProcessorTest extends BaseTest {

    /**
     * 测试文档转图片
     */
    @Test
    public void documentTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\document\\bigDataTest2.pdf", MemoryPolicy.setupTempFileOnly())
            ) {
                RenderProcessor imager = PdfHandler.getDocumentProcessor(document).getRenderProcessor();

                imager.setDpi(72F);
                imager.enableOptimization();
                imager.enableBinary();
                imager.image("E:\\PDF\\pdfbox\\document\\imager", ImageType.PNG);
            }
        });
    }

    /**
     * 测试页面转图片
     */
    @Test
    public void pageTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf");
                    OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get("E:\\PDF\\pdfbox\\imager\\testPage.png")))
            ) {
                RenderProcessor imager = PdfHandler.getDocumentProcessor(document).getRenderProcessor();
                imager.setDpi(300F);
                imager.image(outputStream, ImageType.PNG, 0);
            }
        });
    }
}
