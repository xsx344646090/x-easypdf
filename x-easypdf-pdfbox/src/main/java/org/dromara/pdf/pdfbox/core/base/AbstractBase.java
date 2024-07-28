package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.pdf.pdfbox.core.enums.ContentMode;

import java.util.Objects;

/**
 * 抽象基础类
 *
 * @author xsx
 * @date 2023/6/2
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
@Data
public abstract class AbstractBase {
    /**
     * 日志
     */
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * 上下文
     */
    protected Context context;
    /**
     * 内容模式
     */
    protected ContentMode contentMode;
    /**
     * 是否重置内容流
     */
    protected Boolean isResetContentStream;

    /**
     * 初始化
     *
     */
    protected void init(Context context) {
        // 初始化上下文
        this.context = context;
        // 初始化内容模式
        this.contentMode = ContentMode.APPEND;
        // 初始化是否重置内容流
        this.isResetContentStream = Boolean.TRUE;
    }

    /**
     * 初始化
     * @param base 基础类
     */
    protected void init(AbstractBase base) {
        // 初始化上下文
        this.context = base.context;
        // 初始化内容模式
        if (Objects.isNull(this.contentMode)) {
            this.contentMode = base.contentMode;
        }
        // 初始化是否重置内容流
        if (Objects.isNull(this.isResetContentStream)) {
            this.isResetContentStream = base.isResetContentStream;
        }
    }
}
