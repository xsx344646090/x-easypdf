package org.dromara.pdf.pdfbox.component;

import org.dromara.pdf.pdfbox.base.BaseTest;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Barcode;
import org.dromara.pdf.pdfbox.core.enums.BarcodeType;
import org.dromara.pdf.pdfbox.core.enums.LineStyle;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.core.enums.VerticalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

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
public class BarcodeTest extends BaseTest {

    /**
     * 测试二维码
     */
    @Test
    public void qrcodeTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();
            // 设置页边距
            document.setMargin(100F);

            // 创建 A4 大小的页面
            Page page = new Page(document);

            // 创建二维码对象
            Barcode barcode = new Barcode(document.getCurrentPage());
            // 设置编码类型为 QR_CODE
            barcode.setCodeType(BarcodeType.QR_CODE);
            // 设置宽度
            barcode.setWidth(150);
            // 设置高度
            barcode.setHeight(150);
            // 设置图像宽度
            barcode.setImageWidth(450);
            // 设置图像高度
            barcode.setImageHeight(450);
            // 设置设置上边距
            // barcode.setMarginTop(100F);
            // 设置二维码内容
            barcode.setContent("https://x-easypdf.cn");
            // 设置二维码下方的文字
            barcode.setWords("文字");
            // 设置二维码下方文字的字体
            barcode.setWordsFontName("微软雅黑");
            // 设置二维码下方文字的字号
            barcode.setWordsSize(36);
            // 设置二维码是否显示边框
            barcode.setIsBorder(true);
            // 设置二维码边框样式
            barcode.setBorderLineStyle(LineStyle.SOLID);
            // 设置二维码是否显示下方文字
            barcode.setIsShowWords(true);
            // 设置二维码是否缓存
            barcode.setIsCache(Boolean.FALSE);
            // 设置二维码旋转角度
            // barcode.setAngle(30F);
            // 设置二维码居中
            barcode.setHorizontalAlignment(HorizontalAlignment.CENTER);
            barcode.setVerticalAlignment(VerticalAlignment.CENTER);
            // 渲染二维码
            barcode.render();

            // 添加页面
            document.appendPage(page);
            // 保存文档
            document.save("E:\\PDF\\pdfbox\\barcode\\qrcodeTest.pdf");
            // 关闭文档
            document.close();
        });
    }

    /**
     * 测试条形码
     */
    @Test
    public void barcodeTest() {
        this.test(() -> {
            // 创建文档
            Document document = PdfHandler.getDocumentHandler().create();
            // 设置页边距
            document.setMargin(0F);

            // 创建一个 A4 大小的页面
            Page page = new Page(document);

            // 创建一个条形码对象
            Barcode barcode = new Barcode(document.getCurrentPage());
            // 设置编码类型为 CODE_128
            barcode.setCodeType(BarcodeType.CODE_128);
            // 设置条形码宽度为 250
            barcode.setWidth(250);
            // 设置条形码高度为 50
            barcode.setHeight(50);
            // 设置条形码图像宽度为 500
            barcode.setImageWidth(500);
            // 设置条形码图像高度为 100
            barcode.setImageHeight(100);
            // 设置条形码内容为 "123456789"
            barcode.setContent("123456789");
            // 设置条形码下方的文字
            barcode.setWords("123456789");
            // 设置条形码下方文字的大小
            barcode.setWordsSize(36);
            // 设置条形码下方文字的 Y 轴偏移量为 30
            barcode.setWordsOffsetY(30);
            // 设置条形码周围是否有边框
            barcode.setIsBorder(true);
            // 设置条形码边框样式为点状
            barcode.setBorderLineStyle(LineStyle.DOTTED);
            // 设置条形码下方文字是否显示
            barcode.setIsShowWords(true);
            // 设置条形码是否缓存
            barcode.setIsCache(Boolean.FALSE);
            // 渲染条形码
            barcode.render();

            // 添加页面
            document.appendPage(page);
            // 保存文档
            document.save("E:\\PDF\\pdfbox\\barcode\\barcodeTest.pdf");
            // 关闭文档
            document.close();
        });
    }

}
