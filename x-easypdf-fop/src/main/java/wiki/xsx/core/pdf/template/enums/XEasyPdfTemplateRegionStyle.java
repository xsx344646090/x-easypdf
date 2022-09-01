package wiki.xsx.core.pdf.template.enums;

/**
 * pdf模板区域样式
 *
 * @author xsx
 * @date 2022/8/27
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public enum XEasyPdfTemplateRegionStyle {

    /**
     * 主体
     */
    BODY("xsl-region-body"),
    /**
     * 页眉
     */
    BEFORE("xsl-region-before"),
    /**
     * 页脚
     */
    AFTER("xsl-region-after"),
    /**
     * 左侧栏
     */
    START("xsl-region-start"),
    /**
     * 右侧栏
     */
    END("xsl-region-end");

    /**
     * 值
     */
    private final String value;

    /**
     * 有参构造
     *
     * @param value 值
     */
    XEasyPdfTemplateRegionStyle(String value) {
        this.value = value;
    }

    /**
     * 获取值
     *
     * @return 返回值
     */
    public String getValue() {
        return this.value;
    }
}
