package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.*;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.BookmarkInfo;
import org.dromara.pdf.pdfbox.util.ColorUtil;

import java.util.*;

/**
 * 抽象书签分析器
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
public abstract class AbstractBookmarkAnalyzer extends AbstractAnalyzer {

    /**
     * 书签信息列表
     */
    protected final Set<BookmarkInfo> infoSet = new HashSet<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractBookmarkAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理书签
     *
     * @param bookmarkIndex 书签索引
     */
    public abstract void processOutlineItem(int... bookmarkIndex);

    /**
     * 转为书签列表
     *
     * @param itemIterable 书签列表
     * @return 返回书签列表
     */
    protected List<PDOutlineItem> toOutlineItemList(Iterable<PDOutlineItem> itemIterable) {
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
     * @param set             书签列表
     * @param documentCatalog pdfbox目录
     * @param outlineItem     pdfbox书签
     */
    protected void processOutlineItem(Set<BookmarkInfo> set, PDDocumentCatalog documentCatalog, PDOutlineItem outlineItem) {
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
        // 处理页面目标
        BookmarkInfo bookmarkInfo = this.processPageDestination(outlineItem, destination);
        // 没有书签
        if (Objects.isNull(bookmarkInfo)) {
            // 返回
            return;
        } else {
            // 处理子书签
            bookmarkInfo.setChildren(this.processOutlineItemChildren(documentCatalog, outlineItem));
            // 添加书签
            set.add(bookmarkInfo);
        }
        // 处理名称目标
        bookmarkInfo = this.processNamedDestination(documentCatalog, destination);
        // 没有书签
        if (Objects.isNull(bookmarkInfo)) {
            // 返回
            return;
        } else {
            // 处理子书签
            bookmarkInfo.setChildren(this.processOutlineItemChildren(documentCatalog, outlineItem));
            // 添加书签
            set.add(bookmarkInfo);
        }
        // 处理书签信息
        this.processBookmarkInfo(set);
    }

    /**
     * 处理子书签
     *
     * @param documentCatalog pdfbox目录
     * @param outlineItem     pdfbox书签
     * @return 返回书签列表
     */
    protected Set<BookmarkInfo> processOutlineItemChildren(PDDocumentCatalog documentCatalog, PDOutlineItem outlineItem) {
        // 有子书签
        if (outlineItem.hasChildren()) {
            // 定义书签列表
            Set<BookmarkInfo> children = new HashSet<>(64);
            // 获取子书签
            Iterable<PDOutlineItem> items = outlineItem.children();
            // 遍历子书签
            for (PDOutlineItem item : items) {
                // 处理书签
                this.processOutlineItem(children, documentCatalog, item);
            }
            // 返回书签列表
            return children;
        }
        return null;
    }

    /**
     * 处理目标
     *
     * @param outlineItem pdfbox书签
     * @return 返回pdfbox目标
     */
    @SneakyThrows
    protected PDDestination processDestination(PDOutlineItem outlineItem) {
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
     * @param outlineItem pdfbox书签
     * @param destination pdfbox目标
     * @return 返回书签信息
     */
    @SneakyThrows
    protected BookmarkInfo processPageDestination(PDOutlineItem outlineItem, PDDestination destination) {
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
                // 设置颜色
                info.setColor(ColorUtil.toColor(outlineItem.getTextColor()));
                // 设置是否粗体
                info.setIsBold(outlineItem.isBold());
                // 设置是否斜体
                info.setIsItalic(outlineItem.isItalic());
                // 设置是否展开
                info.setIsOpen(outlineItem.isNodeOpen());
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 返回书签
                return info;
            }
            // 如果书签目标为页面合适的宽度目标，则设置书签信息
            if (destination instanceof PDPageFitWidthDestination) {
                // 转换为页面合适的宽度目标
                PDPageFitWidthDestination xyzDestination = (PDPageFitWidthDestination) destination;
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 返回书签
                return info;
            }
            // 如果书签目标为页面合适的尺寸目标，则设置书签信息
            if (destination instanceof PDPageFitRectangleDestination) {
                // 转换为页面合适的尺寸目标
                PDPageFitRectangleDestination xyzDestination = (PDPageFitRectangleDestination) destination;
                // 设置起始页面索引
                info.setBeginPageIndex(xyzDestination.retrievePageNumber());
                // 设置起始页面顶点Y轴坐标
                info.setBeginPageTopY(xyzDestination.getTop());
                // 返回书签
                return info;
            }
            // 返回书签
            return info;
        }
        return null;
    }

    /**
     * 处理名称目标
     *
     * @param documentCatalog pdfbox目录
     * @param destination     pdfbox目标
     */
    @SneakyThrows
    protected BookmarkInfo processNamedDestination(PDDocumentCatalog documentCatalog, PDDestination destination) {
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
            }
            return info;
        }
        return null;
    }

    /**
     * 处理书签信息
     *
     * @param set 书签列表
     */
    protected void processBookmarkInfo(Set<BookmarkInfo> set) {
        // 转为list
        List<BookmarkInfo> list = new ArrayList<>(set);
        // 如果书签列表不为空，则处理书签
        if (!list.isEmpty()) {
            // 获取pdfbox页面树
            PDPageTree pages = this.getDocument().getPages();
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
                                        "\ncolor: " + current.getColor() +
                                        "\nbold: " + current.getIsBold() +
                                        "\nitalic: " + current.getIsItalic() +
                                        "\nopen: " + current.getIsOpen() +
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