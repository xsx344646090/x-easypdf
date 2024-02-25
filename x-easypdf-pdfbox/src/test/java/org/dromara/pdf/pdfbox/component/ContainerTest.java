package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Barcode;
import org.dromara.pdf.pdfbox.core.component.Circle;
import org.dromara.pdf.pdfbox.core.component.Container;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.core.enums.BarcodeType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author xsx
 * @date 2023/8/24
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
public class ContainerTest extends BaseTest {

    @Test
    public void simpleContainerTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Container container = new Container(document.getCurrentPage());
            container.setWidth(200F);
            container.setHeight(200F);
            container.setIsBorder(true);

            Textarea textarea = new Textarea(container.getPage());
            textarea.setText("hello world");

            Circle circle = new Circle(container.getPage());
            circle.setRadius(20F);
            circle.setRelativeBeginY(12F);

            Barcode barcode = new Barcode(container.getPage());
            barcode.setCodeType(BarcodeType.QR_CODE);
            barcode.setContent("https://x-easypdf.cn");
            barcode.setWidth(80);
            barcode.setHeight(80);
            barcode.setImageWidth(100);
            barcode.setImageHeight(100);
            barcode.setMarginTop(20F);
            barcode.setIsBorder(true);

            Textarea textarea1 = new Textarea(container.getPage());
            textarea1.setText("hello world");

            container.setComponents(Arrays.asList(textarea, circle, textarea1, barcode));
            container.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\container\\simpleContainerTest.pdf");
            document.close();
        });
    }

    @Test
    public void testTextContainer() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Container container = new Container(document.getCurrentPage());
            container.setWidth(100F);
            container.setHeight(100F);
            container.setIsBorder(true);

            Textarea textarea1 = new Textarea(container.getPage());
            textarea1.setText("hello");
            Textarea textarea2 = new Textarea(container.getPage());
            textarea2.setText("world1world2");

            container.setComponents(Arrays.asList(textarea1, textarea2));
            container.render();
            container.render();
            container.render();
            container.render();
            container.render();
            container.render();
            container.setIsWrap(true);
            container.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\container\\testTextContainer.pdf");
            document.close();
        });
    }

    @Test
    public void testTextBreakContainer() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setMargin(50F);

            Page page = document.createPage(PageSize.A4);

            Container container = new Container(document.getCurrentPage());
            container.setWidth(100F);
            container.setHeight(100F);
            container.setIsBorder(true);

            Textarea textarea1 = new Textarea(container.getPage());
            textarea1.setText("hello");
            Textarea textarea2 = new Textarea(container.getPage());
            Float beginX = 0F;
            for (int i = 0; i <= 7; i++) {
                textarea2.setText("world1world" + i);
                container.setComponents(Arrays.asList(textarea1, textarea2));
                if (i == 7) {
                    container.setIsPagingBorder(true);
                }
                container.render();
                beginX = container.getContext().getContainerInfo().getBeginX();
            }
            Boolean isAlreadyPaging = container.getContext().getIsAlreadyPaging();
            if (isAlreadyPaging) {
                Float width = container.getWidth();
                container = new Container(container.getPage().getFirstParentPage());
                container.setBeginX(beginX + width);
                container.setWidth(100F);
                container.setHeight(100F);
                container.setIsBorder(true);
                textarea1 = new Textarea(container.getPage());
                textarea1.setText("hello1");
                textarea2 = new Textarea(container.getPage());
                textarea2.setText("world1111111111111");
                container.setComponents(Arrays.asList(textarea1, textarea2));
                container.render();
                container.close();
            }

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\container\\testTextBreakContainer.pdf");
            document.close();
        });
    }
}
