package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.EmbeddedFileInfo;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * 附件处理器
 *
 * @author xsx
 * @date 2024/1/17
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
@Getter
@EqualsAndHashCode(callSuper = true)
public class AttachmentProcessor extends AbstractProcessor {

    /**
     * 文件字典
     */
    protected Map<String, EmbeddedFileInfo> fileMap = new HashMap<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AttachmentProcessor(Document document) {
        super(document);
        this.initFiles();
    }

    /**
     * 添加或替换文件
     *
     * @param files 文件
     */
    public void addOrSet(EmbeddedFileInfo... files) {
        // 参数校验
        Objects.requireNonNull(files, "the file can not be null");
        // 添加文件
        for (EmbeddedFileInfo file : files) {
            this.fileMap.put(file.getName(), file);
        }
    }

    /**
     * 移除文件
     *
     * @param names 文件名
     */
    public void remove(String... names) {
        // 移除全部
        if (Objects.isNull(names)) {
            // 重置文件
            this.fileMap = new HashMap<>(0);
        } else {
            // 遍历文件名
            for (String name : names) {
                try {
                    // 移除文件
                    this.fileMap.remove(name);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the name['" + name + "'] is invalid, will be ignored");
                }
            }
        }
    }

    /**
     * 构建嵌入文件
     *
     * @param data     数据
     * @param mimeType 邮件扩展类型
     * @return 返回嵌入文件
     */
    @SneakyThrows
    public PDEmbeddedFile buildEmbeddedFile(byte[] data, String mimeType) {
        // 参数校验
        Objects.requireNonNull(data, "the data can not be null");
        // 创建嵌入文件
        PDEmbeddedFile file = new PDEmbeddedFile(this.getDocument(), new ByteArrayInputStream(data));
        // 设置邮件扩展类型
        file.setSubtype(mimeType);
        // 设置文件大小
        file.setSize(data.length);
        // 设置创建日期
        file.setCreationDate(Calendar.getInstance());
        // 返回嵌入文件
        return file;
    }

    /**
     * 刷新文件
     */
    public void flush() {
        this.getDocument().getDocumentCatalog().setNames(this.fileMap.isEmpty() ? null : this.createNameDictionary());
    }

    /**
     * 初始化文件
     */
    protected void initFiles() {
        // 获取当前PDF文档的名称字典
        PDDocumentNameDictionary dictionary = this.getDocument().getDocumentCatalog().getNames();
        // 检查名称字典是否不为空
        if (Objects.nonNull(dictionary)) {
            // 从名称字典中获取嵌入文件名称树节点
            PDEmbeddedFilesNameTreeNode tree = dictionary.getEmbeddedFiles();
            // 检查嵌入文件名称树节点是否不为空
            if (Objects.nonNull(tree)) {
                // 提取嵌入的文件
                this.extractFiles(tree);
            }
        }
    }

    /**
     * 提取文件
     *
     * @param tree 文件树
     */
    @SneakyThrows
    protected void extractFiles(PDNameTreeNode<PDComplexFileSpecification> tree) {
        // 获取树结构中的名称映射
        Map<String, PDComplexFileSpecification> names = tree.getNames();
        // 检查名称映射是否不为空
        if (Objects.nonNull(names)) {
            // 获取名称映射的条目集合
            Set<Map.Entry<String, PDComplexFileSpecification>> entrySet = names.entrySet();
            // 遍历条目集合
            for (Map.Entry<String, PDComplexFileSpecification> entry : entrySet) {
                // 获取当前条目的值（文件规范）
                PDComplexFileSpecification specification = entry.getValue();
                // 检查文件规范是否不为空
                if (Objects.nonNull(specification)) {
                    // 将文件信息添加到文件字典中
                    this.fileMap.put(specification.getFilename(), new EmbeddedFileInfo(specification.getFilename(), specification.getFileDescription(), specification.getEmbeddedFile()));
                }
            }
        } else {
            // 如果名称映射为空，则获取子节点列表
            List<PDNameTreeNode<PDComplexFileSpecification>> kids = tree.getKids();
            // 检查子节点列表是否为空
            if (Objects.isNull(kids)) {
                // 如果子节点列表为空，则直接返回
                return;
            }
            // 遍历子节点列表
            for (PDNameTreeNode<PDComplexFileSpecification> node : kids) {
                // 递归提取
                this.extractFiles(node);
            }
        }
    }

    /**
     * 创建名称字典
     *
     * @return 返回名称字典
     */
    protected PDDocumentNameDictionary createNameDictionary() {
        // 创建名称字典
        PDDocumentNameDictionary names = new PDDocumentNameDictionary(this.getDocument().getDocumentCatalog());
        // 创建名称树
        PDEmbeddedFilesNameTreeNode tree = new PDEmbeddedFilesNameTreeNode();
        // 创建字典
        Map<String, PDComplexFileSpecification> map = new HashMap<>(this.fileMap.size());
        // 获取文件集合
        Collection<EmbeddedFileInfo> infos = this.fileMap.values();
        // 遍历文件
        for (EmbeddedFileInfo info : infos) {
            // 添加文本描述
            map.put(info.getName(), this.createSpecification(info));
        }
        // 添加字典
        tree.setNames(map);
        // 添加树
        names.setEmbeddedFiles(tree);
        // 返回名称字典
        return names;
    }

    /**
     * 创建文件描述
     *
     * @param info 嵌入文件信息
     * @return 返回文件描述
     */
    protected PDComplexFileSpecification createSpecification(EmbeddedFileInfo info) {
        // 创建文件描述
        PDComplexFileSpecification specification = new PDComplexFileSpecification();
        // 设置文件名
        specification.setFile(info.getName());
        // 设置文件的Unicode名称
        specification.setFileUnicode(info.getName());
        // 设置文件描述
        specification.setFileDescription(info.getDescription());
        // 设置嵌入的文件
        specification.setEmbeddedFile(info.getEmbeddedFile());
        // 设置嵌入文件的Unicode
        specification.setEmbeddedFileUnicode(info.getEmbeddedFile());
        // 返回文件描述
        return specification;
    }
}
