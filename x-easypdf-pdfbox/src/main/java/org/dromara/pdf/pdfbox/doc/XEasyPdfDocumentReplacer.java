package org.dromara.pdf.pdfbox.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.dromara.pdf.pdfbox.component.image.XEasyPdfImageType;
import org.dromara.pdf.pdfbox.util.XEasyPdfFileUtil;
import org.dromara.pdf.pdfbox.util.XEasyPdfFontUtil;
import org.dromara.pdf.pdfbox.util.XEasyPdfImageUtil;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pdf文档替换器
 *
 * @author xsx
 * @date 2022/1/11
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
public class XEasyPdfDocumentReplacer implements Serializable {

    private static final long serialVersionUID = 5248577569799461988L;

    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(XEasyPdfDocumentReplacer.class);
    /**
     * pdfbox文档
     */
    private PDDocument document;
    /**
     * pdf文档
     */
    private XEasyPdfDocument pdfDocument;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 是否允许替换评论
     */
    boolean isAllowReplaceComments = Boolean.FALSE;

    /**
     * 有参构造
     *
     * @param pdfDocument pdf文档
     */
    XEasyPdfDocumentReplacer(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.build(true);
    }

    /**
     * 有参构造
     *
     * @param pdfDocument pdf文档
     * @param target      pdfbox文档
     */
    XEasyPdfDocumentReplacer(XEasyPdfDocument pdfDocument, PDDocument target) {
        this.pdfDocument = pdfDocument;
        this.document = target;
    }

    /**
     * 设置是否允许替换引用
     *
     * @param b
     * @return
     */
    public XEasyPdfDocumentReplacer enableReplaceComments(boolean b) {
        this.isAllowReplaceComments = Boolean.TRUE;
        return this;
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer setFontPath(String fontPath) {
        this.fontPath = fontPath;
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.fontPath = style.getPath();
        }
        return this;
    }

    /**
     * 替换文本
     *
     * @param replaceMap 替换字典（key可为正则）
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceText(Map<String, String> replaceMap) {
        return this.replaceText(replaceMap, (int[]) null);
    }

    /**
     * 替换文本
     *
     * @param replaceMap 替换字典（key可为正则）
     * @param pageIndex  页面索引
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceText(Map<String, String> replaceMap, int... pageIndex) {
        return this.replaceText(1, replaceMap, pageIndex);
    }

    /**
     * 替换文本
     *
     * @param count      替换次数
     * @param replaceMap 替换字典（key可为正则）
     * @param pageIndex  页面索引
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceText(int count, Map<String, String> replaceMap, int... pageIndex) {
        // 替换字典不为空且替换次数大于0，则替换文本
        if (replaceMap != null && !replaceMap.isEmpty() && count > 0) {
            // 如果页面索引为空，则替换全部页面
            if (pageIndex == null || pageIndex.length == 0) {
                // 获取页面树
                PDPageTree pages = this.document.getPages();
                // 遍历页面树
                for (PDPage page : pages) {
                    // 遍历替换次数
                    for (int i = 0; i < count; i++) {
                        // 替换文本
                        this.replaceText(page, replaceMap);
                    }
                }
            }
            // 否则替换给定页面索引
            else {
                // 遍历页面索引
                for (int index : pageIndex) {
                    // 如果页面索引大于等于0，则替换文本
                    if (index >= 0) {
                        // 遍历替换次数
                        for (int i = 0; i < count; i++) {
                            // 替换文本
                            this.replaceText(this.document.getPage(index), replaceMap);
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * 替换评论
     *
     * @param replaceMap 替换字典（key可为正则）
     * @param pageIndex  页面索引
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceComment(Map<String, String> replaceMap, int... pageIndex) {
        // 替换字典不为空且替换次数大于0，则替换文本
        if (replaceMap != null && !replaceMap.isEmpty()) {
            // 如果页面索引为空，则替换全部页面
            if (pageIndex == null || pageIndex.length == 0) {
                // 获取页面树
                PDPageTree pages = this.document.getPages();
                // 遍历页面树
                for (PDPage page : pages) {
                    this.replaceComment(page, replaceMap);
                }
            }
            // 否则替换给定页面索引
            else {
                // 遍历页面索引
                for (int index : pageIndex) {
                    // 如果页面索引大于等于0，则替换文本
                    if (index >= 0) {
                        // 替换文本
                        this.replaceComment(this.document.getPage(index), replaceMap);
                    }
                }
            }
        }
        return this;
    }


    /**
     * 替换图像
     *
     * @param image     待替换图像
     * @param pageIndex 页面索引
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer replaceImage(BufferedImage image, int... pageIndex) {
        return this.replaceImage(image, XEasyPdfImageType.PNG, pageIndex);
    }

    /**
     * 替换图像
     *
     * @param image     待替换图像
     * @param imageType 待替换图像类型
     * @param pageIndex 页面索引
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer replaceImage(BufferedImage image, XEasyPdfImageType imageType, int... pageIndex) {
        return this.replaceImage(image, imageType, null, pageIndex);
    }

    /**
     * 替换图像
     *
     * @param image            待替换图像
     * @param replaceIndexList 待替换图像索引列表
     * @param pageIndex        页面索引
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceImage(BufferedImage image, List<Integer> replaceIndexList, int... pageIndex) {
        return this.replaceImage(image, XEasyPdfImageType.PNG, replaceIndexList, pageIndex);
    }

    /**
     * 替换图像
     *
     * @param image            待替换图像
     * @param imageType        待替换图像类型
     * @param replaceIndexList 待替换图像索引列表
     * @param pageIndex        页面索引
     * @return 返回pdf文档替换器
     */
    @SneakyThrows
    public XEasyPdfDocumentReplacer replaceImage(BufferedImage image, XEasyPdfImageType imageType, List<Integer> replaceIndexList, int... pageIndex) {
        // 定义pdf图像
        PDImageXObject imageObject = null;
        // 如果待替换图像不为空，则重置pdf图像
        if (image != null) {
            // 重置pdf图像
            imageObject = PDImageXObject.createFromByteArray(
                    this.document,
                    XEasyPdfImageUtil.toBytes(image, imageType.name()), imageType.name()
            );
        }
        return this.replaceImage(imageObject, replaceIndexList, pageIndex);
    }

