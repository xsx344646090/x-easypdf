package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.dromara.pdf.pdfbox.enums.RotationAngle;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.COSBaseInfo;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面
 *
 * @author xsx
 * @date 2023/6/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
@Data
@EqualsAndHashCode(callSuper = true)
public class Page extends AbstractBaseFont implements Closeable {

    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(Page.class);
    /**
     * id
     */
    private String id;
    /**
     * 任务页面
     */
    private PDPage target;
    /**
     * 页面尺寸
     */
    private PageRectangle rectangle;
    /**
     * 页面索引（当前页码）
     */
    private Integer index;
    /**
     * 父页面
     */
    private Page parentPage;
    /**
     * 子页面
     */
    private Page subPage;

    /**
     * 有参构造
     *
     * @param context   上下文
     * @param rectangle 尺寸
     */
    Page(Context context, PageRectangle rectangle) {
        this(context, new PDPage(rectangle.getSize()));
    }

    /**
     * 有参构造
     *
     * @param context 上下文
     * @param page    pdfbox页面
     */
    Page(Context context, PDPage page) {
        this.init(context, page);
        if (this.getBackgroundColor() != Color.WHITE) {
            this.initBackgroundColor();
        }
        context.reset(this);
    }

    /**
     * 初始化
     */
    @Override
    public void init() {

    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {

    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     */
    @Override
    public void setMarginTop(float margin) {
        // 获取页面高度
        Float height = this.getContext().getPage().getHeight();
        // 获取上边距
        Float marginTop = this.getMarginTop();
        // 设置上边距
        super.setMarginTop(margin);
        // 获取游标
        Cursor cursor = this.getContext().getCursor();
        // 重置游标Y轴坐标
        if (Objects.equals(cursor.getY(), height - marginTop)) {
            cursor.setY(height - margin);
        }
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    @Override
    public void setMarginLeft(float margin) {
        // 获取左边距
        Float marginLeft = this.getMarginLeft();
        // 重置左边距
        super.setMarginLeft(margin);
        // 获取游标
        Cursor cursor = this.getContext().getCursor();
        // 重置游标X轴坐标
        if (Objects.equals(cursor.getX(), marginLeft)) {
            cursor.setX(margin);
        }
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        super.setFontName(fontName);
        super.setFont(PdfHandler.getFontHandler().getPDFont(this.getContext().getTargetDocument(), fontName, true));
    }

    /**
     * 获取页面宽度
     *
     * @return 返回页面宽度
     */
    public Float getWidth() {
        return this.rectangle.getWidth();
    }

    /**
     * 获取页面高度
     *
     * @return 返回页面高度
     */
    public Float getHeight() {
        return this.rectangle.getHeight();
    }

    /**
     * 获取排除页面边距的页面宽度
     *
     * @return 返回页面宽度
     */
    public Float getWithoutMarginWidth() {
        return this.getWidth() - this.getMarginLeft() - this.getMarginRight();
    }

    /**
     * 获取排除页面边距的页面高度
     *
     * @return 返回页面高度
     */
    public Float getWithoutMarginHeight() {
        return this.getHeight() - this.getMarginTop() - this.getMarginBottom();
    }

    /**
     * 获取第一个父页面
     *
     * @return 返回父页面
     */
    public Page getFirstParentPage() {
        // 获取父页面
        Page parent = this.getParentPage();
        // 父页面不为空
        if (Objects.nonNull(parent)) {
            // 循环获取
            while (Objects.nonNull(parent.getParentPage())) {
                parent = parent.getParentPage();
            }
        }
        // 返回父页面
        return parent;
    }

    /**
     * 获取最后一个子页面
     *
     * @return 返回子页面
     */
    public Page getLastSubPage() {
        // 获取子页面
        Page subPage = this.getSubPage();
        // 子页面不为空
        if (Objects.nonNull(subPage)) {
            // 循环获取
            while (Objects.nonNull(subPage.getSubPage())) {
                // 重置子页面
                subPage = subPage.getSubPage();
            }
        }
        // 返回子页面
        return subPage;
    }

    /**
     * 获取最新索引
     *
     * @return 返回索引
     */
    public Integer getLastIndex() {
        // 定义索引
        int index = Optional.ofNullable(this.getParentPage()).map(p -> 0).orElse(1);
        // 获取子页面
        Page subPage = Optional.ofNullable(this.getFirstParentPage()).orElse(this.getSubPage());
        // 子页面不为空
        if (Objects.nonNull(subPage)) {
            // 索引自增
            index++;
            // 循环获取
            while (Objects.nonNull(subPage.getSubPage())) {
                // 索引自增
                index++;
                // 重置子页面
                subPage = subPage.getSubPage();
            }
        }
        // 返回索引
        return index;
    }

    /**
     * 旋转
     *
     * @param angle 角度
     */
    public void rotation(RotationAngle angle) {
        Objects.requireNonNull(angle, "the rotation angle can not be null");
        this.target.setRotation(angle.getAngle());
    }

    /**
     * 替换文本
     *
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    public void replaceText(Map<String, String> replaceMap) {
        // 获取pdf解析器
        PDFStreamParser parser = new PDFStreamParser(this.getTarget());
        // 解析页面
        parser.parse();
        // 获取标记列表
        List<Object> tokens = parser.parse();
        // 如果替换文本标记成功，则更新内容
        if (this.replaceTextToken(this.getFont(), this.getTarget().getResources(), tokens, replaceMap)) {
            // 定义更新流
            PDStream updatedStream = new PDStream(this.getContext().getTargetDocument());
            // 创建输出流
            try (OutputStream outputStream = updatedStream.createOutputStream(COSName.FLATE_DECODE)) {
                // 创建内容写入器
                ContentStreamWriter tokenWriter = new ContentStreamWriter(outputStream);
                // 写入标记列表
                tokenWriter.writeTokens(tokens);
                // 设置页面内容
                this.getTarget().setContents(updatedStream);
            }
        }
    }

    /**
     * 替换评论
     *
     * @param replaceMap 替换字典（key可为正则）
     */
    @SneakyThrows
    public void replaceComment(Map<String, String> replaceMap) {
        // 获取页面注解
        List<PDAnnotation> pdAnnotations = this.getTarget().getAnnotations();
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
     * @param image        待替换图像
     * @param imageIndexes 图像索引
     */
    @SneakyThrows
    public void replaceImage(BufferedImage image, int... imageIndexes) {
        // 定义pdf图像
        PDImageXObject imageObject = null;
        // 如果待替换图像不为空，则重置pdf图像
        if (Objects.nonNull(image)) {
            // 重置pdf图像
            imageObject = PDImageXObject.createFromByteArray(
                    this.getContext().getTargetDocument(),
                    ImageUtil.toBytes(image, "png"),
                    "unknown"
            );
        }
        // 获取页面资源
        PDResources resources = this.getTarget().getResources();
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
     * 重置尺寸
     *
     * @param rectangle 页面尺寸
     */
    public void resetRectangle(PageRectangle rectangle) {
        // 设置尺寸
        this.target.setArtBox(this.target.getMediaBox());
        this.target.setBleedBox(this.rectangle.getSize());
        this.target.setMediaBox(rectangle.getSize());
        this.target.setCropBox(rectangle.getSize());
        // 重置尺寸
        this.rectangle = rectangle;
    }

    /**
     * 再创建
     */
    public void recreate() {
        // 获取子页面
        Page subPage = new Page(this.getContext(), this.getRectangle());
        // 初始化
        subPage.init(this, true);
        // 设置父页面
        subPage.setParentPage(this);
        // 设置子页面
        this.setSubPage(subPage);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        // 重置上下文
        this.setContext(null);
        // 重置任务页面
        this.setTarget(null);
        // 重置父页面
        this.setParentPage(null);
        // 重置子页面
        this.setSubPage(null);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param target  任务页面
     */
    private void init(Context context, PDPage target) {
        // 设置上下文
        super.setContext(context);
        // 初始化
        super.init(context.getDocument(), true);
        // 初始化id
        this.id = UUID.randomUUID().toString();
        // 初始化任务页面
        this.target = target;
        // 初始化页面尺寸
        this.rectangle = new PageRectangle(target.getCropBox());
    }

    /**
     * 初始化背景颜色
     */
    @SneakyThrows
    private void initBackgroundColor() {
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getContext().getTargetDocument(),
                this.getTarget(),
                PDPageContentStream.AppendMode.APPEND,
                true,
                this.getIsResetContentStream()
        );
        // 绘制矩形（背景矩形）
        contentStream.addRect(
                0,
                0,
                this.getWidth(),
                this.getHeight()
        );
        // 设置矩形颜色（背景颜色）
        contentStream.setNonStrokingColor(this.getBackgroundColor());
        // 填充矩形（背景矩形）
        contentStream.fill();
        // 关闭内容流
        contentStream.close();
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
                if (this.getReplacegetString(info.getCosBase(), replaceMap, resourceFont, font)) {
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
     * @param resourceFont 原文本pdfbox字体
     * @param replaceFont  替换文本pdfbox字体
     */
    @SuppressWarnings("all")
    @SneakyThrows
    private boolean getReplacegetString(COSBase cosBase, Map<String, String> replaceMap, PDFont resourceFont, PDFont replaceFont) {
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
            // 添加文本关联
            PdfHandler.getFontHandler().addToSubset(replaceFont, replaceString);
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
    private String getReplaceString(String content, Map<String, String> replaceMap) {
        if (Objects.isNull(content)) {
            return null;
        }
        if (!content.trim().isEmpty()) {
            // 遍历待替换字典文本列表
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
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
                } else if (cosBase instanceof COSInteger) {
                    COSInteger cosInteger = (COSInteger) cosBase;
                    // 空格,暂时不知道空格的实际表示值，据观测
                    if (cosInteger.intValue() <= -199) {
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
}
