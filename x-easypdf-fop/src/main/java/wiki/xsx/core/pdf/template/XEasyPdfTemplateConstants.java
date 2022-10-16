package wiki.xsx.core.pdf.template;

/**
 * pdf模板常量
 *
 * @author xsx
 * @date 2022/8/3
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
public final class XEasyPdfTemplateConstants {

    /**
     * fop生产者
     */
    public static final String FOP_PRODUCER = "x-easypdf/fop";
    /**
     * 默认配置路径
     */
    public static final String DEFAULT_CONFIG_PATH = "/wiki/xsx/core/pdf/template/fop.xconf";
    /**
     * 默认模板路径
     */
    public static final String DEFAULT_TEMPLATE_PATH = "/wiki/xsx/core/pdf/template/template.fo";
    /**
     * 默认边框参数
     */
    public static final String DEFAULT_BORDER_VALUE = "1px solid black";
    /**
     * 默认分割线样式
     */
    public static final String DEFAULT_SPLIT_LINE_STYLE_VALUE = "rule";
    /**
     * 默认虚线分割线样式
     */
    public static final String DEFAULT_DOTTED_SPLIT_LINE_STYLE_VALUE = "dots";
    /**
     * 命名空间
     */
    public static final String NAMESPACE = "http://www.x-easypdf.cn/ns";
}
