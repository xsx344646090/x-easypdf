package wiki.xsx.core.pdf.template.component;

import lombok.Data;
import wiki.xsx.core.pdf.template.XEasyPdfTemplatePositionStyle;

/**
 * pdf模板组件公共参数
 *
 * @author xsx
 * @date 2022/8/11
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
public class XEasyPdfTemplateComponentParam {
    /**
     * 上填充
     */
    protected String paddingTop;
    /**
     * 下填充
     */
    protected String paddingBottom;
    /**
     * 左填充
     */
    protected String paddingLeft;
    /**
     * 右填充
     */
    protected String paddingRight;
    /**
     * 是否包含边框
     */
    protected Boolean hasBorder;
    /**
     * 水平样式（居左、居中、居右）
     */
    protected XEasyPdfTemplatePositionStyle horizontalStyle;
}
