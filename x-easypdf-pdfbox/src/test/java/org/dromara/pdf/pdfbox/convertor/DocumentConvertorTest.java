package org.dromara.pdf.pdfbox.convertor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.PWLength;
import org.dromara.pdf.pdfbox.core.ext.convertor.excel.ExcelConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.excel.ExcelType;
import org.dromara.pdf.pdfbox.core.ext.convertor.html.HtmlConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.html.HtmlType;
import org.dromara.pdf.pdfbox.core.ext.convertor.ppt.PowerpointConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.ppt.PowerpointType;
import org.dromara.pdf.pdfbox.core.ext.convertor.rtf.RichTextConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.word.WordConvertor;
import org.dromara.pdf.pdfbox.core.ext.convertor.word.WordType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author xsx
 * @date 2024/12/18
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
public class DocumentConvertorTest extends BaseTest {
    
    /**
     * word转pdf测试
     */
    @Test
    public void wordToPdfForPathTest() {
        this.test(() -> {
            WordConvertor convertor = PdfHandler.getDocumentConvertor(null).getWordConvertor();
            Document document = convertor.toPdf(WordType.DOC, "E:\\PDF\\pdfbox\\convertor\\word\\test.doc");
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\word\\wordToPdfForPathTest.pdf");
        });
    }
    
    /**
     * word转pdf测试
     */
    @Test
    public void wordToPdfForFileTest() {
        this.test(() -> {
            WordConvertor convertor = PdfHandler.getDocumentConvertor(null).getWordConvertor();
            Document document = convertor.toPdf(WordType.DOC, new File("E:\\PDF\\pdfbox\\convertor\\word\\test.doc"));
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\word\\wordToPdfForFileTest.pdf");
        });
    }
    
