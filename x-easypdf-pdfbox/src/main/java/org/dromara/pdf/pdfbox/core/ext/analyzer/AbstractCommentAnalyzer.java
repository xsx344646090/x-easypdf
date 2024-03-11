package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFreeText;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.CommentInfo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 抽象评论分析器
 *
 * @author xsx
 * @date 2023/10/25
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
@Getter
public abstract class AbstractCommentAnalyzer extends AbstractAnalyzer {

    /**
     * 评论信息列表
     */
    protected final Set<CommentInfo> infoSet = new HashSet<>(64);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractCommentAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理评论
     *
     * @param pageIndex 页面索引
     * @param page      页面
     */
    public abstract void processComment(int pageIndex, PDPage page);

    /**
     * 处理评论
     *
     * @param pageIndex  页面索引
     * @param page       页面
     * @param annotation 文档注解
     */
    @SneakyThrows
    protected void processComment(int pageIndex, PDPage page, PDAnnotation annotation) {
        // 如果为文本注解，则获取评论
        if (annotation instanceof PDAnnotationText || annotation instanceof PDAnnotationFreeText) {
            // 获取评论
            String content = annotation.getContents();
            // 如果文本内容不为空，则分析文本
            if (Objects.nonNull(content) && !content.trim().isEmpty()) {
                // 获取当前页面尺寸
                PDRectangle pageRectangle = page.getMediaBox();
                // 获取评论尺寸
                PDRectangle commentRectangle = annotation.getRectangle();
                // 获取字体信息
                String[] fontInfo = this.getFontInfo(annotation);
                // 获取字体名称
                String fontName = fontInfo[1];
                // 获取字体大小
                String fontSize = fontInfo[2];
                // 构建评论信息
                CommentInfo info = CommentInfo.builder()
                        .pageIndex(pageIndex)
                        .pageWidth(pageRectangle.getWidth())
                        .pageHeight(pageRectangle.getHeight())
                        .fontName(fontName)
                        .fontSize(Float.valueOf(fontSize.substring(0, fontSize.indexOf("pt"))))
                        .textContent(content)
                        .textBeginPosition(commentRectangle.getLowerLeftX() + "," + commentRectangle.getLowerLeftY())
                        .textEndPosition(commentRectangle.getUpperRightX() + "," + commentRectangle.getUpperRightY())
                        .textWidth(commentRectangle.getWidth())
                        .textHeight(commentRectangle.getHeight())
                        .isHidden(annotation.isHidden())
                        .isInvisible(annotation.isInvisible())
                        .isLocked(annotation.isLocked())
                        .isLockedContents(annotation.isLockedContents())
                        .isNoRotate(annotation.isNoRotate())
                        .isNoView(annotation.isNoView())
                        .isNoZoom(annotation.isNoZoom())
                        .isPrinted(annotation.isPrinted())
                        .isReadOnly(annotation.isReadOnly())
                        .isToggleNoView(annotation.isToggleNoView())
                        .build();
                // 添加评论
                this.infoSet.add(info);
                // 如果日志打印开启，则打印日志
                if (log.isDebugEnabled()) {
                    // 打印日志
                    log.debug(
                            "\n********************************************ANALYZE COMMENT BEGIN********************************************" +
                                    "\npage index: " + info.getPageIndex() +
                                    "\npage width: " + info.getPageWidth() +
                                    "\npage height: " + info.getPageHeight() +
                                    "\ncomment font name: " + info.getFontName() +
                                    "\ncomment font size: " + info.getFontSize() +
                                    "\ncomment content: " + info.getTextContent() +
                                    "\ncomment begin position: " + info.getTextBeginPosition() +
                                    "\ncomment end position: " + info.getTextEndPosition() +
                                    "\ncomment width: " + info.getTextWidth() +
                                    "\ncomment height: " + info.getTextHeight() +
                                    "\ncomment is hidden: " + info.getIsHidden() +
                                    "\ncomment is invisible: " + info.getIsInvisible() +
                                    "\ncomment is locked: " + info.getIsLocked() +
                                    "\ncomment is locked contents: " + info.getIsLockedContents() +
                                    "\ncomment is no rotate: " + info.getIsNoRotate() +
                                    "\ncomment is no view: " + info.getIsNoView() +
                                    "\ncomment is no zoom: " + info.getIsNoZoom() +
                                    "\ncomment is printed: " + info.getIsPrinted() +
                                    "\ncomment is readOnly: " + info.getIsReadOnly() +
                                    "\n*********************************************ANALYZE COMMENT END*********************************************"
                    );
                }
            }
        }
    }

    /**
     * 处理字体名称
     *
     * @param annotation 文档注解
     * @return 返回字体信息
     */
    protected String[] getFontInfo(PDAnnotation annotation) {
        return annotation.getCOSObject().getString(COSName.DS).split(";")[0].split(" ");
    }
}