package org.dromara.pdf.pdfbox.core.info;

import lombok.Builder;
import lombok.Data;

/**
 * 评论信息
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
@Data
@Builder
public class CommentInfo {
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
     * 文本宽度
     */
    private Float textWidth;
    /**
     * 文本高度
     */
    private Float textHeight;
    /**
     * 是否隐藏
     */
    private Boolean isHidden;
    /**
     * 是否不可见
     */
    private Boolean isInvisible;
    /**
     * 是否锁定
     */
    private Boolean isLocked;
    /**
     * 是否锁定内容
     */
    private Boolean isLockedContents;
    /**
     * 是否无旋转
     */
    private Boolean isNoRotate;
    /**
     * 是否无视图
     */
    private Boolean isNoView;
    /**
     * 是否无缩放
     */
    private Boolean isNoZoom;
    /**
     * 是否可打印
     */
    private Boolean isPrinted;
    /**
     * 是否只读
     */
    private Boolean isReadOnly;
    /**
     * 是否切换无视图
     */
    private Boolean isToggleNoView;
}
