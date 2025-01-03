package org.dromara.pdf.pdfbox.core.ext.ai;

import org.dromara.pdf.pdfbox.core.base.Document;

/**
 * 智谱AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://bigmodel.cn/dev/api/normal-model/glm-4">官方文档</a>
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
public class ZhiPuAIParser extends AbstractAIParser {
    
    /**
     * 有参构造
     *
     * @param document       文档
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     */
    public ZhiPuAIParser(Document document, String ak, String sk, boolean isJsonResponse) {
        super(document, ak, sk, isJsonResponse);
        this.config.setImageUrl("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        this.config.setImageModel("glm-4v-plus");
        this.config.setTextUrl("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        this.config.setTextModel("glm-4-plus");
    }
}