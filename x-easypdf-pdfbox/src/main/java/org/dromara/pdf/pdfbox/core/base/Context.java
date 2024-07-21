package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.component.BorderInfo;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

import java.util.*;

/**
 * 上下文
 *
 * @author xsx
 * @date 2023/9/4
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
@Data
public class Context {

    /**
     * 文档
     */
    protected Document document;
    /**
     * 当前页面
     */
    protected Page page;
    /**
     * 页面数量
     */
    protected Integer pageCount;
    /**
     * 执行中的组件类型
     */
    protected ComponentType executingComponentType;
    /**
     * 页眉
     */
    protected PageHeader pageHeader;
    /**
     * 页脚
     */
    protected PageFooter pageFooter;
    /**
     * 光标
     */
    protected Cursor cursor;
    /**
     * 分页Y轴偏移量
     */
    protected Float offsetY;
    /**
     * 换行起始坐标
     */
    protected Float wrapBeginX;
    /**
     * 换行宽度
     */
    protected Float wrapWidth;
    /**
     * 高度
     */
    protected Float height;
    /**
     * 是否第一个组件
     */
    protected Boolean isFirstComponent;
    /**
     * 是否已经分页
     */
    protected Boolean isAlreadyPaging;
    /**
     * 是否虚拟渲染
     */
    protected Boolean isVirtualRender;
    /**
     * 是否手动分页
     */
    protected Boolean isManualBreak;
    /**
     * 目录列表
     */
    protected List<CatalogInfo> catalogs;
    /**
     * 边框信息
     */
    protected BorderInfo borderInfo;
    /**
     * 自定义信息
     */
    protected Map<String, Object> customInfo;
    /**
     * 字体字典
     */
    protected Map<String, PDFont> fontMap;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public Context(Document document) {
        this.document = document;
        this.wrapBeginX = 0F;
        this.offsetY = 0F;
        this.cursor = new Cursor();
        this.isFirstComponent = Boolean.TRUE;
        this.isVirtualRender = Boolean.FALSE;
        this.isManualBreak = Boolean.FALSE;
        this.catalogs = new ArrayList<>(16);
        this.customInfo = new HashMap<>(16);
        this.fontMap = new HashMap<>(16);
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
     * 获取字体
     *
     * @param fontName 字体名称
     * @return 返回字体
     */
    public PDFont getFont(String fontName) {
        PDFont font = this.fontMap.get(fontName);
        if (Objects.isNull(font)) {
            this.addFontCache(fontName);
            font = this.fontMap.get(fontName);
        }
        return font;
    }

    /**
     * 是否相同组件
     *
     * @return 返回布尔值，是为true，否为false
     */
    public boolean isEqualsComponent(ComponentType type) {
        return this.executingComponentType == type;
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
     * 获取页眉高度
     *
     * @return 返回页眉高度
     */
    public float getPageHeaderHeight() {
        return Optional.ofNullable(this.pageHeader).map(PageHeader::getHeight).orElse(0F);
    }

    /**
     * 获取页脚高度
     *
     * @return 返回页脚高度
     */
    public float getPageFooterHeight() {
        return Optional.ofNullable(this.pageFooter).map(PageFooter::getHeight).orElse(0F);
    }
    
    /**
     * 获取最大X轴起始坐标
     *
     * @return 返回最大X轴起始坐标
     */
    public float getMaxBeginX() {
        return this.page.getWidth() - this.page.getMarginRight();
    }

    /**
     * 获取最大Y轴起始坐标
     *
     * @return 返回最大Y轴起始坐标
     */
    public float getMaxBeginY() {
        return this.page.getHeight() - this.page.getMarginTop() - this.getPageHeaderHeight();
    }

    /**
     * 添加字体缓存
     *
     * @param fontNames 字体名称
     */
    public void addFontCache(String... fontNames) {
        Objects.requireNonNull(fontNames, "the font names can not be null");
        for (String fontName : fontNames) {
            if (!this.fontMap.containsKey(fontName)) {
                this.fontMap.put(fontName, PdfHandler.getFontHandler().getPDFont(this.getTargetDocument(), fontName, true));
            }
        }
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
            // 累计页面数量
            this.pageCount = Optional.ofNullable(this.pageCount).orElse(0) + 1;
            // 重置页面
            this.page = page;
            // 重置高度
            this.resetHeight(this.height);
            // 重置光标
            this.resetCursor();
            // 重置换行起始坐标
            this.resetWrapBeginX(this.page.getMarginLeft());
            // 重置换行宽度
            this.resetWrapWidth(this.wrapWidth);
        }
    }

    /**
     * 重置光标
     */
    public void resetCursor() {
        // 重置光标
        this.cursor.reset(this.page.getMarginLeft(), this.page.getHeight() - this.page.getMarginTop());
    }

    /**
     * 重置光标
     */
    public void resetCursor(Float beginX, Float beginY) {
        // 重置光标
        this.cursor.reset(beginX, beginY);
    }

    /**
     * 重置光标X
     *
     * @param beginX x轴坐标
     */
    public void resetCursorX(Float beginX) {
        // 重置光标
        this.cursor.setX(beginX);
    }

    /**
     * 重置光标Y
     *
     * @param beginY y轴坐标
     */
    public void resetCursorY(Float beginY) {
        // 重置光标
        this.cursor.setY(beginY);
    }

    /**
     * 重置换行宽度
     *
     * @param wrapWidth 换行宽度
     */
    public void resetWrapWidth(Float wrapWidth) {
        // 换行宽度为空
        if (Objects.isNull(wrapWidth)) {
            // 重置为页面宽度
            this.wrapWidth = this.page.getWithoutMarginWidth();
        } else {
            // 重置为指定宽度
            this.wrapWidth = wrapWidth;
        }
    }

    /**
     * 重置高度
     *
     * @param height 高度
     */
    public void resetHeight(Float height) {
        // 高度为空
        if (Objects.isNull(height)) {
            // 重置为页面高度
            this.height = this.page.getWithoutMarginHeight() - this.getPageHeaderHeight() - this.getPageFooterHeight();
        } else {
            // 重置为指定高度
            this.height = height;
        }
    }

    /**
     * 重置换行起始坐标
     *
     * @param wrapBeginX 换行宽度
     */
    public void resetWrapBeginX(Float wrapBeginX) {
        // 换行宽度为空
        if (Objects.isNull(wrapBeginX)) {
            // 重置为页面左边距
            this.wrapBeginX = this.page.getMarginLeft();
        } else {
            // 重置为指定坐标
            this.wrapBeginX = wrapBeginX;
        }
    }

    /**
     * 重置Y轴偏移量
     *
     * @param offsetY 偏移量
     */
    public void resetOffsetY(Float offsetY) {
        // 偏移量为空
        if (Objects.isNull(offsetY)) {
            // 重置为0
            this.offsetY = 0F;
        } else {
            // 重置为指定偏移量
            this.offsetY = offsetY;
        }
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

    /**
     * 重置是否虚拟渲染
     *
     * @param isVirtualRender 是否虚拟渲染
     */
    public void resetIsVirtualRender(Boolean isVirtualRender) {
        // 是否虚拟渲染为空
        if (Objects.isNull(isVirtualRender)) {
            // 重置为false
            this.isVirtualRender = Boolean.FALSE;
        } else {
            // 重置为指定虚拟渲染
            this.isVirtualRender = isVirtualRender;
        }
    }

    /**
     * 清理
     */
    public void clear() {
        // 重置文档
        this.document = null;
        // 重置页面
        this.page = null;
        // 重置页眉
        this.pageHeader = null;
        // 重置页脚
        this.pageFooter = null;
        // 重置边框信息
        this.borderInfo = null;
        // 清理字体
        this.fontMap.clear();
    }
}
