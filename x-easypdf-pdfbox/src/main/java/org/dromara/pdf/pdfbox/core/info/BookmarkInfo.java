package org.dromara.pdf.pdfbox.core.info;

import lombok.Builder;
import lombok.Data;

/**
 * 书签信息
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
@Data
@Builder
public class BookmarkInfo {
    /**
     * 标题
     */
    private String title;
    /**
     * 起始页面索引
     */
    private Integer beginPageIndex;
    /**
     * 结束页面索引
     */
    private Integer endPageIndex;
    /**
     * 起始页面顶部Y轴坐标
     */
    private Integer beginPageTopY;
    /**
     * 起始页面底部Y轴坐标
     */
    private Integer beginPageBottomY;
    /**
     * 结束页面顶部Y轴坐标
     */
    private Integer endPageTopY;
    /**
     * 结束页面底部Y轴坐标
     */
    private Integer endPageBottomY;
}
