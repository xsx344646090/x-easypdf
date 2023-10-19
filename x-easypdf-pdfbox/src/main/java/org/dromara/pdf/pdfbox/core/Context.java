package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.pdfbox.core.component.ContainerInfo;
import org.dromara.pdf.pdfbox.core.component.PageFooter;
import org.dromara.pdf.pdfbox.core.component.PageHeader;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.enums.ContentMode;

import java.util.*;

/**
 * 上下文
 *
 * @author xsx
 * @date 2023/9/4
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
public class Context {

    /**
     * 内容模式
     */
    private ContentMode contentMode;
    /**
     * 是否重置内容流
     */
    private Boolean isResetContentStream;
    /**
     * 文档
     */
    private Document document;
    /**
     * 当前页面
     */
    private Page page;
    /**
     * 执行中的组件类型
     */
    private ComponentType executingComponentType;
    /**
     * 页眉
     */
    private PageHeader pageHeader;
    /**
     * 页脚
     */
    private PageFooter pageFooter;
    /**
     * 游标
     */
    private Cursor cursor;
    /**
     * 是否已经分页
     */
    private Boolean isAlreadyPaging;
    /**
     * 换行起始坐标
     */
    private Float wrapBeginX;
    /**
     * 换行宽度
     */
    private Float wrapWidth;
    /**
     * 换行高度
     */
    private Float wrapHeight;
    /**
     * 目录列表
     */
    private List<CatalogInfo> catalogs;
    /**
     * 容器信息
     */
    private ContainerInfo containerInfo;

    /**
     * 自定义信息
     */
    private Map<String, Object> customInfo;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public Context(Document document) {
        this.document = document;
        this.cursor = new Cursor();
        this.catalogs = new ArrayList<>(16);
        this.customInfo = new HashMap<>();
    }

    /**
     * 获取任务文档
     *
     * @return 返回任务文档
     */
    public PDDocument getTargetDocument() {
        return this.document.getTarget();
    }

    /**
     * 获取任务页面
     *
     * @return 返回任务页面
     */
    public PDPage getTargetPage() {
        return this.page.getTarget();
    }

    /**
     * 是否首行
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isFirstLine() {
        if (this.hasPageHeader()) {
            return this.cursor.getY() == this.page.getHeight() - this.page.getMarginTop() - this.pageHeader.getHeight();
        }
        return this.cursor.getY() == this.page.getHeight() - this.page.getMarginTop();
    }

    /**
     * 是否容器换行
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isContainerWrap() {
        return this.containerInfo != null && this.containerInfo.getIsFirstComponent();
    }

    /**
     * 是否有页眉
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean hasPageHeader() {
        return Objects.nonNull(this.pageHeader);
    }

    /**
     * 是否有页脚
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean hasPageFooter() {
        return Objects.nonNull(this.pageFooter);
    }

    /**
     * 重置
     *
     * @param page 页面
     */
    public void reset(Page page) {
        // 初始化是否已经分页
        this.isAlreadyPaging = !page.getId().equals(Optional.ofNullable(this.page).map(Page::getId).orElse(null));
        // 已经分页
        if (this.isAlreadyPaging) {
            // 重置页面
            this.page = page;
            // 重置游标
            this.cursor.reset(
                    this.page.getMarginLeft(),
                    this.page.getHeight() - this.page.getMarginTop()
            );
            // 重置换行宽度
            this.resetWrapWidth(this.wrapWidth);
        }
    }

    /**
     * 重置换行宽度
     *
     * @param wrapWidth 换行宽度
     */
    public Float resetWrapWidth(Float wrapWidth) {
        // 获取原换行宽度
        Float old = this.wrapWidth;
        // 换行宽度为空
        if (Objects.isNull(wrapWidth)) {
            // 重置为页面宽度
            this.wrapWidth = this.page.getWithoutMarginWidth();
        } else {
            // 重置为指定宽度
            this.wrapWidth = wrapWidth;
        }
        // 返回原换行宽度
        return old;
    }

    /**
     * 重置换行高度
     *
     * @param wrapHeight 换行高度
     * @return 返回原换行高度
     */
    public Float resetWrapHeight(Float wrapHeight) {
        // 获取原换行高度
        Float old = this.wrapHeight;
        // 换行高度为空
        if (Objects.isNull(wrapHeight)) {
            // 重置为0
            this.wrapHeight = 0F;
        } else {
            // 重置为指定高度
            this.wrapHeight = wrapHeight;
        }
        // 返回原换行高度
        return old;
    }

    /**
     * 重置执行中的组件类型
     *
     * @param type 组件类型
     */
    public void resetExecutingComponentType(ComponentType type) {
        // 重置执行中的组件类型
        if (type == this.executingComponentType) {
            this.executingComponentType = null;
        }
    }
}
