package wiki.xsx.core.pdf.template.doc.page;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * pdf模板-页面参数
 *
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
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
class XEasyPdfTemplatePageParam {
    /**
     * 页面宽度
     */
    private String width = "21cm";
    /**
     * 页面高度
     */
    private String height = "29.7cm";
    /**
     * 上边距
     */
    private String marginTop;
    /**
     * 下边距
     */
    private String marginBottom;
    /**
     * 左边距
     */
    private String marginLeft;
    /**
     * 右边距
     */
    private String marginRight;
    /**
     * id
     */
    private String id;
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
     * 页面主体参数
     */
    private XEasyPdfTemplateRegionBodyParam regionBodyParam = new XEasyPdfTemplateRegionBodyParam();
    /**
     * 页面左侧栏参数
     */
    private XEasyPdfTemplateRegionStartParam regionStartParam = new XEasyPdfTemplateRegionStartParam();
    /**
     * 页面右侧栏参数
     */
    private XEasyPdfTemplateRegionEndParam regionEndParam = new XEasyPdfTemplateRegionEndParam();
    /**
     * 页眉参数
     */
    private XEasyPdfTemplateRegionBeforeParam regionBeforeParam = new XEasyPdfTemplateRegionBeforeParam();
    /**
     * 页脚参数
     */
    private XEasyPdfTemplateRegionAfterParam regionAfterParam = new XEasyPdfTemplateRegionAfterParam();

    /**
     * 切换横向
     */
    @SuppressWarnings("all")
    void changeLandscape() {
        // 获取临时页面宽度
        String tempWidth = this.width;
        // 重置页面宽度 = 页面高度
        this.width = this.height;
        // 重置页面高度 = 临时页面宽度
        this.height = tempWidth;
    }

    /**
     * 是否包含主体
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasBody() {
        return !this.regionBodyParam.getComponents().isEmpty();
    }

    /**
     * 是否包含左侧栏
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasStart() {
        return !this.regionStartParam.getComponents().isEmpty();
    }

    /**
     * 是否包含右侧栏
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasEnd() {
        return !this.regionEndParam.getComponents().isEmpty();
    }

    /**
     * 是否包含页眉
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasHeader() {
        return !this.regionBeforeParam.getComponents().isEmpty();
    }

    /**
     * 是否包含页脚
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasFooter() {
        return !this.regionAfterParam.getComponents().isEmpty();
    }
}
