package org.dromara.pdf.pdfbox.other;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.dromara.pdf.pdfbox.base.BaseTest;
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
        this.test(this::create);
    }

    public void create() {
        try {
            // Creating a PdfDocument object
            String dest = "E:\\PDF\\pdfbox\\table\\simpleTableTest1.pdf";
            PdfWriter writer = new PdfWriter(dest);

            // Creating a PdfDocument object
            PdfDocument pdf = new PdfDocument(writer);

            // Creating a Document object
            Document doc = new Document(pdf);
            doc.setMargins(0, 0, 0, 0);

            // Creating a table
            float [] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);

            // Adding cells to the table
            float height = 1000F;
            for (int i = 0; i < 15; i++) {
                Cell cell = new Cell();
                cell.setHeight(height);
                cell.add(new Paragraph("Name"));
                table.addCell(cell);
            }

            // Adding Table to document
            doc.add(table);

            // Closing the document
            doc.close();
            System.out.println("Table created successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
