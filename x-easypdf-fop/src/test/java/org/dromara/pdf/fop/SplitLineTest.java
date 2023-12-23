package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/9/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class SplitLineTest {

    @Test
    public void test() {
        // 定义fop配置文件路径
        String configPath = "E:\\pdf\\test\\fo\\fop.xconf";
        // 定义输出路径
        String outputPath = "E:\\pdf\\test\\fo\\template-split-line.pdf";
        // 转换pdf
        Document document = TemplateHandler.Document.build().setConfigPath(configPath).addPage(
                TemplateHandler.Page.build().addBodyComponent(
                        TemplateHandler.SplitLine.build().setStyle("dotted").setLength("100%"),
                        TemplateHandler.SplitLine.build().setStyle("dashed").setLength("100%"),
                        TemplateHandler.SplitLine.build().setStyle("solid").setLength("100%"),
                        TemplateHandler.SplitLine.build().setStyle("double").setLength("100%"),
                        TemplateHandler.SplitLine.build().setStyle("groove").setLength("100%"),
                        TemplateHandler.SplitLine.build().setStyle("ridge").setLength("100%")
                )
        );
        // 转换pdf
        document.transform(outputPath);
    }
}
