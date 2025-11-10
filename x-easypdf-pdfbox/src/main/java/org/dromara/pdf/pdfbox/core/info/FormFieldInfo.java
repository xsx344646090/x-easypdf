package org.dromara.pdf.pdfbox.core.info;

import lombok.Builder;
import lombok.Data;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDPage;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * 表单字段信息
 *
 * @author xsx
 * @date 2023/2/21
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
@Builder
public class FormFieldInfo {
    /**
     * 页面索引
     */
    private Integer pageIndex;
    /**
     * 页面宽度
     */
    private Float pageWidth;
    /**
     * 页面高度
     */
    private Float pageHeight;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;
    /**
     * 是否只读
     */
    private Boolean isReadOnly;
    /**
     * 是否必须
     */
    private Boolean isRequired;
    /**
     * 是否非导出
     */
    private Boolean isNoExport;
    /**
     * 字段
     */
    private PDField field;
    /**
     * 页面
     */
    private PDPage page;


    @Override
    public String toString() {
        return "FormFieldInfo{" +
                "pageIndex=" + pageIndex +
                ", pageWidth=" + pageWidth +
                ", pageHeight=" + pageHeight +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", isReadOnly=" + isReadOnly +
                ", isRequired=" + isRequired +
                ", isNoExport=" + isNoExport +
                '}';
    }
}
