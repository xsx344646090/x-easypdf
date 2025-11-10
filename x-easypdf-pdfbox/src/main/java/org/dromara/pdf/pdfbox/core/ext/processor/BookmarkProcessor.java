package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 书签处理器
 *
 * @author xsx
 * @date 2023/11/15
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
public class BookmarkProcessor extends AbstractProcessor {

    /**
     * pdfbox书签节点列表
     */
    protected List<PDOutlineItem> itemList = new ArrayList<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public BookmarkProcessor(Document document) {
        super(document);
        this.initOutlineItem();
    }

    /**
     * 获取书签
     *
     * @return 返回书签
     */
    public List<PDOutlineItem> getItems() {
        return this.itemList;
    }

    /**
     * 插入书签
     *
     * @param index 书签索引
     * @param item  书签
     */
    public void insert(int index, PDOutlineItem item) {
        Objects.requireNonNull(item, "the item can not be null");
        try {
            // 插入书签
            this.itemList.add(index, item);
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
    }

    /**
     * 追加书签
     *
     * @param item 书签
     */
    public void append(PDOutlineItem item) {
        Objects.requireNonNull(item, "the item can not be null");
        // 添加书签
        this.itemList.add(item);
    }

    /**
     * 设置书签（替换）
     *
     * @param index 书签索引
     * @param item  书签
     */
    public void set(int index, PDOutlineItem item) {
        Objects.requireNonNull(item, "the item can not be null");
        try {
            // 设置书签
            this.itemList.set(index, item);
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
    }

    /**
     * 移除书签
     *
     * @param indexes 书签索引
     */
    public void remove(int... indexes) {
        // 移除全部
        if (Objects.isNull(indexes)) {
            // 重置书签
            this.itemList = new ArrayList<>(0);
        } else {
            // 遍历书签索引
            for (int index : indexes) {
                try {
                    // 移除书签
                    this.itemList.remove(index);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        }
    }

    /**
     * 刷新书签
     */
    public void flush() {
        // 创建书签
        PDDocumentOutline root = new PDDocumentOutline();
        // 添加书签
        this.itemList.forEach(root::addLast);
        // 设置书签
        this.getDocument().getDocumentCatalog().setDocumentOutline(root);
    }

    /**
     * 初始化书签
     */
    protected void initOutlineItem() {
        // 获取书签
        PDDocumentOutline documentOutline = this.getDocument().getDocumentCatalog().getDocumentOutline();
        // 如果书签不为空，则获取书签节点
        if (Objects.nonNull(documentOutline)) {
            // 添加书签节点列表
            documentOutline.children().forEach(this.itemList::add);
        }
    }
}
