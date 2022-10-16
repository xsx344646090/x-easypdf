package wiki.xsx.core.pdf.template.template;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateDataSource;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * pdf模板参数
 *
 * @author xsx
 * @date 2022/7/31
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
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
class XEasyPdfTemplateParam {

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
     * 数据源
     */
    private XEasyPdfTemplateDataSource dataSource;
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
    private Boolean isErrorInfo = Boolean.FALSE;

    /**
     * 初始化参数
     */
    void initParams() {
        // 如果配置路径未初始化，则初始化为默认配置
        if (this.configPath == null) {
            // 初始化默认配置
            this.configPath = XEasyPdfTemplateConstants.DEFAULT_CONFIG_PATH;
        }
        // 如果数据源未初始化，则提示错误信息
        if (this.dataSource == null) {
            // 提示错误信息
            throw new IllegalArgumentException("the data source can not be null");
        }
        // 如果fop工厂未初始化，则初始化
        if (this.fopFactory == null) {
            // 初始化fop工厂
            this.fopFactory = this.initFopFactory();
        }
        // 如果用户代理未初始化，则初始化
        if (this.userAgent == null) {
            // 初始化用户代理
            this.userAgent = this.initUserAgent();
        }
    }

    /**
     * 初始化fop工厂
     *
     * @return 返回fop工厂
     */
    @SneakyThrows
    FopFactory initFopFactory() {
        // 定义fop工厂
        FopFactory factory;
        try (
                // 创建配置输入流（从资源路径读取）
                InputStream inputStream = this.getClass().getResourceAsStream(this.configPath)
        ) {
            // 创建fop工厂
            factory = new FopFactoryBuilder(
                    new File(".").toURI()
            ).setConfiguration(
                    new DefaultConfigurationBuilder().build(inputStream)
            ).build();
        } catch (Exception e) {
            // 创建配置输入流（从绝对路径读取）
            try (InputStream inputStream = Files.newInputStream(Paths.get(this.configPath))) {
                // 创建fop工厂
                factory = new FopFactoryBuilder(
                        new File(this.configPath).toURI()
                ).setConfiguration(
                        new DefaultConfigurationBuilder().build(inputStream)
                ).build();
            }
        }
        // 返回fop工厂
        return factory;
    }

    /**
     * 初始化用户代理
     *
     * @return 返回用户代理
     */
    FOUserAgent initUserAgent() {
        // 创建代理
        FOUserAgent userAgent = this.fopFactory.newFOUserAgent();
        // 设置生产者
        userAgent.setProducer(XEasyPdfTemplateConstants.FOP_PRODUCER);
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
        // 返回代理
        return userAgent;
    }
}
