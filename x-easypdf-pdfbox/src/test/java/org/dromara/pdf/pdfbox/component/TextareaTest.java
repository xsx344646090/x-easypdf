package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.InnerDest;
import org.dromara.pdf.pdfbox.core.base.OuterDest;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.HighlightMode;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Arrays;

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

    /**
     * 全局配置测试
     */
    @Test
    public void globalTest() {
        this.test(() -> {
            PdfHandler.getFontHandler().addFont(Paths.get("C:\\Users\\xsx\\Downloads\\Noto_Emoji\\NotoEmoji-VariableFont_wght.ttf").toFile());

            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontName("微软雅黑");
            document.setFontColor(Color.RED);
            document.setFontSize(12F);
            document.setSpecialFontNames("Noto Emoji Regular");

            Page page = new Page(document);
            page.setMargin(20F);
            page.setFontName("仿宋");
            page.setFontColor(Color.BLUE);
            page.setFontSize(20F);

            Textarea textarea = new Textarea(document.getCurrentPage());
            textarea.setText("第一个页面，包含emoji字符：↔，测试");
            textarea.setFontName("微软雅黑");
            textarea.setFontColor(Color.BLACK);
            textarea.render();
            log.info("页面最新页码： " + page.getLastNo());

            textarea.setFontName("微软雅黑");
            textarea.setIsBreak(true);
            textarea.setText("这是第二页，使用的上个页面设置");
            textarea.render();
            log.info("页面最新页码： " + page.getLastNo());

            textarea = new Textarea(document.getCurrentPage());
            textarea.setText("这是第二页换行文本，使用的自定义设置");
            textarea.setIsWrap(true);
            textarea.setFontColor(Color.BLACK);
            textarea.setFontSize(6F);
            textarea.render();
            log.info("页面最新页码： " + page.getLastNo());

            Page page1 = new Page(document);

            textarea = new Textarea(document.getCurrentPage());
            textarea.setText("这是第三页，使用的全局设置");
            textarea.render();
            log.info("页面最新页码： " + (page.getLastNo() + page1.getLastNo()));

            document.appendPage(page);
            document.appendPage(page1);
            document.save("E:\\PDF\\pdfbox\\textarea\\globalTest.pdf");
            document.close();
        });
    }

    /**
     * 边距测试
     */
    @Test
    public void marginTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

            Textarea textarea = new Textarea(page);
            textarea.setMarginTop(100F);
            textarea.setText("测试文本");
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\marginTest.pdf");
            document.close();
        });
    }

    /**
     * 换行测试
     */
    @Test
    public void wrapTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(20F);

            Page page = new Page(document);

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

    /**
     * 字体样式测试
     */
    @Test
    public void fontStyleTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

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
            textarea.setFontStyle(FontStyle.STROKE);
            textarea.setText("x-easypdf（STROKE）");
            textarea.render();

            textarea.setIsWrap(true);
            textarea.setFontStyle(FontStyle.HIDDEN);
            textarea.setText("x-easypdf（HIDDEN）");
            textarea.render();

            textarea.setIsWrap(true);
            textarea.setFontStyle(FontStyle.ITALIC);
            textarea.setText("x-easypdf（ITALIC）");
            textarea.render();

            textarea.setIsWrap(true);
            textarea.setFontStyle(FontStyle.ITALIC_BOLD);
            textarea.setFontSlope(0.5F);
            textarea.setText("x-easypdf（ITALIC_BOLD）");
            textarea.render();

            textarea.setIsWrap(true);
            textarea.setFontStyle(FontStyle.ITALIC_LIGHT);
            textarea.setFontSlope(0.7F);
            textarea.setText("x-easypdf（ITALIC_LIGHT）");
            textarea.render();

            textarea.setIsWrap(true);
            textarea.setFontStyle(FontStyle.ITALIC_STROKE);
            textarea.setFontSlope(1F);
            textarea.setText("x-easypdf（ITALIC_STROKE）");
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\fontStyleTest.pdf");
            document.close();
        });
    }

    /**
     * 高亮测试
     */
    @Test
    public void highlightTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

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

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\highlightTest.pdf");
            document.close();
        });
    }

    /**
     * 下划线测试
     */
    @Test
    public void underlineTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

            Textarea textarea = new Textarea(page);
            textarea.setUnderlineWidth(2F);
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

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\underlineTest.pdf");
            document.close();
        });
    }

    /**
     * 删除线测试
     */
    @Test
    public void deleteLineTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

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

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\deleteLineTest.pdf");
            document.close();
        });
    }

    /**
     * 外部链接测试
     */
    @Test
    public void outerDestTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

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
            document.save("E:\\PDF\\pdfbox\\textarea\\outerDestTest.pdf");
            document.close();
        });
    }

    /**
     * 内部链接测试
     */
    @Test
    public void innerDestTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            // document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

            Textarea textarea = new Textarea(page);
            textarea.setUnderlineColor(Color.BLUE);
            textarea.setIsUnderline(true);
            textarea.setInnerDest(
                    InnerDest.create()
                            .setName("x-easypdf官网")
                            .setPage(page)
                            .setTopY(100)
                            .setHighlightMode(HighlightMode.OUTLINE)
            );
            textarea.setText("这是\n超链接文本");
            textarea.render();

            textarea = new Textarea(page);
            textarea.setText("x-easypdf官网：https://x-easypdf.cn");
            textarea.setBeginX(50F);
            textarea.setBeginY(100F);
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\innerDestTest.pdf");
            document.close();
        });
    }

    /**
     * 自动分页测试
     */
    @Test
    public void autoBreakTest() {
        this.test(() -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                builder.append("爽爽的贵阳，避暑的天堂");
            }

            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(30F);

            Page page = new Page(document);

            Textarea textarea = new Textarea(page);
            textarea.setIsHighlight(true);
            textarea.setIsDeleteLine(true);
            textarea.setIsUnderline(true);
            textarea.setText(builder.toString());
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\textarea\\autoBreakTest.pdf");
            document.close();
        });
    }

    /**
     * 边框测试
     */
    @Test
    public void borderTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();
            // 设置页边距
            document.setMargin(50F);
            // 设置字体大小
            document.setFontSize(12F);

            // 创建一个A4大小的页面
            Page page = new Page(document);

            // 创建文本域
            Textarea textarea = new Textarea(page);
            // 设置上边框
            textarea.setIsBorderTop(true);
            // 设置行间距
            textarea.setLeading(12F);
            // 设置边框颜色
            textarea.setBorderColor(Color.CYAN);
            // 设置文本
            textarea.setText("爽爽的贵阳，避暑的天堂1");
            // 渲染文本
            textarea.render();

            // 取消上边框
            textarea.setIsBorderTop(false);
            // 设置下边框
            textarea.setIsBorderBottom(true);
            // 设置左边距
            textarea.setMarginLeft(200F);
            // 设置文本
            textarea.setText("爽爽的贵阳，避暑的天堂2");
            // 渲染文本
            textarea.render();

            // 设置自动换行
            textarea.setIsWrap(true);
            // 取消下边框
            textarea.setIsBorderBottom(false);
            // 设置左边框
            textarea.setIsBorderLeft(true);
            // 设置左边距
            textarea.setMarginLeft(0F);
            // 设置文本
            textarea.setText("爽爽的贵阳，避暑的天堂3");
            // 渲染文本
            textarea.render();

            // 取消自动换行
            textarea.setIsWrap(false);
            // 取消左边框
            textarea.setIsBorderLeft(false);
            // 设置右边框
            textarea.setIsBorderRight(true);
            // 设置左边距
            textarea.setMarginLeft(200F);
            // 设置文本
            textarea.setText("爽爽的贵阳，避暑的天堂4");
            // 渲染文本
            textarea.render();

            // 创建另一个A4大小的页面
            Page page1 = new Page(document);

            // 创建文本域
            textarea = new Textarea(page1);
            // 设置边框
            textarea.setIsBorder(true);
            // 设置文本
            textarea.setText("爽爽的贵阳，避暑的天堂");
            // 渲染文本
            textarea.render();

            // 添加页面
            document.appendPage(page, page1);
            // 保存文档
            document.save("E:\\PDF\\pdfbox\\textarea\\borderTest.pdf");
            // 关闭文档
            document.close();
        });

    }

    /**
     * 自定义分行测试
     */
    @Test
    public void textListTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();
            // 设置页边距
            document.setMargin(50F);
            // 设置字体大小
            document.setFontSize(12F);

            // 创建一个A4大小的页面
            Page page = new Page(document);

            // 创建一个Textarea对象
            Textarea textarea = new Textarea(page);
            // 设置是否显示边框
            textarea.setIsBorder(true);
            // 设置行间距
            textarea.setLeading(12F);
            // 设置文本列表
            textarea.setTextList(Arrays.asList("爽爽的贵阳", "避暑的天堂"));
            // 设置字体透明度
            textarea.setFontAlpha(0.5F);
            // 渲染文本
            textarea.render();

            // 添加页面
            document.appendPage(page);
            // 保存文档
            document.save("E:\\PDF\\pdfbox\\textarea\\textListTest.pdf");
            // 关闭文档
            document.close();
        });
    }

}
