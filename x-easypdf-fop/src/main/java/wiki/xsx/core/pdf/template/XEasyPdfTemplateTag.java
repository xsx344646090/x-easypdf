package wiki.xsx.core.pdf.template;

/**
 * pdf模板标签
 *
 * @author xsx
 * @date 2022/8/11
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplateTag {

    /**
     * 页面模板
     * <p>标签："fo:layout-master-set"</p>
     */
    public static final String LAYOUT_MASTER_SET = "fo:layout-master-set";
    /**
     * 单页面模板
     * <p>标签："fo:simple-page-master"</p>
     */
    public static final String SIMPLE_PAGE_MASTER = "fo:simple-page-master";
    /**
     * 主体（页面的主体）
     * <p>标签："fo:region-body"</p>
     */
    public static final String REGION_BODY = "fo:region-body";
    /**
     * 页眉
     * <p>标签："fo:region-before"</p>
     */
    public static final String REGION_BEFORE = "fo:region-before";
    /**
     * 页脚
     * <p>标签："fo:region-after"</p>
     */
    public static final String REGION_AFTER = "fo:region-after";
    /**
     * 页面序列
     * <p>标签："fo:page-sequence"</p>
     */
    public static final String PAGE_SEQUENCE = "fo:page-sequence";
    /**
     * 静态内容
     * <p>标签："fo:static-content"</p>
     */
    public static final String STATIC_CONTENT = "fo:static-content";
    /**
     * 页面流
     * <p>标签："fo:flow"</p>
     */
    public static final String FLOW = "fo:flow";
    /**
     * 块
     * <p>标签："fo:block"</p>
     */
    public static final String BLOCK = "fo:block";
    /**
     * 内行
     * <p>标签："fo:inline"</p>
     */
    public static final String IN_LINE = "fo:inline";
    /**
     * 外部图形
     * <p>标签："fo:external-graphic"</p>
     */
    public static final String EXTERNAL_GRAPHIC = "fo:external-graphic";
}