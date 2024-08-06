package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.Getter;

/**
 * 签名权限
 *
 * @author xsx
 * @date 2024/3/13
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
public enum SignPermission {
    /**
     * 无限制
     */
    NONE_LIMIT(0),
    /**
     * 限制
     */
    LIMIT(1),
    /**
     * 仅允许表单、签名与动作
     */
    ONLY_FORM_SIGN_ACTION(2);

    /**
     * 类型
     */
    private final int type;

    /**
     * 有参构造
     *
     * @param type 类型
     */
    SignPermission(int type) {
        this.type = type;
    }
}
