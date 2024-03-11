package org.dromara.pdf.pdfbox.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * 文件工具
 *
 * @author xsx
 * @date 2023/6/14
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
public class FileUtil {

    /**
     * 创建目录
     *
     * @param path 路径
     * @return 返回路径
     */
    public static Path createDirectories(Path path) {
        // 如果目录不存在，则创建
        if (!Files.exists(path)) {
            try {
                // 定义目录路径
                Path directoryPath;
                // 如果给定路径不为目录，则初始化为父目录
                if (!Files.isDirectory(path)) {
                    // 初始化为父目录
                    directoryPath = path.getParent();
                } else {
                    // 初始化为给定目录
                    directoryPath = path;
                }
                // 创建目录
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                // 提示错误信息
                throw new IllegalArgumentException("the path is illegal and create directory fail");
            }
        }
        // 返回路径
        return path;
    }

    /**
     * 解析后缀
     *
     * @param file 文件
     * @return 返回后缀
     */
    public static String parseSuffix(File file) {
        // 如果文件为空，则提示错误信息
        Objects.requireNonNull(file, "the file can not be null");
        // 获取文件名称
        String name = file.getName();
        // 获取最后一个点号位置
        int dot = name.lastIndexOf('.');
        // 如果位置未获取到，则提示错误信息
        if (dot == -1) {
            // 提示错误信息
            throw new IllegalArgumentException("the file suffix can not supported: " + name);
        }
        // 返回后缀名
        return name.substring(dot + 1).toLowerCase();
    }
}
