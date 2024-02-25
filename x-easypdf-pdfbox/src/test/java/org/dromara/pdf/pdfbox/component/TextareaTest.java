package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
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
public class TextareaTest extends BaseTest {

    @Test
    public void globalTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Paths.get("F:\\pdf\\OTF\\SimplifiedChinese\\SourceHanSerifSC-Regular.otf").toFile());

            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontName("微软雅黑");
            document.setFontColor(Color.RED);
            document.setFontSize(12F);

            Page page = document.createPage(PageSize.A4);
            page.setMargin(20F);
            page.setFontName("仿宋");
            page.setFontColor(Color.BLUE);
            page.setFontSize(20F);

            Textarea textarea = new Textarea(document.getCurrentPage());
            textarea.setText("这是第一页，使用的页面设置");
            textarea.render();
            System.out.println("page.getLastIndex() = " + page.getLastIndex());

            textarea.setIsBreak(true);
            textarea.setText("这是第二页，使用的上个页面设置");
            textarea.render();
            System.out.println("page.getLastIndex() = " + page.getLastIndex());

            textarea = new Textarea(document.getCurrentPage());
            textarea.setText("这是第二页换行文本，使用的自定义设置");
            textarea.setIsWrap(true);
            textarea.setFontColor(Color.BLACK);
            textarea.setFontSize(6F);
            textarea.render();
            System.out.println("page.getLastIndex() = " + page.getLastIndex());

            Page page1 = document.createPage(PageSize.A4);

            textarea = new Textarea(document.getCurrentPage());
            textarea.setText("这是第三页，使用的全局设置");
            textarea.render();
            System.out.println("page1.getLastIndex() = " + (page.getLastIndex() + page1.getLastIndex()));

            document.appendPage(page);
            document.appendPage(page1);
            document.save("E:\\PDF\\pdfbox\\textarea\\globalTest.pdf");
            document.close();
        });
    }

    @Test
    public void wrapTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(20F);

            Page page = document.createPage(PageSize.A4);

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

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\wrapTest.pdf");
            document.close();
        });
    }

    @Test
    public void fontStyleTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = document.createPage(PageSize.A4);

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

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\fontStyleTest.pdf");
            document.close();
        });
    }

    @Test
    public void testMargin() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageSize.A4);
        Textarea textarea = new Textarea(page);
        textarea.setMarginTop(100F);
        textarea.setText("测试文本");
        textarea.render();
        document.appendPage(page);
        document.save("E:\\PDF\\textarea\\testMargin.pdf");
        document.close();
    }

    @Test
    public void testHighlight() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageSize.A4);
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
        document.save("D:\\PDF\\textarea\\testHighlight.pdf");
        document.close();
    }

    @Test
    public void testUnderline() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageSize.A4);
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
        document.save("D:\\PDF\\textarea\\testUnderline.pdf");
        document.close();
    }

    @Test
    public void testDeleteLine() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageSize.A4);
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
        document.save("D:\\PDF\\textarea\\testDeleteLine.pdf");
        document.close();
    }

    @Test
    public void testOuterDest() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(30F);
        Page page = document.createPage(PageSize.A4);
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
        document.save("D:\\PDF\\textarea\\testLink.pdf");
        document.close();
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
        Page page = document.createPage(PageSize.A4);
        Textarea textarea = new Textarea(page);
        textarea.setHighlightColor(Color.CYAN);
        textarea.setIsHighlight(true);
        textarea.setIsDeleteLine(true);
        textarea.setIsUnderline(true);
        textarea.setText(builder.toString());
        textarea.render();
        document.appendPage(page);
        document.save("D:\\PDF\\textarea\\testAutoBreak.pdf");
        document.close();
    }

    @Test
    public void testBorder() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        document.setFontSize(12F);
        Page page = document.createPage(PageSize.A4);
        Textarea textarea = new Textarea(page);
        textarea.setIsBorderTop(true);
        textarea.setLeading(12F);
        textarea.setBorderColor(Color.CYAN);
        textarea.setText("爽爽的贵阳，避暑的天堂1");
        textarea.setCatalog(new CatalogInfo("爽爽的贵阳，避暑的天堂1"));
        textarea.render();
        textarea.setIsBorderTop(false);
        textarea.setIsBorderBottom(true);
        textarea.setMarginLeft(200F);
        textarea.setText("爽爽的贵阳，避暑的天堂2");
        textarea.setCatalog(new CatalogInfo("爽爽的贵阳，避暑的天堂2"));
        textarea.render();
        textarea.setIsWrap(true);
        textarea.setIsBorderBottom(false);
        textarea.setIsBorderLeft(true);
        textarea.setMarginLeft(0F);
        textarea.setText("爽爽的贵阳，避暑的天堂3");
        textarea.setCatalog(new CatalogInfo("爽爽的贵阳，避暑的天堂3"));
        textarea.render();
        textarea.setIsWrap(false);
        textarea.setIsBorderLeft(false);
        textarea.setIsBorderRight(true);
        textarea.setMarginLeft(200F);
        textarea.setText("爽爽的贵阳，避暑的天堂4");
        textarea.setCatalog(new CatalogInfo("爽爽的贵阳，避暑的天堂4"));
        textarea.render();
        Page page1 = document.createPage(PageSize.A4);
        textarea = new Textarea(page1);
        textarea.setIsBorder(true);
        textarea.setText("爽爽的贵阳，避暑的天堂");
        textarea.setCatalog(new CatalogInfo("爽爽的贵阳，避暑的天堂"));
        textarea.render();
        document.appendPage(page);
        document.appendPage(page1);
        document.flushCatalog();
        List<CatalogInfo> catalogs = document.getCatalogs();
        catalogs.forEach(System.out::println);
        document.save("E:\\PDF\\textarea\\testBorder.pdf");
        document.close();
    }
}
