package org.dromara.pdf.pdfbox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 缓存工具
 *
 * @author xsx
 * @date 2025/9/23
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
public class CacheUtil {

    /**
     * 图片缓存
     */
    public static final Map<String, byte[]> IMAGE_CACHE = createImageCache();
    /**
     * 字符宽度缓存
     */
    public static final Map<String, Map<Character, Float>> CHARACTER_WIDTH_CACHE = createCharacterCache();

    /**
     * 获取图像对象
     *
     * @param key      键
     * @param supplier 提供者
     * @return 返回图像对象
     */
    public static byte[] getImage(String key, Supplier<byte[]> supplier) {
        return IMAGE_CACHE.computeIfAbsent(key, k -> supplier.get());
    }

    /**
     * 获取字符宽度缓存
     *
     * @param fontName  字体名称
     * @return 返回字符宽度
     */
    public static Map<Character, Float> getCharacterCache(String fontName) {
        return CHARACTER_WIDTH_CACHE.computeIfAbsent(fontName, k-> new HashMap<>(3000));
    }

    /**
     * 创建图像缓存
     *
     * @return 返回缓存
     */
    private static Map<String, byte[]> createImageCache() {
        return new HashMap<>(32);
    }

    /**
     * 创建字符缓存
     *
     * @return 返回缓存
     */
    private static Map<String, Map<Character, Float>> createCharacterCache() {
        return new HashMap<>(16);
    }
}
