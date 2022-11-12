package wiki.xsx.core.pdf.template.doc;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplate;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.doc.bookmark.XEasyPdfTemplateBookmarkComponent;
import wiki.xsx.core.pdf.template.doc.page.XEasyPdfTemplatePageComponent;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-文档
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
public class XEasyPdfTemplateDocument implements XEasyPdfTemplateDocumentComponent {

    /**
     * pdf模板文档参数
     */
    private final XEasyPdfTemplateDocumentParam param = new XEasyPdfTemplateDocumentParam();

    /**
     * 设置配置文件路径（fop配置文件路径）
     * <p>注：此路径为绝对路径</p>
     *
     * @param configPath 配置文件路径（绝对路径）
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument setConfigPath(String configPath) {
        this.param.setConfigPath(configPath);
        return this;
    }

    /**
     * 添加页面组件
     * <p>注：不推荐添加多个页面，会影响总页码的准确性</p>
     *
     * @param pages 页面组件列表
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument addPage(XEasyPdfTemplatePageComponent... pages) {
        Optional.ofNullable(pages).ifPresent(v -> Collections.addAll(this.param.getPageList(), v));
        return this;
    }

    /**
     * 添加书签组件
     *
     * @param bookmarks 书签组件列表
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument addBookmark(XEasyPdfTemplateBookmarkComponent... bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(v -> Collections.addAll(this.param.getBookmarkList(), v));
        return this;
    }

    /**
     * 添加书签组件
     *
     * @param bookmarks 书签组件列表
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument addBookmark(List<XEasyPdfTemplateBookmarkComponent> bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(this.param.getBookmarkList()::addAll);
        return this;
    }

    /**
     * 转换
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public wiki.xsx.core.pdf.doc.XEasyPdfDocument transform() {
        return this.initTemplate().transform();
    }

    /**
     * 转换
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    @Override
    public void transform(OutputStream outputStream) {
        this.initTemplate().transform(outputStream);
    }

    /**
     * 获取xsl-fo文档
     *
     * @return 返回xsl-fo文档
     */
    @SneakyThrows
    @Override
    public Document getDocument() {
        // 定义文档
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                this.getClass().getResourceAsStream(XEasyPdfTemplateConstants.DEFAULT_TEMPLATE_PATH)
        );
        // 添加页面
        this.addPage(document);
        // 返回文档
        return document;
    }

    /**
     * 获取xsl-fo文档内容
     *
     * @return 返回文档内容
     */
    @SneakyThrows
    public String getContent() {
        return XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(this).getDocumentContent();
    }

    /**
     * 初始化模板
     *
     * @return 返回pdf模板
     */
    private XEasyPdfTemplate initTemplate() {
        return XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(this.param.getConfigPath())
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(this));
    }

    /**
     * 添加页面
     *
     * @param document fo文档
     */
    private void addPage(Document document) {
        // 获取根元素
        Element root = document.getDocumentElement();
        // 定义页面索引
        int index = 0;
        // 获取页面列表
        List<XEasyPdfTemplatePageComponent> pageList = this.param.getPageList();
        // 遍历页面组件
        for (XEasyPdfTemplatePageComponent page : pageList) {
            // 添加页面节点
            root.appendChild(page.createElement(++index, document, this.createBookmark(document, index)));
        }
    }

    /**
     * 创建书签元素
     *
     * @param document fo文档
     * @param index    当前索引
     * @return 返回书签元素
     */
    private Element createBookmark(Document document, int index) {
        // 如果当前索引大于1，则返回空
        if (index > 1) {
            // 返回空
            return null;
        }
        // 如果书签列表不为空，则返回书签
        if (!this.param.getBookmarkList().isEmpty()) {
            // 创建bookmark-tree元素
            Element tree = document.createElement(XEasyPdfTemplateTags.BOOKMARK_TREE);
            // 添加书签
            this.param.getBookmarkList().forEach(v -> tree.appendChild(v.createElement(document)));
            // 返回bookmark-tree元素
            return tree;
        }
        // 返回空
        return null;
    }
}
