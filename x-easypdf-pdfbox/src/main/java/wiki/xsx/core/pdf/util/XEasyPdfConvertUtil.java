package wiki.xsx.core.pdf.util;

import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 转换工具
 *
 * @author xsx
 * @date 2020/5/24
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfConvertUtil {

    /**
     * 转为基本类型数组
     *
     * @param list 包装类型列表
     * @return 返回基本类型数组
     */
    public static int[] toInt(List<Integer> list) {
        // 如果列表为空，则返回空数组
        if (list == null || list.isEmpty()) {
            // 返回空数组
            return new int[0];
        }
        // 获取列表大小
        int size = list.size();
        // 创建数组
        int[] array = new int[size];
        // 遍历列表
        for (int i = 0; i < size; i++) {
            // 初始化数组
            array[i] = list.get(i);
        }
        // 返回数组
        return array;
    }

    /**
     * 转为包装类型列表
     *
     * @param array 基本类型数组
     * @return 返回包装类型列表
     */
    public static List<Integer> toInteger(int[] array) {
        // 如果数组为空，则返回空列表
        if (array == null || array.length == 0) {
            // 返回空列表
            return new ArrayList<>(0);
        }
        // 获取数组长度
        int size = array.length;
        // 定义列表
        List<Integer> list = new ArrayList<>(size);
        // 遍历数组
        for (int index : array) {
            // 添加列表
            list.add(index);
        }
        // 返回列表
        return list;
    }

    /**
     * 转为列表
     *
     * @param iterable 迭代器
     * @param <T>      数据类型
     * @return 返回列表
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        // 定义列表
        List<T> list = new ArrayList<>(16);
        // 遍历迭代器
        for (T t : iterable) {
            // 添加列表
            list.add(t);
        }
        // 返回列表
        return list;
    }

    /**
     * 转为新对象（深拷贝）
     *
     * @param object 源对象
     * @param <T>    对象类型
     * @return 返回新对象
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static <T> T toNewObject(Serializable object) {
        // 如果源对象为空，则返回空
        if (object == null) {
            // 返回空
            return null;
        }
        try (
                // 创建字节数组输出流
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                // 创建对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            // 写入对象
            oos.writeObject(object);
            // 刷新
            oos.flush();
            // 创建对象输入流
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
                // 读取对象
                return (T) ois.readObject();
            }
        }
    }
}
