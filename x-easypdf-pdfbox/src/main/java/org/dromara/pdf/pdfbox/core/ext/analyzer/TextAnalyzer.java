package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.SneakyThrows;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 文本分析器
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
public class TextAnalyzer extends AbstractTextAnalyzer {

    /**
     * 是否打印日志
     */
    protected boolean isPrint;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextAnalyzer(Document document) {
        super(document);
        this.isPrint = true;
    }

    /**
     * 有参构造
     *
     * @param document 文档
     * @param isPrint  是否打印日志
     */
    public TextAnalyzer(Document document, boolean isPrint) {
        super(document);
        this.isPrint = isPrint;
    }

    /**
     * 处理文本
     *
     * @param pageIndex 页面索引
     */
    @SneakyThrows
    @Override
    public void processText(int pageIndex) {
        // 创建文本剥离器
        DefaultTextStripper textStripper = new DefaultTextStripper(pageIndex, this.log, this.isPrint);
        // 创建写入器
        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new ByteArrayOutputStream()))) {
            // 写入文本
            textStripper.writeText(this.getDocument(), writer);
        }
        // 重置文本信息列表
        this.infoSet.addAll(textStripper.getInfoSet());
    }

    /**
     * 获取字符数
     *
     * @param pageIndex 页面索引
     * @return 返回字符数
     */
    @SneakyThrows
    @Override
    public int getCharacterCount(int pageIndex) {
        // 定义正则
        final String regex = "\r|\n|\t|\b|\\s";
        // 定义替换文本
        final String replacement = "";
        // 定义页面索引
        int index = pageIndex + 1;
        // 创建文本剥离器
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置起始页码
        stripper.setStartPage(index);
        // 设置结束页码
        stripper.setEndPage(index);
        // 返回字符数
        return stripper.getText(this.getDocument()).replaceAll(regex, replacement).length();
    }
}
