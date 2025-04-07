package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import org.dromara.pdf.pdfbox.core.base.Document;

/**
 * 腾讯（hunyuan）AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://cloud.tencent.com/document/api/1729/105701">官方文档</a>
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
public class TencentAIParser extends AbstractAIParser {

    /**
     * 有参构造
     *
     * @param document       文档
     * @param apiKey         api密钥
     * @param isJsonResponse 是否json响应
     */
    public TencentAIParser(Document document, String apiKey, boolean isJsonResponse) {
        super(document, apiKey, isJsonResponse);
        this.config.setImageUrl("https://api.hunyuan.cloud.tencent.com/v1/chat/completions");
        this.config.setImageModel("hunyuan-turbo-vision");
        this.config.setTextUrl("https://api.hunyuan.cloud.tencent.com/v1/chat/completions");
        this.config.setTextModel("hunyuan-turbo");
    }
}
