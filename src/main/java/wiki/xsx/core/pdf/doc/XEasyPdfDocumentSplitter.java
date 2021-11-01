package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import wiki.xsx.core.pdf.util.XEasyPdfConvertUtil;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf文档拆分器
 * @author xsx
 * @date 2020/5/24
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
public class XEasyPdfDocumentSplitter {

    /**
     * pdfbox文档
     */
    private final PDDocument document;
    /**
     * pdf文档
     */
    private final XEasyPdfDocument pdfDocument;
    /**
     * 拆分文档列表
     */
    private final List<List<Integer>> documentList = new ArrayList<>(10);

    /**
     * 构造方法私有化
     */
    @SneakyThrows
    XEasyPdfDocumentSplitter(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.getTarget();
    }

    /**
     * 添加拆分文档
     * @param pageIndex 拆分页面索引
     * @return 返回pdf文档拆分器
     */
    public XEasyPdfDocumentSplitter addDocument(int ...pageIndex) {
        this.documentList.add(XEasyPdfConvertUtil.toInteger(pageIndex));
        return this;
    }

    /**
     * 拆分文档
     * @param outputPath 输出路径（目录）
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentSplitter split(String outputPath) throws IOException {
        return this.split(outputPath, null);
    }


    /**
     * 拆分文档
     * @param outputPath 输出路径（目录）
     * @param prefix 文档名称前缀
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentSplitter split(String outputPath, String prefix) throws IOException {
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (prefix==null) {
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
                try(OutputStream outputStream = Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(fileNameBuilder.toString())))) {
                    // 拆分文档
                    this.split(outputStream, XEasyPdfConvertUtil.toInt(this.documentList.get(i)));
                }
            }
            //  按单页面拆分
        }else {
            // 拆分文档
            List<PDDocument> documents = new Splitter().split(this.document);
            // 定义拆分文档列表索引
            int index = 1;
            // 遍历拆分文档列表
            for (PDDocument target : documents) {
                // 新建文件名称构造器
                fileNameBuilder = new StringBuilder();
                // 构建文件名称
                fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(index).append(".pdf");
                // 获取输出流
                try(OutputStream outputStream = Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(fileNameBuilder.toString())))) {
                    // 设置文档信息及保护策略
                    this.pdfDocument.setInfoAndPolicyAndBookmark(target);
                    // 保存文档
                    target.save(outputStream);
                    // 关闭文档
                    target.close();
                }
                // 拆分文档列表索引自增
                index++;
            }
        }
        return this;
    }

    /**
     * 拆分文档
     * @param outputStream 输出流
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentSplitter split(OutputStream outputStream, int ...pageIndex) throws IOException {
        // 新建任务文档
        try(PDDocument target = new PDDocument()) {
            // 获取源文档页面树
            PDPageTree sourcePages = this.document.getPages();
            // 遍历页面索引
            for (int index : pageIndex) {
                // 获取源文档页面
                PDPage pdPage = sourcePages.get(index);
                // 任务文档添加页面
                PDPage importPage = target.importPage(pdPage);
                // 设置页面资源缓存
                importPage.setResources(pdPage.getResources());
            }
            // 设置文档信息及保护策略
            this.pdfDocument.setInfoAndPolicyAndBookmark(target);
            // 保存任务文档
            target.save(outputStream);
            // 关闭文档
        }
        return this;
    }

    /**
     * 完成操作
     * @return 返回pdf文档
     */
    public XEasyPdfDocument finish() {
        return this.pdfDocument;
    }
}
