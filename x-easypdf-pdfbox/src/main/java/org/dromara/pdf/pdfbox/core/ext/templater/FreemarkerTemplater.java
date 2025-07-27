package org.dromara.pdf.pdfbox.core.ext.templater;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.Setter;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.File;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * freemarker模板引擎
 *
 * @author xsx
 * @date 2025/7/21
 * @see <a href="https://freemarker.apache.org/docs/">官方文档</a>
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
public class FreemarkerTemplater extends AbstractHtmlTemplater {

    /**
     * 模板配置
     */
    protected static final Map<String, Configuration> CONFIGURATION = new ConcurrentHashMap<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public FreemarkerTemplater(Document document) {
        super(document);
    }

    /**
     * 模板路径
     *
     * @param templatePath 模板路径
     */
    @Override
    public void setTemplatePath(String templatePath) {
        super.setTemplatePath(templatePath);
        CONFIGURATION.computeIfAbsent(templatePath, this::initConfiguration);
    }

    /**
     * 处理
     *
     * @param writer 写入器
     */
    @SneakyThrows
    @Override
    protected void process(Writer writer) {
        Configuration configuration = CONFIGURATION.get(this.templatePath);
        configuration.getTemplate(this.templateName, StandardCharsets.UTF_8.name()).process(this.templateData, writer);
    }

    /**
     * 初始化配置
     *
     * @param templatePath 模板路径
     * @return 返回配置
     */
    @SneakyThrows
    protected Configuration initConfiguration(String templatePath) {
        // 创建配置
        Configuration config = new Configuration(Configuration.VERSION_2_3_34);
        // 设置默认编码
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 设置模板异常处理器
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 设置模板异常日志
        config.setLogTemplateExceptions(false);
        // 设置未检查异常
        config.setWrapUncheckedExceptions(true);
        // 设置空循环变量回退
        config.setFallbackOnNullLoopVariable(false);
        // 非资源路径
        if (Thread.currentThread().getContextClassLoader().getResource(templatePath) == null) {
            try {
                // 设置文件目录解析器
                config.setDirectoryForTemplateLoading(Paths.get(templatePath).toFile());
            } catch (Exception e) {
                // 设置远程资源解析器
                config.setTemplateLoader(new DefaultURLTemplateLoader(templatePath));
            }
        } else {
            // 设置资源解析器
            config.setTemplateLoader(new ClassTemplateLoader(FreemarkerTemplater.class, File.separator + templatePath));
        }
        // 返回配置
        return config;
    }

    /**
     * 默认远程模板加载器
     */
    public static class DefaultURLTemplateLoader extends URLTemplateLoader {

        /**
         * 远程路径（目录）
         */
        private final String remotePath;
        /**
         * 模板名称
         */
        @Setter
        private String templateName;

        /**
         * 有参构造
         *
         * @param remotePath 远程路径（目录）
         */
        public DefaultURLTemplateLoader(String remotePath) {
            this.remotePath = remotePath;
        }

        /**
         * 获取路径
         *
         * @param name 模板名称
         * @return 返回路径
         */
        @Override
        protected URL getURL(String name) {
            if (!Objects.equals(this.templateName, name)) {
                return null;
            }
            String fullPath = this.remotePath + name;
            if ((this.remotePath.equals("/")) && this.isInvalid(fullPath)) {
                return null;
            }

            URL url;
            try {
                url = new URL(fullPath);
            } catch (MalformedURLException e) {
                url = null;
            }
            return url;
        }

        /**
         * 是否无效
         *
         * @param fullPath 完整路径
         * @return 返回布尔值，true为是，false为否
         */
        private boolean isInvalid(String fullPath) {
            int i = 0;
            int ln = fullPath.length();
            if ((i < ln) && (fullPath.charAt(i) == '/')) {
                i++;
            }
            while (i < ln) {
                char c = fullPath.charAt(i);
                if (c == '/') {
                    return false;
                }
                if (c == ':') {
                    return true;
                }
                i++;
            }
            return false;
        }
    }
}
