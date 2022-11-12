package wiki.xsx.core.pdf.template.doc.bookmark;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-书签参数
 *
 * @author xsx
 * @date 2022/11/2
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
class XEasyPdfTemplateBookmarkParam {

    /**
     * 内部地址
     * <p>注：标签id</p>
     */
    private String internalDestination;
    /**
     * 标题
     */
    private String title;
    /**
     * 字体名称
     */
    private String fontFamily;
    /**
     * 字体样式
     * <p>normal：正常</p>
     * <p>oblique：斜体</p>
     * <p>italic：斜体</p>
     * <p>backslant：斜体</p>
     */
    private String fontStyle;
    /**
     * 字体大小
     */
    private String fontSize;
    /**
     * 字体大小调整
     */
    private String fontSizeAdjust;
    /**
     * 字体重量
     * <p>normal：正常（400）</p>
     * <p>bold：粗体（700）</p>
     * <p>bolder：加粗（900）</p>
     * <p>lighter：细体（100）</p>
     */
    private String fontWeight;
    /**
     * 字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String fontColor;
    /**
     * 子书签列表
     */
    private List<XEasyPdfTemplateBookmark> children = new ArrayList<>(10);
}
