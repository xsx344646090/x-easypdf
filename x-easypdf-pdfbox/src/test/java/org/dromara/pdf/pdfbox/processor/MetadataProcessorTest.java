package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.MetadataProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author xsx
 * @date 2024/2/19
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
public class MetadataProcessorTest extends BaseTest {

    /**
     * 测试元数据
     */
    @Test
    public void metadataTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\comment\\addCommentTest.pdf")
            ) {
                MetadataProcessor processor = new MetadataProcessor(document);
                processor.setProducer("myProducer");
                processor.setKeywords("myKeywords1", "myKeywords2");
                processor.setCreateDate(Calendar.getInstance());
                processor.setModifyDate(Calendar.getInstance());
                processor.setCreatorTool("myCreatorTool");
                processor.setTitle("myTitle");
                processor.setAuthors("myAuthors1", "myAuthors2");
                processor.setDescription("description");
                processor.setFormat("pdf");
                processor.setRights("myRights");
                processor.setRightsMarked(Boolean.FALSE);
                processor.setRightsUrl("https://x-easypdf.cn");
                processor.flush();

                document.save("E:\\PDF\\pdfbox\\metadata\\metadataTest.pdf");
            }
        });
    }
}
