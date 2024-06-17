package org.dromara.pdf.fop.core.base;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.fop.configuration.Configuration;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.dromara.pdf.fop.core.datasource.DataSource;
import org.dromara.pdf.fop.support.layout.ExpandLayoutManagerMaker;
import org.dromara.pdf.fop.support.layout.LayoutManagerMapping;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * pdf模板参数
 *
 * @author xsx
 * @date 2022/7/31
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
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
class TemplateParam {

    /**
     * 配置路径
     */
    private String configPath;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 主题
     */
    private String subject;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * fop工厂
     */
    private FopFactory fopFactory;
    /**
     * 用户代理
     */
    private FOUserAgent userAgent;
    /**
     * 基础路径
     */
    private URI baseUri;
    /**
     * 布局管理器
     */
    private ExpandLayoutManagerMaker layoutManagerMaker;
    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * 是否开启辅助功能
     */
    private Boolean isAccessibility = Boolean.FALSE;
    /**
     * 是否开启保留空标签
     */
    private Boolean isKeepEmptyTags = Boolean.TRUE;
    /**
     * 是否开启错误定位信息
     */
    private Boolean isErrorInfo = Boolean.TRUE;
    /**
     * 是否开启保留内存
     */
    private Boolean isConserveMemory = Boolean.FALSE;

    /**
     * 初始化参数
     */
    void initParams() {
        // 如果配置路径未初始化，则初始化为默认配置
        if (Objects.isNull(this.configPath)) {
            // 初始化默认配置
            this.configPath = Constants.DEFAULT_CONFIG_PATH;
        }
        // 如果数据源未初始化，则提示错误信息
        if (Objects.isNull(this.dataSource)) {
            // 提示错误信息
            throw new IllegalArgumentException("the data source can not be null");
        }
        // 如果fop工厂未初始化，则初始化
        if (Objects.isNull(this.fopFactory)) {
            // 初始化fop工厂
            this.fopFactory = this.initFopFactory();
        }
        // 如果用户代理未初始化，则初始化
        if (Objects.isNull(this.userAgent)) {
            // 初始化用户代理
            this.userAgent = this.initUserAgent();
        }
        // 如果布局管理器未初始化，则初始化
        if (Objects.isNull(this.layoutManagerMaker)) {
            // 初始化布局管理器
            layoutManagerMaker = new LayoutManagerMapping();
        }
        // 初始化布局管理器
        this.layoutManagerMaker.initialize(this.userAgent);
    }

