package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 合并处理器
 *
 * @author xsx
 * @date 2023/10/20
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
@EqualsAndHashCode(callSuper = true)
public class MergeProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public MergeProcessor(Document document) {
        super(document);
    }

    /**
     * 合并文档
     *
     * @param documents 文档列表
     */
    public void merge(List<Document> documents) {
        this.merge(documents.toArray(new Document[0]));
    }

    /**
     * 合并文档
     *
     * @param documents 文档列表
     */
    public void merge(Document... documents) {
        // 检查参数
        Objects.requireNonNull(documents, "the documents can not be null");
        // 遍历文档列表
        for (Document document : documents) {
            // 导入页面
            this.importPage(this.getDocument(), document.getTarget());
        }
    }

    /**
     * 合并文档
     *
     * @param files 文件列表
     */
    @SneakyThrows
    public void merge(File... files) {
        // 检查参数
        Objects.requireNonNull(files, "the documents can not be null");
        // 定义临时文件
        File temp = new File(Constants.TEMP_FILE_PATH, UUID.randomUUID() + ".pdf");
        // 定义临时文档列表
        List<Document> tempDocuments = new ArrayList<>(files.length);
        // 遍历文件列表
        for (File file : files) {
            // 加载文档
            Document document = PdfHandler.getDocumentHandler().load(file);
            // 获取pdfbox页面树
            PDPageTree pageTree = document.getTarget().getPages();
            // 遍历页面树
            for (PDPage sourcePage : pageTree) {
                // 添加页面
                PDPage importPage = this.getDocument().importPage(sourcePage);
                // 添加页面资源
                importPage.setResources(sourcePage.getResources());
            }
            // 添加文档
            tempDocuments.add(document);
        }
        // 保存临时文件
        this.getDocument().save(temp);
        // 关闭文档
        this.document.close();
        // 重置文档
        this.document = PdfHandler.getDocumentHandler().load(temp);
        // 关闭临时文档
        tempDocuments.forEach(Document::close);
        // 删除临时文件
        temp.deleteOnExit();
    }

    /**
     * 刷新文档
     *
     * @return 返回文档
     */
    public Document flush() {
        return this.document;
    }

    /**
     * 导入页面
     *
     * @param target 任务文档
     * @param source 源文档
     */
    @SneakyThrows
    protected void importPage(PDDocument target, PDDocument source) {
        // 获取pdfbox页面树
        PDPageTree pageTree = source.getPages();
        // 遍历页面树
        for (PDPage sourcePage : pageTree) {
            // 添加页面
            PDPage importPage = target.importPage(sourcePage);
            // 添加页面资源
            importPage.setResources(sourcePage.getResources());
            // 添加列表
            this.document.getPages().add(new Page(this.document, sourcePage));
        }
    }
}
