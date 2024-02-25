package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.ext.processor.CommentProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.junit.Test;

import java.awt.*;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * @author xsx
 * @date 2023/12/23
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
public class CommentProcessorTest extends BaseTest {

    @Test
    public void addCommentTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                // 创建处理器
                CommentProcessor processor = new CommentProcessor(document);

                // 创建评论
                PDAnnotationFreeText commentAnnotation = new PDAnnotationFreeText();
                // 设置名称
                commentAnnotation.setAnnotationName(UUID.randomUUID().toString());
                // 设置内容
                commentAnnotation.setContents("测试");
                // 设置背景颜色
                commentAnnotation.setColor(ColorUtil.toPDColor(Color.CYAN));
                // 设置作者
                commentAnnotation.setTitlePopup("xsx");
                // 设置主题
                commentAnnotation.setSubject("主题");
                // 设置日期
                commentAnnotation.setModifiedDate((new GregorianCalendar()));
                // 设置范围
                commentAnnotation.setRectangle(Size.create(200F, 300F, 200F, 240F).getRectangle());
                // 添加评论
                processor.add(0, "宋体", commentAnnotation);

                // 保存
                document.save("E:\\PDF\\pdfbox\\comment\\addCommentTest.pdf");
            }
        });
    }
}
