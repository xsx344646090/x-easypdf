package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.enums.PageJoinType;
import org.dromara.pdf.pdfbox.core.enums.RotationAngle;

import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * 页面处理器
 *
 * @author xsx
 * @date 2023/11/13
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
public class PageProcessor extends AbstractProcessor {
    
    /**
     * 有参构造
     *
     * @param document 文档
     */
    public PageProcessor(Document document) {
        super(document);
    }
    
    /**
     * 获取页面
     *
     * @return 返回页面
     */
    public List<Page> getPages() {
        return this.document.getPages();
    }
    
    /**
     * 插入页面
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void insert(int index, Page page) {
        // 参数校验
        Objects.requireNonNull(page, "the page can not be null");
        try {
            // 添加页面
            this.document.getPages().add(index, page);
            // 遍历
            while (true) {
                // 索引自增
                index++;
                // 获取子页面
                page = page.getSubPage();
                // 子页面不存在结束
                if (Objects.isNull(page)) {
                    break;
                }
                // 添加子页面
                this.document.getPages().add(index, page);
            }
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
    }
    
    /**
     * 追加页面
     *
     * @param page 页面
     */
    public void append(Page page) {
        // 参数校验
        Objects.requireNonNull(page, "the page can not be null");
        // 添加页面
        this.document.getPages().add(page);
        // 遍历
        while (true) {
            // 获取子页面
            page = page.getSubPage();
            // 子页面不存在结束
            if (Objects.isNull(page)) {
                break;
            }
            // 添加子页面
            this.document.getPages().add(page);
        }
    }
    
    /**
     * 设置页面（替换）
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void set(int index, Page page) {
        // 参数校验
        Objects.requireNonNull(page, "the page can not be null");
        try {
            // 设置页面
            this.document.getPages().set(index, page);
            // 遍历
            while (true) {
                // 索引自增
                index++;
                // 获取子页面
                page = page.getSubPage();
                // 子页面不存在结束
                if (Objects.isNull(page)) {
                    break;
                }
                // 添加子页面
                this.document.getPages().add(index, page);
            }
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
    }
    
    /**
     * 移除页面
     *
     * @param indexes 页面索引
     */
    public void remove(int... indexes) {
        // 参数校验
        Objects.requireNonNull(indexes, "the indexes can not be null");
        // 创建临时列表
        List<Page> temp = new ArrayList<>(this.document.getPages());
        // 遍历索引
        for (int index : indexes) {
            try {
                // 移除页面
                this.document.getPages().remove(temp.get(index));
            } catch (Exception e) {
                // 提示信息
                log.warn("the index['" + index + "'] is invalid, will be ignored");
            }
        }
    }
    
    /**
     * 拼接页面
     *
     * @param type    拼接类型
     * @param newPage 新页面
     * @param pages   页面列表
     */
    @SneakyThrows
    public void join(PageJoinType type, Page newPage, Page... pages) {
        // 参数校验
        Objects.requireNonNull(type, "the type can not be null");
        Objects.requireNonNull(type, "the new page can not be null");
        Objects.requireNonNull(pages, "the pages can not be null");
        // 拼接页面
        this.join(type, null, null, newPage, pages);
    }
    
    /**
     * 拼接页面
     *
     * @param type    拼接类型
     * @param beginX  X轴起始坐标
     * @param beginY  Y轴起始坐标
     * @param newPage 新页面
     * @param pages   页面列表
     */
    @SneakyThrows
    public void join(PageJoinType type, Float beginX, Float beginY, Page newPage, Page... pages) {
        // 定义x轴坐标
        float x = Optional.ofNullable(beginX).orElse(0F);
        // 定义Y轴坐标
        float y = Optional.ofNullable(beginY).orElse(newPage.getHeight());
        // 遍历页面
        for (Page page : pages) {
            // 垂直拼接
            if (type == PageJoinType.VERTICAL) {
                // y轴坐标-页面高度
                y = y - page.getHeight();
            }
            // 定义层级
            LayerUtility layer = new LayerUtility(this.document.getTarget());
            // 定义页面
            PDFormXObject form = layer.importPageAsForm(page.getContext().getTargetDocument(), page.getTarget());
            // 添加页面
            layer.appendFormAsLayer(newPage.getTarget(), form, AffineTransform.getTranslateInstance(x, y), UUID.randomUUID().toString());
            // 水平拼接
            if (type == PageJoinType.HORIZONTAL) {
                // x轴坐标+页面宽度
                x = x + page.getWidth();
            }
        }
        // 添加页面
        if (!this.document.getPages().contains(newPage)) {
            this.document.getPages().add(newPage);
        }
    }
    
