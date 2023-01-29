package wiki.xsx.core.pdf.template.ext.barcode;

import org.apache.xmlgraphics.image.loader.Image;
import org.apache.xmlgraphics.image.loader.ImageFlavor;
import org.apache.xmlgraphics.image.loader.ImageInfo;
import org.apache.xmlgraphics.image.loader.ImageSessionContext;
import org.apache.xmlgraphics.image.loader.impl.AbstractImageLoader;
import org.apache.xmlgraphics.image.loader.impl.ImageBuffered;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 条形码加载器
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
public class XEasyPdfTemplateBarcodeLoader extends AbstractImageLoader {

    /**
     * 加载图像
     *
     * @param info    图像信息
     * @param hints   提示映射
     * @param session 会话上下文
     * @return 返回图像
     */
    @Override
    public Image loadImage(ImageInfo info, Map hints, ImageSessionContext session) {
        return new ImageBuffered(info, (BufferedImage) info.getCustomObjects().get(XEasyPdfTemplateBarcodeImageHandler.IMAGE_TYPE), null);
    }

    /**
     * 获取目标图像类型
     *
     * @return 返回图像类型
     */
    @Override
    public ImageFlavor getTargetFlavor() {
        return XEasyPdfTemplateBarcodeImageHandler.IMAGE_FLAVOR;
    }
}
