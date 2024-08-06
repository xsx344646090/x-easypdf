package org.dromara.pdf.pdfbox.core.ext.comparator;

import lombok.SneakyThrows;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.TextCompareInfo;

import java.util.*;

/**
 * 文本比较器
 *
 * @author xsx
 * @date 2024/8/5
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
public class TextComparator extends AbstractComparator {
    
    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextComparator(Document document) {
        super(document);
        
    }
    
    /**
     * 比较
     *
     * @param other 文档
     */
    public Map<Integer, List<TextCompareInfo>> compareTo(Document other, int... pageIndexes) {
        // 定义结果
        Map<Integer, List<TextCompareInfo>> result = new HashMap<>(this.document.getTotalPageNumber());
        // 判断是否指定页码
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页码
            for (int pageIndex : pageIndexes) {
                // 添加比较结果
                result.put(pageIndex, this.compareTexts(other, pageIndex));
            }
        } else {
            // 遍历所有页码
            for (int pageIndex = 0; pageIndex < this.document.getTotalPageNumber(); pageIndex++) {
                // 添加比较结果
                result.put(pageIndex, this.compareTexts(other, pageIndex));
            }
        }
        // 返回结果
        return result;
    }
    
    /**
     * 比较文本
     *
     * @param other     文档
     * @param pageIndex 页面索引
     * @return 返回比较结果
     */
    protected List<TextCompareInfo> compareTexts(Document other, int pageIndex) {
        // 定义结果
        List<TextCompareInfo> results = new ArrayList<>(64);
        // 获取当前文档的文本
        List<String> list = this.processPageText(this.document, pageIndex);
        // 获取比较文档的文本
        List<String> otherList = this.processPageText(other, pageIndex);
        // 遍历文本
        for (int i = 0; i < list.size(); i++) {
            // 获取文本
            String text = list.get(i);
            // 判断是否有比较文本
            if (Objects.nonNull(otherList) && otherList.size() > i) {
                // 获取比较文本
                String otherText = otherList.get(i);
                // 判断文本是否相同
                if (!Objects.equals(text, otherText)) {
                    // 添加比较结果
                    results.add(TextCompareInfo.builder().pageIndex(pageIndex).lineNumber(i).content(text).compareContent(otherText).build());
                }
            } else {
                // 添加比较结果
                results.add(TextCompareInfo.builder().pageIndex(pageIndex).lineNumber(i).content(text).build());
            }
        }
        // 返回结果
        return results;
    }
    
    /**
     * 处理页面文本
     *
     * @param document  文档
     * @param pageIndex 页面索引
     * @return 返回文本列表
     */
    @SneakyThrows
    protected List<String> processPageText(Document document, int pageIndex) {
        // 如果页码不存在，则返回null
        if (Objects.isNull(document.getPage(pageIndex))) {
            return null;
        }
        // 创建文本剥离器
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按位置排序
        stripper.setSortByPosition(true);
        // 设置起始页面
        stripper.setStartPage(pageIndex + 1);
        // 设置结束页面
        stripper.setEndPage(pageIndex + 1);
        // 返回文本列表
        return Arrays.asList(stripper.getText(document.getTarget()).split(System.lineSeparator()));
    }
}
