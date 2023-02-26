package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.doc.component.line.XEasyPdfTemplateSplitLine;
import wiki.xsx.core.pdf.template.doc.component.text.XEasyPdfTemplateText;
import wiki.xsx.core.pdf.template.doc.component.text.XEasyPdfTemplateTextExtend;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePage;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfTemplateTextTest {

    @Test
    public void testText() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-text.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build()
                .setConfigPath(configPath)
                .addPage(
                        XEasyPdfTemplateHandler.Page.build()
                                .setFontSize("30pt")
                                .setFontColor("BLUE")
                                .addBodyComponent(
                                        XEasyPdfTemplateHandler.Text.build()
                                                .setText("加粗需要字体支持")
                                                .setFontFamily("仿宋_GB2312")
                                                .setFontWeight("bold")
                                                .setHorizontalStyle("right")
                                                .setMarginRight("10pt"),
                                        XEasyPdfTemplateHandler.Text.build()
                                                .setText("不加粗            文本间隔")
                                                .setFontFamily("仿宋_GB2312")
                                                .setFontColor("BLUE")
                                                .setWhiteSpaceCollapse("false")
                                                .enableDeleteLine()
                                                .setDeleteLineColor("RED")
                                                .enableUnderLine()
                                                .setUnderLineColor("RED")
                                                .setUnderLineWidth("3pt")
                                )
                );
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void testTextExtend() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-textExtend.pdf";
        // 转换pdf
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build().setConfigPath(configPath).addPage(XEasyPdfTemplateHandler.Page.build().addBodyComponent(XEasyPdfTemplateHandler.TextExtend.build().setFontFamily("微软雅黑").setFontSize("30pt").addText(XEasyPdfTemplateHandler.Text.build().setText("hello"), XEasyPdfTemplateHandler.Text.build().setText("上标").setFontSize("12pt").setVerticalStyle("top"), XEasyPdfTemplateHandler.Text.build().setText(", world"))));
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void test() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build();
        // 创建页面
        XEasyPdfTemplatePage page = XEasyPdfTemplateHandler.Page.build().setFontFamily("微软雅黑");
        // 创建title
        XEasyPdfTemplateText title = XEasyPdfTemplateHandler.Text.build().setText("贵阳").setFontSize("30pt").setHorizontalStyle("center");
        // 创建文本1
        XEasyPdfTemplateText text1 = XEasyPdfTemplateHandler.Text.build().setText("贵阳，简称“筑”，别称林城、筑城，贵州省辖地级市、省会、Ⅰ型大城市，中国");
        // 创建文本2，特殊配置
        XEasyPdfTemplateText text2 = XEasyPdfTemplateHandler.Text.build().setText("西南地区").setFontColor("blue").setUnderLineColor("blue").setLinkExternalDestination("https://baike.baidu.com/item/%E8%A5%BF%E5%8D%97%E5%9C%B0%E5%8C%BA/4465918?fromModule=lemma_inlink").enableLink().enableUnderLine();
        // 创建文本3
        XEasyPdfTemplateText text3 = XEasyPdfTemplateHandler.Text.build().setText("重要的中心城市之一、重要的区域创新中心和全国重要的生态休闲度假旅游城市。");
        // 创建扩展文本
        XEasyPdfTemplateTextExtend textExtend = XEasyPdfTemplateHandler.TextExtend.build().addText(text1, text2, text3).setMarginTop("12pt").setTextIndent("24pt");
        // 添加文本
        page.addBodyComponent(title, textExtend);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void test2() {
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\test.pdf";
        // 创建文档
        XEasyPdfTemplateDocument document = XEasyPdfTemplateHandler.Document.build();
        // 创建页面
        XEasyPdfTemplatePage page = XEasyPdfTemplateHandler.Page.build();
        // 创建点线
        XEasyPdfTemplateSplitLine dotted = XEasyPdfTemplateHandler.SplitLine.build().setStyle("dotted").setLength("100%");
        // 创建虚线
        XEasyPdfTemplateSplitLine dashed = XEasyPdfTemplateHandler.SplitLine.build().setStyle("dashed").setLength("100%");
        // 创建实线
        XEasyPdfTemplateSplitLine solid = XEasyPdfTemplateHandler.SplitLine.build().setStyle("solid").setLength("100%");
        // 创建双实线
        XEasyPdfTemplateSplitLine doubled = XEasyPdfTemplateHandler.SplitLine.build().setStyle("double").setLength("100%");
        // 创建槽线
        XEasyPdfTemplateSplitLine groove = XEasyPdfTemplateHandler.SplitLine.build().setStyle("groove").setLength("100%");
        // 创建脊线
        XEasyPdfTemplateSplitLine ridge = XEasyPdfTemplateHandler.SplitLine.build().setStyle("ridge").setLength("100%");
        // 添加分割线
        page.addBodyComponent(dotted, dashed, solid, doubled, groove, ridge);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void test3() {
        // 定义xsl-fo模板路径
        String templatePath = "/wiki/xsx/core/pdf/template/xml/test.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 定义数据map
        Map<String, Object> data = new HashMap<>();
        // 设置值
        data.put("data", "hello world");
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                // .setConfigPath(configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }
}
