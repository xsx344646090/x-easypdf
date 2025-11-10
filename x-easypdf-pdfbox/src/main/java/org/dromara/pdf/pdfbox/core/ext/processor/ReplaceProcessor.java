package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.ReplaceInfo;
import org.dromara.pdf.pdfbox.util.CommonUtil;
import org.dromara.pdf.pdfbox.util.TextTokenUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.COSName;
import org.dromara.pdf.shade.org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.dromara.pdf.shade.org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDResources;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.common.PDStream;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 替换处理器
 *
 * @author xsx
 * @date 2023/11/8
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
@EqualsAndHashCode(callSuper = true)
public class ReplaceProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public ReplaceProcessor(Document document) {
        super(document);
    }

    /**
     * 替换文本
     *
     * @param font        替换字体
     * @param replaceMap  替换字典
     * @param pageIndexes 页面索引
     */
    public void replaceText(PDFont font, Map<String, String> replaceMap, int... pageIndexes) {
        Objects.requireNonNull(font, "the font can not be null");
        Objects.requireNonNull(replaceMap, "the replace map not be null");
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int pageIndex : pageIndexes) {
                // 替换文本
                this.replaceText(pageTree.get(pageIndex), font, replaceMap);
            }
        } else {
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 替换文本
                this.replaceText(page, font, replaceMap);
            }
        }
    }

    /**
     * 替换文本
     *
     * @param replaceList 替换列表
     * @param pageIndexes 页面索引
     */
    public void replaceText(List<ReplaceInfo> replaceList, int... pageIndexes) {
        Objects.requireNonNull(replaceList, "the replace list can not be null");
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int pageIndex : pageIndexes) {
                // 替换文本
                this.replaceText(pageTree.get(pageIndex), replaceList);
            }
        } else {
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 替换文本
                this.replaceText(page, replaceList);
            }
        }
    }

    /**
     * 替换评论
     *
     * @param replaceMap  替换字典（key可为正则）
     * @param pageIndexes 页面索引
     */
    public void replaceComment(Map<String, String> replaceMap, int... pageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int pageIndex : pageIndexes) {
                // 替换评论
                this.replaceComment(pageTree.get(pageIndex), replaceMap);
            }
        } else {
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 替换评论
                this.replaceComment(page, replaceMap);
            }
        }
    }

    /**
     * 替换图像
     *
     * @param image        待替换图像
     * @param pageIndexes  页面索引
     * @param imageIndexes 图像索引
     */
    public void replaceImage(byte[] image, List<Integer> pageIndexes, int... imageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && imageIndexes.length > 0) {
            // 遍历页面索引
            for (int pageIndex : pageIndexes) {
                // 替换图像
                this.replaceImage(pageTree.get(pageIndex), image, imageIndexes);
            }
        } else {
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 替换图像
                this.replaceImage(page, image, imageIndexes);
            }
        }
    }

    /**
     * 替换书签（标题）
     *
     * @param replaceMap      替换字典（key可为正则）
     * @param bookmarkIndexes 书签索引
     */
    public void replaceBookmark(Map<String, String> replaceMap, int... bookmarkIndexes) {
        // 获取pdfbox目录
        PDDocumentCatalog documentCatalog = this.getDocument().getDocumentCatalog();
        // 获取pdfbox文档概要
        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
        // 文档概要非空
        if (Objects.nonNull(documentOutline)) {
            // 定义索引
            int index = 0;
            // 获取书签列表
            Iterable<PDOutlineItem> items = documentOutline.children();
            // 书签索引非空
            if (Objects.nonNull(bookmarkIndexes) && bookmarkIndexes.length > 0) {
                // 获取书签列表
                PrimitiveIterator.OfInt iterator = Arrays.stream(bookmarkIndexes).sorted().iterator();
                // 定义书签索引
                int bookmarkIndex = 0;
                // 遍历获取书签列表
                for (PDOutlineItem outlineItem : items) {
                    // 跳过小于书签索引
                    if (index < bookmarkIndex) {
                        // 索引自增
                        index++;
                        // 跳过
                        continue;
                    }
                    // 没有书签
                    if (!iterator.hasNext()) {
                        // 结束
                        break;
                    }
                    // 重置书签索引
                    bookmarkIndex = iterator.next();
                    // 当前索引等于书签索引
                    if (index == bookmarkIndex) {
                        // 替换书签
                        this.replaceBookmark(outlineItem, replaceMap);
                    }
                    // 索引自增
                    index++;
                }
            } else {
                // 遍历书签列表
                for (PDOutlineItem outlineItem : items) {
                    // 替换书签
                    this.replaceBookmark(outlineItem, replaceMap);
                    // 索引自增
                    index++;
                }
            }
        }
    }

    /**
     * 替换文本
     *
     * @param page       页面
     * @param font       字体
     * @param replaceMap 替换字典
     */
    @SneakyThrows
    protected void replaceText(PDPage page, PDFont font, Map<String, String> replaceMap) {
        // 获取pdf解析器
        PDFStreamParser parser = new PDFStreamParser(page);
        // 获取标记列表
        List<Object> tokens = parser.parse();
        // 如果替换文本标记成功，则更新内容
        if (TextTokenUtil.replaceTextForToken(log, this.getDocument(), page.getResources(), tokens, font, replaceMap)) {
            this.updateContents(page, tokens);
        }
    }

    /**
     * 替换文本
     *
     * @param page        页面
     * @param replaceList 替换列表
     */
    @SneakyThrows
    protected void replaceText(PDPage page, List<ReplaceInfo> replaceList) {
        // 定义替换索引字典
        Map<Character, Integer> replaceIndexMap = new HashMap<>(replaceList.size());
        // 初始化字典
        replaceList.forEach(info -> replaceIndexMap.put(info.getOriginal(), 0));
        // 获取pdf解析器
        PDFStreamParser parser = new PDFStreamParser(page);
        // 获取标记列表
        List<Object> tokens = parser.parse();
        // 如果替换文本标记成功，则更新内容
        if (TextTokenUtil.replaceTextForToken(log, this.getDocument(), page.getResources(), tokens, replaceList, replaceIndexMap)) {
            this.updateContents(page, tokens);
        }
    }

    /**
     * 替换评论
     *
     * @param page       页面
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    protected void replaceComment(PDPage page, Map<String, String> replaceMap) {
        // 获取页面注解
        List<PDAnnotation> pdAnnotations = page.getAnnotations();
        // 定义评论
        String comment;
        // 定义替换后的内容
        String replaceString;
        // 遍历注解
        for (PDAnnotation annotation : pdAnnotations) {
            // 如果为文本注解，则获取评论
            if (annotation instanceof PDAnnotationText) {
                // 转换为文本注解
                PDAnnotationText text = (PDAnnotationText) annotation;
                // 获取评论
                comment = text.getContents();
                // 重置替换后的内容
                replaceString = this.getReplaceString(comment, replaceMap);
                // 如果评论与替换后的内容不一致，则重置评论
                if (!Objects.equals(replaceString, comment)) {
                    // 重置评论
                    text.setContents(replaceString);
                    // 开启日志
                    if (log.isDebugEnabled()) {
                        // 打印日志
                        log.debug("Replaced comment: original [\"" + comment + "\"], now [\"" + replaceString + "\"]");
                    }
                }
            }
        }
    }

    /**
     * 替换图像
     *
     * @param page         页面
     * @param image        待替换图像
     * @param imageIndexes 图像索引
     */
    @SneakyThrows
    protected void replaceImage(PDPage page, byte[] image, int... imageIndexes) {
        // 定义pdf图像
        PDImageXObject imageObject = null;
        // 如果待替换图像不为空，则重置pdf图像
        if (Objects.nonNull(image)) {
            // 重置pdf图像
            imageObject = CommonUtil.createImage(this.getContext().getTargetDocument(), image);
        }
        // 获取页面资源
        PDResources resources = page.getResources();
        // 获取资源内容名称列表
        Iterable<COSName> objectNames = resources.getXObjectNames();
        // 全部替换
        if (Objects.isNull(imageIndexes) || imageIndexes.length == 0) {
            // 遍历资源内容名称
            for (COSName cosName : objectNames) {
                // 如果资源内容为图片，则替换
                if (resources.getXObject(cosName) instanceof PDImage) {
                    // 替换图像
                    resources.put(cosName, imageObject);
                }
            }
        } else {
            // 重置页面索引列表
            Iterator<Integer> iterator = Arrays.stream(imageIndexes).sorted().iterator();
            // 定义当前图像索引
            int index = 0;
            // 定义替换索引
            int replaceIndex = iterator.next();
            // 遍历资源内容名称
            for (COSName cosName : objectNames) {
                // 如果资源内容为图片，则替换
                if (resources.getXObject(cosName) instanceof PDImage) {
                    // 如果当前图像索引为替换索引，则替换图像
                    if (index == replaceIndex) {
                        // 替换图像
                        resources.put(cosName, imageObject);
                        // 如果存在下一个页面索引，则获取下一个页面索引
                        if (iterator.hasNext()) {
                            // 重置替换索引
                            replaceIndex = iterator.next();
                        } else {
                            // 结束遍历
                            break;
                        }
                    }
                    // 当前图像索引自增
                    index++;
                }
            }
        }
    }

    /**
     * 替换书签
     *
     * @param outlineItem 书签
     * @param replaceMap  替换字典（key可为正则）
     */
    protected void replaceBookmark(PDOutlineItem outlineItem, Map<String, String> replaceMap) {
        // 获取替换标题
        String title = this.getReplaceString(outlineItem.getTitle(), replaceMap);
        // 非空
        if (Objects.nonNull(title)) {
            // 替换
            outlineItem.setTitle(title);
            // 开启日志
            if (log.isDebugEnabled()) {
                // 打印日志
                log.debug("Replaced bookmark: original [\"" + outlineItem.getTitle() + "\"], now [\"" + title + "\"]");
            }
        }
        // 获取子书签
        Iterable<PDOutlineItem> children = outlineItem.children();
        // 遍历子书签
        for (PDOutlineItem child : children) {
            // 替换书签
            this.replaceBookmark(child, replaceMap);
        }
    }

    /**
     * 获取替换后的字符串
     *
     * @param content    源内容
     * @param replaceMap 替换字典
     * @return 返回替换后的字符串
     */
    protected String getReplaceString(String content, Map<String, String> replaceMap) {
        // 空内容
        if (Objects.isNull(content)) {
            // 返回空
            return null;
        }
        // 非空
        if (!content.trim().isEmpty()) {
            // 遍历待替换字典文本列表
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                // 替换字符串
                Matcher matcher = Pattern.compile(entry.getKey(), Pattern.LITERAL).matcher(content);
                // 匹配
                if (matcher.find()) {
                    // 替换
                    content = matcher.replaceAll(entry.getValue());
                }
            }
        }
        // 返回内容
        return content;
    }

    /**
     * 更新内容
     *
     * @param page   页面
     * @param tokens 标记
     */
    @SneakyThrows
    protected void updateContents(PDPage page, List<Object> tokens) {
        // 定义更新流
        PDStream updatedStream = new PDStream(this.getDocument());
        // 创建输出流
        try (OutputStream outputStream = updatedStream.createOutputStream(COSName.FLATE_DECODE)) {
            // 创建内容写入器
            ContentStreamWriter tokenWriter = new ContentStreamWriter(outputStream);
            // 写入标记列表
            tokenWriter.writeTokens(tokens);
            // 设置页面内容
            page.setContents(updatedStream);
        }
    }
}