    /**
     * 替换属性
     *
     * @param map 替换字典（key可为正则）
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer replaceAttributes(Map<String, String> map) {
        PDDocumentInformation information = this.pdfDocument.getParam().getSource().getDocumentInformation();
        this.replaceAttributes(this.pdfDocument.information(), information, map);
        return this;
    }

    /**
     * 文档签名器
     *
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner signer() {
        return new XEasyPdfDocumentSigner(this.pdfDocument);
    }

    /**
     * 完成操作
     *
     * @param outputPath 文件输出路径
     */
    @SneakyThrows
    public void finish(String outputPath) {
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath))))) {
            this.finish(outputStream);
        }
    }

    /**
     * 完成操作
     *
     * @param outputStream 文件输出流
     */
    @SneakyThrows
    public void finish(OutputStream outputStream) {
        // 替换总页码占位符
        this.pdfDocument.replaceTotalPagePlaceholder(this.document, false);
        // 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
        this.pdfDocument.setBasicInfo(this.document);
        // 保存文档
        this.document.save(outputStream);
        // 关闭文档
        this.pdfDocument.close();
    }

    /**
     * 完成操作
     */
    void finish() {
        this.pdfDocument = null;
        this.document = null;
    }

    /**
     * 替换文本
     *
     * @param page       pdfbox页面
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    void replaceText(PDPage page, Map<String, String> replaceMap) {
        // 获取pdfbox字体
        PDFont font = this.initFont();
        // 获取页面资源
        PDResources resources = page.getResources();
        // 获取pdf解析器
        PDFStreamParser parser = new PDFStreamParser(page);
        // 解析页面
        parser.parse();
        // 获取标记列表
        List<Object> tokens = parser.getTokens();
        // 如果替换文本标记成功，则更新内容
        if (this.replaceTextToken(font, resources, tokens, replaceMap)) {
            // 定义更新流
            PDStream updatedStream = new PDStream(this.document);
            // 创建输出流
            try (OutputStream outputStream = updatedStream.createOutputStream(COSName.FLATE_DECODE)) {
                // 创建内容写入器
                ContentStreamWriter tokenWriter = new ContentStreamWriter(outputStream);
                // 写入标记列表
                tokenWriter.writeTokens(tokens);
                // 设置页面内容
                page.setContents(updatedStream);
            }
            // 添加字体嵌入
            this.pdfDocument.getParam().embedFont(Collections.singleton(font));
        }
    }

    /**
     * 替换评论内容
     *
     * @param page       文档页
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    void replaceComment(PDPage page, Map<String, String> replaceMap) {
        // 获取页面注解
        List<PDAnnotation> pdAnnotations = page.getAnnotations();
        // 定义评论
        String comment;
        // 定义替换后的内容
        String replaceString;
        // 遍历注解
        for (PDAnnotation p : pdAnnotations) {
            // 如果为文本注解，则获取评论
            if (p instanceof PDAnnotationText) {
                // 转换为文本注解
                PDAnnotationText pdAnnotationText = (PDAnnotationText) p;
                // 获取评论
                comment = pdAnnotationText.getContents();
                // 重置替换后的内容
                replaceString = this.replaceString(comment, replaceMap);
                // 如果评论与替换后的内容不一致，则重置评论
                if (!Objects.equals(replaceString, comment)) {
                    // 重置评论
                    pdAnnotationText.setContents(replaceString);
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
     * 查找待替换字符串
     *
     * @param source     源字符串
     * @param replaceMap 替换字典
     * @return 返回布尔值
     */
    private Boolean findReplaceString(String source, Map<String, String> replaceMap) {
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            // 获取待替换文本字典
            // 替换字符串，大小写不敏感
            Matcher matcher = Pattern.compile(entry.getKey(), Pattern.LITERAL | Pattern.CASE_INSENSITIVE).matcher(source);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 替换字符串
     *
     * @param content    源内容
     * @param replaceMap 替换字典
     * @return 返回替换后的内容
     */
    private String replaceString(String content, Map<String, String> replaceMap) {
        if (content == null) {
            return null;
        }
        if (content.trim().length() > 0) {
            // 获取待替换字典文本迭代器
            // 遍历待替换字典文本列表
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                // 获取待替换文本字典
                // 替换字符串，大小写不敏感
                Matcher matcher = Pattern.compile(entry.getKey(), Pattern.LITERAL | Pattern.CASE_INSENSITIVE).matcher(content);
                if (matcher.find()) {
                    content = matcher.replaceAll(entry.getValue());
                }
            }
        }
        return content;
    }

    /**
     * 初始化字体
     *
     * @return 返回pdfbox字体
     */
    private PDFont initFont() {
        // 如果字体路径为空，则初始化为文档字体路径
        if (this.fontPath == null) {
            // 初始化为文档字体路径
            this.fontPath = this.pdfDocument.getFontPath();
        }
        // 读取字体
        return XEasyPdfFontUtil.loadFont(this.pdfDocument, this.fontPath, true);
    }

    /**
     * 初始化资源字体字典
     *
     * @param resources pdfbox页面资源
     * @return 返回资源字体字典
     */
    @SneakyThrows
    private Map<COSName, PDFont> initResourceFontMap(PDResources resources) {
        // 定义资源字体字典
        Map<COSName, PDFont> resourceFontMap = new HashMap<>(16);
        // 获取资源字体名称迭代器
        for (COSName cosName : resources.getFontNames()) {
            // 添加资源字体
            resourceFontMap.put(cosName, resources.getFont(cosName));
        }
        return resourceFontMap;
    }

    /**
     * 替换文本标记
     *
     * @param font       pdfbox字体
     * @param resources  pdfbox页面资源
     * @param tokens     标记列表
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    private boolean replaceTextToken(
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
                if (this.replaceString(info.getCosBase(), replaceMap, resourceFont, font)) {
                    // 设置字体
                    tokens.set(info.getFontIndex(), replaceFontName);
                    // 如果资源字体未包含该字体，则添加资源字体
                    if (resources.getFont(replaceFontName) == null) {
                        // 添加资源字体
                        resources.put(replaceFontName, font);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 替换字符串
     *
     * @param cosBase      基础对象
     * @param replaceMap   替换字典（key可为正则）
     * @param resourceFont 原文本pdfbox字体
     * @param replaceFont  替换文本pdfbox字体
     */
    @SneakyThrows
    private boolean replaceString(COSBase cosBase, Map<String, String> replaceMap, PDFont resourceFont, PDFont replaceFont) {
        // 如果为cos数组或cos字符串
        if (cosBase instanceof COSArray || cosBase instanceof COSString) {
            String source = this.readFromToken(cosBase, resourceFont);
            // 如果替换过字符串，则关联文本
            String temp = this.replaceString(source, replaceMap);
            if (temp == null || temp.equals(source)) {
                return false;
            }
            // 添加文本关联
            XEasyPdfFontUtil.addToSubset(replaceFont, temp);
            // 如果为cos数组
            if (cosBase instanceof COSArray) {
                // 转换为cos数组
                COSArray array = (COSArray) cosBase;
                // 清理数组
                array.clear();
                // 添加新值
                array.add(new COSString(replaceFont.encode(temp)));
            }
            // 否则为cos字符串
            else {
                // 字符串编码
                byte[] array = replaceFont.encode(temp);
                // 转换为cos字符串
                COSString cosString = (COSString) cosBase;
                // 设置新值
                cosString.setValue(array);
            }
            // 如果开启日志，则打印日志
            if (log.isDebugEnabled()) {
                // 打印日志
                log.debug("replace string from \"" + source + "\" to \"" + temp + "\"");
            }
            return true;
        }
        return false;
    }


    /**
     * 从token中读出文本内容
     *
     * @param token        标记
     * @param resourceFont 资源字体
     * @return 返回文本内容
     */
    private String readFromToken(Object token, PDFont resourceFont) throws IOException {
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
                }
                // 如果为cosInteger
                else if (cosBase instanceof COSInteger) {
                    COSInteger cosInteger = (COSInteger) cosBase;
                    // 空格,暂时不知道空格的实际表示值，据观测
                    if (cosInteger.intValue() <= -199) {
                        builder.append(" ");
                    }
                }
            }
        }
        // 如果为cos字符串
        else if (token instanceof COSString) {
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
     * 替换图像
     *
     * @param image            待替换图像
     * @param replaceIndexList 待替换图像索引列表
     * @param pageIndex        页面索引
     * @return 返回pdf文档替换器
     */
    private XEasyPdfDocumentReplacer replaceImage(PDImageXObject image, List<Integer> replaceIndexList, int... pageIndex) {
        // 获取pdfbox文档页面树
        PDPageTree pages = this.document.getPages();
        // 如果页面索引为空，则替换全部图像
        if (pageIndex == null || pageIndex.length == 0) {
            // 遍历页面树
            for (PDPage page : pages) {
                // 替换图像
                this.replaceImage(page.getResources(), image, replaceIndexList);
            }
        }
        // 否则替换给定页面索引图像
        else {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 如果索引小于0，则跳过
                if (index < 0) {
                    // 跳过
                    continue;
                }
                // 替换图像
                this.replaceImage(pages.get(index).getResources(), image, replaceIndexList);
            }
        }
        return this;
    }

    /**
     * 替换图像
     *
     * @param resources        页面资源
     * @param image            待替换图像
     * @param replaceIndexList 待替换图像索引列表
     */
    @SneakyThrows
    private void replaceImage(PDResources resources, PDImageXObject image, List<Integer> replaceIndexList) {
        // 获取资源内容名称列表
        Iterable<COSName> objectNames = resources.getXObjectNames();
        // 如果待替换图像索引列表为空，则替换全部图像
        if (replaceIndexList == null || replaceIndexList.isEmpty()) {
            // 遍历资源内容名称
            for (COSName cosName : objectNames) {
                // 如果资源内容为图片，则替换
                if (resources.getXObject(cosName) instanceof PDImage) {
                    // 替换图像
                    resources.put(cosName, image);
                }
            }
        }
        // 否则替换给定图像索引图像
        else {
            // 重置页面索引列表
            Iterator<Integer> iterator = new TreeSet<>(replaceIndexList).iterator();
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
                        resources.put(cosName, image);
                        // 如果存在下一个页面索引，则获取下一个页面索引
                        if (iterator.hasNext()) {
                            // 重置替换索引
                            replaceIndex = iterator.next();
                        }
                        // 否则结束遍历
                        else {
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
     * 替换文档属性的内容
     *
     * @param replaceMap
     */
    private void replaceAttributes(XEasyPdfDocumentInfo info, PDDocumentInformation information, Map<String, String> replaceMap) {
        info.setAuthor(this.replaceString(information.getAuthor(), replaceMap));
        info.setCreator(this.replaceString(information.getCreator(), replaceMap));
        info.setSubject(this.replaceString(information.getSubject(), replaceMap));
        info.setTitle(this.replaceString(information.getTitle(), replaceMap));
        info.setKeywords(this.replaceString(information.getKeywords(), replaceMap));
    }

    /**
     * cosBase信息
     */
    @Data
    @AllArgsConstructor
    private static class COSBaseInfo {
        /**
         * cosBase
         */
        private COSBase cosBase;
        /**
         * 字体索引
         */
        private Integer fontIndex;
        /**
         * 字体名称
         */
        private COSName fontName;
        /**
         * 字体
         */
        private PDFont font;
    }
}
