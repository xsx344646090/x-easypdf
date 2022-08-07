package wiki.xsx.core.pdf.template.handler;

import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocument;
import wiki.xsx.core.pdf.template.page.XEasyPdfTemplatePage;
import wiki.xsx.core.pdf.template.template.XEasyPdfTemplate;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateDocumentDataSource;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateThymeleafDataSource;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateXMLDataSource;

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
         * @return 返回pdf模板文档
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
         * @return 返回pdf模板页面
         */
        public static XEasyPdfTemplatePage build() {
            return new XEasyPdfTemplatePage();
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
