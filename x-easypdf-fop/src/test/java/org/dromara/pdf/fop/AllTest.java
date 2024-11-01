package org.dromara.pdf.fop;


import org.dromara.pdf.fop.core.doc.Document;
import org.dromara.pdf.fop.core.doc.bookmark.Bookmark;
import org.dromara.pdf.fop.core.doc.component.barcode.Barcode;
import org.dromara.pdf.fop.core.doc.component.table.Table;
import org.dromara.pdf.fop.core.doc.component.table.TableRow;
import org.dromara.pdf.fop.core.doc.component.text.Text;
import org.dromara.pdf.fop.core.doc.component.text.TextExtend;
import org.dromara.pdf.fop.core.doc.page.Page;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.junit.Test;

public class AllTest extends BaseTest {

    @Test
    public void helloWorldTest() {
        this.test(() -> {
            // 定义pdf输出路径
            String outputPath = "E:\\PDF\\fop\\document.pdf";
            // 定义配置文件路径
            String configPath = "test/fop.xconf";
            // 创建文档对象
            Document document = TemplateHandler.Document.build();
            // 设置配置文件路径
            document.setConfigPath(configPath);
            // 创建页面对象
            Page page = TemplateHandler.Page.build();
            // 创建组件对象
            Text text = TemplateHandler.Text.build().setText("hello world").setFontFamily("思源");
            // 添加组件
            page.addBodyComponent(text);
            // 添加页面
            document.addPage(page);
            // 转换文档
            document.transform(outputPath);
        });
    }

