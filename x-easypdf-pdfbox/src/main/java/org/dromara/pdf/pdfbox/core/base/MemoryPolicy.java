package org.dromara.pdf.pdfbox.core.base;

import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.shade.org.apache.pdfbox.io.MemoryUsageSetting;

import java.nio.file.Paths;

/**
 * 内存策略
 *
 * @author xsx
 * @date 2023/6/12
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
public class MemoryPolicy {

    /**
     * pdfbox内存使用设置
     */
    private final MemoryUsageSetting setting;

    /**
     * 有参构造
     *
     * @param setting pdfbox内存使用设置
     */
    private MemoryPolicy(MemoryUsageSetting setting) {
        this.setting = setting;
    }

    /**
     * 仅主内存
     *
     * @return 返回内存策略
     */
    public static MemoryPolicy setupMainMemoryOnly() {
        return new MemoryPolicy(MemoryUsageSetting.setupMainMemoryOnly());
    }

    /**
     * 仅主内存
     *
     * @param maxMemoryBytes 最大内存字节
     * @return 返回内存策略
     */
    public static MemoryPolicy setupMainMemoryOnly(long maxMemoryBytes) {
        return new MemoryPolicy(MemoryUsageSetting.setupMainMemoryOnly(maxMemoryBytes));
    }

    /**
     * 仅临时文件
     *
     * @return 返回内存策略
     */
    public static MemoryPolicy setupTempFileOnly() {
        return setupTempFileOnly(Constants.TEMP_FILE_PATH);
    }

    /**
     * 仅临时文件
     *
     * @param tempPath 临时文件路径
     * @return 返回内存策略
     */
    public static MemoryPolicy setupTempFileOnly(String tempPath) {
        return new MemoryPolicy(MemoryUsageSetting.setupTempFileOnly().setTempDir(Paths.get(tempPath).toFile()));
    }

    /**
     * 混合模式
     *
     * @param tempPath 临时文件路径
     * @return 返回内存策略
     */
    public static MemoryPolicy setupMix(long maxMemoryBytes, String tempPath) {
        return new MemoryPolicy(MemoryUsageSetting.setupMainMemoryOnly(maxMemoryBytes).setTempDir(Paths.get(tempPath).toFile()));
    }

    /**
     * 获取设置
     *
     * @return 返回pdfbox内存使用设置
     */
    MemoryUsageSetting getSetting() {
        return this.setting;
    }
}
