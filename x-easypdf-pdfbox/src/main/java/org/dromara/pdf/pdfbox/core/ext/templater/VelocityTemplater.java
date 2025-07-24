package org.dromara.pdf.pdfbox.core.ext.templater;

import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.Writer;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * velocity模板引擎
 *
 * @author xsx
 * @date 2025/7/21
 * @see <a href="https://velocity.apache.org/engine/">官方文档</a>
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
public class VelocityTemplater extends AbstractHtmlTemplater {

    /**
     * 引擎缓存
     */
    protected static final Map<String, VelocityEngine> ENGINE_CACHE = new ConcurrentHashMap<>(16);


    /**
     * 有参构造
     *
     * @param document 文档
     */
    public VelocityTemplater(Document document) {
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
        // 非资源路径
        if (Objects.isNull(Thread.currentThread().getContextClassLoader().getResource(templatePath))) {
            ENGINE_CACHE.computeIfAbsent(templatePath, this::initFileEngine);
        } else {
            ENGINE_CACHE.computeIfAbsent(templatePath, this::initClassPathEngine);
        }
    }

    /**
     * 初始化文件路径模板引擎
     *
     * @return 返回模板引擎
     */
    protected VelocityEngine initFileEngine(String templatePath) {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "file");
        engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);
        engine.setProperty("resource.loader.file.class", FileResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    /**
     * 初始化类路径模板引擎
     *
     * @return 返回模板引擎
     */
    protected VelocityEngine initClassPathEngine(String templatePath) {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "class");
        engine.setProperty("resource.loader.path", templatePath);
        engine.setProperty("resource.loader.class.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    /**
     * 处理
     *
     * @param writer 写入器
     */
    @SneakyThrows
    @Override
    protected void process(Writer writer) {
        // 获取模板全路径
        String templatePath = String.join("/", this.templatePath, this.templateName);
        // 非资源路径
        if (Thread.currentThread().getContextClassLoader().getResource(templatePath) == null) {
            // 渲染模板
            ENGINE_CACHE.get(this.templatePath).getTemplate(this.templateName).merge(this.getEngineContext(), writer);
        } else {
            // 渲染模板
            ENGINE_CACHE.get(this.templatePath).getTemplate(templatePath).merge(this.getEngineContext(), writer);
        }
    }

    /**
     * 获取模板引擎上下文
     *
     * @return 返回模板引擎上下文
     */
    protected VelocityContext getEngineContext() {
        return new VelocityContext(this.templateData);
    }
}
