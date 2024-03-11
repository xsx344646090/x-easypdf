package org.dromara.pdf.fop;

import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.core.doc.component.block.BlockContainer;
import org.dromara.pdf.fop.core.doc.component.page.CurrentPageNumber;
import org.dromara.pdf.fop.core.doc.component.page.TotalPageNumber;
import org.dromara.pdf.fop.core.doc.component.text.Text;
import org.dromara.pdf.fop.core.doc.component.text.TextExtend;
import org.dromara.pdf.fop.core.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

/**
 * @author xsx
 * @date 2022/8/6
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
public class TextTest extends BaseTest {

    @Test
    public void textTest1() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\text\\textTest1.pdf";
            // 转换pdf
            Document document = TemplateHandler.Document.build()
                    .setConfigPath(configPath)
                    .addPage(
                            TemplateHandler.Page.build()
                                    .setFontSize("30pt")
                                    .setFontColor("BLUE")
                                    .addBodyComponent(
                                            TemplateHandler.Text.build()
                                                    .setText("加粗需要字体支持")
                                                    .setFontFamily("仿宋")
                                                    .setFontWeight("bold")
                                                    .setHorizontalStyle("right")
                                                    .setMarginRight("10pt"),
                                            TemplateHandler.Text.build()
                                                    .setText("不加粗            文本间隔")
                                                    .setFontFamily("仿宋")
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
        });
    }

    @Test
    public void textTest2() {
        // 定义输出路径
        String outputPath = "E:\\PDF\\fop\\text\\textTest2.pdf";
        // 定义页面id
        String pageId = "page";
        // 创建文档
        Document document = TemplateHandler.Document.build();
        // 创建页面
        Page page = TemplateHandler.Page.build().setFontFamily("微软雅黑");
        // 创建当前页码
        CurrentPageNumber currentPageNumber = TemplateHandler.CurrentPageNumber.build();
        // 创建总页码
        TotalPageNumber totalPageNumber = TemplateHandler.TotalPageNumber.build().setPageId(pageId);
        // 创建普通文本
        Text text1 = TemplateHandler.Text.build().setText("第一页内容");
        // 创建普通文本并分页
        Text text2 = TemplateHandler.Text.build().setText("第二页内容").setBreakBefore("page");
        // 创建容器
        BlockContainer container = TemplateHandler.BlockContainer.build();
        // 创建当前页码文本
        Text currentText = TemplateHandler.Text.build().setText("当前第： ");
        // 创建总页码文本
        Text totalText = TemplateHandler.Text.build().setText("，共： ");
        // 添加容器内组件
        container.addComponent(currentText, currentPageNumber, totalText, totalPageNumber);
        // 设置id
        page.setId(pageId);
        // 设置页眉高度并添加页眉组件
        page.setHeaderHeight("20pt").addHeaderComponent(container);
        // 设置页面主体上边距并添加页面主体组件
        page.setBodyMarginTop("20pt").addBodyComponent(text1, text2);
        // 添加页面
        document.addPage(page);
        // 转换pdf
        document.transform(outputPath);
    }

    @Test
    public void textExtendTest1() {
        this.test(() -> {
            // 定义fop配置文件路径
            String configPath = "E:\\PDF\\fop\\fop.xconf";
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\text\\textExtendTest1.pdf";
            // 转换pdf
            TemplateHandler.Document.build()
                    .setConfigPath(configPath)
                    .addPage(
                            TemplateHandler.Page.build()
                                    .addBodyComponent(
                                            TemplateHandler.TextExtend.build()
                                                    .setFontFamily("微软雅黑")
                                                    .setFontSize("30pt")
                                                    .addText(
                                                            TemplateHandler.Text.build().setText("hello"),
                                                            TemplateHandler.Text.build().setText("上标").setFontSize("12pt").setVerticalStyle("top"),
                                                            TemplateHandler.Text.build().setText(", world")
                                                    )
                                    )
                    ).transform(outputPath);
        });
    }

    @Test
    public void textExtendTest2() {
        this.test(() -> {
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\text\\textExtendTest2.pdf";
            // 创建文档
            Document document = TemplateHandler.Document.build();
            // 创建页面
            Page page = TemplateHandler.Page.build().setFontFamily("微软雅黑");
            // 创建title
            Text title = TemplateHandler.Text.build().setText("贵阳").setFontSize("30pt").setHorizontalStyle("center");
            // 创建文本1
            Text text1 = TemplateHandler.Text.build().setText("贵阳，简称“筑”，别称林城、筑城，贵州省辖地级市、省会、Ⅰ型大城市，中国");
            // 创建文本2，特殊配置
            Text text2 = TemplateHandler.Text.build().setText("西南地区").setFontColor("blue").setUnderLineColor("blue").setLinkExternalDestination("https://baike.baidu.com/item/%E8%A5%BF%E5%8D%97%E5%9C%B0%E5%8C%BA/4465918?fromModule=lemma_inlink").enableLink().enableUnderLine();
            // 创建文本3
            Text text3 = TemplateHandler.Text.build().setText("重要的中心城市之一、重要的区域创新中心和全国重要的生态休闲度假旅游城市。");
            // 创建扩展文本
            TextExtend textExtend = TemplateHandler.TextExtend.build().addText(text1, text2, text3).setMarginTop("12pt").setTextIndent("24pt");
            // 添加文本
            page.addBodyComponent(title, textExtend);
            // 添加页面
            document.addPage(page);
            // 转换pdf
            document.transform(outputPath);
        });
    }
}
