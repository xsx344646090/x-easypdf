package org.dromara.pdf.pdfbox.core.ext.convertor.ppt;

import com.documents4j.api.DocumentType;
import lombok.Getter;

/**
 * ppt类型
 *
 * @author xsx
 * @date 2025/1/8
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
@Getter
public enum PowerpointType {
    /**
     * ppt
     */
    PPT(DocumentType.PPT),
    /**
     * pptx
     */
    PPTX(DocumentType.PPTX);
    
    /**
     * 类型
     */
    private final DocumentType type;
    
    /**
     * 有参构造
     *
     * @param type 类型
     */
    PowerpointType(DocumentType type) {
        this.type = type;
    }
}
