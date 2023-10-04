package org.dromara.pdf.pdfbox.core;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.dromara.pdf.pdfbox.support.Constants;

import java.nio.file.Paths;

/**
 * 内存策略
 *
 * @author xsx
 * @date 2023/6/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
     * 仅设置主内存
     *
     * @return 返回内存策略
     */
    public static MemoryPolicy setupMainMemoryOnly() {
        return new MemoryPolicy(MemoryUsageSetting.setupMainMemoryOnly());
    }

    /**
     * 仅设置临时文件
     *
     * @return 返回内存策略
     */
    public static MemoryPolicy setupTempFileOnly() {
        return setupTempFileOnly(Constants.TEMP_FILE_PATH);
    }

    /**
     * 仅设置临时文件
     *
     * @param tempPath 临时文件路径
     * @return 返回内存策略
     */
    public static MemoryPolicy setupTempFileOnly(String tempPath) {
        MemoryUsageSetting setting = MemoryUsageSetting.setupTempFileOnly();
        setting.setTempDir(Paths.get(tempPath).toFile());
        return new MemoryPolicy(setting);
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
