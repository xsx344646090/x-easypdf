package org.dromara.pdf.pdfbox.other;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.util.IdUtil;
import org.junit.Test;

/**
 * @author xsx
 * @date 2024/6/4
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
public class ItextTest extends BaseTest {

    /**
     * 测试目录页
     */
    @Test
    public void itextTest() {
        for (int k = 0; k < 10; k++) {
            this.test(this::create);
        }
    }

    public void create() {
        try {
            String fontPath = "E:\\Workspace\\x-easypdf\\gitee\\x-easypdf-pdfbox\\src\\main\\resources\\org\\dromara\\pdf\\pdfbox\\ttf\\HarmonyOS_Sans_SC_Regular.ttf"; // 中文字体文件路径
            // 加载中文字体
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);

            // Creating a PdfDocument object
            String dest = "E:\\PDF\\pdfbox\\document\\simpleTableTest-" + IdUtil.get() + ".pdf";
            PdfWriter writer = new PdfWriter(dest);

            // Creating a PdfDocument object
            PdfDocument pdf = new PdfDocument(writer);

            // Creating a Document object
            Document doc = new Document(pdf);
            doc.setMargins(50, 50, 50, 50);

            Paragraph paragraph = new Paragraph().setFont(font).setMultipliedLeading(0.6F);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 5000; j++) {
                    builder.append("测试内容").append(j);
                }
            }
            paragraph.add(builder.toString());

            // Adding Paragraph to document
            doc.add(paragraph);
            // Closing the document
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
