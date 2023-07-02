package org.dromara.pdf.fop.ext.barcode;

import org.apache.fop.fo.FONode;
import org.apache.fop.fo.XMLObj;
import org.dromara.pdf.fop.XEasyPdfTemplateConstants;

/**
 * 条形码对象
 *
 * @author xsx
 * @date 2022/10/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfTemplateBarcodeObj extends XMLObj {

    /**
     * 有参构造
     *
     * @param parent 父节点
     */
    public XEasyPdfTemplateBarcodeObj(FONode parent) {
        super(parent);
    }

    /**
     * 获取命名空间
     *
     * @return 返回命名空间
     */
    public String getNamespaceURI() {
        return XEasyPdfTemplateConstants.NAMESPACE;
    }

    /**
     * 获取命名空间前缀
     *
     * @return 返回命名空间前缀
     */
    public String getNormalNamespacePrefix() {
        return "xe";
    }
}
