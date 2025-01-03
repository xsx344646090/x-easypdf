package org.dromara.pdf.pdfbox.core.ext.ai;

import lombok.Data;
import okhttp3.OkHttpClient;

/**
 * 抽象AI解析器
 *
 * @author xsx
 * @date 2024/12/25
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
@Data
public class AIParserConfig {
    
    /**
     * api密钥
     */
    protected String ak;
    /**
     * 密钥
     */
    protected String sk;
    /**
     * 图像接口地址
     */
    protected String imageUrl;
    /**
     * 图像模型
     */
    protected String imageModel;
    /**
     * 图像系统提示词
     */
    protected String imageSystemPrompt;
    /**
     * 文本接口地址
     */
    protected String textUrl;
    /**
     * 文本模型
     */
    protected String textModel;
    /**
     * 文本系统提示词
     */
    protected String textSystemPrompt;
    /**
     * 是否json响应
     */
    protected boolean isJsonResponse;
    /**
     * http客户端
     */
    protected OkHttpClient client;
    
    /**
     * 有参构造
     *
     * @param ak                api密钥
     * @param sk                密钥
     * @param imageSystemPrompt 图像系统提示词
     * @param textSystemPrompt  文本系统提示词
     * @param isJsonResponse    是否json格式返回
     * @param client            http客户端
     */
    public AIParserConfig(String ak, String sk, String imageSystemPrompt, String textSystemPrompt, boolean isJsonResponse, OkHttpClient client) {
        this.ak = ak;
        this.sk = sk;
        this.imageSystemPrompt = imageSystemPrompt;
        this.textSystemPrompt = textSystemPrompt;
        this.isJsonResponse = isJsonResponse;
        this.client = client;
    }
}