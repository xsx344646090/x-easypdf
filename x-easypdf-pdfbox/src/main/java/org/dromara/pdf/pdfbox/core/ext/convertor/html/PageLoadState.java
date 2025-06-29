package org.dromara.pdf.pdfbox.core.ext.convertor.html;

import com.microsoft.playwright.options.LoadState;
import lombok.Getter;

/**
 * 页面加载状态
 *
 * @author xsx
 * @date 2025/6/26
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
@Getter
public enum PageLoadState {
    /**
     * 加载完成
     */
    LOAD(LoadState.LOAD),
    /**
     * 文档加载完成
     */
    DOMCONTENTLOADED(LoadState.DOMCONTENTLOADED),
    /**
     * 网络空闲
     */
    NETWORKIDLE(LoadState.NETWORKIDLE);

    /**
     * 状态
     */
    private LoadState state;

    /**
     * 有参构造
     *
     * @param state 状态
     */
    PageLoadState(LoadState state) {
        this.state = state;
    }
}
