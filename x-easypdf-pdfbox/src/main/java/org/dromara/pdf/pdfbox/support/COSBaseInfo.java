package org.dromara.pdf.pdfbox.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.List;

/**
 * 标记信息
 *
 * @author xsx
 * @date 2023/10/10
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
@AllArgsConstructor
public class COSBaseInfo {
    /**
     * 字体索引
     */
    private Integer fontIndex;
    /**
     * 原字体
     */
    private PDFont font;
    /**
     * 替换字体
     */
    private PDFont replaceFont;
    /**
     * 是否替换
     */
    private Boolean isReplace;
    /**
     * token列表
     */
    private List<TokenValue> tokens;

    /**
     * 标记值
     */
    @Data
    @AllArgsConstructor
    public static class TokenValue {
        /**
         * 标记
         */
        private Object token;
        /**
         * 值
         */
        private String value;
    }

}