    @Test
    public void allTest() {
        this.test(() -> {
            TemplateHandler.Banner.disable();
            // 定义输出路径
            String outputPath = "E:\\PDF\\fop\\allTest.pdf";
            // 定义书签
            Bookmark bookmark = TemplateHandler.Bookmark.build()
                                        // 设置标题
                                        .setTitle("目录")
                                        // 设置内部地址（对应组件id）
                                        .setInternalDestination("title");
            // 创建标题
            Text title = TemplateHandler.Text.build()
                                 // 设置id
                                 .setId("title")
                                 // 设置文本
                                 .setText("贵阳市简介")
                                 // 设置字体大小
                                 .setFontSize("20pt")
                                 // 设置水平居中
                                 .setHorizontalStyle("center");
            // 创建扩展文本
            TextExtend createText = TemplateHandler.TextExtend.build()
                                            // 设置字体大小
                                            .setFontSize("12pt")
                                            // 设置段前空白
                                            .setSpaceBefore("12pt")
                                            // 设置段前缩进
                                            .setStartIndent("60pt")
                                            // 设置文本间隔
                                            .setTextSpacing("80pt")
                                            // 设置文本
                                            .addText(
                                                    TemplateHandler.Text.build().setText("创建时间：2022-11-11 00:00:00"),
                                                    TemplateHandler.Text.build().setText("创建人：x-easypdf")
                                            );

            // 创建二维码
            Barcode barcode = TemplateHandler.Barcode.build()
                                      // 设置条形码类型
                                      .setType("qr_code")
                                      // 设置条形码内容
                                      .setContent("https://baike.baidu.com/item/贵阳/438289")
                                      // 设置条形码说明文字
                                      .setWords("扫一扫")
                                      // 设置条形码图像宽度
                                      .setWidth("60pt")
                                      // 设置条形码图像高度
                                      .setHeight("60pt")
                                      // 设置水平居右
                                      .setHorizontalStyle("right")
                                      // 设置上移62pt
                                      .setMarginTop("-62pt");
            // 创建内容
            Text text = TemplateHandler.Text.build()
                                // 设置id
                                .setId("text")
                                // 设置文本
                                .setText(
                                        "贵阳，简称“筑”，别称林城、筑城，贵州省辖地级市、省会、Ⅰ型大城市，国务院批复确定的中国西南地区重要的中心城市之一、" +
                                                "重要的区域创新中心和全国重要的生态休闲度假旅游城市。" +
                                                "贵阳地处黔中山原丘陵中部，东南与黔南布依族苗族自治州的瓮安、龙里、惠水、长顺4县接壤，" +
                                                "西靠安顺市的平坝区和毕节市的织金县，北邻毕节市的黔西市、金沙县和遵义市的播州区，" +
                                                "截至2020年，全市下辖6个区、3个县，代管1个县级市。截至2021年末，贵阳市常住人口610.23万人。"
                                )
                                // 设置段前空白
                                .setSpaceBefore("12pt")
                                // 设置文本缩进
                                .setTextIndent("24pt");
            // 创建内容
            Text remark = TemplateHandler.Text.build()
                                  // 设置文本
                                  .setText("-- 摘自百度百科")
                                  // 设置字体大小
                                  .setFontSize("12pt")
                                  // 设置水平居右
                                  .setHorizontalStyle("right");
            // 创建子书签
            Bookmark child = TemplateHandler.Bookmark.build()
                                     // 设置标题
                                     .setTitle("贵阳市行政区划")
                                     // 设置内部地址（对应组件id）
                                     .setInternalDestination("tableText");
            // 添加子书签
            bookmark.addChild(child);
            // 创建内容
            Text tableText = TemplateHandler.Text.build()
                                     // 设置id
                                     .setId("tableText")
                                     // 设置文本
                                     .setText("贵阳市行政区划")
                                     // 设置字体大小
                                     .setFontSize("20pt")
                                     // 设置当前位置分页
                                     .setBreakBefore("page")
                                     // 设置段前空白
                                     .setSpaceBefore("20pt")
                                     // 设置水平居右
                                     .setHorizontalStyle("center");
            // 创建表格
            Table table = TemplateHandler.Table.build().setHeader(
                    // 设置表头
                    TemplateHandler.Table.Header.build().addRow(
                            this.createRow("#7C7D7D", "center", "名称", "区划代码", "人口（万人）", "面积（平方千米）")
                    )
            ).setBody(
                    // 设置表格主体
                    TemplateHandler.Table.Body.build().addRow(
                            this.createRow(null, "left", "贵阳市", "520100", "599", "8035"),
                            this.createRow(null, "left", "南明区", "520102", "105", "271"),
                            this.createRow(null, "left", "云岩区", "520103", "106", "94"),
                            this.createRow(null, "left", "花溪区", "520111", "97", "964"),
                            this.createRow(null, "left", "乌当区", "520112", "34", "686"),
                            this.createRow(null, "left", "白云区", "520113", "46", "270"),
                            this.createRow(null, "left", "观山湖区", "520115", "64", "309"),
                            this.createRow(null, "right", "清镇市", "520181", "63", "1302"),
                            this.createRow(null, "right", "开阳县", "520121", "34", "2026"),
                            this.createRow(null, "right", "息烽县", "520122", "22", "1037"),
                            this.createRow(null, "right", "修文县", "520123", "29", "1076")
                    )
            ).setMinRowHeight("30pt").setVerticalStyle("center");
            // 创建文档
            Document document = TemplateHandler.Document.build();
            // 总页数文本
            Text totalPageText = TemplateHandler.Text.build();
            // 创建页面
            Page page = TemplateHandler.Page.build()
                                // 设置页面id
                                .setId("pageId")
                                // 设置字体
                                .setFontFamily("思源宋体")
                                // 设置字体大小
                                .setFontSize("15pt")
                                // 设置边距（上下左右）
                                .setMargin("20pt")
                                // 设置主体内容
                                .addBodyComponent(title, createText, barcode, text, remark, tableText, table)
                                // 设置页脚高度
                                .setFooterHeight("20pt")
                                // 设置页脚内容
                                .addFooterComponent(
                                        // 创建块容器并添加内容
                                        TemplateHandler.BlockContainer.build().addComponent(
                                                // 创建文本
                                                TemplateHandler.Text.build().setText("第 "),
                                                // 创建当前页码
                                                TemplateHandler.CurrentPageNumber.build(),
                                                TemplateHandler.Text.build().setText(" 页，共 "),
                                                totalPageText,
                                                // 创建总页码并设置页面id
                                                // TemplateHandler.TotalPageNumber.build().setPageId("pageId"),
                                                TemplateHandler.Text.build().setText(" 页")
                                        ).setHorizontalStyle("center")
                                )
                                // 设置主体水印
                                .setBodyWatermark(
                                        // 创建文字水印
                                        TemplateHandler.Watermark.build()
                                                // 设置水印图片目录
                                                .setTempDir("E:\\PDF\\fop")
                                                // 设置水印id
                                                .setId("watermark")
                                                // 设置水印内容
                                                .setText("贵阳", "x-easypdf")
                                                // 设置水印图像宽度
                                                .setWidth("600pt")
                                                // 设置水印图像高度
                                                .setHeight("300pt")
                                                // 设置水印显示宽度
                                                .setShowWidth("200pt")
                                                // 设置文字大小
                                                .setFontSize("100pt")
                                                // 设置文字颜色
                                                .setFontColor("gray")
                                                // 设置文字透明度
                                                .setFontAlpha("100")
                                                // 设置逆时针旋转
                                                .setRadians("-45")
                                                // 开启文件覆盖
                                                .enableOverwrite()
                                );
            // 添加页面、书签
            document.addPage(page).addBookmark(bookmark);
            // 获取总页数
            Integer totalPage = document.getTotalPage();
            // 设置总页数
            totalPageText.setText(totalPage.toString());
            // 打印总页数
            log.info("Total pages: " + totalPage);
            // 转换pdf
            org.dromara.pdf.pdfbox.core.base.Document document1 = document.transform();
            document1.saveAndClose(outputPath);
        });
    }

    private TableRow createRow(String backgroundColor, String style, String... texts) {
        TableRow row = TemplateHandler.Table.Row.build();
        for (String text : texts) {
            row.addCell(
                    TemplateHandler.Table.Cell.build().addComponent(
                            TemplateHandler.Text.build().setText(text)
                    ).setBorderStyle("solid").setBackgroundColor(backgroundColor).setHorizontalStyle(style)
            );
        }
        return row;
    }
}
