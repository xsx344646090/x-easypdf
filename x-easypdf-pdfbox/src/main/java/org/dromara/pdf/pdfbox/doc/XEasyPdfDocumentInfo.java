package org.dromara.pdf.pdfbox.doc;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.Serializable;
import java.util.Calendar;


/**
 * pdf文档信息
 *
 * @author xsx
 * @date 2020/5/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfDocumentInfo implements Serializable {

    private static final long serialVersionUID = -9083747658446890160L;

    /**
     * pdf文档
     */
    private final XEasyPdfDocument document;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 主题
     */
    private String subject;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Calendar creationDate;
    /**
     * 修改时间
     */
    private Calendar modificationDate;

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    XEasyPdfDocumentInfo(XEasyPdfDocument document) {
        this.document = document;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 获取标题
     *
     * @return 返回标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * 获取作者
     *
     * @return 返回作者
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * 设置主题
     *
     * @param subject 主题
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * 获取主题
     *
     * @return 返回主题
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * 设置关键词
     *
     * @param keywords 关键词
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    /**
     * 获取关键词
     *
     * @return 返回关键词
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    /**
     * 获取创建者
     *
     * @return 返回创建者
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setCreateTime(Calendar createTime) {
        this.creationDate = createTime;
        return this;
    }

    /**
     * 获取创建时间
     *
     * @return 返回创建时间
     */
    public Calendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo setUpdateTime(Calendar updateTime) {
        this.modificationDate = updateTime;
        return this;
    }

    /**
     * 获取修改时间
     *
     * @return 返回修改时间
     */
    public Calendar getModificationDate() {
        return this.modificationDate;
    }

    /**
     * 完成信息设置
     *
     * @return 返回pdf文档
     */
    public XEasyPdfDocument finish() {
        this.document.setInfo(this);
        return this.document;
    }

    /**
     * 获取pdfBox文档信息
     *
     * @return 返回pdfBox文档信息
     */
    PDDocumentInformation getInfo() {
        PDDocumentInformation pdInfo = new PDDocumentInformation();
        pdInfo.setTitle(this.title);
        pdInfo.setAuthor(this.author);
        pdInfo.setSubject(this.subject);
        pdInfo.setKeywords(this.keywords);
        pdInfo.setCreator(this.creator);
        pdInfo.setCreationDate(this.creationDate);
        pdInfo.setModificationDate(this.modificationDate);
        return pdInfo;
    }
}
