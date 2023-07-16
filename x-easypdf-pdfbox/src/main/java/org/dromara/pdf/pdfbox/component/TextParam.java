package org.dromara.pdf.pdfbox.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.BaseParam;
import org.dromara.pdf.pdfbox.core.PageParam;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.TextUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 文本参数
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TextParam extends BaseParam {

    /**
     * 页面参数
     */
    private PageParam pageParam;
    /**
     * 相对起始X轴坐标（不影响后续坐标）
     */
    private Float relativeBeginX;
    /**
     * 相对起始Y轴坐标（不影响后续坐标）
     */
    private Float relativeBeginY;
    /**
     * 制表符大小
     */
    private Integer tabSize;
    /**
     * 文本内容
     */
    private String text;
    /**
     * 文本列表
     */
    private List<String> textList;
    /**
     * 高亮颜色
     */
    private Color highlightColor;
    /**
     * 是否高亮
     */
    private Boolean isHighlight;
    /**
     * 下划线宽度
     */
    private Float underlineWidth;
    /**
     * 下划线颜色
     */
    private Color underlineColor;
    /**
     * 是否下划线
     */
    private Boolean isUnderline;
    /**
     * 删除线宽度
     */
    private Float deleteLineWidth;
    /**
     * 删除线颜色
     */
    private Color deleteLineColor;
    /**
     * 是否删除线
     */
    private Boolean isDeleteLine;
    /**
     * 超链接地址
     */
    private String linkUrl;
    /**
     * 超链接地址
     */
    private Boolean isLink;
    /**
     * 是否自定义X轴坐标
     */
    private Boolean isCustomX;

    /**
     * 初始化
     */
    void init() {
        // 初始化参数
        super.init(this.pageParam);
        // 初始化分页
        if (this.getIsBreak()) {
            this.pageParam = this.pageParam.createPage().getParam();
            super.init(this.pageParam);
            this.beginX = null;
            this.beginY = null;
        }
        // 初始化字体
        if (this.getFontParam().getFontName() != null) {
            PDFont pdFont = PdfHandler.getFontHandler().getPDFont(this.pageParam.getDocumentParam().getTarget(), this.getFontParam().getFontName(), true);
            this.getFontParam().setFont(pdFont);
        }
        // 初始化制表符大小
        if (this.tabSize == null) {
            this.tabSize = 4;
        }
        // 初始化高亮颜色
        if (this.highlightColor == null) {
            this.highlightColor = pageParam.getBackgroundColor();
        }
        // 初始化是否高亮
        if (this.isHighlight == null) {
            this.isHighlight = Boolean.FALSE;
        }
        // 初始化下划线宽度
        if (this.underlineWidth == null) {
            this.underlineWidth = 1F;
        }
        // 初始化下划线颜色
        if (this.underlineColor == null) {
            this.underlineColor = this.getFontParam().getFontColor();
        }
        // 初始化是否下划线
        if (this.isUnderline == null) {
            this.isUnderline = Boolean.FALSE;
        }
        // 初始化删除线宽度
        if (this.deleteLineWidth == null) {
            this.deleteLineWidth = 1F;
        }
        // 初始化删除线颜色
        if (this.deleteLineColor == null) {
            this.deleteLineColor = this.getFontParam().getFontColor();
        }
        // 初始化是否删除线
        if (this.isDeleteLine == null) {
            this.isDeleteLine = Boolean.FALSE;
        }
        // 初始化是否超链接
        if (this.isLink == null) {
            this.isLink = Boolean.FALSE;
        }
        // 初始化起始X轴坐标
        if (this.beginX == null) {
            this.beginX = this.pageParam.getBeginX();
            this.isCustomX = Boolean.FALSE;
        }
        // 初始化起始Y轴坐标
        if (this.beginY == null) {
            this.beginY = this.pageParam.getBeginY();
            if (this.beginY + this.pageParam.getMarginTop() == this.pageParam.getRectangle().getHeight()) {
                this.beginY = this.beginY - this.getFontParam().getFontSize();
            }
        }
        // 初始化相对起始X轴坐标
        if (this.relativeBeginX == null) {
            this.relativeBeginX = 0F;
        }
        // 初始化相对起始Y轴坐标
        if (this.relativeBeginY == null) {
            this.relativeBeginY = 0F;
        }
        // 初始化换行
        if (this.getIsWrap()) {
            this.beginX = this.pageParam.getMarginLeft();
            this.beginY = this.beginY - this.getFontParam().getFontSize() - this.getFontParam().getLeading();
            this.pageParam.setBeginX(this.beginX);
        }
        // 重置起始X轴坐标
        this.beginX = this.beginX + this.getMarginLeft();
        // 重置起始Y轴坐标
        this.beginY = this.beginY - this.getMarginTop();
        // 初始化文本
        this.initText();
    }

    /**
     * 初始化文本
     */
    private void initText() {
        // 文本为空
        if (this.text == null) {
            this.textList = Collections.emptyList();
            return;
        }
        // 初始化文本列表
        this.initTextList();
    }

    /**
     * 处理文本
     *
     * @return 返回文本列表
     */
    private List<String> processText() {
        // 临时文本
        String temp = this.text;
        // 过滤特殊字符
        temp = TextUtil.filterAll(temp);
        // 替换制表符
        temp = temp.replaceAll("\t", TextUtil.space(this.tabSize));
        // 根据换行符拆分
        return Arrays.asList(temp.split("\n"));
    }

    /**
     * 初始化文本列表
     */
    private void initTextList() {
        // 创建临时文本列表
        List<String> tempTextList = new ArrayList<>(256);
        // 添加文本
        tempTextList.addAll(this.processText());
        // 获取页面宽度
        float pageWidth = this.pageParam.getRectangle().getWidth();
        // 获取首行宽度
        float firstLineWidth = pageWidth - this.beginX - this.pageParam.getMarginRight();
        // 获取新行宽度
        float newLineWidth = pageWidth - this.pageParam.getMarginLeft() - this.pageParam.getMarginRight() - this.getMarginLeft() - this.getMarginRight();
        // 新行宽度小于字体大小
        if (newLineWidth < this.getFontParam().getFontSize()) {
            throw new IllegalArgumentException("the font size is larger, must set less font size");
        }
        // 初始化文本列表
        this.initTextList(tempTextList, firstLineWidth, newLineWidth);
    }

    /**
     * 初始化文本列表
     *
     * @param tempTextList   临时文本列表
     * @param firstLineWidth 首行宽度
     * @param newLineWidth   新行宽度
     */
    private void initTextList(List<String> tempTextList, float firstLineWidth, float newLineWidth) {
        // 重置文本列表
        this.textList = new ArrayList<>(tempTextList.size());
        // 获取首行文本
        String text = tempTextList.get(0);
        // 首行文本不为空
        if (TextUtil.isNotBlank(text)) {
            // 获取首行内容
            String firstContent = TextUtil.splitText(
                    text,
                    firstLineWidth,
                    this.pageParam.getFontParam().getFont(),
                    this.getFontParam().getFontSize(),
                    this.getFontParam().getCharacterSpacing()
            );
            // 首行内容为空
            if (firstContent == null) {
                // 重置起始X轴坐标
                this.beginX = this.pageParam.getMarginLeft();
                // 重置起始Y轴坐标
                this.beginY = this.beginY - this.getFontParam().getFontSize();
                // 添加文本
                this.textList.addAll(
                        TextUtil.splitLines(
                                text,
                                newLineWidth,
                                this.pageParam.getFontParam().getFont(),
                                this.getFontParam().getFontSize(),
                                this.getFontParam().getCharacterSpacing()
                        )
                );
            } else {
                // 添加首行文本
                this.textList.add(firstContent);
                // 首行内容长度小于首行文本长度
                if (firstContent.length() < this.text.length()) {
                    // 添加剩余文本
                    this.textList.addAll(
                            TextUtil.splitLines(
                                    text.substring(firstContent.length()),
                                    newLineWidth,
                                    this.pageParam.getFontParam().getFont(),
                                    this.getFontParam().getFontSize(),
                                    this.getFontParam().getCharacterSpacing()
                            )
                    );
                }
            }
        }
        // 遍历添加剩余文本
        for (int i = 1, count = tempTextList.size(); i < count; i++) {
            if (TextUtil.isNotBlank(tempTextList.get(i))) {
                this.textList.addAll(
                        TextUtil.splitLines(
                                tempTextList.get(i),
                                newLineWidth,
                                this.pageParam.getFontParam().getFont(),
                                this.getFontParam().getFontSize(),
                                this.getFontParam().getCharacterSpacing()
                        )
                );
            }
        }
    }
}
