package wiki.xsx.core.pdf.template.enums;

/**
 * pdf模板边框样式
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
public enum XEasyPdfTemplateBorderStyle {

    /**
     * 无
     */
    NONE("none"),
    /**
     * 隐藏
     */
    HIDDEN("hidden"),
    /**
     * 点虚线
     */
    DOTTED("dotted"),
    /**
     * 短虚线
     */
    DASHED("dashed"),
    /**
     * 实线
     */
    SOLID("solid"),
    /**
     * 双实线
     */
    DOUBLE("double"),
    /**
     * 凹线（槽）
     */
    GROOVE("groove"),
    /**
     * 凸线（脊）
     */
    RIDGE("ridge"),
    /**
     * 嵌入
     */
    INSET("inset"),
    /**
     * 凸出
     */
    OUTSET("outset");

    /**
     * 值
     */
    private final String value;

    XEasyPdfTemplateBorderStyle(String value) {
        this.value = value;
    }

    /**
     * 获取键
     *
     * @return 返回键
     */
    public String getKey() {
        return "border-style";
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
