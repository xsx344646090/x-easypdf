package wiki.xsx.core.pdf.handler;

import lombok.SneakyThrows;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.barcode.XEasyPdfBarCode;
import wiki.xsx.core.pdf.component.circle.XEasyPdfCircle;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.component.image.XEasyPdfImageType;
import wiki.xsx.core.pdf.component.image.XEasyPdfSVGImage;
import wiki.xsx.core.pdf.component.layout.XEasyPdfHorizontalLayout;
import wiki.xsx.core.pdf.component.layout.XEasyPdfLayoutComponent;
import wiki.xsx.core.pdf.component.layout.XEasyPdfVerticalLayout;
import wiki.xsx.core.pdf.component.line.XEasyPdfBaseLine;
import wiki.xsx.core.pdf.component.line.XEasyPdfDottedSplitLine;
import wiki.xsx.core.pdf.component.line.XEasyPdfSolidSplitLine;
import wiki.xsx.core.pdf.component.rect.XEasyPdfRect;
import wiki.xsx.core.pdf.component.table.XEasyPdfCell;
import wiki.xsx.core.pdf.component.table.XEasyPdfRow;
import wiki.xsx.core.pdf.component.table.XEasyPdfTable;
import wiki.xsx.core.pdf.component.text.XEasyPdfText;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPageRectangle;
import wiki.xsx.core.pdf.footer.XEasyPdfDefaultFooter;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.header.XEasyPdfDefaultHeader;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfDefaultWatermark;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * pdf助手
 *
 * @author xsx
 * @date 2020/4/1
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
public class XEasyPdfHandler {

    /**
     * pdf文档
     */
    public static class Document {
        /**
         * 构建pdf
         */
        public static XEasyPdfDocument build() {
            return new XEasyPdfDocument();
        }

        /**
         * 构建pdf
         *
         * @param pages 页面
         */
        public static XEasyPdfDocument build(XEasyPdfPage... pages) {
            return new XEasyPdfDocument().addPage(pages);
        }

        /**
         * 构建pdf
         *
         * @param pages 页面列表
         */
        public static XEasyPdfDocument build(List<XEasyPdfPage> pages) {
            return new XEasyPdfDocument().addPage(pages);
        }

        /**
         * 加载pdf
         *
         * @param sourcePath 源文件路径
         */
        @SneakyThrows
        public static XEasyPdfDocument load(String sourcePath) {
            return new XEasyPdfDocument(sourcePath);
        }

        /**
         * 加载pdf
         *
         * @param sourceInputStream 源文件数据流
         */
        @SneakyThrows
        public static XEasyPdfDocument load(InputStream sourceInputStream) {
            return new XEasyPdfDocument(sourceInputStream);
        }
    }

    /**
     * pdf页面
     */
    public static class Page {

        /**
         * 总页码占位符
         */
        private static final String TOTAL_PAGE_PLACEHOLDER = "${TPE}";

        /**
         * 当前页码占位符
         */
        private static final String CURRENT_PAGE_PLACEHOLDER = "${PE}";

        /**
         * 构建页面
         *
         * @param components 组件列表
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(List<XEasyPdfComponent> components) {
            return new XEasyPdfPage(null).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param components 组件
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfComponent... components) {
            return new XEasyPdfPage(null).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param watermark  页面水印组件
         * @param components 组件
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfDefaultWatermark watermark, XEasyPdfComponent... components) {
            return new XEasyPdfPage(null).setWatermark(watermark).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param watermark  页面水印组件
         * @param components 组件列表
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfDefaultWatermark watermark, List<XEasyPdfComponent> components) {
            return new XEasyPdfPage(null).setWatermark(watermark).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param pageSize   pdf页面尺寸
         * @param components 组件
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfPageRectangle pageSize, XEasyPdfComponent... components) {
            return new XEasyPdfPage(pageSize).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param pageSize   pdf页面尺寸
         * @param components 组件列表
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfPageRectangle pageSize, List<XEasyPdfComponent> components) {
            return new XEasyPdfPage(pageSize).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param pageSize   pdf页面尺寸
         * @param watermark  页面水印组件
         * @param components 组件
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfPageRectangle pageSize, XEasyPdfDefaultWatermark watermark, XEasyPdfComponent... components) {
            return new XEasyPdfPage(pageSize).setWatermark(watermark).addComponent(components);
        }

        /**
         * 构建页面
         *
         * @param pageSize   pdf页面尺寸
         * @param watermark  页面水印组件
         * @param components 组件列表
         * @return 返回pdf页面组件
         */
        public static XEasyPdfPage build(XEasyPdfPageRectangle pageSize, XEasyPdfDefaultWatermark watermark, List<XEasyPdfComponent> components) {
            return new XEasyPdfPage(pageSize).setWatermark(watermark).addComponent(components);
        }