    /**
     * 初始化fop工厂
     *
     * @return 返回fop工厂
     */
    @SneakyThrows
    FopFactory initFopFactory() {
        // 定义输入流
        InputStream inputStream = null;
        try {
            // 创建配置输入流（从资源路径读取）
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.configPath);
            // 如果为空，则从绝对路径读取
            if (inputStream == null) {
                // 创建配置输入流（从绝对路径读取）
                inputStream = Files.newInputStream(Paths.get(this.configPath));
            }
            // 初始化配置
            Configuration configuration = this.initConfiguration(new DefaultConfigurationBuilder().build(inputStream));
            // 返回fop工厂
            return new FopFactoryBuilder(
                    this.baseUri,
                    new ImageResourceResolver(this.baseUri)
            ).setLayoutManagerMakerOverride(
                    this.layoutManagerMaker
            ).setConfiguration(
                    configuration
            ).build();
        } finally {
            // 如果输入流不为空，则关闭
            if (inputStream != null) {
                // 关闭输入流
                inputStream.close();
            }
        }
    }

    /**
     * 初始化用户代理
     *
     * @return 返回用户代理
     */
    FOUserAgent initUserAgent() {
        // 创建代理
        FOUserAgent userAgent = this.fopFactory.newFOUserAgent();
        // 设置资源解析器
        userAgent.getFontManager().setResourceResolver(
                ResourceResolverFactory.createInternalResourceResolver(
                        this.baseUri,
                        new FontResourceResolver(this.initResource(this.fopFactory.getUserConfig()))
                )
        );
        // 设置生产者
        userAgent.setProducer(Constants.PRODUCER);
        // 设置开启辅助功能
        userAgent.setAccessibility(this.isAccessibility);
        // 设置作者
        userAgent.setAuthor(this.author);
        // 设置创建者
        userAgent.setCreator(this.creator);
        // 设置标题
        userAgent.setTitle(this.title);
        // 设置主题
        userAgent.setSubject(this.subject);
        // 设置关键词
        userAgent.setKeywords(this.keywords);
        // 设置创建时间
        userAgent.setCreationDate(this.creationDate);
        // 设置保留空标签
        userAgent.setKeepEmptyTags(this.isKeepEmptyTags);
        // 设置开启错误定位信息
        userAgent.setLocatorEnabled(this.isErrorInfo);
        // 设置开启保留内存
        userAgent.setConserveMemoryPolicy(this.isConserveMemory);
        // 返回代理
        return userAgent;
    }

    /**
     * 初始化资源
     *
     * @param configuration 配置
     * @return 返回资源字典
     */
    @SneakyThrows
    private Map<String, String> initResource(Configuration configuration) {
        // 定义资源字典
        Map<String, String> resource = new HashMap<>(10);
        // 获取类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取renderers节点
        Configuration renderers = configuration.getChild("renderers");
        // 获取renderer节点
        Configuration[] rendererArray = renderers.getChildren("renderer");
        // 遍历renderer节点
        for (Configuration renderer : rendererArray) {
            // 如果为pdf渲染器，则解析
            if ("application/pdf".equals(renderer.getAttribute("mime"))) {
                // 获取fonts节点
                Configuration fonts = renderer.getChild("fonts");
                // 获取font节点
                Configuration[] fontArray = fonts.getChildren("font");
                // 遍历font节点
                for (Configuration font : fontArray) {
                    // 获取embed-url属性
                    String attribute = font.getAttribute("embed-url");
                    // 如果embed-url属性不为空，且为资源路径，则放入资源字典
                    if (attribute != null && classLoader.getResource(attribute) != null) {
                        // 放入资源字典
                        resource.put(FilenameUtils.getName(attribute), attribute);
                    }
                }
            }
        }
        // 返回资源字典
        return resource;
    }

    /**
     * 初始化配置
     *
     * @param configuration 配置
     * @return 返回配置
     */
    @SneakyThrows
    private Configuration initConfiguration(Configuration configuration) {
        // 获取基础路径配置
        Configuration baseConfig = configuration.getChild("base");
        // 如果基础路径配置为空，则初始化为当前路径
        if (baseConfig == null) {
            // 初始化为当前路径
            this.baseUri = new File(".").toURI();
        }
        // 否则初始化为给定路径
        else {
            // 初始化为给定路径
            this.baseUri = new File(baseConfig.getValue(".")).toURI();
        }
        // 返回配置
        return configuration;
    }

    /**
     * 字体资源解析器
     */
    public static final class FontResourceResolver implements ResourceResolver {

        /**
         * 资源字体
         */
        private final Map<String, String> resources;

        /**
         * 有参构造
         *
         * @param resources 资源字体
         */
        public FontResourceResolver(Map<String, String> resources) {
            this.resources = resources;
        }

        /**
         * 获取资源
         *
         * @param uri uri
         * @return 返回资源
         * @throws IOException IO异常
         */
        @Override
        public Resource getResource(URI uri) throws IOException {
            // 获取资源字体路径
            String resourceFontPath = this.resources.get(FilenameUtils.getName(uri.toString()));
            // 定义输入流
            InputStream inputStream;
            // 如果资源字体路径不为空，则从资源路径读取
            if (resourceFontPath != null) {
                // 读取输入流（从资源路径读取）
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceFontPath);
            } else {
                // 读取输入流（从绝对路径读取）
                inputStream = uri.toURL().openStream();
            }
            // 返回资源
            return new Resource(inputStream);
        }

        /**
         * 获取输出流
         *
         * @param uri uri
         * @return 返回输出流
         * @throws IOException IO异常
         */
        @SuppressWarnings("all")
        @Override
        public OutputStream getOutputStream(URI uri) throws IOException {
            return uri.toURL().openConnection().getOutputStream();
        }
    }

    /**
     * 图像资源解析器
     */
    public static final class ImageResourceResolver implements ResourceResolver {

        /**
         * 基础索引
         */
        private final Integer baseIndexOf;
        /**
         * 类型
         */
        private static final String TYPE = "file";

        /**
         * 有参构造
         *
         * @param baseUri 基础路径
         */
        @SneakyThrows
        public ImageResourceResolver(URI baseUri) {
            this.baseIndexOf = Paths.get(baseUri).toRealPath().toUri().getPath().length();
        }

        /**
         * 获取资源
         *
         * @param uri uri
         * @return 返回资源
         * @throws IOException IO异常
         */
        @Override
        public Resource getResource(URI uri) throws IOException {
            // 定义输入流
            InputStream inputStream;
            // 如果为文件类型，则从本地读取
            if (TYPE.equals(uri.getScheme()) && uri.getPath().length() > this.baseIndexOf) {
                // 读取输入流（从资源路径读取）
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri.getPath().substring(this.baseIndexOf));
                // 如果输入流为空，则从绝对路径读取
                if (inputStream == null) {
                    // 读取输入流（从绝对路径读取）
                    inputStream = Files.newInputStream(Paths.get(uri));
                }
            } else {
                // 读取输入流（从网络请求）
                inputStream = uri.toURL().openStream();
            }
            // 返回资源
            return new Resource(inputStream);
        }

        /**
         * 获取输出流
         *
         * @param uri uri
         * @return 返回输出流
         * @throws IOException IO异常
         */
        @SuppressWarnings("all")
        @Override
        public OutputStream getOutputStream(URI uri) throws IOException {
            return uri.toURL().openConnection().getOutputStream();
        }
    }
}
