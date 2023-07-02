package org.dromara.pdf.pdfbox.component.layout;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.pdfbox.component.XEasyPdfComponent;

/**
 * pdf布局组件
 *
 * @author xsx
 * @date 2022/3/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
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
public class XEasyPdfLayoutComponent {
    /**
     * pdf组件
     */
    private XEasyPdfComponent component;
    /**
     * 宽度
     */
    private Float width;
    /**
     * 高度
     */
    private Float height;

    /**
     * 有参构造
     *
     * @param width  宽度
     * @param height 高度
     */
    public XEasyPdfLayoutComponent(Float width, Float height) {
        this.width = width;
        this.height = height;
    }
}
