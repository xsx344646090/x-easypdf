package org.dromara.pdf.pdfbox.core.ext.parser.ai;

import lombok.Data;

/**
 * AI解析信息
 *
 * @author xsx
 * @date 2024/12/25
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
public class AIParseInfo {
    
    /**
     * 任务id
     */
    protected String taskId;
    /**
     * 解析结果
     */
    protected String result;
    /**
     * token总消耗数
     */
    protected Integer totalTokens;
    
    /**
     * 有参构造
     *
     * @param taskId      任务id
     * @param result      结果
     * @param totalTokens token总消耗数
     */
    public AIParseInfo(String taskId, String result, String totalTokens) {
        this.taskId = taskId;
        this.result = result;
        this.totalTokens = Integer.parseInt(totalTokens);
    }
}