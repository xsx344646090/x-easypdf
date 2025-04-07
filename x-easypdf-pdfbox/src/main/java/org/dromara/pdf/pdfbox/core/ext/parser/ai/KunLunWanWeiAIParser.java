package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import okhttp3.Headers;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.security.MessageDigest;
import java.util.List;

/**
 * 昆仑万维（tiangong）AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://model-platform.tiangong.cn/api-reference">官方文档</a>
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
public class KunLunWanWeiAIParser extends AbstractAIParser {

    /**
     * 摘要
     */
    private static final MessageDigest DIGEST = initDigest();

    /**
     * 有参构造
     *
     * @param document       文档
     * @param apiKey         api密钥
     * @param isJsonResponse 是否json响应
     */
    public KunLunWanWeiAIParser(Document document, String apiKey, boolean isJsonResponse) {
        super(document, apiKey, isJsonResponse);
        this.config.setTextUrl("https://api-maas.singularity-ai.com/sky-work/api/v1/chat");
    }

    /**
     * 初始化摘要
     *
     * @return 返回摘要
     */
    @SneakyThrows
    private static MessageDigest initDigest() {
        return MessageDigest.getInstance("MD5");
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

    /**
     * 获取请求头
     *
     * @return 返回请求头
     */
    @Override
    protected Headers getRequestHeaders() {
        long timestamp = System.currentTimeMillis() / 1000;
        return new Headers.Builder().add("app_key", this.config.getApiKey()).add("sign", this.getSign(timestamp)).add("timestamp", String.valueOf(timestamp)).build();
    }

    /**
     * 获取请求体
     *
     * @return 返回请求体
     */
    @Override
    protected JSONObject getRequestBody(String model, List<Message> messages) {
        JSONObject jsonBody = JSONObject.of("messages", messages).fluentPut("intent", "chat");
        if (this.config.isJsonResponse()) {
            jsonBody.fluentPut("response_format ", JSONObject.of("type", "json_object"));
        }
        return jsonBody;
    }

    /**
     * 获取响应
     *
     * @return 返回响应
     */
    @Override
    protected JSONObject getResponse(String url, String model, List<Message> messages) {
        // 响应体
        JSONObject response = super.getResponse(url, model, messages);
        if (response.containsKey("code")) {
            throw new RuntimeException(response.toString());
        }
        return response;
    }

    /**
     * 获取签名
     *
     * @param timestamp 时间戳
     * @return 返回签名
     */
    @SneakyThrows
    protected String getSign(long timestamp) {
        String data = this.config.getApiKey() + timestamp;
        byte[] messageDigest = DIGEST.digest(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
