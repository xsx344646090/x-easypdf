package org.dromara.pdf.pdfbox.support;

import lombok.Getter;

/**
 * 字符包装
 *
 * @author xsx
 * @date 2025/5/9
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
@Getter
public class CharacterWrapper {

    private final char c;

    public CharacterWrapper(char c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CharacterWrapper)) {
            return false;
        }

        CharacterWrapper that = (CharacterWrapper) o;
        return c == that.c;
    }

    @Override
    public int hashCode() {
        return c;
    }
}
