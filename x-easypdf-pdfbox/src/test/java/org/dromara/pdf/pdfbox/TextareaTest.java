package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.*;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author xsx
 * @date 2023/7/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class TextareaTest {
    @Test
    public void testGlobal() {
        PdfHandler.getFontHandler().addFont(Paths.get("D:\\PDF\\textarea\\SourceHanSerifSC-Regular.otf").toFile());
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontName("SourceHanSerifSC-Regular");
        document.setFontColor(Color.RED);
        document.setFontSize(12F);
        Page page = document.createPage(PageRectangle.A4);
        page.setMargin(20F);
        page.setFontName("SimSun");
        page.setFontColor(Color.BLUE);
        page.setFontSize(20F);
        Textarea textarea = new Textarea(document.getCurrentPage());
        textarea.setText("这是第一页，使用的页面设置");
        textarea.render();
        textarea.setIsBreak(true);
        textarea.setText("这是第二页，使用的上个页面设置");
        textarea.render();
        textarea = new Textarea(page);
        textarea.setText("这是第二页换行文本，使用的自定义设置");
        textarea.setIsWrap(true);
        textarea.setFontColor(Color.BLACK);
        textarea.setFontSize(6F);
        textarea.render();
        page = document.createPage(PageRectangle.A4);
        textarea = new Textarea(page);
        textarea.setText("这是第三页，使用的全局设置");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testGlobal.pdf");
    }

    @Test
    public void testWrap() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setText("HelloWorld1\nHelloWorld2\nHelloWorld3");
        textarea.render();
        textarea = new Textarea(page);
        textarea.setText("不换行");
        textarea.render();
        textarea = new Textarea(page);
        textarea.setIsWrap(true);
        textarea.setText("手动换行");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testWrap.pdf");
    }

    @Test
    public void testFontStyle() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.NORMAL);
        textarea.setText("x-easypdf（NORMAL）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.BOLD);
        textarea.setText("x-easypdf（BOLD）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.LIGHT);
        textarea.setText("x-easypdf（LIGHT）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.ITALIC);
        textarea.setText("x-easypdf（ITALIC）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.STROKE);
        textarea.setText("x-easypdf（STROKE）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.HIDDEN);
        textarea.setText("x-easypdf（HIDDEN）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.ITALIC_BOLD);
        textarea.setText("x-easypdf（ITALIC_BOLD）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.ITALIC_LIGHT);
        textarea.setText("x-easypdf（ITALIC_LIGHT）");
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setFontStyle(FontStyle.ITALIC_STROKE);
        textarea.setText("x-easypdf（ITALIC_STROKE）");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testFontStyle.pdf");
    }

    @Test
    public void testHighlight() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setHighlightColor(Color.CYAN);
        textarea.setIsHighlight(true);
        textarea.setText("这是\n高亮文本");
        textarea.render();
        textarea.setIsHighlight(false);
        textarea.setText("关闭\n高亮文本");
        textarea.render();
        textarea.setIsHighlight(true);
        textarea.setText("开启\n高亮文本");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testHighlight.pdf");
    }

    @Test
    public void testUnderline() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setUnderlineColor(Color.CYAN);
        textarea.setIsUnderline(true);
        textarea.setText("这是\n下划线文本");
        textarea.render();
        textarea.setIsUnderline(false);
        textarea.setText("关闭\n下划线文本");
        textarea.render();
        textarea.setIsUnderline(true);
        textarea.setText("开启\n下划线文本");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testUnderline.pdf");
    }

    @Test
    public void testDeleteLine() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setDeleteLineColor(Color.CYAN);
        textarea.setIsDeleteLine(true);
        textarea.setText("这是\n删除线文本");
        textarea.render();
        textarea.setIsDeleteLine(false);
        textarea.setText("关闭\n删除线文本");
        textarea.render();
        textarea.setIsDeleteLine(true);
        textarea.setText("开启\n删除线文本");
        textarea.render();
        document.saveAndClose("D:\\PDF\\textarea\\testDeleteLine.pdf");
    }

    @Test
    public void testOuterDest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setUnderlineColor(Color.BLUE);
        textarea.setIsUnderline(true);
        textarea.setOuterDest(
                        OuterDest.create()
                                .setName("x-easypdf官网")
                                .setUrl("https://x-easypdf.cn")
                                .setHighlightMode(HighlightMode.OUTLINE)
                );
        textarea.setText("这是\n超链接文本");
        textarea.render();
        document.appendPage(page);
        document.saveAndClose("D:\\PDF\\textarea\\testLink.pdf");
    }

    @Test
    public void testAutoBreak() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("爽爽的贵阳，避暑的天堂");
        }
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setHighlightColor(Color.CYAN);
        textarea.setIsHighlight(true);
        textarea.setIsDeleteLine(true);
        textarea.setIsUnderline(true);
        textarea.setText(builder.toString());
        textarea.render();
        document.appendPage(page);
        document.saveAndClose("D:\\PDF\\textarea\\testAutoBreak.pdf");
    }

    @Test
    public void testBorder() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(12F);
        Page page = document.createPage(PageRectangle.A4);
        Textarea textarea = new Textarea(page);
        textarea.setIsBorderTop(true);
        textarea.setLeading(12F);
        textarea.setBorderColor(Color.CYAN);
        textarea.setText("爽爽的贵阳，避暑的天堂1");
        textarea.setCatalog(new Catalog("爽爽的贵阳，避暑的天堂1"));
        textarea.render();
        textarea.setIsBorderTop(false);
        textarea.setIsBorderBottom(true);
        textarea.setMarginLeft(200F);
        textarea.setText("爽爽的贵阳，避暑的天堂2");
        textarea.setCatalog(new Catalog("爽爽的贵阳，避暑的天堂2"));
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setIsBorderBottom(false);
        textarea.setIsBorderLeft(true);
        textarea.setMarginLeft(0F);
        textarea.setText("爽爽的贵阳，避暑的天堂3");
        textarea.setCatalog(new Catalog("爽爽的贵阳，避暑的天堂3"));
        textarea.render();
        textarea.setIsWrap(false);
        textarea.setIsBorderLeft(false);
        textarea.setIsBorderRight(true);
        textarea.setMarginLeft(200F);
        textarea.setText("爽爽的贵阳，避暑的天堂4");
        textarea.setCatalog(new Catalog("爽爽的贵阳，避暑的天堂4"));
        textarea.render();
        Page page1 = document.createPage(PageRectangle.A4);
        textarea = new Textarea(page1);
        textarea.setIsBorder(true);
        textarea.setText("爽爽的贵阳，避暑的天堂");
        textarea.setCatalog(new Catalog("爽爽的贵阳，避暑的天堂"));
        textarea.render();
        List<Catalog> catalogs = document.appendPage(page).appendPage(page1).flushCatalog().getCatalogs();
        catalogs.forEach(System.out::println);
        document.saveAndClose("E:\\PDF\\textarea\\testBorder.pdf");
    }
}
