package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.ReplaceProcessor;
import org.dromara.pdf.pdfbox.core.info.ReplaceInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class ReplaceProcessorTest extends BaseTest {

    /**
     * 测试替换文本
     */
    @Test
    public void replaceTextTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                PDFont font = document.getContext().getFont("仿宋");
                Map<String, String> map = new HashMap<>();
                map.put("World", "X-easypdf");
                processor.replaceText(font, map, 0);
                document.save("E:\\PDF\\pdfbox\\processor\\replace\\replaceTextTest.pdf");
            }
        });
    }

    /**
     * 测试替换文本
     */
    @Test
    public void replaceTextTest2() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                PDFont font = document.getContext().getFont("微软雅黑");
                // 替换指定第一个与第二个
                ReplaceInfo replaceInfo1 = new ReplaceInfo('贵', '遵', Stream.of(0, 1).collect(Collectors.toSet()), font);
                // 替换全部
                ReplaceInfo replaceInfo2 = ReplaceInfo.builder().original('阳').value('义').font(font).build();
                // 替换为空（移除）
                ReplaceInfo replaceInfo3 = new ReplaceInfo('百', '\u0000', Stream.of(1).collect(Collectors.toSet()), font);
                ReplaceInfo replaceInfo4 = new ReplaceInfo('科', '\u0000', Stream.of(1).collect(Collectors.toSet()), font);
                processor.replaceText(Arrays.asList(replaceInfo1, replaceInfo2, replaceInfo3, replaceInfo4), 0);
                document.save("E:\\PDF\\pdfbox\\processor\\replace\\replaceTextTest2.pdf");
            }
        });
    }

    /**
     * 测试替换图像
     */
    @Test
    public void replaceImageTest() {
        this.test(() -> {
            try (Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\allTest.pdf")) {
                ReplaceProcessor processor = PdfHandler.getDocumentProcessor(document).getReplaceProcessor();
                BufferedImage image = ImageUtil.read(new File("E:\\PDF\\pdfbox\\test.jpg"));
                processor.replaceImage(image, Arrays.asList(0, 1), 0);
                document.save("E:\\PDF\\pdfbox\\processor\\replace\\replaceImageTest.pdf");
            }
        });
    }
}
