package org.dromara.pdf.fop.core.doc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dromara.pdf.fop.core.base.Constants;
import org.dromara.pdf.fop.core.base.Template;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.dromara.pdf.fop.core.doc.bookmark.BookmarkComponent;
import org.dromara.pdf.fop.core.doc.page.PageComponent;
import org.dromara.pdf.fop.handler.TemplateHandler;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-文档
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
@Slf4j
public class Document implements DocumentComponent {

    /**
     * pdf模板文档参数
     */
    private final DocumentParam param = new DocumentParam();

    /**
     * 设置配置文件路径（fop配置文件路径）
     * <p>注：此路径为绝对路径</p>
     *
     * @param configPath 配置文件路径（绝对路径）
     * @return 返回pdf模板-文档
     */
    public Document setConfigPath(String configPath) {
        this.param.setConfigPath(configPath);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return 返回pdf模板-文档
     */
    public Document setTitle(String title) {
        this.param.setTitle(title);
        return this;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     * @return 返回pdf模板-文档
     */
    public Document setAuthor(String author) {
        this.param.setAuthor(author);
        return this;
    }

    /**
     * 设置主题
     *
     * @param subject 主题
     * @return 返回pdf模板-文档
     */
    public Document setSubject(String subject) {
        this.param.setSubject(subject);
        return this;
    }

    /**
     * 设置关键词
     *
     * @param keywords 关键词
     * @return 返回pdf模板-文档
     */
    public Document setKeywords(String keywords) {
        this.param.setKeywords(keywords);
        return this;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     * @return 返回pdf模板-文档
     */
    public Document setCreator(String creator) {
        this.param.setCreator(creator);
        return this;
    }

    /**
     * 设置创建时间
     *
     * @param date 创建时间
     * @return 返回pdf模板-文档
     */
    public Document setCreationDate(Date date) {
        this.param.setCreationDate(date);
        return this;
    }

    /**
     * 添加页面组件
     * <p>注：不推荐添加多个页面，会影响总页码的准确性</p>
     *
     * @param pages 页面组件列表
     * @return 返回pdf模板-文档
     */
    public Document addPage(PageComponent... pages) {
        Optional.ofNullable(pages).ifPresent(v -> Collections.addAll(this.param.getPageList(), v));
        return this;
    }

    /**
     * 添加书签组件
     *
     * @param bookmarks 书签组件列表
     * @return 返回pdf模板-文档
     */
    public Document addBookmark(BookmarkComponent... bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(v -> Collections.addAll(this.param.getBookmarkList(), v));
        return this;
    }

    /**
     * 添加书签组件
     *
     * @param bookmarks 书签组件列表
     * @return 返回pdf模板-文档
     */
    public Document addBookmark(List<BookmarkComponent> bookmarks) {
        Optional.ofNullable(bookmarks).ifPresent(this.param.getBookmarkList()::addAll);
        return this;
    }

    /**
     * 保存模板
     *
     * @param path 保存路径
     */
    @SneakyThrows
    public void save(String path) {
        // 创建输出流
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(path))) {
            // 保存模板
            this.save(outputStream);
        }
    }

    /**
     * 保存模板
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void save(OutputStream outputStream) {
        // 获取模板内容
        String content = this.getContent();
        // 如果开启日志，则打印xsl-fo内容
        if (log.isInfoEnabled()) {
            // 打印xsl-fo内容
            log.info("XSL-FO ==> \n" + content);
        }
        // 写入内容
        outputStream.write(content.getBytes());
    }

    /**
     * 转换
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public org.dromara.pdf.pdfbox.core.base.Document transform() {
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
    public org.w3c.dom.Document getDocument() {
        // 定义文档
        org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.DEFAULT_TEMPLATE_PATH)
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
        return TemplateHandler.DataSource.Document.build().setDocument(this).getDocumentContent();
    }

    /**
     * 获取总页数
     *
     * @return 返回总页数
     */
    public Integer getTotalPage() {
        return this.initTemplate().getTotalPage();
    }

    /**
     * 初始化模板
     *
     * @return 返回pdf模板
     */
    private Template initTemplate() {
        return TemplateHandler.Template.build()
                .setConfigPath(this.param.getConfigPath())
                .setTitle(this.param.getTitle())
                .setAuthor(this.param.getAuthor())
                .setSubject(this.param.getSubject())
                .setKeywords(this.param.getKeywords())
                .setCreator(this.param.getCreator())
                .setCreationDate(this.param.getCreationDate())
                .setDataSource(TemplateHandler.DataSource.Document.build().setDocument(this));
    }

    /**
     * 添加页面
     *
     * @param document fo文档
     */
    private void addPage(org.w3c.dom.Document document) {
        // 获取根元素
        Element root = document.getDocumentElement();
        // 定义页面索引
        int index = 0;
        // 获取页面列表
        List<PageComponent> pageList = this.param.getPageList();
        // 遍历页面组件
        for (PageComponent page : pageList) {
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
    private Element createBookmark(org.w3c.dom.Document document, int index) {
        // 如果当前索引大于1，则返回空
        if (index > 1) {
            // 返回空
            return null;
        }
        // 如果书签列表不为空，则返回书签
        if (!this.param.getBookmarkList().isEmpty()) {
            // 创建bookmark-tree元素
            Element tree = document.createElement(TemplateTags.BOOKMARK_TREE);
            // 添加书签
            this.param.getBookmarkList().forEach(v -> tree.appendChild(v.createElement(document)));
            // 返回bookmark-tree元素
            return tree;
        }
        // 返回空
        return null;
    }
}
