package org.dromara.pdf.fop.core.doc.component.link;

import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.base.TemplateTags;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.dromara.pdf.fop.core.doc.component.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * pdf模板-超链接组件
 * <p>fo:basic-link</p>
 *
 * @author xsx
 * @date 2022/11/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Link implements Component {

    /**
     * 超链接参数
     */
    private final LinkParam param = new LinkParam();

    /**
     * 设置id
     *
     * @param id id
     * @return 返回超链接组件
     */
    public Link setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置文本
     *
     * @param text 文本组件
     * @return 返回超链接组件
     */
    public Link setText(Text text) {
        this.param.setComponent(text);
        return this;
    }

    /**
     * 设置内部地址
     * <p>注：标签id</p>
     *
     * @param destination 地址
     * @return 返回超链接组件
     */
    public Link setInternalDestination(String destination) {
        this.param.setInternalDestination(destination);
        return this;
    }

    /**
     * 设置外部地址
     * <p>注：url</p>
     *
     * @param destination 地址
     * @return 返回超链接组件
     */
    public Link setExternalDestination(String destination) {
        this.param.setExternalDestination(destination);
        return this;
    }

    /**
     * 设置分页符-前
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之前强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之前强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakBefore 分页符
     * @return 返回超链接组件
     */
    public Link setBreakBefore(String breakBefore) {
        this.param.setBreakBefore(breakBefore);
        return this;
    }

    /**
     * 设置分页符-后
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之后强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之后强制分页一次或两个，以便下一页将成为奇数页</p>
     *
     * @param breakAfter 分页符
     * @return 返回超链接组件
     */
    public Link setBreakAfter(String breakAfter) {
        this.param.setBreakAfter(breakAfter);
        return this;
    }

    /**
     * 开启分页时保持
     *
     * @return 返回超链接组件
     */
    public Link enableKeepTogether() {
        this.param.setKeepTogether("always");
        return this;
    }

    /**
     * 开启分页时与上一个元素保持
     *
     * @return 返回超链接组件
     */
    public Link enableKeepWithPrevious() {
        this.param.setKeepWithPrevious("always");
        return this;
    }

    /**
     * 开启分页时与下一个元素保持
     *
     * @return 返回超链接组件
     */
    public Link enableKeepWithNext() {
        this.param.setKeepWithNext("always");
        return this;
    }

    /**
     * 创建元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    @Override
    public Element createElement(Document document) {
        // 创建block元素
        Element block = this.createBlockElement(document, this.param);
        // 添加link元素
        block.appendChild(this.createLink(document));
        // 返回block元素
        return block;
    }

    /**
     * 创建link元素
     *
     * @param document fo文档
     * @return 返回link元素
     */
    private Element createLink(Document document) {
        // 创建link元素
        Element link = document.createElement(TemplateTags.BASIC_LINK);
        // 设置内部地址
        Optional.ofNullable(this.param.getInternalDestination()).ifPresent(v -> link.setAttribute(TemplateAttributes.INTERNAL_DESTINATION, v.intern()));
        // 设置外部地址
        Optional.ofNullable(this.param.getExternalDestination()).ifPresent(v -> link.setAttribute(TemplateAttributes.EXTERNAL_DESTINATION, v.intern()));
        // 添加组件
        Optional.ofNullable(this.param.getComponent()).ifPresent(v -> link.appendChild(v.createElement(document)));
        // 返回link元素
        return link;
    }
}
