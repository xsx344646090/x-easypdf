package org.dromara.pdf.fop;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;

import java.util.Optional;

/**
 * @author xsx
 * @date 2023/11/16
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
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

    /**
     * 日志
     */
    public static final Log log = LogFactory.getLog(BaseTest.class);

    @Before
    public void setup() {
        // 初始化日志实现
        System.setProperty("org.apache.commons.logging.log", "org.apache.commons.logging.impl.SimpleLog");
        // 初始化日志级别
        System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "info");
    }

    @SneakyThrows
    public void test(Function function) {
        this.test(function, null);
    }

    @SneakyThrows
    public void test(Function function, String message) {
        long begin = System.currentTimeMillis();
        function.test();
        long end = System.currentTimeMillis();
        long diff = end - begin;
        log.info(Optional.ofNullable(message).orElse("") + "耗时：" + diff / 1000D + "s");
    }

    @FunctionalInterface
    public interface Function {
        void test() throws Exception;
    }
}
