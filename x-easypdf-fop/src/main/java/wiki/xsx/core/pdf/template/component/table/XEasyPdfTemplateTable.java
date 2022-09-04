package wiki.xsx.core.pdf.template.component.table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.enums.XEasyPdfTemplatePositionStyle;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateElementHandler;

import java.util.Optional;

/**
 * pdf模板-表格组件
 *
 * @author xsx
 * @date 2022/8/22
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
public class XEasyPdfTemplateTable implements XEasyPdfTemplateComponent {

    /**
     * 表格参数
     */
    private final XEasyPdfTemplateTableParam param = new XEasyPdfTemplateTableParam();

    /**
     * 设置表头
     *
     * @param header 表头
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setHeader(XEasyPdfTemplateTableHeader header) {
        this.param.setHeader(header);
        return this;
    }

    /**
     * 设置表格体
     *
     * @param body 表格体
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBody(XEasyPdfTemplateTableBody body) {
        this.param.setBody(body);
        return this;
    }

    /**
     * 设置表尾
     *
     * @param footer 表尾
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setFooter(XEasyPdfTemplateTableFooter footer) {
        this.param.setFooter(footer);
        return this;
    }

    // /**
    //  * 设置上填充
    //  *
    //  * @param paddingTop 上填充
    //  * @return 返回表格组件
    //  */
    // public XEasyPdfTemplateTable setPaddingTop(String paddingTop) {
    //     this.param.setPaddingTop(paddingTop);
    //     return this;
    // }
    //
    // /**
    //  * 设置下填充
    //  *
    //  * @param paddingBottom 下填充
    //  * @return 返回表格组件
    //  */
    // public XEasyPdfTemplateTable setPaddingBottom(String paddingBottom) {
    //     this.param.setPaddingBottom(paddingBottom);
    //     return this;
    // }
    //
    // /**
    //  * 设置左填充
    //  *
    //  * @param paddingLeft 左填充
    //  * @return 返回表格组件
    //  */
    // public XEasyPdfTemplateTable setPaddingLeft(String paddingLeft) {
    //     this.param.setPaddingLeft(paddingLeft);
    //     return this;
    // }
    //
    // /**
    //  * 设置右填充
    //  *
    //  * @param paddingRight 右填充
    //  * @return 返回表格组件
    //  */
    // public XEasyPdfTemplateTable setPaddingRight(String paddingRight) {
    //     this.param.setPaddingRight(paddingRight);
    //     return this;
    // }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置边框
     *
     * @param border 边框
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorder(String border) {
        this.param.setBorder(border);
        return this;
    }

    /**
     * 设置边框折叠
     *
     * @param borderCollapse 边框折叠
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorderCollapse(String borderCollapse) {
        this.param.setBorderCollapse(borderCollapse);
        return this;
    }

    /**
     * 设置边框间距
     *
     * @param borderSpacing 边框间距
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setBorderSpacing(String borderSpacing) {
        this.param.setBorderSpacing(borderSpacing);
        return this;
    }

    /**
     * 设置文本水平样式
     *
     * @param horizontalStyle 水平样式
     * @return 返回表格组件
     */
    public XEasyPdfTemplateTable setHorizontalStyle(XEasyPdfTemplatePositionStyle horizontalStyle) {
        this.param.setHorizontalStyle(horizontalStyle);
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
        // 添加table元素
        block.appendChild(this.createTable(document));
        // 返回block元素
        return block;
    }

    /**
     * 创建table元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTable(Document document) {
        // 创建table元素
        Element table = document.createElement(XEasyPdfTemplateTags.TABLE);
        // 添加表头
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableHeader(document));
        // 添加表格体
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableBody(document));
        // 添加表尾
        XEasyPdfTemplateElementHandler.appendChild(table, this.createTableFooter(document));
        // 设置上填充
        Optional.ofNullable(this.param.getPaddingTop()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.PADDING_TOP, v));
        // 设置宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.WIDTH, v));
        // 设置高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.HEIGHT, v));
        // 设置边框
        Optional.ofNullable(this.param.getBorder()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER, v));
        // 设置边框折叠
        Optional.ofNullable(this.param.getBorderCollapse()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER_COLLAPSE, v));
        // 设置边框间距
        Optional.ofNullable(this.param.getBorderSpacing()).ifPresent(v -> table.setAttribute(XEasyPdfTemplateAttributes.BORDER_SPACING, v));
        // 返回table元素
        return table;
    }

    /**
     * 创建tableHeader元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableHeader(Document document) {
        // 获取表头
        XEasyPdfTemplateTableHeader header = this.param.getHeader();
        // 如果表头为空则返回空，否则返回表头元素
        return header == null ? null : header.createElement(document);
    }

    /**
     * 创建tableBody元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableBody(Document document) {
        // 获取表格体
        XEasyPdfTemplateTableBody body = this.param.getBody();
        // 如果表格体为空，则提示错误信息
        if (body == null) {
            // 提示错误信息
            throw new IllegalArgumentException("the table body can not be null");
        }
        // 返回表格体元素
        return body.createElement(document);
    }

    /**
     * 创建tableFooter元素
     *
     * @param document fo文档
     * @return 返回元素
     */
    private Element createTableFooter(Document document) {
        // 获取表尾
        XEasyPdfTemplateTableFooter footer = this.param.getFooter();
        // 如果表尾为空则返回空，否则返回表尾元素
        return footer == null ? null : footer.createElement(document);
    }
}
