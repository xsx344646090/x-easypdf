package org.dromara.pdf.pdfbox.core.ext.templater;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;

import java.util.function.Supplier;

/**
 * 文档模板引擎
 *
 * @author xsx
 * @date 2025/7/21
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
public class DocumentTemplater extends AbstractTemplater {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentTemplater(Document document) {
        super(document);
    }

    /**
     * 获取freemarker引擎
     *
     * @return 返回freemarker引擎
     */
    public FreemarkerTemplater getFreemarkerTemplater() {
        return new FreemarkerTemplater(this.document);
    }

    /**
     * 获取thymeleaf引擎
     *
     * @return 返回thymeleaf引擎
     */
    public ThymeleafTemplater getThymeleafTemplater() {
        return new ThymeleafTemplater(this.document);
    }

    /**
     * 获取jte引擎
     *
     * @return 返回jte引擎
     */
    public JteTemplater getJteTemplater() {
        return new JteTemplater(this.document);
    }

    /**
     * 获取beetl引擎
     *
     * @return 返回beetl引擎
     */
    public BeetlTemplater getBeetlTemplater() {
        return new BeetlTemplater(this.document);
    }

    /**
     * 获取enjoy引擎
     *
     * @return 返回enjoy引擎
     */
    public EnjoyTemplater getEnjoyTemplater() {
        return new EnjoyTemplater(this.document);
    }

    /**
     * 获取velocity引擎
     *
     * @return 返回velocity引擎
     */
    public VelocityTemplater getVelocityTemplater() {
        return new VelocityTemplater(this.document);
    }

    /**
     * 获取自定义引擎
     *
     * @return 返回自定义引擎
     */
    @SneakyThrows
    public <T extends AbstractHtmlTemplater> T getCustomTemplater(Supplier<T> supplier) {
        return supplier.get();
    }
}
