package wiki.xsx.core.pdf.template;

import org.junit.Test;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

/**
 * @author xsx
 * @date 2022/8/6
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
public class XEasyPdfTemplateTest {

    @Test
    public void testFont() {
        // 定义字体路径
        String fontPath = "H:\\java_workspace\\my\\x-easypdf\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\ttf\\HarmonyOS_Sans_SC_Medium.ttf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\myFont.xml";
        // 转换字体
        XEasyPdfTemplateHandler.Font.build()
                .setFontPath(fontPath)
                .setOutputPath(outputPath)
                .transform();
    }

    @Test
    public void testThymeleaf() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\x-easypdf\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Thymeleaf.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Thymeleaf.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }

    @Test
    public void testXml() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义xsl-fo模板路径
        String templatePath = "H:\\java_workspace\\my\\x-easypdf\\src\\main\\resources\\wiki\\xsx\\core\\pdf\\template\\template.fo";
        // 定义pdf输出路径
        String outputPath = "E:\\pdf\\test\\fo\\Xml.pdf";
        // 转换pdf
        XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.XML.build().setTemplatePath(templatePath))
                .transform(outputPath);
    }
}
