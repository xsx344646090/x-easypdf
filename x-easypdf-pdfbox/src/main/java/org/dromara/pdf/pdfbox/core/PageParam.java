package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * 页面参数
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
public class PageParam extends BaseParam {
    /**
     * pdf文档参数
     */
    private DocumentParam documentParam;
    /**
     * 任务页面
     */
    private PDPage target;
    /**
     * 页面尺寸
     */
    private PageRectangle rectangle;
    /**
     * 页面索引（当前页码）
     */
    private Integer index;

    /**
     * 获取X轴起始坐标
     *
     * @return 返回X轴起始坐标
     */
    public Float getBeginX() {
        return this.initBeginX(this.getMarginLeft());
    }

    /**
     * 获取Y轴起始坐标
     *
     * @return 返回Y轴起始坐标
     */
    public Float getBeginY() {
        return this.initBeginY(this.getMarginTop());
    }

    /**
     * 创建页面
     *
     * @return 返回页面
     */
    public Page createPage() {
        Page page = new Page(this.documentParam, this.rectangle);
        page.getParam().init(this);
        return page;
    }

    /**
     * 初始化
     *
     * @param index         页面索引
     * @param page          pdfbox页面对象
     * @param documentParam 文档参数
     */
    void init(Integer index, PDPage page, DocumentParam documentParam) {
        super.init(documentParam, true);
        this.documentParam = documentParam;
        this.target = page;
        this.rectangle = new PageRectangle(page.getMediaBox());
        this.index = index;

        this.documentParam.getTarget().addPage(this.target);
    }

    /**
     * 初始化
     *
     * @param pageParam 页面参数
     */
    void init(PageParam pageParam) {
        this.setMarginTop(pageParam.getMarginTop());
        this.setMarginBottom(pageParam.getMarginBottom());
        this.setMarginLeft(pageParam.getMarginLeft());
        this.setMarginRight(pageParam.getMarginRight());
        this.getFontParam().setFontName(pageParam.getFontParam().getFontName());
        this.getFontParam().setFont(pageParam.getFontParam().getFont());
        this.getFontParam().setFontSize(pageParam.getFontParam().getFontSize());
        this.getFontParam().setFontColor(pageParam.getFontParam().getFontColor());
        this.getFontParam().setCharacterSpacing(pageParam.getFontParam().getCharacterSpacing());
        this.getFontParam().setLeading(pageParam.getFontParam().getLeading());
        this.setContentMode(pageParam.getContentMode());
        this.setBeginX(null);
        this.setBeginY(null);
    }

    /**
     * 初始化边距
     *
     * @param margin 边距
     */
    void initMargin(float margin) {
        this.initMarginLeft(margin);
        this.setMarginRight(margin);
        this.initMarginTop(margin);
        this.setMarginBottom(margin);
    }

    /**
     * 初始化左边距
     *
     * @param margin 边距
     */
    void initMarginLeft(float margin) {
        this.setMarginLeft(margin);
        this.initBeginX(margin);
    }

    /**
     * 初始化上边距
     *
     * @param margin 边距
     */
    void initMarginTop(float margin) {
        this.setMarginTop(margin);
        this.initBeginY(margin);
    }

    /**
     * 初始化X轴起始坐标
     *
     * @param x 坐标
     * @return 返回X轴起始坐标
     */
    Float initBeginX(float x) {
        if (super.getBeginX() == null) {
            this.setBeginX(x);
        }
        return super.getBeginX();
    }

    /**
     * 初始化Y轴起始坐标
     *
     * @param y 坐标
     * @return 返回Y轴起始坐标
     */
    Float initBeginY(float y) {
        if (super.getBeginY() == null) {
            this.setBeginY(this.rectangle.getHeight() - y);
        }
        return super.getBeginY();
    }

    /**
     * 释放资源
     */
    void release() {
        this.documentParam = null;
        this.target = null;
    }
}
