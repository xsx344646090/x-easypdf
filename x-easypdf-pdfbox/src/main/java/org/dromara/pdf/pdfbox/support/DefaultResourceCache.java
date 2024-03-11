package org.dromara.pdf.pdfbox.support;

import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.ResourceCache;
import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.apache.pdfbox.pdmodel.graphics.shading.PDShading;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

/**
 * 资源缓存
 *
 * @author xsx
 * @date 2024/1/22
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
public class DefaultResourceCache implements ResourceCache {

    @Override
    public PDFont getFont(COSObject indirect) {
        return null;
    }

    @Override
    public PDColorSpace getColorSpace(COSObject indirect) {
        return null;
    }

    @Override
    public PDExtendedGraphicsState getExtGState(COSObject indirect) {
        return null;
    }

    @Override
    public PDShading getShading(COSObject indirect) {
        return null;
    }

    @Override
    public PDAbstractPattern getPattern(COSObject indirect) {
        return null;
    }

    @Override
    public PDPropertyList getProperties(COSObject indirect) {
        return null;
    }

    @Override
    public PDXObject getXObject(COSObject indirect) {
        return null;
    }

    @Override
    public void put(COSObject indirect, PDFont font) {

    }

    @Override
    public void put(COSObject indirect, PDColorSpace colorSpace) {

    }

    @Override
    public void put(COSObject indirect, PDExtendedGraphicsState extGState) {

    }

    @Override
    public void put(COSObject indirect, PDShading shading) {

    }

    @Override
    public void put(COSObject indirect, PDAbstractPattern pattern) {

    }

    @Override
    public void put(COSObject indirect, PDPropertyList propertyList) {

    }

    @Override
    public void put(COSObject indirect, PDXObject xobject) {

    }
}
