package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.AttachmentProcessor;
import org.dromara.pdf.pdfbox.core.info.EmbeddedFileInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/**
 * @author xsx
 * @date 2025/1/21
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
public class AttachmentProcessorTest extends BaseTest {

    /**
     * 测试获取文件
     */
    @Test
    public void getFileTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\attachmentTest.pdf");
            ) {
                String fileName = "test.xlsx";
                AttachmentProcessor processor = PdfHandler.getDocumentProcessor(document).getAttachmentProcessor();
                Map<String, EmbeddedFileInfo> fileMap = processor.getFileMap();
                EmbeddedFileInfo info = fileMap.get(fileName);
                Files.write(new File("E:\\PDF\\pdfbox\\processor", fileName).toPath(), info.getFileBytes());
            }
        });
    }

    /**
     * 测试添加或替换文件
     */
    @Test
    public void addOrSetFileTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\attachmentTest.pdf")
            ) {
                AttachmentProcessor processor = PdfHandler.getDocumentProcessor(document).getAttachmentProcessor();
                String fileName = "test.txt";
                byte[] data = "测试文本".getBytes(StandardCharsets.UTF_8);
                PDEmbeddedFile embeddedFile = processor.buildEmbeddedFile(data, "text/plain");
                EmbeddedFileInfo file = new EmbeddedFileInfo(fileName, "测试文本", embeddedFile);
                processor.addOrSet(file);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\addOrSetFileTest.pdf");
            }
        });
    }

    /**
     * 测试移除文件
     */
    @Test
    public void removeFileTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\processor\\attachmentTest.pdf")
            ) {
                AttachmentProcessor processor = PdfHandler.getDocumentProcessor(document).getAttachmentProcessor();
                String fileName = "test.pdf";
                processor.remove(fileName);
                processor.flush();
                document.save("E:\\PDF\\pdfbox\\processor\\removeFileTest.pdf");
            }
        });
    }
}
