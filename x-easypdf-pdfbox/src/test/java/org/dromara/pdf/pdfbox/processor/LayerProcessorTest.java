package org.dromara.pdf.pdfbox.processor;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.processor.ImageProcessor;
import org.dromara.pdf.pdfbox.core.ext.processor.LayerProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * @author xsx
 * @date 2024/2/5
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
public class LayerProcessorTest extends BaseTest {

    @Test
    public void test() {
        for (int i = 0; i < 2; i++) {
            this.layerTest();
        }
    }

    @Test
    public void layerTest() {
        this.test(() -> {
            File file = new File("E:\\PDF\\pdfbox\\hello-world.pdf");
            ITesseract instance = new Tesseract();  // JNA Interface Mapping
            // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
            instance.setLanguage("eng+chi_sim");
            instance.setDatapath("./src/test/resources/org/dromara/pdf/pdfbox/tessdata");

            try (
                    Document document = PdfHandler.getDocumentHandler().load(file)
            ) {
                ImageProcessor processor = PdfHandler.getImageProcessor(document);

                // pdf转图片
                float scale = 300 / 72f;
                processor.setDpi(72 * scale);
                processor.enableBinary();
                BufferedImage image = processor.image(ImageType.PNG, 0);

                // 识别文本
                List<Word> words = instance.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_TEXTLINE);
                Word word = words.get(0);

                // 创建文档
                Document doc = PdfHandler.getDocumentHandler().create();

                // 创建页面
                Page page = doc.createPage();

                // 添加文本
                float fontSizeOffset = 3F;
                Textarea textarea = new Textarea(page);
                textarea.setText(word.getText());
                textarea.setFontSize(fontSizeOffset + (float) word.getBoundingBox().getHeight() / scale);
                textarea.setBeginX((float) word.getBoundingBox().getX() / scale);
                textarea.setBeginY(fontSizeOffset + (float) (page.getHeight() - textarea.getFontSize() - word.getBoundingBox().getY() / scale));
                textarea.render();

                // 添加图层
                LayerProcessor layerProcessor = PdfHandler.getLayerProcessor(doc);
                layerProcessor.append("layer1", image, page);

                doc.appendPage(page);
                doc.save("E:\\PDF\\pdfbox\\layer\\layerTest.pdf");
                doc.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }
}
