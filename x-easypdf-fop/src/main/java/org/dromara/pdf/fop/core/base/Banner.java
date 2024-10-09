package org.dromara.pdf.fop.core.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 标语
 *
 * @author xsx
 * @date 2024/2/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Banner {
    /**
     * 标语键
     */
    private static final String BANNER_KEY = "x-easypdf.banner";
    /**
     * 当前版本
     */
    private static final String VERSION = initVersion();
    /**
     * 文本
     */
    private static final String TEXT = "\n" +
            " ____  ____     ________       _       ______  ____  ____  _______  ______   ________  \n" +
            "|_  _||_  _|   |_   __  |     / \\    .' ____ \\|_  _||_  _||_   __ \\|_   _ `.|_   __  | \n" +
            "  \\ \\  / /______ | |_ \\_|    / _ \\   | (___ \\_| \\ \\  / /    | |__) | | | `. \\ | |_ \\_| \n" +
            "   > `' <|______||  _| _    / ___ \\   _.____`.   \\ \\/ /     |  ___/  | |  | | |  _|    \n" +
            " _/ /'`\\ \\_     _| |__/ | _/ /   \\ \\_| \\____) |  _|  |_    _| |_    _| |_.' /_| |_     \n" +
            "|____||____|   |________||____| |____|\\______.' |______|  |_____|  |______.'|_____|    \n" +
            "# Version: " + VERSION + "\n" +
            "# Website: https://x-easypdf.cn\n" +
            "# Repository: https://gitee.com/dromara/x-easypdf\n" +
            "# If you find it useful, please give a star.\n";

    /**
     * 关闭
     */
    public static void disable() {
        System.setProperty(BANNER_KEY, "false");
    }

    /**
     * 打印
     */
    public static void print() {
        String flag = System.getProperty(BANNER_KEY, "true");
        if ("true".equals(flag)) {
            System.out.println(TEXT);
        }
    }

    /**
     * 初始化版本
     *
     * @return 返回版本
     */
    private static String initVersion() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("x-easypdf.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return "v" + properties.getProperty("version", "unknown");
        } catch (IOException e) {
            return "unknown";
        }
    }
}
