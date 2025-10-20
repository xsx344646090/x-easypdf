package org.dromara.pdf.pdfbox.base;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.MemoryPolicy;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.ext.processor.MergeProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.IdUtil;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xsx
 * @date 2024/8/14
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class BigDataTest extends BaseTest {

    /**
     * 测试大数据
     */
    @Test
    public void bigDataTest1() {
        for (int i = 0; i < 1; i++) {
            // 页面数：3465 耗时：2.531s 大小：8.56MB
            this.test(() -> {
                PdfHandler.disableScanSystemFonts();
                Document document = PdfHandler.getDocumentHandler().create();
                document.setMargin(50F);

                Page page = new Page(document);

                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 1000000; j++) {
                    builder.append("测试内容").append(j);
                }
                Textarea textarea = new Textarea(page);
                textarea.setText(builder.toString());
                textarea.render();

                document.appendPage(page);
                document.save("E:\\PDF\\pdfbox\\document\\bigDataTest1.pdf");
                System.out.println("内存占用：" + Runtime.getRuntime().totalMemory() / 1024 / 1024);
                document.close();
            });
        }
    }

    /**
     * 测试大数据
     */
    @Test
    public void bigDataTest2() {
        for (int k = 0; k < 10; k++) {
            // 页面数：290 耗时：0.531s 大小：778KB
            this.test(() -> {
                // PdfHandler.enableCompression(8);
                PdfHandler.disableScanSystemFonts();
                // PdfHandler.getFontHandler().addFont(new File("C:\\Windows\\Fonts\\simfang.ttf"));
                Document document = PdfHandler.getDocumentHandler().create();
                document.setMargin(50F);
                // document.setFontName("仿宋");
                Page page = new Page(document);

                for (int i = 0; i < 20; i++) {
                    StringBuilder builder = new StringBuilder();
                    for (int j = 0; j < 5000; j++) {
                        builder.append("测试内容").append(j);
                    }
                    Textarea textarea = new Textarea(document.getCurrentPage());
                    textarea.setText(builder.toString());
                    textarea.render();
                }

                document.appendPage(page);
                log.info("页面数：" + document.getTotalPageNumber());

                // this.test(() -> document.save("E:\\PDF\\pdfbox\\document\\bigDataTest-"+ IdUtil.get() +".pdf"), "保存");
                document.saveAndClose("E:\\PDF\\pdfbox\\document\\bigDataTest-"+ IdUtil.get() +".pdf");
            });
        }
    }

    /**
     * 测试大数据
     */
    @Test
    public void bigDataTest3() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create(MemoryPolicy.setupTempFileOnly("E:\\PDF\\pdfbox\\document"));
            document.setMargin(50F);

            int total = 500;

            List<File> files = new ArrayList<>(total);
            for (int i = 0; i < total; i++) {
                int finalI = i;
                this.test(() -> {
                    File file = new File("E:\\PDF\\pdfbox\\document\\bigDataTest3_" + finalI + ".pdf");
                    Document temp = PdfHandler.getDocumentHandler().create();
                    temp.setMargin(50F);
                    Page page = new Page(temp);
                    StringBuilder builder = new StringBuilder();
                    for (int j = 0; j < 5000; j++) {
                        builder.append(finalI).append("测试内容").append(j);
                    }
                    Textarea textarea = new Textarea(page);
                    textarea.setText(builder.toString());
                    textarea.render();

                    temp.appendPage(page);
                    temp.save(file);
                    temp.close();
                    files.add(file);
                }, "单次渲染");
            }

            MergeProcessor processor = PdfHandler.getDocumentProcessor(document).getMergeProcessor();
            int size = files.size() - 1;
            int begin = 0;
            int end = Math.min(49, size);
            do {
                List<File> subList = files.subList(begin, end);
                processor.merge(subList.toArray(new File[0]));
                begin = end;
                end = Math.min(end + 49, size);
            } while (end != size);

            document = processor.flush();
            log.info("页面数：" + document.getTotalPageNumber());

            Document finalDocument = document;
            this.test(() -> finalDocument.save("E:\\PDF\\pdfbox\\document\\bigDataTest3.pdf"), "保存");
            document.close();

            files.forEach(File::delete);
        });
    }
}
