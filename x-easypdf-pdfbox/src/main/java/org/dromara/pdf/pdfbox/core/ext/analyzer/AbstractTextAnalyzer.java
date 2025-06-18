package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.TextInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 抽象文本分析器
 *
 * @author xsx
 * @date 2023/10/19
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
public abstract class AbstractTextAnalyzer extends AbstractAnalyzer {

    /**
     * 文本信息列表
     */
    protected Set<TextInfo> infoSet = new HashSet<>(64);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractTextAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理文本
     *
     * @param pageIndex 页面索引
     */
    public abstract void processText(int pageIndex);

    /**
     * 获取字符数
     *
     * @param pageIndex 页面索引
     * @return 返回字符数
     */
    public abstract int getCharacterCount(int pageIndex);

    /**
     * 文本剥离器
     */
    protected static class DefaultTextStripper extends PDFTextStripper {
        /**
         * 文本信息列表
         */
        @Getter
        protected final Set<TextInfo> infoSet = new HashSet<>(64);
        /**
         * 日志
         */
        protected Log log;
        /**
         * 是否打印日志
         */
        protected boolean isPrint;
        /**
         * 页面索引
         */
        protected Integer pageIndex;

        /**
         * 有参构造
         *
         * @param pageIndex 页面索引
         * @param log       日志
         * @param isPrint   是否打印日志
         */
        public DefaultTextStripper(Integer pageIndex, Log log, boolean isPrint) {
            this(pageIndex, " ", log);
        }

        /**
         * 有参构造
         *
         * @param pageIndex     页面索引
         * @param wordSeparator 单词分隔符
         * @param log           日志
         */
        public DefaultTextStripper(Integer pageIndex, String wordSeparator, Log log) {
            this.log = log;
            this.pageIndex = pageIndex;
            this.setSortByPosition(false);
            this.setStartPage(this.pageIndex + 1);
            this.setEndPage(this.pageIndex + 1);
            this.setWordSeparator(wordSeparator);
        }

        /**
         * 写入字符串
         *
         * @param content       文本内容
         * @param textPositions 文本坐标列表
         */
        @Override
        protected void writeString(String content, List<TextPosition> textPositions) {
            // 如果文本内容不为空，则分析文本
            if (Objects.nonNull(content) && !content.trim().isEmpty()) {
                // 获取当前页面尺寸
                PDRectangle rectangle = this.getCurrentPage().getMediaBox();
                // 获取页面宽度
                float width = rectangle.getWidth();
                // 获取页面高度
                float height = rectangle.getHeight();
                // 获取文本起始定位
                TextPosition begin = textPositions.get(0);
                // 获取文本结束定位
                TextPosition end = textPositions.get(textPositions.size() - 1);
                // 构建文本信息
                TextInfo textInfo = TextInfo.builder()
                        .pageIndex(this.pageIndex)
                        .pageWidth(width)
                        .pageHeight(height)
                        .fontName(this.getFontName(begin.getFont().getName()))
                        .fontSize(begin.getFontSize())
                        .textContent(content)
                        .textBeginPosition(begin.getXDirAdj() + "," + (height - begin.getYDirAdj()))
                        .textEndPosition(end.getXDirAdj() + "," + (height - end.getYDirAdj()))
                        .textTotalWidth(end.getXDirAdj() - begin.getXDirAdj())
                        .build();
                // 添加文本列表
                this.infoSet.add(textInfo);
                // 如果日志打印开启，则打印日志
                if (this.isPrint && log.isDebugEnabled()) {
                    // 打印日志
                    log.debug(
                            "\n********************************************ANALYZE TEXT BEGIN********************************************" +
                                    "\npage index: " + textInfo.getPageIndex() +
                                    "\npage width: " + textInfo.getPageWidth() +
                                    "\npage height: " + textInfo.getPageHeight() +
                                    "\ntext font name: " + textInfo.getFontName() +
                                    "\ntext font size: " + textInfo.getFontSize() +
                                    "\ntext content: " + textInfo.getTextContent() +
                                    "\ntext begin position: " + textInfo.getTextBeginPosition() +
                                    "\ntext end position: " + textInfo.getTextEndPosition() +
                                    "\ntext total width: " + textInfo.getTextTotalWidth() +
                                    "\n*********************************************ANALYZE TEXT END*********************************************"
                    );
                }
            }
        }

        /**
         * 获取字体名称
         *
         * @param fontName 字体名称
         * @return 返回字体名称
         */
        protected String getFontName(String fontName) {
            // 如果字体名称不为空，则返回实际字体名称
            if (Objects.nonNull(fontName)) {
                // 返回实际字体名称
                return fontName.substring(fontName.indexOf('+') + 1);
            }
            return null;
        }
    }
}
