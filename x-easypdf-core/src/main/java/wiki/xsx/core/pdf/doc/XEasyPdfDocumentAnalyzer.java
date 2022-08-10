package wiki.xsx.core.pdf.doc;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.contentstream.operator.state.*;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.*;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import wiki.xsx.core.pdf.util.XEasyPdfConvertUtil;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pdf文档分析器
 *
 * @author xsx
 * @date 2022/4/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfDocumentAnalyzer implements Serializable {

    private static final long serialVersionUID = -5122489986081853881L;

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(XEasyPdfDocumentAnalyzer.class);
    /**
     * pdfbox文档
     */
    private transient final PDDocument document;
    /**
     * pdf文档
     */
    private final XEasyPdfDocument pdfDocument;
    /**
     * 文本信息列表
     */
    private transient List<TextInfo> textInfoList;
    /**
     * 图像信息列表
     */
    private transient List<ImageInfo> imageInfoList;
    /**
     * 图像信息列表
     */
    private transient List<BookmarkInfo> bookmarkInfoList;

    /**
     * 有参构造
     *
     * @param pdfDocument pdf文档
     */
    @SneakyThrows
    XEasyPdfDocumentAnalyzer(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.build(true);
    }

    /**
     * 分析文本
     *
     * @param pageIndex 页面索引
     * @return 返回pdf文档分析器
     */
    @SneakyThrows
    public XEasyPdfDocumentAnalyzer analyzeText(int... pageIndex) {
        // 创建文本分析器
        TextAnalyzer analyzer = new TextAnalyzer(this.document);
        // 如果给定页面索引为空，则处理文档所有页面文本
        if (pageIndex == null || pageIndex.length == 0) {
            // 遍历文档页面索引
            for (int i = 0, count = this.document.getNumberOfPages(); i < count; i++) {
                // 处理文本
                analyzer.processText(i);
            }
        }
        // 否则处理给定页面索引文本
        else {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 如果页面索引大于等于0，则处理文本
                if (index >= 0) {
                    // 处理文本
                    analyzer.processText(index);
                }
            }
        }
        // 重置文本信息列表
        this.textInfoList = analyzer.textInfoList;
        return this;
    }

    /**
     * 分析图像
     *
     * @param pageIndex 页面索引
     * @return 返回pdf文档分析器
     */
    @SneakyThrows
    public XEasyPdfDocumentAnalyzer analyzeImage(int... pageIndex) {
        // 创建图像分析器
        ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
        // 获取页面树
        PDPageTree pages = this.document.getPages();
        // 如果给定页面索引为空，则处理文档所有页面图像
        if (pageIndex == null || pageIndex.length == 0) {
            // 遍历文档页面索引
            for (int i = 0, count = pages.getCount(); i < count; i++) {
                // 设置页面索引并处理页面图像
                imageAnalyzer.processImage(i, pages.get(i));
            }
        }
        // 否则处理给定页面索引图像
        else {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 如果页面索引大于等于0，则处理图像
                if (index >= 0) {
                    // 设置页面索引并处理页面图像
                    imageAnalyzer.processImage(index, pages.get(index));
                }
            }
        }
        // 重置图像信息列表
        this.imageInfoList = imageAnalyzer.imageInfoList;
        return this;
    }

    /**
     * 分析书签
     *
     * @param bookmarkIndex 书签索引
     * @return 返回pdf文档分析器
     */
    @SneakyThrows
    public XEasyPdfDocumentAnalyzer analyzeBookmark(int... bookmarkIndex) {
        // 重置书签信息列表
        this.bookmarkInfoList = this.processOutlineItem(this.document.getDocumentCatalog(), bookmarkIndex);
        return this;
    }

    /**
     * 获取文本信息列表
     *
     * @return 返回文本信息列表
     */
    public List<TextInfo> getTextInfoList() {
        return this.textInfoList;
    }

    /**
     * 获取图像信息列表
     *
     * @return 返回图像信息列表
     */
    public List<ImageInfo> getImageInfoList() {
        return this.imageInfoList;
    }

    /**
     * 获取书签信息列表
     *
     * @return 返回书签信息列表
     */
    public List<BookmarkInfo> getBookmarkInfoList() {
        return this.bookmarkInfoList;
    }

    /**
     * 完成操作
     */
    public void finish() {
        this.pdfDocument.close();
    }

    /**
     * 文本信息
     */
    @Builder
    @Data
    public static class TextInfo implements Serializable {

        private static final long serialVersionUID = 8466964755899811503L;

        /**
         * 页面索引
         */
        private Integer pageIndex;
        /**
         * 页面宽度
         */
        private Float pageWidth;
        /**
         * 页面高度
         */
        private Float pageHeight;
        /**
         * 字体名称
         */
        private String fontName;
        /**
         * 字体大小
         */
        private Float fontSize;
        /**
         * 文本内容
         */
        private String textContent;
        /**
         * 文本起始位置坐标
         */
        private String textBeginPosition;
        /**
         * 文本结束位置坐标
         */
        private String textEndPosition;
        /**
         * 文本总宽度
         */
        private Float textTotalWidth;
    }

    /**
     * 图片信息
     */
    @Builder
    @Data
    public static class ImageInfo implements Serializable {

        private static final long serialVersionUID = -3274677412926972735L;

        /**
         * 页面索引
         */
        private Integer pageIndex;
        /**
         * 页面宽度
         */
        private Float pageWidth;
        /**
         * 页面高度
         */
        private Float pageHeight;
        /**
         * 图像索引
         */
        private Integer imageIndex;
        /**
         * 图像类型
         */
        private String imageType;
        /**
         * 图像真实宽度
         */
        private Integer imageRealWidth;
        /**
         * 图像真实高度
         */
        private Integer imageRealHeight;
        /**
         * 图像显示宽度
         */
        private Integer imageDisplayWidth;
        /**
         * 图像显示高度
         */
        private Integer imageDisplayHeight;
        /**
         * 图像位置坐标
         */
        private String imagePosition;
        /**
         * 图像
         */
        private BufferedImage image;
    }

    /**
     * 书签信息
     */
    @Builder
    @Data
    public static class BookmarkInfo implements Serializable {

        private static final long serialVersionUID = 9113863764818167947L;

        /**
         * 标题
         */
        private String title;
        /**
         * 起始页面索引
         */
        private Integer beginPageIndex;
        /**
         * 结束页面索引
         */
        private Integer endPageIndex;
        /**
         * 起始页面顶部Y轴坐标
         */
        private Integer beginPageTopY;
        /**
         * 起始页面底部Y轴坐标
         */
        private Integer beginPageBottomY;
        /**
         * 结束页面顶部Y轴坐标
         */
        private Integer endPageTopY;
        /**
         * 结束页面底部Y轴坐标
         */
        private Integer endPageBottomY;
    }

    /**
     * 文本分析器
     */
    private static class TextAnalyzer extends PDFTextStripper implements Serializable {

        private static final long serialVersionUID = 7087714696135717794L;

        /**
         * 页面索引
         */
        private Integer pageIndex;
        /**
         * pdf文档
         */
        private final PDDocument document;
        /**
         * 文本信息列表
         */
        private final List<TextInfo> textInfoList = new ArrayList<>(256);

        /**
         * 有参构造
         *
         * @throws IOException IO异常
         */
        TextAnalyzer(PDDocument document) throws IOException {
            this.document = document;
            this.setSortByPosition(true);
        }

        /**
         * 处理文本
         *
         * @param pageIndex 页面索引
         */
        @SneakyThrows
        protected void processText(int pageIndex) {
            // 初始化页面索引
            this.pageIndex = pageIndex;
            // 设置起始页面索引
            this.setStartPage(this.pageIndex + 1);
            // 设置结束页面索引
            this.setEndPage(this.pageIndex + 1);
            // 创建写入器
            try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new ByteArrayOutputStream()))) {
                // 写入文本
                this.writeText(this.document, writer);
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
            if (content != null && content.trim().length() > 0) {
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
                this.textInfoList.add(textInfo);
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
        private String getFontName(String fontName) {
            // 如果字体名称不为空，则返回实际字体名称
            if (fontName != null) {
                // 返回实际字体名称
                return fontName.substring(fontName.indexOf('+') + 1);
            }
            return null;
        }
    }

    /**
     * 图像分析器
     */
    private static class ImageAnalyzer extends PDFStreamEngine implements Serializable {

        private static final long serialVersionUID = 2131598546867279339L;

        /**
         * 页面索引
         */
        private Integer pageIndex;
        /**
         * 图像索引
         */
        private Integer imageIndex;
        /**
         * 图像信息列表
         */
        private final List<ImageInfo> imageInfoList = new ArrayList<>(16);

        /**
         * 无参构造
         */
        ImageAnalyzer() {
            this.addOperator(new Concatenate());
            this.addOperator(new DrawObject());
            this.addOperator(new SetGraphicsStateParameters());
            this.addOperator(new Save());
            this.addOperator(new Restore());
            this.addOperator(new SetMatrix());
        }

        /**
         * 处理图像
         *
         * @param pageIndex 页面索引
         * @param page      pdfbox页面
         */
        @SneakyThrows
        protected void processImage(int pageIndex, PDPage page) {
            this.pageIndex = pageIndex;
            this.imageIndex = 0;
            this.processPage(page);
        }

        /**
         * 处理操作标记
         *
         * @param operator 操作标记
         * @param operands 标记列表
         */
        @SneakyThrows
        @Override
        protected void processOperator(Operator operator, List<COSBase> operands) {
            // 如果操作标记名称为绘制对象，则分析图像
            if (OperatorName.DRAW_OBJECT.equals(operator.getName())) {
                // 获取pdf对象
                PDXObject xObject = this.getResources().getXObject((COSName) operands.get(0));
                // 如果对象为pdf图像，则分析图像
                if (xObject instanceof PDImageXObject) {
                    // 转换为pdf图像
                    PDImageXObject image = (PDImageXObject) xObject;
                    // 获取当前页面尺寸
                    PDRectangle rectangle = this.getCurrentPage().getMediaBox();
                    // 获取页面矩阵
                    Matrix matrix = this.getGraphicsState().getCurrentTransformationMatrix();
                    // 构建图像信息
                    ImageInfo imageInfo = ImageInfo.builder()
                            .pageIndex(this.pageIndex)
                            .pageWidth(rectangle.getWidth())
                            .pageHeight(rectangle.getHeight())
                            .imageIndex(this.imageIndex)
                            .imageType(image.getSuffix())
                            .imageRealWidth(image.getWidth())
                            .imageRealHeight(image.getHeight())
                            .imageDisplayWidth((int) matrix.getScalingFactorX())
                            .imageDisplayHeight((int) matrix.getScalingFactorY())
                            .imagePosition(this.getPosition(matrix, image.getWidth() == ((int) matrix.getScalingFactorX()), image.getHeight() == ((int) matrix.getScalingFactorY())))
                            .image(image.getImage())
                            .build();
                    // 添加图像列表
                    this.imageInfoList.add(imageInfo);
                    // 如果日志打印开启，则打印日志
                    if (log.isDebugEnabled()) {
                        // 打印日志
                        log.debug(
                                "\n********************************************ANALYZE IMAGE BEGIN********************************************" +
                                        "\npage index: " + imageInfo.getPageIndex() +
                                        "\npage width: " + imageInfo.getPageWidth() +
                                        "\npage height: " + imageInfo.getPageHeight() +
                                        "\nimage index: " + imageInfo.getImageIndex() +
                                        "\nimage type: " + imageInfo.getImageType() +
                                        "\nimage real width: " + imageInfo.getImageRealWidth() +
                                        "\nimage real height: " + imageInfo.getImageRealHeight() +
                                        "\nimage display width: " + imageInfo.getImageDisplayWidth() +
                                        "\nimage display height: " + imageInfo.getImageDisplayHeight() +
                                        "\nimage position: " + imageInfo.getImagePosition() +
                                        "\n*********************************************ANALYZE IMAGE END*********************************************"
                        );
                    }
                    // 图像索引自增
                    this.imageIndex++;
                }
                // 如果对象为pdf表单，则处理表单
                else if (xObject instanceof PDFormXObject) {
                    // 处理表单
                    this.showForm((PDFormXObject) xObject);
                }
            }
            // 否则使用父类方法处理
            else {
                // 使用父类方法处理
                super.processOperator(operator, operands);
            }
        }

        /**
         * 获取位置坐标
         *
         * @param matrix      页面矩阵
         * @param equalWidth  宽度相等
         * @param equalHeight 高度相等
         * @return 返回位置坐标
         */
        private String getPosition(Matrix matrix, boolean equalWidth, boolean equalHeight) {
            // 定义字符串构建器
            StringBuilder builder = new StringBuilder();
            // 如果真实宽度与显示宽度相等，且X轴坐标小于0，则添加原点坐标
            if (equalWidth && matrix.getTranslateX() < 0) {
                // 添加原点坐标
                builder.append("0.0");
            }
            // 否则添加实际X轴坐标
            else {
                // 添加实际X轴坐标
                builder.append(matrix.getTranslateX());
            }
            // 添加分隔符
            builder.append(',');
            // 如果真实高度与显示高度相等，且Y轴坐标小于0，则添加原点坐标
            if (equalHeight && matrix.getTranslateY() < 0) {
                // 添加原点坐标
                builder.append("0.0");
            }
            // 否则添加实际Y轴坐标
            else {
                // 添加实际Y轴坐标
                builder.append(matrix.getTranslateY());
            }
            return builder.toString();
        }
    }

    /**
     * 处理书签
     *
     * @param documentCatalog pdfbox目录
     * @param bookmarkIndex   书签索引
     * @return 返回书签信息列表
     */
    @SneakyThrows
    private List<BookmarkInfo> processOutlineItem(PDDocumentCatalog documentCatalog, int... bookmarkIndex) {
        // 定义书签信息列表
        List<BookmarkInfo> list = new ArrayList<>(16);
        // 获取pdfbox文档概要
        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
        // 如果文档概要不为空， 则解析书签信息
        if (documentOutline != null) {
            // 获取pdfbox书签列表
            List<PDOutlineItem> itemList = XEasyPdfConvertUtil.toList(documentOutline.children());
            // 如果书签索引不为空，则解析给定书签索引信息
            if (bookmarkIndex != null && bookmarkIndex.length > 0) {
                // 遍历书签索引
                for (int index : bookmarkIndex) {
                    // 如果书签索引大于0，则处理书签
                    if (index > 0) {
                        // 处理书签
                        this.processOutlineItem(list, documentCatalog, itemList.get(index));
                    }
                }
            }
            // 否则处理全部书签信息
            else {
                // 遍历pdfbox书签列表
                for (PDOutlineItem outlineItem : itemList) {
                    // 处理书签
                    this.processOutlineItem(list, documentCatalog, outlineItem);
                }
            }
        }
        return list;
    }

    /**
     * 处理书签
     *
     * @param list            书签列表
     * @param documentCatalog pdfbox目录
     * @param outlineItem     pdfbox书签
     */
    private void processOutlineItem(List<BookmarkInfo> list, PDDocumentCatalog documentCatalog, PDOutlineItem outlineItem) {
        // 获取书签目标
        PDDestination destination = this.processDestination(outlineItem);
        // 如果书签目标为空，则跳过
        if (destination == null) {
            // 返回
            return;
        }
        // 如果处理页面目标成功，则跳过
        if (this.processPageDestination(list, outlineItem, destination)) {
            // 返回
            return;
        }
        // 处理名称目标
        this.processNamedDestination(list, documentCatalog, destination);
        // 处理书签信息
        this.processBookmarkInfo(list);
    }

    /**
     * 处理目标
     *
     * @param outlineItem pdfbox书签
     * @return 返回pdfbox目标
     */
    @SneakyThrows
    private PDDestination processDestination(PDOutlineItem outlineItem) {
        // 获取书签目标
        PDDestination destination = outlineItem.getDestination();
        // 如果书签目标为空，则获取活动
        if (destination == null) {
            // 获取书签活动
            PDAction action = outlineItem.getAction();
            // 如果书签活动为转跳，则重置书签目标
            if (action instanceof PDActionGoTo) {
                // 重置书签目标为书签活动目标
                destination = ((PDActionGoTo) action).getDestination();
            }
        }
        return destination;
    }

    /**
     * 处理页面目标
     *
     * @param list        书签列表
     * @param outlineItem pdfbox书签
     * @param destination pdfbox目标
     * @return 返回布尔值，成功为true，失败为false
     */
    private boolean processPageDestination(List<BookmarkInfo> list, PDOutlineItem outlineItem, PDDestination destination) {
        // 如果书签目标为页面目标，则解析书签信息
        if (destination instanceof PDPageDestination) {
            // 创建书签信息
            BookmarkInfo info = BookmarkInfo.builder().build();
            // 如果书签目标为页面XYZ目标，则设置书签信息
            if (destination instanceof PDPageXYZDestination) {
                // 转换为页面XYZ目标
                PDPageXYZDestination xyzDestination = (PDPageXYZDestination) destination;
                // 设置标题
                info.setTitle(outlineItem.getTitle());
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 添加书签列表
                list.add(info);
                // 返回true
                return true;
            }
            // 如果书签目标为页面合适的宽度目标，则设置书签信息
            if (destination instanceof PDPageFitWidthDestination) {
                // 转换为页面合适的宽度目标
                PDPageFitWidthDestination xyzDestination = (PDPageFitWidthDestination) destination;
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 添加书签列表
                list.add(info);
                // 返回true
                return true;
            }
            // 如果书签目标为页面合适的尺寸目标，则设置书签信息
            if (destination instanceof PDPageFitRectangleDestination) {
                // 转换为页面合适的尺寸目标
                PDPageFitRectangleDestination xyzDestination = (PDPageFitRectangleDestination) destination;
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 添加书签列表
                list.add(info);
                // 返回true
                return true;
            }
        }
        return false;
    }

    /**
     * 处理名称目标
     *
     * @param list            书签列表
     * @param documentCatalog pdfbox目录
     * @param destination     pdfbox目标
     */
    @SneakyThrows
    private void processNamedDestination(List<BookmarkInfo> list, PDDocumentCatalog documentCatalog, PDDestination destination) {
        // 如果书签目标为名称目标，则设置书签信息
        if (destination instanceof PDNamedDestination) {
            // 创建书签信息
            BookmarkInfo info = BookmarkInfo.builder().build();
            // 获取页面目标
            PDPageDestination pageDestination = documentCatalog.findNamedDestinationPage((PDNamedDestination) destination);
            // 如果页面目标不为空，则添加书签信息
            if (pageDestination != null) {
                // 设置起始页面索引
                info.setBeginPageIndex(pageDestination.retrievePageNumber());
                // 添加书签列表
                list.add(info);
            }
        }
    }

    /**
     * 处理书签信息
     *
     * @param list 书签列表
     */
    private void processBookmarkInfo(List<BookmarkInfo> list) {
        // 如果书签列表不为空，则处理书签
        if (!list.isEmpty()) {
            // 获取pdfbox页面树
            PDPageTree pages = this.document.getPages();
            // 获取书签最大索引
            int maxIndex = list.size() - 1;
            // 如果书签最大索引为0，则说明只有一个书签
            if (maxIndex == 0) {
                // 获取书签信息
                BookmarkInfo bookmarkInfo = list.get(0);
                // 设置结束页面索引
                bookmarkInfo.setEndPageIndex(0);
                // 设置起始页面顶部Y轴坐标
                bookmarkInfo.setBeginPageTopY((int) pages.get(bookmarkInfo.getBeginPageIndex()).getMediaBox().getHeight());
                // 设置起始页面底部Y轴坐标
                bookmarkInfo.setBeginPageBottomY(0);
                // 设置结束页面顶部Y轴坐标
                bookmarkInfo.setEndPageTopY(bookmarkInfo.getBeginPageTopY());
                // 设置结束页面底部Y轴坐标
                bookmarkInfo.setEndPageBottomY(0);
            }
            // 否则遍历所有书签信息
            else {
                // 遍历书签信息
                for (int i = 0; i <= maxIndex; i++) {
                    // 获取当前书签信息
                    BookmarkInfo current = list.get(i);
                    // 如果书签索引等于最大书签索引，则说明为最后一个书签信息
                    if (i == maxIndex) {
                        // 设置起始页面底部Y轴坐标
                        current.setBeginPageBottomY(0);
                        // 设置结束页面索引
                        current.setEndPageIndex(pages.getCount() - 1);
                        // 设置结束页面顶部Y轴坐标
                        current.setEndPageTopY((int) pages.get(current.getEndPageIndex()).getMediaBox().getHeight());
                        // 设置结束页面底部Y轴坐标
                        current.setEndPageBottomY(0);
                    }
                    // 否则可以获取下一个书签信息
                    else {
                        // 获取下一个书签信息
                        BookmarkInfo next = list.get(i + 1);
                        // 如果当前书签信息起始页面索引等于下一个书签信息起始页面索引，则说明为同一页
                        if (current.getBeginPageIndex().equals(next.getBeginPageIndex())) {
                            // 设置起始页面底部Y轴坐标
                            current.setBeginPageBottomY(next.getBeginPageTopY());
                            // 设置结束页面索引
                            current.setEndPageIndex(current.getBeginPageIndex());
                            // 设置结束页面顶部Y轴坐标
                            current.setEndPageTopY(current.getBeginPageTopY());
                            // 设置结束页面底部Y轴坐标
                            current.setEndPageBottomY(current.getBeginPageBottomY());
                        }
                        // 否则当前书签占用整个页面
                        else {
                            // 设置起始页面底部Y轴坐标
                            current.setBeginPageBottomY(0);
                            // 设置结束页面索引
                            current.setEndPageIndex(next.getBeginPageIndex());
                            // 设置结束页面顶部Y轴坐标
                            current.setEndPageTopY((int) pages.get(current.getEndPageIndex()).getMediaBox().getHeight());
                            // 设置结束页面底部Y轴坐标
                            current.setEndPageBottomY(next.getBeginPageTopY());
                        }
                    }
                    // 如果日志打印开启，则打印日志
                    if (log.isDebugEnabled()) {
                        // 打印日志
                        log.debug(
                                "\n********************************************ANALYZE BOOKMARK BEGIN********************************************" +
                                        "\ntitle: " + current.getTitle() +
                                        "\nbegin page index: " + current.getBeginPageIndex() +
                                        "\nend page index: " + current.getEndPageIndex() +
                                        "\nbegin page top y: " + current.getBeginPageTopY() +
                                        "\nbegin page bottom y: " + current.getBeginPageBottomY() +
                                        "\nend page top y: " + current.getEndPageTopY() +
                                        "\nend page bottom y: " + current.getEndPageBottomY() +
                                        "\n*********************************************ANALYZE BOOKMARK END*********************************************"
                        );
                    }
                }
            }
        }
    }
}
