package wiki.xsx.core.pdf.template.handler;

import wiki.xsx.core.pdf.template.XEasyPdfTemplate;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.datasource.*;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.doc.bookmark.XEasyPdfTemplateBookmark;
import wiki.xsx.core.pdf.template.doc.component.barcode.XEasyPdfTemplateBarcode;
import wiki.xsx.core.pdf.template.doc.component.block.XEasyPdfTemplateBlockContainer;
import wiki.xsx.core.pdf.template.doc.component.image.XEasyPdfTemplateImage;
import wiki.xsx.core.pdf.template.doc.component.line.XEasyPdfTemplateSplitLine;
import wiki.xsx.core.pdf.template.doc.component.link.XEasyPdfTemplateLink;
import wiki.xsx.core.pdf.template.doc.component.page.XEasyPdfTemplateCurrentPageNumber;
import wiki.xsx.core.pdf.template.doc.component.page.XEasyPdfTemplateTotalPageNumber;
import wiki.xsx.core.pdf.template.doc.component.table.*;
import wiki.xsx.core.pdf.template.doc.component.text.XEasyPdfTemplateText;
import wiki.xsx.core.pdf.template.doc.component.text.XEasyPdfTemplateTextExtend;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePage;
import wiki.xsx.core.pdf.template.doc.watermark.XEasyPdfTemplateWatermark;

