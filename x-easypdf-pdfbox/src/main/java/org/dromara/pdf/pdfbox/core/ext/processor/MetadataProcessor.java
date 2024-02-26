package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.*;
import org.apache.xmpbox.xml.DomXmpParser;
import org.apache.xmpbox.xml.XmpSerializer;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.support.Constants;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 元数据处理器
 *
 * @author xsx
 * @date 2024/2/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class MetadataProcessor extends AbstractProcessor {

    /**
     * 文档元数据
     */
    protected PDMetadata metadata;
    /**
     * xmp元数据
     */
    protected XMPMetadata xmpMetadata;
    /**
     * adobe大纲
     */
    protected AdobePDFSchema adobeSchema;
    /**
     * 基础大纲
     */
    protected XMPBasicSchema basicSchema;
    /**
     * dc大纲
     */
    protected DublinCoreSchema dcSchema;
    /**
     * 版权大纲
     */
    protected XMPRightsManagementSchema rmSchema;
    /**
     * 媒体大纲
     */
    protected XMPMediaManagementSchema mmSchema;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public MetadataProcessor(Document document) {
        super(document);
        this.initMetadata();
    }

    /**
     * 获取pdf版本
     *
     * @return 返回pdf版本
     */
    public String getPDFVersion() {
        return Objects.isNull(this.adobeSchema) ? null : this.adobeSchema.getPDFVersion();
    }

    /**
     * 获取制作程序
     *
     * @return 返回制作程序
     */
    public String getProducer() {
        return Objects.isNull(this.adobeSchema) ? null : this.adobeSchema.getProducer();
    }

    /**
     * 获取关键字
     *
     * @return 返回关键字
     */
    public String getKeywords() {
        return Objects.isNull(this.adobeSchema) ? null : this.adobeSchema.getKeywords();
    }

    /**
     * 获取创建日期
     *
     * @return 返回创建日期
     */
    public Calendar getCreateDate() {
        return Objects.isNull(this.basicSchema) ? null : this.basicSchema.getCreateDate();
    }

    /**
     * 获取修改日期
     *
     * @return 返回修改日期
     */
    public Calendar getModifyDate() {
        return Objects.isNull(this.basicSchema) ? null : this.basicSchema.getModifyDate();
    }

    /**
     * 获取创建工具
     *
     * @return 返回创建工具
     */
    public String getCreatorTool() {
        return Objects.isNull(this.basicSchema) ? null : this.basicSchema.getCreatorTool();
    }

    /**
     * 获取标题
     *
     * @return 返回标题
     */
    @SneakyThrows
    public String getTitle() {
        return Objects.isNull(this.dcSchema) ? null : this.dcSchema.getTitle();
    }

    /**
     * 获取作者
     *
     * @return 返回作者
     */
    public List<String> getAuthors() {
        return Objects.isNull(this.dcSchema) ? null : this.dcSchema.getCreators();
    }

    /**
     * 获取主题
     *
     * @return 返回主题
     */
    @SneakyThrows
    public String getSubject() {
        return Objects.isNull(this.dcSchema) ? null : this.dcSchema.getDescription();
    }

    /**
     * 获取格式
     *
     * @return 返回格式
     */
    public String getFormat() {
        return Objects.isNull(this.dcSchema) ? null : this.dcSchema.getFormat();
    }

    /**
     * 获取版权信息
     *
     * @return 返回版权信息
     */
    @SneakyThrows
    public String getRights() {
        return Objects.isNull(this.dcSchema) ? null : this.dcSchema.getRights();
    }

    /**
     * 获取版权状态
     * <p>注：null=未知，true=版权所有，false=公共域</p>
     *
     * @return 返回版权状态
     */
    public Boolean getRightsMarked() {
        return Objects.isNull(this.rmSchema) ? null : this.rmSchema.getMarked();
    }

    /**
     * 获取版权信息url
     *
     * @return 返回版权信息url
     */
    public String getRightsUrl() {
        return Objects.isNull(this.rmSchema) ? null : this.rmSchema.getWebStatement();
    }

    /**
     * 获取文档id
     *
     * @return 返回文档id
     */
    public String getDocumentID() {
        return Objects.isNull(this.mmSchema) ? null : this.mmSchema.getDocumentID();
    }

    /**
     * 获取原始文档id
     *
     * @return 返回原始文档id
     */
    public String getOriginalDocumentID() {
        return Objects.isNull(this.mmSchema) ? null : this.mmSchema.getOriginalDocumentID();
    }

    /**
     * 获取实例id
     *
     * @return 返回实例id
     */
    public String getInstanceID() {
        return Objects.isNull(this.mmSchema) ? null : this.mmSchema.getInstanceID();
    }

    /**
     * 设置pdf版本
     *
     * @param version 版本
     */
    public void setPDFVersion(float version) {
        this.document.setVersion(version);
        if (Objects.isNull(this.adobeSchema)) {
            this.adobeSchema = this.xmpMetadata.createAndAddAdobePDFSchema();
        }
        this.adobeSchema.setProducer(String.valueOf(version));

    }

    /**
     * 设置关键字
     *
     * @param keywords 关键字
     */
    public void setKeywords(String... keywords) {
        Objects.requireNonNull(keywords, "the keywords can not be null");
        if (Objects.isNull(this.adobeSchema)) {
            this.adobeSchema = this.xmpMetadata.createAndAddAdobePDFSchema();
        }
        Optional.ofNullable(this.adobeSchema.getKeywordsProperty()).ifPresent(this.adobeSchema::removeProperty);

        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        Optional.ofNullable(this.dcSchema.getSubjectsProperty()).ifPresent(this.dcSchema::removeProperty);
        Arrays.stream(keywords).forEach(this.dcSchema::addSubject);
    }

    /**
     * 设置制作程序
     *
     * @param producer 制作程序
     */
    public void setProducer(String producer) {
        Objects.requireNonNull(producer, "the producer can not be null");
        if (Objects.isNull(this.adobeSchema)) {
            this.adobeSchema = this.xmpMetadata.createAndAddAdobePDFSchema();
        }
        this.adobeSchema.setProducer(producer);
    }

    /**
     * 设置创建日期
     *
     * @param date 日期
     */
    public void setCreateDate(Calendar date) {
        Objects.requireNonNull(date, "the date can not be null");
        if (Objects.isNull(this.basicSchema)) {
            this.basicSchema = this.xmpMetadata.createAndAddXMPBasicSchema();
        }
        this.basicSchema.setCreateDate(date);
    }

    /**
     * 设置修改日期
     *
     * @param date 日期
     */
    public void setModifyDate(Calendar date) {
        Objects.requireNonNull(date, "the date can not be null");
        if (Objects.isNull(this.basicSchema)) {
            this.basicSchema = this.xmpMetadata.createAndAddXMPBasicSchema();
        }
        this.basicSchema.setModifyDate(date);
    }

    /**
     * 设置创建工具
     *
     * @param creatorTool 创建工具
     */
    public void setCreatorTool(String creatorTool) {
        Objects.requireNonNull(creatorTool, "the creator tool can not be null");
        if (Objects.isNull(this.basicSchema)) {
            this.basicSchema = this.xmpMetadata.createAndAddXMPBasicSchema();
        }
        this.basicSchema.setCreatorTool(creatorTool);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        Objects.requireNonNull(title, "the title can not be null");
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        this.dcSchema.setTitle(title);
    }

    /**
     * 设置作者
     *
     * @param authors 作者
     */
    public void setAuthors(String... authors) {
        Objects.requireNonNull(authors, "the authors can not be null");
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        Optional.ofNullable(this.dcSchema.getCreatorsProperty()).ifPresent(this.dcSchema::removeProperty);
        Arrays.stream(authors).forEach(this.dcSchema::addCreator);
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        Objects.requireNonNull(description, "the description can not be null");
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        this.dcSchema.setDescription(description);
    }

    /**
     * 设置格式
     *
     * @param mimeType 格式类型
     */
    public void setFormat(String mimeType) {
        Objects.requireNonNull(mimeType, "the mime type can not be null");
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        this.dcSchema.setFormat(mimeType);
    }

    /**
     * 设置版权信息
     *
     * @param rights 版权信息
     */
    public void setRights(String rights) {
        Objects.requireNonNull(rights, "the rights can not be null");
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        this.dcSchema.addRights(null, rights);
    }

    /**
     * 设置版权状态
     *
     * @param marked 版权状态
     */
    public void setRightsMarked(Boolean marked) {
        Objects.requireNonNull(marked, "the rights marked not be null");
        if (Objects.isNull(this.rmSchema)) {
            this.rmSchema = this.xmpMetadata.createAndAddXMPRightsManagementSchema();
        }
        this.rmSchema.setMarked(marked);
    }

    /**
     * 设置版权信息url
     *
     * @param url 版权信息url
     */
    public void setRightsUrl(String url) {
        Objects.requireNonNull(url, "the rights url can not be null");
        if (Objects.isNull(this.rmSchema)) {
            this.rmSchema = this.xmpMetadata.createAndAddXMPRightsManagementSchema();
        }
        this.rmSchema.setWebStatement(url);
    }

    /**
     * 刷新元数据
     */
    @SneakyThrows
    public void flush() {
        // 处理adobe大纲
        this.processAdobeSchema();
        // 处理基础大纲
        this.processBasicSchema();
        // 处理dc大纲
        this.processDcSchema();
        // 定义输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            // 序列化xmp元数据
            new XmpSerializer().serialize(this.xmpMetadata, outputStream, true);
            // 导入xmp元数据
            this.metadata.importXMPMetadata(outputStream.toByteArray());
            // 设置元数据
            this.getDocument().getDocumentCatalog().setMetadata(this.metadata);
            // 设置是否刷新元数据
            this.document.setIsFlushMetadata(Boolean.FALSE);
        }
    }

    /**
     * 初始化元数据
     */
    @SneakyThrows
    protected void initMetadata() {
        this.metadata = Optional.ofNullable(this.getDocument().getDocumentCatalog().getMetadata()).orElse(new PDMetadata(this.document.getTarget()));
        try {
            this.xmpMetadata = new DomXmpParser().parse(this.metadata.exportXMPMetadata());
        }catch (Exception e) {
            this.xmpMetadata = XMPMetadata.createXMPMetadata();
        }
        this.adobeSchema = this.xmpMetadata.getAdobePDFSchema();
        this.basicSchema = this.xmpMetadata.getXMPBasicSchema();
        this.dcSchema = this.xmpMetadata.getDublinCoreSchema();
        this.rmSchema = this.xmpMetadata.getXMPRightsManagementSchema();
        this.mmSchema = this.xmpMetadata.getXMPMediaManagementSchema();
    }

    /**
     * 处理adobe大纲
     */
    protected void processAdobeSchema() {
        // 初始化adobe大纲
        if (Objects.isNull(this.adobeSchema)) {
            this.adobeSchema = this.xmpMetadata.createAndAddAdobePDFSchema();
        }
        // 初始化制作程序
        if (Objects.isNull(this.adobeSchema.getProducer())) {
            this.adobeSchema.setProducer(Constants.PRODUCER);
        }
        // 初始化pdf版本
        if (Objects.isNull(this.adobeSchema.getPDFVersion())) {
            this.adobeSchema.setPDFVersion(String.valueOf(this.document.getVersion()));
        }
    }

    /**
     * 处理基础大纲
     */
    protected void processBasicSchema() {
        // 初始化基础大纲
        if (Objects.isNull(this.basicSchema)) {
            this.basicSchema = this.xmpMetadata.createAndAddXMPBasicSchema();
        }
        // 初始化创建日期
        if (Objects.isNull(this.basicSchema.getCreateDate())) {
            this.basicSchema.setCreateDate(Calendar.getInstance());
        }
        // 初始化修改日期
        if (Objects.isNull(this.basicSchema.getModifyDate())) {
            this.basicSchema.setModifyDate(Calendar.getInstance());
        }
        // 初始化创建工具
        if (Objects.isNull(this.basicSchema.getCreatorTool())) {
            this.basicSchema.setCreatorTool(Constants.PRODUCER);
        }
    }

    /**
     * 处理dc大纲
     */
    protected void processDcSchema() {
        // 初始化dc大纲
        if (Objects.isNull(this.dcSchema)) {
            this.dcSchema = this.xmpMetadata.createAndAddDublinCoreSchema();
        }
        // 初始化格式
        if (Objects.isNull(this.dcSchema.getFormat())) {
            this.dcSchema.setFormat(Constants.MIME_TYPE);
        }
    }
}
