package wiki.xsx.core.pdf.template.page;

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
    private String pageWidth = "595.27563px";
    /**
     * 页面高度
     */
    private String pageHeight = "841.8898px";
    /**
     * 上边距
     */
    private String marginTop = "0px";
    /**
     * 下边距
     */
    private String marginBottom = "0px";
    /**
     * 左边距
     */
    private String marginLeft = "0px";
    /**
     * 右边距
     */
    private String marginRight = "0px";
    /**
     * 页面主体参数
     */
    private XEasyPdfTemplateRegionBodyParam regionBodyParam = new XEasyPdfTemplateRegionBodyParam();
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
        String tempWidth = this.pageWidth;
        // 重置页面宽度 = 页面高度
        this.pageWidth = this.pageHeight;
        // 重置页面高度 = 临时页面宽度
        this.pageHeight = tempWidth;
    }

    /**
     * 是否包含主体
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasBody() {
        return !this.regionBodyParam.getComponentList().isEmpty();
    }

    /**
     * 是否包含页眉
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasHeader() {
        return !this.regionBeforeParam.getComponentList().isEmpty();
    }

    /**
     * 是否包含页脚
     *
     * @return 返回布尔值，是为true，否为false
     */
    boolean hasFooter() {
        return !this.regionAfterParam.getComponentList().isEmpty();
    }
}
