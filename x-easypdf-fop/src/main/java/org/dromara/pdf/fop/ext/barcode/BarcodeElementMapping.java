package org.dromara.pdf.fop.ext.barcode;

import org.apache.fop.fo.ElementMapping;
import org.apache.fop.fo.FONode;
import org.dromara.pdf.fop.Constants;
import org.w3c.dom.DOMImplementation;

import java.util.HashMap;

/**
 * 条形码元素映射
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
public class BarcodeElementMapping extends ElementMapping {

    /**
     * 无参构造
     */
    public BarcodeElementMapping() {
        this.namespaceURI = Constants.NAMESPACE;
        this.initialize();
    }

    /**
     * 获取文档实现
     *
     * @return 返回文档实现
     */
    public DOMImplementation getDOMImplementation() {
        return getDefaultDOMImplementation();
    }

    /**
     * 初始化
     */
    protected void initialize() {
        if (this.foObjs == null) {
            this.foObjs = new HashMap<>(1);
            this.foObjs.put(BarcodeImageHandler.IMAGE_TYPE, new BarcodeMaker());
        }
    }

    /**
     * 条形码生成器
     */
    static class BarcodeMaker extends Maker {
        public FONode make(FONode parent) {
            return new BarcodeElement(parent);
        }
    }
}
