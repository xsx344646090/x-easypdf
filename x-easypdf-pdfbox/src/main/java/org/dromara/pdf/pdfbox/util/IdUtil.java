package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;

/**
 * id工具
 *
 * @author xsx
 * @date 2025/4/8
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
public class IdUtil {

    /**
     * 雪花
     */
    private static final Snowflake SNOWFLAKE = new Snowflake(0, 0);

    /**
     * 获取id
     *
     * @return 返回id
     */
    public static String get() {
        return SNOWFLAKE.nextIdStr();
    }

    /**
     * 雪花
     */
    private static final class Snowflake {

        /**
         * 起始时间（2000-01-01 00：00：00）
         */
        private static final long EPOCH = 946656000000L;
        /**
         * 当前机器码id
         */
        private final long workerId;
        /**
         * 当前数据中心id
         */
        private final long dataCenterId;
        /**
         * 上一次时间戳
         */
        private long lastTimestamp = -1L;
        /**
         * 时间偏移量
         */
        private long offset = 0L;
        /**
         * 序列
         */
        private long sequence = 0L;

        /**
         * 构造
         *
         * @param workerId     机器码id
         * @param dataCenterId 数据中心id
         */
        public Snowflake(long workerId, long dataCenterId) {
            // 最大支持机器节点数0~31，一共32个
            long maxWorkerId = 31L;
            // 如果机器码id大于最大id或小于0，则提示错误信息
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %s or less than 0", maxWorkerId));
            }
            // 初始化机器码id
            this.workerId = workerId;
            // 初始化数据中心id
            this.dataCenterId = dataCenterId;
        }

        /**
         * 下一个ID
         *
         * @return ID
         */
        @SneakyThrows
        public synchronized long nextId() {
            // 当前时间
            long currentTime = System.currentTimeMillis();
            // 重置时间偏移量
            if (currentTime >= this.lastTimestamp) {
                this.offset = 0L;
            }
            // 获取当前时间戳
            long timestamp = currentTime + this.offset;
            // 时间回拨或超前
            if (timestamp < this.lastTimestamp) {
                // 处理时间回拨
                timestamp = this.processBackTime(timestamp);
            }
            // 如果上一次时间戳等于当前时间戳，则重置序列
            if (this.lastTimestamp == timestamp) {
                // 重置序列
                this.sequence = (this.sequence + 1) & 3L;
                // 如果序列等于0，则重置当前时间戳
                if (this.sequence == 0) {
                    // 重置当前时间戳
                    timestamp = this.tilNextMillis();
                }
            } else {
                // 重置序列为0
                this.sequence = 0L;
            }
            // 重置上一次时间戳为当前时间戳
            this.lastTimestamp = timestamp;
            // 返回id
            return ((timestamp - EPOCH) << 22L) | (this.dataCenterId << 17L) | (this.workerId << 2L) | this.sequence;
        }

        /**
         * 下一个ID（字符串形式）
         *
         * @return ID 字符串形式
         */
        public String nextIdStr() {
            return Long.toString(this.nextId());
        }

        // ------------------------------------------------------------------------------------------------------------------------------------ Private method start

        /**
         * 循环等待下一个时间
         *
         * @return 下一个时间
         */
        private long tilNextMillis() {
            // 获取当前时间戳
            long timestamp = System.currentTimeMillis() + this.offset;
            // 如果当前时间戳等于上一次时间戳，则重置当前时间戳
            while (timestamp <= this.lastTimestamp) {
                // 重置当前时间戳
                timestamp = System.currentTimeMillis() + this.offset;
                // 时间回拨
                if (timestamp < this.lastTimestamp) {
                    // 处理时间回拨
                    timestamp = this.processBackTime(timestamp);
                    // 重置序列为0
                    this.sequence = 0L;
                }
            }
            // 返回当前时间戳
            return timestamp;
        }

        /**
         * 处理时间回拨
         *
         * @param timestamp 当前时间戳
         * @return 返回上一次时间戳的下一个时间点
         */
        private long processBackTime(long timestamp) {
            // 重置偏移
            this.offset = this.lastTimestamp - timestamp;
            // 使用上一次时间戳的下一个时间点
            return this.lastTimestamp + 1;
        }
        // ------------------------------------------------------------------------------------------------------------------------------------ Private method end
    }
}
