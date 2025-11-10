package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 抽象评论提取器
 *
 * @author xsx
 * @date 2023/10/24
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
public abstract class AbstractCommentExtractor extends AbstractExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractCommentExtractor(Document document) {
        super(document);
    }

    /**
     * 提取评论
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    public abstract Map<Integer, List<String>> extract(String regex, int... pageIndexes);

    /**
     * 处理评论
     *
     * @param regex       正则表达式
     * @param pageIndexes 页面索引
     * @return 返回文本字典 <p>key = 页面索引，value = 提取文本</p>
     */
    protected Map<Integer, List<String>> processComment(String regex, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, List<String>> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 根据页面索引提取文本
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                // 提取文本
                data.put(index, this.processComment(regex, pageTree.get(index)));
            }
        } else {
            // 循环提取页面文本
            for (int index = 0; index < this.getDocument().getNumberOfPages(); index++) {
                // 提取文本
                data.put(index, this.processComment(regex, pageTree.get(index)));
            }
        }
        // 返回文本字典
        return data;
    }

    /**
     * 处理评论
     *
     * @param regex 正则表达式
     * @param page  页面
     * @return 返回文本列表
     */
    @SneakyThrows
    protected List<String> processComment(String regex, PDPage page) {
        // 定义文本列表
        List<String> list = new ArrayList<>(32);
        // 获取页面注解
        List<PDAnnotation> pdAnnotations = page.getAnnotations();
        // 定义评论
        String comment;
        // 遍历注解
        for (PDAnnotation pa : pdAnnotations) {
            // 如果为文本注解，则获取评论
            if (pa instanceof PDAnnotationText || pa instanceof PDAnnotationFreeText) {
                // 获取评论
                comment = pa.getContents();
                // 如果正则表达式有内容，则进行匹配
                if (Objects.nonNull(regex) && !regex.trim().isEmpty()) {
                    // 获取正则匹配器
                    Matcher matcher = Pattern.compile(regex).matcher(comment);
                    // 循环匹配
                    while (matcher.find()) {
                        // 添加文本
                        list.add(matcher.group());
                    }
                } else {
                    // 添加文本
                    list.add(comment);
                }
            }
        }
        // 返回文本列表
        return list;
    }
}
