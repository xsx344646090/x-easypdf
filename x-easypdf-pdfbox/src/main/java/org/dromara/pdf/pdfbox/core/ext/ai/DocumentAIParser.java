package org.dromara.pdf.pdfbox.core.ext.ai;

import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.function.Supplier;

/**
 * 文档AI解析器
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
public class DocumentAIParser extends AbstractParser {
    
    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentAIParser(Document document) {
        super(document);
    }
    
    /**
     * 获取智谱AI
     *
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     * @return 返回智谱AI
     */
    public ZhiPuAIParser getZhiPuAI(String ak, String sk, boolean isJsonResponse) {
        return new ZhiPuAIParser(this.document, ak, sk, isJsonResponse);
    }
    
    /**
     * 获取腾讯AI
     *
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     * @return 返回腾讯AI
     */
    public TencentAIParser getTencentAI(String ak, String sk, boolean isJsonResponse) {
        return new TencentAIParser(this.document, ak, sk, isJsonResponse);
    }
    
    /**
     * 获取深度求索AI
     *
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     * @return 返回深度求索AI
     */
    public DeepSeekAIParser getDeepSeekAI(String ak, String sk, boolean isJsonResponse) {
        return new DeepSeekAIParser(this.document, ak, sk, isJsonResponse);
    }
    
    /**
     * 获取阿里AI
     *
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     * @return 返回阿里AI
     */
    public ALiAIParser getALiAI(String ak, String sk, boolean isJsonResponse) {
        return new ALiAIParser(this.document, ak, sk, isJsonResponse);
    }
    
    /**
     * 获取自定义AI
     *
     * @param supplier 提供者
     * @return 返回自定义AI
     */
    public AbstractAIParser getCustomAI(Supplier<AbstractAIParser> supplier) {
        return supplier.get();
    }
}