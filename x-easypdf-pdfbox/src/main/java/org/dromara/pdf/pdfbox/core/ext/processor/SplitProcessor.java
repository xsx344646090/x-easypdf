package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 拆分处理器
 *
 * @author xsx
 * @date 2023/10/19
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
public class SplitProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public SplitProcessor(Document document) {
        super(document);
    }

    /**
     * 拆分文档
     *
     * @param outputPath  输出路径
     * @param pageIndexes 页面索引
     */
    @SneakyThrows
    public void split(String outputPath, int... pageIndexes) {
        // 检查参数
        Objects.requireNonNull(outputPath, "the output path can not be null");
        // 检查参数
        Objects.requireNonNull(pageIndexes, "the page indexes can not be null");
        // 获取输出流
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(outputPath))))) {
            // 拆分文档
            this.split(outputStream, pageIndexes);
        }
    }

    /**
     * 拆分文档
     *
     * @param outputStream 输出流
     * @param pageIndexes  页面索引
     */
    public void split(OutputStream outputStream, int... pageIndexes) {
        // 检查参数
        Objects.requireNonNull(outputStream, "the output stream can not be null");
        // 检查参数
        Objects.requireNonNull(pageIndexes, "the page indexes can not be null");
        // 拆分文档
        this.split(outputStream, this.toIntegerList(pageIndexes));
    }

    /**
     * 拆分文档
     *
     * @param outputStream 输出流
     * @param pageIndexes  页面索引
     */
    @SneakyThrows
    public void split(OutputStream outputStream, List<Integer> pageIndexes) {
        // 检查参数
        Objects.requireNonNull(outputStream, "the output stream can not be null");
        // 检查参数
        Objects.requireNonNull(pageIndexes, "the page indexes can not be null");
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
    }

    /**
     * 拆分全部（单页拆分为独立文档）
     *
     * @param directory 目录
     */
    @SneakyThrows
    public void splitAll(String directory) {
        // 检查参数
        Objects.requireNonNull(directory, "the directory can not be null");
        // 文件名称构造器
        StringBuilder fileNameBuilder;
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
            fileNameBuilder.append(directory).append(File.separator).append("x-easypdf-split").append(index).append(".pdf");
            // 获取输出流
            try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(fileNameBuilder.toString()))))) {
                // 保存任务
                this.saveTarget(target, outputStream);
            }
            // 拆分文档列表索引自增
            index++;
        }
    }

    /**
     * 保存任务
     *
     * @param target       任务文档
     * @param outputStream 输出流
     */
    @SneakyThrows
    protected void saveTarget(PDDocument target, OutputStream outputStream) {
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
    protected List<Integer> toIntegerList(int[] array) {
        // 定义列表
        List<Integer> list = new ArrayList<>(array.length);
        // 添加数据
        Arrays.stream(array).forEach(list::add);
        // 返回列表
        return list;
    }
}
