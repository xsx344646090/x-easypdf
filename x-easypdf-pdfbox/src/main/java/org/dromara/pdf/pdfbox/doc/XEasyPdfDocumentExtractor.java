package org.dromara.pdf.pdfbox.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pdf文档提取器
 *
 * @author xsx
 * @date 2020/11/15
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfDocumentExtractor implements Serializable {

    private static final long serialVersionUID = 8464226596006053066L;

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
     * 表格正则（单行单列）
     */
    private static final Pattern TABLE_PATTERN = Pattern.compile("(\\S[^\\n\\r]+)");

    /**
     * 构造方法
     *
     * @param pdfDocument pdf文档
     */
    @SneakyThrows
    XEasyPdfDocumentExtractor(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.build(true);
        this.simpleExtractor = new SimpleExtractor(this.document);
        this.regionExtractor = new RegionExtractor();
    }

    /**
     * 添加区域
     *
     * @param regionName 区域名称
     * @param rectangle  区域图形
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor addRegion(String regionName, Rectangle rectangle) {
        this.regionExtractor.addRegion(regionName, rectangle);
        return this;
    }

    /**
     * 清理区域
     *
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor clearRegion() {
        this.regionExtractor.clearRegion();
        return this;
    }

    /**
     * 提取文本
     *
     * @param textList  待接收文本列表
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractText(List<String> textList, int... pageIndex) {
        this.extractText(textList, null, pageIndex);
        return this;
    }

    /**
     * 提取文本
     *
     * @param textList  待接收文本列表
     * @param regex     正则表达式
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractText(List<String> textList, String regex, int... pageIndex) {
        this.simpleExtractor.extract(textList, regex, pageIndex);
        return this;
    }

    /**
     * 根据区域提取文本
     *
     * @param dataList  待接收文本字典列表(key=区域名称，value=提取文本)
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractTextByRegions(List<Map<String, String>> dataList, int... pageIndex) {
        // 如果页面索引有内容，则根据页面索引进行区域文本提取
        if (pageIndex != null && pageIndex.length > 0) {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 添加数据
                this.addText(dataList, index);
            }
        }
        // 否则页面索引没有内容，则提取全部页面中的区域文本
        else {
            // 遍历文档页面
            for (int index = 0, count = this.document.getNumberOfPages() - 1; index < count; index++) {
                // 添加数据
                this.addText(dataList, index);
            }
        }
        return this;
    }

    /**
     * 提取表格文本(单行单列)
     *
     * @param textList  待接收文本列表（第一层为行，第二层为列）
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractTextForSimpleTable(List<List<String>> textList, int pageIndex) {
        // 获取给定页面索引的页面尺寸
        PDRectangle rectangle = this.document.getPage(pageIndex).getMediaBox();
        // 获取最大尺寸
        int maxSize = Math.max((int) rectangle.getWidth() + 1, (int) rectangle.getHeight() + 1);
        // 提取区域表格文本(单行单列)
        this.extractTextByRegionsForSimpleTable(textList, new Rectangle(maxSize, maxSize), pageIndex);
        return this;
    }

    /**
     * 提取区域表格文本(单行单列)
     *
     * @param textList  待接收文本列表（第一层为行，第二层为列）
     * @param rectangle 区域图形
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    @SneakyThrows
    public XEasyPdfDocumentExtractor extractTextByRegionsForSimpleTable(List<List<String>> textList, Rectangle rectangle, int pageIndex) {
        // 定义区域key
        final String key = "table";
        // 定义分词
        final String wordSeparator = "X-EasyPdf-Separator";
        // 创建区域提取器
        RegionExtractor regionExtractor = new RegionExtractor();
        // 添加区域
        regionExtractor.addRegion(key, rectangle);
        // 提取文本
        String text = regionExtractor.extract(this.document.getPage(pageIndex), wordSeparator).get(key);
        // 如果文本有内容，则进行文本拆分
        if (text != null && text.length() > 0) {
            // 定义源文本列表
            List<String> sourceList = new ArrayList<>(textList.size());
            // 获取正则匹配器
            Matcher matcher = TABLE_PATTERN.matcher(text);
            // 循环匹配
            while (matcher.find()) {
                // 添加文本列表
                sourceList.add(matcher.group());
            }
            // 遍历源文本列表
            for (String rowText : sourceList) {
                // 添加到待接收文本列表
                textList.add(Arrays.asList(rowText.split(wordSeparator)));
            }
        }
        return this;
    }

    /**
     * 提取图片(全部)
     *
     * @param imageList 待接收图片列表
     * @return 返回pdf文档提取器
     */
    @SneakyThrows
    public XEasyPdfDocumentExtractor extractImage(List<BufferedImage> imageList) {
        // 获取pdfbox文档页面树
        PDPageTree pages = this.document.getPages();
        // 遍历页面树
        for (PDPage page : pages) {
            // 添加图片
            this.addImage(imageList, page.getResources());
        }
        return this;
    }

    /**
     * 提取图片
     *
     * @param imageList 待接收图片列表
     * @param pageIndex 页面索引
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractImage(List<BufferedImage> imageList, int... pageIndex) {
        // 如果页面索引不为空，则添加图片
        if (pageIndex != null && pageIndex.length > 0) {
            // 获取pdfbox文档页面树
            PDPageTree pages = this.document.getPages();
            // 遍历页面索引
            for (int index : pageIndex) {
                // 如果页面索引小于0，则跳过
                if (index < 0) {
                    // 跳过该页面索引
                    continue;
                }
                // 添加图片
                this.addImage(imageList, pages.get(index).getResources());
            }
        }
        return this;
    }

    /**
     * 提取表单
     *
     * @param formMap 待接收表单字典
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractForm(Map<String, String> formMap) {
        // 获取pdfBox表单
        PDAcroForm form = this.document.getDocumentCatalog().getAcroForm();
        // 如果表单不为空，则提取表单内容
        if (form != null) {
            // 获取表单字段列表
            List<PDField> fields = form.getFields();
            // 遍历表单字段列表
            for (PDField field : fields) {
                // 提取表单内容
                formMap.put(field.getFullyQualifiedName(), field.getValueAsString());
            }
        }
        return this;
    }

    /**
     * 完成操作
     *
     * @return 返回pdf文档
     */
    public XEasyPdfDocument finish() {
        return this.pdfDocument.reset();
    }

    /**
     * 添加文本
     *
     * @param dataList  待接收文本字典列表(key=区域名称，value=提取文本)
     * @param pageIndex 页面索引
     */
    private void addText(List<Map<String, String>> dataList, int pageIndex) {
        // 提取文本
        Map<String, String> data = this.regionExtractor.extract(this.document.getPage(pageIndex));
        // 如果文本字典不为空，则添加数据
        if (!data.isEmpty()) {
            // 添加数据
            dataList.add(data);
        }
    }

    /**
     * 添加图片
     *
     * @param imageList 待接收图片列表
     * @param resources pdfbox页面资源
     */
    @SneakyThrows
    private void addImage(List<BufferedImage> imageList, PDResources resources) {
        // 获取页面资源内容名称
        Iterable<COSName> objectNames = resources.getXObjectNames();
        // 遍历资源内容名称
        for (COSName objectName : objectNames) {
            // 获取资源内容
            PDXObject xObject = resources.getXObject(objectName);
            // 如果资源内容为图片，则添加到待接收图片列表
            if (xObject instanceof PDImage) {
                // 添加到待接收图片列表
                imageList.add(((PDImage) xObject).getImage());
            }
            // 否则资源内容为表单，则递归调用
            else if (xObject instanceof PDFormXObject) {
                // 添加图片
                this.addImage(imageList, ((PDFormXObject) xObject).getResources());
            }
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
         *
         * @param document pdfbox文档
         * @throws IOException IO异常
         */
        public SimpleExtractor(PDDocument document) throws IOException {
            this.document = document;
        }

        /**
         * 提取文本
         *
         * @param textList  待接收文本列表
         * @param regex     正则表达式
         * @param pageIndex 页面索引
         */
        void extract(List<String> textList, String regex, int... pageIndex) {
            // 如果页面索引有内容，则根据页面索引提取文本
            if (pageIndex != null && pageIndex.length > 0) {
                // 遍历页面索引
                for (int index : pageIndex) {
                    // 设置起始页面
                    this.setStartPage(index + 1);
                    // 设置结束页面
                    this.setEndPage(index + 1);
                    // 提取文本
                    this.extract(textList, regex);
                }
                // 如果页面索引没有内容，则提取全部文本
            } else {
                // 提取全部文本
                this.extract(textList, regex);
            }
        }


        /**
         * 提取文本
         *
         * @param textList 待接收文本列表
         * @param regex    正则表达式
         */
        @SneakyThrows
        private void extract(List<String> textList, String regex) {
            // 获取文本
            String text = this.getText(this.document);
            // 如果正则表达式有内容，则进行匹配
            if (regex != null && regex.trim().length() > 0) {
                // 获取正则匹配器
                Matcher matcher = Pattern.compile(regex).matcher(text);
                // 循环匹配
                while (matcher.find()) {
                    // 添加文本列表
                    textList.add(matcher.group());
                }
                // 如果正则表达式没有内容，则直接添加到文本列表
            } else {
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
        private Map<String, ArrayList<List<TextPosition>>> regionCharacterList = new HashMap<>(32);
        /**
         * 区域文本字典
         */
        private Map<String, StringWriter> regionText = new HashMap<>(32);
        /**
         * 区域
         */
        private Map<String, Rectangle> regionArea = new HashMap<>(32);

        /**
         * 无参构造
         *
         * @throws IOException IO异常
         */
        RegionExtractor() throws IOException {
            // 设置排序
            super.setSortByPosition(true);
        }

        /**
         * 添加区域
         *
         * @param regionName 区域名称
         * @param rectangle  区域图形
         */
        void addRegion(String regionName, Rectangle rectangle) {
            this.regionArea.put(regionName, rectangle);
        }

        /**
         * 清理区域
         */
        void clearRegion() {
            this.regionCharacterList = new HashMap<>(32);
            this.regionText = new HashMap<>(32);
            this.regionArea = new HashMap<>(32);
        }

        /**
         * 提取文本
         *
         * @param page pdfbox页面
         * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
         */
        Map<String, String> extract(PDPage page) {
            return this.extract(page, " ");
        }

        /**
         * 提取文本
         *
         * @param page          pdfbox页面
         * @param wordSeparator 单词分隔符
         * @return 返回文本字典(key = 区域名称 ， value = 提取文本)
         */
        @SneakyThrows
        Map<String, String> extract(PDPage page, String wordSeparator) {
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
}
