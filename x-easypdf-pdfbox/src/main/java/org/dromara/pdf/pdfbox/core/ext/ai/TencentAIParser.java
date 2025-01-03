package org.dromara.pdf.pdfbox.core.ext.ai;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.Headers;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.util.HttpUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 腾讯AI解析器
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
     * @param ak             api密钥
     * @param sk             密钥
     * @param isJsonResponse 是否json响应
     */
    public TencentAIParser(Document document, String ak, String sk, boolean isJsonResponse) {
        super(document, ak, sk, isJsonResponse);
        this.config.setImageModel("hunyuan-turbo-vision");
        this.config.setTextModel("hunyuan-turbo");
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
        return new ImageParser().parse(this.config, prompt, this.getPageImage(pageIndex));
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
        return new ImageParser().parse(this.config, prompt, this.getPageImage(pageIndex, imageIndex));
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
        return new TextParser().parse(this.config, prompt, this.getPageText(beginPageIndex, endPageIndex));
    }
    
    /**
     * 解析器
     */
    protected static class Parser {
        
        /**
         * 端点
         */
        private static final String ENDPOINT = "hunyuan.tencentcloudapi.com";
        /**
         * 服务
         */
        private static final String SERVICE = "hunyuan";
        /**
         * 版本
         */
        private static final String VERSION = "2023-09-01";
        /**
         * 接口
         */
        private static final String ACTION = "ChatCompletions";
        /**
         * 请求路径
         */
        private static final String URL = "https://hunyuan.tencentcloudapi.com";
        /**
         * 内容json路径
         */
        private static final String CONTENT_JSON_PATH = "$.Choices[0].Message.Content";
        /**
         * 标记json路径
         */
        private static final String TOKEN_JSON_PATH = "$.Usage.TotalTokens";
        
        /**
         * 解析
         *
         * @param config   配置
         * @param messages 消息
         * @return 返回解析信息
         */
        @SneakyThrows
        protected AIParseInfo parseInfo(AIParserConfig config, String model, List<Message> messages) {
            // 时间戳
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            // 请求体
            String body = JSONObject.of("Model", model).fluentPut("Messages", messages).toString(JSONWriter.Feature.FieldBased);
            // 请求头
            Headers headers = new Headers.Builder()
                                      .add("Authorization", this.getAuthorization(timestamp, config.getAk(), config.getSk(), body.getBytes(StandardCharsets.UTF_8)))
                                      .add("X-TC-Action", ACTION)
                                      .add("X-TC-Version", VERSION)
                                      .add("X-TC-Timestamp", timestamp)
                                      .build();
            
            // 响应体
            JSONObject response = HttpUtil.request(config.getClient(), URL, headers, body).getJSONObject("Response");
            // 请求错误
            if (response.containsKey("ErrorMsg")) {
                throw new RuntimeException(response.getByPath("$.ErrorMsg.Msg").toString());
            }
            // 返回解析信息
            return new AIParseInfo(response.getString("Id"), String.valueOf(response.getByPath(CONTENT_JSON_PATH)), String.valueOf(response.getByPath(TOKEN_JSON_PATH)));
        }
        
        /**
         * 获取授权
         *
         * @param timestamp 时间戳
         * @param ak        api密钥
         * @param sk        密钥
         * @param body      请求体
         * @return 返回授权
         */
        protected String getAuthorization(String timestamp, String ak, String sk, byte[] body) {
            String contentType = "application/json; charset=utf-8";
            String canonicalUri = "/";
            String canonicalQueryString = "";
            String canonicalHeaders = "content-type:" + contentType + "\nhost:" + ENDPOINT + "\n";
            String signedHeaders = "content-type;host";
            String hashedRequestPayload = Sign.sha256Hex(body);
            String canonicalRequest = "POST\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date = sdf.format(new Date(Long.parseLong(timestamp + "000")));
            String credentialScope = date + "/" + SERVICE + "/" + "tc3_request";
            String hashedCanonicalRequest = Sign.sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));
            String stringToSign = "TC3-HMAC-SHA256\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
            byte[] secretDate = Sign.hmac256(("TC3" + sk).getBytes(StandardCharsets.UTF_8), date);
            byte[] secretService = Sign.hmac256(secretDate, SERVICE);
            byte[] secretSigning = Sign.hmac256(secretService, "tc3_request");
            String signature = DatatypeConverter.printHexBinary(Sign.hmac256(secretSigning, stringToSign)).toLowerCase();
            return "TC3-HMAC-SHA256 Credential=" + ak + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
        }
    }
    
    /**
     * 图像解析器
     */
    @Setter
    protected static class ImageParser extends Parser {
        
        /**
         * 解析图像
         *
         * @param config 配置
         * @param prompt 提示词
         * @param image  图像
         * @return 返回解析信息
         */
        @SneakyThrows
        public AIParseInfo parse(AIParserConfig config, String prompt, BufferedImage image) {
            // 返回解析信息
            return this.parseInfo(config, config.getImageModel(), this.createMessages(config.getImageSystemPrompt(), prompt, image));
        }
        
        /**
         * 创建消息
         *
         * @param systemPrompt 系统提示词
         * @param prompt       提示词
         * @param image        图像
         * @return 返回消息
         */
        protected List<Message> createMessages(String systemPrompt, String prompt, BufferedImage image) {
            List<Message> messages = new ArrayList<>(2);
            if (Objects.nonNull(systemPrompt)) {
                messages.add(new Message("system", systemPrompt));
            }
            messages.add(new Message("user", this.createMessageContents(prompt, image)));
            return messages;
        }
        
        /**
         * 创建消息内容
         *
         * @param prompt 提示词
         * @param image  图像
         * @return 返回消息内容
         */
        protected List<Object> createMessageContents(String prompt, BufferedImage image) {
            // 定义内容列表
            List<Object> contents = new ArrayList<>(2);
            // 添加内容
            contents.add(new ImageContent("data:image/png;base64," + ImageUtil.toBase64(image, ImageType.PNG.getType())));
            contents.add(new TextContent("<question>" + prompt + "</question>"));
            // 返回内容列表
            return contents;
        }
    }
    
    /**
     * 文本解析器
     */
    @Setter
    protected static class TextParser extends Parser {
        
        /**
         * 解析
         *
         * @param config 配置
         * @param prompt 提示词
         * @param text   文本
         * @return 返回解析信息
         */
        @SneakyThrows
        public AIParseInfo parse(AIParserConfig config, String prompt, String text) {
            // 返回解析信息
            return this.parseInfo(config, config.getTextModel(), this.createMessages(config.getTextSystemPrompt(), prompt, text));
        }
        
        /**
         * 创建消息
         *
         * @param systemPrompt 系统提示词
         * @param prompt       提示词
         * @param text         文本
         * @return 返回消息
         */
        protected List<Message> createMessages(String systemPrompt, String prompt, String text) {
            // 定义消息列表
            List<Message> messages = new ArrayList<>(3);
            // 添加消息
            if (Objects.nonNull(systemPrompt)) {
                messages.add(new Message("system", systemPrompt));
            }
            messages.add(new Message("user", text));
            messages.add(new Message("user", "<question>" + prompt + "</question>"));
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
        protected String Role;
        /**
         * 文本内容
         */
        protected String Content;
        /**
         * 内容列表
         */
        protected Object Contents;
        
        /**
         * 有参构造
         *
         * @param role    角色
         * @param content 内容
         */
        public Message(String role, String content) {
            this.Role = role;
            this.Content = content;
        }
        
        /**
         * 有参构造
         *
         * @param role     角色
         * @param contents 内容
         */
        public Message(String role, Object contents) {
            this.Role = role;
            this.Contents = contents;
        }
    }
    
    /**
     * 文本消息内容
     */
    @Setter
    @Getter
    protected static class TextContent {
        /**
         * 类型
         */
        protected String Type = "text";
        /**
         * 文本
         */
        protected String Text;
        
        /**
         * 有参构造
         *
         * @param text 文本
         */
        public TextContent(String text) {
            this.Text = text;
        }
    }
    
    /**
     * 图像消息内容
     */
    @Setter
    @Getter
    protected static class ImageContent {
        /**
         * 类型
         */
        protected String Type = "image_url";
        /**
         * 图像地址
         */
        protected ImageUrl ImageUrl;
        
        /**
         * 有参构造
         *
         * @param url 图像地址
         */
        public ImageContent(String url) {
            this.ImageUrl = new ImageUrl(url);
        }
    }
    
    /**
     * 图像地址
     */
    @Setter
    @Getter
    protected static class ImageUrl {
        /**
         * base64
         */
        protected String Url;
        
        /**
         * 有参构造
         *
         * @param url base64
         */
        public ImageUrl(String url) {
            this.Url = url;
        }
    }
    
    /**
     * 签名
     */
    protected static class Sign {
        
        @SneakyThrows
        public static String sign(String secretKey, String sigStr, String sigMethod) {
            Mac mac = Mac.getInstance(sigMethod);
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm()));
            byte[] hash = mac.doFinal(sigStr.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printBase64Binary(hash);
        }
        
        @SneakyThrows
        public static String sha256Hex(byte[] b) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(b);
            return DatatypeConverter.printHexBinary(d).toLowerCase();
        }
        
        @SneakyThrows
        public static byte[] hmac256(byte[] key, String msg) {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, mac.getAlgorithm()));
            return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}