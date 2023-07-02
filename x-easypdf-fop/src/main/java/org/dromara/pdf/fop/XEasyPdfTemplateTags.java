package org.dromara.pdf.fop;

/**
 * pdf模板标签
 *
 * @author xsx
 * @date 2022/8/11
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfTemplateTags {

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
     * 左侧栏
     * <p>标签："fo:region-start"</p>
     */
    public static final String REGION_START = "fo:region-start";
    /**
     * 右侧栏
     * <p>标签："fo:region-end"</p>
     */
    public static final String REGION_END = "fo:region-end";
    /**
     * 页面序列
     * <p>标签："fo:page-sequence"</p>
     */
    public static final String PAGE_SEQUENCE = "fo:page-sequence";
    /**
     * 页码
     * <p>标签："fo:page-number"</p>
     */
    public static final String PAGE_NUMBER = "fo:page-number";
    /**
     * 总页码
     * <p>标签："fo:page-number-citation-last"</p>
     */
    public static final String PAGE_NUMBER_CITATION_LAST = "fo:page-number-citation-last";
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
     * 块容器
     * <p>标签："fo:block-container"</p>
     */
    public static final String BLOCK_CONTAINER = "fo:block-container";
    /**
     * 内联
     * <p>标签："fo:inline"</p>
     */
    public static final String IN_LINE = "fo:inline";
    /**
     * 外部图形
     * <p>标签："fo:external-graphic"</p>
     */
    public static final String EXTERNAL_GRAPHIC = "fo:external-graphic";
    /**
     * 表格
     * <p>标签："fo:table"</p>
     */
    public static final String TABLE = "fo:table";
    /**
     * 表格列
     * <p>标签："fo:table-column"</p>
     */
    public static final String TABLE_COLUMN = "fo:table-column";
    /**
     * 表头
     * <p>标签："fo:table-header"</p>
     */
    public static final String TABLE_HEADER = "fo:table-header";
    /**
     * 表尾
     * <p>标签："fo:table-footer"</p>
     */
    public static final String TABLE_FOOTER = "fo:table-footer";
    /**
     * 表体
     * <p>标签："fo:table-body"</p>
     */
    public static final String TABLE_BODY = "fo:table-body";
    /**
     * 表格行
     * <p>标签："fo:table-row"</p>
     */
    public static final String TABLE_ROW = "fo:table-row";
    /**
     * 表格单元格
     * <p>标签："fo:table-cell"</p>
     */
    public static final String TABLE_CELL = "fo:table-cell";
    /**
     * 分割线
     * <p>标签："fo:leader"</p>
     */
    public static final String LEADER = "fo:leader";
    /**
     * 外部对象
     * <p>标签："fo:instream-foreign-object"</p>
     */
    public static final String INSTREAM_FOREIGN_OBJECT = "fo:instream-foreign-object";
    /**
     * 条形码
     * <p>标签："fo:barcode"</p>
     */
    public static final String BARCODE = "xe:barcode";
    /**
     * 超链接
     * <p>标签："fo:basic-link"</p>
     */
    public static final String BASIC_LINK = "fo:basic-link";
    /**
     * 书签树
     * <p>标签："fo:bookmark-tree"</p>
     */
    public static final String BOOKMARK_TREE = "fo:bookmark-tree";
    /**
     * 书签
     * <p>标签："fo:bookmark"</p>
     */
    public static final String BOOKMARK = "fo:bookmark";
    /**
     * 书签标题
     * <p>标签："fo:bookmark-title"</p>
     */
    public static final String BOOKMARK_TITLE = "fo:bookmark-title";
}
