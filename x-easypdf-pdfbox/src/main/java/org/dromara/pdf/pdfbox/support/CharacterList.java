package org.dromara.pdf.pdfbox.support;

import lombok.Data;

import java.util.Objects;

/**
 * 字符链表
 *
 * @author xsx
 * @date 2024/9/25
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
public class CharacterList {
    /**
     * 数据
     */
    private Character data;
    /**
     * 上一个
     */
    private CharacterList previous;
    /**
     * 下一个
     */
    private CharacterList next;

    /**
     * 创建
     *
     * @param text 文本
     * @return 返回字符链表
     */
    public static CharacterList create(String text) {
        // 转为字符数组
        char[] chars = text.toCharArray();
        // 定义根
        CharacterList root = new CharacterList();
        // 定义上一个
        CharacterList previous = root;
        // 定义当前
        CharacterList current;
        // 遍历字符
        for (char c : chars) {
            // 重置当前
            current = new CharacterList();
            // 设置数据
            current.setData(c);
            // 设置上一个
            current.setPrevious(previous);
            // 重置上一个
            previous = current;
        }
        // 返回根
        return root;
    }

    /**
     * 设置上一个
     *
     * @param node 节点
     */
    public void setPrevious(CharacterList node) {
        this.previous = node;
        node.setNext(this);
    }

    /**
     * 是否有上一个
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean hasPrevious() {
        return Objects.nonNull(this.previous);
    }

    /**
     * 是否有下一个
     *
     * @return 返回布尔值，true为是，false为否
     */
    public boolean hasNext() {
        return Objects.nonNull(this.next);
    }

    /**
     * 跳过下一个
     */
    public void skipNext() {
        if (Objects.nonNull(this.next)) {
            this.next = this.next.next;
        }
    }
}
