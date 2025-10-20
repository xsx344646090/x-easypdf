package org.dromara.pdf.pdfbox.core.ext.handler.tokenizer;

import java.util.function.Supplier;

/**
 * 文档分词器
 *
 * @author xsx
 * @date 2025/10/15
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
public class DocumentTokenizer {

    /**
     * 标准分词器
     *
     * @return 返回标准分词器
     */
    public StandardTokenizer standardTokenizer() {
        return new StandardTokenizer();
    }

    /**
     * 单词分词器
     *
     * @return 返回单词分词器
     */
    public WordsTokenizer wordsTokenizer() {
        return new WordsTokenizer();
    }

    /**
     * 字符分词器
     *
     * @return 返回字符分词器
     */
    public CharacterTokenizer characterTokenizer() {
        return new CharacterTokenizer();
    }

    /**
     * 自定义分词器
     *
     * @param supplier 分词器提供者
     * @param <T>      分词器类型
     * @return 返回自定义分词器
     */
    public <T extends AbstractTokenizer> T customTokenizer(Supplier<T> supplier) {
        return supplier.get();
    }
}
