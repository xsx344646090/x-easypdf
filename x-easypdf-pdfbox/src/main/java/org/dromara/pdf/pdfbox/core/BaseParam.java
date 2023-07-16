package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.pdfbox.enums.ContentMode;
import org.dromara.pdf.pdfbox.enums.HorizontalAlignment;

import java.awt.*;

/**
 * @author xsx
 * @date 2023/6/2
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
public class BaseParam {

    /**
     * 上边距
     */
    protected Float marginTop;
    /**
     * 下边距
     */
    protected Float marginBottom;
    /**
     * 左边距
     */
    protected Float marginLeft;
    /**
     * 右边距
     */
    protected Float marginRight;
    /**
     * 内容模式
     */
    protected ContentMode contentMode;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 对齐方式
     */
    protected HorizontalAlignment horizontalAlignment;
    /**
     * 是否换行
     */
    protected Boolean isWrap;
    /**
     * 是否分页
     */
    protected Boolean isBreak;
    /**
     * 起始X轴坐标
     */
    protected Float beginX;
    /**
     * 起始Y轴坐标
     */
    protected Float beginY;
    /**
     * 字体参数
     */
    protected FontParam fontParam = new FontParam();
    /**
     * 边框参数
     */
    protected BorderParam borderParam = new BorderParam();

    /**
     * 初始化
     *
     * @param param 基础参数
     */
    protected void init(BaseParam param) {
        this.init(param, false);
    }

    /**
     * 初始化
     *
     * @param param           基础参数
     * @param isInitWithParam 是否使用参数初始化
     */
    protected void init(BaseParam param, boolean isInitWithParam) {
        // 初始化字体
        this.initFont(param);
        // 初始化边框
        this.initBorder(param);
        // 初始化边距
        this.initMargin(isInitWithParam ? param : null);
        // 初始化其他
        this.initOthers(param);
    }

    /**
     * 初始化字体
     *
     * @param param 基础参数
     */
    private void initFont(BaseParam param) {
        if (this.fontParam.getFont() == null) {
            this.fontParam.setFont(param.fontParam.getFont());
        }
        if (this.fontParam.getFontSize() == null) {
            this.fontParam.setFontSize(param.fontParam.getFontSize());
        }
        if (this.fontParam.getFontColor() == null) {
            this.fontParam.setFontColor(param.fontParam.getFontColor());
        }
        if (this.fontParam.getFontStyle() == null) {
            this.fontParam.setFontStyle(param.fontParam.getFontStyle());
        }
        if (this.fontParam.getCharacterSpacing() == null) {
            this.fontParam.setCharacterSpacing(param.fontParam.getCharacterSpacing());
        }
        if (this.fontParam.getLeading() == null) {
            this.fontParam.setLeading(param.fontParam.getLeading());
        }
    }

    /**
     * 初始化边框
     *
     * @param param 基础参数
     */
    private void initBorder(BaseParam param) {
        if (this.borderParam.getStyle() == null) {
            this.borderParam.setStyle(param.borderParam.getStyle());
        }
        if (this.borderParam.getWidth() == null) {
            this.borderParam.setWidth(param.borderParam.getWidth());
        }
        if (this.borderParam.getLineLength() == null) {
            this.borderParam.setLineLength(param.borderParam.getLineLength());
        }
        if (this.borderParam.getLineSpace() == null) {
            this.borderParam.setLineSpace(param.borderParam.getLineSpace());
        }
        if (this.borderParam.getTopColor() == null) {
            this.borderParam.setTopColor(param.borderParam.getTopColor());
        }
        if (this.borderParam.getBottomColor() == null) {
            this.borderParam.setBottomColor(param.borderParam.getBottomColor());
        }
        if (this.borderParam.getLeftColor() == null) {
            this.borderParam.setLeftColor(param.borderParam.getLeftColor());
        }
        if (this.borderParam.getRightColor() == null) {
            this.borderParam.setRightColor(param.borderParam.getRightColor());
        }
        if (this.borderParam.getIsTop() == null) {
            this.borderParam.setIsTop(param.borderParam.getIsTop());
        }
        if (this.borderParam.getIsBottom() == null) {
            this.borderParam.setIsBottom(param.borderParam.getIsBottom());
        }
        if (this.borderParam.getIsLeft() == null) {
            this.borderParam.setIsLeft(param.borderParam.getIsLeft());
        }
        if (this.borderParam.getIsRight() == null) {
            this.borderParam.setIsRight(param.borderParam.getIsRight());
        }
    }

    /**
     * 初始化边距
     *
     * @param param 基础参数
     */
    private void initMargin(BaseParam param) {
        if (param == null) {
            if (this.marginLeft == null) {
                this.marginLeft = 0F;
            }
            if (this.marginRight == null) {
                this.marginRight = 0F;
            }
            if (this.marginTop == null) {
                this.marginTop = 0F;
            }
            if (this.marginBottom == null) {
                this.marginBottom = 0F;
            }
        } else {
            if (this.marginLeft == null) {
                this.marginLeft = param.marginLeft;
            }
            if (this.marginRight == null) {
                this.marginRight = param.marginRight;
            }
            if (this.marginTop == null) {
                this.marginTop = param.marginTop;
            }
            if (this.marginBottom == null) {
                this.marginBottom = param.marginBottom;
            }
        }
    }

    /**
     * 初始化其他
     */
    private void initOthers(BaseParam param) {
        if (this.contentMode == null) {
            this.contentMode = param.contentMode;
        }
        if (this.backgroundColor == null) {
            this.backgroundColor = param.backgroundColor;
        }
        if (this.horizontalAlignment == null) {
            this.horizontalAlignment = param.horizontalAlignment;
        }
        if (this.isWrap == null) {
            this.isWrap = Boolean.FALSE;
        }
        if (this.isBreak == null) {
            this.isBreak = Boolean.FALSE;
        }
    }
}
