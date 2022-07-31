package wiki.xsx.core.pdf;

import lombok.SneakyThrows;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.Configuration;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.apache.xmlgraphics.util.MimeConstants;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author xsx
 * @date 2022/6/26
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class HelloWorld {

    @Test
    public void test() {
        System.setProperty(XEasyPdfHandler.FontMappingPolicy.key(), XEasyPdfHandler.FontMappingPolicy.ALL.name());
        XEasyPdfHandler.Document.build(XEasyPdfHandler.Page.build(XEasyPdfHandler.Text.build("Hello World"))).save("E:\\pdf\\hello-world.pdf").close();
    }

    @SneakyThrows
    @Test
    public void test2() {
        XEasyPdfHandler.Template.Font.builder()
                .setFontPath("C:\\Windows\\Fonts\\msyh.ttf")
                .setOutputPath("E:\\pdf\\test\\fo\\msyh.xml")
                .build();
    }

    @SneakyThrows
//    @Test
    public String test5() {
        // 字体转换
        // TTFReader.main();
        byte[] bytes = Files.readAllBytes(Paths.get("E:\\pdf\\test\\fo\\region-1\\test.fo"));
        String template = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("template = " + template);
        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.XML);
        templateEngine.setTemplateResolver(resolver);
        Context context = new Context();
        context.setLocale(Locale.CHINA);
        List<String> list = new ArrayList<>(3);
        list.add("第一行");
        list.add("第二行");
        list.add("第三行");
        context.setVariable("orders", list);
        String content = templateEngine.process(template, context);
        System.out.println("------------------------------------------------------");
        System.out.println("content = " + content);
        return content;
    }

    @SneakyThrows
    @Test
    public void test8() {
        String content = test5();
        Charset utf8 = StandardCharsets.UTF_8;
        try {

            // Setup directories
            File baseDir = new File("E:\\pdf\\test\\fo");
            File outDir = new File(baseDir, "out");
            outDir.mkdirs();

            // Setup input and output files
//            File xmlfile = new File(baseDir, "fp.xml");
            File xsltfile = new File(baseDir, "fp.xslt");
            File pdffile = new File(outDir, "fp.pdf");

            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            //指定fop配置文件
            Configuration cfg = cfgBuilder.buildFromFile(
                    new File("E:\\pdf\\test\\fo\\fop.xconf")
            );
            FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(new File(".").toURI()).setConfiguration(cfg);

            // configure fopFactory as desired
            final FopFactory fopFactory = fopFactoryBuilder.build();

            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired

            // Setup output
//            ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
            OutputStream out = new FileOutputStream(pdffile);
            try {
                // Construct fop with desired output format
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();

                Transformer transformer = factory.newTransformer(
                        new StreamSource(new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))))
                );

                // Set the value of a <param> in the stylesheet
                transformer.setParameter("versionParam", "2.0");

                // Setup input for XSLT transformation
//                Source src = new StreamSource(new InputStreamReader(new FileInputStream(xmlfile), utf8));
                Source src = new StreamSource();

                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
//                XEasyPdfDocument document = XEasyPdfHandler.Document.load(new ByteArrayInputStream(out.toByteArray()));
//                document.save(pdffile.getAbsolutePath()).close();
            } finally {
                out.close();
            }

            System.out.println("生成PDF------Success!");
        } catch (Exception e) {
            System.out.println("生成PDF------Fail!");
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
}
