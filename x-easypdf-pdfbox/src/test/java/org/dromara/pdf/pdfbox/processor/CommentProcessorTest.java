package org.dromara.pdf.pdfbox.processor;

import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Size;
import org.dromara.pdf.pdfbox.core.ext.processor.CommentProcessor;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.pdfbox.util.IdUtil;
import org.junit.Test;

import java.awt.*;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

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

    /**
     * 测试获取评论字典
     */
    @Test
    public void getMapTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();
                Map<Integer, List<PDAnnotation>> map = processor.getMap();
                map.forEach((key, value) -> System.out.println("第" + key + "页：" + value));
            }
        });
    }

    /**
     * 测试获取评论列表
     */
    @Test
    public void getListTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();
                List<PDAnnotation> list = processor.getList();
                list.forEach(System.out::println);
            }
        });
    }

    /**
     * 测试添加评论
     */
    @Test
    public void addTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                // 创建处理器
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();

                // 创建评论
                PDAnnotationFreeText commentAnnotation = new PDAnnotationFreeText();
                // 设置名称
                commentAnnotation.setAnnotationName(IdUtil.get());
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
                document.save("E:\\PDF\\pdfbox\\processor\\comment\\addTest.pdf");
            }
        });
    }

    /**
     * 测试添加评论
     */
    @Test
    public void setTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                // 创建处理器
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();

                // 创建评论
                PDAnnotationFreeText commentAnnotation = new PDAnnotationFreeText();
                // 设置名称
                commentAnnotation.setAnnotationName(IdUtil.get());
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
                // 替换评论
                processor.set(0, "test", commentAnnotation);

                // 保存
                document.save("E:\\PDF\\pdfbox\\processor\\comment\\setTest.pdf");
            }
        });
    }

    /**
     * 测试移除评论
     */
    @Test
    public void removeTest() {
        this.test(() -> {
            try (
                    Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\hello-world.pdf")
            ) {
                // 创建处理器
                CommentProcessor processor = PdfHandler.getDocumentProcessor(document).getCommentProcessor();

                // 移除评论
                processor.remove(0, "test", 0);
                // 保存
                document.save("E:\\PDF\\pdfbox\\processor\\comment\\removeTest.pdf");
            }
        });
    }
}