        /**
         * 获取总页码占位符
         *
         * @return 返回总页码占位符
         */
        public static String getTotalPagePlaceholder() {
            return TOTAL_PAGE_PLACEHOLDER;
        }

        /**
         * 获取当前页码占位符
         *
         * @return 返回当前页码占位符
         */
        public static String getCurrentPagePlaceholder() {
            return CURRENT_PAGE_PLACEHOLDER;
        }
    }

    /**
     * pdf水印组件
     */
    public static class Watermark {
        /**
         * 构建页面水印
         *
         * @param text 水印文本
         * @return 返回pdf页面水印组件
         */
        public static XEasyPdfDefaultWatermark build(String text) {
            return new XEasyPdfDefaultWatermark(text);
        }

        /**
         * 构建页面水印
         *
         * @param fontSize 字体大小
         * @param text     水印文本
         * @return 返回pdf页面水印组件
         */
        public static XEasyPdfDefaultWatermark build(float fontSize, String text) {
            return new XEasyPdfDefaultWatermark(fontSize, text);
        }
    }

    /**
     * pdf文本组件
     */
    public static class Text {
        /**
         * 构建文本
         *
         * @param text 待写入文本
         * @return 返回pdf文本组件
         */
        public static XEasyPdfText build(String text) {
            return new XEasyPdfText(text);
        }

        /**
         * 构建文本
         *
         * @param textList 待写入文本列表
         * @return 返回pdf文本组件
         */
        public static XEasyPdfText build(List<String> textList) {
            return new XEasyPdfText(textList);
        }

        /**
         * 构建文本
         *
         * @param fontSize 字体大小
         * @param text     待写入文本
         * @return 返回pdf文本组件
         */
        public static XEasyPdfText build(float fontSize, String text) {
            return new XEasyPdfText(fontSize, text);
        }

        /**
         * 构建文本
         *
         * @param fontSize 字体大小
         * @param textList 待写入文本列表
         * @return 返回pdf文本组件
         */
        public static XEasyPdfText build(float fontSize, List<String> textList) {
            return new XEasyPdfText(fontSize, textList);
        }
    }

    /**
     * pdf线条组件
     */
    public static class Line {
        /**
         * 构建线条
         *
         * @param beginX 当前页面X轴起始坐标
         * @param beginY 当前页面Y轴起始坐标
         * @param endX   当前页面X轴结束坐标
         * @param endY   当前页面Y轴结束坐标
         * @return 返回pdf线条组件
         */
        public static XEasyPdfBaseLine build(float beginX, float beginY, float endX, float endY) {
            return new XEasyPdfBaseLine(beginX, beginY, endX, endY);
        }
    }

    /**
     * pdf分割线组件
     */
    public static class SplitLine {
        /**
         * 实线
         */
        public static class SolidLine {
            /**
             * 构建实线分割线
             *
             * @return 返回pdf实线分割线组件
             */
            public static XEasyPdfSolidSplitLine build() {
                return new XEasyPdfSolidSplitLine();
            }
        }

        /**
         * 虚线
         */
        public static class DottedLine {
            /**
             * 构建虚线分割线
             *
             * @return 返回pdf虚线分割线组件
             */
            public static XEasyPdfDottedSplitLine build() {
                return new XEasyPdfDottedSplitLine();
            }
        }
    }

    /**
     * pdf图片组件
     */
    public static class Image {
        /**
         * 构建图片
         *
         * @param image 待添加图片
         * @return 返回pdf图片组件
         */
        public static XEasyPdfImage build(File image) {
            return new XEasyPdfImage(image);
        }

        /**
         * 构建图片
         *
         * @param imageInputStream 待添加图片数据流
         * @param imageType        待添加图片类型（扩展名）
         * @return 返回pdf图片组件
         */
        public static XEasyPdfImage build(InputStream imageInputStream, XEasyPdfImageType imageType) {
            return new XEasyPdfImage(imageInputStream, imageType);
        }

        /**
         * 构建图片
         *
         * @param image  待添加图片
         * @param width  图片宽度
         * @param height 图片高度
         * @return 返回pdf图片组件
         */
        public static XEasyPdfImage build(File image, int width, int height) {
            return new XEasyPdfImage(image, width, height);
        }

        /**
         * 构建图片
         *
         * @param imageInputStream 待添加图片数据流
         * @param imageType        待添加图片类型（扩展名）
         * @param width            图片宽度
         * @param height           图片高度
         * @return 返回pdf图片组件
         */
        public static XEasyPdfImage build(InputStream imageInputStream, XEasyPdfImageType imageType, int width, int height) {
            return new XEasyPdfImage(imageInputStream, imageType, width, height);
        }
    }

