package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.*;

/**
 * 区域文本提取器
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
public class RegionTextExtractor extends PDFTextStripper implements Closeable {

    /**
     * pdfbox文档
     */
    private PDDocument document;
    /**
     * 区域字符列表
     */
    private final Map<String, ArrayList<List<TextPosition>>> regionCharacterList = new HashMap<>(32);
    /**
     * 区域文本字典
     */
    private final Map<String, StringWriter> regionText = new HashMap<>(32);
    /**
     * 区域
     */
    private final Map<String, Rectangle> regionArea;

    /**
     * 有参构造
     *
     * @param document   文档
     * @param regionArea 区域
     */
    public RegionTextExtractor(Document document, Map<String, Rectangle> regionArea) {
        // 设置排序
        super.setSortByPosition(true);
        // 初始化文档
        this.document = document.getTarget();
        // 初始化区域
        this.regionArea = regionArea;
    }

    /**
     * 提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param pageIndexes   页面索引
     * @return 返回文本字典(key = 页面索引 ， value = 提取文本)
     */
    public Map<Integer, Map<String, String>> extract(String wordSeparator, int... pageIndexes) {
        // 定义文本字典
        Map<Integer, Map<String, String>> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.document.getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            Arrays.stream(pageIndexes).sorted().forEach(
                    // 添加数据
                    index -> data.put(index, this.extract(wordSeparator, pageTree.get(index)))
            );
        } else {
            // 定义索引
            int index = 0;
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加数据
                data.put(index, this.extract(wordSeparator, page));
                // 索引自增
                index++;
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
     * @param page pdfbox页面
     * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
     */
    protected Map<String, String> extract(PDPage page) {
        return this.extract(" ", page);
    }

    /**
     * 提取文本
     *
     * @param wordSeparator 单词分隔符
     * @param page          pdfbox页面
     * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
     */
    @SneakyThrows
    protected Map<String, String> extract(String wordSeparator, PDPage page) {
        // 定义文本字典
        Map<String, String> data;
        // 如果区域为空，则初始化文本字典为空字典
        if (this.regionArea.isEmpty()) {
            // 初始化文本字典为空字典
            data = new HashMap<>(0);
            // 如果区域不为空，则初始化文本字典并填充
        } else {
            // 获取区域名称列表
            Set<String> keySet = this.regionArea.keySet();
            // 初始化文本字典
            data = new HashMap<>(keySet.size());
            // 遍历区域名称列表
            for (String region : keySet) {
                // 设置起始页面
                this.setStartPage(this.getCurrentPageNo());
                // 设置结束页面
                this.setEndPage(this.getCurrentPageNo());
                // 设置单词分隔符
                this.setWordSeparator(wordSeparator);
                // 定义区域字符列表
                ArrayList<List<TextPosition>> regionCharactersByArticle = new ArrayList<>(256);
                // 添加空列表
                regionCharactersByArticle.add(new ArrayList<>(256));
                // 设置区域字符列表
                this.regionCharacterList.put(region, regionCharactersByArticle);
                // 设置区域文本
                this.regionText.put(region, new StringWriter());
            }
            // 如果页面有内容，则处理页面
            if (page.hasContents()) {
                // 处理页面
                this.processPage(page);
            }
            // 遍历区域名称列表
            for (String region : keySet) {
                // 设置文本字典
                data.put(region, this.regionText.get(region).toString());
            }
        }
        return data;
    }

    /**
     * 处理文本定位
     *
     * @param text 文本
     */
    @Override
    protected void processTextPosition(TextPosition text) {
        // 获取区域列表
        Set<Map.Entry<String, Rectangle>> entrySet = this.regionArea.entrySet();
        // 遍历区域列表
        for (Map.Entry<String, Rectangle> regionAreaEntry : entrySet) {
            // 获取区域
            Rectangle2D rect = regionAreaEntry.getValue();
            // 如果当前区域坐标包含文本坐标，则进行提取文本
            if (rect.contains(text.getX(), text.getY())) {
                // 初始化字符列表
                this.charactersByArticle = this.regionCharacterList.get(regionAreaEntry.getKey());
                // 调用pdfbox提取器处理文本定位
                super.processTextPosition(text);
            }
        }
    }

    /**
     * 写入页面
     *
     * @throws IOException IO异常
     */
    @Override
    protected void writePage() throws IOException {
        // 获取区域名称列表
        Set<String> keySet = this.regionArea.keySet();
        // 遍历区域名称列表
        for (String region : keySet) {
            // 初始化字符列表
            this.charactersByArticle = this.regionCharacterList.get(region);
            // 初始化输出
            this.output = this.regionText.get(region);
            // 调用pdfbox提取器写入页面
            super.writePage();
        }
    }
}
