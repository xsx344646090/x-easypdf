package org.dromara.pdf.pdfbox.processor;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.enums.FormFieldStateStyle;
import org.dromara.pdf.pdfbox.core.enums.ImageFieldLayout;
import org.dromara.pdf.pdfbox.core.ext.processor.form.*;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ImageUtil;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xsx
 * @date 2023/11/22
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
public class FormProcessorTest extends BaseTest {
    
    /**
     * 测试文本填写
     */
    @Test
    public void fillTextTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\form\\form.pdf")
            ) {
                FormProcessor processor = new FormProcessor(document);
                processor.setFont("微软雅黑", 12F, Color.BLACK);
                
                Map<String, String> map = new HashMap<>(1);
                map.put("test2", "其他");
                processor.fillText(map);
                processor.readOnly();
                
                document.save("E:\\PDF\\pdfbox\\form\\fillTextTest3.pdf");
            }
        });
    }
    
    /**
     * 测试图像填写
     */
    @Test
    public void fillImageTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\form\\addImageFieldTest.pdf")
            ) {
                FormProcessor processor = new FormProcessor(document);
                
                Map<String, BufferedImage> map = new HashMap<>(1);
                map.put("test", ImageUtil.read(new File("E:\\PDF\\pdfbox\\image\\test.png")));
                processor.fillImage(map);
                
                document.save("E:\\PDF\\pdfbox\\form\\fillImageTest.pdf");
            }
        });
    }
    
    /**
     * 测试单选填写
     */
    @Test
    public void fillRadioTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\form\\addRadioFieldTest.pdf")
            ) {
                FormProcessor processor = new FormProcessor(document);
                
                processor.fillRadio("test", 2);
                
                document.save("E:\\PDF\\pdfbox\\form\\fillRadioFieldTest.pdf");
            }
        });
    }
    
    /**
     * 测试多选填写
     */
    @Test
    public void fillCheckBoxTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\form\\addCheckBoxFieldTest.pdf")
            ) {
                FormProcessor processor = new FormProcessor(document);
                Map<String, Boolean> map = new HashMap<>(3);
                map.put("test1", false);
                map.put("test2", false);
                map.put("test3", false);
                
                processor.fillCheckBox(map);
                
                document.save("E:\\PDF\\pdfbox\\form\\fillCheckBoxTest.pdf");
            }
        });
    }
    
    /**
     * 测试添加文本字段
     */
    @Test
    public void addTextFieldTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            Page page = new Page(document);
            // 需要先添加页面
            document.appendPage(page);
            
            FormProcessor processor = new FormProcessor(document);
            
            TextFieldBuilder builder = TextFieldBuilder.builder(document, page, Size.create(0F, 100F, 0F, 50F));
            builder.setName("test");
            builder.setFontName("仿宋");
            builder.setMaxLength(50);
            builder.setIsMultiline(true);
            
            processor.addField(builder);
            
            document.saveAndClose("E:\\PDF\\pdfbox\\form\\addTextFieldTest.pdf");
        });
    }
    
    /**
     * 测试添加图像字段
     */
    @Test
    public void addImageFieldTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            Page page = new Page(document);
            // 需要先添加页面
            document.appendPage(page);
            
            FormProcessor processor = new FormProcessor(document);
            
            ImageFieldBuilder builder = ImageFieldBuilder.builder(document, page, Size.create(0F, 50F, 0F, 50F));
            builder.setName("test");
            builder.setLabel("test");
            builder.setLayout(ImageFieldLayout.IMAGE_BEHIND);
            
            processor.addField(builder);
            
            document.saveAndClose("E:\\PDF\\pdfbox\\form\\addImageFieldTest.pdf");
        });
    }
    
    /**
     * 测试添加单选字段
     */
    @Test
    public void addRadioFieldTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            Page page = new Page(document);
            // 需要先添加页面
            document.appendPage(page);
            
            FormProcessor processor = new FormProcessor(document);
            
            float leftX = 100F;
            float rightX = 120F;
            float bottomY = 480F;
            float topY = 500F;
            RadioFieldBuilder builder = RadioFieldBuilder.builder(document, page);
            builder.setName("test");
            for (int i = 0; i < 6; i++) {
                RadioField field = new RadioField(Size.create(leftX, rightX, bottomY, topY), "选项" + i);
                field.setStyle(FormFieldStateStyle.values()[i]);
                field.setIsSelected(i == 1);
                builder.addFields(field);
                leftX = leftX + 30F;
                rightX = rightX + 30F;
            }
            processor.addField(builder);
            
            document.saveAndClose("E:\\PDF\\pdfbox\\form\\addRadioFieldTest.pdf");
        });
    }
    
    /**
     * 测试添加多选字段
     */
    @Test
    public void addCheckBoxFieldTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            Page page = new Page(document);
            // 需要先添加页面
            document.appendPage(page);
            
            FormProcessor processor = new FormProcessor(document);
            
            float leftX = 100F;
            float rightX = 120F;
            float bottomY = 480F;
            float topY = 500F;
            for (int i = 0; i < 6; i++) {
                CheckBoxFieldBuilder builder = CheckBoxFieldBuilder.builder(document, page, Size.create(leftX, rightX, bottomY, topY));
                builder.setName("test" + i);
                builder.setValue("选项" + i);
                builder.setStyle(FormFieldStateStyle.values()[i]);
                builder.setIsSelected(true);
                processor.addField(builder);
                
                leftX = leftX + 30F;
                rightX = rightX + 30F;
            }
            
            document.saveAndClose("E:\\PDF\\pdfbox\\form\\addCheckBoxFieldTest.pdf");
        });
    }
}
