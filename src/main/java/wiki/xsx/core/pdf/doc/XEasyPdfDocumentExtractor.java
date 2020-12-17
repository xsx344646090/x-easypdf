package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pdf文档提取器
 * @author xsx
 * @date 2020/11/15
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
public class XEasyPdfDocumentExtractor {

    /**
     * pdfbox文档
     */
    private final PDDocument document;
    /**
     * pdf文档
     */
    private final XEasyPdfDocument pdfDocument;
    /**
     * 简单提取器
     */
    private final SimpleExtractor simpleExtractor;
    /**
     * 区域提取器
     */
    private final RegionExtractor regionExtractor;

    /**
     * 构造方法
     * @param pdfDocument pdf文档
     */
    @SneakyThrows
    XEasyPdfDocumentExtractor(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.getTarget();
        this.simpleExtractor = new SimpleExtractor(this.document);
        this.regionExtractor = new RegionExtractor();
    }

    /**
     * 添加区域
     * @param regionName 区域名称
     * @param rectangle 区域图形
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor addRegion(String regionName, Rectangle rectangle) {
        this.regionExtractor.addRegion(regionName, rectangle);
        return this;
    }

    /**
     * 根据区域提取文本
     * @param dataList 待接收文本字典列表(key=区域名称，value=提取文本)
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentExtractor extractByRegions(List<Map<String, String>> dataList, int ...pageIndex) throws IOException {
        // 如果页面索引有内容，则根据页面索引进行区域文本提取
        if (pageIndex!=null&&pageIndex.length>0) {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 添加数据
                this.addData(dataList, index);
            }
            // 如果页面索引没有内容，则提取全部页面中的区域文本
        }else {
            // 遍历文档页面
            for (int index = 0, count = this.document.getNumberOfPages() - 1; index < count; index++) {
                // 添加数据
                this.addData(dataList, index);
            }
        }
        return this;
    }

    /**
     * 提取文本(全部)
     * @param textList 文本列表
     * @param pageIndex 页面索引
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentExtractor extract(List<String> textList, int ...pageIndex) throws IOException {
        this.extract(textList, null, pageIndex);
        return this;
    }

    /**
     * 提取文本
     * @param textList 文本列表
     * @param regex 正则表达式
     * @param pageIndex 页面索引
     */
    public XEasyPdfDocumentExtractor extract(List<String> textList, String regex, int ...pageIndex) throws IOException {
        this.simpleExtractor.extract(textList, regex, pageIndex);
        return this;
    }

    /**
     * 提取表格文本(单行列)
     * @param textList 文本列表（第一层为行，第二层为列）
     * @param pageIndex 页面索引
     * @throws IOException IO异常
     */
    public XEasyPdfDocumentExtractor extractBySimpleTable(List<List<String>> textList, int pageIndex) throws IOException {
        List<String> sourceList = new ArrayList<>(1024);
        this.extract(sourceList, "(\\S[^\\n&]+)", pageIndex);
        for (String rowText : sourceList) {
            textList.add(Arrays.asList(rowText.split("\\s")));
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

    /**
     * 添加数据
     * @param dataList 待接收文本字典列表(key=区域名称，value=提取文本)
     * @param pageIndex 页面索引
     * @throws IOException IO异常
     */
    private void addData(List<Map<String, String>> dataList, int pageIndex) throws IOException {
        // 提取文本
        Map<String, String> data = this.regionExtractor.extract(this.document.getPage(pageIndex));
        // 如果文本字典不为空，则添加数据
        if (!data.isEmpty()) {
            // 添加数据
            dataList.add(data);
        }
    }

    /**
     * 简单提取器
     */
    private static class SimpleExtractor extends PDFTextStripper {

        /**
         * pdfbox文档
         */
        private final PDDocument document;

        /**
         * 有参构造
         * @param document pdfbox文档
         * @throws IOException IO异常
         */
        public SimpleExtractor(PDDocument document) throws IOException {
            this.document = document;
        }

        /**
         * 提取文本
         * @param textList 文本列表
         * @param regex 正则表达式
         * @param pageIndex 页面索引
         * @throws IOException IO异常
         */
        void extract(List<String> textList, String regex, int ...pageIndex) throws IOException {
            // 如果页面索引有内容，则根据页面索引提取文本
            if (pageIndex!=null&&pageIndex.length>0) {
                // 遍历页面索引
                for (int index : pageIndex) {
                    // 设置起始页面
                    this.setStartPage(index+1);
                    // 设置结束页面
                    this.setEndPage(index+1);
                    // 提取文本
                    this.extract(textList, regex);
                }
                // 如果页面索引没有内容，则提取全部文本
            }else {
                // 提取全部文本
                this.extract(textList, regex);
            }
        }


        /**
         * 提取文本
         * @param textList 文本列表
         * @param regex 正则表达式
         * @throws IOException IO异常
         */
        private void extract(List<String> textList, String regex) throws IOException {
            // 获取文本
            String text = this.getText(this.document);
            // 如果正则表达式有内容，则进行匹配
            if (regex!=null&&regex.trim().length()>0) {
                // 获取正则匹配器
                Matcher matcher = Pattern.compile(regex).matcher(text);
                // 循环匹配
                while (matcher.find()) {
                    // 添加文本列表
                    textList.add(matcher.group());
                }
                // 如果正则表达式没有内容，则直接添加到文本列表
            }else {
                // 添加文本列表
                textList.add(text);
            }
        }
    }

    /**
     * 区域提取器
     */
    private static class RegionExtractor extends PDFTextStripper {

        /**
         * 区域字符列表
         */
        private final Map<String, ArrayList<List<TextPosition>>> regionCharacterList = new HashMap<>();
        /**
         * 区域文本字典
         */
        private final Map<String, StringWriter> regionText = new HashMap<>(256);
        /**
         * 区域
         */
        private final Map<String, Rectangle> regionArea = new HashMap<>(32);

        /**
         * 无参构造
         * @throws IOException IO异常
         */
        RegionExtractor() throws IOException {
            // 设置排序
            super.setSortByPosition(true);
        }

        /**
         * 添加区域
         * @param regionName 区域名称
         * @param rectangle 区域图形
         */
        void addRegion(String regionName, Rectangle rectangle) {
            this.regionArea.put(regionName, rectangle);
        }

        /**
         * 提取文本
         * @param page pdfbox页面
         * @return 返回文本字典(key=区域名称，value=提取文本)
         * @throws IOException IO异常
         */
        Map<String, String> extract(PDPage page) throws IOException {
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
                if(page.hasContents()) {
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
}