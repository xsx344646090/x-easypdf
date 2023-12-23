package org.dromara.pdf.pdfbox;

import lombok.SneakyThrows;
import org.junit.Before;

/**
 * @author xsx
 * @date 2023/11/16
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class BaseTest {

    @FunctionalInterface
    public interface Function {
        void execute() throws Exception;
    }

    @Before
    public void setup() {
        // 初始化日志实现
        System.setProperty("org.apache.commons.logging.log", "org.apache.commons.logging.impl.SimpleLog");
        // 初始化日志级别
        System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "info");
    }

    @SneakyThrows
    public void test(Function function) {
        long begin = System.currentTimeMillis();
        function.execute();
        long end = System.currentTimeMillis();
        long diff = end - begin;
        System.out.printf("耗时：%ss\n", diff / 1000D);
    }
}
