package org.dromara.pdf.pdfbox.core.ext.templater;

import com.jfinal.template.Engine;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.io.Writer;
import java.util.Objects;

/**
 * enjoy模板引擎
 *
 * @author xsx
 * @date 2025/7/21
 * @see <a href="https://www.jfinal.com/doc/6-1">官方文档</a>
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
public class EnjoyTemplater extends AbstractHtmlTemplater {

    /**
     * 文件路径模板引擎
     */
    protected static final Engine FILEPATH_TEMPLATE_ENGINE = initFileEngine();
    /**
     * 类路径模板引擎
     */
    protected static final Engine CLASSPATH_TEMPLATE_ENGINE = initClassPathEngine();

    static {
        // 极速模式
        Engine.setFastMode(true);
        // 支持中文表达式
        Engine.setChineseExpression(true);
    }

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public EnjoyTemplater(Document document) {
        super(document);
    }

    /**
     * 初始化文件路径模板引擎
     *
     * @return 返回模板引擎
     */
    protected static Engine initFileEngine() {
        return Engine.create("file").setDevMode(false).setCompressorOn('\n').setStaticFieldExpression(true);
    }

    /**
     * 初始化类路径模板引擎
     *
     * @return 返回模板引擎
     */
    protected static Engine initClassPathEngine() {
        return Engine.create("classPath").setDevMode(false).setCompressorOn('\n').setToClassPathSourceFactory().setStaticFieldExpression(true);
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
        if (Objects.isNull(Thread.currentThread().getContextClassLoader().getResource(templatePath))) {
            FILEPATH_TEMPLATE_ENGINE.getTemplate(templatePath).render(this.templateData, writer);
        } else {
            CLASSPATH_TEMPLATE_ENGINE.getTemplate(templatePath).render(this.templateData, writer);
        }
    }
}
