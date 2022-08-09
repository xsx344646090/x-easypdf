package wiki.xsx.core.pdf.template.component.image;

import lombok.Data;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTextPositionStyle;

/**
 * pdf模板-图像参数
 *
 * @author xsx
 * @date 2022/8/9
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
@Data
@Accessors(chain = true)
class XEasyPdfTemplateImageParam {
    /**
     * 宽度
     */
    private String width;
    /**
     * 高度
     */
    private String height;
    /**
     * 图像路径
     */
    private String path;
    /**
     * 是否远程图片
     */
    private Boolean isRemote;
    /**
     * 是否包含边框
     */
    private Boolean hasBorder;
    /**
     * 水平样式（居左、居中、居右）
     */
    private XEasyPdfTemplateTextPositionStyle horizontalStyle;
    /**
     * 垂直样式（居上、居中、居下）
     */
    private XEasyPdfTemplateTextPositionStyle verticalStyle;
}
