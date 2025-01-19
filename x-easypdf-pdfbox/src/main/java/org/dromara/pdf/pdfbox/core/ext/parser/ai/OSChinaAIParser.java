package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import org.dromara.pdf.pdfbox.core.base.Document;

/**
 * 开源中国（gitee）AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://ai.gitee.com/docs/openapi/v1#tag/%E6%96%87%E6%9C%AC%E7%94%9F%E6%88%90/POST/chat/completions">官方文档</a>
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
public class OSChinaAIParser extends AbstractAIParser {
    
    /**
     * 有参构造
     *
     * @param document       文档
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     */
    public OSChinaAIParser(Document document, String ak, String sk, boolean isJsonResponse) {
        super(document, ak, sk, isJsonResponse);
        this.config.setTextUrl("https://ai.gitee.com/v1/chat/completions");
        this.config.setTextModel("glm-4-9b-chat");
    }
    
    /**
     * 解析页面（整个页面）
     * <p>注：访问大模型超时时间为1分钟</p>
     *
     * @param prompt    提示词
     * @param pageIndex 页面索引
     * @return 返回解析内容
     */
    @Override
    public AIParseInfo parsePageWithImage(String prompt, int pageIndex) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 解析图像（页面中的图像）
     * <p>注：访问大模型超时时间为1分钟</p>
     *
     * @param prompt     提示词
     * @param pageIndex  页面索引
     * @param imageIndex 图像索引
     * @return 返回解析信息
     */
    @Override
    public AIParseInfo parseImageWithPage(String prompt, int pageIndex, int imageIndex) {
        throw new UnsupportedOperationException();
    }
}