    /**
     * 重组页面
     *
     * @param indexes 页面索引
     */
    public void restructure(int... indexes) {
        // 参数校验
        Objects.requireNonNull(indexes, "the indexes can not be null");
        // 获取页面列表
        List<Page> pages = this.document.getPages();
        // 创建新列表
        List<Page> newPages = new ArrayList<>(indexes.length);
        // 遍历索引
        for (int index : indexes) {
            try {
                // 添加页面
                newPages.add(pages.get(index));
            } catch (Exception e) {
                // 提示信息
                log.warn("the index['" + index + "'] is invalid, will be ignored");
            }
        }
        // 重置页面列表
        this.document.setPages(newPages);
    }
    
    /**
     * 重排序页面
     *
     * @param indexes 页面索引
     */
    public void resort(int... indexes) {
        // 参数校验
        Objects.requireNonNull(indexes, "the indexes can not be null");
        // 获取页面列表
        List<Page> pages = this.document.getPages();
        // 创建排序列表
        List<Page> orderPages = new ArrayList<>(pages.size());
        // 遍历索引
        for (int index : indexes) {
            try {
                // 添加页面
                orderPages.add(pages.remove(index));
            } catch (Exception e) {
                // 提示信息
                log.warn("the index['" + index + "'] is invalid, will be ignored");
            }
        }
        // 添加剩余页面
        orderPages.addAll(pages);
        // 重置页面列表
        this.document.setPages(orderPages);
    }
    
    /**
     * 旋转页面
     *
     * @param angle 角度
     */
    public void rotation(RotationAngle angle, int... pageIndexes) {
        // 参数校验
        Objects.requireNonNull(angle, "the rotation angle can not be null");
        // 获取页面树
        List<Page> pages = this.getPages();
        // 页面索引存在
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                try {
                    // 旋转页面
                    pages.get(index).rotation(angle);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        } else {
            // 遍历页面树
            for (Page page : pages) {
                // 旋转页面
                page.rotation(angle);
            }
        }
    }
    
    /**
     * 缩放页面
     *
     * @param rectangle 页面尺寸
     */
    public void scale(PageSize rectangle, int... pageIndexes) {
        // 参数校验
        Objects.requireNonNull(rectangle, "the rectangle can not be null");
        // 获取页面列表
        List<Page> pages = this.document.getPages();
        // 页面索引存在
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                try {
                    // 缩放页面
                    pages.get(index).scale(rectangle);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        } else {
            // 遍历页面
            for (Page page : pages) {
                // 缩放页面
                page.scale(rectangle);
            }
        }
    }
    
    /**
     * 裁剪页面
     *
     * @param rectangle 页面尺寸
     */
    public void crop(PageSize rectangle, int... pageIndexes) {
        // 参数校验
        Objects.requireNonNull(rectangle, "the rectangle can not be null");
        // 获取页面列表
        List<Page> pages = this.document.getPages();
        // 页面索引存在
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            for (int index : pageIndexes) {
                try {
                    // 裁剪页面
                    pages.get(index).crop(rectangle);
                } catch (Exception e) {
                    // 提示信息
                    log.warn("the index['" + index + "'] is invalid, will be ignored");
                }
            }
        } else {
            // 遍历页面
            for (Page page : pages) {
                // 裁剪页面
                page.crop(rectangle);
            }
        }
    }
    
    /**
     * 重置尺寸（恢复原有尺寸）
     */
    public void resetSize() {
        this.document.getPages().forEach(Page::resetRectangle);
    }
    
    /**
     * 刷新页面
     */
    public void flush() {
        // 遍历页面
        for (int i = 0; i < this.document.getPages().size(); i++) {
            // 设置索引
            this.document.getPages().get(i).setIndex(i);
        }
        // 获取pdfbox页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 移除页面
        pageTree.forEach(pageTree::remove);
        // 重新添加
        this.document.getPages().forEach(page -> this.getDocument().addPage(page.getTarget()));
    }
}
