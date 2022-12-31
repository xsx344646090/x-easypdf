package wiki.xsx.core.pdf.component.layout;

import wiki.xsx.core.pdf.component.XEasyPdfComponent;

import java.util.List;

/**
 * pdf布局组件
 *
 * @author xsx
 * @date 2022/3/21
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public interface XEasyPdfLayout extends XEasyPdfComponent {

    /**
     * 开启边框
     *
     * @return 返回pdf布局
     */
    XEasyPdfLayout enableBorder();

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回pdf布局
     */
    XEasyPdfLayout addLayoutComponent(XEasyPdfLayoutComponent... components);

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回pdf布局
     */
    XEasyPdfLayout addLayoutComponent(List<XEasyPdfLayoutComponent> components);
}
