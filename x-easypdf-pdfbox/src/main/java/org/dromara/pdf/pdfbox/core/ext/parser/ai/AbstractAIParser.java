package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.enums.ImageType;
import org.dromara.pdf.pdfbox.core.ext.analyzer.DocumentAnalyzer;
import org.dromara.pdf.pdfbox.core.ext.parser.AbstractParser;
import org.dromara.pdf.pdfbox.core.ext.processor.RenderProcessor;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.HttpUtil;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public abstract class AbstractAIParser extends AbstractParser {

    /**
     * 内容json路径
     */
    private static final String CONTENT_JSON_PATH = "$.choices[0].message.content";
    /**
     * 标记json路径
     */
    private static final String TOKEN_JSON_PATH = "$.usage.total_tokens";
    /**
     * 任务id key
     */
    protected String taskIdKey = "id";
    /**
     * ai解析配置
     */
    protected AIParserConfig config;

    /**
     * 有参构造
     *
     * @param document       文档
     * @param apiKey         api密钥
     * @param isJsonResponse 是否json响应
     */
    public AbstractAIParser(Document document, String apiKey, boolean isJsonResponse) {
        super(document);
        this.config = new AIParserConfig(
                apiKey,
                "你是一个图像分析与数据提取专家，能够根据所给的信息准确地回答用户提出的问题",
                "你是一个数据分析与提取专家，能够根据所给的信息准确地回答用户提出的问题",
                isJsonResponse,
                HttpUtil.CLIENT
        );
    }

    /**
     * 设置http客户端
     *
     * @param client 客户端
     */
    public void setHttpClient(OkHttpClient client) {
        Objects.requireNonNull(client, "the http client can not be null");
        this.config.setClient(client);
    }

    /**
     * 设置图像模型
     *
     * @param model 模型
     */
    public void setImageModel(String model) {
        Objects.requireNonNull(model, "the model can not be null");
        this.config.setImageModel(model);
    }

    /**
     * 设置图像系统提示词
     *
     * @param prompt 提示词
     */
    public void setImageSystemPrompt(String prompt) {
        this.config.setImageSystemPrompt(prompt);
    }

    /**
     * 设置文本模型
     *
     * @param model 模型
     */
    public void setTextModel(String model) {
        Objects.requireNonNull(model, "the model can not be null");
        this.config.setTextModel(model);
    }

    /**
     * 设置文本系统提示词
     *
     * @param prompt 提示词
     */
    public void setTextSystemPrompt(String prompt) {
        this.config.setTextSystemPrompt(prompt);
    }

    /**
     * 是否json响应
     *
     * @param flag 标记
     */
    public void setIsJsonResponse(boolean flag) {
        this.config.setJsonResponse(flag);
    }

    /**
     * 解析页面（整个页面）
     * <p>注：访问大模型超时时间为1分钟</p>
     *
     * @param prompt    提示词
     * @param pageIndex 页面索引
     * @return 返回解析内容
     */
    public AIParseInfo parsePageWithImage(String prompt, int pageIndex) {
        return this.parseImageInfo(this.createImageMessages(prompt, this.getPageImage(pageIndex)));
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
    public AIParseInfo parseImageWithPage(String prompt, int pageIndex, int imageIndex) {
        return this.parseImageInfo(this.createImageMessages(prompt, this.getPageImage(pageIndex, imageIndex)));
    }

    /**
     * 解析文本（文档）
     * <p>注：访问大模型超时时间为1分钟</p>
     *
     * @param prompt 提示词
     * @return 返回解析信息
     */
    public AIParseInfo parseTextWithDocument(String prompt) {
        return this.parseTextWithPage(prompt, -1, -1);
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
    @SneakyThrows
    public AIParseInfo parseTextWithPage(String prompt, int beginPageIndex, int endPageIndex) {
        return this.parseTexInfo(this.createTextMessages(prompt, this.getPageText(beginPageIndex, endPageIndex)));
    }

    /**
     * 解析图像信息
     *
     * @param messages 消息
     * @return 返回解析信息
     */
    protected AIParseInfo parseImageInfo(List<Message> messages) {
        // 返回解析信息
        return this.parseInfo(this.config.getImageUrl(), this.config.getImageModel(), messages);
    }

    /**
     * 解析文本信息
     *
     * @param messages 消息
     * @return 返回解析信息
     */
    protected AIParseInfo parseTexInfo(List<Message> messages) {
        // 返回解析信息
        return this.parseInfo(this.config.getTextUrl(), this.config.getTextModel(), messages);
    }

    /**
     * 解析
     *
     * @param url      请求路径
     * @param model    模型
     * @param messages 消息
     * @return 返回解析信息
     */
    protected AIParseInfo parseInfo(String url, String model, List<Message> messages) {
        // 返回解析信息
        return this.getParseInfo(this.getResponse(url, model, messages));
    }

    /**
     * 创建图像消息
     *
     * @param prompt 提示词
     * @param image  图像
     * @return 返回消息
     */
    protected List<Message> createImageMessages(String prompt, BufferedImage image) {
        // 定义消息列表
        List<Message> messages = new ArrayList<>(2);
        // 添加消息
        if (Objects.nonNull(this.config.getImageSystemPrompt())) {
            messages.add(new Message("system", this.config.getImageSystemPrompt()));
        }
        messages.add(new Message("user", this.createImageMessageContents(prompt, image)));
        // 返回消息
        return messages;
    }

    /**
     * 创建图像消息内容
     *
     * @param prompt 提示词
     * @param image  图像
     * @return 返回消息内容
     */
    protected List<Object> createImageMessageContents(String prompt, BufferedImage image) {
        // 定义内容列表
        List<Object> contents = new ArrayList<>(2);
        // 添加内容
        contents.add(new ImageContent(ImageUtil.toBase64(image, ImageType.PNG.getType())));
        contents.add(new TextContent(prompt));
        // 返回内容列表
        return contents;
    }

    /**
     * 创建消息
     *
     * @param prompt 提示词
     * @param text   文本
     * @return 返回消息
     */
    protected List<Message> createTextMessages(String prompt, String text) {
        // 定义消息列表
        List<Message> messages = new ArrayList<>(3);
        // 添加消息
        if (Objects.nonNull(this.config.getTextSystemPrompt())) {
            messages.add(new Message("system", this.config.getTextSystemPrompt()));
        }
        messages.add(new Message("user", text));
        messages.add(new Message("user", prompt));
        // 返回消息
        return messages;
    }

    /**
     * 获取授权
     *
     * @return 返回授权
     */
    protected String getAuthorization() {
        return "Bearer " + this.config.getApiKey();
    }

    /**
     * 获取请求头
     *
     * @return 返回请求头
     */
    protected Headers getRequestHeaders() {
        return new Headers.Builder().add("Authorization", this.getAuthorization()).build();
    }

    /**
     * 获取请求体
     *
     * @param model    模型
     * @param messages 消息
     * @return 返回请求体
     */
    protected JSONObject getRequestBody(String model, List<Message> messages) {
        JSONObject jsonBody = JSONObject.of("model", model).fluentPut("messages", messages);
        if (this.config.isJsonResponse()) {
            jsonBody.fluentPut("response_format ", JSONObject.of("type", "json_object"));
        }
        return jsonBody;
    }

    /**
     * 获取响应
     *
     * @param url      地址
     * @param model    模型
     * @param messages 消息
     * @return 返回响应
     */
    protected JSONObject getResponse(String url, String model, List<Message> messages) {
        // 响应体
        JSONObject response = HttpUtil.request(this.config.getClient(), url, this.getRequestHeaders(), this.getRequestBody(model, messages).toString());
        if (response.containsKey("error")) {
            throw new RuntimeException(response.toString());
        }
        return response;
    }

    /**
     * 获取解析信息
     *
     * @param response 响应
     * @return 返回解析信息
     */
    protected AIParseInfo getParseInfo(JSONObject response) {
        // 返回解析信息
        return new AIParseInfo(response.getString(this.taskIdKey), String.valueOf(response.getByPath(CONTENT_JSON_PATH)), String.valueOf(response.getByPath(TOKEN_JSON_PATH)));
    }

    /**
     * 获取页面图像
     * <p>注：页面转图像</p>
     *
     * @param pageIndex 页面索引
     * @return 返回页面图像
     */
    protected BufferedImage getPageImage(int pageIndex) {
        RenderProcessor processor = PdfHandler.getDocumentProcessor(this.document).getRenderProcessor();
        processor.setDpi(300F);
        return processor.image(ImageType.PNG, pageIndex);
    }

    /**
     * 获取页面图像
     * <p>注：页面中指定索引的图像</p>
     *
     * @param pageIndex 页面索引
     * @return 返回页面图像
     */
    protected BufferedImage getPageImage(int pageIndex, int imageIndex) {
        DocumentAnalyzer analyzer = PdfHandler.getDocumentAnalyzer(this.document);
        List<ImageInfo> list = analyzer.analyzeImage(pageIndex).stream().sorted(Comparator.comparing(ImageInfo::getImageIndex)).collect(Collectors.toList());
        try {
            return list.get(imageIndex).getImage();
        } catch (Exception e) {
            throw new IllegalArgumentException("the image index['" + imageIndex + "'] is error");
        }
    }

    /**
     * 获取页面文本
     *
     * @param beginPageIndex 起始页面索引
     * @param endPageIndex   结束页面索引
     * @return 返回文本
     */
    @SneakyThrows
    protected String getPageText(int beginPageIndex, int endPageIndex) {
        PDFTextStripper stripper = new PDFTextStripper();
        if (beginPageIndex > -1) {
            stripper.setStartPage(beginPageIndex + 1);
        }
        if (endPageIndex > -1) {
            stripper.setEndPage(endPageIndex + 1);
        }
        return "阅读下面文章，并根据文章内容回答问题，不要复述问题，直接开始回答\n\n" + stripper.getText(this.getDocument());
    }

    /**
     * 消息
     */
    @Setter
    @Getter
    @AllArgsConstructor
    protected static class Message {
        /**
         * 角色
         */
        protected String role;
        /**
         * 内容
         */
        protected Object content;
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
        protected String type = "text";
        /**
         * 文本
         */
        protected String text;

        /**
         * 有参构造
         *
         * @param text 文本
         */
        public TextContent(String text) {
            this.text = text;
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
        protected String type = "image_url";
        /**
         * 图像地址
         */
        protected ZhiPuAIParser.ImageUrl image_url;

        /**
         * 有参构造
         *
         * @param url 图像地址
         */
        public ImageContent(String url) {
            this.image_url = new ZhiPuAIParser.ImageUrl(url);
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
        protected String url;

        /**
         * 有参构造
         *
         * @param url base64
         */
        public ImageUrl(String url) {
            this.url = "data:image/png;base64," + url;
        }
    }
}
