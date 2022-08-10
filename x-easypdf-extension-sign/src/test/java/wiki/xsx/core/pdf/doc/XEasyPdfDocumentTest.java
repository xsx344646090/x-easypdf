package wiki.xsx.core.pdf.doc;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;
import wiki.xsx.core.pdf.util.XEasyPdfImageUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    private static final String OUTPUT_PATH = "E:\\pdf\\test\\doc\\";

    @Test
    public void test() throws IOException {
        long begin = System.currentTimeMillis();
        // 定义保存路径
        final String outputPath = OUTPUT_PATH + "sign.pdf";
        final String imagePath = OUTPUT_PATH + "2022010115431859.gif";
        final String certPath = OUTPUT_PATH + "file.p12";
        try (OutputStream outputStream = Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath)))) {
            XEasyPdfHandler.Document.build(
                    XEasyPdfHandler.Page.build(
                            XEasyPdfHandler.Text.build("爽爽的贵阳，避暑的天堂").setFontSize(16).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                    )
            ).signer().setSignerInfo(
                    "xsx", "贵阳市", "测试", "qq: 344646090"
            ).setCertificate(
                    XEasyPdfDocumentSignAlgorithm.MD5withRSA, XEasyPdfDocumentSignKeyStoreType.PKCS12, new File(certPath), "123456"
            ).setSignImage(
                    XEasyPdfImageUtil.read(new File(imagePath)), 240F, 30F, 50F
            ).sign(0, outputStream);
        }
        long end = System.currentTimeMillis();
        System.out.println("完成，耗时： " + (end-begin));
    }
}
