package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.comparator.DocumentComparator;
import org.dromara.pdf.pdfbox.core.info.ImageCompareInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author xsx
 * @date 2024/8/3
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
public class ImageComparatorTest extends BaseTest {

    /**
     * 测试图像比较
     */
    @Test
    public void compareToTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf");
                    Document document2 = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")
            ) {
                DocumentComparator comparator = PdfHandler.getDocumentComparator(document2);
                Map<Integer, List<ImageCompareInfo>> map = comparator.compareToImage(document);
                System.out.println("map = " + map);
            }
        });
    }
}
