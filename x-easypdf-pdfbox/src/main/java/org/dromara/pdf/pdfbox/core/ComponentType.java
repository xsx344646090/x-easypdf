package org.dromara.pdf.pdfbox.core;

/**
 * 组件类型
 *
 * @author xsx
 * @date 2023/9/21
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
public enum ComponentType {

    /**
     * 页眉
     */
    PAGE_HEADER,
    /**
     * 页脚
     */
    PAGE_FOOTER,
    /**
     * 文本域
     */
    TEXTAREA,
    /**
     * 图像
     */
    IMAGE,
    /**
     * 条形码
     */
    BARCODE,
    /**
     * 容器
     */
    CONTAINER,
    /**
     * 自定义
     */
    CUSTOM;


    public boolean isNotPageHeaderOrFooter() {
        return this != PAGE_HEADER && this != PAGE_FOOTER;
    }
}