    /**
     * word转pdf测试
     */
    @Test
    public void wordToPdfForStreamTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\word\\test.doc" ))) {
                WordConvertor convertor = PdfHandler.getDocumentConvertor(null).getWordConvertor();
                Document document = convertor.toPdf(WordType.DOC, inputStream);
                document.encryption(true, PWLength.LENGTH_128, "123", "123");
                document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\word\\wordToPdfForStreamTest.pdf");
            }
        });
    }
    
    /**
     * pdf转word测试
     */
    @Test
    public void pdfToWordForPathTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\word\\test.pdf");
            WordConvertor convertor = PdfHandler.getDocumentConvertor(document).getWordConvertor();
            boolean flag = convertor.toWord(WordType.DOCX, "E:\\PDF\\pdfbox\\convertor\\word\\pdfToWordForPathTest.docx");
            if (flag) {
                log.info("转换成功");
            }
            document.close();
        });
    }
    
    /**
     * pdf转word测试
     */
    @Test
    public void pdfToWordForFileTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\word\\test.pdf");
            WordConvertor convertor = PdfHandler.getDocumentConvertor(document).getWordConvertor();
            boolean flag = convertor.toWord(WordType.DOCX, new File("E:\\PDF\\pdfbox\\convertor\\word\\pdfToWordForFileTest.docx"));
            if (flag) {
                log.info("转换成功");
            }
            document.close();
        });
    }
    
    /**
     * pdf转word测试
     */
    @Test
    public void pdfToWordForStreamTest() {
        this.test(() -> {
            try (OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\word\\test.docx"))) {
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\word\\wordToPdfForFileTest.pdf");
                WordConvertor convertor = PdfHandler.getDocumentConvertor(document).getWordConvertor();
                boolean flag = convertor.toWord(WordType.DOCX, outputStream);
                if (flag) {
                    log.info("转换成功");
                }
                document.close();
            }
        });
    }
    
    /**
     * excel转pdf测试
     */
    @Test
    public void excelToPdfForPathTest() {
        this.test(() -> {
            ExcelConvertor convertor = PdfHandler.getDocumentConvertor(null).getExcelConvertor();
            Document document = convertor.toPdf(ExcelType.XLS, "E:\\PDF\\pdfbox\\convertor\\excel\\test.xls");
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\excel\\excelToPdfForPathTest.pdf");
        });
    }
    
    /**
     * excel转pdf测试
     */
    @Test
    public void excelToPdfForFileTest() {
        this.test(() -> {
            ExcelConvertor convertor = PdfHandler.getDocumentConvertor(null).getExcelConvertor();
            Document document = convertor.toPdf(ExcelType.XLSX, new File("E:\\PDF\\pdfbox\\convertor\\excel\\test.xlsx"));
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\excel\\excelToPdfForFileTest.pdf");
        });
    }
    
    /**
     * excel转pdf测试
     */
    @Test
    public void excelToPdfForStreamTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\excel\\test.xlsx" ))) {
                ExcelConvertor convertor = PdfHandler.getDocumentConvertor(null).getExcelConvertor();
                Document document = convertor.toPdf(ExcelType.XLSX, inputStream);
                document.encryption(true, PWLength.LENGTH_128, "123", "123");
                document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\excel\\excelToPdfForStreamTest.pdf");
            }
        });
    }
    
    /**
     * ppt转pdf测试
     */
    @Test
    public void pptToPdfForPathTest() {
        this.test(() -> {
            PowerpointConvertor convertor = PdfHandler.getDocumentConvertor(null).getPowerpointConvertor();
            Document document = convertor.toPdf(PowerpointType.PPTX, "E:\\PDF\\pdfbox\\convertor\\ppt\\test.pptx");
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\ppt\\pptToPdfForPathTest.pdf");
        });
    }
    
    /**
     * ppt转pdf测试
     */
    @Test
    public void pptToPdfForFileTest() {
        this.test(() -> {
            PowerpointConvertor convertor = PdfHandler.getDocumentConvertor(null).getPowerpointConvertor();
            Document document = convertor.toPdf(PowerpointType.PPTX, new File("E:\\PDF\\pdfbox\\convertor\\ppt\\test.pptx"));
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\ppt\\pptToPdfForFileTest.pdf");
        });
    }
    
    /**
     * ppt转pdf测试
     */
    @Test
    public void pptToPdfForStreamTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\ppt\\test.pptx" ))) {
                PowerpointConvertor convertor = PdfHandler.getDocumentConvertor(null).getPowerpointConvertor();
                Document document = convertor.toPdf(PowerpointType.PPTX, inputStream);
                document.encryption(true, PWLength.LENGTH_128, "123", "123");
                document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\ppt\\pptToPdfForStreamTest.pdf");
            }
        });
    }
    
    /**
     * html转pdf测试
     */
    @Test
    public void htmlToPdfForPathTest() {
        this.test(() -> {
            HtmlConvertor convertor = PdfHandler.getDocumentConvertor(null).getHtmlConvertor();
            Document document = convertor.toPdf(HtmlType.HTML, "E:\\PDF\\pdfbox\\convertor\\html\\test.html");
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\excelToPdfForPathTest.pdf");
        });
    }
    
    /**
     * html转pdf测试
     */
    @Test
    public void htmlToPdfForFileTest() {
        this.test(() -> {
            HtmlConvertor convertor = PdfHandler.getDocumentConvertor(null).getHtmlConvertor();
            Document document = convertor.toPdf(HtmlType.HTML, new File("E:\\PDF\\pdfbox\\convertor\\html\\test.html"));
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\excelToPdfForFileTest.pdf");
        });
    }
    
    /**
     * html转pdf测试
     */
    @Test
    public void htmlToPdfForStreamTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\html\\test.html" ))) {
                HtmlConvertor convertor = PdfHandler.getDocumentConvertor(null).getHtmlConvertor();
                Document document = convertor.toPdf(HtmlType.HTML, inputStream);
                document.encryption(true, PWLength.LENGTH_128, "123", "123");
                document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\html\\excelToPdfForStreamTest.pdf");
            }
        });
    }
    
    /**
     * rtf转pdf测试
     */
    @Test
    public void rtfToPdfForPathTest() {
        this.test(() -> {
            RichTextConvertor convertor = PdfHandler.getDocumentConvertor(null).getRichTextConvertor();
            Document document = convertor.toPdf("E:\\PDF\\pdfbox\\convertor\\rtf\\test.rtf");
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\rtf\\rtfToPdfForPathTest.pdf");
        });
    }
    
    /**
     * rtf转pdf测试
     */
    @Test
    public void rtfToPdfForFileTest() {
        this.test(() -> {
            RichTextConvertor convertor = PdfHandler.getDocumentConvertor(null).getRichTextConvertor();
            Document document = convertor.toPdf(new File("E:\\PDF\\pdfbox\\convertor\\rtf\\test.rtf"));
            document.encryption(true, PWLength.LENGTH_128, "123", "123");
            document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\rtf\\rtfToPdfForFileTest.pdf");
        });
    }
    
    /**
     * rtf转pdf测试
     */
    @Test
    public void rtfToPdfForStreamTest() {
        this.test(() -> {
            try (InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\rtf\\test.rtf" ))) {
                RichTextConvertor convertor = PdfHandler.getDocumentConvertor(null).getRichTextConvertor();
                Document document = convertor.toPdf(inputStream);
                document.encryption(true, PWLength.LENGTH_128, "123", "123");
                document.saveAndClose("E:\\PDF\\pdfbox\\convertor\\rtf\\rtfToPdfForStreamTest.pdf");
            }
        });
    }
    
    /**
     * pdf转rtf测试
     */
    @Test
    public void pdfToRtfForPathTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\rtf\\test.pdf", "123");
            document.decrypt();
            RichTextConvertor convertor = PdfHandler.getDocumentConvertor(document).getRichTextConvertor();
            boolean flag = convertor.toRtf("E:\\PDF\\pdfbox\\convertor\\rtf\\pdfToRtfForPathTest.rtf");
            if (flag) {
                log.info("转换成功");
            }
            document.close();
        });
    }
    
    /**
     * pdf转rtf测试
     */
    @Test
    public void pdfToRtfForFileTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\rtf\\test.pdf", "123");
            document.decrypt();
            RichTextConvertor convertor = PdfHandler.getDocumentConvertor(document).getRichTextConvertor();
            boolean flag = convertor.toRtf(new File("E:\\PDF\\pdfbox\\convertor\\rtf\\pdfToRtfForFileTest.rtf"));
            if (flag) {
                log.info("转换成功");
            }
            document.close();
        });
    }
    
    /**
     * pdf转rtf测试
     */
    @Test
    public void pdfToRtfForStreamTest() {
        this.test(() -> {
            try (OutputStream outputStream = Files.newOutputStream(Paths.get("E:\\PDF\\pdfbox\\convertor\\rtf\\pdfToRtfForStreamTest.rtf"))) {
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\convertor\\rtf\\test.pdf", "123");
                document.decrypt();
                RichTextConvertor convertor = PdfHandler.getDocumentConvertor(document).getRichTextConvertor();
                boolean flag = convertor.toRtf(outputStream);
                if (flag) {
                    log.info("转换成功");
                }
                document.close();
            }
        });
    }
}
