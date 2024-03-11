package org.dromara.pdf.fop.support.barcode;

import lombok.SneakyThrows;
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.PropertyList;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

import java.awt.geom.Point2D;

/**
 * 条形码元素
 *
 * @author xsx
 * @date 2022/10/12
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class BarcodeElement extends BarcodeObj {

    /**
     * 有参构造
     *
     * @param parent 父节点
     */
    public BarcodeElement(FONode parent) {
        super(parent);
    }

    /**
     * 处理节点
     *
     * @param elementName  元素名称
     * @param locator      定位器
     * @param attlist      当前节点属性列表
     * @param propertyList 父节点属性列表
     */
    @SneakyThrows
    public void processNode(String elementName, Locator locator, Attributes attlist, PropertyList propertyList) {
        super.processNode(elementName, locator, attlist, propertyList);
        this.createBasicDocument();
    }

    /**
     * 获取维度
     *
     * @param view 2d维度
     * @return 返回维度
     */
    public Point2D getDimension(Point2D view) {
        Node barcode = this.doc.getFirstChild();
        if (barcode != null && BarcodeImageHandler.IMAGE_TYPE.equals(barcode.getLocalName())) {
            BarCodeConfig config = BarCodeConfig.onlyInitRectangle(barcode.getAttributes());
            return new Point2D.Float((float) config.getWidth(), (float) config.getHeight());
        }
        return null;
    }
}
