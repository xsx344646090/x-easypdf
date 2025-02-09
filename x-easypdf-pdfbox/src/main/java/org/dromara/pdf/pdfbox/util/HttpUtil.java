package org.dromara.pdf.pdfbox.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import okhttp3.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * http工具
 *
 * @author xsx
 * @date 2024/12/31
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
public class HttpUtil {
    
    /**
     * 媒体类型
     */
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    /**
     * 客户端
     */
    public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
                                                      .connectTimeout(60, TimeUnit.SECONDS)
                                                      .readTimeout(60, TimeUnit.SECONDS)
                                                      .writeTimeout(60, TimeUnit.SECONDS)
                                                      .callTimeout(60, TimeUnit.SECONDS)
                                                      .retryOnConnectionFailure(true)
                                                      .build();
    
    /**
     * 请求
     *
     * @param client   客户端
     * @param url      请求地址
     * @param headers  请求头
     * @param jsonBody json请求体
     * @return 返回响应字符串
     */
    @SneakyThrows
    public static JSONObject request(OkHttpClient client, String url, Headers headers, String jsonBody) {
        Request request = new Request.Builder()
                                  .url(url)
                                  .headers(headers)
                                  .post(RequestBody.create(jsonBody, MEDIA_TYPE))
                                  .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                return JSONObject.parseObject(body.string());
            }
            throw new RuntimeException("ai request fail");
        }
    }
    
    /**
     * 请求
     *
     * @param client   客户端
     * @param url      请求地址
     * @param headers  请求头
     * @param formBody 表单请求体
     * @return 返回响应字符串
     */
    @SneakyThrows
    public static JSONObject request(OkHttpClient client, String url, Headers headers, RequestBody formBody) {
        Request request = new Request.Builder()
                                  .url(url)
                                  .headers(headers)
                                  .post(formBody)
                                  .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                return JSONObject.parseObject(body.string());
            }
            throw new RuntimeException("ai request fail");
        }
    }
}
