package org.dromara.pdf.pdfbox.core.ext;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.Document;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 文档拆分器
 *
 * @author xsx
 * @date 2023/10/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class DocumentSplitter implements Closeable {

    /**
     * pdf文档
     */
    private Document document;
    /**
     * 拆分文档列表
     */
    private final List<List<Integer>> documentList = new ArrayList<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentSplitter(Document document) {
        this.document = document;
    }

    /**
     * 添加拆分文档
     *
     * @param pageIndexes 拆分页面索引
     * @return 返回文档拆分器
     */
    public DocumentSplitter addDocument(int... pageIndexes) {
        // 添加页面列表
        if (Objects.nonNull(pageIndexes)) {
            this.documentList.add(this.toIntegerList(pageIndexes));
        }
        // 返回文档拆分器
        return this;
    }

    /**
     * 拆分文档
     *
     * @param outputPath 输出路径（目录）
     * @return 返回文档拆分器
     */
    public DocumentSplitter split(String outputPath) {
        return this.split(outputPath, null);
    }


    /**
     * 拆分文档
     *
     * @param outputPath 输出路径（目录）
     * @param prefix     文档名称前缀
     * @return 返回文档拆分器
     */
    @SneakyThrows
    public DocumentSplitter split(String outputPath, String prefix) {
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (Objects.isNull(prefix)) {
            // 初始化文档名称前缀
            prefix = "x-easypdf";
        }
        // 文件名称构造器
        StringBuilder fileNameBuilder;
        // 如果拆分文档列表不为空，则使用拆分文档列表进行拆分，否则按单页面拆分
        if (!this.documentList.isEmpty()) {
            // 遍历拆分页面列表
            for (int i = 0, count = this.documentList.size(); i < count; i++) {
                // 新建文件名称构造器
                fileNameBuilder = new StringBuilder();
                // 构建文件名称
                fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(i + 1).append(".pdf");
                // 获取输出流
                try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(fileNameBuilder.toString()))))) {
                    // 拆分文档
                    this.split(outputStream, this.documentList.get(i));
                }
            }
        }
        //  按单页面拆分
        else {
            // 定义拆分文档列表索引
            int index = 1;
            // 获取pdfbox页面树
            PDPageTree pageTree = this.document.getTarget().getPages();
            // 遍历页面树
            for (PDPage sourcePage : pageTree) {
                // 创建任务
                PDDocument target = new PDDocument();
                // 添加页面
                PDPage importPage = target.importPage(sourcePage);
                // 添加页面资源
                importPage.setResources(sourcePage.getResources());
                // 新建文件名称构造器
                fileNameBuilder = new StringBuilder();
                // 构建文件名称
                fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(index).append(".pdf");
                // 获取输出流
                try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(fileNameBuilder.toString()))))) {
                    // 保存任务
                    this.saveTarget(target, outputStream);
                }
                // 拆分文档列表索引自增
                index++;
            }
        }
        // 返回文档拆分器
        return this;
    }

    /**
     * 拆分文档
     *
     * @param outputStream 输出流
     * @param pageIndexes  页面索引
     * @return 返回文档拆分器
     */
    public DocumentSplitter split(OutputStream outputStream, int... pageIndexes) {
        return this.split(outputStream, this.toIntegerList(pageIndexes));
    }

    /**
     * 拆分文档
     *
     * @param outputStream 输出流
     * @param pageIndexes  页面索引
     * @return 返回文档拆分器
     */
    @SneakyThrows
    public DocumentSplitter split(OutputStream outputStream, List<Integer> pageIndexes) {
        // 新建任务文档
        PDDocument target = new PDDocument();
        // 获取源文档页面树
        PDPageTree sourcePages = this.document.getTarget().getPages();
        // 遍历页面索引
        for (int index : pageIndexes) {
            // 获取源文档页面
            PDPage pdPage = sourcePages.get(index);
            // 任务文档添加页面
            PDPage importPage = target.importPage(pdPage);
            // 设置页面资源缓存
            importPage.setResources(pdPage.getResources());
        }
        // 保存任务
        this.saveTarget(target, outputStream);
        // 返回文档拆分器
        return this;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }

    /**
     * 保存任务
     *
     * @param target       任务文档
     * @param outputStream 输出流
     */
    @SneakyThrows
    private void saveTarget(PDDocument target, OutputStream outputStream) {
        // 设置文档版本
        target.setVersion(this.document.getVersion());
        // 设置文档信息
        target.setDocumentInformation(this.document.getInfo());
        // 设置元数据
        target.getDocumentCatalog().setMetadata(this.document.getMetadata());
        // 保存文档
        target.save(outputStream);
        // 关闭文档
        target.close();
    }

    /**
     * 转为整形列表
     *
     * @param array 数组
     * @return 返回列表
     */
    private List<Integer> toIntegerList(int[] array) {
        List<Integer> list = new ArrayList<>(array.length);
        Arrays.stream(array).forEach(list::add);
        return list;
    }
}
