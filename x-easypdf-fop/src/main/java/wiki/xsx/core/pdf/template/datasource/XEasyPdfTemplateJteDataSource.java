package wiki.xsx.core.pdf.template.datasource;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * pdf模板-jte数据源
 *
 * @author xsx
 * @date 2022/11/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
@Accessors(chain = true)
public class XEasyPdfTemplateJteDataSource extends XEasyPdfTemplateAbstractDataSource {

    /**
     * 引擎缓存
     */
    private static final Map<String, TemplateEngine> ENGINE_CACHE = new HashMap<>(10);
    /**
     * 缓存锁
     */
    private static final Object LOCK = new Object();

    /**
     * 设置模板路径
     *
     * @param templatePath 模板路径
     * @return 返回jte数据源
     */
    public XEasyPdfTemplateJteDataSource setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    /**
     * 设置模板数据
     *
     * @param templateData 模板数据
     * @return 返回jte数据源
     */
    public XEasyPdfTemplateJteDataSource setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
        return this;
    }

    /**
     * 处理模板
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    protected InputStream processTemplate() {
        // 创建输出流
        try (StringOutput output = new StringOutput()) {
            // 如果非资源路径，则为目录解析器
            if (Thread.currentThread().getContextClassLoader().getResource(this.templatePath) == null) {
                // 处理模板
                this.getTemplateEngine().render(Paths.get(this.templatePath).getFileName().toString(), this.templateData, output);
            }
            // 否则为资源解析器
            else {
                // 处理模板
                this.getTemplateEngine().render(this.templatePath, this.templateData, output);
            }
            // 返回输入流
            return new BufferedInputStream(new ByteArrayInputStream(output.toString().getBytes()));
        }
    }

    /**
     * 获取引擎
     *
     * @return 返回引擎
     */
    private TemplateEngine getTemplateEngine() {
        // 获取引擎
        TemplateEngine engine = XEasyPdfTemplateJteDataSource.ENGINE_CACHE.get(this.templatePath);
        // 如果引擎为空，则创建引擎
        if (engine == null) {
            // 添加锁
            synchronized (LOCK) {
                // 再次获取引擎
                engine = XEasyPdfTemplateJteDataSource.ENGINE_CACHE.get(this.templatePath);
                // 如果引擎仍然为空，则创建引擎
                if (engine == null) {
                    // 定义解析器
                    CodeResolver resolver;
                    // 如果非资源路径，则重置解析器为目录解析器
                    if (Thread.currentThread().getContextClassLoader().getResource(this.templatePath) == null) {
                        // 重置解析器为目录解析器
                        resolver = new DirectoryCodeResolver(Paths.get(this.templatePath).getParent());
                    }
                    // 否则重置解析器为资源解析器
                    else {
                        // 重置解析器为资源解析器
                        resolver = new ResourceCodeResolver(".", Thread.currentThread().getContextClassLoader());
                    }
                    // 创建模板引擎
                    engine = TemplateEngine.create(resolver, ContentType.Html);
                    // 添加引擎缓存
                    XEasyPdfTemplateJteDataSource.ENGINE_CACHE.put(this.templatePath, engine);
                }
            }
        }
        // 返回引擎
        return engine;
    }
}
