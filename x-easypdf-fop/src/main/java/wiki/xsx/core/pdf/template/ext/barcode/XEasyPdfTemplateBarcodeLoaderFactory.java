package wiki.xsx.core.pdf.template.ext.barcode;

import org.apache.xmlgraphics.image.loader.ImageFlavor;
import org.apache.xmlgraphics.image.loader.impl.AbstractImageLoaderFactory;
import org.apache.xmlgraphics.image.loader.spi.ImageLoader;

/**
 * 条形码加载器工厂
 *
 * @author xsx
 * @date 2022/10/15
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplateBarcodeLoaderFactory extends AbstractImageLoaderFactory {

    /**
     * 获取支持的类型名称
     *
     * @return 返回类型名称
     */
    @Override
    public String[] getSupportedMIMETypes() {
        return new String[]{XEasyPdfTemplateBarcodeImageHandler.MIME_TYPE};
    }

    /**
     * 获取支持的图像类型
     *
     * @param mime 类型名称
     * @return 返回图像类型
     */
    @Override
    public ImageFlavor[] getSupportedFlavors(String mime) {
        return XEasyPdfTemplateBarcodeImageHandler.IMAGE_FLAVORS;
    }

    /**
     * 获取新图像加载器
     *
     * @param targetFlavor 目标图像类型
     * @return 返回新图像加载器
     */
    @Override
    public ImageLoader newImageLoader(ImageFlavor targetFlavor) {
        return new XEasyPdfTemplateBarcodeLoader();
    }

    /**
     * 是否可用
     *
     * @return 返回是
     */
    @Override
    public boolean isAvailable() {
        return true;
    }
}