    /**
     * pdf矢量图片组件
     */
    public static class SVGImage {
        /**
         * 构建矢量图片
         *
         * @return 返回pdf矢量图片组件
         */
        public static XEasyPdfSVGImage build() {
            return new XEasyPdfSVGImage();
        }
    }

    /**
     * pdf条形码(一维码/二维码)组件
     */
    public static class BarCode {
        /**
         * 构建条形码
         *
         * @param codeType 条形码类型
         * @param content  条形码内容
         * @return 返回pdf条形码组件
         */
        public static XEasyPdfBarCode build(XEasyPdfBarCode.CodeType codeType, String content) {
            return new XEasyPdfBarCode(codeType, content);
        }

        /**
         * 构建条形码
         *
         * @param codeType 条形码类型
         * @param content  条形码内容
         * @param words    条形码文字
         * @return 返回pdf条形码组件
         */
        public static XEasyPdfBarCode build(XEasyPdfBarCode.CodeType codeType, String content, String words) {
            return new XEasyPdfBarCode(codeType, content, words);
        }

        /**
         * 构建条形码
         *
         * @param codeType 条形码类型
         * @param content  条形码内容
         * @param beginX   X轴起始坐标
         * @param beginY   Y轴起始坐标
         * @return 返回pdf条形码组件
         */
        public static XEasyPdfBarCode build(XEasyPdfBarCode.CodeType codeType, String content, float beginX, float beginY) {
            return new XEasyPdfBarCode(codeType, content, beginX, beginY);
        }

        /**
         * 构建条形码
         *
         * @param codeType 条形码类型
         * @param content  条形码内容
         * @param words    条形码文字
         * @param beginX   X轴起始坐标
         * @param beginY   Y轴起始坐标
         * @return 返回pdf条形码组件
         */
        public static XEasyPdfBarCode build(XEasyPdfBarCode.CodeType codeType, String content, String words, float beginX, float beginY) {
            return new XEasyPdfBarCode(codeType, content, words, beginX, beginY);
        }
    }

    /**
     * pdf矩形组件
     */
    public static class Rect {
        /**
         * 构建矩形
         *
         * @param width  宽度
         * @param height 高度
         * @return 返回pdf矩形组件
         */
        public static XEasyPdfRect build(float width, float height) {
            return new XEasyPdfRect(width, height);
        }

        /**
         * 构建矩形
         *
         * @param width  宽度
         * @param height 高度
         * @param beginX X轴起始坐标
         * @param beginY Y轴起始坐标
         * @return 返回pdf矩形组件
         */
        public static XEasyPdfRect build(float width, float height, float beginX, float beginY) {
            return new XEasyPdfRect(width, height, beginX, beginY);
        }
    }

    /**
     * pdf圆形组件
     */
    public static class Circle {
        /**
         * 构建圆形
         *
         * @param radius 半径
         * @return 返回pdf圆形组件
         */
        public static XEasyPdfCircle build(float radius) {
            return new XEasyPdfCircle(radius);
        }

        /**
         * 构建圆形
         *
         * @param radius 半径
         * @param beginX X轴起始坐标
         * @param beginY Y轴起始坐标
         * @return 返回pdf圆形组件
         */
        public static XEasyPdfCircle build(float radius, float beginX, float beginY) {
            return new XEasyPdfCircle(radius, beginX, beginY);
        }
    }

    /**
     * pdf表格组件
     */
    public static class Table {
        /**
         * pdf表格行
         */
        public static class Row {
            /**
             * pdf单元格
             */
            public static class Cell {

                /**
                 * 构建单元格
                 *
                 * @param width 宽度
                 * @return 返回pdf单元格组件
                 */
                public static XEasyPdfCell build(float width) {
                    return new XEasyPdfCell(width);
                }

                /**
                 * 构建单元格
                 *
                 * @param width  宽度
                 * @param height 高度
                 * @return 返回pdf单元格组件
                 */
                public static XEasyPdfCell build(float width, float height) {
                    return new XEasyPdfCell(width, height);
                }
            }

            /**
             * 构建表格行
             *
             * @param cells 单元格
             * @return 返回pdf表格行组件
             */
            public static XEasyPdfRow build(XEasyPdfCell... cells) {
                return new XEasyPdfRow(cells);
            }

            /**
             * 构建表格行
             *
             * @param cellList 单元格列表
             * @return 返回pdf表格行组件
             */
            public static XEasyPdfRow build(List<XEasyPdfCell> cellList) {
                return new XEasyPdfRow(cellList);
            }
        }

