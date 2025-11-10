package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.DefaultFreeTextAppearanceHandler;
import org.dromara.pdf.pdfbox.util.ColorUtil;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 评论处理器
 *
 * @author xsx
 * @date 2023/12/27
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
public class CommentProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public CommentProcessor(Document document) {
        super(document);
    }

    /**
     * 获取评论字典
     * <p>key=页面索引，value=评论列表</p>
     *
     * @return 返回评论字典
     */
    public Map<Integer, List<PDAnnotation>> getMap() {
        // 获取总页数
        int pages = this.document.getTotalPageNumber();
        // 定义数据字典
        Map<Integer, List<PDAnnotation>> data = new HashMap<>(pages);
        // 遍历页面
        for (int i = 0; i < pages; i++) {
            // 添加评论
            data.put(i, this.getList(i));
        }
        // 返回数据字典
        return data;
    }

    /**
     * 获取评论列表
     *
     * @param pageIndexes 页面索引
     * @return 返回评论列表
     */
    @SneakyThrows
    public List<PDAnnotation> getList(int... pageIndexes) {
        // 定义列表
        List<PDAnnotation> list = new ArrayList<>(64);
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 给定页面索引
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                try {
                    // 添加评论
                    this.addAnnotation(pageTree.get(index), list);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        } else {
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加评论
                this.addAnnotation(page, list);
            }
        }
        // 返回列表
        return list;
    }

    /**
     * 添加
     *
     * @param pageIndex 页面索引
     * @param comment   评论
     */
    @SneakyThrows
    public void add(int pageIndex, PDAnnotationFreeText comment) {
        this.add(pageIndex, null, null, null, comment);
    }

    /**
     * 添加
     *
     * @param pageIndex 页面索引
     * @param fontName  字体名称
     * @param comment   评论
     */
    @SneakyThrows
    public void add(int pageIndex, String fontName, PDAnnotationFreeText comment) {
        this.add(pageIndex, fontName, null, null, comment);
    }

    /**
     * 添加
     *
     * @param pageIndex 页面索引
     * @param fontName  字体名称
     * @param fontSize  字体大小
     * @param comment   评论
     */
    @SneakyThrows
    public void add(int pageIndex, String fontName, Float fontSize, PDAnnotationFreeText comment) {
        this.add(pageIndex, fontName, fontSize, null, comment);
    }

    /**
     * 添加
     *
     * @param pageIndex 页面索引
     * @param fontName  字体名称
     * @param fontSize  字体大小
     * @param fontColor 字体颜色
     * @param comment   评论
     */
    @SneakyThrows
    public void add(int pageIndex, String fontName, Float fontSize, Color fontColor, PDAnnotationFreeText comment) {
        // 检查参数
        Objects.requireNonNull(comment, "the comment can not be null");
        try {
            // 获取字体
            PDFont font = Optional.ofNullable(fontName)
                    .map(name -> PdfHandler.getFontHandler().getPDFont(this.getDocument(), name, true))
                    .orElse(this.document.getFont());
            // 获取字体大小
            fontSize = Optional.ofNullable(fontSize).orElse(10F);
            // 获取字体颜色
            fontColor = Optional.ofNullable(fontColor).orElse(Color.BLACK);
            // 获取页面
            PDPage page = this.getDocument().getPage(pageIndex);
            // 设置页面
            comment.setPage(page);
            // 设置默认样式
            comment.setDefaultStyleString(this.initDefaultStyleString(font, fontSize, fontColor));
            // 设置自定义外观助手
            comment.setCustomAppearanceHandler(new DefaultFreeTextAppearanceHandler(this.document, comment, font));
            // 构建外观
            comment.constructAppearances();
            // 添加评论
            page.getAnnotations().add(comment);
        } catch (Exception e) {
            // 提示信息
            log.warn("the page index['" + pageIndex + "'] is invalid, will be ignored");
        }
    }

    /**
     * 设置评论（替换）
     *
     * @param pageIndex 页面索引
     * @param content   内容
     * @param comment   评论
     * @param indexes   评论索引（内容相同）
     */
    public void set(int pageIndex, String content, PDAnnotationFreeText comment, int... indexes) {
        // 检查参数
        Objects.requireNonNull(content, "the content can not be null");
        Objects.requireNonNull(comment, "the comment can not be null");
        // 定义标记字典
        Map<Integer, Boolean> flagMap = new HashMap<>();
        // 初始化标记字典
        if (Objects.nonNull(indexes)) {
            // 遍历评论索引
            for (int index : indexes) {
                // 添加评论索引
                flagMap.put(index, Boolean.TRUE);
            }
        }
        try {
            // 获取页面
            PDPage page = this.getDocument().getPage(pageIndex);
            // 获取注解
            List<PDAnnotation> annotations = page.getAnnotations();
            // 定义新列表
            List<PDAnnotation> newList = new ArrayList<>(annotations.size());
            // 定义索引
            int index = 0;
            // 遍历注解
            for (PDAnnotation annotation : annotations) {
                // 文本注解
                if (annotation instanceof PDAnnotationText || annotation instanceof PDAnnotationFreeText) {
                    // 内容一致
                    if (Objects.equals(annotation.getContents(), content)) {
                        // 全部或存在索引
                        if (flagMap.isEmpty() || flagMap.containsKey(index)) {
                            // 添加注解
                            newList.add(comment);
                        } else {
                            // 添加注解
                            newList.add(annotation);
                        }
                        // 索引自增
                        index++;
                    } else {
                        // 添加注解
                        newList.add(annotation);
                    }
                } else {
                    // 添加注解
                    newList.add(annotation);
                }
            }
            // 重置注解
            page.setAnnotations(newList);
        } catch (Exception e) {
            // 提示信息
            log.warn("the page index['" + pageIndex + "'] is invalid, will be ignored");
        }
    }

    /**
     * 移除
     *
     * @param pageIndex 页面索引
     * @param content   内容
     * @param indexes   评论索引（内容相同）
     */
    public void remove(int pageIndex, String content, int... indexes) {
        // 检查参数
        Objects.requireNonNull(content, "the content can not be null");
        // 定义标记字典
        Map<Integer, Boolean> flagMap = new HashMap<>();
        // 初始化标记字典
        if (Objects.nonNull(indexes)) {
            // 遍历评论索引
            for (int index : indexes) {
                // 添加评论索引
                flagMap.put(index, Boolean.TRUE);
            }
        }
        try {
            // 获取页面
            PDPage page = this.getDocument().getPage(pageIndex);
            // 获取注解
            List<PDAnnotation> annotations = page.getAnnotations();
            // 定义新列表
            List<PDAnnotation> newList = new ArrayList<>(annotations.size());
            // 定义索引
            int index = 0;
            // 遍历注解
            for (PDAnnotation annotation : annotations) {
                // 文本注解
                if (annotation instanceof PDAnnotationText || annotation instanceof PDAnnotationFreeText) {
                    // 内容一致
                    if (Objects.equals(annotation.getContents(), content)) {
                        // 不包含移除索引
                        if (!flagMap.isEmpty() && !flagMap.containsKey(index)) {
                            // 添加注解
                            newList.add(annotation);
                        }
                        // 索引自增
                        index++;
                    } else {
                        // 添加注解
                        newList.add(annotation);
                    }
                } else {
                    // 添加注解
                    newList.add(annotation);
                }
            }
            // 重置注解
            page.setAnnotations(newList);
        } catch (Exception e) {
            // 提示信息
            log.warn("the page index['" + pageIndex + "'] is invalid, will be ignored");
        }
    }

    /**
     * 添加注解
     *
     * @param page 页面
     * @param list 评论列表
     */
    @SneakyThrows
    protected void addAnnotation(PDPage page, List<PDAnnotation> list) {
        // 获取页面注解
        List<PDAnnotation> annotations = page.getAnnotations();
        // 遍历注解
        for (PDAnnotation annotation : annotations) {
            // 文本注解
            if (annotation instanceof PDAnnotationText || annotation instanceof PDAnnotationFreeText) {
                // 添加评论
                list.add(annotation);
            }
        }
    }

    /**
     * 初始化默认样式
     *
     * @param font      字体
     * @param fontSize  字体大小
     * @param fontColor 字体颜色
     * @return 返回样式
     */
    protected String initDefaultStyleString(PDFont font, Float fontSize, Color fontColor) {
        return String.join(
                ";",
                String.join(":", "font", String.join(" ", font.getName(), fontSize + "pt")),
                String.join(":", "color", ColorUtil.toHexString(fontColor))
        );
    }
}
