package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import org.dromara.pdf.pdfbox.core.base.Document;

/**
 * 阿里（qwen）AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://help.aliyun.com/zh/model-studio/user-guide/text-generation">官方文档</a>
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
public class ALiAIParser extends AbstractAIParser {

    /**
     * 有参构造
     *
     * @param document       文档
     * @param apiKey         api密钥
     * @param isJsonResponse 是否json响应
     */
    public ALiAIParser(Document document, String apiKey, boolean isJsonResponse) {
        super(document, apiKey, isJsonResponse);
        this.config.setImageUrl("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions");
        this.config.setImageModel("qwen-vl-plus");
        this.config.setTextUrl("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions");
        this.config.setTextModel("qwen-turbo");
    }
}
