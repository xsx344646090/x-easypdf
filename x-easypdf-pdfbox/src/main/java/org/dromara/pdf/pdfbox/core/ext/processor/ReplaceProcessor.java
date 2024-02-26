package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.COSBaseInfo;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * @param font        字体
     * @param replaceMap  替换字典（key可为正则）
     * @param pageIndexes 页面索引
     */
    public void replaceText(PDFont font, Map<String, String> replaceMap, int... pageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes)) {
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
     * 替换评论
     *
     * @param replaceMap  替换字典（key可为正则）
     * @param pageIndexes 页面索引
     */
    public void replaceComment(Map<String, String> replaceMap, int... pageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes)) {
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
    public void replaceImage(BufferedImage image, List<Integer> pageIndexes, int... imageIndexes) {
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes)) {
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
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    protected void replaceText(PDPage page, PDFont font, Map<String, String> replaceMap) {
        // 获取pdf解析器
        PDFStreamParser parser = new PDFStreamParser(page);
        // // 解析页面
        // parser.parse();
        // 获取标记列表
        List<Object> tokens = parser.parse();
        // 如果替换文本标记成功，则更新内容
        if (this.replaceTextToken(font, page.getResources(), tokens, replaceMap)) {
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
                    // 如果开启日志，则打印日志
                    if (log.isDebugEnabled()) {
                        // 打印日志
                        log.debug("replace comment from \"" + comment + "\" to \"" + replaceString + "\"");
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
    protected void replaceImage(PDPage page, BufferedImage image, int... imageIndexes) {
        // 定义pdf图像
        PDImageXObject imageObject = null;
        // 如果待替换图像不为空，则重置pdf图像
        if (Objects.nonNull(image)) {
            // 重置pdf图像
            imageObject = PDImageXObject.createFromByteArray(
                    this.getDocument(),
                    ImageUtil.toBytes(image, ImageType.PNG.getType()),
                    ImageType.PNG.getType()
            );
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
     * 替换文本标记
     *
     * @param font       字体
     * @param resources  页面资源
     * @param tokens     标记列表
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    protected boolean replaceTextToken(
            PDFont font,
            PDResources resources,
            List<Object> tokens,
            Map<String, String> replaceMap
    ) {
        // 获取资源字体字典
        Map<COSName, PDFont> resourceFontMap = this.initResourceFontMap(resources);
        // 获取替换字体名称
        COSName replaceFontName = COSName.getPDFName(font.getName());
        // 定义资源字体索引
        Integer resourceFontIndex = null;
        // 定义资源字体名称
        COSName resourceFontName = null;
        // 定义资源字体
        PDFont resourceFont = null;
        // cosBase字典
        Map<COSName, List<COSBaseInfo>> cosBaseMap = new LinkedHashMap<>(10);
        // 遍历标记列表
        for (int i = 0; i < tokens.size(); i++) {
            // 获取标记
            Object token = tokens.get(i);
            // 如果标记为cosName，则重置资源字体
            if (token instanceof COSName) {
                // 如果资源字体不为空，则重置资源字体索引与名称
                if (resourceFontMap.get(token) != null) {
                    // 重置资源字体
                    resourceFont = resourceFontMap.get(token);
                    // 重置资源字体索引
                    resourceFontIndex = i;
                    // 重置资源字体名称
                    resourceFontName = (COSName) token;
                    // 如果cosBase字典不包括该字体名称，则添加新列表
                    if (!cosBaseMap.containsKey(resourceFontName)) {
                        // 添加新列表
                        cosBaseMap.put(resourceFontName, new ArrayList<>(64));
                    }
                }
                // 跳过
                continue;
            }
            // 如果为cosArray或cosString，则添加cosBase信息
            if (token instanceof COSArray || token instanceof COSString) {
                // 获取列表
                List<COSBaseInfo> list = cosBaseMap.get(resourceFontName);
                // 如果列表不为空，则添加信息
                if (list != null) {
                    // 添加信息
                    list.add(new COSBaseInfo((COSBase) token, resourceFontIndex, resourceFontName, resourceFont));
                }
            }
        }
        // 定义替换标记
        boolean flag = false;
        // 获取cosBase列表
        Collection<List<COSBaseInfo>> values = cosBaseMap.values();
        // 遍历cosBase列表
        for (List<COSBaseInfo> cosBases : values) {
            // 如果列表为空，则跳过
            if (cosBases.isEmpty()) {
                // 跳过
                continue;
            }
            // 在一种字体范围内替换
            for (COSBaseInfo info : cosBases) {
                // 如果替换内容成功，则设置字体
                if (this.getReplaceString(info.getCosBase(), replaceMap, resourceFont, font)) {
                    // 设置字体
                    tokens.set(info.getFontIndex(), replaceFontName);
                    // 如果资源字体未包含该字体，则添加资源字体
                    if (Objects.isNull(resources.getFont(replaceFontName))) {
                        // 添加资源字体
                        resources.put(replaceFontName, font);
                    }
                    // 重置替换标记
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 替换字符串
     *
     * @param cosBase      基础对象
     * @param replaceMap   替换字典（key可为正则）
     * @param resourceFont 原字体
     * @param replaceFont  替换字体
     */
    @SuppressWarnings("all")
    @SneakyThrows
    protected boolean getReplaceString(COSBase cosBase, Map<String, String> replaceMap, PDFont resourceFont, PDFont replaceFont) {
        // 如果为cos数组或cos字符串
        if (cosBase instanceof COSArray || cosBase instanceof COSString) {
            // 原始字符串
            String source = this.readFromToken(cosBase, resourceFont);
            // 替换后的字符串
            String replaceString = this.getReplaceString(source, replaceMap);
            // 跳过无意义字符串
            if (Objects.isNull(replaceString) || Objects.equals(replaceString, source)) {
                return false;
            }
            // 嵌入字体
            PdfHandler.getFontHandler().addToSubset(this.getDocument(), replaceFont, replaceString);
            // cos数组
            if (cosBase instanceof COSArray) {
                // 转换为cos数组
                COSArray array = (COSArray) cosBase;
                // 清理数组
                array.clear();
                // 添加新值
                array.add(new COSString(replaceFont.encode(replaceString)));
            } else {
                // 字符串编码
                byte[] array = replaceFont.encode(replaceString);
                // 转换为cos字符串
                COSString cosString = (COSString) cosBase;
                // 设置新值
                cosString.setValue(array);
            }
            // 如果开启日志，则打印日志
            if (log.isDebugEnabled()) {
                // 打印日志
                log.debug("replace string from \"" + source + "\" to \"" + replaceString + "\"");
            }
            return true;
        }
        return false;
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
     * 从token中读出文本内容
     *
     * @param token        标记
     * @param resourceFont 资源字体
     * @return 返回文本内容
     */
    protected String readFromToken(Object token, PDFont resourceFont) throws IOException {
        // 定义字符串构建器
        StringBuilder builder = new StringBuilder();
        // 如果为cos数组
        if (token instanceof COSArray) {
            // 转换为cos数组
            COSArray array = (COSArray) token;
            // 遍历cos数组
            for (COSBase cosBase : array) {
                // 如果为cos字符串，则进行处理
                if (cosBase instanceof COSString) {
                    // 转换为cos字符串
                    COSString cosString = (COSString) cosBase;
                    // 获取字符串输入流
                    try (InputStream in = new ByteArrayInputStream(cosString.getBytes())) {
                        // 读取字符
                        while (in.available() > 0) {
                            // 解析字符
                            builder.append(resourceFont.toUnicode(resourceFont.readCode(in)));
                        }
                    }
                } else if (cosBase instanceof COSInteger) {
                    // 转换
                    COSInteger cosInteger = (COSInteger) cosBase;
                    // 空格,暂时不知道空格的实际表示值，据观测
                    if (cosInteger.intValue() <= -199) {
                        // 添加空格
                        builder.append(" ");
                    }
                }
            }
        } else if (token instanceof COSString) {
            // 转换为cos字符串
            COSString cosString = (COSString) token;
            // 获取字符串输入流
            try (InputStream in = new ByteArrayInputStream(cosString.getBytes())) {
                // 读取字符
                while (in.available() > 0) {
                    // 解析字符
                    builder.append(resourceFont.toUnicode(resourceFont.readCode(in)));
                }
            }
        }
        return builder.toString();
    }

    /**
     * 初始化资源字体字典
     *
     * @param resources 页面资源
     * @return 返回资源字体字典
     */
    @SneakyThrows
    protected Map<COSName, PDFont> initResourceFontMap(PDResources resources) {
        // 定义资源字体字典
        Map<COSName, PDFont> resourceFontMap = new HashMap<>(16);
        // 添加资源字体
        if (Objects.nonNull(resources)) {
            // 遍历字体名称
            for (COSName cosName : resources.getFontNames()) {
                // 添加资源字体
                resourceFontMap.put(cosName, resources.getFont(cosName));
            }
        }
        return resourceFontMap;
    }
}
