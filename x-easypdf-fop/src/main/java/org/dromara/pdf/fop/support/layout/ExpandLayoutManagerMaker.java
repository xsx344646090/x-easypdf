package org.dromara.pdf.fop.support.layout;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.layoutmgr.LayoutManagerMaker;

/**
 * 扩展布局管理器
 *
 * @author xsx
 * @date 2024/6/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public interface ExpandLayoutManagerMaker extends LayoutManagerMaker {

    /**
     * 初始化
     *
     * @param userAgent 用户代理
     */
    void initialize(FOUserAgent userAgent);
}
