package org.dromara.pdf.pdfbox.handler;

import org.dromara.pdf.pdfbox.component.Text;
import org.dromara.pdf.pdfbox.core.Page;

/**
 * 文本助手
 *
 * @author xsx
 * @date 2023/6/12
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
public class TextHandler {

    /**
     * 助手实例
     */
    private static final TextHandler INSTANCE = new TextHandler();

    /**
     * 无参构造
     */
    private TextHandler() {
    }

    /**
     * 获取实例
     *
     * @return 返回实例
     */
    public static TextHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 构建
     *
     * @param page 页面
     * @return 返回文本
     */
    public Text build(Page page) {
        return new Text(page);
    }
}
