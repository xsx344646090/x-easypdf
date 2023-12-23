package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.Closeable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单文本提取器
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
public class SimpleTextExtractor extends PDFTextStripper implements Closeable {

    /**
     * pdfbox文档
     */
    private PDDocument document;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public SimpleTextExtractor(Document document) {
        this.document = document.getTarget();
    }

    /**
     * 提取文本
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取文本）
     */
    public Map<Integer, List<String>> extract(String regex, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, List<String>> data = new HashMap<>(32);
        // 根据页面索引提取文本
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 设置起始页面
                this.setStartPage(index + 1);
                // 设置结束页面
                this.setEndPage(index + 1);
                // 提取文本
                data.put(index, this.extract(regex));
            }
        } else {
            // 循环提取页面文本
            for (int index = 0; index < this.document.getNumberOfPages(); index++) {
                // 设置起始页面
                this.setStartPage(index + 1);
                // 设置结束页面
                this.setEndPage(index + 1);
                // 提取全部文本
                data.put(index, this.extract(regex));
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 提取文本（表单）
     *
     * @return 返回文本字典（key = 字段名称，value = 提取文本）
     */
    public Map<String, String> extractForForm() {
        // 定义文本字典
        Map<String, String> data = new HashMap<>(32);
        // 获取pdfBox表单
        PDAcroForm form = this.document.getDocumentCatalog().getAcroForm();
        // 如果表单不为空，则提取表单内容
        if (Objects.nonNull(form)) {
            // 获取表单字段列表
            List<PDField> fields = form.getFields();
            // 遍历表单字段列表
            for (PDField field : fields) {
                // 提取表单内容
                data.put(field.getFullyQualifiedName(), field.getValueAsString());
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }

    /**
     * 提取文本
     *
     * @param regex 正则表达式
     * @return 返回文本列表
     */
    @SneakyThrows
    protected List<String> extract(String regex) {
        // 定义文本列表
        List<String> list = new ArrayList<>(32);
        // 获取文本
        String text = this.getText(this.document);
        // 如果正则表达式有内容，则进行匹配
        if (Objects.nonNull(regex) && !regex.trim().isEmpty()) {
            // 获取正则匹配器
            Matcher matcher = Pattern.compile(regex).matcher(text);
            // 循环匹配
            while (matcher.find()) {
                // 添加文本
                list.add(matcher.group());
            }
        } else {
            // 添加文本
            list.add(text);
        }
        // 返回文本列表
        return list;
    }
}
