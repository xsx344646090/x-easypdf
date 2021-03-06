package wiki.xsx.core.pdf.doc;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImageType;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;
import wiki.xsx.core.pdf.util.XEasyPdfImageUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 * @author xsx
 * @date 2020/6/8
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XEasyPdfDocumentTest {

    private static final String FONT_PATH = "C:\\Windows\\Fonts\\msyh.ttf";
    private static final String OUTPUT_PATH = "E:\\pdf\\test\\doc\\";
    private static final String IMAGE_PATH = OUTPUT_PATH + "test.png";

    @Before
    public void setup() {
        File dir = new File(OUTPUT_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Test
    public void test01AddPage() throws IOException {
        String filePath = OUTPUT_PATH + "testAddPage.pdf";
        XEasyPdfHandler.Document.build().addPage(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("Hello World").setHorizontalStyle(XEasyPdfPositionStyle.CENTER),
                        XEasyPdfHandler.Text.build("??????????????????"),
                        XEasyPdfHandler.Text.build("???????????????")
                ),
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("??????????????????").setHorizontalStyle(XEasyPdfPositionStyle.CENTER),
                        XEasyPdfHandler.Text.build("???????????????").setHorizontalStyle(XEasyPdfPositionStyle.RIGHT)
                )
        ).setFontPath(FONT_PATH).save(filePath).close();
        System.out.println("finish");
    }

    @Test
    public void test02Save() throws IOException {
        String sourcePath = OUTPUT_PATH + "testAddPage.pdf";
        String filePath = OUTPUT_PATH + "doc1.pdf";
        XEasyPdfHandler.Document.load(sourcePath).addPage(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("Hello World").setHorizontalStyle(XEasyPdfPositionStyle.CENTER),
                        XEasyPdfHandler.Text.build("??????????????????")
                ).setFontPath(FONT_PATH)
        ).save(filePath).close();
        System.out.println("finish");
    }

    @Test
    public void test03SetInfo() throws IOException {
        String filePath = OUTPUT_PATH + "info.pdf";
        XEasyPdfHandler.Document.build().addPage(
                XEasyPdfHandler.Page.build()
        ).information()
            .setTitle("test info")
            .setAuthor("xsx")
            .setSubject("info")
            .setCreator("my-creator")
            .setKeywords("pdf,xsx")
            .setCreateTime(Calendar.getInstance())
            .setUpdateTime(Calendar.getInstance())
            .finish()
            .save(filePath)
            .close();
        System.out.println("finish");
    }

    @Test
    public void test04SetPermission() throws IOException {
        String filePath = OUTPUT_PATH + "permission.pdf";
        XEasyPdfHandler.Document.build().addPage(
                XEasyPdfHandler.Page.build()
        ).permission()
                .setCanPrintDegraded(false)
                .setCanPrint(false)
                .setCanAssembleDocument(false)
                .setCanExtractContent(false)
                .setCanExtractForAccessibility(false)
                .setCanFillInForm(false)
                .setCanModify(false)
                .setCanModifyAnnotations(false)
                .finish()
                .save(filePath)
                .close();
        System.out.println("finish");
    }

    @Test
    public void test05SetBackgroundColor() throws IOException {
        String filePath = OUTPUT_PATH + "backgroundColor.pdf";
        XEasyPdfHandler.Document.build().addPage(
                XEasyPdfHandler.Page.build(),
                XEasyPdfHandler.Page.build()
        ).setGlobalBackgroundColor(Color.YELLOW)
                .save(filePath)
                .close();
        System.out.println("finish");
    }

    @Test
    public void test06SetBackgroundImage() throws IOException {
        String filePath = OUTPUT_PATH + "backgroundImage.pdf";
        XEasyPdfHandler.Document.build().addPage(
                XEasyPdfHandler.Page.build().addComponent(
                        XEasyPdfHandler.Text.build(
                                "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                                        + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!" + "hello world!!!"
                        ).setFontSize(20)
                ).setBackgroundColor(Color.WHITE),
                XEasyPdfHandler.Page.build()
        ).setFontPath(FONT_PATH)
                .setGlobalBackgroundColor(new Color(0,191,255))
                .setGlobalBackgroundImage(XEasyPdfHandler.Image.build(new File(IMAGE_PATH)).setHorizontalStyle(XEasyPdfPositionStyle.CENTER))
                .save(filePath)
                .close();
        System.out.println("finish");
    }

    @Test
    public void test07InsertPage() throws IOException {
        String sourcePath = OUTPUT_PATH + "backgroundColor.pdf";
        String filePath = OUTPUT_PATH + "insertPage.pdf";
        XEasyPdfHandler.Document.load(sourcePath).insertPage(
                -100,
                XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("????????????"))
        ).insertPage(
                100,
                XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("????????????"))
        ).setFontPath(FONT_PATH)
                .setGlobalBackgroundColor(new Color(0,191,255))
                .save(filePath)
                .close();
        System.out.println("finish");
    }

    @Test
    public void test08Merge() throws IOException {
        String sourcePath = OUTPUT_PATH + "backgroundColor.pdf";
        String mergePath1 = OUTPUT_PATH + "insertPage.pdf";
        String mergePath2 = OUTPUT_PATH + "doc1.pdf";
        String filePath = OUTPUT_PATH + "merge.pdf";
        XEasyPdfHandler.Document.load(sourcePath).merge(
                XEasyPdfHandler.Document.load(mergePath1),
                XEasyPdfHandler.Document.load(mergePath2)
        ).save(filePath).close();
        System.out.println("finish");
    }

    @Test
    public void test09Image1() throws IOException {
        String sourcePath = OUTPUT_PATH + "doc1.pdf";
        XEasyPdfHandler.Document.load(sourcePath).imager().image(OUTPUT_PATH, XEasyPdfImageType.PNG).finish().close();
        System.out.println("finish");
    }

    @Test
    public void test10Image2() throws IOException {
        String sourcePath = OUTPUT_PATH + "insertPage.pdf";
        String prefix = "x-easypdf";
        XEasyPdfHandler.Document.load(sourcePath).imager().image(OUTPUT_PATH, XEasyPdfImageType.JPEG, prefix).finish().close();
        System.out.println("finish");
    }

    @Test
    public void test11Image3() throws IOException {
        String sourcePath = OUTPUT_PATH + "merge.pdf";
        String filePath1 = OUTPUT_PATH + "merge0.jpg";
        String filePath2 = OUTPUT_PATH + "merge6.jpg";
        try(
                OutputStream outputStream1 = Files.newOutputStream(Paths.get(filePath1));
                OutputStream outputStream2 = Files.newOutputStream(Paths.get(filePath2))
        ) {
            XEasyPdfHandler.Document.load(sourcePath)
                    .imager()
                    .image(outputStream1, XEasyPdfImageType.JPEG, 0)
                    .image(outputStream2, XEasyPdfImageType.PNG, 6)
                    .finish()
                    .close();
        }
        System.out.println("finish");
    }

    @Test
    public void test12Split1() throws IOException {
        String sourcePath = OUTPUT_PATH + "doc1.pdf";
        XEasyPdfHandler.Document.load(sourcePath).splitter().split(OUTPUT_PATH).finish().close();
        System.out.println("finish");
    }

    @Test
    public void test13Split2() throws IOException {
        String sourcePath = OUTPUT_PATH + "testAddPage.pdf";
        XEasyPdfHandler.Document.load(sourcePath)
                .splitter()
                .addDocument(1)
                .addDocument(1, 0)
                .split(OUTPUT_PATH, "mypdf")
                .finish()
                .close();
        System.out.println("finish");
    }

    @Test
    public void test14Split3() throws IOException {
        String sourcePath = OUTPUT_PATH + "doc1.pdf";
        String filePath = OUTPUT_PATH + "testSplit3.pdf";
        try(OutputStream outputStream = Files.newOutputStream(Paths.get(filePath))) {
            XEasyPdfHandler.Document.load(sourcePath).splitter().split(outputStream, 1).finish().close();
        }
        System.out.println("finish");
    }

    @Test
    public void test15StripText() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH + "doc1.pdf";
        List<String> list = new ArrayList<>(1024);
        XEasyPdfHandler.Document.load(sourcePath).extractor().extract(list, ".*???.*").finish().close();
        for (String s : list) {
            System.out.println("s = " + s);
        }
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test16StripText1() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH + "doc1.pdf";
        List<String> list = new ArrayList<>(1024);
        XEasyPdfHandler.Document.load(sourcePath).extractor().extract(list).finish().close();
        for (String s : list) {
            System.out.println("s = " + s);
        }
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test17StripText2() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH + "doc1.pdf";
        List<Map<String, String>> dataList = new ArrayList<>();
        XEasyPdfHandler.Document.load(sourcePath).extractor().addRegion("test1", new Rectangle(600,2000)).extractByRegions(dataList, 0).finish().close();
        System.out.println("dataList = " + dataList);
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test18StripTable() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH +"testAddPage.pdf";
        List<List<String>> list = new ArrayList<>(1024);
        XEasyPdfHandler.Document.load(sourcePath).extractor().extractForSimpleTable(list, 0).finish().close();
        for (List<String> s : list) {
            System.out.println("s = " + s);
        }
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test19StripTable2() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH +"testAddPage.pdf";
        List<List<String>> list = new ArrayList<>(1024);
        XEasyPdfHandler.Document.load(sourcePath).extractor().extractByRegionsForSimpleTable(list, new Rectangle(0,0, 800, 170),1).finish().close();
        for (List<String> s : list) {
            System.out.println("s = " + s);
        }
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test20StripTable3() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH +"testAddPage.pdf";
        List<Map<String, String>> dataList = new ArrayList<>();
        XEasyPdfHandler.Document.load(sourcePath).extractor().addRegion("test1", new Rectangle(0,320,800,540)).extractByRegions(dataList, 0).finish().close();
        System.out.println("dataList = " + dataList);
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test21FillForm() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = "E:\\pdf\\hi.pdf";
        final String outputPath = OUTPUT_PATH + "test_fill2.pdf";
        Map<String, String> form = new HashMap<>(2);
        form.put("test1", "???????????????");
        form.put("test2", "???????????????");
        XEasyPdfHandler.Document.load(sourcePath).formFiller().fill(form).finish(outputPath);
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test22() throws IOException {
        final String outputPath = OUTPUT_PATH+"text.pdf";

        // ??????????????????
        XEasyPdfHandler.Document.build().setGlobalBackgroundImage(
                // ????????????
                XEasyPdfHandler.Image.build(new File(IMAGE_PATH)).setHeight(800F).enableCenterStyle()
                // ??????????????????
        ).setGlobalHeader(
                // ????????????
                XEasyPdfHandler.Header.build(
                        // ????????????????????????????????????
                        XEasyPdfHandler.Text.build("????????????").setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                )
                // ??????????????????
        ).setGlobalFooter(
                // ????????????
                XEasyPdfHandler.Footer.build(
                        // ??????????????????
                        XEasyPdfHandler.Text.build("????????????")
                )
                // ????????????
        ).addPage(
                // ????????????
                XEasyPdfHandler.Page.build(
                        // ????????????
                        XEasyPdfHandler.Text.build(
                                "Hello World(????????????DEMO)"
                        ).setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setFontSize(20F).setMargin(10F)
                        // ????????????
                        ,XEasyPdfHandler.Text.build(
                                "????????????????????????????????????PDFBOX????????????????????????PDF?????????????????????" +
                                        "?????????????????????????????????????????????????????????????????????" +
                                        "?????????TEXT(??????)???LINE(?????????)?????????????????????????????????????????????????????????????????????"
                        ).setHorizontalStyle(XEasyPdfPositionStyle.LEFT).setFontSize(14F).setMargin(10F).setAutoIndent(9)
                        // ????????????
                        ,XEasyPdfHandler.Text.build(
                                "-- by xsx"
                        ).setHorizontalStyle(XEasyPdfPositionStyle.RIGHT).setFontSize(12F).setMarginTop(10F).setMarginRight(10F)
                        // ????????????
                        ,XEasyPdfHandler.Text.build(
                                "2020.03.12"
                        ).setHorizontalStyle(XEasyPdfPositionStyle.RIGHT).setFontSize(12F).setMarginTop(10F).setMarginRight(10F)
                        // ?????????????????????
                        ,XEasyPdfHandler.SplitLine.SolidLine.build().setMarginTop(10F)
                        // ?????????????????????
                        ,XEasyPdfHandler.SplitLine.DottedLine.build().setLineLength(10F).setMarginTop(10F).setLineWidth(10F)
                        // ?????????????????????
                        ,XEasyPdfHandler.SplitLine.SolidLine.build().setMarginTop(10F)
                        // ????????????
                        ,XEasyPdfHandler.Text.build( "??????").setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                )
                // ??????????????????????????????
        ).save(outputPath).close();
    }

    @Test
    public void test23() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH+"hi.pdf";
        final String outputPath = OUTPUT_PATH+"test_fill4.pdf";
        Map<String, String> form = new HashMap<>(5);
        form.put("test1", "???????????????");
        form.put("test2", "???????????????");
        form.put("text1", "xxx");
        form.put("text2", "sss");
        form.put("hi", "??????xsx");
        XEasyPdfHandler.Document.load(sourcePath).formFiller().fill(form).finish(outputPath);;
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }


    @Test
    public void test24() throws IOException {
        long begin = System.currentTimeMillis();
        final String sourcePath = OUTPUT_PATH+"hi.pdf";
        final String outputPath = OUTPUT_PATH+"ZZZ.pdf";
        Map<String, String> form = new HashMap<>(2);
        form.put("hi", "??????");
        form.put("test1", "7");
        form.put("test2", "xxx");
        XEasyPdfHandler.Document.load(sourcePath).setFontPath(FONT_PATH).addPage(
                XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("??????"))
        ).formFiller().fill(form).finish(outputPath);
        long end = System.currentTimeMillis();
        System.out.println("finish("+(end-begin)+"ms)");
    }

    @Test
    public void test26() throws IOException {
        long begin = System.currentTimeMillis();
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "merge.pdf";
        XEasyPdfDocument document = XEasyPdfHandler.Document.build();
        document.merge(
                XEasyPdfHandler.Document.build().addPage(
                        XEasyPdfHandler.Page.build(
                                XEasyPdfHandler.Text.build("???????????????")
                        )
                ),
                XEasyPdfHandler.Document.build().addPage(
                        XEasyPdfHandler.Page.build(
                                XEasyPdfHandler.Text.build("???????????????")
                        )
                )
        ).setGlobalHeader(
                XEasyPdfHandler.Header.build(
                        XEasyPdfHandler.Text.build("???????????????"+XEasyPdfHandler.Page.getCurrentPagePlaceholder()+"" +
                                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                                "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" +
                                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
                        )
                )
        ).setGlobalFooter(
                XEasyPdfHandler.Footer.build(
                        XEasyPdfHandler.Text.build("???????????????"+XEasyPdfHandler.Page.getCurrentPagePlaceholder())
                )
        ).bookmark()
                .setBookMark(0, "???1?????????")
                .setBookMark(
                        XEasyPdfDocumentBookmark.BookmarkNode.build()
                                .setTitle("???????????????")
                                .setPage(1)
                                .setTitleColor(new Color(255, 0, 153))
                                .addChild(
                                        XEasyPdfDocumentBookmark.BookmarkNode.build()
                                                .setTitle("????????????????????????")
                                                .setPage(1)
                                                .setTop(300)
                                                .setTitleColor(new Color(255,50,100))
                                )
                )
                .finish()
                .save(outputPath)
                .close();
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }

    @Test
    public void test27() throws IOException {
        long begin = System.currentTimeMillis();
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "mutilPage.pdf";
        XEasyPdfDocument document = XEasyPdfHandler.Document.build();
        document.addPage(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("??????????????????????????????"),
                        XEasyPdfHandler.Text.build("??????????????????????????????"),
                        XEasyPdfHandler.Text.build("??????????????????????????????")
                ),
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("?????????????????????")
                ),
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("??????????????????????????????"),
                        XEasyPdfHandler.Text.build("??????????????????????????????")
                )
        ).setGlobalHeader(
                XEasyPdfHandler.Header.build(
                        XEasyPdfHandler.Text.build(Arrays.asList("???????????????"+XEasyPdfHandler.Page.getCurrentPagePlaceholder(), "???????????????", "??????XXXXXX")).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                )
        ).setGlobalFooter(
                XEasyPdfHandler.Footer.build(
                        XEasyPdfHandler.Text.build("???????????????"+XEasyPdfHandler.Page.getCurrentPagePlaceholder())
                )
        ).save(outputPath).close();
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }

    @Test
    public void test28() throws IOException {
        long begin = System.currentTimeMillis();
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "sign.pdf";
        final String imagePath = "C:\\Users\\Administrator\\Desktop\\2022010115431859.gif";
        final String certPath = "E:\\pdf\\file.p12";
        try (OutputStream outputStream = Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath)))) {
            XEasyPdfHandler.Document.build(
                    XEasyPdfHandler.Page.build(
                            XEasyPdfHandler.Text.build("?????????????????????????????????").setFontSize(16).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                    )
            ).signer().setSignerInfo(
                    "xsx", "?????????", "??????", "qq: 344646090"
            ).setCertificate(
                    XEasyPdfDocumentSigner.SignAlgorithm.MD5withRSA, XEasyPdfDocumentSigner.KeyStoreType.PKCS12, new File(certPath), "123456"
            ).setSignImage(
                    XEasyPdfImageUtil.read(new File(imagePath)), 240F, 30F, 50F
            ).sign(0, outputStream);
        }
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }

    @Test
    public void test29() throws IOException {
        long begin = System.currentTimeMillis();
        // ???????????????
        final String sourcePath = OUTPUT_PATH + "merge.pdf";
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "replaceText.pdf";
        Map<String, String> replaceMap = new HashMap<>(1);
        replaceMap.put("\\d", "??????");
        try (OutputStream outputStream = Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath)))) {
            XEasyPdfHandler.Document.load(sourcePath).replacer().replaceText(replaceMap).finish(outputStream);
        }
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }

    @Test
    public void test30() throws IOException {
        long begin = System.currentTimeMillis();
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "flush.pdf";
        XEasyPdfHandler.Document.build(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("??????")
                )
        ).flush().addPage(
                XEasyPdfHandler.Page.build(
                        XEasyPdfHandler.Text.build("??????")
                )
        ).save(outputPath);
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }

    @Test
    public void allTest() throws IOException {
        long begin = System.currentTimeMillis();
        // ??????????????????
        final String outputPath = OUTPUT_PATH + "allTest.pdf";
        // ?????????????????????????????????
        Color headerAndFooterColor = new Color(10, 195, 255);
        // ?????????????????????
        Color lineColor = new Color(210, 0, 210);
        // ??????????????????
        try (InputStream backgroundImageInputStream = new URL("https://i0.hdslb.com/bfs/article/1e60a08c2dfdcfcd5bab0cae4538a1a7fe8fc0f3.png").openStream()) {
            // ??????????????????
            XEasyPdfHandler.Document.build().setGlobalBackgroundImage(
                            // ???????????????????????????
                            XEasyPdfHandler.Image.build(backgroundImageInputStream, XEasyPdfImageType.PNG).setVerticalStyle(XEasyPdfPositionStyle.CENTER)
                            // ??????????????????
                    ).setGlobalHeader(
                            // ????????????
                            XEasyPdfHandler.Header.build(
                                    // ????????????????????????????????????
                                    XEasyPdfHandler.Image.build(new File(IMAGE_PATH)).setHeight(50F).enableCenterStyle(),
                                    // ????????????????????????????????????
                                    XEasyPdfHandler.Text.build("??????????????????")
                                            // ????????????????????????
                                            .enableCenterStyle()
                                            // ??????????????????
                                            .setFontSize(20F)
                                            // ??????????????????
                                            .setFontColor(headerAndFooterColor)
                                            // ???????????????
                                            .setDefaultFontStyle(XEasyPdfDefaultFontStyle.BOLD)
                            )
                            // ??????????????????
                    ).setGlobalFooter(
                            // ????????????
                            XEasyPdfHandler.Footer.build(
                                    // ????????????????????????????????????
                                    XEasyPdfHandler.Image.build(new File(IMAGE_PATH)).setHeight(50F).setVerticalStyle(XEasyPdfPositionStyle.BOTTOM),
                                    // ??????????????????????????????????????????????????????
                                    XEasyPdfHandler.Text.build(Arrays.asList("???????????????????????????", "???????????????????????????"))
                                            // ??????????????????
                                            .setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                                            // ??????????????????
                                            .setVerticalStyle(XEasyPdfPositionStyle.BOTTOM)
                                            // ??????????????????
                                            .setFontSize(20F)
                                            // ??????????????????
                                            .setFontColor(headerAndFooterColor)
                                            // ???????????????
                                            .setDefaultFontStyle(XEasyPdfDefaultFontStyle.BOLD)
                            )
                            // ????????????
                    ).addPage(
                            // ????????????
                            XEasyPdfHandler.Page.build(
                                    // ????????????
                                    XEasyPdfHandler.Text.build(
                                            "x-easypdf??????????????????"
                                    // ??????????????????
                                    ).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                                            // ??????????????????
                                            .setFontSize(16F)
                                            // ???????????????
                                            .setDefaultFontStyle(XEasyPdfDefaultFontStyle.LIGHT)
                                            // ???????????????
                                            .enableDeleteLine()
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "x-easypdf???????????????PDFBOX??????????????????"
                                    ).setFontSize(16F).setFontColor(new Color(51, 0, 153))
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "?????????PDF?????????????????????"
                                    ).enableTextAppend().setFontSize(16F).setFontColor(new Color(102, 0, 153))
                                    // ?????????????????????????????????
                                    .enableUnderline().setUnderlineColor(Color.RED)
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "??????????????????????????????"
                                    ).enableTextAppend().setFontSize(16F).setFontColor(new Color(153, 0, 153))
                                    // ??????????????????????????????
                                    .enableHighlight().setHighlightColor(Color.ORANGE)
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "?????????????????????????????????"
                                    ).enableTextAppend().setFontSize(16F).setFontColor(new Color(204, 0, 153))
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "??????????????????????????????????????????"
                                    ).enableTextAppend().setFontSize(16F).setFontColor(new Color(255, 0, 153))
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "-- by xsx"
                                    ).setHorizontalStyle(XEasyPdfPositionStyle.RIGHT).setMarginTop(10F).setMarginRight(10F)
                                    // ????????????
                                    ,XEasyPdfHandler.Text.build(
                                            "2021.10.10"
                                    ).setHorizontalStyle(XEasyPdfPositionStyle.RIGHT).setMarginTop(10F).setMarginRight(10F)
                                    // ?????????????????????
                                    ,XEasyPdfHandler.SplitLine.SolidLine.build().setMarginTop(10F).setColor(lineColor).setContentMode(XEasyPdfComponent.ContentMode.PREPEND)
                                    // ?????????????????????
                                    ,XEasyPdfHandler.SplitLine.DottedLine.build().setLineLength(10F).setMarginTop(10F).setLineWidth(10F).setColor(lineColor).setContentMode(XEasyPdfComponent.ContentMode.PREPEND)
                                    // ?????????????????????
                                    ,XEasyPdfHandler.SplitLine.SolidLine.build().setMarginTop(10F).setColor(lineColor).setContentMode(XEasyPdfComponent.ContentMode.PREPEND)
                                    // ????????????
                                    ,XEasyPdfHandler.Table.build(
                                            // ?????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 50F).addContent(
                                                            // ????????????
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 50F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ),
                                                    // ???????????????????????????????????????15????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 50F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ).setFontSize(15F).setBorderColor(Color.GREEN),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 50F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 50F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    )
                                                // ???????????????????????????20
                                            ).setFontSize(20F),
                                            // ?????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ??????????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 300F).addContent(
                                                            XEasyPdfHandler.Text.build(Arrays.asList("?????????", "?????????", "????????????"))
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(300F).addContent(
                                                            XEasyPdfHandler.Text.build("?????????????????????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 300F).addContent(
                                                            XEasyPdfHandler.Text.build("?????????????????????????????????")
                                                    )
                                                // ???????????????100???????????????????????????????????????
                                            ).setHeight(100F),
                                            // ?????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ????????????????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).enableVerticalMerge(),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????")
                                                    )
                                                // ???????????????100????????????????????????????????????????????????????????????
                                            ).setHeight(100F).setBorderColor(Color.RED).setFontColor(Color.BLUE),
                                            // ????????????????????????????????????????????????????????????????????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ????????????????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F, 100F).enableVerticalMerge(),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(300F, 100F).addContent(
                                                            XEasyPdfHandler.Text.build("?????????????????????????????????")
                                                    )
                                            ),
                                            // ????????????????????????????????????????????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("???????????????????????????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????????????????????????????????????????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????????????????????????????????????????????????????????????????????????????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????????????????????????????????????????????????????????????????")
                                                    ),
                                                    // ???????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(100F).addContent(
                                                            XEasyPdfHandler.Text.build("???????????????????????????????????????")
                                                    )
                                            ),
                                            // ?????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ????????????????????????????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(500F, 100F).addContent(
                                                            XEasyPdfHandler.Text.build("????????????1")
                                                    ).setBorderColor(Color.ORANGE).setFontColor(Color.PINK)
                                            )
                                        // ???????????????
                                    ).setTileRow(
                                            // ?????????
                                            XEasyPdfHandler.Table.Row.build(
                                                    // ??????????????????????????????????????????????????????????????????30????????????????????????
                                                    XEasyPdfHandler.Table.Row.Cell.build(500F, 100F).addContent(
                                                            XEasyPdfHandler.Text.build("??????")
                                                    ).setBorderColor(Color.BLACK).setFontSize(30F).setFontColor(Color.MAGENTA)
                                            )
                                        // ????????????????????????????????????
                                    ).enableCenterStyle()
                                            // ??????????????????50
                                            .setMarginLeft(50F)
                                            // ??????????????????10
                                            .setMarginTop(10F)
                                            // ???????????????????????????
                                            .setBorderColor(Color.GRAY)
                                            // ?????????????????????????????????????????????????????????
                                            .enableAutoTitle()
                            )
                            // ???????????????
                    ).save(outputPath).close();
        }
        long end = System.currentTimeMillis();
        System.out.println("?????????????????? " + (end-begin));
    }
}
