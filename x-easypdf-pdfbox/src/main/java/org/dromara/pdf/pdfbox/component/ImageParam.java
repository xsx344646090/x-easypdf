package org.dromara.pdf.pdfbox.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.dromara.pdf.pdfbox.core.BaseParam;
import org.dromara.pdf.pdfbox.core.PageParam;

/**
 * 图像参数
 *
 * @author xsx
 * @date 2023/6/30
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
public class ImageParam extends BaseParam {

    /**
     * 页面参数
     */
    private PageParam pageParam;
    /**
     * pdfbox图像对象
     */
    private PDImageXObject image;
    /**
     * 宽度（显示）
     */
    private Integer width;
    /**
     * 高度（显示）
     */
    private Integer height;
    /**
     * 相对起始X轴坐标（不影响后续坐标）
     */
    private Float relativeBeginX;
    /**
     * 相对起始Y轴坐标（不影响后续坐标）
     */
    private Float relativeBeginY;
    /**
     * 是否自定义X轴坐标
     */
    private Boolean isCustomX;

    /**
     * 初始化
     */
    void init() {
        // 初始化参数
        super.init(this.pageParam);
        // 初始化分页
        if (this.getIsBreak()) {
            this.pageParam = this.pageParam.createPage().getParam();
            super.init(this.pageParam);
            this.beginX = null;
            this.beginY = null;
        }
        // 初始化起始X轴坐标
        if (this.beginX == null) {
            this.beginX = this.pageParam.getBeginX();
            this.isCustomX = Boolean.FALSE;
        }
        // 初始化起始Y轴坐标
        if (this.beginY == null) {
            this.beginY = this.pageParam.getBeginY();
            if (this.beginY + this.pageParam.getMarginTop() == this.pageParam.getRectangle().getHeight()) {
                this.beginY = this.beginY - this.getFontParam().getFontSize();
            }
        }
        // 初始化相对起始X轴坐标
        if (this.relativeBeginX == null) {
            this.relativeBeginX = 0F;
        }
        // 初始化相对起始Y轴坐标
        if (this.relativeBeginY == null) {
            this.relativeBeginY = 0F;
        }
        // 初始化换行
        if (this.getIsWrap()) {
            this.beginX = this.pageParam.getMarginLeft();
            this.beginY = this.beginY - this.getFontParam().getFontSize() - this.getFontParam().getLeading();
            this.pageParam.setBeginX(this.beginX);
        }
        // 重置起始X轴坐标
        this.beginX = this.beginX + this.getMarginLeft();
        // 重置起始Y轴坐标
        this.beginY = this.beginY - this.getMarginTop();
        // 初始化图像
        this.initImage();
    }

    /**
     * 初始化图像
     */
    @SneakyThrows
    private void initImage() {
        if (this.image == null) {
            throw new IllegalArgumentException("the image can not be null");
        }
        if (this.width == null) {
            this.width = this.image.getWidth();
        }
        if (this.height == null) {
            this.height = this.image.getHeight();
        }
    }
}
