package org.dromara.pdf.fop.handler;

import org.dromara.pdf.fop.core.base.Constants;
import org.dromara.pdf.fop.core.datasource.*;
import org.dromara.pdf.fop.core.doc.component.table.*;

/**
 * pdf模板助手
 *
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
public class TemplateHandler {

    /**
     * pdf模板
     */
    public static class Template {
        /**
         * 构建pdf模板
         *
         * @return 返回pdf模板
         */
        public static org.dromara.pdf.fop.core.base.Template build() {
            return new org.dromara.pdf.fop.core.base.Template();
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
            public static XMLDataSource build() {
                return new XMLDataSource();
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
            public static ThymeleafDataSource build() {
                return new ThymeleafDataSource();
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
            public static JteDataSource build() {
                return new JteDataSource();
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
            public static FreemarkerDataSource build() {
                return new FreemarkerDataSource();
            }

            /**
             * 设置模板路径（目录）
             *
             * @param templatePath 模板路径（目录）
             */
            public static void setTemplatePath(String templatePath) {
                System.setProperty(Constants.FREEMARKER_TEMPLATE_PATH_KEY, templatePath);
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
            public static DocumentDataSource build() {
                return new DocumentDataSource();
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
        public static org.dromara.pdf.fop.core.doc.Document build() {
            return new org.dromara.pdf.fop.core.doc.Document();
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
        public static org.dromara.pdf.fop.core.doc.page.Page build() {
            return new org.dromara.pdf.fop.core.doc.page.Page();
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
        public static org.dromara.pdf.fop.core.doc.component.page.CurrentPageNumber build() {
            return new org.dromara.pdf.fop.core.doc.component.page.CurrentPageNumber();
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
        public static org.dromara.pdf.fop.core.doc.component.page.TotalPageNumber build() {
            return new org.dromara.pdf.fop.core.doc.component.page.TotalPageNumber();
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
        public static org.dromara.pdf.fop.core.doc.component.text.Text build() {
            return new org.dromara.pdf.fop.core.doc.component.text.Text();
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
        public static org.dromara.pdf.fop.core.doc.component.text.TextExtend build() {
            return new org.dromara.pdf.fop.core.doc.component.text.TextExtend();
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
        public static org.dromara.pdf.fop.core.doc.component.image.Image build() {
            return new org.dromara.pdf.fop.core.doc.component.image.Image();
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
        public static org.dromara.pdf.fop.core.doc.component.table.Table build() {
            return new org.dromara.pdf.fop.core.doc.component.table.Table();
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
            public static TableHeader build() {
                return new TableHeader();
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
            public static TableFooter build() {
                return new TableFooter();
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
            public static TableBody build() {
                return new TableBody();
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
            public static TableRow build() {
                return new TableRow();
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
            public static TableCell build() {
                return new TableCell();
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
        public static org.dromara.pdf.fop.core.doc.component.line.SplitLine build() {
            return new org.dromara.pdf.fop.core.doc.component.line.SplitLine();
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
        public static org.dromara.pdf.fop.core.doc.component.link.Link build() {
            return new org.dromara.pdf.fop.core.doc.component.link.Link();
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
        public static org.dromara.pdf.fop.core.doc.component.block.BlockContainer build() {
            return new org.dromara.pdf.fop.core.doc.component.block.BlockContainer();
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
        public static org.dromara.pdf.fop.core.doc.component.barcode.Barcode build() {
            return new org.dromara.pdf.fop.core.doc.component.barcode.Barcode();
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
        public static org.dromara.pdf.fop.core.doc.bookmark.Bookmark build() {
            return new org.dromara.pdf.fop.core.doc.bookmark.Bookmark();
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
        public static org.dromara.pdf.fop.core.doc.watermark.Watermark build() {
            return new org.dromara.pdf.fop.core.doc.watermark.Watermark();
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
        public static FontHandler build() {
            return new FontHandler();
        }
    }
    
    /**
     * pdf模板标语
     */
    public static class Banner {
        
        /**
         * 关闭
         */
        public static void disable() {
            org.dromara.pdf.fop.core.base.Banner.disable();
        }
    }
}
