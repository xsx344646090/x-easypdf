package org.dromara.pdf.pdfbox;

import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.core.Page;
import org.dromara.pdf.pdfbox.core.PageRectangle;
import org.dromara.pdf.pdfbox.core.component.Container;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author xsx
 * @date 2023/8/24
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
public class ContainerTest {

    @Test
    public void testTextContainer() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageRectangle.A4);
        document.appendPage(page);
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

        document.saveAndClose("E:\\PDF\\container\\testTextContainer.pdf");
    }

    @Test
    public void testTextBreakContainer() {
        Document document = PdfHandler.getDocumentHandler().create();
        document.setMargin(50F);
        Page page = document.createPage(PageRectangle.A4);
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
        document.saveAndClose("E:\\PDF\\container\\testTextBreakContainer.pdf");
    }
}
