package org.dromara.pdf.pdfbox.base;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.OuterDest;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.component.Image;
import org.dromara.pdf.pdfbox.core.component.*;
import org.dromara.pdf.pdfbox.core.enums.BarcodeType;
import org.dromara.pdf.pdfbox.core.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

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
public class HelloWorldTest extends BaseTest {

    /**
     * 测试hello world
     */
    @Test
    public void test() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");

            Page page = document.createPage();

            Textarea textarea = new Textarea(page);
            textarea.setText("Hello World!");
            textarea.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\hello-world.pdf");
            document.close();
        });
    }

    /**
     * 测试完整功能
     */
    @Test
    public void allTest() {
        this.test(() -> {
            Document document = PdfHandler.getDocumentHandler().create();
            document.setFontName("微软雅黑");
            document.setMargin(20F);

            Page page = document.createPage();

            PageHeader pageHeader = new PageHeader(document.getCurrentPage());

            Textarea headerText = new Textarea(pageHeader.getPage());
            headerText.setText("x-easypdf是一个简单易用的框架");

            Barcode headerBarcode = new Barcode(pageHeader.getPage());
            headerBarcode.setCodeType(BarcodeType.QR_CODE);
            headerBarcode.setContent("https://baike.baidu.com/item/%E8%B4%B5%E9%98%B3%E5%B8%82/6085276?fr=ge_ala");
            headerBarcode.setWidth(60);
            headerBarcode.setHeight(60);
            headerBarcode.setImageWidth(180);
            headerBarcode.setImageHeight(180);
            headerBarcode.setCodeMargin(1);
            headerBarcode.setMarginLeft(320F);
            headerBarcode.setRelativeBeginY(-12F);
            headerBarcode.setWords("扫一扫");
            headerBarcode.setWordsSize(20);
            headerBarcode.setWordsOffsetY(10);
            headerBarcode.setIsShowWords(true);

            pageHeader.setWidth(page.getWithoutMarginWidth());
            pageHeader.setHeight(90F);
            pageHeader.setComponents(Arrays.asList(headerText, headerBarcode));
            pageHeader.setIsBorder(false);
            pageHeader.render();

            PageFooter pageFooter = new PageFooter(document.getCurrentPage());

            Textarea footerText = new Textarea(pageFooter.getPage());
            footerText.setText("当前第" + footerText.getPlaceholder() + "页");
            footerText.setHorizontalAlignment(HorizontalAlignment.CENTER);

            pageFooter.setWidth(page.getWithoutMarginWidth());
            pageFooter.setHeight(20F);
            pageFooter.setComponents(Collections.singletonList(footerText));
            pageFooter.setIsBorder(true);
            pageFooter.render();

            Textarea title = new Textarea(document.getCurrentPage());
            title.setText("贵阳市简介");
            title.setHorizontalAlignment(HorizontalAlignment.CENTER);
            title.setFontSize(20F);
            title.render();

            Textarea text = new Textarea(document.getCurrentPage());
            text.setText("\t\t贵阳市，简称“筑”，别称林城、筑城，贵州省辖地级市、省会、Ⅰ型大城市，中国西南地区重要的中心城市之一、重要的区域创新中心和中国重要的生态休闲度假旅游城市。介于东经106°07′—107°17′，北纬26°11′—26°55′之间，总面积8043平方千米，地处黔中山原丘陵中部，东南与黔南布依族苗族自治州的瓮安、龙里、惠水、长顺4县接壤，西靠安顺市的平坝区和毕节市的织金县，北邻毕节市的黔西市、金沙县和遵义市的播州区。截至2022年4月，贵阳市下辖6个区、3个县，代管1个县级市。2022年末，贵阳市常住人口622.04万人");
            text.setIsWrap(true);
            text.render();

            text = new Textarea(document.getCurrentPage());
            text.setText("\t\t贵阳原为边疆民族地区，春秋战国至汉初，贵阳地区属古夜郎。汉代设牂牁郡，贵阳为牂牁郡治所，明代设贵阳府。民国三年（1914年），改设贵阳县，四年（1915），民国三十年（1941年）撤销贵阳县，以贵阳城区及近郊设立贵阳市。境内有山地、河流、峡谷、湖泊、岩溶、洞穴、瀑布、原始森林、人文、古城楼阁等32种旅游景点。");
            text.setIsWrap(true);
            text.setMarginTop(12F);
            text.render();

            Image image = new Image(document.getCurrentPage());
            image.setImage(new File("E:\\PDF\\pdfbox\\image\\jiaxiulou.jpg"));
            image.setScale(0.3F);
            image.setMarginLeft(100F);
            image.setIsWrap(true);
            image.render();

            text = new Textarea(document.getCurrentPage());
            text.setText("\t\t贵阳市是贵州省的政治、经济、文化、科教、交通中心，西南地区重要的交通和通信枢纽、工业基地及商贸旅游服务中心。是国家大数据产业发展集聚区，国家大数据综合试验区核心区。贵阳是首个国家森林城市、国家循环经济试点城市、中国综合性铁路枢纽、中国避暑之都，曾登“中国十大避暑旅游城市”榜首。");
            text.setIsWrap(true);
            text.setMarginTop(24F);
            text.render();

            text = new Textarea(document.getCurrentPage());
            text.setText("-- 摘自百度百科");
            text.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            text.setIsWrap(true);
            text.setMarginTop(12F);
            text.setOuterDest(OuterDest.create().setUrl("https://baike.baidu.com/item/%E8%B4%B5%E9%98%B3%E5%B8%82/6085276?fr=ge_ala"));
            text.setFontColor(Color.BLUE);
            text.render();

            document.appendPage(page);
            document.save("E:\\PDF\\pdfbox\\allTest.pdf");
            document.close();
        });
    }
}