        /**
         * 构建表格
         *
         * @param rows 表格行
         * @return 返回pdf表格组件
         */
        public static XEasyPdfTable build(XEasyPdfRow... rows) {
            return new XEasyPdfTable(rows);
        }

        /**
         * 构建表格
         *
         * @param rowList 表格行列表
         * @return 返回pdf表格组件
         */
        public static XEasyPdfTable build(List<XEasyPdfRow> rowList) {
            return new XEasyPdfTable(rowList);
        }
    }

    /**
     * pdf页眉组件
     */
    public static class Header {

        /**
         * 构建页眉
         *
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build() {
            return new XEasyPdfDefaultHeader();
        }

        /**
         * 构建页眉
         *
         * @param component 自定义组件
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build(XEasyPdfComponent component) {
            return new XEasyPdfDefaultHeader(component);
        }

        /**
         * 构建页眉
         *
         * @param text 文本组件
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build(XEasyPdfText text) {
            return new XEasyPdfDefaultHeader(text);
        }

        /**
         * 构建页眉
         *
         * @param image 图片组件
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build(XEasyPdfImage image) {
            return new XEasyPdfDefaultHeader(image);
        }

        /**
         * 构建页眉
         *
         * @param image 图片组件
         * @param text  文本组件
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build(XEasyPdfImage image, XEasyPdfText text) {
            return new XEasyPdfDefaultHeader(image, text);
        }

        /**
         * 构建页眉
         *
         * @param text  文本组件
         * @param image 图片组件
         * @return 返回pdf页眉组件
         */
        public static XEasyPdfHeader build(XEasyPdfText text, XEasyPdfImage image) {
            return new XEasyPdfDefaultHeader(image, text);
        }
    }

    /**
     * pdf页脚组件
     */
    public static class Footer {

        /**
         * 构建页脚
         *
         * @param component 自定义组件
         * @return 返回pdf页脚组件
         */
        public static XEasyPdfFooter build(XEasyPdfComponent component) {
            return new XEasyPdfDefaultFooter(component);
        }

        /**
         * 构建页脚
         *
         * @param text 文本组件
         * @return 返回pdf页脚组件
         */
        public static XEasyPdfFooter build(XEasyPdfText text) {
            return new XEasyPdfDefaultFooter(text);
        }

        /**
         * 构建页脚
         *
         * @param image 图片组件
         * @return 返回pdf页脚组件
         */
        public static XEasyPdfFooter build(XEasyPdfImage image) {
            return new XEasyPdfDefaultFooter(image);
        }

        /**
         * 构建页脚
         *
         * @param image 图片组件
         * @param text  文本组件
         * @return 返回pdf页脚组件
         */
        public static XEasyPdfFooter build(XEasyPdfImage image, XEasyPdfText text) {
            return new XEasyPdfDefaultFooter(image, text);
        }

        /**
         * 构建页脚
         *
         * @param text  文本组件
         * @param image 图片组件
         * @return 返回pdf页脚组件
         */
        public static XEasyPdfFooter build(XEasyPdfText text, XEasyPdfImage image) {
            return new XEasyPdfDefaultFooter(image, text);
        }
    }

    /**
     * pdf布局组件
     */
    public static class Layout {
        /**
         * 水平布局
         */
        public static class Horizontal {
            /**
             * 构建水平布局
             *
             * @return 返回pdf水平布局组件
             */
            public static XEasyPdfHorizontalLayout build() {
                return new XEasyPdfHorizontalLayout();
            }
        }

        /**
         * 垂直布局
         */
        public static class Vertical {
            /**
             * 构建水平布局
             *
             * @return 返回pdf水平布局组件
             */
            public static XEasyPdfVerticalLayout build() {
                return new XEasyPdfVerticalLayout();
            }
        }

        /**
         * pdf布局中的组件
         */
        public static class Component {
            /**
             * 构建布局组件中的组件
             *
             * @param width  宽度
             * @param height 高度
             * @return 返回pdf布局组件中的组件
             */
            public static XEasyPdfLayoutComponent build(float width, float height) {
                return new XEasyPdfLayoutComponent(width, height);
            }

            /**
             * 构建布局组件中的组件
             *
             * @param width     宽度
             * @param height    高度
             * @param component pdf组件
             * @return 返回pdf布局组件中的组件
             */
            public static XEasyPdfLayoutComponent build(float width, float height, XEasyPdfComponent component) {
                return new XEasyPdfLayoutComponent(width, height).setComponent(component);
            }
        }
    }

    /**
     * pdf字体映射策略
     */
    public enum FontMappingPolicy {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 全部
         */
        ALL;

        /**
         * 获取key
         *
         * @return 返回key
         */
        public static String key() {
            return "x-easypdf.font.mapping";
        }
    }
}
