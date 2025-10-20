package org.dromara.pdf.pdfbox.other;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Line;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;

/**
 * @author xsx
 * @date 2024/7/2
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
public class CatalogTest extends BaseTest {

    /**
     * 测试目录页
     */
    @Test
    public void catalogTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();
            // 设置边距
            document.setMargin(50F);
            // 创建页面
            Page page = new Page(document);
            // 创建文本域
            Textarea textarea = new Textarea(page);
            // 设置内容
            textarea.setText("目录");
            // 设置字体大小
            textarea.setFontSize(20F);
            // 渲染
            textarea.render();
            // 循环设置内容
            for (int i = 1; i <= 15; i++) {
                // 创建标题文本域
                Textarea title = new Textarea(page);
                // 设置内容
                title.setText(String.format("第 %s 章", i));
                // 设置字体大小
                title.setFontSize(12F);
                // 设置换行
                title.setIsWrap(true);
                // 设置上边距
                title.setMarginTop(20F);
                // 渲染
                title.render();
                // 创建分割线
                Line line = new Line(page);
                // 设置样式
                line.setLineStyle(LineStyle.DOTTED);
                // 设置线宽
                line.setLineWidth(1F);
                // 设置点线间隔
                line.setDottedSpacing(3F);
                // 设置左边距
                line.setMarginLeft(20F);
                // 设置右边距
                line.setMarginRight(20F);
                // 设置Y轴相对坐标
                line.setRelativeBeginY(-4F);
                // 设置线长
                if (i > 9) {
                    line.setLineLength(343F);
                } else {
                    line.setLineLength(350F);
                }
                // 渲染
                line.render();
                // 创建页码文本域
                Textarea pageNum = new Textarea(page);
                // 设置内容
                pageNum.setText(String.format("%d", i * 2));
                // 设置字体大小
                pageNum.setFontSize(12F);
                // 渲染
                pageNum.render();
            }
            // 添加页面
            document.appendPage(page);
            // 保存并关闭
            document.saveAndClose("E:\\PDF\\pdfbox\\textarea\\catalogTest.pdf");
        });
    }
}