/**
 * pdf模板助手
 *
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
public class XEasyPdfTemplateHandler {

    /**
     * pdf模板
     */
    public static class Template {
        /**
         * 构建pdf模板
         *
         * @return 返回pdf模板
         */
        public static XEasyPdfTemplate build() {
            return new XEasyPdfTemplate();
        }
    }

    /**
     * pdf模板数据源
     */
    public static class DataSource {
        /**
         * xml数据源
         *
         * @see <a href="https://www.runoob.com/xsl/xsl-tutorial.html">XSLT</a>
         */
        public static class XML {
            /**
             * 构建xml数据源
             *
             * @return 返回xml数据源
             */
            public static XEasyPdfTemplateXMLDataSource build() {
                return new XEasyPdfTemplateXMLDataSource();
            }
        }

        /**
         * thymeleaf数据源
         *
         * @see <a href="https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html">Thymeleaf</a>
         */
        public static class Thymeleaf {
            /**
             * 构建thymeleaf数据源
             *
             * @return 返回thymeleaf数据源
             */
            public static XEasyPdfTemplateThymeleafDataSource build() {
                return new XEasyPdfTemplateThymeleafDataSource();
            }
        }

        /**
         * jte数据源
         *
         * @see <a href="https://gitee.com/qcrud/jte/blob/main/DOCUMENTATION.md">Jte</a>
         */
        public static class Jte {
            /**
             * 构建jte数据源
             *
             * @return 返回jte数据源
             */
            public static XEasyPdfTemplateJteDataSource build() {
                return new XEasyPdfTemplateJteDataSource();
            }
        }

        /**
         * freemarker数据源
         *
         * @see <a href="https://freemarker.apache.org/docs/dgui_quickstart.html">Freemarker</a>
         */
        public static class Freemarker {
            /**
             * 构建freemarker数据源
             *
             * @return 返回freemarker数据源
             */
            public static XEasyPdfTemplateFreemarkerDataSource build() {
                return new XEasyPdfTemplateFreemarkerDataSource();
            }

            /**
             * 设置模板路径（目录）
             *
             * @param templatePath 模板路径（目录）
             */
            public static void setTemplatePath(String templatePath) {
                System.setProperty(XEasyPdfTemplateConstants.FREEMARKER_TEMPLATE_PATH_KEY, templatePath);
            }
        }

        /**
         * document数据源
         */
        public static class Document {
            /**
             * 构建document数据源
             *
             * @return 返回document数据源
             */
            public static XEasyPdfTemplateDocumentDataSource build() {
                return new XEasyPdfTemplateDocumentDataSource();
            }
        }
    }

    /**
     * pdf模板文档
     */
    public static class Document {
        /**
         * 构建文档
         *
         * @return 返回pdf模板-文档
         */
        public static XEasyPdfTemplateDocument build() {
            return new XEasyPdfTemplateDocument();
        }
    }

    /**
     * pdf模板页面
     */
    public static class Page {
        /**
         * 构建页面
         *
         * @return 返回pdf模板-页面
         */
        public static XEasyPdfTemplatePage build() {
            return new XEasyPdfTemplatePage();
        }
    }

    /**
     * pdf模板当前页码
     */
    public static class CurrentPageNumber {
        /**
         * 构建当前页码
         *
         * @return 返回pdf模板-当前页码
         */
        public static XEasyPdfTemplateCurrentPageNumber build() {
            return new XEasyPdfTemplateCurrentPageNumber();
        }
    }

    /**
     * pdf模板总页码
     */
    public static class TotalPageNumber {
        /**
         * 构建总页码
         *
         * @return 返回pdf模板-总页码
         */
        public static XEasyPdfTemplateTotalPageNumber build() {
            return new XEasyPdfTemplateTotalPageNumber();
        }
    }

    /**
     * pdf模板文本
     */
    public static class Text {
        /**
         * 构建文本
         *
         * @return 返回pdf模板-文本
         */
        public static XEasyPdfTemplateText build() {
            return new XEasyPdfTemplateText();
        }
    }

    /**
     * pdf模板文本扩展
     */
    public static class TextExtend {
        /**
         * 构建文本扩展
         *
         * @return 返回pdf模板-文本扩展
         */
        public static XEasyPdfTemplateTextExtend build() {
            return new XEasyPdfTemplateTextExtend();
        }
    }

    /**
     * pdf模板图像
     */
    public static class Image {
        /**
         * 构建图像
         *
         * @return 返回pdf模板-图像
         */
        public static XEasyPdfTemplateImage build() {
            return new XEasyPdfTemplateImage();
        }
    }

    /**
     * pdf模板表格
     */
    public static class Table {
        /**
         * 构建表格
         *
         * @return 返回pdf模板-表格
         */
        public static XEasyPdfTemplateTable build() {
            return new XEasyPdfTemplateTable();
        }

        /**
         * pdf模板表头
         */
        public static class Header {
            /**
             * 构建表头
             *
             * @return 返回pdf模板-表头
             */
            public static XEasyPdfTemplateTableHeader build() {
                return new XEasyPdfTemplateTableHeader();
            }
        }

        /**
         * pdf模板表尾
         */
        public static class Footer {
            /**
             * 构建表尾
             *
             * @return 返回pdf模板-表尾
             */
            public static XEasyPdfTemplateTableFooter build() {
                return new XEasyPdfTemplateTableFooter();
            }
        }

        /**
         * pdf模板表格体
         */
        public static class Body {
            /**
             * 构建表格体
             *
             * @return 返回pdf模板-表格体
             */
            public static XEasyPdfTemplateTableBody build() {
                return new XEasyPdfTemplateTableBody();
            }
        }

        /**
         * pdf模板表格行
         */
        public static class Row {
            /**
             * 构建表格行
             *
             * @return 返回pdf模板-表格行
             */
            public static XEasyPdfTemplateTableRow build() {
                return new XEasyPdfTemplateTableRow();
            }
        }

        /**
         * pdf模板表格单元格
         */
        public static class Cell {
            /**
             * 构建表格单元格
             *
             * @return 返回pdf模板-表格单元格
             */
            public static XEasyPdfTemplateTableCell build() {
                return new XEasyPdfTemplateTableCell();
            }
        }
    }

    /**
     * pdf模板分割线
     */
    public static class SplitLine {
        /**
         * 构建分割线
         *
         * @return 返回pdf模板-分割线
         */
        public static XEasyPdfTemplateSplitLine build() {
            return new XEasyPdfTemplateSplitLine();
        }
    }

    /**
     * pdf模板超链接
     */
    public static class Link {
        /**
         * 构建超链接
         *
         * @return 返回pdf模板-超链接
         */
        public static XEasyPdfTemplateLink build() {
            return new XEasyPdfTemplateLink();
        }
    }

    /**
     * pdf模板块容器
     */
    public static class BlockContainer {
        /**
         * 构建块容器
         *
         * @return 返回pdf模板-块容器
         */
        public static XEasyPdfTemplateBlockContainer build() {
            return new XEasyPdfTemplateBlockContainer();
        }
    }

    /**
     * pdf模板条形码
     */
    public static class Barcode {
        /**
         * 构建条形码
         *
         * @return 返回pdf模板-条形码
         */
        public static XEasyPdfTemplateBarcode build() {
            return new XEasyPdfTemplateBarcode();
        }
    }

    /**
     * pdf模板书签
     */
    public static class Bookmark {
        /**
         * 构建书签
         *
         * @return 返回pdf模板-书签
         */
        public static XEasyPdfTemplateBookmark build() {
            return new XEasyPdfTemplateBookmark();
        }
    }

    /**
     * pdf模板水印（文本）
     */
    public static class Watermark {
        /**
         * 构建水印（文本）
         *
         * @return 返回pdf模板-水印（文本）
         */
        public static XEasyPdfTemplateWatermark build() {
            return new XEasyPdfTemplateWatermark();
        }
    }

    /**
     * pdf模板字体
     * <p>注：用于生成自定义字体</p>
     */
    public static class Font {
        /**
         * 构建字体
         *
         * @return 返回pdf模板字体助手
         */
        public static XEasyPdfTemplateFontHandler build() {
            return new XEasyPdfTemplateFontHandler();
        }
    }
}
