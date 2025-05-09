package org.dromara.pdf.pdfbox.handler;

import com.documents4j.api.IConverter;
import com.documents4j.conversion.msoffice.MicrosoftExcelBridge;
import com.documents4j.conversion.msoffice.MicrosoftPowerpointBridge;
import com.documents4j.conversion.msoffice.MicrosoftWordBridge;
import com.documents4j.job.RemoteConverter;

import java.util.concurrent.TimeUnit;

/**
 * 转换器助手
 *
 * @author xsx
 * @date 2025/5/6
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
public class ConvertorHandler {

    /**
     * 创建远程转换器
     * <p>注：仅创建一次</p>
     *
     * @return 返回远程转换器
     */
    public static IConverter createRemote(String baseUri) {
        return RemoteConverter.builder()
                .requestTimeout(300, TimeUnit.SECONDS)
                .baseUri(baseUri)
                .build();
    }

    /**
     * 创建本地转换器
     * <p>注：仅创建一次</p>
     *
     * @return 返回本地转换器
     */
    public static IConverter createLocal() {
        return com.documents4j.job.LocalConverter.builder()
                .enable(MicrosoftPowerpointBridge.class)
                .processTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建本地转换器（仅word）
     * <p>注：仅创建一次</p>
     *
     * @return 返回本地转换器
     */
    public static IConverter createLocalWithWord() {
        return com.documents4j.job.LocalConverter.builder()
                .disable(MicrosoftPowerpointBridge.class)
                .disable(MicrosoftExcelBridge.class)
                .processTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建本地转换器（仅excel）
     * <p>注：仅创建一次</p>
     *
     * @return 返回本地转换器
     */
    public static IConverter createLocalWithExcel() {
        return com.documents4j.job.LocalConverter.builder()
                .disable(MicrosoftPowerpointBridge.class)
                .disable(MicrosoftWordBridge.class)
                .processTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建本地转换器（仅ppt）
     * <p>注：仅创建一次</p>
     *
     * @return 返回本地转换器
     */
    public static IConverter createLocalWithPowerpoint() {
        return com.documents4j.job.LocalConverter.builder()
                .enable(MicrosoftPowerpointBridge.class)
                .disable(MicrosoftWordBridge.class)
                .disable(MicrosoftExcelBridge.class)
                .processTimeout(300, TimeUnit.SECONDS)
                .build();
    }
}
