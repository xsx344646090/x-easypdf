package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.component.Text;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.nio.file.Paths;

/**
 * @author xsx
 * @date 2023/7/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class TextTest {
    @Test
    public void testGlobal() {
        PdfHandler.getFontHandler().addFont(Paths.get("D:\\PDF\\text\\SourceHanSerifSC-Regular.otf").toFile());
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontName("SourceHanSerifSC-Regular").setFontColor(Color.RED).setFontSize(12F);
        Page page = PdfHandler.getPageHandler().build(document);
        page.setMargin(20F).setFontName("SimSun").setFontColor(Color.BLUE).setFontSize(20F);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setText("这是第一页，使用的页面设置").render();
        page = text.setBreak(true).setText("这是第二页，使用的上个页面设置").render();
        text = PdfHandler.getTextHandler().build(page);
        text.setText("这是第二页换行文本，使用的自定义设置").setWrap(true).setFontColor(Color.BLACK).setFontSize(6F).render();
        page = PdfHandler.getPageHandler().build(document);
        text = PdfHandler.getTextHandler().build(page);
        text.setText("这是第三页，使用的全局设置").render();
        document.saveAndClose("D:\\PDF\\text\\testGlobal.pdf");
    }

    @Test
    public void testWrap() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setText("HelloWorld1\nHelloWorld2\nHelloWorld3").render();
        text = PdfHandler.getTextHandler().build(page);
        text.setText("不换行").render();
        text = PdfHandler.getTextHandler().build(page);
        text.setWrap(true).setText("手动换行").render();
        document.saveAndClose("D:\\PDF\\text\\testWrap.pdf");
    }

    @Test
    public void testFontStyle() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setWrap(true).setFontStyle(FontStyle.NORMAL).setText("x-easypdf（NORMAL）").render();
        text.setWrap(true).setFontStyle(FontStyle.BOLD).setText("x-easypdf（BOLD）").render();
        text.setWrap(true).setFontStyle(FontStyle.LIGHT).setText("x-easypdf（LIGHT）").render();
        text.setWrap(true).setFontStyle(FontStyle.ITALIC).setText("x-easypdf（ITALIC）").render();
        text.setWrap(true).setFontStyle(FontStyle.STROKE).setText("x-easypdf（STROKE）").render();
        text.setWrap(true).setFontStyle(FontStyle.HIDDEN).setText("x-easypdf（HIDDEN）").render();
        text.setWrap(true).setFontStyle(FontStyle.ITALIC_BOLD).setText("x-easypdf（ITALIC_BOLD）").render();
        text.setWrap(true).setFontStyle(FontStyle.ITALIC_LIGHT).setText("x-easypdf（ITALIC_LIGHT）").render();
        text.setWrap(true).setFontStyle(FontStyle.ITALIC_STROKE).setText("x-easypdf（ITALIC_STROKE）").render();
        document.saveAndClose("D:\\PDF\\text\\testFontStyle.pdf");
    }

    @Test
    public void testHighlight() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setHighlightColor(Color.CYAN).setHighlight(true).setText("这是\n高亮文本").render();
        text.setHighlight(false).setText("关闭\n高亮文本").render();
        text.setHighlight(true).setText("开启\n高亮文本").render();
        document.saveAndClose("D:\\PDF\\text\\testHighlight.pdf");
    }

    @Test
    public void testUnderline() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setUnderlineColor(Color.CYAN).setUnderline(true).setText("这是\n下划线文本").render();
        text.setUnderline(false).setText("关闭\n下划线文本").render();
        text.setUnderline(true).setText("开启\n下划线文本").render();
        document.saveAndClose("D:\\PDF\\text\\testUnderline.pdf");
    }

    @Test
    public void testDeleteLine() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setDeleteLineColor(Color.CYAN).setDeleteLine(true).setText("这是\n删除线文本").render();
        text.setDeleteLine(false).setText("关闭\n删除线文本").render();
        text.setDeleteLine(true).setText("开启\n删除线文本").render();
        document.saveAndClose("D:\\PDF\\text\\testDeleteLine.pdf");
    }

    @Test
    public void testLink() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setUnderlineColor(Color.BLUE)
                .setUnderline(true)
                .setLinkUrl("https://www.x-easypdf.cn")
                .setText("这是\n超链接文本")
                .render();
        document.saveAndClose("D:\\PDF\\text\\testLink.pdf");
    }

    @Test
    public void testAutoBreak() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("爽爽的贵阳，避暑的天堂");
        }
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(30F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setHighlightColor(Color.CYAN)
                .setHighlight(true)
                .setDeleteLine(true)
                .setUnderline(true)
                .setText(builder.toString())
                .render();
        document.saveAndClose("D:\\PDF\\text\\testAutoBreak.pdf");
    }

    @Test
    public void testBorder() {
        Document document = PdfHandler.getDocumentHandler().build();
        document.setMargin(50F).setFontSize(12F);
        Page page = PdfHandler.getPageHandler().build(document);
        Text text = PdfHandler.getTextHandler().build(page);
        text.setTopBorder(true)
                .setLeading(12F)
                .setBorderColor(Color.CYAN)
                .setText("爽爽的贵阳，避暑的天堂1")
                .render();
        text.setTopBorder(false)
                .setBottomBorder(true)
                .setMarginLeft(200F)
                .setText("爽爽的贵阳，避暑的天堂2")
                .render();
        text.setWrap(true)
                .setBottomBorder(false)
                .setLeftBorder(true)
                .setMarginLeft(0F)
                .setText("爽爽的贵阳，避暑的天堂3")
                .render();
        page = text.setWrap(false)
                .setLeftBorder(false)
                .setRightBorder(true)
                .setMarginLeft(200F)
                .setText("爽爽的贵阳，避暑的天堂4")
                .render();
        text = PdfHandler.getTextHandler().build(page);
        text.setBorder(true)
                .setText("爽爽的贵阳，避暑的天堂")
                .render();
        document.saveAndClose("D:\\PDF\\text\\testBorder.pdf");
    }
}
