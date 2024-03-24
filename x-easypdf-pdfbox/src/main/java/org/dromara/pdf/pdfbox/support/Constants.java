package org.dromara.pdf.pdfbox.support;

/**
 * 常量
 *
 * @author xsx
 * @date 2023/6/3
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
public final class Constants {

    /**
     * 每英寸像素点
     */
    public static final Integer POINTS_PER_INCH = 72;
    /**
     * 每毫米像素点
     */
    public static final Float POINTS_PER_MM = 1 / 25.4f * POINTS_PER_INCH;
    /**
     * 生产者
     */
    public static final String PRODUCER = "x-easypdf/pdfbox";
    /**
     * 格式类型
     */
    public static final String MIME_TYPE = "application/pdf";
    /**
     * 当前页码占位符
     */
    public static final String CURRENT_PAGE_PLACEHOLDER = "${CPN}";
    /**
     * 默认版本
     */
    public static final Float DEFAULT_VERSION = 1.6F;
    /**
     * 默认字体
     */
    public static final String DEFAULT_FONT_NAME = "HarmonyOS_Sans_SC_Medium";
    /**
     * 默认字体路径
     */
    public static final String DEFAULT_FONT_RESOURCE_PATH = "org/dromara/pdf/pdfbox/ttf/HarmonyOS_Sans_SC_Medium.ttf";
    /**
     * 默认外观
     */
    public static final String DEFAULT_APPEARANCE = String.join("", "/", DEFAULT_FONT_NAME, " 10 Tf 0 g");
    /**
     * 用户家目录路径
     */
    public static final String USER_HOME_PATH = System.getProperty("user.home");
    /**
     * 临时文件路径
     */
    public static final String TEMP_FILE_PATH = System.getProperty("java.io.tmpdir");
    /**
     * 字体缓存文件路径
     */
    public static final String FONT_CACHE_PATH = System.getProperty("pdfbox.fontcache");
    /**
     * 字体缓存文件后缀名
     */
    public static final String FONT_CACHE_SUFFIX_NAME = ".pdfbox.cache";
}