package wiki.xsx.core.pdf.template;

import lombok.SneakyThrows;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfDocumentBookmark;
import wiki.xsx.core.pdf.doc.XEasyPdfDocumentReplacer;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EasyPdfTest {


    @SneakyThrows
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        testFop();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    private static void testPdfbox() {
        XEasyPdfText test1 = XEasyPdfHandler.Text.build("test1");
        test1.onAfterDraw(
                (d, p, t) -> d.bookmark().setBookMark(
                        XEasyPdfDocumentBookmark.BookmarkNode.build().setTitle("test11111").setPage(d.getTotalPage() - 1).setTop(p.getPageY().intValue())
                ).finish()
        );
        XEasyPdfText test2 = XEasyPdfHandler.Text.build("test2");
        test2.onAfterDraw(
                (d, p, t) -> d.bookmark().setBookMark(
                        XEasyPdfDocumentBookmark.BookmarkNode.build().setTitle("test22222").setPage(d.getTotalPage() - 1).setTop(p.getPageY().intValue())
                ).finish()
        );
        XEasyPdfHandler.Document.build(
                XEasyPdfHandler.Page.build(test1),
                XEasyPdfHandler.Page.build(test2)
        ).save("E:\\pdf\\bookmark.pdf").close();
    }

    private static void testFop() {
        // 定义fop配置文件路径
        String configPath = "C:\\Users\\86158\\Desktop\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "E:\\pdf\\test.fo";
        // 定义xml数据路径
        String xmlPath = "E:\\pdf\\data.xml";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\xml2.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build().setDataSource(
                XEasyPdfTemplateHandler.DataSource.XML.build()
                        .setTemplatePath(templatePath)
                        .setXmlPath(xmlPath)
        ).transform(outputPath);
    }

    private static void document() throws IOException {
        // 定义源文档路径
        String filePath = "E:\\pdf\\" + "testFormFill" + ".pdf";
        OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(filePath))));
        try {
            // 定义文档路径
            // 读取文档
            XEasyPdfDocument document = XEasyPdfHandler.Document.load("E:\\pdf\\hello-world.pdf");
            // 获取文档替换器
            XEasyPdfDocumentReplacer replacer = document.replacer();
            // 定义替换字典
            Map<String, String> dataMap = new HashMap<>(2);
            // 设置字段
            dataMap.put("投保人", "贵州");
            // 设置字段
            dataMap.put("date", "贵阳");
            // 替换文本
            replacer.replaceText(dataMap);
            // 完成操作
            replacer.finish(outputStream);
        } finally {
            outputStream.close();
        }
    }

}
