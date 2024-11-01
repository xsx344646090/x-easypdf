package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.ImageProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2024/10/28
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
public class ImageProcessorTest extends BaseTest {

    /**
     * 测试图像转换
     */
    @Test
    public void toPdfTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().create();
            ) {
                List<File> files = new ArrayList<>(20);
                for (int i = 1; i <= 20; i++) {
                    File file = new File(String.join("", "F:\\document\\我\\x-easypdf\\pdf\\", String.valueOf(i), ".jfif"));
                    files.add(file);
                }
                ImageProcessor processor = PdfHandler.getDocumentProcessor(document).getImageProcessor();
                processor.toPdf(files.toArray(new File[0]));
                document.save("E:\\PDF\\pdfbox\\processor\\toPdfTest.pdf");
            }
        });
    }
}
