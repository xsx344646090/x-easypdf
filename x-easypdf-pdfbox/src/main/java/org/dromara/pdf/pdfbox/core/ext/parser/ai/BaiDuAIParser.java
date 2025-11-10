package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.util.HttpUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 百度（ernie bot）AI解析器
 *
 * @author xsx
 * @date 2024/12/25
 * @see <a href="https://cloud.baidu.com/doc/WENXINWORKSHOP/s/om5aq2brc">官方文档</a>
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
public class BaiDuAIParser extends AbstractAIParser {

    /**
     * 有参构造
     *
     * @param document 文档
     * @param apiKey   api密钥
     */
    public BaiDuAIParser(Document document, String apiKey) {
        super(document, apiKey, false);
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
     * 解析文本（页面）
     * <p>注：访问大模型超时时间为1分钟</p>
     *
     * @param prompt         提示词
     * @param beginPageIndex 起始页面索引
     * @param endPageIndex   结束页面索引
     * @return 返回解析信息
     */
    @Override
    public AIParseInfo parseTextWithPage(String prompt, int beginPageIndex, int endPageIndex) {
        throw new UnsupportedOperationException();
    }

    /**
     * 解析器
     */
    protected static class Parser {

        /**
         * 请求路径
         */
        private static final String ACCESS_TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";
        /**
         * 标记json路径
         */
        private static final String TOKEN_JSON_PATH = "$.usage.total_tokens";

        /**
         * 解析图像
         *
         * @param client   http客户端
         * @param ak       api密钥
         * @param sk       密钥
         * @param url      请求地址
         * @param jsonBody json请求体
         * @return 返回解析信息
         */
        @SneakyThrows
        protected AIParseInfo parseInfo(OkHttpClient client, String ak, String sk, String url, String jsonBody) {
            // 请求头
            Headers headers = new Headers.Builder().add("Content-Type", "application/json").build();
            // 完整请求路径
            String allUrl = url + "?" + "access_token=" + this.getAccessToken(client, ak, sk);
            // 响应体
            JSONObject response = HttpUtil.request(client, allUrl, headers, jsonBody);
            // 请求错误
            if (response.containsKey("error_msg")) {
                throw new RuntimeException(response.getString("error_msg"));
            }
            // 返回解析信息
            return new AIParseInfo(response.getString("id"), response.getString("result"), String.valueOf(response.getByPath(TOKEN_JSON_PATH)));
        }

        /**
         * 获取访问凭证
         *
         * @param ak api密钥
         * @param sk 密钥
         * @return 返回访问凭证
         */
        protected String getAccessToken(OkHttpClient client, String ak, String sk) {
            // 请求头
            Headers headers = new Headers.Builder().add("Content-Type", "application/x-www-form-urlencoded").build();
            // 表单请求体
            RequestBody body = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", ak)
                    .add("client_secret", sk)
                    .build();
            // 响应体
            JSONObject response = HttpUtil.request(client, ACCESS_TOKEN_URL, headers, body);
            if (response.containsKey("error")) {
                throw new RuntimeException("ai request fail: " + response.getString("error_description"));
            }
            // 返回访问凭证
            return response.getString("access_token");
        }
    }

    /**
     * 图像解析器
     */
    protected static class ImageParser extends Parser {

        /**
         * 请求路径
         */
        private static final String URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/image2text/fuyu_8b";

        /**
         * 设置模型
         *
         * @param model 模型
         */
        public void setModel(String model) {
            throw new UnsupportedOperationException();
        }

        /**
         * 设置系统提示词
         *
         * @param prompt 提示词
         */
        public void setSystemPrompt(String prompt) {
            throw new UnsupportedOperationException();
        }

        /**
         * 解析图像
         *
         * @param client http客户端
         * @param ak     api密钥
         * @param sk     密钥
         * @param prompt 提示词
         * @param image  图像
         * @return 返回解析信息
         */
        @SneakyThrows
        public AIParseInfo parse(OkHttpClient client, String ak, String sk, String prompt, BufferedImage image) {
            // json请求体
            String jsonBody = JSONObject.of("prompt", prompt).fluentPut("image", ImageUtil.toBase64(image, ImageType.PNG.getType())).toString();
            // 返回解析信息
            return this.parseInfo(client, ak, sk, URL, jsonBody);
        }
    }

    /**
     * 文本解析器
     */
    @Setter
    protected static class TextParser extends Parser {
        /**
         * 请求路径
         */
        private static final String URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-speed-128k";
        /**
         * 系统提示词
         */
        protected String systemPrompt = "<role>你是一个数据分析与提取专家</role>";

        /**
         * 设置模型
         *
         * @param model 模型
         */
        public void setModel(String model) {
            throw new UnsupportedOperationException();
        }

        /**
         * 解析
         *
         * @param client http客户端
         * @param ak     api密钥
         * @param sk     密钥
         * @param prompt 提示词
         * @param text   文本
         * @return 返回解析信息
         */
        @SneakyThrows
        public AIParseInfo parse(OkHttpClient client, String ak, String sk, String prompt, String text) {
            // json请求体
            String jsonBody = JSONObject.of("messages", this.createMessages(prompt, text)).toString();
            // 返回解析信息
            return this.parseInfo(client, ak, sk, URL, jsonBody);
        }

        /**
         * 创建消息
         *
         * @param prompt 提示词
         * @param text   文本
         * @return 返回消息
         */
        protected List<Message> createMessages(String prompt, String text) {
            // 定义消息列表
            List<Message> messages = new ArrayList<>(3);
            // 添加消息
            if (Objects.nonNull(this.systemPrompt)) {
                messages.add(new Message("user", this.systemPrompt));
            }
            messages.add(new Message("user", text));
            messages.add(new Message("user", prompt));
            // 返回消息
            return messages;
        }
    }

    /**
     * 消息
     */
    @Setter
    @Getter
    protected static class Message {
        /**
         * 角色
         */
        protected String role;
        /**
         * 文本内容
         */
        protected String content;

        /**
         * 有参构造
         *
         * @param role    角色
         * @param content 内容
         */
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
