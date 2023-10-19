package org.dromara.pdf.pdfbox.core.ext;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.*;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.info.BookmarkInfo;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 书签分析器
 *
 * @author xsx
 * @date 2023/10/19
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
public class BookmarkAnalyzer implements Closeable {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(BookmarkAnalyzer.class);

    /**
     * pdf文档
     */
    private PDDocument document;

    /**
     * 有参构造
     *
     * @param document pdf文档
     */
    public BookmarkAnalyzer(PDDocument document) {
        this.document = document;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }

    /**
     * 处理书签
     *
     * @param bookmarkIndex 书签索引
     * @return 返回书签信息列表
     */
    @SneakyThrows
    protected List<BookmarkInfo> processOutlineItem(int... bookmarkIndex) {
        // 定义书签信息列表
        List<BookmarkInfo> list = new ArrayList<>(16);
        // 获取pdfbox目录
        PDDocumentCatalog documentCatalog = this.document.getDocumentCatalog();
        // 获取pdfbox文档概要
        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
        // 如果文档概要不为空， 则解析书签信息
        if (Objects.nonNull(documentOutline)) {
            // 如果书签索引不为空，则解析给定书签索引信息
            if (Objects.nonNull(bookmarkIndex) && bookmarkIndex.length > 0) {
                // 获取书签列表
                List<PDOutlineItem> items = this.toOutlineItemList(documentOutline.children());
                // 遍历书签索引
                for (int index : bookmarkIndex) {
                    // 如果书签索引大于0，则处理书签
                    if (index > 0) {
                        // 处理书签
                        this.processOutlineItem(list, documentCatalog, items.get(index));
                    }
                }
            }
            // 否则处理全部书签信息
            else {
                // 遍历pdfbox书签列表
                documentOutline.children().forEach(outlineItem -> this.processOutlineItem(list, documentCatalog, outlineItem));
            }
        }
        return list;
    }

    /**
     * 转为书签列表
     *
     * @param itemIterable 书签列表
     * @return 返回书签列表
     */
    private List<PDOutlineItem> toOutlineItemList(Iterable<PDOutlineItem> itemIterable) {
        // 定义列表
        List<PDOutlineItem> list = new ArrayList<>(16);
        // 书签列表不为空
        if (Objects.nonNull(itemIterable)) {
            // 添加书签
            itemIterable.forEach(list::add);
        }
        // 返回列表
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
        // 如果书签为空，则跳过
        if (Objects.isNull(outlineItem)) {
            // 返回
            return;
        }
        // 获取书签目标
        PDDestination destination = this.processDestination(outlineItem);
        // 如果书签目标为空，则跳过
        if (Objects.isNull(destination)) {
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
        if (Objects.isNull(destination)) {
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
            if (Objects.nonNull(pageDestination)) {
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