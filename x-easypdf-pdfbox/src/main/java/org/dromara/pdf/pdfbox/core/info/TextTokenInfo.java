package org.dromara.pdf.pdfbox.core.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.List;
import java.util.Objects;

/**
 * 文本标记信息
 *
 * @author xsx
 * @date 2024/8/27
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
public class TextTokenInfo {
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
     * 字体大小
     */
    private Float fontSize;
    /**
     * 是否替换
     */
    private Boolean isReplace;
    /**
     * token列表
     */
    private List<TextValue> tokens;

    /**
     * 文本值
     */
    @Data
    public static class TextValue {
        /**
         * 索引
         */
        private Integer index;
        /**
         * 文本矩阵索引
         */
        private Integer matrixIndex;
        /**
         * X轴坐标
         */
        private Float x;
        /**
         * Y轴坐标
         */
        private Float y;
        /**
         * 标记
         */
        private Object token;
        /**
         * 值
         */
        private String value;
        /**
         * 替换值
         */
        private String replaceValue;
        /**
         * 是否被替换
         */
        private Boolean isReplaced;
        /**
         * 是否嵌入子集
         */
        private Boolean isEmbedSubset;
        /**
         * 子集
         */
        private List<TextValue> children;

        /**
         * 有参构造
         *
         * @param index       索引
         * @param matrixIndex 文本矩阵索引
         * @param x           X轴坐标
         * @param y           Y轴坐标
         * @param token       标记
         * @param value       文本值
         */
        public TextValue(Integer index, Integer matrixIndex, Float x, Float y, Object token, String value) {
            this.index = index;
            this.matrixIndex = matrixIndex;
            this.x = x;
            this.y = y;
            this.token = token;
            this.value = value;
            this.replaceValue = value;
        }

        /**
         * 是否替换
         *
         * @return 返回布尔值，true为是，false为否
         */
        public boolean isReplaced() {
            return Objects.nonNull(this.isReplaced) && this.isReplaced;
        }
    }

}
