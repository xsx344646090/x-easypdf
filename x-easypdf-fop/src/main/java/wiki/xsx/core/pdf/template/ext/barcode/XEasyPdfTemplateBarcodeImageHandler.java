package wiki.xsx.core.pdf.template.ext.barcode;

import org.apache.fop.apps.MimeConstants;
import org.apache.fop.render.ImageHandler;
import org.apache.fop.render.RenderingContext;
import org.apache.xmlgraphics.image.loader.Image;
import org.apache.xmlgraphics.image.loader.ImageFlavor;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * 条形码图像助手
 *
 * @author xsx
 * @date 2022/10/15
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
public class XEasyPdfTemplateBarcodeImageHandler implements ImageHandler {

    /**
     * 图像类型
     */
    public static final String IMAGE_TYPE = "barcode";
    /**
     * mime类型
     */
    public static final String MIME_TYPE = MimeConstants.MIME_PNG;
    /**
     * 图像类型
     */
    public static final ImageFlavor IMAGE_FLAVOR = new ImageFlavor(IMAGE_TYPE);
    /**
     * 图像类型数组
     */
    public static final ImageFlavor[] IMAGE_FLAVORS = new ImageFlavor[]{IMAGE_FLAVOR};

    /**
     * 是否兼容
     *
     * @param targetContext 上下文
     * @param image         图像
     * @return 返回布尔值，是为true，否为false
     */
    @Override
    public boolean isCompatible(RenderingContext targetContext, Image image) {
        if (image != null) {
            return image.getFlavor().isCompatible(IMAGE_FLAVOR);
        }
        return true;
    }

    /**
     * 处理图像
     *
     * @param context 上下文
     * @param image   图像
     * @param pos     尺寸
     */
    @Override
    public void handleImage(RenderingContext context, Image image, Rectangle pos) {

    }

    /**
     * 获取优先级
     *
     * @return 返回优先级
     */
    @Override
    public int getPriority() {
        return 0;
    }

    /**
     * 获取支持的图像类型
     *
     * @return 返回图像类型数组
     */
    @Override
    public ImageFlavor[] getSupportedImageFlavors() {
        return IMAGE_FLAVORS;
    }

    /**
     * 获取支持的图像类
     *
     * @return 返回缓冲图像类
     */
    @Override
    public Class<BufferedImage> getSupportedImageClass() {
        return BufferedImage.class;
    }
}
