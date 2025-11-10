package org.dromara.pdf.pdfbox.base;

import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocument;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.*;
import org.dromara.pdf.pdfbox.core.component.Line;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.core.enums.PWLength;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xsx
 * @date 2023/7/16
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
public class DocumentTest extends BaseTest {

    /**
     * 测试原生pdfbox
     */
    @Test
    public void pdfboxTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(new File("E:\\PDF\\pdfbox\\document\\test.pdf"));
            document.setFontName("微软雅黑");

            PDDocument pdDocument = document.getTarget();

            PDPage page = new PDPage(PDRectangle.A4);
            pdDocument.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);

            PDFont pdFont = document.getFont();

            float x = 0F;
            float y = page.getMediaBox().getHeight() - 12;
            contentStream.setFont(pdFont, 12f);
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText("你好，世界！hello world");
            contentStream.endText();
            contentStream.beginText();
            x = 20 * 12F;
            contentStream.newLineAtOffset(x, y);
            contentStream.showText("第二段文本");
            contentStream.endText();
            contentStream.close();

            document.save("E:\\PDF\\pdfbox\\processor\\test.pdf");
            document.close();
        });
    }

    /**
     * 测试读取文档
     */
    @Test
    public void loadTest() {
        this.test(() -> {
            // 本地读取
            this.test(() -> {
                Document document = PdfHandler.getDocumentHandler().load("E:\\PDF\\pdfbox\\document\\test.pdf");
                Assert.assertEquals(1, document.getTotalPageNumber());
                document.close();
            }, "本地读取");

            // 本地读取
            this.test(() -> {
                Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\document\\test.pdf").toFile());
                Assert.assertEquals(1, document.getTotalPageNumber());
                document.close();
            }, "本地读取");

            // 流读取
            this.test(() -> {
                InputStream inputStream = Files.newInputStream(Paths.get("E:\\PDF\\pdfbox\\document\\test.pdf"));
                Document document = PdfHandler.getDocumentHandler().load(inputStream);
                Assert.assertEquals(1, document.getTotalPageNumber());
                document.close();
                inputStream.close();
            }, "流读取");

            // 资源读取
            this.test(() -> {
                Document document = PdfHandler.getDocumentHandler().loadByResource("org/dromara/pdf/pdfbox/test.pdf");
                Assert.assertEquals(1, document.getTotalPageNumber());
                document.close();
            }, "资源读取");

            // 远程读取
            this.test(() -> {
                Document document = PdfHandler.getDocumentHandler().loadByRemote("http://localhost:22222/temp/test.pdf");
                Assert.assertEquals(1, document.getTotalPageNumber());
                document.close();
            }, "远程读取");
        });
    }

    /**
     * 测试修改版本
     */
    @Test
    public void versionTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\document\\test.pdf").toFile());
            document.setVersion(1.7F);
            document.save("E:\\PDF\\pdfbox\\document\\versionTest.pdf");
            document.close();
        });
    }

    /**
     * 测试加密
     */
    @Test
    public void encryptionTest() {
        this.test(() -> {
            Document document = this.create(2);
            document.encryption(true, PWLength.LENGTH_128, "123456", "123456");
            document.save("E:\\PDF\\pdfbox\\document\\encryptionTest.pdf");
            document.close();
        });
    }

    /**
     * 测试解密
     */
    @Test
    public void decryptTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(
                    Paths.get("E:\\PDF\\pdfbox\\document\\encryptionTest.pdf").toFile(),
                    "123456"
            );
            document.decrypt();
            document.save("E:\\PDF\\pdfbox\\document\\decryptTest.pdf");
            document.close();
        });
    }

    /**
     * 测试插入页面
     */
    @Test
    public void insertPageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\document\\test.pdf").toFile());

            Page page = new Page(document, PageSize.A4);

            document.insertPage(1, page);
            document.save("E:\\PDF\\pdfbox\\document\\insertPageTest1.pdf");
            document.close();
        });
    }

    /**
     * 测试追加页面
     */
    @Test
    public void appendPageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\document\\test.pdf").toFile());

            Page page = new Page(document, PageSize.A4);

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\document\\appendPageTest.pdf");
            document.close();
        });
    }

    /**
     * 测试替换页面
     */
    @Test
    public void setPageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().load(Paths.get("E:\\PDF\\pdfbox\\document\\insertPageTest1.pdf").toFile());

            Page page = new Page(document);

            document.setPage(0, page);
            document.save("E:\\PDF\\pdfbox\\document\\setPageTest.pdf");
            document.close();
        });
    }

    /**
     * 测试创建页面
     */
    @Test
    public void createPageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();

            Page page = new Page(document);
            log.info(page.getWidth());

            page = new Page(document);
            log.info(page.getWidth());

            document.close();
        });
    }

    /**
     * 测试获取当前页面
     */
    @Test
    public void getCurrentPageTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();

            Page page = document.getCurrentPage();
            log.info(page);

            page = new Page(document);
            log.info(page);

            document.appendPage(page);

            page = document.getCurrentPage();
            log.info(page);

            page = document.getPage(0);
            log.info(page);

            document.close();
        });
    }

    /**
     * 测试目录
     */
    @Test
    public void getCatalogsTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);
            document.setFontSize(12F);

            Page page1 = new Page(document);

            Textarea textarea1 = new Textarea(page1);
            textarea1.setLeading(12F);
            textarea1.setTextList(Arrays.asList("第一页，爽爽的贵阳", "避暑的天堂"));
            textarea1.setCatalog(new CatalogInfo("第一页，爽爽的贵阳，避暑的天堂"));
            textarea1.render();

            Page page2 = new Page(document);

            Textarea textarea2 = new Textarea(page2);
            textarea2.setLeading(12F);
            textarea2.setTextList(Arrays.asList("第二页，爽爽的贵阳", "避暑的天堂"));
            textarea2.setCatalog(new CatalogInfo("第二页，爽爽的贵阳，避暑的天堂"));
            textarea2.render();

            document.appendPage(page1, page2);

            Page catalogPage = new Page(document);
            Textarea textarea = new Textarea(catalogPage);
            textarea.setText("目录");
            textarea.setFontSize(20F);
            textarea.render();

            List<CatalogInfo> catalogs = document.getCatalogs();
            for (CatalogInfo catalog : catalogs) {
                InnerDest dest = new InnerDest();
                dest.setPage(catalog.getPage());
                dest.setTopY(catalog.getBeginY().intValue());

                Textarea content = new Textarea(catalogPage);
                content.setMarginTop(10F);
                content.setText(catalog.getTitle());
                content.setInnerDest(dest);
                content.setIsWrap(true);
                content.render();

                Line line = new Line(catalogPage);
                line.setLineStyle(LineStyle.DOTTED);
                line.setLineWidth(2F);
                line.setLineLength(260F);
                line.setDottedSpacing(1F);
                line.setMarginLeft(20F);
                line.setMarginRight(20F);
                line.setRelativeBeginY(-5F);
                line.render();

                Textarea contentPageIndex = new Textarea(catalogPage);
                contentPageIndex.setText(String.valueOf((catalog.getPage().getIndex() + 1)));
                contentPageIndex.render();

                log.info(catalog);
            }

            document.insertPage(0, catalogPage);
            document.save("E:\\PDF\\pdfbox\\catalogTest.pdf");
            document.close();
        });
    }

    /**
     * 测试总页数
     */
    @Test
    public void totalPageNumberTest() {
        this.test(() -> {
            Document document = this.create(null);

            int totalPageNumber = document.getTotalPageNumber();
            log.info("总页数： " + totalPageNumber);

            document.close();
            document = this.create(totalPageNumber);
            document.save("E:\\PDF\\pdfbox\\document\\totalPageNumberTest.pdf");
            document.close();
        });
    }

    /**
     * 创建文档
     */
    private Document create(Integer totalPage) {
        // 创建文档
        Document document = PdfHandler.getDocumentHandler().create();

        // 创建页面
        Page page = new Page(document);

        // 创建页脚
        PageFooter pageFooter = new PageFooter(document.getCurrentPage());
        // 设置页脚高度
        pageFooter.setHeight(100F);
        // 创建文本
        Textarea footerText = new Textarea(pageFooter.getPage());
        // 设置文本内容
        footerText.setText("共" + totalPage + "页");
        // 设置组件
        pageFooter.setComponents(footerText);
        if (Objects.isNull(totalPage)) {
            // 虚拟渲染
            pageFooter.virtualRender();
        } else {
            // 真实渲染
            pageFooter.render();
        }
        // 添加页面
        document.appendPage(page);
        // 返回文档
        return document;
    }
}
