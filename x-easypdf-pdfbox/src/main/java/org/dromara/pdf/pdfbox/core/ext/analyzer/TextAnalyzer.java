package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.TextPosition;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.TextInfo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

/**
 * 文本分析器
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
public class TextAnalyzer extends AbstractTextAnalyzer {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(TextAnalyzer.class);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public TextAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理文本
     *
     * @param pageIndex 页面索引
     */
    @SneakyThrows
    public void processText(int pageIndex) {
        // 初始化页面索引
        this.pageIndex = pageIndex;
        // 设置起始页面索引
        this.setStartPage(this.pageIndex + 1);
        // 设置结束页面索引
        this.setEndPage(this.pageIndex + 1);
        // 创建写入器
        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new ByteArrayOutputStream()))) {
            // 写入文本
            this.writeText(this.document.getTarget(), writer);
        }
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
            this.infoList.add(textInfo);
            // 如果日志打印开启，则打印日志
            if (log.isDebugEnabled()) {
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
