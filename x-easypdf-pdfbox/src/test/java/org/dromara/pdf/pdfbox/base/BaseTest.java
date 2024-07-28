package org.dromara.pdf.pdfbox.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

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


    protected final Log log = LogFactory.getLog(this.getClass());

    public void test(Function function) {
        this.test(function, null);
    }

    public void test(Function function, String message) {
        String title = Optional.ofNullable(message).orElse("");
        try {
            long begin = System.currentTimeMillis();
            function.test();
            long end = System.currentTimeMillis();
            long diff = end - begin;
            log.info(title + "耗时：" + diff / 1000D + "s");
        } catch (Exception e) {
            log.error(title + "执行失败", e);
        }
    }

    @FunctionalInterface
    public interface Function {
        void test() throws Exception;
    }
}
